// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenGulfHaradForest extends LOTRBiomeGenGulfHarad
{
    public LOTRBiomeGenGulfHaradForest(final int i, final boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 5;
        super.decorator.addTree(LOTRTreeType.DRAGONBLOOD, 1000);
        super.decorator.addTree(LOTRTreeType.DRAGONBLOOD_LARGE, 400);
        super.decorator.clearRandomStructures();
        super.decorator.clearVillages();
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 1.0f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
