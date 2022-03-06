// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingLargeTownHouse extends LOTRWorldGenEasterlingStructureTown
{
    public LOTRWorldGenEasterlingLargeTownHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -9; k2 <= 9; ++k2) {
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
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k3 = -8; k3 <= 8; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((k4 == 8 && i4 % 4 == 2) || (i4 == 6 && k4 % 4 == 0)) {
                    for (int j2 = 4; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if (i4 == 6 || k4 == 8) {
                    for (int j2 = 3; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (k4 == 8) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    else if (i4 == 6) {
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
                    for (int j2 = 1; j2 <= 14; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -3; k3 <= -1; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.brickGoldBlock, super.brickGoldMeta);
            }
        }
        for (final int k2 : new int[] { -4, 0, 4 }) {
            for (int i5 = -5; i5 <= 5; ++i5) {
                this.setBlockAndMetadata(world, i5, 0, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        for (final int i6 : new int[] { -2, 2 }) {
            for (int k5 = -7; k5 <= 7; ++k5) {
                this.setBlockAndMetadata(world, i6, 0, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
        }
        for (final int i6 : new int[] { -4, 4 }) {
            this.setBlockAndMetadata(world, i6 - 1, 2, -8, super.brickStairBlock, 4);
            this.setAir(world, i6, 2, -8);
            this.setBlockAndMetadata(world, i6 + 1, 2, -8, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i6, 3, -8, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i6 - 1, 2, 8, super.brickStairBlock, 4);
            this.setAir(world, i6, 2, 8);
            this.setBlockAndMetadata(world, i6 + 1, 2, 8, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i6, 3, 8, super.brickStairBlock, 7);
        }
        for (final int k2 : new int[] { -6, -2, 2, 6 }) {
            this.setBlockAndMetadata(world, -6, 2, k2 - 1, super.brickStairBlock, 7);
            this.setAir(world, -6, 2, k2);
            this.setBlockAndMetadata(world, -6, 2, k2 + 1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -6, 3, k2, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 2, k2 - 1, super.brickStairBlock, 7);
            this.setAir(world, 6, 2, k2);
            this.setBlockAndMetadata(world, 6, 2, k2 + 1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, 6, 3, k2, super.brickStairBlock, 4);
        }
        for (final int k2 : new int[] { -9, 9 }) {
            this.setBlockAndMetadata(world, -6, 3, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 6, 3, k2, super.fenceBlock, super.fenceMeta);
        }
        for (final int i6 : new int[] { -7, 7 }) {
            this.setBlockAndMetadata(world, i6, 3, -8, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i6, 3, 8, super.fenceBlock, super.fenceMeta);
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6, 3, -9, Blocks.torch, 4);
            this.setBlockAndMetadata(world, i6, 3, 9, Blocks.torch, 3);
        }
        for (final int k2 : new int[] { -4, 0, 4 }) {
            this.setBlockAndMetadata(world, -7, 3, k2, Blocks.torch, 1);
            this.setBlockAndMetadata(world, 7, 3, k2, Blocks.torch, 2);
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
                this.setBlockAndMetadata(world, i3, 4, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            }
        }
        for (final int k2 : new int[] { -4, 4 }) {
            for (int i5 = -5; i5 <= 5; ++i5) {
                this.setBlockAndMetadata(world, i5, 4, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        for (final int i6 : new int[] { -2, 2 }) {
            for (int k5 = -7; k5 <= 7; ++k5) {
                if (k5 <= -5 || k5 >= 5) {
                    this.setBlockAndMetadata(world, i6, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                }
            }
        }
        for (final int i6 : new int[] { -2, 2 }) {
            for (int j2 = 1; j2 <= 3; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, -4, super.woodBeamBlock, super.woodBeamMeta);
            }
            for (int j2 = 1; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, 0, super.woodBeamBlock, super.woodBeamMeta);
            }
        }
        for (int l = 0; l <= 4; ++l) {
            final int j3 = 1 + l;
            final int k6 = -1 + l;
            for (int i6 = -1; i6 <= 1; ++i6) {
                this.setAir(world, i6, 4, k6);
                if (l <= 3) {
                    this.setBlockAndMetadata(world, i6, j3, k6, super.plankStairBlock, 2);
                }
                for (int j4 = j3 - 1; j4 >= 1; --j4) {
                    this.setBlockAndMetadata(world, i6, j4, k6, super.plankBlock, super.plankMeta);
                }
            }
        }
        for (int k7 = -1; k7 <= 3; ++k7) {
            this.setBlockAndMetadata(world, -2, 5, k7, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 2, 5, k7, super.fenceBlock, super.fenceMeta);
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -2, super.fenceBlock, super.fenceMeta);
        }
        for (final int i6 : new int[] { -2, 2 }) {
            for (final int k8 : new int[] { 0, 4 }) {
                this.setBlockAndMetadata(world, i6, 5, k8, super.woodBeamBlock, super.woodBeamMeta);
                this.setBlockAndMetadata(world, i6, 6, k8, super.plankSlabBlock, super.plankSlabMeta);
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 == 5 && (k4 == 0 || k4 == 4 || k4 == 7)) || (k4 == 7 && i4 == 2)) {
                    for (int j2 = 5; j2 <= 8; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
                else if (i4 == 5 || k4 == 7) {
                    for (int j2 = 5; j2 <= 7; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                    }
                    if (k4 == 7) {
                        this.setBlockAndMetadata(world, i3, 8, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    else if (i4 == 5) {
                        this.setBlockAndMetadata(world, i3, 8, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 6, -7, LOTRMod.reedBars, 0);
        for (final int i6 : new int[] { -5, 5 }) {
            this.setBlockAndMetadata(world, i6, 6, -2, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i6, 6, 2, LOTRMod.reedBars, 0);
        }
        for (final int k2 : new int[] { -8, 8 }) {
            this.setBlockAndMetadata(world, -5, 7, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 5, 7, k2, super.fenceBlock, super.fenceMeta);
        }
        for (final int i6 : new int[] { -6, 6 }) {
            this.setBlockAndMetadata(world, i6, 7, -7, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i6, 7, 7, super.fenceBlock, super.fenceMeta);
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6, 7, -8, Blocks.torch, 4);
            this.setBlockAndMetadata(world, i6, 7, 8, Blocks.torch, 3);
        }
        for (final int k2 : new int[] { -4, 0, 4 }) {
            this.setBlockAndMetadata(world, -6, 7, k2, Blocks.torch, 1);
            this.setBlockAndMetadata(world, 6, 7, k2, Blocks.torch, 2);
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -8, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 5, 8, super.roofStairBlock, 3);
        }
        for (int k7 = -7; k7 <= 7; ++k7) {
            this.setBlockAndMetadata(world, -6, 5, k7, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 6, 5, k7, super.roofStairBlock, 0);
        }
        for (final int k2 : new int[] { -9, 9 }) {
            this.setBlockAndMetadata(world, -7, 5, k2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, -6, 4, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, -5, 4, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -4, 5, k2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, -3, 4, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, -2, 4, k2, super.roofStairBlock, (k2 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, -1, 4, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 5, k2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 1, 4, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 2, 4, k2, super.roofStairBlock, (k2 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, 3, 4, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 4, 5, k2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 5, 4, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 4, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 7, 5, k2, super.roofSlabBlock, super.roofSlabMeta);
        }
        for (final int i6 : new int[] { -7, 7 }) {
            for (final int k8 : new int[] { -4, 0, 4 }) {
                this.setBlockAndMetadata(world, i6, 4, k8 - 1, super.roofStairBlock, 6);
                this.setBlockAndMetadata(world, i6, 4, k8, super.roofStairBlock, (i6 <= 0) ? 1 : 0);
                this.setBlockAndMetadata(world, i6, 4, k8 + 1, super.roofStairBlock, 7);
            }
            for (final int k8 : new int[] { -6, -2, 2, 6 }) {
                this.setBlockAndMetadata(world, i6, 5, k8, super.roofSlabBlock, super.roofSlabMeta);
            }
            this.setBlockAndMetadata(world, i6, 4, -8, super.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 4, -7, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 4, 7, super.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 4, 8, super.roofStairBlock, 7);
        }
        for (final int k2 : new int[] { -8, 8 }) {
            this.setBlockAndMetadata(world, -6, 9, k2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, -5, 8, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, -4, 8, k2, super.roofStairBlock, (k2 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, -3, 8, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -2, 8, k2, super.roofStairBlock, (k2 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, -1, 8, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 9, k2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 1, 8, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 2, 8, k2, super.roofStairBlock, (k2 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, 3, 8, k2, super.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 8, k2, super.roofStairBlock, (k2 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, 5, 8, k2, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 6, 9, k2, super.roofSlabBlock, super.roofSlabMeta);
        }
        for (final int i6 : new int[] { -6, 6 }) {
            this.setBlockAndMetadata(world, i6, 8, -7, super.roofStairBlock, 6);
            for (int k5 = -6; k5 <= -4; ++k5) {
                this.setBlockAndMetadata(world, i6, 8, k5, super.roofStairBlock, (i6 <= 0) ? 1 : 0);
            }
            this.setBlockAndMetadata(world, i6, 8, -3, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 9, -2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i6, 8, -1, super.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 9, 0, super.roofStairBlock, (i6 <= 0) ? 1 : 0);
            this.setBlockAndMetadata(world, i6, 8, 1, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 9, 2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i6, 8, 3, super.roofStairBlock, 6);
            for (int k5 = 4; k5 <= 6; ++k5) {
                this.setBlockAndMetadata(world, i6, 8, k5, super.roofStairBlock, (i6 <= 0) ? 1 : 0);
            }
            this.setBlockAndMetadata(world, i6, 8, 7, super.roofStairBlock, 7);
        }
        for (int k7 = -7; k7 <= 7; ++k7) {
            for (int m = 0; m <= 4; ++m) {
                final int j5 = 9 + m;
                this.setBlockAndMetadata(world, -5 + m, j5, k7, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 5 - m, j5, k7, super.roofStairBlock, 0);
                if (m > 0) {
                    this.setBlockAndMetadata(world, -5 + m, j5 - 1, k7, super.roofStairBlock, 4);
                    this.setBlockAndMetadata(world, 5 - m, j5 - 1, k7, super.roofStairBlock, 5);
                }
            }
            this.setBlockAndMetadata(world, 0, 13, k7, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 0, 14, k7, super.roofSlabBlock, super.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 13, -8, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 14, -8, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 13, 8, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 14, 8, super.roofStairBlock, 2);
        for (final int k2 : new int[] { -7, 7 }) {
            for (final int i7 : new int[] { -2, 2 }) {
                for (int j6 = 5; j6 <= 11; ++j6) {
                    this.setBlockAndMetadata(world, i7, j6, k2, super.woodBeamBlock, super.woodBeamMeta);
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 8, -6, super.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 8, 6, super.plankStairBlock, 6);
        }
        for (final int k2 : new int[] { -6, 6 }) {
            for (int l2 = 0; l2 <= 3; ++l2) {
                final int j7 = 9 + l2;
                for (int i8 = -4 + l2; i8 <= 4 - l2; ++i8) {
                    this.setBlockAndMetadata(world, i8, j7, k2, super.plankBlock, super.plankMeta);
                }
            }
        }
        for (final int k2 : new int[] { -4, 4 }) {
            for (int i5 = -4; i5 <= 4; ++i5) {
                this.setBlockAndMetadata(world, i5, 8, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        for (final int i6 : new int[] { -2, 2 }) {
            for (int k5 = -6; k5 <= 6; ++k5) {
                this.setBlockAndMetadata(world, i6, 8, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, -8, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        this.setBlockAndMetadata(world, 0, 1, -8, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -8, super.doorBlock, 8);
        this.setBlockAndMetadata(world, -2, 3, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, -5, Blocks.torch, 4);
        this.spawnItemFrame(world, -2, 2, -4, 1, this.getEasterlingFramedItem(random));
        this.spawnItemFrame(world, 2, 2, -4, 3, this.getEasterlingFramedItem(random));
        this.setBlockAndMetadata(world, -3, 1, -6, super.plankStairBlock, 1);
        this.setBlockAndMetadata(world, 3, 1, -6, super.plankStairBlock, 0);
        for (int k7 = -7; k7 <= -5; ++k7) {
            this.setBlockAndMetadata(world, -5, 1, k7, super.plankStairBlock, 4);
            if (random.nextBoolean()) {
                this.placePlate(world, random, -5, 2, k7, super.plateBlock, LOTRFoods.RHUN);
            }
            else {
                this.placeMug(world, random, -5, 2, k7, 3, LOTRFoods.RHUN_DRINK);
            }
            this.setBlockAndMetadata(world, 5, 1, k7, super.plankStairBlock, 5);
            if (random.nextBoolean()) {
                this.placePlate(world, random, 5, 2, k7, super.plateBlock, LOTRFoods.RHUN);
            }
            else {
                this.placeMug(world, random, 5, 2, k7, 1, LOTRFoods.RHUN_DRINK);
            }
        }
        this.spawnItemFrame(world, -6, 2, 0, 1, this.getEasterlingFramedItem(random));
        this.spawnItemFrame(world, 6, 2, 0, 3, this.getEasterlingFramedItem(random));
        this.setBlockAndMetadata(world, -2, 1, 1, (Block)Blocks.chest, 5);
        this.setBlockAndMetadata(world, -2, 1, 2, (Block)Blocks.chest, 5);
        this.setBlockAndMetadata(world, 2, 1, 1, (Block)Blocks.chest, 4);
        this.setBlockAndMetadata(world, 2, 1, 2, (Block)Blocks.chest, 4);
        this.setBlockAndMetadata(world, -2, 1, 3, super.plankStairBlock, 5);
        this.placeBarrel(world, random, -2, 2, 3, 5, LOTRFoods.RHUN_DRINK);
        this.setBlockAndMetadata(world, 2, 1, 3, super.plankStairBlock, 4);
        this.placeBarrel(world, random, 2, 2, 3, 4, LOTRFoods.RHUN_DRINK);
        this.setBlockAndMetadata(world, -2, 3, 4, LOTRMod.chandelier, 3);
        this.setBlockAndMetadata(world, 2, 3, 4, LOTRMod.chandelier, 3);
        this.setBlockAndMetadata(world, 0, 1, 3, super.tableBlock, 0);
        this.setAir(world, 0, 2, 3);
        this.setBlockAndMetadata(world, 0, 3, 3, super.plankStairBlock, 7);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 5; k3 <= 7; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.brickBlock, super.brickMeta);
            }
            for (int k3 = 7; k3 <= 8; ++k3) {
                for (int j5 = 1; j5 <= 4; ++j5) {
                    this.setBlockAndMetadata(world, i3, j5, k3, super.brickBlock, super.brickMeta);
                }
            }
            for (int k3 = 9, j5 = 4; (j5 >= 0 || !this.isOpaque(world, i3, j5, k3)) && this.getY(j5) >= 0; --j5) {
                this.setBlockAndMetadata(world, i3, j5, k3, super.brickBlock, super.brickMeta);
                this.setGrassToDirt(world, i3, j5 - 1, k3);
            }
            this.setBlockAndMetadata(world, i3, 5, 9, super.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 4, 8, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i3, 5, 8, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i3, 6, 8, super.brickStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, 1, 7, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 2, 7, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 3, 7, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 1, 7, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 2, 7, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 3, 7, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 0, 7, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 1, 7, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 2, 7, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 0, 0, 8, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 1, 8, (Block)Blocks.fire, 0);
        this.setAir(world, 0, 2, 8);
        this.setBlockAndMetadata(world, -5, 1, 5, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -5, 1, 6, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -5, 1, 7, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -4, 1, 7, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -3, 1, 7, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 5, 1, 5, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 5, 1, 6, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 5, 1, 7, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 4, 1, 7, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 1, 7, super.plankStairBlock, 6);
        for (int i3 = 3; i3 <= 5; ++i3) {
            if (random.nextBoolean()) {
                this.placePlate(world, random, i3, 2, 7, super.plateBlock, LOTRFoods.RHUN);
            }
            else {
                this.placeMug(world, random, i3, 2, 7, 0, LOTRFoods.RHUN_DRINK);
            }
        }
        for (int k7 = 5; k7 <= 6; ++k7) {
            if (random.nextBoolean()) {
                this.placePlate(world, random, 5, 2, k7, super.plateBlock, LOTRFoods.RHUN);
            }
            else {
                this.placeMug(world, random, 5, 2, k7, 1, LOTRFoods.RHUN_DRINK);
            }
        }
        this.placeWallBanner(world, 0, 7, 7, super.bannerType, 2);
        this.setBlockAndMetadata(world, -2, 7, 4, LOTRMod.chandelier, 3);
        this.setBlockAndMetadata(world, 2, 7, 4, LOTRMod.chandelier, 3);
        this.placeArmorStand(world, -4, 5, 6, 0, null);
        this.placeArmorStand(world, 4, 5, 6, 0, null);
        this.setBlockAndMetadata(world, -2, 7, -4, LOTRMod.chandelier, 3);
        this.setBlockAndMetadata(world, 2, 7, -4, LOTRMod.chandelier, 3);
        for (final int i6 : new int[] { -4, 0, 4 }) {
            this.setBlockAndMetadata(world, i6, 5, -5, super.bedBlock, 2);
            this.setBlockAndMetadata(world, i6, 5, -6, super.bedBlock, 10);
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.placeChest(world, random, i6, 5, -6, 3, super.chestContents);
        }
        for (final int i6 : new int[] { -3, -1, 1, 3 }) {
            this.setBlockAndMetadata(world, i6, 5, -6, super.plankStairBlock, 7);
            if (random.nextBoolean()) {
                this.placePlate(world, random, i6, 6, -6, super.plateBlock, LOTRFoods.RHUN);
            }
            else {
                this.placeMug(world, random, i6, 6, -6, 2, LOTRFoods.RHUN_DRINK);
            }
        }
        this.placeWeaponRack(world, 0, 7, -6, 4, this.getEasterlingWeaponItem(random));
        if (random.nextBoolean()) {
            this.spawnItemFrame(world, -2, 7, -7, 0, new ItemStack(Items.clock));
        }
        else {
            this.spawnItemFrame(world, 2, 7, -7, 0, new ItemStack(Items.clock));
        }
        for (int men = 2, m = 0; m < men; ++m) {
            final LOTREntityEasterling easterling = new LOTREntityEasterling(world);
            this.spawnNPCAndSetHome(easterling, world, 0, 5, 3, 16);
        }
        return true;
    }
}
