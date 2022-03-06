// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIHorseFollowHiringPlayer extends EntityAIBase
{
    private LOTRNPCMount theHorse;
    private EntityCreature livingHorse;
    private EntityPlayer theHiringPlayer;
    private double moveSpeed;
    private int followTick;
    private float maxNearDist;
    private float minFollowDist;
    private boolean avoidsWater;
    private boolean initSpeed;
    
    public LOTREntityAIHorseFollowHiringPlayer(final LOTRNPCMount entity) {
        this.theHorse = entity;
        this.livingHorse = (EntityCreature)this.theHorse;
        this.minFollowDist = 8.0f;
        this.maxNearDist = 6.0f;
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
        final LOTREntityNPC ridingNPC = (LOTREntityNPC)rider;
        if (!ridingNPC.hiredNPCInfo.isActive) {
            return false;
        }
        final EntityPlayer entityplayer = ridingNPC.hiredNPCInfo.getHiringPlayer();
        if (entityplayer == null) {
            return false;
        }
        if (!ridingNPC.hiredNPCInfo.shouldFollowPlayer()) {
            return false;
        }
        if (this.livingHorse.getDistanceSqToEntity((Entity)entityplayer) < this.minFollowDist * this.minFollowDist) {
            return false;
        }
        this.theHiringPlayer = entityplayer;
        return true;
    }
    
    public boolean continueExecuting() {
        if (((Entity)this.livingHorse).riddenByEntity == null || !((Entity)this.livingHorse).riddenByEntity.isEntityAlive() || !(((Entity)this.livingHorse).riddenByEntity instanceof LOTREntityNPC)) {
            return false;
        }
        final LOTREntityNPC ridingNPC = (LOTREntityNPC)((Entity)this.livingHorse).riddenByEntity;
        return ridingNPC.hiredNPCInfo.isActive && ridingNPC.hiredNPCInfo.getHiringPlayer() != null && ridingNPC.hiredNPCInfo.shouldFollowPlayer() && !this.livingHorse.getNavigator().noPath() && this.livingHorse.getDistanceSqToEntity((Entity)this.theHiringPlayer) > this.maxNearDist * this.maxNearDist;
    }
    
    public void startExecuting() {
        this.followTick = 0;
        this.avoidsWater = this.livingHorse.getNavigator().getAvoidsWater();
        this.livingHorse.getNavigator().setAvoidsWater(false);
        if (!this.initSpeed) {
            final double d = this.livingHorse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            this.moveSpeed = 1.0 / d * 0.37;
            this.initSpeed = true;
        }
    }
    
    public void resetTask() {
        this.theHiringPlayer = null;
        this.livingHorse.getNavigator().clearPathEntity();
        this.livingHorse.getNavigator().setAvoidsWater(this.avoidsWater);
    }
    
    public void updateTask() {
        final LOTREntityNPC ridingNPC = (LOTREntityNPC)((Entity)this.livingHorse).riddenByEntity;
        this.livingHorse.getLookHelper().setLookPositionWithEntity((Entity)this.theHiringPlayer, 10.0f, (float)this.livingHorse.getVerticalFaceSpeed());
        ((Entity)ridingNPC).rotationYaw = ((Entity)this.livingHorse).rotationYaw;
        ((EntityLivingBase)ridingNPC).rotationYawHead = ((EntityLivingBase)this.livingHorse).rotationYawHead;
        if (ridingNPC.hiredNPCInfo.shouldFollowPlayer() && --this.followTick <= 0) {
            this.followTick = 10;
            if (!this.livingHorse.getNavigator().tryMoveToEntityLiving((Entity)this.theHiringPlayer, this.moveSpeed) && ridingNPC.hiredNPCInfo.teleportAutomatically) {
                double d = ridingNPC.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
                d = Math.max(d, 24.0);
                if (ridingNPC.getDistanceSqToEntity((Entity)this.theHiringPlayer) > d * d) {
                    ridingNPC.hiredNPCInfo.tryTeleportToHiringPlayer(false);
                }
            }
        }
    }
}
