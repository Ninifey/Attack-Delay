// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.Random;
import net.minecraft.util.Vec3;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIRangedAttack extends EntityAIBase
{
    private EntityLiving theOwner;
    private IRangedAttackMob theOwnerRanged;
    private EntityLivingBase attackTarget;
    private int rangedAttackTime;
    private double moveSpeed;
    private double moveSpeedFlee;
    private int repathDelay;
    private int attackTimeMin;
    private int attackTimeMax;
    private float attackRange;
    private float attackRangeSq;
    
    public LOTREntityAIRangedAttack(final IRangedAttackMob entity, final double speed, final int time, final float range) {
        this(entity, speed, time, time, range);
    }
    
    public LOTREntityAIRangedAttack(final IRangedAttackMob entity, final double speed, final int min, final int max, final float range) {
        this.moveSpeedFlee = 1.5;
        this.rangedAttackTime = -1;
        this.theOwnerRanged = entity;
        this.theOwner = (EntityLiving)entity;
        this.moveSpeed = speed;
        this.attackTimeMin = min;
        this.attackTimeMax = max;
        this.attackRange = range;
        this.attackRangeSq = range * range;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        final EntityLivingBase target = this.theOwner.getAttackTarget();
        if (target == null) {
            return false;
        }
        this.attackTarget = target;
        return true;
    }
    
    public boolean continueExecuting() {
        if (!this.theOwner.isEntityAlive()) {
            return false;
        }
        this.attackTarget = this.theOwner.getAttackTarget();
        return this.attackTarget != null && this.attackTarget.isEntityAlive();
    }
    
    public void resetTask() {
        this.attackTarget = null;
        this.repathDelay = 0;
        this.rangedAttackTime = -1;
    }
    
    public void updateTask() {
        final double distanceSq = this.theOwner.getDistanceSq(((Entity)this.attackTarget).posX, ((Entity)this.attackTarget).boundingBox.minY, ((Entity)this.attackTarget).posZ);
        final boolean canSee = this.theOwner.getEntitySenses().canSee((Entity)this.attackTarget);
        if (canSee) {
            ++this.repathDelay;
        }
        else {
            this.repathDelay = 0;
        }
        if (distanceSq <= this.attackRangeSq) {
            if (this.theOwner.getDistanceSqToEntity((Entity)this.attackTarget) < 25.0) {
                final Vec3 vec = findPositionAwayFrom((EntityLivingBase)this.theOwner, this.attackTarget, 8, 16);
                if (vec != null) {
                    this.theOwner.getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord, this.moveSpeedFlee);
                }
            }
            else if (this.repathDelay >= 20) {
                this.theOwner.getNavigator().clearPathEntity();
                this.repathDelay = 0;
            }
        }
        else {
            this.theOwner.getNavigator().tryMoveToEntityLiving((Entity)this.attackTarget, this.moveSpeed);
        }
        this.theOwner.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0f, 30.0f);
        --this.rangedAttackTime;
        if (this.rangedAttackTime == 0) {
            if (distanceSq > this.attackRangeSq || !canSee) {
                return;
            }
            float power;
            final float distanceRatio = power = MathHelper.sqrt_double(distanceSq) / this.attackRange;
            power = MathHelper.clamp_float(power, 0.1f, 1.0f);
            this.theOwnerRanged.attackEntityWithRangedAttack(this.attackTarget, power);
            this.rangedAttackTime = MathHelper.floor_float(distanceRatio * (this.attackTimeMax - this.attackTimeMin) + this.attackTimeMin);
        }
        else if (this.rangedAttackTime < 0) {
            final float distanceRatio = MathHelper.sqrt_double(distanceSq) / this.attackRange;
            this.rangedAttackTime = MathHelper.floor_float(distanceRatio * (this.attackTimeMax - this.attackTimeMin) + this.attackTimeMin);
        }
    }
    
    public static Vec3 findPositionAwayFrom(final EntityLivingBase entity, final EntityLivingBase target, final int min, final int max) {
        final Random random = entity.getRNG();
        for (int l = 0; l < 24; ++l) {
            final int i = MathHelper.floor_double(((Entity)entity).posX) - max + random.nextInt(max * 2 + 1);
            final int j = MathHelper.floor_double(((Entity)entity).boundingBox.minY) - 4 + random.nextInt(9);
            final int k = MathHelper.floor_double(((Entity)entity).posZ) - max + random.nextInt(max * 2 + 1);
            final double d = target.getDistanceSq((double)i, (double)j, (double)k);
            if (d > min * min && d < max * max) {
                return Vec3.createVectorHelper((double)i, (double)j, (double)k);
            }
        }
        return null;
    }
}
