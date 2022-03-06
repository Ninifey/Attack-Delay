// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenNearHaradOasis extends LOTRBiomeGenNearHaradRiverbank
{
    public LOTRBiomeGenNearHaradOasis(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.decorator.treesPerChunk = 3;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 4;
        super.decorator.flowersPerChunk = 5;
        super.decorator.doubleFlowersPerChunk = 2;
        super.decorator.addTree(LOTRTreeType.DATE_PALM, 2000);
        super.decorator.addTree(LOTRTreeType.OLIVE, 500);
        super.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 200);
        super.decorator.addTree(LOTRTreeType.OAK_SHRUB, 3000);
        super.decorator.clearRandomStructures();
        super.decorator.clearVillages();
    }
    
    @Override
    protected boolean hasMixedHaradSoils() {
        return false;
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterNearHaradOasis;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("oasis");
    }
}
