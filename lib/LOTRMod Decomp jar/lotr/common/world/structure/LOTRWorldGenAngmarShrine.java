// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarShrine extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenAngmarShrine(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += 4;
                break;
            }
            case 1: {
                i -= 4;
                break;
            }
            case 2: {
                k -= 4;
                break;
            }
            case 3: {
                i += 4;
                break;
            }
        }
        if (super.restrictions) {
            int minHeight = j;
            int maxHeight = j;
            for (int i2 = i - 3; i2 <= i + 3; ++i2) {
                for (int k2 = k - 3; k2 <= k + 3; ++k2) {
                    final int j2 = world.getTopSolidOrLiquidBlock(i2, k2) - 1;
                    final Block l = world.getBlock(i2, j2, k2);
                    if (l != Blocks.grass && l != Blocks.dirt && l != Blocks.stone) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                }
            }
            if (maxHeight - minHeight > 3) {
                return false;
            }
        }
        for (int i3 = i - 3; i3 <= i + 3; ++i3) {
            for (int k3 = k - 3; k3 <= k + 3; ++k3) {
                for (int j3 = j; !LOTRMod.isOpaque(world, i3, j3, k3) && j3 >= 0; --j3) {
                    this.placeRandomBrick(world, random, i3, j3, k3);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
        }
        for (int m = 0; m <= 2; ++m) {
            for (int i4 = i - 3 + m; i4 <= i + 3 - m; ++i4) {
                for (int k4 = k - 3 + m; k4 <= k + 3 - m; ++k4) {
                    this.placeRandomBrick(world, random, i4, j + 1 + m, k4);
                }
            }
            this.placeRandomStairs(world, random, i - 3 + m, j + 1 + m, k, 0);
            this.placeRandomStairs(world, random, i + 3 - m, j + 1 + m, k, 1);
            this.placeRandomStairs(world, random, i, j + 1 + m, k - 3 + m, 2);
            this.placeRandomStairs(world, random, i, j + 1 + m, k + 3 - m, 3);
        }
        this.func_150516_a(world, i, j + 4, k, LOTRMod.angmarTable, 0);
        this.func_150516_a(world, i - 2, j + 3, k - 2, LOTRMod.morgulTorch, 5);
        this.func_150516_a(world, i - 2, j + 3, k + 2, LOTRMod.morgulTorch, 5);
        this.func_150516_a(world, i + 2, j + 3, k - 2, LOTRMod.morgulTorch, 5);
        this.func_150516_a(world, i + 2, j + 3, k + 2, LOTRMod.morgulTorch, 5);
        for (int pillars = 4 + random.nextInt(5), p = 0; p < pillars; ++p) {
            int i2 = 4 + random.nextInt(4);
            int k2 = 4 + random.nextInt(4);
            if (random.nextBoolean()) {
                i2 *= -1;
            }
            if (random.nextBoolean()) {
                k2 *= -1;
            }
            i2 += i;
            k2 += k;
            final int height = 2 + random.nextInt(3);
            final int j4 = world.getTopSolidOrLiquidBlock(i2, k2) - 1;
            final Block l2 = world.getBlock(i2, j4, k2);
            if (l2 == Blocks.grass || l2 == Blocks.dirt || l2 == Blocks.stone) {
                this.setGrassToDirt(world, i2, j4, k2);
                for (int j5 = j4; j5 < j4 + height; ++j5) {
                    this.placeRandomBrick(world, random, i2, j5, k2);
                }
                this.func_150516_a(world, i2, j4 + height, k2, LOTRMod.guldurilBrick, 2);
            }
        }
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick2, 1);
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick2, 0);
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.stairsAngmarBrickCracked, meta);
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.stairsAngmarBrick, meta);
        }
    }
}
