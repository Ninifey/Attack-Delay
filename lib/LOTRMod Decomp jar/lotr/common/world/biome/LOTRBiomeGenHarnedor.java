// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenHarnedor;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenNearHaradDesertCamp;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenHarnedor extends LOTRBiome
{
    private static NoiseGeneratorPerlin noiseDirt;
    private static NoiseGeneratorPerlin noiseSand;
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenHarnedor(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 10, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 20).setSpawnChance(100);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 30).setSpawnChance(100);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 2);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[5];
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
        final int n6 = 1;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5);
        final int n7 = 2;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5).setConquestThreshold(50.0f);
        final int n8 = 3;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LOSSARNACH_SOLDIERS, 2).setConquestThreshold(50.0f);
        final int n9 = 4;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LEBENNIN_SOLDIERS, 2).setConquestThreshold(50.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = { null };
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n11 = 0;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists5[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n12 = 1;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists5[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 3).setConquestThreshold(50.0f);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n13 = 0;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists6[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
        final int n14 = 1;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists6[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0f);
        factionList6.add(lists6);
        final LOTRBiomeSpawnList.FactionContainer factionList7 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists7 = { null };
        final int n15 = 0;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists7[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        factionList7.add(lists7);
        final LOTRBiomeSpawnList.FactionContainer factionList8 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists8 = { null };
        final int n16 = 0;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists8[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList8.add(lists8);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND_SAND);
        this.addBiomeVariant(LOTRBiomeVariant.WASTELAND_SAND);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
        super.decorator.setTreeCluster(3, 30);
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.flowersPerChunk = 3;
        super.decorator.cactiPerChunk = 1;
        super.decorator.deadBushPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 1000);
        super.decorator.addTree(LOTRTreeType.CEDAR, 250);
        super.decorator.addTree(LOTRTreeType.LEMON, 5);
        super.decorator.addTree(LOTRTreeType.ORANGE, 5);
        super.decorator.addTree(LOTRTreeType.LIME, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 5);
        super.decorator.addTree(LOTRTreeType.ALMOND, 5);
        super.decorator.addTree(LOTRTreeType.PLUM, 5);
        this.registerHaradFlowers();
        super.biomeColors.setGrass(14538086);
        super.decorator.addRandomStructure(new LOTRWorldGenNearHaradDesertCamp(false), 3000);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 600);
        super.decorator.addRandomStructure(new LOTRWorldGenHarnedorTower(false), 800);
        super.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 6000);
        super.decorator.addVillage(new LOTRVillageGenHarnedor(this, 0.75f));
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
        super.invasionSpawns.addInvasion(LOTRInvasions.GONDOR, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.GONDOR_ITHILIEN, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_HARNEDOR, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_COAST, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterHarnedor;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.HARNEDOR;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("harnedor");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.HARAD_PATH;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenHarnedor.noiseDirt.func_151601_a(i * 0.09, k * 0.09);
        final double d2 = LOTRBiomeGenHarnedor.noiseDirt.func_151601_a(i * 0.4, k * 0.4);
        final double d3 = LOTRBiomeGenHarnedor.noiseSand.func_151601_a(i * 0.09, k * 0.09);
        final double d4 = LOTRBiomeGenHarnedor.noiseSand.func_151601_a(i * 0.4, k * 0.4);
        if (d3 + d4 > 0.6) {
            super.topBlock = (Block)Blocks.sand;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 > 0.2) {
            super.topBlock = Blocks.dirt;
            super.topBlockMeta = 1;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(12) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        if (random.nextBoolean()) {
            return new GrassBlockAndMeta(LOTRMod.aridGrass, 0);
        }
        return super.getRandomGrass(random);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
    
    static {
        LOTRBiomeGenHarnedor.noiseDirt = new NoiseGeneratorPerlin(new Random(3869098386927266L), 1);
        LOTRBiomeGenHarnedor.noiseSand = new NoiseGeneratorPerlin(new Random(92726978206783582L), 1);
    }
}
