// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTownWall extends LOTRWorldGenGondorStructure
{
    private int xMin;
    private int xMax;
    private int xMinInner;
    private int xMaxInner;
    
    private LOTRWorldGenGondorTownWall(final boolean flag, final int x0, final int x1) {
        this(flag, x0, x1, x0, x1);
    }
    
    private LOTRWorldGenGondorTownWall(final boolean flag, final int x0, final int x1, final int xi0, final int xi1) {
        super(flag);
        this.xMin = x0;
        this.xMax = x1;
        this.xMinInner = xi0;
        this.xMaxInner = xi1;
    }
    
    public static LOTRWorldGenGondorTownWall Centre(final boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -5, 5);
    }
    
    public static LOTRWorldGenGondorTownWall Left(final boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -9, 6);
    }
    
    public static LOTRWorldGenGondorTownWall Right(final boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -6, 9);
    }
    
    public static LOTRWorldGenGondorTownWall LeftEnd(final boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -6, 6, -5, 6);
    }
    
    public static LOTRWorldGenGondorTownWall RightEnd(final boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -6, 6, -6, 5);
    }
    
    public static LOTRWorldGenGondorTownWall LeftEndShort(final boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -5, 6);
    }
    
    public static LOTRWorldGenGondorTownWall RightEndShort(final boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -6, 5);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int i2 = this.xMin; i2 <= this.xMax; ++i2) {
            final int i3 = Math.abs(i2);
            int k2 = 0;
            this.findSurface(world, i2, k2);
            for (int j2 = 1; (j2 >= 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                this.setBlockAndMetadata(world, i2, j2, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                this.setGrassToDirt(world, i2, j2 - 1, k2);
            }
            for (int j2 = 2; j2 <= 3; ++j2) {
                this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
            }
            this.setBlockAndMetadata(world, i2, 4, k2, super.brick2Block, super.brick2Meta);
            final int i4 = IntMath.mod(i2, 4);
            if (i4 == 2) {
                this.setBlockAndMetadata(world, i2, 5, k2, super.rockWallBlock, super.rockWallMeta);
            }
            else {
                this.setBlockAndMetadata(world, i2, 5, k2, super.brickBlock, super.brickMeta);
                if (i4 == 3) {
                    this.setBlockAndMetadata(world, i2, 6, k2, super.brickStairBlock, 1);
                }
                else if (i4 == 0) {
                    this.setBlockAndMetadata(world, i2, 6, k2, super.brickBlock, super.brickMeta);
                }
                else if (i4 == 1) {
                    this.setBlockAndMetadata(world, i2, 6, k2, super.brickStairBlock, 0);
                }
            }
            if (i2 >= this.xMinInner && i2 <= this.xMaxInner) {
                for (k2 = 1; k2 <= 1; ++k2) {
                    for (int j3 = 4; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                }
            }
        }
        return true;
    }
}
