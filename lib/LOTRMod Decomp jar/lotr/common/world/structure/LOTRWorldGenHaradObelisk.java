// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHaradObelisk extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenHaradObelisk(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && world.getBlock(i, j - 1, k) != Blocks.sand && world.getBlock(i, j - 1, k) != Blocks.dirt && world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += 8;
                break;
            }
            case 1: {
                i -= 8;
                break;
            }
            case 2: {
                k -= 8;
                break;
            }
            case 3: {
                i += 8;
                break;
            }
        }
        if (super.restrictions) {
            for (int i2 = i - 7; i2 <= i + 7; ++i2) {
                for (int k2 = k - 7; k2 <= k + 7; ++k2) {
                    final int j2 = world.getTopSolidOrLiquidBlock(i2, k2);
                    final Block block = world.getBlock(i2, j2 - 1, k2);
                    if (block != Blocks.sand && block != Blocks.dirt && block != Blocks.stone && block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = i - 7; i2 <= i + 7; ++i2) {
            for (int k2 = k - 7; k2 <= k + 7; ++k2) {
                for (int j2 = j; (j2 == j || !LOTRMod.isOpaque(world, i2, j2, k2)) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i2, j2, k2, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
        }
        for (int i2 = i - 7; i2 <= i + 7; ++i2) {
            for (int k2 = k - 7; k2 <= k + 7; ++k2) {
                final int i3 = Math.abs(i2 - i);
                final int k3 = Math.abs(k2 - k);
                if (i3 == 7 || k3 == 7) {
                    this.func_150516_a(world, i2, j + 1, k2, Blocks.sandstone, 0);
                }
                if ((i3 == 5 && k3 <= 5) || (k3 == 5 && i3 <= 5)) {
                    this.func_150516_a(world, i2, j + 1, k2, Blocks.sandstone, 0);
                    this.func_150516_a(world, i2, j + 2, k2, Blocks.sandstone, 2);
                }
                if ((i3 == 3 && k3 <= 3) || (k3 == 3 && i3 <= 3)) {
                    this.func_150516_a(world, i2, j + 1, k2, Blocks.sandstone, 0);
                    this.func_150516_a(world, i2, j + 2, k2, Blocks.sandstone, 2);
                    this.placeHaradBrick(world, random, i2, j + 3, k2);
                }
                if (i3 <= 1 && k3 <= 1) {
                    this.func_150516_a(world, i2, j + 1, k2, Blocks.sandstone, 0);
                    this.func_150516_a(world, i2, j + 2, k2, Blocks.sandstone, 2);
                    this.placeHaradBrick(world, random, i2, j + 3, k2);
                    this.func_150516_a(world, i2, j + 4, k2, Blocks.sandstone, 2);
                    this.placeHaradBrick(world, random, i2, j + 5, k2);
                }
                for (int l = 0; l <= 2; ++l) {
                    final int l2 = 8 - (l * 2 + 1);
                    if (i3 == l2 && k3 == l2) {
                        this.placeHaradBrick(world, random, i2, j + l + 2, k2);
                        this.placeHaradWall(world, random, i2, j + l + 3, k2);
                        this.placeHaradWall(world, random, i2, j + l + 4, k2);
                    }
                }
            }
        }
        this.placeHaradBrick(world, random, i - 1, j + 6, k);
        this.placeHaradBrick(world, random, i + 1, j + 6, k);
        this.placeHaradBrick(world, random, i, j + 6, k - 1);
        this.placeHaradBrick(world, random, i, j + 6, k + 1);
        for (int j3 = j + 6; j3 <= j + 9; ++j3) {
            this.func_150516_a(world, i, j3, k, Blocks.sandstone, 2);
        }
        this.func_150516_a(world, i - 1, j + 10, k, Blocks.sandstone, 2);
        this.func_150516_a(world, i + 1, j + 10, k, Blocks.sandstone, 2);
        this.func_150516_a(world, i, j + 10, k - 1, Blocks.sandstone, 2);
        this.func_150516_a(world, i, j + 10, k + 1, Blocks.sandstone, 2);
        this.func_150516_a(world, i, j + 10, k, LOTRMod.hearth, 0);
        this.func_150516_a(world, i, j + 11, k, (Block)Blocks.fire, 0);
        return true;
    }
    
    private void placeHaradBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(3) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick3, 11);
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 15);
        }
    }
    
    private void placeHaradWall(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(3) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.wall3, 3);
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.wall, 15);
        }
    }
}
