// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDaleSoldier;
import lotr.common.entity.npc.LOTREntityDaleArcher;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDaleCaptain;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDaleFortress extends LOTRWorldGenDaleStructure
{
    public LOTRWorldGenDaleFortress(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 12);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            int maxEdgeHeight = 0;
            for (int i2 = -12; i2 <= 12; ++i2) {
                for (int k2 = -12; k2 <= 12; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                    if ((Math.abs(i2) == 12 || Math.abs(k2) == 12) && j2 > maxEdgeHeight) {
                        maxEdgeHeight = j2;
                    }
                }
            }
            super.originY = this.getY(maxEdgeHeight);
        }
        for (int i3 = -11; i3 <= 11; ++i3) {
            for (int k3 = -11; k3 <= 11; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                this.layFoundation(world, i3, k3);
                if (i4 <= 9 && k4 <= 9) {
                    for (int j3 = 1; j3 <= 8; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                    if (i4 <= 8 && k4 <= 8 && ((i4 == 8 && k4 >= 4) || (k4 == 8 && i4 >= 4))) {
                        for (int j3 = 1; j3 <= 3; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    if ((i4 == 9 && k4 == 2) || (k4 == 9 && i4 == 2)) {
                        for (int j3 = 1; j3 <= 5; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.pillarBlock, super.pillarMeta);
                        }
                    }
                    else if ((i4 == 9 && (k4 == 3 || k4 == 5 || k4 == 9)) || (k4 == 9 && (i4 == 3 || i4 == 5 || i4 == 9))) {
                        for (int j3 = 1; j3 <= 5; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    else if (i4 == 9 || k4 == 9) {
                        for (int j3 = 4; j3 <= 5; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    if (i4 == 9) {
                        this.setBlockAndMetadata(world, i3, 3, -8, super.brickStairBlock, 7);
                        this.setBlockAndMetadata(world, i3, 3, -6, super.brickStairBlock, 6);
                        this.setBlockAndMetadata(world, i3, 3, 6, super.brickStairBlock, 7);
                        this.setBlockAndMetadata(world, i3, 3, 8, super.brickStairBlock, 6);
                    }
                    if (k4 == 9) {
                        this.setBlockAndMetadata(world, -8, 3, k3, super.brickStairBlock, 4);
                        this.setBlockAndMetadata(world, -6, 3, k3, super.brickStairBlock, 5);
                        this.setBlockAndMetadata(world, 6, 3, k3, super.brickStairBlock, 4);
                        this.setBlockAndMetadata(world, 8, 3, k3, super.brickStairBlock, 5);
                    }
                    if ((i4 == 9 && k4 <= 5) || (k4 == 9 && i4 <= 5)) {
                        this.setBlockAndMetadata(world, i3, 6, k3, super.brickWallBlock, super.brickWallMeta);
                    }
                    if ((i4 == 6 && k4 <= 5) || (k4 == 6 && i4 <= 5)) {
                        this.setBlockAndMetadata(world, i3, 6, k3, super.brickWallBlock, super.brickWallMeta);
                        if ((i4 == 6 && k4 <= 3) || (k4 == 6 && i4 <= 3)) {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.brickBlock, super.brickMeta);
                        }
                        if (i3 == -5) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.brickStairBlock, 4);
                        }
                        else if (i3 == 5) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.brickStairBlock, 5);
                        }
                        else if (k3 == -5) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.brickStairBlock, 7);
                        }
                        else if (k3 == 5) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.brickStairBlock, 6);
                        }
                    }
                    if (i4 <= 8 && k4 <= 8 && (i4 >= 7 || k4 >= 7)) {
                        if (i4 <= 2 || k4 <= 2) {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.plankBlock, super.plankMeta);
                        }
                        else if (i3 == -3) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.plankStairBlock, 4);
                            this.setBlockAndMetadata(world, i3, 5, k3, super.plankStairBlock, 1);
                        }
                        else if (i3 == 3) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.plankStairBlock, 5);
                            this.setBlockAndMetadata(world, i3, 5, k3, super.plankStairBlock, 0);
                        }
                        else if (k3 == -3) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.plankStairBlock, 7);
                            this.setBlockAndMetadata(world, i3, 5, k3, super.plankStairBlock, 2);
                        }
                        else if (k3 == 3) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.plankStairBlock, 6);
                            this.setBlockAndMetadata(world, i3, 5, k3, super.plankStairBlock, 3);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.plankBlock, super.plankMeta);
                        }
                    }
                    if (i4 == 6 && k4 == 6) {
                        for (int j3 = 1; j3 <= 5; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    if ((i4 == 6 || i4 == 9) && (k4 == 6 || k4 == 9)) {
                        for (int j3 = 6; j3 <= 7; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    if ((i4 == 9 && (k4 == 7 || k4 == 8)) || (k4 == 9 && (i4 == 7 || i4 == 8))) {
                        for (int j3 = 6; j3 <= 7; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.barsBlock, 0);
                        }
                    }
                }
            }
        }
        for (int j4 = 1; j4 <= 3; ++j4) {
            for (int i5 = -1; i5 <= 1; ++i5) {
                this.setBlockAndMetadata(world, i5, j4, -9, LOTRMod.gateWooden, 2);
                this.setBlockAndMetadata(world, i5, j4, 9, super.brickBlock, super.brickMeta);
            }
            for (int k3 = -1; k3 <= 1; ++k3) {
                this.setBlockAndMetadata(world, -9, j4, k3, LOTRMod.gateWooden, 5);
                this.setBlockAndMetadata(world, 9, j4, k3, LOTRMod.gateWooden, 4);
            }
        }
        for (final int i2 : new int[] { -9, 6 }) {
            for (final int k5 : new int[] { -9, 6 }) {
                for (int i6 = i2; i6 <= i2 + 3; ++i6) {
                    this.setBlockAndMetadata(world, i6, 8, k5, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i6, 8, k5 + 3, super.brickBlock, super.brickMeta);
                }
                for (int k6 = k5 + 1; k6 <= k5 + 2; ++k6) {
                    this.setBlockAndMetadata(world, i2, 8, k6, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i2 + 3, 8, k6, super.brickBlock, super.brickMeta);
                }
                for (int i6 = i2; i6 <= i2 + 3; ++i6) {
                    for (int k7 = k5; k7 <= k5 + 3; ++k7) {
                        this.setBlockAndMetadata(world, i6, 9, k7, super.roofBlock, super.roofMeta);
                        this.setBlockAndMetadata(world, i6, 10, k7, super.roofSlabBlock, super.roofSlabMeta);
                    }
                }
                for (int i6 = i2 - 1; i6 <= i2 + 4; ++i6) {
                    this.setBlockAndMetadata(world, i6, 9, k5 - 1, super.roofStairBlock, 2);
                    this.setBlockAndMetadata(world, i6, 9, k5 + 4, super.roofStairBlock, 3);
                }
                for (int k6 = k5; k6 <= k5 + 3; ++k6) {
                    this.setBlockAndMetadata(world, i2 - 1, 9, k6, super.roofStairBlock, 1);
                    this.setBlockAndMetadata(world, i2 + 4, 9, k6, super.roofStairBlock, 0);
                }
                this.setBlockAndMetadata(world, i2, 8, k5 - 1, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, i2 + 3, 8, k5 - 1, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, i2 + 4, 8, k5, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, i2 + 4, 8, k5 + 3, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, i2 + 3, 8, k5 + 4, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i2, 8, k5 + 4, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i2 - 1, 8, k5 + 3, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i2 - 1, 8, k5, super.roofStairBlock, 5);
            }
        }
        this.setBlockAndMetadata(world, -6, 8, -8, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -6, 8, -7, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -7, 8, -6, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -8, 8, -6, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 6, 8, -8, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 6, 8, -7, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 7, 8, -6, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 8, 8, -6, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -6, 8, 8, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -6, 8, 7, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -7, 8, 6, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -8, 8, 6, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 6, 8, 8, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 6, 8, 7, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 7, 8, 6, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 8, 8, 6, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -8, 8, -8, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 8, 8, -8, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -8, 8, 8, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 8, 8, 8, Blocks.torch, 1);
        for (final int i2 : new int[] { -2, 2 }) {
            for (int j3 = 6; j3 <= 8; ++j3) {
                this.setBlockAndMetadata(world, i2, j3, -9, super.pillarBlock, super.pillarMeta);
            }
            this.setBlockAndMetadata(world, i2, 9, -9, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i2, 5, -10, Blocks.torch, 4);
            this.placeWallBanner(world, i2, 8, -9, LOTRItemBanner.BannerType.DALE, 2);
        }
        for (int j4 = 1; j4 <= 4; ++j4) {
            this.setBlockAndMetadata(world, -7, j4, -7, Blocks.ladder, 4);
            this.setBlockAndMetadata(world, 7, j4, -7, Blocks.ladder, 5);
        }
        this.setBlockAndMetadata(world, 0, 4, -8, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 4, 8, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -8, 4, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 8, 4, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -6, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -5, 3, -6, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 6, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 5, 3, -6, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -6, 3, 5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -5, 3, 6, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 6, 3, 5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 5, 3, 6, Blocks.torch, 1);
        for (int k8 = -8; k8 <= -4; ++k8) {
            this.setBlockAndMetadata(world, -3, 0, k8, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, -2, 0, k8, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, 2, 0, k8, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, 3, 0, k8, (Block)Blocks.grass, 0);
        }
        for (int k8 = 4; k8 <= 8; ++k8) {
            this.setBlockAndMetadata(world, -3, 0, k8, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, -2, 0, k8, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, 2, 0, k8, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, 3, 0, k8, (Block)Blocks.grass, 0);
        }
        for (int i3 = -8; i3 <= -4; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -3, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, i3, 0, -2, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, i3, 0, 2, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, i3, 0, 3, (Block)Blocks.grass, 0);
        }
        for (int i3 = 4; i3 <= 8; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -3, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, i3, 0, -2, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, i3, 0, 2, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, i3, 0, 3, (Block)Blocks.grass, 0);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                this.setBlockAndMetadata(world, i3, 1, k3, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i3, 2, k3, super.brickBlock, super.brickMeta);
                if (i4 == 1 && k4 == 1) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickWallBlock, super.brickWallMeta);
                }
                else if (i4 == 1 || k4 == 1) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i3, 4, k3, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i3, 5, k3, super.brickWallBlock, super.brickWallMeta);
                }
                if (i3 == 0 && k3 == 0) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i3, 4, k3, LOTRMod.hearth, 0);
                    this.setBlockAndMetadata(world, i3, 5, k3, (Block)Blocks.fire, 0);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 6, 0, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, -1, 6, 0, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 6, 0, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 6, -1, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 6, 1, super.roofStairBlock, 3);
        for (int k8 = -7; k8 <= -4; ++k8) {
            for (int i5 = -7; i5 <= -4; ++i5) {
                this.setBlockAndMetadata(world, i5, 0, k8, LOTRMod.dirtPath, 0);
            }
            for (int i5 = 4; i5 <= 7; ++i5) {
                this.setBlockAndMetadata(world, i5, 0, k8, LOTRMod.dirtPath, 0);
            }
        }
        this.setBlockAndMetadata(world, -4, 1, -5, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, -5, 1, -4, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 5, 1, -4, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 4, 1, -5, super.brickWallBlock, super.brickWallMeta);
        for (int k8 = 4; k8 <= 7; ++k8) {
            for (int i5 = -7; i5 <= -4; ++i5) {
                this.setBlockAndMetadata(world, i5, 0, k8, super.plankBlock, super.plankMeta);
            }
            for (int i5 = 4; i5 <= 7; ++i5) {
                this.setBlockAndMetadata(world, i5, 0, k8, super.plankBlock, super.plankMeta);
            }
        }
        for (int k8 = 4; k8 <= 6; ++k8) {
            for (int i5 = -6; i5 <= -4; ++i5) {
                this.setBlockAndMetadata(world, i5, 4, k8, Blocks.wool, 11);
            }
            for (int i5 = 4; i5 <= 6; ++i5) {
                this.setBlockAndMetadata(world, i5, 4, k8, Blocks.wool, 11);
            }
        }
        for (int j4 = 1; j4 <= 3; ++j4) {
            this.setBlockAndMetadata(world, -4, j4, 4, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 4, j4, 4, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -4, 0, -4, (Block)Blocks.grass, 0);
        this.setBlockAndMetadata(world, -3, 0, -3, (Block)Blocks.grass, 0);
        this.setBlockAndMetadata(world, 4, 0, -4, (Block)Blocks.grass, 0);
        this.setBlockAndMetadata(world, 3, 0, -3, (Block)Blocks.grass, 0);
        this.setBlockAndMetadata(world, -4, 0, 4, (Block)Blocks.grass, 0);
        this.setBlockAndMetadata(world, -3, 0, 3, (Block)Blocks.grass, 0);
        this.setBlockAndMetadata(world, 4, 0, 4, (Block)Blocks.grass, 0);
        this.setBlockAndMetadata(world, 3, 0, 3, (Block)Blocks.grass, 0);
        for (int j4 = 1; j4 <= 3; ++j4) {
            this.setBlockAndMetadata(world, -7, j4, 7, super.plankBlock, super.plankMeta);
        }
        this.setBlockAndMetadata(world, -7, 3, 6, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -6, 3, 7, super.plankStairBlock, 4);
        for (final int j5 : new int[] { 1, 2 }) {
            this.setBlockAndMetadata(world, -7, j5, 5, LOTRMod.strawBed, 0);
            this.setBlockAndMetadata(world, -7, j5, 6, LOTRMod.strawBed, 8);
            this.setBlockAndMetadata(world, -5, j5, 7, LOTRMod.strawBed, 3);
            this.setBlockAndMetadata(world, -6, j5, 7, LOTRMod.strawBed, 11);
        }
        for (int j4 = 1; j4 <= 3; ++j4) {
            this.setBlockAndMetadata(world, 7, j4, 7, super.plankBlock, super.plankMeta);
        }
        this.setBlockAndMetadata(world, 7, 3, 6, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 6, 3, 7, super.plankStairBlock, 5);
        for (int i3 = 4; i3 <= 6; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 7, super.plankBlock, super.plankMeta);
            if (i3 <= 5) {
                this.placeBarrel(world, random, i3, 2, 7, 2, LOTRFoods.DALE_DRINK);
            }
        }
        for (int k8 = 4; k8 <= 6; ++k8) {
            this.setBlockAndMetadata(world, 7, 1, k8, super.plankBlock, super.plankMeta);
            if (k8 <= 5) {
                this.placeBarrel(world, random, 7, 2, k8, 5, LOTRFoods.DALE_DRINK);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, 8, super.floorBlock, super.floorMeta);
        }
        this.setBlockAndMetadata(world, -3, 1, 8, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -2, 1, 8, super.plankBlock, super.plankMeta);
        this.placeChest(world, random, -3, 2, 8, 2, LOTRChestContents.DALE_WATCHTOWER);
        this.placeChest(world, random, -2, 2, 8, 2, LOTRChestContents.DALE_WATCHTOWER);
        this.setBlockAndMetadata(world, 2, 1, 8, LOTRMod.daleTable, 0);
        this.setBlockAndMetadata(world, 3, 1, 8, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 0, 1, 7, LOTRMod.commandTable, 0);
        this.setBlockAndMetadata(world, 6, 1, 6, Blocks.furnace, 2);
        final LOTREntityDaleCaptain captain = new LOTREntityDaleCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 3, 16);
        for (int soldiers = 3 + random.nextInt(4), l = 0; l < soldiers; ++l) {
            final LOTREntityDaleSoldier soldier = (random.nextInt(3) == 0) ? new LOTREntityDaleArcher(world) : new LOTREntityDaleSoldier(world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 1, 3, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityDaleSoldier.class, LOTREntityDaleArcher.class);
        respawner.setCheckRanges(16, -6, 10, 12);
        respawner.setSpawnRanges(10, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    private void layFoundation(final World world, final int i, final int k) {
        for (int j = 0; j == 0 || (!this.isOpaque(world, i, j, k) && this.getY(j) >= 0); --j) {
            this.setBlockAndMetadata(world, i, j, k, super.floorBlock, super.floorMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
}
