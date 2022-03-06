// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingStatue extends LOTRWorldGenEasterlingStructure
{
    public LOTRWorldGenEasterlingStatue(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -6; i2 <= 6; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -6; i2 <= 6; ++i2) {
            for (int k2 = -5; k2 <= 5; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 1; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, LOTRMod.rock, 4);
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                for (int j3 = 2; j3 <= 25; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                if (i2 == -6) {
                    this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.stairsRedRock, 1);
                }
                else if (i2 == 6) {
                    this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.stairsRedRock, 0);
                }
                else if (k2 == -5) {
                    this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.stairsRedRock, 2);
                }
                else if (k2 == 5) {
                    this.setBlockAndMetadata(world, i2, 1, k2, LOTRMod.stairsRedRock, 3);
                }
            }
        }
        for (int k4 = 0; k4 <= 1; ++k4) {
            this.setBlockAndMetadata(world, -2, 2, k4, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 2, k4, super.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -2, 2, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -1, 2, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -2, 3, 1, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 3, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 3, 2, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 3, 2, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -2, 4, 1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 4, 1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -2, 4, 2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 4, 2, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 5, 1, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 5, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -3, 5, 2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -2, 5, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -1, 5, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -2, 6, 1, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 6, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 6, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -1, 6, 2, super.brickBlock, super.brickMeta);
        for (int k4 = -1; k4 <= 0; ++k4) {
            this.setBlockAndMetadata(world, 1, 2, k4, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 2, k4, super.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, 1, 2, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 2, 2, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 3, 0, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 3, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 3, 1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 3, 1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 4, 0, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 4, 0, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 4, 1, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 4, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 5, 0, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 5, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 5, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 2, 5, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 3, 5, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 6, 0, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 6, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 6, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 2, 6, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 6, 2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 6, 2, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 7, 0, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 7, 0, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 7, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 2, 7, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 7, 1, super.brickStairBlock, 5);
        for (int i2 = -1; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 7, 1, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 3, 7, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -3, 7, 2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -2, 7, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -1, 7, 2, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 7, 2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 7, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 2, 7, 2, super.brickBlock, super.brickMeta);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 8, 0, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 8, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 8, 1, super.brickStairBlock, 5);
        for (int i2 = -1; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 8, 1, super.brickBlock, super.brickMeta);
        }
        for (int i2 = -2; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 8, 2, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 8, 2, super.brickStairBlock, 4);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 9, 0, super.brickWallBlock, super.brickWallMeta);
        }
        this.setBlockAndMetadata(world, 2, 9, 0, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -2, 9, 1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 9, 1, super.brickWallBlock, super.brickWallMeta);
        for (int i2 = 0; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 9, 1, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 3, 9, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -3, 9, 2, super.brickStairBlock, 5);
        for (int i2 = -2; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 9, 2, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 9, 2, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 10, 0, super.brickWallBlock, super.brickWallMeta);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 10, 0, super.pillarBlock, super.pillarMeta);
        }
        this.setBlockAndMetadata(world, 2, 10, 0, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 10, 1, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, 2, 10, 1, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, -2, 10, 2, super.brickWallBlock, super.brickWallMeta);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 10, 2, super.brickStairBlock, 3);
        }
        this.setBlockAndMetadata(world, 2, 10, 2, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 11, 0, super.brickWallBlock, super.brickWallMeta);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 11, 0, super.pillarBlock, super.pillarMeta);
        }
        this.setBlockAndMetadata(world, 2, 11, 0, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 11, 1, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, 2, 11, 1, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, -2, 11, 2, super.brickWallBlock, super.brickWallMeta);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 11, 2, super.pillarBlock, super.pillarMeta);
        }
        this.setBlockAndMetadata(world, 2, 11, 2, super.brickWallBlock, super.brickWallMeta);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 12, 0, super.brickWallBlock, super.brickWallMeta);
        }
        this.setBlockAndMetadata(world, -2, 12, 1, super.brickStairBlock, 5);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 12, 1, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 12, 1, super.brickStairBlock, 4);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 12, 2, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -1, 13, 0, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 13, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 13, 0, super.brickStairBlock, 4);
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 13, 1, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 13, 2, super.brickStairBlock, 5);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 13, 2, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 13, 2, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 14, 0, super.brickStairBlock, 1);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 14, 0, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 14, 0, super.brickStairBlock, 0);
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 14, 1, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 14, 2, super.brickStairBlock, 1);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 14, 2, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 14, 2, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 15, 0, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 15, 0, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 15, 0, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -2, 15, 1, super.brickStairBlock, 1);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 15, 1, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 15, 1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 15, 2, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 15, 2, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 15, 2, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -4, 2, -1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 2, 0, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 2, 2, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 2, 3, super.brickBlock, super.brickMeta);
        for (int k4 = -1; k4 <= 3; ++k4) {
            this.setBlockAndMetadata(world, -4, 3, k4, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -4, 4, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 4, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 4, 1, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, -4, 4, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 4, 3, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 5, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 5, 1, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, -4, 5, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 6, -1, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 6, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 6, 1, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, -4, 6, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 6, 3, super.brickStairBlock, 7);
        for (int k4 = -1; k4 <= 3; ++k4) {
            this.setBlockAndMetadata(world, -4, 7, k4, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -5, 8, -1, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -4, 8, -1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 8, 0, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 8, 2, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 8, 3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -5, 9, -1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 9, -1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -4, 9, 0, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 10, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 10, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 10, 1, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 11, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 11, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -4, 11, 1, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 11, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 11, 2, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 12, 0, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -3, 12, 0, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 12, 1, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 12, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 12, 2, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 13, 0, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -3, 13, 0, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 13, 1, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 13, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 13, 2, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 14, 0, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 14, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 14, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 14, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -4, 14, 2, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 14, 2, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 15, 0, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 15, 1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 15, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 15, 2, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 16, 1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 3, 16, 1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 15, 0, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 15, 1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 15, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 3, 15, 2, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 14, -1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 14, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 4, 14, 0, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 14, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 4, 14, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 14, 2, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 13, -3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 2, 13, -3, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 13, -2, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 3, 13, -2, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 13, -1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 3, 13, -1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 4, 13, -1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 13, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 4, 13, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 13, 1, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 12, -3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 2, 12, -3, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 12, -2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 3, 12, -2, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 12, -1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 4, 12, -1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 4, 12, 0, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 2, -3, super.brickBlock, super.brickMeta);
        for (int j4 = 3; j4 <= 11; ++j4) {
            this.setBlockAndMetadata(world, 1, j4, -3, super.brickWallBlock, super.brickWallMeta);
        }
        for (int j4 = 14; j4 <= 18; ++j4) {
            this.setBlockAndMetadata(world, 1, j4, -3, super.brickWallBlock, super.brickWallMeta);
        }
        for (int j4 = 19; j4 <= 27; ++j4) {
            this.setBlockAndMetadata(world, 1, j4, -3, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 1, 28, -3, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 21, -4, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 21, -2, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 23, -4, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 24, -5, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 24, -4, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 25, -5, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 25, -4, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 26, -4, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 16, 0, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 16, 0, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 16, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 16, 1, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 16, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 16, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 16, 2, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -1, 17, 0, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 17, 0, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 1, 17, 0, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 17, 1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 17, 1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 17, 2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 17, 2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 17, 2, super.brickStairBlock, 4);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 18, -1, super.brickStairBlock, 6);
        }
        for (int k4 = 0; k4 <= 2; ++k4) {
            this.setBlockAndMetadata(world, -2, 18, k4, super.brickStairBlock, 5);
            for (int i4 = -1; i4 <= 1; ++i4) {
                this.setBlockAndMetadata(world, i4, 18, k4, super.brickBlock, super.brickMeta);
            }
            this.setBlockAndMetadata(world, 2, 18, k4, super.brickStairBlock, 4);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 18, 3, super.brickStairBlock, 7);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 19, 0, super.brickStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -1, 19, 1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 19, 1, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 1, 19, 1, super.brickStairBlock, 0);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 19, 2, super.brickStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -2, 20, 2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 20, 2, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 20, 2, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 20, 2, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 21, 2, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 21, 2, super.brickStairBlock, 1);
        return true;
    }
}
