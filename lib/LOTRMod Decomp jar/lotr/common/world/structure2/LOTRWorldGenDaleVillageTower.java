// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.item.LOTRItemBanner;
import com.google.common.math.IntMath;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDaleVillageTower extends LOTRWorldGenDaleStructure
{
    public LOTRWorldGenDaleVillageTower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 <= 2 && k3 <= 2) {
                    for (int j3 = 0; (j3 == 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.floorBlock, super.floorMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                    for (int j3 = 1; j3 <= 10; ++j3) {
                        this.setAir(world, i2, j3, k2);
                    }
                }
                if ((i3 == 2 && k3 == 3) || (k3 == 2 && i3 == 3)) {
                    for (int j3 = 1; !this.isOpaque(world, i2, j3, k2) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickWallBlock, super.brickWallMeta);
                    }
                }
                if (i3 <= 2 && k3 <= 2) {
                    if (i3 == 2 && k3 == 2) {
                        for (int j3 = 1; j3 <= 6; ++j3) {
                            this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                        }
                    }
                    else if (i3 == 2 || k3 == 2) {
                        for (int j3 = 4; j3 <= 6; ++j3) {
                            this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                        }
                    }
                    if (i3 == 2 && k3 == 2) {
                        this.setBlockAndMetadata(world, i2, 7, k2, super.brickWallBlock, super.brickWallMeta);
                    }
                    if ((i3 == 2 && k3 == 1) || (k3 == 2 && i3 == 1)) {
                        this.setBlockAndMetadata(world, i2, 7, k2, super.brickBlock, super.brickMeta);
                    }
                    if ((i3 == 2 && k3 == 0) || (k3 == 2 && i3 == 0)) {
                        this.setBlockAndMetadata(world, i2, 7, k2, super.barsBlock, 0);
                        this.setBlockAndMetadata(world, i2, 8, k2, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i2, 9, k2, super.brickSlabBlock, super.brickSlabMeta);
                    }
                    if (i3 <= 1 && k3 <= 1) {
                        if (i3 == 1 && k3 == 1) {
                            this.setBlockAndMetadata(world, i2, 6, k2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                            this.setBlockAndMetadata(world, i2, 7, k2, super.brickWallBlock, super.brickWallMeta);
                            for (int j3 = 8; j3 <= 11; ++j3) {
                                this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                            }
                            this.setBlockAndMetadata(world, i2, 12, k2, super.brickWallBlock, super.brickWallMeta);
                        }
                        if ((i3 == 1 && k3 == 0) || (k3 == 1 && i3 == 0)) {
                            this.setBlockAndMetadata(world, i2, 9, k2, super.brickBlock, super.brickMeta);
                            this.setBlockAndMetadata(world, i2, 10, k2, super.brickBlock, super.brickMeta);
                        }
                        if (i3 == 0 && k3 == 0) {
                            this.setBlockAndMetadata(world, i2, 9, k2, super.brickBlock, super.brickMeta);
                            this.setBlockAndMetadata(world, i2, 10, k2, super.brickBlock, super.brickMeta);
                            this.setBlockAndMetadata(world, i2, 11, k2, LOTRMod.hearth, 0);
                            this.setBlockAndMetadata(world, i2, 12, k2, (Block)Blocks.fire, 0);
                            this.setBlockAndMetadata(world, i2, 13, k2, super.roofBlock, super.roofMeta);
                            this.setBlockAndMetadata(world, i2, 14, k2, super.roofSlabBlock, super.roofSlabMeta);
                        }
                    }
                }
            }
        }
        for (final int i4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i4, 3, -1, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i4, 3, 1, super.brickStairBlock, 6);
        }
        for (final int k4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, -1, 3, k4, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 3, k4, super.brickStairBlock, 5);
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            this.setBlockAndMetadata(world, i2, 5, -3, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i2, 5, 3, super.brickStairBlock, 7);
            if (IntMath.mod(i2, 2) == 1) {
                this.setBlockAndMetadata(world, i2, 6, -3, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, i2, 6, 3, super.brickWallBlock, super.brickWallMeta);
            }
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -3, 5, k5, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 5, k5, super.brickStairBlock, 4);
            if (IntMath.mod(k5, 2) == 1) {
                this.setBlockAndMetadata(world, -3, 6, k5, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, 3, 6, k5, super.brickWallBlock, super.brickWallMeta);
            }
        }
        for (final int i4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i4, 8, -1, super.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i4, 8, 1, super.brickStairBlock, 3);
        }
        for (final int k4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, -1, 8, k4, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 1, 8, k4, super.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -1, 8, 0, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 8, 0, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 8, -1, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 8, 1, super.brickStairBlock, 6);
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 10, -2, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i2, 10, 2, super.roofStairBlock, 3);
        }
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, -2, 10, k5, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 10, k5, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -1, 11, 0, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 11, 0, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 11, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 11, 1, super.brickStairBlock, 3);
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 13, -1, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i2, 13, 1, super.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, 13, 0, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 13, 0, super.roofStairBlock, 0);
        this.placeWallBanner(world, 0, 4, -2, LOTRItemBanner.BannerType.DALE, 2);
        this.placeWallBanner(world, -2, 4, 0, LOTRItemBanner.BannerType.DALE, 3);
        this.placeWallBanner(world, 0, 4, 2, LOTRItemBanner.BannerType.DALE, 0);
        this.placeWallBanner(world, 2, 4, 0, LOTRItemBanner.BannerType.DALE, 1);
        this.setBlockAndMetadata(world, -1, 5, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 5, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 5, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 5, 1, Blocks.torch, 4);
        return true;
    }
}
