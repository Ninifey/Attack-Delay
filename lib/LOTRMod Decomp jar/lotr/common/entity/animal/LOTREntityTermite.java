// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.entity.LOTREntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.entity.monster.EntityMob;

public class LOTREntityTermite extends EntityMob
{
    private int fuseTime;
    private static int maxFuseTime;
    public static float explosionSize;
    
    public LOTREntityTermite(final World world) {
        super(world);
        this.setSize(0.4f, 0.4f);
        ((Entity)this).renderDistanceWeight = 2.0;
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.0, true));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        ((EntityLiving)this).targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true));
        ((EntityLiving)this).targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)LOTREntityNPC.class, 0, true));
        ((EntityLiving)this).experienceValue = 2;
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!((Entity)this).worldObj.isClient) {
            final EntityLivingBase target = this.getAttackTarget();
            if (target == null) {
                --this.fuseTime;
            }
            else {
                final float dist = this.getDistanceToEntity((Entity)target);
                if (dist < 3.0f) {
                    if (this.fuseTime == 0) {
                        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "creeper.primed", 1.0f, 0.5f);
                    }
                    ++this.fuseTime;
                    if (this.fuseTime >= 20) {
                        this.explode();
                    }
                }
                else {
                    --this.fuseTime;
                }
            }
            this.fuseTime = Math.min(Math.max(this.fuseTime, 0), LOTREntityTermite.maxFuseTime);
        }
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        return true;
    }
    
    private void explode() {
        if (!((Entity)this).worldObj.isClient) {
            ((Entity)this).worldObj.createExplosion((Entity)this, ((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, LOTREntityTermite.explosionSize, LOTRMod.canGrief(((Entity)this).worldObj));
            this.setDead();
        }
    }
    
    protected String getLivingSound() {
        return "mob.silverfish.say";
    }
    
    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }
    
    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }
    
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer) {
            this.func_145779_a(LOTRMod.termite, 1);
            this.setDead();
        }
    }
    
    protected boolean canDespawn() {
        return false;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
    
    static {
        LOTREntityTermite.maxFuseTime = 20;
        LOTREntityTermite.explosionSize = 2.0f;
    }
}
