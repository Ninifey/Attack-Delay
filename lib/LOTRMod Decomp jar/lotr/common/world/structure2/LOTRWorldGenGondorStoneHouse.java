// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenGondorStoneHouse extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorStoneHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        if (random.nextBoolean()) {
            super.doorBlock = LOTRMod.doorLebethron;
        }
        super.bedBlock = Blocks.bed;
        if (random.nextBoolean()) {
            super.plateBlock = LOTRMod.plateBlock;
        }
        else {
            super.plateBlock = LOTRMod.ceramicPlateBlock;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -7; k2 <= 6; ++k2) {
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
        for (int i3 = -5; i3 <= 4; ++i3) {
            for (int k3 = -7; k3 <= 5; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i3 == -5) {
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                    this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.grass, 0);
                    for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else {
                    for (int j2 = 0; (j2 == 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (k3 >= -4) {
                        if ((k3 == -4 || k3 == 5) && i4 == 4) {
                            for (int j2 = 1; j2 <= 7; ++j2) {
                                this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                            }
                        }
                        else if (k3 == -4 || k3 == 5 || i4 == 4) {
                            for (int j2 = 1; j2 <= 7; ++j2) {
                                this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                            }
                        }
                        else if (k3 >= -3 && k3 <= 4 && i4 <= 3) {
                            for (int j2 = 1; j2 <= 3; ++j2) {
                                this.setAir(world, i3, j2, k3);
                            }
                            this.setBlockAndMetadata(world, i3, 4, k3, super.plankBlock, super.plankMeta);
                            for (int j2 = 5; j2 <= 9; ++j2) {
                                this.setAir(world, i3, j2, k3);
                            }
                        }
                    }
                    if (k3 <= -5) {
                        if (k3 == -7) {
                            if (i4 == 4 || i4 == 2) {
                                for (int j2 = 1; j2 <= 3; ++j2) {
                                    this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                                }
                            }
                            else {
                                for (int j2 = 1; j2 <= 3; ++j2) {
                                    this.setAir(world, i3, j2, k3);
                                }
                            }
                        }
                        else if (i4 == 4) {
                            this.setBlockAndMetadata(world, i3, 1, k3, super.brickBlock, super.brickMeta);
                            this.placeFlowerPot(world, i3, 2, k3, this.getRandomFlower(world, random));
                            this.setBlockAndMetadata(world, i3, 3, k3, super.fenceBlock, super.fenceMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, 0, k3, super.plankBlock, super.plankMeta);
                            for (int j2 = 1; j2 <= 3; ++j2) {
                                this.setAir(world, i3, j2, k3);
                            }
                        }
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            final int i5 = Math.abs(i3);
            for (int step = 0; step <= 2; ++step) {
                final int j3 = 8 + step;
                this.setBlockAndMetadata(world, i3, j3, -5 + step, super.brick2StairBlock, 2);
                this.setBlockAndMetadata(world, i3, j3, -4 + step, super.brick2Block, super.brick2Meta);
                this.setBlockAndMetadata(world, i3, j3, -3 + step, super.brick2StairBlock, 7);
                this.setBlockAndMetadata(world, i3, j3, 6 - step, super.brick2StairBlock, 3);
                this.setBlockAndMetadata(world, i3, j3, 5 - step, super.brick2Block, super.brick2Meta);
                this.setBlockAndMetadata(world, i3, j3, 4 - step, super.brick2StairBlock, 6);
                if (i5 == 4) {
                    for (int j4 = 8; j4 <= j3 - 1; ++j4) {
                        this.setBlockAndMetadata(world, i3, j4, -5 + step, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i3, j4, 6 - step, super.brickBlock, super.brickMeta);
                    }
                }
            }
            for (int k5 = -2; k5 <= 3; ++k5) {
                final int j3 = 10;
                this.setBlockAndMetadata(world, i3, j3, k5, super.brick2Block, super.brick2Meta);
                if (i5 == 4) {
                    for (int j4 = 8; j4 <= j3 - 1; ++j4) {
                        this.setBlockAndMetadata(world, i3, j4, k5, super.brickBlock, super.brickMeta);
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= -3; ++i3) {
            for (int k3 = -1; k3 <= 2; ++k3) {
                for (int j5 = 1; j5 <= 11; ++j5) {
                    this.setBlockAndMetadata(world, i3, j5, k3, super.brickBlock, super.brickMeta);
                }
                this.setGrassToDirt(world, i3, 0, k3);
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -7, super.brick2SlabBlock, super.brick2SlabMeta);
            this.setBlockAndMetadata(world, i3, 4, -6, super.brick2Block, super.brick2Meta);
            this.setBlockAndMetadata(world, i3, 4, -5, super.brick2Block, super.brick2Meta);
            this.setBlockAndMetadata(world, i3, 5, -5, super.brick2SlabBlock, super.brick2SlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 3, -6, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, 0, 1, -4, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, super.doorBlock, 8);
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 3);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -1; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, i3, 1, k3, Blocks.carpet, 15);
            }
        }
        if (random.nextInt(4) == 0) {
            this.placeChest(world, random, 0, 0, 1, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_HOUSE_TREASURE);
        }
        this.setBlockAndMetadata(world, 3, 2, 4, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 3, 1, LOTRMod.chandelier, 2);
        for (int k6 = 0; k6 <= 1; ++k6) {
            this.setBlockAndMetadata(world, -3, 1, k6, Blocks.iron_bars, 0);
            this.setBlockAndMetadata(world, -3, 2, k6, Blocks.furnace, 4);
            this.setBlockAndMetadata(world, -4, 0, k6, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, -4, 1, k6, (Block)Blocks.fire, 0);
            for (int j6 = 2; j6 <= 10; ++j6) {
                this.setAir(world, -4, j6, k6);
            }
        }
        for (int k6 = -3; k6 <= -2; ++k6) {
            this.setBlockAndMetadata(world, -3, 1, k6, super.plankBlock, super.plankMeta);
            this.placeMug(world, random, -3, 2, k6, 3, LOTRFoods.GONDOR_DRINK);
            this.setBlockAndMetadata(world, -3, 3, k6, super.plankStairBlock, 4);
        }
        for (int k6 = 3; k6 <= 4; ++k6) {
            this.setBlockAndMetadata(world, -3, 3, k6, super.plankStairBlock, 4);
        }
        this.setBlockAndMetadata(world, -3, 1, 3, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -3, 1, 4, super.plankBlock, super.plankMeta);
        this.placePlateWithCertainty(world, random, -3, 2, 4, super.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, -2, 1, 4, Blocks.crafting_table, 0);
        for (int k6 = 0; k6 <= 3; ++k6) {
            this.setAir(world, 3, 4, k6);
        }
        for (int step2 = 0; step2 <= 3; ++step2) {
            this.setBlockAndMetadata(world, 3, 1 + step2, 2 - step2, super.plankStairBlock, 3);
        }
        this.setBlockAndMetadata(world, 3, 1, 1, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 3, 1, 0, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 3, 2, 0, super.plankStairBlock, 6);
        this.placeChest(world, random, 3, 1, -1, 5, LOTRChestContents.GONDOR_HOUSE);
        this.setBlockAndMetadata(world, 3, 1, -2, super.tableBlock, 0);
        this.setBlockAndMetadata(world, 3, 1, -3, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 3, 2, -3, super.fenceBlock, super.fenceMeta);
        for (int k6 = -3; k6 <= -1; ++k6) {
            this.setBlockAndMetadata(world, 3, 3, k6, super.plankBlock, super.plankMeta);
        }
        this.spawnItemFrame(world, 3, 3, -1, 3, new ItemStack(Items.clock));
        for (int j7 = 1; j7 <= 3; ++j7) {
            this.setBlockAndMetadata(world, 0, j7, 5, super.pillarBlock, super.pillarMeta);
        }
        this.placeWallBanner(world, 0, 3, 5, super.bannerType, 2);
        for (final int i6 : new int[] { -3, 1 }) {
            this.setBlockAndMetadata(world, i6, 2, 5, super.brickStairBlock, 0);
            this.setBlockAndMetadata(world, i6, 3, 5, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, i6 + 1, 2, 5, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, i6 + 1, 3, 5, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, i6 + 2, 2, 5, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, i6 + 2, 3, 5, super.brickStairBlock, 5);
        }
        for (final int k2 : new int[] { -4, 5 }) {
            for (final int i7 : new int[] { -3, 1 }) {
                this.setBlockAndMetadata(world, i7, 6, k2, super.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i7, 7, k2, super.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i7 + 1, 6, k2, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, i7 + 1, 7, k2, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, i7 + 1, 8, k2, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i7 + 2, 6, k2, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i7 + 2, 7, k2, super.brickStairBlock, 5);
            }
            this.setBlockAndMetadata(world, 0, 6, k2, LOTRMod.brick, 5);
        }
        this.setBlockAndMetadata(world, -2, 5, 0, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -2, 6, 0, LOTRMod.plateBlock, 0);
        this.setBlockAndMetadata(world, -2, 5, 1, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, -2, 6, 1, 3, LOTRFoods.GONDOR_DRINK);
        for (final int k2 : new int[] { -1, 2 }) {
            this.setBlockAndMetadata(world, -2, 5, k2, super.bedBlock, 11);
            this.setBlockAndMetadata(world, -1, 5, k2, super.bedBlock, 3);
            this.spawnItemFrame(world, -3, 7, k2, 1, this.getGondorFramedItem(random));
        }
        for (int k6 = 0; k6 <= 1; ++k6) {
            for (int j6 = 7; j6 <= 8; ++j6) {
                this.setBlockAndMetadata(world, -3, j6, k6, super.pillarBlock, super.pillarMeta);
            }
        }
        this.placeChest(world, random, -3, 5, -3, 4, LOTRChestContents.GONDOR_HOUSE);
        this.setBlockAndMetadata(world, -3, 5, -2, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 5, 3, super.plankBlock, super.plankMeta);
        this.placeChest(world, random, -3, 5, 4, 4, LOTRChestContents.GONDOR_HOUSE);
        this.setBlockAndMetadata(world, 0, 9, -2, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, 0, 8, -2, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, 0, 9, 3, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, 0, 8, 3, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -3, 7, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 7, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 7, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 7, 3, Blocks.torch, 1);
        for (int k6 = -1; k6 <= 2; ++k6) {
            this.setBlockAndMetadata(world, -5, 12, k6, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 12, k6, super.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -4, 12, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 12, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 12, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 12, 2, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 13, 0, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, -4, 13, 1, super.brickWallBlock, super.brickWallMeta);
        for (int men = 2, l = 0; l < men; ++l) {
            final LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
            this.spawnNPCAndSetHome(gondorMan, world, 0, 1, 0, 16);
        }
        return true;
    }
}
