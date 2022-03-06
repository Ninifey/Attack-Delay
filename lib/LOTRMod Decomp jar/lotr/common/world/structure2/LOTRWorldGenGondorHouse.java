// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRFoods;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorHouse extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
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
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 != 4 || k4 != 4) {
                    if ((i4 == 3 && k4 == 4) || (k4 == 3 && i4 == 4)) {
                        for (int j2 = 4; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                            this.setGrassToDirt(world, i3, j2 - 1, k3);
                        }
                    }
                    else if (i4 == 4 || k4 == 4) {
                        for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.rockBlock, super.rockMeta);
                            this.setGrassToDirt(world, i3, j2 - 1, k3);
                        }
                        for (int j2 = 1; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.wallBlock, super.wallMeta);
                        }
                        if (k4 == 4) {
                            this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                        }
                    }
                    else {
                        for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.rockBlock, super.rockMeta);
                            this.setGrassToDirt(world, i3, j2 - 1, k3);
                        }
                        for (int j2 = 1; j2 <= 5; ++j2) {
                            this.setAir(world, i3, j2, k3);
                        }
                    }
                }
            }
        }
        for (final int i5 : new int[] { -4, 4 }) {
            for (int j2 = 1; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i5, j2, 0, super.woodBeamBlock, super.woodBeamMeta);
            }
            this.setBlockAndMetadata(world, i5, 2, -2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i5, 2, 2, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -2, 2, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 2, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 0, -4, super.rockBlock, super.rockMeta);
        this.setBlockAndMetadata(world, 0, 1, -4, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, super.doorBlock, 8);
        for (int k5 = -5; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, -4, 4, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 5, k5, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, -2, 5, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, -1, 6, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 0, 6, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            this.setBlockAndMetadata(world, 0, 7, k5, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 1, 6, k5, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 5, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 3, 5, k5, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 4, 4, k5, super.roofStairBlock, 0);
            final int k6 = Math.abs(k5);
            if (k6 == 5) {
                this.setBlockAndMetadata(world, -3, 4, k5, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, -1, 5, k5, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 1, 5, k5, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, 3, 4, k5, super.roofStairBlock, 5);
            }
            else if (k6 == 4) {
                this.setBlockAndMetadata(world, -1, 5, k5, super.wallBlock, super.wallMeta);
                this.setBlockAndMetadata(world, 0, 5, k5, super.wallBlock, super.wallMeta);
                this.setBlockAndMetadata(world, 1, 5, k5, super.wallBlock, super.wallMeta);
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 3; k3 <= 5; ++k3) {
                for (int j3 = 7; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
            this.setBlockAndMetadata(world, i3, 8, 3, super.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 8, 5, super.brickStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, 8, 4, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 8, 4, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 8, 4, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 9, 4, super.brickWallBlock, super.brickWallMeta);
        for (int i3 = -3; i3 <= -2; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -3, super.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 1, -2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, i3, 1, -1, super.plankStairBlock, 2);
            if (random.nextBoolean()) {
                this.placePlateWithCertainty(world, random, i3, 2, -2, super.plateBlock, LOTRFoods.GONDOR);
            }
            else {
                final int drinkMeta = random.nextInt(4);
                this.placeMug(world, random, i3, 2, -2, drinkMeta, LOTRFoods.GONDOR_DRINK);
            }
        }
        this.setBlockAndMetadata(world, 2, 1, -3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 1, -3, super.plankBlock, super.plankMeta);
        this.placePlateWithCertainty(world, random, 3, 2, -3, super.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, 3, 1, -2, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 3, 1, -1, super.tableBlock, 0);
        this.setBlockAndMetadata(world, -2, 1, 1, super.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 1, 1, super.bedBlock, 11);
        this.setBlockAndMetadata(world, 2, 1, 1, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 3, 1, 1, super.bedBlock, 9);
        this.setBlockAndMetadata(world, -3, 1, 3, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 3, 1, 3, super.plankBlock, super.plankMeta);
        this.placeChest(world, random, -2, 1, 3, 2, super.chestContents);
        this.placeChest(world, random, 2, 1, 3, 2, super.chestContents);
        this.setBlockAndMetadata(world, 0, 1, 3, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 2, 3, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 0, 0, 4, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, (Block)Blocks.fire, 0);
        for (int j4 = 2; j4 <= 7; ++j4) {
            this.setAir(world, 0, j4, 4);
        }
        this.spawnItemFrame(world, 0, 3, 3, 2, this.getGondorFramedItem(random));
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
            this.spawnNPCAndSetHome(gondorMan, world, 0, 1, 0, 16);
        }
        return true;
    }
}
