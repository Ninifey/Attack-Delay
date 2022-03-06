// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import java.util.Iterator;
import java.util.List;
import net.minecraft.util.ChunkCoordinates;
import java.util.ArrayList;
import net.minecraft.block.material.Material;
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

public class LOTRWorldGenWillow extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int minHeight;
    private int maxHeight;
    private boolean needsWater;
    
    public LOTRWorldGenWillow(final boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.wood6;
        this.woodMeta = 1;
        this.leafBlock = LOTRMod.leaves6;
        this.leafMeta = 1;
        this.minHeight = 8;
        this.maxHeight = 13;
        this.needsWater = false;
    }
    
    public LOTRWorldGenWillow setNeedsWater() {
        this.needsWater = true;
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
        if (this.needsWater) {
            boolean water = false;
            for (int attempts = 4, l = 0; l < attempts; ++l) {
                final int i3 = i + MathHelper.getRandomIntegerInRange(random, -12, 12);
                final int j3 = j + MathHelper.getRandomIntegerInRange(random, -8, 4);
                final int k3 = k + MathHelper.getRandomIntegerInRange(random, -12, 12);
                if (world.getBlock(i3, j3, k3).getMaterial() == Material.water) {
                    water = true;
                    break;
                }
            }
            if (!water) {
                return false;
            }
        }
        if (!flag) {
            return false;
        }
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        final List<ChunkCoordinates> vineGrows = new ArrayList<ChunkCoordinates>();
        int angle = 0;
        while (angle < 360) {
            angle += 30 + random.nextInt(30);
            final float angleR = (float)Math.toRadians(angle);
            final float sin = MathHelper.sin(angleR);
            final float cos = MathHelper.cos(angleR);
            final int base = j + height - 3 - random.nextInt(3);
            final int length = 2 + random.nextInt(4);
            int i4 = i;
            int j4 = base;
            int k4 = k;
            for (int m = 0; m < length; ++m) {
                if (m > 0 && (m % 4 == 0 || random.nextInt(3) == 0)) {
                    ++j4;
                }
                if (random.nextFloat() < Math.abs(cos)) {
                    i4 += (int)Math.signum(cos);
                }
                if (random.nextFloat() < Math.abs(sin)) {
                    k4 += (int)Math.signum(sin);
                }
                this.func_150516_a(world, i4, j4, k4, this.woodBlock, this.woodMeta);
            }
            this.spawnLeafCluster(world, random, i4, j4, k4);
            vineGrows.add(new ChunkCoordinates(i4, j4, k4));
        }
        for (int j5 = 0; j5 < height; ++j5) {
            this.func_150516_a(world, i, j + j5, k, this.woodBlock, this.woodMeta);
            if (j5 == height - 1) {
                this.spawnLeafCluster(world, random, i, j + j5, k);
                vineGrows.add(new ChunkCoordinates(i, j + j5, k));
            }
        }
        for (int i5 = i - 1; i5 <= i + 1; ++i5) {
            for (int k5 = k - 1; k5 <= k + 1; ++k5) {
                final int i6 = i5 - i;
                final int k6 = k5 - k;
                if (Math.abs(i6) != Math.abs(k6)) {
                    final int rootX = i5;
                    int rootY = j + 1 + random.nextInt(2);
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
        for (final ChunkCoordinates coords : vineGrows) {
            this.spawnVineCluster(world, random, coords.posX, coords.posY, coords.posZ);
        }
        return true;
    }
    
    private void spawnLeafCluster(final World world, final Random random, final int i, final int j, final int k) {
        final int leafRange = 3;
        final int leafRangeSq = leafRange * leafRange;
        final int leafRangeSqLess = (int)((leafRange - 0.5) * (leafRange - 0.5));
        for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
            for (int j2 = j - leafRange; j2 <= j + leafRange; ++j2) {
                for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                    final int i3 = i2 - i;
                    final int j3 = j2 - j;
                    final int k3 = k2 - k;
                    final int dist = i3 * i3 + j3 * j3 + k3 * k3;
                    final int taxicab = Math.abs(i3) + Math.abs(j3) + Math.abs(k3);
                    if ((dist < leafRangeSqLess || (dist < leafRangeSq && random.nextInt(3) == 0)) && taxicab <= 4) {
                        final Block block = world.getBlock(i2, j2, k2);
                        if (block.isReplaceable((IBlockAccess)world, i2, j2, k2) || block.isLeaves((IBlockAccess)world, i2, j2, k2)) {
                            this.func_150516_a(world, i2, j2, k2, this.leafBlock, this.leafMeta);
                        }
                    }
                }
            }
        }
    }
    
    private void spawnVineCluster(final World world, final Random random, final int i, final int j, final int k) {
        final int leafRange = 3;
        final int leafRangeSq = leafRange * leafRange;
        for (int i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
            for (int j2 = j - leafRange; j2 <= j + leafRange; ++j2) {
                for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                    final int i3 = i2 - i;
                    final int j3 = j2 - j;
                    final int k3 = k2 - k;
                    final int dist = i3 * i3 + j3 * j3 + k3 * k3;
                    if (dist < leafRangeSq) {
                        final Block block = world.getBlock(i2, j2, k2);
                        final int meta = world.getBlockMetadata(i2, j2, k2);
                        if (block == this.leafBlock && meta == this.leafMeta) {
                            final int vineChance = 2;
                            if (random.nextInt(vineChance) == 0 && world.getBlock(i2 - 1, j2, k2).isAir((IBlockAccess)world, i2 - 1, j2, k2)) {
                                this.growVines(world, random, i2 - 1, j2, k2, 8);
                            }
                            if (random.nextInt(vineChance) == 0 && world.getBlock(i2 + 1, j2, k2).isAir((IBlockAccess)world, i2 + 1, j2, k2)) {
                                this.growVines(world, random, i2 + 1, j2, k2, 2);
                            }
                            if (random.nextInt(vineChance) == 0 && world.getBlock(i2, j2, k2 - 1).isAir((IBlockAccess)world, i2, j2, k2 - 1)) {
                                this.growVines(world, random, i2, j2, k2 - 1, 1);
                            }
                            if (random.nextInt(vineChance) == 0 && world.getBlock(i2, j2, k2 + 1).isAir((IBlockAccess)world, i2, j2, k2 + 1)) {
                                this.growVines(world, random, i2, j2, k2 + 1, 4);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void growVines(final World world, final Random random, final int i, int j, final int k, final int meta) {
        this.func_150516_a(world, i, j, k, LOTRMod.willowVines, meta);
        int vines = 0;
        while (true) {
            --j;
            if (!world.getBlock(i, j, k).isAir((IBlockAccess)world, i, j, k) || vines >= 2 + random.nextInt(4)) {
                break;
            }
            this.func_150516_a(world, i, j, k, LOTRMod.willowVines, meta);
            ++vines;
        }
    }
}
