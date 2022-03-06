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

public class LOTRWorldGenAspen extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int minHeight;
    private int maxHeight;
    private int extraTrunk;
    
    public LOTRWorldGenAspen(final boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.wood7;
        this.woodMeta = 0;
        this.leafBlock = LOTRMod.leaves7;
        this.leafMeta = 0;
        this.minHeight = 8;
        this.maxHeight = 15;
        this.extraTrunk = 0;
    }
    
    public LOTRWorldGenAspen setMinMaxHeight(final int min, final int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }
    
    public LOTRWorldGenAspen setBlocks(final Block b1, final int m1, final Block b2, final int m2) {
        this.woodBlock = b1;
        this.woodMeta = m1;
        this.leafBlock = b2;
        this.leafMeta = m2;
        return this;
    }
    
    public LOTRWorldGenAspen setExtraTrunkWidth(final int w) {
        this.extraTrunk = w;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        int leafMin = 3 + random.nextInt(3);
        leafMin = j + leafMin - 1;
        final int leafTop = j + height + 1;
        boolean flag = true;
        if (j >= 1 && height + 1 <= 256) {
            for (int j2 = j; j2 <= j + height + 1; ++j2) {
                int range = 1;
                if (j2 == j) {
                    range = 0;
                }
                if (j2 >= leafMin) {
                    range = 2;
                }
                for (int i2 = i - range; i2 <= i + this.extraTrunk + range && flag; ++i2) {
                    for (int k2 = k - range; k2 <= k + this.extraTrunk + range && flag; ++k2) {
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
        if (!flag) {
            return false;
        }
        boolean canGrow = true;
        for (int i3 = i; i3 <= i + this.extraTrunk && canGrow; ++i3) {
            for (int k3 = k; k3 <= k + this.extraTrunk && canGrow; ++k3) {
                final Block below = world.getBlock(i3, j - 1, k3);
                if (!below.canSustainPlant((IBlockAccess)world, i3, j - 1, k3, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
                    canGrow = false;
                }
            }
        }
        if (!canGrow) {
            return false;
        }
        for (int i3 = i; i3 <= i + this.extraTrunk; ++i3) {
            for (int k3 = k; k3 <= k + this.extraTrunk; ++k3) {
                final Block below = world.getBlock(i3, j - 1, k3);
                below.onPlantGrow(world, i3, j - 1, k3, i3, j, k3);
            }
        }
        for (int j3 = leafMin; j3 <= leafTop; ++j3) {
            int leafWidth = 2;
            if (j3 >= leafTop - 1) {
                leafWidth = 0;
            }
            else if (j3 >= leafTop - 3 || j3 <= leafMin + 1 || random.nextInt(4) == 0) {
                leafWidth = 1;
            }
            for (int branches = 4 + random.nextInt(5), b = 0; b < branches; ++b) {
                int i4 = i;
                int k4 = k;
                if (this.extraTrunk > 0) {
                    i4 += random.nextInt(this.extraTrunk + 1);
                    k4 += random.nextInt(this.extraTrunk + 1);
                }
                int i5 = i4;
                int k5 = k4;
                int length = 4 + random.nextInt(8);
                length *= this.extraTrunk + 1;
                for (int l = 0; l < length && Math.abs(i5 - i4) <= leafWidth; ++l) {
                    if (Math.abs(k5 - k4) > leafWidth) {
                        break;
                    }
                    final Block block = world.getBlock(i5, j3, k5);
                    if (!block.isReplaceable((IBlockAccess)world, i5, j3, k5) && !block.isLeaves((IBlockAccess)world, i5, j3, k5)) {
                        break;
                    }
                    this.func_150516_a(world, i5, j3, k5, this.leafBlock, this.leafMeta);
                    final int dir = random.nextInt(4);
                    if (dir == 0) {
                        --i5;
                    }
                    else if (dir == 1) {
                        ++i5;
                    }
                    else if (dir == 2) {
                        --k5;
                    }
                    else if (dir == 3) {
                        ++k5;
                    }
                }
            }
        }
        for (int j3 = j; j3 < j + height; ++j3) {
            for (int i2 = i; i2 <= i + this.extraTrunk; ++i2) {
                for (int k2 = k; k2 <= k + this.extraTrunk; ++k2) {
                    this.func_150516_a(world, i2, j3, k2, this.woodBlock, this.woodMeta);
                }
            }
        }
        return true;
    }
}
