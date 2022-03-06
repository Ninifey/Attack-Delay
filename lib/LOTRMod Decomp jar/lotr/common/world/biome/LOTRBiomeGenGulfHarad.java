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
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenGulfHarad;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.animal.LOTREntityWhiteOryx;
import lotr.common.entity.animal.LOTREntityAurochs;
import net.minecraft.entity.passive.EntityChicken;
import lotr.common.entity.animal.LOTREntityWildBoar;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntitySheep;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenGulfHarad extends LOTRBiome
{
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseSand;
    protected static NoiseGeneratorPerlin noiseRedSand;
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenGulfHarad(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntitySheep.class, 12, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityWildBoar.class, 10, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityChicken.class, 8, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityAurochs.class, 6, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityWhiteOryx.class, 12, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityCamel.class, 2, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 10, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_HARADRIM, 20).setSpawnChance(100);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 5).setSpawnChance(100);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = { null };
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists3[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        factionList3.add(lists3);
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
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.flowersPerChunk = 1;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.deadBushPerChunk = 1;
        super.decorator.cactiPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.PALM, 500);
        super.decorator.addTree(LOTRTreeType.ACACIA, 300);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 400);
        super.decorator.addTree(LOTRTreeType.DRAGONBLOOD, 200);
        super.decorator.addTree(LOTRTreeType.DRAGONBLOOD_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.DATE_PALM, 50);
        super.decorator.addTree(LOTRTreeType.LEMON, 5);
        super.decorator.addTree(LOTRTreeType.ORANGE, 5);
        super.decorator.addTree(LOTRTreeType.LIME, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.ALMOND, 5);
        super.decorator.addTree(LOTRTreeType.PLUM, 5);
        this.registerHaradFlowers();
        super.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 3000);
        super.decorator.addVillage(new LOTRVillageGenGulfHarad(this, 0.75f));
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterGulfHarad;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.GULF_HARAD;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("gulf");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.GULF_HARAD;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        if (this.hasMixedHaradSoils()) {
            final double d1 = LOTRBiomeGenGulfHarad.noiseDirt.func_151601_a(i * 0.002, k * 0.002);
            final double d2 = LOTRBiomeGenGulfHarad.noiseDirt.func_151601_a(i * 0.07, k * 0.07);
            final double d3 = LOTRBiomeGenGulfHarad.noiseDirt.func_151601_a(i * 0.25, k * 0.25);
            final double d4 = LOTRBiomeGenGulfHarad.noiseSand.func_151601_a(i * 0.002, k * 0.002);
            final double d5 = LOTRBiomeGenGulfHarad.noiseSand.func_151601_a(i * 0.07, k * 0.07);
            final double d6 = LOTRBiomeGenGulfHarad.noiseSand.func_151601_a(i * 0.25, k * 0.25);
            final double d7 = LOTRBiomeGenGulfHarad.noiseRedSand.func_151601_a(i * 0.002, k * 0.002);
            final double d8 = LOTRBiomeGenGulfHarad.noiseRedSand.func_151601_a(i * 0.07, k * 0.07);
            final double d9 = LOTRBiomeGenGulfHarad.noiseRedSand.func_151601_a(i * 0.25, k * 0.25);
            if (d7 + d8 + d9 > 0.9) {
                super.topBlock = (Block)Blocks.sand;
                super.topBlockMeta = 1;
                super.fillerBlock = super.topBlock;
                super.fillerBlockMeta = super.topBlockMeta;
            }
            else if (d4 + d5 + d6 > 1.2) {
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
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(20) == 0) {
            for (int boulders = 1 + random.nextInt(3), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getHeightValue(i2, k2);
                this.boulderGen.generate(world, random, i2, j1, k2);
            }
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(final Random random) {
        final LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
        if (random.nextInt(3) != 0) {
            doubleFlowerGen.setFlowerType(3);
        }
        else {
            doubleFlowerGen.setFlowerType(2);
        }
        return doubleFlowerGen;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
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
        LOTRBiomeGenGulfHarad.noiseDirt = new NoiseGeneratorPerlin(new Random(8359286029006L), 1);
        LOTRBiomeGenGulfHarad.noiseSand = new NoiseGeneratorPerlin(new Random(473689270272L), 1);
        LOTRBiomeGenGulfHarad.noiseRedSand = new NoiseGeneratorPerlin(new Random(3528569078920702727L), 1);
    }
}
