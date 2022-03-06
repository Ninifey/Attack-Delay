// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.init.Items;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemFood;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.StringUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import java.util.UUID;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIGollumFollowOwner;
import lotr.common.entity.ai.LOTREntityAIGollumFishing;
import lotr.common.entity.ai.LOTREntityAIGollumAvoidEntity;
import lotr.common.entity.ai.LOTREntityAIGollumPanic;
import lotr.common.entity.ai.LOTREntityAIGollumRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import lotr.common.inventory.LOTRInventoryNPC;

public class LOTREntityGollum extends LOTREntityNPC implements LOTRCharacter
{
    public static int INV_ROWS;
    private int eatingTick;
    public int prevFishTime;
    public boolean isFishing;
    public LOTRInventoryNPC inventory;
    public int prevFishRequired;
    public int fishRequired;
    
    public LOTREntityGollum(final World world) {
        super(world);
        this.prevFishTime = 400;
        this.inventory = new LOTRInventoryNPC("gollum", this, LOTREntityGollum.INV_ROWS * 9);
        this.prevFishRequired = 20;
        this.fishRequired = this.prevFishRequired;
        this.setSize(0.6f, 1.2f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIGollumRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIGollumPanic(this, 1.4));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIGollumAvoidEntity(this, LOTREntityOrc.class, 8.0f, 1.2, 1.4));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIGollumAvoidEntity(this, LOTREntityElf.class, 8.0f, 1.2, 1.4));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIGollumFishing(this, 1.5));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAIGollumFollowOwner(this, 1.2, 6.0f, 4.0f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.1f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)"");
        ((Entity)this).dataWatcher.addObject(18, (Object)0);
        ((Entity)this).dataWatcher.addObject(19, (Object)0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public String getGollumOwnerUUID() {
        return ((Entity)this).dataWatcher.getWatchableObjectString(17);
    }
    
    public void setGollumOwnerUUID(final String s) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)s);
    }
    
    public EntityPlayer getGollumOwner() {
        try {
            final UUID uuid = UUID.fromString(this.getGollumOwnerUUID());
            return (uuid == null) ? null : ((Entity)this).worldObj.func_152378_a(uuid);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public boolean isGollumFleeing() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(18) == 1;
    }
    
    public void setGollumFleeing(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(18, (Object)(byte)(flag ? 1 : 0));
    }
    
    public boolean isGollumSitting() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(19) == 1;
    }
    
    public void setGollumSitting(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(19, (Object)(byte)(flag ? 1 : 0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        this.inventory.writeToNBT(nbt);
        nbt.setString("GollumOwnerUUID", this.getGollumOwnerUUID());
        nbt.setBoolean("GollumSitting", this.isGollumSitting());
        nbt.setInteger("GollumFishTime", this.prevFishTime);
        nbt.setInteger("FishReq", this.fishRequired);
        nbt.setInteger("FishReqPrev", this.prevFishRequired);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.inventory.readFromNBT(nbt);
        if (nbt.hasKey("GollumOwnerUUID")) {
            this.setGollumOwnerUUID(nbt.getString("GollumOwnerUUID"));
        }
        this.setGollumSitting(nbt.getBoolean("GollumSitting"));
        this.prevFishTime = nbt.getInteger("GollumFishTime");
        if (nbt.hasKey("FishReq")) {
            this.fishRequired = nbt.getInteger("FishReq");
            this.prevFishRequired = nbt.getInteger("FishReqPrev");
        }
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && ((Entity)this).rand.nextInt(500) == 0) {
            this.heal(1.0f);
        }
        if (this.eatingTick > 0) {
            if (this.eatingTick % 4 == 0) {
                ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "random.eat", 0.5f + 0.5f * ((Entity)this).rand.nextInt(2), (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.2f + 1.0f);
            }
            --this.eatingTick;
        }
        if (this.prevFishTime > 0) {
            --this.prevFishTime;
        }
        if (this.isGollumSitting() && !((Entity)this).worldObj.isClient && ((Entity)this).onGround) {
            this.getJumpHelper().setJumping();
        }
        if (!((Entity)this).worldObj.isClient && this.getEquipmentInSlot(0) != null && this.getGollumOwner() != null) {
            final double d = this.getDistanceSqToEntity((Entity)this.getGollumOwner());
            if (d < 4.0) {
                this.getLookHelper().setLookPositionWithEntity((Entity)this.getGollumOwner(), 100.0f, 100.0f);
                this.getLookHelper().onUpdateLook();
                final EntityItem entityitem = new EntityItem(((Entity)this).worldObj, ((Entity)this).posX, ((Entity)this).posY + this.getEyeHeight(), ((Entity)this).posZ, this.getEquipmentInSlot(0));
                entityitem.delayBeforeCanPickup = 40;
                float f = 0.3f;
                ((Entity)entityitem).motionX = -MathHelper.sin(((EntityLivingBase)this).rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(((Entity)this).rotationPitch / 180.0f * 3.1415927f) * f;
                ((Entity)entityitem).motionZ = MathHelper.cos(((EntityLivingBase)this).rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(((Entity)this).rotationPitch / 180.0f * 3.1415927f) * f;
                ((Entity)entityitem).motionY = -MathHelper.sin(((Entity)this).rotationPitch / 180.0f * 3.1415927f) * f + 0.1f;
                f = 0.02f;
                final float f2 = ((Entity)this).rand.nextFloat() * 3.1415927f * 2.0f;
                f *= ((Entity)this).rand.nextFloat();
                final EntityItem entityItem = entityitem;
                ((Entity)entityItem).motionX += Math.cos(f2) * f;
                final EntityItem entityItem2 = entityitem;
                ((Entity)entityItem2).motionY += (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.1f;
                final EntityItem entityItem3 = entityitem;
                ((Entity)entityItem3).motionZ += Math.sin(f2) * f;
                ((Entity)this).worldObj.spawnEntityInWorld((Entity)entityitem);
                this.setCurrentItemOrArmor(0, (ItemStack)null);
            }
        }
        if (!((Entity)this).worldObj.isClient && StringUtils.isNullOrEmpty(this.getGollumOwnerUUID()) && ((Entity)this).rand.nextInt(40) == 0) {
            final List<EntityPlayer> nearbyPlayers = (List<EntityPlayer>)((Entity)this).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this).boundingBox.expand(80.0, 80.0, 80.0));
            for (final EntityPlayer entityplayer : nearbyPlayers) {
                final double d2 = this.getDistanceToEntity((Entity)entityplayer);
                int chance = (int)(d2 / 8.0);
                chance = Math.max(2, chance);
                if (((Entity)this).rand.nextInt(chance) == 0) {
                    ((Entity)this).worldObj.playSoundAtEntity((Entity)entityplayer, this.getLivingSound(), this.getSoundVolume(), this.getSoundPitch());
                }
            }
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer entityplayer) {
        if (!((Entity)this).worldObj.isClient) {
            if (this.getGollumOwner() != null && entityplayer == this.getGollumOwner()) {
                final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                if (itemstack != null && itemstack.getItem() instanceof ItemFood && this.canGollumEat(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!entityplayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = itemstack;
                        --itemStack.stackSize;
                        if (itemstack.stackSize <= 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                        }
                    }
                    this.heal((float)((ItemFood)itemstack.getItem()).func_150905_g(itemstack));
                    this.eatingTick = 20;
                    return true;
                }
                if (entityplayer.isSneaking()) {
                    entityplayer.openGui((Object)LOTRMod.instance, 10, ((Entity)this).worldObj, this.getEntityId(), 0, 0);
                    return true;
                }
                this.setGollumSitting(!this.isGollumSitting());
                if (this.isGollumSitting()) {
                    LOTRSpeech.sendSpeech(this.getGollumOwner(), this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/stay", this.getGollumOwner()));
                }
                else {
                    LOTRSpeech.sendSpeech(this.getGollumOwner(), this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/follow", this.getGollumOwner()));
                }
                return true;
            }
            else {
                final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                if (itemstack != null && itemstack.getItem() == Items.fish) {
                    final boolean tamed = false;
                    if (itemstack.stackSize >= this.fishRequired) {
                        if (!entityplayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack2 = itemstack;
                            itemStack2.stackSize -= this.fishRequired;
                            if (itemstack.stackSize <= 0) {
                                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                            }
                        }
                        this.fishRequired = 0;
                    }
                    else {
                        this.fishRequired -= itemstack.stackSize;
                        if (!entityplayer.capabilities.isCreativeMode) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                        }
                    }
                    this.eatingTick = 20;
                    if (this.fishRequired <= 0) {
                        this.setGollumOwnerUUID(entityplayer.getUniqueID().toString());
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tameGollum);
                        LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/tame", entityplayer));
                        LOTRSpeech.messageAllPlayers((IChatComponent)new ChatComponentTranslation("chat.lotr.tameGollum", new Object[] { entityplayer.getCommandSenderName(), this.getCommandSenderName() }));
                        this.spawnHearts();
                        this.fishRequired = Math.round(this.prevFishRequired * (1.5f + ((Entity)this).rand.nextFloat() * 0.25f));
                        this.prevFishRequired = this.fishRequired;
                    }
                    else {
                        LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/tameProgress", entityplayer));
                    }
                    return true;
                }
            }
        }
        return super.interact(entityplayer);
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isGollumFleeing()) {
            return "char/gollum/say";
        }
        return super.getSpeechBank(entityplayer);
    }
    
    private boolean canGollumEat(final ItemStack itemstack) {
        if (itemstack.getItem() == Items.fish || itemstack.getItem() == Items.cooked_fished) {
            return true;
        }
        final ItemFood food = (ItemFood)itemstack.getItem();
        return food.isWolfsFavoriteMeat();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, float f) {
        final EntityPlayer owner = this.getGollumOwner();
        if (owner != null && damagesource.getEntity() == owner) {
            f = 0.0f;
            if (!((Entity)this).worldObj.isClient) {
                LOTRSpeech.sendSpeech(owner, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/hurt", owner));
            }
        }
        if (super.attackEntityFrom(damagesource, f)) {
            this.setGollumSitting(false);
            return true;
        }
        return false;
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        if (!((Entity)this).worldObj.isClient && !StringUtils.isNullOrEmpty(this.getGollumOwnerUUID())) {
            LOTRSpeech.messageAllPlayers(this.func_110142_aN().func_151521_b());
        }
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient) {
            this.inventory.dropAllItems();
            LOTRLevelData.setGollumSpawned(false);
        }
    }
    
    @Override
    public boolean canDropRares() {
        return false;
    }
    
    public String getLivingSound() {
        return "lotr:gollum.say";
    }
    
    public String getHurtSound() {
        return "lotr:gollum.hurt";
    }
    
    public String getDeathSound() {
        return "lotr:gollum.death";
    }
    
    public String getSplashSound() {
        return super.getSplashSound();
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            for (int i = 0; i < 4; ++i) {
                final double d = ((Entity)this).rand.nextGaussian() * 0.02;
                final double d2 = ((Entity)this).rand.nextGaussian() * 0.02;
                final double d3 = ((Entity)this).rand.nextGaussian() * 0.02;
                ((Entity)this).worldObj.spawnParticle(((Entity)this).rand.nextBoolean() ? "bubble" : "splash", ((Entity)this).posX + ((Entity)this).rand.nextFloat() * ((Entity)this).width * 2.0f - ((Entity)this).width, ((Entity)this).posY + 0.5 + ((Entity)this).rand.nextFloat() * ((Entity)this).height, ((Entity)this).posZ + ((Entity)this).rand.nextFloat() * ((Entity)this).width * 2.0f - ((Entity)this).width, d, d2, d3);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        return false;
    }
    
    static {
        LOTREntityGollum.INV_ROWS = 3;
    }
}
