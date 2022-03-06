// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;

public class LOTRBiomeGenRhunForest extends LOTRBiomeGenRhun
{
    public LOTRBiomeGenRhunForest(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 16, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 20, 4, 6));
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 8;
        super.decorator.logsPerChunk = 1;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 2000);
        super.decorator.addTree(LOTRTreeType.OAK_PARTY, 100);
        this.registerRhunForestFlowers();
        super.biomeColors.resetGrass();
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
