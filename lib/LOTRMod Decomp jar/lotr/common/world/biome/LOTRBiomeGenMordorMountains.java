// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure.LOTRWorldGenMordorTower;

public class LOTRBiomeGenMordorMountains extends LOTRBiomeGenMordor
{
    public LOTRBiomeGenMordorMountains(final int i, final boolean major) {
        super(i, major);
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenMordorTower(false), 400);
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return null;
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return null;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("mountains");
    }
}
