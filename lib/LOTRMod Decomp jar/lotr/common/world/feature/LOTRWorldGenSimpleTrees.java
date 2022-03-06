// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenSimpleTrees extends WorldGenAbstractTree
{
    private int minHeight;
    private int maxHeight;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int extraTrunkWidth;
    
    public LOTRWorldGenSimpleTrees(final boolean flag, final int i, final int j, final Block k, final int l, final Block i1, final int j1) {
        super(flag);
        this.minHeight = i;
        this.maxHeight = j;
        this.woodBlock = k;
        this.woodMeta = l;
        this.leafBlock = i1;
        this.leafMeta = j1;
    }
    
    public LOTRWorldGenSimpleTrees setTrunkWidth(final int i) {
        this.extraTrunkWidth = i - 1;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        boolean flag = true;
        if (j < 1 || j + height + 1 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + 1 + height; ++j2) {
            int range = 1;
            if (j2 == j) {
                range = 0;
            }
            if (j2 >= j + 1 + height - 2) {
                range = 2;
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
                if (!block.canSustainPlant((IBlockAccess)world, i3, j - 1, k3, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
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
            final byte leafStart = 3;
            final byte leafRangeMin = 0;
            for (int j3 = j - leafStart + height; j3 <= j + height; ++j3) {
                final int j4 = j3 - (j + height);
                for (int leafRange = leafRangeMin + 1 - j4 / 2, i4 = i - leafRange; i4 <= i + leafRange + this.extraTrunkWidth; ++i4) {
                    for (int k4 = k - leafRange; k4 <= k + leafRange + this.extraTrunkWidth; ++k4) {
                        int i5 = i4 - i;
                        int k5 = k4 - k;
                        if (i5 > 0) {
                            i5 -= this.extraTrunkWidth;
                        }
                        if (k5 > 0) {
                            k5 -= this.extraTrunkWidth;
                        }
                        final Block block2 = world.getBlock(i4, j3, k4);
                        if ((Math.abs(i5) != leafRange || Math.abs(k5) != leafRange || (random.nextInt(2) != 0 && j4 != 0)) && (block2.isReplaceable((IBlockAccess)world, i4, j3, k4) || block2.isLeaves((IBlockAccess)world, i4, j3, k4))) {
                            this.func_150516_a(world, i4, j3, k4, this.leafBlock, this.leafMeta);
                        }
                    }
                }
            }
            for (int j3 = j; j3 < j + height; ++j3) {
                for (int i6 = i; i6 <= i + this.extraTrunkWidth; ++i6) {
                    for (int k6 = k; k6 <= k + this.extraTrunkWidth; ++k6) {
                        final Block block3 = world.getBlock(i6, j3, k6);
                        if (block3.isReplaceable((IBlockAccess)world, i6, j3, k6) || block3.isLeaves((IBlockAccess)world, i6, j3, k6)) {
                            this.func_150516_a(world, i6, j3, k6, this.woodBlock, this.woodMeta);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
