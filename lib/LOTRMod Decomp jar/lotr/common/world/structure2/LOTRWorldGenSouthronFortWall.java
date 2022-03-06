// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public abstract class LOTRWorldGenSouthronFortWall extends LOTRWorldGenSouthronStructure
{
    protected boolean isLong;
    
    public LOTRWorldGenSouthronFortWall(final boolean flag) {
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
        int xMin = -1;
        int xMax = 1;
        if (this.isLong) {
            xMin = -2;
            xMax = 2;
        }
        for (int i2 = xMin; i2 <= xMax; ++i2) {
            final int i3 = Math.abs(i2);
            final int k2 = 0;
            this.findSurface(world, i2, k2);
            final boolean beam = i3 % 4 == 2;
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
            int k3 = k2 - 1;
            this.setBlockAndMetadata(world, i2, 2, k3, super.brickStairBlock, 2);
            for (int j3 = 1; (j3 >= 1 || !this.isOpaque(world, i2, j3, k3)) && this.getY(j3) >= 0; --j3) {
                this.setBlockAndMetadata(world, i2, j3, k3, super.brickBlock, super.brickMeta);
                this.setGrassToDirt(world, i2, j3 - 1, k3);
            }
            k3 = k2 + 1;
            this.setBlockAndMetadata(world, i2, 2, k3, super.brickStairBlock, 3);
            for (int j3 = 1; (j3 >= 1 || !this.isOpaque(world, i2, j3, k3)) && this.getY(j3) >= 0; --j3) {
                this.setBlockAndMetadata(world, i2, j3, k3, super.brickBlock, super.brickMeta);
                this.setGrassToDirt(world, i2, j3 - 1, k3);
            }
        }
        return true;
    }
    
    public static class Short extends LOTRWorldGenSouthronFortWall
    {
        public Short(final boolean flag) {
            super(flag);
            super.isLong = false;
        }
    }
    
    public static class Long extends LOTRWorldGenSouthronFortWall
    {
        public Long(final boolean flag) {
            super(flag);
            super.isLong = true;
        }
    }
}
