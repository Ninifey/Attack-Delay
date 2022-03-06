// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIHiredHorseRemainStill extends EntityAIBase
{
    private LOTRNPCMount theHorse;
    private EntityCreature livingHorse;
    
    public LOTREntityAIHiredHorseRemainStill(final LOTRNPCMount entity) {
        this.theHorse = entity;
        this.livingHorse = (EntityCreature)this.theHorse;
        this.setMutexBits(5);
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
        return ridingNPC.hiredNPCInfo.isActive && !this.livingHorse.isInWater() && ((Entity)this.livingHorse).onGround && ridingNPC.hiredNPCInfo.isHalted() && (ridingNPC.getAttackTarget() == null || !ridingNPC.getAttackTarget().isEntityAlive());
    }
    
    public void startExecuting() {
        this.livingHorse.getNavigator().clearPathEntity();
    }
}
