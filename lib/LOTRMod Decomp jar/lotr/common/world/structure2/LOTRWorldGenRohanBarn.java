// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityCow;
import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.entity.passive.EntityAnimal;
import lotr.common.entity.npc.LOTREntityRohanFarmer;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanBarn extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanBarn(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -1; k2 <= 16; ++k2) {
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
                    if (maxHeight - minHeight > 6) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = 0; k3 <= 15; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = IntMath.mod(k3, 3);
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 11; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                boolean beam = false;
                if (i4 == 5 && k4 == 0) {
                    beam = true;
                }
                if ((k3 == 0 || k3 == 15) && i4 == 2) {
                    beam = true;
                }
                if (beam) {
                    for (int j3 = 1; j3 <= 5; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                    }
                    if (k3 == 0 || k3 == 15) {
                        for (int j3 = 6; j3 <= 7; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                }
                else if (i4 == 5 || k3 == 0 || k3 == 15) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.plank2Block, super.plank2Meta);
                    for (int j3 = 2; j3 <= 5; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.plankBlock, super.plankMeta);
                    }
                    if (k3 == 0 || k3 == 15) {
                        for (int j3 = 6; j3 <= 7; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.plankBlock, super.plankMeta);
                        }
                    }
                    this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    this.setBlockAndMetadata(world, i3, 8, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                }
                if (i4 <= 4 && k3 >= 1 && k3 <= 14) {
                    if (k3 >= 3 && k3 <= 12) {
                        this.setBlockAndMetadata(world, i3, 0, k3, Blocks.dirt, 1);
                    }
                    if (random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                    if (i4 >= 2 || k3 <= 3) {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.plankBlock, super.plankMeta);
                        if (random.nextBoolean()) {
                            this.setBlockAndMetadata(world, i3, 6, k3, LOTRMod.thatchFloor, 0);
                        }
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            final int i5 = Math.abs(i3);
            if (i5 == 2 || i5 == 5) {
                for (int k5 = -1; k5 <= 16; ++k5) {
                    this.setBlockAndMetadata(world, i3, 5, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    this.setBlockAndMetadata(world, i3, 8, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    if (k5 == -1 || k5 == 16) {
                        this.setBlockAndMetadata(world, i3, 1, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                        this.setGrassToDirt(world, i3, 0, k5);
                        for (int j4 = 2; j4 <= 4; ++j4) {
                            this.setBlockAndMetadata(world, i3, j4, k5, super.fenceBlock, super.fenceMeta);
                        }
                        for (int j4 = 6; j4 <= 7; ++j4) {
                            this.setBlockAndMetadata(world, i3, j4, k5, super.fenceBlock, super.fenceMeta);
                        }
                    }
                }
            }
            else {
                for (final int k6 : new int[] { 0, 15 }) {
                    this.setBlockAndMetadata(world, i3, 3, k6, super.plank2SlabBlock, super.plank2SlabMeta);
                    if (i3 == -4 || i3 == 3) {
                        this.setBlockAndMetadata(world, i3, 4, k6, super.plankStairBlock, 4);
                    }
                    else if (i3 == -3 || i3 == 4) {
                        this.setBlockAndMetadata(world, i3, 4, k6, super.plankStairBlock, 5);
                    }
                    if (i3 == -1) {
                        this.setBlockAndMetadata(world, i3, 4, k6, super.plankStairBlock, 4);
                    }
                    else if (i3 == 1) {
                        this.setBlockAndMetadata(world, i3, 4, k6, super.plankStairBlock, 5);
                    }
                    else if (i3 == 0) {
                        this.setBlockAndMetadata(world, i3, 4, k6, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                    }
                    this.setBlockAndMetadata(world, i3, 7, k6, super.fenceBlock, super.fenceMeta);
                }
                for (final int k6 : new int[] { -1, 16 }) {
                    if (i5 >= 3 || k6 != -1) {
                        this.setBlockAndMetadata(world, i3, 1, k6, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                    }
                    this.setBlockAndMetadata(world, i3, 5, k6, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                    this.setBlockAndMetadata(world, i3, 8, k6, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
            }
        }
        for (int k7 = 0; k7 <= 15; ++k7) {
            final int k8 = IntMath.mod(k7, 3);
            if (k8 == 0) {
                for (int i2 = -7; i2 <= 7; ++i2) {
                    final int i6 = Math.abs(i2);
                    if (i6 == 6) {
                        this.setBlockAndMetadata(world, i2, 1, k7, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                        this.setGrassToDirt(world, i2, 0, k7);
                        for (int j2 = 2; j2 <= 4; ++j2) {
                            this.setBlockAndMetadata(world, i2, j2, k7, super.fenceBlock, super.fenceMeta);
                        }
                    }
                    if (i6 >= 6) {
                        this.setBlockAndMetadata(world, i2, 5, k7, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                }
            }
            else {
                for (final int i7 : new int[] { -6, 6 }) {
                    this.setBlockAndMetadata(world, i7, 1, k7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
                this.setBlockAndMetadata(world, -7, 5, k7, super.plank2StairBlock, 1);
                this.setBlockAndMetadata(world, -6, 5, k7, super.plank2StairBlock, 4);
                this.setBlockAndMetadata(world, 6, 5, k7, super.plank2StairBlock, 5);
                this.setBlockAndMetadata(world, 7, 5, k7, super.plank2StairBlock, 0);
                if (k7 >= 3) {
                    for (final int i7 : new int[] { -5, 5 }) {
                        this.setBlockAndMetadata(world, i7, 3, k7, super.plank2SlabBlock, super.plank2SlabMeta);
                        if (k8 == 1) {
                            this.setBlockAndMetadata(world, i7, 4, k7, super.plankStairBlock, 7);
                        }
                        else if (k8 == 2) {
                            this.setBlockAndMetadata(world, i7, 4, k7, super.plankStairBlock, 6);
                        }
                    }
                }
            }
        }
        for (final int k2 : new int[] { -1, 16 }) {
            this.setBlockAndMetadata(world, -7, 5, k2, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -6, 5, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 6, 5, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 7, 5, k2, super.plank2StairBlock, 0);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int j5 = 1; j5 <= 4; ++j5) {
                this.setBlockAndMetadata(world, i3, j5, 0, super.gateBlock, 2);
            }
        }
        this.setBlockAndMetadata(world, 0, 3, 0, LOTRMod.gateIronBars, 2);
        for (int k7 = 1; k7 <= 14; ++k7) {
            if (IntMath.mod(k7, 3) == 0) {
                this.setBlockAndMetadata(world, -6, 6, k7, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, -6, 7, k7, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, -6, 8, k7, super.plank2StairBlock, 1);
                this.setBlockAndMetadata(world, -5, 9, k7, super.plank2StairBlock, 1);
                this.setBlockAndMetadata(world, -4, 9, k7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                this.setBlockAndMetadata(world, -3, 10, k7, super.plank2SlabBlock, super.plank2SlabMeta);
                for (int i8 = -2; i8 <= 2; ++i8) {
                    this.setBlockAndMetadata(world, i8, 10, k7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
                this.setBlockAndMetadata(world, 3, 10, k7, super.plank2SlabBlock, super.plank2SlabMeta);
                this.setBlockAndMetadata(world, 4, 9, k7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                this.setBlockAndMetadata(world, 5, 9, k7, super.plank2StairBlock, 0);
                this.setBlockAndMetadata(world, 6, 8, k7, super.plank2StairBlock, 0);
                this.setBlockAndMetadata(world, 6, 6, k7, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, 6, 7, k7, super.plank2Block, super.plank2Meta);
            }
            else {
                this.setBlockAndMetadata(world, -6, 6, k7, super.roofBlock, super.roofMeta);
                this.setBlockAndMetadata(world, -6, 7, k7, super.roofBlock, super.roofMeta);
                this.setBlockAndMetadata(world, -6, 8, k7, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, -5, 9, k7, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, -4, 9, k7, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                this.setBlockAndMetadata(world, -3, 10, k7, super.roofSlabBlock, super.roofSlabMeta);
                for (int i8 = -2; i8 <= 2; ++i8) {
                    this.setBlockAndMetadata(world, i8, 10, k7, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                }
                this.setBlockAndMetadata(world, 3, 10, k7, super.roofSlabBlock, super.roofSlabMeta);
                this.setBlockAndMetadata(world, 4, 9, k7, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                this.setBlockAndMetadata(world, 5, 9, k7, super.roofStairBlock, 0);
                this.setBlockAndMetadata(world, 6, 8, k7, super.roofStairBlock, 0);
                this.setBlockAndMetadata(world, 6, 6, k7, super.roofBlock, super.roofMeta);
                this.setBlockAndMetadata(world, 6, 7, k7, super.roofBlock, super.roofMeta);
            }
        }
        for (final int k2 : new int[] { 0, 15 }) {
            this.setBlockAndMetadata(world, -6, 6, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -6, 7, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -6, 8, k2, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -5, 9, k2, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -4, 9, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -3, 9, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -3, 10, k2, super.plank2SlabBlock, super.plank2SlabMeta);
            this.setBlockAndMetadata(world, -2, 9, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, -2, 10, k2, super.plank2Block, super.plank2Meta);
            for (int i9 = -1; i9 <= 1; ++i9) {
                this.setBlockAndMetadata(world, i9, 10, k2, super.plank2Block, super.plank2Meta);
            }
            this.setBlockAndMetadata(world, 2, 9, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 2, 10, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 3, 9, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 3, 10, k2, super.plank2SlabBlock, super.plank2SlabMeta);
            this.setBlockAndMetadata(world, 4, 9, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 5, 9, k2, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, 6, 8, k2, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, 6, 6, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 6, 7, k2, super.plank2Block, super.plank2Meta);
        }
        for (final int k2 : new int[] { -1, 16 }) {
            this.setBlockAndMetadata(world, -6, 8, k2, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -5, 9, k2, super.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -4, 9, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, -3, 9, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, -3, 10, k2, super.plank2SlabBlock, super.plank2SlabMeta);
            this.setBlockAndMetadata(world, -2, 10, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, -1, 10, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, -1, 11, k2, super.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 0, 11, k2, super.plank2SlabBlock, super.plank2SlabMeta);
            this.setBlockAndMetadata(world, 1, 10, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 1, 11, k2, super.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, 2, 10, k2, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 3, 9, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 3, 10, k2, super.plank2SlabBlock, super.plank2SlabMeta);
            this.setBlockAndMetadata(world, 4, 9, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            this.setBlockAndMetadata(world, 5, 9, k2, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, 6, 8, k2, super.plank2StairBlock, 0);
        }
        for (int k7 = 0; k7 <= 15; ++k7) {
            this.setBlockAndMetadata(world, 0, 11, k7, super.plank2SlabBlock, super.plank2SlabMeta);
        }
        this.setBlockAndMetadata(world, -4, 1, 1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 1, 1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 1, 1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 1, 1, Blocks.hay_block, 0);
        for (int j6 = 1; j6 <= 7; ++j6) {
            if (j6 >= 6) {
                this.setBlockAndMetadata(world, -5, j6, 2, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, 5, j6, 2, super.plankBlock, super.plankMeta);
            }
            this.setBlockAndMetadata(world, -4, j6, 2, Blocks.ladder, 4);
            this.setBlockAndMetadata(world, 4, j6, 2, Blocks.ladder, 5);
        }
        for (int k7 = 3; k7 <= 12; ++k7) {
            final int k8 = IntMath.mod(k7, 3);
            for (int i2 = -4; i2 <= 4; ++i2) {
                final int i6 = Math.abs(i2);
                if (k8 == 0) {
                    if (i6 >= 2) {
                        this.setBlockAndMetadata(world, i2, 1, k7, super.fenceBlock, super.fenceMeta);
                        this.setBlockAndMetadata(world, i2, 2, k7, super.fenceBlock, super.fenceMeta);
                    }
                    if (i6 == 2) {
                        this.setBlockAndMetadata(world, i2, 3, k7, super.fenceBlock, super.fenceMeta);
                        this.setBlockAndMetadata(world, i2, 4, k7, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (k8 == 1) {
                    if (i6 == 2) {
                        this.setBlockAndMetadata(world, i2, 1, k7, super.fenceBlock, super.fenceMeta);
                    }
                    if (i6 == 4) {
                        this.setBlockAndMetadata(world, i2, 1, k7, Blocks.hay_block, 0);
                        this.setBlockAndMetadata(world, i2, 2, k7, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (k8 == 2) {
                    if (i6 == 2) {
                        this.setBlockAndMetadata(world, i2, 1, k7, super.fenceGateBlock, (i2 > 0) ? 3 : 1);
                    }
                    if (i6 == 4) {
                        this.setBlockAndMetadata(world, i2, 1, k7, (Block)Blocks.cauldron, 3);
                        this.setBlockAndMetadata(world, i2, 2, k7, super.fenceBlock, super.fenceMeta);
                    }
                    if (i6 == 3) {
                        final EntityAnimal animal = getRandomAnimal(world, random);
                        this.spawnNPCAndSetHome((EntityCreature)animal, world, i2, 1, k7, 0);
                        animal.detachHome();
                    }
                }
                if (i6 == 4) {
                    this.setBlockAndMetadata(world, i2, 3, k7, super.plank2SlabBlock, super.plank2SlabMeta);
                }
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int hayHeight = 1 + random.nextInt(2), j7 = 1; j7 <= hayHeight; ++j7) {
                this.setBlockAndMetadata(world, i3, j7, 14, Blocks.hay_block, 0);
            }
        }
        this.placeChest(world, random, -4, 1, 13, 4, LOTRChestContents.ROHAN_HOUSE);
        this.placeChest(world, random, -4, 1, 14, 4, LOTRChestContents.ROHAN_HOUSE);
        this.setBlockAndMetadata(world, 4, 1, 13, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 4, 1, 14, super.tableBlock, 0);
        this.setBlockAndMetadata(world, -2, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 3, 14, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, 14, Blocks.torch, 4);
        for (int k7 = 3; k7 <= 14; ++k7) {
            this.setBlockAndMetadata(world, -2, 6, k7, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 2, 6, k7, super.fenceBlock, super.fenceMeta);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, 3, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -2, 6, 1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 6, 1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 7, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 7, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 7, 14, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 7, 14, Blocks.torch, 4);
        for (final int k2 : new int[] { 1, 14 }) {
            for (int i9 = -4; i9 <= 4; ++i9) {
                final int i10 = Math.abs(i9);
                if (i10 <= 1 || i10 >= 3) {
                    this.setBlockAndMetadata(world, i9, 8, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
            }
        }
        for (int k7 = 1; k7 <= 14; ++k7) {
            if (k7 == 1 || IntMath.mod(k7, 3) == 0) {
                for (final int i9 : new int[] { -5, 5 }) {
                    this.setBlockAndMetadata(world, i9, 6, k7, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i9, 7, k7, super.fenceBlock, super.fenceMeta);
                }
            }
            else if (k7 != 2) {
                for (final int i9 : new int[] { -5, 5 }) {
                    final int j3 = 6;
                    if (random.nextBoolean()) {
                        int j8 = j3;
                        if (random.nextBoolean()) {
                            ++j8;
                        }
                        for (int j9 = j3; j9 <= j8; ++j9) {
                            this.setBlockAndMetadata(world, i9, j9, k7, Blocks.hay_block, 0);
                        }
                        if (j8 >= j3 + 1 && random.nextBoolean()) {
                            final int i11 = (Math.abs(i9) - 1) * Integer.signum(i9);
                            j8 = j3;
                            if (random.nextBoolean()) {
                                ++j8;
                            }
                            for (int j10 = j3; j10 <= j8; ++j10) {
                                this.setBlockAndMetadata(world, i11, j10, k7, Blocks.hay_block, 0);
                            }
                        }
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            final int i5 = Math.abs(i3);
            if (i5 != 2 && random.nextBoolean()) {
                this.setBlockAndMetadata(world, i3, 6, 1, Blocks.hay_block, 0);
            }
        }
        final LOTREntityRohanMan farmer = new LOTREntityRohanFarmer(world);
        this.spawnNPCAndSetHome(farmer, world, 0, 1, 8, 16);
        return true;
    }
    
    public static EntityAnimal getRandomAnimal(final World world, final Random random) {
        final int animal = random.nextInt(4);
        if (animal == 0) {
            return (EntityAnimal)new EntityCow(world);
        }
        if (animal == 1) {
            return (EntityAnimal)new EntityPig(world);
        }
        if (animal == 2) {
            return (EntityAnimal)new EntitySheep(world);
        }
        if (animal == 3) {
            return (EntityAnimal)new EntityChicken(world);
        }
        return null;
    }
}
