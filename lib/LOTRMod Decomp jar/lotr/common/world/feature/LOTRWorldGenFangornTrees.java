// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenFangornTrees extends WorldGenAbstractTree
{
    private Block woodID;
    private int woodMeta;
    private Block leafID;
    private int leafMeta;
    private boolean generateLeaves;
    private boolean restrictions;
    private float heightFactor;
    
    public LOTRWorldGenFangornTrees(final boolean flag, final Block i, final int j, final Block k, final int l) {
        super(flag);
        this.generateLeaves = true;
        this.restrictions = true;
        this.heightFactor = 1.0f;
        this.woodID = i;
        this.woodMeta = j;
        this.leafID = k;
        this.leafMeta = l;
    }
    
    public LOTRWorldGenFangornTrees disableRestrictions() {
        this.restrictions = false;
        return this;
    }
    
    public LOTRWorldGenFangornTrees setNoLeaves() {
        this.generateLeaves = false;
        return this;
    }
    
    public LOTRWorldGenFangornTrees setHeightFactor(final float f) {
        this.heightFactor = f;
        return this;
    }
    
    public boolean generate(final World world, final Random random, int i, final int j, int k) {
        if (this.restrictions) {
            final Block below = world.getBlock(i, j - 1, k);
            final boolean isSoil = below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling);
            if (!isSoil) {
                return false;
            }
        }
        final float f = 0.5f + random.nextFloat() * 0.5f;
        final int height = (int)(f * 40.0f * this.heightFactor);
        final int trunkRadiusMin = (int)(f * 5.0f);
        final int trunkRadiusMax = trunkRadiusMin + 4;
        int xSlope = 4 + random.nextInt(7);
        if (random.nextBoolean()) {
            xSlope *= -1;
        }
        int zSlope = 4 + random.nextInt(7);
        if (random.nextBoolean()) {
            zSlope *= -1;
        }
        if (this.restrictions) {
            boolean flag = true;
            if (j < 1 || j + height + 5 > 256) {
                return false;
            }
            for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                for (int k2 = k - 1; k2 <= k + 1; ++k2) {
                    for (int j2 = j; j2 <= j + height; ++j2) {
                        for (int width = trunkRadiusMax, i3 = i2 - width; i3 <= i2 + width && flag; ++i3) {
                            for (int k3 = k2 - width; k3 <= k2 + width && flag; ++k3) {
                                if (j2 >= 0 && j2 < 256) {
                                    if (!this.isReplaceable(world, i3, j2, k3)) {
                                        flag = false;
                                    }
                                }
                                else {
                                    flag = false;
                                }
                            }
                        }
                    }
                }
            }
            if (!flag) {
                return false;
            }
        }
        for (int j3 = 0; j3 < height; ++j3) {
            for (int width2 = trunkRadiusMax - (int)(j3 / (float)height * (trunkRadiusMax - trunkRadiusMin)), i4 = i - width2; i4 <= i + width2; ++i4) {
                for (int k4 = k - width2; k4 <= k + width2; ++k4) {
                    final int i5 = i4 - i;
                    final int k5 = k4 - k;
                    if (i5 * i5 + k5 * k5 < width2 * width2) {
                        final Block block = world.getBlock(i4, j + j3, k4);
                        if (block == Blocks.air || block.isLeaves((IBlockAccess)world, i4, j + j3, k4)) {
                            this.func_150516_a(world, i4, j + j3, k4, this.woodID, this.woodMeta);
                        }
                        if (j3 == 0) {
                            world.getBlock(i4, j - 1, k4).onPlantGrow(world, i4, j - 1, k4, i4, j, k4);
                            for (int j4 = j - 1; !LOTRMod.isOpaque(world, i4, j4, k4) && j4 >= 0; --j4) {
                                if (Math.abs(j4 - j) > 6 + random.nextInt(5)) {
                                    break;
                                }
                                this.func_150516_a(world, i4, j4, k4, this.woodID, this.woodMeta);
                                world.getBlock(i4, j4 - 1, k4).onPlantGrow(world, i4, j4 - 1, k4, i4, j4, k4);
                            }
                        }
                    }
                }
            }
            if (j3 % xSlope == 0) {
                if (xSlope > 0) {
                    ++i;
                }
                else if (xSlope < 0) {
                    --i;
                }
            }
            if (j3 % zSlope == 0) {
                if (zSlope > 0) {
                    ++k;
                }
                else if (zSlope < 0) {
                    --k;
                }
            }
        }
        int angle = 0;
        while (angle < 360) {
            angle += 10 + random.nextInt(20);
            final float angleR = angle / 180.0f * 3.1415927f;
            final float sin = MathHelper.sin(angleR);
            final float cos = MathHelper.cos(angleR);
            final int boughLength = 12 + random.nextInt(10);
            final int boughThickness = Math.round(boughLength / 25.0f * 1.5f);
            final int boughBaseHeight = j + MathHelper.floor_double((double)(height * (0.9f + random.nextFloat() * 0.1f)));
            final int boughHeight = 3 + random.nextInt(4);
            for (int l = 0; l < boughLength; ++l) {
                final int i6 = i + Math.round(sin * l);
                final int k6 = k + Math.round(cos * l);
                final int j5 = boughBaseHeight + Math.round(l / (float)boughLength * boughHeight);
                for (int range = boughThickness - Math.round(l / (float)boughLength * boughThickness * 0.5f), i7 = i6 - range; i7 <= i6 + range; ++i7) {
                    for (int j6 = j5 - range; j6 <= j5 + range; ++j6) {
                        for (int k7 = k6 - range; k7 <= k6 + range; ++k7) {
                            final Block block2 = world.getBlock(i7, j6, k7);
                            if (block2.isReplaceable((IBlockAccess)world, i7, j6, k7) || block2.isLeaves((IBlockAccess)world, i7, j6, k7)) {
                                this.func_150516_a(world, i7, j6, k7, this.woodID, this.woodMeta | 0xC);
                            }
                        }
                    }
                }
                final int branch_angle = angle + random.nextInt(360);
                final float branch_angleR = branch_angle / 180.0f * 3.1415927f;
                final float branch_sin = MathHelper.sin(branch_angleR);
                final float branch_cos = MathHelper.cos(branch_angleR);
                final int branchLength = 7 + random.nextInt(6);
                final int branchHeight = random.nextInt(6);
                final int leafRange = 3;
                for (int l2 = 0; l2 < branchLength; ++l2) {
                    final int i8 = i6 + Math.round(branch_sin * l2);
                    final int k8 = k6 + Math.round(branch_cos * l2);
                    int j8;
                    int j7;
                    for (j7 = (j8 = j5 + Math.round(l2 / (float)branchLength * branchHeight)); j8 >= j7 - 1; --j8) {
                        final Block block3 = world.getBlock(i8, j8, k8);
                        if (block3.isReplaceable((IBlockAccess)world, i8, j8, k8) || block3.isLeaves((IBlockAccess)world, i8, j8, k8)) {
                            this.func_150516_a(world, i8, j8, k8, this.woodID, this.woodMeta | 0xC);
                        }
                    }
                    if (this.generateLeaves && l2 == branchLength - 1) {
                        for (int i9 = i8 - leafRange; i9 <= i8 + leafRange; ++i9) {
                            for (int j9 = j7 - leafRange; j9 <= j7 + leafRange; ++j9) {
                                for (int k9 = k8 - leafRange; k9 <= k8 + leafRange; ++k9) {
                                    final int i10 = i9 - i8;
                                    final int j10 = j9 - j7;
                                    final int k10 = k9 - k8;
                                    final int dist = i10 * i10 + j10 * j10 + k10 * k10;
                                    if (dist < (leafRange - 1) * (leafRange - 1) || (dist < leafRange * leafRange && random.nextInt(3) != 0)) {
                                        final Block block4 = world.getBlock(i9, j9, k9);
                                        if (block4.getMaterial() == Material.air || block4.isLeaves((IBlockAccess)world, i9, j9, k9)) {
                                            this.func_150516_a(world, i9, j9, k9, this.leafID, this.leafMeta);
                                            if (random.nextInt(40) == 0 && world.isAirBlock(i9 - 1, j9, k9)) {
                                                this.growVines(world, random, i9 - 1, j9, k9, 8);
                                            }
                                            if (random.nextInt(40) == 0 && world.isAirBlock(i9 + 1, j9, k9)) {
                                                this.growVines(world, random, i9 + 1, j9, k9, 2);
                                            }
                                            if (random.nextInt(40) == 0 && world.isAirBlock(i9, j9, k9 - 1)) {
                                                this.growVines(world, random, i9, j9, k9 - 1, 1);
                                            }
                                            if (random.nextInt(40) == 0 && world.isAirBlock(i9, j9, k9 + 1)) {
                                                this.growVines(world, random, i9, j9, k9 + 1, 4);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private void growVines(final World world, final Random random, final int i, int j, final int k, final int meta) {
        this.func_150516_a(world, i, j, k, Blocks.vine, meta);
        int length = 4 + random.nextInt(12);
        while (true) {
            --j;
            if (!world.isAirBlock(i, j, k) || length <= 0) {
                break;
            }
            this.func_150516_a(world, i, j, k, Blocks.vine, meta);
            --length;
        }
    }
}
