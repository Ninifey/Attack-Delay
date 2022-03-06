// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenDesertTrees extends WorldGenAbstractTree
{
    private boolean isNatural;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenDesertTrees(final boolean flag, final Block b1, final int m1, final Block b2, final int m2) {
        super(flag);
        this.isNatural = !flag;
        this.woodBlock = b1;
        this.woodMeta = m1;
        this.leafBlock = b2;
        this.leafMeta = m2;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = 3 + random.nextInt(3);
        boolean flag = true;
        if (!this.isNatural) {
            if (j >= 1 && height + 1 <= 256) {
                for (int j2 = j; j2 <= j + height + 1; ++j2) {
                    int range = 1;
                    if (j2 == j) {
                        range = 0;
                    }
                    if (j2 >= j + height - 1) {
                        range = 2;
                    }
                    for (int i2 = i - range; i2 <= i + range && flag; ++i2) {
                        for (int k2 = k - range; k2 <= k + range && flag; ++k2) {
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
            }
            else {
                flag = false;
            }
        }
        final Block below = world.getBlock(i, j - 1, k);
        final boolean isSoil = below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling) || (this.isNatural && (below == Blocks.sand || below == Blocks.stone));
        if (!isSoil) {
            flag = false;
        }
        if (!flag) {
            return false;
        }
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        for (int branch = 0; branch < 4; ++branch) {
            final int branchLength = 1 + random.nextInt(3);
            int i3 = i;
            int j3 = j + height - 1 - random.nextInt(2);
            int k3 = k;
            for (int l = 0; l < branchLength; ++l) {
                if (random.nextInt(3) != 0) {
                    ++j3;
                }
                if (random.nextInt(3) != 0) {
                    switch (branch) {
                        case 0: {
                            --i3;
                            break;
                        }
                        case 1: {
                            ++k3;
                            break;
                        }
                        case 2: {
                            ++i3;
                            break;
                        }
                        case 3: {
                            --k3;
                            break;
                        }
                    }
                }
                if (!this.isReplaceable(world, i3, j3, k3)) {
                    break;
                }
                this.func_150516_a(world, i3, j3, k3, this.woodBlock, this.woodMeta);
            }
            final byte leafStart = 1;
            final byte leafRangeMin = 0;
            for (int j4 = j3 - leafStart; j4 <= j3 + 1; ++j4) {
                final int j5 = j4 - j3;
                for (int leafRange = leafRangeMin + 1 - j5 / 2, i4 = i3 - leafRange; i4 <= i3 + leafRange; ++i4) {
                    final int i5 = i4 - i3;
                    for (int k4 = k3 - leafRange; k4 <= k3 + leafRange; ++k4) {
                        final int k5 = k4 - k3;
                        if (Math.abs(i5) != leafRange || Math.abs(k5) != leafRange || (random.nextInt(2) != 0 && j5 != 0)) {
                            final Block block = world.getBlock(i4, j4, k4);
                            if (block.isReplaceable((IBlockAccess)world, i4, j4, k4) || block.isLeaves((IBlockAccess)world, i4, j4, k4)) {
                                this.func_150516_a(world, i4, j4, k4, this.leafBlock, this.leafMeta);
                            }
                        }
                    }
                }
            }
        }
        for (int j6 = j; j6 < j + height; ++j6) {
            this.func_150516_a(world, i, j6, k, this.woodBlock, this.woodMeta);
        }
        return true;
    }
}
