// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorFortGate extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorFortGate(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        this.findSurface(world, 0, 0);
        final int gateX = super.originX;
        final int gateY = super.originY;
        final int gateZ = super.originZ;
        for (int i2 = -4; i2 <= 4; ++i2) {
            final int i3 = Math.abs(i2);
            final int k2 = 0;
            if (i3 <= 1) {
                super.originX = gateX;
                super.originY = gateY;
                super.originZ = gateZ;
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                for (int j2 = 1; j2 <= 4; ++j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.gateBlock, 2);
                }
                if (i2 == -1) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.brickStairBlock, 4);
                }
                if (i2 == 0) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.brickStairBlock, 6);
                }
                if (i2 == 1) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.brickStairBlock, 5);
                }
                this.setBlockAndMetadata(world, i2, 6, k2, super.brick2Block, super.brick2Meta);
                if (i3 == 0) {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.brick2SlabBlock, super.brick2SlabMeta);
                }
                this.setBlockAndMetadata(world, i2, 5, 1, super.rockSlabBlock, super.rockSlabMeta);
            }
            if (i3 == 2) {
                this.findSurface(world, i2, k2);
                for (int j2 = 5; (j2 >= 1 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.pillarBlock, super.pillarMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                this.setBlockAndMetadata(world, i2, 6, k2, super.brick2SlabBlock, super.brick2SlabMeta);
                this.setBlockAndMetadata(world, i2, 4, 1, super.rockSlabBlock, super.rockSlabMeta | 0x8);
            }
            if (i3 >= 3) {
                this.findSurface(world, i2, k2);
                for (int j2 = 3; (j2 >= 1 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                this.setBlockAndMetadata(world, i2, 4, k2, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i2, 5, k2, super.brick2WallBlock, super.brick2WallMeta);
                this.setBlockAndMetadata(world, i2, 4, 1, super.rockSlabBlock, super.rockSlabMeta | 0x8);
                if (i3 == 4) {
                    for (int j2 = 3; (j2 >= 1 || !this.isOpaque(world, i2, j2, 1)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i2, j2, 1, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i2, j2 - 1, 1);
                    }
                    this.setBlockAndMetadata(world, i2, 4, 1, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    for (int j2 = 4; !this.isOpaque(world, i2, j2, 2) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i2, j2, 2, Blocks.ladder, 3);
                    }
                }
            }
        }
        return true;
    }
}
