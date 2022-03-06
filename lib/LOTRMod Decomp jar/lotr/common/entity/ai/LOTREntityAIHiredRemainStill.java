// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIHiredRemainStill extends EntityAIBase
{
    private LOTREntityNPC theNPC;
    
    public LOTREntityAIHiredRemainStill(final LOTREntityNPC entity) {
        this.theNPC = entity;
        this.setMutexBits(6);
    }
    
    public boolean shouldExecute() {
        return this.theNPC.hiredNPCInfo.isActive && !this.theNPC.isInWater() && ((Entity)this.theNPC).onGround && this.theNPC.hiredNPCInfo.isHalted() && (this.theNPC.getAttackTarget() == null || !this.theNPC.getAttackTarget().isEntityAlive());
    }
    
    public void startExecuting() {
        this.theNPC.getNavigator().clearPathEntity();
    }
    
    public void updateTask() {
        this.theNPC.getNavigator().clearPathEntity();
        final Vec3 pos = Vec3.createVectorHelper(((Entity)this.theNPC).posX, ((Entity)this.theNPC).posY + this.theNPC.getEyeHeight(), ((Entity)this.theNPC).posZ);
        final Vec3 look = this.theNPC.getLookVec().normalize();
        final Vec3 lookUp = pos.addVector(look.xCoord * 3.0, pos.yCoord + 0.25, look.zCoord * 3.0);
        this.theNPC.getLookHelper().setLookPosition(lookUp.xCoord, lookUp.yCoord, lookUp.zCoord, 20.0f, 20.0f);
    }
}
