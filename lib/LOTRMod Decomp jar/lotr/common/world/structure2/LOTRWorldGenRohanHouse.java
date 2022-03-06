// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.List;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityRohanMan;
import java.util.ArrayList;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanHouse extends LOTRWorldGenRohanStructure
{
    private boolean hasStoneBase;
    private boolean setHasBase;
    
    public LOTRWorldGenRohanHouse(final boolean flag) {
        super(flag);
        this.setHasBase = false;
    }
    
    public LOTRWorldGenRohanHouse setHasBase(final boolean flag) {
        this.hasStoneBase = flag;
        this.setHasBase = true;
        return this;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!this.setHasBase) {
            this.hasStoneBase = random.nextBoolean();
        }
        this.setOriginAndRotation(world, i, j, k, rotation, this.hasStoneBase ? 10 : 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 5; ++i2) {
                for (int k2 = -10; k2 <= 10; ++k2) {
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
        if (this.hasStoneBase) {
            for (int i3 = -6; i3 <= 5; ++i3) {
                for (int k3 = -9; k3 <= 8; ++k3) {
                    final boolean corner = (i3 == -6 || i3 == 5) && Math.abs(k3) == 8;
                    final boolean stairSide = (i3 == -2 || i3 == 1) && k3 == -9;
                    final boolean stair = i3 >= -1 && i3 <= 0 && k3 == -9;
                    if (corner || stairSide) {
                        for (int j3 = 1; (j3 >= 1 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                        if (corner) {
                            this.setBlockAndMetadata(world, i3, 2, k3, super.rockSlabBlock, super.rockSlabMeta);
                        }
                    }
                    else if (stair || k3 >= -8) {
                        for (int j3 = 1; (j3 >= 1 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                        if (stair) {
                            this.setBlockAndMetadata(world, i3, 1, k3, super.brickStairBlock, 2);
                        }
                    }
                }
            }
            ++super.originY;
        }
        else {
            for (int i3 = -3; i3 <= 2; ++i3) {
                for (int k3 = -5; k3 <= 4; ++k3) {
                    if (k3 >= -4 || (i3 >= -1 && i3 <= 0)) {
                        for (int j4 = 0; (j4 >= 0 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.plank2Block, super.plank2Meta);
                            this.setGrassToDirt(world, i3, j4 - 1, k3);
                        }
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 4; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
                for (int j4 = 1; j4 <= 8; ++j4) {
                    this.setAir(world, i3, j4, k3);
                }
            }
        }
        for (int i3 = -3; i3 <= 2; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                if (random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                }
            }
        }
        for (int i3 = -4; i3 <= 3; ++i3) {
            for (int k3 = -7; k3 <= 5; ++k3) {
                boolean beam = false;
                if (k3 == -7 && (i3 == -4 || i3 == -2 || i3 == 1 || i3 == 3)) {
                    beam = true;
                }
                else if (Math.abs(k3) == 5 && (i3 == -4 || i3 == 3)) {
                    beam = true;
                }
                if (beam) {
                    for (int j5 = 3; (j5 >= 1 || !this.isOpaque(world, i3, j5, k3)) && this.getY(j5) >= 0; --j5) {
                        this.setBlockAndMetadata(world, i3, j5, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j5 - 1, k3);
                    }
                }
                else if (k3 >= -5) {
                    if (i3 == -4 || i3 == 3) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.plank2Block, super.plank2Meta);
                        this.setGrassToDirt(world, i3, 0, k3);
                        for (int j5 = 2; j5 <= 3; ++j5) {
                            this.setBlockAndMetadata(world, i3, j5, k3, super.plankBlock, super.plankMeta);
                        }
                    }
                    else if (Math.abs(k3) == 5) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.plank2Block, super.plank2Meta);
                        this.setGrassToDirt(world, i3, 0, k3);
                        for (int j5 = 2; j5 <= 3; ++j5) {
                            this.setBlockAndMetadata(world, i3, j5, k3, super.plankBlock, super.plankMeta);
                        }
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                }
            }
        }
        for (int k4 = -7; k4 <= 6; ++k4) {
            final boolean roofEdge = k4 == -7 || k4 == 6;
            for (int step = 0; step <= 4; ++step) {
                final int j5 = 3 + step;
                Block stairBlock = super.roofStairBlock;
                if (step == 4 || roofEdge) {
                    stairBlock = super.plank2StairBlock;
                }
                this.setBlockAndMetadata(world, -5 + step, j5, k4, stairBlock, 1);
                this.setBlockAndMetadata(world, 4 - step, j5, k4, stairBlock, 0);
                if (roofEdge && step <= 3) {
                    this.setBlockAndMetadata(world, -4 + step, j5, k4, stairBlock, 4);
                    this.setBlockAndMetadata(world, 3 - step, j5, k4, stairBlock, 5);
                }
                if (k4 >= -4 && k4 <= 4 && step >= 1 && step <= 3) {
                    this.setBlockAndMetadata(world, -4 + step, j5, k4, stairBlock, 4);
                    this.setBlockAndMetadata(world, 3 - step, j5, k4, stairBlock, 5);
                }
            }
        }
        for (final int k2 : new int[] { -6, -5, 5 }) {
            this.setBlockAndMetadata(world, -2, 5, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -1, 5, k2, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 5, k2, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 1, 5, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -1, 6, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 0, 6, k2, super.plankBlock, super.plankMeta);
        }
        for (final int k2 : new int[] { -7, 6 }) {
            this.setBlockAndMetadata(world, -1, 8, k2, super.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, 0, 8, k2, super.plank2StairBlock, 1);
        }
        for (int i3 = -4; i3 <= 3; ++i3) {
            if (i3 == -4 || i3 == -2 || i3 == 1 || i3 == 3) {
                this.setBlockAndMetadata(world, i3, 3, -7, super.plank2Block, super.plank2Meta);
            }
            else {
                this.setBlockAndMetadata(world, i3, 3, -7, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            }
            if (i3 >= -3 && i3 <= 2) {
                this.setBlockAndMetadata(world, i3, 3, 6, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            }
        }
        for (int i3 = -3; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -6, super.plankBlock, super.plankMeta);
        }
        this.setBlockAndMetadata(world, -4, 3, -6, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        this.setBlockAndMetadata(world, 3, 3, -6, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        this.setBlockAndMetadata(world, -1, 4, -6, super.rockSlabBlock, super.rockSlabMeta);
        this.setBlockAndMetadata(world, 0, 4, -6, super.rockSlabBlock, super.rockSlabMeta);
        this.setBlockAndMetadata(world, -2, 4, -7, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 1, 4, -7, super.fenceBlock, super.fenceMeta);
        for (int i3 = -1; i3 <= 0; ++i3) {
            for (int j6 = 1; j6 <= 2; ++j6) {
                this.setAir(world, i3, j6, -5);
            }
        }
        this.setBlockAndMetadata(world, -1, 3, -5, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 3, -5, super.plankStairBlock, 5);
        for (final int i4 : new int[] { -5, 4 }) {
            for (final int k5 : new int[] { -7, 6 }) {
                for (int j7 = 2; (j7 >= 1 || !this.isOpaque(world, i4, j7, k5)) && this.getY(j7) >= 0; --j7) {
                    this.setBlockAndMetadata(world, i4, j7, k5, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        for (final int i4 : new int[] { -4, 3 }) {
            this.setAir(world, i4, 2, -2);
            this.setBlockAndMetadata(world, i4, 3, -2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 4, -2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i4, 2, -3, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4, 2, -1, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4, 3, -3, super.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i4, 3, -1, super.plankStairBlock, 6);
        }
        for (final int i4 : new int[] { -5, 4 }) {
            this.setBlockAndMetadata(world, i4, 1, -3, super.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i4, 1, -2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 1, -1, super.plankStairBlock, 6);
            for (int k6 = -3; k6 <= -1; ++k6) {
                if (random.nextBoolean()) {
                    this.placeFlowerPot(world, i4, 2, k6, this.getRandomFlower(world, random));
                }
            }
            this.setBlockAndMetadata(world, i4, 3, -4, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i4, 3, -3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 4, -3, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i4, 4, -2, super.roofBlock, super.roofMeta);
            this.setAir(world, i4, 3, -2);
            this.setBlockAndMetadata(world, i4, 3, -1, super.roofSlabBlock, super.roofSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 4, -1, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i4, 3, 0, super.roofBlock, super.roofMeta);
            for (final int k5 : new int[] { -4, 0 }) {
                for (int j7 = 2; (j7 >= 1 || !this.isOpaque(world, i4, j7, k5)) && this.getY(j7) >= 0; --j7) {
                    this.setBlockAndMetadata(world, i4, j7, k5, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -4, 2, 3, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 2, 5, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 1, 2, 5, super.fenceBlock, super.fenceMeta);
        for (int k4 = 1; k4 <= 3; ++k4) {
            for (int i5 = 2; i5 <= 3; ++i5) {
                for (int j4 = 5; (j4 >= 0 || !this.isOpaque(world, i5, j4, k4)) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i5, j4, k4, super.brickBlock, super.brickMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 3, 5, 1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 3, 5, 3, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 2, 6, 1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 2, 6, 3, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 3, 6, 2, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 6, 2, super.brickBlock, super.brickMeta);
        for (int j8 = 6; j8 <= 8; ++j8) {
            this.setBlockAndMetadata(world, 2, j8, 2, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 9, 2, super.rockSlabBlock, super.rockSlabMeta);
        for (int k4 = 0; k4 <= 4; ++k4) {
            this.setBlockAndMetadata(world, 2, 4, k4, super.brickBlock, super.brickMeta);
            for (int step2 = 0; step2 <= 1; ++step2) {
                this.setBlockAndMetadata(world, 1 - step2, 5 + step2, k4, super.brickStairBlock, 5);
            }
        }
        for (final int k2 : new int[] { 0, 4 }) {
            for (int j2 = 1; j2 <= 3; ++j2) {
                this.setBlockAndMetadata(world, 2, j2, k2, super.rockWallBlock, super.rockWallMeta);
            }
        }
        this.setBlockAndMetadata(world, 2, 0, 2, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 2, 1, 2, (Block)Blocks.fire, 0);
        this.setBlockAndMetadata(world, 2, 2, 2, Blocks.furnace, 5);
        this.setBlockAndMetadata(world, 2, 3, 2, super.brickCarvedBlock, super.brickCarvedMeta);
        this.setBlockAndMetadata(world, 1, 0, 2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.setBlockAndMetadata(world, 1, 1, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 1, 2, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 1, 1, 3, super.brickBlock, super.brickMeta);
        for (int k4 = 1; k4 <= 3; ++k4) {
            this.setBlockAndMetadata(world, 1, 2, k4, super.rockSlabBlock, super.rockSlabMeta);
        }
        this.placeWeaponRack(world, 1, 3, 2, 7, this.getRandomRohanWeapon(random));
        this.spawnItemFrame(world, 2, 4, 2, 3, this.getRohanFramedItem(random));
        for (int i3 = -2; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -4, super.plank2SlabBlock, super.plank2SlabMeta);
        }
        this.setBlockAndMetadata(world, -3, 1, -4, super.plankStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 1, -3, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 1, -2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -3, 1, -1, super.tableBlock, 0);
        this.placeChest(world, random, -3, 1, 0, 4, LOTRChestContents.ROHAN_HOUSE);
        this.setBlockAndMetadata(world, 2, 1, -4, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 2, 1, -3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 2, 1, -2, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 2, 1, -1, (Block)Blocks.cauldron, 3);
        this.placeBarrel(world, random, 2, 2, -4, 5, LOTRFoods.ROHAN_DRINK);
        this.placeMug(world, random, 2, 2, -3, 1, LOTRFoods.ROHAN_DRINK);
        if (random.nextBoolean()) {
            this.placePlateWithCertainty(world, random, 2, 2, -2, super.plateBlock, LOTRFoods.ROHAN);
        }
        else {
            this.setBlockAndMetadata(world, 2, 2, -2, super.plateBlock, 0);
        }
        if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, 3, 2, -2, this.getRandomCakeBlock(random), 0);
        }
        for (int k4 = 2; k4 <= 3; ++k4) {
            this.setBlockAndMetadata(world, -2, 1, k4, super.bedBlock, 3);
            this.setBlockAndMetadata(world, -3, 1, k4, super.bedBlock, 11);
            this.setBlockAndMetadata(world, -3, 3, k4, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        }
        for (final int k2 : new int[] { 1, 4 }) {
            for (int j2 = 1; j2 <= 2; ++j2) {
                this.setBlockAndMetadata(world, -3, j2, k2, super.fenceBlock, super.fenceMeta);
            }
            this.setBlockAndMetadata(world, -3, 3, k2, super.plank2Block, super.plank2Meta);
        }
        this.setBlockAndMetadata(world, -3, 3, -4, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 3, -4, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 4, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 4, 4, Blocks.torch, 4);
        this.spawnItemFrame(world, -2, 3, -5, 0, this.getRohanFramedItem(random));
        this.spawnItemFrame(world, 1, 3, -5, 0, this.getRohanFramedItem(random));
        if (random.nextInt(3) != 0) {
            for (int i3 = -1; i3 <= 0; ++i3) {
                for (int k3 = -3; k3 <= -1; ++k3) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.carpetBlock, super.carpetMeta);
                }
            }
        }
        if (random.nextInt(3) != 0) {
            final boolean hayOrWood = random.nextBoolean();
            for (int i5 = -1; i5 <= 1; ++i5) {
                for (int k7 = 6; k7 <= 7; ++k7) {
                    if (k7 == 6 || i5 == 0) {
                        int j5;
                        for (j5 = 1; !this.isOpaque(world, i5, j5 - 1, k7) && this.getY(j5) >= 0; --j5) {}
                        int j9 = j5;
                        if (i5 == 0 && k7 == 6) {
                            ++j9;
                        }
                        for (int j10 = j5; j10 <= j9; ++j10) {
                            if (hayOrWood) {
                                this.setBlockAndMetadata(world, i5, j10, k7, Blocks.hay_block, 0);
                            }
                            else {
                                this.setBlockAndMetadata(world, i5, j10, k7, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                            }
                        }
                        this.setGrassToDirt(world, i5, j5 - 1, k7);
                    }
                }
            }
        }
        if (random.nextBoolean()) {
            int j8 = 2;
            final int k3 = 6;
            final List<Integer> chestCoords = new ArrayList<Integer>();
            for (int i4 = -4; i4 <= 3; ++i4) {
                if (!this.isOpaque(world, i4, j8, k3)) {
                    chestCoords.add(i4);
                }
            }
            if (!chestCoords.isEmpty()) {
                int i4;
                for (i4 = chestCoords.get(random.nextInt(chestCoords.size())); !this.isOpaque(world, i4, j8 - 1, k3) && this.getY(j8) >= 0; --j8) {}
                this.placeChest(world, random, i4, j8, k3, 3, LOTRChestContents.ROHAN_HOUSE);
            }
        }
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityRohanMan rohirrim = new LOTREntityRohanMan(world);
            this.spawnNPCAndSetHome(rohirrim, world, 0, 1, 0, 16);
        }
        return true;
    }
}
