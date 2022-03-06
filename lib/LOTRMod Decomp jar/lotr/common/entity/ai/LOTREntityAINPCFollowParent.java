// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCFollowParent extends EntityAIBase
{
    private LOTREntityNPC theNPC;
    private LOTREntityNPC parentNPC;
    private double moveSpeed;
    private int followTick;
    
    public LOTREntityAINPCFollowParent(final LOTREntityNPC npc, final double d) {
        this.theNPC = npc;
        this.moveSpeed = d;
    }
    
    public boolean shouldExecute() {
        if (this.theNPC.familyInfo.getAge() >= 0) {
            return false;
        }
        final LOTREntityNPC parent = this.theNPC.familyInfo.getParentToFollow();
        if (parent == null) {
            return false;
        }
        if (this.theNPC.getDistanceSqToEntity((Entity)parent) < 9.0 || this.theNPC.getDistanceSqToEntity((Entity)parent) >= 256.0) {
            return false;
        }
        this.parentNPC = parent;
        return true;
    }
    
    public boolean continueExecuting() {
        if (!this.parentNPC.isEntityAlive()) {
            return false;
        }
        final double d = this.theNPC.getDistanceSqToEntity((Entity)this.parentNPC);
        return d >= 9.0 && d <= 256.0;
    }
    
    public void startExecuting() {
        this.followTick = 0;
    }
    
    public void resetTask() {
        this.parentNPC = null;
    }
    
    public void updateTask() {
        if (this.followTick-- <= 0) {
            this.followTick = 10;
            this.theNPC.getNavigator().tryMoveToEntityLiving((Entity)this.parentNPC, this.moveSpeed);
        }
    }
}
