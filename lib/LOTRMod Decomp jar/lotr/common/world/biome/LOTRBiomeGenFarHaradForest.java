// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenFarHaradForest extends LOTRBiomeGenFarHaradSavannah
{
    public LOTRBiomeGenFarHaradForest(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.variantChance = 0.4f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 7;
        super.decorator.vinesPerChunk = 10;
        super.decorator.logsPerChunk = 3;
        super.decorator.grassPerChunk = 8;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 3;
        super.decorator.melonPerChunk = 0.08f;
        super.biomeColors.setGrass(11659848);
        super.biomeColors.setFoliage(8376636);
    }
}
