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

public class LOTRWorldGenCedar extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int minHeight;
    private int maxHeight;
    private boolean hangingLeaves;
    
    public LOTRWorldGenCedar(final boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.wood4;
        this.woodMeta = 2;
        this.leafBlock = LOTRMod.leaves4;
        this.leafMeta = 2;
        this.minHeight = 10;
        this.maxHeight = 16;
        this.hangingLeaves = false;
    }
    
    public LOTRWorldGenCedar setMinMaxHeight(final int min, final int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }
    
    public LOTRWorldGenCedar setBlocks(final Block b1, final int m1, final Block b2, final int m2) {
        this.woodBlock = b1;
        this.woodMeta = m1;
        this.leafBlock = b2;
        this.leafMeta = m2;
        return this;
    }
    
    public LOTRWorldGenCedar setHangingLeaves() {
        this.hangingLeaves = true;
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
        int j3;
        for (int canopyMin = j3 = j + height - 2; j3 <= j + height; ++j3) {
            final int leafRange = 2 - (j3 - (j + height));
            this.spawnLeaves(world, random, i, j3, k, leafRange);
            if (j3 == canopyMin) {
                for (int i3 = i - 1; i3 <= i + 1; ++i3) {
                    for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                        if (i3 == i || k3 == k) {
                            final Block block = world.getBlock(i3, j3, k3);
                            if (block.isReplaceable((IBlockAccess)world, i3, j3, k3) || block.isLeaves((IBlockAccess)world, i3, j3, k3)) {
                                this.func_150516_a(world, i3, j3, k3, this.woodBlock, this.woodMeta);
                            }
                        }
                    }
                }
            }
        }
        for (j3 = j + height - 1; j3 > j + height / 2; j3 -= 1 + random.nextInt(3)) {
            final int branches = 1 + random.nextInt(3);
            int l = 0;
        Label_0457:
            while (l < branches) {
                final float angle = random.nextFloat() * 3.1415927f * 2.0f;
                int i4 = i;
                int k4 = k;
                int j4 = j3;
                final int length = MathHelper.getRandomIntegerInRange(random, 4, 7);
                final int leafMin = 1 + random.nextInt(2);
                while (true) {
                    for (int l2 = 0; l2 < length; ++l2) {
                        i4 = i + (int)(0.5f + MathHelper.cos(angle) * (l2 + 1));
                        k4 = k + (int)(0.5f + MathHelper.sin(angle) * (l2 + 1));
                        j4 = j3 - 3 + l2 / 2;
                        final Block block2 = world.getBlock(i4, j4, k4);
                        if (!block2.isReplaceable((IBlockAccess)world, i4, j4, k4) && !block2.isWood((IBlockAccess)world, i4, j4, k4) && !block2.isLeaves((IBlockAccess)world, i4, j4, k4)) {
                            ++l;
                            continue Label_0457;
                        }
                        this.func_150516_a(world, i4, j4, k4, this.woodBlock, this.woodMeta);
                        if (l2 == length - 1 && leafMin >= 2) {
                            for (int i5 = i4 - 1; i5 <= i4 + 1; ++i5) {
                                for (int k5 = k4 - 1; k5 <= k4 + 1; ++k5) {
                                    if (i5 == i4 || k5 == k4) {
                                        final int j5 = j4 - 1;
                                        final Block block3 = world.getBlock(i5, j5, k5);
                                        if (block3.isReplaceable((IBlockAccess)world, i5, j5, k5) || block3.isLeaves((IBlockAccess)world, i5, j5, k5)) {
                                            this.func_150516_a(world, i5, j5, k5, this.woodBlock, this.woodMeta);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (int j6 = j4 - leafMin; j6 <= j4; ++j6) {
                        final int leafRange2 = 1 - (j6 - j4);
                        this.spawnLeaves(world, random, i4, j6, k4, leafRange2);
                    }
                    continue;
                }
            }
        }
        for (j3 = 0; j3 < height; ++j3) {
            this.func_150516_a(world, i, j + j3, k, this.woodBlock, this.woodMeta);
        }
        for (int i6 = i - 1; i6 <= i + 1; ++i6) {
            for (int k6 = k - 1; k6 <= k + 1; ++k6) {
                final int i7 = i6 - i;
                final int k7 = k6 - k;
                if (Math.abs(i7) != Math.abs(k7)) {
                    final int rootX = i6;
                    int rootY = j + random.nextInt(2);
                    final int rootZ = k6;
                    int roots = 0;
                    while (world.getBlock(rootX, rootY, k6).isReplaceable((IBlockAccess)world, rootX, rootY, rootZ)) {
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
        return true;
    }
    
    private void spawnLeaves(final World world, final Random random, final int i, final int j, final int k, final int leafRange) {
        final int leafRangeSq = leafRange * leafRange;
        for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
            for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                final int i3 = i2 - i;
                final int k3 = k2 - k;
                if (i3 * i3 + k3 * k3 <= leafRangeSq) {
                    final Block block = world.getBlock(i2, j, k2);
                    if (block.isReplaceable((IBlockAccess)world, i2, j, k2) || block.isLeaves((IBlockAccess)world, i2, j, k2)) {
                        this.func_150516_a(world, i2, j, k2, this.leafBlock, this.leafMeta);
                        if (this.hangingLeaves && random.nextInt(10) == 0) {
                            this.func_150516_a(world, i2, j, k2, this.woodBlock, this.woodMeta);
                            final Block block2 = world.getBlock(i2, j + 1, k2);
                            if (block2.isReplaceable((IBlockAccess)world, i2, j + 1, k2) || block2.isLeaves((IBlockAccess)world, i2, j + 1, k2)) {
                                this.func_150516_a(world, i2, j + 1, k2, this.leafBlock, this.leafMeta);
                            }
                            for (int hang = 2 + random.nextInt(3), j2 = j - 1; j2 >= j - hang; --j2) {
                                final Block block3 = world.getBlock(i2, j2, k2);
                                if (block3.isReplaceable((IBlockAccess)world, i2, j2, k2) || block3.isLeaves((IBlockAccess)world, i2, j2, k2)) {
                                    this.func_150516_a(world, i2, j2, k2, this.leafBlock, this.leafMeta);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
