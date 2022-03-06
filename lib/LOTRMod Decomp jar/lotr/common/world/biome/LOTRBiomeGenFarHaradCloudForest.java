// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntityJungleScorpion;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityFlamingo;

public class LOTRBiomeGenFarHaradCloudForest extends LOTRBiomeGenFarHarad
{
    public LOTRBiomeGenFarHaradCloudForest(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityFlamingo.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBird.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 10, 4, 4));
        super.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityJungleScorpion.class, 30, 4, 4));
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 10;
        super.decorator.vinesPerChunk = 50;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 4;
        super.decorator.grassPerChunk = 15;
        super.decorator.doubleGrassPerChunk = 10;
        super.decorator.enableFern = true;
        super.decorator.melonPerChunk = 0.1f;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.JUNGLE_CLOUD, 4000);
        super.decorator.addTree(LOTRTreeType.JUNGLE, 500);
        super.decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
        super.decorator.addTree(LOTRTreeType.MANGO, 20);
        this.registerJungleFlowers();
        super.biomeColors.setGrass(2007124);
        super.biomeColors.setFoliage(428338);
        super.biomeColors.setSky(11452859);
        super.biomeColors.setFoggy(true);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("cloudForest");
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return super.getChanceToSpawnAnimals() * 0.5f;
    }
}
