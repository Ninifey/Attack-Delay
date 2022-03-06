// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTownHouse extends LOTRWorldGenEasterlingStructureTown
{
    public LOTRWorldGenEasterlingTownHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -8; k2 <= 8; ++k2) {
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
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 == 4 && (k4 == 2 || k4 == 6)) || (i4 == 0 && k3 == 6)) {
                    for (int j2 = 4; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if (i4 == 4 || k4 == 6) {
                    for (int j2 = 3; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (k4 == 6) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    else if (i4 == 4) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    }
                }
                else {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        if (IntMath.mod(i3, 2) == 1 && IntMath.mod(k3, 2) == 1) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.pillarRedBlock, super.pillarRedMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.brickRedBlock, super.brickRedMeta);
                        }
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        for (final int k2 : new int[] { -4, 4 }) {
            for (final int i5 : new int[] { -4, 4 }) {
                this.setBlockAndMetadata(world, i5, 2, k2 - 1, super.brickStairBlock, 7);
                this.setAir(world, i5, 2, k2);
                this.setBlockAndMetadata(world, i5, 2, k2 + 1, super.brickStairBlock, 6);
            }
            this.setBlockAndMetadata(world, -4, 3, k2, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 3, k2, super.brickStairBlock, 4);
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6, 2, -6, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i6, 3, -6, super.brickStairBlock, 6);
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6 - 1, 2, 6, super.brickStairBlock, 4);
            this.setAir(world, i6, 2, 6);
            this.setBlockAndMetadata(world, i6 + 1, 2, 6, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i6, 3, 6, super.brickStairBlock, 7);
        }
        for (final int k2 : new int[] { -7, 7 }) {
            this.setBlockAndMetadata(world, -4, 3, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 4, 3, k2, super.fenceBlock, super.fenceMeta);
        }
        for (final int i6 : new int[] { -5, 5 }) {
            this.setBlockAndMetadata(world, i6, 3, -6, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i6, 3, 6, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -1, 3, -7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 3, -7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 3, 7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 3, 7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -5, 3, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -5, 3, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 5, 3, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 5, 3, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 0, -6, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        this.setBlockAndMetadata(world, 0, 1, -6, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -6, super.doorBlock, 8);
        for (int k5 = -5; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, 0, 0, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        for (final int k2 : new int[] { -2, 2 }) {
            for (int i7 = -3; i7 <= 3; ++i7) {
                this.setBlockAndMetadata(world, i7, 0, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        for (int k5 = -6; k5 <= 6; ++k5) {
            for (int l = 0; l <= 3; ++l) {
                final int j3 = 5 + l;
                this.setBlockAndMetadata(world, -4 + l, j3, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 4 - l, j3, k5, super.roofStairBlock, 0);
                if (l > 0) {
                    this.setBlockAndMetadata(world, -4 + l, j3 - 1, k5, super.roofStairBlock, 4);
                    this.setBlockAndMetadata(world, 4 - l, j3 - 1, k5, super.roofStairBlock, 5);
                }
            }
            this.setBlockAndMetadata(world, 0, 8, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 0, 9, k5, super.roofSlabBlock, super.roofSlabMeta);
        }
        for (final int k2 : new int[] { -7, 7 }) {
            for (int m = 0; m <= 2; ++m) {
                final int j4 = 5 + m;
                this.setBlockAndMetadata(world, -3 + m, j4, k2, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 3 - m, j4, k2, super.roofStairBlock, 0);
                if (m > 0) {
                    this.setBlockAndMetadata(world, -3 + m, j4 - 1, k2, super.roofStairBlock, 4);
                    this.setBlockAndMetadata(world, 3 - m, j4 - 1, k2, super.roofStairBlock, 5);
                }
            }
            this.setBlockAndMetadata(world, 0, 7, k2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 0, 8, k2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 0, 9, k2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, -4, 4, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, -3, 4, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -2, 4, k2, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            this.setBlockAndMetadata(world, -1, 4, k2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 0, 4, k2, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 1, 4, k2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 2, 4, k2, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 3, 4, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 4, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 5, k2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 0, 5, k2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 1, 5, k2, super.roofSlabBlock, super.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 8, -8, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 9, -8, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 8, 8, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 9, 8, super.roofStairBlock, 2);
        for (final int k2 : new int[] { -6, 6 }) {
            for (int m = 0; m <= 2; ++m) {
                final int j4 = 5 + m;
                this.setBlockAndMetadata(world, -3 + m, j4, k2, super.roofBlock, super.roofMeta);
                this.setBlockAndMetadata(world, 3 - m, j4, k2, super.roofBlock, super.roofMeta);
                for (int i8 = -2 + m; i8 <= 2 - m; ++i8) {
                    this.setBlockAndMetadata(world, i8, j4, k2, super.plankBlock, super.plankMeta);
                }
            }
        }
        for (final int i6 : new int[] { -5, 5 }) {
            this.setBlockAndMetadata(world, i6, 5, -7, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i6, 4, -6, super.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 4, -5, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 5, -4, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i6, 4, -3, super.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 4, -1, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 4, 1, super.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 4, 3, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 5, 4, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i6, 4, 5, super.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 4, 6, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 5, 7, super.roofSlabBlock, super.roofSlabMeta);
        }
        for (final int k2 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, -5, 4, k2, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 5, 4, k2, super.roofStairBlock, 0);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -2; k3 <= 5; ++k3) {
                this.setBlockAndMetadata(world, i3, 4, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                if (Math.abs(i3) <= 2 && random.nextInt(3) == 0) {
                    this.setBlockAndMetadata(world, i3, 5, k3, LOTRMod.thatchFloor, 0);
                }
            }
        }
        for (final int k2 : new int[] { -2, 2 }) {
            for (int i7 = -3; i7 <= 3; ++i7) {
                this.setBlockAndMetadata(world, i7, 4, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        for (int k5 = -5; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, 0, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        for (int j5 = 1; j5 <= 5; ++j5) {
            this.setBlockAndMetadata(world, -3, j5, 0, super.woodBeamBlock, super.woodBeamMeta);
        }
        for (int j5 = 1; j5 <= 6; ++j5) {
            this.setBlockAndMetadata(world, -2, j5, 0, super.woodBeamBlock, super.woodBeamMeta);
            this.setBlockAndMetadata(world, -1, j5, 0, Blocks.ladder, 4);
        }
        for (int i3 = 2; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -1, super.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 2, -1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i3, 3, -1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i3, 1, 1, super.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 2, 1, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 3, 1, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 3, 0, super.brickBlock, super.brickMeta);
            for (int k3 = -1; k3 <= 1; ++k3) {
                this.setBlockAndMetadata(world, i3, 4, k3, super.brickBlock, super.brickMeta);
            }
        }
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, 2, 5, k5, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 6, k5, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 5, k5, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 3, 6, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 3, 7, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 3, 8, 0, Blocks.flower_pot, 0);
        this.setBlockAndMetadata(world, 2, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 2, 1, 0, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 2, 2, 0, Blocks.furnace, 5);
        this.setBlockAndMetadata(world, 3, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 3, 1, 0, (Block)Blocks.fire, 0);
        this.spawnItemFrame(world, 2, 3, 0, 3, this.getEasterlingFramedItem(random));
        this.setBlockAndMetadata(world, -2, 1, -5, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 1, -5, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 1, -4, super.plankStairBlock, 4);
        this.placePlate(world, random, -2, 2, -5, super.plateBlock, LOTRFoods.RHUN);
        this.placePlate(world, random, -3, 2, -5, super.plateBlock, LOTRFoods.RHUN);
        this.placeMug(world, random, -3, 2, -4, 3, LOTRFoods.RHUN_DRINK);
        this.setBlockAndMetadata(world, 3, 1, -4, super.tableBlock, 0);
        for (final int k2 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, -3, 1, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.placeBarrel(world, random, -3, 2, k2, 4, LOTRFoods.RHUN_DRINK);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 5, super.plankStairBlock, 6);
            if (Math.abs(i3) >= 2) {
                if (random.nextBoolean()) {
                    this.placePlate(world, random, i3, 2, 5, super.plateBlock, LOTRFoods.RHUN);
                }
                else {
                    this.placeMug(world, random, i3, 2, 5, 0, LOTRFoods.RHUN_DRINK);
                }
            }
        }
        this.setBlockAndMetadata(world, -1, 1, 5, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 1, 1, 5, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -2, 1, 3, super.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 2, 1, 3, super.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 3, -2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 0, 3, 2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, -3, 5, -2, super.woodBeamBlock, super.woodBeamMeta);
        this.setBlockAndMetadata(world, 3, 5, -2, super.woodBeamBlock, super.woodBeamMeta);
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -2, super.fenceBlock, super.fenceMeta);
        }
        for (final int i6 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, i6, 5, 4, super.bedBlock, 0);
            this.setBlockAndMetadata(world, i6, 5, 5, super.bedBlock, 8);
        }
        this.placeChest(world, random, 0, 5, 5, 2, super.chestContents);
        this.setBlockAndMetadata(world, 0, 7, 5, LOTRMod.chandelier, 3);
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityEasterling easterling = new LOTREntityEasterling(world);
            this.spawnNPCAndSetHome(easterling, world, 0, 1, 0, 16);
        }
        return true;
    }
}
