// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenHobbitHouse extends LOTRWorldGenHobbitStructure
{
    public LOTRWorldGenHobbitHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean canBeWealthy(final Random random) {
        return false;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        if (random.nextBoolean()) {
            super.bedBlock = LOTRMod.strawBed;
        }
        else {
            super.bedBlock = Blocks.bed;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 11, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -9; i2 <= 8; ++i2) {
                for (int k2 = -10; k2 <= 6; ++k2) {
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
                }
            }
            if (maxHeight - minHeight > 6) {
                return false;
            }
        }
        for (int i3 = -4; i3 <= 3; ++i3) {
            for (int k3 = -10; k3 <= -6; ++k3) {
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    if (j3 == 0) {
                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)Blocks.grass, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                    }
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 5; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                if (i3 == -4 || i3 == 3 || k3 == -10) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.outFenceBlock, super.outFenceMeta);
                }
            }
        }
        for (int i3 = -9; i3 <= 8; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int k4 = Math.abs(k3);
                boolean beam = false;
                boolean wall = false;
                boolean indoors = false;
                if ((i3 == -7 || i3 == 6) && k4 == 4) {
                    beam = true;
                }
                else if ((i3 == -9 || i3 == 8) && k4 == 0) {
                    beam = true;
                }
                else if ((i3 == -3 || i3 == 2) && k4 == 6) {
                    beam = true;
                }
                else if ((i3 >= -6 && i3 <= 5 && k4 <= 5) || (k4 <= 3 && i3 >= -8 && i3 <= 7)) {
                    if (i3 == -8 || i3 == 7 || k4 == 5) {
                        wall = true;
                    }
                    else {
                        indoors = true;
                    }
                }
                if (beam || wall || indoors) {
                    for (int j4 = 1; j4 <= 6; ++j4) {
                        this.setAir(world, i3, j4, k3);
                    }
                    if (beam) {
                        for (int j4 = 2; (j4 >= 0 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.beamBlock, super.beamMeta);
                            this.setGrassToDirt(world, i3, j4 - 1, k3);
                        }
                    }
                    else if (wall) {
                        for (int j4 = 0; (j4 >= 0 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.plankBlock, super.plankMeta);
                            this.setGrassToDirt(world, i3, j4 - 1, k3);
                        }
                        for (int j4 = 1; j4 <= 2; ++j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.wallBlock, super.wallMeta);
                        }
                    }
                    else if (indoors) {
                        for (int j4 = 0; (j4 >= 0 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.floorBlock, super.floorMeta);
                            this.setGrassToDirt(world, i3, j4 - 1, k3);
                        }
                        this.setBlockAndMetadata(world, i3, 3, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 1; ++i3) {
            for (int j5 = 1; j5 <= 3; ++j5) {
                this.setBlockAndMetadata(world, i3, j5, -5, Blocks.brick_block, 0);
            }
        }
        for (int i3 = -1; i3 <= 0; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -5, super.floorBlock, super.floorMeta);
            for (int j5 = 1; j5 <= 2; ++j5) {
                this.setBlockAndMetadata(world, i3, j5, -5, super.gateBlock, 2);
            }
        }
        this.setBlockAndMetadata(world, 1, 2, -4, (Block)Blocks.tripwire_hook, 0);
        for (int j6 = 1; j6 <= 3; ++j6) {
            this.setBlockAndMetadata(world, -3, j6, -4, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, -7, j6, 0, super.beamBlock, super.beamMeta);
            for (int i4 = -6; i4 <= -4; ++i4) {
                this.setBlockAndMetadata(world, i4, j6, 0, super.plankBlock, super.plankMeta);
            }
            this.setBlockAndMetadata(world, -3, j6, 0, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, -3, j6, 4, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 2, j6, 4, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 6, j6, 0, super.beamBlock, super.beamMeta);
            this.setBlockAndMetadata(world, 2, j6, -4, super.beamBlock, super.beamMeta);
        }
        this.setBlockAndMetadata(world, -2, 3, -4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 3, -3, super.plankBlock, super.plankMeta);
        for (int i3 = -6; i3 <= -4; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, -4, super.plankBlock, super.plankMeta);
        }
        for (int k5 = -3; k5 <= -1; ++k5) {
            this.setBlockAndMetadata(world, -7, 3, k5, super.plankBlock, super.plankMeta);
        }
        for (int k5 = 1; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -7, 3, k5, super.plankBlock, super.plankMeta);
        }
        for (int i3 = -6; i3 <= -4; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, 4, super.plankBlock, super.plankMeta);
        }
        for (int k5 = 1; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -3, 3, k5, super.plankBlock, super.plankMeta);
        }
        for (int i3 = -2; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, 4, super.plankBlock, super.plankMeta);
        }
        this.setBlockAndMetadata(world, 2, 3, 3, super.plankBlock, super.plankMeta);
        for (int i3 = 3; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, 4, (Block)Blocks.double_stone_slab, 0);
        }
        for (int k5 = 1; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, 6, 3, k5, (Block)Blocks.double_stone_slab, 0);
        }
        this.setBlockAndMetadata(world, 5, 3, 0, super.plankBlock, super.plankMeta);
        for (int k5 = -3; k5 <= -1; ++k5) {
            this.setBlockAndMetadata(world, 6, 3, k5, super.plankBlock, super.plankMeta);
        }
        for (int i3 = 3; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, -4, super.plankBlock, super.plankMeta);
        }
        this.setBlockAndMetadata(world, 2, 3, -3, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 1, 3, -4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -4, 1, -4, super.plank2Block, super.plank2Meta);
        this.setBlockAndMetadata(world, -4, 2, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -5, 2, -5, LOTRMod.glassPane, 0);
        this.placeChest(world, random, -6, 1, -4, 3, LOTRChestContents.HOBBIT_HOLE_STUDY);
        for (int j6 = 1; j6 <= 2; ++j6) {
            this.setBlockAndMetadata(world, -7, j6, -3, Blocks.bookshelf, 0);
            this.setBlockAndMetadata(world, -7, j6, -1, Blocks.bookshelf, 0);
        }
        this.setBlockAndMetadata(world, -7, 1, -2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        this.setBlockAndMetadata(world, -5, 1, -2, Blocks.oak_stairs, 1);
        this.spawnItemFrame(world, -5, 2, 0, 2, new ItemStack(Items.clock));
        this.setBlockAndMetadata(world, -3, 1, 1, super.plankStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 2, 1, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 1, 3, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 2, 3, super.plankStairBlock, 6);
        for (final int i5 : new int[] { -6, -5 }) {
            this.setBlockAndMetadata(world, i5, 1, 3, super.bedBlock, 0);
            this.setBlockAndMetadata(world, i5, 1, 4, super.bedBlock, 8);
        }
        this.setBlockAndMetadata(world, -4, 1, 4, super.plank2Block, super.plank2Meta);
        this.setBlockAndMetadata(world, -4, 2, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -7, 1, 1, super.beamBlock, super.beamMeta);
        this.placeBarrel(world, random, -7, 2, 1, 4, LOTRFoods.HOBBIT_DRINK);
        this.setBlockAndMetadata(world, -1, 2, 5, LOTRMod.glassPane, 0);
        this.setBlockAndMetadata(world, 0, 2, 5, LOTRMod.glassPane, 0);
        this.setBlockAndMetadata(world, 2, 2, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 1, 4, LOTRMod.hobbitTable, 0);
        for (int i3 = 4; i3 <= 5; ++i3) {
            this.placeChest(world, random, i3, 1, 4, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
        }
        this.setBlockAndMetadata(world, 6, 1, 3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 6, 1, 2, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 6, 1, 1, LOTRMod.hobbitOven, 5);
        this.setBlockAndMetadata(world, 6, 2, -1, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 4, 2, -5, LOTRMod.glassPane, 0);
        this.setBlockAndMetadata(world, 3, 2, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 6, 1, -1, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, 6, 1, -2, super.plankStairBlock, 1);
        this.setBlockAndMetadata(world, 6, 1, -3, super.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 5, 1, -4, super.plankStairBlock, 1);
        this.setBlockAndMetadata(world, 4, 1, -4, super.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 3, 1, -4, super.plankStairBlock, 0);
        for (int i3 = 3; i3 <= 4; ++i3) {
            for (int k3 = -2; k3 <= -1; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, Blocks.planks, 1);
                if (random.nextBoolean()) {
                    this.placePlateWithCertainty(world, random, i3, 2, k3, super.plateBlock, LOTRFoods.HOBBIT);
                }
                else {
                    this.placeMug(world, random, i3, 2, k3, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
                }
            }
        }
        for (int i3 = -1; i3 <= 0; ++i3) {
            for (int k3 = -2; k3 <= 1; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, super.carpetBlock, super.carpetMeta);
            }
        }
        for (int i3 = -2; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -6, super.pathBlock, super.pathMeta);
        }
        this.setBlockAndMetadata(world, -1, 0, -7, super.pathBlock, super.pathMeta);
        this.setBlockAndMetadata(world, 0, 0, -7, super.pathBlock, super.pathMeta);
        this.setBlockAndMetadata(world, 0, 0, -8, super.pathBlock, super.pathMeta);
        this.setBlockAndMetadata(world, 1, 0, -8, super.pathBlock, super.pathMeta);
        this.setBlockAndMetadata(world, 1, 0, -9, super.pathBlock, super.pathMeta);
        this.setBlockAndMetadata(world, 1, 0, -10, super.pathBlock, super.pathMeta);
        this.setAir(world, 1, 1, -10);
        for (int i3 = -3; i3 <= 2; ++i3) {
            for (int k3 = -9; k3 <= -7; ++k3) {
                if (this.getBlock(world, i3, 0, k3) == Blocks.grass && random.nextInt(4) == 0) {
                    this.plantFlower(world, random, i3, 1, k3);
                }
            }
        }
        this.placeHedge(world, -7, 1, -5);
        this.placeHedge(world, -8, 1, -4);
        for (int k5 = -3; k5 <= -1; ++k5) {
            this.placeHedge(world, -9, 1, k5);
        }
        for (int k5 = 1; k5 <= 3; ++k5) {
            this.placeHedge(world, -9, 1, k5);
        }
        this.placeHedge(world, -8, 1, 4);
        this.placeHedge(world, -7, 1, 5);
        for (int i3 = -6; i3 <= -4; ++i3) {
            this.placeHedge(world, i3, 1, 6);
        }
        for (int i3 = 3; i3 <= 5; ++i3) {
            this.placeHedge(world, i3, 1, 6);
        }
        this.placeHedge(world, 6, 1, 5);
        this.placeHedge(world, 7, 1, 4);
        for (int k5 = 1; k5 <= 3; ++k5) {
            this.placeHedge(world, 8, 1, k5);
        }
        for (int k5 = -3; k5 <= -1; ++k5) {
            this.placeHedge(world, 8, 1, k5);
        }
        this.placeHedge(world, 7, 1, -4);
        this.placeHedge(world, 6, 1, -5);
        for (int i3 = -2; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 6, Blocks.brick_block, 0);
            this.setGrassToDirt(world, i3, 0, 6);
            this.placeFlowerPot(world, i3, 2, 6, this.getRandomFlower(world, random));
        }
        for (int i3 = -6; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, -6, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, i3, 3, 6, super.roofSlabBlock, super.roofSlabMeta);
        }
        for (int k5 = -3; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -9, 3, k5, super.roofSlabBlock, super.roofSlabMeta);
            this.setBlockAndMetadata(world, 8, 3, k5, super.roofSlabBlock, super.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, -7, 3, -5, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -8, 3, -4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -8, 3, 4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -7, 3, 5, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 6, 3, 5, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 7, 3, 4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 7, 3, -4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 6, 3, -5, super.roofSlabBlock, super.roofSlabMeta);
        for (int i3 = -6; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, -5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i3, 3, 5, super.roofBlock, super.roofMeta);
        }
        for (int k5 = -3; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -8, 3, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 7, 3, k5, super.roofBlock, super.roofMeta);
        }
        this.setBlockAndMetadata(world, -7, 3, -4, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 6, 3, -4, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, -7, 3, 4, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 6, 3, 4, super.roofBlock, super.roofMeta);
        for (int i3 = -5; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -4, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 4, 4, super.roofStairBlock, 3);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -7, 4, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 6, 4, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -6, 4, -4, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -6, 4, -3, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -7, 4, -3, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 5, 4, -4, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 5, 4, -3, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 6, 4, -3, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -6, 4, 4, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -6, 4, 3, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -7, 4, 3, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 5, 4, 4, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 5, 4, 3, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 6, 4, 3, super.roofStairBlock, 0);
        for (int i3 = -6; i3 <= 5; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                if ((i3 >= -5 && i3 <= 4) || (k3 >= -2 && k3 <= 2)) {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i3, 5, k3, super.roofSlabBlock, super.roofSlabMeta);
                }
            }
        }
        for (int i3 = -5; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -2, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 5, 2, super.roofStairBlock, 3);
        }
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, -5, 5, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 4, 5, k5, super.roofStairBlock, 0);
        }
        for (int i3 = -4; i3 <= 3; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                this.setBlockAndMetadata(world, i3, 5, k3, super.roofBlock, super.roofMeta);
            }
        }
        this.setBlockAndMetadata(world, 3, 5, 0, Blocks.brick_block, 0);
        this.setBlockAndMetadata(world, 3, 6, 0, Blocks.brick_block, 0);
        this.setBlockAndMetadata(world, 3, 7, 0, Blocks.flower_pot, 0);
        for (int i3 = -2; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 3, -5, Blocks.brick_block, 0);
            this.setBlockAndMetadata(world, i3, 4, -5, super.roofSlabBlock, super.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, -3, 3, -6, super.tileSlabBlock, super.tileSlabMeta);
        this.setBlockAndMetadata(world, -2, 3, -6, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -1, 4, -6, super.tileSlabBlock, super.tileSlabMeta);
        this.setBlockAndMetadata(world, 0, 4, -6, super.tileSlabBlock, super.tileSlabMeta);
        this.setBlockAndMetadata(world, 1, 3, -6, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 2, 3, -6, super.tileSlabBlock, super.tileSlabMeta);
        this.setBlockAndMetadata(world, -3, 2, -7, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -2, 3, -7, super.tileSlabBlock, super.tileSlabMeta);
        this.setBlockAndMetadata(world, -1, 3, -7, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 3, -7, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 3, -7, super.tileSlabBlock, super.tileSlabMeta);
        this.setBlockAndMetadata(world, 2, 2, -7, super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.spawnHobbitCouple(world, 0, 1, 0, 16);
        return true;
    }
    
    private void placeHedge(final World world, final int i, final int j, final int k) {
        int j2;
        for (j2 = j; !this.isOpaque(world, i, j2 - 1, k) && j2 >= j - 6; --j2) {}
        this.setBlockAndMetadata(world, i, j2, k, super.hedgeBlock, super.hedgeMeta);
    }
}
