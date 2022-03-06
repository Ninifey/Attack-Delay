// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public abstract class LOTRWorldGenSouthronTownWall extends LOTRWorldGenSouthronStructure
{
    protected boolean centrePillar;
    protected int leftExtent;
    protected int rightExtent;
    
    public LOTRWorldGenSouthronTownWall(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean canUseRedBricks() {
        return false;
    }
    
    @Override
    protected boolean forceCedarWood() {
        return true;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int i2 = -this.leftExtent; i2 <= this.rightExtent; ++i2) {
            final int i3 = Math.abs(i2);
            final int k2 = 0;
            this.findSurface(world, i2, k2);
            for (int k3 = k2; k3 <= k2 + 1; ++k3) {
                for (int j2 = 1; (j2 >= 1 || !this.isOpaque(world, i2, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k3, super.stoneBlock, super.stoneMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k3);
                }
                for (int j2 = 2; j2 <= 4; ++j2) {
                    this.setBlockAndMetadata(world, i2, j2, k3, super.brickBlock, super.brickMeta);
                }
            }
            this.setBlockAndMetadata(world, i2, 5, k2, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i2, 5, k2 + 1, super.stoneBlock, super.stoneMeta);
            this.setBlockAndMetadata(world, i2, 5, k2 + 2, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i2, 6, k2 + 2, super.fenceBlock, super.fenceMeta);
            final int pillarOffset = this.centrePillar ? IntMath.mod(i2, 4) : IntMath.mod(i2 + 2, 4);
            if (pillarOffset == 0) {
                final int k4 = k2 - 1;
                for (int j3 = 4; (j3 >= 1 || !this.isOpaque(world, i2, j3, k4)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k4, super.pillarBlock, super.pillarMeta);
                    this.setGrassToDirt(world, i2, j3 - 1, k4);
                }
                this.setBlockAndMetadata(world, i2, 5, k4, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i2, 6, k4, super.brickWallBlock, super.brickWallMeta);
            }
            else if (pillarOffset == 1) {
                this.setBlockAndMetadata(world, i2, 5, k2 - 1, super.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i2, 6, k2 - 1, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i2, 7, k2 - 1, super.brickSlabBlock, super.brickSlabMeta);
            }
            else if (pillarOffset == 2) {
                this.setBlockAndMetadata(world, i2, 5, k2 - 1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                this.setBlockAndMetadata(world, i2, 6, k2 - 1, super.brickWallBlock, super.brickWallMeta);
            }
            else if (pillarOffset == 3) {
                this.setBlockAndMetadata(world, i2, 5, k2 - 1, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i2, 6, k2 - 1, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i2, 7, k2 - 1, super.brickSlabBlock, super.brickSlabMeta);
            }
            if (pillarOffset % 2 == 0) {
                this.setBlockAndMetadata(world, i2, 4, k2 + 2, super.plankStairBlock, 7);
            }
            else {
                for (int k4 = k2 + 2, j3 = 4; (j3 >= 1 || !this.isOpaque(world, i2, j3, k4)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k4, super.woodBeamBlock, super.woodBeamMeta);
                    this.setGrassToDirt(world, i2, j3 - 1, k4);
                }
            }
        }
        return true;
    }
    
    public static class Short extends LOTRWorldGenSouthronTownWall
    {
        public Short(final boolean flag) {
            super(flag);
            super.centrePillar = true;
            super.leftExtent = 1;
            super.rightExtent = 1;
        }
    }
    
    public static class Long extends LOTRWorldGenSouthronTownWall
    {
        public Long(final boolean flag) {
            super(flag);
            super.centrePillar = true;
            super.leftExtent = 2;
            super.rightExtent = 2;
        }
    }
    
    public static class SideMid extends LOTRWorldGenSouthronTownWall
    {
        public SideMid(final boolean flag) {
            super(flag);
            super.centrePillar = false;
            super.leftExtent = 4;
            super.rightExtent = 4;
        }
    }
    
    public static class Extra extends LOTRWorldGenSouthronTownWall
    {
        public Extra(final boolean flag) {
            super(flag);
            super.centrePillar = true;
            super.leftExtent = 1;
            super.rightExtent = 2;
        }
    }
}
