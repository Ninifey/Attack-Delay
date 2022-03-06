// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingLamp extends LOTRWorldGenEasterlingStructure
{
    public LOTRWorldGenEasterlingLamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -1; i2 <= 1; ++i2) {
                for (int k2 = -1; k2 <= 1; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 1; j3 <= 6; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    if (i3 == 1 && k3 == 1) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.pillarBlock, super.pillarMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                    }
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                if (i3 == 0 && k3 == 0) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i2, 2, k2, super.brickBlock, super.brickMeta);
                }
                if (i3 + k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.brickWallBlock, super.brickWallMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 3, 0, Blocks.glowstone, 0);
        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.trapdoor, 4);
        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.trapdoor, 5);
        this.setBlockAndMetadata(world, -1, 3, 0, Blocks.trapdoor, 7);
        this.setBlockAndMetadata(world, 1, 3, 0, Blocks.trapdoor, 6);
        return true;
    }
}
