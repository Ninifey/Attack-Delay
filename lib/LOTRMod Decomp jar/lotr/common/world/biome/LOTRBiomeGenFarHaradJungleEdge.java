// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenFarHaradJungleEdge extends LOTRBiomeGenFarHaradJungle
{
    public LOTRBiomeGenFarHaradJungleEdge(final int i, final boolean major) {
        super(i, major);
        super.obsidianGravelRarity = 200;
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        super.decorator.treesPerChunk = 4;
        super.decorator.vinesPerChunk = 10;
        super.decorator.melonPerChunk = 0.03f;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.JUNGLE, 200);
        super.decorator.addTree(LOTRTreeType.JUNGLE_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.MAHOGANY, 50);
        super.decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
        super.decorator.addTree(LOTRTreeType.MANGO, 5);
        super.biomeColors.resetSky();
        super.biomeColors.resetFog();
        super.invasionSpawns.clearInvasions();
        super.invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.TAUREDAIN.setRepair(0.5f);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("edge");
    }
    
    @Override
    public boolean hasJungleLakes() {
        return false;
    }
    
    @Override
    public boolean isMuddy() {
        return false;
    }
}
