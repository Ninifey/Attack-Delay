// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import lotr.common.world.structure.LOTRWorldGenWoodElfPlatform;
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

public class LOTRWorldGenMirkOak extends WorldGenAbstractTree
{
    private int minHeight;
    private int maxHeight;
    private int trunkWidth;
    private boolean isMirky;
    private boolean restrictions;
    private boolean isDead;
    private boolean hasRoots;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenMirkOak(final boolean flag, final int i, final int j, final int k, final boolean mirk) {
        super(flag);
        this.restrictions = true;
        this.hasRoots = true;
        this.woodBlock = LOTRMod.wood;
        this.woodMeta = 2;
        this.leafBlock = LOTRMod.leaves;
        this.leafMeta = 2;
        this.minHeight = i;
        this.maxHeight = j;
        this.trunkWidth = k;
        this.isMirky = mirk;
    }
    
    public LOTRWorldGenMirkOak setBlocks(final Block b1, final int m1, final Block b2, final int m2) {
        this.woodBlock = b1;
        this.woodMeta = m1;
        this.leafBlock = b2;
        this.leafMeta = m2;
        return this;
    }
    
    public LOTRWorldGenMirkOak setGreenOak() {
        return this.setBlocks(LOTRMod.wood7, 1, LOTRMod.leaves7, 1);
    }
    
    public LOTRWorldGenMirkOak setRedOak() {
        return this.setBlocks(LOTRMod.wood7, 1, LOTRMod.leaves, 3);
    }
    
    public LOTRWorldGenMirkOak disableRestrictions() {
        this.restrictions = false;
        return this;
    }
    
    public LOTRWorldGenMirkOak setDead() {
        this.isDead = true;
        return this;
    }
    
    public LOTRWorldGenMirkOak disableRoots() {
        this.hasRoots = false;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        boolean flag = true;
        if (!this.restrictions || (j >= 1 && j + height + 5 <= 256)) {
            if (this.restrictions) {
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
            }
            if (this.restrictions) {
                for (int i3 = i - this.trunkWidth; i3 <= i + this.trunkWidth; ++i3) {
                    for (int k3 = k - this.trunkWidth; k3 <= k + this.trunkWidth; ++k3) {
                        world.getBlock(i3, j - 1, k3).onPlantGrow(world, i3, j - 1, k3, i3, j, k3);
                    }
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
                        this.func_150516_a(world, i5, j3, k5, this.woodBlock, this.woodMeta);
                    }
                    this.growLeafCanopy(world, random, i5, j3, k5);
                }
                if (this.trunkWidth == 2) {
                    int platforms = 0;
                    if (random.nextInt(3) != 0) {
                        if (random.nextBoolean()) {
                            platforms = 1 + random.nextInt(3);
                        }
                        else {
                            platforms = 4 + random.nextInt(5);
                        }
                    }
                    for (int m = 0; m < platforms; ++m) {
                        final int j4 = j + MathHelper.getRandomIntegerInRange(random, 10, height);
                        new LOTRWorldGenWoodElfPlatform(false).generate(world, random, i, j4, k);
                    }
                }
            }
            else {
                this.growLeafCanopy(world, random, i, j + height - 1, k);
            }
            if (this.hasRoots) {
                for (int roots = 4 + random.nextInt(5 * this.trunkWidth + 1), l2 = 0; l2 < roots; ++l2) {
                    int i2 = i;
                    int j4 = j + 1 + random.nextInt(this.trunkWidth * 2 + 1);
                    int k6 = k;
                    int xDirection = 0;
                    int zDirection = 0;
                    final int rootLength = 1 + random.nextInt(4);
                    if (random.nextBoolean()) {
                        if (random.nextBoolean()) {
                            i2 -= this.trunkWidth + 1;
                            xDirection = -1;
                        }
                        else {
                            i2 += this.trunkWidth + 1;
                            xDirection = 1;
                        }
                        k6 -= this.trunkWidth + 1;
                        k6 += random.nextInt(this.trunkWidth * 2 + 2);
                    }
                    else {
                        if (random.nextBoolean()) {
                            k6 -= this.trunkWidth + 1;
                            zDirection = -1;
                        }
                        else {
                            k6 += this.trunkWidth + 1;
                            zDirection = 1;
                        }
                        i2 -= this.trunkWidth + 1;
                        i2 += random.nextInt(this.trunkWidth * 2 + 2);
                    }
                    for (int l3 = 0; l3 < rootLength; ++l3) {
                        int rootBlocks = 0;
                        for (int j5 = j4; !world.getBlock(i2, j5, k6).isOpaqueCube(); --j5) {
                            this.func_150516_a(world, i2, j5, k6, this.woodBlock, this.woodMeta | 0xC);
                            world.getBlock(i2, j5 - 1, k6).onPlantGrow(world, i2, j5 - 1, k6, i2, j5, k6);
                            if (++rootBlocks > 5) {
                                break;
                            }
                        }
                        --j4;
                        if (random.nextBoolean()) {
                            if (xDirection == -1) {
                                --i2;
                            }
                            else if (xDirection == 1) {
                                ++i2;
                            }
                            else if (zDirection == -1) {
                                --k6;
                            }
                            else if (zDirection == 1) {
                                ++k6;
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
        final int leafStart = j + 2;
        final int leafTop = j + 5;
        final int maxRange = 3;
        if (!this.isDead) {
            for (int j2 = leafStart; j2 <= leafTop; ++j2) {
                final int j3 = j2 - leafTop;
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
                            int below = 0;
                            if (this.isMirky && j2 == leafStart && i3 <= 3 && k3 <= 3 && random.nextInt(3) == 0) {
                                ++below;
                            }
                            for (int j4 = j2; j4 >= j2 - below; --j4) {
                                final Block block = world.getBlock(i2, j4, k2);
                                if (block.isReplaceable((IBlockAccess)world, i2, j4, k2) || block.isLeaves((IBlockAccess)world, i2, j4, k2)) {
                                    this.func_150516_a(world, i2, j4, k2, this.leafBlock, this.leafMeta);
                                    if (this.isMirky) {
                                        if (random.nextInt(20) == 0 && world.isAirBlock(i2 - 1, j4, k2)) {
                                            this.growVines(world, random, i2 - 1, j4, k2, 8);
                                        }
                                        if (random.nextInt(20) == 0 && world.isAirBlock(i2 + 1, j4, k2)) {
                                            this.growVines(world, random, i2 + 1, j4, k2, 2);
                                        }
                                        if (random.nextInt(20) == 0 && world.isAirBlock(i2, j4, k2 - 1)) {
                                            this.growVines(world, random, i2, j4, k2 - 1, 1);
                                        }
                                        if (random.nextInt(20) == 0 && world.isAirBlock(i2, j4, k2 + 1)) {
                                            this.growVines(world, random, i2, j4, k2 + 1, 4);
                                        }
                                    }
                                }
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
                            this.func_150516_a(world, i4, j2, k4, this.woodBlock, this.woodMeta);
                        }
                    }
                }
            }
        }
    }
    
    private void growVines(final World world, final Random random, final int i, int j, final int k, final int meta) {
        this.func_150516_a(world, i, j, k, LOTRMod.mirkVines, meta);
        int length = 4 + random.nextInt(8);
        while (true) {
            --j;
            if (!world.isAirBlock(i, j, k) || length <= 0) {
                break;
            }
            this.func_150516_a(world, i, j, k, LOTRMod.mirkVines, meta);
            --length;
        }
    }
}
