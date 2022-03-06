// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetHuorn;
import net.minecraft.world.World;

public class LOTREntityHuorn extends LOTREntityHuornBase
{
    public LOTREntityHuorn(final World world) {
        super(world);
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetHuorn.class);
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.FANGORN;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killHuorn;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
