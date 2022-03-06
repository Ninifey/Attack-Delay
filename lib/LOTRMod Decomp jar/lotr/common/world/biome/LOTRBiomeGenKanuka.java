// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenKanuka extends LOTRBiomeGenFarHarad
{
    private static NoiseGeneratorPerlin noisePaths1;
    private static NoiseGeneratorPerlin noisePaths2;
    private static final int FOREST_HEIGHT = 75;
    
    public LOTRBiomeGenKanuka(final int i, final boolean major) {
        super(i, major);
        super.spawnableLOTRAmbientList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBird.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 10, 4, 4));
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        super.enablePodzol = false;
        super.decorator.treesPerChunk = 0;
        super.decorator.setTreeCluster(8, 3);
        super.decorator.vinesPerChunk = 0;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 4;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.enableFern = true;
        super.decorator.melonPerChunk = 0.0f;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.KANUKA, 100);
        super.biomeColors.setGrass(11915563);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("kanuka");
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final double d1 = LOTRBiomeGenKanuka.noisePaths1.func_151601_a(i * 0.008, k * 0.008);
        final double d2 = LOTRBiomeGenKanuka.noisePaths2.func_151601_a(i * 0.008, k * 0.008);
        if ((d1 > 0.0 && d1 < 0.1) || (d2 > 0.0 && d2 < 0.1)) {
            super.topBlock = LOTRMod.dirtPath;
            super.topBlockMeta = 1;
        }
        super.enablePodzol = (height > 75);
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.enablePodzol = false;
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int count = 0; count < 4; ++count) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            if (j1 > 75) {
                super.decorator.genTree(world, random, i2, j1, k2);
            }
        }
        final LOTRBiomeVariant variant = ((LOTRWorldChunkManager)world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
        int grasses = 12;
        grasses = Math.round(grasses * variant.grassFactor);
        for (int l = 0; l < grasses; ++l) {
            final int i3 = i + random.nextInt(16) + 8;
            final int j2 = random.nextInt(128);
            final int k3 = k + random.nextInt(16) + 8;
            if (world.getHeightValue(i3, k3) > 75) {
                final WorldGenerator grassGen = this.getRandomWorldGenForGrass(random);
                grassGen.generate(world, random, i3, j2, k3);
            }
        }
        int doubleGrasses = 4;
        doubleGrasses = Math.round(doubleGrasses * variant.grassFactor);
        for (int m = 0; m < doubleGrasses; ++m) {
            final int i4 = i + random.nextInt(16) + 8;
            final int j3 = random.nextInt(128);
            final int k4 = k + random.nextInt(16) + 8;
            if (world.getHeightValue(i4, k4) > 75) {
                final WorldGenerator grassGen2 = this.getRandomWorldGenForDoubleGrass(random);
                grassGen2.generate(world, random, i4, j3, k4);
            }
        }
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        if (random.nextInt(5) != 0) {
            return new GrassBlockAndMeta((Block)Blocks.tallgrass, 2);
        }
        return super.getRandomGrass(random);
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForDoubleGrass(final Random random) {
        final WorldGenDoublePlant generator = new WorldGenDoublePlant();
        generator.func_150548_a(3);
        return (WorldGenerator)generator;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return super.getChanceToSpawnAnimals() * 0.25f;
    }
    
    static {
        LOTRBiomeGenKanuka.noisePaths1 = new NoiseGeneratorPerlin(new Random(22L), 1);
        LOTRBiomeGenKanuka.noisePaths2 = new NoiseGeneratorPerlin(new Random(11L), 1);
    }
}
