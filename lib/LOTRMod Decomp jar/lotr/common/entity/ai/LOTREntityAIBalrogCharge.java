// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityBalrog;

public class LOTREntityAIBalrogCharge extends LOTREntityAIAttackOnCollide
{
    private LOTREntityBalrog theBalrog;
    private float chargeDist;
    private int frustrationTime;
    private boolean hitChargeTarget;
    private int chargingTick;
    
    public LOTREntityAIBalrogCharge(final LOTREntityBalrog balrog, final double speed, final float dist, final int fr) {
        super(balrog, speed, false);
        this.hitChargeTarget = false;
        this.theBalrog = balrog;
        this.chargeDist = dist;
        this.frustrationTime = fr;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theBalrog.isBalrogCharging()) {
            return false;
        }
        final boolean flag = super.shouldExecute();
        if (!flag) {
            return false;
        }
        if (this.theBalrog.chargeFrustration >= this.frustrationTime) {
            return true;
        }
        final double dist = this.theBalrog.getDistanceSqToEntity((Entity)super.attackTarget);
        return dist >= this.chargeDist * this.chargeDist;
    }
    
    @Override
    public boolean continueExecuting() {
        if (!this.theBalrog.isEntityAlive()) {
            return false;
        }
        super.attackTarget = this.theBalrog.getAttackTarget();
        return super.attackTarget != null && super.attackTarget.isEntityAlive() && this.chargingTick > 0 && !this.hitChargeTarget;
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.theBalrog.setBalrogCharging(true);
        this.hitChargeTarget = false;
        this.chargingTick = 200;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.theBalrog.setBalrogCharging(false);
        this.hitChargeTarget = false;
        this.chargingTick = 0;
    }
    
    @Override
    public void updateTask() {
        this.updateLookAndPathing();
        if (this.chargingTick > 0) {
            --this.chargingTick;
        }
        AxisAlignedBB targetBB = ((Entity)this.theBalrog).boundingBox.expand(0.5, 0.0, 0.5);
        final double motionExtent = 2.0;
        final float angleRad = (float)Math.atan2(((Entity)this.theBalrog).motionZ, ((Entity)this.theBalrog).motionX);
        targetBB = targetBB.getOffsetBoundingBox(MathHelper.cos(angleRad) * motionExtent, 0.0, MathHelper.sin(angleRad) * motionExtent);
        final List hitTargets = super.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this.theBalrog, targetBB);
        boolean hitAnyEntities = false;
        for (int i = 0; i < hitTargets.size(); ++i) {
            final Entity obj = hitTargets.get(i);
            if (obj instanceof EntityLivingBase) {
                final EntityLivingBase hitEntity = (EntityLivingBase)obj;
                if (hitEntity != ((Entity)this.theBalrog).riddenByEntity) {
                    final float attackStr = (float)this.theBalrog.getEntityAttribute(LOTREntityBalrog.balrogChargeDamage).getAttributeValue();
                    final boolean flag = hitEntity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.theBalrog), attackStr);
                    if (flag) {
                        final float knock = 2.5f;
                        final float knockY = 0.5f;
                        hitEntity.addVelocity((double)(-MathHelper.sin((float)Math.toRadians(((Entity)this.theBalrog).rotationYaw)) * knock), (double)knockY, (double)(MathHelper.cos((float)Math.toRadians(((Entity)this.theBalrog).rotationYaw)) * knock));
                        hitEntity.setFire(6);
                        hitAnyEntities = true;
                        if (hitEntity == super.attackTarget) {
                            this.hitChargeTarget = true;
                        }
                    }
                }
            }
        }
        if (hitAnyEntities) {}
    }
}
