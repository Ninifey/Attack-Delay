// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitHole extends LOTRWorldGenHobbitStructure
{
    public LOTRWorldGenHobbitHole(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 17);
        this.setupRandomBlocks(random);
        final int radius = 16;
        final int height = 7;
        final int extraRadius = 2;
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -radius; i2 <= radius; ++i2) {
                for (int k2 = -radius; k2 <= radius; ++k2) {
                    if (i2 * i2 + k2 * k2 <= radius * radius) {
                        final int j2 = this.getTopBlock(world, i2, k2) - 1;
                        if (!this.isSurface(world, i2, j2, k2)) {
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
            }
            if (maxHeight - minHeight > 8) {
                return false;
            }
        }
        for (int i3 = -radius; i3 <= radius; ++i3) {
            for (int k3 = -radius; k3 <= radius; ++k3) {
                for (int j3 = height; j3 >= 0; --j3) {
                    final int i4 = i3;
                    final int j4 = j3 + (radius - height);
                    final int k4 = k3;
                    if (i4 * i4 + j4 * j4 + k4 * k4 < (radius + extraRadius) * (radius + extraRadius)) {
                        final boolean grass = !this.isOpaque(world, i3, j3 + 1, k3);
                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)(grass ? Blocks.grass : Blocks.dirt), 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
            }
        }
        for (int i3 = -radius; i3 <= radius; ++i3) {
            for (int k3 = -radius; k3 <= radius; ++k3) {
                if (i3 * i3 + k3 * k3 < radius * radius) {
                    for (int j3 = -1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                        final boolean grass2 = !this.isOpaque(world, i3, j3 + 1, k3);
                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)(grass2 ? Blocks.grass : Blocks.dirt), 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
            }
        }
        this.setGrassToDirt(world, 0, 7, 0);
        this.setBlockAndMetadata(world, 0, 8, 0, Blocks.brick_block, 0);
        this.setBlockAndMetadata(world, 0, 9, 0, Blocks.brick_block, 0);
        this.setBlockAndMetadata(world, 0, 10, 0, Blocks.flower_pot, 0);
        for (int k5 = -16; k5 <= -13; ++k5) {
            for (int j5 = 1; j5 <= 4; ++j5) {
                for (int i2 = -3; i2 <= 3; ++i2) {
                    this.setAir(world, i2, j5, k5);
                }
            }
        }
        for (int j6 = 1; j6 <= 3; ++j6) {
            for (int i5 = -2; i5 <= 2; ++i5) {
                this.setAir(world, i5, j6, -12);
            }
        }
        for (int k5 = -17; k5 <= -13; ++k5) {
            for (int i5 = -5; i5 <= 5; ++i5) {
                for (int j3 = 0; j3 == 0 || (!this.isOpaque(world, i5, j3, k5) && this.getY(j3) >= 0); --j3) {
                    final boolean grass2 = j3 == 0;
                    this.setBlockAndMetadata(world, i5, j3, k5, (Block)(grass2 ? Blocks.grass : Blocks.dirt), 0);
                }
                for (int j3 = 1; j3 <= 3; ++j3) {
                    this.setAir(world, i5, j3, k5);
                }
            }
        }
        for (int k5 = -16; k5 <= -13; ++k5) {
            this.setBlockAndMetadata(world, 4, 1, k5, super.outFenceBlock, super.outFenceMeta);
            this.setBlockAndMetadata(world, -4, 1, k5, super.outFenceBlock, super.outFenceMeta);
            this.setBlockAndMetadata(world, 0, 0, k5, super.pathBlock, super.pathMeta);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -12, super.pathBlock, super.pathMeta);
            this.setBlockAndMetadata(world, i3, 0, -11, super.pathBlock, super.pathMeta);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -16, super.outFenceBlock, super.outFenceMeta);
        }
        this.setBlockAndMetadata(world, 0, 1, -16, super.outFenceGateBlock, 0);
        if (random.nextInt(5) == 0) {
            final String[] signLines = LOTRNames.getHobbitSign(random);
            final int[] signPos = { -3, -2, -1, 1, 2, 3 };
            final int i2 = signPos[random.nextInt(signPos.length)];
            final int signMeta = MathHelper.getRandomIntegerInRange(random, 6, 10) & 0xF;
            this.placeSign(world, i2, 2, -16, Blocks.standing_sign, signMeta, signLines);
        }
        for (int k5 = -15; k5 <= -13; ++k5) {
            for (final int i6 : new int[] { -1, 1 }) {
                final int j7 = 1;
                this.plantFlower(world, random, i6, j7, k5);
            }
        }
        if (random.nextInt(3) == 0) {
            for (int k5 = -14; k5 <= -13; ++k5) {
                for (final int i6 : new int[] { -2, 2 }) {
                    this.setBlockAndMetadata(world, i6, 1, k5, super.hedgeBlock, super.hedgeMeta);
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int j5 = 1; j5 <= 3; ++j5) {
                this.setBlockAndMetadata(world, i3, j5, -10, Blocks.brick_block, 0);
            }
        }
        final boolean gateFlip = random.nextBoolean();
        if (gateFlip) {
            for (int i5 = 0; i5 <= 1; ++i5) {
                this.setBlockAndMetadata(world, i5, 0, -10, super.floorBlock, super.floorMeta);
                for (int j3 = 1; j3 <= 2; ++j3) {
                    this.setAir(world, i5, j3, -11);
                    this.setBlockAndMetadata(world, i5, j3, -10, super.gateBlock, 2);
                }
            }
            this.setBlockAndMetadata(world, -2, 1, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -2, 2, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -2, 3, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -2, 1, -12, super.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, -2, 3, -12, super.plank2StairBlock, 6);
            this.setBlockAndMetadata(world, -1, 3, -12, super.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, -1, 1, -11, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, -1, 2, -11, super.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, -1, 3, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 0, 3, -11, super.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, 0, 3, -12, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 1, 3, -11, super.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 1, 3, -12, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 2, 1, -11, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, 2, 2, -11, super.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 2, 3, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 2, 1, -12, super.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, 2, 3, -12, super.plank2StairBlock, 6);
            this.placeSign(world, -2, 2, -12, Blocks.wall_sign, 2, new String[] { "", super.homeName1, super.homeName2, "" });
        }
        else {
            for (int i5 = -1; i5 <= 0; ++i5) {
                this.setBlockAndMetadata(world, i5, 0, -10, super.floorBlock, super.floorMeta);
                for (int j3 = 1; j3 <= 2; ++j3) {
                    this.setAir(world, i5, j3, -11);
                    this.setBlockAndMetadata(world, i5, j3, -10, super.gateBlock, 2);
                }
            }
            this.setBlockAndMetadata(world, 2, 1, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 2, 2, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 2, 3, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 2, 1, -12, super.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, 2, 3, -12, super.plank2StairBlock, 6);
            this.setBlockAndMetadata(world, 1, 3, -12, super.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 1, 1, -11, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, 1, 2, -11, super.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 1, 3, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 0, 3, -11, super.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 0, 3, -12, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, -1, 3, -11, super.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, -1, 3, -12, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, -2, 1, -11, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, -2, 2, -11, super.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, -2, 3, -11, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -2, 1, -12, super.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, -2, 3, -12, super.plank2StairBlock, 6);
            this.placeSign(world, 2, 2, -12, Blocks.wall_sign, 2, new String[] { "", super.homeName1, super.homeName2, "" });
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, -3, j5, -12, LOTRMod.woodBeamV1, 0);
            this.setBlockAndMetadata(world, 3, j5, -12, LOTRMod.woodBeamV1, 0);
        }
        for (int i5 = -3; i5 <= 3; ++i5) {
            if (Math.abs(i5) <= 1) {
                this.setBlockAndMetadata(world, i5, 4, -13, LOTRMod.slabClayTileDyedSingle2, 5);
            }
            else {
                this.setBlockAndMetadata(world, i5, 3, -13, LOTRMod.slabClayTileDyedSingle2, 13);
            }
        }
        this.setBlockAndMetadata(world, -4, 3, -13, LOTRMod.slabClayTileDyedSingle2, 5);
        this.setBlockAndMetadata(world, 4, 3, -13, LOTRMod.slabClayTileDyedSingle2, 5);
        for (int k3 = -9; k3 <= 1; ++k3) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int j8 = 1; j8 <= 3; ++j8) {
                    this.setAir(world, i2, j8, k3);
                }
            }
            this.setBlockAndMetadata(world, 1, 0, k3, super.floorBlock, super.floorMeta);
            this.setBlockAndMetadata(world, 0, 0, k3, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -1, 0, k3, super.floorBlock, super.floorMeta);
            this.setBlockAndMetadata(world, 2, 1, k3, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -2, 1, k3, super.plank2StairBlock, 0);
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, 3, j3, k3, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, -3, j3, k3, super.plank2Block, super.plank2Meta);
            }
            this.setBlockAndMetadata(world, 2, 3, k3, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -2, 3, k3, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 1, 3, k3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, -1, 3, k3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 0, 4, k3, super.plank2Block, super.plank2Meta);
        }
        for (final int k6 : new int[] { -8, -4, 0 }) {
            this.setBlockAndMetadata(world, 0, 3, k6, super.chandelierBlock, super.chandelierMeta);
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                this.setBlockAndMetadata(world, i2, j5, 2, super.plank2Block, super.plank2Meta);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 2, super.plankBlock, super.plankMeta);
        this.setAir(world, 0, 1, 2);
        this.setAir(world, 0, 2, 2);
        this.setBlockAndMetadata(world, -1, 1, 2, super.plank2StairBlock, 0);
        this.setBlockAndMetadata(world, 1, 1, 2, super.plank2StairBlock, 1);
        this.setBlockAndMetadata(world, -1, 2, 2, super.plank2StairBlock, 4);
        this.setBlockAndMetadata(world, 1, 2, 2, super.plank2StairBlock, 5);
        for (int k3 = 3; k3 <= 9; ++k3) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int j8 = 1; j8 <= 3; ++j8) {
                    this.setAir(world, i2, j8, k3);
                }
                this.setBlockAndMetadata(world, i2, 4, k3, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, i2, 0, k3, super.plankBlock, super.plankMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 3, 6, super.chandelierBlock, super.chandelierMeta);
        for (int k3 = 5; k3 <= 7; ++k3) {
            for (int i2 = -1; i2 <= 1; ++i2) {
                this.setBlockAndMetadata(world, i2, 1, k3, super.carpetBlock, super.carpetMeta);
            }
        }
        if (super.isWealthy && random.nextBoolean()) {
            this.placeChest(world, random, 0, 0, 6, 2, LOTRChestContents.HOBBIT_HOLE_TREASURE);
        }
        for (int i5 = -3; i5 <= 3; ++i5) {
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, i5, j3, 10, super.plank2Block, super.plank2Meta);
            }
        }
        for (int i5 = -1; i5 <= 1; ++i5) {
            this.setBlockAndMetadata(world, i5, 2, 10, LOTRMod.glassPane, 0);
            this.setBlockAndMetadata(world, i5, 3, 10, LOTRMod.glassPane, 0);
            for (int k7 = 11; k7 <= 14; ++k7) {
                this.setBlockAndMetadata(world, i5, 1, k7, (Block)Blocks.grass, 0);
                for (int j8 = 2; j8 <= 3; ++j8) {
                    this.setAir(world, i5, j8, k7);
                }
            }
            this.setBlockAndMetadata(world, i5, 4, 10, super.plank2Block, super.plank2Meta);
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, -3, j5, 3, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 3, j5, 3, super.plank2Block, super.plank2Meta);
        }
        for (int i5 = -2; i5 <= 2; ++i5) {
            this.setBlockAndMetadata(world, i5, 3, 3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        }
        for (int k3 = 4; k3 <= 9; ++k3) {
            this.setBlockAndMetadata(world, -3, 3, k3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 3, 3, k3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        }
        for (int i5 = -3; i5 <= 3; ++i5) {
            this.setBlockAndMetadata(world, i5, 1, 9, Blocks.oak_stairs, 2);
        }
        this.setBlockAndMetadata(world, -3, 1, 8, Blocks.oak_stairs, 0);
        this.setBlockAndMetadata(world, 3, 1, 8, Blocks.oak_stairs, 1);
        for (int k3 = 4; k3 <= 9; ++k3) {
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, -4, j3, k3, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, 4, j3, k3, super.plank2Block, super.plank2Meta);
            }
        }
        this.setAir(world, -4, 1, 6);
        this.setAir(world, -4, 2, 6);
        this.setBlockAndMetadata(world, -4, 1, 5, super.plank2StairBlock, 3);
        this.setBlockAndMetadata(world, -4, 1, 7, super.plank2StairBlock, 2);
        this.setBlockAndMetadata(world, -4, 2, 5, super.plank2StairBlock, 7);
        this.setBlockAndMetadata(world, -4, 2, 7, super.plank2StairBlock, 6);
        this.setAir(world, 4, 1, 6);
        this.setAir(world, 4, 2, 6);
        this.setBlockAndMetadata(world, 4, 1, 5, super.plank2StairBlock, 3);
        this.setBlockAndMetadata(world, 4, 1, 7, super.plank2StairBlock, 2);
        this.setBlockAndMetadata(world, 4, 2, 5, super.plank2StairBlock, 7);
        this.setBlockAndMetadata(world, 4, 2, 7, super.plank2StairBlock, 6);
        this.setBlockAndMetadata(world, -3, 2, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 2, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 2, 9, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 2, 9, Blocks.torch, 1);
        this.setAir(world, 2, 1, -6);
        this.setBlockAndMetadata(world, 2, 0, -6, super.floorBlock, super.floorMeta);
        this.setBlockAndMetadata(world, 3, 0, -6, super.floorBlock, super.floorMeta);
        this.setAir(world, 3, 1, -6);
        this.setAir(world, 3, 2, -6);
        this.setBlockAndMetadata(world, 3, 1, -7, super.plank2StairBlock, 3);
        this.setBlockAndMetadata(world, 3, 1, -5, super.plank2StairBlock, 2);
        this.setBlockAndMetadata(world, 3, 2, -7, super.plank2StairBlock, 7);
        this.setBlockAndMetadata(world, 3, 2, -5, super.plank2StairBlock, 6);
        for (int k3 = -8; k3 <= -3; ++k3) {
            for (int i2 = 4; i2 <= 8; ++i2) {
                if (i2 != 8 || k3 != -8) {
                    for (int j8 = 1; j8 <= 3; ++j8) {
                        this.setAir(world, i2, j8, k3);
                    }
                    this.setBlockAndMetadata(world, i2, 0, k3, super.floorBlock, super.floorMeta);
                    if (i2 < 7 || k3 > -7) {
                        this.setBlockAndMetadata(world, i2, 4, k3, super.plank2Block, super.plank2Meta);
                    }
                }
            }
        }
        for (int i5 = 4; i5 <= 7; ++i5) {
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, i5, j3, -2, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, i5, j3, -8, super.plank2Block, super.plank2Meta);
            }
            this.setBlockAndMetadata(world, i5, 3, -7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, i5, 3, -3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        }
        for (int k3 = -7; k3 <= -3; ++k3) {
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, 8, j3, k3, super.plank2Block, super.plank2Meta);
            }
        }
        for (int j5 = 1; j5 <= 2; ++j5) {
            for (int i2 = 5; i2 <= 6; ++i2) {
                this.setAir(world, i2, j5, -8);
                this.setBlockAndMetadata(world, i2, j5, -9, Blocks.bookshelf, 0);
            }
            for (int k7 = -6; k7 <= -4; ++k7) {
                this.setAir(world, 8, j5, k7);
                this.setBlockAndMetadata(world, 9, j5, k7, Blocks.bookshelf, 0);
            }
        }
        this.setBlockAndMetadata(world, 6, 3, -5, super.chandelierBlock, super.chandelierMeta);
        this.setBlockAndMetadata(world, 5, 1, -5, Blocks.oak_stairs, 3);
        this.setBlockAndMetadata(world, 5, 1, -3, (Block)Blocks.wooden_slab, 8);
        this.placeChest(world, random, 7, 1, -3, 2, LOTRChestContents.HOBBIT_HOLE_STUDY);
        if (random.nextBoolean()) {
            this.placeWallBanner(world, 3, 3, -4, LOTRItemBanner.BannerType.HOBBIT, 1);
        }
        this.setAir(world, -2, 1, -6);
        this.setBlockAndMetadata(world, -2, 0, -6, super.floorBlock, super.floorMeta);
        this.setBlockAndMetadata(world, -3, 0, -6, super.floorBlock, super.floorMeta);
        this.setAir(world, -3, 1, -6);
        this.setAir(world, -3, 2, -6);
        this.setBlockAndMetadata(world, -3, 1, -7, super.plank2StairBlock, 3);
        this.setBlockAndMetadata(world, -3, 1, -5, super.plank2StairBlock, 2);
        this.setBlockAndMetadata(world, -3, 2, -7, super.plank2StairBlock, 7);
        this.setBlockAndMetadata(world, -3, 2, -5, super.plank2StairBlock, 6);
        for (int k3 = -7; k3 <= -4; ++k3) {
            for (int i2 = -4; i2 >= -7; --i2) {
                this.setBlockAndMetadata(world, i2, 0, k3, super.floorBlock, super.floorMeta);
                for (int j8 = 1; j8 <= 3; ++j8) {
                    this.setAir(world, i2, j8, k3);
                }
                this.setBlockAndMetadata(world, i2, 4, k3, super.plank2Block, super.plank2Meta);
            }
        }
        for (int i5 = -4; i5 >= -7; --i5) {
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, i5, j3, -8, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, i5, j3, -3, super.plank2Block, super.plank2Meta);
            }
            this.setBlockAndMetadata(world, i5, 3, -7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, i5, 3, -4, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        }
        for (int k3 = -7; k3 <= -3; ++k3) {
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, -8, j3, k3, super.plank2Block, super.plank2Meta);
            }
        }
        for (int k3 = -7; k3 <= -6; ++k3) {
            for (int i2 = -5; i2 >= -6; --i2) {
                this.setBlockAndMetadata(world, i2, 1, k3, super.carpetBlock, super.carpetMeta);
            }
        }
        for (int i5 = -5; i5 >= -6; --i5) {
            this.setBlockAndMetadata(world, i5, 0, -8, super.floorBlock, super.floorMeta);
            this.setBlockAndMetadata(world, i5, 1, -8, (Block)Blocks.wooden_slab, 8);
            this.setBlockAndMetadata(world, i5, 2, -8, Blocks.bookshelf, 0);
            this.setBlockAndMetadata(world, i5, 1, -9, super.plank2Block, super.plank2Meta);
        }
        this.setBlockAndMetadata(world, -4, 1, -4, Blocks.planks, 0);
        this.setBlockAndMetadata(world, -7, 1, -4, Blocks.planks, 0);
        this.setBlockAndMetadata(world, -4, 2, -4, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -7, 2, -4, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -5, 1, -5, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -5, 1, -4, super.bedBlock, 8);
        this.setBlockAndMetadata(world, -6, 1, -5, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -6, 1, -4, super.bedBlock, 8);
        this.spawnItemFrame(world, -8, 2, -6, 1, new ItemStack(Items.clock));
        this.setBlockAndMetadata(world, 4, 0, 6, super.plankBlock, super.plankMeta);
        for (int i5 = 5; i5 <= 6; ++i5) {
            this.setBlockAndMetadata(world, i5, 0, 7, super.floorBlock, super.floorMeta);
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setAir(world, i5, j3, 7);
            }
            this.setBlockAndMetadata(world, i5, 4, 7, super.plank2Block, super.plank2Meta);
        }
        for (int i5 = 5; i5 <= 7; ++i5) {
            this.setBlockAndMetadata(world, i5, 0, 6, super.floorBlock, super.floorMeta);
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setAir(world, i5, j3, 6);
            }
            this.setBlockAndMetadata(world, i5, 4, 6, super.plank2Block, super.plank2Meta);
        }
        for (int i5 = 5; i5 <= 8; ++i5) {
            this.setBlockAndMetadata(world, i5, 0, 5, super.floorBlock, super.floorMeta);
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setAir(world, i5, j3, 5);
            }
            this.setBlockAndMetadata(world, i5, 4, 5, super.plank2Block, super.plank2Meta);
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, 7, j5, 7, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 8, j5, 6, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 9, j5, 5, super.plank2Block, super.plank2Meta);
        }
        this.setBlockAndMetadata(world, 7, 2, 6, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 8, 2, 5, Blocks.torch, 1);
        for (int k3 = 4; k3 >= -1; --k3) {
            for (int i2 = 4; i2 <= 9; ++i2) {
                this.setBlockAndMetadata(world, i2, 0, k3, super.floorBlock, super.floorMeta);
                for (int j8 = 1; j8 <= 3; ++j8) {
                    this.setAir(world, i2, j8, k3);
                }
                this.setBlockAndMetadata(world, i2, 4, k3, super.plank2Block, super.plank2Meta);
            }
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, 3, j3, k3, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, 10, j3, k3, super.plank2Block, super.plank2Meta);
            }
            this.setBlockAndMetadata(world, 4, 3, k3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 9, 3, k3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        }
        for (int k3 = 2; k3 >= 0; --k3) {
            this.setBlockAndMetadata(world, 4, 1, k3, Blocks.oak_stairs, 0);
            this.setBlockAndMetadata(world, 9, 1, k3, Blocks.oak_stairs, 1);
        }
        this.setBlockAndMetadata(world, 4, 1, -1, Blocks.oak_stairs, 3);
        this.setBlockAndMetadata(world, 4, 1, 3, Blocks.oak_stairs, 2);
        this.setBlockAndMetadata(world, 9, 1, -1, Blocks.oak_stairs, 3);
        this.setBlockAndMetadata(world, 9, 1, 3, Blocks.oak_stairs, 2);
        this.setBlockAndMetadata(world, 6, 3, 1, super.chandelierBlock, super.chandelierMeta);
        this.setBlockAndMetadata(world, 7, 3, 1, super.chandelierBlock, super.chandelierMeta);
        this.setBlockAndMetadata(world, 6, 1, 2, Blocks.planks, 1);
        this.setBlockAndMetadata(world, 7, 1, 2, Blocks.planks, 1);
        this.setBlockAndMetadata(world, 6, 1, 1, (Block)Blocks.wooden_slab, 9);
        this.setBlockAndMetadata(world, 7, 1, 1, (Block)Blocks.wooden_slab, 9);
        this.setBlockAndMetadata(world, 6, 1, 0, Blocks.planks, 1);
        this.setBlockAndMetadata(world, 7, 1, 0, Blocks.planks, 1);
        for (int i5 = 6; i5 <= 7; ++i5) {
            for (int k7 = 2; k7 >= 0; --k7) {
                if (random.nextInt(3) == 0) {
                    this.placeMug(world, random, i5, 2, k7, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
                }
                else if (random.nextBoolean()) {
                    this.placePlateWithCertainty(world, random, i5, 2, k7, super.plateBlock, LOTRFoods.HOBBIT);
                }
                else {
                    this.placePlate(world, random, i5, 2, k7, super.plateBlock, LOTRFoods.HOBBIT);
                }
            }
        }
        this.setBlockAndMetadata(world, 5, 3, 7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        this.setBlockAndMetadata(world, 6, 3, 7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        this.setBlockAndMetadata(world, 7, 3, 6, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        this.setBlockAndMetadata(world, 8, 3, 5, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        for (int j5 = 1; j5 <= 3; ++j5) {
            for (int i2 = 5; i2 <= 6; ++i2) {
                this.setBlockAndMetadata(world, i2, j5, 8, super.plank2Block, super.plank2Meta);
            }
            for (int i2 = 8; i2 <= 9; ++i2) {
                this.setBlockAndMetadata(world, i2, j5, -2, super.plank2Block, super.plank2Meta);
            }
        }
        this.setBlockAndMetadata(world, -4, 0, 6, super.plankBlock, super.plankMeta);
        for (int k3 = 7; k3 >= 3; --k3) {
            for (int i2 = -5; i2 >= -7; --i2) {
                this.setBlockAndMetadata(world, i2, 0, k3, (Block)Blocks.double_stone_slab, 0);
                for (int j8 = 1; j8 <= 3; ++j8) {
                    this.setAir(world, i2, j8, k3);
                }
                this.setBlockAndMetadata(world, i2, 4, k3, super.plank2Block, super.plank2Meta);
            }
        }
        for (int k3 = 6; k3 >= 3; --k3) {
            for (int i2 = -5; i2 >= -6; --i2) {
                this.setBlockAndMetadata(world, i2, 0, k3, super.floorBlock, super.floorMeta);
            }
        }
        this.setBlockAndMetadata(world, -5, 1, 8, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -6, 1, 8, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -7, 1, 8, LOTRMod.hobbitTable, 0);
        for (int i5 = -7; i5 <= -5; ++i5) {
            this.setAir(world, i5, 2, 8);
            this.setBlockAndMetadata(world, i5, 2, 9, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, i5, 3, 8, (Block)Blocks.double_stone_slab, 0);
        }
        this.setBlockAndMetadata(world, -8, 1, 8, super.plank2Block, super.plank2Meta);
        this.setBlockAndMetadata(world, -8, 2, 8, super.plank2Block, super.plank2Meta);
        for (int k3 = 6; k3 <= 7; ++k3) {
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, -8, j3, k3, super.plank2Block, super.plank2Meta);
            }
        }
        for (int k3 = 3; k3 <= 5; ++k3) {
            this.setBlockAndMetadata(world, -8, 0, k3, (Block)Blocks.double_stone_slab, 0);
            this.setAir(world, -8, 2, k3);
            this.setBlockAndMetadata(world, -9, 2, k3, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -8, 3, k3, (Block)Blocks.double_stone_slab, 0);
        }
        this.setBlockAndMetadata(world, -8, 1, 4, super.plank2Block, super.plank2Meta);
        this.setBlockAndMetadata(world, -8, 1, 5, LOTRMod.hobbitOven, 4);
        this.setBlockAndMetadata(world, -8, 1, 3, LOTRMod.hobbitOven, 4);
        this.setBlockAndMetadata(world, -8, 1, 4, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -6, 3, 5, super.chandelierBlock, super.chandelierMeta);
        for (int i5 = -4; i5 >= -9; --i5) {
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, i5, j3, 2, super.plank2Block, super.plank2Meta);
            }
        }
        this.setBlockAndMetadata(world, -6, 0, 2, super.plankBlock, super.plankMeta);
        this.setAir(world, -6, 1, 2);
        this.setAir(world, -6, 2, 2);
        this.setBlockAndMetadata(world, -7, 1, 2, super.plank2StairBlock, 0);
        this.setBlockAndMetadata(world, -5, 1, 2, super.plank2StairBlock, 1);
        this.setBlockAndMetadata(world, -7, 2, 2, super.plank2StairBlock, 4);
        this.setBlockAndMetadata(world, -5, 2, 2, super.plank2StairBlock, 5);
        for (int k3 = -2; k3 <= 1; ++k3) {
            for (int i2 = -9; i2 <= -4; ++i2) {
                this.setBlockAndMetadata(world, i2, 0, k3, super.plankBlock, super.plankMeta);
                for (int j8 = 1; j8 <= 3; ++j8) {
                    this.setAir(world, i2, j8, k3);
                }
                this.setBlockAndMetadata(world, i2, 4, k3, (Block)Blocks.double_stone_slab, 0);
            }
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, -10, j3, k3, super.plank2Block, super.plank2Meta);
            }
        }
        for (int i5 = -9; i5 <= -4; ++i5) {
            this.setBlockAndMetadata(world, i5, 1, -2, (Block)Blocks.wooden_slab, 8);
            this.setBlockAndMetadata(world, i5, 3, -2, (Block)Blocks.wooden_slab, 0);
        }
        for (int k3 = -2; k3 <= 1; ++k3) {
            this.setBlockAndMetadata(world, -4, 1, k3, (Block)Blocks.wooden_slab, 8);
            this.setBlockAndMetadata(world, -4, 3, k3, (Block)Blocks.wooden_slab, 0);
            this.setBlockAndMetadata(world, -9, 1, k3, (Block)Blocks.wooden_slab, 8);
            this.setBlockAndMetadata(world, -9, 3, k3, (Block)Blocks.wooden_slab, 0);
        }
        this.setBlockAndMetadata(world, -8, 1, 1, (Block)Blocks.wooden_slab, 8);
        this.setBlockAndMetadata(world, -8, 3, 1, (Block)Blocks.wooden_slab, 0);
        this.setBlockAndMetadata(world, -6, 3, 1, Blocks.torch, 4);
        for (int k3 = -2; k3 <= 1; ++k3) {
            if (random.nextInt(3) != 0) {
                final Block cakeBlock = LOTRWorldGenHobbitStructure.getRandomCakeBlock(random);
                this.setBlockAndMetadata(world, -4, 2, k3, cakeBlock, 0);
            }
        }
        for (int i5 = -7; i5 <= -6; ++i5) {
            this.placePlateWithCertainty(world, random, i5, 2, -2, super.plateBlock, LOTRFoods.HOBBIT);
        }
        this.placeBarrel(world, random, -5, 2, -2, 3, LOTRFoods.HOBBIT_DRINK);
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, -9, j5, -3, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -4, j5, 3, super.plank2Block, super.plank2Meta);
        }
        this.placeChest(world, random, -8, 2, -2, (Block)Blocks.chest, 3, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.placeChest(world, random, -9, 2, -1, (Block)Blocks.chest, 4, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.placeChest(world, random, -9, 2, 0, (Block)Blocks.chest, 4, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.placeChest(world, random, -8, 2, 1, (Block)Blocks.chest, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
        if (gateFlip) {
            this.setBlockAndMetadata(world, -1, 2, -9, (Block)Blocks.tripwire_hook, 0);
        }
        else {
            this.setBlockAndMetadata(world, 1, 2, -9, (Block)Blocks.tripwire_hook, 0);
        }
        final int grassRadius = radius - 3;
        for (int grass3 = MathHelper.getRandomIntegerInRange(random, 24, 40), l = 0; l < grass3; ++l) {
            final int i6 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            final int k8 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            final int j9 = this.getTopBlock(world, i6, k8);
            this.plantTallGrass(world, random, i6, j9, k8);
        }
        for (int flowers = MathHelper.getRandomIntegerInRange(random, 2, 5), m = 0; m < flowers; ++m) {
            final int i7 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            final int k9 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            final int j10 = this.getTopBlock(world, i7, k9);
            this.plantFlower(world, random, i7, j10, k9);
        }
        if (random.nextInt(4) == 0) {
            final int i6 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            final int k8 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            final int j9 = this.getTopBlock(world, i6, k8);
            final WorldGenerator treeGen = (WorldGenerator)LOTRBiome.shire.func_150567_a(random);
            treeGen.generate(world, random, this.getX(i6, k8), this.getY(j9), this.getZ(i6, k8));
        }
        final int homeRadius = MathHelper.floor_double(radius * 1.5);
        this.spawnHobbitCouple(world, 0, 1, 0, homeRadius);
        return true;
    }
}
