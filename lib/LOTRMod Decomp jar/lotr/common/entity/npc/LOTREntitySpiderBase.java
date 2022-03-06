// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.block.Block;
import net.minecraft.util.DamageSource;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRMountFunctions;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.entity.Entity;
import lotr.common.LOTRLevelData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIUntamedPanic;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public abstract class LOTREntitySpiderBase extends LOTREntityNPCRideable
{
    public static int VENOM_NONE;
    public static int VENOM_SLOWNESS;
    public static int VENOM_POISON;
    private static final int CLIMB_TIME = 100;
    
    public LOTREntitySpiderBase(final World world) {
        super(world);
        this.setSize(1.4f, 0.8f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4f));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.2, false));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIUntamedPanic(this, 1.2));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
        super.spawnsInDarkness = true;
    }
    
    protected abstract int getRandomSpiderScale();
    
    protected abstract int getRandomSpiderType();
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
        ((Entity)this).dataWatcher.addObject(21, (Object)0);
        ((Entity)this).dataWatcher.addObject(22, (Object)(byte)this.getRandomSpiderScale());
        this.setSpiderType(this.getRandomSpiderType());
        ((Entity)this).dataWatcher.addObject(23, (Object)0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0 + this.getSpiderScale() * 6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35 - this.getSpiderScale() * 0.03);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(2.0 + this.getSpiderScale());
    }
    
    public boolean isSpiderClimbing() {
        return (((Entity)this).dataWatcher.getWatchableObjectByte(20) & 0x1) != 0x0;
    }
    
    public void setSpiderClimbing(final boolean flag) {
        byte b = ((Entity)this).dataWatcher.getWatchableObjectByte(20);
        if (flag) {
            b |= 0x1;
        }
        else {
            b &= 0xFFFFFFFE;
        }
        ((Entity)this).dataWatcher.updateObject(20, (Object)b);
    }
    
    public int getSpiderType() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(21);
    }
    
    public void setSpiderType(final int i) {
        ((Entity)this).dataWatcher.updateObject(21, (Object)(byte)i);
    }
    
    public int getSpiderScale() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(22);
    }
    
    public void setSpiderScale(final int i) {
        ((Entity)this).dataWatcher.updateObject(22, (Object)(byte)i);
    }
    
    public float getSpiderScaleAmount() {
        return 0.5f + this.getSpiderScale() / 2.0f;
    }
    
    public int getSpiderClimbTime() {
        return ((Entity)this).dataWatcher.getWatchableObjectShort(23);
    }
    
    public void setSpiderClimbTime(final int i) {
        ((Entity)this).dataWatcher.updateObject(23, (Object)(short)i);
    }
    
    public boolean shouldRenderClimbingMeter() {
        return !((Entity)this).onGround && this.getSpiderClimbTime() > 0;
    }
    
    public float getClimbFractionRemaining() {
        float f = this.getSpiderClimbTime() / 100.0f;
        f = Math.min(f, 1.0f);
        f = 1.0f - f;
        return f;
    }
    
    @Override
    public boolean isMountSaddled() {
        return this.isNPCTamed() && ((Entity)this).riddenByEntity instanceof EntityPlayer;
    }
    
    @Override
    public boolean getBelongsToNPC() {
        return false;
    }
    
    @Override
    public void setBelongsToNPC(final boolean flag) {
    }
    
    @Override
    public String getMountArmorTexture() {
        return null;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("SpiderType", (byte)this.getSpiderType());
        nbt.setByte("SpiderScale", (byte)this.getSpiderScale());
        nbt.setShort("SpiderRideTime", (short)this.getSpiderClimbTime());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSpiderType(nbt.getByte("SpiderType"));
        this.setSpiderScale(nbt.getByte("SpiderScale"));
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(2.0 + this.getSpiderScale());
        this.setSpiderClimbTime(nbt.getShort("SpiderRideTime"));
    }
    
    @Override
    protected float getNPCScale() {
        return this.getSpiderScaleAmount();
    }
    
    public float getRenderSizeModifier() {
        return this.getSpiderScaleAmount();
    }
    
    protected boolean canRideSpider() {
        return this.getSpiderScale() > 0;
    }
    
    @Override
    protected double getBaseMountedYOffset() {
        return ((Entity)this).height - 0.7;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient) {
            final Entity rider = ((Entity)this).riddenByEntity;
            if (rider instanceof EntityPlayer && !((Entity)this).onGround) {
                if (((Entity)this).isCollidedHorizontally) {
                    this.setSpiderClimbTime(this.getSpiderClimbTime() + 1);
                }
            }
            else {
                this.setSpiderClimbTime(0);
            }
            if (this.getSpiderClimbTime() >= 100) {
                this.setSpiderClimbing(false);
                if (((Entity)this).onGround) {
                    this.setSpiderClimbTime(0);
                }
            }
            else {
                this.setSpiderClimbing(((Entity)this).isCollidedHorizontally);
            }
        }
        if (!((Entity)this).worldObj.isClient && ((Entity)this).riddenByEntity instanceof EntityPlayer && LOTRLevelData.getData((EntityPlayer)((Entity)this).riddenByEntity).getAlignment(this.getFaction()) < 50.0f) {
            ((Entity)this).riddenByEntity.mountEntity((Entity)null);
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer entityplayer) {
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (this.getSpiderType() == LOTREntitySpiderBase.VENOM_POISON && itemstack != null && itemstack.getItem() == Items.glass_bottle) {
            final ItemStack itemStack = itemstack;
            --itemStack.stackSize;
            if (itemstack.stackSize <= 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(LOTRMod.bottlePoison));
            }
            else if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(LOTRMod.bottlePoison)) && !entityplayer.capabilities.isCreativeMode) {
                entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(LOTRMod.bottlePoison), false);
            }
            return true;
        }
        if (((Entity)this).worldObj.isClient || super.hiredNPCInfo.isActive) {
            return false;
        }
        if (LOTRMountFunctions.interact(this, entityplayer)) {
            return true;
        }
        if (this.canRideSpider() && this.getAttackTarget() != entityplayer) {
            final boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0f;
            boolean notifyNotEnoughAlignment = false;
            if (!notifyNotEnoughAlignment && itemstack != null && LOTRMod.isOreNameEqual(itemstack, "bone") && this.isNPCTamed() && this.getHealth() < this.getMaxHealth()) {
                if (hasRequiredAlignment) {
                    if (!entityplayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack2 = itemstack;
                        --itemStack2.stackSize;
                        if (itemstack.stackSize == 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                        }
                    }
                    this.heal(4.0f);
                    this.playSound(this.getLivingSound(), this.getSoundVolume(), this.getSoundPitch() * 1.5f);
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if (!notifyNotEnoughAlignment && ((Entity)this).riddenByEntity == null) {
                if (itemstack != null && itemstack.interactWithEntity(entityplayer, (EntityLivingBase)this)) {
                    return true;
                }
                if (hasRequiredAlignment) {
                    entityplayer.mountEntity((Entity)this);
                    this.setAttackTarget(null);
                    this.getNavigator().clearPathEntity();
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if (notifyNotEnoughAlignment) {
                LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 50.0f, this.getFaction());
                return true;
            }
        }
        return super.interact(entityplayer);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                final int difficulty = ((Entity)this).worldObj.difficultySetting.getDifficultyId();
                final int duration = difficulty * (difficulty + 5) / 2;
                if (duration > 0) {
                    if (this.getSpiderType() == LOTREntitySpiderBase.VENOM_SLOWNESS) {
                        ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
                    }
                    else if (this.getSpiderType() == LOTREntitySpiderBase.VENOM_POISON) {
                        ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        return damagesource != DamageSource.fall && super.attackEntityFrom(damagesource, f);
    }
    
    protected String getLivingSound() {
        return "mob.spider.say";
    }
    
    protected String getHurtSound() {
        return "mob.spider.say";
    }
    
    protected String getDeathSound() {
        return "mob.spider.death";
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
        this.playSound("mob.spider.step", 0.15f, 1.0f);
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int string = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), j = 0; j < string; ++j) {
            this.func_145779_a(Items.string, 1);
        }
        if (flag && (((Entity)this).rand.nextInt(3) == 0 || ((Entity)this).rand.nextInt(1 + i) > 0)) {
            this.func_145779_a(Items.spider_eye, 1);
        }
    }
    
    @Override
    public boolean canDropRares() {
        return false;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        final int i = this.getSpiderScale();
        return 2 + i + ((Entity)this).rand.nextInt(i + 2);
    }
    
    public boolean isOnLadder() {
        return this.isSpiderClimbing();
    }
    
    public void setInWeb() {
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public boolean isPotionApplicable(final PotionEffect effect) {
        return (this.getSpiderType() != LOTREntitySpiderBase.VENOM_SLOWNESS || effect.getPotionID() != Potion.moveSlowdown.id) && (this.getSpiderType() != LOTREntitySpiderBase.VENOM_POISON || effect.getPotionID() != Potion.poison.id) && super.isPotionApplicable(effect);
    }
    
    @Override
    public boolean allowLeashing() {
        return this.isNPCTamed();
    }
    
    @Override
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        return false;
    }
    
    static {
        LOTREntitySpiderBase.VENOM_NONE = 0;
        LOTREntitySpiderBase.VENOM_SLOWNESS = 1;
        LOTREntitySpiderBase.VENOM_POISON = 2;
    }
}
