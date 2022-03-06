// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityDeer;

public class LOTRBiomeGenLindonWoodlands extends LOTRBiomeGenLindon
{
    public LOTRBiomeGenLindonWoodlands(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 30, 4, 6));
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 6;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 3;
        this.registerForestFlowers();
    }
}
