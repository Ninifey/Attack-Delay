// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenVolcanoCrater;
import lotr.common.world.feature.LOTRWorldGenStreams;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.feature.LOTRWorldGenObsidianGravel;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradVolcano extends LOTRBiomeGenFarHarad
{
    private WorldGenerator boulderGen;
    private WorldGenerator obsidianGen;
    private static NoiseGeneratorPerlin noiseDirt;
    
    public LOTRBiomeGenFarHaradVolcano(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        this.obsidianGen = new LOTRWorldGenObsidianGravel();
        super.topBlock = Blocks.stone;
        super.fillerBlock = Blocks.stone;
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableMonsterList.clear();
        super.spawnableLOTRAmbientList.clear();
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10).setSpawnChance(200);
        factionList.add(lists);
        super.decorator.treesPerChunk = 0;
        super.decorator.grassPerChunk = 0;
        super.decorator.doubleGrassPerChunk = 0;
        super.decorator.flowersPerChunk = 0;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
        super.decorator.addTree(LOTRTreeType.ACACIA_DEAD, 200);
        super.decorator.addTree(LOTRTreeType.CHARRED, 500);
        super.biomeColors.setSky(5986904);
        super.biomeColors.setClouds(3355443);
        super.biomeColors.setFog(6710886);
        super.biomeColors.setWater(4009759);
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterFarHaradVolcano;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("volcano");
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiomeGenFarHaradVolcano.noiseDirt.func_151601_a(i * 0.09, k * 0.09);
        final double d2 = LOTRBiomeGenFarHaradVolcano.noiseDirt.func_151601_a(i * 0.4, k * 0.4);
        if (d1 + d2 > 0.2) {
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
        if (random.nextInt(32) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        final WorldGenerator lavaGen = new LOTRWorldGenStreams((Block)Blocks.flowing_lava);
        for (int l = 0; l < 50; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = 40 + random.nextInt(120);
            final int k3 = k + random.nextInt(16) + 8;
            lavaGen.generate(world, random, i2, j1, k3);
        }
        if (random.nextInt(1) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int k4 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i3, k4);
            new LOTRWorldGenVolcanoCrater().generate(world, random, i3, j1, k4);
        }
        if (random.nextInt(50) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int k4 = k + random.nextInt(16) + 8;
            final int j1 = world.getTopSolidOrLiquidBlock(i3, k4);
            this.obsidianGen.generate(world, random, i3, j1, k4);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.5f;
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 2;
    }
    
    static {
        LOTRBiomeGenFarHaradVolcano.noiseDirt = new NoiseGeneratorPerlin(new Random(5286926989260620260L), 1);
    }
}
