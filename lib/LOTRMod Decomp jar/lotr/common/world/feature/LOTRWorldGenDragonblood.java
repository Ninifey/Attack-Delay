// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraftforge.common.IPlantable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockSapling;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenDragonblood extends WorldGenAbstractTree
{
    private int minHeight;
    private int maxHeight;
    private int trunkWidth;
    private boolean hasRoots;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenDragonblood(final boolean flag, final int i, final int j, final int k) {
        super(flag);
        this.hasRoots = true;
        this.woodBlock = LOTRMod.wood9;
        this.woodMeta = 0;
        this.leafBlock = LOTRMod.leaves9;
        this.leafMeta = 0;
        this.minHeight = i;
        this.maxHeight = j;
        this.trunkWidth = k;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        boolean flag = true;
        if (j < 1 || j + height + 5 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + height + 5; ++j2) {
            int range = this.trunkWidth + 1;
            if (j2 == j) {
                range = this.trunkWidth;
            }
            if (j2 >= j + height + 2) {
                range = this.trunkWidth + 2;
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
        for (int i3 = i - this.trunkWidth; i3 <= i + this.trunkWidth && flag; ++i3) {
            for (int k3 = k - this.trunkWidth; k3 <= k + this.trunkWidth && flag; ++k3) {
                final Block block = world.getBlock(i3, j - 1, k3);
                final boolean isSoil = block.canSustainPlant((IBlockAccess)world, i3, j - 1, k3, ForgeDirection.UP, (IPlantable)Blocks.sapling);
                if (!isSoil) {
                    flag = false;
                }
            }
        }
        if (!flag) {
            return false;
        }
        for (int i3 = i - this.trunkWidth; i3 <= i + this.trunkWidth; ++i3) {
            for (int k3 = k - this.trunkWidth; k3 <= k + this.trunkWidth; ++k3) {
                world.getBlock(i3, j - 1, k3).onPlantGrow(world, i3, j - 1, k3, i3, j, k3);
            }
        }
        for (int j2 = 0; j2 < height; ++j2) {
            for (int i4 = i - this.trunkWidth; i4 <= i + this.trunkWidth; ++i4) {
                for (int k4 = k - this.trunkWidth; k4 <= k + this.trunkWidth; ++k4) {
                    this.func_150516_a(world, i4, j + j2, k4, this.woodBlock, this.woodMeta);
                }
            }
        }
        if (this.trunkWidth >= 1) {
            int deg = 0;
            while (deg < 360) {
                deg += (40 + random.nextInt(30)) / this.trunkWidth;
                final float angle = (float)Math.toRadians(deg);
                final float cos = MathHelper.cos(angle);
                final float sin = MathHelper.sin(angle);
                final float angleY = random.nextFloat() * (float)Math.toRadians(40.0);
                final float cosY = MathHelper.cos(angleY);
                final float sinY = MathHelper.sin(angleY);
                int length = 3 + random.nextInt(6);
                length *= 1 + random.nextInt(this.trunkWidth);
                int i5 = i;
                int k5 = k;
                int j3 = j + height - 1 - random.nextInt(5);
                for (int l = 0; l < length; ++l) {
                    if (Math.floor(cos * l) != Math.floor(cos * (l - 1))) {
                        i5 += (int)Math.signum(cos);
                    }
                    if (Math.floor(sin * l) != Math.floor(sin * (l - 1))) {
                        k5 += (int)Math.signum(sin);
                    }
                    if (Math.floor(sinY * l) != Math.floor(sinY * (l - 1))) {
                        j3 += (int)Math.signum(sinY);
                    }
                    final Block block2 = world.getBlock(i5, j3, k5);
                    if (!block2.isReplaceable((IBlockAccess)world, i5, j3, k5) && !block2.isWood((IBlockAccess)world, i5, j3, k5) && !block2.isLeaves((IBlockAccess)world, i5, j3, k5)) {
                        break;
                    }
                    this.func_150516_a(world, i5, j3, k5, this.woodBlock, this.woodMeta | 0xC);
                }
                this.growLeafCanopy(world, random, i5, j3, k5);
            }
        }
        else {
            this.growLeafCanopy(world, random, i, j + height - 1, k);
        }
        for (int i3 = i - 1 - this.trunkWidth; i3 <= i + 1 + this.trunkWidth; ++i3) {
            for (int k3 = k - 1 - this.trunkWidth; k3 <= k + 1 + this.trunkWidth; ++k3) {
                final int i6 = i3 - i;
                final int k6 = k3 - k;
                if (Math.abs(i6) != Math.abs(k6)) {
                    final int rootX = i3;
                    int rootY = j + random.nextInt(2 + this.trunkWidth);
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
            }
        }
        return true;
    }
    
    private void growLeafCanopy(final World world, final Random random, final int i, final int j, final int k) {
        final int leafStart = j + 2;
        final int leafTop = j + 4;
        final int maxRange = 3;
        for (int j2 = leafStart; j2 <= leafTop; ++j2) {
            final int j3 = j2 - (leafTop + 1);
            final int leafRange = maxRange - j3;
            final int leafRangeSq = leafRange * leafRange;
            for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
                for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                    final int i3 = Math.abs(i2 - i);
                    final int k3 = Math.abs(k2 - k);
                    final int dist = i3 * i3 + k3 * k3;
                    boolean grow = dist < leafRangeSq;
                    if (i3 == leafRange - 1 || k3 == leafRange - 1) {
                        grow &= (random.nextInt(4) > 0);
                    }
                    if (grow) {
                        for (int below = 0, j4 = j2; j4 >= j2 - below; --j4) {
                            final Block block = world.getBlock(i2, j4, k2);
                            if (block.isReplaceable((IBlockAccess)world, i2, j4, k2) || block.isLeaves((IBlockAccess)world, i2, j4, k2)) {
                                this.func_150516_a(world, i2, j4, k2, this.leafBlock, this.leafMeta);
                            }
                        }
                    }
                }
            }
        }
        for (int j2 = j; j2 <= j + 2; ++j2) {
            for (int i4 = i - maxRange; i4 <= i + maxRange; ++i4) {
                for (int k4 = k - maxRange; k4 <= k + maxRange; ++k4) {
                    final int i5 = Math.abs(i4 - i);
                    final int k5 = Math.abs(k4 - k);
                    final int j5 = j2 - j;
                    if ((i5 == 0 && k5 == 0) || (i5 == k5 && i5 == j5) || ((i5 == 0 || k5 == 0) && i5 != k5 && (i5 == j5 + 1 || k5 == j5 + 1))) {
                        final Block block2 = world.getBlock(i4, j2, k4);
                        if (block2.isReplaceable((IBlockAccess)world, i4, j2, k4) || block2.isLeaves((IBlockAccess)world, i4, j2, k4)) {
                            this.func_150516_a(world, i4, j2, k4, this.woodBlock, this.woodMeta | 0xC);
                        }
                    }
                }
            }
        }
    }
}
