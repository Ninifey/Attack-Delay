// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenGnarledOak extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int minHeight;
    private int maxHeight;
    
    public LOTRWorldGenGnarledOak(final boolean flag) {
        super(flag);
        this.woodBlock = Blocks.log;
        this.woodMeta = 0;
        this.leafBlock = (Block)Blocks.leaves;
        this.leafMeta = 0;
        this.minHeight = 4;
        this.maxHeight = 9;
    }
    
    public LOTRWorldGenGnarledOak setBlocks(final Block b1, final int m1, final Block b2, final int m2) {
        this.woodBlock = b1;
        this.woodMeta = m1;
        this.leafBlock = b2;
        this.leafMeta = m2;
        return this;
    }
    
    public LOTRWorldGenGnarledOak setMinMaxHeight(final int min, final int max) {
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
        if (!flag) {
            return false;
        }
        boolean canGrow = true;
        Block below = world.getBlock(i, j - 1, k);
        if (!below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            canGrow = false;
        }
        if (!canGrow) {
            return false;
        }
        below = world.getBlock(i, j - 1, k);
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        for (int j3 = j; j3 < j + height; ++j3) {
            this.func_150516_a(world, i, j3, k, this.woodBlock, this.woodMeta);
        }
        this.generateLeaves(world, random, i, j + height, k);
        for (int branches = 2 + random.nextInt(3), b = 0; b < branches; ++b) {
            final float angle = random.nextFloat() * 3.1415927f * 2.0f;
            final float cos = MathHelper.cos(angle);
            final float sin = MathHelper.sin(angle);
            final float angleY = random.nextFloat() * (float)Math.toRadians(40.0);
            final float cosY = MathHelper.cos(angleY);
            final float sinY = MathHelper.sin(angleY);
            final int length = 2 + random.nextInt(3);
            int i3 = i;
            int k3 = k;
            int j4 = j + height - 1 - random.nextInt(3);
            if (j4 < j + 2) {
                j4 = j + 2;
            }
            for (int l = 0; l < length; ++l) {
                if (Math.floor(cos * l) != Math.floor(cos * (l - 1))) {
                    i3 += (int)Math.signum(cos);
                }
                if (Math.floor(sin * l) != Math.floor(sin * (l - 1))) {
                    k3 += (int)Math.signum(sin);
                }
                if (Math.floor(sinY * l) != Math.floor(sinY * (l - 1))) {
                    j4 += (int)Math.signum(sinY);
                }
                if (!this.isReplaceable(world, i3, j4, k3)) {
                    break;
                }
                this.func_150516_a(world, i3, j4, k3, this.woodBlock, this.woodMeta | 0xC);
            }
            this.generateLeaves(world, random, i3, j4, k3);
        }
        int lastDir = -1;
        for (int j5 = j + 2; j5 < j + height; ++j5) {
            if (random.nextInt(3) == 0) {
                final int dir = random.nextInt(4);
                if (dir != lastDir) {
                    lastDir = dir;
                    for (int length2 = 1, m = 1; m <= length2; ++m) {
                        final int i4 = i + Direction.offsetX[dir] * m;
                        final int k4 = k + Direction.offsetZ[dir] * m;
                        final Block block = world.getBlock(i4, j5, k4);
                        if (!block.isReplaceable((IBlockAccess)world, i4, j5, k4) && !block.isLeaves((IBlockAccess)world, i4, j5, k4)) {
                            break;
                        }
                        if (dir == 0 || dir == 2) {
                            this.func_150516_a(world, i4, j5, k4, this.woodBlock, this.woodMeta | 0x8);
                        }
                        else {
                            this.func_150516_a(world, i4, j5, k4, this.woodBlock, this.woodMeta | 0x4);
                        }
                    }
                }
            }
        }
        for (int i5 = i - 1; i5 <= i + 1; ++i5) {
            for (int k5 = k - 1; k5 <= k + 1; ++k5) {
                if (i5 != i || k5 != k) {
                    if (random.nextInt(4) <= 0) {
                        final int rootX = i5;
                        int rootY = j + random.nextInt(2);
                        final int rootZ = k5;
                        int roots = 0;
                        while (world.getBlock(rootX, rootY, k5).isReplaceable((IBlockAccess)world, rootX, rootY, rootZ)) {
                            this.func_150516_a(world, rootX, rootY, rootZ, this.woodBlock, this.woodMeta | 0xC);
                            world.getBlock(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
                            --rootY;
                            if (++roots > 4 + random.nextInt(3)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private void generateLeaves(final World world, final Random random, final int i, final int j, final int k) {
        final int leafRange = 3;
        final int leafRangeSq = leafRange * leafRange;
        final int leafRangeSqLess = (int)((leafRange - 0.5) * (leafRange - 0.5));
        for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
            for (int j2 = j - leafRange + 1; j2 <= j + leafRange; ++j2) {
                for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                    final int i3 = i2 - i;
                    final int j3 = j2 - j;
                    final int k3 = k2 - k;
                    final int dist = i3 * i3 + j3 * j3 + k3 * k3;
                    if (dist < leafRangeSqLess || (dist < leafRangeSq && random.nextInt(3) == 0)) {
                        final Block block = world.getBlock(i2, j2, k2);
                        if (block.isReplaceable((IBlockAccess)world, i2, j2, k2) || block.isLeaves((IBlockAccess)world, i2, j2, k2)) {
                            this.func_150516_a(world, i2, j2, k2, this.leafBlock, this.leafMeta);
                        }
                    }
                }
            }
        }
    }
}
