// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRangerLodge extends LOTRWorldGenRangerStructure
{
    public LOTRWorldGenRangerLodge(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 6; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
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
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 <= 4 || k4 <= 3) {
                    for (int j2 = 0; (j2 >= -3 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                    if (k4 == 4 || i4 == 5) {
                        boolean beam = false;
                        if (k3 == -4 && (i4 == 1 || i4 == 4)) {
                            beam = true;
                        }
                        if (k3 == 4 && (i4 == 0 || i4 == 4)) {
                            beam = true;
                        }
                        if (i4 == 5 && (k4 == 0 || k4 == 3)) {
                            beam = true;
                        }
                        if (beam) {
                            for (int j3 = 1; j3 <= 3; ++j3) {
                                this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                            }
                        }
                        else {
                            for (int j3 = 1; j3 <= 3; ++j3) {
                                this.setBlockAndMetadata(world, i3, j3, k3, super.wallBlock, super.wallMeta);
                            }
                        }
                    }
                    if (k4 <= 3 && i4 <= 4) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                        if (random.nextInt(3) == 0) {
                            this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                        }
                        for (int j2 = -2; j2 <= -1; ++j2) {
                            this.setAir(world, i3, j2, k3);
                        }
                    }
                }
            }
        }
        for (final int k2 : new int[] { -4, 4 }) {
            for (int i5 = -4; i5 <= 4; ++i5) {
                this.setBlockAndMetadata(world, i5, 4, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        for (final int i6 : new int[] { -5, 5 }) {
            for (int k5 = -3; k5 <= 3; ++k5) {
                final int k6 = Math.abs(k5);
                if (k6 == 0) {
                    for (int j4 = 4; j4 <= 6; ++j4) {
                        this.setBlockAndMetadata(world, i6, j4, k5, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i6, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    if (k6 <= 2) {
                        this.setBlockAndMetadata(world, i6, 5, k5, super.wallBlock, super.wallMeta);
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 6, -1, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 7, 0, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 6, 1, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 5, 3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 5, -4, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 6, -3, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 6, -2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i3, 7, -1, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 7, 1, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 6, 2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i3, 6, 3, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 5, 4, super.roofStairBlock, 3);
        }
        for (int k7 = -4; k7 <= 4; ++k7) {
            this.setBlockAndMetadata(world, 0, 4, k7, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        this.setBlockAndMetadata(world, 0, 1, -4, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 4, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 2, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 3, 2, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 2, 4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 2, 4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -5, 2, -1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -5, 2, 1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 3, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -4, 4, 0, Blocks.torch, 2);
        for (final int i6 : new int[] { -4, 4 }) {
            for (final int k8 : new int[] { -3, 3 }) {
                this.setBlockAndMetadata(world, i6, 1, k8, super.plankBlock, super.plankMeta);
                for (int j5 = 2; j5 <= 4; ++j5) {
                    this.setBlockAndMetadata(world, i6, j5, k8, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -2, 1, -3, super.plankBlock, super.plankMeta);
        this.placePlate(world, random, -2, 2, -3, super.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, -3, 1, -3, super.plankBlock, super.plankMeta);
        this.placePlate(world, random, -3, 2, -3, super.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, -4, 1, -2, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, -4, 2, -2, 3, LOTRFoods.RANGER_DRINK);
        this.placeChest(world, random, -4, 1, -1, 4, super.chestContentsHouse);
        this.setBlockAndMetadata(world, -4, 1, 0, Blocks.crafting_table, 0);
        this.placeChest(world, random, -4, 1, 1, 4, super.chestContentsHouse);
        this.setBlockAndMetadata(world, -4, 1, 2, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, -4, 2, 2, 4, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, -3, 1, 3, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, -3, 2, 3, 2, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, -2, 1, 3, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, -2, 2, 3, 0, LOTRFoods.RANGER_DRINK);
        for (final int k2 : new int[] { -3, 3 }) {
            this.setBlockAndMetadata(world, 2, 1, k2, super.bedBlock, 1);
            this.setBlockAndMetadata(world, 3, 1, k2, super.bedBlock, 9);
        }
        this.setBlockAndMetadata(world, 4, 1, -2, super.plankBlock, super.plankMeta);
        for (int i3 = 4; i3 <= 6; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                for (int j6 = 5; (j6 >= 0 || !this.isOpaque(world, i3, j6, k3)) && this.getY(j6) >= 0; --j6) {
                    this.setBlockAndMetadata(world, i3, j6, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j6 - 1, k3);
                }
            }
        }
        this.setBlockAndMetadata(world, 4, 6, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 6, 5, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 6, 5, 1, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 6, 6, 0, super.brickStairBlock, 0);
        for (int j7 = 6; j7 <= 8; ++j7) {
            this.setBlockAndMetadata(world, 5, j7, 0, super.brickBlock, super.brickMeta);
        }
        for (int j7 = 9; j7 <= 10; ++j7) {
            this.setBlockAndMetadata(world, 5, j7, 0, super.brickWallBlock, super.brickWallMeta);
        }
        this.setBlockAndMetadata(world, 5, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 5, 1, 0, (Block)Blocks.fire, 0);
        for (int j7 = 2; j7 <= 3; ++j7) {
            this.setAir(world, 5, j7, 0);
        }
        this.setBlockAndMetadata(world, 4, 1, 0, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 4, 2, 0, Blocks.furnace, 5);
        this.spawnItemFrame(world, 4, 4, 0, 3, this.getRangerFramedItem(random));
        this.setBlockAndMetadata(world, 4, 1, 2, Blocks.trapdoor, 3);
        for (int j7 = -2; j7 <= 0; ++j7) {
            this.setBlockAndMetadata(world, 4, j7, 2, Blocks.ladder, 3);
        }
        for (final int i6 : new int[] { -4, 4 }) {
            for (final int k8 : new int[] { -3, 3 }) {
                this.setBlockAndMetadata(world, i6, 0, k8, super.plankBlock, super.plankMeta);
                for (int j5 = -2; j5 <= -1; ++j5) {
                    this.setBlockAndMetadata(world, i6, j5, k8, super.woodBeamBlock, super.woodBeamMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -3, -1, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, -1, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -3, -1, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, -1, 3, Blocks.torch, 1);
        for (final int i6 : new int[] { -2, 0, 2 }) {
            this.setBlockAndMetadata(world, i6, -2, -2, super.bedBlock, 2);
            this.setBlockAndMetadata(world, i6, -2, -3, super.bedBlock, 10);
        }
        for (final int k2 : new int[] { -2, 2 }) {
            ItemStack[] armor = null;
            if (random.nextInt(3) == 0) {
                armor = new ItemStack[] { new ItemStack(LOTRMod.helmetRanger), new ItemStack(LOTRMod.bodyRanger), new ItemStack(LOTRMod.legsRanger), new ItemStack(LOTRMod.bootsRanger) };
            }
            this.placeArmorStand(world, -4, -2, k2, 3, armor);
        }
        for (final int k2 : new int[] { -1, 1 }) {
            this.spawnItemFrame(world, -5, -1, k2, 1, this.getRangerFramedItem(random));
        }
        this.setBlockAndMetadata(world, 0, -2, 3, super.tableBlock, 0);
        for (final int i6 : new int[] { -1, 1 }) {
            final int amount = 2 + random.nextInt(5);
            this.placeChest(world, random, i6, -2, 3, 2, super.chestContentsRanger, amount);
        }
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
            this.spawnNPCAndSetHome(dunedain, world, 0, 1, 0, 16);
        }
        return true;
    }
}
