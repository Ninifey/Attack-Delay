// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDolGuldurOrcChieftain;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDolGuldurTower extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenDolGuldurTower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        final int radius = 6;
        final int radiusPlusOne = radius + 1;
        this.setOriginAndRotation(world, i, j, k, rotation, radiusPlusOne);
        final int sections = 3 + random.nextInt(3);
        final int sectionHeight = 6;
        final int topHeight = sections * sectionHeight;
        final double radiusD = radius - 0.5;
        final double radiusDPlusOne = radiusD + 1.0;
        final int wallThresholdMin = (int)(radiusD * radiusD);
        final int wallThresholdMax = (int)(radiusDPlusOne * radiusDPlusOne);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -radiusPlusOne; i2 <= radiusPlusOne; ++i2) {
                for (int k2 = -radiusPlusOne; k2 <= radiusPlusOne; ++k2) {
                    final int distSq = i2 * i2 + k2 * k2;
                    if (distSq < wallThresholdMax) {
                        final int j2 = this.getTopBlock(world, i2, k2) - 1;
                        final Block block = this.getBlock(world, i2, j2, k2);
                        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
                            return false;
                        }
                        if (j2 < minHeight) {
                            minHeight = j2;
                        }
                        if (j2 > maxHeight) {
                            maxHeight = j2;
                        }
                        if (maxHeight - minHeight > 16) {
                            return false;
                        }
                    }
                }
            }
        }
        for (int i3 = -radius; i3 <= radius; ++i3) {
            for (int k3 = -radius; k3 <= radius; ++k3) {
                final int distSq2 = i3 * i3 + k3 * k3;
                if (distSq2 < wallThresholdMax) {
                    for (int j3 = 0; (j3 == 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        if (distSq2 >= wallThresholdMin) {
                            this.placeRandomBrick(world, random, i3, j3, k3);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, j3, k3, Blocks.stonebrick, 0);
                        }
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
            }
        }
        for (int l = 0; l < sections; ++l) {
            final int sectionBase = l * sectionHeight;
            for (int j4 = sectionBase + 1; j4 <= sectionBase + sectionHeight; ++j4) {
                for (int i4 = -radius; i4 <= radius; ++i4) {
                    for (int k4 = -radius; k4 <= radius; ++k4) {
                        final int distSq3 = i4 * i4 + k4 * k4;
                        if (distSq3 < wallThresholdMax) {
                            if (distSq3 >= wallThresholdMin) {
                                this.placeRandomBrick(world, random, i4, j4, k4);
                            }
                            else if (j4 == sectionBase + sectionHeight) {
                                this.setBlockAndMetadata(world, i4, j4, k4, Blocks.stonebrick, 0);
                            }
                            else {
                                this.setAir(world, i4, j4, k4);
                            }
                        }
                    }
                }
            }
            for (int j4 = sectionBase + 2; j4 <= sectionBase + 3; ++j4) {
                for (int k2 = -1; k2 <= 1; ++k2) {
                    this.setBlockAndMetadata(world, -radius, j4, k2, LOTRMod.orcSteelBars, 0);
                    this.setBlockAndMetadata(world, radius, j4, k2, LOTRMod.orcSteelBars, 0);
                }
                for (int i4 = -1; i4 <= 1; ++i4) {
                    this.setBlockAndMetadata(world, i4, j4, -radius, LOTRMod.orcSteelBars, 0);
                }
            }
            if (l > 0) {
                this.setAir(world, 0, sectionBase, 0);
                for (int i2 = -1; i2 <= 1; ++i2) {
                    for (int k2 = -1; k2 <= 1; ++k2) {
                        final int i5 = Math.abs(i2);
                        final int k5 = Math.abs(k2);
                        if (i5 == 1 || k5 == 1) {
                            this.setBlockAndMetadata(world, i2, sectionBase + 1, k2, LOTRMod.wall2, 8);
                        }
                        if (i5 == 1 && k5 == 1) {
                            this.setBlockAndMetadata(world, i2, sectionBase + 2, k2, LOTRMod.wall2, 8);
                            this.placeSkull(world, random, i2, sectionBase + 2, k2);
                        }
                    }
                }
            }
            else {
                for (int i2 = -1; i2 <= 1; ++i2) {
                    for (int j3 = sectionBase + 1; j3 <= sectionBase + 3; ++j3) {
                        this.setAir(world, i2, j3, -radius);
                    }
                    this.setBlockAndMetadata(world, i2, sectionBase, -radius, Blocks.stonebrick, 0);
                }
                this.placeRandomStairs(world, random, -1, sectionBase + 3, -radius, 4);
                this.placeRandomStairs(world, random, 1, sectionBase + 3, -radius, 5);
                this.placeWallBanner(world, 0, sectionBase + 6, -radius, LOTRItemBanner.BannerType.DOL_GULDUR, 2);
                for (int i2 = -5; i2 <= 5; ++i2) {
                    this.setBlockAndMetadata(world, i2, sectionBase, 0, LOTRMod.guldurilBrick, 4);
                }
                for (int k6 = -6; k6 <= 3; ++k6) {
                    this.setBlockAndMetadata(world, 0, sectionBase, k6, LOTRMod.guldurilBrick, 4);
                }
                this.setBlockAndMetadata(world, 0, sectionBase + 1, 0, LOTRMod.guldurilBrick, 4);
                this.setBlockAndMetadata(world, 0, sectionBase + 2, 0, LOTRMod.wall2, 8);
                this.placeSkull(world, random, 0, sectionBase + 3, 0);
            }
            for (int j4 = sectionBase + 1; j4 <= sectionBase + 5; ++j4) {
                this.setBlockAndMetadata(world, -2, j4, -5, LOTRMod.wood, 2);
                this.setBlockAndMetadata(world, 2, j4, -5, LOTRMod.wood, 2);
                this.setBlockAndMetadata(world, 5, j4, -2, LOTRMod.wood, 2);
                this.setBlockAndMetadata(world, 5, j4, 2, LOTRMod.wood, 2);
                this.setBlockAndMetadata(world, -3, j4, 4, LOTRMod.wood, 2);
                this.setBlockAndMetadata(world, 3, j4, 4, LOTRMod.wood, 2);
                this.setBlockAndMetadata(world, -5, j4, -2, LOTRMod.wood, 2);
                this.setBlockAndMetadata(world, -5, j4, 2, LOTRMod.wood, 2);
            }
            this.setBlockAndMetadata(world, -3, sectionBase + 4, 3, LOTRMod.morgulTorch, 4);
            this.setBlockAndMetadata(world, 3, sectionBase + 4, 3, LOTRMod.morgulTorch, 4);
            this.setBlockAndMetadata(world, 4, sectionBase + 4, -2, LOTRMod.morgulTorch, 1);
            this.setBlockAndMetadata(world, 4, sectionBase + 4, 2, LOTRMod.morgulTorch, 1);
            this.setBlockAndMetadata(world, -2, sectionBase + 4, -4, LOTRMod.morgulTorch, 3);
            this.setBlockAndMetadata(world, 2, sectionBase + 4, -4, LOTRMod.morgulTorch, 3);
            this.setBlockAndMetadata(world, -4, sectionBase + 4, -2, LOTRMod.morgulTorch, 2);
            this.setBlockAndMetadata(world, -4, sectionBase + 4, 2, LOTRMod.morgulTorch, 2);
            this.setBlockAndMetadata(world, -3, sectionBase + 5, 3, Blocks.stone_brick_stairs, 6);
            this.setBlockAndMetadata(world, 3, sectionBase + 5, 3, Blocks.stone_brick_stairs, 6);
            this.setBlockAndMetadata(world, 4, sectionBase + 5, -2, Blocks.stone_brick_stairs, 5);
            this.setBlockAndMetadata(world, 5, sectionBase + 5, -1, Blocks.stone_brick_stairs, 7);
            this.setBlockAndMetadata(world, 5, sectionBase + 5, 1, Blocks.stone_brick_stairs, 6);
            this.setBlockAndMetadata(world, 4, sectionBase + 5, 2, Blocks.stone_brick_stairs, 5);
            this.setBlockAndMetadata(world, -2, sectionBase + 5, -4, Blocks.stone_brick_stairs, 7);
            this.setBlockAndMetadata(world, -1, sectionBase + 5, -5, Blocks.stone_brick_stairs, 4);
            this.setBlockAndMetadata(world, 1, sectionBase + 5, -5, Blocks.stone_brick_stairs, 5);
            this.setBlockAndMetadata(world, 2, sectionBase + 5, -4, Blocks.stone_brick_stairs, 7);
            this.setBlockAndMetadata(world, -4, sectionBase + 5, -2, Blocks.stone_brick_stairs, 4);
            this.setBlockAndMetadata(world, -5, sectionBase + 5, -1, Blocks.stone_brick_stairs, 7);
            this.setBlockAndMetadata(world, -5, sectionBase + 5, 1, Blocks.stone_brick_stairs, 6);
            this.setBlockAndMetadata(world, -4, sectionBase + 5, 2, Blocks.stone_brick_stairs, 4);
            for (int step = 0; step <= 2; ++step) {
                this.setBlockAndMetadata(world, 1 - step, sectionBase + 1 + step, 4, Blocks.stone_brick_stairs, 0);
                for (int j3 = sectionBase + 1; j3 <= sectionBase + step; ++j3) {
                    this.setBlockAndMetadata(world, 1 - step, j3, 4, Blocks.stonebrick, 0);
                }
            }
            for (int k6 = 4; k6 <= 5; ++k6) {
                for (int j3 = sectionBase + 1; j3 <= sectionBase + 3; ++j3) {
                    this.setBlockAndMetadata(world, -2, j3, k6, Blocks.stonebrick, 0);
                }
            }
            for (int i2 = -2; i2 <= 0; ++i2) {
                this.setAir(world, i2, sectionBase + sectionHeight, 5);
            }
            for (int step = 0; step <= 2; ++step) {
                this.setBlockAndMetadata(world, -1 + step, sectionBase + 4 + step, 5, Blocks.stone_brick_stairs, 1);
                this.setBlockAndMetadata(world, -1 + step, sectionBase + 3 + step, 5, Blocks.stonebrick, 0);
                this.setBlockAndMetadata(world, -1 + step, sectionBase + 2 + step, 5, Blocks.stone_brick_stairs, 4);
            }
            this.setBlockAndMetadata(world, 2, sectionBase + 5, 5, Blocks.stone_brick_stairs, 4);
        }
        this.placeChest(world, random, -1, 1, 5, 0, LOTRChestContents.DOL_GULDUR_TOWER);
        for (int k7 = -3; k7 <= 3; k7 += 6) {
            for (int step2 = 0; step2 <= 3; ++step2) {
                this.placeBrickSupports(world, random, -9 + step2, k7);
                this.placeBrickSupports(world, random, 9 - step2, k7);
                this.placeRandomStairs(world, random, -9 + step2, 1 + step2 * 2, k7, 1);
                this.placeRandomStairs(world, random, 9 - step2, 1 + step2 * 2, k7, 0);
                for (int j4 = 1; j4 <= step2 * 2; ++j4) {
                    this.placeRandomBrick(world, random, -9 + step2, j4, k7);
                    this.placeRandomBrick(world, random, 9 - step2, j4, k7);
                }
            }
        }
        for (int i3 = -3; i3 <= 3; i3 += 6) {
            for (int step2 = 0; step2 <= 3; ++step2) {
                this.placeBrickSupports(world, random, i3, -9 + step2);
                this.placeBrickSupports(world, random, i3, 9 - step2);
                this.placeRandomStairs(world, random, i3, 1 + step2 * 2, -9 + step2, 2);
                this.placeRandomStairs(world, random, i3, 1 + step2 * 2, 9 - step2, 3);
                for (int j4 = 1; j4 <= step2 * 2; ++j4) {
                    this.placeRandomBrick(world, random, i3, j4, -9 + step2);
                    this.placeRandomBrick(world, random, i3, j4, 9 - step2);
                }
            }
        }
        for (int i3 = -radius; i3 <= radius; ++i3) {
            for (int k3 = -radius; k3 <= radius; ++k3) {
                final int distSq2 = i3 * i3 + k3 * k3;
                if (distSq2 < wallThresholdMax) {
                    if (distSq2 >= (int)Math.pow(radiusD - 0.25, 2.0)) {
                        final int i6 = Math.abs(i3);
                        final int k8 = Math.abs(k3);
                        this.setBlockAndMetadata(world, i3, topHeight + 1, k3, LOTRMod.wall2, 8);
                        if (i6 >= 3 && k8 >= 3) {
                            this.setBlockAndMetadata(world, i3, topHeight + 2, k3, LOTRMod.wall2, 8);
                            if (i6 == 4 && k8 == 4) {
                                this.setBlockAndMetadata(world, i3, topHeight + 3, k3, LOTRMod.wall2, 8);
                                this.setBlockAndMetadata(world, i3, topHeight + 4, k3, LOTRMod.wall2, 8);
                                this.setBlockAndMetadata(world, i3, topHeight + 5, k3, LOTRMod.morgulTorch, 5);
                            }
                        }
                    }
                }
            }
        }
        this.setAir(world, -2, topHeight + 1, 5);
        for (int i3 = -2; i3 <= 2; i3 += 4) {
            for (int step2 = 0; step2 <= 4; ++step2) {
                final int j4 = topHeight + 1 + step2 * 2;
                int k2 = -9 + step2;
                this.placeRandomStairs(world, random, i3, j4 - 2, k2, 7);
                for (int j5 = j4 - 1; j5 <= j4 + 1; ++j5) {
                    this.placeRandomBrick(world, random, i3, j5, k2);
                }
                this.placeRandomStairs(world, random, i3, j4 + 2, k2, 2);
                k2 = 9 - step2;
                this.placeRandomStairs(world, random, i3, j4 - 2, k2, 6);
                for (int j5 = j4 - 1; j5 <= j4 + 1; ++j5) {
                    this.placeRandomBrick(world, random, i3, j5, k2);
                }
                this.placeRandomStairs(world, random, i3, j4 + 2, k2, 3);
            }
            for (int j6 = topHeight - 4; j6 <= topHeight + 2; ++j6) {
                for (int k6 = -9; k6 <= -8; ++k6) {
                    this.placeRandomBrick(world, random, i3, j6, k6);
                }
                for (int k6 = 8; k6 <= 9; ++k6) {
                    this.placeRandomBrick(world, random, i3, j6, k6);
                }
            }
            this.placeRandomBrick(world, random, i3, topHeight - 1, -7);
            this.placeRandomBrick(world, random, i3, topHeight, -7);
            this.setBlockAndMetadata(world, i3, topHeight + 1, -7, LOTRMod.wall2, 8);
            this.placeRandomBrick(world, random, i3, topHeight - 1, 7);
            this.placeRandomBrick(world, random, i3, topHeight, 7);
            this.setBlockAndMetadata(world, i3, topHeight + 1, 7, LOTRMod.wall2, 8);
            this.placeRandomStairs(world, random, i3, topHeight - 4, -9, 6);
            this.placeRandomStairs(world, random, i3, topHeight - 5, -8, 6);
            this.placeRandomStairs(world, random, i3, topHeight - 4, 9, 7);
            this.placeRandomStairs(world, random, i3, topHeight - 5, 8, 7);
        }
        for (int k7 = -2; k7 <= 2; k7 += 4) {
            for (int step2 = 0; step2 <= 4; ++step2) {
                final int j4 = topHeight + 1 + step2 * 2;
                int i4 = -9 + step2;
                this.placeRandomStairs(world, random, i4, j4 - 2, k7, 4);
                for (int j5 = j4 - 1; j5 <= j4 + 1; ++j5) {
                    this.placeRandomBrick(world, random, i4, j5, k7);
                }
                this.placeRandomStairs(world, random, i4, j4 + 2, k7, 1);
                i4 = 9 - step2;
                this.placeRandomStairs(world, random, i4, j4 - 2, k7, 5);
                for (int j5 = j4 - 1; j5 <= j4 + 1; ++j5) {
                    this.placeRandomBrick(world, random, i4, j5, k7);
                }
                this.placeRandomStairs(world, random, i4, j4 + 2, k7, 0);
            }
            for (int j6 = topHeight - 4; j6 <= topHeight + 2; ++j6) {
                for (int i2 = -9; i2 <= -8; ++i2) {
                    this.placeRandomBrick(world, random, i2, j6, k7);
                }
                for (int i2 = 8; i2 <= 9; ++i2) {
                    this.placeRandomBrick(world, random, i2, j6, k7);
                }
            }
            this.placeRandomBrick(world, random, -7, topHeight - 1, k7);
            this.placeRandomBrick(world, random, -7, topHeight, k7);
            this.setBlockAndMetadata(world, -7, topHeight + 1, k7, LOTRMod.wall2, 8);
            this.placeRandomBrick(world, random, 7, topHeight - 1, k7);
            this.placeRandomBrick(world, random, 7, topHeight, k7);
            this.setBlockAndMetadata(world, 7, topHeight + 1, k7, LOTRMod.wall2, 8);
            this.placeRandomStairs(world, random, -9, topHeight - 4, k7, 5);
            this.placeRandomStairs(world, random, -8, topHeight - 5, k7, 5);
            this.placeRandomStairs(world, random, 9, topHeight - 4, k7, 4);
            this.placeRandomStairs(world, random, 8, topHeight - 5, k7, 4);
        }
        this.spawnNPCAndSetHome(new LOTREntityDolGuldurOrcChieftain(world), world, 0, topHeight + 1, 0, 16);
        this.setBlockAndMetadata(world, 0, topHeight + 1, -4, LOTRMod.commandTable, 0);
        return true;
    }
    
    private void placeBrickSupports(final World world, final Random random, final int i, final int k) {
        for (int j = 0; !this.isOpaque(world, i, j, k) && this.getY(j) >= 0; --j) {
            this.placeRandomBrick(world, random, i, j, k);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(6) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 9);
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 8);
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (random.nextInt(6) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDolGuldurBrickCracked, meta);
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDolGuldurBrick, meta);
        }
    }
}
