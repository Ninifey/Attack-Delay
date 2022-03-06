// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityNearHaradMerchant;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenRhun;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityKineAraw;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenRhunLand extends LOTRBiome
{
    private WorldGenerator boulderGen;
    private WorldGenerator boulderGenSandstone;
    protected boolean rhunBoulders;
    
    public LOTRBiomeGenRhunLand(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        this.boulderGenSandstone = new LOTRWorldGenBoulder(Blocks.sandstone, 0, 1, 3);
        this.rhunBoulders = true;
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 5, 2, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityKineAraw.class, 6, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 4, 1, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[6];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 20).setSpawnChance(100);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 20).setSpawnChance(100);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 3).setSpawnChance(100);
        final int n4 = 3;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10).setConquestOnly();
        final int n5 = 4;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestOnly();
        final int n6 = 5;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 5).setConquestThreshold(50.0f);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(1, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WICKED_DWARVES, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DALE_SOLDIERS, 10);
        final int n9 = 1;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DALE_MEN, 5).setConquestThreshold(200.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = { null };
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n11 = 0;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists5[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        final int n12 = 1;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists5[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_MEN, 5).setConquestThreshold(200.0f);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n13 = 0;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists6[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n14 = 1;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists6[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 1);
        final int n15 = 2;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists6[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        factionList6.add(lists6);
        final LOTRBiomeSpawnList.FactionContainer factionList7 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists7 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n16 = 0;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists7[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_GUARDS, 10);
        final int n17 = 1;
        final LOTRBiomeSpawnList npcSpawnList17 = super.npcSpawnList;
        lists7[n17] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_MEN, 5).setConquestThreshold(200.0f);
        factionList7.add(lists7);
        super.npcSpawnList.conquestGainRate = 0.75f;
        super.variantChance = 0.3f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_DATE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.1f);
        super.decorator.setTreeCluster(8, 20);
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 6;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 4;
        super.decorator.addTree(LOTRTreeType.OAK, 100);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH_TALL, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.BEECH, 50);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.MAPLE, 50);
        super.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.CYPRESS, 400);
        super.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.OAK_SHRUB, 600);
        super.decorator.addTree(LOTRTreeType.APPLE, 2);
        super.decorator.addTree(LOTRTreeType.PEAR, 2);
        super.decorator.addTree(LOTRTreeType.ALMOND, 5);
        super.decorator.addTree(LOTRTreeType.PLUM, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE, 20);
        super.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.DATE_PALM, 25);
        super.decorator.addTree(LOTRTreeType.POMEGRANATE, 25);
        this.registerRhunPlainsFlowers();
        super.biomeColors.setGrass(14538041);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.RHUN(1, 4), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 600);
        super.decorator.addVillage(new LOTRVillageGenRhun(this, 0.75f, true));
        this.registerTravellingTrader(LOTREntityNearHaradMerchant.class);
        this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.DALE, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRhunLand;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RHUN_KHAGANATE;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHUN.getSubregion("rhudel");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.RHUN;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        final int chunkX = i & 0xF;
        final int chunkZ = k & 0xF;
        final int xzIndex = chunkX * 16 + chunkZ;
        final int ySize = blocks.length / 256;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.08, k * 0.08);
        final double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
        if (d1 + d2 > 0.1) {
            for (int minHeight = height - 8 - random.nextInt(6), j = height - 1; j >= minHeight; --j) {
                final int index = xzIndex * ySize + j;
                final Block block = blocks[index];
                if (block == Blocks.stone) {
                    blocks[index] = Blocks.sandstone;
                    meta[index] = 0;
                }
            }
        }
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (this.rhunBoulders && random.nextInt(50) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getHeightValue(i2, k2);
                if (random.nextInt(3) == 0) {
                    this.boulderGenSandstone.generate(world, random, i2, j1, k2);
                }
                else {
                    this.boulderGen.generate(world, random, i2, j1, k2);
                }
            }
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(final Random random) {
        if (random.nextInt(3) == 0) {
            final LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
            doubleFlowerGen.setFlowerType(0);
            return doubleFlowerGen;
        }
        return super.getRandomWorldGenForDoubleFlower(random);
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.1f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
