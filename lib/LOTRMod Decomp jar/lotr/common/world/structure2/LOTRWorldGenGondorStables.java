// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import lotr.common.LOTRFoods;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;

public class LOTRWorldGenGondorStables extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorStables(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.bedBlock = Blocks.bed;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -6; k2 <= 6; ++k2) {
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
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -5; k3 <= 5; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 == 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 7; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -6, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 4, -5, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i3, 5, -4, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 5, -3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i3, 6, -2, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 6, -1, super.roofSlabBlock, super.roofSlabMeta | 0x8);
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            final int i5 = Math.abs(i3);
            if (i5 == 4 || i5 == 0) {
                for (int j3 = 1; j3 <= 3; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, -5, super.woodBeamBlock, super.woodBeamMeta);
                }
                this.setBlockAndMetadata(world, i3, 3, -6, super.plankStairBlock, 6);
                for (int k5 = -4; k5 <= -1; ++k5) {
                    this.setBlockAndMetadata(world, i3, 1, k5, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i3, 2, k5, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i3, 3, k5, super.fenceBlock, super.fenceMeta);
                }
                for (int k5 = -5; k5 <= -1; ++k5) {
                    this.setBlockAndMetadata(world, i3, 4, k5, super.roofBlock, super.roofMeta);
                    if (k5 >= -3) {
                        this.setBlockAndMetadata(world, i3, 5, k5, super.roofBlock, super.roofMeta);
                    }
                    if (k5 >= -1) {
                        this.setBlockAndMetadata(world, i3, 6, k5, super.roofBlock, super.roofMeta);
                    }
                }
            }
            else {
                this.setBlockAndMetadata(world, i3, 2, -5, super.fenceGateBlock, 2);
                for (int k5 = -4; k5 <= -1; ++k5) {
                    this.setBlockAndMetadata(world, i3, 0, k5, super.rockBlock, super.rockMeta);
                    if (random.nextInt(3) != 0) {
                        this.setBlockAndMetadata(world, i3, 1, k5, LOTRMod.thatchFloor, 0);
                    }
                }
            }
        }
        for (final int i6 : new int[] { -4, 4 }) {
            for (int j2 = 1; j2 <= 6; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, 0, super.pillarBlock, super.pillarMeta);
            }
            for (int j2 = 1; j2 <= 5; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, 5, super.pillarBlock, super.pillarMeta);
            }
            for (int k6 = 1; k6 <= 4; ++k6) {
                for (int j4 = 1; j4 <= 6; ++j4) {
                    this.setBlockAndMetadata(world, i6, j4, k6, super.brickBlock, super.brickMeta);
                }
            }
            for (int k6 = 2; k6 <= 3; ++k6) {
                for (int j4 = 1; j4 <= 2; ++j4) {
                    this.setAir(world, i6, j4, k6);
                }
            }
            this.setBlockAndMetadata(world, i6, 3, 2, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 3, 3, super.brickStairBlock, 6);
            for (int k6 = 1; k6 <= 4; ++k6) {
                this.setBlockAndMetadata(world, i6, 4, k6, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int j5 = 1; j5 <= 6; ++j5) {
                if (j5 >= 2 && j5 <= 4) {
                    this.setBlockAndMetadata(world, i3, j5, 0, super.plankBlock, super.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, j5, 0, super.brickBlock, super.brickMeta);
                }
            }
            for (int j5 = 1; j5 <= 5; ++j5) {
                this.setBlockAndMetadata(world, i3, j5, 5, super.brickBlock, super.brickMeta);
            }
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6, 1, -1, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, i6, 4, -1, Blocks.torch, 4);
            final LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome((EntityCreature)horse, world, i6, 1, -3, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = 1; k3 <= 4; ++k3) {
                this.setBlockAndMetadata(world, i3, 4, k3, super.plankBlock, super.plankMeta);
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, 0, super.brick2StairBlock, 2);
            for (int k3 = 1; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 7, k3, super.brick2Block, super.brick2Meta);
            }
            this.setBlockAndMetadata(world, i3, 7, 4, super.brick2StairBlock, 3);
            this.setBlockAndMetadata(world, i3, 6, 5, super.brick2StairBlock, 3);
            this.setBlockAndMetadata(world, i3, 5, 6, super.brick2StairBlock, 3);
            if (Math.abs(i3) == 5) {
                this.setBlockAndMetadata(world, i3, 6, 4, super.brick2StairBlock, 6);
                this.setBlockAndMetadata(world, i3, 5, 5, super.brick2StairBlock, 6);
            }
        }
        for (final int i6 : new int[] { -3, 1 }) {
            this.setBlockAndMetadata(world, i6, 2, 5, super.brickStairBlock, 0);
            this.setBlockAndMetadata(world, i6, 3, 5, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, i6 + 1, 2, 5, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, i6 + 1, 3, 5, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, i6 + 2, 2, 5, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, i6 + 2, 3, 5, super.brickStairBlock, 5);
        }
        this.setBlockAndMetadata(world, 0, 2, 5, LOTRMod.brick, 5);
        this.setBlockAndMetadata(world, -3, 1, 4, super.plankBlock, super.plankMeta);
        this.placeFlowerPot(world, -3, 2, 4, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -2, 1, 4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.placeMug(world, random, -2, 2, 4, 0, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, -1, 1, 4, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 0, 1, 4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.placePlateWithCertainty(world, random, 0, 2, 4, super.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, 1, 1, 4, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 2, 1, 4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.placeMug(world, random, 2, 2, 4, 0, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, 3, 1, 4, super.plankBlock, super.plankMeta);
        this.placeFlowerPot(world, 3, 2, 4, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -3, 1, 1, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -2, 1, 1, super.tableBlock, 0);
        this.setBlockAndMetadata(world, 3, 1, 1, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 3, 2, 1, 2, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, 0, 3, 3, LOTRMod.chandelier, 1);
        for (int j6 = 1; j6 <= 6; ++j6) {
            this.setBlockAndMetadata(world, 0, j6, 0, super.pillarBlock, super.pillarMeta);
        }
        for (int j6 = 1; j6 <= 4; ++j6) {
            this.setBlockAndMetadata(world, 0, j6, 1, Blocks.ladder, 3);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, 4, super.brick2StairBlock, 6);
        }
        this.setBlockAndMetadata(world, 3, 5, 1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 5, 2, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 5, 3, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 6, 1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 2, 5, 1, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 2, 5, 2, Blocks.hay_block, 0);
        if (random.nextInt(3) == 0) {
            this.placeChest(world, random, 3, 5, 1, 5, LOTRChestContents.GONDOR_HOUSE_TREASURE);
        }
        this.setBlockAndMetadata(world, 3, 6, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 5, 3, super.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 5, 3, super.bedBlock, 11);
        this.placeChest(world, random, -3, 5, 1, 3, LOTRChestContents.GONDOR_HOUSE);
        this.placeBarrel(world, random, -2, 5, 1, 3, LOTRFoods.GONDOR_DRINK);
        final LOTREntityGondorMan gondorian = new LOTREntityGondorMan(world);
        this.spawnNPCAndSetHome(gondorian, world, 0, 1, 2, 8);
        return true;
    }
}
