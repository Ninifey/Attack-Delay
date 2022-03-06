// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;

public class LOTRBiomeGenRohanWoodlands extends LOTRBiomeGenRohan
{
    public LOTRBiomeGenRohanWoodlands(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 8, 4, 8));
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 5;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 3;
        super.decorator.addTree(LOTRTreeType.BIRCH, 50);
        this.registerForestFlowers();
        super.decorator.clearVillages();
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.75f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
