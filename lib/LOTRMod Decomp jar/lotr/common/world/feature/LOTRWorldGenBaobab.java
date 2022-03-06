// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.util.MathHelper;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenBaobab extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenBaobab(final boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.wood4;
        this.woodMeta = 1;
        this.leafBlock = LOTRMod.leaves4;
        this.leafMeta = 1;
    }
    
    public boolean generate(final World world, final Random random, int i, final int j, int k) {
        final int trunkCircleWidth = 4;
        final int height = 16 + random.nextInt(10);
        int xSlope = 5 + random.nextInt(10);
        if (random.nextBoolean()) {
            xSlope *= -1;
        }
        int zSlope = 5 + random.nextInt(10);
        if (random.nextBoolean()) {
            zSlope *= -1;
        }
        boolean flag = true;
        if (j >= 1 && j + height + 5 <= 256) {
            for (int i2 = i - trunkCircleWidth - 1; i2 <= i + trunkCircleWidth + 1 && flag; ++i2) {
                for (int k2 = k - trunkCircleWidth - 1; k2 <= k + trunkCircleWidth + 1 && flag; ++k2) {
                    final int i3 = Math.abs(i2 - i);
                    final int k3 = Math.abs(k2 - k);
                    if (i3 * i3 + k3 * k3 <= trunkCircleWidth * trunkCircleWidth) {
                        for (int j2 = j; j2 <= j + 1 + height; ++j2) {
                            if (j2 >= 0 && j2 < 256) {
                                final Block block = world.getBlock(i2, j2, k2);
                                if (!this.isReplaceable(world, i2, j2, k2) && !block.isReplaceable((IBlockAccess)world, i2, j2, k2)) {
                                    flag = false;
                                }
                            }
                            else {
                                flag = false;
                            }
                        }
                        final Block below = world.getBlock(i2, j - 1, k2);
                        final boolean isSoil = below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling);
                        if (!isSoil) {
                            flag = false;
                        }
                    }
                }
            }
        }
        else {
            flag = false;
        }
        if (!flag) {
            return false;
        }
        for (int j3 = 0; j3 < height; ++j3) {
            for (int i4 = i - trunkCircleWidth - 1; i4 <= i + trunkCircleWidth + 1; ++i4) {
                for (int k4 = k - trunkCircleWidth - 1; k4 <= k + trunkCircleWidth + 1; ++k4) {
                    final int i5 = Math.abs(i4 - i);
                    final int k5 = Math.abs(k4 - k);
                    if (i5 * i5 + k5 * k5 <= trunkCircleWidth * trunkCircleWidth) {
                        if (j3 == 0) {
                            world.getBlock(i4, j - 1, k4).onPlantGrow(world, i4, j - 1, k4, i4, j, k4);
                        }
                        this.func_150516_a(world, i4, j + j3, k4, this.woodBlock, this.woodMeta);
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
        for (int j3 = j + height - 1; j3 > j + (int)(height * 0.75f); --j3) {
            for (int branches = 2 + random.nextInt(3), l = 0; l < branches; ++l) {
                final float angle = random.nextFloat() * 3.1415927f * 2.0f;
                int i6 = i;
                int k6 = k;
                int j4 = j3;
                for (int length = MathHelper.getRandomIntegerInRange(random, 4, 6), l2 = trunkCircleWidth; l2 < trunkCircleWidth + length; ++l2) {
                    i6 = i + (int)(1.5f + MathHelper.cos(angle) * l2);
                    k6 = k + (int)(1.5f + MathHelper.sin(angle) * l2);
                    j4 = j3 - 3 + l2 / 2;
                    if (!this.isReplaceable(world, i6, j4, k6)) {
                        break;
                    }
                    this.func_150516_a(world, i6, j4, k6, this.woodBlock, this.woodMeta);
                }
                final int leafMin = 1 + random.nextInt(2);
                for (int j5 = j4 - leafMin; j5 <= j4; ++j5) {
                    final int leafRange = 1 - (j5 - j4);
                    this.spawnLeaves(world, i6, j5, k6, leafRange);
                }
            }
        }
        for (int i2 = i - trunkCircleWidth - 1; i2 <= i + trunkCircleWidth + 1; ++i2) {
            for (int k2 = k - trunkCircleWidth - 1; k2 <= k + trunkCircleWidth + 1; ++k2) {
                final int i3 = Math.abs(i2 - i);
                final int k3 = Math.abs(k2 - k);
                if (i3 * i3 + k3 * k3 <= trunkCircleWidth * trunkCircleWidth && random.nextInt(5) == 0) {
                    int j2 = j + height;
                    for (int topHeight = 2 + random.nextInt(3), m = 0; m < topHeight; ++m) {
                        this.func_150516_a(world, i2, j2, k2, this.woodBlock, this.woodMeta);
                        ++j2;
                    }
                    final int leafMin2 = 2;
                    for (int j6 = j2 - leafMin2; j6 <= j2; ++j6) {
                        final int leafRange2 = 1 - (j6 - j2);
                        this.spawnLeaves(world, i2, j6, k2, leafRange2);
                    }
                }
            }
        }
        return true;
    }
    
    private void spawnLeaves(final World world, final int i, final int j, final int k, final int leafRange) {
        final int leafRangeSq = leafRange * leafRange;
        for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
            for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                final int i3 = i2 - i;
                final int k3 = k2 - k;
                if (i3 * i3 + k3 * k3 <= leafRangeSq) {
                    final Block block = world.getBlock(i2, j, k2);
                    if (block.isReplaceable((IBlockAccess)world, i2, j, k2) || block.isLeaves((IBlockAccess)world, i2, j, k2)) {
                        this.func_150516_a(world, i2, j, k2, this.leafBlock, this.leafMeta);
                    }
                }
            }
        }
    }
}
