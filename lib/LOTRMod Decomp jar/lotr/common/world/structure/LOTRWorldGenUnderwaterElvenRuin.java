// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenUnderwaterElvenRuin extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenUnderwaterElvenRuin(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && world.getBlock(i, j, k).getMaterial() != Material.water) {
            return false;
        }
        --j;
        final int width = 3 + random.nextInt(3);
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += width + 1;
                break;
            }
            case 1: {
                i -= width + 1;
                break;
            }
            case 2: {
                k -= width + 1;
                break;
            }
            case 3: {
                i += width + 1;
                break;
            }
        }
        if (super.restrictions) {
            int minHeight = j + 1;
            int maxHeight = j + 1;
            for (int i2 = i - width; i2 <= i + width; ++i2) {
                for (int k2 = k - width; k2 <= k + width; ++k2) {
                    final int j2 = world.getTopSolidOrLiquidBlock(i2, k2);
                    if (world.getBlock(i2, j2, k2).getMaterial() != Material.water) {
                        return false;
                    }
                    final Block block = world.getBlock(i2, j2 - 1, k2);
                    if (block != Blocks.dirt && block != Blocks.sand && block != Blocks.clay) {
                        return false;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                }
            }
            if (Math.abs(maxHeight - minHeight) > 5) {
                return false;
            }
        }
        for (int i3 = i - width - 3; i3 <= i + width + 3; ++i3) {
            for (int k3 = k - width - 3; k3 <= k + width + 3; ++k3) {
                final int i4 = Math.abs(i3 - i);
                final int k4 = Math.abs(k3 - k);
                if ((i4 <= width && k4 <= width) || random.nextInt(Math.max(1, i4 + k4)) == 0) {
                    final int j2 = world.getTopSolidOrLiquidBlock(i3, k3);
                    this.placeRandomBrick(world, random, i3, j2, k3);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                    if (random.nextInt(5) == 0) {
                        this.placeRandomBrick(world, random, i3, j2 + 1, k3);
                        if (random.nextInt(4) == 0) {
                            this.placeRandomBrick(world, random, i3, j2 + 2, k3);
                            if (random.nextInt(3) == 0) {
                                this.placeRandomBrick(world, random, i3, j2 + 3, k3);
                            }
                        }
                    }
                    if ((i4 == width && k4 == width) || random.nextInt(20) == 0) {
                        for (int height = 2 + random.nextInt(4), j3 = j2; j3 < j2 + height; ++j3) {
                            this.placeRandomPillar(world, random, i3, j3, k3);
                        }
                    }
                }
            }
        }
        final int j4 = world.getTopSolidOrLiquidBlock(i, k);
        this.func_150516_a(world, i, j4, k, Blocks.glowstone, 0);
        this.func_150516_a(world, i, j4 + 1, k, LOTRMod.chestStone, 0);
        LOTRChestContents.fillChest(world, random, i, j4 + 1, k, LOTRChestContents.UNDERWATER_ELVEN_RUIN);
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 2);
                break;
            }
            case 1: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 3);
                break;
            }
            case 2: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 4);
                break;
            }
        }
    }
    
    private void placeRandomPillar(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(3) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.pillar, 11);
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.pillar, 10);
        }
    }
}
