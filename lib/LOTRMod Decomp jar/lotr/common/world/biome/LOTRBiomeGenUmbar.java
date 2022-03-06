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
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenUmbar;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenCorsairCamp;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenUmbar extends LOTRBiome
{
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseSand;
    
    public LOTRBiomeGenUmbar(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityCamel.class, 4, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 5, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBARIANS, 30).setSpawnChance(100);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 10).setSpawnChance(100);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 10).setSpawnChance(100);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 10);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 2);
        final int n6 = 2;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 2);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        final int n8 = 1;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.PELARGIR_SOLDIERS, 5).setConquestThreshold(100.0f);
        final int n9 = 2;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 5).setConquestThreshold(100.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = { null };
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        factionList4.add(lists4);
        super.npcSpawnList.conquestGainRate = 0.2f;
        super.variantChance = 0.3f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.1f);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 1000);
        super.decorator.addTree(LOTRTreeType.CEDAR, 300);
        super.decorator.addTree(LOTRTreeType.CYPRESS, 500);
        super.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.PALM, 100);
        super.decorator.addTree(LOTRTreeType.DATE_PALM, 5);
        super.decorator.addTree(LOTRTreeType.LEMON, 2);
        super.decorator.addTree(LOTRTreeType.ORANGE, 2);
        super.decorator.addTree(LOTRTreeType.LIME, 2);
        super.decorator.addTree(LOTRTreeType.OLIVE, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 5);
        super.decorator.addTree(LOTRTreeType.PLUM, 2);
        this.registerHaradFlowers();
        super.biomeColors.setGrass(11914805);
        super.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1500);
        super.decorator.addRandomStructure(new LOTRWorldGenCorsairCamp(false), 800);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.UMBAR(1, 3), 800);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 800);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NUMENOR(1, 3), 2000);
        super.decorator.addVillage(new LOTRVillageGenUmbar(this, 0.9f));
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterUmbar;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.UMBAR;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("umbar");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.UMBAR;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenUmbar.noiseDirt.func_151601_a(i * 0.002, k * 0.002);
        final double d2 = LOTRBiomeGenUmbar.noiseDirt.func_151601_a(i * 0.07, k * 0.07);
        final double d3 = LOTRBiomeGenUmbar.noiseDirt.func_151601_a(i * 0.25, k * 0.25);
        final double d4 = LOTRBiomeGenUmbar.noiseSand.func_151601_a(i * 0.002, k * 0.002);
        final double d5 = LOTRBiomeGenUmbar.noiseSand.func_151601_a(i * 0.07, k * 0.07);
        final double d6 = LOTRBiomeGenUmbar.noiseSand.func_151601_a(i * 0.25, k * 0.25);
        if (d4 + d5 + d6 > 1.1) {
            super.topBlock = (Block)Blocks.sand;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        else if (d1 + d2 + d3 > 0.6) {
            super.topBlock = Blocks.dirt;
            super.topBlockMeta = 1;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(final Random random) {
        final LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
        if (random.nextInt(5) == 0) {
            doubleFlowerGen.setFlowerType(3);
        }
        else {
            doubleFlowerGen.setFlowerType(2);
        }
        return doubleFlowerGen;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.15f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.05f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 2;
    }
    
    static {
        LOTRBiomeGenUmbar.noiseDirt = new NoiseGeneratorPerlin(new Random(7849067306796L), 1);
        LOTRBiomeGenUmbar.noiseSand = new NoiseGeneratorPerlin(new Random(628602597026L), 1);
    }
}
