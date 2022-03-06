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

public class LOTRWorldGenPine extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int minHeight;
    private int maxHeight;
    
    public LOTRWorldGenPine(final boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.wood5;
        this.woodMeta = 0;
        this.leafBlock = LOTRMod.leaves5;
        this.leafMeta = 0;
        this.minHeight = 12;
        this.maxHeight = 24;
    }
    
    public LOTRWorldGenPine setMinMaxHeight(final int min, final int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        boolean flag = true;
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
        final Block below = world.getBlock(i, j - 1, k);
        final boolean isSoil = below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling);
        if (!isSoil) {
            flag = false;
        }
        if (!flag) {
            return false;
        }
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        this.func_150516_a(world, i, j + height, k, this.leafBlock, this.leafMeta);
        this.generateLeafLayer(world, random, i, j + height - 1, k, 1);
        int leafHeight = j + height - 3;
        final int minLeafHeight = j + (int)(height * 0.5f);
        while (leafHeight > minLeafHeight) {
            final int r = random.nextInt(3);
            if (r == 0) {
                this.generateLeafLayer(world, random, i, leafHeight, k, 1);
                leafHeight -= 2;
            }
            else if (r == 1) {
                --leafHeight;
                this.generateLeafLayer(world, random, i, leafHeight + 1, k, 1);
                this.generateLeafLayer(world, random, i, leafHeight, k, 2);
                this.generateLeafLayer(world, random, i, leafHeight - 1, k, 1);
                leafHeight -= 3;
            }
            else {
                if (r != 2) {
                    continue;
                }
                --leafHeight;
                this.generateLeafLayer(world, random, i, leafHeight + 1, k, 2);
                this.generateLeafLayer(world, random, i, leafHeight, k, 3);
                this.generateLeafLayer(world, random, i, leafHeight - 1, k, 2);
                leafHeight -= 3;
            }
        }
        this.generateLeafLayer(world, random, i, leafHeight, k, 1);
        int lastDir = -1;
        for (int j3 = j; j3 < j + height; ++j3) {
            this.func_150516_a(world, i, j3, k, this.woodBlock, this.woodMeta);
            if (j3 >= j + 3 && j3 < minLeafHeight && random.nextInt(3) == 0) {
                final int dir = random.nextInt(4);
                if (dir != lastDir) {
                    lastDir = dir;
                    for (int length = 1, l = 1; l <= length; ++l) {
                        final int i3 = i + Direction.offsetX[dir] * l;
                        final int k3 = k + Direction.offsetZ[dir] * l;
                        if (!this.isReplaceable(world, i3, j3, k3)) {
                            break;
                        }
                        if (dir == 0 || dir == 2) {
                            this.func_150516_a(world, i3, j3, k3, this.woodBlock, this.woodMeta | 0x8);
                        }
                        else {
                            this.func_150516_a(world, i3, j3, k3, this.woodBlock, this.woodMeta | 0x4);
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private void generateLeafLayer(final World world, final Random random, final int i, final int j, final int k, final int range) {
        for (int i2 = i - range; i2 <= i + range; ++i2) {
            for (int k2 = k - range; k2 <= k + range; ++k2) {
                final int i3 = Math.abs(i2 - i);
                final int k3 = Math.abs(k2 - k);
                if (i3 + k3 <= range) {
                    final Block block = world.getBlock(i2, j, k2);
                    if (block.isReplaceable((IBlockAccess)world, i2, j, k2) || block.isLeaves((IBlockAccess)world, i2, j, k2)) {
                        this.func_150516_a(world, i2, j, k2, this.leafBlock, this.leafMeta);
                    }
                }
            }
        }
    }
}
