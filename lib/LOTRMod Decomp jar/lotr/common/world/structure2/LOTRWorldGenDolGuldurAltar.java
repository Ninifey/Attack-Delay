// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDolGuldurAltar extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenDolGuldurAltar(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        if (super.restrictions) {
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.stone && block != Blocks.dirt && block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            for (int k2 = -5; k2 <= 5; ++k2) {
                for (int j2 = 0; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                    this.placeRandomBrick(world, random, i2, j2, k2);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                for (int j2 = 1; j2 <= 8; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 <= 4 && k3 <= 4) {
                    this.placeRandomBrick(world, random, i2, 1, k2);
                }
                if (i3 <= 3 && k3 <= 3) {
                    if (random.nextInt(10) == 0) {
                        this.setBlockAndMetadata(world, i2, 2, k2, LOTRMod.guldurilBrick, 4);
                    }
                    else {
                        this.placeRandomBrick(world, random, i2, 2, k2);
                    }
                }
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            this.placeRandomStairs(world, random, i2, 1, -5, 2);
            this.placeRandomStairs(world, random, i2, 1, 5, 3);
        }
        for (int k4 = -5; k4 <= 5; ++k4) {
            this.placeRandomStairs(world, random, -5, 1, k4, 1);
            this.placeRandomStairs(world, random, 5, 1, k4, 0);
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            this.placeRandomStairs(world, random, i2, 2, -4, 2);
            this.placeRandomStairs(world, random, i2, 2, 4, 3);
        }
        for (int k4 = -4; k4 <= 4; ++k4) {
            this.placeRandomStairs(world, random, -4, 2, k4, 1);
            this.placeRandomStairs(world, random, 4, 2, k4, 0);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.placeRandomStairs(world, random, i2, 3, -1, 2);
            this.placeRandomStairs(world, random, i2, 3, 1, 3);
        }
        for (int k4 = -1; k4 <= 1; ++k4) {
            this.placeRandomStairs(world, random, -1, 3, k4, 1);
            this.placeRandomStairs(world, random, 1, 3, k4, 0);
        }
        this.placeRandomBrick(world, random, 0, 3, 0);
        this.setBlockAndMetadata(world, 0, 4, 0, LOTRMod.dolGuldurTable, 0);
        for (int x = -4; x <= 3; x += 7) {
            for (int z = -4; z <= 3; z += 7) {
                for (int i4 = x; i4 <= x + 1; ++i4) {
                    for (int k5 = z; k5 <= z + 1; ++k5) {
                        for (int j3 = 2; j3 <= 5; ++j3) {
                            this.placeRandomBrick(world, random, i4, j3, k5);
                        }
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -4, 6, -4, LOTRMod.wall2, 8);
        this.setBlockAndMetadata(world, -4, 7, -4, LOTRMod.morgulTorch, 5);
        this.setBlockAndMetadata(world, 4, 6, -4, LOTRMod.wall2, 8);
        this.setBlockAndMetadata(world, 4, 7, -4, LOTRMod.morgulTorch, 5);
        this.setBlockAndMetadata(world, -4, 6, 4, LOTRMod.wall2, 8);
        this.setBlockAndMetadata(world, -4, 7, 4, LOTRMod.morgulTorch, 5);
        this.setBlockAndMetadata(world, 4, 6, 4, LOTRMod.wall2, 8);
        this.setBlockAndMetadata(world, 4, 7, 4, LOTRMod.morgulTorch, 5);
        for (int i2 = -4; i2 <= 4; i2 += 8) {
            this.placeRandomStairs(world, random, i2, 6, -3, 2);
            this.placeRandomStairs(world, random, i2, 6, -2, 7);
            this.setBlockAndMetadata(world, i2, 7, -2, LOTRMod.guldurilBrick, 4);
            this.placeRandomStairs(world, random, i2, 8, -2, 2);
            this.placeRandomStairs(world, random, i2, 8, -1, 7);
            this.placeRandomStairs(world, random, i2, 6, 3, 3);
            this.placeRandomStairs(world, random, i2, 6, 2, 6);
            this.setBlockAndMetadata(world, i2, 7, 2, LOTRMod.guldurilBrick, 4);
            this.placeRandomStairs(world, random, i2, 8, 2, 3);
            this.placeRandomStairs(world, random, i2, 8, 1, 6);
        }
        for (int k4 = -4; k4 <= 4; k4 += 8) {
            this.placeRandomStairs(world, random, -3, 6, k4, 1);
            this.placeRandomStairs(world, random, -2, 6, k4, 4);
            this.setBlockAndMetadata(world, -2, 7, k4, LOTRMod.guldurilBrick, 4);
            this.placeRandomStairs(world, random, -2, 8, k4, 1);
            this.placeRandomStairs(world, random, -1, 8, k4, 4);
            this.placeRandomStairs(world, random, 3, 6, k4, 0);
            this.placeRandomStairs(world, random, 2, 6, k4, 5);
            this.setBlockAndMetadata(world, 2, 7, k4, LOTRMod.guldurilBrick, 4);
            this.placeRandomStairs(world, random, 2, 8, k4, 0);
            this.placeRandomStairs(world, random, 1, 8, k4, 5);
        }
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 9);
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 8);
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDolGuldurBrickCracked, meta);
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDolGuldurBrick, meta);
        }
    }
}
