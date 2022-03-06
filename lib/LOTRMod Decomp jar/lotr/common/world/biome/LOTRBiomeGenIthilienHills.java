// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenIthilienHills extends LOTRBiomeGenIthilien
{
    public LOTRBiomeGenIthilienHills(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.variantChance = 0.2f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        super.decorator.treesPerChunk = 0;
        super.decorator.logsPerChunk = 0;
        super.decorator.flowersPerChunk = 2;
        super.decorator.grassPerChunk = 8;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
}
