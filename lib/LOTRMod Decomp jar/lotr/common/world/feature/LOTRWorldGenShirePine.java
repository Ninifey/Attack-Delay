// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.util.Direction;
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

public class LOTRWorldGenShirePine extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int minHeight;
    private int maxHeight;
    
    public LOTRWorldGenShirePine(final boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.wood;
        this.woodMeta = 0;
        this.leafBlock = LOTRMod.leaves;
        this.leafMeta = 0;
        this.minHeight = 10;
        this.maxHeight = 20;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        final int leafHeight = 6 + random.nextInt(4);
        final int minLeafHeight = j + height - leafHeight;
        final int maxLeafWidth = 2 + random.nextInt(2);
        boolean flag = true;
        if (j < 1 || j + height + 1 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + 1 + height && flag; ++j2) {
            int checkRange = 0;
            if (j2 < minLeafHeight) {
                checkRange = 0;
            }
            else {
                checkRange = maxLeafWidth;
            }
            for (int i2 = i - checkRange; i2 <= i + checkRange && flag; ++i2) {
                for (int k2 = k - checkRange; k2 <= k + checkRange && flag; ++k2) {
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
        final Block below = world.getBlock(i, j - 1, k);
        if (below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            below.onPlantGrow(world, i, j - 1, k, i, j, k);
            int leafWidth = random.nextInt(2);
            int leafWidthLimit = 1;
            int nextLeafWidth = 0;
            for (int j3 = j + height; j3 >= minLeafHeight; --j3) {
                for (int i3 = i - leafWidth; i3 <= i + leafWidth; ++i3) {
                    for (int k3 = k - leafWidth; k3 <= k + leafWidth; ++k3) {
                        final int i4 = i3 - i;
                        final int k4 = k3 - k;
                        if (leafWidth <= 0 || Math.abs(i4) != leafWidth || Math.abs(k4) != leafWidth) {
                            final Block block = world.getBlock(i3, j3, k3);
                            if (block.isReplaceable((IBlockAccess)world, i3, j3, k3) || block.isLeaves((IBlockAccess)world, i3, j3, k3)) {
                                this.func_150516_a(world, i3, j3, k3, this.leafBlock, this.leafMeta);
                            }
                        }
                    }
                }
                if (leafWidth >= leafWidthLimit) {
                    leafWidth = nextLeafWidth;
                    nextLeafWidth = 1;
                    if (++leafWidthLimit > maxLeafWidth) {
                        leafWidthLimit = maxLeafWidth;
                    }
                }
                else {
                    ++leafWidth;
                }
            }
            int lastDir = -1;
            for (int j4 = j; j4 < j + height; ++j4) {
                this.func_150516_a(world, i, j4, k, this.woodBlock, this.woodMeta);
                if (j4 >= j + 3 && j4 < minLeafHeight && random.nextInt(3) == 0) {
                    final int dir = random.nextInt(4);
                    if (dir != lastDir) {
                        lastDir = dir;
                        for (int length = 1, l = 1; l <= length; ++l) {
                            final int i5 = i + Direction.offsetX[dir] * l;
                            final int k5 = k + Direction.offsetZ[dir] * l;
                            if (!this.isReplaceable(world, i5, j4, k5)) {
                                break;
                            }
                            if (dir == 0 || dir == 2) {
                                this.func_150516_a(world, i5, j4, k5, this.woodBlock, this.woodMeta | 0x8);
                            }
                            else {
                                this.func_150516_a(world, i5, j4, k5, this.woodBlock, this.woodMeta | 0x4);
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
