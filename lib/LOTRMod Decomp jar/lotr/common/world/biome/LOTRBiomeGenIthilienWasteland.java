// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenIthilienWasteland extends LOTRBiomeGenIthilien
{
    public LOTRBiomeGenIthilienWasteland(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        this.clearBiomeVariants();
        super.variantChance = 0.7f;
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        super.decorator.logsPerChunk = 2;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
        super.decorator.addTree(LOTRTreeType.LEBETHRON_DEAD, 200);
        super.decorator.addTree(LOTRTreeType.BIRCH_DEAD, 50);
    }
}
