// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDunedainBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRangerSmithy extends LOTRWorldGenRangerStructure
{
    public LOTRWorldGenRangerSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 9; ++i2) {
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
        for (int i3 = -7; i3 <= 8; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int k4 = Math.abs(k3);
                for (int j3 = 1; j3 <= 8; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                if (i3 <= 1 || (i3 == 2 && k4 == 4)) {
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.cobbleBlock, super.cobbleMeta);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
                else if (((i3 == 2 || i3 == 8) && k4 <= 3) || (i3 >= 3 && i3 <= 7 && k4 <= 4)) {
                    if (((i3 == 2 || i3 == 8) && k4 <= 3) || (i3 >= 3 && i3 <= 7 && k4 == 4)) {
                        boolean beam = false;
                        if ((i3 == 2 || i3 == 8) && k4 == 3) {
                            beam = true;
                        }
                        if ((i3 == 3 || i3 == 7) && k4 == 4) {
                            beam = true;
                        }
                        if (beam) {
                            for (int j2 = 4; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                                this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                                this.setGrassToDirt(world, i3, j2 - 1, k3);
                            }
                        }
                        else {
                            for (int j2 = 1; j2 <= 3; ++j2) {
                                this.setBlockAndMetadata(world, i3, j2, k3, super.wallBlock, super.wallMeta);
                            }
                            for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                                this.setBlockAndMetadata(world, i3, j2, k3, super.plankBlock, super.plankMeta);
                                this.setGrassToDirt(world, i3, j2 - 1, k3);
                            }
                        }
                    }
                    else {
                        for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.plankBlock, super.plankMeta);
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                        if (random.nextInt(3) == 0) {
                            this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                        }
                    }
                }
            }
        }
        for (int i3 = 4; i3 <= 6; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -4, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 4, 4, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, 2, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            this.setBlockAndMetadata(world, 8, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        this.setBlockAndMetadata(world, 5, 2, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 5, 2, 4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 2, -2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 2, 2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 1, 0, super.doorBlock, 2);
        this.setBlockAndMetadata(world, 2, 2, 0, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 3, 3, 0, Blocks.torch, 2);
        for (int l = 0; l <= 2; ++l) {
            final int j4 = 4 + l;
            for (int i2 = 2 + l; i2 <= 8 - l; ++i2) {
                this.setBlockAndMetadata(world, i2, j4, -5 + l, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i2, j4, 5 - l, super.roofStairBlock, 3);
            }
            for (final int i4 : new int[] { 1 + l, 9 - l }) {
                this.setBlockAndMetadata(world, i4, j4, -4 + l, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i4, j4, 4 - l, super.roofStairBlock, 3);
            }
            for (int k6 = -3 + l; k6 <= 3 - l; ++k6) {
                this.setBlockAndMetadata(world, 1 + l, j4, k6, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 9 - l, j4, k6, super.roofStairBlock, 0);
            }
            for (final int k7 : new int[] { -4 + l, 4 - l }) {
                this.setBlockAndMetadata(world, 2 + l, j4, k7, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 8 - l, j4, k7, super.roofStairBlock, 0);
            }
        }
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, 5, 7, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 4, 7, k5, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 6, 7, k5, super.roofSlabBlock, super.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, 5, 7, -2, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 5, 7, 2, super.roofSlabBlock, super.roofSlabMeta);
        for (int l = 0; l <= 1; ++l) {
            final int j4 = 5 + l;
            for (int i2 = 4 + l; i2 <= 6 - l; ++i2) {
                this.setBlockAndMetadata(world, i2, j4, -3 + l, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                this.setBlockAndMetadata(world, i2, j4, 3 - l, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
            for (int k6 = -2 + l; k6 <= 2 - l; ++k6) {
                this.setBlockAndMetadata(world, 3 + l, j4, k6, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                this.setBlockAndMetadata(world, 7 - l, j4, k6, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            }
        }
        for (int i3 = 7; i3 <= 9; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                for (int j5 = 5; (j5 >= 0 || !this.isOpaque(world, i3, j5, k3)) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i3, j5, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j5 - 1, k3);
                }
            }
        }
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, 9, 5, k5, super.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, 8, 5, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 8, 5, 1, super.brickStairBlock, 3);
        for (int j6 = 6; j6 <= 7; ++j6) {
            this.setBlockAndMetadata(world, 8, j6, 0, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 8, 8, 0, super.brickWallBlock, super.brickWallMeta);
        for (int k5 = -3; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, 5, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        for (final int i5 : new int[] { 3, 7 }) {
            for (final int k8 : new int[] { -3, 3 }) {
                this.setBlockAndMetadata(world, i5, 1, k8, super.plankBlock, super.plankMeta);
                for (int j7 = 2; j7 <= 4; ++j7) {
                    this.setBlockAndMetadata(world, i5, j7, k8, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 3, 1, -2, super.plankBlock, super.plankMeta);
        this.placePlate(world, random, 3, 2, -2, super.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, 4, 1, -3, super.plankBlock, super.plankMeta);
        this.placePlate(world, random, 4, 2, -3, super.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, 5, 1, -3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 6, 1, -3, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 6, 2, -3, 2, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 7, 1, -2, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 7, 2, -2, 5, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 3, 1, 2, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 3, 2, 2, 3, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 5, 1, 3, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 6, 1, 3, super.bedBlock, 9);
        this.placeChest(world, random, 7, 1, 2, 5, super.chestContentsHouse);
        this.setBlockAndMetadata(world, 8, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 8, 1, 0, (Block)Blocks.fire, 0);
        for (int j6 = 2; j6 <= 3; ++j6) {
            this.setAir(world, 8, j6, 0);
        }
        this.setBlockAndMetadata(world, 7, 1, 0, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 7, 2, 0, Blocks.furnace, 5);
        this.spawnItemFrame(world, 7, 3, 0, 3, this.getRangerFramedItem(random));
        this.placeChest(world, random, 1, 1, 2, 5, LOTRChestContents.RANGER_SMITHY);
        this.setBlockAndMetadata(world, 1, 1, -2, super.tableBlock, 0);
        this.setBlockAndMetadata(world, 1, 1, -3, Blocks.crafting_table, 0);
        for (int j6 = 1; j6 <= 3; ++j6) {
            for (int i6 = -6; i6 <= -3; ++i6) {
                for (int k6 = 0; k6 <= 3; ++k6) {
                    this.setBlockAndMetadata(world, i6, j6, k6, super.brickBlock, super.brickMeta);
                }
            }
            this.setBlockAndMetadata(world, -2, j6, 3, super.brickBlock, super.brickMeta);
        }
        for (int j6 = 1; j6 <= 3; ++j6) {
            this.setBlockAndMetadata(world, -6, j6, -3, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, -2, j6, -3, super.fenceBlock, super.fenceMeta);
        }
        for (int l = 0; l <= 1; ++l) {
            final int j4 = 4 + l;
            for (int i2 = -6 + l; i2 <= -2 - l; ++i2) {
                this.setBlockAndMetadata(world, i2, j4, -3 + l, super.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i2, j4, 3 - l, super.brickStairBlock, 3);
            }
            for (int k6 = -2 + l; k6 <= 2 - l; ++k6) {
                this.setBlockAndMetadata(world, -6 + l, j4, k6, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, -2 - l, j4, k6, super.brickStairBlock, 0);
            }
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            for (int i6 = -5; i6 <= -3; ++i6) {
                this.setBlockAndMetadata(world, i6, 4, k5, super.brickBlock, super.brickMeta);
            }
        }
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, -4, 5, k5, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -4, 1, 0, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -3, 1, 0, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 1, LOTRMod.alloyForge, 4);
        this.setBlockAndMetadata(world, -4, 2, 0, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 2, 0, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 2, 1, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -4, 1, 1, Blocks.lava, 0);
        for (int j6 = 2; j6 <= 5; ++j6) {
            this.setAir(world, -4, j6, 1);
        }
        this.setBlockAndMetadata(world, -2, 1, 2, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -5, 1, -1, LOTRMod.unsmeltery, 4);
        this.setBlockAndMetadata(world, -5, 1, -3, Blocks.anvil, 1);
        this.setBlockAndMetadata(world, -3, 1, -3, Blocks.anvil, 1);
        final LOTREntityDunedainBlacksmith blacksmith = new LOTREntityDunedainBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 0, 8);
        return true;
    }
}
