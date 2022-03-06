// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIFlee extends EntityAIBase
{
    private EntityCreature theEntity;
    private double speed;
    private double attackerX;
    private double attackerY;
    private double attackerZ;
    private int timer;
    private boolean firstPath;
    
    public LOTREntityAIFlee(final EntityCreature entity, final double d) {
        this.theEntity = entity;
        this.speed = d;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        final EntityLivingBase attacker = this.theEntity.getAITarget();
        if (attacker == null) {
            return false;
        }
        this.attackerX = ((Entity)attacker).posX;
        this.attackerY = ((Entity)attacker).posY;
        this.attackerZ = ((Entity)attacker).posZ;
        return true;
    }
    
    public void startExecuting() {
        this.timer = 60 + this.theEntity.getRNG().nextInt(50);
    }
    
    public boolean continueExecuting() {
        return this.timer > 0;
    }
    
    public void updateTask() {
        --this.timer;
        if (!this.firstPath || this.theEntity.getNavigator().noPath()) {
            final Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, Vec3.createVectorHelper(this.attackerX, this.attackerY, this.attackerZ));
            if (vec3 != null && this.theEntity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speed)) {
                this.theEntity.setRevengeTarget((EntityLivingBase)null);
                this.firstPath = true;
            }
        }
    }
    
    public void resetTask() {
        this.theEntity.getNavigator().clearPathEntity();
        this.timer = 0;
        this.firstPath = false;
    }
}
