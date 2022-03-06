// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCAvoidEvilPlayer extends EntityAIBase
{
    private LOTREntityNPC theNPC;
    private double farSpeed;
    private double nearSpeed;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private PathEntity entityPathEntity;
    private PathNavigate entityPathNavigate;
    
    public LOTREntityAINPCAvoidEvilPlayer(final LOTREntityNPC npc, final float f, final double d, final double d1) {
        this.theNPC = npc;
        this.distanceFromEntity = f;
        this.farSpeed = d;
        this.nearSpeed = d1;
        this.entityPathNavigate = npc.getNavigator();
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        final List validPlayers = new ArrayList();
        final List list = ((Entity)this.theNPC).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this.theNPC).boundingBox.expand((double)this.distanceFromEntity, this.distanceFromEntity / 2.0, (double)this.distanceFromEntity));
        if (list.isEmpty()) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            final EntityPlayer entityplayer = list.get(i);
            if (!entityplayer.capabilities.isCreativeMode) {
                final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.theNPC.getFaction());
                if ((this.theNPC.familyInfo.getAge() < 0 && alignment < 0.0f) || (this.theNPC instanceof LOTREntityHobbit && alignment <= -100.0f)) {
                    validPlayers.add(entityplayer);
                }
            }
        }
        if (validPlayers.isEmpty()) {
            return false;
        }
        this.closestLivingEntity = validPlayers.get(0);
        final Vec3 fleePath = RandomPositionGenerator.findRandomTargetBlockAwayFrom((EntityCreature)this.theNPC, 16, 7, Vec3.createVectorHelper(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
        if (fleePath == null) {
            return false;
        }
        if (this.closestLivingEntity.getDistanceSq(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord) < this.closestLivingEntity.getDistanceSqToEntity((Entity)this.theNPC)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord);
        return this.entityPathEntity != null && this.entityPathEntity.isDestinationSame(fleePath);
    }
    
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath();
    }
    
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }
    
    public void resetTask() {
        this.closestLivingEntity = null;
    }
    
    public void updateTask() {
        if (this.theNPC.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theNPC.getNavigator().setSpeed(this.nearSpeed);
        }
        else {
            this.theNPC.getNavigator().setSpeed(this.farSpeed);
        }
    }
}
