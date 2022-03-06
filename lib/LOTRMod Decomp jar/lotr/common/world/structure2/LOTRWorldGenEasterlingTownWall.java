// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTownWall extends LOTRWorldGenEasterlingStructure
{
    private int xMin;
    private int xMax;
    private boolean isCentre;
    
    private LOTRWorldGenEasterlingTownWall(final boolean flag, final int x0, final int x1, final boolean c) {
        super(flag);
        this.xMin = x0;
        this.xMax = x1;
        this.isCentre = c;
    }
    
    public static LOTRWorldGenEasterlingTownWall Centre(final boolean flag) {
        return new LOTRWorldGenEasterlingTownWall(flag, -7, 7, true);
    }
    
    public static LOTRWorldGenEasterlingTownWall Left(final boolean flag) {
        return new LOTRWorldGenEasterlingTownWall(flag, -4, 3, false);
    }
    
    public static LOTRWorldGenEasterlingTownWall Right(final boolean flag) {
        return new LOTRWorldGenEasterlingTownWall(flag, -3, 4, false);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int i2 = this.xMin; i2 <= this.xMax; ++i2) {
            final int i3 = Math.abs(i2);
            this.findSurface(world, i2, 0);
            for (int k2 = -1; k2 <= 1; ++k2) {
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                this.setBlockAndMetadata(world, i2, 1, k2, super.brickRedBlock, super.brickRedMeta);
                for (int j2 = 2; j2 <= 5; ++j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                }
                if (k2 == 0) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.brickRedBlock, super.brickRedMeta);
                }
            }
            if (IntMath.mod(i2, 2) == (this.isCentre ? 1 : 0)) {
                this.setBlockAndMetadata(world, i2, 5, -2, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i2, 6, -2, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i2, 7, -2, super.brickStairBlock, 3);
            }
            else {
                this.setBlockAndMetadata(world, i2, 6, -2, super.brickWallBlock, super.brickWallMeta);
            }
        }
        return true;
    }
}
