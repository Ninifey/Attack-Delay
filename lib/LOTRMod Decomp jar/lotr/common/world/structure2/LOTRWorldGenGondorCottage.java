// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.block.Block;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenGondorCottage extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorCottage(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.wallBlock = LOTRMod.daub;
        super.wallMeta = 0;
        if (random.nextInt(3) == 0) {
            super.roofBlock = super.brick2Block;
            super.roofMeta = super.brick2Meta;
            super.roofSlabBlock = super.brick2SlabBlock;
            super.roofSlabMeta = super.brick2SlabMeta;
            super.roofStairBlock = super.brick2StairBlock;
            super.bedBlock = Blocks.bed;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 6; ++i2) {
                for (int k2 = -7; k2 <= 10; ++k2) {
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
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -5; k3 <= 5; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 == 5 && k4 == 5) {
                    for (int j2 = 3; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if (i4 == 5 || k4 == 5) {
                    for (int j2 = 1; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    this.setBlockAndMetadata(world, i3, 2, k3, super.wallBlock, super.wallMeta);
                    this.setBlockAndMetadata(world, i3, 3, k3, super.wallBlock, super.wallMeta);
                }
                else {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.rockBlock, super.rockMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 7; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                    if (random.nextInt(3) != 0) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                    if (i4 == 4 && k4 == 4) {
                        for (int j2 = 1; j2 <= 4; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -7; k3 <= -6; ++k3) {
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, LOTRMod.dirtPath, 0);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 8; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = 6; k3 <= 10; ++k3) {
                if (k3 != 10 || Math.abs(i3) < 3) {
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, LOTRMod.dirtPath, 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                    for (int j3 = 1; j3 <= 8; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                }
            }
        }
        for (final int i5 : new int[] { -5, 5 }) {
            this.setBlockAndMetadata(world, i5, 2, -3, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i5, 2, -2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i5, 2, 0, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i5, 2, 2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i5, 2, 3, super.fenceBlock, super.fenceMeta);
        }
        for (final int k2 : new int[] { -5, 5 }) {
            for (int i6 = -1; i6 <= 1; ++i6) {
                for (int j4 = 2; j4 <= 3; ++j4) {
                    this.setBlockAndMetadata(world, i6, j4, k2, super.brickBlock, super.brickMeta);
                }
            }
            for (int i6 = -4; i6 <= 4; ++i6) {
                this.setBlockAndMetadata(world, i6, 4, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            if (Math.abs(i3) > 1) {
                this.setBlockAndMetadata(world, i3, 2, -5, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, i3, 3, -5, super.wallBlock, super.wallMeta);
                this.setBlockAndMetadata(world, i3, 2, 5, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, i3, 3, 5, super.wallBlock, super.wallMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, -5, super.rockBlock, super.rockMeta);
        this.setBlockAndMetadata(world, 0, 1, -5, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -5, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -6, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 0, 5, super.rockBlock, super.rockMeta);
        this.setBlockAndMetadata(world, 0, 1, 5, super.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, 5, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, 6, Blocks.torch, 3);
        for (final int k2 : new int[] { -5, 5 }) {
            for (int l = 0; l <= 2; ++l) {
                for (int i7 = -3 + l; i7 <= 3 - l; ++i7) {
                    this.setBlockAndMetadata(world, i7, 5 + l, k2, super.wallBlock, super.wallMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 5, -5, super.wallBlock, super.wallMeta);
        this.setBlockAndMetadata(world, 0, 6, -5, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 7, -5, super.wallBlock, super.wallMeta);
        this.setBlockAndMetadata(world, 0, 5, 5, super.wallBlock, super.wallMeta);
        this.setBlockAndMetadata(world, 0, 6, 5, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 7, 5, super.wallBlock, super.wallMeta);
        for (int k5 = -6; k5 <= 6; ++k5) {
            for (int m = 0; m <= 5; ++m) {
                this.setBlockAndMetadata(world, -6 + m, 3 + m, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 6 - m, 3 + m, k5, super.roofStairBlock, 0);
            }
            this.setBlockAndMetadata(world, 0, 8, k5, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 0, 9, k5, super.roofSlabBlock, super.roofSlabMeta);
            if (Math.abs(k5) == 6) {
                for (int m = 0; m <= 4; ++m) {
                    this.setBlockAndMetadata(world, -5 + m, 3 + m, k5, super.roofStairBlock, 4);
                    this.setBlockAndMetadata(world, 5 - m, 3 + m, k5, super.roofStairBlock, 5);
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, 0, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (int k5 = -4; k5 <= 4; ++k5) {
            this.setBlockAndMetadata(world, 0, 4, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        for (int j5 = 1; j5 <= 7; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, 0, super.woodBeamBlock, super.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, -1, 3, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 1, 3, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 3, -1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 3, 1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -4, 3, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 3, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 3, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 3, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 6, -4, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 0, 5, 4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 6, 4, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -2, 1, -4, super.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 1, -4, super.bedBlock, 11);
        this.setBlockAndMetadata(world, 2, 1, -4, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 3, 1, -4, super.bedBlock, 9);
        this.setBlockAndMetadata(world, -4, 1, -2, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -4, 1, -3, super.bedBlock, 10);
        this.setBlockAndMetadata(world, 4, 1, -2, super.bedBlock, 2);
        this.setBlockAndMetadata(world, 4, 1, -3, super.bedBlock, 10);
        this.setBlockAndMetadata(world, -4, 1, 0, Blocks.furnace, 4);
        this.setBlockAndMetadata(world, -4, 1, 1, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.placePlateWithCertainty(world, random, -4, 2, 1, super.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, -4, 1, 2, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -4, 1, 3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.placeMug(world, random, -4, 2, 3, 3, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, -3, 1, 4, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.placeFlowerPot(world, -3, 2, 4, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -2, 1, 4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 1, 0, super.tableBlock, 0);
        this.placeChest(world, random, 4, 1, 1, 5, super.chestContents);
        this.placeChest(world, random, 4, 1, 2, 5, super.chestContents);
        this.setBlockAndMetadata(world, 4, 1, 3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 1, 4, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.placeFlowerPot(world, 3, 2, 4, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, 2, 1, 4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -5, 1, -6, LOTRMod.reedBars, 0);
        for (int i3 = -5; i3 <= -3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -7, LOTRMod.reedBars, 0);
        }
        this.placeFlowerPot(world, -4, 1, -6, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -3, 1, -6, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, 2, 1, -6, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, 3, 1, -6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 1, -6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 5, 1, -6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 2, -6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 1, -7, Blocks.hay_block, 0);
        for (int j5 = 1; j5 <= 2; ++j5) {
            for (int k3 = 6; k3 <= 9; ++k3) {
                this.setBlockAndMetadata(world, -4, j5, k3, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, 4, j5, k3, LOTRMod.reedBars, 0);
            }
            this.setBlockAndMetadata(world, -3, j5, 9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, -2, j5, 9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 2, j5, 9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 3, j5, 9, LOTRMod.reedBars, 0);
            for (int i8 = -2; i8 <= 2; ++i8) {
                this.setBlockAndMetadata(world, i8, j5, 10, LOTRMod.reedBars, 0);
            }
        }
        for (final int i9 : new int[] { -2, 1 }) {
            for (int i5 = i9; i9 <= i5 + 1; ++i9) {
                for (int k6 = 7; k6 <= 8; ++k6) {
                    this.setBlockAndMetadata(world, i9, 0, k6, Blocks.farmland, 7);
                    this.setBlockAndMetadata(world, i9, 1, k6, super.cropBlock, super.cropMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, -1, 9, Blocks.dirt, 0);
        this.setGrassToDirt(world, 0, -2, 9);
        this.setBlockAndMetadata(world, 0, 0, 9, Blocks.water, 0);
        this.setBlockAndMetadata(world, 0, 1, 9, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 2, 9, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 3, 9, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 0, 4, 9, Blocks.pumpkin, 0);
        for (int men = 1 + random.nextInt(2), m = 0; m < men; ++m) {
            final LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
            this.spawnNPCAndSetHome(gondorMan, world, 0, 1, -1, 16);
        }
        return true;
    }
}
