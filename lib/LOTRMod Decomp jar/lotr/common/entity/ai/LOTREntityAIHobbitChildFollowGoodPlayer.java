// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIHobbitChildFollowGoodPlayer extends EntityAIBase
{
    private LOTREntityHobbit theHobbit;
    private EntityPlayer playerToFollow;
    private float range;
    private double speed;
    private int followDelay;
    
    public LOTREntityAIHobbitChildFollowGoodPlayer(final LOTREntityHobbit hobbit, final float f, final double d) {
        this.theHobbit = hobbit;
        this.range = f;
        this.speed = d;
    }
    
    public boolean shouldExecute() {
        if (this.theHobbit.familyInfo.getAge() >= 0) {
            return false;
        }
        final List list = ((Entity)this.theHobbit).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this.theHobbit).boundingBox.expand((double)this.range, 3.0, (double)this.range));
        EntityPlayer entityplayer = null;
        double distanceSq = Double.MAX_VALUE;
        for (final EntityPlayer playerCandidate : list) {
            if (LOTRLevelData.getData(playerCandidate).getAlignment(this.theHobbit.getFaction()) >= 200.0f) {
                final double d = this.theHobbit.getDistanceSqToEntity((Entity)playerCandidate);
                if (d > distanceSq) {
                    continue;
                }
                distanceSq = d;
                entityplayer = playerCandidate;
            }
        }
        if (entityplayer == null) {
            return false;
        }
        if (distanceSq < 9.0) {
            return false;
        }
        this.playerToFollow = entityplayer;
        return true;
    }
    
    public boolean continueExecuting() {
        if (!this.playerToFollow.isEntityAlive() || this.theHobbit.familyInfo.getAge() >= 0) {
            return false;
        }
        final double distanceSq = this.theHobbit.getDistanceSqToEntity((Entity)this.playerToFollow);
        return distanceSq >= 9.0 && distanceSq <= 256.0;
    }
    
    public void startExecuting() {
        this.followDelay = 0;
    }
    
    public void resetTask() {
        this.playerToFollow = null;
    }
    
    public void updateTask() {
        final int followDelay = this.followDelay - 1;
        this.followDelay = followDelay;
        if (followDelay <= 0) {
            this.followDelay = 10;
            this.theHobbit.getNavigator().tryMoveToEntityLiving((Entity)this.playerToFollow, this.speed);
        }
    }
}
