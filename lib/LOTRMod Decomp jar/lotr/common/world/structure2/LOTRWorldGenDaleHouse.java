// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDaleMan;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDaleHouse extends LOTRWorldGenDaleStructure
{
    public LOTRWorldGenDaleHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -8; i2 <= 3; ++i2) {
                for (int k2 = -1; k2 <= 9; ++k2) {
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
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -7; i3 <= 2; ++i3) {
            for (int k3 = 0; k3 <= 8; ++k3) {
                if (i3 >= -2 || k3 <= 4) {
                    for (int j3 = 1; j3 <= 10; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                    Block fillBlock = null;
                    int fillMeta = 0;
                    if ((i3 == -2 || i3 == 2) && (k3 == 0 || k3 == 4 || k3 == 8)) {
                        fillBlock = super.brickBlock;
                        fillMeta = super.brickMeta;
                        for (int j2 = 1; j2 <= 7; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    else if ((k3 == 0 || k3 == 4) && i3 >= -6 && i3 <= -4) {
                        fillBlock = super.brickBlock;
                        fillMeta = super.brickMeta;
                        for (int j2 = 1; j2 <= 4; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        }
                        for (int j2 = 5; j2 <= 6; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.plankBlock, super.plankMeta);
                        }
                        this.setBlockAndMetadata(world, i3, 7, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    else if (i3 == -7 && k3 >= 1 && k3 <= 3) {
                        fillBlock = super.brickBlock;
                        fillMeta = super.brickMeta;
                        for (int j2 = 1; j2 <= 4; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        }
                        for (int j2 = 5; j2 <= 6; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.plankBlock, super.plankMeta);
                        }
                        this.setBlockAndMetadata(world, i3, 7, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    }
                    else if ((k3 == 0 || k3 == 4) && (i3 == -7 || i3 == -3)) {
                        fillBlock = super.woodBlock;
                        fillMeta = super.woodMeta;
                        for (int j2 = 1; j2 <= 7; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.woodBlock, super.woodMeta);
                        }
                    }
                    else {
                        fillBlock = super.floorBlock;
                        fillMeta = super.floorMeta;
                    }
                    for (int j2 = 0; (j2 == 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, fillBlock, fillMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
            }
        }
        for (final int[] pos : new int[][] { { -3, -1 }, { -7, -1 }, { -8, 0 }, { -8, 4 }, { -7, 5 } }) {
            for (int i4 = pos[0], k4 = pos[1], j4 = 7; (j4 >= 4 || !this.isOpaque(world, i4, j4, k4)) && this.getY(j4) >= 0; --j4) {
                this.setBlockAndMetadata(world, i4, j4, k4, super.fenceBlock, super.fenceMeta);
            }
        }
        for (final int k2 : new int[] { 0, 4, 8 }) {
            this.setBlockAndMetadata(world, -1, 3, k2, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 3, k2, super.brickStairBlock, 5);
        }
        for (final int i5 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i5, 3, 1, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 3, 3, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i5, 3, 5, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 3, 7, super.brickStairBlock, 6);
        }
        for (int j5 = 1; j5 <= 2; ++j5) {
            this.setBlockAndMetadata(world, -2, j5, 1, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, -2, j5, 3, super.brickWallBlock, super.brickWallMeta);
        }
        for (int k5 = 1; k5 <= 3; ++k5) {
            for (int j6 = 1; j6 <= 3; ++j6) {
                this.setBlockAndMetadata(world, -3, j6, k5, super.brickBlock, super.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, -3, 1, 2, super.doorBlock, 0);
        this.setBlockAndMetadata(world, -3, 2, 2, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 1, 7, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 1, 1, 7, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 1, 2, 7, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 1, 1, 6, Blocks.hay_block, 0);
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -1, super.brickStairBlock, 6);
            if (IntMath.mod(i3, 2) == 1) {
                this.setBlockAndMetadata(world, i3, 5, -1, super.brickWallBlock, super.brickWallMeta);
            }
        }
        for (int k5 = -1; k5 <= 9; ++k5) {
            this.setBlockAndMetadata(world, 3, 4, k5, super.brickStairBlock, 4);
            if (IntMath.mod(k5, 2) == 1) {
                this.setBlockAndMetadata(world, 3, 5, k5, super.brickWallBlock, super.brickWallMeta);
            }
        }
        for (int i3 = 2; i3 >= -2; --i3) {
            this.setBlockAndMetadata(world, i3, 4, 9, super.brickStairBlock, 7);
            if (IntMath.mod(i3, 2) == 1) {
                this.setBlockAndMetadata(world, i3, 5, 9, super.brickWallBlock, super.brickWallMeta);
            }
        }
        for (int k5 = 9; k5 >= 5; --k5) {
            this.setBlockAndMetadata(world, -3, 4, k5, super.brickStairBlock, 5);
            if (IntMath.mod(k5, 2) == 1) {
                this.setBlockAndMetadata(world, -3, 5, k5, super.brickWallBlock, super.brickWallMeta);
            }
        }
        for (int i3 = -4; i3 >= -6; --i3) {
            this.setBlockAndMetadata(world, i3, 4, 5, super.brickStairBlock, 7);
        }
        this.setBlockAndMetadata(world, -7, 4, 5, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -8, 4, 5, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -8, 4, 4, super.brickBlock, super.brickMeta);
        for (int k5 = 3; k5 >= 1; --k5) {
            this.setBlockAndMetadata(world, -8, 4, k5, super.brickStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -8, 4, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -8, 4, -1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -7, 4, -1, super.brickBlock, super.brickMeta);
        for (int i3 = -6; i3 <= -4; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -1, super.brickStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -3, 4, -1, super.brickBlock, super.brickMeta);
        for (final int k2 : new int[] { 0, 4, 8 }) {
            for (int i4 = -1; i4 <= 1; ++i4) {
                this.setBlockAndMetadata(world, i4, 4, k2, super.brickBlock, super.brickMeta);
                if (k2 == 0 || k2 == 8) {
                    this.setBlockAndMetadata(world, i4, 5, k2, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i4, 6, k2, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i4, 7, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                }
            }
        }
        for (final int i5 : new int[] { -2, 2 }) {
            for (int k6 = 1; k6 <= 3; ++k6) {
                this.setBlockAndMetadata(world, i5, 4, k6, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i5, 5, k6, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i5, 6, k6, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i5, 7, k6, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
            for (int k6 = 5; k6 <= 7; ++k6) {
                this.setBlockAndMetadata(world, i5, 4, k6, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i5, 5, k6, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i5, 6, k6, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i5, 7, k6, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 1; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 4, k3, super.plankBlock, super.plankMeta);
            }
            for (int k3 = 5; k3 <= 7; ++k3) {
                this.setBlockAndMetadata(world, i3, 4, k3, super.plankBlock, super.plankMeta);
            }
        }
        this.setBlockAndMetadata(world, -5, 2, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 6, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 6, 2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 6, 6, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 6, 8, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 6, 6, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -5, 6, 4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -7, 6, 2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -5, 6, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 7, -1, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 2, 7, -1, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 7, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 7, 4, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 7, 8, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 7, 9, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -2, 7, 9, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 7, 8, super.brickStairBlock, 5);
        for (int i3 = -8; i3 <= 3; ++i3) {
            for (int k3 = -1; k3 <= 9; ++k3) {
                if (i3 >= -3 || k3 <= 5) {
                    this.setBlockAndMetadata(world, i3, 8, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -8, 8, -1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -8, 8, 5, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        for (int i3 = -8; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 9, -1, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 10, 0, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 11, 1, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 11, 2, super.roofBlock, super.roofMeta);
            if (i3 <= -1 || i3 >= 1) {
                this.setBlockAndMetadata(world, i3, 11, 3, super.roofStairBlock, 3);
            }
            if (i3 <= -2 || i3 >= 2) {
                this.setBlockAndMetadata(world, i3, 10, 4, super.roofStairBlock, 3);
            }
            if (i3 <= -3 || i3 >= 3) {
                this.setBlockAndMetadata(world, i3, 9, 5, super.roofStairBlock, 3);
            }
        }
        for (int k5 = 3; k5 <= 9; ++k5) {
            if (k5 >= 6) {
                this.setBlockAndMetadata(world, -3, 9, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 3, 9, k5, super.roofStairBlock, 0);
            }
            if (k5 >= 5) {
                this.setBlockAndMetadata(world, -2, 10, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 2, 10, k5, super.roofStairBlock, 0);
            }
            if (k5 >= 4) {
                this.setBlockAndMetadata(world, -1, 11, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 1, 11, k5, super.roofStairBlock, 0);
            }
            this.setBlockAndMetadata(world, 0, 11, k5, super.roofBlock, super.roofMeta);
        }
        for (final int i5 : new int[] { -7, 2 }) {
            for (int k6 = 0; k6 <= 4; ++k6) {
                this.setBlockAndMetadata(world, i5, 9, k6, super.brickBlock, super.brickMeta);
            }
            for (int k6 = 1; k6 <= 3; ++k6) {
                this.setBlockAndMetadata(world, i5, 10, k6, super.brickBlock, super.brickMeta);
            }
        }
        for (final int i5 : new int[] { -8, 3 }) {
            this.setBlockAndMetadata(world, i5, 9, 0, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 10, 1, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i5, 10, 3, super.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i5, 9, 4, super.roofStairBlock, 6);
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 9, 8, super.brickBlock, super.brickMeta);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 10, 8, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 9, 9, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 10, 9, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 9, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, 9, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 12, 2, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 13, 2, (Block)Blocks.fire, 0);
        this.setBlockAndMetadata(world, 0, 11, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -1, 12, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 12, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 1, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, -1, 13, 2, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 1, 13, 2, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 0, 13, 3, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 0, 14, 1, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 14, 2, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 14, 2, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 14, 3, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 14, 2, super.roofBlock, super.roofMeta);
        for (int j5 = 1; j5 <= 7; ++j5) {
            this.setBlockAndMetadata(world, -5, j5, 2, super.woodBeamBlock, super.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, -4, 3, 1, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -6, 7, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 7, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -5, 1, 1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -6, 1, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -6, 1, 2, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -6, 2, 2, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -6, 2, 3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -5, 2, 3, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -5, 3, 3, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 3, 3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 3, 2, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 4, 2, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 4, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -5, 4, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -5, 5, 1, super.fenceBlock, super.fenceMeta);
        for (int k5 = 1; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -3, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            this.setBlockAndMetadata(world, -2, 5, k5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -2, 6, k5, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 5, 2, super.doorBlock, 2);
        this.setBlockAndMetadata(world, -2, 6, 2, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 7, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 7, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 7, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 7, 5, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 5, 1, LOTRMod.strawBed, 1);
        this.setBlockAndMetadata(world, 1, 5, 1, LOTRMod.strawBed, 9);
        this.setBlockAndMetadata(world, 1, 5, 2, super.woodBeamBlock, super.woodBeamMeta);
        this.placeMug(world, random, 1, 6, 2, 1, LOTRFoods.DALE_DRINK);
        this.placeChest(world, random, 1, 5, 3, 5, LOTRChestContents.DALE_HOUSE);
        this.spawnItemFrame(world, 2, 7, 1, 3, new ItemStack(Items.clock));
        this.setBlockAndMetadata(world, 1, 5, 4, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 6, 4, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 7, 4, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 7, 4, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 7, 4, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 5, 5, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 1, 5, 6, Blocks.furnace, 5);
        this.setBlockAndMetadata(world, 1, 5, 7, super.brickBlock, super.brickMeta);
        this.placePlateWithCertainty(world, random, 1, 6, 7, super.plateBlock, LOTRFoods.DALE);
        this.setBlockAndMetadata(world, 0, 5, 7, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -1, 5, 7, LOTRMod.daleTable, 0);
        this.setBlockAndMetadata(world, -1, 7, 7, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 7, 7, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 7, 7, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 7, 6, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 7, 5, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 10, 2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 0, 10, 5, LOTRMod.chandelier, 0);
        if (random.nextInt(3) == 0) {
            final int i3 = MathHelper.getRandomIntegerInRange(random, -6, 1);
            final int k3 = MathHelper.getRandomIntegerInRange(random, 0, 4);
            final int chestDir = Direction.directionToFacing[random.nextInt(4)];
            this.placeChest(world, random, i3, 9, k3, chestDir, LOTRChestContents.DALE_HOUSE_TREASURE);
        }
        final LOTREntityDaleMan daleMan = new LOTREntityDaleMan(world);
        this.spawnNPCAndSetHome(daleMan, world, -1, 5, 2, 16);
        return true;
    }
}
