// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenUmbarForest extends LOTRBiomeGenUmbar
{
    public LOTRBiomeGenUmbarForest(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 7;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 12;
        super.decorator.doubleGrassPerChunk = 4;
        this.registerForestFlowers();
        super.decorator.clearVillages();
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
