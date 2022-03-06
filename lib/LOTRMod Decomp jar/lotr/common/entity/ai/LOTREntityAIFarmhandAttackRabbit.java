// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTREntityAIFarmhandAttackRabbit extends LOTREntityAINearestAttackableTargetBasic
{
    private LOTREntityNPC theNPC;
    
    public LOTREntityAIFarmhandAttackRabbit(final LOTREntityNPC npc) {
        super(npc, LOTREntityRabbit.class, 0, false);
        this.theNPC = npc;
    }
    
    @Override
    public boolean shouldExecute() {
        return (!this.theNPC.hiredNPCInfo.isActive || this.theNPC.hiredNPCInfo.isGuardMode()) && super.shouldExecute();
    }
    
    protected double getTargetDistance() {
        return 8.0;
    }
}
