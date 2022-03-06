// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketFamilyInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import net.minecraft.item.Item;

public class LOTRFamilyInfo
{
    private LOTREntityNPC theEntity;
    public Class marriageEntityClass;
    public Item marriageRing;
    public float marriageAlignmentRequired;
    public LOTRAchievement marriageAchievement;
    public int potentialMaxChildren;
    public int timeToMature;
    public int breedingDelay;
    public UUID spouseUniqueID;
    public int children;
    public int maxChildren;
    public UUID maleParentID;
    public UUID femaleParentID;
    public UUID ringGivingPlayer;
    private boolean doneFirstUpdate;
    private boolean resendData;
    private int age;
    private boolean male;
    private String name;
    private int drunkTime;
    private int timeUntilDrunkSpeech;
    
    public LOTRFamilyInfo(final LOTREntityNPC npc) {
        this.doneFirstUpdate = false;
        this.resendData = true;
        this.theEntity = npc;
    }
    
    public int getAge() {
        return this.age;
    }
    
    public void setAge(final int i) {
        this.age = i;
        this.markDirty();
    }
    
    public boolean isMale() {
        return this.male;
    }
    
    public void setMale(final boolean flag) {
        this.male = flag;
        this.markDirty();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String s) {
        this.name = s;
        this.markDirty();
    }
    
    public boolean isDrunk() {
        return this.drunkTime > 0;
    }
    
    public void setDrunkTime(final int i) {
        final boolean prevDrunk = this.isDrunk();
        this.drunkTime = i;
        if (this.isDrunk() != prevDrunk) {
            this.markDirty();
        }
    }
    
    private void markDirty() {
        if (!((Entity)this.theEntity).worldObj.isClient) {
            if (((Entity)this.theEntity).ticksExisted > 0) {
                this.resendData = true;
            }
            else {
                this.sendDataToAllWatchers();
            }
        }
    }
    
    public void sendData(final EntityPlayerMP entityplayer) {
        final LOTRPacketFamilyInfo packet = new LOTRPacketFamilyInfo(this.theEntity.getEntityId(), this.getAge(), this.isMale(), this.getName(), this.isDrunk());
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    private void sendDataToAllWatchers() {
        final int x = MathHelper.floor_double(((Entity)this.theEntity).posX) >> 4;
        final int z = MathHelper.floor_double(((Entity)this.theEntity).posZ) >> 4;
        final PlayerManager playermanager = ((WorldServer)((Entity)this.theEntity).worldObj).getPlayerManager();
        final List players = ((Entity)this.theEntity).worldObj.playerEntities;
        for (final Object obj : players) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
                this.sendData(entityplayer);
            }
        }
    }
    
    public void receiveData(final LOTRPacketFamilyInfo packet) {
        this.setAge(packet.age);
        this.setMale(packet.isMale);
        this.setName(packet.name);
        if (packet.isDrunk) {
            this.setDrunkTime(100000);
        }
        else {
            this.setDrunkTime(0);
        }
    }
    
    public void onUpdate() {
        if (!((Entity)this.theEntity).worldObj.isClient) {
            if (!this.doneFirstUpdate) {
                this.doneFirstUpdate = true;
            }
            if (this.resendData) {
                this.sendDataToAllWatchers();
                this.resendData = false;
            }
            if (this.getAge() < 0) {
                this.setAge(this.getAge() + 1);
            }
            else if (this.getAge() > 0) {
                this.setAge(this.getAge() - 1);
            }
            if (this.drunkTime > 0) {
                this.setDrunkTime(this.drunkTime - 1);
            }
            if (this.isDrunk()) {
                this.theEntity.addPotionEffect(new PotionEffect(Potion.confusion.id, 20));
                if (this.timeUntilDrunkSpeech > 0) {
                    --this.timeUntilDrunkSpeech;
                }
                if (this.theEntity.isEntityAlive() && this.theEntity.getAttackTarget() == null && this.timeUntilDrunkSpeech == 0) {
                    final double range = 12.0;
                    final List players = ((Entity)this.theEntity).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this.theEntity).boundingBox.expand(range, range, range));
                    for (final Object obj : players) {
                        final EntityPlayer entityplayer = (EntityPlayer)obj;
                        if (entityplayer.isEntityAlive() && !entityplayer.capabilities.isCreativeMode) {
                            final String speechBank = this.theEntity.getSpeechBank(entityplayer);
                            if (speechBank == null || this.theEntity.getRNG().nextInt(3) != 0) {
                                continue;
                            }
                            this.theEntity.sendSpeechBank(entityplayer, speechBank);
                        }
                    }
                    this.timeUntilDrunkSpeech = 20 * MathHelper.getRandomIntegerInRange(this.theEntity.getRNG(), 5, 20);
                }
            }
        }
    }
    
    public boolean canMarryNPC(final LOTREntityNPC npc) {
        if (npc.getClass() != this.theEntity.getClass() || npc.familyInfo.spouseUniqueID != null || npc.familyInfo.getAge() != 0 || npc.getEquipmentInSlot(4) != null) {
            return false;
        }
        if (npc == this.theEntity || npc.familyInfo.isMale() == this.isMale() || (this.maleParentID != null && this.maleParentID == npc.familyInfo.maleParentID) || (this.femaleParentID != null && this.femaleParentID == npc.familyInfo.femaleParentID)) {
            return false;
        }
        final ItemStack heldItem = npc.getEquipmentInSlot(0);
        return heldItem != null && heldItem.getItem() == this.marriageRing;
    }
    
    public LOTREntityNPC getSpouse() {
        if (this.spouseUniqueID == null) {
            return null;
        }
        final List list = ((Entity)this.theEntity).worldObj.getEntitiesWithinAABB((Class)this.theEntity.getClass(), ((Entity)this.theEntity).boundingBox.expand(16.0, 8.0, 16.0));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof LOTREntityNPC && entity != this.theEntity && entity.getUniqueID().equals(this.spouseUniqueID)) {
                final LOTREntityNPC npc = (LOTREntityNPC)entity;
                if (npc.familyInfo.spouseUniqueID != null && this.theEntity.getUniqueID().equals(npc.familyInfo.spouseUniqueID)) {
                    return npc;
                }
            }
        }
        return null;
    }
    
    public LOTREntityNPC getParentToFollow() {
        final UUID parentToFollowID = this.isMale() ? this.maleParentID : this.femaleParentID;
        final List list = ((Entity)this.theEntity).worldObj.getEntitiesWithinAABB((Class)this.theEntity.getClass(), ((Entity)this.theEntity).boundingBox.expand(16.0, 8.0, 16.0));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof LOTREntityNPC && entity != this.theEntity && parentToFollowID != null && entity.getUniqueID().equals(parentToFollowID)) {
                return (LOTREntityNPC)entity;
            }
        }
        return null;
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        if (this.theEntity.hiredNPCInfo.isActive) {
            return false;
        }
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && itemstack.getItem() == this.marriageRing && LOTRLevelData.getData(entityplayer).getAlignment(this.theEntity.getFaction()) >= this.marriageAlignmentRequired && this.theEntity.getClass() == this.marriageEntityClass && this.getAge() == 0 && this.theEntity.getEquipmentInSlot(0) == null && this.theEntity.getEquipmentInSlot(4) == null && this.spouseUniqueID == null) {
            if (!entityplayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = itemstack;
                --itemStack.stackSize;
                if (itemstack.stackSize <= 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                }
            }
            if (!((Entity)this.theEntity).worldObj.isClient) {
                this.theEntity.setCurrentItemOrArmor(0, new ItemStack(this.marriageRing));
                this.ringGivingPlayer = entityplayer.getUniqueID();
            }
            return this.theEntity.isNPCPersistent = true;
        }
        return false;
    }
    
    public EntityPlayer getRingGivingPlayer() {
        if (this.ringGivingPlayer != null) {
            for (final Object obj : ((Entity)this.theEntity).worldObj.playerEntities) {
                final EntityPlayer entityplayer = (EntityPlayer)obj;
                if (entityplayer.getUniqueID().equals(this.ringGivingPlayer)) {
                    return entityplayer;
                }
            }
        }
        return null;
    }
    
    public void setChild() {
        this.setAge(-this.timeToMature);
    }
    
    public void setMaxBreedingDelay() {
        float f = (float)this.breedingDelay;
        f *= 0.5f + this.theEntity.getRNG().nextFloat() * 0.5f;
        this.setAge((int)f);
    }
    
    public int getRandomMaxChildren() {
        return 1 + this.theEntity.getRNG().nextInt(this.potentialMaxChildren);
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        nbt.setInteger("NPCAge", this.getAge());
        nbt.setBoolean("NPCMale", this.isMale());
        if (this.getName() != null) {
            nbt.setString("NPCName", this.getName());
        }
        nbt.setInteger("NPCDrunkTime", this.drunkTime);
        if (this.spouseUniqueID != null) {
            nbt.setLong("SpouseUUIDMost", this.spouseUniqueID.getMostSignificantBits());
            nbt.setLong("SpouseUUIDLeast", this.spouseUniqueID.getLeastSignificantBits());
        }
        nbt.setInteger("Children", this.children);
        nbt.setInteger("MaxChildren", this.maxChildren);
        if (this.maleParentID != null) {
            nbt.setLong("MaleParentUUIDMost", this.maleParentID.getMostSignificantBits());
            nbt.setLong("MaleParentUUIDLeast", this.maleParentID.getLeastSignificantBits());
        }
        if (this.femaleParentID != null) {
            nbt.setLong("FemaleParentUUIDMost", this.femaleParentID.getMostSignificantBits());
            nbt.setLong("FemaleParentUUIDLeast", this.femaleParentID.getLeastSignificantBits());
        }
        if (this.ringGivingPlayer != null) {
            nbt.setLong("RingGivingPlayerUUIDMost", this.ringGivingPlayer.getMostSignificantBits());
            nbt.setLong("RingGivingPlayerUUIDLeast", this.ringGivingPlayer.getLeastSignificantBits());
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        this.setAge(nbt.getInteger("NPCAge"));
        if (nbt.hasKey("NPCMale")) {
            this.setMale(nbt.getBoolean("NPCMale"));
        }
        if (nbt.hasKey("NPCName")) {
            this.setName(nbt.getString("NPCName"));
        }
        this.setDrunkTime(nbt.getInteger("NPCDrunkTime"));
        if (nbt.hasKey("SpouseUUIDMost") && nbt.hasKey("SpouseUUIDLeast")) {
            this.spouseUniqueID = new UUID(nbt.getLong("SpouseUUIDMost"), nbt.getLong("SpouseUUIDLeast"));
        }
        this.children = nbt.getInteger("Children");
        this.maxChildren = nbt.getInteger("MaxChildren");
        if (nbt.hasKey("MaleParentUUIDMost") && nbt.hasKey("MaleParentUUIDLeast")) {
            this.maleParentID = new UUID(nbt.getLong("MaleParentUUIDMost"), nbt.getLong("MaleParentUUIDLeast"));
        }
        if (nbt.hasKey("FemaleParentUUIDMost") && nbt.hasKey("FemaleParentUUIDLeast")) {
            this.femaleParentID = new UUID(nbt.getLong("FemaleParentUUIDMost"), nbt.getLong("FemaleParentUUIDLeast"));
        }
        if (nbt.hasKey("RingGivingPlayer")) {
            this.ringGivingPlayer = new UUID(nbt.getLong("RingGivingPlayerUUIDMost"), nbt.getLong("RingGivingPlayerUUIDLeast"));
        }
    }
}
