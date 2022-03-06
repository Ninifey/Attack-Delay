// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenTaiga extends LOTRBiomeGenTundra
{
    public LOTRBiomeGenTaiga(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        super.variantChance = 0.75f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_SPRUCE);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE);
        super.decorator.treesPerChunk = 2;
        super.decorator.flowersPerChunk = 2;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        super.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 100);
        super.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 50);
        super.decorator.addTree(LOTRTreeType.FIR, 200);
        super.decorator.addTree(LOTRTreeType.PINE, 200);
        this.registerTaigaFlowers();
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
