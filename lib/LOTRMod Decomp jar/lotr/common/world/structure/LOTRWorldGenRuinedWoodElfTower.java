// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedWoodElfTower extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenRuinedWoodElfTower(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && (world.getBlock(i, j - 1, k) != Blocks.grass || world.getBiomeGenForCoords(i, k) != LOTRBiome.mirkwoodCorrupted)) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        final int radius = 6;
        final int radiusPlusOne = radius + 1;
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += radiusPlusOne;
                break;
            }
            case 1: {
                i -= radiusPlusOne;
                break;
            }
            case 2: {
                k -= radiusPlusOne;
                break;
            }
            case 3: {
                i += radiusPlusOne;
                break;
            }
        }
        if (super.restrictions) {
            for (int i2 = i - radiusPlusOne; i2 <= i + radiusPlusOne; ++i2) {
                for (int k2 = k - radiusPlusOne; k2 <= k + radiusPlusOne; ++k2) {
                    final int i3 = i2 - i;
                    final int k3 = k2 - k;
                    if (i3 * i3 + k3 * k3 <= radiusPlusOne * radiusPlusOne) {
                        final int j2 = world.getTopSolidOrLiquidBlock(i2, k2) - 1;
                        final Block block = world.getBlock(i2, j2, k2);
                        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone && !block.isWood((IBlockAccess)world, i2, j2, k2) && !block.isLeaves((IBlockAccess)world, i2, j2, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        final int sections = 3 + random.nextInt(3);
        final int sectionHeight = 8;
        final int topHeight = j + sections * sectionHeight;
        final int wallThresholdMin = radius * radius;
        final int wallThresholdMax = radiusPlusOne * radiusPlusOne;
        for (int i4 = i - radius; i4 <= i + radius; ++i4) {
            for (int k4 = k - radius; k4 <= k + radius; ++k4) {
                final int i5 = i4 - i;
                final int k5 = k4 - k;
                final int distSq = i5 * i5 + k5 * k5;
                if (distSq < wallThresholdMax) {
                    int j3;
                    for (int start = j3 = j - sectionHeight; (j3 == start || !LOTRMod.isOpaque(world, i4, j3, k4)) && j3 >= 0; --j3) {
                        if (j3 != start || distSq >= wallThresholdMin) {
                            this.placeRandomStoneBrick(world, random, i4, j3, k4);
                        }
                        else {
                            this.placeDungeonBlock(world, random, i4, j3, k4);
                        }
                        this.setGrassToDirt(world, i4, j3 - 1, k4);
                    }
                }
            }
        }
        for (int l = -1; l < sections; ++l) {
            final int sectionBase = j + l * sectionHeight;
            for (int j4 = sectionBase + 1; j4 <= sectionBase + sectionHeight; ++j4) {
                for (int i6 = i - radius; i6 <= i + radius; ++i6) {
                    for (int k6 = k - radius; k6 <= k + radius; ++k6) {
                        final int i7 = i6 - i;
                        final int k7 = k6 - k;
                        final int distSq2 = i7 * i7 + k7 * k7;
                        if (distSq2 < wallThresholdMax) {
                            if (distSq2 >= wallThresholdMin) {
                                this.placeRandomStoneBrick(world, random, i6, j4, k6);
                                if (l == sections - 1 && j4 == sectionBase + sectionHeight) {
                                    this.placeRandomStoneBrick(world, random, i6, j4 + 1, k6);
                                    this.placeRandomStoneSlab(world, random, i6, j4 + 2, k6, false);
                                }
                            }
                            else if (j4 == sectionBase + sectionHeight && (Math.abs(i7) > 2 || Math.abs(k7) > 2)) {
                                this.placeDungeonBlock(world, random, i6, j4, k6);
                            }
                            else {
                                this.func_150516_a(world, i6, j4, k6, Blocks.air, 0);
                            }
                            this.setGrassToDirt(world, i6, j4 - 1, k6);
                        }
                    }
                }
                this.placeDungeonBlock(world, random, i, j4, k);
            }
            for (int l2 = 0; l2 < 2; ++l2) {
                final int stairBase = sectionBase + l2 * 4;
                this.placeRandomStoneSlab(world, random, i, stairBase + 1, k + 1, false);
                this.placeRandomStoneSlab(world, random, i, stairBase + 1, k + 2, false);
                this.placeRandomStoneSlab(world, random, i + 1, stairBase + 2, k, false);
                this.placeRandomStoneSlab(world, random, i + 2, stairBase + 2, k, false);
                this.placeRandomStoneSlab(world, random, i, stairBase + 3, k - 1, false);
                this.placeRandomStoneSlab(world, random, i, stairBase + 3, k - 2, false);
                this.placeRandomStoneSlab(world, random, i - 1, stairBase + 4, k, false);
                this.placeRandomStoneSlab(world, random, i - 2, stairBase + 4, k, false);
                for (int i8 = 0; i8 <= 1; ++i8) {
                    for (int k8 = 0; k8 <= 1; ++k8) {
                        this.placeRandomStoneSlab(world, random, i + 1 + i8, stairBase + 1, k + 1 + k8, true);
                        this.placeRandomStoneSlab(world, random, i + 1 + i8, stairBase + 2, k - 2 + k8, true);
                        this.placeRandomStoneSlab(world, random, i - 2 + i8, stairBase + 3, k - 2 + k8, true);
                        this.placeRandomStoneSlab(world, random, i - 2 + i8, stairBase + 4, k + 1 + k8, true);
                    }
                }
            }
            if (l > 0) {
                for (int j4 = sectionBase + 1; j4 <= sectionBase + 4; ++j4) {
                    for (int i6 = i - 1; i6 <= i + 1; ++i6) {
                        this.func_150516_a(world, i6, j4, k - 6, Blocks.air, 0);
                        this.func_150516_a(world, i6, j4, k + 6, Blocks.air, 0);
                    }
                    for (int k9 = k - 1; k9 <= k + 1; ++k9) {
                        this.func_150516_a(world, i - 6, j4, k9, Blocks.air, 0);
                        this.func_150516_a(world, i + 6, j4, k9, Blocks.air, 0);
                    }
                }
                this.placeRandomStoneStairs(world, random, i - 1, sectionBase + 4, k - 6, 5);
                this.placeRandomStoneStairs(world, random, i + 1, sectionBase + 4, k - 6, 4);
                this.placeRandomStoneStairs(world, random, i - 1, sectionBase + 4, k + 6, 5);
                this.placeRandomStoneStairs(world, random, i + 1, sectionBase + 4, k + 6, 4);
                this.placeRandomStoneStairs(world, random, i - 6, sectionBase + 4, k - 1, 7);
                this.placeRandomStoneStairs(world, random, i - 6, sectionBase + 4, k + 1, 6);
                this.placeRandomStoneStairs(world, random, i + 6, sectionBase + 4, k - 1, 7);
                this.placeRandomStoneStairs(world, random, i + 6, sectionBase + 4, k + 1, 6);
            }
        }
        for (int j5 = topHeight + 2; j5 <= topHeight + 3; ++j5) {
            this.placeRandomStoneBrick(world, random, i + 6, j5, k - 3);
            this.placeRandomStoneBrick(world, random, i + 6, j5, k);
            this.placeRandomStoneBrick(world, random, i + 6, j5, k + 3);
            this.placeRandomStoneBrick(world, random, i - 3, j5, k + 6);
            this.placeRandomStoneBrick(world, random, i, j5, k + 6);
            this.placeRandomStoneBrick(world, random, i + 3, j5, k + 6);
            this.placeRandomStoneBrick(world, random, i - 6, j5, k - 3);
            this.placeRandomStoneBrick(world, random, i - 6, j5, k);
            this.placeRandomStoneBrick(world, random, i - 6, j5, k + 3);
            this.placeRandomStoneBrick(world, random, i - 3, j5, k - 6);
            this.placeRandomStoneBrick(world, random, i, j5, k - 6);
            this.placeRandomStoneBrick(world, random, i + 3, j5, k - 6);
        }
        this.placeRandomStoneBrick(world, random, i + 6, topHeight + 2, k - 2);
        this.placeRandomStoneBrick(world, random, i + 6, topHeight + 2, k + 2);
        this.placeRandomStoneBrick(world, random, i - 2, topHeight + 2, k + 6);
        this.placeRandomStoneBrick(world, random, i + 2, topHeight + 2, k + 6);
        this.placeRandomStoneBrick(world, random, i - 6, topHeight + 2, k - 2);
        this.placeRandomStoneBrick(world, random, i - 6, topHeight + 2, k + 2);
        this.placeRandomStoneBrick(world, random, i - 2, topHeight + 2, k - 6);
        this.placeRandomStoneBrick(world, random, i + 2, topHeight + 2, k - 6);
        for (int j5 = j - sectionHeight - 6; j5 <= j - sectionHeight - 1; ++j5) {
            this.placeDungeonBlock(world, random, i - 6, j5, k);
            this.placeDungeonBlock(world, random, i - 5, j5, k - 2);
            this.placeDungeonBlock(world, random, i - 5, j5, k - 1);
            this.placeDungeonBlock(world, random, i - 5, j5, k + 1);
            this.placeDungeonBlock(world, random, i - 5, j5, k + 2);
            this.placeDungeonBlock(world, random, i - 4, j5, k - 3);
            this.placeDungeonBlock(world, random, i - 4, j5, k + 3);
            this.placeDungeonBlock(world, random, i - 3, j5, k - 5);
            this.placeDungeonBlock(world, random, i - 3, j5, k - 4);
            this.placeDungeonBlock(world, random, i - 3, j5, k + 4);
            this.placeDungeonBlock(world, random, i - 3, j5, k + 5);
            this.placeDungeonBlock(world, random, i - 2, j5, k - 6);
            this.placeDungeonBlock(world, random, i - 2, j5, k + 6);
            this.placeDungeonBlock(world, random, i - 1, j5, k - 6);
            this.placeDungeonBlock(world, random, i - 1, j5, k + 6);
            this.placeDungeonBlock(world, random, i, j5, k - 6);
            this.placeDungeonBlock(world, random, i, j5, k + 6);
            this.placeDungeonBlock(world, random, i + 1, j5, k - 5);
            this.placeDungeonBlock(world, random, i + 1, j5, k - 4);
            this.placeDungeonBlock(world, random, i + 1, j5, k + 4);
            this.placeDungeonBlock(world, random, i + 1, j5, k + 5);
            this.placeDungeonBlock(world, random, i + 2, j5, k - 3);
            this.placeDungeonBlock(world, random, i + 2, j5, k + 3);
            this.placeDungeonBlock(world, random, i + 3, j5, k - 2);
            this.placeDungeonBlock(world, random, i + 3, j5, k + 2);
            this.placeDungeonBlock(world, random, i + 4, j5, k - 2);
            this.placeDungeonBlock(world, random, i + 4, j5, k + 2);
            this.placeDungeonBlock(world, random, i + 5, j5, k - 1);
            this.placeDungeonBlock(world, random, i + 5, j5, k);
            this.placeDungeonBlock(world, random, i + 5, j5, k + 1);
            if (j5 == j - sectionHeight - 6 || j5 == j - sectionHeight - 1) {
                this.placeDungeonBlock(world, random, i - 5, j5, k);
                for (int k4 = k - 2; k4 <= k + 2; ++k4) {
                    this.placeDungeonBlock(world, random, i - 4, j5, k4);
                }
                for (int k4 = k - 3; k4 <= k + 3; ++k4) {
                    this.placeDungeonBlock(world, random, i - 3, j5, k4);
                }
                for (int k4 = k - 5; k4 <= k + 5; ++k4) {
                    this.placeDungeonBlock(world, random, i - 2, j5, k4);
                    this.placeDungeonBlock(world, random, i - 1, j5, k4);
                    this.placeDungeonBlock(world, random, i, j5, k4);
                }
                for (int k4 = k - 3; k4 <= k + 3; ++k4) {
                    this.placeDungeonBlock(world, random, i + 1, j5, k4);
                }
                for (int k4 = k - 2; k4 <= k + 2; ++k4) {
                    this.placeDungeonBlock(world, random, i + 2, j5, k4);
                }
                for (int k4 = k - 1; k4 <= k + 1; ++k4) {
                    this.placeDungeonBlock(world, random, i + 3, j5, k4);
                    this.placeDungeonBlock(world, random, i + 4, j5, k4);
                }
            }
            else {
                this.func_150516_a(world, i - 5, j5, k, Blocks.air, 0);
                for (int k4 = k - 2; k4 <= k + 2; ++k4) {
                    this.func_150516_a(world, i - 4, j5, k4, Blocks.air, 0);
                }
                for (int k4 = k - 3; k4 <= k + 3; ++k4) {
                    this.func_150516_a(world, i - 3, j5, k4, Blocks.air, 0);
                }
                for (int k4 = k - 5; k4 <= k + 5; ++k4) {
                    this.func_150516_a(world, i - 2, j5, k4, Blocks.air, 0);
                    this.func_150516_a(world, i - 1, j5, k4, Blocks.air, 0);
                    this.func_150516_a(world, i, j5, k4, Blocks.air, 0);
                }
                for (int k4 = k - 3; k4 <= k + 3; ++k4) {
                    this.func_150516_a(world, i + 1, j5, k4, Blocks.air, 0);
                }
                for (int k4 = k - 2; k4 <= k + 2; ++k4) {
                    this.func_150516_a(world, i + 2, j5, k4, Blocks.air, 0);
                }
                for (int k4 = k - 1; k4 <= k + 1; ++k4) {
                    this.func_150516_a(world, i + 3, j5, k4, Blocks.air, 0);
                    this.func_150516_a(world, i + 4, j5, k4, Blocks.air, 0);
                }
            }
        }
        for (int i4 = i - 2; i4 <= i; ++i4) {
            this.placeDungeonBlock(world, random, i4, j - sectionHeight - 2, k - 5);
            this.placeDungeonBlock(world, random, i4, j - sectionHeight - 2, k - 4);
            this.placeDungeonBlock(world, random, i4, j - sectionHeight - 2, k + 4);
            this.placeDungeonBlock(world, random, i4, j - sectionHeight - 2, k + 5);
        }
        for (int k10 = k - 1; k10 <= k + 1; ++k10) {
            this.placeDungeonBlock(world, random, i + 3, j - sectionHeight - 2, k10);
            this.placeDungeonBlock(world, random, i + 4, j - sectionHeight - 2, k10);
        }
        for (int j5 = j - sectionHeight - 5; j5 <= j - sectionHeight - 3; ++j5) {
            for (int i9 = i - 2; i9 <= i; ++i9) {
                if (random.nextInt(4) == 0) {
                    this.func_150516_a(world, i9, j5, k - 4, LOTRMod.woodElfBars, 0);
                }
                if (random.nextInt(4) == 0) {
                    this.func_150516_a(world, i9, j5, k + 4, LOTRMod.woodElfBars, 0);
                }
            }
            for (int k4 = k - 1; k4 <= k + 1; ++k4) {
                if (random.nextInt(4) == 0) {
                    this.func_150516_a(world, i + 3, j5, k4, LOTRMod.woodElfBars, 0);
                }
            }
        }
        this.placeSkull(world, random, i - 2, j - sectionHeight - 5, k - 5, 3, 1);
        this.placeSkull(world, random, i + 2, j - sectionHeight - 5, k + 5, 3, 1);
        this.placeSkull(world, random, i + 4, j - sectionHeight - 5, k - 1, 1, 3);
        for (int spiders = MathHelper.getRandomIntegerInRange(random, 4, 6); spiders > 0; --spiders) {
            final LOTREntityMirkwoodSpider spider = new LOTREntityMirkwoodSpider(world);
            spider.setLocationAndAngles(i - 1 + 0.5, (double)(j - sectionHeight - 5), k + 0.5, 0.0f, 0.0f);
            spider.setHomeArea(i, j + 1, k, 8);
            spider.onSpawnWithEgg(null);
            spider.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)spider);
        }
        new LOTRWorldGenWebOfUngoliant(super.notifyChanges, 24).generate(world, random, i - 1, j - sectionHeight - 5, k);
        this.placeDungeonBlock(world, random, i + 4, j - sectionHeight - 5, k);
        this.func_150516_a(world, i + 4, j - sectionHeight - 5, k - 1, (Block)Blocks.chest, 0);
        LOTRChestContents.fillChest(world, random, i + 4, j - sectionHeight - 5, k - 1, LOTRChestContents.MIRKWOOD_LOOT);
        this.func_150516_a(world, i + 4, j - sectionHeight - 5, k + 1, (Block)Blocks.chest, 0);
        LOTRChestContents.fillChest(world, random, i + 4, j - sectionHeight - 5, k + 1, LOTRChestContents.MIRKWOOD_LOOT);
        this.func_150516_a(world, i - 5, j - sectionHeight - 1, k, Blocks.air, 0);
        this.func_150516_a(world, i - 5, j - sectionHeight, k, Blocks.air, 0);
        switch (rotation) {
            case 0: {
                final int k9 = k - radius;
                for (int i9 = i - 1; i9 <= i + 1; ++i9) {
                    for (int j4 = j + 1; j4 <= j + 3; ++j4) {
                        this.setAir(world, i9, j4, k9);
                    }
                }
                break;
            }
            case 1: {
                final int i9 = i + radius;
                for (int k9 = k - 1; k9 <= k + 1; ++k9) {
                    for (int j4 = j + 1; j4 <= j + 3; ++j4) {
                        this.setAir(world, i9, j4, k9);
                    }
                }
                break;
            }
            case 2: {
                final int k9 = k + radius;
                for (int i9 = i - 1; i9 <= i + 1; ++i9) {
                    for (int j4 = j + 1; j4 <= j + 3; ++j4) {
                        this.setAir(world, i9, j4, k9);
                    }
                }
                break;
            }
            case 3: {
                final int i9 = i - radius;
                for (int k9 = k - 1; k9 <= k + 1; ++k9) {
                    for (int j4 = j + 1; j4 <= j + 3; ++j4) {
                        this.setAir(world, i9, j4, k9);
                    }
                }
                break;
            }
        }
        return true;
    }
    
    private void placeRandomStoneBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(20) == 0) {
            return;
        }
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 5);
                break;
            }
            case 1: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 6);
                break;
            }
            case 2: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 7);
                break;
            }
        }
    }
    
    private void placeRandomStoneSlab(final World world, final Random random, final int i, final int j, final int k, final boolean inverted) {
        if (random.nextInt(8) == 0) {
            return;
        }
        this.func_150516_a(world, i, j, k, LOTRMod.slabSingle6, 2 + random.nextInt(3) | (inverted ? 8 : 0));
    }
    
    private void placeRandomStoneStairs(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (random.nextInt(8) == 0) {
            return;
        }
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                this.func_150516_a(world, i, j, k, LOTRMod.stairsWoodElvenBrick, meta);
                break;
            }
            case 1: {
                this.func_150516_a(world, i, j, k, LOTRMod.stairsWoodElvenBrickMossy, meta);
                break;
            }
            case 2: {
                this.func_150516_a(world, i, j, k, LOTRMod.stairsWoodElvenBrickCracked, meta);
                break;
            }
        }
    }
    
    private void placeDungeonBlock(final World world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 5);
                break;
            }
            case 1: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 6);
                break;
            }
            case 2: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 7);
                break;
            }
        }
    }
    
    private void placeSkull(final World world, final Random random, int i, final int j, int k, final int xRange, final int zRange) {
        i += random.nextInt(xRange);
        k += random.nextInt(zRange);
        if (random.nextBoolean()) {
            this.placeSkull(world, random, i, j, k);
        }
    }
}
