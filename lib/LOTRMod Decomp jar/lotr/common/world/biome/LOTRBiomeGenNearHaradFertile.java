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
import lotr.common.world.village.LOTRVillageGenSouthron;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
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

public class LOTRBiomeGenNearHaradFertile extends LOTRBiome
{
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseSand;
    protected static NoiseGeneratorPerlin noiseRedSand;
    
    public LOTRBiomeGenNearHaradFertile(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityCamel.class, 6, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 15, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 20).setSpawnChance(100);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 15).setSpawnChance(100);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 2);
        final int n5 = 2;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 2);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5);
        final int n8 = 2;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5).setConquestThreshold(50.0f);
        final int n9 = 3;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5).setConquestThreshold(100.0f);
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
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = { null };
        final int n13 = 0;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists6[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        factionList6.add(lists6);
        final LOTRBiomeSpawnList.FactionContainer factionList7 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists7 = { null };
        final int n14 = 0;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists7[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList7.add(lists7);
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_DATE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND_SAND);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.deadBushPerChunk = 1;
        super.decorator.cactiPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.CEDAR, 800);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
        super.decorator.addTree(LOTRTreeType.DATE_PALM, 50);
        super.decorator.addTree(LOTRTreeType.CYPRESS, 400);
        super.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.PALM, 100);
        super.decorator.addTree(LOTRTreeType.LEMON, 5);
        super.decorator.addTree(LOTRTreeType.ORANGE, 5);
        super.decorator.addTree(LOTRTreeType.LIME, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.ALMOND, 5);
        super.decorator.addTree(LOTRTreeType.PLUM, 5);
        this.registerHaradFlowers();
        super.biomeColors.setGrass(11914805);
        super.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
        super.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NUMENOR(1, 3), 4000);
        super.decorator.addVillage(new LOTRVillageGenSouthron(this, 0.6f));
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterSouthronCoasts;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.SOUTHRON_COASTS;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("fertile");
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
        if (this.hasMixedHaradSoils()) {
            final double d1 = LOTRBiomeGenNearHaradFertile.noiseDirt.func_151601_a(i * 0.002, k * 0.002);
            final double d2 = LOTRBiomeGenNearHaradFertile.noiseDirt.func_151601_a(i * 0.07, k * 0.07);
            final double d3 = LOTRBiomeGenNearHaradFertile.noiseDirt.func_151601_a(i * 0.25, k * 0.25);
            final double d4 = LOTRBiomeGenNearHaradFertile.noiseSand.func_151601_a(i * 0.002, k * 0.002);
            final double d5 = LOTRBiomeGenNearHaradFertile.noiseSand.func_151601_a(i * 0.07, k * 0.07);
            final double d6 = LOTRBiomeGenNearHaradFertile.noiseSand.func_151601_a(i * 0.25, k * 0.25);
            final double d7 = LOTRBiomeGenNearHaradFertile.noiseRedSand.func_151601_a(i * 0.002, k * 0.002);
            final double d8 = LOTRBiomeGenNearHaradFertile.noiseRedSand.func_151601_a(i * 0.07, k * 0.07);
            final double d9 = LOTRBiomeGenNearHaradFertile.noiseRedSand.func_151601_a(i * 0.25, k * 0.25);
            if (d7 + d8 + d9 > 1.6) {
                super.topBlock = (Block)Blocks.sand;
                super.topBlockMeta = 1;
                super.fillerBlock = super.topBlock;
                super.fillerBlockMeta = super.topBlockMeta;
            }
            else if (d4 + d5 + d6 > 0.9) {
                super.topBlock = (Block)Blocks.sand;
                super.topBlockMeta = 0;
                super.fillerBlock = super.topBlock;
                super.fillerBlockMeta = super.topBlockMeta;
            }
            else if (d1 + d2 + d3 > 0.4) {
                super.topBlock = Blocks.dirt;
                super.topBlockMeta = 1;
            }
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    protected boolean hasMixedHaradSoils() {
        return true;
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
        return 0.3f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
    
    static {
        LOTRBiomeGenNearHaradFertile.noiseDirt = new NoiseGeneratorPerlin(new Random(12960262626062L), 1);
        LOTRBiomeGenNearHaradFertile.noiseSand = new NoiseGeneratorPerlin(new Random(17860128964L), 1);
        LOTRBiomeGenNearHaradFertile.noiseRedSand = new NoiseGeneratorPerlin(new Random(358960629620L), 1);
    }
}
