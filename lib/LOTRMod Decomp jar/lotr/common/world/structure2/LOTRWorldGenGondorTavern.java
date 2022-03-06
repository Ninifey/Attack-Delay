// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGondorBartender;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import java.util.Random;

public class LOTRWorldGenGondorTavern extends LOTRWorldGenGondorStructure
{
    private String[] tavernName;
    private String[] tavernNameSign;
    private String tavernNameNPC;
    
    public LOTRWorldGenGondorTavern(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.bedBlock = Blocks.bed;
        this.tavernName = LOTRNames.getGondorTavernName(random);
        this.tavernNameSign = new String[] { "", this.tavernName[0], this.tavernName[1], "" };
        this.tavernNameNPC = this.tavernName[0] + " " + this.tavernName[1];
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -9; i2 <= 13; ++i2) {
                for (int k2 = -2; k2 <= 16; ++k2) {
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
        if (super.restrictions) {
            final int oppHeight = this.getTopBlock(world, 0, 15) - 1;
            if (oppHeight > 0) {
                super.originY = this.getY(oppHeight);
            }
        }
        for (int i3 = -7; i3 <= 11; ++i3) {
            for (int k3 = 0; k3 <= 14; ++k3) {
                if (!((i3 == -7 || i3 == 11) & (k3 == 0 || k3 == 14))) {
                    boolean beam = false;
                    if (i3 == -7 || i3 == 11) {
                        beam = (IntMath.mod(k3, 4) == 1);
                    }
                    else if (k3 == 0 || k3 == 14) {
                        beam = (IntMath.mod(i3, 4) == 2);
                    }
                    if (beam) {
                        for (int j3 = 4; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                    }
                    else if (i3 == -7 || i3 == 11 || k3 == 0 || k3 == 14) {
                        for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.rockBlock, super.rockMeta);
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                        for (int j3 = 1; j3 <= 4; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.wallBlock, super.wallMeta);
                        }
                    }
                    else {
                        for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.plankBlock, super.plankMeta);
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                        for (int j3 = 1; j3 <= 4; ++j3) {
                            this.setAir(world, i3, j3, k3);
                        }
                    }
                }
            }
        }
        for (final int k2 : new int[] { 0, 14 }) {
            for (int i4 = -4; i4 <= 8; ++i4) {
                if (IntMath.mod(i4, 4) == 0 && i4 != 0) {
                    this.setBlockAndMetadata(world, i4, 2, k2, LOTRMod.glassPane, 0);
                    this.setBlockAndMetadata(world, i4, 3, k2, LOTRMod.glassPane, 0);
                }
            }
        }
        for (final int i5 : new int[] { -7, 11 }) {
            for (int k4 = 3; k4 <= 11; ++k4) {
                if (IntMath.mod(k4, 4) == 3 && (i5 != -7 || k4 != 7)) {
                    this.setBlockAndMetadata(world, i5, 2, k4, LOTRMod.glassPane, 0);
                    this.setBlockAndMetadata(world, i5, 3, k4, LOTRMod.glassPane, 0);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 1, 0, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, 0, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 0, 14, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 1, 14, super.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, 14, super.doorBlock, 8);
        for (final int k2 : new int[] { -1, 15 }) {
            int i4 = 0;
            final int doorHeight = this.getTopBlock(world, i4, k2) - 1;
            if (doorHeight < 0) {
                for (int j4 = 0; (j4 == 0 || !this.isOpaque(world, i4, j4, k2)) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i4, j4, k2, super.plankBlock, super.plankMeta);
                    this.setGrassToDirt(world, i4, j4 - 1, k2);
                }
                ++i4;
                for (int j4 = 0; !this.isOpaque(world, i4, j4, k2) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i4, j4, k2, super.plankStairBlock, 0);
                    this.setGrassToDirt(world, i4, j4 - 1, k2);
                    for (int j5 = j4 - 1; !this.isOpaque(world, i4, j5, k2) && this.getY(j5) >= 0; --j5) {
                        this.setBlockAndMetadata(world, i4, j5, k2, super.plankBlock, super.plankMeta);
                        this.setGrassToDirt(world, i4, j5 - 1, k2);
                    }
                    if (++i4 >= 15) {
                        break;
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -2, 3, -1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 4, -1, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 2, 3, -1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 4, -1, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 0, 4, -1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 4, -2, super.plankBlock, super.plankMeta);
        this.placeSign(world, -1, 4, -2, Blocks.wall_sign, 5, this.tavernNameSign);
        this.placeSign(world, 1, 4, -2, Blocks.wall_sign, 4, this.tavernNameSign);
        this.placeSign(world, 0, 4, -3, Blocks.wall_sign, 2, this.tavernNameSign);
        this.setBlockAndMetadata(world, -2, 3, 15, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 4, 15, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 2, 3, 15, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 4, 15, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 0, 4, 15, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 4, 16, super.plankBlock, super.plankMeta);
        this.placeSign(world, -1, 4, 16, Blocks.wall_sign, 5, this.tavernNameSign);
        this.placeSign(world, 1, 4, 16, Blocks.wall_sign, 4, this.tavernNameSign);
        this.placeSign(world, 0, 4, 17, Blocks.wall_sign, 3, this.tavernNameSign);
        for (int i3 = -8; i3 <= 12; ++i3) {
            for (int k3 = -1; k3 <= 15; ++k3) {
                if (!((i3 <= -7 || i3 >= 11) & (k3 <= 0 || k3 >= 14))) {
                    boolean beam = false;
                    if (i3 == -8 || i3 == 12) {
                        beam = (IntMath.mod(k3, 4) == 1);
                    }
                    else if (k3 == -1 || k3 == 15) {
                        beam = (IntMath.mod(i3, 4) == 2);
                    }
                    if (beam) {
                        if (i3 == -8 || i3 == 12) {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                        }
                        if (k3 == -1 || k3 == 15) {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                        }
                        for (int j3 = 6; j3 <= 8; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                    else if (i3 == -8 || i3 == 12 || k3 == -1 || k3 == 15) {
                        if (i3 == -8 || i3 == 12) {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                        }
                        if (k3 == -1 || k3 == 15) {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                        }
                        for (int j3 = 6; j3 <= 8; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.wallBlock, super.wallMeta);
                        }
                    }
                    else if (((i3 == -7 || i3 == 11) && (k3 == 1 || k3 == 13)) || ((i3 == -6 || i3 == 10) && (k3 == 0 || k3 == 14))) {
                        if (i3 == -7 || i3 == 11) {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                        }
                        if (k3 == 0 || k3 == 14) {
                            this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                        }
                        for (int j3 = 6; j3 <= 8; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.wallBlock, super.wallMeta);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.plankBlock, super.plankMeta);
                        for (int j3 = 6; j3 <= 11; ++j3) {
                            this.setAir(world, i3, j3, k3);
                        }
                    }
                }
            }
        }
        for (final int k2 : new int[] { -1, 15 }) {
            for (int i4 = -4; i4 <= 8; ++i4) {
                if (IntMath.mod(i4, 4) == 0) {
                    this.setBlockAndMetadata(world, i4, 7, k2, LOTRMod.glassPane, 0);
                }
            }
        }
        for (final int i5 : new int[] { -8, 12 }) {
            for (int k4 = 3; k4 <= 11; ++k4) {
                if (IntMath.mod(k4, 4) == 3) {
                    this.setBlockAndMetadata(world, i5, 7, k4, LOTRMod.glassPane, 0);
                }
            }
        }
        for (int step = 0; step <= 2; ++step) {
            for (int i6 = -9; i6 <= 13; ++i6) {
                if (i6 >= -7 + step && i6 <= 11 - step) {
                    this.setBlockAndMetadata(world, i6, 8 + step, -2 + step, super.roofStairBlock, 2);
                    this.setBlockAndMetadata(world, i6, 8 + step, 16 - step, super.roofStairBlock, 3);
                }
                if (i6 <= -7 + step || i6 >= 11 - step) {
                    this.setBlockAndMetadata(world, i6, 8 + step, 0 + step, super.roofStairBlock, 2);
                    this.setBlockAndMetadata(world, i6, 8 + step, 14 - step, super.roofStairBlock, 3);
                }
            }
            this.setBlockAndMetadata(world, -7 + step, 8 + step, -1 + step, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 11 - step, 8 + step, -1 + step, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, -7 + step, 8 + step, 15 - step, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 11 - step, 8 + step, 15 - step, super.roofStairBlock, 0);
        }
        for (int i3 = -9; i3 <= 13; ++i3) {
            this.setBlockAndMetadata(world, i3, 11, 4, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i3, 12, 5, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 12, 6, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i3, 12, 7, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 13, 7, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 12, 8, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i3, 12, 9, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 11, 10, super.roofBlock, super.roofMeta);
            if (i3 >= -3 && i3 <= 7) {
                this.setBlockAndMetadata(world, i3, 11, 1, super.roofSlabBlock, super.roofSlabMeta);
                this.setBlockAndMetadata(world, i3, 11, 2, super.roofBlock, super.roofMeta);
                this.setBlockAndMetadata(world, i3, 11, 3, super.roofBlock, super.roofMeta);
                this.setBlockAndMetadata(world, i3, 11, 11, super.roofBlock, super.roofMeta);
                this.setBlockAndMetadata(world, i3, 11, 12, super.roofBlock, super.roofMeta);
                this.setBlockAndMetadata(world, i3, 11, 13, super.roofSlabBlock, super.roofSlabMeta);
            }
            else {
                this.setBlockAndMetadata(world, i3, 11, 3, super.roofSlabBlock, super.roofSlabMeta);
                this.setBlockAndMetadata(world, i3, 11, 11, super.roofSlabBlock, super.roofSlabMeta);
            }
            if (i3 == -4 || i3 == 8) {
                this.setBlockAndMetadata(world, i3, 11, 1, super.roofSlabBlock, super.roofSlabMeta);
                this.setBlockAndMetadata(world, i3, 11, 2, super.roofSlabBlock, super.roofSlabMeta);
                this.setBlockAndMetadata(world, i3, 11, 12, super.roofSlabBlock, super.roofSlabMeta);
                this.setBlockAndMetadata(world, i3, 11, 13, super.roofSlabBlock, super.roofSlabMeta);
            }
            if (i3 == -9 || i3 == 13) {
                this.setBlockAndMetadata(world, i3, 8, 1, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i3, 9, 2, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i3, 10, 3, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i3, 11, 5, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                this.setBlockAndMetadata(world, i3, 11, 9, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                this.setBlockAndMetadata(world, i3, 10, 11, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, i3, 9, 12, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, i3, 8, 13, super.roofStairBlock, 6);
            }
        }
        for (final int i5 : new int[] { -8, 12 }) {
            for (int k4 = 2; k4 <= 12; ++k4) {
                this.setBlockAndMetadata(world, i5, 9, k4, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
            for (int k4 = 3; k4 <= 11; ++k4) {
                this.setBlockAndMetadata(world, i5, 10, k4, super.wallBlock, super.wallMeta);
            }
            for (int k4 = 5; k4 <= 9; ++k4) {
                this.setBlockAndMetadata(world, i5, 11, k4, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = 3; i3 <= 5; ++i3) {
            for (int k3 = 6; k3 <= 8; ++k3) {
                for (int j6 = 0; j6 <= 13; ++j6) {
                    if (i3 == 4 && k3 == 7) {
                        this.setAir(world, i3, j6, k3);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, j6, k3, super.brickBlock, super.brickMeta);
                    }
                }
            }
            this.setBlockAndMetadata(world, i3, 14, 6, super.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 14, 8, super.brickStairBlock, 3);
        }
        this.setBlockAndMetadata(world, 3, 14, 7, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 5, 14, 7, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 15, 7, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 4, 16, 7, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 4, 17, 7, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 4, 18, 7, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 4, 0, 7, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 4, 1, 7, (Block)Blocks.fire, 0);
        this.setBlockAndMetadata(world, 4, 1, 6, Blocks.iron_bars, 0);
        this.setBlockAndMetadata(world, 4, 1, 8, Blocks.iron_bars, 0);
        this.setBlockAndMetadata(world, 3, 1, 7, Blocks.iron_bars, 0);
        this.setBlockAndMetadata(world, 5, 1, 7, Blocks.iron_bars, 0);
        this.setBlockAndMetadata(world, 4, 2, 6, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 4, 2, 8, Blocks.furnace, 3);
        this.setBlockAndMetadata(world, 3, 2, 7, Blocks.furnace, 5);
        this.setBlockAndMetadata(world, 5, 2, 7, Blocks.furnace, 4);
        this.setBlockAndMetadata(world, 0, 4, 3, LOTRMod.chandelier, 1);
        this.setBlockAndMetadata(world, 0, 4, 11, LOTRMod.chandelier, 1);
        this.setBlockAndMetadata(world, 8, 4, 3, LOTRMod.chandelier, 1);
        this.setBlockAndMetadata(world, 8, 4, 11, LOTRMod.chandelier, 1);
        for (final int k2 : new int[] { 1, 2 }) {
            this.setBlockAndMetadata(world, -4, 1, k2, super.plankBlock, super.plankMeta);
            this.placeMugOrPlate(world, random, -4, 2, k2);
            this.setBlockAndMetadata(world, -6, 1, k2, super.plankStairBlock, 0);
            this.setBlockAndMetadata(world, -2, 1, k2, super.plankStairBlock, 1);
        }
        for (final int k2 : new int[] { 1, 2, 12, 13 }) {
            this.setBlockAndMetadata(world, 2, 1, k2, super.plankBlock, super.plankMeta);
            this.placeMugOrPlate(world, random, 2, 2, k2);
            this.setBlockAndMetadata(world, 3, 1, k2, super.plankBlock, super.plankMeta);
            this.placeMugOrPlate(world, random, 3, 2, k2);
            this.setBlockAndMetadata(world, 5, 1, k2, super.plankStairBlock, 1);
        }
        for (int k5 = 6; k5 <= 8; ++k5) {
            this.setBlockAndMetadata(world, 8, 1, k5, super.plankBlock, super.plankMeta);
            this.placeMugOrPlate(world, random, 8, 2, k5);
            this.setBlockAndMetadata(world, 10, 1, k5, super.plankStairBlock, 1);
        }
        for (int i3 = 7; i3 <= 10; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 1, super.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 1, 13, super.plankStairBlock, 2);
        }
        for (int k5 = 2; k5 <= 4; ++k5) {
            this.setBlockAndMetadata(world, 10, 1, k5, super.plankStairBlock, 1);
        }
        for (int k5 = 10; k5 <= 12; ++k5) {
            this.setBlockAndMetadata(world, 10, 1, k5, super.plankStairBlock, 1);
        }
        for (int i3 = 7; i3 <= 8; ++i3) {
            for (final int k4 : new int[] { 3, 4, 10, 11 }) {
                this.setBlockAndMetadata(world, i3, 1, k4, super.plankBlock, super.plankMeta);
                this.placeMugOrPlate(world, random, i3, 2, k4);
            }
        }
        for (int j7 = 1; j7 <= 4; ++j7) {
            this.setBlockAndMetadata(world, -2, j7, 5, super.woodBeamBlock, super.woodBeamMeta);
            this.setBlockAndMetadata(world, -2, j7, 9, super.woodBeamBlock, super.woodBeamMeta);
        }
        for (int i3 = -6; i3 <= -3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 5, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, i3, 3, 5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i3, 4, 5, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 1, 9, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, i3, 3, 9, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i3, 4, 9, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (int k5 = 6; k5 <= 8; ++k5) {
            this.setBlockAndMetadata(world, -2, 1, k5, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -2, 3, k5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, -2, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        this.setBlockAndMetadata(world, -4, 1, 5, super.fenceGateBlock, 0);
        this.placeBarrel(world, random, -6, 2, 5, 3, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -5, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -3, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, -4, 1, 9, super.fenceGateBlock, 2);
        this.placeBarrel(world, random, -6, 2, 9, 2, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -5, 2, 9, 0, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -3, 2, 9, 0, LOTRFoods.GONDOR_DRINK);
        this.placeBarrel(world, random, -2, 2, 8, 5, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -2, 2, 6, 1, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, -6, 1, 6, super.plankStairBlock, 4);
        this.placePlateWithCertainty(world, random, -6, 2, 6, super.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, -6, 1, 7, Blocks.furnace, 4);
        this.setBlockAndMetadata(world, -6, 1, 8, (Block)Blocks.cauldron, 3);
        this.placeChest(world, random, -3, 0, 8, 5, LOTRChestContents.GONDOR_HOUSE);
        for (int k5 = 6; k5 <= 8; ++k5) {
            this.setBlockAndMetadata(world, -6, 3, k5, super.plankStairBlock, 4);
            this.placeBarrel(world, random, -6, 4, k5, 4, LOTRFoods.GONDOR_DRINK);
        }
        this.setBlockAndMetadata(world, -4, 4, 7, LOTRMod.chandelier, 1);
        for (int step = 0; step <= 2; ++step) {
            this.setBlockAndMetadata(world, -3 - step, 1 + step, 13, super.plankStairBlock, 0);
            this.setBlockAndMetadata(world, -4 - step, 1 + step, 13, super.plankStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -6, 3, 13, super.plankBlock, super.plankMeta);
        for (int step = 0; step <= 1; ++step) {
            this.setBlockAndMetadata(world, -6, 4 + step, 12 - step, super.plankStairBlock, 3);
            this.setBlockAndMetadata(world, -6, 3 + step, 12 - step, super.plankStairBlock, 6);
        }
        for (int i3 = -6; i3 <= -4; ++i3) {
            this.setAir(world, i3, 5, 13);
        }
        this.setAir(world, -6, 5, 12);
        for (int i3 = -5; i3 <= -3; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, 14, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i3, 6, 12, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -3, 6, 13, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -7, 6, 12, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -7, 6, 11, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -5, 6, 11, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -5, 7, 12, Blocks.torch, 5);
        for (int i3 = -7; i3 <= -3; ++i3) {
            for (int k3 = 10; k3 <= 14; ++k3) {
                if (i3 != -3 || k3 != 10) {
                    if ((i3 >= -4 || k3 <= 11) && k3 <= 13) {
                        this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
                    }
                    if (i3 >= -5 || k3 <= 12) {
                        this.setBlockAndMetadata(world, i3, 9, k3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 4, 7, 6, Blocks.iron_bars, 0);
        this.setBlockAndMetadata(world, 4, 7, 8, Blocks.iron_bars, 0);
        this.setBlockAndMetadata(world, 3, 7, 7, Blocks.iron_bars, 0);
        this.setBlockAndMetadata(world, 5, 7, 7, Blocks.iron_bars, 0);
        this.spawnItemFrame(world, 3, 10, 7, 3, this.getGondorFramedItem(random));
        for (int i3 = -2; i3 <= 1; ++i3) {
            for (int k3 = 5; k3 <= 9; ++k3) {
                this.setBlockAndMetadata(world, i3, 6, k3, Blocks.carpet, 12);
            }
        }
        for (int i3 = -2; i3 <= 6; ++i3) {
            final int i7 = IntMath.mod(i3, 4);
            if (i7 == 2) {
                for (int j6 = 6; j6 <= 8; ++j6) {
                    this.setBlockAndMetadata(world, i3, j6, 3, super.woodBeamBlock, super.woodBeamMeta);
                    for (int k2 = 0; k2 <= 2; ++k2) {
                        this.setBlockAndMetadata(world, i3, j6, k2, super.wallBlock, super.wallMeta);
                    }
                    this.setBlockAndMetadata(world, i3, j6, 11, super.woodBeamBlock, super.woodBeamMeta);
                    for (int k2 = 12; k2 <= 14; ++k2) {
                        this.setBlockAndMetadata(world, i3, j6, k2, super.wallBlock, super.wallMeta);
                    }
                }
                for (int k6 = 0; k6 <= 3; ++k6) {
                    this.setBlockAndMetadata(world, i3, 9, k6, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                }
                for (int k6 = 11; k6 <= 14; ++k6) {
                    this.setBlockAndMetadata(world, i3, 9, k6, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                }
            }
            else {
                for (int j6 = 6; j6 <= 8; ++j6) {
                    this.setBlockAndMetadata(world, i3, j6, 3, super.wallBlock, super.wallMeta);
                    this.setBlockAndMetadata(world, i3, j6, 11, super.wallBlock, super.wallMeta);
                }
                this.setBlockAndMetadata(world, i3, 9, 3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                this.setBlockAndMetadata(world, i3, 9, 11, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                for (int k6 = 0; k6 <= 2; ++k6) {
                    this.setBlockAndMetadata(world, i3, 9, k6, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                }
                for (int k6 = 12; k6 <= 14; ++k6) {
                    this.setBlockAndMetadata(world, i3, 9, k6, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                }
            }
            if (i7 == 0) {
                this.setBlockAndMetadata(world, i3, 6, 3, super.doorBlock, 3);
                this.setBlockAndMetadata(world, i3, 7, 3, super.doorBlock, 8);
                this.setBlockAndMetadata(world, i3, 8, 2, Blocks.torch, 4);
                this.setBlockAndMetadata(world, i3, 6, 11, super.doorBlock, 1);
                this.setBlockAndMetadata(world, i3, 7, 11, super.doorBlock, 8);
                this.setBlockAndMetadata(world, i3, 8, 12, Blocks.torch, 3);
            }
            if (i7 == 3) {
                this.setBlockAndMetadata(world, i3, 6, 1, super.bedBlock, 0);
                this.setBlockAndMetadata(world, i3, 6, 2, super.bedBlock, 8);
                this.setBlockAndMetadata(world, i3, 6, 0, (Block)Blocks.chest, 4);
                this.setBlockAndMetadata(world, i3, 6, 13, super.bedBlock, 2);
                this.setBlockAndMetadata(world, i3, 6, 12, super.bedBlock, 10);
                this.setBlockAndMetadata(world, i3, 6, 14, (Block)Blocks.chest, 4);
            }
            if (i7 == 1) {
                this.setBlockAndMetadata(world, i3, 6, 2, super.plankStairBlock, 2);
                this.setBlockAndMetadata(world, i3, 6, 0, super.plankBlock, super.plankMeta);
                this.placeMug(world, random, i3, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
                this.setBlockAndMetadata(world, i3, 6, 12, super.plankStairBlock, 3);
                this.setBlockAndMetadata(world, i3, 6, 14, super.plankBlock, super.plankMeta);
                this.placeMug(world, random, i3, 7, 14, 0, LOTRFoods.GONDOR_DRINK);
            }
            for (int k6 = 1; k6 <= 3; ++k6) {
                this.setBlockAndMetadata(world, i3, 10, k6, super.wallBlock, super.wallMeta);
            }
            for (int k6 = 11; k6 <= 13; ++k6) {
                this.setBlockAndMetadata(world, i3, 10, k6, super.wallBlock, super.wallMeta);
            }
        }
        for (int k5 = 5; k5 <= 9; ++k5) {
            final int k7 = IntMath.mod(k5, 4);
            if (k7 == 1) {
                for (int j6 = 6; j6 <= 8; ++j6) {
                    this.setBlockAndMetadata(world, -4, j6, k5, super.woodBeamBlock, super.woodBeamMeta);
                    for (int i5 = -7; i5 <= -5; ++i5) {
                        this.setBlockAndMetadata(world, i5, j6, k5, super.wallBlock, super.wallMeta);
                    }
                    this.setBlockAndMetadata(world, 8, j6, k5, super.woodBeamBlock, super.woodBeamMeta);
                    for (int i5 = 9; i5 <= 11; ++i5) {
                        this.setBlockAndMetadata(world, i5, j6, k5, super.wallBlock, super.wallMeta);
                    }
                }
                for (int i2 = -7; i2 <= -4; ++i2) {
                    this.setBlockAndMetadata(world, i2, 9, k5, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                }
                for (int i2 = 8; i2 <= 11; ++i2) {
                    this.setBlockAndMetadata(world, i2, 9, k5, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                }
            }
            else {
                for (int j6 = 6; j6 <= 8; ++j6) {
                    this.setBlockAndMetadata(world, -4, j6, k5, super.wallBlock, super.wallMeta);
                    this.setBlockAndMetadata(world, 8, j6, k5, super.wallBlock, super.wallMeta);
                }
                this.setBlockAndMetadata(world, -4, 9, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                this.setBlockAndMetadata(world, 8, 9, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                for (int i2 = -7; i2 <= -5; ++i2) {
                    this.setBlockAndMetadata(world, i2, 9, k5, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                }
                for (int i2 = 9; i2 <= 11; ++i2) {
                    this.setBlockAndMetadata(world, i2, 9, k5, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                }
            }
            if (k7 == 3) {
                this.setBlockAndMetadata(world, -4, 6, k5, super.doorBlock, 0);
                this.setBlockAndMetadata(world, -4, 7, k5, super.doorBlock, 8);
                this.setBlockAndMetadata(world, -5, 8, k5, Blocks.torch, 1);
                this.setBlockAndMetadata(world, 8, 6, k5, super.doorBlock, 2);
                this.setBlockAndMetadata(world, 8, 7, k5, super.doorBlock, 8);
                this.setBlockAndMetadata(world, 9, 8, k5, Blocks.torch, 2);
            }
            if (k7 == 0) {
                this.setBlockAndMetadata(world, -6, 6, k5, super.bedBlock, 1);
                this.setBlockAndMetadata(world, -5, 6, k5, super.bedBlock, 9);
                this.setBlockAndMetadata(world, -7, 6, k5, (Block)Blocks.chest, 2);
                this.setBlockAndMetadata(world, 10, 6, k5, super.bedBlock, 3);
                this.setBlockAndMetadata(world, 9, 6, k5, super.bedBlock, 11);
                this.setBlockAndMetadata(world, 11, 6, k5, (Block)Blocks.chest, 2);
            }
            if (k7 == 2) {
                this.setBlockAndMetadata(world, -5, 6, k5, super.plankStairBlock, 1);
                this.setBlockAndMetadata(world, -7, 6, k5, super.plankBlock, super.plankMeta);
                this.placeMug(world, random, -7, 7, k5, 3, LOTRFoods.GONDOR_DRINK);
                this.setBlockAndMetadata(world, 9, 6, k5, super.plankStairBlock, 0);
                this.setBlockAndMetadata(world, 11, 6, k5, super.plankBlock, super.plankMeta);
                this.placeMug(world, random, 11, 7, k5, 1, LOTRFoods.GONDOR_DRINK);
            }
            for (int i2 = -7; i2 <= -4; ++i2) {
                this.setBlockAndMetadata(world, i2, 10, k5, super.wallBlock, super.wallMeta);
                this.setBlockAndMetadata(world, i2, 11, k5, super.wallBlock, super.wallMeta);
            }
            for (int i2 = 8; i2 <= 11; ++i2) {
                this.setBlockAndMetadata(world, i2, 10, k5, super.wallBlock, super.wallMeta);
                this.setBlockAndMetadata(world, i2, 11, k5, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = 7; i3 <= 8; ++i3) {
            for (int k3 = 10; k3 <= 11; ++k3) {
                if (i3 != 7 || k3 != 10) {
                    for (int j6 = 6; j6 <= 8; ++j6) {
                        this.setBlockAndMetadata(world, i3, j6, k3, super.wallBlock, super.wallMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 9, k3, super.woodBeamBlock, super.woodBeamMeta | ((i3 == 8) ? 8 : 4));
                    this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 8, 6, 10, super.doorBlock, 2);
        this.setBlockAndMetadata(world, 8, 7, 10, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 9, 8, 10, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 7, 8, 13, Blocks.torch, 2);
        for (int i3 = 7; i3 <= 8; ++i3) {
            for (int k3 = 12; k3 <= 13; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = 9; i3 <= 11; ++i3) {
            for (int k3 = 10; k3 <= 11; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = 7; i3 <= 9; ++i3) {
            for (int k3 = 12; k3 <= 14; ++k3) {
                this.setBlockAndMetadata(world, i3, 9, k3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
        }
        for (int i3 = 9; i3 <= 11; ++i3) {
            for (int k3 = 10; k3 <= 12; ++k3) {
                this.setBlockAndMetadata(world, i3, 9, k3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
        }
        this.setBlockAndMetadata(world, 11, 6, 11, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 11, 6, 12, super.bedBlock, 8);
        this.setBlockAndMetadata(world, 11, 6, 10, (Block)Blocks.chest, 5);
        this.setBlockAndMetadata(world, 7, 6, 13, super.bedBlock, 2);
        this.setBlockAndMetadata(world, 7, 6, 12, super.bedBlock, 10);
        this.setBlockAndMetadata(world, 7, 6, 14, (Block)Blocks.chest, 4);
        this.setBlockAndMetadata(world, 9, 6, 14, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 9, 7, 14, 0, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, 10, 6, 13, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 10, 7, 13, 1, LOTRFoods.GONDOR_DRINK);
        for (int i3 = 7; i3 <= 8; ++i3) {
            for (int k3 = 3; k3 <= 4; ++k3) {
                if (i3 != 7 || k3 != 4) {
                    for (int j6 = 6; j6 <= 8; ++j6) {
                        this.setBlockAndMetadata(world, i3, j6, k3, super.wallBlock, super.wallMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 9, k3, super.woodBeamBlock, super.woodBeamMeta | ((i3 == 8) ? 8 : 4));
                    this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 8, 6, 4, super.doorBlock, 2);
        this.setBlockAndMetadata(world, 8, 7, 4, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 9, 8, 4, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 7, 8, 1, Blocks.torch, 2);
        for (int i3 = 7; i3 <= 8; ++i3) {
            for (int k3 = 1; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = 9; i3 <= 11; ++i3) {
            for (int k3 = 3; k3 <= 4; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = 7; i3 <= 9; ++i3) {
            for (int k3 = 0; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, i3, 9, k3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
        }
        for (int i3 = 9; i3 <= 11; ++i3) {
            for (int k3 = 2; k3 <= 4; ++k3) {
                this.setBlockAndMetadata(world, i3, 9, k3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
        }
        this.setBlockAndMetadata(world, 11, 6, 3, super.bedBlock, 2);
        this.setBlockAndMetadata(world, 11, 6, 2, super.bedBlock, 10);
        this.setBlockAndMetadata(world, 11, 6, 4, (Block)Blocks.chest, 5);
        this.setBlockAndMetadata(world, 7, 6, 1, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 7, 6, 2, super.bedBlock, 8);
        this.setBlockAndMetadata(world, 7, 6, 0, (Block)Blocks.chest, 4);
        this.setBlockAndMetadata(world, 9, 6, 0, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 9, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, 10, 6, 1, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 10, 7, 1, 1, LOTRFoods.GONDOR_DRINK);
        for (int i3 = -4; i3 <= -3; ++i3) {
            for (int k3 = 3; k3 <= 4; ++k3) {
                if (i3 != -3 || k3 != 4) {
                    for (int j6 = 6; j6 <= 8; ++j6) {
                        this.setBlockAndMetadata(world, i3, j6, k3, super.wallBlock, super.wallMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 9, k3, super.woodBeamBlock, super.woodBeamMeta | ((i3 == -4) ? 8 : 4));
                    this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -4, 6, 4, super.doorBlock, 0);
        this.setBlockAndMetadata(world, -4, 7, 4, super.doorBlock, 8);
        this.setBlockAndMetadata(world, -5, 8, 4, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -3, 8, 1, Blocks.torch, 1);
        for (int i3 = -4; i3 <= -3; ++i3) {
            for (int k3 = 1; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = -7; i3 <= -5; ++i3) {
            for (int k3 = 3; k3 <= 4; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = -5; i3 <= -3; ++i3) {
            for (int k3 = 0; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, i3, 9, k3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
        }
        for (int i3 = -7; i3 <= -5; ++i3) {
            for (int k3 = 2; k3 <= 4; ++k3) {
                this.setBlockAndMetadata(world, i3, 9, k3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
        }
        this.setBlockAndMetadata(world, -7, 6, 3, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -7, 6, 2, super.bedBlock, 10);
        this.setBlockAndMetadata(world, -7, 6, 4, (Block)Blocks.chest, 4);
        this.setBlockAndMetadata(world, -3, 6, 1, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 6, 2, super.bedBlock, 8);
        this.setBlockAndMetadata(world, -3, 6, 0, (Block)Blocks.chest, 5);
        this.setBlockAndMetadata(world, -5, 6, 0, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, -5, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, -6, 6, 1, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, -6, 7, 1, 3, LOTRFoods.GONDOR_DRINK);
        for (int i3 = -3; i3 <= 7; ++i3) {
            for (final int k4 : new int[] { 5, 9 }) {
                this.setBlockAndMetadata(world, i3, 11, k4, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
        }
        this.setBlockAndMetadata(world, -1, 11, 7, LOTRMod.chandelier, 1);
        this.setBlockAndMetadata(world, 7, 11, 7, LOTRMod.chandelier, 1);
        final LOTREntityGondorMan bartender = new LOTREntityGondorBartender(world);
        bartender.setSpecificLocationName(this.tavernNameNPC);
        this.spawnNPCAndSetHome(bartender, world, -4, 1, 7, 2);
        for (int men = 6 + random.nextInt(7), l = 0; l < men; ++l) {
            final LOTREntityGondorMan gondorian = new LOTREntityGondorMan(world);
            this.spawnNPCAndSetHome(gondorian, world, 2, 1, 7, 16);
        }
        return true;
    }
    
    private void placeMugOrPlate(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextBoolean()) {
            this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.GONDOR_DRINK);
        }
        else {
            this.placePlate(world, random, i, j, k, super.plateBlock, LOTRFoods.GONDOR);
        }
    }
}
