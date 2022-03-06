// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.entity.EntityCreature;
import lotr.common.LOTRFoods;
import com.google.common.math.IntMath;
import net.minecraft.block.Block;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;

public class LOTRWorldGenGondorFortress extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorFortress(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.bedBlock = Blocks.bed;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 12);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -14; i2 <= 14; ++i2) {
                for (int k2 = -14; k2 <= 14; ++k2) {
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
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -11; i3 <= 11; ++i3) {
            for (int k3 = -11; k3 <= 11; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 >= 9 && i4 <= 11 && k4 <= 5) || (k4 >= 9 && k4 <= 11 && i4 <= 5)) {
                    boolean pillar = false;
                    if (i4 == 11) {
                        pillar = (k4 == 2 || k4 == 5);
                    }
                    else if (k4 == 11) {
                        pillar = (i4 == 2 || i4 == 5);
                    }
                    for (int j3 = 5; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        if (pillar && j3 >= 1) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.pillarBlock, super.pillarMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                        }
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                    this.setBlockAndMetadata(world, i3, 6, k3, super.brickBlock, super.brickMeta);
                    for (int j3 = 7; j3 <= 9; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                    if (i4 == 9 || i4 == 11 || k4 == 9 || k4 == 11) {
                        this.setBlockAndMetadata(world, i3, 7, k3, super.brick2WallBlock, super.brick2WallMeta);
                        if (i4 == 5 || k4 == 5) {
                            this.setBlockAndMetadata(world, i3, 8, k3, Blocks.torch, 5);
                        }
                    }
                }
                else {
                    for (int j2 = 0; (j2 == 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 9; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        for (final int i5 : new int[] { -10, 10 }) {
            for (final int k5 : new int[] { -10, 10 }) {
                for (int i6 = i5 - 4; i6 <= i5 + 4; ++i6) {
                    for (int k6 = k5 - 4; k6 <= k5 + 4; ++k6) {
                        final int i7 = Math.abs(i6 - i5);
                        final int k7 = Math.abs(k6 - k5);
                        final int i8 = Math.abs(i6);
                        final int k8 = Math.abs(k6);
                        if (i7 != 4 || k7 < 3) {
                            if (k7 != 4 || i7 < 3) {
                                if ((i7 == 4 && k7 <= 2) || (k7 == 4 && i7 <= 2) || (i7 == 3 && k7 == 3)) {
                                    boolean pillar2 = false;
                                    if (i7 == 4) {
                                        pillar2 = (k7 == 2);
                                    }
                                    else if (k7 == 4) {
                                        pillar2 = (i7 == 2);
                                    }
                                    for (int j4 = 5; (j4 >= 0 || !this.isOpaque(world, i6, j4, k6)) && this.getY(j4) >= 0; --j4) {
                                        if (pillar2 && j4 >= 1) {
                                            this.setBlockAndMetadata(world, i6, j4, k6, super.pillarBlock, super.pillarMeta);
                                        }
                                        else {
                                            this.setBlockAndMetadata(world, i6, j4, k6, super.brickBlock, super.brickMeta);
                                        }
                                        this.setGrassToDirt(world, i6, j4 - 1, k6);
                                    }
                                    this.setBlockAndMetadata(world, i6, 6, k6, super.brickBlock, super.brickMeta);
                                    for (int j4 = 7; j4 <= 9; ++j4) {
                                        this.setAir(world, i6, j4, k6);
                                    }
                                    if (i7 <= 1 || k7 <= 1) {
                                        this.setBlockAndMetadata(world, i6, 7, k6, super.brick2WallBlock, super.brick2WallMeta);
                                        if (i8 == 10 || k8 == 10) {
                                            if (i8 <= 10 && k8 <= 10) {
                                                this.setAir(world, i6, 7, k6);
                                            }
                                            else {
                                                this.setBlockAndMetadata(world, i6, 8, k6, Blocks.torch, 5);
                                            }
                                        }
                                    }
                                    else {
                                        this.setBlockAndMetadata(world, i6, 7, k6, super.brick2Block, super.brick2Meta);
                                        this.setBlockAndMetadata(world, i6, 8, k6, super.brick2SlabBlock, super.brick2SlabMeta);
                                    }
                                }
                                else {
                                    for (int j5 = 0; (j5 == 0 || !this.isOpaque(world, i6, j5, k6)) && this.getY(j5) >= 0; --j5) {
                                        this.setBlockAndMetadata(world, i6, j5, k6, super.brickBlock, super.brickMeta);
                                        this.setGrassToDirt(world, i6, j5 - 1, k6);
                                    }
                                    for (int j5 = 1; j5 <= 9; ++j5) {
                                        this.setAir(world, i6, j5, k6);
                                    }
                                    this.setBlockAndMetadata(world, i6, 6, k6, super.plankBlock, super.plankMeta);
                                    if ((i8 == 9 && k8 == 9) || (i8 == 11 && k8 == 11)) {
                                        this.setBlockAndMetadata(world, i6, 5, k6, LOTRMod.chandelier, 2);
                                    }
                                }
                            }
                        }
                    }
                }
                for (int j6 = 1; j6 <= 8; ++j6) {
                    this.setBlockAndMetadata(world, i5, j6, k5, super.woodBeamBlock, super.woodBeamMeta);
                }
                this.setBlockAndMetadata(world, i5, 9, k5, super.plankSlabBlock, super.plankSlabMeta);
                this.setBlockAndMetadata(world, i5, 8, k5 - 1, Blocks.torch, 4);
                this.setBlockAndMetadata(world, i5, 8, k5 + 1, Blocks.torch, 3);
                this.setBlockAndMetadata(world, i5 - 1, 8, k5, Blocks.torch, 1);
                this.setBlockAndMetadata(world, i5 + 1, 8, k5, Blocks.torch, 2);
                if (i5 < 0) {
                    for (int j6 = 1; j6 <= 5; ++j6) {
                        this.setBlockAndMetadata(world, i5 + 1, j6, k5, Blocks.ladder, 4);
                    }
                    this.setBlockAndMetadata(world, i5 + 1, 6, k5, Blocks.trapdoor, 11);
                }
                if (i5 > 0) {
                    for (int j6 = 1; j6 <= 5; ++j6) {
                        this.setBlockAndMetadata(world, i5 - 1, j6, k5, Blocks.ladder, 5);
                    }
                    this.setBlockAndMetadata(world, i5 - 1, 6, k5, Blocks.trapdoor, 10);
                }
                if (k5 < 0) {
                    for (int j6 = 1; j6 <= 5; ++j6) {
                        this.setBlockAndMetadata(world, i5, j6, k5 + 1, Blocks.ladder, 3);
                    }
                    this.setBlockAndMetadata(world, i5, 6, k5 + 1, Blocks.trapdoor, 8);
                }
                if (k5 > 0) {
                    for (int j6 = 1; j6 <= 5; ++j6) {
                        this.setBlockAndMetadata(world, i5, j6, k5 - 1, Blocks.ladder, 2);
                    }
                    this.setBlockAndMetadata(world, i5, 6, k5 - 1, Blocks.trapdoor, 9);
                }
            }
        }
        for (final int i5 : new int[] { -11, 11 }) {
            final int i9 = i5 + Integer.signum(i5) * -1;
            for (final int k9 : new int[] { -4, 3 }) {
                this.setBlockAndMetadata(world, i5, 2, k9, super.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i5, 2, k9 + 1, super.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i5, 4, k9, super.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i5, 4, k9 + 1, super.brickStairBlock, 6);
                for (int k6 = k9; k6 <= k9 + 1; ++k6) {
                    this.setAir(world, i5, 3, k6);
                    this.setBlockAndMetadata(world, i9, 3, k6, LOTRMod.brick, 5);
                }
            }
            this.setBlockAndMetadata(world, i5, 2, -1, super.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i5, 2, 0, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, i5, 2, 1, super.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i5, 4, -1, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 4, 0, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i5, 4, 1, super.brickStairBlock, 6);
            for (int k10 = -1; k10 <= 1; ++k10) {
                this.setAir(world, i5, 3, k10);
                this.setBlockAndMetadata(world, i9, 3, k10, LOTRMod.brick, 5);
            }
        }
        for (final int k2 : new int[] { -11, 11 }) {
            final int k11 = k2 + Integer.signum(k2) * -1;
            for (final int i10 : new int[] { -4, 3 }) {
                this.setBlockAndMetadata(world, i10, 2, k2, super.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i10 + 1, 2, k2, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i10, 4, k2, super.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i10 + 1, 4, k2, super.brickStairBlock, 5);
                for (int i11 = i10; i11 <= i10 + 1; ++i11) {
                    this.setAir(world, i11, 3, k2);
                    this.setBlockAndMetadata(world, i11, 3, k11, LOTRMod.brick, 5);
                }
            }
            if (k2 > 0) {
                this.setBlockAndMetadata(world, -1, 2, k2, super.brickStairBlock, 0);
                this.setBlockAndMetadata(world, 0, 2, k2, super.brickSlabBlock, super.brickSlabMeta);
                this.setBlockAndMetadata(world, 1, 2, k2, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, -1, 4, k2, super.brickStairBlock, 4);
                this.setBlockAndMetadata(world, 0, 4, k2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                this.setBlockAndMetadata(world, 1, 4, k2, super.brickStairBlock, 5);
                for (int i12 = -1; i12 <= 1; ++i12) {
                    this.setAir(world, i12, 3, k2);
                    this.setBlockAndMetadata(world, i12, 3, k11, LOTRMod.brick, 5);
                }
            }
        }
        for (final int k2 : new int[] { -14, 14 }) {
            final int k11 = k2 + Integer.signum(k2) * -1;
            for (final int i10 : new int[] { -10, 10 }) {
                this.setBlockAndMetadata(world, i10 - 1, 3, k2, super.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i10, 3, k2, super.brick2WallBlock, super.brick2WallMeta);
                this.setBlockAndMetadata(world, i10 + 1, 3, k2, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i10 - 1, 4, k2, super.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i10, 4, k2, super.brick2WallBlock, super.brick2WallMeta);
                this.setBlockAndMetadata(world, i10 + 1, 4, k2, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i10 - 1, 1, k11, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i10, 1, k11, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i10 + 1, 1, k11, super.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i10, 2, k11, super.brickSlabBlock, super.brickSlabMeta);
            }
        }
        for (final int i5 : new int[] { -14, 14 }) {
            final int i9 = i5 + Integer.signum(i5) * -1;
            for (final int k9 : new int[] { -10, 10 }) {
                this.setBlockAndMetadata(world, i5, 3, k9 - 1, super.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i5, 3, k9, super.brick2WallBlock, super.brick2WallMeta);
                this.setBlockAndMetadata(world, i5, 3, k9 + 1, super.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i5, 4, k9 - 1, super.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i5, 4, k9, super.brick2WallBlock, super.brick2WallMeta);
                this.setBlockAndMetadata(world, i5, 4, k9 + 1, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i9, 1, k9 - 1, super.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i9, 1, k9, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i9, 1, k9 + 1, super.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i9, 2, k9, super.brickSlabBlock, super.brickSlabMeta);
            }
        }
        for (final int i5 : new int[] { -10, 10 }) {
            for (final int i13 : new int[] { i5 - 2, i5 + 2 }) {
                this.placeArmorStand(world, i13, 1, -7, 0, null);
                this.placeArmorStand(world, i13, 1, 7, 2, null);
            }
            this.placeChest(world, random, i5, 1, -6, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
            this.setAir(world, i5, 2, -6);
            this.spawnItemFrame(world, i5, 3, -6, 2, this.getGondorFramedItem(random));
            this.placeChest(world, random, i5, 1, 6, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
            this.setAir(world, i5, 2, 6);
            this.spawnItemFrame(world, i5, 3, 6, 0, this.getGondorFramedItem(random));
        }
        for (final int k2 : new int[] { -10, 10 }) {
            for (final int k12 : new int[] { k2 - 2, k2 + 2 }) {
                this.placeArmorStand(world, -7, 1, k12, 1, null);
                this.placeArmorStand(world, 7, 1, k12, 3, null);
            }
            this.placeChest(world, random, -6, 1, k2, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
            this.setAir(world, -6, 2, k2);
            this.spawnItemFrame(world, -6, 3, k2, 3, this.getGondorFramedItem(random));
            this.placeChest(world, random, 6, 1, k2, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
            this.setAir(world, 6, 2, k2);
            this.spawnItemFrame(world, 6, 3, k2, 1, this.getGondorFramedItem(random));
        }
        for (int j7 = 1; j7 <= 4; ++j7) {
            for (int i14 = -1; i14 <= 1; ++i14) {
                this.setBlockAndMetadata(world, i14, j7, -10, super.gateBlock, 2);
                this.setAir(world, i14, j7, -9);
                this.setAir(world, i14, j7, -11);
            }
            this.setBlockAndMetadata(world, -2, j7, -9, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, 2, j7, -9, super.pillarBlock, super.pillarMeta);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -12, j8 = 0; (j8 <= 0 || !this.isOpaque(world, i3, j8, k3)) && this.getY(j8) >= 0; --j8) {
                this.setBlockAndMetadata(world, i3, j8, k3, super.brickBlock, super.brickMeta);
                this.setGrassToDirt(world, i3, j8 - 1, k3);
            }
        }
        this.placeWallBanner(world, -2, 4, -11, super.bannerType2, 2);
        this.placeWallBanner(world, 2, 4, -11, super.bannerType, 2);
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = -2; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.brick2Block, super.brick2Meta);
            }
        }
        for (int i3 = -8; i3 <= 8; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, 0, super.brick2Block, super.brick2Meta);
        }
        for (int k13 = -12; k13 <= 8; ++k13) {
            this.setBlockAndMetadata(world, 0, 0, k13, super.brick2Block, super.brick2Meta);
        }
        this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.brick4, 6);
        for (int j7 = 1; j7 <= 4; ++j7) {
            this.setBlockAndMetadata(world, -1, j7, -1, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, 1, j7, -1, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, -1, j7, 1, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, 1, j7, 1, super.brickWallBlock, super.brickWallMeta);
        }
        this.setBlockAndMetadata(world, -1, 5, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 5, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 5, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 5, 0, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 5, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 5, 0, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 5, 1, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 5, 1, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 5, 1, super.brickStairBlock, 3);
        for (int j7 = 6; j7 <= 9; ++j7) {
            this.setBlockAndMetadata(world, 0, j7, 0, super.pillarBlock, super.pillarMeta);
        }
        this.setBlockAndMetadata(world, 0, 10, 0, LOTRMod.brick, 5);
        this.setBlockAndMetadata(world, 0, 11, 0, LOTRMod.beacon, 0);
        this.placeWallBanner(world, 0, 9, 0, super.bannerType, 0);
        this.placeWallBanner(world, 0, 9, 0, super.bannerType2, 1);
        this.placeWallBanner(world, 0, 9, 0, super.bannerType, 2);
        this.placeWallBanner(world, 0, 9, 0, super.bannerType2, 3);
        this.setBlockAndMetadata(world, 0, 4, 0, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -3, 3, -8, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, -3, 4, -8, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 3, 3, -8, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 3, 4, -8, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -8, 3, -3, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, -8, 4, -3, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 8, 3, -3, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 8, 4, -3, Blocks.torch, 5);
        for (int i3 = -7; i3 <= 7; ++i3) {
            final int i15 = Math.abs(i3);
            if (i15 >= 2) {
                for (int k14 = -7; k14 <= -2; ++k14) {
                    final int k4 = k14 + 9;
                    int d = Math.abs(i15 - k4);
                    if (d == 0 && (i15 == 2 || i15 == 7)) {
                        d = 2;
                    }
                    if (d <= 2) {
                        this.setBlockAndMetadata(world, i3, 0, k14, (Block)Blocks.grass, 0);
                        if (d == 0) {
                            this.setBlockAndMetadata(world, i3, 1, k14, (Block)Blocks.double_plant, 4);
                            this.setBlockAndMetadata(world, i3, 2, k14, (Block)Blocks.double_plant, 8);
                        }
                        else if (d == 1) {
                            this.setBlockAndMetadata(world, i3, 1, k14, (Block)Blocks.red_flower, 6);
                        }
                        else if (d == 2) {
                            this.setBlockAndMetadata(world, i3, 1, k14, (Block)Blocks.red_flower, 4);
                        }
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -7, 0, 1, super.brick2Block, super.brick2Meta);
        for (int step = 0; step <= 2; ++step) {
            this.setBlockAndMetadata(world, -7, 1 + step, 2 + step, super.brickStairBlock, 2);
            for (int j9 = 1; j9 < 1 + step; ++j9) {
                this.setBlockAndMetadata(world, -7, j9, 2 + step, super.brickBlock, super.brickMeta);
            }
        }
        for (int j7 = 1; j7 <= 3; ++j7) {
            this.setBlockAndMetadata(world, -7, j7, 5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -7, j7, 6, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -8, j7, 4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -8, j7, 5, super.brickBlock, super.brickMeta);
        }
        for (int step = 0; step <= 2; ++step) {
            this.setBlockAndMetadata(world, -8, 4 + step, 3 - step, super.brickStairBlock, 3);
            for (int j9 = 1; j9 < 4 + step; ++j9) {
                this.setBlockAndMetadata(world, -8, j9, 3 - step, super.brickBlock, super.brickMeta);
            }
        }
        for (int k13 = -1; k13 <= 0; ++k13) {
            this.setBlockAndMetadata(world, -8, 5, k13, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, -8, 6, k13, super.brickBlock, super.brickMeta);
        }
        this.setAir(world, -9, 7, 0);
        this.setAir(world, -9, 7, 1);
        this.setBlockAndMetadata(world, -8, 7, -1, super.brick2WallBlock, super.brick2WallMeta);
        for (int i3 = 6; i3 <= 8; ++i3) {
            for (int k3 = -1; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.brick2Block, super.brick2Meta);
                if (i3 >= 7 && k3 >= 0 && k3 <= 2) {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.plankSlabBlock, super.plankSlabMeta);
                }
            }
        }
        for (int j7 = 1; j7 <= 3; ++j7) {
            this.setBlockAndMetadata(world, 6, j7, -1, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 6, j7, 3, super.fenceBlock, super.fenceMeta);
        }
        for (int i3 = 7; i3 <= 8; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, -1, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i3, 3, 3, super.fenceBlock, super.fenceMeta);
        }
        for (int k13 = 0; k13 <= 2; ++k13) {
            this.setBlockAndMetadata(world, 6, 3, k13, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, 8, 1, -1, LOTRMod.alloyForge, 5);
        this.setBlockAndMetadata(world, 8, 2, -1, Blocks.furnace, 5);
        this.placeArmorStand(world, 8, 1, 0, 1, super.strFief.getFiefdomArmor());
        this.setBlockAndMetadata(world, 8, 1, 1, super.tableBlock, 0);
        this.placeChest(world, random, 8, 1, 2, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        this.setBlockAndMetadata(world, 8, 1, 3, Blocks.crafting_table, 0);
        this.spawnItemFrame(world, 9, 2, 1, 3, this.getGondorFramedItem(random));
        this.spawnItemFrame(world, 9, 2, 2, 3, this.getGondorFramedItem(random));
        this.setBlockAndMetadata(world, 6, 1, 1, Blocks.anvil, 0);
        this.setBlockAndMetadata(world, 8, 3, 1, Blocks.torch, 1);
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = 4; k3 <= 8; ++k3) {
                final int i4 = Math.abs(i3);
                if (i4 != 5 || k3 != 4) {
                    if (i4 <= 4 && k3 >= 5) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.plankBlock, super.plankMeta);
                        this.setBlockAndMetadata(world, i3, 4, k3, super.brickBlock, super.brickMeta);
                    }
                    else {
                        if (i4 == 1 || i4 == 4 || k3 == 5) {
                            for (int j10 = 1; j10 <= 3; ++j10) {
                                this.setBlockAndMetadata(world, i3, j10, k3, super.woodBeamBlock, super.woodBeamMeta);
                            }
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, 1, k3, super.brickBlock, super.brickMeta);
                            for (int j10 = 2; j10 <= 3; ++j10) {
                                this.setBlockAndMetadata(world, i3, j10, k3, super.plankBlock, super.plankMeta);
                            }
                        }
                        this.setBlockAndMetadata(world, i3, 4, k3, super.brickSlabBlock, super.brickSlabMeta);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 1, 4, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, 4, super.doorBlock, 8);
        for (int i3 = -4; i3 <= 4; ++i3) {
            if (IntMath.mod(i3, 2) == 0) {
                this.setBlockAndMetadata(world, i3, 1, 7, super.bedBlock, 0);
                this.setBlockAndMetadata(world, i3, 1, 8, super.bedBlock, 8);
            }
            else {
                this.placeChest(world, random, i3, 1, 8, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
            }
        }
        this.placeWallBanner(world, -2, 3, 9, super.bannerType, 2);
        this.placeWallBanner(world, 2, 3, 9, super.bannerType, 2);
        this.setBlockAndMetadata(world, -4, 1, 5, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, 5, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, -4, 2, 5, 3, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -3, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, 3, 1, 5, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 4, 1, 5, super.plankBlock, super.plankMeta);
        this.placePlateWithCertainty(world, random, 3, 2, 5, super.plateBlock, LOTRFoods.GONDOR);
        this.placePlateWithCertainty(world, random, 4, 2, 5, super.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, -3, 3, 6, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, 3, 3, 6, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -5, 1, 2, LOTRMod.commandTable, 0);
        final LOTREntityGondorMan captain = super.strFief.createCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 12);
        final Class[] soldierClasses = super.strFief.getSoldierClasses();
        for (int soldiers = 4 + random.nextInt(4), l = 0; l < soldiers; ++l) {
            Class entityClass = soldierClasses[0];
            if (random.nextInt(3) == 0) {
                entityClass = soldierClasses[1];
            }
            final LOTREntityGondorMan soldier = (LOTREntityGondorMan)LOTREntities.createEntityByClass(entityClass, world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(soldierClasses[0], soldierClasses[1]);
        respawner.setCheckRanges(20, -8, 12, 12);
        respawner.setSpawnRanges(10, 0, 8, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
}
