// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRWorldGenEasterlingGarden extends LOTRWorldGenEasterlingStructure
{
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenEasterlingGarden(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        this.leafBlock = LOTRMod.leaves6;
        this.leafMeta = 2;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 10);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -10; i2 <= 10; ++i2) {
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
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -10; i3 <= 10; ++i3) {
            for (int k3 = -10; k3 <= 10; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    if (j2 == 0) {
                        this.setBlockAndMetadata(world, i3, j2, k3, (Block)Blocks.grass, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 0);
                    }
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 9; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 <= 9 && k4 <= 9 && ((i4 == 9 && k4 >= 2 && k4 <= 8) || (k4 == 9 && i4 >= 2 && i4 <= 8))) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.brickBlock, super.brickMeta);
                    if ((i4 == 9 && k4 == 2) || (k4 == 9 && i4 == 2)) {
                        for (int j2 = 1; j2 <= 6; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                        }
                    }
                    else {
                        for (int j2 = 1; j2 <= 2; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        }
                        if ((i4 == 9 && k4 % 2 == 0) || (k4 == 9 && i4 % 2 == 0)) {
                            for (int j2 = 3; j2 <= 6; ++j2) {
                                this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                            }
                        }
                    }
                }
                if (i4 >= 2 && i4 <= 8 && k4 >= 2 && k4 <= 8) {
                    if ((i4 == 2 && k4 >= 5) || (k4 == 2 && i4 >= 5)) {
                        int hedgeHeight = 0;
                        if (i4 == 2) {
                            hedgeHeight = k4 - 4;
                        }
                        else if (k4 == 2) {
                            hedgeHeight = i4 - 4;
                        }
                        for (int j3 = 1; j3 <= hedgeHeight; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, this.leafBlock, this.leafMeta | 0x4);
                        }
                    }
                    else if (i4 == 3 && k4 == 3) {
                        for (int j2 = 1; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.fenceBlock, super.fenceMeta);
                        }
                    }
                    else {
                        final int sum = i4 + k4;
                        if (sum >= 4 && sum <= 7) {
                            if (random.nextBoolean()) {
                                this.plantFlower(world, random, i3, 1, k3);
                            }
                        }
                        else if (sum >= 8 && sum <= 9) {
                            this.setBlockAndMetadata(world, i3, 1, k3, (Block)Blocks.grass, 0);
                            this.setGrassToDirt(world, i3, 0, k3);
                            if (random.nextBoolean()) {
                                this.plantFlower(world, random, i3, 2, k3);
                            }
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, 2, k3, (Block)Blocks.grass, 0);
                            this.setBlockAndMetadata(world, i3, 1, k3, Blocks.dirt, 0);
                            this.setGrassToDirt(world, i3, 0, k3);
                            if (sum >= 12 && i4 <= 7 && k4 <= 7) {
                                this.setBlockAndMetadata(world, i3, 2, k3, Blocks.water, 0);
                            }
                            else if (random.nextBoolean()) {
                                this.plantFlower(world, random, i3, 3, k3);
                            }
                        }
                    }
                }
                if (k4 == 10 && i4 <= 9) {
                    this.setBlockAndMetadata(world, i3, 7, k3, super.brickStairBlock, (k3 > 0) ? 7 : 6);
                    this.setBlockAndMetadata(world, i3, 8, k3, super.brickBlock, super.brickMeta);
                }
                if (i4 == 10 && k4 <= 9) {
                    this.setBlockAndMetadata(world, i3, 7, k3, super.brickStairBlock, (i3 > 0) ? 4 : 5);
                    this.setBlockAndMetadata(world, i3, 8, k3, super.brickBlock, super.brickMeta);
                }
                if (k4 == 8 && i4 <= 7) {
                    this.setBlockAndMetadata(world, i3, 7, k3, super.brickStairBlock, (k3 > 0) ? 6 : 7);
                    this.setBlockAndMetadata(world, i3, 8, k3, super.brickWallBlock, super.brickWallMeta);
                }
                if (i4 == 8 && k4 <= 7) {
                    this.setBlockAndMetadata(world, i3, 7, k3, super.brickStairBlock, (i3 > 0) ? 5 : 4);
                    this.setBlockAndMetadata(world, i3, 8, k3, super.brickWallBlock, super.brickWallMeta);
                }
                if (i4 == 9 && k4 == 9) {
                    this.setBlockAndMetadata(world, i3, 7, k3, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i3, 8, k3, super.brickBlock, super.brickMeta);
                }
                if (i4 == 8 && k4 == 8) {
                    this.setBlockAndMetadata(world, i3, 7, k3, super.brickStairBlock, (k3 > 0) ? 6 : 7);
                }
                if ((k4 == 9 && i4 <= 8) || (i4 == 9 && k4 <= 8)) {
                    this.setBlockAndMetadata(world, i3, 7, k3, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i3, 8, k3, Blocks.water, 0);
                }
                if (i4 <= 1 && k4 <= 1) {
                    if (i4 == 0 && k4 == 0) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.brickCarvedBlock, super.brickCarvedMeta);
                    }
                    else if (i4 + k4 == 1) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.brickFloweryBlock, super.brickFloweryMeta);
                    }
                    else if (i4 + k4 == 2) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.brickRedBlock, super.brickRedMeta);
                    }
                }
                if (i4 <= 1 && k4 >= 2 && k4 <= 9) {
                    if (i4 == 0) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.brickRedBlock, super.brickRedMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.brickBlock, super.brickMeta);
                    }
                }
                if (k4 <= 1 && i4 >= 2 && i4 <= 9) {
                    if (k4 == 0) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.brickRedBlock, super.brickRedMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.brickBlock, super.brickMeta);
                    }
                }
                if ((k4 == 8 && i4 >= 7 && i4 <= 8) || (i4 == 8 && k4 >= 7 && k4 <= 8)) {
                    this.setBlockAndMetadata(world, i3, 8, k3, Blocks.water, 0);
                }
            }
        }
        final int domeRadius = 4;
        final int domeRadiusSq = domeRadius * domeRadius;
        final int domeX = 0;
        final int domeY = 4;
        final int domeZ = 0;
        for (int i5 = -3; i5 <= 3; ++i5) {
            for (int k5 = -3; k5 <= 3; ++k5) {
                for (int j4 = 4; j4 <= 8; ++j4) {
                    final int dx = i5 - domeX;
                    final int dy = j4 - domeY;
                    final int dz = k5 - domeZ;
                    final float dSq = (float)(dx * dx + dy * dy + dz * dz);
                    if (Math.abs(dSq - domeRadiusSq) <= 3.0) {
                        this.setBlockAndMetadata(world, i5, j4, k5, this.leafBlock, this.leafMeta | 0x4);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -9, 7, -8, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -8, 7, -8, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 8, 7, -8, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 9, 7, -8, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -9, 7, 8, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -8, 7, 8, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 8, 7, 8, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 9, 7, 8, super.brickStairBlock, 7);
        for (final int k6 : new int[] { -9, 9 }) {
            this.setBlockAndMetadata(world, -1, 5, k6, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 5, k6, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -1, 6, k6, super.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 0, 6, k6, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, 1, 6, k6, super.brickStairBlock, 1);
        }
        for (final int i6 : new int[] { -9, 9 }) {
            this.setBlockAndMetadata(world, i6, 5, -1, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 5, 1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 6, -1, super.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i6, 6, 0, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, i6, 6, 1, super.brickStairBlock, 2);
        }
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6, 6, -8, Blocks.torch, 3);
            this.setBlockAndMetadata(world, i6, 6, 8, Blocks.torch, 4);
        }
        for (final int k6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, -8, 6, k6, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 8, 6, k6, Blocks.torch, 1);
        }
        for (int i5 = -8; i5 <= 8; ++i5) {
            final int i7 = Math.abs(i5);
            if (i7 == 0) {
                this.setBlockAndMetadata(world, i5, 8, -10, super.brickRedCarvedBlock, super.brickRedCarvedMeta);
                this.setBlockAndMetadata(world, i5, 9, -10, super.brickRedStairBlock, 3);
                this.setBlockAndMetadata(world, i5, 8, 10, super.brickRedCarvedBlock, super.brickRedCarvedMeta);
                this.setBlockAndMetadata(world, i5, 9, 10, super.brickRedStairBlock, 2);
            }
            if (i7 == 3 || i7 == 7) {
                for (final int k7 : new int[] { -10, 10 }) {
                    this.setBlockAndMetadata(world, i5 - 1, 9, k7, super.brickStairBlock, 1);
                    this.setBlockAndMetadata(world, i5, 9, k7, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i5 + 1, 9, k7, super.brickStairBlock, 0);
                }
            }
        }
        for (int k8 = -8; k8 <= 8; ++k8) {
            final int k9 = Math.abs(k8);
            if (k9 == 0) {
                this.setBlockAndMetadata(world, -10, 8, k8, super.brickRedCarvedBlock, super.brickRedCarvedMeta);
                this.setBlockAndMetadata(world, -10, 9, k8, super.brickRedStairBlock, 0);
                this.setBlockAndMetadata(world, 10, 8, k8, super.brickRedCarvedBlock, super.brickRedCarvedMeta);
                this.setBlockAndMetadata(world, 10, 9, k8, super.brickRedStairBlock, 1);
            }
            if (k9 == 3 || k9 == 7) {
                for (final int i8 : new int[] { -10, 10 }) {
                    this.setBlockAndMetadata(world, i8, 9, k8 - 1, super.brickStairBlock, 2);
                    this.setBlockAndMetadata(world, i8, 9, k8, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i8, 9, k8 + 1, super.brickStairBlock, 3);
                }
            }
        }
        return true;
    }
}
