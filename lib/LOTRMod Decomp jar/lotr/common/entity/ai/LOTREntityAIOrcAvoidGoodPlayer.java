// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.Iterator;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLiving;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.Entity;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIOrcAvoidGoodPlayer extends EntityAIBase
{
    private LOTREntityOrc theOrc;
    private double speed;
    private EntityLivingBase closestEnemyPlayer;
    private float distanceFromEntity;
    private PathEntity entityPathEntity;
    private PathNavigate entityPathNavigate;
    
    public LOTREntityAIOrcAvoidGoodPlayer(final LOTREntityOrc orc, final float f, final double d) {
        this.theOrc = orc;
        this.distanceFromEntity = f;
        this.speed = d;
        this.entityPathNavigate = orc.getNavigator();
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        if (!this.theOrc.isWeakOrc || this.theOrc.hiredNPCInfo.isActive) {
            return false;
        }
        if (this.theOrc.getFaction() == LOTRFaction.MORDOR) {
            return false;
        }
        if (this.theOrc.currentRevengeTarget != null || this.anyNearbyOrcsAttacked()) {
            return false;
        }
        final List validPlayers = ((Entity)this.theOrc).worldObj.selectEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this.theOrc).boundingBox.expand((double)this.distanceFromEntity, this.distanceFromEntity / 2.0, (double)this.distanceFromEntity), (IEntitySelector)new IEntitySelector() {
            public boolean isEntityApplicable(final Entity entity) {
                final EntityPlayer entityplayer = (EntityPlayer)entity;
                if (entityplayer.capabilities.isCreativeMode || LOTREntityAIOrcAvoidGoodPlayer.this.theOrc.currentRevengeTarget == entityplayer) {
                    return false;
                }
                final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTREntityAIOrcAvoidGoodPlayer.this.theOrc.getFaction());
                return alignment <= -500.0f;
            }
        });
        if (validPlayers.isEmpty()) {
            return false;
        }
        this.closestEnemyPlayer = validPlayers.get(0);
        final Vec3 fleePath = RandomPositionGenerator.findRandomTargetBlockAwayFrom((EntityCreature)this.theOrc, 16, 7, Vec3.createVectorHelper(((Entity)this.closestEnemyPlayer).posX, ((Entity)this.closestEnemyPlayer).posY, ((Entity)this.closestEnemyPlayer).posZ));
        if (fleePath == null) {
            return false;
        }
        if (this.closestEnemyPlayer.getDistanceSq(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord) < this.closestEnemyPlayer.getDistanceSqToEntity((Entity)this.theOrc)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord);
        return this.entityPathEntity != null && this.entityPathEntity.isDestinationSame(fleePath);
    }
    
    private boolean anyNearbyOrcsAttacked() {
        final List nearbyAllies = ((Entity)this.theOrc).worldObj.selectEntitiesWithinAABB((Class)EntityLiving.class, ((Entity)this.theOrc).boundingBox.expand((double)this.distanceFromEntity, this.distanceFromEntity / 2.0, (double)this.distanceFromEntity), (IEntitySelector)new IEntitySelector() {
            public boolean isEntityApplicable(final Entity entity) {
                return entity != LOTREntityAIOrcAvoidGoodPlayer.this.theOrc && LOTRMod.getNPCFaction(entity).isGoodRelation(LOTREntityAIOrcAvoidGoodPlayer.this.theOrc.getFaction());
            }
        });
        for (final Object obj : nearbyAllies) {
            final EntityLiving ally = (EntityLiving)obj;
            if (ally instanceof LOTREntityOrc) {
                if (((LOTREntityOrc)ally).currentRevengeTarget instanceof EntityPlayer) {
                    return true;
                }
                continue;
            }
            else {
                if (ally.getAttackTarget() instanceof EntityPlayer) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath() && this.theOrc.getAITarget() != this.closestEnemyPlayer && !this.anyNearbyOrcsAttacked();
    }
    
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.speed);
    }
    
    public void resetTask() {
        this.closestEnemyPlayer = null;
    }
}
