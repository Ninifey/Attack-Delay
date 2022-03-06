// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityEasterlingBartender;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenEasterlingTavern extends LOTRWorldGenEasterlingStructure
{
    private String[] tavernName;
    private String[] tavernNameSign;
    private String tavernNameNPC;
    
    public LOTRWorldGenEasterlingTavern(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.bedBlock = LOTRMod.strawBed;
        this.tavernName = LOTRNames.getRhunTavernName(random);
        this.tavernNameSign = new String[] { "", this.tavernName[0], this.tavernName[1], "" };
        this.tavernNameNPC = this.tavernName[0] + " " + this.tavernName[1];
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 11);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -9; i2 <= 9; ++i2) {
                for (int k2 = -12; k2 <= 11; ++k2) {
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
        for (int i3 = -8; i3 <= 8; ++i3) {
            for (int k3 = -10; k3 <= 10; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 1; j2 <= 12; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if ((i4 == 8 && k4 % 4 == 2) || (k4 == 10 && i4 % 4 == 0)) {
                    for (int j2 = 4; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if (i4 == 8 || k4 == 10) {
                    for (int j2 = 3; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (k4 == 10) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    else if (i4 == 8) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    }
                }
                else {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.plankBlock, super.plankMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (i4 % 4 == 2 && k4 % 4 == 0) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.logBlock, super.logMeta);
                    }
                }
            }
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k3 = -9; k3 <= 9; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 <= 4 && k4 <= 9) {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                if (i4 % 4 == 0 && k4 <= 9) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                }
                if (k4 % 4 == 2 && i4 <= 7) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    if (k4 == 2) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                }
                if (k4 == 2 && i4 % 4 == 0) {
                    for (int j2 = 1; j2 <= 3; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
            }
        }
        for (int i3 = -8; i3 <= 8; ++i3) {
            final int i5 = Math.abs(i3);
            if (i5 == 2) {
                this.setBlockAndMetadata(world, i3, 2, -10, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, i3, 3, -10, super.brickStairBlock, 6);
            }
            if (i5 == 6) {
                this.setBlockAndMetadata(world, i3 - 1, 2, -10, super.brickStairBlock, 4);
                this.setAir(world, i3, 2, -10);
                this.setBlockAndMetadata(world, i3 + 1, 2, -10, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i3, 3, -10, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i3 - 1, 2, 10, super.brickStairBlock, 4);
                this.setAir(world, i3, 2, 10);
                this.setBlockAndMetadata(world, i3 + 1, 2, 10, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i3, 3, 10, super.brickStairBlock, 7);
            }
            if (i5 == 4) {
                this.setBlockAndMetadata(world, i3, 3, -11, Blocks.torch, 4);
                this.setBlockAndMetadata(world, i3, 3, 11, Blocks.torch, 3);
            }
            if (i5 == 0) {
                this.setBlockAndMetadata(world, i3, 3, 11, Blocks.torch, 3);
            }
            if (i5 == 8) {
                this.setBlockAndMetadata(world, i3, 3, -11, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, i3, 3, 11, super.fenceBlock, super.fenceMeta);
            }
        }
        for (int k5 = -10; k5 <= 10; ++k5) {
            final int k6 = Math.abs(k5);
            if (k6 % 4 == 0) {
                this.setBlockAndMetadata(world, -8, 2, k5 - 1, super.brickStairBlock, 7);
                this.setAir(world, -8, 2, k5);
                this.setBlockAndMetadata(world, -8, 2, k5 + 1, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, -8, 3, k5, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, 8, 2, k5 - 1, super.brickStairBlock, 7);
                this.setAir(world, 8, 2, k5);
                this.setBlockAndMetadata(world, 8, 2, k5 + 1, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, 8, 3, k5, super.brickStairBlock, 4);
            }
            if (k6 % 4 == 2) {
                this.setBlockAndMetadata(world, -9, 3, k5, Blocks.torch, 1);
                this.setBlockAndMetadata(world, 9, 3, k5, Blocks.torch, 2);
            }
            if (k6 == 10) {
                this.setBlockAndMetadata(world, -9, 3, k5, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, 9, 3, k5, super.fenceBlock, super.fenceMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, -10, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 1, -10, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -10, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -10, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 4, -11, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 4, -12, super.plankBlock, super.plankMeta);
        this.placeSign(world, -1, 4, -12, Blocks.wall_sign, 5, this.tavernNameSign);
        this.placeSign(world, 1, 4, -12, Blocks.wall_sign, 4, this.tavernNameSign);
        this.placeSign(world, 0, 4, -13, Blocks.wall_sign, 2, this.tavernNameSign);
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -9; k3 <= 9; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 == 4 && k4 == 2) || (k4 == 9 && i4 % 4 == 0)) {
                    for (int j2 = 5; j2 <= 8; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                    }
                    if (i4 == 0) {
                        for (int j2 = 9; j2 <= 11; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                }
                else if (i4 == 4 || k4 == 9) {
                    for (int j2 = 5; j2 <= 7; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                    }
                    if (k4 == 9) {
                        this.setBlockAndMetadata(world, i3, 8, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    else if (i4 == 4) {
                        this.setBlockAndMetadata(world, i3, 8, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    }
                }
            }
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6, 6, -9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i6, 7, -9, super.brickStairBlock, 6);
            if (i6 >= 0) {
                this.setBlockAndMetadata(world, i6, 6, 9, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, i6, 7, 9, super.brickStairBlock, 7);
            }
        }
        for (final int i6 : new int[] { -4, 4 }) {
            this.setBlockAndMetadata(world, i6, 8, -10, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i6, 8, 10, super.fenceBlock, super.fenceMeta);
        }
        for (final int k2 : new int[] { -9, 9 }) {
            this.setBlockAndMetadata(world, -5, 8, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 5, 8, k2, super.fenceBlock, super.fenceMeta);
        }
        for (int step = 0; step <= 1; ++step) {
            final int j3 = 5 + step;
            for (int k7 = -10 + step; k7 <= 10 - step; ++k7) {
                this.setBlockAndMetadata(world, -8 + step, j3, k7, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, -7 + step, j3, k7, super.roofStairBlock, 4);
            }
            for (int i2 = -7 + step; i2 <= -5; ++i2) {
                this.setBlockAndMetadata(world, i2, j3, -10 + step, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i2, j3, -9 + step, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i2, j3, 10 - step, super.roofStairBlock, 3);
                this.setBlockAndMetadata(world, i2, j3, 9 - step, super.roofStairBlock, 6);
            }
            for (int k7 = -10 + step; k7 <= 10 - step; ++k7) {
                this.setBlockAndMetadata(world, 8 - step, j3, k7, super.roofStairBlock, 0);
                this.setBlockAndMetadata(world, 7 - step, j3, k7, super.roofStairBlock, 5);
            }
            for (int i2 = 5; i2 <= 7 - step; ++i2) {
                this.setBlockAndMetadata(world, i2, j3, -10 + step, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i2, j3, -9 + step, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i2, j3, 10 - step, super.roofStairBlock, 3);
                this.setBlockAndMetadata(world, i2, j3, 9 - step, super.roofStairBlock, 6);
            }
            if (step == 1) {
                for (int k7 = -9 + step; k7 <= 9 - step; ++k7) {
                    for (int i6 = -7 + step; i6 <= -5; ++i6) {
                        this.setBlockAndMetadata(world, i6, j3 + 1, k7, super.roofSlabBlock, super.roofSlabMeta);
                    }
                    for (int i6 = 5; i6 <= 7 - step; ++i6) {
                        this.setBlockAndMetadata(world, i6, j3 + 1, k7, super.roofSlabBlock, super.roofSlabMeta);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -4, 5, -10, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -3, 5, -10, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -2, 5, -10, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -1, 5, -10, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 5, -10, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 1, 5, -10, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 5, -10, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 5, -10, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 4, 5, -10, super.roofStairBlock, 1);
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, 10, super.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, 0, 5, 10, super.roofBlock, super.roofMeta);
        for (int i3 = -9; i3 <= 9; ++i3) {
            final int i5 = Math.abs(i3);
            if (i5 == 9 || i5 == 6 || i5 == 2) {
                this.setBlockAndMetadata(world, i3, 5, -11, super.roofSlabBlock, super.roofSlabMeta);
            }
            if (i3 == -8 || i3 == 7) {
                this.setBlockAndMetadata(world, i3, 4, -11, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i3 + 1, 4, -11, super.roofStairBlock, 4);
            }
            if (i5 == 4) {
                this.setBlockAndMetadata(world, i3 - 1, 4, -11, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i3, 4, -11, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i3 + 1, 4, -11, super.roofStairBlock, 4);
            }
            if (i5 <= 1) {
                this.setBlockAndMetadata(world, i3, 5, -11, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
            if (i5 == 9 || i5 == 6 || i5 == 2) {
                this.setBlockAndMetadata(world, i3, 5, 11, super.roofSlabBlock, super.roofSlabMeta);
            }
            if (i3 == -8 || i3 == 7) {
                this.setBlockAndMetadata(world, i3, 4, 11, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i3 + 1, 4, 11, super.roofStairBlock, 4);
            }
            if (i5 == 4 || i5 == 0) {
                this.setBlockAndMetadata(world, i3 - 1, 4, 11, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i3, 4, 11, super.roofStairBlock, 3);
                this.setBlockAndMetadata(world, i3 + 1, 4, 11, super.roofStairBlock, 4);
            }
        }
        for (int k5 = -10; k5 <= 10; ++k5) {
            final int k6 = Math.abs(k5);
            if (k6 % 4 == 0) {
                this.setBlockAndMetadata(world, -9, 5, k5, super.roofSlabBlock, super.roofSlabMeta);
                this.setBlockAndMetadata(world, 9, 5, k5, super.roofSlabBlock, super.roofSlabMeta);
            }
            if (k5 == -10 || k5 == 9) {
                this.setBlockAndMetadata(world, -9, 4, k5, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, -9, 4, k5 + 1, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, 9, 4, k5, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, 9, 4, k5 + 1, super.roofStairBlock, 7);
            }
            if (k6 <= 6 && k6 % 4 == 2) {
                this.setBlockAndMetadata(world, -9, 4, k5 - 1, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, -9, 4, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, -9, 4, k5 + 1, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, 9, 4, k5 - 1, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, 9, 4, k5, super.roofStairBlock, 0);
                this.setBlockAndMetadata(world, 9, 4, k5 + 1, super.roofStairBlock, 7);
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            final int i5 = Math.abs(i3);
            for (final int k8 : new int[] { -10, 10 }) {
                if (i5 == 2 || i5 == 5) {
                    this.setBlockAndMetadata(world, i3, 9, k8, super.roofSlabBlock, super.roofSlabMeta);
                }
                if (i5 == 0) {
                    this.setBlockAndMetadata(world, i3 - 1, 8, k8, super.roofStairBlock, 5);
                    this.setBlockAndMetadata(world, i3, 8, k8, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i3 + 1, 8, k8, super.roofStairBlock, 4);
                    this.setBlockAndMetadata(world, i3, 6, k8, super.roofWallBlock, super.roofWallMeta);
                    this.setBlockAndMetadata(world, i3, 7, k8, super.roofWallBlock, super.roofWallMeta);
                }
                if (i3 == -4 || i3 == 3) {
                    this.setBlockAndMetadata(world, i3, 8, k8, super.roofStairBlock, 5);
                    this.setBlockAndMetadata(world, i3 + 1, 8, k8, super.roofStairBlock, 4);
                }
            }
        }
        for (int k5 = -9; k5 <= 9; ++k5) {
            final int k6 = Math.abs(k5);
            if (k6 == 0 || k6 == 4 || k6 == 7) {
                this.setBlockAndMetadata(world, -5, 9, k5, super.roofSlabBlock, super.roofSlabMeta);
                this.setBlockAndMetadata(world, 5, 9, k5, super.roofSlabBlock, super.roofSlabMeta);
            }
            if (k6 == 2) {
                this.setBlockAndMetadata(world, -5, 8, k5 - 1, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, -5, 8, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, -5, 8, k5 + 1, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, 5, 8, k5 - 1, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, 5, 8, k5, super.roofStairBlock, 0);
                this.setBlockAndMetadata(world, 5, 8, k5 + 1, super.roofStairBlock, 7);
            }
            if (k5 == -9 || k5 == -6 || k5 == 5 || k5 == 8) {
                this.setBlockAndMetadata(world, -5, 8, k5, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, -5, 8, k5 + 1, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, 5, 8, k5, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, 5, 8, k5 + 1, super.roofStairBlock, 7);
            }
        }
        for (int k5 = -9; k5 <= 9; ++k5) {
            for (int step2 = 0; step2 <= 3; ++step2) {
                final int j4 = 9 + step2;
                this.setBlockAndMetadata(world, -4 + step2, j4, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 4 - step2, j4, k5, super.roofStairBlock, 0);
                if (step2 > 0) {
                    this.setBlockAndMetadata(world, -4 + step2, j4 - 1, k5, super.roofStairBlock, 4);
                    this.setBlockAndMetadata(world, 4 - step2, j4 - 1, k5, super.roofStairBlock, 5);
                }
            }
            this.setBlockAndMetadata(world, 0, 12, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 0, 13, k5, super.roofSlabBlock, super.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 12, -10, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 13, -10, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 12, 10, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 13, 10, super.roofStairBlock, 2);
        for (final int k2 : new int[] { -8, 8 }) {
            for (int step3 = 0; step3 <= 2; ++step3) {
                final int j5 = 9 + step3;
                for (int i7 = -3 + step3; i7 <= 3 - step3; ++i7) {
                    this.setBlockAndMetadata(world, i7, j5, k2, super.plankBlock, super.plankMeta);
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, -8, super.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 8, 8, super.plankStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -4, 3, -6, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 0, 3, -6, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 4, 3, -6, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, -6, 3, -2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 6, 3, -2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, -6, 3, 2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 6, 3, 2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, -4, 3, 6, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 4, 3, 6, LOTRMod.chandelier, 0);
        this.placeTable(world, random, -5, -4, 1, -7, -6);
        for (int i3 = -7; i3 <= -4; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -9, super.plankStairBlock, 3);
        }
        for (int k5 = -8; k5 <= -6; ++k5) {
            this.setBlockAndMetadata(world, -7, 1, k5, super.plankStairBlock, 0);
        }
        this.placeTable(world, random, 4, 5, 1, -7, -6);
        for (int i3 = 4; i3 <= 7; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -9, super.plankStairBlock, 3);
        }
        for (int k5 = -8; k5 <= -6; ++k5) {
            this.setBlockAndMetadata(world, 7, 1, k5, super.plankStairBlock, 1);
        }
        this.placeTable(world, random, -7, -6, 1, 0, 0);
        for (int i3 = -7; i3 <= -6; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -2, super.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 1, 2, super.plankStairBlock, 2);
        }
        this.placeTable(world, random, 4, 5, 1, -1, 1);
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, 7, 1, k5, super.plankStairBlock, 1);
        }
        this.placeTable(world, random, -7, -6, 1, 8, 9);
        for (int i3 = -7; i3 <= -6; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 6, super.plankStairBlock, 3);
        }
        for (int k5 = 8; k5 <= 9; ++k5) {
            this.setBlockAndMetadata(world, -4, 1, k5, super.plankStairBlock, 1);
        }
        this.placeTable(world, random, 6, 7, 1, 8, 9);
        for (int i3 = 6; i3 <= 7; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 6, super.plankStairBlock, 3);
        }
        for (int k5 = 8; k5 <= 9; ++k5) {
            this.setBlockAndMetadata(world, 4, 1, k5, super.plankStairBlock, 0);
        }
        for (int i3 = -3; i3 <= -1; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -2, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i3, 3, -2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i3, 3, 2, super.fenceBlock, super.fenceMeta);
        }
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, -4, 1, k5, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -4, 3, k5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 0, 1, k5, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 3, k5, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -3, 1, 2, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -2, 1, 2, super.fenceGateBlock, 0);
        this.setBlockAndMetadata(world, -1, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -4, 1, 1, (Block)Blocks.cauldron, 3);
        this.placeChest(world, random, -1, 0, -1, 3, LOTRChestContents.EASTERLING_HOUSE);
        this.placeBarrel(world, random, -3, 2, -2, 2, LOTRFoods.RHUN_DRINK);
        this.placeBarrel(world, random, 0, 2, -1, 4, LOTRFoods.RHUN_DRINK);
        for (int i3 = -4; i3 <= 0; ++i3) {
            for (int k3 = -2; k3 <= 2; ++k3) {
                if ((i3 == -4 && k3 >= -1 && k3 <= 0) || (k3 == -2 && i3 >= -2 && i3 <= -1) || (i3 == 0 && k3 >= 0 && k3 <= 1)) {
                    if (random.nextBoolean()) {
                        this.placeMug(world, random, i3, 2, k3, random.nextInt(4), LOTRFoods.RHUN_DRINK);
                    }
                    else {
                        this.placePlate(world, random, i3, 2, k3, super.plateBlock, LOTRFoods.RHUN);
                    }
                }
            }
        }
        for (int i3 = -3; i3 <= -1; ++i3) {
            for (int k3 = 8; k3 <= 10; ++k3) {
                for (int j4 = 0; j4 <= 4; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
            }
            for (int k3 = 8; k3 <= 9; ++k3) {
                for (int j4 = 5; j4 <= 8; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int j6 = 1; j6 <= 7; ++j6) {
            this.setAir(world, -2, j6, 9);
        }
        this.setBlockAndMetadata(world, -2, 0, 9, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, -2, 1, 9, (Block)Blocks.fire, 0);
        this.setBlockAndMetadata(world, -2, 1, 8, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -2, 2, 8, Blocks.furnace, 2);
        this.spawnItemFrame(world, -2, 3, 8, 2, this.getEasterlingFramedItem(random));
        this.setBlockAndMetadata(world, -2, 6, 8, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -2, 7, 8, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 8, 8, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 8, 8, super.brickStairBlock, 0);
        for (int j6 = 5; j6 <= 7; ++j6) {
            this.setBlockAndMetadata(world, -2, j6, 10, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 8, 10, super.brickStairBlock, 3);
        for (int j6 = 9; j6 <= 13; ++j6) {
            this.setBlockAndMetadata(world, -2, j6, 9, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 14, 9, Blocks.flower_pot, 0);
        for (int step = 0; step <= 3; ++step) {
            final int j3 = 1 + step;
            final int k7 = 4 + step;
            for (int i6 = 2; i6 <= 3; ++i6) {
                this.setAir(world, i6, 4, k7);
                this.setBlockAndMetadata(world, i6, j3, k7, super.plankStairBlock, 2);
                this.setBlockAndMetadata(world, i6, j3, k7 + 1, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i6, j3, k7 + 2, super.plankStairBlock, 7);
            }
        }
        for (int i3 = 1; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, 3, super.fenceBlock, super.fenceMeta);
        }
        for (int k5 = 4; k5 <= 6; ++k5) {
            this.setBlockAndMetadata(world, 1, 5, k5, super.fenceBlock, super.fenceMeta);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int j3 = 5; j3 <= 7; ++j3) {
                this.setBlockAndMetadata(world, i3, j3, -2, super.plankBlock, super.plankMeta);
            }
            this.setBlockAndMetadata(world, i3, 8, -2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (int k5 = -8; k5 <= 8; ++k5) {
            if (k5 <= -2) {
                for (int j3 = 5; j3 <= 7; ++j3) {
                    this.setBlockAndMetadata(world, 0, j3, k5, super.plankBlock, super.plankMeta);
                }
            }
            this.setBlockAndMetadata(world, 0, 8, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        for (int j6 = 5; j6 <= 7; ++j6) {
            this.setBlockAndMetadata(world, 0, j6, -2, super.woodBeamBlock, super.woodBeamMeta);
        }
        this.placeTable(world, random, -3, -2, 5, 4, 5);
        for (int i3 = -3; i3 <= -2; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, 2, super.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 5, 7, super.plankStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -3, 7, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 7, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 7, 8, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 5, -2, super.doorBlock, 3);
        this.setBlockAndMetadata(world, -2, 6, -2, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 2, 5, -2, super.doorBlock, 3);
        this.setBlockAndMetadata(world, 2, 6, -2, super.doorBlock, 8);
        this.setBlockAndMetadata(world, -3, 5, -3, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 5, -5, super.plankBlock, super.plankMeta);
        this.placePlateWithCertainty(world, random, -3, 6, -5, super.plateBlock, LOTRFoods.RHUN);
        this.setBlockAndMetadata(world, -3, 5, -6, (Block)Blocks.chest, 4);
        for (final int i6 : new int[] { -3, -1 }) {
            this.setBlockAndMetadata(world, i6, 5, -7, super.bedBlock, 2);
            this.setBlockAndMetadata(world, i6, 5, -8, super.bedBlock, 10);
        }
        this.spawnItemFrame(world, 0, 6, -5, 3, LOTRFoods.RHUN_DRINK.getRandomVessel(random).getEmptyVessel());
        this.setBlockAndMetadata(world, -3, 6, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 6, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 6, -8, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 6, -8, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 5, -3, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, 3, 5, -5, super.plankBlock, super.plankMeta);
        this.placePlateWithCertainty(world, random, 3, 6, -5, super.plateBlock, LOTRFoods.RHUN);
        this.setBlockAndMetadata(world, 3, 5, -6, (Block)Blocks.chest, 5);
        for (final int i6 : new int[] { 1, 3 }) {
            this.setBlockAndMetadata(world, i6, 5, -7, super.bedBlock, 2);
            this.setBlockAndMetadata(world, i6, 5, -8, super.bedBlock, 10);
        }
        this.spawnItemFrame(world, 0, 6, -5, 1, LOTRFoods.RHUN_DRINK.getRandomVessel(random).getEmptyVessel());
        this.setBlockAndMetadata(world, 3, 6, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 6, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 6, -8, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 6, -8, Blocks.torch, 3);
        final LOTREntityEasterling bartender = new LOTREntityEasterlingBartender(world);
        bartender.setSpecificLocationName(this.tavernNameNPC);
        this.spawnNPCAndSetHome(bartender, world, -2, 1, 0, 2);
        for (int men = 6 + random.nextInt(5), l = 0; l < men; ++l) {
            final LOTREntityEasterling easterling = new LOTREntityEasterling(world);
            this.spawnNPCAndSetHome(easterling, world, 2, 1, 0, 16);
        }
        return true;
    }
    
    private void placeTable(final World world, final Random random, final int i1, final int i2, final int j, final int k1, final int k2) {
        for (int l = i1; l <= i2; ++l) {
            for (int m = k1; m <= k2; ++m) {
                this.setBlockAndMetadata(world, l, j, m, super.plankBlock, super.plankMeta);
                if (random.nextInt(3) != 0) {
                    if (random.nextBoolean()) {
                        this.placeMug(world, random, l, j + 1, m, random.nextInt(4), LOTRFoods.RHUN_DRINK);
                    }
                    else {
                        this.placePlate(world, random, l, j + 1, m, super.plateBlock, LOTRFoods.RHUN);
                    }
                }
            }
        }
    }
}
