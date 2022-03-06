// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenHolly extends WorldGenAbstractTree
{
    private int extraTrunkWidth;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenHolly(final boolean flag) {
        super(flag);
        this.extraTrunkWidth = 0;
        this.woodBlock = LOTRMod.wood2;
        this.woodMeta = 2;
        this.leafBlock = LOTRMod.leaves2;
        this.leafMeta = 2;
    }
    
    public LOTRWorldGenHolly setLarge() {
        this.extraTrunkWidth = 1;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        int height = 9 + random.nextInt(6);
        if (this.extraTrunkWidth > 0) {
            height += 10 + random.nextInt(4);
        }
        boolean flag = true;
        if (j < 1 || j + height + 1 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + 1 + height; ++j2) {
            int range = 1;
            if (j2 == j) {
                range = 0;
            }
            if (j2 > j + 2 && j2 < j + height - 2) {
                range = 2;
                if (this.extraTrunkWidth > 0) {
                    ++range;
                }
            }
            for (int i2 = i - range; i2 <= i + range + this.extraTrunkWidth && flag; ++i2) {
                for (int k2 = k - range; k2 <= k + range + this.extraTrunkWidth && flag; ++k2) {
                    if (j2 >= 0 && j2 < 256) {
                        if (!this.isReplaceable(world, i2, j2, k2)) {
                            flag = false;
                        }
                    }
                    else {
                        flag = false;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        }
        boolean flag2 = true;
        for (int i3 = i; i3 <= i + this.extraTrunkWidth && flag2; ++i3) {
            for (int k3 = k; k3 <= k + this.extraTrunkWidth && flag2; ++k3) {
                final Block block = world.getBlock(i3, j - 1, k3);
                if (!block.canSustainPlant((IBlockAccess)world, i3, j - 1, k3, ForgeDirection.UP, (IPlantable)LOTRMod.sapling2)) {
                    flag2 = false;
                }
            }
        }
        if (flag2) {
            for (int i3 = i; i3 <= i + this.extraTrunkWidth; ++i3) {
                for (int k3 = k; k3 <= k + this.extraTrunkWidth; ++k3) {
                    world.getBlock(i3, j - 1, k3).onPlantGrow(world, i3, j - 1, k3, i3, j, k3);
                }
            }
            for (int leafStop = 2 + random.nextInt(2), j3 = height; j3 > leafStop; --j3) {
                if (j3 == height) {
                    for (int i4 = 0; i4 <= this.extraTrunkWidth; ++i4) {
                        for (int k4 = 0; k4 <= this.extraTrunkWidth; ++k4) {
                            this.growLeaves(world, i + i4, j + j3, k + k4);
                        }
                    }
                }
                else if (j3 > height - 3 || j3 == leafStop + 1) {
                    for (int i4 = -1; i4 <= 1 + this.extraTrunkWidth; ++i4) {
                        for (int k4 = -1; k4 <= 1 + this.extraTrunkWidth; ++k4) {
                            int i5 = i4;
                            if (i5 > 0) {
                                i5 -= this.extraTrunkWidth;
                            }
                            int k5 = k4;
                            if (k5 > 0) {
                                k5 -= this.extraTrunkWidth;
                            }
                            if (j3 != height - 1 || Math.abs(i5) != 1 || Math.abs(k5) != 1) {
                                this.growLeaves(world, i + i4, j + j3, k + k4);
                            }
                        }
                    }
                }
                else {
                    for (int i4 = -3; i4 <= 3 + this.extraTrunkWidth; ++i4) {
                        for (int k4 = -3; k4 <= 3 + this.extraTrunkWidth; ++k4) {
                            int i5 = i4;
                            if (i5 > 0) {
                                i5 -= this.extraTrunkWidth;
                            }
                            int k5 = k4;
                            if (k5 > 0) {
                                k5 -= this.extraTrunkWidth;
                            }
                            if ((j3 % 2 == 0 || Math.abs(i5) != 2 || Math.abs(k5) != 2) && ((Math.abs(i5) < 3 && Math.abs(k5) < 3) || (this.extraTrunkWidth > 0 && j3 % 2 == 0 && (i5 == 0 || k5 == 0)))) {
                                this.growLeaves(world, i + i4, j + j3, k + k4);
                            }
                        }
                    }
                }
            }
            for (int j3 = 0; j3 < height; ++j3) {
                for (int i4 = 0; i4 <= this.extraTrunkWidth; ++i4) {
                    for (int k4 = 0; k4 <= this.extraTrunkWidth; ++k4) {
                        final Block block2 = world.getBlock(i + i4, j + j3, k + k4);
                        if (this.isReplaceable(world, i + i4, j + j3, k + k4)) {
                            this.func_150516_a(world, i + i4, j + j3, k + k4, this.woodBlock, this.woodMeta);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void growLeaves(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);
        if (block.isReplaceable((IBlockAccess)world, i, j, k) || block.isLeaves((IBlockAccess)world, i, j, k)) {
            this.func_150516_a(world, i, j, k, this.leafBlock, this.leafMeta);
        }
    }
}
