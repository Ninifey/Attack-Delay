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

public class LOTRWorldGenRedwood extends WorldGenAbstractTree
{
    private int trunkWidth;
    private int extraTrunkWidth;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenRedwood(final boolean flag) {
        super(flag);
        this.trunkWidth = 0;
        this.extraTrunkWidth = 0;
        this.woodBlock = LOTRMod.wood8;
        this.woodMeta = 1;
        this.leafBlock = LOTRMod.leaves8;
        this.leafMeta = 1;
    }
    
    public LOTRWorldGenRedwood setTrunkWidth(final int i) {
        this.trunkWidth = i;
        return this;
    }
    
    public LOTRWorldGenRedwood setExtraTrunkWidth(final int i) {
        this.extraTrunkWidth = i;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int fullWidth = 1 + this.extraTrunkWidth + this.trunkWidth * 2;
        int height = fullWidth * MathHelper.getRandomIntegerInRange(random, 15, 20);
        if (fullWidth > 1) {
            height += (fullWidth - 1) * MathHelper.getRandomIntegerInRange(random, 0, 8);
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
            for (int i2 = i - this.trunkWidth - range; i2 <= i + this.trunkWidth + this.extraTrunkWidth + range && flag; ++i2) {
                for (int k2 = k - this.trunkWidth - range; k2 <= k + this.trunkWidth + this.extraTrunkWidth + range && flag; ++k2) {
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
        for (int i3 = i - this.trunkWidth; i3 <= i + this.trunkWidth + this.extraTrunkWidth && canGrow; ++i3) {
            for (int k3 = k - this.trunkWidth; k3 <= k + this.trunkWidth + this.extraTrunkWidth && canGrow; ++k3) {
                final Block block = world.getBlock(i3, j - 1, k3);
                if (!block.canSustainPlant((IBlockAccess)world, i3, j - 1, k3, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
                    canGrow = false;
                }
            }
        }
        if (canGrow) {
            for (int i3 = i - this.trunkWidth; i3 <= i + this.trunkWidth + this.extraTrunkWidth; ++i3) {
                for (int k3 = k - this.trunkWidth; k3 <= k + this.trunkWidth + this.extraTrunkWidth; ++k3) {
                    world.getBlock(i3, j - 1, k3).onPlantGrow(world, i3, j - 1, k3, i3, j, k3);
                }
            }
            int narrowHeight = -1;
            if (fullWidth > 3) {
                narrowHeight = j + (int)(height * MathHelper.randomFloatClamp(random, 0.3f, 0.4f));
            }
            final int leafStart = j + (int)(height * MathHelper.randomFloatClamp(random, 0.45f, 0.6f));
            final int leafTop = j + height + 1;
            int leafRange = 0;
            final int maxRange = 2;
            boolean increasing = true;
            for (int j3 = leafTop; j3 >= leafStart; --j3) {
                if (j3 >= leafTop - 1) {
                    leafRange = 0;
                }
                else if (increasing) {
                    if (++leafRange >= 3) {
                        increasing = false;
                    }
                }
                else if (--leafRange <= 1) {
                    increasing = true;
                }
                leafRange = Math.min(leafRange, 4);
                int trunkWidthHere = this.trunkWidth;
                if (narrowHeight > -1 && j3 >= narrowHeight) {
                    --trunkWidthHere;
                }
                for (int i4 = i - trunkWidthHere - maxRange; i4 <= i + trunkWidthHere + this.extraTrunkWidth + maxRange; ++i4) {
                    for (int k4 = k - trunkWidthHere - maxRange; k4 <= k + trunkWidthHere + this.extraTrunkWidth + maxRange; ++k4) {
                        int i5 = Math.abs(i4 - i);
                        int k5 = Math.abs(k4 - k);
                        i5 -= trunkWidthHere;
                        k5 -= trunkWidthHere;
                        if (i4 > i) {
                            i5 -= this.extraTrunkWidth;
                        }
                        if (k4 > k) {
                            k5 -= this.extraTrunkWidth;
                        }
                        int d = i5 + k5;
                        if (j3 < leafTop - 2) {
                            d += random.nextInt(2);
                        }
                        if (d <= leafRange) {
                            final Block block2 = world.getBlock(i4, j3, k4);
                            if (block2.isReplaceable((IBlockAccess)world, i4, j3, k4) || block2.isLeaves((IBlockAccess)world, i4, j3, k4)) {
                                this.func_150516_a(world, i4, j3, k4, this.leafBlock, this.leafMeta);
                            }
                        }
                    }
                }
            }
            for (int j3 = 0; j3 < height; ++j3) {
                int trunkWidthHere = this.trunkWidth;
                if (narrowHeight > -1 && j + j3 >= narrowHeight) {
                    --trunkWidthHere;
                }
                for (int i4 = -trunkWidthHere; i4 <= trunkWidthHere + this.extraTrunkWidth; ++i4) {
                    for (int k4 = -trunkWidthHere; k4 <= trunkWidthHere + this.extraTrunkWidth; ++k4) {
                        int i5 = Math.abs(i4);
                        int k5 = Math.abs(k4);
                        if (i4 > 0) {
                            i5 -= this.extraTrunkWidth;
                        }
                        if (k4 > 0) {
                            k5 -= this.extraTrunkWidth;
                        }
                        final int i6 = i + i4;
                        final int j4 = j + j3;
                        final int k6 = k + k4;
                        if (narrowHeight <= -1 || j4 >= narrowHeight || j4 <= j + 15 || j4 >= leafStart || i5 != trunkWidthHere || k5 != trunkWidthHere) {
                            final Block block3 = world.getBlock(i6, j4, k6);
                            if (this.isReplaceable(world, i6, j4, k6)) {
                                this.func_150516_a(world, i6, j4, k6, this.woodBlock, this.woodMeta);
                            }
                        }
                    }
                }
            }
            for (int i7 = i - this.trunkWidth - 1; i7 <= i + this.trunkWidth + this.extraTrunkWidth + 1; ++i7) {
                for (int k7 = k - this.trunkWidth - 1; k7 <= k + this.trunkWidth + this.extraTrunkWidth + 1; ++k7) {
                    int i8 = Math.abs(i7 - i);
                    int k8 = Math.abs(k7 - k);
                    i8 -= this.trunkWidth;
                    k8 -= this.trunkWidth;
                    if (i7 > i) {
                        i8 -= this.extraTrunkWidth;
                    }
                    if (k7 > k) {
                        k8 -= this.extraTrunkWidth;
                    }
                    if ((i8 == 1 || k8 == 1) && i8 != k8) {
                        final int rootX = i7;
                        int rootY = j + fullWidth / 2 + random.nextInt(2 + fullWidth / 2);
                        final int rootZ = k7;
                        while (world.getBlock(rootX, rootY, k7).isReplaceable((IBlockAccess)world, rootX, rootY, rootZ)) {
                            this.func_150516_a(world, rootX, rootY, rootZ, this.woodBlock, this.woodMeta | 0xC);
                            world.getBlock(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
                            if (--rootY < j - 3 - random.nextInt(3)) {
                                break;
                            }
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
