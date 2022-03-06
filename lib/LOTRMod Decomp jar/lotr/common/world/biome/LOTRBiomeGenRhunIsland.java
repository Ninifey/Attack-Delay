// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenRhunIsland extends LOTRBiomeGenRhunLand
{
    public LOTRBiomeGenRhunIsland(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
        super.decorator.clearRandomStructures();
        super.decorator.clearVillages();
        this.clearTravellingTraders();
        super.invasionSpawns.clearInvasions();
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRhunIsland;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.TOL_RHUNAER;
    }
}
