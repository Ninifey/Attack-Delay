// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.entity.SharedMonsterAttributes;
import java.util.ArrayList;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityLionBase;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAILionChase extends EntityAIBase
{
    private LOTREntityLionBase theLion;
    private EntityCreature targetEntity;
    private double speed;
    private int chaseTimer;
    private int lionRePathDelay;
    private int targetRePathDelay;
    
    public LOTREntityAILionChase(final LOTREntityLionBase lion, final double d) {
        this.theLion = lion;
        this.speed = d;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        if (this.theLion.isChild() || this.theLion.isInLove()) {
            return false;
        }
        if (this.theLion.getRNG().nextInt(800) != 0) {
            return false;
        }
        final List entities = ((Entity)this.theLion).worldObj.getEntitiesWithinAABB((Class)EntityAnimal.class, ((Entity)this.theLion).boundingBox.expand(12.0, 12.0, 12.0));
        final List validTargets = new ArrayList();
        for (int i = 0; i < entities.size(); ++i) {
            final EntityAnimal entity = entities.get(i);
            if (entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) == null) {
                validTargets.add(entity);
            }
        }
        if (validTargets.isEmpty()) {
            return false;
        }
        this.targetEntity = (EntityCreature)validTargets.get(this.theLion.getRNG().nextInt(validTargets.size()));
        return true;
    }
    
    public void startExecuting() {
        this.chaseTimer = 300 + this.theLion.getRNG().nextInt(400);
    }
    
    public void updateTask() {
        --this.chaseTimer;
        this.theLion.getLookHelper().setLookPositionWithEntity((Entity)this.targetEntity, 30.0f, 30.0f);
        --this.lionRePathDelay;
        if (this.lionRePathDelay <= 0) {
            this.lionRePathDelay = 10;
            this.theLion.getNavigator().tryMoveToEntityLiving((Entity)this.targetEntity, this.speed);
        }
        if (this.targetEntity.getNavigator().noPath()) {
            final Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.targetEntity, 16, 7, Vec3.createVectorHelper(((Entity)this.theLion).posX, ((Entity)this.theLion).posY, ((Entity)this.theLion).posZ));
            if (vec3 != null) {
                this.targetEntity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 2.0);
            }
        }
    }
    
    public boolean continueExecuting() {
        return this.targetEntity != null && this.targetEntity.isEntityAlive() && this.chaseTimer > 0 && this.theLion.getDistanceSqToEntity((Entity)this.targetEntity) < 256.0;
    }
    
    public void resetTask() {
        this.chaseTimer = 0;
        this.lionRePathDelay = 0;
        this.targetRePathDelay = 0;
    }
}
