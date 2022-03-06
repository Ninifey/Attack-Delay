// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRAchievement;
import net.minecraft.init.Items;
import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntitySnowball;
import lotr.common.entity.projectile.LOTREntityTrollSnowball;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntitySnowTroll extends LOTREntityTroll
{
    private EntityAIBase rangedAttackAI;
    private EntityAIBase meleeAttackAI;
    
    public LOTREntitySnowTroll(final World world) {
        super(world);
        this.rangedAttackAI = this.getTrollRangedAttackAI();
        super.isImmuneToFrost = true;
    }
    
    @Override
    public float getTrollScale() {
        return 0.8f;
    }
    
    @Override
    public EntityAIBase getTrollAttackAI() {
        return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }
    
    protected EntityAIBase getTrollRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.2, 20, 30, 24.0f);
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(21, (Object)0);
    }
    
    public boolean isThrowingSnow() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(21) == 1;
    }
    
    public void setThrowingSnow(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(21, (Object)(byte)(flag ? 1 : 0));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
    }
    
    @Override
    protected boolean hasTrollName() {
        return false;
    }
    
    @Override
    protected boolean canTrollBeTickled(final EntityPlayer entityplayer) {
        return false;
    }
    
    @Override
    public double getMeleeRange() {
        return 12.0;
    }
    
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            this.setThrowingSnow(false);
        }
        if (mode == AttackMode.MELEE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(3, this.meleeAttackAI);
            this.setThrowingSnow(false);
        }
        if (mode == AttackMode.RANGED) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(3, this.rangedAttackAI);
            this.setThrowingSnow(true);
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                final int difficulty = ((Entity)this).worldObj.difficultySetting.getDifficultyId();
                final int duration = difficulty * (difficulty + 5) / 2;
                if (duration > 0) {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        final EntityArrow template = new EntityArrow(((Entity)this).worldObj, (EntityLivingBase)this, target, f * 1.6f, 0.5f);
        final EntitySnowball snowball = new LOTREntityTrollSnowball(((Entity)this).worldObj, (EntityLivingBase)this);
        snowball.setLocationAndAngles(((Entity)template).posX, ((Entity)template).posY, ((Entity)template).posZ, ((Entity)template).rotationYaw, ((Entity)template).rotationPitch);
        ((Entity)snowball).motionX = ((Entity)template).motionX;
        ((Entity)snowball).motionY = ((Entity)template).motionY;
        ((Entity)snowball).motionZ = ((Entity)template).motionZ;
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)snowball);
        this.playSound("random.bow", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
        this.swingItem();
    }
    
    @Override
    public void onTrollDeathBySun() {
        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:troll.transform", this.getSoundVolume(), this.getSoundPitch());
        ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
        this.setDead();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            super.handleHealthUpdate(b);
            for (int l = 0; l < 64; ++l) {
                ((Entity)this).worldObj.spawnParticle("snowballpoof", ((Entity)this).posX + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int furs = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < furs; ++l) {
            this.func_145779_a(LOTRMod.fur, 1);
        }
        for (int snows = 2 + ((Entity)this).rand.nextInt(4) + ((Entity)this).rand.nextInt(i * 2 + 1), j = 0; j < snows; ++j) {
            this.func_145779_a(Items.snowball, 1);
        }
    }
    
    @Override
    public void dropTrollItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextBoolean()) {
            super.dropTrollItems(flag, i);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killSnowTroll;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 3.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return null;
    }
}
