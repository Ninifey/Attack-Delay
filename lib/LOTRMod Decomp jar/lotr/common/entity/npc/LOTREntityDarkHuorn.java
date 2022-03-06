// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.world.biome.LOTRBiomeGenOldForest;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetHuorn;
import net.minecraft.world.World;

public class LOTREntityDarkHuorn extends LOTREntityHuornBase
{
    public LOTREntityDarkHuorn(final World world) {
        super(world);
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetHuorn.class);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.setTreeType(0);
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DARK_HUORN;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDarkHuorn;
    }
    
    @Override
    protected boolean isTreeHomeBiome(final BiomeGenBase biome) {
        return biome instanceof LOTRBiomeGenOldForest;
    }
}
