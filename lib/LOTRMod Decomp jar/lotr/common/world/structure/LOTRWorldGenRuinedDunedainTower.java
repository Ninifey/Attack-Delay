// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedDunedainTower extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenRuinedDunedainTower(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        final int radius = 4 + random.nextInt(2);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
            switch (rotation) {
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
        final int sections = 4 + random.nextInt(3);
        final int sectionHeight = 4 + random.nextInt(4);
        final int maxHeight = (sections - 1) * sectionHeight;
        int wallThresholdMin = radius;
        wallThresholdMin *= wallThresholdMin;
        int wallThresholdMax = radius + 1;
        wallThresholdMax *= wallThresholdMax;
        for (int i2 = i - radius; i2 <= i + radius; ++i2) {
            for (int k2 = k - radius; k2 <= k + radius; ++k2) {
                final int i3 = i2 - i;
                final int k3 = k2 - k;
                final int distSq = i3 * i3 + k3 * k3;
                if (distSq < wallThresholdMax) {
                    if (distSq >= wallThresholdMin) {
                        for (int j2 = j - 1; j2 >= 0; --j2) {
                            final Block block = world.getBlock(i2, j2, k2);
                            this.placeRandomBrick(world, random, i2, j2, k2);
                            if (block == Blocks.grass || block == Blocks.dirt) {
                                break;
                            }
                            if (block == Blocks.stone) {
                                break;
                            }
                            if (!super.restrictions && block.isOpaqueCube()) {
                                break;
                            }
                        }
                        final int j3 = j + maxHeight;
                        for (int j4 = j; j4 <= j3; ++j4) {
                            if (random.nextInt(20) != 0) {
                                this.placeRandomBrick(world, random, i2, j4, k2);
                            }
                        }
                        for (int j5 = j3 + 1 + random.nextInt(3), j6 = j3; j6 <= j5; ++j6) {
                            this.placeRandomBrick(world, random, i2, j6, k2);
                        }
                    }
                    else {
                        for (int j2 = j + sectionHeight; j2 <= j + maxHeight; j2 += sectionHeight) {
                            if (random.nextInt(6) != 0) {
                                this.func_150516_a(world, i2, j2, k2, (Block)Blocks.stone_slab, 8);
                            }
                        }
                    }
                }
            }
        }
        for (int j7 = j + sectionHeight; j7 < j + maxHeight; j7 += sectionHeight) {
            for (int j8 = j7 + 2; j8 <= j7 + 3; ++j8) {
                for (int i4 = i - 1; i4 <= i + 1; ++i4) {
                    this.placeIronBars(world, random, i4, j8, k - radius);
                    this.placeIronBars(world, random, i4, j8, k + radius);
                }
                for (int k4 = k - 1; k4 <= k + 1; ++k4) {
                    this.placeIronBars(world, random, i - radius, j8, k4);
                    this.placeIronBars(world, random, i + radius, j8, k4);
                }
            }
        }
        this.func_150516_a(world, i, j + maxHeight, k, (Block)Blocks.stone_slab, 8);
        this.func_150516_a(world, i, j + maxHeight + 1, k, LOTRMod.chestStone, rotation + 2);
        LOTRChestContents.fillChest(world, random, i, j + maxHeight + 1, k, LOTRChestContents.DUNEDAIN_TOWER);
        switch (rotation) {
            case 0: {
                for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                    for (int height = j + 1 + random.nextInt(3), j9 = j; j9 <= height; ++j9) {
                        this.func_150516_a(world, i2, j9, k - radius, Blocks.air, 0);
                    }
                }
                break;
            }
            case 1: {
                for (int k5 = k - 1; k5 <= k + 1; ++k5) {
                    for (int height = j + 1 + random.nextInt(3), j9 = j; j9 <= height; ++j9) {
                        this.func_150516_a(world, i + radius, j9, k5, Blocks.air, 0);
                    }
                }
                break;
            }
            case 2: {
                for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                    for (int height = j + 1 + random.nextInt(3), j9 = j; j9 <= height; ++j9) {
                        this.func_150516_a(world, i2, j9, k + radius, Blocks.air, 0);
                    }
                }
                break;
            }
            case 3: {
                for (int k5 = k - 1; k5 <= k + 1; ++k5) {
                    for (int height = j + 1 + random.nextInt(3), j9 = j; j9 <= height; ++j9) {
                        this.func_150516_a(world, i - radius, j9, k5, Blocks.air, 0);
                    }
                }
                break;
            }
        }
        for (int l = 0; l < 16; ++l) {
            final int i5 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            final int k4 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            final int j10 = world.getHeightValue(i5, k4);
            if (world.getBlock(i5, j10 - 1, k4) == Blocks.grass) {
                final int randomFeature = random.nextInt(4);
                boolean flag = true;
                if (randomFeature == 0) {
                    if (!LOTRMod.isOpaque(world, i5, j10, k4)) {
                        this.func_150516_a(world, i5, j10, k4, (Block)Blocks.stone_slab, random.nextBoolean() ? 0 : 5);
                    }
                }
                else {
                    for (int j11 = j10; j11 < j10 + randomFeature && flag; flag = !LOTRMod.isOpaque(world, i5, j11, k4), ++j11) {}
                    if (flag) {
                        for (int j11 = j10; j11 < j10 + randomFeature; ++j11) {
                            this.placeRandomBrick(world, random, i5, j11, k4);
                        }
                    }
                }
                if (flag) {
                    world.getBlock(i5, j10 - 1, k4).onPlantGrow(world, i5, j10 - 1, k4, i5, j10, k4);
                }
            }
        }
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(5) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick2, 4 + random.nextInt(2));
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick2, 3);
        }
    }
    
    private void placeIronBars(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, Blocks.air, 0);
        }
        else {
            this.func_150516_a(world, i, j, k, Blocks.iron_bars, 0);
        }
    }
}
