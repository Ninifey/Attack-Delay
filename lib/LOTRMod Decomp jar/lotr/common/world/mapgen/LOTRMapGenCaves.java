// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen;

import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.block.Block;
import lotr.common.world.LOTRChunkProvider;
import net.minecraft.world.gen.MapGenBase;

public class LOTRMapGenCaves extends MapGenBase
{
    public LOTRChunkProvider.ChunkFlags chunkFlags;
    
    protected void generateLargeCaveNode(final long seed, final int par3, final int par4, final Block[] blockArray, final double par6, final double par8, final double par10, final boolean cutSurface) {
        this.generateCaveNode(seed, par3, par4, blockArray, par6, par8, par10, 1.0f + super.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5, cutSurface);
    }
    
    protected void generateCaveNode(final long seed, final int par3, final int par4, final Block[] blockArray, double par6, double par8, double par10, final float par12, float angle, float par14, int par15, int par16, final double par17, final boolean cutSurface) {
        final double var19 = par3 * 16 + 8;
        final double var20 = par4 * 16 + 8;
        float var21 = 0.0f;
        float var22 = 0.0f;
        final Random caveRand = new Random(seed);
        if (par16 <= 0) {
            final int var23 = super.range * 16 - 16;
            par16 = var23 - caveRand.nextInt(var23 / 4);
        }
        boolean var24 = false;
        if (par15 == -1) {
            par15 = par16 / 2;
            var24 = true;
        }
        final int var25 = caveRand.nextInt(par16 / 2) + par16 / 4;
        final boolean var26 = caveRand.nextInt(6) == 0;
        while (par15 < par16) {
            final double var27 = 1.5 + MathHelper.sin(par15 * 3.1415927f / par16) * par12 * 1.0f;
            final double var28 = var27 * par17;
            final float var29 = MathHelper.cos(par14);
            final float var30 = MathHelper.sin(par14);
            par6 += MathHelper.cos(angle) * var29;
            par8 += var30;
            par10 += MathHelper.sin(angle) * var29;
            if (var26) {
                par14 *= 0.92f;
            }
            else {
                par14 *= 0.7f;
            }
            par14 += var22 * 0.1f;
            angle += var21 * 0.1f;
            var22 *= 0.9f;
            var21 *= 0.75f;
            var22 += (caveRand.nextFloat() - caveRand.nextFloat()) * caveRand.nextFloat() * 2.0f;
            var21 += (caveRand.nextFloat() - caveRand.nextFloat()) * caveRand.nextFloat() * 4.0f;
            if (!var24 && par15 == var25 && par12 > 1.0f && par16 > 0) {
                this.generateCaveNode(caveRand.nextLong(), par3, par4, blockArray, par6, par8, par10, caveRand.nextFloat() * 0.5f + 0.5f, angle - 1.5707964f, par14 / 3.0f, par15, par16, 1.0, cutSurface);
                this.generateCaveNode(caveRand.nextLong(), par3, par4, blockArray, par6, par8, par10, caveRand.nextFloat() * 0.5f + 0.5f, angle + 1.5707964f, par14 / 3.0f, par15, par16, 1.0, cutSurface);
                return;
            }
            if (var24 || caveRand.nextInt(4) != 0) {
                final double var31 = par6 - var19;
                final double var32 = par10 - var20;
                final double var33 = par16 - par15;
                final double var34 = par12 + 2.0f + 16.0f;
                if (var31 * var31 + var32 * var32 - var33 * var33 > var34 * var34) {
                    return;
                }
                if (par6 >= var19 - 16.0 - var27 * 2.0 && par10 >= var20 - 16.0 - var27 * 2.0 && par6 <= var19 + 16.0 + var27 * 2.0 && par10 <= var20 + 16.0 + var27 * 2.0) {
                    int var35 = MathHelper.floor_double(par6 - var27) - par3 * 16 - 1;
                    int var36 = MathHelper.floor_double(par6 + var27) - par3 * 16 + 1;
                    int var37 = MathHelper.floor_double(par8 - var28) - 1;
                    int var38 = MathHelper.floor_double(par8 + var28) + 1;
                    int var39 = MathHelper.floor_double(par10 - var27) - par4 * 16 - 1;
                    int var40 = MathHelper.floor_double(par10 + var27) - par4 * 16 + 1;
                    var35 = Math.max(var35, 0);
                    var36 = Math.min(var36, 16);
                    var37 = Math.max(var37, 1);
                    var38 = Math.min(var38, 248);
                    var39 = Math.max(var39, 0);
                    var40 = Math.min(var40, 16);
                    boolean anyWater = false;
                    for (int var41 = var35; !anyWater && var41 < var36; ++var41) {
                        for (int var42 = var39; !anyWater && var42 < var40; ++var42) {
                            for (int var43 = var38 + 1; !anyWater && var43 >= var37 - 1; --var43) {
                                final int var44 = (var41 * 16 + var42) * 256 + var43;
                                if (var43 >= 0 && var43 < 256) {
                                    if (blockArray[var44] == Blocks.flowing_water || blockArray[var44] == Blocks.water) {
                                        anyWater = true;
                                    }
                                    if (var43 != var37 - 1 && var41 != var35 && var41 != var36 - 1 && var42 != var39 && var42 != var40 - 1) {
                                        var43 = var37;
                                    }
                                }
                            }
                        }
                    }
                    if (!anyWater) {
                        for (int var41 = var35; var41 < var36; ++var41) {
                            final double var45 = (var41 + par3 * 16 + 0.5 - par6) / var27;
                            for (int var44 = var39; var44 < var40; ++var44) {
                                final double var46 = (var44 + par4 * 16 + 0.5 - par10) / var27;
                                final int xzIndex = var41 * 16 + var44;
                                int blockIndex = xzIndex * 256 + (var37 + 1);
                                if (var45 * var45 + var46 * var46 < 1.0) {
                                    for (int var47 = var37; var47 <= var38 - 1; ++var47) {
                                        final double var48 = (var47 + 0.5 - par8) / var28;
                                        if (var48 > -0.7 && var45 * var45 + var48 * var48 + var46 * var46 < 1.0) {
                                            final LOTRBiome biome = (LOTRBiome)super.worldObj.getBiomeGenForCoords(var41 + par3 * 16, var44 + par4 * 16);
                                            this.digBlock(blockArray, blockIndex, xzIndex, var41, var47, var44, par3, par4, biome, cutSurface);
                                        }
                                        ++blockIndex;
                                    }
                                }
                            }
                        }
                        if (var24) {
                            break;
                        }
                    }
                }
            }
            ++par15;
        }
    }
    
    protected void digBlock(final Block[] blockArray, final int index, final int xzIndex, final int i, final int j, final int k, final int chunkX, final int chunkZ, final LOTRBiome biome, final boolean cutSurface) {
        final Block block = blockArray[index];
        boolean isTop = false;
        boolean belowVillageOrRoad = false;
        final int topCheckDepth = 1;
        if (j >= 59 - topCheckDepth) {
            isTop = true;
            for (int checkAboveMax = 5, j2 = topCheckDepth + 1; j2 <= topCheckDepth + checkAboveMax && j + j2 <= 255; ++j2) {
                if (blockArray[index + j2].isOpaqueCube()) {
                    isTop = false;
                    break;
                }
            }
        }
        if (this.chunkFlags.isVillage || this.chunkFlags.roadFlags[xzIndex]) {
            final int roadDepth = 4;
            if (j >= 59 - roadDepth) {
                belowVillageOrRoad = true;
                for (int checkAboveMax2 = 5, j3 = roadDepth + 1; j3 <= roadDepth + checkAboveMax2 && j + j3 <= 255; ++j3) {
                    if (blockArray[index + j3].isOpaqueCube()) {
                        belowVillageOrRoad = false;
                        break;
                    }
                }
            }
        }
        boolean dig = isTerrainBlock(block, biome) || block.getMaterial().isLiquid();
        if (belowVillageOrRoad) {
            dig = false;
        }
        if (isTop && (!cutSurface || this.chunkFlags.isVillage)) {
            dig = false;
        }
        if (dig) {
            if (j < 10) {
                blockArray[index] = Blocks.lava;
            }
            else {
                blockArray[index] = Blocks.air;
                if (isTop) {
                    for (int grassCheckMax = 5, j3 = 1; j3 <= grassCheckMax; ++j3) {
                        if (j - j3 <= 0) {
                            break;
                        }
                        if (blockArray[index - j3] == biome.fillerBlock) {
                            blockArray[index - j3] = biome.topBlock;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    protected int caveRarity() {
        return 10;
    }
    
    protected int getCaveGenerationHeight() {
        return super.rand.nextInt(super.rand.nextInt(120) + 8);
    }
    
    protected void func_151538_a(final World world, final int i, final int k, final int chunkX, final int chunkZ, final Block[] blocks) {
        int caves = super.rand.nextInt(super.rand.nextInt(super.rand.nextInt(40) + 1) + 1);
        if (super.rand.nextInt(this.caveRarity()) != 0) {
            caves = 0;
        }
        for (int l = 0; l < caves; ++l) {
            final int i2 = i * 16 + super.rand.nextInt(16);
            final int j1 = this.getCaveGenerationHeight();
            final int k2 = k * 16 + super.rand.nextInt(16);
            final boolean cutSurface = super.rand.nextInt(5) == 0;
            int nodes = 1;
            if (super.rand.nextInt(4) == 0) {
                this.generateLargeCaveNode(super.rand.nextLong(), chunkX, chunkZ, blocks, i2, j1, k2, cutSurface);
                nodes += super.rand.nextInt(4);
            }
            for (int n = 0; n < nodes; ++n) {
                final float angle = super.rand.nextFloat() * 3.1415927f * 2.0f;
                final float var18 = (super.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float size = super.rand.nextFloat() * 2.0f + super.rand.nextFloat();
                if (super.rand.nextInt(10) == 0) {
                    size *= super.rand.nextFloat() * super.rand.nextFloat() * 3.0f + 1.0f;
                }
                this.generateCaveNode(super.rand.nextLong(), chunkX, chunkZ, blocks, i2, j1, k2, size, angle, var18, 0, 0, 1.0, cutSurface);
            }
        }
    }
    
    public static boolean isTerrainBlock(final Block block, final BiomeGenBase biome) {
        return block == biome.topBlock || block == biome.fillerBlock || (block == Blocks.grass || block == Blocks.dirt || block == Blocks.sand || block == LOTRMod.whiteSand || block == Blocks.gravel || block == LOTRMod.mudGrass || block == LOTRMod.mud) || block == LOTRMod.dirtPath || (block == Blocks.stone || block == LOTRMod.rock || block == Blocks.sandstone || block == LOTRMod.redSandstone || block == LOTRMod.whiteSandstone) || (block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel);
    }
}
