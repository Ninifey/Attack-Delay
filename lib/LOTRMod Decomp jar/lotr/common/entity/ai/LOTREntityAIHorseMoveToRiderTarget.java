// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIHorseMoveToRiderTarget extends EntityAIBase
{
    private LOTRNPCMount theHorse;
    private EntityCreature livingHorse;
    private double speed;
    private PathEntity entityPathEntity;
    private int pathCheckTimer;
    
    public LOTREntityAIHorseMoveToRiderTarget(final LOTRNPCMount horse) {
        this.theHorse = horse;
        this.livingHorse = (EntityCreature)this.theHorse;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (!this.theHorse.getBelongsToNPC()) {
            return false;
        }
        final Entity rider = ((Entity)this.livingHorse).riddenByEntity;
        if (rider == null || !rider.isEntityAlive() || !(rider instanceof LOTREntityNPC)) {
            return false;
        }
        final EntityLivingBase riderTarget = ((LOTREntityNPC)rider).getAttackTarget();
        if (riderTarget == null || !riderTarget.isEntityAlive()) {
            return false;
        }
        this.entityPathEntity = this.livingHorse.getNavigator().getPathToEntityLiving((Entity)riderTarget);
        return this.entityPathEntity != null;
    }
    
    public boolean continueExecuting() {
        if (((Entity)this.livingHorse).riddenByEntity == null || !((Entity)this.livingHorse).riddenByEntity.isEntityAlive() || !(((Entity)this.livingHorse).riddenByEntity instanceof LOTREntityNPC)) {
            return false;
        }
        final LOTREntityNPC rider = (LOTREntityNPC)((Entity)this.livingHorse).riddenByEntity;
        final EntityLivingBase riderTarget = rider.getAttackTarget();
        return riderTarget != null && riderTarget.isEntityAlive() && !this.livingHorse.getNavigator().noPath();
    }
    
    public void startExecuting() {
        this.speed = ((LOTREntityNPC)((Entity)this.livingHorse).riddenByEntity).getEntityAttribute(LOTREntityNPC.horseAttackSpeed).getAttributeValue();
        this.livingHorse.getNavigator().setPath(this.entityPathEntity, this.speed);
        this.pathCheckTimer = 0;
    }
    
    public void resetTask() {
        this.livingHorse.getNavigator().clearPathEntity();
    }
    
    public void updateTask() {
        final LOTREntityNPC rider = (LOTREntityNPC)((Entity)this.livingHorse).riddenByEntity;
        final EntityLivingBase riderTarget = rider.getAttackTarget();
        final boolean aimingBow = rider.isAimingRanged() && this.livingHorse.getEntitySenses().canSee((Entity)riderTarget);
        if (!aimingBow) {
            this.livingHorse.getLookHelper().setLookPositionWithEntity((Entity)riderTarget, 30.0f, 30.0f);
            ((Entity)rider).rotationYaw = ((Entity)this.livingHorse).rotationYaw;
            ((EntityLivingBase)rider).rotationYawHead = ((EntityLivingBase)this.livingHorse).rotationYawHead;
        }
        if (--this.pathCheckTimer <= 0) {
            this.pathCheckTimer = 4 + this.livingHorse.getRNG().nextInt(7);
            this.livingHorse.getNavigator().tryMoveToEntityLiving((Entity)riderTarget, this.speed);
        }
        if (aimingBow) {
            if (rider.getDistanceSqToEntity((Entity)riderTarget) < 25.0) {
                final Vec3 vec = LOTREntityAIRangedAttack.findPositionAwayFrom((EntityLivingBase)rider, riderTarget, 8, 16);
                if (vec != null) {
                    this.livingHorse.getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord, this.speed);
                }
            }
            else {
                this.livingHorse.getNavigator().clearPathEntity();
            }
        }
    }
}
