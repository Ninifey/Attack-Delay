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
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenAlmond extends WorldGenAbstractTree
{
    private int minHeight;
    private int maxHeight;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenAlmond(final boolean flag) {
        super(flag);
        this.minHeight = 4;
        this.maxHeight = 5;
        this.woodBlock = LOTRMod.wood7;
        this.woodMeta = 3;
        this.leafBlock = LOTRMod.leaves7;
        this.leafMeta = 3;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        final int leafStart = j + height - 3;
        final int leafTop = j + height;
        boolean flag = true;
        if (j < 1 || j + height + 1 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + height + 1; ++j2) {
            int range = 1;
            if (j2 == j) {
                range = 0;
            }
            if (j2 >= leafStart) {
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
        if (!flag) {
            return false;
        }
        boolean canGrow = true;
        Block below = world.getBlock(i, j - 1, k);
        if (!below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            canGrow = false;
        }
        if (canGrow) {
            below = world.getBlock(i, j - 1, k);
            below.onPlantGrow(world, i, j - 1, k, i, j, k);
            for (int j3 = leafStart; j3 <= leafTop; ++j3) {
                int leafRange = 0;
                final int maxRange = 2;
                final int j4 = leafTop - j3;
                if (j4 == 0) {
                    leafRange = 1;
                }
                else if (j4 == 1) {
                    leafRange = 2;
                }
                else if (j4 == 2) {
                    leafRange = 3;
                }
                else {
                    leafRange = 1;
                }
                for (int i3 = i - maxRange; i3 <= i + maxRange; ++i3) {
                    for (int k3 = k - maxRange; k3 <= k + maxRange; ++k3) {
                        final int i4 = Math.abs(i3 - i);
                        final int k4 = Math.abs(k3 - k);
                        if (i4 + k4 <= leafRange) {
                            final Block block = world.getBlock(i3, j3, k3);
                            if (block.isReplaceable((IBlockAccess)world, i3, j3, k3) || block.isLeaves((IBlockAccess)world, i3, j3, k3)) {
                                this.func_150516_a(world, i3, j3, k3, this.leafBlock, this.leafMeta);
                            }
                        }
                    }
                }
            }
            for (int j3 = j; j3 < j + height; ++j3) {
                final Block block2 = world.getBlock(i, j3, k);
                if (block2.isReplaceable((IBlockAccess)world, i, j3, k) || block2.isLeaves((IBlockAccess)world, i, j3, k)) {
                    this.func_150516_a(world, i, j3, k, this.woodBlock, this.woodMeta);
                }
            }
            return true;
        }
        return false;
    }
}
