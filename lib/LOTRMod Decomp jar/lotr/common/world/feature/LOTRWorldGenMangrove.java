// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenMangrove extends WorldGenAbstractTree
{
    private Block woodID;
    private int woodMeta;
    private Block leafID;
    private int leafMeta;
    
    public LOTRWorldGenMangrove(final boolean flag) {
        super(flag);
        this.woodID = LOTRMod.wood3;
        this.woodMeta = 3;
        this.leafID = LOTRMod.leaves3;
        this.leafMeta = 3;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = 6 + random.nextInt(5);
        boolean flag = true;
        if (j < 1 || j + height + 1 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + 1 + height; ++j2) {
            int range = 1;
            if (j2 == j) {
                range = 0;
            }
            if (j2 >= j + 1 + height - 2) {
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
        final Block below = world.getBlock(i, j - 1, k);
        final boolean canGrow = below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling) || below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.deadbush);
        if (canGrow) {
            world.getBlock(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
            final int leafStart = 3;
            final int leafRangeMin = 0;
            final int leafRangeFactor = 2;
            for (int j3 = j - leafStart + height; j3 <= j + height; ++j3) {
                final int j4 = j3 - (j + height);
                for (int leafRange = leafRangeMin + 1 - j4 / leafRangeFactor, i3 = i - leafRange; i3 <= i + leafRange; ++i3) {
                    final int i4 = i3 - i;
                    for (int k3 = k - leafRange; k3 <= k + leafRange; ++k3) {
                        final int k4 = k3 - k;
                        final Block block = world.getBlock(i3, j3, k3);
                        if ((Math.abs(i4) != leafRange || Math.abs(k4) != leafRange || (random.nextInt(2) != 0 && j4 != 0)) && block.canBeReplacedByLeaves((IBlockAccess)world, i3, j3, k3)) {
                            this.func_150516_a(world, i3, j3, k3, this.leafID, this.leafMeta);
                            if (random.nextInt(8) == 0 && world.getBlock(i3 - 1, j3, k3).isAir((IBlockAccess)world, i3 - 1, j3, k3)) {
                                this.growVines(world, random, i3 - 1, j3, k3, 8);
                            }
                            if (random.nextInt(8) == 0 && world.getBlock(i3 + 1, j3, k3).isAir((IBlockAccess)world, i3 + 1, j3, k3)) {
                                this.growVines(world, random, i3 + 1, j3, k3, 2);
                            }
                            if (random.nextInt(8) == 0 && world.getBlock(i3, j3, k3 - 1).isAir((IBlockAccess)world, i3, j3, k3 - 1)) {
                                this.growVines(world, random, i3, j3, k3 - 1, 1);
                            }
                            if (random.nextInt(8) == 0 && world.getBlock(i3, j3, k3 + 1).isAir((IBlockAccess)world, i3, j3, k3 + 1)) {
                                this.growVines(world, random, i3, j3, k3 + 1, 4);
                            }
                        }
                    }
                }
            }
            for (int j3 = 0; j3 < height; ++j3) {
                final Block block2 = world.getBlock(i, j + j3, k);
                if (block2.isReplaceable((IBlockAccess)world, i, j + j3, k) || block2.isLeaves((IBlockAccess)world, i, j + j3, k)) {
                    this.func_150516_a(world, i, j + j3, k, this.woodID, this.woodMeta);
                }
            }
            for (int i5 = i - 1; i5 <= i + 1; ++i5) {
                for (int k5 = k - 1; k5 <= k + 1; ++k5) {
                    final int i6 = i5 - i;
                    final int k6 = k5 - k;
                    if (Math.abs(i6) != Math.abs(k6)) {
                        int rootX = i5;
                        int rootY = j + 1 + random.nextInt(3);
                        int rootZ = k5;
                        final int xWay = Integer.signum(i6);
                        final int zWay = Integer.signum(k6);
                        int roots = 0;
                        while (world.getBlock(rootX, rootY, k5).isReplaceable((IBlockAccess)world, rootX, rootY, rootZ)) {
                            this.func_150516_a(world, rootX, rootY, rootZ, this.woodID, this.woodMeta | 0xC);
                            world.getBlock(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
                            --rootY;
                            if (random.nextInt(3) > 0) {
                                rootX += xWay;
                                rootZ += zWay;
                            }
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
    
    private void growVines(final World world, final Random random, final int i, int j, final int k, final int meta) {
        this.func_150516_a(world, i, j, k, Blocks.vine, meta);
        int vines = 0;
        while (true) {
            --j;
            if (!world.getBlock(i, j, k).isAir((IBlockAccess)world, i, j, k) || vines >= 2 + random.nextInt(3)) {
                break;
            }
            this.func_150516_a(world, i, j, k, Blocks.vine, meta);
            ++vines;
        }
    }
}
