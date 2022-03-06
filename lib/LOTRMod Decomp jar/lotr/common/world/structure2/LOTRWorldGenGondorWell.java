// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorWell extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorWell(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
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
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, super.rockBlock, super.rockMeta);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 6; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 == 2 && k4 == 2) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.rockBlock, super.rockMeta);
                    this.setBlockAndMetadata(world, i3, 2, k3, super.rockBlock, super.rockMeta);
                    this.setBlockAndMetadata(world, i3, 3, k3, super.rockWallBlock, super.rockWallMeta);
                    this.setBlockAndMetadata(world, i3, 4, k3, super.rockBlock, super.rockMeta);
                    this.setBlockAndMetadata(world, i3, 5, k3, super.rockSlabBlock, super.rockSlabMeta);
                }
                if (i4 <= 2 && k4 <= 2) {
                    final int d = i4 + k4;
                    if (d == 3) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.rockSlabBlock, super.rockSlabMeta | 0x8);
                        this.setBlockAndMetadata(world, i3, 5, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    }
                    if (d == 2) {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                        this.setBlockAndMetadata(world, i3, 6, k3, super.rockSlabBlock, super.rockSlabMeta);
                    }
                    if (d == 1) {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.rockSlabBlock, super.rockSlabMeta | 0x8);
                        this.setBlockAndMetadata(world, i3, 6, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    }
                    if (d == 0) {
                        this.setBlockAndMetadata(world, i3, 6, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                        this.setBlockAndMetadata(world, i3, 7, k3, super.rockSlabBlock, super.rockSlabMeta);
                    }
                }
                if ((i4 == 2 && k4 <= 1) || (k4 == 2 && i4 <= 1)) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i3, 0, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                }
            }
        }
        final int waterDepth = 1 + random.nextInt(4);
        for (int depth = waterDepth + 1 + random.nextInt(3), j3 = 0; j3 < depth; ++j3) {
            final int j4 = 0 - j3;
            final boolean watery = j3 >= depth - waterDepth;
            for (int i5 = -1; i5 <= 1; ++i5) {
                for (int k5 = -1; k5 <= 1; ++k5) {
                    if (watery) {
                        this.setBlockAndMetadata(world, i5, j4, k5, Blocks.water, 0);
                    }
                    else {
                        this.setAir(world, i5, j4, k5);
                    }
                }
            }
            if (!watery) {
                this.setBlockAndMetadata(world, 0, j4, -1, Blocks.ladder, 3);
                this.setBlockAndMetadata(world, 0, j4, 1, Blocks.ladder, 2);
                this.setBlockAndMetadata(world, -1, j4, 0, Blocks.ladder, 4);
                this.setBlockAndMetadata(world, 1, j4, 0, Blocks.ladder, 5);
            }
        }
        this.setBlockAndMetadata(world, 0, 1, -2, super.fenceGateBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, 2, super.fenceGateBlock, 2);
        this.setBlockAndMetadata(world, -2, 1, 0, super.fenceGateBlock, 1);
        this.setBlockAndMetadata(world, 2, 1, 0, super.fenceGateBlock, 3);
        for (int j3 = 4; j3 <= 5; ++j3) {
            this.setBlockAndMetadata(world, 0, j3, 0, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -3, 5, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 5, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 5, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, 3, Blocks.torch, 3);
        return true;
    }
}
