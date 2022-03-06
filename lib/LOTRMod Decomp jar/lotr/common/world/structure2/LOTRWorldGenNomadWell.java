// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenNomadWell extends LOTRWorldGenNomadStructure
{
    public LOTRWorldGenNomadWell(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
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
                    if (maxHeight - minHeight > 2) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = -2; k3 <= 2; ++k3) {
                if (!this.isSurface(world, i3, 0, k3)) {
                    this.laySandBase(world, i3, 0, k3);
                }
                for (int j3 = 1; j3 <= 4; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        final int waterDepth = 3 + random.nextInt(3);
        for (int i4 = -2; i4 <= 2; ++i4) {
            for (int k4 = -2; k4 <= 2; ++k4) {
                final int i5 = Math.abs(i4);
                final int k5 = Math.abs(k4);
                if (i5 == 2 || k5 == 2) {
                    this.setBlockAndMetadata(world, i4, 0, k4, (Block)Blocks.grass, 0);
                    this.setBlockAndMetadata(world, i4, 1, k4, super.fenceBlock, super.fenceMeta);
                    if (i5 == 2 && k5 == 2) {
                        this.setBlockAndMetadata(world, i4, 2, k4, super.fenceBlock, super.fenceMeta);
                        this.setBlockAndMetadata(world, i4, 3, k4, Blocks.torch, 5);
                    }
                }
                for (int j4 = 0; j4 >= -waterDepth; --j4) {
                    if (!this.isOpaque(world, i4, j4, k4)) {
                        this.setBlockAndMetadata(world, i4, j4, k4, Blocks.sandstone, 0);
                        this.setGrassToDirt(world, i4, j4 - 1, k4);
                    }
                }
                if (i5 <= 1 && k5 <= 1) {
                    for (int minY = -waterDepth + 1 + random.nextInt(2), j5 = 0; j5 >= minY; --j5) {
                        this.setBlockAndMetadata(world, i4, j5, k4, Blocks.water, 0);
                    }
                }
            }
        }
        for (int grassRadius = 5, i2 = -grassRadius; i2 <= grassRadius; ++i2) {
            for (int k2 = -grassRadius; k2 <= grassRadius; ++k2) {
                final int i6 = Math.abs(i2);
                final int k6 = Math.abs(k2);
                if ((i6 >= 3 || k6 >= 3) && i6 * i6 + k6 * k6 < grassRadius * grassRadius && random.nextInt(3) != 0) {
                    final int j5 = this.getTopBlock(world, i2, k2) - 1;
                    if (this.isSurface(world, i2, j5, k2)) {
                        this.setBlockAndMetadata(world, i2, j5, k2, (Block)Blocks.grass, 0);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -2, 1, 0, super.fenceGateBlock, 3);
        this.setBlockAndMetadata(world, 2, 1, 0, super.fenceGateBlock, 1);
        this.setBlockAndMetadata(world, 0, 1, -2, super.fenceGateBlock, 2);
        this.setBlockAndMetadata(world, 0, 1, 2, super.fenceGateBlock, 0);
        return true;
    }
}
