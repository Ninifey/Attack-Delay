// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityAnimal;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityGondorFarmhand;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGondorFarmer;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorBarn extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorBarn(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -12; i2 <= 5; ++i2) {
                for (int k2 = -2; k2 <= 15; ++k2) {
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
        for (int i3 = -12; i3 <= 4; ++i3) {
            for (int k3 = -2; k3 <= 15; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.grass, 0);
                for (int j3 = -1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 10; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        for (final int i4 : new int[] { -4, 4 }) {
            for (int k4 = 0; k4 <= 13; ++k4) {
                this.setBlockAndMetadata(world, i4, 1, k4, super.rockBlock, super.rockMeta);
                if (k4 == 0 || k4 == 4 || k4 == 9 || k4 == 13) {
                    for (int j4 = 2; j4 <= 5; ++j4) {
                        this.setBlockAndMetadata(world, i4, j4, k4, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
                else {
                    for (int j4 = 2; j4 <= 5; ++j4) {
                        this.setBlockAndMetadata(world, i4, j4, k4, super.plankBlock, super.plankMeta);
                    }
                }
            }
            this.setBlockAndMetadata(world, i4, 4, 1, super.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i4, 4, 2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4, 4, 3, super.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i4, 4, 10, super.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i4, 4, 11, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4, 4, 12, super.plankStairBlock, 6);
        }
        for (final int k2 : new int[] { 0, 13 }) {
            for (int i5 = -3; i5 <= 3; ++i5) {
                final int i6 = Math.abs(i5);
                if (i6 <= 1) {
                    this.setBlockAndMetadata(world, i5, 1, k2, super.fenceGateBlock, 0);
                }
                else {
                    this.setBlockAndMetadata(world, i5, 1, k2, super.rockBlock, super.rockMeta);
                }
                if (i6 == 2) {
                    for (int j5 = 2; j5 <= 7; ++j5) {
                        this.setBlockAndMetadata(world, i5, j5, k2, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
                if (i6 == 3) {
                    for (int j5 = 2; j5 <= 5; ++j5) {
                        this.setBlockAndMetadata(world, i5, j5, k2, super.plankBlock, super.plankMeta);
                    }
                    for (int j5 = 6; j5 <= 8; ++j5) {
                        this.setBlockAndMetadata(world, i5, j5, k2, super.wallBlock, super.wallMeta);
                    }
                }
            }
            this.setBlockAndMetadata(world, -1, 4, k2, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 4, k2, super.plankStairBlock, 5);
            for (int i5 = -1; i5 <= 1; ++i5) {
                this.setBlockAndMetadata(world, i5, 5, k2, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i5, 6, k2, super.wallBlock, super.wallMeta);
                this.setBlockAndMetadata(world, i5, 8, k2, super.wallBlock, super.wallMeta);
                this.setBlockAndMetadata(world, i5, 9, k2, super.wallBlock, super.wallMeta);
            }
            this.setBlockAndMetadata(world, -1, 7, k2, super.wallBlock, super.wallMeta);
            this.setBlockAndMetadata(world, 0, 7, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 1, 7, k2, super.wallBlock, super.wallMeta);
        }
        for (final int k2 : new int[] { -1, 14 }) {
            for (int i5 = -3; i5 <= 3; ++i5) {
                this.setBlockAndMetadata(world, i5, 6, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        this.setBlockAndMetadata(world, 0, 5, -1, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 5, 14, super.plankStairBlock, 7);
        for (int k5 = -1; k5 <= 14; ++k5) {
            this.setBlockAndMetadata(world, -2, 8, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            this.setBlockAndMetadata(world, 2, 8, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            this.setBlockAndMetadata(world, 0, 10, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            this.setBlockAndMetadata(world, -5, 5, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 6, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, -4, 7, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 8, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -2, 9, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 10, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 0, 11, k5, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 1, 10, k5, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 9, k5, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 3, 8, k5, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 7, k5, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 6, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 5, 5, k5, super.roofStairBlock, 0);
            if (k5 == -1 || k5 == 14) {
                this.setBlockAndMetadata(world, -4, 5, k5, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, -3, 7, k5, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, -1, 9, k5, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 1, 9, k5, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, 3, 7, k5, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, 4, 5, k5, super.roofStairBlock, 5);
            }
        }
        for (int k5 = 1; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -3, 1, k5, Blocks.hay_block, 0);
        }
        for (int k5 = 1; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -3, 2, k5, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -2, 1, k5, Blocks.hay_block, 0);
        }
        for (int k5 = 10; k5 <= 12; ++k5) {
            this.setBlockAndMetadata(world, -3, 1, k5, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 3, 1, k5, Blocks.hay_block, 0);
        }
        for (int k5 = 11; k5 <= 12; ++k5) {
            this.setBlockAndMetadata(world, -3, 2, k5, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -2, 1, k5, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 2, 1, k5, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 3, 2, k5, Blocks.hay_block, 0);
        }
        this.setBlockAndMetadata(world, -3, 1, 4, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -3, 1, 9, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 1, 9, Blocks.crafting_table, 0);
        for (int j6 = 2; j6 <= 4; ++j6) {
            this.setBlockAndMetadata(world, -3, j6, 4, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, -3, j6, 9, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 3, j6, 9, super.fenceBlock, super.fenceMeta);
        }
        for (int k5 = 1; k5 <= 12; ++k5) {
            for (int i7 = -3; i7 <= 3; ++i7) {
                this.setBlockAndMetadata(world, i7, 5, k5, super.plankBlock, super.plankMeta);
            }
            this.setBlockAndMetadata(world, -3, 7, k5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, -1, 9, k5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 1, 9, k5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 3, 7, k5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        }
        this.setBlockAndMetadata(world, 0, 4, 4, LOTRMod.chandelier, 1);
        this.setBlockAndMetadata(world, 0, 4, 9, LOTRMod.chandelier, 1);
        for (int step = 0; step <= 3; ++step) {
            this.setBlockAndMetadata(world, 3, 1 + step, 2 + step, super.plankStairBlock, 2);
            this.setBlockAndMetadata(world, 3, 1 + step, 3 + step, super.plankStairBlock, 7);
        }
        this.setBlockAndMetadata(world, 2, 4, 6, super.plankStairBlock, 5);
        for (int k5 = 3; k5 <= 5; ++k5) {
            this.setAir(world, 3, 5, k5);
        }
        this.setAir(world, 3, 5, 6);
        this.setAir(world, 3, 7, 6);
        this.setBlockAndMetadata(world, 2, 5, 6, super.plankStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 6, 2, super.fenceBlock, super.fenceMeta);
        for (int k5 = 2; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, 2, 6, k5, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, 1, 6, 5, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 1, 6, 6, super.fenceGateBlock, 1);
        this.setBlockAndMetadata(world, 1, 6, 7, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 6, 7, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 3, 6, 7, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 3, 6, 9, super.bedBlock, 2);
        this.setBlockAndMetadata(world, 3, 6, 8, super.bedBlock, 10);
        this.setBlockAndMetadata(world, 2, 6, 12, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 6, 12, super.tableBlock, 0);
        this.placeChest(world, random, 3, 6, 11, 5, LOTRChestContents.GONDOR_HOUSE);
        for (int i3 = -3; i3 <= -2; ++i3) {
            for (int k3 = 7; k3 <= 8; ++k3) {
                this.setBlockAndMetadata(world, i3, 6, k3, super.plankBlock, super.plankMeta);
            }
        }
        this.placeBarrel(world, random, -3, 6, 6, 4, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -2, 7, 7, 3, LOTRFoods.GONDOR_DRINK);
        this.placePlateWithCertainty(world, random, -2, 7, 8, super.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, 0, 9, 4, LOTRMod.chandelier, 1);
        this.setBlockAndMetadata(world, 0, 9, 9, LOTRMod.chandelier, 1);
        for (int k5 = 1; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, -3, 6, k5, Blocks.hay_block, 0);
        }
        for (int k5 = 1; k5 <= 4; ++k5) {
            this.setBlockAndMetadata(world, -2, 6, k5, Blocks.hay_block, 0);
        }
        for (int k5 = 2; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -2, 7, k5, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -1, 6, k5, Blocks.hay_block, 0);
        }
        this.setBlockAndMetadata(world, -3, 6, 11, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 6, 12, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 7, 12, Blocks.hay_block, 0);
        for (int k5 = 10; k5 <= 12; ++k5) {
            this.setBlockAndMetadata(world, -2, 6, k5, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -1, 6, k5, Blocks.hay_block, 0);
        }
        this.setBlockAndMetadata(world, -2, 7, 11, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -1, 7, 11, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 0, 6, 11, Blocks.hay_block, 0);
        if (random.nextInt(3) == 0) {
            if (random.nextBoolean()) {
                this.placeChest(world, random, -2, 6, 3, 4, LOTRChestContents.GONDOR_HOUSE_TREASURE);
            }
            else {
                this.placeChest(world, random, -1, 6, 11, 4, LOTRChestContents.GONDOR_HOUSE_TREASURE);
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = 0; k3 <= 13; ++k3) {
                if (this.isOpaque(world, i3, 1, k3)) {
                    this.setGrassToDirt(world, i3, 0, k3);
                }
            }
        }
        for (int animals = 3 + random.nextInt(6), l = 0; l < animals; ++l) {
            final EntityCreature animal = (EntityCreature)getRandomAnimal(world, random);
            this.spawnNPCAndSetHome(animal, world, 0, 1, 6, 0);
            animal.detachHome();
        }
        for (int k3 = 1; k3 <= 12; ++k3) {
            this.setBlockAndMetadata(world, -10, 1, k3, super.rockWallBlock, super.rockWallMeta);
        }
        for (final int k4 : new int[] { 0, 13 }) {
            this.setBlockAndMetadata(world, -10, 1, k4, super.rockWallBlock, super.rockWallMeta);
            this.setBlockAndMetadata(world, -10, 2, k4, Blocks.torch, 5);
            this.setBlockAndMetadata(world, -9, 1, k4, super.fenceGateBlock, 0);
            for (int i8 = -8; i8 <= -5; ++i8) {
                this.setBlockAndMetadata(world, i8, 1, k4, super.rockWallBlock, super.rockWallMeta);
            }
            this.setBlockAndMetadata(world, -5, 2, k4, Blocks.torch, 5);
        }
        for (int i7 = -9; i7 <= -5; ++i7) {
            for (int k6 = 1; k6 <= 12; ++k6) {
                if (i7 == -5 && k6 >= 2 && k6 <= 11) {
                    this.setBlockAndMetadata(world, i7, -1, k6, Blocks.dirt, 0);
                    this.setBlockAndMetadata(world, i7, 0, k6, Blocks.water, 0);
                    this.setBlockAndMetadata(world, i7, 1, k6, super.rockSlabBlock, super.rockSlabMeta);
                }
                else if (i7 >= -8 && k6 >= 2 && k6 <= 11) {
                    this.setBlockAndMetadata(world, i7, 0, k6, Blocks.farmland, 7);
                    this.setBlockAndMetadata(world, i7, 1, k6, super.cropBlock, super.cropMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i7, 0, k6, LOTRMod.dirtPath, 0);
                }
            }
        }
        this.setBlockAndMetadata(world, -10, 2, 6, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -10, 3, 6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -10, 4, 6, Blocks.pumpkin, 3);
        final LOTREntityGondorMan farmer = new LOTREntityGondorFarmer(world);
        this.spawnNPCAndSetHome(farmer, world, 0, 6, 8, 16);
        for (int farmhands = 1 + random.nextInt(3), m = 0; m < farmhands; ++m) {
            final LOTREntityGondorFarmhand farmhand = new LOTREntityGondorFarmhand(world);
            this.spawnNPCAndSetHome(farmhand, world, -7, 1, 6, 12);
            farmhand.seedsItem = super.seedItem;
        }
        return true;
    }
    
    public static EntityAnimal getRandomAnimal(final World world, final Random random) {
        final int animal = random.nextInt(4);
        if (animal == 0) {
            return (EntityAnimal)new EntityCow(world);
        }
        if (animal == 1) {
            return (EntityAnimal)new EntityPig(world);
        }
        if (animal == 2) {
            return (EntityAnimal)new EntitySheep(world);
        }
        if (animal == 3) {
            return (EntityAnimal)new EntityChicken(world);
        }
        return null;
    }
}
