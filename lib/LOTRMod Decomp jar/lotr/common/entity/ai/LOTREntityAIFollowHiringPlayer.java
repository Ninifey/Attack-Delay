// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import java.util.List;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import java.util.ArrayList;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.npc.LOTRBannerBearer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIFollowHiringPlayer extends EntityAIBase
{
    private LOTREntityNPC theNPC;
    private final boolean isBannerBearer;
    private EntityPlayer theHiringPlayer;
    private double moveSpeed;
    private int followTick;
    private float maxNearDist;
    private float minFollowDist;
    private boolean avoidsWater;
    private EntityLiving bannerBearerTarget;
    
    public LOTREntityAIFollowHiringPlayer(final LOTREntityNPC entity) {
        this.theNPC = entity;
        this.isBannerBearer = (entity instanceof LOTRBannerBearer);
        final double entityMoveSpeed = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        this.moveSpeed = 1.0 / entityMoveSpeed * 0.37;
        this.minFollowDist = 8.0f;
        this.maxNearDist = 6.0f;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (!this.theNPC.hiredNPCInfo.isActive) {
            return false;
        }
        final EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
        if (entityplayer == null) {
            return false;
        }
        this.theHiringPlayer = entityplayer;
        if (!this.theNPC.hiredNPCInfo.shouldFollowPlayer()) {
            return false;
        }
        if (this.isBannerBearer) {
            final List alliesToFollow = new ArrayList();
            final List nearbyEntities = ((Entity)this.theNPC).worldObj.getEntitiesWithinAABB((Class)EntityLiving.class, ((Entity)this.theNPC).boundingBox.expand(16.0, 16.0, 16.0));
            for (int i = 0; i < nearbyEntities.size(); ++i) {
                final EntityLiving entity = nearbyEntities.get(i);
                if (entity != this.theNPC && LOTRMod.getNPCFaction((Entity)entity) == this.theNPC.getFaction()) {
                    if (entity instanceof LOTREntityNPC) {
                        final LOTREntityNPC npc = (LOTREntityNPC)entity;
                        if (!npc.hiredNPCInfo.isActive) {
                            continue;
                        }
                        if (npc.hiredNPCInfo.getHiringPlayer() != entityplayer) {
                            continue;
                        }
                    }
                    alliesToFollow.add(entity);
                }
            }
            EntityLiving entityToFollow = null;
            double d = Double.MAX_VALUE;
            for (int j = 0; j < alliesToFollow.size(); ++j) {
                final EntityLiving entity2 = alliesToFollow.get(j);
                final double dist = this.theNPC.getDistanceSqToEntity((Entity)entity2);
                if (dist < d && dist > this.minFollowDist * this.minFollowDist) {
                    d = dist;
                    entityToFollow = entity2;
                }
            }
            if (entityToFollow != null) {
                this.bannerBearerTarget = entityToFollow;
                return true;
            }
        }
        return this.theNPC.getDistanceSqToEntity((Entity)entityplayer) >= this.minFollowDist * this.minFollowDist;
    }
    
    public boolean continueExecuting() {
        if (this.theNPC.hiredNPCInfo.isActive && this.theNPC.hiredNPCInfo.getHiringPlayer() != null && this.theNPC.hiredNPCInfo.shouldFollowPlayer() && !this.theNPC.getNavigator().noPath()) {
            final EntityLivingBase target = (EntityLivingBase)((this.bannerBearerTarget != null) ? this.bannerBearerTarget : this.theHiringPlayer);
            return this.theNPC.getDistanceSqToEntity((Entity)target) > this.maxNearDist * this.maxNearDist;
        }
        return false;
    }
    
    public void startExecuting() {
        this.followTick = 0;
        this.avoidsWater = this.theNPC.getNavigator().getAvoidsWater();
        this.theNPC.getNavigator().setAvoidsWater(false);
    }
    
    public void resetTask() {
        this.theHiringPlayer = null;
        this.bannerBearerTarget = null;
        this.theNPC.getNavigator().clearPathEntity();
        this.theNPC.getNavigator().setAvoidsWater(this.avoidsWater);
    }
    
    public void updateTask() {
        final EntityLivingBase target = (EntityLivingBase)((this.bannerBearerTarget != null) ? this.bannerBearerTarget : this.theHiringPlayer);
        this.theNPC.getLookHelper().setLookPositionWithEntity((Entity)target, 10.0f, (float)this.theNPC.getVerticalFaceSpeed());
        if (this.theNPC.hiredNPCInfo.shouldFollowPlayer() && --this.followTick <= 0) {
            this.followTick = 10;
            if (!this.theNPC.getNavigator().tryMoveToEntityLiving((Entity)target, this.moveSpeed) && this.theNPC.hiredNPCInfo.teleportAutomatically) {
                double d = this.theNPC.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
                d = Math.max(d, 24.0);
                if (this.theNPC.getDistanceSqToEntity((Entity)this.theHiringPlayer) > d * d) {
                    this.theNPC.hiredNPCInfo.tryTeleportToHiringPlayer(false);
                }
            }
        }
    }
}
