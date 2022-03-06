// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;

public class LOTRWorldGenGondorWatchfort extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorWatchfort(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.barsBlock = Blocks.iron_bars;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            final int x1 = -6;
            final int x2 = 6;
            final int z1 = -6;
            final int z2 = 34;
            for (int i2 = x1; i2 <= x2; ++i2) {
                for (int k2 = z1; k2 <= z2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int j3 = 1; j3 <= 11; ++j3) {
                for (int k3 = -5; k3 <= 5; ++k3) {
                    if (Math.abs(i3) == 5 && Math.abs(k3) == 5) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.pillar2Block, super.pillar2Meta);
                    }
                    else {
                        this.placeRandomBrick(world, random, i3, j3, k3);
                    }
                }
            }
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -6, super.brick2StairBlock, 2);
            this.setBlockAndMetadata(world, i3, 1, 6, super.brick2StairBlock, 3);
        }
        for (int k4 = -5; k4 <= 5; ++k4) {
            this.setBlockAndMetadata(world, -6, 1, k4, super.brick2StairBlock, 1);
            this.setBlockAndMetadata(world, 6, 1, k4, super.brick2StairBlock, 0);
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k5 = -6; k5 <= 6; ++k5) {
                for (int j4 = 0; !this.isOpaque(world, i3, j4, k5) && this.getY(j4) >= 0; --j4) {
                    this.placeRandomBrick(world, random, i3, j4, k5);
                    this.setGrassToDirt(world, i3, j4 - 1, k5);
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k5 = -4; k5 <= 4; ++k5) {
                for (int j4 = 2; j4 <= 5; ++j4) {
                    this.setAir(world, i3, j4, k5);
                }
                for (int j4 = 7; j4 <= 10; ++j4) {
                    this.setAir(world, i3, j4, k5);
                }
            }
        }
        for (final int j5 : new int[] { 4, 9 }) {
            this.setBlockAndMetadata(world, -4, j5, -2, Blocks.torch, 2);
            this.setBlockAndMetadata(world, -4, j5, 2, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 4, j5, -2, Blocks.torch, 1);
            this.setBlockAndMetadata(world, 4, j5, 2, Blocks.torch, 1);
            this.setBlockAndMetadata(world, -2, j5, -4, Blocks.torch, 3);
            this.setBlockAndMetadata(world, 2, j5, -4, Blocks.torch, 3);
            this.setBlockAndMetadata(world, -2, j5, 4, Blocks.torch, 4);
            this.setBlockAndMetadata(world, 2, j5, 4, Blocks.torch, 4);
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int j3 = 12; j3 <= 16; ++j3) {
                for (int k3 = -4; k3 <= 4; ++k3) {
                    if (Math.abs(i3) == 4 && Math.abs(k3) == 4) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.pillar2Block, super.pillar2Meta);
                    }
                    else {
                        this.placeRandomBrick(world, random, i3, j3, k3);
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 12, -5, super.brick2StairBlock, 2);
            this.setBlockAndMetadata(world, i3, 12, 5, super.brick2StairBlock, 3);
        }
        for (int k4 = -4; k4 <= 4; ++k4) {
            this.setBlockAndMetadata(world, -5, 12, k4, super.brick2StairBlock, 1);
            this.setBlockAndMetadata(world, 5, 12, k4, super.brick2StairBlock, 0);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k5 = -3; k5 <= 3; ++k5) {
                for (int j4 = 12; j4 <= 15; ++j4) {
                    this.setAir(world, i3, j4, k5);
                }
            }
        }
        this.setBlockAndMetadata(world, -3, 14, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 14, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 14, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 14, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 14, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 14, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 14, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 14, 3, Blocks.torch, 4);
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.placeRandomWall(world, random, i3, 17, -4);
            this.placeRandomWall(world, random, i3, 17, 4);
        }
        for (int k4 = -4; k4 <= 4; ++k4) {
            this.placeRandomWall(world, random, -4, 17, k4);
            this.placeRandomWall(world, random, 4, 17, k4);
        }
        for (final int i4 : new int[] { -4, 4 }) {
            for (final int k6 : new int[] { -4, 4 }) {
                for (int j6 = 17; j6 <= 19; ++j6) {
                    this.setBlockAndMetadata(world, i4, j6, k6, super.pillar2Block, super.pillar2Meta);
                }
                this.placeRandomBrick(world, random, i4, 20, k6);
            }
        }
        this.setBlockAndMetadata(world, -4, 19, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 4, 19, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 19, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 4, 19, 3, Blocks.torch, 4);
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k5 = -2; k5 <= 2; ++k5) {
                this.setBlockAndMetadata(world, i3, 21, k5, super.brick2Block, super.brick2Meta);
                if (Math.abs(i3) <= 1 && Math.abs(k5) <= 1) {
                    this.setBlockAndMetadata(world, i3, 22, k5, super.brick2Block, super.brick2Meta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 22, k5, super.brick2SlabBlock, super.brick2SlabMeta);
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k5 = -5; k5 <= 5; ++k5) {
                this.setBlockAndMetadata(world, i3, 21, k5, super.brick2SlabBlock, super.brick2SlabMeta);
            }
        }
        for (final int i4 : new int[] { -4, 4 }) {
            for (final int k6 : new int[] { -4, 4 }) {
                this.setBlockAndMetadata(world, i4, 20, k6 - 1, super.brick2StairBlock, 6);
                this.setBlockAndMetadata(world, i4, 20, k6 + 1, super.brick2StairBlock, 7);
                for (int k7 = k6 - 1; k7 <= k6 + 1; ++k7) {
                    this.setBlockAndMetadata(world, i4 - 1, 20, k7, super.brick2StairBlock, 5);
                    this.setBlockAndMetadata(world, i4 + 1, 20, k7, super.brick2StairBlock, 4);
                }
            }
        }
        this.setBlockAndMetadata(world, -4, 21, -4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, -4, 21, -3, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, -4, 21, -2, super.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -4, 21, -1, super.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, -4, 21, 0, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, -4, 21, 1, super.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, -4, 21, 2, super.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -4, 21, 3, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, -4, 21, 4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, -3, 21, -4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, -3, 21, -3, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, -3, 21, -2, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, -3, 21, -1, super.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -3, 21, 0, super.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -3, 21, 1, super.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -3, 21, 2, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, -3, 21, 3, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, -3, 21, 4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, -4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, -3, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, -2, super.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 4, 21, -1, super.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, 4, 21, 0, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, 1, super.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, 4, 21, 2, super.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 4, 21, 3, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, 4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 3, 21, -4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 3, 21, -3, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, 3, 21, -2, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, 3, 21, -1, super.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 3, 21, 0, super.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 3, 21, 1, super.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 3, 21, 2, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, 3, 21, 3, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, 3, 21, 4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, -2, 21, 4, super.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, -1, 21, 4, super.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 0, 21, 4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 1, 21, 4, super.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, 2, 21, 4, super.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, -2, 21, 3, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, -1, 21, 3, super.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, 0, 21, 3, super.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, 1, 21, 3, super.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, 2, 21, 3, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, -2, 21, -4, super.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, -1, 21, -4, super.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 0, 21, -4, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 1, 21, -4, super.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, 2, 21, -4, super.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, -2, 21, -3, super.brick2Block, super.brick2Meta);
        this.setBlockAndMetadata(world, -1, 21, -3, super.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, 0, 21, -3, super.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, 1, 21, -3, super.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, 2, 21, -3, super.brick2Block, super.brick2Meta);
        this.placeBarredWindowOnZ(world, -5, 3, 0);
        this.placeBarredWindowOnZ(world, 5, 3, 0);
        this.placeBarredWindowOnX(world, 0, 3, -5);
        this.placeBarredWindowOnX(world, 0, 3, 5);
        this.placeBarredWindowOnZ(world, -5, 8, 0);
        this.placeBarredWindowOnZ(world, 5, 8, 0);
        this.placeBarredWindowOnX(world, 0, 8, -5);
        this.placeBarredWindowOnX(world, 0, 8, 5);
        this.placeBarredWindowOnZ(world, -4, 13, 0);
        this.placeBarredWindowOnZ(world, 4, 13, 0);
        this.placeBarredWindowOnX(world, 0, 13, -4);
        this.placeBarredWindowOnX(world, 0, 13, 4);
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k5 = -8; k5 <= -7; ++k5) {
                for (int j4 = 0; !this.isOpaque(world, i3, j4, k5) && this.getY(j4) >= 0; --j4) {
                    this.placeRandomBrick(world, random, i3, j4, k5);
                    this.setGrassToDirt(world, i3, j4 - 1, k5);
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            if (Math.abs(i3) == 2) {
                for (int j3 = 1; j3 <= 4; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, -6, super.pillarBlock, super.pillarMeta);
                }
            }
            this.setBlockAndMetadata(world, i3, 5, -6, super.brick2SlabBlock, super.brick2SlabMeta);
            this.placeRandomStairs(world, random, i3, 1, -8, 2);
        }
        this.placeWallBanner(world, 0, 7, -5, super.bannerType, 2);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k5 = -7; k5 <= -6; ++k5) {
                this.placeRandomBrick(world, random, i3, 1, k5);
            }
            this.placeRandomBrick(world, random, i3, 1, -5);
            this.setAir(world, i3, 2, -5);
            this.setAir(world, i3, 3, -5);
            this.setAir(world, i3, 4, -5);
        }
        this.placeRandomStairs(world, random, -2, 1, -7, 1);
        this.placeRandomStairs(world, random, 2, 1, -7, 0);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int j3 = 2; j3 <= 4; ++j3) {
                this.setBlockAndMetadata(world, i3, j3, -6, super.gateBlock, 3);
            }
        }
        this.placeRandomSlab(world, random, -4, 2, -4, true);
        this.placeBarrel(world, random, -4, 3, -4, 4, LOTRFoods.GONDOR_DRINK);
        this.placeRandomSlab(world, random, -4, 2, -3, true);
        this.placeBarrel(world, random, -4, 3, -3, 4, LOTRFoods.GONDOR_DRINK);
        this.placeChest(world, random, -4, 2, -2, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
        this.placeRandomSlab(world, random, 4, 2, -4, true);
        this.placeBarrel(world, random, 4, 3, -4, 5, LOTRFoods.GONDOR_DRINK);
        this.placeRandomSlab(world, random, 4, 2, -3, true);
        this.placeBarrel(world, random, 4, 3, -3, 5, LOTRFoods.GONDOR_DRINK);
        this.placeChest(world, random, 4, 2, -2, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
        this.setBlockAndMetadata(world, -4, 2, 4, super.tableBlock, 0);
        this.setBlockAndMetadata(world, 4, 2, 4, super.tableBlock, 0);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int step = 0; step <= 3; ++step) {
                final int k3 = -1 + step;
                final int j5 = 2 + step;
                this.setAir(world, i3, 6, k3);
                for (int j7 = 2; j7 < j5; ++j7) {
                    this.placeRandomBrick(world, random, i3, j7, k3);
                }
                this.placeRandomStairs(world, random, i3, j5, k3, 2);
            }
            this.placeRandomStairs(world, random, i3, 6, 3, 2);
        }
        this.placeChest(world, random, 0, 2, 2, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        this.setAir(world, 0, 3, 2);
        this.setBlockAndMetadata(world, 0, 7, -4, LOTRMod.commandTable, 0);
        for (final int i4 : new int[] { -3, 3 }) {
            for (int step2 = 0; step2 <= 4; ++step2) {
                final int k2 = 2 - step2;
                final int j2 = 7 + step2;
                this.setAir(world, i4, 11, k2);
                for (int j8 = 7; j8 < j2; ++j8) {
                    this.placeRandomBrick(world, random, i4, j8, k2);
                }
                this.placeRandomStairs(world, random, i4, j2, k2, 3);
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int step = 0; step <= 3; ++step) {
                final int k3 = -2 + step;
                final int j5 = 12 + step;
                this.setAir(world, i3, 16, k3);
                for (int j7 = 12; j7 < j5; ++j7) {
                    this.placeRandomBrick(world, random, i3, j7, k3);
                }
                this.placeRandomStairs(world, random, i3, j5, k3, 2);
            }
            this.placeRandomStairs(world, random, i3, 16, 2, 2);
        }
        for (int k4 = 5; k4 <= 28; ++k4) {
            for (int j3 = 12; j3 <= 15; ++j3) {
                for (int i5 = -2; i5 <= 2; ++i5) {
                    this.setAir(world, i5, j3, k4);
                }
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.placeRandomBrick(world, random, i3, 13, 4);
            this.placeRandomBrick(world, random, i3, 14, 4);
        }
        for (final int i4 : new int[] { -2, 2 }) {
            this.placeRandomBrick(world, random, i4, 12, 5);
            this.placeRandomBrick(world, random, i4, 13, 5);
            this.setBlockAndMetadata(world, i4, 14, 5, super.brick2WallBlock, super.brick2WallMeta);
            this.setBlockAndMetadata(world, i4, 15, 5, Blocks.torch, 5);
        }
        this.setBlockAndMetadata(world, 0, 12, 4, super.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 13, 4, super.doorBlock, 8);
        for (int k4 = 6; k4 <= 28; ++k4) {
            for (int i6 = -1; i6 <= 1; ++i6) {
                this.placeRandomBrick(world, random, i6, 11, k4);
            }
            this.placeRandomWall(world, random, -2, 12, k4);
            this.placeRandomWall(world, random, 2, 12, k4);
            this.placeRandomStairs(world, random, -2, 11, k4, 5);
            this.placeRandomStairs(world, random, 2, 11, k4, 4);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.placeRandomStairs(world, random, i3, 10, 6, 7);
            this.placeRandomStairs(world, random, i3, 10, 16, 6);
            this.placeRandomStairs(world, random, i3, 10, 18, 7);
            this.placeRandomStairs(world, random, i3, 10, 28, 6);
            for (int j3 = 10; !this.isOpaque(world, i3, j3, 17) && this.getY(j3) >= 0; --j3) {
                this.placeRandomBrick(world, random, i3, j3, 17);
                this.setGrassToDirt(world, i3, j3 - 1, 17);
            }
        }
        for (int j9 = 12; j9 <= 13; ++j9) {
            this.placeRandomBrick(world, random, -2, j9, 11);
            this.placeRandomBrick(world, random, 2, j9, 11);
            this.placeRandomBrick(world, random, -2, j9, 23);
            this.placeRandomBrick(world, random, 2, j9, 23);
        }
        this.setBlockAndMetadata(world, -1, 13, 11, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 13, 11, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 13, 23, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 13, 23, Blocks.torch, 1);
        this.placeBanner(world, -2, 14, 11, super.bannerType, 3);
        this.placeBanner(world, 2, 14, 11, super.bannerType, 1);
        this.placeBanner(world, -2, 14, 23, super.bannerType, 3);
        this.placeBanner(world, 2, 14, 23, super.bannerType, 1);
        for (int j9 = 12; j9 <= 14; ++j9) {
            this.placeRandomBrick(world, random, -2, j9, 17);
            this.placeRandomBrick(world, random, 2, j9, 17);
        }
        this.placeRandomBrick(world, random, -2, 15, 17);
        this.placeRandomBrick(world, random, 2, 15, 17);
        this.placeRandomStairs(world, random, -1, 15, 17, 4);
        this.placeRandomStairs(world, random, 1, 15, 17, 5);
        this.placeRandomSlab(world, random, 0, 15, 17, true);
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 16, 17, super.brick2Block, super.brick2Meta);
        }
        this.setBlockAndMetadata(world, -2, 16, 17, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 2, 16, 17, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, 0, 17, 17, super.brick2SlabBlock, super.brick2SlabMeta);
        this.setBlockAndMetadata(world, -2, 14, 16, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 14, 16, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 14, 18, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 14, 18, Blocks.torch, 3);
        final LOTRWorldGenBeaconTower beaconTower = new LOTRWorldGenBeaconTower(super.notifyChanges);
        beaconTower.restrictions = false;
        beaconTower.generateRoom = false;
        beaconTower.strFief = super.strFief;
        final int beaconX = 0;
        final int beaconY = 12;
        final int beaconZ = 34;
        beaconTower.generateWithSetRotationAndHeight(world, random, this.getX(beaconX, beaconZ), this.getY(beaconY), this.getZ(beaconX, beaconZ), (this.getRotationMode() + 2) % 4, -8);
        this.setAir(world, -1, 12, 29);
        this.setAir(world, 0, 12, 29);
        this.setAir(world, 1, 12, 29);
        final Class[] soldierClasses = super.strFief.getSoldierClasses();
        for (int soldiers = 4 + random.nextInt(4), l = 0; l < soldiers; ++l) {
            Class entityClass = soldierClasses[0];
            if (random.nextInt(3) == 0) {
                entityClass = soldierClasses[1];
            }
            final LOTREntityGondorMan soldier = (LOTREntityGondorMan)LOTREntities.createEntityByClass(entityClass, world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 2, -3, 32);
        }
        final LOTREntityGondorMan captain = super.strFief.createCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 15, 0, 8);
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(soldierClasses[0], soldierClasses[1]);
        respawner.setCheckRanges(24, -8, 18, 12);
        respawner.setSpawnRanges(4, 2, 17, 32);
        this.placeNPCRespawner(respawner, world, 0, 2, 0);
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(10) == 0) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, super.brickMossyBlock, super.brickMossyMeta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, super.brickCrackedBlock, super.brickCrackedMeta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, super.brickBlock, super.brickMeta);
        }
    }
    
    private void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k, final boolean inverted) {
        final int flag = inverted ? 8 : 0;
        if (random.nextInt(10) == 0) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, super.brickMossySlabBlock, super.brickMossySlabMeta | flag);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, super.brickCrackedSlabBlock, super.brickCrackedSlabMeta | flag);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, super.brickSlabBlock, super.brickSlabMeta | flag);
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (random.nextInt(10) == 0) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, super.brickMossyStairBlock, meta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, super.brickCrackedStairBlock, meta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, super.brickStairBlock, meta);
        }
    }
    
    private void placeRandomWall(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(10) == 0) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, super.brickMossyWallBlock, super.brickMossyWallMeta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, super.brickCrackedWallBlock, super.brickCrackedWallMeta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, super.brickWallBlock, super.brickWallMeta);
        }
    }
    
    private void placeBarredWindowOnX(final World world, final int i, final int j, final int k) {
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int j2 = 0; j2 <= 1; ++j2) {
                this.setBlockAndMetadata(world, i + i2, j + j2, k, super.barsBlock, 0);
            }
        }
    }
    
    private void placeBarredWindowOnZ(final World world, final int i, final int j, final int k) {
        for (int k2 = -1; k2 <= 1; ++k2) {
            for (int j2 = 0; j2 <= 1; ++j2) {
                this.setBlockAndMetadata(world, i, j + j2, k + k2, super.barsBlock, 0);
            }
        }
    }
}
