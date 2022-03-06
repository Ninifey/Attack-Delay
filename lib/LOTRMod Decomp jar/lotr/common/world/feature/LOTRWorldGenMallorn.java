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

public class LOTRWorldGenMallorn extends WorldGenAbstractTree
{
    private int minHeight;
    private int maxHeight;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenMallorn(final boolean flag) {
        super(flag);
        this.minHeight = 10;
        this.maxHeight = 14;
        this.woodBlock = LOTRMod.wood;
        this.woodMeta = 1;
        this.leafBlock = LOTRMod.leaves;
        this.leafMeta = 1;
    }
    
    public LOTRWorldGenMallorn setMinMaxHeight(final int min, final int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        final int leafMin = j + (int)(height * 0.6f);
        boolean flag = true;
        if (j < 1 || j + height + 1 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + height + 1; ++j2) {
            int range = 1;
            if (j2 == j) {
                range = 0;
            }
            if (j2 >= leafMin) {
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
        if (!flag) {
            return false;
        }
        boolean canGrow = true;
        Block below = world.getBlock(i, j - 1, k);
        if (!below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            canGrow = false;
        }
        if (canGrow) {
            below = world.getBlock(i, j - 1, k);
            below.onPlantGrow(world, i, j - 1, k, i, j, k);
            int deg = 0;
            for (int j3 = j + height; j3 >= leafMin; --j3) {
                for (int branches = 1 + random.nextInt(2), b = 0; b < branches; ++b) {
                    deg += 50 + random.nextInt(70);
                    final float angle = (float)Math.toRadians(deg);
                    final float cos = MathHelper.cos(angle);
                    final float sin = MathHelper.sin(angle);
                    final float angleY = random.nextFloat() * (float)Math.toRadians(50.0);
                    final float cosY = MathHelper.cos(angleY);
                    final float sinY = MathHelper.sin(angleY);
                    final int length = 4 + random.nextInt(6);
                    int i3 = i;
                    int k3 = k;
                    int j4 = j3;
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
                        final Block block = world.getBlock(i3, j4, k3);
                        if (!block.isReplaceable((IBlockAccess)world, i3, j4, k3) && !block.isWood((IBlockAccess)world, i3, j4, k3) && !block.isLeaves((IBlockAccess)world, i3, j4, k3)) {
                            break;
                        }
                        this.func_150516_a(world, i3, j4, k3, this.woodBlock, this.woodMeta | 0xC);
                    }
                    this.growLeafCanopy(world, random, i3, j4, k3);
                }
            }
            for (int j3 = j; j3 < j + height; ++j3) {
                this.func_150516_a(world, i, j3, k, this.woodBlock, this.woodMeta);
            }
            for (int i4 = i - 1; i4 <= i + 1; ++i4) {
                for (int k4 = k - 1; k4 <= k + 1; ++k4) {
                    final int i5 = i4 - i;
                    final int k5 = k4 - k;
                    if (Math.abs(i5) != Math.abs(k5)) {
                        final int rootX = i4;
                        int rootY = j + random.nextInt(2);
                        final int rootZ = k4;
                        int roots = 0;
                        while (world.getBlock(rootX, rootY, k4).isReplaceable((IBlockAccess)world, rootX, rootY, rootZ)) {
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
        return false;
    }
    
    private void growLeafCanopy(final World world, final Random random, final int i, final int j, final int k) {
        final int leafStart = j - 1;
        final int leafTop = j + 2;
        final int maxRange = 3 + random.nextInt(2);
        final int[] ranges = { -2, 0, -1, -2 };
        for (int j2 = leafStart; j2 <= leafTop; ++j2) {
            final int leafRange = maxRange + ranges[j2 - leafStart];
            final int leafRangeSq = leafRange * leafRange;
            for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
                for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                    final int i3 = Math.abs(i2 - i);
                    final int k3 = Math.abs(k2 - k);
                    final int j3 = Math.abs(j2 - j);
                    final int dSq = i3 * i3 + k3 * k3;
                    final int dCh = i3 + j3 + k3;
                    boolean grow = dSq < leafRangeSq && dCh <= 4;
                    if (i3 == leafRange - 1 || k3 == leafRange - 1) {
                        grow &= (random.nextInt(4) != 0);
                    }
                    if (grow) {
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
