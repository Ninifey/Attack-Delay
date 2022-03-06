// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import java.util.UUID;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.MathHelper;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatComponentText;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import java.util.List;

public class LOTRTravellingTraderInfo
{
    private LOTREntityNPC theEntity;
    private LOTRTravellingTrader theTrader;
    public int timeUntilDespawn;
    private List escortUUIDs;
    
    public LOTRTravellingTraderInfo(final LOTRTravellingTrader entity) {
        this.timeUntilDespawn = -1;
        this.escortUUIDs = new ArrayList();
        this.theEntity = (LOTREntityNPC)entity;
        this.theTrader = entity;
    }
    
    public void startVisiting(final EntityPlayer entityplayer) {
        this.timeUntilDespawn = 24000;
        if (((Entity)this.theEntity).worldObj.playerEntities.size() <= 1) {
            final IChatComponent componentName = (IChatComponent)new ChatComponentText(this.theEntity.getCommandSenderName());
            componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            LOTRSpeech.messageAllPlayers((IChatComponent)new ChatComponentTranslation("lotr.travellingTrader.arrive", new Object[] { componentName }));
        }
        else {
            final IChatComponent componentName = (IChatComponent)new ChatComponentText(this.theEntity.getCommandSenderName());
            componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            LOTRSpeech.messageAllPlayersInWorld(((Entity)this.theEntity).worldObj, (IChatComponent)new ChatComponentTranslation("lotr.travellingTrader.arriveMP", new Object[] { componentName, entityplayer.getCommandSenderName() }));
        }
        final int i = MathHelper.floor_double(((Entity)this.theEntity).posX);
        final int j = MathHelper.floor_double(((Entity)this.theEntity).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this.theEntity).posZ);
        this.theEntity.setHomeArea(i, j, k, 16);
        for (int escorts = 2 + ((Entity)this.theEntity).worldObj.rand.nextInt(3), l = 0; l < escorts; ++l) {
            final LOTREntityNPC escort = this.theTrader.createTravellingEscort();
            if (escort != null) {
                escort.setLocationAndAngles(((Entity)this.theEntity).posX, ((Entity)this.theEntity).posY, ((Entity)this.theEntity).posZ, ((Entity)this.theEntity).rotationYaw, ((Entity)this.theEntity).rotationPitch);
                escort.isNPCPersistent = true;
                escort.spawnRidingHorse = false;
                escort.onSpawnWithEgg(null);
                ((Entity)this.theEntity).worldObj.spawnEntityInWorld((Entity)escort);
                escort.setHomeArea(i, j, k, 16);
                escort.isTraderEscort = true;
                this.escortUUIDs.add(escort.getUniqueID());
            }
        }
    }
    
    private void removeEscorts() {
        for (final Object obj : ((Entity)this.theEntity).worldObj.loadedEntityList) {
            final Entity entity = (Entity)obj;
            final UUID entityUUID = entity.getUniqueID();
            for (final Object uuid : this.escortUUIDs) {
                if (entityUUID.equals(uuid)) {
                    entity.setDead();
                }
            }
        }
    }
    
    public void onUpdate() {
        if (!((Entity)this.theEntity).worldObj.isClient) {
            if (this.timeUntilDespawn > 0) {
                --this.timeUntilDespawn;
            }
            if (this.timeUntilDespawn == 2400) {
                for (final Object player : ((Entity)this.theEntity).worldObj.playerEntities) {
                    LOTRSpeech.sendSpeechAndChatMessage((EntityPlayer)player, this.theEntity, this.theTrader.getDepartureSpeech());
                }
            }
            if (this.timeUntilDespawn == 0) {
                final IChatComponent componentName = (IChatComponent)new ChatComponentText(this.theEntity.getCommandSenderName());
                componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                LOTRSpeech.messageAllPlayersInWorld(((Entity)this.theEntity).worldObj, (IChatComponent)new ChatComponentTranslation("lotr.travellingTrader.depart", new Object[] { componentName }));
                this.theEntity.setDead();
                this.removeEscorts();
            }
        }
    }
    
    public void onDeath() {
        if (!((Entity)this.theEntity).worldObj.isClient && this.timeUntilDespawn >= 0) {
            LOTRSpeech.messageAllPlayers(this.theEntity.func_110142_aN().func_151521_b());
            this.removeEscorts();
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        nbt.setInteger("DespawnTime", this.timeUntilDespawn);
        final NBTTagList escortTags = new NBTTagList();
        for (final Object obj : this.escortUUIDs) {
            if (obj instanceof UUID) {
                final NBTTagCompound escortData = new NBTTagCompound();
                escortData.setLong("UUIDMost", ((UUID)obj).getMostSignificantBits());
                escortData.setLong("UUIDLeast", ((UUID)obj).getLeastSignificantBits());
                escortTags.appendTag((NBTBase)escortData);
            }
        }
        nbt.setTag("Escorts", (NBTBase)escortTags);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        this.timeUntilDespawn = nbt.getInteger("DespawnTime");
        this.escortUUIDs.clear();
        final NBTTagList tags = nbt.getTagList("Escorts", 10);
        if (tags != null) {
            for (int i = 0; i < tags.tagCount(); ++i) {
                final NBTTagCompound escortData = tags.getCompoundTagAt(i);
                this.escortUUIDs.add(new UUID(escortData.getLong("UUIDMost"), escortData.getLong("UUIDLeast")));
            }
        }
    }
}
