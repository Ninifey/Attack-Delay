// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityDolAmrothCaptain;
import com.google.common.math.IntMath;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityHorse;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;

public class LOTRWorldGenDolAmrothStables extends LOTRWorldGenStructureBase2
{
    private Block brickBlock;
    private int brickMeta;
    private Block slabBlock;
    private int slabMeta;
    private Block stairBlock;
    private Block wallBlock;
    private int wallMeta;
    private Block rockSlabBlock;
    private Block doubleRockSlabBlock;
    private int rockSlabMeta;
    private Block pillarBlock;
    private int pillarMeta;
    private Block logBlock;
    private int logMeta;
    private Block plankBlock;
    private int plankMeta;
    private Block plankSlabBlock;
    private int plankSlabMeta;
    private Block fenceBlock;
    private int fenceMeta;
    private Block gateBlock;
    private Block woodBeamBlock;
    private int woodBeamMeta;
    private Block roofBlock;
    private int roofMeta;
    private Block roofSlabBlock;
    private int roofSlabMeta;
    private Block roofStairBlock;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenDolAmrothStables(final boolean flag) {
        super(flag);
        this.brickBlock = LOTRMod.brick3;
        this.brickMeta = 9;
        this.slabBlock = LOTRMod.slabSingle6;
        this.slabMeta = 7;
        this.stairBlock = LOTRMod.stairsDolAmrothBrick;
        this.wallBlock = LOTRMod.wall2;
        this.wallMeta = 14;
        this.rockSlabBlock = LOTRMod.slabSingle;
        this.doubleRockSlabBlock = LOTRMod.slabDouble;
        this.rockSlabMeta = 2;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 6;
        this.logBlock = Blocks.log;
        this.logMeta = 0;
        this.plankBlock = Blocks.planks;
        this.plankMeta = 0;
        this.plankSlabBlock = (Block)Blocks.wooden_slab;
        this.plankSlabMeta = 0;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 0;
        this.woodBeamBlock = LOTRMod.woodBeamV1;
        this.woodBeamMeta = 0;
        this.gateBlock = Blocks.fence_gate;
        this.roofBlock = LOTRMod.clayTileDyed;
        this.roofMeta = 11;
        this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
        this.roofSlabMeta = 3;
        this.roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
        this.leafBlock = LOTRMod.leaves4;
        this.leafMeta = 6;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -9; i2 <= 9; ++i2) {
                for (int k2 = -1; k2 <= 19; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
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
        for (int segment = 0; segment <= 2; ++segment) {
            final int kz = segment * 4;
            for (int i2 = -8; i2 <= 8; ++i2) {
                for (int k2 = kz; k2 <= kz + 3; ++k2) {
                    for (int j2 = 0; (j2 == 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                    for (int j2 = 1; j2 <= 7; ++j2) {
                        this.setAir(world, i2, j2, k2);
                    }
                }
            }
            this.placeWoodPillar(world, -8, kz);
            this.placeWoodPillar(world, 8, kz);
            for (final int i3 : new int[] { -3, 3 }) {
                if (segment == 0) {
                    this.placeWoodPillar(world, i3, kz);
                }
                else {
                    for (int j3 = 1; j3 <= 3; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, kz, this.logBlock, this.logMeta);
                    }
                }
            }
            for (int i2 = -7; i2 <= -4; ++i2) {
                this.setBlockAndMetadata(world, i2, 1, kz, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i2, 2, kz, this.wallBlock, this.wallMeta);
                this.setBlockAndMetadata(world, i2, 4, kz, this.brickBlock, this.brickMeta);
            }
            for (int i2 = 4; i2 <= 7; ++i2) {
                this.setBlockAndMetadata(world, i2, 1, kz, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i2, 2, kz, this.wallBlock, this.wallMeta);
                this.setBlockAndMetadata(world, i2, 4, kz, this.brickBlock, this.brickMeta);
            }
            this.setBlockAndMetadata(world, -4, 2, kz, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -4, 3, kz, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, 4, 2, kz, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 4, 3, kz, this.wallBlock, this.wallMeta);
            for (int k3 = kz + 1; k3 <= kz + 3; ++k3) {
                this.setBlockAndMetadata(world, -8, 1, k3, this.doubleRockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, -8, 2, k3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, -8, 3, k3, this.rockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, -8, 4, k3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, 8, 1, k3, this.doubleRockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, 8, 2, k3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, 8, 3, k3, this.rockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, 8, 4, k3, this.brickBlock, this.brickMeta);
            }
            for (int j4 = 1; j4 <= 3; ++j4) {
                this.setBlockAndMetadata(world, -2, j4, kz, this.fenceBlock, this.brickMeta);
                this.setBlockAndMetadata(world, 2, j4, kz, this.fenceBlock, this.brickMeta);
            }
            for (int k3 = kz + 1; k3 <= kz + 3; ++k3) {
                for (int i4 = -7; i4 <= -3; ++i4) {
                    this.setBlockAndMetadata(world, i4, 0, k3, this.doubleRockSlabBlock, this.rockSlabMeta);
                    if (random.nextInt(3) != 0) {
                        this.setBlockAndMetadata(world, i4, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                }
                for (int i4 = 3; i4 <= 7; ++i4) {
                    this.setBlockAndMetadata(world, i4, 0, k3, this.doubleRockSlabBlock, this.rockSlabMeta);
                    if (random.nextInt(3) != 0) {
                        this.setBlockAndMetadata(world, i4, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                }
            }
            for (final int i3 : new int[] { -7, 7 }) {
                this.setBlockAndMetadata(world, i3, 1, kz + 1, Blocks.hay_block, 0);
                this.setBlockAndMetadata(world, i3, 1, kz + 2, Blocks.hay_block, 0);
                this.setBlockAndMetadata(world, i3, 1, kz + 3, this.fenceBlock, this.fenceMeta);
            }
            for (final int i3 : new int[] { -3, 3 }) {
                this.setBlockAndMetadata(world, i3, 1, kz + 1, this.gateBlock, 1);
                this.setBlockAndMetadata(world, i3, 1, kz + 2, this.gateBlock, 1);
                this.setBlockAndMetadata(world, i3, 1, kz + 3, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, i3, 2, kz + 3, Blocks.torch, 5);
            }
            for (final int f : new int[] { -1, 1 }) {
                final LOTREntityHorse horse = new LOTREntityHorse(world);
                this.spawnNPCAndSetHome((EntityCreature)horse, world, 5 * f, 1, kz + 2, 0);
                horse.setHorseType(0);
                horse.saddleMountForWorldGen();
                horse.detachHome();
            }
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = kz + 1; k2 <= kz + 3; ++k2) {
                    this.setBlockAndMetadata(world, i2, 4, k2, this.rockSlabBlock, this.rockSlabMeta | 0x8);
                }
                if (segment > 0) {
                    if (Math.abs(i2) <= 1) {
                        this.setBlockAndMetadata(world, i2, 4, kz, this.rockSlabBlock, this.rockSlabMeta | 0x8);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 4, kz, this.brickBlock, this.brickMeta);
                    }
                }
            }
            for (final int i3 : new int[] { -3, 3 }) {
                this.setBlockAndMetadata(world, i3, 4, kz + 1, this.doubleRockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, i3, 4, kz + 3, this.doubleRockSlabBlock, this.rockSlabMeta);
            }
            for (int i2 = -8; i2 <= 8; ++i2) {
                this.setBlockAndMetadata(world, i2, 5, kz, this.brickBlock, this.brickMeta);
            }
            for (final int i3 : new int[] { -7, 4 }) {
                this.setBlockAndMetadata(world, i3, 6, kz, this.stairBlock, 1);
                this.setBlockAndMetadata(world, i3 + 1, 6, kz, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i3 + 1, 7, kz, this.stairBlock, 1);
                this.setBlockAndMetadata(world, i3 + 2, 6, kz, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i3 + 2, 7, kz, this.stairBlock, 0);
                this.setBlockAndMetadata(world, i3 + 3, 6, kz, this.stairBlock, 0);
                for (int k4 = kz + 1; k4 <= kz + 3; ++k4) {
                    this.setBlockAndMetadata(world, i3, 5, k4, this.roofStairBlock, 1);
                    this.setBlockAndMetadata(world, i3 + 1, 5, k4, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i3 + 1, 6, k4, this.roofStairBlock, 1);
                    this.setBlockAndMetadata(world, i3 + 2, 5, k4, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i3 + 2, 6, k4, this.roofStairBlock, 0);
                    this.setBlockAndMetadata(world, i3 + 3, 5, k4, this.roofStairBlock, 0);
                }
            }
            this.setBlockAndMetadata(world, -2, 6, kz, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -1, 6, kz, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -1, 7, kz, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 0, 6, kz, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 0, 7, kz, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 0, 8, kz, this.slabBlock, this.slabMeta);
            this.setBlockAndMetadata(world, 1, 6, kz, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 1, 7, kz, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 2, 6, kz, this.stairBlock, 0);
            for (int k3 = kz + 1; k3 <= kz + 3; ++k3) {
                this.setBlockAndMetadata(world, -2, 5, k3, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, -1, 5, k3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, -1, 6, k3, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 0, 5, k3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, 0, 6, k3, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, 0, 7, k3, this.roofSlabBlock, this.roofSlabMeta);
                this.setBlockAndMetadata(world, 1, 5, k3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, 1, 6, k3, this.roofStairBlock, 0);
                this.setBlockAndMetadata(world, 2, 5, k3, this.roofStairBlock, 0);
            }
            for (final int i3 : new int[] { -8, -3, 3, 8 }) {
                for (int k4 = kz + 1; k4 <= kz + 3; ++k4) {
                    this.setBlockAndMetadata(world, i3, 5, k4, this.wallBlock, this.wallMeta);
                }
            }
        }
        for (int i5 = -9; i5 <= 9; ++i5) {
            final int i6 = Math.abs(i5);
            if (i6 <= 2) {
                this.setBlockAndMetadata(world, i5, 0, -1, this.doubleRockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, i5, 0, -2, this.doubleRockSlabBlock, this.rockSlabMeta);
            }
            else {
                this.placeGrassFoundation(world, i5, -1);
                if (i6 == 3 || i6 == 8) {
                    for (int j4 = 1; j4 <= 4; ++j4) {
                        this.setBlockAndMetadata(world, i5, j4, -1, this.wallBlock, this.wallMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i5, 1, -1, this.leafBlock, this.leafMeta);
                }
            }
        }
        for (int k5 = 0; k5 <= 11; ++k5) {
            for (final int i7 : new int[] { -9, 9 }) {
                this.placeGrassFoundation(world, i7, k5);
                if (k5 % 4 == 0) {
                    for (int j5 = 1; j5 <= 4; ++j5) {
                        this.setBlockAndMetadata(world, i7, j5, k5, this.wallBlock, this.wallMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i7, 1, k5, this.leafBlock, this.leafMeta);
                }
            }
            if (k5 % 4 == 0) {
                this.setBlockAndMetadata(world, -9, 5, k5, this.stairBlock, 1);
                this.setBlockAndMetadata(world, 9, 5, k5, this.stairBlock, 0);
            }
        }
        this.setBlockAndMetadata(world, -1, 5, 0, this.stairBlock, 4);
        this.setBlockAndMetadata(world, 0, 5, 0, this.stairBlock, 6);
        this.setBlockAndMetadata(world, 1, 5, 0, this.stairBlock, 5);
        this.setBlockAndMetadata(world, 0, 6, 0, LOTRMod.brick, 5);
        for (int i5 = -1; i5 <= 1; ++i5) {
            this.setBlockAndMetadata(world, i5, 0, 0, this.doubleRockSlabBlock, this.rockSlabMeta);
            for (int j6 = 1; j6 <= 4; ++j6) {
                this.setBlockAndMetadata(world, i5, j6, 0, LOTRMod.gateDolAmroth, 2);
            }
        }
        for (final int i4 : new int[] { -2, 2 }) {
            for (int j2 = 1; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i4, j2, 0, this.brickBlock, this.brickMeta);
            }
            this.placeWallBanner(world, i4, 4, 0, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
        }
        this.setBlockAndMetadata(world, -8, 5, -1, this.stairBlock, 1);
        this.setBlockAndMetadata(world, 8, 5, -1, this.stairBlock, 0);
        for (final int i4 : new int[] { -7, 4 }) {
            this.setBlockAndMetadata(world, i4 + 0, 5, -1, this.stairBlock, 4);
            this.setBlockAndMetadata(world, i4 + 0, 6, -1, this.stairBlock, 1);
            this.setBlockAndMetadata(world, i4 + 1, 6, -1, this.stairBlock, 4);
            this.setBlockAndMetadata(world, i4 + 1, 7, -1, this.stairBlock, 1);
            this.setBlockAndMetadata(world, i4 + 2, 6, -1, this.stairBlock, 5);
            this.setBlockAndMetadata(world, i4 + 2, 7, -1, this.stairBlock, 0);
            this.setBlockAndMetadata(world, i4 + 3, 5, -1, this.stairBlock, 5);
            this.setBlockAndMetadata(world, i4 + 3, 6, -1, this.stairBlock, 0);
            this.setBlockAndMetadata(world, i4 + 1, 5, 0, this.stairBlock, 4);
            this.setBlockAndMetadata(world, i4 + 2, 5, 0, this.stairBlock, 5);
        }
        this.setBlockAndMetadata(world, -3, 5, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -2, 5, -1, this.stairBlock, 4);
        this.setBlockAndMetadata(world, -2, 6, -1, this.stairBlock, 1);
        this.setBlockAndMetadata(world, -1, 6, -1, this.stairBlock, 4);
        this.setBlockAndMetadata(world, -1, 7, -1, this.stairBlock, 1);
        this.setBlockAndMetadata(world, 0, 7, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 8, -1, this.slabBlock, this.slabMeta);
        this.setBlockAndMetadata(world, 1, 6, -1, this.stairBlock, 5);
        this.setBlockAndMetadata(world, 1, 7, -1, this.stairBlock, 0);
        this.setBlockAndMetadata(world, 2, 5, -1, this.stairBlock, 5);
        this.setBlockAndMetadata(world, 2, 6, -1, this.stairBlock, 0);
        this.setBlockAndMetadata(world, 3, 5, -1, this.brickBlock, this.brickMeta);
        for (int k5 = 1; k5 <= 3; ++k5) {
            for (int i8 = -1; i8 <= 1; ++i8) {
                if (k5 != 3 || Math.abs(i8) < 1) {
                    this.setAir(world, i8, 4, k5);
                }
            }
        }
        for (int k5 = 1; k5 <= 11; ++k5) {
            this.setBlockAndMetadata(world, 0, 0, k5, this.doubleRockSlabBlock, this.rockSlabMeta);
        }
        for (int i5 = -9; i5 <= 9; ++i5) {
            for (int k6 = 12; k6 <= 18; ++k6) {
                for (int j4 = 0; (j4 == 0 || !this.isOpaque(world, i5, j4, k6)) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i5, j4, k6, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i5, j4 - 1, k6);
                }
                for (int j4 = 1; j4 <= 9; ++j4) {
                    this.setAir(world, i5, j4, k6);
                }
            }
        }
        for (final int k2 : new int[] { 12, 18 }) {
            for (int i7 = -9; i7 <= 9; ++i7) {
                this.setBlockAndMetadata(world, i7, 1, k2, this.doubleRockSlabBlock, this.rockSlabMeta);
                for (int j5 = 2; j5 <= 6; ++j5) {
                    this.setBlockAndMetadata(world, i7, j5, k2, this.brickBlock, this.brickMeta);
                }
            }
            for (final int i9 : new int[] { -9, 9 }) {
                this.placeWoodPillar(world, i9, k2);
                this.setBlockAndMetadata(world, i9, 4, k2, this.doubleRockSlabBlock, this.rockSlabMeta);
                for (int j7 = 5; j7 <= 7; ++j7) {
                    this.setBlockAndMetadata(world, i9, j7, k2, this.pillarBlock, this.pillarMeta);
                }
                this.setBlockAndMetadata(world, i9, 8, k2, this.rockSlabBlock, this.rockSlabMeta);
            }
            for (int i7 = -8; i7 <= 8; ++i7) {
                final int i10 = Math.abs(i7);
                if (i10 >= 5) {
                    if (i10 % 2 == 0) {
                        this.setBlockAndMetadata(world, i7, 7, k2, this.slabBlock, this.slabMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i7, 7, k2, this.brickBlock, this.brickMeta);
                    }
                }
                if (i10 == 4) {
                    for (int j3 = 5; j3 <= 10; ++j3) {
                        this.setBlockAndMetadata(world, i7, j3, k2, this.pillarBlock, this.pillarMeta);
                    }
                    this.setBlockAndMetadata(world, i7, 11, k2, this.slabBlock, this.slabMeta);
                }
                if (i10 <= 3) {
                    for (int j3 = 7; j3 <= 8; ++j3) {
                        this.setBlockAndMetadata(world, i7, j3, k2, this.brickBlock, this.brickMeta);
                    }
                    this.setBlockAndMetadata(world, i7, 9, k2, this.doubleRockSlabBlock, this.rockSlabMeta);
                    if (i10 >= 1) {
                        this.setBlockAndMetadata(world, i7, 10, k2, this.brickBlock, this.brickMeta);
                        if (i10 % 2 == 0) {
                            this.setBlockAndMetadata(world, i7, 11, k2, this.slabBlock, this.slabMeta);
                        }
                    }
                    if (i10 == 0) {
                        this.setBlockAndMetadata(world, i7, 10, k2, this.slabBlock, this.slabMeta);
                    }
                }
            }
        }
        for (int k5 = 13; k5 <= 17; ++k5) {
            for (final int i7 : new int[] { -4, 4 }) {
                this.setBlockAndMetadata(world, i7, 9, k5, this.doubleRockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, i7, 10, k5, this.brickBlock, this.brickMeta);
                if (k5 % 2 == 0) {
                    this.setBlockAndMetadata(world, i7, 11, k5, this.slabBlock, this.slabMeta);
                }
            }
        }
        for (int i5 = -3; i5 <= 3; ++i5) {
            final int i6 = Math.abs(i5);
            for (int k3 = 13; k3 <= 17; ++k3) {
                this.setBlockAndMetadata(world, i5, 9, k3, this.doubleRockSlabBlock, this.rockSlabMeta);
            }
            for (int k3 = 14; k3 <= 16; ++k3) {
                this.setBlockAndMetadata(world, i5, 10, k3, this.doubleRockSlabBlock, this.rockSlabMeta);
                if (i6 <= 2) {
                    this.setBlockAndMetadata(world, i5, 11, k3, this.brickBlock, this.brickMeta);
                }
                if (i6 <= 1) {
                    this.setBlockAndMetadata(world, i5, 12, k3, this.brickBlock, this.brickMeta);
                }
            }
        }
        for (int k5 = 13; k5 <= 17; ++k5) {
            this.setBlockAndMetadata(world, -3, 11, k5, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -2, 12, k5, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 13, k5, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 0, 13, k5, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 14, k5, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 1, 13, k5, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 12, k5, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 3, 11, k5, this.roofStairBlock, 0);
        }
        for (final int k2 : new int[] { 13, 17 }) {
            this.setBlockAndMetadata(world, -3, 10, k2, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -2, 11, k2, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 12, k2, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 12, k2, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 2, 11, k2, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 10, k2, this.roofStairBlock, 5);
        }
        this.placeWallBanner(world, 0, 12, 14, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
        this.placeWallBanner(world, 0, 12, 16, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
        this.placeWallBanner(world, -4, 9, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
        this.setBlockAndMetadata(world, -3, 9, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
        this.setBlockAndMetadata(world, -2, 9, 12, this.stairBlock, 6);
        this.setBlockAndMetadata(world, -2, 8, 12, LOTRMod.stainedGlassPane, 11);
        this.setBlockAndMetadata(world, -1, 9, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
        this.setBlockAndMetadata(world, 0, 9, 12, this.stairBlock, 6);
        this.setBlockAndMetadata(world, 0, 8, 12, LOTRMod.stainedGlassPane, 0);
        this.setBlockAndMetadata(world, 1, 9, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
        this.setBlockAndMetadata(world, 2, 9, 12, this.stairBlock, 6);
        this.setBlockAndMetadata(world, 2, 8, 12, LOTRMod.stainedGlassPane, 11);
        this.setBlockAndMetadata(world, 3, 9, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
        this.placeWallBanner(world, 4, 9, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
        this.placeWoodPillar(world, -3, 12);
        this.placeWoodPillar(world, 3, 12);
        this.placeWoodPillar(world, -6, 18);
        this.placeWoodPillar(world, -2, 18);
        this.placeWoodPillar(world, 2, 18);
        this.placeWoodPillar(world, 6, 18);
        for (int k5 = 13; k5 <= 17; ++k5) {
            for (final int i7 : new int[] { -9, 9 }) {
                this.setBlockAndMetadata(world, i7, 1, k5, this.doubleRockSlabBlock, this.rockSlabMeta);
                for (int j5 = 2; j5 <= 3; ++j5) {
                    this.setBlockAndMetadata(world, i7, j5, k5, this.brickBlock, this.brickMeta);
                }
                this.setBlockAndMetadata(world, i7, 4, k5, this.doubleRockSlabBlock, this.rockSlabMeta);
                for (int j5 = 5; j5 <= 6; ++j5) {
                    this.setBlockAndMetadata(world, i7, j5, k5, this.brickBlock, this.brickMeta);
                }
                if (k5 % 2 == 1) {
                    this.setBlockAndMetadata(world, i7, 7, k5, this.slabBlock, this.slabMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i7, 7, k5, this.brickBlock, this.brickMeta);
                }
            }
        }
        for (int k5 = 13; k5 <= 17; ++k5) {
            for (int step = 0; step <= 4; ++step) {
                this.setBlockAndMetadata(world, -8 + step, 4 + step, k5, this.stairBlock, 4);
                this.setBlockAndMetadata(world, 8 - step, 4 + step, k5, this.stairBlock, 5);
            }
            this.setBlockAndMetadata(world, -8, 5, k5, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -8, 6, k5, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, -7, 6, k5, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, 8, 5, k5, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 8, 6, k5, (Block)Blocks.grass, 0);
            this.setBlockAndMetadata(world, 7, 6, k5, (Block)Blocks.grass, 0);
        }
        for (int i5 = -8; i5 <= 8; ++i5) {
            final int i6 = Math.abs(i5);
            if (i6 == 8) {
                for (int k3 = 13; k3 <= 17; ++k3) {
                    this.setBlockAndMetadata(world, i5, 7, k3, this.leafBlock, this.leafMeta);
                }
                for (int k3 = 14; k3 <= 16; ++k3) {
                    this.setBlockAndMetadata(world, i5, 8, k3, this.leafBlock, this.leafMeta);
                }
            }
            else if (i6 == 7) {
                for (int k3 = 13; k3 <= 17; ++k3) {
                    this.setBlockAndMetadata(world, i5, 7, k3, this.leafBlock, this.leafMeta);
                }
                this.setBlockAndMetadata(world, i5, 8, 13, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 8, 17, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 7, 15, this.rockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, i5, 6, 15, this.brickBlock, this.brickMeta);
            }
            else if (i6 == 6) {
                this.setBlockAndMetadata(world, i5, 7, 13, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 7, 17, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 8, 13, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 8, 17, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 7, 14, this.rockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, i5, 7, 15, this.rockSlabBlock, this.rockSlabMeta);
                this.setBlockAndMetadata(world, i5, 7, 16, this.rockSlabBlock, this.rockSlabMeta);
            }
            else if (i6 == 5) {
                this.setBlockAndMetadata(world, i5, 8, 13, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 8, 14, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 8, 16, this.leafBlock, this.leafMeta);
                this.setBlockAndMetadata(world, i5, 8, 17, this.leafBlock, this.leafMeta);
            }
        }
        for (int i5 = -8; i5 <= 8; ++i5) {
            for (int k6 = 13; k6 <= 17; ++k6) {
                this.setBlockAndMetadata(world, i5, 0, k6, this.doubleRockSlabBlock, this.rockSlabMeta);
            }
        }
        for (int i5 = -2; i5 <= 2; ++i5) {
            this.setBlockAndMetadata(world, i5, 0, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
            for (int j6 = 1; j6 <= 3; ++j6) {
                this.setAir(world, i5, j6, 12);
            }
        }
        for (int j8 = 1; j8 <= 3; ++j8) {
            for (final int i7 : new int[] { -2, 2 }) {
                this.setBlockAndMetadata(world, i7, j8, 12, this.brickBlock, this.brickMeta);
            }
            for (int i8 = -1; i8 <= 1; ++i8) {
                this.setBlockAndMetadata(world, i8, j8, 12, LOTRMod.gateDolAmroth, 2);
            }
        }
        for (final int f2 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, 4 * f2, 4, 13, this.slabBlock, this.slabMeta | 0x8);
            this.setBlockAndMetadata(world, 3 * f2, 5, 13, this.slabBlock, this.slabMeta);
            this.setBlockAndMetadata(world, 2 * f2, 5, 13, this.slabBlock, this.slabMeta | 0x8);
            this.setBlockAndMetadata(world, 1 * f2, 6, 13, this.slabBlock, this.slabMeta);
            this.setBlockAndMetadata(world, 0 * f2, 6, 13, this.slabBlock, this.slabMeta);
            this.placeWallBanner(world, 3 * f2, 4, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
            this.setBlockAndMetadata(world, 6 * f2, 3, 13, Blocks.torch, 3);
            for (int j2 = 5; j2 <= 6; ++j2) {
                this.setBlockAndMetadata(world, 6 * f2, j2, 18, this.woodBeamBlock, this.woodBeamMeta);
            }
            for (int j2 = 5; j2 <= 7; ++j2) {
                this.setBlockAndMetadata(world, 2 * f2, j2, 18, this.woodBeamBlock, this.woodBeamMeta);
            }
            this.placeWallBanner(world, 6 * f2, 5, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
            this.placeWallBanner(world, 6 * f2, 5, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
            this.placeWallBanner(world, 2 * f2, 6, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
            this.placeWallBanner(world, 2 * f2, 6, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
            this.setBlockAndMetadata(world, 6 * f2, 3, 17, Blocks.torch, 4);
            for (final int k7 : new int[] { 13, 17 }) {
                this.setBlockAndMetadata(world, 4 * f2, 1, k7, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, 5 * f2, 1, k7, this.plankSlabBlock, this.plankSlabMeta | 0x8);
                this.setBlockAndMetadata(world, 7 * f2, 1, k7, this.plankSlabBlock, this.plankSlabMeta | 0x8);
                this.placeChest(world, random, 6 * f2, 1, k7, 0, LOTRChestContents.DOL_AMROTH_STABLES);
            }
        }
        this.setBlockAndMetadata(world, -8, 1, 13, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -8, 1, 17, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 8, 1, 13, LOTRMod.gondorianTable, 0);
        this.setBlockAndMetadata(world, 8, 1, 17, LOTRMod.dolAmrothTable, 0);
        for (final int i4 : new int[] { -9, 9 }) {
            for (int k8 = 14; k8 <= 16; ++k8) {
                this.setBlockAndMetadata(world, i4, 1, k8, this.doubleRockSlabBlock, this.rockSlabMeta);
                this.setAir(world, i4, 2, k8);
            }
            this.setBlockAndMetadata(world, i4, 3, 14, this.stairBlock, 7);
            this.setBlockAndMetadata(world, i4, 3, 15, this.slabBlock, this.slabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 3, 16, this.stairBlock, 6);
        }
        for (final int i11 : new int[] { -8, 7 }) {
            int i4;
            for (i4 = i11; i11 <= i4 + 1; ++i11) {
                this.setBlockAndMetadata(world, i11, 1, 18, this.doubleRockSlabBlock, this.rockSlabMeta);
                this.setAir(world, i11, 2, 18);
            }
            this.setBlockAndMetadata(world, i4, 3, 18, this.stairBlock, 4);
            this.setBlockAndMetadata(world, i4 + 1, 3, 18, this.stairBlock, 5);
        }
        for (int i5 = -8; i5 <= 8; ++i5) {
            this.setBlockAndMetadata(world, i5, 1, 15, Blocks.carpet, 11);
        }
        for (int i5 = -2; i5 <= 2; ++i5) {
            this.setBlockAndMetadata(world, i5, 1, 14, Blocks.carpet, 11);
            this.setBlockAndMetadata(world, i5, 1, 16, Blocks.carpet, 11);
        }
        this.generateWindow(world, -4, 3, 18);
        this.generateWindow(world, 4, 3, 18);
        for (final int k2 : new int[] { 14, 16 }) {
            this.setBlockAndMetadata(world, -1, 9, k2, Blocks.stained_hardened_clay, 3);
            this.setBlockAndMetadata(world, 0, 9, k2, Blocks.stained_hardened_clay, 11);
            this.setBlockAndMetadata(world, 1, 9, k2, Blocks.stained_hardened_clay, 3);
        }
        this.setBlockAndMetadata(world, -2, 9, 15, Blocks.stained_hardened_clay, 3);
        this.setBlockAndMetadata(world, -1, 9, 15, Blocks.stained_hardened_clay, 11);
        this.setBlockAndMetadata(world, 0, 9, 15, Blocks.stained_hardened_clay, 11);
        this.setBlockAndMetadata(world, 1, 9, 15, Blocks.stained_hardened_clay, 11);
        this.setBlockAndMetadata(world, 2, 9, 15, Blocks.stained_hardened_clay, 3);
        this.setBlockAndMetadata(world, 0, 8, 15, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 7, 15, LOTRMod.chandelier, 2);
        for (int i5 = -1; i5 <= 1; ++i5) {
            this.setBlockAndMetadata(world, i5, 3, 18, this.doubleRockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, i5, 7, 18, this.doubleRockSlabBlock, this.rockSlabMeta);
            for (int j6 = 4; j6 <= 6; ++j6) {
                if (IntMath.mod(i5 + j6, 2) == 0) {
                    this.setBlockAndMetadata(world, i5, j6, 18, LOTRMod.stainedGlassPane, 0);
                }
                else {
                    this.setBlockAndMetadata(world, i5, j6, 18, LOTRMod.stainedGlassPane, 11);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 8, 18, this.doubleRockSlabBlock, this.rockSlabMeta);
        for (int i5 = -6; i5 <= 6; ++i5) {
            final int i6 = Math.abs(i5);
            this.placeGrassFoundation(world, i5, 19);
            if (i6 % 4 == 2) {
                this.setBlockAndMetadata(world, i5, 1, 19, this.stairBlock, 3);
                this.setGrassToDirt(world, i5, 0, 19);
            }
            else {
                this.setBlockAndMetadata(world, i5, 1, 19, this.leafBlock, this.leafMeta);
            }
            if (i6 >= 6) {
                this.setBlockAndMetadata(world, i5, 6, 19, this.slabBlock, this.slabMeta | 0x8);
            }
            else if (i6 >= 3) {
                this.setBlockAndMetadata(world, i5, 7, 19, this.slabBlock, this.slabMeta);
            }
            else if (i6 >= 2) {
                this.setBlockAndMetadata(world, i5, 7, 19, this.slabBlock, this.slabMeta | 0x8);
            }
            else {
                this.setBlockAndMetadata(world, i5, 8, 19, this.slabBlock, this.slabMeta);
            }
        }
        final LOTREntityNPC captain = new LOTREntityDolAmrothCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 15, 8);
        return true;
    }
    
    private void placeWoodPillar(final World world, final int i, final int k) {
        for (int j = 0; (!this.isOpaque(world, i, j, k) || (this.getBlock(world, i, j, k) == this.brickBlock && this.getMeta(world, i, j, k) == this.brickMeta)) && this.getY(j) >= 0; --j) {
            this.setBlockAndMetadata(world, i, j, k, this.woodBeamBlock, this.woodBeamMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
        for (int j = 1; j <= 4; ++j) {
            this.setBlockAndMetadata(world, i, j, k, this.woodBeamBlock, this.woodBeamMeta);
        }
    }
    
    private void generateWindow(final World world, final int i, final int j, final int k) {
        this.setBlockAndMetadata(world, i - 1, j, k, this.stairBlock, 0);
        this.setBlockAndMetadata(world, i, j, k, this.slabBlock, this.slabMeta);
        this.setBlockAndMetadata(world, i + 1, j, k, this.stairBlock, 1);
        for (int i2 = i - 1; i2 <= i + 1; ++i2) {
            this.setAir(world, i2, j + 1, k);
            this.setAir(world, i2, j + 2, k);
        }
        this.setBlockAndMetadata(world, i - 1, j + 3, k, this.stairBlock, 4);
        this.setBlockAndMetadata(world, i, j + 3, k, this.slabBlock, this.slabMeta | 0x8);
        this.setBlockAndMetadata(world, i + 1, j + 3, k, this.stairBlock, 5);
    }
    
    private void placeGrassFoundation(final World world, final int i, final int k) {
        for (int j1 = 6; (j1 >= 0 || !this.isOpaque(world, i, j1, k)) && this.getY(j1) >= 0; --j1) {
            if (j1 > 0) {
                this.setAir(world, i, j1, k);
            }
            else if (j1 == 0) {
                this.setBlockAndMetadata(world, i, j1, k, (Block)Blocks.grass, 0);
                this.setGrassToDirt(world, i, j1 - 1, k);
            }
            else {
                this.setBlockAndMetadata(world, i, j1, k, Blocks.dirt, 0);
                this.setGrassToDirt(world, i, j1 - 1, k);
            }
        }
    }
}
