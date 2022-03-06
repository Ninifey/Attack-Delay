// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.world.ChunkPosition;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import lotr.common.world.spawning.LOTRSpawnerAnimals;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.block.BlockFalling;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.Chunk;
import lotr.common.world.map.LOTRMountains;
import lotr.common.world.map.LOTRRoadGenerator;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import lotr.common.world.mapgen.tpyr.LOTRMapGenTauredainPyramid;
import lotr.common.world.mapgen.dwarvenmine.LOTRMapGenDwarvenMine;
import lotr.common.world.mapgen.LOTRMapGenRavine;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.MapGenBase;
import lotr.common.world.mapgen.LOTRMapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class LOTRChunkProvider implements IChunkProvider
{
    private World worldObj;
    private Random rand;
    private BiomeGenBase[] biomesForGeneration;
    private LOTRBiomeVariant[] variantsForGeneration;
    private static final double COORDINATE_SCALE = 684.412;
    private static final double HEIGHT_SCALE = 1.0;
    private static final double MAIN_NOISE_SCALE_XZ = 400.0;
    private static final double MAIN_NOISE_SCALE_Y = 5000.0;
    private static final double DEPTH_NOISE_SCALE = 200.0;
    private static final double DEPTH_NOISE_EXP = 0.5;
    private static final double HEIGHT_STRETCH = 6.0;
    private static final double UPPER_LIMIT_SCALE = 512.0;
    private static final double LOWER_LIMIT_SCALE = 512.0;
    private int biomeSampleRadius;
    private int biomeSampleWidth;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen5;
    private NoiseGeneratorOctaves noiseGen6;
    private NoiseGeneratorOctaves stoneNoiseGen;
    private double[] noise1;
    private double[] noise2;
    private double[] noise3;
    private double[] noise5;
    private double[] noise6;
    private double[] stoneNoise;
    private double[] heightNoise;
    private float[] biomeHeightNoise;
    private double[] blockHeightNoiseArray;
    private LOTRMapGenCaves caveGenerator;
    private MapGenBase ravineGenerator;
    private MapGenStructure dwarvenMineGenerator;
    private MapGenStructure tauredainPyramid;
    public static final int seaLevel = 62;
    
    public LOTRChunkProvider(final World world, final long seed) {
        this.stoneNoise = new double[256];
        this.caveGenerator = new LOTRMapGenCaves();
        this.ravineGenerator = (MapGenBase)new LOTRMapGenRavine();
        this.dwarvenMineGenerator = new LOTRMapGenDwarvenMine();
        this.tauredainPyramid = new LOTRMapGenTauredainPyramid();
        this.worldObj = world;
        this.rand = new Random(seed);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.stoneNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.biomeSampleRadius = 6;
        this.biomeSampleWidth = 2 * this.biomeSampleRadius + 1;
        this.biomeHeightNoise = new float[this.biomeSampleWidth * this.biomeSampleWidth];
        for (int i = -this.biomeSampleRadius; i <= this.biomeSampleRadius; ++i) {
            for (int k = -this.biomeSampleRadius; k <= this.biomeSampleRadius; ++k) {
                final float f = 10.0f / MathHelper.sqrt_float(i * i + k * k + 0.2f);
                this.biomeHeightNoise[i + this.biomeSampleRadius + (k + this.biomeSampleRadius) * this.biomeSampleWidth] = f;
            }
        }
    }
    
    private void generateTerrain(final int i, final int j, final Block[] blocks, final ChunkFlags chunkFlags) {
        final LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)this.worldObj.getWorldChunkManager();
        final byte byte0 = 4;
        final byte byte2 = 32;
        final int k = byte0 + 1;
        final byte byte3 = 33;
        final int l = byte0 + 1;
        this.biomesForGeneration = chunkManager.getBiomesForGeneration(this.biomesForGeneration, i * byte0 - this.biomeSampleRadius, j * byte0 - this.biomeSampleRadius, k + this.biomeSampleWidth, l + this.biomeSampleWidth);
        this.variantsForGeneration = chunkManager.getVariantsChunkGen(this.variantsForGeneration, i * byte0 - this.biomeSampleRadius, j * byte0 - this.biomeSampleRadius, k + this.biomeSampleWidth, l + this.biomeSampleWidth, this.biomesForGeneration);
        this.heightNoise = this.initializeHeightNoise(this.heightNoise, i * byte0, 0, j * byte0, k, byte3, l, chunkFlags);
        this.blockHeightNoiseArray = new double[blocks.length];
        for (int i2 = 0; i2 < byte0; ++i2) {
            for (int j2 = 0; j2 < byte0; ++j2) {
                for (int k2 = 0; k2 < byte2; ++k2) {
                    final double d = 0.125;
                    double d2 = this.heightNoise[((i2 + 0) * l + j2 + 0) * byte3 + k2 + 0];
                    double d3 = this.heightNoise[((i2 + 0) * l + j2 + 1) * byte3 + k2 + 0];
                    double d4 = this.heightNoise[((i2 + 1) * l + j2 + 0) * byte3 + k2 + 0];
                    double d5 = this.heightNoise[((i2 + 1) * l + j2 + 1) * byte3 + k2 + 0];
                    final double d6 = (this.heightNoise[((i2 + 0) * l + j2 + 0) * byte3 + k2 + 1] - d2) * d;
                    final double d7 = (this.heightNoise[((i2 + 0) * l + j2 + 1) * byte3 + k2 + 1] - d3) * d;
                    final double d8 = (this.heightNoise[((i2 + 1) * l + j2 + 0) * byte3 + k2 + 1] - d4) * d;
                    final double d9 = (this.heightNoise[((i2 + 1) * l + j2 + 1) * byte3 + k2 + 1] - d5) * d;
                    for (int l2 = 0; l2 < 8; ++l2) {
                        final double d10 = 0.25;
                        double d11 = d2;
                        double d12 = d3;
                        final double d13 = (d4 - d2) * d10;
                        final double d14 = (d5 - d3) * d10;
                        for (int i3 = 0; i3 < 4; ++i3) {
                            final int j3 = i3 + i2 * 4 << 12 | 0 + j2 * 4 << 8 | k2 * 8 + l2;
                            final double d15 = 0.25;
                            final double d16 = (d12 - d11) * d15;
                            for (int k3 = 0; k3 < 4; ++k3) {
                                final int blockIndex = j3 + k3 * 256;
                                final double blockHeightNoise = d11 + d16 * k3;
                                this.blockHeightNoiseArray[blockIndex] = blockHeightNoise;
                                if (blockHeightNoise > 0.0) {
                                    blocks[blockIndex] = Blocks.stone;
                                }
                                else if (k2 * 8 + l2 <= 62) {
                                    blocks[blockIndex] = Blocks.water;
                                }
                                else {
                                    blocks[blockIndex] = Blocks.air;
                                }
                            }
                            d11 += d13;
                            d12 += d14;
                        }
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                        d5 += d9;
                    }
                }
            }
        }
    }
    
    private void replaceBlocksForBiome(final int i, final int k, final Block[] blocks, final byte[] metadata, final BiomeGenBase[] biomeArray, final LOTRBiomeVariant[] variantArray, final ChunkFlags chunkFlags) {
        final double d = 0.03125;
        this.stoneNoise = this.stoneNoiseGen.generateNoiseOctaves(this.stoneNoise, i * 16, k * 16, 0, 16, 16, 1, d * 2.0, d * 2.0, d * 2.0);
        final int ySize = blocks.length / 256;
        for (int i2 = 0; i2 < 16; ++i2) {
            for (int k2 = 0; k2 < 16; ++k2) {
                final int x = i * 16 + i2;
                final int z = k * 16 + k2;
                final int xzIndex = i2 * 16 + k2;
                final int xzIndexBiome = i2 + k2 * 16;
                final LOTRBiome biome = (LOTRBiome)biomeArray[xzIndexBiome];
                final LOTRBiomeVariant variant = variantArray[xzIndexBiome];
                int height = 0;
                for (int j = ySize - 1; j >= 0; --j) {
                    final int index = xzIndex * ySize + j;
                    final Block block = blocks[index];
                    if (block.isOpaqueCube()) {
                        height = j;
                        break;
                    }
                }
                biome.generateBiomeTerrain(this.worldObj, this.rand, blocks, metadata, x, z, this.stoneNoise[xzIndex], height, variant);
                if (LOTRFixedStructures.hasMapFeatures(this.worldObj)) {
                    final boolean road = LOTRRoadGenerator.generateRoad(this.worldObj, this.rand, x, z, biome, blocks, metadata, this.blockHeightNoiseArray);
                    chunkFlags.roadFlags[xzIndex] = road;
                    final int lavaHeight = LOTRMountains.getLavaHeight(x, z);
                    if (lavaHeight > 0) {
                        for (int l = lavaHeight; l >= 0; --l) {
                            final int index2 = xzIndex * ySize + l;
                            final Block block2 = blocks[index2];
                            if (block2.isOpaqueCube()) {
                                break;
                            }
                            blocks[index2] = Blocks.lava;
                            metadata[index2] = 0;
                        }
                    }
                }
            }
        }
    }
    
    public Chunk loadChunk(final int i, final int k) {
        return this.provideChunk(i, k);
    }
    
    public Chunk provideChunk(final int i, final int k) {
        this.rand.setSeed(i * 341873128712L + k * 132897987541L);
        final LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)this.worldObj.getWorldChunkManager();
        final Block[] blocks = new Block[65536];
        final byte[] metadata = new byte[65536];
        final ChunkFlags chunkFlags = new ChunkFlags();
        this.generateTerrain(i, k, blocks, chunkFlags);
        this.biomesForGeneration = chunkManager.loadBlockGeneratorData(this.biomesForGeneration, i * 16, k * 16, 16, 16);
        this.variantsForGeneration = chunkManager.getBiomeVariants(this.variantsForGeneration, i * 16, k * 16, 16, 16);
        this.replaceBlocksForBiome(i, k, blocks, metadata, this.biomesForGeneration, this.variantsForGeneration, chunkFlags);
        this.caveGenerator.chunkFlags = chunkFlags;
        this.caveGenerator.func_151539_a((IChunkProvider)this, this.worldObj, i, k, blocks);
        this.ravineGenerator.func_151539_a((IChunkProvider)this, this.worldObj, i, k, blocks);
        this.dwarvenMineGenerator.func_151539_a((IChunkProvider)this, this.worldObj, i, k, blocks);
        this.tauredainPyramid.func_151539_a((IChunkProvider)this, this.worldObj, i, k, blocks);
        final Chunk chunk = new Chunk(this.worldObj, i, k);
        final ExtendedBlockStorage[] blockStorage = chunk.getBlockStorageArray();
        for (int i2 = 0; i2 < 16; ++i2) {
            for (int k2 = 0; k2 < 16; ++k2) {
                for (int j1 = 0; j1 < 256; ++j1) {
                    final int blockIndex = i2 << 12 | k2 << 8 | j1;
                    final Block block = blocks[blockIndex];
                    if (block != null) {
                        if (block != Blocks.air) {
                            final byte meta = metadata[blockIndex];
                            final int j2 = j1 >> 4;
                            if (blockStorage[j2] == null) {
                                blockStorage[j2] = new ExtendedBlockStorage(j2 << 4, true);
                            }
                            blockStorage[j2].func_150818_a(i2, j1 & 0xF, k2, block);
                            blockStorage[j2].setExtBlockMetadata(i2, j1 & 0xF, k2, meta & 0xF);
                        }
                    }
                }
            }
        }
        final byte[] biomes = chunk.getBiomeArray();
        for (int l = 0; l < biomes.length; ++l) {
            biomes[l] = (byte)this.biomesForGeneration[l].biomeID;
        }
        final byte[] variants = new byte[256];
        for (int m = 0; m < variants.length; ++m) {
            variants[m] = (byte)this.variantsForGeneration[m].variantID;
        }
        LOTRBiomeVariantStorage.setChunkBiomeVariants(this.worldObj, chunk, variants);
        chunk.generateSkylightMap();
        LOTRFixedStructures.nanoTimeElapsed = 0L;
        return chunk;
    }
    
    private double[] initializeHeightNoise(double[] noise, final int i, final int j, final int k, final int xSize, final int ySize, final int zSize, final ChunkFlags chunkFlags) {
        if (noise == null) {
            noise = new double[xSize * ySize * zSize];
        }
        double xzNoiseScale = 400.0;
        double heightStretch = 6.0;
        final int noiseCentralIndex = (xSize - 1) / 2 + this.biomeSampleRadius + ((zSize - 1) / 2 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
        final LOTRBiome noiseCentralBiome = (LOTRBiome)this.biomesForGeneration[noiseCentralIndex];
        if (noiseCentralBiome.biomeTerrain.hasXZScale()) {
            xzNoiseScale = noiseCentralBiome.biomeTerrain.getXZScale();
        }
        if (noiseCentralBiome.biomeTerrain.hasHeightStretchFactor()) {
            heightStretch *= noiseCentralBiome.biomeTerrain.getHeightStretchFactor();
        }
        this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, i, k, xSize, zSize, 1.121, 1.121, 0.5);
        this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, i, k, xSize, zSize, 200.0, 200.0, 0.5);
        this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, i, j, k, xSize, ySize, zSize, 684.412 / xzNoiseScale, 2.0E-4, 684.412 / xzNoiseScale);
        this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, i, j, k, xSize, ySize, zSize, 684.412, 1.0, 684.412);
        this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, i, j, k, xSize, ySize, zSize, 684.412, 1.0, 684.412);
        int noiseIndexXZ = 0;
        int noiseIndex = 0;
        for (int i2 = 0; i2 < xSize; ++i2) {
            for (int k2 = 0; k2 < zSize; ++k2) {
                int xPos = i + i2 << 2;
                int zPos = k + k2 << 2;
                xPos += 2;
                zPos += 2;
                float totalBaseHeight = 0.0f;
                float totalHeightVariation = 0.0f;
                float totalHeightNoise = 0.0f;
                float totalFlatBiomeHeight = 0.0f;
                int totalFlatBiomeCount = 0;
                final int centreBiomeIndex = i2 + this.biomeSampleRadius + (k2 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
                final BiomeGenBase centreBiome = this.biomesForGeneration[centreBiomeIndex];
                final LOTRBiomeVariant centreVariant = this.variantsForGeneration[centreBiomeIndex];
                float centreHeight = centreBiome.minHeight + centreVariant.getHeightBoostAt(xPos, zPos);
                if (centreVariant.absoluteHeight) {
                    centreHeight = centreVariant.getHeightBoostAt(xPos, zPos);
                }
                for (int i3 = -this.biomeSampleRadius; i3 <= this.biomeSampleRadius; ++i3) {
                    for (int k3 = -this.biomeSampleRadius; k3 <= this.biomeSampleRadius; ++k3) {
                        final int biomeIndex = i2 + i3 + this.biomeSampleRadius + (k2 + k3 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
                        final BiomeGenBase biome = this.biomesForGeneration[biomeIndex];
                        final LOTRBiomeVariant variant = this.variantsForGeneration[biomeIndex];
                        final int xPosHere = xPos + (i3 << 2);
                        final int zPosHere = zPos + (k3 << 2);
                        float baseHeight = biome.minHeight + variant.getHeightBoostAt(xPosHere, zPosHere);
                        float heightVariation = biome.maxHeight * variant.hillFactor;
                        if (variant.absoluteHeight) {
                            baseHeight = variant.getHeightBoostAt(xPosHere, zPosHere);
                            heightVariation = variant.hillFactor;
                        }
                        float baseHeightPlus = baseHeight + 2.0f;
                        if (baseHeightPlus == 0.0f) {
                            baseHeightPlus = 0.001f;
                        }
                        float heightNoise = this.biomeHeightNoise[i3 + this.biomeSampleRadius + (k3 + this.biomeSampleRadius) * this.biomeSampleWidth] / baseHeightPlus / 2.0f;
                        heightNoise = Math.abs(heightNoise);
                        if (baseHeight > centreHeight) {
                            heightNoise /= 2.0f;
                        }
                        totalBaseHeight += baseHeight * heightNoise;
                        totalHeightVariation += heightVariation * heightNoise;
                        totalHeightNoise += heightNoise;
                        float flatBiomeHeight = biome.minHeight;
                        boolean isWater = ((LOTRBiome)biome).isWateryBiome();
                        if (variant.absoluteHeight && variant.absoluteHeightLevel < 0.0f) {
                            isWater = true;
                        }
                        if (isWater) {
                            flatBiomeHeight = baseHeight;
                        }
                        totalFlatBiomeHeight += flatBiomeHeight;
                        ++totalFlatBiomeCount;
                    }
                }
                float avgBaseHeight = totalBaseHeight / totalHeightNoise;
                float avgHeightVariation = totalHeightVariation / totalHeightNoise;
                final float avgFlatBiomeHeight = totalFlatBiomeHeight / totalFlatBiomeCount;
                if (LOTRFixedStructures.hasMapFeatures(this.worldObj)) {
                    final float roadNear = LOTRRoads.isRoadNear(xPos, zPos, 32);
                    if (roadNear >= 0.0f) {
                        final float interpFactor = roadNear;
                        avgBaseHeight = avgFlatBiomeHeight + (avgBaseHeight - avgFlatBiomeHeight) * interpFactor;
                        avgHeightVariation *= interpFactor;
                    }
                    final float mountain = LOTRMountains.getTotalHeightBoost(xPos, zPos);
                    if (mountain > 0.005f) {
                        avgBaseHeight += mountain;
                        final float mtnV = 0.2f;
                        final float dv = avgHeightVariation - mtnV;
                        avgHeightVariation = mtnV + dv / (1.0f + mountain);
                    }
                }
                if (centreBiome instanceof LOTRBiome) {
                    final LOTRBiome lb = (LOTRBiome)centreBiome;
                    lb.decorator.checkForVillages(this.worldObj, xPos, zPos, chunkFlags);
                    if (chunkFlags.isFlatVillage) {
                        avgBaseHeight = avgFlatBiomeHeight;
                        avgHeightVariation = 0.0f;
                    }
                }
                avgBaseHeight = (avgBaseHeight * 4.0f - 1.0f) / 8.0f;
                if (avgHeightVariation == 0.0f) {
                    avgHeightVariation = 0.001f;
                }
                double heightNoise2 = this.noise6[noiseIndexXZ] / 8000.0;
                if (heightNoise2 < 0.0) {
                    heightNoise2 = -heightNoise2 * 0.3;
                }
                heightNoise2 = heightNoise2 * 3.0 - 2.0;
                if (heightNoise2 < 0.0) {
                    heightNoise2 /= 2.0;
                    if (heightNoise2 < -1.0) {
                        heightNoise2 = -1.0;
                    }
                    heightNoise2 /= 1.4;
                    heightNoise2 /= 2.0;
                }
                else {
                    if (heightNoise2 > 1.0) {
                        heightNoise2 = 1.0;
                    }
                    heightNoise2 /= 8.0;
                }
                ++noiseIndexXZ;
                for (int j2 = 0; j2 < ySize; ++j2) {
                    double baseHeight2 = avgBaseHeight;
                    final double heightVariation2 = avgHeightVariation;
                    baseHeight2 += heightNoise2 * 0.2 * centreVariant.hillFactor;
                    baseHeight2 = baseHeight2 * ySize / 16.0;
                    final double var28 = ySize / 2.0 + baseHeight2 * 4.0;
                    double totalNoise = 0.0;
                    double var29 = (j2 - var28) * heightStretch * 128.0 / 256.0 / heightVariation2;
                    if (var29 < 0.0) {
                        var29 *= 4.0;
                    }
                    final double var30 = this.noise1[noiseIndex] / 512.0;
                    final double var31 = this.noise2[noiseIndex] / 512.0;
                    final double var32 = (this.noise3[noiseIndex] / 10.0 + 1.0) / 2.0 * centreVariant.hillFactor;
                    if (var32 < 0.0) {
                        totalNoise = var30;
                    }
                    else if (var32 > 1.0) {
                        totalNoise = var31;
                    }
                    else {
                        totalNoise = var30 + (var31 - var30) * var32;
                    }
                    totalNoise -= var29;
                    if (j2 > ySize - 4) {
                        final double var33 = (j2 - (ySize - 4)) / 3.0f;
                        totalNoise = totalNoise * (1.0 - var33) + -10.0 * var33;
                    }
                    noise[noiseIndex] = totalNoise;
                    ++noiseIndex;
                }
            }
        }
        return noise;
    }
    
    public boolean chunkExists(final int i, final int j) {
        return true;
    }
    
    public void populate(final IChunkProvider ichunkprovider, final int i, final int j) {
        BlockFalling.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        final LOTRBiome biome = (LOTRBiome)this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        final LOTRBiomeVariant variant = ((LOTRWorldChunkManager)this.worldObj.getWorldChunkManager()).getBiomeVariantAt(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        final long l2 = this.rand.nextLong() / 2L * 2L + 1L;
        final long l3 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(i * l2 + j * l3 ^ this.worldObj.getSeed());
        this.dwarvenMineGenerator.generateStructuresInChunk(this.worldObj, this.rand, i, j);
        this.tauredainPyramid.generateStructuresInChunk(this.worldObj, this.rand, i, j);
        if (this.rand.nextInt(4) == 0) {
            final int i2 = k + this.rand.nextInt(16) + 8;
            final int j2 = this.rand.nextInt(128);
            final int k2 = l + this.rand.nextInt(16) + 8;
            if (j2 < 60) {
                new WorldGenLakes(Blocks.water).generate(this.worldObj, this.rand, i2, j2, k2);
            }
        }
        if (this.rand.nextInt(8) == 0) {
            final int i2 = k + this.rand.nextInt(16) + 8;
            final int j2 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            final int k2 = l + this.rand.nextInt(16) + 8;
            if (j2 < 60) {
                new WorldGenLakes(Blocks.lava).generate(this.worldObj, this.rand, i2, j2, k2);
            }
        }
        biome.decorate(this.worldObj, this.rand, k, l);
        if (biome.getChanceToSpawnAnimals() <= 1.0f) {
            if (this.rand.nextFloat() < biome.getChanceToSpawnAnimals()) {
                LOTRSpawnerAnimals.worldGenSpawnAnimals(this.worldObj, biome, variant, k + 8, l + 8, this.rand);
            }
        }
        else {
            for (int spawns = MathHelper.floor_double((double)biome.getChanceToSpawnAnimals()), i3 = 0; i3 < spawns; ++i3) {
                LOTRSpawnerAnimals.worldGenSpawnAnimals(this.worldObj, biome, variant, k + 8, l + 8, this.rand);
            }
        }
        k += 8;
        l += 8;
        for (int i2 = 0; i2 < 16; ++i2) {
            for (int k3 = 0; k3 < 16; ++k3) {
                final int j3 = this.worldObj.getPrecipitationHeight(k + i2, l + k3);
                if (this.worldObj.isBlockFreezable(i2 + k, j3 - 1, k3 + l)) {
                    this.worldObj.setBlock(i2 + k, j3 - 1, k3 + l, Blocks.ice, 0, 2);
                }
                if (this.worldObj.func_147478_e(i2 + k, j3, k3 + l, true)) {
                    this.worldObj.setBlock(i2 + k, j3, k3 + l, Blocks.snow_layer, 0, 2);
                }
            }
        }
        BlockFalling.fallInstantly = false;
    }
    
    public boolean saveChunks(final boolean flag, final IProgressUpdate update) {
        return true;
    }
    
    public void saveExtraData() {
    }
    
    public boolean unloadQueuedChunks() {
        return false;
    }
    
    public boolean canSave() {
        return true;
    }
    
    public String makeString() {
        return "MiddleEarthLevelSource";
    }
    
    public List getPossibleCreatures(final EnumCreatureType creatureType, final int i, final int j, final int k) {
        final BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        return (biome == null) ? null : biome.getSpawnableList(creatureType);
    }
    
    public ChunkPosition func_147416_a(final World world, final String type, final int i, final int j, final int k) {
        return null;
    }
    
    public int getLoadedChunkCount() {
        return 0;
    }
    
    public void recreateStructures(final int i, final int k) {
        this.dwarvenMineGenerator.func_151539_a((IChunkProvider)this, this.worldObj, i, k, (Block[])null);
        this.tauredainPyramid.func_151539_a((IChunkProvider)this, this.worldObj, i, k, (Block[])null);
    }
    
    public static class ChunkFlags
    {
        public boolean isVillage;
        public boolean isFlatVillage;
        public boolean[] roadFlags;
        
        private ChunkFlags() {
            this.isVillage = false;
            this.isFlatVillage = false;
            this.roadFlags = new boolean[256];
        }
    }
}
