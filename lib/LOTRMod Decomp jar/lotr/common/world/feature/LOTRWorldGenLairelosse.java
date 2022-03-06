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

public class LOTRWorldGenLairelosse extends WorldGenAbstractTree
{
    private int minHeight;
    private int maxHeight;
    private int extraTrunk;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenLairelosse(final boolean flag) {
        super(flag);
        this.minHeight = 5;
        this.maxHeight = 8;
        this.extraTrunk = 0;
        this.woodBlock = LOTRMod.wood7;
        this.woodMeta = 2;
        this.leafBlock = LOTRMod.leaves7;
        this.leafMeta = 2;
    }
    
    public LOTRWorldGenLairelosse setMinMaxHeight(final int min, final int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }
    
    public LOTRWorldGenLairelosse setExtraTrunkWidth(final int i) {
        this.extraTrunk = i;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        final int leafStart = j + 1 + this.extraTrunk + random.nextInt(3);
        final int leafTop = j + height + 1;
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
        if (canGrow) {
            for (int i3 = i; i3 <= i + this.extraTrunk; ++i3) {
                for (int k3 = k; k3 <= k + this.extraTrunk; ++k3) {
                    final Block below = world.getBlock(i3, j - 1, k3);
                    below.onPlantGrow(world, i3, j - 1, k3, i3, j, k3);
                }
            }
            int leafRange = 0;
            final int maxRange = 2;
            for (int j3 = leafTop; j3 >= leafStart; --j3) {
                if (j3 >= leafTop - 1) {
                    leafRange = 0;
                }
                else if (++leafRange > 2) {
                    leafRange = 1;
                }
                for (int i4 = i - maxRange; i4 <= i + this.extraTrunk + maxRange; ++i4) {
                    for (int k4 = k - maxRange; k4 <= k + this.extraTrunk + maxRange; ++k4) {
                        int i5 = Math.abs(i4 - i);
                        int k5 = Math.abs(k4 - k);
                        if (i4 > i) {
                            i5 -= this.extraTrunk;
                        }
                        if (k4 > k) {
                            k5 -= this.extraTrunk;
                        }
                        if (i5 + k5 <= leafRange) {
                            final Block block = world.getBlock(i4, j3, k4);
                            if (block.isReplaceable((IBlockAccess)world, i4, j3, k4) || block.isLeaves((IBlockAccess)world, i4, j3, k4)) {
                                this.func_150516_a(world, i4, j3, k4, this.leafBlock, this.leafMeta);
                            }
                        }
                    }
                }
            }
            for (int j3 = j; j3 < j + height; ++j3) {
                for (int i4 = i; i4 <= i + this.extraTrunk; ++i4) {
                    for (int k4 = k; k4 <= k + this.extraTrunk; ++k4) {
                        this.func_150516_a(world, i4, j3, k4, this.woodBlock, this.woodMeta);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
