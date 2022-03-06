// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenNurnMarshes extends LOTRBiomeGenNurn
{
    public LOTRBiomeGenNurnMarshes(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        super.decorator.sandPerChunk = 0;
        super.decorator.quagmirePerChunk = 1;
        super.decorator.treesPerChunk = 0;
        super.decorator.willowPerChunk = 1;
        super.decorator.logsPerChunk = 2;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 10;
        super.decorator.enableFern = true;
        super.decorator.canePerChunk = 10;
        super.decorator.reedPerChunk = 4;
        super.decorator.clearRandomStructures();
    }
}
