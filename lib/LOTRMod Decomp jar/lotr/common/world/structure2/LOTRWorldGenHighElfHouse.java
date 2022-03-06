// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityCreature;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;

public class LOTRWorldGenHighElfHouse extends LOTRWorldGenStructureBase2
{
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block brickCarvedBlock;
    protected int brickCarvedMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block stoneBlock;
    protected int stoneMeta;
    protected Block stoneSlabBlock;
    protected int stoneSlabMeta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block leafBlock;
    protected int leafMeta;
    protected Block tableBlock;
    protected Block bedBlock;
    protected Block barsBlock;
    protected Block torchBlock;
    protected Block chandelierBlock;
    protected int chandelierMeta;
    protected Block plateBlock;
    protected LOTRItemBanner.BannerType bannerType;
    protected LOTRChestContents chestContents;
    
    public LOTRWorldGenHighElfHouse(final boolean flag) {
        super(flag);
    }
    
    protected LOTREntityElf createElf(final World world) {
        return new LOTREntityHighElf(world);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        this.brickBlock = LOTRMod.brick3;
        this.brickMeta = 2;
        this.brickSlabBlock = LOTRMod.slabSingle5;
        this.brickSlabMeta = 5;
        this.brickStairBlock = LOTRMod.stairsHighElvenBrick;
        this.brickWallBlock = LOTRMod.wall2;
        this.brickWallMeta = 11;
        this.brickCarvedBlock = LOTRMod.brick2;
        this.brickCarvedMeta = 13;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 10;
        this.stoneBlock = LOTRMod.smoothStoneV;
        this.stoneMeta = 0;
        this.stoneSlabBlock = (Block)Blocks.stone_slab;
        this.stoneSlabMeta = 0;
        final int randomRoof = random.nextInt(5);
        if (randomRoof == 0) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 11;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.roofSlabMeta = 3;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
        }
        else if (randomRoof == 1) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 3;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.roofSlabMeta = 3;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
        }
        else if (randomRoof == 2) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 9;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.roofSlabMeta = 1;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedCyan;
        }
        else if (randomRoof == 3) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 8;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.roofSlabMeta = 0;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
        }
        else if (randomRoof == 4) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 7;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.roofSlabMeta = 7;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedGray;
        }
        final int randomWood = random.nextInt(4);
        if (randomWood == 0) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = (Block)Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
        }
        else if (randomWood == 1) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 2;
            this.plankSlabBlock = (Block)Blocks.wooden_slab;
            this.plankSlabMeta = 2;
            this.plankStairBlock = Blocks.birch_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 2;
        }
        else if (randomWood == 2) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 1;
            this.plankStairBlock = LOTRMod.stairsBeech;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 9;
        }
        else if (randomWood == 3) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsApple;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 4;
        }
        final int randomLeaf = random.nextInt(3);
        if (randomLeaf == 0) {
            this.leafBlock = (Block)Blocks.leaves;
            this.leafMeta = 4;
        }
        else if (randomLeaf == 1) {
            this.leafBlock = (Block)Blocks.leaves;
            this.leafMeta = 6;
        }
        else if (randomLeaf == 2) {
            this.leafBlock = LOTRMod.leaves2;
            this.leafMeta = 5;
        }
        this.tableBlock = LOTRMod.highElvenTable;
        this.bedBlock = LOTRMod.highElvenBed;
        this.barsBlock = LOTRMod.highElfWoodBars;
        this.torchBlock = LOTRMod.highElvenTorch;
        this.chandelierBlock = LOTRMod.chandelier;
        this.chandelierMeta = 10;
        this.plateBlock = LOTRMod.plateBlock;
        this.bannerType = LOTRItemBanner.BannerType.HIGH_ELF;
        this.chestContents = LOTRChestContents.HIGH_ELVEN_HALL;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        final boolean leafy = random.nextBoolean();
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -1; k2 <= 14; ++k2) {
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
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = 0; k3 <= 13; ++k3) {
                final int i4 = Math.abs(i3);
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 12; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                if (i4 <= 2 && k3 >= 1 && k3 <= 12) {
                    this.setBlockAndMetadata(world, i3, 0, k3, this.stoneBlock, this.stoneMeta);
                }
                if (i4 <= 2 && k3 == 0) {
                    this.setBlockAndMetadata(world, i3, 0, k3, this.pillarBlock, this.pillarMeta);
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            final int i5 = Math.abs(i3);
            if (i5 % 2 == 1) {
                this.setBlockAndMetadata(world, i3, 1, 0, this.pillarBlock, this.pillarMeta);
                this.setBlockAndMetadata(world, i3, 2, 0, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i3, 3, 0, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i3, 4, 0, this.pillarBlock, this.pillarMeta);
                if (i5 == 1) {
                    this.setBlockAndMetadata(world, i3, 5, 0, this.pillarBlock, this.pillarMeta);
                }
            }
            if (i5 == 0) {
                this.setBlockAndMetadata(world, i3, 6, 0, this.pillarBlock, this.pillarMeta);
                for (int j4 = 7; j4 <= 9; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, 0, this.brickWallBlock, this.brickWallMeta);
                }
                for (int j4 = 10; j4 <= 11; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, 0, this.pillarBlock, this.pillarMeta);
                }
            }
            else if (i5 <= 2) {
                this.setBlockAndMetadata(world, i3, 6, 0, this.brickWallBlock, this.brickWallMeta);
            }
        }
        this.setBlockAndMetadata(world, -2, 5, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 5, 0, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 2, 5, 0, this.brickStairBlock, 5);
        for (final int i6 : new int[] { -3, 3 }) {
            for (int j2 = 1; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, 1, this.pillarBlock, this.pillarMeta);
            }
            this.setBlockAndMetadata(world, i6 + Integer.signum(i6), 4, 1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i6, 1, 2, this.brickStairBlock, (i6 > 0) ? 4 : 5);
            this.setBlockAndMetadata(world, i6, 2, 2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i6, 3, 2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i6, 4, 2, this.plankStairBlock, (i6 <= 0) ? 1 : 0);
            this.setBlockAndMetadata(world, i6, 1, 3, this.brickStairBlock, (i6 <= 0) ? 1 : 0);
            this.setBlockAndMetadata(world, i6, 3, 3, this.barsBlock, 0);
            this.setBlockAndMetadata(world, i6, 4, 3, this.plankStairBlock, (i6 > 0) ? 4 : 5);
            this.setBlockAndMetadata(world, i6, 1, 4, this.brickStairBlock, (i6 > 0) ? 4 : 5);
            this.setBlockAndMetadata(world, i6, 2, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i6, 3, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i6, 4, 4, this.plankStairBlock, (i6 <= 0) ? 1 : 0);
            for (int j2 = 1; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, 5, this.pillarBlock, this.pillarMeta);
                this.setBlockAndMetadata(world, i6, j2, 8, this.pillarBlock, this.pillarMeta);
            }
            this.setBlockAndMetadata(world, i6, 4, 6, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 4, 7, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i6 + Integer.signum(i6), 4, 5, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i6 + Integer.signum(i6), 4, 8, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i6, 1, 9, this.brickStairBlock, (i6 > 0) ? 4 : 5);
            this.setBlockAndMetadata(world, i6, 2, 9, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i6, 3, 9, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i6, 4, 9, this.plankStairBlock, (i6 <= 0) ? 1 : 0);
            this.setBlockAndMetadata(world, i6, 1, 10, this.brickStairBlock, (i6 <= 0) ? 1 : 0);
            this.setBlockAndMetadata(world, i6, 3, 10, this.barsBlock, 0);
            this.setBlockAndMetadata(world, i6, 4, 10, this.plankStairBlock, (i6 > 0) ? 4 : 5);
            this.setBlockAndMetadata(world, i6, 1, 11, this.brickStairBlock, (i6 > 0) ? 4 : 5);
            this.setBlockAndMetadata(world, i6, 2, 11, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i6, 3, 11, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i6, 4, 11, this.plankStairBlock, (i6 <= 0) ? 1 : 0);
            for (int j2 = 1; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, 12, this.pillarBlock, this.pillarMeta);
            }
            this.setBlockAndMetadata(world, i6 + Integer.signum(i6), 4, 12, this.brickWallBlock, this.brickWallMeta);
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            final int i5 = Math.abs(i3);
            if (i5 == 0) {
                for (int j4 = 1; j4 <= 6; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, 13, this.pillarBlock, this.pillarMeta);
                }
                for (int j4 = 7; j4 <= 9; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, 13, this.brickWallBlock, this.brickWallMeta);
                }
                for (int j4 = 10; j4 <= 11; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, 13, this.pillarBlock, this.pillarMeta);
                }
            }
            else if (i5 == 1) {
                this.setBlockAndMetadata(world, i3, 1, 13, this.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i3, 3, 13, this.barsBlock, 0);
                this.setBlockAndMetadata(world, i3, 4, 13, this.plankStairBlock, 7);
            }
            else if (i5 == 2) {
                this.setBlockAndMetadata(world, i3, 1, 13, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i3, 2, 13, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i3, 3, 13, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i3, 4, 13, this.plankStairBlock, (i3 <= 0) ? 1 : 0);
            }
            if (i5 >= 1 && i5 <= 2) {
                this.setBlockAndMetadata(world, i3, 5, 13, this.stoneSlabBlock, this.stoneSlabMeta | 0x8);
                this.setBlockAndMetadata(world, i3, 6, 13, this.brickWallBlock, this.brickWallMeta);
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = 1; k3 <= 12; ++k3) {
                this.setBlockAndMetadata(world, i3, 5, k3, this.stoneBlock, this.stoneMeta);
            }
        }
        for (int k4 = 0; k4 <= 13; ++k4) {
            Block block = this.roofBlock;
            int meta = this.roofMeta;
            Block slabBlock = this.roofSlabBlock;
            int slabMeta = this.roofSlabMeta;
            Block stairBlock = this.roofStairBlock;
            if (k4 == 1 || k4 == 12) {
                block = this.brickBlock;
                meta = this.brickMeta;
                slabBlock = this.brickSlabBlock;
                slabMeta = this.brickSlabMeta;
                stairBlock = this.brickStairBlock;
            }
            this.setBlockAndMetadata(world, -4, 5, k4, stairBlock, 1);
            this.setBlockAndMetadata(world, -3, 5, k4, stairBlock, 4);
            this.setBlockAndMetadata(world, -3, 6, k4, block, meta);
            this.setBlockAndMetadata(world, -3, 7, k4, block, meta);
            this.setBlockAndMetadata(world, -3, 8, k4, stairBlock, 1);
            this.setBlockAndMetadata(world, -2, 8, k4, stairBlock, 4);
            this.setBlockAndMetadata(world, -2, 9, k4, block, meta);
            this.setBlockAndMetadata(world, -2, 10, k4, stairBlock, 1);
            this.setBlockAndMetadata(world, -1, 10, k4, stairBlock, 4);
            this.setBlockAndMetadata(world, 4, 5, k4, stairBlock, 0);
            this.setBlockAndMetadata(world, 3, 5, k4, stairBlock, 5);
            this.setBlockAndMetadata(world, 3, 6, k4, block, meta);
            this.setBlockAndMetadata(world, 3, 7, k4, block, meta);
            this.setBlockAndMetadata(world, 3, 8, k4, stairBlock, 0);
            this.setBlockAndMetadata(world, 2, 8, k4, stairBlock, 5);
            this.setBlockAndMetadata(world, 2, 9, k4, block, meta);
            this.setBlockAndMetadata(world, 2, 10, k4, stairBlock, 0);
            this.setBlockAndMetadata(world, 1, 10, k4, stairBlock, 5);
            if (k4 <= 1 || k4 >= 12) {
                this.setBlockAndMetadata(world, -1, 11, k4, block, meta);
                this.setBlockAndMetadata(world, -1, 12, k4, stairBlock, 1);
                this.setBlockAndMetadata(world, 1, 11, k4, block, meta);
                this.setBlockAndMetadata(world, 1, 12, k4, stairBlock, 0);
            }
            else if (k4 <= 4 || k4 >= 9) {
                this.setBlockAndMetadata(world, -1, 11, k4, stairBlock, 1);
                this.setBlockAndMetadata(world, 1, 11, k4, stairBlock, 0);
            }
            else if (k4 == 5) {
                this.setBlockAndMetadata(world, -1, 11, k4, stairBlock, 3);
                this.setBlockAndMetadata(world, 1, 11, k4, stairBlock, 3);
            }
            else if (k4 == 8) {
                this.setBlockAndMetadata(world, -1, 11, k4, stairBlock, 2);
                this.setBlockAndMetadata(world, 1, 11, k4, stairBlock, 2);
            }
            else {
                this.setBlockAndMetadata(world, -1, 11, k4, slabBlock, slabMeta);
                this.setBlockAndMetadata(world, 1, 11, k4, slabBlock, slabMeta);
            }
        }
        for (int k4 = 0; k4 <= 13; ++k4) {
            this.setBlockAndMetadata(world, 0, 11, k4, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 12, -1, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 13, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 14, -1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 0, 12, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 0, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 12, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 1, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 4, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 5, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 8, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 9, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 10, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 11, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 12, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 12, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 13, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 13, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 12, 14, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 13, 14, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 14, 14, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 4, 1, this.brickStairBlock, 4);
        for (int j5 = 1; j5 <= 4; ++j5) {
            this.setBlockAndMetadata(world, -2, j5, 2, Blocks.bookshelf, 0);
        }
        this.setBlockAndMetadata(world, -2, 1, 3, this.brickStairBlock, 4);
        this.placeFlowerPot(world, -2, 2, 3, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -2, 4, 3, this.stoneSlabBlock, this.stoneSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -2, 1, 4, (Block)Blocks.grass, 0);
        this.setBlockAndMetadata(world, -1, 1, 4, Blocks.trapdoor, 6);
        this.setBlockAndMetadata(world, -2, 1, 5, Blocks.trapdoor, 5);
        this.setBlockAndMetadata(world, -2, 2, 4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -2, 3, 4, this.leafBlock, this.leafMeta);
        this.setBlockAndMetadata(world, -2, 4, 4, this.leafBlock, this.leafMeta);
        this.setBlockAndMetadata(world, 2, 4, 1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 1, 2, this.brickStairBlock, 5);
        this.placeMug(world, random, 2, 2, 2, 1, LOTRFoods.ELF_DRINK);
        this.setBlockAndMetadata(world, 2, 1, 3, this.tableBlock, 0);
        this.setBlockAndMetadata(world, 2, 4, 3, this.stoneSlabBlock, this.stoneSlabMeta | 0x8);
        for (int j5 = 1; j5 <= 4; ++j5) {
            this.setBlockAndMetadata(world, 2, j5, 4, Blocks.bookshelf, 0);
        }
        for (int i3 = -1; i3 <= 0; ++i3) {
            for (int k3 = 2; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, Blocks.carpet, 3);
            }
        }
        for (final int k2 : new int[] { 5, 8 }) {
            this.setBlockAndMetadata(world, -2, 4, k2, this.brickStairBlock, 4);
            for (int i7 = -1; i7 <= 1; ++i7) {
                this.setBlockAndMetadata(world, i7, 4, k2, this.brickSlabBlock, this.brickSlabMeta | 0x8);
            }
            this.setBlockAndMetadata(world, 2, 4, k2, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -2, 3, k2, this.torchBlock, 2);
            this.setBlockAndMetadata(world, 2, 3, k2, this.torchBlock, 1);
        }
        for (int i3 = 0; i3 <= 1; ++i3) {
            for (int k3 = 7; k3 <= 8; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, Blocks.carpet, 11);
            }
        }
        this.setBlockAndMetadata(world, -2, 4, 10, this.stoneSlabBlock, this.stoneSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 2, 4, 10, this.stoneSlabBlock, this.stoneSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -2, 4, 12, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 4, 12, this.brickStairBlock, 5);
        this.spawnItemFrame(world, -3, 2, 9, 1, this.getElfFramedItem(random));
        this.spawnItemFrame(world, 3, 2, 9, 3, this.getElfFramedItem(random));
        this.spawnItemFrame(world, -3, 2, 11, 1, this.getElfFramedItem(random));
        this.spawnItemFrame(world, 3, 2, 11, 3, this.getElfFramedItem(random));
        if (leafy) {
            for (int i3 = -2; i3 <= 2; ++i3) {
                for (int k3 = 6; k3 <= 7; ++k3) {
                    if (random.nextInt(3) != 0) {
                        this.setBlockAndMetadata(world, i3, 4, k3, this.leafBlock, this.leafMeta);
                    }
                }
            }
        }
        for (int i3 = 0; i3 <= 1; ++i3) {
            for (int k3 = 9; k3 <= 11; ++k3) {
                this.setAir(world, i3, 5, k3);
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 1; k3 <= 9; ++k3) {
                this.setBlockAndMetadata(world, i3, 10, k3, this.brickSlabBlock, this.brickSlabMeta | 0x8);
            }
            for (int k3 = 10; k3 <= 12; ++k3) {
                this.setBlockAndMetadata(world, i3, 9, k3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i3, 10, k3, this.brickBlock, this.brickMeta);
            }
        }
        for (int k4 = 9; k4 <= 12; ++k4) {
            for (int j6 = 6; j6 <= 8; ++j6) {
                this.setBlockAndMetadata(world, 2, j6, k4, this.brickBlock, this.brickMeta);
            }
        }
        for (int k4 = 11; k4 <= 12; ++k4) {
            for (int j6 = 6; j6 <= 8; ++j6) {
                this.setBlockAndMetadata(world, -2, j6, k4, this.brickBlock, this.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, -2, 6, 1, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 7, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 8, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 9, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -1, 10, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 6, 1, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 7, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 2, 8, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 9, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 1, 10, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 10, 1, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -1, 10, 2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 10, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 9, 3, this.chandelierBlock, this.chandelierMeta);
        this.setBlockAndMetadata(world, -2, 6, 4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 7, 4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 8, 4, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 6, 4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 2, 7, 4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 2, 8, 4, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 10, 6, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 9, 6, this.chandelierBlock, this.chandelierMeta);
        this.setBlockAndMetadata(world, -1, 10, 7, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 7, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 10, 8, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 10, 8, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -1, 9, 9, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 10, 9, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 9, 9, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 10, 9, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 8, 12, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 8, 12, this.brickStairBlock, 5);
        for (int j5 = 1; j5 <= 11; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, 10, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, 0, 8, 10, this.brickCarvedBlock, this.brickCarvedMeta);
        this.placeWallBanner(world, 0, 3, 10, this.bannerType, 2);
        this.setBlockAndMetadata(world, -1, 1, 9, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, -1, 1, 10, this.brickSlabBlock, this.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -1, 2, 11, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 2, 11, this.brickSlabBlock, this.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 3, 11, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 1, 3, 10, this.brickSlabBlock, this.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 4, 9, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 4, 9, this.brickSlabBlock, this.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -1, 5, 9, this.brickSlabBlock, this.brickSlabMeta);
        for (int i3 = 0; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, 8, this.brickWallBlock, this.brickWallMeta);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 1; k3 <= 7; ++k3) {
                final int i4 = Math.abs(i3);
                final int k5 = Math.abs(k3 - 4);
                if (i4 == 0 && k5 == 0) {
                    this.setBlockAndMetadata(world, i3, 6, k3, Blocks.carpet, 0);
                }
                else if ((i4 == 0 && k5 <= 2) || (i4 == 1 && k5 == 0)) {
                    this.setBlockAndMetadata(world, i3, 6, k3, Blocks.carpet, 3);
                }
                else if ((i4 == 0 && k5 == 3) || (i4 == 1 && k5 == 1)) {
                    this.setBlockAndMetadata(world, i3, 6, k3, Blocks.carpet, 11);
                }
            }
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6, 6, 3, this.bedBlock, 2);
            this.setBlockAndMetadata(world, i6, 6, 2, this.bedBlock, 10);
        }
        this.setBlockAndMetadata(world, -2, 6, 5, this.plankStairBlock, 4);
        this.placeMug(world, random, -2, 7, 5, 3, LOTRFoods.ELF_DRINK);
        this.setBlockAndMetadata(world, 2, 6, 5, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 2, 6, 6, this.plankSlabBlock, this.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 2, 6, 7, this.plankStairBlock, 6);
        this.placeChest(world, random, 2, 7, 5, 5, this.chestContents);
        this.placePlateWithCertainty(world, random, 2, 7, 6, this.plateBlock, LOTRFoods.ELF);
        this.placeBarrel(world, random, 2, 7, 7, 5, LOTRFoods.ELF_DRINK);
        if (leafy) {
            for (int i3 = -4; i3 <= 4; ++i3) {
                for (int k3 = 0; k3 <= 13; ++k3) {
                    for (int j4 = 5; j4 <= 12; ++j4) {
                        if ((Math.abs(i3) >= 3 || j4 >= 11) && random.nextInt(4) == 0 && this.isAir(world, i3, j4, k3) && (this.isSolidRoofBlock(world, i3, j4 - 1, k3) || this.isSolidRoofBlock(world, i3 - 1, j4, k3) || this.isSolidRoofBlock(world, i3 + 1, j4, k3))) {
                            this.setBlockAndMetadata(world, i3, j4, k3, this.leafBlock, this.leafMeta);
                        }
                    }
                }
            }
        }
        for (int elves = 1 + random.nextInt(2), l = 0; l < elves; ++l) {
            final LOTREntityElf elf = this.createElf(world);
            this.spawnNPCAndSetHome(elf, world, 0, 1, 7, 16);
        }
        return true;
    }
    
    private boolean isSolidRoofBlock(final World world, final int i, final int j, final int k) {
        return this.getBlock(world, i, j, k).getMaterial().isOpaque();
    }
    
    protected ItemStack getElfFramedItem(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.helmetHighElven), new ItemStack(LOTRMod.bodyHighElven), new ItemStack(LOTRMod.legsHighElven), new ItemStack(LOTRMod.bootsHighElven), new ItemStack(LOTRMod.daggerHighElven), new ItemStack(LOTRMod.swordHighElven), new ItemStack(LOTRMod.spearHighElven), new ItemStack(LOTRMod.longspearHighElven), new ItemStack(LOTRMod.highElvenBow), new ItemStack(Items.arrow), new ItemStack(Items.feather), new ItemStack(LOTRMod.swanFeather), new ItemStack(LOTRMod.quenditeCrystal), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing) };
        return items[random.nextInt(items.length)].copy();
    }
}
