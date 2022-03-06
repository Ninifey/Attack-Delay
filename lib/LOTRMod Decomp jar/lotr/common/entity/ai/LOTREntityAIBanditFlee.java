// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityBandit;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIBanditFlee extends EntityAIBase
{
    private LOTREntityBandit theBandit;
    private double speed;
    private double range;
    private EntityPlayer targetPlayer;
    
    public LOTREntityAIBanditFlee(final LOTREntityBandit bandit, final double d) {
        this.theBandit = bandit;
        this.speed = d;
        this.range = bandit.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (this.theBandit.getAttackTarget() != null) {
            return false;
        }
        if (this.theBandit.banditInventory.isEmpty()) {
            return false;
        }
        this.targetPlayer = this.findNearestPlayer();
        return this.targetPlayer != null;
    }
    
    private EntityPlayer findNearestPlayer() {
        final List players = ((Entity)this.theBandit).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this.theBandit).boundingBox.expand(this.range, this.range, this.range));
        double distance = this.range;
        EntityPlayer ret = null;
        for (int i = 0; i < players.size(); ++i) {
            final EntityPlayer entityplayer = players.get(i);
            if (!entityplayer.capabilities.isCreativeMode) {
                final double d = this.theBandit.getDistanceToEntity((Entity)entityplayer);
                if (d < distance) {
                    distance = d;
                    ret = entityplayer;
                }
            }
        }
        return ret;
    }
    
    public void updateTask() {
        if (this.theBandit.getNavigator().noPath()) {
            final Vec3 away = RandomPositionGenerator.findRandomTargetBlockAwayFrom((EntityCreature)this.theBandit, (int)this.range, 10, Vec3.createVectorHelper(((Entity)this.targetPlayer).posX, ((Entity)this.targetPlayer).posY, ((Entity)this.targetPlayer).posZ));
            if (away != null) {
                this.theBandit.getNavigator().tryMoveToXYZ(away.xCoord, away.yCoord, away.zCoord, this.speed);
            }
            this.targetPlayer = this.findNearestPlayer();
        }
    }
    
    public boolean continueExecuting() {
        return this.targetPlayer != null && this.targetPlayer.isEntityAlive() && !this.targetPlayer.capabilities.isCreativeMode && this.theBandit.getAttackTarget() == null && this.theBandit.getDistanceSqToEntity((Entity)this.targetPlayer) < this.range * this.range;
    }
    
    public void resetTask() {
        this.targetPlayer = null;
    }
}
