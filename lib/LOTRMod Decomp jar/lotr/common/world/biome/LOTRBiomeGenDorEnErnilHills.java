// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenDorEnErnilHills extends LOTRBiomeGenDorEnErnil
{
    public LOTRBiomeGenDorEnErnilHills(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.variantChance = 0.2f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        super.decorator.treesPerChunk = 1;
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 3;
    }
}
