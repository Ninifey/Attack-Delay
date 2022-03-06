// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.feature.LOTRWorldGenMordorMoss;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenBlackUrukFort;
import lotr.common.world.structure.LOTRWorldGenMordorTower;
import lotr.common.world.structure2.LOTRWorldGenMordorWargPit;
import lotr.common.world.structure2.LOTRWorldGenMordorCamp;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenMordor extends LOTRBiome
{
    protected WorldGenerator boulderGen;
    protected boolean enableMordorBoulders;
    protected static NoiseGeneratorPerlin noiseDirt;
    protected static NoiseGeneratorPerlin noiseGravel;
    
    public LOTRBiomeGenMordor(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 0, 2, 8);
        this.enableMordorBoulders = true;
        super.topBlock = LOTRMod.rock;
        super.topBlockMeta = 0;
        super.fillerBlock = LOTRMod.rock;
        super.fillerBlockMeta = 0;
        if (this.isGorgoroth()) {
            this.setDisableRain();
        }
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[5];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 5);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 30);
        final int n4 = 3;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 10);
        final int n5 = 4;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 7);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(1);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WICKED_DWARVES, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = { null };
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists4[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        final int n9 = 1;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists4[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
        factionList4.add(lists4);
        super.npcSpawnList.conquestGainRate = 0.5f;
        super.decorator.clearOres();
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.mordorDirt, 0, 60, LOTRMod.rock), 10.0f, 0, 60);
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.mordorGravel, 0, 32, LOTRMod.rock), 10.0f, 0, 60);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreNaurite, 12, LOTRMod.rock), 20.0f, 0, 64);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreMorgulIron, 1, 8, LOTRMod.rock), 20.0f, 0, 64);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreGulduril, 1, 8, LOTRMod.rock), 6.0f, 0, 32);
        super.decorator.flowersPerChunk = 0;
        super.decorator.grassPerChunk = 1;
        super.decorator.generateWater = false;
        if (this.isGorgoroth()) {
            super.decorator.sandPerChunk = 0;
            super.decorator.clayPerChunk = 0;
            super.decorator.dryReedChance = 1.0f;
            super.enableRocky = false;
        }
        super.decorator.addTree(LOTRTreeType.CHARRED, 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenMordorCamp(false), 100);
        super.decorator.addRandomStructure(new LOTRWorldGenMordorWargPit(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenMordorTower(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenBlackUrukFort(false), 2000);
        super.biomeColors.setGrass(5980459);
        super.biomeColors.setFoliage(6508333);
        super.biomeColors.setSky(6700595);
        super.biomeColors.setClouds(4924185);
        super.biomeColors.setFog(3154711);
        super.biomeColors.setWater(2498845);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public boolean hasSky() {
        return !this.isGorgoroth();
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMordor;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.MORDOR;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("mordor");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.MORDOR;
    }
    
    @Override
    public LOTRRoadType.BridgeType getBridgeBlock() {
        return LOTRRoadType.BridgeType.CHARRED;
    }
    
    public boolean isGorgoroth() {
        return true;
    }
    
    protected boolean hasMordorSoils() {
        return true;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        if (this.isGorgoroth() && this.hasMordorSoils()) {
            final double d1 = LOTRBiomeGenMordor.noiseDirt.func_151601_a(i * 0.08, k * 0.08);
            final double d2 = LOTRBiomeGenMordor.noiseDirt.func_151601_a(i * 0.4, k * 0.4);
            final double d3 = LOTRBiomeGenMordor.noiseGravel.func_151601_a(i * 0.08, k * 0.08);
            final double d4 = LOTRBiomeGenMordor.noiseGravel.func_151601_a(i * 0.4, k * 0.4);
            if (d3 + d4 > 0.8) {
                super.topBlock = LOTRMod.mordorGravel;
                super.topBlockMeta = 0;
                super.fillerBlock = super.topBlock;
                super.fillerBlockMeta = super.topBlockMeta;
            }
            else if (d1 + d2 > 0.5) {
                super.topBlock = LOTRMod.mordorDirt;
                super.topBlockMeta = 0;
                super.fillerBlock = super.topBlock;
                super.fillerBlockMeta = super.topBlockMeta;
            }
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
        for (int j = ySize - 1; j >= 0; --j) {
            final int index = xzIndex * ySize + j;
            final Block block = blocks[index];
            if (block == Blocks.stone) {
                blocks[index] = LOTRMod.rock;
                meta[index] = 0;
            }
        }
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (this.isGorgoroth()) {
            if (this.enableMordorBoulders && random.nextInt(24) == 0) {
                for (int l = 0; l < 6; ++l) {
                    final int i2 = i + random.nextInt(16) + 8;
                    final int k2 = k + random.nextInt(16) + 8;
                    this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
                }
            }
            if (random.nextInt(60) == 0) {
                for (int l = 0; l < 8; ++l) {
                    final int i2 = i + random.nextInt(16) + 8;
                    final int k2 = k + random.nextInt(16) + 8;
                    final int j1 = world.getHeightValue(i2, k2);
                    super.decorator.genTree(world, random, i2, j1, k2);
                }
            }
            if (super.decorator.grassPerChunk > 0) {
                if (random.nextInt(20) == 0) {
                    for (int l = 0; l < 6; ++l) {
                        final int i2 = i + random.nextInt(6) + 8;
                        final int k2 = k + random.nextInt(6) + 8;
                        final int j1 = world.getHeightValue(i2, k2);
                        if (world.isAirBlock(i2, j1, k2) && LOTRMod.mordorThorn.canBlockStay(world, i2, j1, k2)) {
                            world.setBlock(i2, j1, k2, LOTRMod.mordorThorn, 0, 2);
                        }
                    }
                }
                if (random.nextInt(20) == 0) {
                    final int i3 = i + random.nextInt(16) + 8;
                    final int k3 = k + random.nextInt(16) + 8;
                    final int j2 = world.getHeightValue(i3, k3);
                    if (world.isAirBlock(i3, j2, k3) && LOTRMod.mordorMoss.canBlockStay(world, i3, j2, k3)) {
                        new LOTRWorldGenMordorMoss().generate(world, random, i3, j2, k3);
                    }
                }
            }
        }
        if (LOTRFixedStructures.MORDOR_CHERRY_TREE.isAt(i, k)) {
            final int i3 = i + 8;
            final int k3 = k + 8;
            final int j2 = world.getHeightValue(i3, k3);
            LOTRTreeType.CHERRY_MORDOR.create(false, random).generate(world, random, i3, j2, k3);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.0f;
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        if (this.isGorgoroth()) {
            return new GrassBlockAndMeta(LOTRMod.mordorGrass, 0);
        }
        return super.getRandomGrass(random);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.0f;
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
    
    public static boolean isSurfaceMordorBlock(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);
        final int meta = world.getBlockMetadata(i, j, k);
        return (block == LOTRMod.rock && meta == 0) || block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel;
    }
    
    static {
        LOTRBiomeGenMordor.noiseDirt = new NoiseGeneratorPerlin(new Random(389502092662L), 1);
        LOTRBiomeGenMordor.noiseGravel = new NoiseGeneratorPerlin(new Random(1379468206L), 1);
    }
}
