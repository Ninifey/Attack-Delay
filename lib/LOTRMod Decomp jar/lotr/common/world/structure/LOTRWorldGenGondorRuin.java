// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorRuin extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenGondorRuin(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += 8;
                break;
            }
            case 1: {
                i -= 8;
                break;
            }
            case 2: {
                k -= 8;
                break;
            }
            case 3: {
                i += 8;
                break;
            }
        }
        j = world.getTopSolidOrLiquidBlock(i, k);
        if (super.restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        for (int i2 = i - 7; i2 <= i + 7; ++i2) {
            for (int k2 = k - 7; k2 <= k + 7; ++k2) {
                final int j2 = world.getTopSolidOrLiquidBlock(i2, k2);
                final Block block = world.getBlock(i2, j2 - 1, k2);
                if (block.isOpaqueCube()) {
                    if (random.nextInt(3) == 0) {
                        this.func_150516_a(world, i2, j2 - 1, k2, LOTRMod.rock, 1);
                    }
                    if (random.nextInt(3) == 0) {
                        if (random.nextInt(3) == 0) {
                            this.placeRandomSlab(world, random, i2, j2, k2);
                            this.setGrassToDirt(world, i2, j2 - 1, k2);
                        }
                        else {
                            this.placeRandomBrick(world, random, i2, j2, k2);
                            this.setGrassToDirt(world, i2, j2 - 1, k2);
                        }
                    }
                    if (LOTRMod.isOpaque(world, i2, j2, k2) && random.nextInt(4) == 0) {
                        if (random.nextInt(5) == 0) {
                            this.func_150516_a(world, i2, j2 + 1, k2, LOTRMod.wall, 3);
                            this.placeSkull(world, random, i2, j2 + 2, k2);
                        }
                        else if (random.nextInt(3) == 0) {
                            this.placeRandomSlab(world, random, i2, j2 + 1, k2);
                        }
                        else {
                            this.placeRandomBrick(world, random, i2, j2 + 1, k2);
                        }
                    }
                }
            }
        }
        for (int i2 = i - 7; i2 <= i + 7; i2 += 7) {
            for (int k2 = k - 7; k2 <= k + 7; k2 += 7) {
                final int j2 = world.getTopSolidOrLiquidBlock(i2, k2);
                this.setGrassToDirt(world, i2, j2 - 1, k2);
                int j3 = j2;
                while (true) {
                    this.placeRandomBrick(world, random, i2, j3, k2);
                    if (random.nextInt(4) == 0 || j3 > j2 + 4) {
                        break;
                    }
                    ++j3;
                }
                if (i2 == i && k2 == k) {
                    this.func_150516_a(world, i2, j3 + 1, k2, LOTRMod.beacon, 0);
                }
                else {
                    this.func_150516_a(world, i2, j3 + 1, k2, LOTRMod.brick, 5);
                }
            }
        }
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(20) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 5);
        }
        else if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 1);
        }
    }
    
    private void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(5) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 2);
        }
        else if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 4 + random.nextInt(2));
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 3);
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int metadata) {
        if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, random.nextBoolean() ? LOTRMod.stairsGondorBrickMossy : LOTRMod.stairsGondorBrickCracked, metadata);
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.stairsGondorBrick, metadata);
        }
    }
}
