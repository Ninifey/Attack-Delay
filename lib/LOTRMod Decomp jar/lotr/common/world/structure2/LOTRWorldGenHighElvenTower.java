// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityHighElfLord;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHighElfWarrior;
import lotr.common.LOTRFoods;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;

public class LOTRWorldGenHighElvenTower extends LOTRWorldGenStructureBase2
{
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block floorBlock;
    protected int floorMeta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block plateBlock;
    protected Block leafBlock;
    protected int leafMeta;
    
    public LOTRWorldGenHighElvenTower(final boolean flag) {
        super(flag);
        this.brickBlock = LOTRMod.brick3;
        this.brickMeta = 2;
        this.brickSlabBlock = LOTRMod.slabSingle5;
        this.brickSlabMeta = 5;
        this.brickStairBlock = LOTRMod.stairsHighElvenBrick;
        this.brickWallBlock = LOTRMod.wall2;
        this.brickWallMeta = 11;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 10;
        this.floorBlock = (Block)Blocks.double_stone_slab;
        this.floorMeta = 0;
        this.roofBlock = LOTRMod.clayTileDyed;
        this.roofMeta = 3;
        this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
        this.roofSlabMeta = 3;
        this.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
        this.plankBlock = Blocks.planks;
        this.plankMeta = 2;
        this.plankSlabBlock = (Block)Blocks.wooden_slab;
        this.plankSlabMeta = 2;
        this.plankStairBlock = Blocks.birch_stairs;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 2;
        this.plateBlock = LOTRMod.plateBlock;
        this.leafBlock = (Block)Blocks.leaves;
        this.leafMeta = 6;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        final int radius = 7;
        final int radiusPlusOne = radius + 1;
        final int sections = 2 + random.nextInt(3);
        final int sectionHeight = 8;
        this.setOriginAndRotation(world, i, j, k, rotation, radius + 3);
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
                        if (!this.isSurface(world, i2, j2, k2)) {
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
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                final int distSq = i3 * i3 + k3 * k3;
                if (distSq < wallThresholdMax) {
                    this.layFoundation(world, i3, k3);
                    if (distSq >= wallThresholdMin) {
                        this.setBlockAndMetadata(world, i3, 1, k3, this.pillarBlock, this.pillarMeta);
                        for (int j2 = 2; j2 <= 6; ++j2) {
                            if ((i4 == 5 && k4 == 5) || (i4 == radius && k4 == 2) || (k4 == radius && i4 == 2)) {
                                this.setBlockAndMetadata(world, i3, j2, k3, this.pillarBlock, this.pillarMeta);
                            }
                            else {
                                this.setBlockAndMetadata(world, i3, j2, k3, this.brickBlock, this.brickMeta);
                            }
                        }
                        this.setBlockAndMetadata(world, i3, 7, k3, this.pillarBlock, this.pillarMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 1, k3, this.brickBlock, this.brickMeta);
                        for (int j2 = 2; j2 <= 6; ++j2) {
                            this.setAir(world, i3, j2, k3);
                        }
                        this.setBlockAndMetadata(world, i3, 7, k3, this.brickBlock, this.brickMeta);
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 == 4 || k4 == 4) {
                    this.setBlockAndMetadata(world, i3, 1, k3, this.floorBlock, this.floorMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 1, k3, this.pillarBlock, this.pillarMeta);
                }
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int j3 = 2; j3 <= 4; ++j3) {
                this.setBlockAndMetadata(world, i3, j3, -radius, LOTRMod.gateHighElven, 2);
            }
            for (int k3 = -radius; k3 <= -4; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, this.pillarBlock, this.pillarMeta);
            }
        }
        for (int k5 = -6; k5 <= -5; ++k5) {
            this.setBlockAndMetadata(world, -2, 1, k5, this.floorBlock, this.floorMeta);
            this.setBlockAndMetadata(world, 2, 1, k5, this.floorBlock, this.floorMeta);
        }
        this.setBlockAndMetadata(world, 0, 1, -radius - 1, this.brickBlock, this.brickMeta);
        this.layFoundation(world, 0, -radius - 1);
        this.setBlockAndMetadata(world, 0, 1, -radius - 2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 1, -radius - 1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 1, -radius - 1, this.brickStairBlock, 2);
        this.layFoundation(world, 0, -radius - 2);
        this.layFoundation(world, -1, -radius - 1);
        this.layFoundation(world, 1, -radius - 1);
        this.setBlockAndMetadata(world, -2, 1, -radius - 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 1, -radius - 2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 1, -radius - 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 1, -radius - 1, this.brickStairBlock, 0);
        this.layFoundation(world, -2, -radius - 1);
        this.layFoundation(world, -1, -radius - 2);
        this.layFoundation(world, 1, -radius - 2);
        this.layFoundation(world, 2, -radius - 1);
        for (final int i5 : new int[] { -radius + 1, radius - 1 }) {
            this.setBlockAndMetadata(world, i5, 2, -2, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 2, 2, this.plankStairBlock, 6);
            for (int k6 = -1; k6 <= 1; ++k6) {
                this.setBlockAndMetadata(world, i5, 2, k6, this.plankSlabBlock, this.plankSlabMeta | 0x8);
            }
        }
        this.setBlockAndMetadata(world, -2, 2, radius - 1, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 2, radius - 1, this.plankStairBlock, 5);
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 2, radius - 1, this.plankSlabBlock, this.plankSlabMeta | 0x8);
        }
        for (final int i5 : new int[] { -radius + 2, radius - 2 }) {
            this.setBlockAndMetadata(world, i5, 2, -4, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 2, -3, this.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i5, 2, 3, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 2, 4, this.plankStairBlock, 6);
        }
        for (final int k2 : new int[] { -radius + 2, radius - 2 }) {
            this.setBlockAndMetadata(world, -4, 2, k2, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 2, k2, this.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 2, k2, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, 4, 2, k2, this.plankStairBlock, 5);
        }
        for (int i3 = -radius; i3 <= radius; ++i3) {
            for (int k3 = -radius; k3 <= radius; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (((i4 == radius - 1 && k4 <= 2) || (k3 == radius - 1 && i4 <= 2) || (i4 == radius - 2 && k4 >= 3 && k4 <= 4) || (k4 == radius - 2 && i4 >= 3 && i4 <= 4)) && random.nextInt(3) == 0) {
                    if (random.nextInt(3) == 0) {
                        this.placeMug(world, random, i3, 3, k3, random.nextInt(4), LOTRFoods.ELF_DRINK);
                    }
                    else {
                        this.placePlate(world, random, i3, 3, k3, this.plateBlock, LOTRFoods.ELF);
                    }
                }
                if (k3 == -radius + 1 && i4 == 2) {
                    for (int j4 = 2; j4 <= 4; ++j4) {
                        this.setBlockAndMetadata(world, i3, j4, k3, this.brickWallBlock, this.brickWallMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 5, k3, LOTRMod.highElvenTorch, 5);
                }
                if ((i4 == radius && k4 == 0) || (k3 == radius && i4 == 0)) {
                    this.setBlockAndMetadata(world, i3, 3, k3, LOTRMod.highElfWoodBars, 0);
                    this.setBlockAndMetadata(world, i3, 4, k3, LOTRMod.highElfWoodBars, 0);
                }
                if ((i4 == radius - 1 && k4 == 1) || (k3 == radius - 1 && i4 == 1)) {
                    this.setBlockAndMetadata(world, i3, 4, k3, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i3, 5, k3, LOTRMod.highElvenTorch, 5);
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -radius + 1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 6, radius - 1, this.brickStairBlock, 6);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -radius + 1, 6, k5, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, radius - 1, 6, k5, this.brickStairBlock, 5);
        }
        for (int i3 = -4; i3 <= -3; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -radius + 2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 6, radius - 2, this.brickStairBlock, 6);
        }
        for (int i3 = 3; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -radius + 2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 6, radius - 2, this.brickStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -radius + 2, 6, -4, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 2, 6, -3, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, radius - 2, 6, -4, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 2, 6, -3, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -radius + 2, 6, 3, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, 6, 4, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 2, 6, 3, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, 6, 4, this.brickStairBlock, 6);
        for (final int k2 : new int[] { -radius + 2, radius - 2 }) {
            this.setBlockAndMetadata(world, -2, 6, k2, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k2, this.brickStairBlock, 5);
        }
        for (final int i5 : new int[] { -radius + 2, radius - 2 }) {
            this.setBlockAndMetadata(world, i5, 6, -2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 6, 2, this.brickStairBlock, 6);
        }
        for (final int k2 : new int[] { -4, 4 }) {
            this.setBlockAndMetadata(world, -4, 6, k2, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 4, 6, k2, this.brickStairBlock, 5);
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, -radius, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 8, radius, this.roofStairBlock, 3);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -radius, 8, k5, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, radius, 8, k5, this.roofStairBlock, 0);
        }
        for (int i3 = -4; i3 <= -3; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, -radius + 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 8, radius - 1, this.roofStairBlock, 3);
        }
        for (int i3 = 3; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, -radius + 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 8, radius - 1, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -radius + 1, 8, -3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 1, 8, 3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 1, 8, -3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 1, 8, 3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 1, 8, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, 8, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, 8, -5, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, 8, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, 8, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, 8, -5, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, 8, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, 8, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, 8, 5, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, 8, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, 8, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, 8, 5, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, 8, -4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 8, -radius + 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, 8, -radius + 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 2, 8, -4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 8, -radius + 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 8, -radius + 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 2, 8, 4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 8, radius - 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, 8, radius - 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 2, 8, 4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 8, radius - 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 8, radius - 1, this.roofStairBlock, 0);
        final int sRadius = radius - 1;
        final int sRadiusPlusOne = sRadius + 1;
        final double sRadiusD = sRadius - 0.7;
        final double sRadiusDPlusOne = sRadiusD + 1.0;
        final int sWallThresholdMin = (int)(sRadiusD * sRadiusD);
        final int sWallThresholdMax = (int)(sRadiusDPlusOne * sRadiusDPlusOne);
        for (int l = 0; l < sections; ++l) {
            final int sectionBase = 7 + l * sectionHeight;
            for (int i6 = -sRadius; i6 <= sRadius; ++i6) {
                for (int k7 = -sRadius; k7 <= sRadius; ++k7) {
                    final int i7 = Math.abs(i6);
                    final int k8 = Math.abs(k7);
                    final int distSq2 = i6 * i6 + k7 * k7;
                    if (distSq2 < sWallThresholdMax) {
                        for (int j5 = sectionBase + 1; j5 <= sectionBase + sectionHeight; ++j5) {
                            if (distSq2 >= sWallThresholdMin) {
                                if ((i7 == 4 && k8 == 4) || (i7 == sRadius && k8 == 1) || (k8 == sRadius && i7 == 1)) {
                                    this.setBlockAndMetadata(world, i6, j5, k7, this.pillarBlock, this.pillarMeta);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i6, j5, k7, this.brickBlock, this.brickMeta);
                                }
                            }
                            else if (j5 == sectionBase + sectionHeight) {
                                this.setBlockAndMetadata(world, i6, j5, k7, this.brickBlock, this.brickMeta);
                            }
                            else {
                                this.setAir(world, i6, j5, k7);
                            }
                        }
                    }
                    if ((i7 == 0 && k8 == sRadius) || (k8 == 0 && i7 == sRadius)) {
                        this.setBlockAndMetadata(world, i6, sectionBase + 1, k7, this.pillarBlock, this.pillarMeta);
                        this.setBlockAndMetadata(world, i6, sectionBase + 3, k7, LOTRMod.highElfWoodBars, 0);
                        this.setBlockAndMetadata(world, i6, sectionBase + 4, k7, LOTRMod.highElfWoodBars, 0);
                        this.setBlockAndMetadata(world, i6, sectionBase + 6, k7, this.pillarBlock, this.pillarMeta);
                    }
                    if ((i7 == 1 && k8 == sRadius - 1) || (k8 == 1 && i7 == sRadius - 1)) {
                        this.setBlockAndMetadata(world, i6, sectionBase + 4, k7, this.fenceBlock, this.fenceMeta);
                        this.setBlockAndMetadata(world, i6, sectionBase + 5, k7, LOTRMod.highElvenTorch, 5);
                    }
                    if ((i7 == 3 && k8 == 4) || (k8 == 3 && i7 == 4)) {
                        this.setBlockAndMetadata(world, i6, sectionBase + 1, k7, this.plankBlock, this.plankMeta);
                        if (random.nextInt(4) == 0) {
                            if (random.nextBoolean()) {
                                this.placeMug(world, random, i6, sectionBase + 2, k7, random.nextInt(4), LOTRFoods.ELF_DRINK);
                            }
                            else {
                                this.placePlate(world, random, i6, sectionBase + 2, k7, this.plateBlock, LOTRFoods.ELF);
                            }
                        }
                        this.setBlockAndMetadata(world, i6, sectionBase + 6, k7, this.fenceBlock, this.fenceMeta);
                        this.setBlockAndMetadata(world, i6, sectionBase + 7, k7, this.leafBlock, this.leafMeta);
                    }
                }
            }
            for (int i6 = -1; i6 <= 1; ++i6) {
                this.setBlockAndMetadata(world, i6, sectionBase + 1, -sRadius + 1, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i6, sectionBase + 1, sRadius - 1, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i6, sectionBase + 7, -sRadius + 1, this.plankStairBlock, 7);
                this.setBlockAndMetadata(world, i6, sectionBase + 7, sRadius - 1, this.plankStairBlock, 6);
            }
            for (int k9 = -1; k9 <= 1; ++k9) {
                this.setBlockAndMetadata(world, -sRadius + 1, sectionBase + 1, k9, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, sRadius - 1, sectionBase + 1, k9, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, -sRadius + 1, sectionBase + 7, k9, this.plankStairBlock, 4);
                this.setBlockAndMetadata(world, sRadius - 1, sectionBase + 7, k9, this.plankStairBlock, 5);
            }
            this.setBlockAndMetadata(world, -sRadius, sectionBase + 2, 0, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, sRadius, sectionBase + 2, 0, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 0, sectionBase + 2, -sRadius, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, 0, sectionBase + 2, sRadius, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, -sRadius, sectionBase + 5, 0, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, sRadius, sectionBase + 5, 0, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 0, sectionBase + 5, -sRadius, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, 0, sectionBase + 5, sRadius, this.brickStairBlock, 6);
            final LOTREntityElf warrior = new LOTREntityHighElfWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 3, sectionBase + 1, 0, 16);
        }
        final int sectionTopHeight = 7 + sections * sectionHeight;
        for (int j6 = 2; j6 <= sectionTopHeight; ++j6) {
            this.setBlockAndMetadata(world, 0, j6, 0, this.pillarBlock, this.pillarMeta);
            final int step = (j6 + 2) % 4;
            if (step == 0) {
                for (int i8 = -2; i8 <= -1; ++i8) {
                    this.setBlockAndMetadata(world, i8, j6, 0, this.brickSlabBlock, this.brickSlabMeta);
                    this.setBlockAndMetadata(world, i8, j6, 1, this.brickSlabBlock, this.brickSlabMeta | 0x8);
                    this.setBlockAndMetadata(world, i8, j6, 2, this.brickSlabBlock, this.brickSlabMeta | 0x8);
                    for (int j7 = j6 + 1; j7 <= j6 + 3; ++j7) {
                        this.setAir(world, i8, j7, 0);
                        this.setAir(world, i8, j7, 1);
                        this.setAir(world, i8, j7, 2);
                    }
                }
                this.setBlockAndMetadata(world, 0, j6, 1, LOTRMod.highElvenTorch, 3);
            }
            else if (step == 1) {
                for (int k7 = 1; k7 <= 2; ++k7) {
                    this.setBlockAndMetadata(world, 0, j6, k7, this.brickSlabBlock, this.brickSlabMeta);
                    this.setBlockAndMetadata(world, 1, j6, k7, this.brickSlabBlock, this.brickSlabMeta | 0x8);
                    this.setBlockAndMetadata(world, 2, j6, k7, this.brickSlabBlock, this.brickSlabMeta | 0x8);
                    for (int j7 = j6 + 1; j7 <= j6 + 3; ++j7) {
                        this.setAir(world, 0, j7, k7);
                        this.setAir(world, 1, j7, k7);
                        this.setAir(world, 2, j7, k7);
                    }
                }
            }
            else if (step == 2) {
                for (int i8 = 1; i8 <= 2; ++i8) {
                    this.setBlockAndMetadata(world, i8, j6, 0, this.brickSlabBlock, this.brickSlabMeta);
                    this.setBlockAndMetadata(world, i8, j6, -1, this.brickSlabBlock, this.brickSlabMeta | 0x8);
                    this.setBlockAndMetadata(world, i8, j6, -2, this.brickSlabBlock, this.brickSlabMeta | 0x8);
                    for (int j7 = j6 + 1; j7 <= j6 + 3; ++j7) {
                        this.setAir(world, i8, j7, 0);
                        this.setAir(world, i8, j7, -1);
                        this.setAir(world, i8, j7, -2);
                    }
                }
                this.setBlockAndMetadata(world, 0, j6, -1, LOTRMod.highElvenTorch, 4);
            }
            else if (step == 3) {
                for (int k7 = -2; k7 <= -1; ++k7) {
                    this.setBlockAndMetadata(world, 0, j6, k7, this.brickSlabBlock, this.brickSlabMeta);
                    this.setBlockAndMetadata(world, -1, j6, k7, this.brickSlabBlock, this.brickSlabMeta | 0x8);
                    this.setBlockAndMetadata(world, -2, j6, k7, this.brickSlabBlock, this.brickSlabMeta | 0x8);
                    for (int j7 = j6 + 1; j7 <= j6 + 3; ++j7) {
                        this.setAir(world, 0, j7, k7);
                        this.setAir(world, -1, j7, k7);
                        this.setAir(world, -2, j7, k7);
                    }
                }
            }
        }
        for (int i9 = -radius; i9 <= radius; ++i9) {
            for (int k9 = -radius; k9 <= radius; ++k9) {
                final int i10 = Math.abs(i9);
                final int k10 = Math.abs(k9);
                final int distSq3 = i9 * i9 + k9 * k9;
                if (distSq3 < wallThresholdMax) {
                    if (distSq3 >= wallThresholdMin) {
                        this.setBlockAndMetadata(world, i9, sectionTopHeight + 1, k9, this.pillarBlock, this.pillarMeta);
                        for (int j8 = sectionTopHeight + 2; j8 <= sectionTopHeight + 5; ++j8) {
                            if ((i10 == 5 && k10 == 5) || (i10 == radius && k10 == 2) || (k10 == radius && i10 == 2)) {
                                this.setBlockAndMetadata(world, i9, j8, k9, this.pillarBlock, this.pillarMeta);
                            }
                            else {
                                this.setBlockAndMetadata(world, i9, j8, k9, this.brickBlock, this.brickMeta);
                            }
                        }
                        this.setBlockAndMetadata(world, i9, sectionTopHeight + 6, k9, this.pillarBlock, this.pillarMeta);
                        this.setBlockAndMetadata(world, i9, sectionTopHeight + 7, k9, this.roofBlock, this.roofMeta);
                        this.setBlockAndMetadata(world, i9, sectionTopHeight + 8, k9, this.roofBlock, this.roofMeta);
                    }
                    else {
                        for (int j8 = sectionTopHeight + 1; j8 <= sectionTopHeight + 6; ++j8) {
                            this.setAir(world, i9, j8, k9);
                        }
                    }
                    if ((i10 == 2 && k10 == radius - 1) || (k10 == 2 && i10 == radius - 1)) {
                        this.setBlockAndMetadata(world, i9, sectionTopHeight + 4, k9, this.fenceBlock, this.fenceMeta);
                        this.setBlockAndMetadata(world, i9, sectionTopHeight + 5, k9, LOTRMod.highElvenTorch, 5);
                    }
                    if ((i10 <= 1 && k10 == radius - 1) || (k10 <= 1 && i10 == radius - 1) || (i10 >= 3 && i10 <= 4 && k10 == radius - 2) || (k10 >= 3 && k10 <= 4 && i10 == radius - 2)) {
                        this.setBlockAndMetadata(world, i9, sectionTopHeight + 6, k9, this.fenceBlock, this.fenceMeta);
                        this.setBlockAndMetadata(world, i9, sectionTopHeight + 7, k9, this.leafBlock, this.leafMeta);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, 0, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 2, 0, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 3, 0, this.roofSlabBlock, this.roofSlabMeta);
        for (int i9 = -2; i9 <= 2; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight, -radius, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i9, sectionTopHeight, radius, this.roofStairBlock, 7);
        }
        for (int k11 = -2; k11 <= 2; ++k11) {
            this.setBlockAndMetadata(world, -radius, sectionTopHeight, k11, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, radius, sectionTopHeight, k11, this.roofStairBlock, 4);
        }
        for (int i9 = -4; i9 <= -3; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight, -radius + 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i9, sectionTopHeight, radius - 1, this.roofStairBlock, 7);
        }
        for (int i9 = 3; i9 <= 4; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight, -radius + 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i9, sectionTopHeight, radius - 1, this.roofStairBlock, 7);
        }
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, -5, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, -5, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, 5, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, 5, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, -4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -4, sectionTopHeight, -radius + 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -2, sectionTopHeight, -radius + 1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, -4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 4, sectionTopHeight, -radius + 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 2, sectionTopHeight, -radius + 1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, 4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -4, sectionTopHeight, radius - 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -2, sectionTopHeight, radius - 1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, 4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 4, sectionTopHeight, radius - 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 2, sectionTopHeight, radius - 1, this.roofStairBlock, 4);
        for (int i9 = -2; i9 <= 2; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 1, -radius + 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 1, radius - 1, this.brickStairBlock, 2);
        }
        for (int k11 = -2; k11 <= 2; ++k11) {
            this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 1, k11, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 1, k11, this.brickStairBlock, 1);
        }
        for (int i9 = -4; i9 <= -3; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 1, -radius + 2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 1, radius - 2, this.brickStairBlock, 2);
        }
        for (int i9 = 3; i9 <= 4; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 1, -radius + 2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 1, radius - 2, this.brickStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, -4, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, -3, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, -4, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, -3, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, 3, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, 4, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, 3, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, 4, this.brickStairBlock, 2);
        for (final int k12 : new int[] { -radius + 2, radius - 2 }) {
            this.setBlockAndMetadata(world, -2, sectionTopHeight + 1, k12, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 2, sectionTopHeight + 1, k12, this.brickStairBlock, 1);
        }
        for (final int i11 : new int[] { -radius + 2, radius - 2 }) {
            this.setBlockAndMetadata(world, i11, sectionTopHeight + 1, -2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i11, sectionTopHeight + 1, 2, this.brickStairBlock, 2);
        }
        for (final int k12 : new int[] { -4, 4 }) {
            this.setBlockAndMetadata(world, -4, sectionTopHeight + 1, k12, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 4, sectionTopHeight + 1, k12, this.brickStairBlock, 1);
        }
        for (int i9 = -1; i9 <= 1; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 2, -radius, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 3, -radius, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 4, -radius, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 5, -radius, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 2, radius, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 3, radius, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 4, radius, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 5, radius, this.brickStairBlock, 6);
        }
        for (int k11 = -1; k11 <= 1; ++k11) {
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 2, k11, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 3, k11, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 4, k11, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 5, k11, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 2, k11, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 3, k11, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 4, k11, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 5, k11, this.brickStairBlock, 5);
        }
        this.placeWallBanner(world, -radius + 1, sectionTopHeight + 4, -4, LOTRItemBanner.BannerType.HIGH_ELF, 1);
        this.placeWallBanner(world, -radius + 1, sectionTopHeight + 4, 4, LOTRItemBanner.BannerType.HIGH_ELF, 1);
        this.placeWallBanner(world, radius - 1, sectionTopHeight + 4, -4, LOTRItemBanner.BannerType.HIGH_ELF, 3);
        this.placeWallBanner(world, radius - 1, sectionTopHeight + 4, 4, LOTRItemBanner.BannerType.HIGH_ELF, 3);
        for (int i9 = -3; i9 <= 3; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 7, -radius - 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 7, radius + 1, this.roofStairBlock, 3);
        }
        for (int k11 = -3; k11 <= 3; ++k11) {
            this.setBlockAndMetadata(world, -radius - 1, sectionTopHeight + 7, k11, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, radius + 1, sectionTopHeight + 7, k11, this.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, -radius, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 7, -radius, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 7, -radius, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, -radius, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 7, -radius, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 7, -radius, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, 4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, radius, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 7, radius, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 7, radius, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, 4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, radius, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 7, radius, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 7, radius, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -4, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, -2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 4, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, radius - 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, radius - 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, radius - 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, radius - 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 4, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, -2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -4, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -radius + 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, -radius + 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, -radius + 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -radius + 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, -4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, -1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 0, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, 4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, 4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, 4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, 4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 0, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, -1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, -4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 4);
        for (int i9 = -2; i9 <= 2; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 9, -radius, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 9, radius, this.roofStairBlock, 3);
        }
        for (int k11 = -2; k11 <= 2; ++k11) {
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 9, k11, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 9, k11, this.roofStairBlock, 0);
        }
        for (int i9 = -4; i9 <= -3; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 3);
        }
        for (int i9 = 3; i9 <= 4; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, -4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, -4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, 4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, 4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 4, this.roofStairBlock, 3);
        for (int j6 = sectionTopHeight + 9; j6 <= sectionTopHeight + 10; ++j6) {
            for (int i6 = -1; i6 <= 1; ++i6) {
                this.setBlockAndMetadata(world, i6, j6, -radius + 1, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i6, j6, radius - 1, this.roofBlock, this.roofMeta);
            }
            for (int k9 = -1; k9 <= 1; ++k9) {
                this.setBlockAndMetadata(world, -radius + 1, j6, k9, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, radius - 1, j6, k9, this.roofBlock, this.roofMeta);
            }
            for (int i6 = -3; i6 <= 3; ++i6) {
                this.setBlockAndMetadata(world, i6, j6, -radius + 2, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i6, j6, radius - 2, this.roofBlock, this.roofMeta);
            }
            for (int k9 = -3; k9 <= 3; ++k9) {
                this.setBlockAndMetadata(world, -radius + 2, j6, k9, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, radius - 2, j6, k9, this.roofBlock, this.roofMeta);
            }
            this.setBlockAndMetadata(world, -4, j6, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -4, j6, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j6, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, j6, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, j6, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j6, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -4, j6, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -4, j6, 4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j6, 4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, j6, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, j6, 4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j6, 4, this.roofBlock, this.roofMeta);
        }
        for (int i9 = -2; i9 <= 2; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 10, -4, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 10, 4, this.roofStairBlock, 6);
        }
        for (int k11 = -1; k11 <= 1; ++k11) {
            this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, k11, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, k11, this.roofStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 10, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, -2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, -2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 10, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, -2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, -2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 10, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 10, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, -4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, 4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, 4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, -4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 1);
        for (int j6 = sectionTopHeight + 11; j6 <= sectionTopHeight + 12; ++j6) {
            for (int i6 = -2; i6 <= 2; ++i6) {
                this.setBlockAndMetadata(world, i6, j6, -4, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i6, j6, 4, this.roofBlock, this.roofMeta);
            }
            for (int k9 = -2; k9 <= 2; ++k9) {
                this.setBlockAndMetadata(world, -4, j6, k9, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, 4, j6, k9, this.roofBlock, this.roofMeta);
            }
            this.setBlockAndMetadata(world, -3, j6, -2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j6, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -2, j6, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j6, -2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j6, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 2, j6, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j6, 2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j6, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -2, j6, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j6, 2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j6, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 2, j6, 3, this.roofBlock, this.roofMeta);
        }
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, -radius + 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, -radius + 2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, radius - 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, radius - 2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 12, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 12, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, -2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, -2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, -1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, 0, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, 1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, 1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, 0, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, -1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, -2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, -2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, -3, this.roofStairBlock, 7);
        for (int i9 = -2; i9 <= 2; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 13, -4, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 13, 4, this.roofStairBlock, 3);
        }
        for (int k11 = -1; k11 <= 1; ++k11) {
            this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, k11, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, k11, this.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 13, -3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, -2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 13, -3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, -2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 13, 3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 13, 3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, 2, this.roofStairBlock, 3);
        for (int j6 = sectionTopHeight + 13; j6 <= sectionTopHeight + 14; ++j6) {
            for (int i6 = -1; i6 <= 1; ++i6) {
                this.setBlockAndMetadata(world, i6, j6, -3, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i6, j6, 3, this.roofBlock, this.roofMeta);
            }
            for (int i6 = -2; i6 <= 2; ++i6) {
                this.setBlockAndMetadata(world, i6, j6, -2, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i6, j6, 2, this.roofBlock, this.roofMeta);
            }
            for (int k9 = -1; k9 <= 1; ++k9) {
                this.setBlockAndMetadata(world, -3, j6, k9, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, -2, j6, k9, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, 2, j6, k9, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, 3, j6, k9, this.roofBlock, this.roofMeta);
            }
        }
        for (int i9 = -1; i9 <= 1; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 14, -1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 14, 1, this.roofStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 14, 0, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 14, 0, this.roofStairBlock, 5);
        for (int i9 = -1; i9 <= 1; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 15, -3, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 15, 3, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 15, -2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, -1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 15, 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 15, -2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, -1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 15, 2, this.roofStairBlock, 0);
        for (int j6 = sectionTopHeight + 15; j6 <= sectionTopHeight + 16; ++j6) {
            for (int i6 = -1; i6 <= 1; ++i6) {
                for (int k7 = -1; k7 <= 1; ++k7) {
                    this.setBlockAndMetadata(world, i6, j6, k7, this.roofBlock, this.roofMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 15, -2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 16, -2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 15, 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 16, 2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 16, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 16, 0, this.roofSlabBlock, this.roofSlabMeta);
        for (int i9 = -1; i9 <= 1; ++i9) {
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 17, -1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i9, sectionTopHeight + 17, 1, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 17, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 17, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 17, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 18, 0, this.roofSlabBlock, this.roofSlabMeta);
        for (int j6 = sectionTopHeight + 10; j6 <= sectionTopHeight + 14; ++j6) {
            this.setBlockAndMetadata(world, 0, j6, 0, Blocks.fence, 2);
        }
        for (int i9 = -2; i9 <= 2; ++i9) {
            for (int k9 = -2; k9 <= 2; ++k9) {
                if (i9 == 0 || k9 == 0) {
                    this.setBlockAndMetadata(world, i9, sectionTopHeight + 10, k9, Blocks.fence, 2);
                }
                if ((i9 == 0 && Math.abs(k9) == 2) || (k9 == 0 && Math.abs(i9) == 2)) {
                    this.setBlockAndMetadata(world, i9, sectionTopHeight + 9, k9, LOTRMod.chandelier, 10);
                }
            }
        }
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 1, 6, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, 6, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 1, 6, LOTRMod.highElvenTable, 0);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, -4, LOTRMod.commandTable, 0);
        final LOTREntityHighElfLord elfLord = new LOTREntityHighElfLord(world);
        elfLord.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(elfLord, world, 0, sectionTopHeight + 1, 1, 16);
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityHighElfWarrior.class);
        respawner.setCheckRanges(12, -16, 50, 12);
        respawner.setSpawnRanges(6, 0, 20, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    private void layFoundation(final World world, final int i, final int k) {
        for (int j = 0; (j == 0 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
            this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
}
