// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.entity.npc.LOTREntityRohanBarrowWraith;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenRohan;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanBarrow extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenRohanBarrow(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && (world.getBlock(i, j - 1, k) != Blocks.grass || !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenRohan))) {
            return false;
        }
        --j;
        final int radius = 7;
        final int height = 4;
        if (!super.restrictions && super.usingPlayer != null) {
            final int playerRotation = this.usingPlayerRotation();
            switch (playerRotation) {
                case 0: {
                    k += radius;
                    break;
                }
                case 1: {
                    i -= radius;
                    break;
                }
                case 2: {
                    k -= radius;
                    break;
                }
                case 3: {
                    i += radius;
                    break;
                }
            }
        }
        if (super.restrictions) {
            int minHeight = j;
            int maxHeight = j;
            for (int i2 = i - radius; i2 <= i + radius; ++i2) {
                for (int k2 = k - radius; k2 <= k + radius; ++k2) {
                    final int j2 = world.getTopSolidOrLiquidBlock(i2, k2) - 1;
                    if (world.getBlock(i2, j2, k2) != Blocks.grass) {
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
        for (int i3 = i - radius; i3 <= i + radius; ++i3) {
            for (int j3 = j + height; j3 >= j; --j3) {
                for (int k3 = k - radius; k3 <= k + radius; ++k3) {
                    final int i4 = i3 - i;
                    final int j4 = j3 - j;
                    final int k4 = k3 - k;
                    if (i4 * i4 + j4 * j4 + k4 * k4 <= radius * radius) {
                        final boolean grass = !LOTRMod.isOpaque(world, i3, j3 + 1, k3);
                        this.func_150516_a(world, i3, j3, k3, (Block)(grass ? Blocks.grass : Blocks.dirt), 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
            }
        }
        for (int i3 = i - radius; i3 <= i + radius; ++i3) {
            for (int k5 = k - radius; k5 <= k + radius; ++k5) {
                for (int j5 = j - 1; !LOTRMod.isOpaque(world, i3, j5, k5) && j5 >= 0; --j5) {
                    final int i4 = i3 - i;
                    final int k6 = k5 - k;
                    if (i4 * i4 + k6 * k6 <= radius * radius) {
                        this.func_150516_a(world, i3, j5, k5, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i3, j5 - 1, k5);
                    }
                }
            }
        }
        for (int l = 0; l < 12; ++l) {
            final int i5 = i - random.nextInt(radius) + random.nextInt(radius);
            final int k3 = k - random.nextInt(radius) + random.nextInt(radius);
            final int j6 = world.getHeightValue(i5, k3);
            if (world.getBlock(i5, j6 - 1, k3) == Blocks.grass) {
                this.func_150516_a(world, i5, j6, k3, LOTRMod.simbelmyne, 0);
            }
        }
        j += height;
        for (int i3 = i - 1; i3 < i + 1; ++i3) {
            for (int k5 = k - 1; k5 <= k + 1; ++k5) {
                this.func_150516_a(world, i3, j - 1, k5, Blocks.air, 0);
            }
        }
        for (int i3 = i - 2; i3 <= i + 2; ++i3) {
            for (int k5 = k - 2; k5 <= k + 2; ++k5) {
                for (int j5 = j - 2; j5 >= j - 4; --j5) {
                    this.func_150516_a(world, i3, j5, k5, Blocks.air, 0);
                }
                this.func_150516_a(world, i3, j - 5, k5, LOTRMod.slabDouble2, 1);
            }
        }
        for (int j7 = j - 3; j7 >= j - 4; --j7) {
            for (int i5 = i - 4; i5 <= i + 4; ++i5) {
                for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                    this.func_150516_a(world, i5, j7, k3, Blocks.air, 0);
                    if (Math.abs(i5 - i) > 2) {
                        this.func_150516_a(world, i5, j - 5, k3, LOTRMod.brick, 4);
                    }
                }
            }
            for (int i5 = i - 1; i5 <= i + 1; ++i5) {
                for (int k3 = k - 4; k3 <= k + 4; ++k3) {
                    this.func_150516_a(world, i5, j7, k3, Blocks.air, 0);
                    if (Math.abs(k3 - k) > 2) {
                        this.func_150516_a(world, i5, j - 5, k3, LOTRMod.brick, 4);
                    }
                }
            }
            this.func_150516_a(world, i - 4, j7, k - 1, LOTRMod.rock, 2);
            this.func_150516_a(world, i - 5, j7, k, LOTRMod.rock, 2);
            this.func_150516_a(world, i - 4, j7, k + 1, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 4, j7, k - 1, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 5, j7, k, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 4, j7, k + 1, LOTRMod.rock, 2);
            this.func_150516_a(world, i - 1, j7, k - 4, LOTRMod.rock, 2);
            this.func_150516_a(world, i, j7, k - 5, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 1, j7, k - 4, LOTRMod.rock, 2);
            this.func_150516_a(world, i - 1, j7, k + 4, LOTRMod.rock, 2);
            this.func_150516_a(world, i, j7, k + 5, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 1, j7, k + 4, LOTRMod.rock, 2);
        }
        this.func_150516_a(world, i - 4, j - 3, k, Blocks.torch, 1);
        this.func_150516_a(world, i - 4, j - 4, k, LOTRMod.slabSingle2, 9);
        this.func_150516_a(world, i + 4, j - 3, k, Blocks.torch, 2);
        this.func_150516_a(world, i + 4, j - 4, k, LOTRMod.slabSingle2, 9);
        this.func_150516_a(world, i, j - 3, k - 4, Blocks.torch, 3);
        this.func_150516_a(world, i, j - 4, k - 4, LOTRMod.slabSingle2, 9);
        this.func_150516_a(world, i, j - 3, k + 4, Blocks.torch, 4);
        this.func_150516_a(world, i, j - 4, k + 4, LOTRMod.slabSingle2, 9);
        for (int i3 = i - 1; i3 <= i + 1; ++i3) {
            this.func_150516_a(world, i3, j - 4, k - 1, LOTRMod.stairsRohanBrick, 2);
            this.func_150516_a(world, i3, j - 4, k + 1, LOTRMod.stairsRohanBrick, 3);
        }
        this.func_150516_a(world, i - 1, j - 4, k, LOTRMod.stairsRohanBrick, 0);
        this.func_150516_a(world, i + 1, j - 4, k, LOTRMod.stairsRohanBrick, 1);
        this.placeSpawnerChest(world, i, j - 5, k, LOTRMod.spawnerChestStone, 4, LOTREntityRohanBarrowWraith.class);
        LOTRChestContents.fillChest(world, random, i, j - 5, k, LOTRChestContents.ROHAN_BARROWS);
        this.func_150516_a(world, i, j - 3, k, LOTRMod.slabSingle2, 1);
        return true;
    }
}
