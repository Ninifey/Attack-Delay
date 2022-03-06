// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import net.minecraft.init.Blocks;
import com.google.common.math.IntMath;
import net.minecraft.block.material.Material;
import lotr.common.world.map.LOTRRoads;
import net.minecraft.block.Block;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityNearHaradMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.structure2.LOTRWorldGenDorwinionBath;
import lotr.common.world.structure2.LOTRWorldGenDorwinionElfHouse;
import lotr.common.world.structure2.LOTRWorldGenDorwinionBrewery;
import lotr.common.world.structure2.LOTRWorldGenDorwinionHouse;
import lotr.common.world.structure2.LOTRWorldGenDorwinionCamp;
import lotr.common.world.structure2.LOTRWorldGenDorwinionGarden;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityKineAraw;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenDorwinion extends LOTRBiome
{
    private WorldGenerator boulderGen;
    private LOTRBiomeSpawnList vineyardSpawnList;
    
    public LOTRBiomeGenDorwinion(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 5, 1, 2);
        this.vineyardSpawnList = new LOTRBiomeSpawnList(this);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 5, 2, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityKineAraw.class, 6, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 2, 1, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_MEN, 30);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_GUARDS, 10);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELVES, 5);
        final int n4 = 3;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELF_WARRIORS, 2);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
        final int n6 = 1;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2).setConquestThreshold(50.0f);
        final int n7 = 2;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        final int n9 = 1;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_MEN, 5).setConquestThreshold(100.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n11 = 1;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists4[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
        final int n12 = 2;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists4[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        final int n13 = 3;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists4[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n14 = 0;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists5[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
        final int n15 = 1;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists5[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 1);
        final int n16 = 2;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists5[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0f);
        final int n17 = 3;
        final LOTRBiomeSpawnList npcSpawnList17 = super.npcSpawnList;
        lists5[n17] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 5).setConquestThreshold(200.0f);
        factionList5.add(lists5);
        super.npcSpawnList.conquestGainRate = 0.75f;
        final LOTRBiomeSpawnList.FactionContainer factionList6 = this.vineyardSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = { null };
        final int n18 = 0;
        final LOTRBiomeSpawnList npcSpawnList18 = super.npcSpawnList;
        lists6[n18] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_VINEYARDS, 10);
        factionList6.add(lists6);
        super.variantChance = 0.3f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.VINEYARD, 8.0f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2f);
        super.decorator.setTreeCluster(8, 20);
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 6;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.addTree(LOTRTreeType.OAK, 200);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH_TALL, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.BEECH, 20);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.CYPRESS, 500);
        super.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.OAK_SHRUB, 800);
        super.decorator.addTree(LOTRTreeType.APPLE, 5);
        super.decorator.addTree(LOTRTreeType.PEAR, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE, 20);
        super.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.ALMOND, 10);
        super.decorator.addTree(LOTRTreeType.PLUM, 10);
        this.registerRhunPlainsFlowers();
        super.biomeColors.setGrass(10538541);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DORWINION(1, 4), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenDorwinionGarden(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenDorwinionCamp(false), 400);
        super.decorator.addRandomStructure(new LOTRWorldGenDorwinionHouse(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenDorwinionBrewery(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenDorwinionElfHouse(false), 400);
        super.decorator.addRandomStructure(new LOTRWorldGenDorwinionBath(false), 600);
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.registerTravellingTrader(LOTREntityNearHaradMerchant.class);
        this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDaleMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.DOL_GULDUR, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRBiomeSpawnList getNPCSpawnList(final World world, final Random random, final int i, final int j, final int k, final LOTRBiomeVariant variant) {
        if (variant == LOTRBiomeVariant.VINEYARD) {
            return this.vineyardSpawnList;
        }
        return super.getNPCSpawnList(world, random, i, j, k, variant);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterDorwinion;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.DORWINION;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.DORWINION.getSubregion("dorwinion");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.DORWINION;
    }
    
    public boolean hasDomesticAnimals() {
        return true;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        final int chunkX = i & 0xF;
        final int chunkZ = k & 0xF;
        final int xzIndex = chunkX * 16 + chunkZ;
        final int ySize = blocks.length / 256;
        final boolean vineyard = variant == LOTRBiomeVariant.VINEYARD;
        if (vineyard && !LOTRRoads.isRoadAt(i, k)) {
            int j = 128;
            while (j >= 0) {
                final int index = xzIndex * ySize + j;
                final Block above = blocks[index + 1];
                final Block block = blocks[index];
                if (block != null && block.isOpaqueCube() && (above == null || above.getMaterial() == Material.air)) {
                    final int i2 = IntMath.mod(i, 6);
                    final int i3 = IntMath.mod(i, 24);
                    final int k2 = IntMath.mod(k, 32);
                    final int k3 = IntMath.mod(k, 64);
                    if ((i2 == 0 || i2 == 5) && k2 != 0) {
                        blocks[index] = Blocks.farmland;
                        meta[index] = 0;
                        int h = 2;
                        if (LOTRBiome.biomeTerrainNoise.func_151601_a((double)i, (double)k) > 0.0) {
                            ++h;
                        }
                        final double d = 0.01;
                        final boolean red = LOTRBiome.biomeTerrainNoise.func_151601_a(i * d, k * d) > 0.0;
                        final Block vineBlock = red ? LOTRMod.grapevineRed : LOTRMod.grapevineWhite;
                        for (int j2 = 1; j2 <= h; ++j2) {
                            blocks[index + j2] = vineBlock;
                            meta[index + j2] = 7;
                        }
                        break;
                    }
                    if (i2 < 2 || i2 > 3) {
                        blocks[index] = super.topBlock;
                        meta[index] = (byte)super.topBlockMeta;
                        break;
                    }
                    blocks[index] = LOTRMod.dirtPath;
                    meta[index] = 0;
                    if (i2 == i3 && ((i2 == 2 && k3 == 16) || (i2 == 3 && k3 == 48))) {
                        for (int h = 3, j3 = 1; j3 <= h; ++j3) {
                            if (j3 == h) {
                                blocks[index + j3] = Blocks.torch;
                                meta[index + j3] = 5;
                            }
                            else {
                                blocks[index + j3] = LOTRMod.fence2;
                                meta[index + j3] = 10;
                            }
                        }
                        break;
                    }
                    break;
                }
                else {
                    --j;
                }
            }
        }
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(50) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
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
