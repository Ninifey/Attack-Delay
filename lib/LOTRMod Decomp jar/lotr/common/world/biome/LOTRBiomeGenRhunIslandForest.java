// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;

public class LOTRBiomeGenRhunIslandForest extends LOTRBiomeGenRhunRedForest
{
    public LOTRBiomeGenRhunIslandForest(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        super.decorator.treesPerChunk = 10;
        super.biomeColors.setFog(6132078);
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
