// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public abstract class LOTRWorldGenGondorFortWall extends LOTRWorldGenGondorStructure
{
    protected boolean isRight;
    
    public LOTRWorldGenGondorFortWall(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        int xMin = -8;
        int xMax = 6;
        if (this.isRight) {
            xMin = -6;
            xMax = 8;
        }
        for (int i2 = xMin; i2 <= xMax; ++i2) {
            final int i3 = Math.abs(i2);
            final int k2 = 0;
            this.findSurface(world, i2, k2);
            final boolean pillar = i3 % 3 == 0;
            if (pillar) {
                for (int j2 = 4; (j2 >= 1 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.pillar2Block, super.pillar2Meta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
            else {
                for (int j2 = 3; (j2 >= 1 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                this.setBlockAndMetadata(world, i2, 4, k2, super.brickStairBlock, 6);
            }
            this.setBlockAndMetadata(world, i2, 5, k2, super.brick2WallBlock, super.brick2WallMeta);
            if (pillar) {
                this.setBlockAndMetadata(world, i2, 6, k2, Blocks.torch, 5);
            }
            this.setBlockAndMetadata(world, i2, 4, 1, super.rockSlabBlock, super.rockSlabMeta | 0x8);
        }
        return true;
    }
    
    public static class Left extends LOTRWorldGenGondorFortWall
    {
        public Left(final boolean flag) {
            super(flag);
            super.isRight = false;
        }
    }
    
    public static class Right extends LOTRWorldGenGondorFortWall
    {
        public Right(final boolean flag) {
            super(flag);
            super.isRight = true;
        }
    }
}
