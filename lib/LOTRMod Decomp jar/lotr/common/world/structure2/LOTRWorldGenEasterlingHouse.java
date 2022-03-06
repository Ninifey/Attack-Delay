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
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingHouse extends LOTRWorldGenEasterlingStructure
{
    public LOTRWorldGenEasterlingHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
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
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (((i4 == 2 || i4 == 6) && k4 == 4) || ((k4 == 0 || k4 == 4) && i4 == 6)) {
                    for (int j2 = 5; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if (i4 == 6 || k4 == 4) {
                    for (int j2 = 1; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 2; j2 <= 3; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.plankBlock, super.plankMeta);
                    }
                    if (k4 == 4) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                        this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    else if (i4 == 6) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                        this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    }
                }
                else {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.plankBlock, super.plankMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 6; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                    if (random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                }
            }
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, 0, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        this.setBlockAndMetadata(world, -7, 3, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 7, 3, 0, super.fenceBlock, super.fenceMeta);
        for (final int i5 : new int[] { -2, 2 }) {
            for (int k5 = -5; k5 <= 5; ++k5) {
                this.setBlockAndMetadata(world, i5, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
            this.setBlockAndMetadata(world, i5, 3, -5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i5, 3, 5, super.fenceBlock, super.fenceMeta);
        }
        for (final int i5 : new int[] { -6, 6 }) {
            this.setBlockAndMetadata(world, i5, 4, -5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i5, 4, 5, super.fenceBlock, super.fenceMeta);
        }
        for (final int k2 : new int[] { -4, 4 }) {
            this.setBlockAndMetadata(world, -7, 4, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 7, 4, k2, super.fenceBlock, super.fenceMeta);
        }
        for (final int i5 : new int[] { -4, 4 }) {
            this.setBlockAndMetadata(world, i5, 2, -4, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i5, 3, -4, super.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i5, 2, 4, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i5, 3, 4, super.plankStairBlock, 7);
        }
        for (final int k2 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, -6, 2, k2, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, -6, 3, k2, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 2, k2, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 6, 3, k2, super.plankStairBlock, 4);
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -5, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 5, 5, super.roofStairBlock, 3);
        }
        for (int k6 = -3; k6 <= 3; ++k6) {
            this.setBlockAndMetadata(world, -7, 5, k6, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 7, 5, k6, super.roofStairBlock, 0);
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -4, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 6, 4, super.roofStairBlock, 3);
        }
        for (int k6 = -3; k6 <= 3; ++k6) {
            this.setBlockAndMetadata(world, -6, 6, k6, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 6, 6, k6, super.roofStairBlock, 0);
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 6, k3, super.roofBlock, super.roofMeta);
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, -2, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 7, 2, super.roofStairBlock, 3);
        }
        for (int k6 = -1; k6 <= 1; ++k6) {
            this.setBlockAndMetadata(world, -5, 7, k6, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 5, 7, k6, super.roofStairBlock, 0);
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                this.setBlockAndMetadata(world, i3, 7, k3, super.roofBlock, super.roofMeta);
            }
        }
        this.setBlockAndMetadata(world, -6, 5, -5, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -7, 6, -5, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -7, 5, -4, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 6, 5, -5, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 7, 6, -5, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 7, 5, -4, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -6, 5, 5, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -7, 6, 5, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -7, 5, 4, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 6, 5, 5, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 7, 6, 5, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 7, 5, 4, super.roofStairBlock, 7);
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -3, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 5, 3, super.roofStairBlock, 6);
        }
        for (int k6 = -2; k6 <= 2; ++k6) {
            this.setBlockAndMetadata(world, -5, 5, k6, super.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 5, 5, k6, super.roofStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -1, 1, -4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 1, 1, -4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 0, -4, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        this.setBlockAndMetadata(world, 0, 1, -4, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, super.doorBlock, 8);
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = 1; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.brickBlock, super.brickMeta);
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 2; k3 <= 4; ++k3) {
                for (int j3 = 1; j3 <= 6; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, 4, super.brickStairBlock, 3);
        }
        for (int j4 = 7; j4 <= 8; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, 3, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, 3, Blocks.flower_pot, 0);
        this.setBlockAndMetadata(world, 0, 0, 3, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, (Block)Blocks.fire, 0);
        this.setAir(world, 0, 2, 3);
        this.setBlockAndMetadata(world, 0, 1, 2, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 2, 2, Blocks.furnace, 2);
        this.spawnItemFrame(world, 0, 3, 2, 2, this.getEasterlingFramedItem(random));
        this.setBlockAndMetadata(world, -2, 3, 0, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 2, 3, 0, LOTRMod.chandelier, 0);
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, 0, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (final int i5 : new int[] { -2, 2 }) {
            for (int k5 = -3; k5 <= -1; ++k5) {
                this.setBlockAndMetadata(world, i5, 0, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
        }
        for (int i3 = -5; i3 <= -3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -3, super.plankStairBlock, 7);
            if (random.nextBoolean()) {
                this.placePlate(world, random, i3, 2, -3, super.plateBlock, LOTRFoods.RHUN);
            }
            else {
                this.placeMug(world, random, i3, 2, -3, 2, LOTRFoods.RHUN_DRINK);
            }
        }
        this.setBlockAndMetadata(world, -4, 1, -1, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 1, 2, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -4, 1, 3, super.bedBlock, 8);
        this.setBlockAndMetadata(world, -5, 1, 3, super.plankBlock, super.plankMeta);
        this.placePlateWithCertainty(world, random, -5, 2, 3, super.plateBlock, LOTRFoods.RHUN);
        this.setBlockAndMetadata(world, 5, 1, -3, super.tableBlock, 0);
        this.placeChest(world, random, 5, 1, -2, 5, super.chestContents);
        this.setBlockAndMetadata(world, 5, 1, -1, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 5, 1, 0, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 5, 2, 0, LOTRMod.ceramicPlateBlock, 0);
        this.setBlockAndMetadata(world, 5, 1, 1, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 5, 1, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.placeMug(world, random, 5, 2, 2, 1, LOTRFoods.RHUN_DRINK);
        this.setBlockAndMetadata(world, 5, 1, 3, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 5, 2, 3, 5, LOTRFoods.RHUN_DRINK);
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 2, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, 3, Blocks.torch, 4);
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityEasterling easterling = new LOTREntityEasterling(world);
            this.spawnNPCAndSetHome(easterling, world, 0, 1, 0, 16);
        }
        return true;
    }
}
