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

public class LOTRWorldGenOlive extends WorldGenAbstractTree
{
    private int minHeight;
    private int maxHeight;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int extraTrunk;
    
    public LOTRWorldGenOlive(final boolean flag) {
        super(flag);
        this.minHeight = 4;
        this.maxHeight = 5;
        this.woodBlock = LOTRMod.wood6;
        this.woodMeta = 3;
        this.leafBlock = LOTRMod.leaves6;
        this.leafMeta = 3;
        this.extraTrunk = 0;
    }
    
    public LOTRWorldGenOlive setMinMaxHeight(final int min, final int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }
    
    public LOTRWorldGenOlive setBlocks(final Block b1, final int m1, final Block b2, final int m2) {
        this.woodBlock = b1;
        this.woodMeta = m1;
        this.leafBlock = b2;
        this.leafMeta = m2;
        return this;
    }
    
    public LOTRWorldGenOlive setExtraTrunkWidth(final int w) {
        this.extraTrunk = w;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        final int leafStart = j + height - 3 + random.nextInt(2);
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
            for (int j3 = leafStart; j3 <= leafTop; ++j3) {
                int leafRange = 0;
                if (j3 == leafTop) {
                    leafRange = 2;
                }
                else if (j3 == leafStart) {
                    leafRange = 1;
                }
                else {
                    leafRange = 3;
                }
                for (int i4 = i - leafRange; i4 <= i + this.extraTrunk + leafRange; ++i4) {
                    for (int k4 = k - leafRange; k4 <= k + this.extraTrunk + leafRange; ++k4) {
                        int i5 = Math.abs(i4 - i);
                        int k5 = Math.abs(k4 - k);
                        if (this.extraTrunk > 0) {
                            if (i4 > i) {
                                i5 -= this.extraTrunk;
                            }
                            if (k4 > k) {
                                k5 -= this.extraTrunk;
                            }
                        }
                        final int dCh = i5 + k5;
                        if (dCh <= 4 && ((i5 < leafRange && k5 < leafRange) || random.nextInt(3) != 0)) {
                            final Block block = world.getBlock(i4, j3, k4);
                            if (block.isReplaceable((IBlockAccess)world, i4, j3, k4) || block.isLeaves((IBlockAccess)world, i4, j3, k4)) {
                                this.func_150516_a(world, i4, j3, k4, this.leafBlock, this.leafMeta);
                            }
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
            if (this.extraTrunk > 0) {
                for (int i3 = i - 1; i3 <= i + this.extraTrunk + 1; ++i3) {
                    for (int k3 = k - 1; k3 <= k + this.extraTrunk + 1; ++k3) {
                        int i6 = Math.abs(i3 - i);
                        int k6 = Math.abs(k3 - k);
                        if (this.extraTrunk > 0) {
                            if (i3 > i) {
                                i6 -= this.extraTrunk;
                            }
                            if (k3 > k) {
                                k6 -= this.extraTrunk;
                            }
                        }
                        if (random.nextInt(4) == 0) {
                            final int rootX = i3;
                            int rootY = j + random.nextInt(2);
                            final int rootZ = k3;
                            int roots = 0;
                            while (world.getBlock(rootX, rootY, k3).isReplaceable((IBlockAccess)world, rootX, rootY, rootZ)) {
                                this.func_150516_a(world, rootX, rootY, rootZ, this.woodBlock, this.woodMeta | 0xC);
                                world.getBlock(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
                                --rootY;
                                if (++roots > 4 + random.nextInt(3)) {
                                    break;
                                }
                            }
                        }
                        if (random.nextInt(4) == 0 && (i6 == 0 || k6 == 0)) {
                            final int j4 = leafStart;
                            final Block block2 = world.getBlock(i3, j4, k3);
                            if (block2.isReplaceable((IBlockAccess)world, i3, j4, k3) || block2.isLeaves((IBlockAccess)world, i3, j4, k3)) {
                                this.func_150516_a(world, i3, j4, k3, this.woodBlock, this.woodMeta);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
