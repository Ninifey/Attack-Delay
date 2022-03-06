// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen;

import net.minecraft.world.gen.MapGenBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenRavine;

public class LOTRMapGenRavine extends MapGenRavine
{
    private float[] ravineNoise;
    
    public LOTRMapGenRavine() {
        this.ravineNoise = new float[1024];
    }
    
    protected void func_151538_a(final World world, final int i, final int k, final int chunkX, final int chunkZ, final Block[] blocks) {
        final BiomeGenBase biome = ((MapGenBase)this).worldObj.getBiomeGenForCoords(chunkX * 16, chunkZ * 16);
        if (((MapGenBase)this).rand.nextBoolean()) {
            super.func_151538_a(world, i, k, chunkX, chunkZ, blocks);
        }
        else if (biome instanceof LOTRBiomeGenMordor && ((LOTRBiomeGenMordor)biome).isGorgoroth()) {
            for (int l = 0; l < 20; ++l) {
                super.func_151538_a(world, i, k, chunkX, chunkZ, blocks);
            }
        }
    }
    
    protected void func_151540_a(final long seed, final int chunkX, final int chunkZ, final Block[] blocks, double d, double d1, double d2, final float f, float ravineAngle, float f2, int intPar1, int intPar2, final double increase) {
        final Random random = new Random(seed);
        final double chunkCentreX = chunkX * 16 + 8;
        final double chunkCentreZ = chunkZ * 16 + 8;
        float f3 = 0.0f;
        float f4 = 0.0f;
        if (intPar2 <= 0) {
            final int j1 = ((MapGenBase)this).range * 16 - 16;
            intPar2 = j1 - random.nextInt(j1 / 4);
        }
        boolean flag = false;
        if (intPar1 == -1) {
            intPar1 = intPar2 / 2;
            flag = true;
        }
        float f5 = 1.0f;
        for (int k1 = 0; k1 < 256; ++k1) {
            if (k1 == 0 || random.nextInt(3) == 0) {
                f5 = 1.0f + random.nextFloat() * random.nextFloat() * 1.0f;
            }
            this.ravineNoise[k1] = f5 * f5;
        }
        while (intPar1 < intPar2) {
            double d3 = 1.5 + MathHelper.sin(intPar1 * 3.1415927f / intPar2) * f * 1.0f;
            double d4 = d3 * increase;
            d3 *= random.nextFloat() * 0.25 + 0.75;
            d4 *= random.nextFloat() * 0.25 + 0.75;
            final float f6 = MathHelper.cos(f2);
            final float f7 = MathHelper.sin(f2);
            d += MathHelper.cos(ravineAngle) * f6;
            d1 += f7;
            d2 += MathHelper.sin(ravineAngle) * f6;
            f2 *= 0.7f;
            f2 += f4 * 0.05f;
            ravineAngle += f3 * 0.05f;
            f4 *= 0.8f;
            f3 *= 0.5f;
            f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
            f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
            if (flag || random.nextInt(4) != 0) {
                final double d5 = d - chunkCentreX;
                final double d6 = d2 - chunkCentreZ;
                final double d7 = intPar2 - intPar1;
                final double d8 = f + 2.0f + 16.0f;
                if (d5 * d5 + d6 * d6 - d7 * d7 > d8 * d8) {
                    return;
                }
                if (d >= chunkCentreX - 16.0 - d3 * 2.0 && d2 >= chunkCentreZ - 16.0 - d3 * 2.0 && d <= chunkCentreX + 16.0 + d3 * 2.0 && d2 <= chunkCentreZ + 16.0 + d3 * 2.0) {
                    int xMin = MathHelper.floor_double(d - d3) - chunkX * 16 - 1;
                    int xMax = MathHelper.floor_double(d + d3) - chunkX * 16 + 1;
                    int yMin = MathHelper.floor_double(d1 - d4) - 1;
                    int yMax = MathHelper.floor_double(d1 + d4) + 1;
                    int zMin = MathHelper.floor_double(d2 - d3) - chunkZ * 16 - 1;
                    int zMax = MathHelper.floor_double(d2 + d3) - chunkZ * 16 + 1;
                    xMin = Math.max(xMin, 0);
                    xMax = Math.min(xMax, 16);
                    yMin = Math.max(yMin, 1);
                    yMax = Math.min(yMax, 120);
                    zMin = Math.max(zMin, 0);
                    zMax = Math.min(zMax, 16);
                    boolean isWater = false;
                Label_0824:
                    for (int i1 = xMin; i1 < xMax; ++i1) {
                        for (int k2 = zMin; k2 < zMax; ++k2) {
                            for (int j2 = yMax + 1; j2 >= yMin - 1; --j2) {
                                final int blockIndex = (i1 * 16 + k2) * 256 + j2;
                                if (j2 >= 0 && j2 < 256) {
                                    if (this.isOceanBlock(blocks, blockIndex, i1, j2, k2, chunkX, chunkZ)) {
                                        isWater = true;
                                    }
                                    if (j2 != yMin - 1 && i1 != xMin && i1 != xMax - 1 && k2 != zMin && k2 != zMax - 1) {
                                        j2 = yMin;
                                    }
                                    if (isWater) {
                                        break Label_0824;
                                    }
                                }
                            }
                        }
                    }
                    if (!isWater) {
                        for (int i1 = xMin; i1 < xMax; ++i1) {
                            final double d9 = (i1 + chunkX * 16 + 0.5 - d) / d3;
                            for (int k3 = zMin; k3 < zMax; ++k3) {
                                final double d10 = (k3 + chunkZ * 16 + 0.5 - d2) / d3;
                                int blockIndex2 = (i1 * 16 + k3) * 256 + yMax;
                                boolean topBlock = false;
                                if (d9 * d9 + d10 * d10 < 1.0) {
                                    for (int j3 = yMax - 1; j3 >= yMin; --j3) {
                                        final double d11 = (j3 + 0.5 - d1) / d4;
                                        if ((d9 * d9 + d10 * d10) * this.ravineNoise[j3] + d11 * d11 / 6.0 < 1.0) {
                                            if (this.isTopBlock(blocks, blockIndex2, i1, j3, k3, chunkX, chunkZ)) {
                                                topBlock = true;
                                            }
                                            this.digBlock(blocks, blockIndex2, i1, j3, k3, chunkX, chunkZ, topBlock);
                                        }
                                        --blockIndex2;
                                    }
                                }
                            }
                        }
                        if (flag) {
                            break;
                        }
                    }
                }
            }
            ++intPar1;
        }
    }
    
    private boolean isTopBlock(final Block[] data, final int index, final int i, final int j, final int k, final int chunkX, final int chunkZ) {
        final BiomeGenBase biome = ((MapGenBase)this).worldObj.getBiomeGenForCoords(i + chunkX * 16, k + chunkZ * 16);
        return data[index] == biome.topBlock;
    }
    
    protected void digBlock(final Block[] data, final int index, final int i, final int j, final int k, final int chunkX, final int chunkZ, final boolean topBlock) {
        final BiomeGenBase biome = ((MapGenBase)this).worldObj.getBiomeGenForCoords(i + chunkX * 16, k + chunkZ * 16);
        final Block top = biome.topBlock;
        final Block filler = biome.fillerBlock;
        final Block block = data[index];
        if (LOTRMapGenCaves.isTerrainBlock(block, biome)) {
            if (j < 10) {
                data[index] = (Block)Blocks.flowing_lava;
            }
            else if (biome instanceof LOTRBiomeGenMordor && ((LOTRBiomeGenMordor)biome).isGorgoroth() && j < 60) {
                data[index] = (Block)Blocks.flowing_lava;
            }
            else {
                data[index] = Blocks.air;
                if (topBlock && data[index - 1] == filler) {
                    data[index - 1] = top;
                }
            }
        }
    }
}
