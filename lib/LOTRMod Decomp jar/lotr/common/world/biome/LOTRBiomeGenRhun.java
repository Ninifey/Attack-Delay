// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityNearHaradMerchant;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenRhun extends LOTRBiome
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenRhun(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 20, 2, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 5, 1, 4));
        super.npcSpawnList.clear();
        super.variantChance = 0.3f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK_SPRUCE);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_SPRUCE, 3.0f);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2f);
        super.decorator.resetTreeCluster();
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 12;
        super.decorator.doubleGrassPerChunk = 8;
        super.decorator.addTree(LOTRTreeType.OAK, 100);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        super.decorator.addTree(LOTRTreeType.PINE, 500);
        super.decorator.addTree(LOTRTreeType.PINE_SHRUB, 4000);
        super.decorator.addTree(LOTRTreeType.CHESTNUT, 500);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.ASPEN, 100);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.MAPLE, 50);
        super.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 20);
        this.registerRhunPlainsFlowers();
        super.biomeColors.setGrass(12504664);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 1000);
        this.registerTravellingTrader(LOTREntityNearHaradMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.RHUN, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRhun;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RHUN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHUN.getSubregion("rhun");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(200) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(final Random random) {
        if (random.nextInt(4) == 0) {
            final LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
            doubleFlowerGen.setFlowerType(0);
            return doubleFlowerGen;
        }
        return super.getRandomWorldGenForDoubleFlower(random);
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.03f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
}
