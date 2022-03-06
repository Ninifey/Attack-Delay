// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDorwinionMan;
import lotr.common.entity.npc.LOTRNames;
import com.google.common.math.IntMath;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRWorldGenDorwinionHouse extends LOTRWorldGenStructureBase2
{
    protected Block woodBeamBlock;
    protected int woodBeamMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block doorBlock;
    protected Block floorBlock;
    protected int floorMeta;
    protected Block wallBlock;
    protected int wallMeta;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block clayBlock;
    protected int clayMeta;
    protected Block claySlabBlock;
    protected int claySlabMeta;
    protected Block clayStairBlock;
    protected Block leafBlock;
    protected int leafMeta;
    protected Block plateBlock;
    
    public LOTRWorldGenDorwinionHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        final int randomWood = random.nextInt(3);
        if (randomWood == 0) {
            this.woodBeamBlock = LOTRMod.woodBeamV1;
            this.woodBeamMeta = 0;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = (Block)Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.doorBlock = Blocks.wooden_door;
        }
        else if (randomWood == 1) {
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 2;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 10;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsCypress;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 10;
            this.doorBlock = LOTRMod.doorCypress;
        }
        else if (randomWood == 2) {
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 3;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 11;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 3;
            this.plankStairBlock = LOTRMod.stairsOlive;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 11;
            this.doorBlock = LOTRMod.doorOlive;
        }
        final int randomFloor = random.nextInt(3);
        if (randomFloor == 0) {
            this.floorBlock = Blocks.cobblestone;
            this.floorMeta = 0;
        }
        else if (randomFloor == 1) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 8;
        }
        else if (randomFloor == 2) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 10;
        }
        final int randomWall = random.nextInt(3);
        if (randomWall == 0) {
            this.wallBlock = Blocks.stonebrick;
            this.wallMeta = 0;
        }
        else if (randomWall == 1) {
            this.wallBlock = Blocks.stained_hardened_clay;
            this.wallMeta = 0;
        }
        else if (randomWall == 2) {
            this.wallBlock = Blocks.stained_hardened_clay;
            this.wallMeta = 4;
        }
        this.brickBlock = LOTRMod.brick5;
        this.brickMeta = 2;
        this.brickSlabBlock = LOTRMod.slabSingle9;
        this.brickSlabMeta = 7;
        this.brickStairBlock = LOTRMod.stairsDorwinionBrick;
        this.brickWallBlock = LOTRMod.wall3;
        this.brickWallMeta = 10;
        this.pillarBlock = LOTRMod.pillar2;
        this.pillarMeta = 6;
        final int randomClay = random.nextInt(5);
        if (randomClay == 0) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 10;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.claySlabMeta = 2;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedPurple;
        }
        else if (randomClay == 1) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 2;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.claySlabMeta = 2;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedMagenta;
        }
        else if (randomClay == 2) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 14;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.claySlabMeta = 6;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedRed;
        }
        else if (randomClay == 3) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 13;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.claySlabMeta = 5;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedGreen;
        }
        else if (randomClay == 4) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 12;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.claySlabMeta = 4;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedBrown;
        }
        this.leafBlock = LOTRMod.leaves6;
        this.leafMeta = 6;
        if (random.nextBoolean()) {
            this.plateBlock = LOTRMod.ceramicPlateBlock;
        }
        else {
            this.plateBlock = LOTRMod.plateBlock;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -10; i2 <= 3; ++i2) {
                for (int k2 = 0; k2 <= 10; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -10; i2 <= 3; ++i2) {
            for (int k2 = 0; k2 <= 10; ++k2) {
                for (int j2 = 1; j2 <= 10; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                boolean beam = false;
                if ((i2 == -2 || i2 == 3) && k2 == 0) {
                    beam = true;
                }
                if (i2 == 3 && k2 == 5) {
                    beam = true;
                }
                if ((i2 == 3 || i2 == -2 || i2 == -10) && k2 == 10) {
                    beam = true;
                }
                if ((i2 == -10 || i2 == -2) && k2 == 4) {
                    beam = true;
                }
                boolean wall = false;
                if (k2 == 0 || k2 == 10) {
                    wall = true;
                }
                if (i2 == 3 || i2 == -10) {
                    wall = true;
                }
                if (i2 == -2 && k2 <= 4) {
                    wall = true;
                }
                if (k2 == 4 && i2 <= -2) {
                    wall = true;
                }
                final boolean garden = i2 >= -10 && i2 <= -3 && k2 >= 0 && k2 <= 3;
                if (garden) {
                    this.setBlockAndMetadata(world, i2, 0, k2, (Block)Blocks.grass, 0);
                    for (int j3 = -1; !this.isOpaque(world, i2, j3, k2) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                    if (random.nextInt(3) == 0) {
                        final BiomeGenBase biome = this.getBiome(world, i2, k2);
                        final int j4 = 1;
                        biome.plantFlower(world, random, this.getX(i2, k2), this.getY(j4), this.getZ(i2, k2));
                    }
                }
                else if (beam) {
                    for (int j3 = 1; j3 <= 8; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, this.wallBlock, this.wallMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                }
                else if (wall) {
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, this.wallBlock, this.wallMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                    this.setBlockAndMetadata(world, i2, 1, k2, this.wallBlock, this.wallMeta);
                    this.setBlockAndMetadata(world, i2, 2, k2, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i2, 3, k2, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i2, 5, k2, this.wallBlock, this.wallMeta);
                    this.setBlockAndMetadata(world, i2, 6, k2, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i2, 7, k2, this.brickBlock, this.brickMeta);
                    if (i2 == -10 || i2 == -2 || i2 == 3) {
                        this.setBlockAndMetadata(world, i2, 4, k2, this.woodBeamBlock, this.woodBeamMeta | 0x8);
                        this.setBlockAndMetadata(world, i2, 8, k2, this.woodBeamBlock, this.woodBeamMeta | 0x8);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 4, k2, this.woodBeamBlock, this.woodBeamMeta | 0x4);
                        this.setBlockAndMetadata(world, i2, 8, k2, this.woodBeamBlock, this.woodBeamMeta | 0x4);
                    }
                }
                else {
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, this.floorBlock, this.floorMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                    if ((i2 >= 0 && i2 <= 1 && k2 >= 2 && k2 <= 8) || (i2 >= -8 && i2 <= -2 && k2 >= 6 && k2 <= 8)) {
                        this.setBlockAndMetadata(world, i2, 4, k2, this.plankSlabBlock, this.plankSlabMeta | 0x8);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 4, k2, this.plankBlock, this.plankMeta);
                    }
                }
            }
        }
        for (final int j5 : new int[] { 2, 6 }) {
            this.setAir(world, 0, j5, 0);
            this.setAir(world, 1, j5, 0);
            this.setBlockAndMetadata(world, 0, j5 + 1, 0, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 1, j5 + 1, 0, this.brickStairBlock, 5);
            for (final int k3 : new int[] { 2, 7 }) {
                this.setAir(world, 3, j5, k3);
                this.setAir(world, 3, j5, k3 + 1);
                this.setBlockAndMetadata(world, 3, j5 + 1, k3, this.brickStairBlock, 7);
                this.setBlockAndMetadata(world, 3, j5 + 1, k3 + 1, this.brickStairBlock, 6);
            }
            for (final int i3 : new int[] { -4, -7 }) {
                this.setAir(world, i3, j5, 10);
                this.setAir(world, i3 - 1, j5, 10);
                this.setBlockAndMetadata(world, i3, j5 + 1, 10, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i3 - 1, j5 + 1, 10, this.brickStairBlock, 4);
            }
            this.setAir(world, -10, j5, 8);
            this.setAir(world, -10, j5, 7);
            this.setAir(world, -10, j5, 6);
            this.setBlockAndMetadata(world, -10, j5 + 1, 8, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -10, j5 + 1, 7, this.brickSlabBlock, this.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, -10, j5 + 1, 6, this.brickStairBlock, 7);
            for (final int i3 : new int[] { -8, -5 }) {
                this.setAir(world, i3, j5, 4);
                this.setAir(world, i3 + 1, j5, 4);
                this.setBlockAndMetadata(world, i3, j5 + 1, 4, this.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i3 + 1, j5 + 1, 4, this.brickStairBlock, 5);
            }
            this.setAir(world, -2, j5, 2);
            this.setBlockAndMetadata(world, -2, j5 + 1, 2, this.brickSlabBlock, this.brickSlabMeta | 0x8);
        }
        this.setAir(world, 1, 6, 10);
        this.setAir(world, 0, 6, 10);
        this.setBlockAndMetadata(world, 1, 7, 10, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 7, 10, this.brickStairBlock, 4);
        for (int i2 = -9; i2 <= -3; ++i2) {
            if (i2 % 3 == 0) {
                this.setBlockAndMetadata(world, i2, 1, 3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i2, 2, 3, this.brickSlabBlock, this.brickSlabMeta);
                this.setBlockAndMetadata(world, i2, 1, 2, this.brickSlabBlock, this.brickSlabMeta);
                this.setGrassToDirt(world, i2, 0, 3);
                this.setGrassToDirt(world, i2, 0, 2);
            }
            else {
                this.setBlockAndMetadata(world, i2, 1, 3, this.leafBlock, this.leafMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, this.floorBlock, this.floorMeta);
        this.setBlockAndMetadata(world, 1, 0, 0, this.floorBlock, this.floorMeta);
        this.setAir(world, 0, 1, 0);
        this.setAir(world, 1, 1, 0);
        this.placeWallBanner(world, -2, 4, 0, LOTRItemBanner.BannerType.DORWINION, 2);
        this.placeWallBanner(world, 3, 4, 0, LOTRItemBanner.BannerType.DORWINION, 2);
        this.setBlockAndMetadata(world, -1, 2, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 2, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 4, 7, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -4, 3, 7, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -9, 3, 5, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -9, 3, 9, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -9, 3, 7, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -3, 1, 5, Blocks.furnace, 3);
        this.setBlockAndMetadata(world, -4, 1, 5, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -5, 1, 5, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -6, 1, 5, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -7, 1, 5, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -8, 1, 5, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -9, 1, 5, this.plankBlock, this.plankMeta);
        this.placeFlowerPot(world, -9, 2, 5, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -9, 1, 6, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -9, 1, 7, LOTRMod.dorwinionTable, 0);
        this.setBlockAndMetadata(world, -9, 1, 8, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -9, 1, 9, this.plankBlock, this.plankMeta);
        this.placeFlowerPot(world, -9, 2, 9, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -8, 1, 9, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -7, 1, 9, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -6, 1, 9, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -5, 1, 9, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -4, 1, 9, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -3, 1, 9, (Block)Blocks.cauldron, 3);
        for (int j6 = 1; j6 <= 9; ++j6) {
            this.setBlockAndMetadata(world, 1, j6, 8, this.woodBeamBlock, this.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, 1, 2, 7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 1, 8, this.plankStairBlock, 2);
        this.setBlockAndMetadata(world, 2, 1, 9, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 2, 9, this.plankStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 1, 9, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 2, 9, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 3, 8, this.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, 8, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 3, 7, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 2, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 1, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 1, 4, 7, this.plankStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 4, 6, this.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 4, 6, this.plankStairBlock, 3);
        this.setAir(world, 0, 4, 7);
        this.setAir(world, 0, 4, 8);
        this.setAir(world, 0, 4, 9);
        this.setAir(world, 1, 4, 9);
        this.setAir(world, 2, 4, 9);
        this.setBlockAndMetadata(world, -1, 5, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -1, 5, 8, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -1, 5, 9, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 5, 8, this.fenceBlock, this.fenceMeta);
        for (final int k4 : new int[] { 5, 9 }) {
            this.setBlockAndMetadata(world, -3, 5, k4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -4, 5, k4, this.plankStairBlock, 5);
            this.setBlockAndMetadata(world, -5, 5, k4, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -8, 5, k4, Blocks.bed, 3);
            this.setBlockAndMetadata(world, -9, 5, k4, Blocks.bed, 11);
        }
        this.placeChest(world, random, -6, 5, 5, 3, LOTRChestContents.DORWINION_HOUSE);
        this.placeChest(world, random, -6, 5, 9, 2, LOTRChestContents.DORWINION_HOUSE);
        this.placeMug(world, random, -4, 6, 5, 2, LOTRFoods.DORWINION_DRINK);
        this.placeMug(world, random, -4, 6, 9, 0, LOTRFoods.DORWINION_DRINK);
        this.setBlockAndMetadata(world, -9, 5, 6, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -9, 5, 7, LOTRMod.dorwinionTable, 0);
        this.setBlockAndMetadata(world, -9, 5, 8, this.plankStairBlock, 6);
        this.placeBarrel(world, random, -9, 6, 7, 4, LOTRFoods.DORWINION_DRINK);
        this.placeMug(world, random, -9, 6, 6, 3, LOTRFoods.DORWINION_DRINK);
        this.placeMug(world, random, -9, 6, 8, 3, LOTRFoods.DORWINION_DRINK);
        this.spawnItemFrame(world, -10, 8, 7, 1, new ItemStack(Items.clock));
        this.setBlockAndMetadata(world, 0, 5, 2, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 5, 3, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 5, 2, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 5, 3, this.plankStairBlock, 5);
        for (int i2 = 0; i2 <= 1; ++i2) {
            for (int k2 = 2; k2 <= 3; ++k2) {
                this.placePlate(world, random, i2, 6, k2, this.plateBlock, LOTRFoods.DORWINION);
            }
        }
        this.setBlockAndMetadata(world, -1, 6, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 6, 1, Blocks.torch, 3);
        for (int i2 = -2; i2 <= 3; ++i2) {
            this.setBlockAndMetadata(world, i2, 8, -1, this.brickStairBlock, 6);
        }
        for (int k5 = -1; k5 <= 11; ++k5) {
            this.setBlockAndMetadata(world, 4, 8, k5, this.brickStairBlock, 4);
            if (IntMath.mod(k5, 2) == 1) {
                this.setBlockAndMetadata(world, 4, 9, k5, this.brickSlabBlock, this.brickSlabMeta);
            }
        }
        for (int i2 = -11; i2 <= 3; ++i2) {
            this.setBlockAndMetadata(world, i2, 8, 11, this.brickStairBlock, 7);
            if (i2 <= -3 && IntMath.mod(i2, 2) == 1) {
                this.setBlockAndMetadata(world, i2, 9, 11, this.brickSlabBlock, this.brickSlabMeta);
            }
        }
        for (int k5 = 4; k5 <= 10; ++k5) {
            this.setBlockAndMetadata(world, -11, 8, k5, this.brickStairBlock, 5);
        }
        for (int i2 = -11; i2 <= -3; ++i2) {
            this.setBlockAndMetadata(world, i2, 8, 3, this.brickStairBlock, 6);
            if (IntMath.mod(i2, 2) == 1) {
                this.setBlockAndMetadata(world, i2, 9, 3, this.brickSlabBlock, this.brickSlabMeta);
            }
        }
        for (int k5 = -1; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -3, 8, k5, this.brickStairBlock, 5);
            if (IntMath.mod(k5, 2) == 1) {
                this.setBlockAndMetadata(world, -3, 9, k5, this.brickSlabBlock, this.brickSlabMeta);
            }
        }
        for (int i2 = -9; i2 <= -1; ++i2) {
            this.setBlockAndMetadata(world, i2, 9, 5, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i2, 10, 6, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i2, 10, 7, this.plankSlabBlock, this.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i2, 10, 8, this.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i2, 9, 9, this.plankStairBlock, 6);
        }
        for (int k5 = 1; k5 <= 9; ++k5) {
            if (k5 <= 5) {
                this.setBlockAndMetadata(world, -1, 9, k5, this.plankStairBlock, 4);
                this.setBlockAndMetadata(world, 0, 10, k5, this.plankStairBlock, 4);
            }
            this.setBlockAndMetadata(world, 1, 10, k5, this.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 2, 9, k5, this.plankStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -10, 9, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -10, 9, 6, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -10, 10, 6, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -10, 10, 7, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -10, 10, 8, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -10, 9, 8, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -10, 9, 9, this.plankBlock, this.plankMeta);
        for (final int i4 : new int[] { -8, -4, 0 }) {
            this.setBlockAndMetadata(world, i4, 10, 7, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i4, 9, 7, LOTRMod.chandelier, 2);
        }
        this.setBlockAndMetadata(world, 0, 10, 6, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 9, 9, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 8, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 9, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 8, this.woodBeamBlock, this.woodBeamMeta);
        this.setBlockAndMetadata(world, -1, 9, 10, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 9, 10, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 10, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 10, 10, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 9, 10, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, 10, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -1, 9, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 9, 0, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 10, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 9, 0, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, 0, this.plankBlock, this.plankMeta);
        for (int i2 = -11; i2 <= 0; ++i2) {
            if (i2 <= -2) {
                this.setBlockAndMetadata(world, i2, 9, 4, this.clayStairBlock, 2);
                this.setBlockAndMetadata(world, i2, 9, 10, this.clayStairBlock, 3);
            }
            if (i2 <= -1) {
                this.setBlockAndMetadata(world, i2, 10, 5, this.clayStairBlock, 2);
                this.setBlockAndMetadata(world, i2, 10, 9, this.clayStairBlock, 3);
            }
            this.setBlockAndMetadata(world, i2, 11, 6, this.clayStairBlock, 2);
            this.setBlockAndMetadata(world, i2, 11, 7, this.clayBlock, this.clayMeta);
            this.setBlockAndMetadata(world, i2, 11, 8, this.clayStairBlock, 3);
        }
        for (int k5 = -1; k5 <= 11; ++k5) {
            if (k5 <= 3 || k5 >= 11) {
                this.setBlockAndMetadata(world, -2, 9, k5, this.clayStairBlock, 1);
            }
            if (k5 <= 4 || k5 >= 10) {
                this.setBlockAndMetadata(world, -1, 10, k5, this.clayStairBlock, 1);
            }
            if (k5 <= 5 || k5 >= 9) {
                this.setBlockAndMetadata(world, 0, 11, k5, this.clayStairBlock, 1);
            }
            this.setBlockAndMetadata(world, 1, 11, k5, this.clayStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 10, k5, this.clayStairBlock, 0);
            this.setBlockAndMetadata(world, 3, 9, k5, this.clayStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -11, 9, 5, this.clayStairBlock, 7);
        this.setBlockAndMetadata(world, -11, 10, 6, this.clayStairBlock, 7);
        this.setBlockAndMetadata(world, -11, 10, 8, this.clayStairBlock, 6);
        this.setBlockAndMetadata(world, -11, 9, 9, this.clayStairBlock, 6);
        this.setBlockAndMetadata(world, -1, 9, 11, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 11, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 11, this.clayStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, 11, this.clayStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 9, -1, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, -1, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, -1, this.clayStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, -1, this.clayStairBlock, 5);
        final String maleName = LOTRNames.getDorwinionName(random, true);
        final String femaleName = LOTRNames.getDorwinionName(random, false);
        final LOTREntityDorwinionMan dorwinionMale = new LOTREntityDorwinionMan(world);
        dorwinionMale.familyInfo.setName(maleName);
        dorwinionMale.familyInfo.setMale(true);
        this.spawnNPCAndSetHome(dorwinionMale, world, 0, 1, 6, 16);
        final LOTREntityDorwinionMan dorwinionFemale = new LOTREntityDorwinionMan(world);
        dorwinionFemale.familyInfo.setName(femaleName);
        dorwinionFemale.familyInfo.setMale(false);
        this.spawnNPCAndSetHome(dorwinionFemale, world, 0, 1, 6, 16);
        return true;
    }
}
