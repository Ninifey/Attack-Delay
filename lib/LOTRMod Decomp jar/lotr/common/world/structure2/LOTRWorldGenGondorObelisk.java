// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorObelisk extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorObelisk(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                for (int j2 = 3; (j2 >= 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.placeRandomBrick(world, random, i2, j2, k2);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                for (int j2 = 4; j2 <= 8; ++j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.rockBlock, super.rockMeta);
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            this.placeRandomStairs(world, random, i2, 4, -3, 2);
            this.placeRandomStairs(world, random, i2, 4, 3, 3);
        }
        for (int k3 = -2; k3 <= 2; ++k3) {
            this.placeRandomStairs(world, random, -3, 4, k3, 1);
            this.placeRandomStairs(world, random, 3, 4, k3, 0);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                for (int j2 = 9; j2 <= 14; ++j2) {
                    this.placeRandomBrick(world, random, i2, j2, k2);
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.placeRandomStairs(world, random, i2, 9, -2, 2);
            this.placeRandomStairs(world, random, i2, 9, 2, 3);
        }
        for (int k3 = -1; k3 <= 1; ++k3) {
            this.placeRandomStairs(world, random, -2, 9, k3, 1);
            this.placeRandomStairs(world, random, 2, 9, k3, 0);
        }
        for (int j3 = 15; j3 <= 18; ++j3) {
            this.placeRandomBrick(world, random, 0, j3, 0);
            this.placeRandomBrick(world, random, -1, j3, 0);
            this.placeRandomBrick(world, random, 1, j3, 0);
            this.placeRandomBrick(world, random, 0, j3, -1);
            this.placeRandomBrick(world, random, 0, j3, 1);
        }
        this.placeRandomStairs(world, random, -1, 19, 0, 1);
        this.placeRandomStairs(world, random, 1, 19, 0, 0);
        this.placeRandomStairs(world, random, 0, 19, -1, 2);
        this.placeRandomStairs(world, random, 0, 19, 1, 3);
        this.placeRandomBrick(world, random, 0, 19, 0);
        this.setBlockAndMetadata(world, 0, 20, 0, LOTRMod.beacon, 0);
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, super.brickMossyBlock, super.brickMossyMeta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, super.brickCrackedBlock, super.brickCrackedMeta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, super.brickBlock, super.brickMeta);
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (random.nextInt(4) == 0) {
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, super.brickMossyStairBlock, meta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, super.brickCrackedStairBlock, meta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, super.brickStairBlock, meta);
        }
    }
}
