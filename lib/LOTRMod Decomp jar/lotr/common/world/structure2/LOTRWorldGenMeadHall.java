// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTREntityRohanShieldmaiden;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityRohanMeadhost;
import net.minecraft.block.Block;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;

public class LOTRWorldGenMeadHall extends LOTRWorldGenRohanStructure
{
    private String[] meadHallName;
    private String[] meadNameSign;
    private String meadNameNPC;
    
    public LOTRWorldGenMeadHall(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        this.meadHallName = LOTRNames.getRohanMeadHallName(random);
        this.meadNameSign = new String[] { "", this.meadHallName[0], this.meadHallName[1], "" };
        this.meadNameNPC = this.meadHallName[0] + " " + this.meadHallName[1];
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -8; i2 <= 8; ++i2) {
                for (int k2 = 0; k2 <= 28; ++k2) {
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
        for (int i3 = -8; i3 <= 8; ++i3) {
            for (int k3 = 0; k3 <= 28; ++k3) {
                for (int j3 = 1; j3 <= 11; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                final boolean corner = Math.abs(i3) == 8 && (k3 == 0 || k3 == 28);
                final boolean stairSide = Math.abs(i3) == 3 && k3 == 0;
                if (corner || stairSide) {
                    for (int j2 = 1; (j2 >= 1 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (corner) {
                        this.setBlockAndMetadata(world, i3, 2, k3, super.rockSlabBlock, super.rockSlabMeta);
                    }
                }
                else {
                    for (int j2 = 1; (j2 >= 1 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (Math.abs(i3) <= 4 && k3 >= 4 && k3 <= 21 && random.nextInt(4) == 0) {
                        this.setBlockAndMetadata(world, i3, 2, k3, LOTRMod.thatchFloor, 0);
                    }
                }
            }
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            final int i4 = Math.abs(i3);
            if (i4 <= 2) {
                this.setBlockAndMetadata(world, i3, 1, 0, super.brickStairBlock, 2);
            }
            if (i4 == 3) {
                this.setBlockAndMetadata(world, i3, 2, 0, super.rockWallBlock, super.rockWallMeta);
            }
            if (i4 >= 4 && i4 <= 7) {
                this.setBlockAndMetadata(world, i3, 2, 0, super.fenceBlock, super.fenceMeta);
            }
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k3 = 2; k3 <= 26; ++k3) {
                final int i5 = Math.abs(i3);
                if (i5 == 5 && (k3 == 2 || k3 == 26)) {
                    for (int j4 = 2; j4 <= 4; ++j4) {
                        this.setBlockAndMetadata(world, i3, j4, k3, super.woodBeamRohanBlock, super.woodBeamRohanMeta);
                    }
                }
                else {
                    if (i5 == 5 && k3 >= 3 && k3 <= 25) {
                        this.setBlockAndMetadata(world, i3, 2, k3, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i3, 3, k3, super.plank2Block, super.plank2Meta);
                        this.setBlockAndMetadata(world, i3, 4, k3, super.plankBlock, super.plankMeta);
                    }
                    if (i5 <= 4 && k3 == 3) {
                        for (int j4 = 2; j4 <= 4; ++j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.plankBlock, super.plankMeta);
                        }
                        this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    if (i5 <= 4 && k3 == 25) {
                        this.setBlockAndMetadata(world, i3, 2, k3, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i3, 3, k3, super.plank2Block, super.plank2Meta);
                        this.setBlockAndMetadata(world, i3, 4, k3, super.plankBlock, super.plankMeta);
                        this.setBlockAndMetadata(world, i3, 5, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                }
                if (k3 >= 3 && k3 <= 25) {
                    if (i5 == 6) {
                        if (k3 % 6 == 2) {
                            for (int j4 = 2; j4 <= 4; ++j4) {
                                this.setBlockAndMetadata(world, i3, j4, k3, super.woodBeamBlock, super.woodBeamMeta);
                            }
                        }
                        else {
                            if (k3 == 3 || k3 == 25) {
                                this.setBlockAndMetadata(world, i3, 2, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                            }
                            else {
                                this.setBlockAndMetadata(world, i3, 2, k3, super.rockSlabBlock, super.rockSlabMeta | 0x8);
                            }
                            if (k3 % 6 == 3 || k3 % 6 == 1) {
                                this.setBlockAndMetadata(world, i3, 4, k3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                            }
                            if (random.nextInt(5) == 0) {
                                this.placeFlowerPot(world, i3, 3, k3, this.getRandomFlower(world, random));
                            }
                        }
                    }
                    if (i5 == 5 && k3 % 6 == 5) {
                        this.setBlockAndMetadata(world, i3, 3, k3, super.plank2StairBlock, (i3 <= 0) ? 1 : 0);
                        this.setBlockAndMetadata(world, i3, 4, k3, super.fenceBlock, super.fenceMeta);
                    }
                }
            }
        }
        for (int k4 = 3; k4 <= 25; ++k4) {
            for (int step = 0; step <= 5; ++step) {
                final int i2 = 1 + step;
                final int j4 = 7 - step / 2;
                Block block = super.roofSlabBlock;
                int meta = super.roofSlabMeta;
                if (k4 == 3 || k4 == 25) {
                    block = super.plankSlabBlock;
                    meta = super.plankSlabMeta;
                }
                if (step % 2 == 0) {
                    meta |= 0x8;
                }
                this.setBlockAndMetadata(world, -i2, j4, k4, block, meta);
                this.setBlockAndMetadata(world, i2, j4, k4, block, meta);
            }
        }
        for (int k4 = 2; k4 <= 26; ++k4) {
            this.setBlockAndMetadata(world, 0, 7, k4, super.logBlock, super.logMeta | 0x8);
            this.setBlockAndMetadata(world, 0, 8, k4, super.plank2SlabBlock, super.plank2SlabMeta);
            if (k4 % 12 == 2) {
                for (int step = 0; step <= 6; ++step) {
                    final int i2 = 1 + step;
                    final int j4 = 8 - (step + 1) / 2;
                    for (final int i6 : new int[] { -i2, i2 }) {
                        if (step % 2 == 0) {
                            this.setBlockAndMetadata(world, i6, j4, k4, super.plank2SlabBlock, super.plank2SlabMeta);
                            this.setBlockAndMetadata(world, i6, j4 - 1, k4, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                        }
                        else {
                            this.setBlockAndMetadata(world, i6, j4, k4, super.plank2Block, super.plank2Meta);
                        }
                    }
                }
                this.setBlockAndMetadata(world, 0, 8, k4, super.plank2Block, super.plank2Meta);
                this.setBlockAndMetadata(world, 0, 9, k4, super.plank2SlabBlock, super.plank2SlabMeta);
                this.setBlockAndMetadata(world, -1, 9, k4, super.plank2StairBlock, 5);
                this.setBlockAndMetadata(world, 1, 9, k4, super.plank2StairBlock, 4);
                for (int j5 = 2; j5 <= 4; ++j5) {
                    this.setBlockAndMetadata(world, -7, j5, k4, super.fence2Block, super.fence2Meta);
                    this.setBlockAndMetadata(world, 7, j5, k4, super.fence2Block, super.fence2Meta);
                }
                if (k4 == 2 || k4 == 26) {
                    this.setBlockAndMetadata(world, -6, 4, k4, super.fence2Block, super.fence2Meta);
                    this.setBlockAndMetadata(world, 6, 4, k4, super.fence2Block, super.fence2Meta);
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            final int i4 = Math.abs(i3);
            if (i4 == 5) {
                this.setBlockAndMetadata(world, i3, 5, 3, super.plankBlock, super.plankMeta);
            }
            if (i4 >= 2 && i4 <= 3) {
                this.setBlockAndMetadata(world, i3, 6, 3, super.plankBlock, super.plankMeta);
            }
            if (i4 == 1) {
                this.setBlockAndMetadata(world, i3, 6, 3, super.plankStairBlock, (i3 > 0) ? 5 : 4);
            }
            if (i4 == 1) {
                this.setBlockAndMetadata(world, i3, 7, 3, super.plankBlock, super.plankMeta);
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            final int i4 = Math.abs(i3);
            if (i4 == 5) {
                this.setBlockAndMetadata(world, i3, 5, 25, super.plankBlock, super.plankMeta);
            }
            if (i4 == 3) {
                this.setBlockAndMetadata(world, i3, 6, 25, super.plankBlock, super.plankMeta);
            }
            if (i4 == 2) {
                for (int j3 = 2; j3 <= 6; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, 25, super.woodBeamRohanBlock, super.woodBeamRohanMeta);
                }
            }
            if (i4 <= 1) {
                for (int j3 = 2; j3 <= 8; ++j3) {
                    if (j3 != 5) {
                        this.setBlockAndMetadata(world, i3, j3, 25, super.brickBlock, super.brickMeta);
                    }
                }
            }
            if (i4 == 0) {
                for (int j3 = 9; j3 <= 11; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, 25, super.brickBlock, super.brickMeta);
                }
                this.setBlockAndMetadata(world, i3, 12, 25, super.rockSlabBlock, super.rockSlabMeta);
            }
            if (i4 == 1) {
                this.setBlockAndMetadata(world, i3, 9, 25, super.brickStairBlock, (i3 <= 0) ? 1 : 0);
            }
            if (i4 <= 2) {
                for (int k5 = 23; k5 <= 24; ++k5) {
                    for (int j4 = 2; j4 <= 6; ++j4) {
                        this.setBlockAndMetadata(world, i3, j4, k5, super.brickBlock, super.brickMeta);
                    }
                }
            }
            if (i4 <= 1) {
                this.setBlockAndMetadata(world, i3, 7, 24, super.brickBlock, super.brickMeta);
            }
            if (i4 == 1) {
                this.setBlockAndMetadata(world, i3, 8, 24, super.brickStairBlock, (i3 <= 0) ? 1 : 0);
            }
            if (i4 == 0) {
                this.setBlockAndMetadata(world, i3, 8, 24, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i3, 9, 24, super.brickStairBlock, 2);
            }
            if (i4 >= 3 && i4 <= 4) {
                for (int j3 = 2; j3 <= 5; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, 24, super.brickBlock, super.brickMeta);
                }
            }
            if (i4 <= 1) {
                this.setBlockAndMetadata(world, i3, 2, 26, super.brickBlock, super.brickMeta);
            }
            if (i4 == 1) {
                this.setBlockAndMetadata(world, i3, 3, 26, super.brickStairBlock, (i3 <= 0) ? 1 : 0);
            }
            if (i4 == 0) {
                this.setBlockAndMetadata(world, i3, 3, 26, super.brickBlock, super.brickMeta);
            }
        }
        for (final int k2 : new int[] { 2, 26 }) {
            for (int i7 = -5; i7 <= 5; ++i7) {
                final int i8 = Math.abs(i7);
                if (i8 == 2 || i8 == 5) {
                    this.setBlockAndMetadata(world, i7, 5, k2, super.woodBeamRohanBlock, super.woodBeamRohanMeta | 0x8);
                }
                if (i8 <= 1) {
                    this.setBlockAndMetadata(world, i7, 5, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
            }
        }
        for (final int i9 : new int[] { -4, 3 }) {
            this.setBlockAndMetadata(world, i9, 2, 2, super.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, i9 + 1, 2, 2, super.plank2StairBlock, 5);
            for (int i10 = i9; i10 <= i9 + 1; ++i10) {
                if (random.nextBoolean()) {
                    this.placeFlowerPot(world, i10, 3, 2, this.getRandomFlower(world, random));
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 2, 3, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 3, 3, super.doorBlock, 8);
        this.setBlockAndMetadata(world, -1, 3, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 3, 2, Blocks.torch, 4);
        this.placeSign(world, 0, 4, 2, Blocks.wall_sign, 2, this.meadNameSign);
        for (final int i9 : new int[] { -2, 2 }) {
            for (int j2 = 2; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i9, j2, 3, super.woodBeamRohanGoldBlock, super.woodBeamRohanGoldMeta);
            }
        }
        for (final int i9 : new int[] { -3, 3 }) {
            this.setBlockAndMetadata(world, i9, 3, 3, super.plankStairBlock, 2);
            this.setBlockAndMetadata(world, i9, 4, 3, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, 0, 4, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 5, 4, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        this.setBlockAndMetadata(world, 2, 5, 4, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        for (int k4 = 4; k4 <= 24; ++k4) {
            this.setBlockAndMetadata(world, -5, 5, k4, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 5, 5, k4, super.roofBlock, super.roofMeta);
            if (k4 <= 23) {
                if (k4 % 6 == 2) {
                    for (int j5 = 2; j5 <= 5; ++j5) {
                        this.setBlockAndMetadata(world, -4, j5, k4, super.woodBeamRohanBlock, super.woodBeamRohanMeta);
                        this.setBlockAndMetadata(world, 4, j5, k4, super.woodBeamRohanBlock, super.woodBeamRohanMeta);
                    }
                    this.setBlockAndMetadata(world, -3, 5, k4, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                    this.setBlockAndMetadata(world, 3, 5, k4, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                    this.setBlockAndMetadata(world, -2, 6, k4, super.plank2SlabBlock, super.plank2SlabMeta);
                    this.setBlockAndMetadata(world, 2, 6, k4, super.plank2SlabBlock, super.plank2SlabMeta);
                    this.setBlockAndMetadata(world, -1, 6, k4, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                    this.setBlockAndMetadata(world, 1, 6, k4, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                    this.setBlockAndMetadata(world, 0, 6, k4, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, 0, 5, k4, LOTRMod.chandelier, 1);
                }
                else {
                    this.setBlockAndMetadata(world, -4, 5, k4, super.plankSlabBlock, super.plankSlabMeta);
                }
                if (k4 % 6 == 4 || k4 % 6 == 0) {
                    this.setBlockAndMetadata(world, -4, 4, k4, Blocks.torch, 2);
                    this.setBlockAndMetadata(world, 4, 4, k4, Blocks.torch, 1);
                }
                if (k4 % 6 == 1 || k4 % 6 == 3) {
                    this.spawnItemFrame(world, -5, 3, k4, 1, this.getRohanFramedItem(random));
                    this.spawnItemFrame(world, 5, 3, k4, 3, this.getRohanFramedItem(random));
                }
            }
        }
        for (int k4 = 9; k4 <= 19; ++k4) {
            for (int i11 = -1; i11 <= 1; ++i11) {
                if (k4 % 5 == 4 && Math.abs(i11) == 1) {
                    this.setBlockAndMetadata(world, i11, 2, k4, super.plank2Block, super.plank2Meta);
                }
                else {
                    this.setBlockAndMetadata(world, i11, 2, k4, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
                if (i11 == 0 && random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        this.placeBarrel(world, random, i11, 3, k4, random.nextBoolean() ? 4 : 5, LOTRFoods.ROHAN_DRINK);
                    }
                    else {
                        this.setBlockAndMetadata(world, i11, 3, k4, this.getRandomCakeBlock(random), 0);
                    }
                }
                else if (random.nextInt(3) == 0) {
                    this.placeMug(world, random, i11, 3, k4, random.nextInt(4), LOTRFoods.ROHAN_DRINK);
                }
                else {
                    this.placePlate(world, random, i11, 3, k4, super.plateBlock, LOTRFoods.ROHAN);
                }
            }
        }
        for (int k4 = 8; k4 <= 20; ++k4) {
            if (k4 % 2 == 0) {
                this.setBlockAndMetadata(world, -3, 2, k4, super.plankStairBlock, 0);
                this.setBlockAndMetadata(world, 3, 2, k4, super.plankStairBlock, 1);
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 6; k3 <= 7; ++k3) {
                this.setBlockAndMetadata(world, i3, 2, k3, super.carpetBlock, super.carpetMeta);
            }
        }
        this.setBlockAndMetadata(world, -2, 2, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 2, 4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -4, 2, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -4, 2, 5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -4, 2, 6, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, -2, 3, 4, 2, LOTRFoods.ROHAN_DRINK);
        this.placeBarrel(world, random, -3, 3, 4, 3, LOTRFoods.ROHAN_DRINK);
        this.placeBarrel(world, random, -4, 3, 5, 4, LOTRFoods.ROHAN_DRINK);
        this.placeMug(world, random, -4, 3, 6, 3, LOTRFoods.ROHAN_DRINK);
        this.setBlockAndMetadata(world, 2, 2, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 3, 2, 4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 4, 2, 4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 4, 2, 5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 4, 2, 6, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 2, 3, 4, 2, LOTRFoods.ROHAN_DRINK);
        this.placeBarrel(world, random, 3, 3, 4, 3, LOTRFoods.ROHAN_DRINK);
        this.placeBarrel(world, random, 4, 3, 5, 5, LOTRFoods.ROHAN_DRINK);
        this.placeMug(world, random, 4, 3, 6, 1, LOTRFoods.ROHAN_DRINK);
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 24, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, i3, 2, 24, (Block)Blocks.fire, 0);
            for (int j5 = 3; j5 <= 4; ++j5) {
                this.setAir(world, i3, j5, 24);
            }
            for (int j5 = 2; j5 <= 3; ++j5) {
                this.setBlockAndMetadata(world, i3, j5, 23, super.barsBlock, 0);
            }
        }
        this.setBlockAndMetadata(world, -3, 6, 24, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 3, 6, 24, super.roofBlock, super.roofMeta);
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = 22; k3 <= 23; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
            }
        }
        this.setBlockAndMetadata(world, -3, 4, 23, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 4, 23, Blocks.torch, 4);
        this.placeWallBanner(world, -2, 5, 23, super.bannerType, 2);
        this.placeWallBanner(world, 2, 5, 23, super.bannerType, 2);
        this.setBlockAndMetadata(world, -1, 5, 23, super.brickCarvedBlock, super.brickCarvedMeta);
        this.setBlockAndMetadata(world, 1, 5, 23, super.brickCarvedBlock, super.brickCarvedMeta);
        this.placeWeaponRack(world, 0, 5, 22, 6, this.getRandomRohanWeapon(random));
        final LOTREntityRohanMan meadhost = new LOTREntityRohanMeadhost(world);
        this.spawnNPCAndSetHome(meadhost, world, 0, 2, 21, 8);
        for (int men = 5 + random.nextInt(5), l = 0; l < men; ++l) {
            final int i9 = random.nextBoolean() ? -2 : 2;
            final int j2 = 2;
            final int k6 = MathHelper.getRandomIntegerInRange(random, 8, 20);
            final LOTREntityRohanMan rohirrim = random.nextBoolean() ? new LOTREntityRohanMan(world) : new LOTREntityRohirrimWarrior(world);
            this.spawnNPCAndSetHome(rohirrim, world, i9, j2, k6, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityRohanShieldmaiden.class);
        respawner.setCheckRanges(32, -12, 12, 2);
        respawner.setSpawnRanges(4, -2, 4, 16);
        respawner.setSpawnIntervalMinutes(5);
        respawner.setNoPlayerRange(8);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    @Override
    protected ItemStack getRohanFramedItem(final Random random) {
        if (random.nextBoolean()) {
            return LOTRFoods.ROHAN_DRINK.getRandomVessel(random).getEmptyVessel();
        }
        return super.getRohanFramedItem(random);
    }
}
