// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.entity.npc.LOTREntityHaradPyramidWraith;
import lotr.common.entity.LOTREntities;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.block.Block;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.command.IEntitySelector;
import lotr.common.entity.LOTRMobSpawnerCondition;
import net.minecraft.entity.monster.EntityMob;

public abstract class LOTREntityScorpion extends EntityMob implements LOTRMobSpawnerCondition
{
    private float scorpionWidth;
    private float scorpionHeight;
    protected boolean spawningFromSpawner;
    private static IEntitySelector noWraiths;
    
    public LOTREntityScorpion(final World world) {
        super(world);
        this.scorpionWidth = -1.0f;
        this.spawningFromSpawner = false;
        this.setSize(1.2f, 0.9f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.2, false));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        ((EntityLiving)this).targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        ((EntityLiving)this).targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true));
        ((EntityLiving)this).targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)LOTREntityNPC.class, 0, true, false, LOTREntityScorpion.noWraiths));
    }
    
    public void setSpawningFromMobSpawner(final boolean flag) {
        this.spawningFromSpawner = flag;
    }
    
    protected int getRandomScorpionScale() {
        return ((Entity)this).rand.nextInt(3);
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(18, (Object)(byte)this.getRandomScorpionScale());
        ((Entity)this).dataWatcher.addObject(19, (Object)0);
    }
    
    public int getScorpionScale() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(18);
    }
    
    public void setScorpionScale(final int i) {
        ((Entity)this).dataWatcher.updateObject(18, (Object)(byte)i);
    }
    
    public float getScorpionScaleAmount() {
        return 0.5f + this.getScorpionScale() / 2.0f;
    }
    
    public int getStrikeTime() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(19);
    }
    
    public void setStrikeTime(final int i) {
        ((Entity)this).dataWatcher.updateObject(19, (Object)i);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0 + this.getScorpionScale() * 6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35 - this.getScorpionScale() * 0.05);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0 + this.getScorpionScale());
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("ScorpionScale", (byte)this.getScorpionScale());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setScorpionScale(nbt.getByte("ScorpionScale"));
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0 + this.getScorpionScale());
    }
    
    public float getBlockPathWeight(final int i, final int j, final int k) {
        if (this.spawningFromSpawner) {
            return 0.0f;
        }
        return super.getBlockPathWeight(i, j, k);
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.rescaleScorpion(this.getScorpionScaleAmount());
        if (!((Entity)this).worldObj.isClient) {
            final int i = this.getStrikeTime();
            if (i > 0) {
                this.setStrikeTime(i - 1);
            }
        }
    }
    
    protected void setSize(final float f, final float f1) {
        final boolean flag = this.scorpionWidth > 0.0f;
        this.scorpionWidth = f;
        this.scorpionHeight = f1;
        if (!flag) {
            this.rescaleScorpion(1.0f);
        }
    }
    
    private void rescaleScorpion(final float f) {
        super.setSize(this.scorpionWidth * f, this.scorpionHeight * f);
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && itemstack.getItem() == Items.glass_bottle) {
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
        return super.interact(entityplayer);
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (!((Entity)this).worldObj.isClient) {
                this.setStrikeTime(20);
            }
            if (entity instanceof EntityLivingBase) {
                final int difficulty = ((Entity)this).worldObj.difficultySetting.getDifficultyId();
                final int duration = difficulty * (difficulty + 5) / 2;
                if (duration > 0) {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
                }
            }
            return true;
        }
        return false;
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
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int k = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), j = 0; j < k; ++j) {
            this.func_145779_a(Items.rotten_flesh, 1);
        }
    }
    
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        final int i = this.getScorpionScale();
        return 2 + i + ((Entity)this).rand.nextInt(i + 2);
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public boolean isPotionApplicable(final PotionEffect effect) {
        return effect.getPotionID() != Potion.poison.id && super.isPotionApplicable(effect);
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
    
    static {
        LOTREntityScorpion.noWraiths = (IEntitySelector)new IEntitySelector() {
            public boolean isEntityApplicable(final Entity entity) {
                return !(entity instanceof LOTREntityHaradPyramidWraith);
            }
        };
    }
}
