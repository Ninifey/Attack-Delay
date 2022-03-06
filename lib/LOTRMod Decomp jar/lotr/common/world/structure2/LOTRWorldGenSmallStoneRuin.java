// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Direction;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public class LOTRWorldGenSmallStoneRuin extends LOTRWorldGenStructureBase2
{
    private Block brickBlock;
    private int brickMeta;
    private Block plankBlock;
    private int plankMeta;
    private Block plankSlabBlock;
    private int plankSlabMeta;
    private Block woodBeamBlock;
    private int woodBeamMeta;
    private Block barsBlock;
    
    public LOTRWorldGenSmallStoneRuin(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        final RuinType ruinType = RuinType.getRandomType(random);
        this.setOriginAndRotation(world, i, j, k, rotation, ruinType.centreShift);
        final int radius = ruinType.checkRadius;
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -radius; i2 <= radius; ++i2) {
                for (int k2 = -radius; k2 <= radius; ++k2) {
                    if (!this.isSuitableSpawnBlock(world, i2, k2)) {
                        return false;
                    }
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 7) {
                        return false;
                    }
                }
            }
        }
        if (ruinType == RuinType.COLUMN) {
            this.brickBlock = Blocks.stonebrick;
            this.layFoundation(world, this.brickMeta = 0, 0, 0, this.brickBlock, this.brickMeta);
            this.layFoundation(world, -1, 0, 0, this.brickBlock, this.brickMeta);
            this.layFoundation(world, 1, 0, 0, this.brickBlock, this.brickMeta);
            this.layFoundation(world, 0, 0, -1, this.brickBlock, this.brickMeta);
            this.layFoundation(world, 0, 0, 1, this.brickBlock, this.brickMeta);
            for (int height = 3 + random.nextInt(3), j3 = 1; j3 <= height; ++j3) {
                if (random.nextInt(3) == 0) {
                    this.setBlockAndMetadata(world, 0, j3, 0, Blocks.stonebrick, 3);
                }
                else {
                    this.setBlockAndMetadata(world, 0, j3, 0, this.brickBlock, this.brickMeta);
                }
            }
            this.setBlockAndMetadata(world, -1, 1, 0, Blocks.stone_brick_stairs, 1);
            this.setBlockAndMetadata(world, 1, 1, 0, Blocks.stone_brick_stairs, 0);
            this.setBlockAndMetadata(world, 0, 1, -1, Blocks.stone_brick_stairs, 2);
            this.setBlockAndMetadata(world, 0, 1, 1, Blocks.stone_brick_stairs, 3);
        }
        else if (ruinType == RuinType.ROOM) {
            for (int i3 = -2; i3 <= 2; ++i3) {
                for (int k3 = -2; k3 <= 2; ++k3) {
                    final int i4 = Math.abs(i3);
                    final int k4 = Math.abs(k3);
                    this.layFoundationRandomStoneBrick(world, random, i3, 0, k3);
                    if (i4 <= 1 && k4 <= 1) {
                        this.setBlockAndMetadata(world, i3, 0, k3, Blocks.cobblestone, 0);
                    }
                    if ((i4 == 2 || k4 == 2) && (i3 != 0 || k3 != -2)) {
                        for (int height2 = 1 + random.nextInt(3), j4 = 1; j4 <= height2; ++j4) {
                            this.placeRandomStoneBrick(world, random, i3, j4, k3);
                        }
                    }
                }
            }
            if (random.nextInt(4) == 0) {
                this.placeChest(world, random, 0, 1, 1, LOTRMod.chestStone, 2, LOTRChestContents.RUINED_HOUSE);
            }
        }
        else if (ruinType == RuinType.BAR_TOWER) {
            final int randomBar = random.nextInt(2);
            if (randomBar == 0) {
                this.barsBlock = Blocks.iron_bars;
            }
            else if (randomBar == 1) {
                this.barsBlock = LOTRMod.bronzeBars;
            }
            for (int i5 = -2; i5 <= 2; ++i5) {
                for (int k5 = -2; k5 <= 2; ++k5) {
                    final int i6 = Math.abs(i5);
                    final int k6 = Math.abs(k5);
                    if ((i6 == 2 && k6 <= 1) || (k6 == 2 && i6 <= 1)) {
                        this.layFoundationRandomStoneBrick(world, random, i5, 0, k5);
                        for (int height3 = 4 + random.nextInt(3), j5 = 1; j5 <= height3; ++j5) {
                            this.placeRandomStoneBrick(world, random, i5, j5, k5);
                        }
                    }
                }
            }
            for (int j3 = 1; j3 <= 2; ++j3) {
                this.setAir(world, 0, j3, -2);
                this.setBlockAndMetadata(world, 0, j3, 2, this.barsBlock, 0);
                this.setBlockAndMetadata(world, -2, j3, 0, this.barsBlock, 0);
                this.setBlockAndMetadata(world, 2, j3, 0, this.barsBlock, 0);
            }
            this.setBlockAndMetadata(world, 0, 3, -2, (Block)Blocks.stone_slab, 8);
            for (final int i7 : new int[] { -1, 1 }) {
                final int k7 = 1;
                final int j5 = this.getTopBlock(world, i7, k7);
                if (random.nextInt(10) == 0) {
                    this.placeChest(world, random, i7, j5, k7, LOTRMod.chestStone, 2, LOTRChestContents.RUINED_HOUSE);
                }
            }
        }
        else if (ruinType == RuinType.PIT_MINE) {
            for (int i3 = -2; i3 <= 2; ++i3) {
                for (int k3 = -2; k3 <= 2; ++k3) {
                    final int i4 = Math.abs(i3);
                    final int k4 = Math.abs(k3);
                    if (i4 == 2 || k4 == 2) {
                        int j2 = this.getTopBlock(world, i3, k3);
                        j2 -= random.nextInt(3);
                        for (int j6 = 1; j6 >= j2; --j6) {
                            this.placeRandomStoneBrick(world, random, i3, j6, k3);
                            this.setGrassToDirt(world, i3, j6 - 1, k3);
                        }
                    }
                    if (i4 == 2 && k4 == 2) {
                        for (int height2 = random.nextInt(3), j4 = 1; j4 <= 1 + height2; ++j4) {
                            this.placeRandomStoneBrick(world, random, i3, j4, k3);
                            this.setGrassToDirt(world, i3, j4 - 1, k3);
                        }
                    }
                }
            }
            final int pitWidth = 4 + random.nextInt(5);
            final int pitHeight = 2 + random.nextInt(3);
            int pitDepth = 60 - random.nextInt(5);
            pitDepth -= super.originY;
            final int pitBottom = pitDepth - pitHeight - 1;
            for (int i7 = -1; i7 <= 1; ++i7) {
                for (int k7 = -1; k7 <= 1; ++k7) {
                    final int i8 = Math.abs(i7);
                    final int k8 = Math.abs(k7);
                    int j8;
                    for (int j7 = j8 = this.getTopBlock(world, i7, k7); j8 >= pitDepth; --j8) {
                        this.setAir(world, i7, j8, k7);
                        if (i8 == 1 && k8 == 1 && random.nextInt(10) == 0) {
                            this.setBlockAndMetadata(world, i7, j8, k7, (Block)Blocks.stone_slab, 0);
                        }
                    }
                }
            }
            for (int i7 = -pitWidth; i7 <= pitWidth; ++i7) {
                for (int k7 = -pitWidth; k7 <= pitWidth; ++k7) {
                    final int i8 = Math.abs(i7);
                    final int k8 = Math.abs(k7);
                    final int randomFloor = random.nextInt(5);
                    if (randomFloor == 0) {
                        this.placeRandomStoneBrick(world, random, i7, pitBottom, k7);
                    }
                    else if (randomFloor == 1) {
                        this.setBlockAndMetadata(world, i7, pitBottom, k7, Blocks.cobblestone, 0);
                    }
                    else if (randomFloor == 2) {
                        this.setBlockAndMetadata(world, i7, pitBottom, k7, Blocks.stone, 0);
                    }
                    else if (randomFloor == 3) {
                        this.setBlockAndMetadata(world, i7, pitBottom, k7, Blocks.gravel, 0);
                    }
                    else if (randomFloor == 4) {
                        this.setBlockAndMetadata(world, i7, pitBottom, k7, Blocks.dirt, 0);
                    }
                    for (int j9 = pitBottom + 1; j9 <= pitBottom + pitHeight; ++j9) {
                        this.setAir(world, i7, j9, k7);
                    }
                    if (i8 == 2 && k8 == 2) {
                        for (int j9 = pitBottom + 1; j9 <= pitBottom + pitHeight; ++j9) {
                            this.setBlockAndMetadata(world, i7, j9, k7, LOTRMod.woodBeamV1, 0);
                        }
                    }
                    else if (i8 <= 2 && k8 <= 2 && (i8 == 2 || k8 == 2)) {
                        if (random.nextInt(4) != 0) {
                            this.setBlockAndMetadata(world, i7, pitBottom + pitHeight, k7, Blocks.fence, 0);
                        }
                    }
                    else if (random.nextInt(60) == 0) {
                        this.placeSkull(world, random, i7, pitBottom + 1, k7);
                    }
                    else if (random.nextInt(120) == 0) {
                        final int chestMeta = Direction.directionToFacing[random.nextInt(4)];
                        this.placeChest(world, random, i7, pitBottom + 1, k7, chestMeta, LOTRChestContents.RUINED_HOUSE);
                    }
                }
            }
        }
        else if (ruinType == RuinType.PLINTH) {
            for (int i3 = -3; i3 <= 2; ++i3) {
                for (int k3 = -3; k3 <= 2; ++k3) {
                    this.layFoundation(world, i3, 0, k3, Blocks.cobblestone, 0);
                }
            }
            for (int i3 = -2; i3 <= 1; ++i3) {
                for (int k3 = -2; k3 <= 1; ++k3) {
                    this.placeRandomStoneBrick(world, random, i3, 1, k3);
                }
            }
            for (final int i9 : new int[] { -3, 2 }) {
                this.setBlockAndMetadata(world, i9, 1, -2, Blocks.stone_brick_stairs, 2);
                this.setBlockAndMetadata(world, i9, 1, -1, (Block)Blocks.stone_slab, 8);
                this.setBlockAndMetadata(world, i9, 1, 0, (Block)Blocks.stone_slab, 8);
                this.setBlockAndMetadata(world, i9, 1, 1, Blocks.stone_brick_stairs, 3);
            }
            for (final int k2 : new int[] { -3, 2 }) {
                this.setBlockAndMetadata(world, -2, 1, k2, Blocks.stone_brick_stairs, 1);
                this.setBlockAndMetadata(world, -1, 1, k2, (Block)Blocks.stone_slab, 8);
                this.setBlockAndMetadata(world, 0, 1, k2, (Block)Blocks.stone_slab, 8);
                this.setBlockAndMetadata(world, 1, 1, k2, Blocks.stone_brick_stairs, 0);
            }
            for (int i3 = -1; i3 <= 0; ++i3) {
                for (int k3 = -1; k3 <= 0; ++k3) {
                    if (random.nextInt(3) == 0) {
                        final int height4 = 2 + random.nextInt(4);
                        for (int j10 = 2; j10 < 2 + height4; ++j10) {
                            this.setBlockAndMetadata(world, i3, j10, k3, LOTRMod.pillar2, 2);
                        }
                        this.setBlockAndMetadata(world, i3, 2 + height4, k3, Blocks.stone_brick_stairs, random.nextInt(4));
                    }
                }
            }
        }
        else if (ruinType == RuinType.RUBBLE) {
            final int width = 3 + random.nextInt(5);
            final int centreWidth = 2;
            for (int i2 = -width; i2 <= width; ++i2) {
                for (int k2 = -width; k2 <= width; ++k2) {
                    final int d = i2 * i2 + k2 * k2;
                    if (d < width * width) {
                        final int j4 = this.getTopBlock(world, i2, k2) - 1;
                        if (this.isSuitableSpawnBlock(world, i2, k2)) {
                            if (random.nextInt(3) == 0) {
                                if (random.nextBoolean()) {
                                    this.setBlockAndMetadata(world, i2, j4, k2, Blocks.cobblestone, 0);
                                }
                                else {
                                    this.placeRandomStoneBrick(world, random, i2, j4, k2);
                                }
                                this.setGrassToDirt(world, i2, j4 - 1, k2);
                            }
                            if (d > centreWidth * centreWidth && random.nextInt(6) == 0) {
                                for (int height5 = 1 + random.nextInt(4), j11 = j4 + 1; j11 <= j4 + height5; ++j11) {
                                    this.setBlockAndMetadata(world, i2, j11, k2, Blocks.stone, 0);
                                    this.setGrassToDirt(world, i2, j11 - 1, k2);
                                }
                            }
                        }
                    }
                }
            }
            if (random.nextInt(6) == 0) {
                for (int i2 = -1; i2 <= 1; ++i2) {
                    for (int k2 = -1; k2 <= 1; ++k2) {
                        this.layFoundation(world, i2, 0, k2, (Block)Blocks.double_stone_slab, 0);
                        this.setBlockAndMetadata(world, i2, 1, k2, (Block)Blocks.stone_slab, 0);
                    }
                }
                this.setBlockAndMetadata(world, 0, 1, 0, Blocks.stonebrick, 3);
                this.placeChest(world, random, 0, 0, 0, LOTRMod.chestStone, 2, LOTRChestContents.RUINED_HOUSE);
            }
        }
        else if (ruinType == RuinType.QUARRY) {
            for (int r = 9, i5 = -r; i5 <= r; ++i5) {
                for (int k5 = -r; k5 <= r; ++k5) {
                    for (int j10 = r; j10 >= 1; --j10) {
                        final int j12 = j10 + 5;
                        final int d2 = i5 * i5 + j12 * j12 + k5 * k5;
                        if (d2 < r * r) {
                            final boolean grass = !this.isOpaque(world, i5, j10 + 1, k5);
                            this.setBlockAndMetadata(world, i5, j10, k5, (Block)(grass ? Blocks.grass : Blocks.dirt), 0);
                            this.setGrassToDirt(world, i5, j10 - 1, k5);
                            if (j10 == 1) {
                                this.layFoundation(world, i5, 0, k5, Blocks.dirt, 0);
                            }
                        }
                    }
                }
            }
            for (int r = 5, i5 = -r; i5 <= r; ++i5) {
                for (int k5 = -r; k5 <= r; ++k5) {
                    for (int j10 = -r; j10 <= r; ++j10) {
                        final int j12 = j10 - 1;
                        final int d2 = i5 * i5 + j12 * j12 + k5 * k5;
                        if (d2 < r * r) {
                            if (random.nextInt(4) == 0) {
                                this.setBlockAndMetadata(world, i5, j10, k5, Blocks.cobblestone, 0);
                            }
                            else {
                                this.setBlockAndMetadata(world, i5, j10, k5, Blocks.stone, 0);
                            }
                            this.setGrassToDirt(world, i5, j10 - 1, k5);
                        }
                    }
                }
            }
            final int r = 5;
            final int r2 = 3;
            for (int i2 = -r; i2 <= r; ++i2) {
                for (int k2 = -r; k2 <= r; ++k2) {
                    for (int j2 = -r; j2 <= r; ++j2) {
                        final int i10 = i2 - 2;
                        final int j13 = j2 - 1;
                        final int k8 = k2 - 2;
                        final int d3 = i10 * i10 + j13 * j13 + k8 * k8;
                        if (d3 < r2 * r2) {
                            this.setAir(world, i2, j2, k2);
                            if (this.getBlock(world, i2, j2 - 1, k2) == Blocks.dirt) {
                                this.setBlockAndMetadata(world, i2, j2 - 1, k2, (Block)Blocks.grass, 0);
                            }
                        }
                    }
                }
            }
            final boolean rotten = random.nextBoolean();
            for (int j10 = -1; j10 <= 3; ++j10) {
                if (rotten) {
                    this.setBlockAndMetadata(world, 1, j10, 1, LOTRMod.woodBeamRotten, 0);
                }
                else {
                    this.setBlockAndMetadata(world, 1, j10, 1, LOTRMod.woodBeamV1, 0);
                }
            }
            if (rotten) {
                this.setBlockAndMetadata(world, 4, 1, 3, LOTRMod.stairsRotten, 1);
                this.setBlockAndMetadata(world, 4, 0, 3, LOTRMod.stairsRotten, 4);
                this.setBlockAndMetadata(world, 3, 0, 3, LOTRMod.stairsRotten, 1);
                this.setBlockAndMetadata(world, 3, -1, 3, LOTRMod.stairsRotten, 4);
                this.setBlockAndMetadata(world, 2, -1, 3, LOTRMod.planksRotten, 0);
                this.setBlockAndMetadata(world, 2, -1, 2, LOTRMod.stairsRotten, 2);
                this.setBlockAndMetadata(world, 5, 2, 2, LOTRMod.stairsRotten, 3);
                this.setGrassToDirt(world, 5, 1, 2);
            }
            else {
                this.setBlockAndMetadata(world, 4, 1, 3, Blocks.oak_stairs, 1);
                this.setBlockAndMetadata(world, 4, 0, 3, Blocks.oak_stairs, 4);
                this.setBlockAndMetadata(world, 3, 0, 3, Blocks.oak_stairs, 1);
                this.setBlockAndMetadata(world, 3, -1, 3, Blocks.oak_stairs, 4);
                this.setBlockAndMetadata(world, 2, -1, 3, Blocks.planks, 0);
                this.setBlockAndMetadata(world, 2, -1, 2, Blocks.oak_stairs, 2);
                this.setBlockAndMetadata(world, 5, 2, 2, Blocks.oak_stairs, 3);
                this.setGrassToDirt(world, 5, 1, 2);
            }
            for (int i9 = -2; i9 <= 2; ++i9) {
                for (int k9 = -2; k9 <= 2; ++k9) {
                    final int i10 = Math.abs(i9);
                    final int k10 = Math.abs(k9);
                    if ((i10 == 2 || k10 == 2) && random.nextBoolean()) {
                        if (rotten) {
                            this.setBlockAndMetadata(world, i9, 6, k9, LOTRMod.fenceRotten, 0);
                        }
                        else {
                            this.setBlockAndMetadata(world, i9, 6, k9, Blocks.fence, 0);
                        }
                    }
                }
            }
        }
        else if (ruinType == RuinType.OBELISK) {
            final int width = radius;
            final int centreWidth = 2;
            for (int i2 = -width; i2 <= width; ++i2) {
                for (int k2 = -width; k2 <= width; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (this.isSuitableSpawnBlock(world, i2, k2)) {
                        if (random.nextInt(3) == 0) {
                            if (random.nextBoolean()) {
                                this.setBlockAndMetadata(world, i2, j2, k2, Blocks.cobblestone, 0);
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, j2, k2, Blocks.gravel, 0);
                            }
                            this.setGrassToDirt(world, i2, j2 - 1, k2);
                        }
                        if ((i2 >= 4 || k2 >= 4) && random.nextInt(6) == 0) {
                            this.setGrassToDirt(world, i2, j2, k2);
                            this.placeRandomStoneBrick(world, random, i2, j2 + 1, k2);
                            if (random.nextInt(3) == 0) {
                                final int rb = random.nextInt(3);
                                if (rb == 0) {
                                    this.placeRandomStoneBrick(world, random, i2, j2 + 2, k2);
                                }
                                else if (rb == 1) {
                                    this.setBlockAndMetadata(world, i2, j2 + 2, k2, Blocks.stone_brick_stairs, random.nextInt(4));
                                }
                                else if (rb == 2) {
                                    this.setBlockAndMetadata(world, i2, j2 + 2, k2, LOTRMod.wallStoneV, 1);
                                }
                            }
                        }
                    }
                }
            }
            for (int i2 = -1; i2 <= 1; ++i2) {
                for (int k2 = -1; k2 <= 1; ++k2) {
                    final int i11 = Math.abs(i2);
                    final int k11 = Math.abs(k2);
                    this.layFoundationRandomStoneBrick(world, random, i2, 0, k2);
                    this.placeRandomStoneBrick(world, random, i2, 1, k2);
                    if (i11 == 0 || k11 == 0) {
                        this.setBlockAndMetadata(world, i2, 1, k2, Blocks.stonebrick, 3);
                        this.placeRandomStoneBrick(world, random, i2, 2, k2);
                    }
                }
            }
            this.setBlockAndMetadata(world, -1, 3, 0, Blocks.stone_brick_stairs, 1);
            this.setBlockAndMetadata(world, 1, 3, 0, Blocks.stone_brick_stairs, 0);
            this.setBlockAndMetadata(world, 0, 3, -1, Blocks.stone_brick_stairs, 2);
            this.setBlockAndMetadata(world, 0, 3, 1, Blocks.stone_brick_stairs, 3);
            final int top = 4 + random.nextInt(4);
            for (int j10 = 3; j10 <= top; ++j10) {
                this.placeRandomStoneBrick(world, random, 0, j10, 0);
            }
            for (int j10 = top + 1; j10 <= top + 2; ++j10) {
                this.setBlockAndMetadata(world, 0, j10, 0, LOTRMod.wallStoneV, 1);
            }
        }
        else if (ruinType == RuinType.WELL) {
            final int wellDepth = 4 + random.nextInt(5);
            final int wellBottom = -wellDepth - 1;
            final boolean water = random.nextBoolean();
            int waterDepth = 2 + random.nextInt(5);
            if (waterDepth > wellDepth) {
                waterDepth = wellDepth;
            }
            for (int i7 = -2; i7 <= 1; ++i7) {
                for (int k7 = -2; k7 <= 1; ++k7) {
                    if (i7 == -2 || i7 == 1 || k7 == -2 || k7 == 1) {
                        this.placeRandomStoneBrick(world, random, i7, 1, k7);
                        this.setBlockAndMetadata(world, i7, 0, k7, (Block)Blocks.double_stone_slab, 0);
                        for (int j5 = -1; j5 >= wellBottom; --j5) {
                            this.placeRandomStoneBrick(world, random, i7, j5, k7);
                        }
                        this.setGrassToDirt(world, i7, wellBottom - 1, k7);
                    }
                    else {
                        for (int j5 = 1; j5 >= wellBottom; --j5) {
                            if (water && j5 <= wellBottom + waterDepth) {
                                this.setBlockAndMetadata(world, i7, j5, k7, Blocks.water, 0);
                            }
                            else {
                                this.setAir(world, i7, j5, k7);
                            }
                        }
                        this.setGrassToDirt(world, i7, wellBottom - 1, k7);
                        this.setBlockAndMetadata(world, i7, wellBottom, k7, Blocks.stone, 0);
                        this.setBlockAndMetadata(world, i7, wellBottom + 1, k7, Blocks.gravel, 0);
                        if (random.nextBoolean()) {
                            this.setBlockAndMetadata(world, i7, wellBottom + 2, k7, Blocks.gravel, 0);
                        }
                        if (random.nextInt(8) == 0) {
                            final int chestMeta2 = Direction.directionToFacing[random.nextInt(4)];
                            this.placeChest(world, random, i7, wellBottom + 1, k7, LOTRMod.chestStone, chestMeta2, LOTRChestContents.RUINED_HOUSE);
                        }
                        if (random.nextInt(3) == 0) {
                            this.setBlockAndMetadata(world, i7, 0, k7, LOTRMod.fenceRotten, 0);
                        }
                    }
                }
            }
            this.setBlockAndMetadata(world, -2, 1, -2, (Block)Blocks.stone_slab, 0);
            this.setBlockAndMetadata(world, 1, 1, -2, (Block)Blocks.stone_slab, 0);
            this.setBlockAndMetadata(world, -2, 1, 1, (Block)Blocks.stone_slab, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, (Block)Blocks.stone_slab, 0);
        }
        else if (ruinType == RuinType.TURRET) {
            final int randomWood = random.nextInt(3);
            if (randomWood == 0) {
                this.plankBlock = Blocks.planks;
                this.plankMeta = 0;
                this.plankSlabBlock = (Block)Blocks.wooden_slab;
                this.plankSlabMeta = 0;
                this.woodBeamBlock = LOTRMod.woodBeamV1;
                this.woodBeamMeta = 0;
            }
            else if (randomWood == 1) {
                this.plankBlock = Blocks.planks;
                this.plankMeta = 1;
                this.plankSlabBlock = (Block)Blocks.wooden_slab;
                this.plankSlabMeta = 1;
                this.woodBeamBlock = LOTRMod.woodBeamV1;
                this.woodBeamMeta = 1;
            }
            else if (randomWood == 2) {
                this.plankBlock = LOTRMod.planksRotten;
                this.plankMeta = 0;
                this.plankSlabBlock = LOTRMod.rottenSlabSingle;
                this.plankSlabMeta = 0;
                this.woodBeamBlock = LOTRMod.woodBeamRotten;
                this.woodBeamMeta = 0;
            }
            final int randomBar2 = random.nextInt(2);
            if (randomBar2 == 0) {
                this.barsBlock = Blocks.iron_bars;
            }
            else if (randomBar2 == 1) {
                this.barsBlock = LOTRMod.bronzeBars;
            }
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
                    final int i11 = Math.abs(i2);
                    final int k11 = Math.abs(k2);
                    this.layFoundationRandomStoneBrick(world, random, i2, 0, k2);
                    this.placeRandomStoneBrick(world, random, i2, 1, k2);
                    if (i11 <= 3 && k11 <= 3) {
                        if (i11 == 3 && k11 == 3) {
                            this.placeRandomStoneBrick(world, random, i2, 2, k2);
                            for (int j5 = 3; j5 <= 5; ++j5) {
                                this.setBlockAndMetadata(world, i2, j5, k2, this.woodBeamBlock, this.woodBeamMeta);
                            }
                            this.placeRandomStoneBrick(world, random, i2, 6, k2);
                        }
                        else if (i11 == 3 || k11 == 3) {
                            for (int j5 = 2; j5 <= 6; ++j5) {
                                if (random.nextInt(8) == 0) {
                                    this.setAir(world, i2, j5, k2);
                                }
                                else {
                                    this.placeRandomStoneBrick(world, random, i2, j5, k2);
                                }
                            }
                            if (i11 <= 1 || k11 <= 1) {
                                if (random.nextInt(4) == 0) {
                                    this.setAir(world, i2, 4, k2);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i2, 4, k2, this.barsBlock, 0);
                                }
                            }
                        }
                        else {
                            if (random.nextInt(4) == 0) {
                                if (random.nextBoolean()) {
                                    this.setBlockAndMetadata(world, i2, 1, k2, Blocks.gravel, 0);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i2, 1, k2, Blocks.cobblestone, 0);
                                }
                            }
                            else if (random.nextInt(4) == 0) {
                                this.setAir(world, i2, 1, k2);
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, 1, k2, this.plankBlock, this.plankMeta);
                            }
                            for (int j5 = 2; j5 <= 5; ++j5) {
                                this.setAir(world, i2, j5, k2);
                            }
                            if (random.nextInt(5) == 0) {
                                this.setAir(world, i2, 6, k2);
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, 6, k2, this.plankSlabBlock, this.plankSlabMeta);
                            }
                        }
                    }
                    if ((i11 == 4 || k11 == 4) && random.nextInt(3) != 0) {
                        if (random.nextInt(3) == 0) {
                            if (random.nextBoolean()) {
                                this.setBlockAndMetadata(world, i2, 7, k2, Blocks.stone_brick_stairs, random.nextInt(4));
                            }
                            else {
                                this.setBlockAndMetadata(world, i2, 7, k2, (Block)Blocks.stone_slab, 0);
                            }
                        }
                        else {
                            this.placeRandomStoneBrick(world, random, i2, 7, k2);
                        }
                    }
                }
            }
            for (int i2 = -4; i2 <= 4; ++i2) {
                this.setBlockAndMetadata(world, i2, 2, -4, Blocks.stone_brick_stairs, 2);
                this.setBlockAndMetadata(world, i2, 2, 4, Blocks.stone_brick_stairs, 3);
                this.setBlockAndMetadata(world, i2, 6, -4, Blocks.stone_brick_stairs, 6);
                this.setBlockAndMetadata(world, i2, 6, 4, Blocks.stone_brick_stairs, 7);
            }
            for (int k5 = -3; k5 <= 3; ++k5) {
                this.setBlockAndMetadata(world, -4, 2, k5, Blocks.stone_brick_stairs, 1);
                this.setBlockAndMetadata(world, 4, 2, k5, Blocks.stone_brick_stairs, 0);
                this.setBlockAndMetadata(world, -4, 6, k5, Blocks.stone_brick_stairs, 5);
                this.setBlockAndMetadata(world, 4, 6, k5, Blocks.stone_brick_stairs, 4);
            }
            if (random.nextInt(3) == 0) {
                this.setBlockAndMetadata(world, 0, 1, 2, this.plankBlock, this.plankMeta);
                this.placeChest(world, random, 0, 2, 2, 2, LOTRChestContents.RUINED_HOUSE);
            }
            if (random.nextInt(3) == 0) {
                this.placeRandomStoneBrick(world, random, 0, 6, 3);
                this.placeChest(world, random, 0, 7, 3, 2, LOTRChestContents.RUINED_HOUSE);
            }
        }
        else if (ruinType == RuinType.WALLS) {
            final int length = 3 + random.nextInt(7);
            final int width2 = 2 + random.nextInt(3);
            final int height4 = 2 + random.nextInt(6);
            final int gravelChance = 2 + random.nextInt(7);
            for (int i7 = -width2; i7 <= width2; ++i7) {
                for (int k7 = -length; k7 <= length; ++k7) {
                    final int i8 = Math.abs(i7);
                    final int k8 = Math.abs(k7);
                    if (this.isSuitableSpawnBlock(world, i7, k7)) {
                        final int j7 = this.getTopBlock(world, i7, k7) - 1;
                        if (i8 == width2) {
                            int h = height4 - random.nextInt(3);
                            if (random.nextInt(8) == 0) {
                                h = random.nextInt(3);
                            }
                            float factor = k8 / (float)length;
                            factor = 1.0f / (factor + 0.01f);
                            factor *= 0.5f + random.nextFloat() * 0.5f;
                            factor = Math.min(factor, 1.0f);
                            h *= (int)factor;
                            final int top2 = j7 + h;
                            for (int j14 = j7 + 1; j14 <= top2; ++j14) {
                                if (random.nextInt(8) == 0) {
                                    this.setBlockAndMetadata(world, i7, j14, k7, Blocks.stone_brick_stairs, random.nextInt(8));
                                }
                                else if (random.nextInt(8) == 0) {
                                    this.setBlockAndMetadata(world, i7, j14, k7, Blocks.cobblestone, 0);
                                }
                                else {
                                    this.placeRandomStoneBrick(world, random, i7, j14, k7);
                                }
                                this.setGrassToDirt(world, i7, j14 - 1, k7);
                            }
                            if (k8 < length / 2 && h >= 3 && h >= height4 - 3 && random.nextBoolean()) {
                                final int h2 = top2 - random.nextInt(2);
                                for (int w = random.nextInt(width2 * 2), w2 = 1; w2 <= w; ++w2) {
                                    if (i7 < 0) {
                                        this.setBlockAndMetadata(world, i7 + w2, h2, k7, (Block)Blocks.stone_slab, 8);
                                    }
                                    else {
                                        this.setBlockAndMetadata(world, i7 - w2, h2, k7, (Block)Blocks.stone_slab, 8);
                                    }
                                    if (random.nextInt(4) == 0) {
                                        break;
                                    }
                                }
                            }
                        }
                        else {
                            if (random.nextInt(5) != 0) {
                                if (random.nextInt(4) == 0) {
                                    this.setBlockAndMetadata(world, i7, j7, k7, Blocks.mossy_cobblestone, 0);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i7, j7, k7, Blocks.cobblestone, 0);
                                }
                            }
                            if ((i8 == width2 - 1 && random.nextInt(Math.max(2, gravelChance / 2)) == 0) || random.nextInt(gravelChance) == 0) {
                                int h = 1;
                                if (random.nextInt(4) == 0) {
                                    ++h;
                                }
                                this.setGrassToDirt(world, i7, j7, k7);
                                for (int j15 = j7 + 1; j15 <= j7 + h; ++j15) {
                                    this.setBlockAndMetadata(world, i7, j15, k7, Blocks.gravel, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (ruinType == RuinType.SHRINE) {
            for (int i3 = -4; i3 <= 4; ++i3) {
                for (int k3 = -4; k3 <= 4; ++k3) {
                    final int i4 = Math.abs(i3);
                    final int k4 = Math.abs(k3);
                    this.layFoundationRandomStoneBrick(world, random, i3, 0, k3);
                    if (i4 <= 3 && k4 <= 3) {
                        if (i4 <= 1 && k4 <= 1) {
                            this.setBlockAndMetadata(world, i3, 1, k3, (Block)Blocks.double_stone_slab, 0);
                            this.setBlockAndMetadata(world, i3, 2, k3, (Block)Blocks.stone_slab, 0);
                        }
                        else if (random.nextInt(16) == 0) {
                            this.setBlockAndMetadata(world, i3, 1, k3, Blocks.gravel, 0);
                        }
                        else if (random.nextInt(16) == 0) {
                            this.setBlockAndMetadata(world, i3, 1, k3, Blocks.cobblestone, 0);
                        }
                        else if (random.nextInt(16) == 0) {
                            this.setBiomeTop(world, i3, 1, k3);
                        }
                        else if (i4 <= 2 && k4 <= 2) {
                            this.setBlockAndMetadata(world, i3, 1, k3, (Block)Blocks.double_stone_slab, 0);
                        }
                        else {
                            this.placeRandomStoneBrick(world, random, i3, 1, k3);
                        }
                    }
                    if (random.nextInt(5) != 0) {
                        if (i3 == -4) {
                            this.setBlockAndMetadata(world, i3, 1, k3, Blocks.stone_brick_stairs, 1);
                        }
                        else if (i3 == 4) {
                            this.setBlockAndMetadata(world, i3, 1, k3, Blocks.stone_brick_stairs, 0);
                        }
                        else if (k3 == -4) {
                            this.setBlockAndMetadata(world, i3, 1, k3, Blocks.stone_brick_stairs, 2);
                        }
                        else if (k3 == 4) {
                            this.setBlockAndMetadata(world, i3, 1, k3, Blocks.stone_brick_stairs, 3);
                        }
                    }
                    if (i4 == 3 && k4 == 3) {
                        final int h3 = 2 + random.nextInt(4);
                        for (int top3 = 1 + h3, j5 = 2; j5 <= top3; ++j5) {
                            this.placeRandomStoneBrick(world, random, i3, j5, k3);
                            this.setGrassToDirt(world, i3, j5 - 1, k3);
                        }
                        if (h3 >= 4) {
                            this.setBlockAndMetadata(world, i3 - 1, 1 + h3, k3 - 1, Blocks.stone_brick_stairs, 6);
                            this.setBlockAndMetadata(world, i3, 1 + h3, k3 - 1, Blocks.stone_brick_stairs, 6);
                            this.setBlockAndMetadata(world, i3 + 1, 1 + h3, k3 - 1, Blocks.stone_brick_stairs, 6);
                            this.setBlockAndMetadata(world, i3 + 1, 1 + h3, k3, Blocks.stone_brick_stairs, 4);
                            this.setBlockAndMetadata(world, i3 + 1, 1 + h3, k3 + 1, Blocks.stone_brick_stairs, 7);
                            this.setBlockAndMetadata(world, i3, 1 + h3, k3 + 1, Blocks.stone_brick_stairs, 7);
                            this.setBlockAndMetadata(world, i3 - 1, 1 + h3, k3 + 1, Blocks.stone_brick_stairs, 7);
                            this.setBlockAndMetadata(world, i3 - 1, 1 + h3, k3, Blocks.stone_brick_stairs, 5);
                        }
                    }
                    if (((i4 == 3 && k4 <= 1) || (k4 == 3 && i4 <= 1)) && random.nextInt(4) != 0) {
                        this.setBlockAndMetadata(world, i3, 2, k3, (Block)Blocks.stone_slab, 0);
                        this.setGrassToDirt(world, i3, 1, k3);
                    }
                }
            }
            this.setBlockAndMetadata(world, 0, 2, 0, Blocks.stonebrick, 3);
            this.placeChest(world, random, 0, 1, 0, LOTRMod.chestStone, 2, LOTRChestContents.RUINED_HOUSE);
        }
        else if (ruinType == RuinType.BRICK_HOUSE) {
            final int width = MathHelper.getRandomIntegerInRange(random, 3, 5);
            final int height6 = MathHelper.getRandomIntegerInRange(random, 1, 4);
            for (int i2 = -width; i2 <= width; ++i2) {
                for (int k2 = -width; k2 <= width; ++k2) {
                    final int i11 = Math.abs(i2);
                    final int k11 = Math.abs(k2);
                    this.layFoundation(world, i2, 0, k2, Blocks.cobblestone, 0);
                    final int randomFloor2 = random.nextInt(5);
                    if (randomFloor2 == 0) {
                        this.setBlockAndMetadata(world, i2, 0, k2, Blocks.cobblestone, 0);
                    }
                    else if (randomFloor2 == 1) {
                        this.setBlockAndMetadata(world, i2, 0, k2, Blocks.mossy_cobblestone, 0);
                    }
                    else if (randomFloor2 == 2) {
                        this.setBlockAndMetadata(world, i2, 0, k2, Blocks.gravel, 0);
                    }
                    else if (randomFloor2 == 3) {
                        this.setBlockAndMetadata(world, i2, 0, k2, Blocks.dirt, 1);
                    }
                    else if (randomFloor2 == 4) {
                        this.setBlockAndMetadata(world, i2, 0, k2, Blocks.brick_block, 0);
                    }
                    if (i11 == width || k11 == width) {
                        if (random.nextInt(10) != 0) {
                            for (int j16 = 1; j16 <= height6; ++j16) {
                                if (random.nextInt(6) != 0) {
                                    if (random.nextInt(3) == 0) {
                                        if (random.nextBoolean()) {
                                            this.setBlockAndMetadata(world, i2, j16, k2, LOTRMod.redBrick, 0);
                                        }
                                        else {
                                            this.setBlockAndMetadata(world, i2, j16, k2, LOTRMod.redBrick, 1);
                                        }
                                    }
                                    else {
                                        this.setBlockAndMetadata(world, i2, j16, k2, Blocks.brick_block, 0);
                                    }
                                }
                                else {
                                    final int randomWall = random.nextInt(7);
                                    if (randomWall == 0) {
                                        this.setBlockAndMetadata(world, i2, j16, k2, (Block)Blocks.double_stone_slab, 0);
                                    }
                                    else if (randomWall == 1) {
                                        this.setBlockAndMetadata(world, i2, j16, k2, LOTRMod.pillar2, 3);
                                    }
                                    else if (randomWall == 2) {
                                        this.setBlockAndMetadata(world, i2, j16, k2, LOTRMod.clayTile, 0);
                                    }
                                    else if (randomWall == 3) {
                                        final int stairDir = random.nextInt(8);
                                        this.setBlockAndMetadata(world, i2, j16, k2, Blocks.brick_stairs, stairDir);
                                    }
                                    else if (randomWall == 4) {
                                        final int stairDir = random.nextInt(8);
                                        this.setBlockAndMetadata(world, i2, j16, k2, LOTRMod.stairsClayTile, stairDir);
                                    }
                                    else if (randomWall == 5) {
                                        this.setBlockAndMetadata(world, i2, j16, k2, Blocks.cobblestone, 0);
                                    }
                                    else if (randomWall == 6) {
                                        this.setBlockAndMetadata(world, i2, j16, k2, LOTRMod.wallStoneV, 6);
                                    }
                                }
                                if (random.nextInt(6) == 0) {
                                    break;
                                }
                            }
                        }
                    }
                    else if (i11 == width - 1 || k11 == width - 1) {
                        if (random.nextInt(3) == 0) {
                            final int randomWall2 = random.nextInt(4);
                            if (randomWall2 == 0) {
                                this.setBlockAndMetadata(world, i2, 1, k2, Blocks.brick_block, 0);
                            }
                            else if (randomWall2 == 1) {
                                final int stairDir2 = random.nextInt(8);
                                this.setBlockAndMetadata(world, i2, 1, k2, Blocks.brick_stairs, stairDir2);
                            }
                            else if (randomWall2 == 2) {
                                this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.planksRotten, 0);
                            }
                            else if (randomWall2 == 3) {
                                this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.fenceRotten, 0);
                            }
                        }
                    }
                    else if (random.nextInt(8) == 0) {
                        final int randomWall2 = random.nextInt(2);
                        if (randomWall2 == 0) {
                            this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.rottenSlabSingle, 0);
                        }
                        else if (randomWall2 == 1) {
                            this.setBlockAndMetadata(world, i2, 1, k2, (Block)Blocks.stone_slab, 4);
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private boolean isSuitableSpawnBlock(final World world, final int i, final int k) {
        final int j = this.getTopBlock(world, i, k);
        if (!this.isSurface(world, i, j - 1, k)) {
            return false;
        }
        final Block above = this.getBlock(world, i, j, k);
        return !above.getMaterial().isLiquid();
    }
    
    private void layFoundation(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        for (int j2 = j; (j2 >= 0 || !this.isOpaque(world, i, j2, k)) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i, j2, k, block, meta);
            this.setGrassToDirt(world, i, j2 - 1, k);
        }
    }
    
    private void layFoundationRandomStoneBrick(final World world, final Random random, final int i, final int j, final int k) {
        for (int j2 = j; (j2 >= 0 || !this.isOpaque(world, i, j2, k)) && this.getY(j2) >= 0; --j2) {
            this.placeRandomStoneBrick(world, random, i, j2, k);
            this.setGrassToDirt(world, i, j2 - 1, k);
        }
    }
    
    private void placeRandomStoneBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 1);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 2);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
        }
    }
    
    private enum RuinType
    {
        COLUMN(0, 1), 
        ROOM(3, 2), 
        BAR_TOWER(3, 2), 
        PIT_MINE(0, 2), 
        PLINTH(0, 3), 
        RUBBLE(0, 0), 
        QUARRY(0, 7), 
        OBELISK(0, 5), 
        WELL(0, 2), 
        TURRET(5, 4), 
        WALLS(0, 3), 
        SHRINE(0, 4), 
        BRICK_HOUSE(0, 5);
        
        private int centreShift;
        private int checkRadius;
        
        private RuinType(final int i, final int j) {
            this.centreShift = i;
            this.checkRadius = j;
        }
        
        public static RuinType getRandomType(final Random random) {
            return values()[random.nextInt(values().length)];
        }
    }
}
