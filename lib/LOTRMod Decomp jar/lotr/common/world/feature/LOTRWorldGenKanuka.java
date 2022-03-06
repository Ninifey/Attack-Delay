// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import java.util.List;
import java.util.ArrayList;
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

public class LOTRWorldGenKanuka extends WorldGenAbstractTree
{
    private int minHeight;
    private int maxHeight;
    private int trunkWidth;
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenKanuka(final boolean flag) {
        this(flag, 5, 12, 0);
    }
    
    public LOTRWorldGenKanuka(final boolean flag, final int i, final int j, final int k) {
        super(flag);
        this.woodBlock = LOTRMod.wood9;
        this.woodMeta = 1;
        this.leafBlock = LOTRMod.leaves9;
        this.leafMeta = 1;
        this.minHeight = i;
        this.maxHeight = j;
        this.trunkWidth = k;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        final float trunkAngleY = (float)Math.toRadians(90.0f - MathHelper.randomFloatClamp(random, 0.0f, 35.0f));
        final float trunkAngle = random.nextFloat() * 3.1415927f * 2.0f;
        final float trunkYCos = MathHelper.cos(trunkAngleY);
        final float trunkYSin = MathHelper.sin(trunkAngleY);
        final float trunkCos = MathHelper.cos(trunkAngle) * trunkYCos;
        final float trunkSin = MathHelper.sin(trunkAngle) * trunkYCos;
        boolean flag = true;
        if (j < 1 || j + height + 3 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + height + 3; ++j2) {
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
        for (int pass = 0; pass <= 1; ++pass) {
            if (pass == 1) {
                for (int i4 = i - this.trunkWidth; i4 <= i + this.trunkWidth; ++i4) {
                    for (int k4 = k - this.trunkWidth; k4 <= k + this.trunkWidth; ++k4) {
                        world.getBlock(i4, j - 1, k4).onPlantGrow(world, i4, j - 1, k4, i4, j, k4);
                    }
                }
            }
            int trunkX = i;
            int trunkZ = k;
            int trunkY = j;
            final List<int[]> trunkCoords = new ArrayList<int[]>();
            for (int trunkHeight = height, l = 0; l < trunkHeight; ++l) {
                if (l > 0) {
                    if (Math.floor(trunkCos * l) != Math.floor(trunkCos * (l + 1))) {
                        trunkX += (int)Math.signum(trunkCos);
                    }
                    if (Math.floor(trunkSin * l) != Math.floor(trunkSin * (l + 1))) {
                        trunkZ += (int)Math.signum(trunkSin);
                    }
                    if (Math.floor(trunkYSin * l) != Math.floor(trunkYSin * (l + 1))) {
                        trunkY += (int)Math.signum(trunkYSin);
                    }
                }
                final int j3 = trunkY;
                for (int i5 = trunkX - this.trunkWidth; i5 <= trunkX + this.trunkWidth; ++i5) {
                    for (int k5 = trunkZ - this.trunkWidth; k5 <= trunkZ + this.trunkWidth; ++k5) {
                        if (pass == 0 && !this.isReplaceable(world, i5, j3, k5)) {
                            return false;
                        }
                        if (pass == 1) {
                            this.func_150516_a(world, i5, j3, k5, this.woodBlock, this.woodMeta | 0xC);
                            trunkCoords.add(new int[] { i5, j3, k5 });
                        }
                    }
                }
            }
            if (pass == 1) {
                final int branchHeight = (int)(height * 0.67);
                int deg = 0;
                while (deg < 360) {
                    int degIncr = MathHelper.getRandomIntegerInRange(random, 70, 150);
                    degIncr -= this.trunkWidth * 10;
                    degIncr = Math.max(degIncr, 20);
                    deg += degIncr;
                    final float angle = (float)Math.toRadians(deg);
                    float cos = MathHelper.cos(angle);
                    float sin = MathHelper.sin(angle);
                    final float angleY = (float)Math.toRadians(70.0f - random.nextFloat() * 20.0f);
                    final float cosY = MathHelper.cos(angleY);
                    final float sinY = MathHelper.sin(angleY);
                    cos *= cosY;
                    sin *= cosY;
                    int length = branchHeight + MathHelper.getRandomIntegerInRange(random, -3, 3);
                    length = Math.max(length, 3);
                    length += this.trunkWidth;
                    final int trunkIndex = MathHelper.getRandomIntegerInRange(random, (int)((trunkCoords.size() - 1) * 0.5), trunkCoords.size() - 1);
                    final int[] oneTrunkCoord = trunkCoords.get(trunkIndex);
                    int i6 = oneTrunkCoord[0];
                    int j4 = oneTrunkCoord[1];
                    int k6 = oneTrunkCoord[2];
                    for (int m = 0; m < length; ++m) {
                        if (Math.floor(cos * m) != Math.floor(cos * (m + 1))) {
                            i6 += (int)Math.signum(cos);
                        }
                        if (Math.floor(sin * m) != Math.floor(sin * (m + 1))) {
                            k6 += (int)Math.signum(sin);
                        }
                        if (Math.floor(sinY * m) != Math.floor(sinY * (m + 1))) {
                            j4 += (int)Math.signum(sinY);
                        }
                        final Block block2 = world.getBlock(i6, j4, k6);
                        if (!block2.isReplaceable((IBlockAccess)world, i6, j4, k6) && !block2.isWood((IBlockAccess)world, i6, j4, k6) && !block2.isLeaves((IBlockAccess)world, i6, j4, k6)) {
                            break;
                        }
                        this.func_150516_a(world, i6, j4, k6, this.woodBlock, this.woodMeta | 0xC);
                    }
                    this.growLeafCanopy(world, random, i6, j4, k6);
                }
            }
        }
        return true;
    }
    
    private void growLeafCanopy(final World world, final Random random, final int i, final int j, final int k) {
        final int leafHeight = 2;
        final int maxRange = 1 + random.nextInt(3);
        for (int j2 = 0; j2 < leafHeight; ++j2) {
            final int j3 = j + j2;
            for (int leafRange = maxRange - j2, i2 = i - leafRange; i2 <= i + leafRange; ++i2) {
                for (int k2 = k - leafRange; k2 <= k + leafRange; ++k2) {
                    final int i3 = Math.abs(i2 - i);
                    final int k3 = Math.abs(k2 - k);
                    final int dist = i3 + k3;
                    if (dist <= leafRange) {
                        final Block block = world.getBlock(i2, j3, k2);
                        if (block.isReplaceable((IBlockAccess)world, i2, j3, k2) || block.isLeaves((IBlockAccess)world, i2, j3, k2)) {
                            this.func_150516_a(world, i2, j3, k2, this.leafBlock, this.leafMeta);
                        }
                    }
                }
            }
        }
        final Block block2 = world.getBlock(i, j, k);
        if (block2.isReplaceable((IBlockAccess)world, i, j, k) || block2.isLeaves((IBlockAccess)world, i, j, k)) {
            this.func_150516_a(world, i, j, k, this.woodBlock, this.woodMeta | 0xC);
        }
    }
}
