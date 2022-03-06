// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenPartyTrees extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private boolean restrictions;
    
    public LOTRWorldGenPartyTrees(final Block block, final int i, final Block block1, final int j) {
        super(false);
        this.restrictions = true;
        this.woodBlock = block;
        this.woodMeta = i;
        this.leafBlock = block1;
        this.leafMeta = j;
    }
    
    public LOTRWorldGenPartyTrees disableRestrictions() {
        this.restrictions = false;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int trunkWidth = 1;
        final int height = random.nextInt(12) + 12;
        boolean flag = true;
        if (this.restrictions) {
            if (j < 1 || j + height + 1 > 256) {
                return false;
            }
            for (int j2 = j; j2 <= j + 1 + height; ++j2) {
                int range = trunkWidth + 1;
                if (j2 == j) {
                    range = trunkWidth;
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
            for (int i3 = i - trunkWidth; i3 <= i + trunkWidth && flag; ++i3) {
                for (int k3 = k - trunkWidth; k3 <= k + trunkWidth && flag; ++k3) {
                    final Block block = world.getBlock(i3, j - 1, k3);
                    if (!block.canSustainPlant((IBlockAccess)world, i3, j - 1, k3, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
                        flag = false;
                    }
                }
            }
            if (!flag) {
                return false;
            }
        }
        for (int i3 = i - trunkWidth; i3 <= i + trunkWidth; ++i3) {
            for (int k3 = k - trunkWidth; k3 <= k + trunkWidth; ++k3) {
                world.getBlock(i3, j - 1, k3).onPlantGrow(world, i3, j - 1, k3, i3, j, k3);
            }
        }
        for (int j2 = 0; j2 < height; ++j2) {
            for (int i4 = i - trunkWidth; i4 <= i + trunkWidth; ++i4) {
                for (int k4 = k - trunkWidth; k4 <= k + trunkWidth; ++k4) {
                    this.func_150516_a(world, i4, j + j2, k4, this.woodBlock, this.woodMeta);
                }
            }
        }
        int angle = 0;
        while (angle < 360) {
            angle += 20 + random.nextInt(25);
            final float angleR = angle / 180.0f * 3.1415927f;
            final float sin = MathHelper.sin(angleR);
            final float cos = MathHelper.cos(angleR);
            final int boughLength = 6 + random.nextInt(6);
            final int boughThickness = Math.round(boughLength / 20.0f * 1.5f);
            final int boughBaseHeight = j + MathHelper.floor_double((double)(height * (0.75f + random.nextFloat() * 0.25f)));
            final int boughHeight = 3 + random.nextInt(4);
            for (int l = 0; l < boughLength; ++l) {
                final int i5 = i + Math.round(sin * l);
                final int k5 = k + Math.round(cos * l);
                final int j3 = boughBaseHeight + Math.round(l / (float)boughLength * boughHeight);
                for (int range2 = boughThickness - Math.round(l / (float)boughLength * boughThickness * 0.5f), i6 = i5 - range2; i6 <= i5 + range2; ++i6) {
                    for (int j4 = j3 - range2; j4 <= j3 + range2; ++j4) {
                        for (int k6 = k5 - range2; k6 <= k5 + range2; ++k6) {
                            final Block block2 = world.getBlock(i6, j4, k6);
                            if (block2.isReplaceable((IBlockAccess)world, i6, j4, k6) || block2.isLeaves((IBlockAccess)world, i6, j4, k6)) {
                                this.func_150516_a(world, i6, j4, k6, this.woodBlock, this.woodMeta | 0xC);
                            }
                        }
                    }
                }
                final int branch_angle = angle + random.nextInt(360);
                final float branch_angleR = branch_angle / 180.0f * 3.1415927f;
                final float branch_sin = MathHelper.sin(branch_angleR);
                final float branch_cos = MathHelper.cos(branch_angleR);
                final int branchLength = 4 + random.nextInt(4);
                final int branchHeight = random.nextInt(5);
                final int leafRange = 3;
                for (int l2 = 0; l2 < branchLength; ++l2) {
                    final int i7 = i5 + Math.round(branch_sin * l2);
                    final int k7 = k5 + Math.round(branch_cos * l2);
                    int j6;
                    int j5;
                    for (j5 = (j6 = j3 + Math.round(l2 / (float)branchLength * branchHeight)); j6 >= j5 - 1; --j6) {
                        final Block block3 = world.getBlock(i7, j6, k7);
                        if (block3.isReplaceable((IBlockAccess)world, i7, j6, k7) || block3.isLeaves((IBlockAccess)world, i7, j6, k7)) {
                            this.func_150516_a(world, i7, j6, k7, this.woodBlock, this.woodMeta | 0xC);
                        }
                    }
                    if (l2 == branchLength - 1) {
                        for (int i8 = i7 - leafRange; i8 <= i7 + leafRange; ++i8) {
                            for (int j7 = j5 - leafRange; j7 <= j5 + leafRange; ++j7) {
                                for (int k8 = k7 - leafRange; k8 <= k7 + leafRange; ++k8) {
                                    final int i9 = i8 - i7;
                                    final int j8 = j7 - j5;
                                    final int k9 = k8 - k7;
                                    final int dist = i9 * i9 + j8 * j8 + k9 * k9;
                                    if (dist < (leafRange - 1) * (leafRange - 1) || (dist < leafRange * leafRange && random.nextInt(3) != 0)) {
                                        final Block block4 = world.getBlock(i8, j7, k8);
                                        if (block4.isReplaceable((IBlockAccess)world, i8, j7, k8) || block4.isLeaves((IBlockAccess)world, i8, j7, k8)) {
                                            this.func_150516_a(world, i8, j7, k8, this.leafBlock, this.leafMeta);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int roots = 5 + random.nextInt(5), m = 0; m < roots; ++m) {
            int i10 = i;
            int j9 = j + 1 + random.nextInt(5);
            int k10 = k;
            int xDirection = 0;
            int zDirection = 0;
            final int rootLength = 2 + random.nextInt(4);
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    i10 -= trunkWidth + 1;
                    xDirection = -1;
                }
                else {
                    i10 += trunkWidth + 1;
                    xDirection = 1;
                }
                k10 -= trunkWidth + 1;
                k10 += random.nextInt(trunkWidth * 2 + 2);
            }
            else {
                if (random.nextBoolean()) {
                    k10 -= trunkWidth + 1;
                    zDirection = -1;
                }
                else {
                    k10 += trunkWidth + 1;
                    zDirection = 1;
                }
                i10 -= trunkWidth + 1;
                i10 += random.nextInt(trunkWidth * 2 + 2);
            }
            for (int l3 = 0; l3 < rootLength; ++l3) {
                int rootBlocks = 0;
                for (int j10 = j9; !world.getBlock(i10, j10, k10).isOpaqueCube(); --j10) {
                    final Block block5 = world.getBlock(i10, j10, k10);
                    if (!block5.isReplaceable((IBlockAccess)world, i10, j10, k10) && !block5.isLeaves((IBlockAccess)world, i10, j10, k10)) {
                        break;
                    }
                    this.func_150516_a(world, i10, j10, k10, this.woodBlock, this.woodMeta | 0xC);
                    world.getBlock(i10, j10 - 1, k10).onPlantGrow(world, i10, j10 - 1, k10, i10, j10, k10);
                    if (++rootBlocks > 5) {
                        break;
                    }
                }
                --j9;
                if (random.nextBoolean()) {
                    if (xDirection == -1) {
                        --i10;
                    }
                    else if (xDirection == 1) {
                        ++i10;
                    }
                    else if (zDirection == -1) {
                        --k10;
                    }
                    else if (zDirection == 1) {
                        ++k10;
                    }
                }
            }
        }
        return true;
    }
}
