// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronFortCorner extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronFortCorner(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean canUseRedBricks() {
        return false;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int i2 = -4; i2 <= 1; ++i2) {
            final int i3 = Math.abs(i2);
            final int k2 = 0;
            this.findSurface(world, i2, k2);
            if (i2 <= 0) {
                final boolean beam = i3 % 4 == 0;
                if (beam) {
                    for (int j2 = 6; (j2 >= 1 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                }
                else {
                    for (int j2 = 5; (j2 >= 1 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, super.plankBlock, super.plankMeta);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                    if (i3 % 2 == 1) {
                        this.setBlockAndMetadata(world, i2, 5, k2, super.plankStairBlock, 2);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 6, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (i2 <= -1) {
                    final int k3 = k2 + 1;
                    this.setBlockAndMetadata(world, i2, 2, k3, super.brickStairBlock, 3);
                    for (int j3 = 1; (j3 >= 1 || !this.isOpaque(world, i2, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k3);
                    }
                }
            }
            if (i2 <= 1) {
                final int k4 = k2 - 1;
                this.setBlockAndMetadata(world, i2, 2, k4, super.brickStairBlock, 2);
                for (int j2 = 1; (j2 >= 1 || !this.isOpaque(world, i2, j2, k4)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k4, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k4);
                }
            }
        }
        for (int k5 = 0; k5 <= 4; ++k5) {
            final int i4 = 0;
            final int k6 = Math.abs(k5);
            this.findSurface(world, i4, k5);
            if (k5 >= 1) {
                final boolean beam = k6 % 4 == 0;
                if (beam) {
                    for (int j2 = 6; (j2 >= 1 || !this.isOpaque(world, i4, j2, k5)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i4, j2, k5, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i4, j2 - 1, k5);
                    }
                }
                else {
                    for (int j2 = 5; (j2 >= 1 || !this.isOpaque(world, i4, j2, k5)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i4, j2, k5, super.plankBlock, super.plankMeta);
                        this.setGrassToDirt(world, i4, j2 - 1, k5);
                    }
                    if (k6 % 2 == 1) {
                        this.setBlockAndMetadata(world, i4, 5, k5, super.plankStairBlock, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i4, 6, k5, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (k5 >= 2) {
                    final int i5 = i4 - 1;
                    this.setBlockAndMetadata(world, i5, 2, k5, super.brickStairBlock, 1);
                    for (int j3 = 1; (j3 >= 1 || !this.isOpaque(world, i5, j3, k5)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i5, j3, k5, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i5, j3 - 1, k5);
                    }
                }
            }
            if (k5 >= 0) {
                final int i6 = i4 + 1;
                this.setBlockAndMetadata(world, i6, 2, k5, super.brickStairBlock, 0);
                for (int j2 = 1; (j2 >= 1 || !this.isOpaque(world, i6, j2, k5)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i6, j2, k5, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i6, j2 - 1, k5);
                }
            }
        }
        return true;
    }
}
