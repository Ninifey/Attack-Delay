// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.entity.npc.LOTREntityGondorRuinsWraith;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorRuins extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenGondorRuins() {
        super(false);
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        if (world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        for (int slabRuinParts = 3 + random.nextInt(4), l = 0; l < slabRuinParts; ++l) {
            final int i2 = i - 5 + random.nextInt(10);
            final int k2 = k - 5 + random.nextInt(10);
            if (i2 != i || k2 != k) {
                final int j2 = world.getHeightValue(i2, k2);
                if (world.getBlock(i2, j2 - 1, k2).isOpaqueCube()) {
                    this.placeRandomSlab(world, random, i2, j2, k2);
                }
                this.setGrassToDirt(world, i2, j2 - 1, k2);
            }
        }
        for (int smallRuinParts = 3 + random.nextInt(4), m = 0; m < smallRuinParts; ++m) {
            final int i3 = i - 5 + random.nextInt(10);
            final int k3 = k - 5 + random.nextInt(10);
            if (i3 != i || k3 != k) {
                final int j3 = world.getHeightValue(i3, k3);
                if (world.getBlock(i3, j3 - 1, k3).isOpaqueCube()) {
                    for (int height = 1 + random.nextInt(3), j4 = 0; j4 < height; ++j4) {
                        this.placeRandomBrick(world, random, i3, j3 + j4, k3);
                    }
                }
                this.setGrassToDirt(world, i3, j3 - 1, k3);
            }
        }
        for (int largeRuinParts = 3 + random.nextInt(5), l2 = 0; l2 < largeRuinParts; ++l2) {
            final int i4 = i - 5 + random.nextInt(10);
            final int k4 = k - 5 + random.nextInt(10);
            if (i4 != i || k4 != k) {
                final int j5 = world.getHeightValue(i4, k4);
                if (world.getBlock(i4, j5 - 1, k4).isOpaqueCube()) {
                    for (int height2 = 4 + random.nextInt(7), j6 = 0; j6 < height2; ++j6) {
                        this.placeRandomBrick(world, random, i4, j5 + j6, k4);
                    }
                }
                this.setGrassToDirt(world, i4, j5 - 1, k4);
            }
        }
        for (int i3 = i - 1; i3 <= i + 1; ++i3) {
            for (int j2 = j - 2; j2 >= j - 5; --j2) {
                for (int k4 = k - 1; k4 <= k + 1; ++k4) {
                    if (!LOTRMod.isOpaque(world, i3, j2, k4)) {
                        return true;
                    }
                }
            }
        }
        for (int i3 = i - 1; i3 <= i + 8; ++i3) {
            for (int j2 = j - 6; j2 >= j - 11; --j2) {
                for (int k4 = k - 3; k4 <= k + 3; ++k4) {
                    if (!LOTRMod.isOpaque(world, i3, j2, k4)) {
                        return true;
                    }
                }
            }
        }
        for (int i3 = i - 1; i3 <= i + 8; ++i3) {
            for (int j2 = j - 6; j2 >= j - 11; --j2) {
                for (int k4 = k - 3; k4 <= k + 3; ++k4) {
                    if (j2 == j - 6 || j2 < j - 9) {
                        world.setBlock(i3, j2, k4, LOTRMod.rock, 1, 2);
                    }
                    else {
                        world.setBlock(i3, j2, k4, LOTRMod.brick, 1, 2);
                    }
                }
            }
        }
        for (int i3 = i; i3 <= i + 7; ++i3) {
            for (int j2 = j - 7; j2 >= j - 9; --j2) {
                for (int k4 = k - 2; k4 <= k + 2; ++k4) {
                    world.setBlock(i3, j2, k4, Blocks.air, 0, 2);
                }
            }
        }
        for (int k2 = k - 2; k2 <= k + 2; ++k2) {
            world.setBlock(i + 7, j - 9, k2, LOTRMod.brick, 1, 2);
            world.setBlock(i + 7, j - 7, k2, LOTRMod.slabSingle, 11, 2);
            world.setBlock(i, j - 7, k2, LOTRMod.slabSingle, 11, 2);
        }
        for (int i3 = i + 1; i3 <= i + 5; ++i3) {
            for (int k3 = k - 1; k3 <= k + 1; ++k3) {
                world.setBlock(i3, j - 10, k3, LOTRMod.slabDouble, 2, 2);
            }
        }
        world.setBlock(i + 2, j - 9, k, LOTRMod.slabSingle, 2, 2);
        world.setBlock(i + 3, j - 9, k, LOTRMod.slabSingle, 2, 2);
        world.setBlock(i + 4, j - 9, k, LOTRMod.slabSingle, 2, 2);
        this.placeSpawnerChest(world, i + 4, j - 10, k, LOTRMod.spawnerChestStone, 4, LOTREntityGondorRuinsWraith.class);
        LOTRChestContents.fillChest(world, random, i + 4, j - 10, k, LOTRChestContents.GONDOR_RUINS_TREASURE);
        world.setBlock(i + 2, j - 10, k, LOTRMod.chestStone, 4, 2);
        LOTRChestContents.fillChest(world, random, i + 2, j - 10, k, LOTRChestContents.GONDOR_RUINS_BONES);
        for (int j7 = j - 2; j7 >= j - 9; --j7) {
            world.setBlock(i, j7, k, Blocks.ladder, 5, 2);
        }
        world.setBlock(i, j - 1, k, LOTRMod.brick, 5, 2);
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 1);
        }
    }
    
    private void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 4 + random.nextInt(2));
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 3);
        }
    }
}
