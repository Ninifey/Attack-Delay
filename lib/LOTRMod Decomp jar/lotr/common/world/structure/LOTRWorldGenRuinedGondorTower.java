// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedGondorTower extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenRuinedGondorTower(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += 4;
                break;
            }
            case 1: {
                i -= 4;
                break;
            }
            case 2: {
                k -= 4;
                break;
            }
            case 3: {
                i += 4;
                break;
            }
        }
        if (super.restrictions) {
            for (int i2 = i - 3; i2 <= i + 3; ++i2) {
                for (int k2 = k + 3; k2 <= k + 3; ++k2) {
                    final int j2 = world.getHeightValue(i2, k2);
                    final Block block = world.getBlock(i2, j2 - 1, k2);
                    if (block != Blocks.grass && !block.isWood((IBlockAccess)world, i2, j2 - 1, k2) && !block.isLeaves((IBlockAccess)world, i2, j2 - 1, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = i - 3; i2 <= i + 3; ++i2) {
            for (int k2 = k - 3; k2 <= k + 3; ++k2) {
                if (Math.abs(i2 - i) == 3 || Math.abs(k2 - k) == 3) {
                    for (int j2 = j + 8; (j2 >= j || !LOTRMod.isOpaque(world, i2, j2, k2)) && j2 >= 0; --j2) {
                        this.placeRandomBrick(world, random, i2, j2, k2);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                }
                else {
                    for (int j2 = j; !LOTRMod.isOpaque(world, i2, j2, k2) && j2 >= 0; --j2) {
                        this.placeRandomBrick(world, random, i2, j2, k2);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                    for (int j2 = j + 1; j2 <= j + 8; ++j2) {
                        this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                    }
                }
                if (Math.abs(i2 - i) < 3 && Math.abs(k2 - k) < 3 && random.nextInt(20) != 0) {
                    this.func_150516_a(world, i2, j + 5, k2, Blocks.planks, 0);
                }
            }
        }
        this.func_150516_a(world, i - 2, j + 1, k - 2, LOTRMod.chestLebethron, 0);
        if (random.nextInt(3) == 0) {
            LOTRChestContents.fillChest(world, random, i - 2, j + 1, k - 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        }
        this.func_150516_a(world, i + 2, j + 1, k - 2, LOTRMod.gondorianTable, 0);
        if (random.nextInt(3) != 0) {
            this.func_150516_a(world, i + 2, j + 6, k - 2, LOTRMod.strawBed, 10);
            this.func_150516_a(world, i + 2, j + 6, k - 1, LOTRMod.strawBed, 2);
        }
        if (random.nextBoolean()) {
            this.func_150516_a(world, i + 2, j + 6, k + 2, Blocks.anvil, 8);
        }
        for (int k3 = k; k3 <= k + 2; ++k3) {
            this.func_150516_a(world, i - 2, j + 6, k3, LOTRMod.slabSingle, 10);
        }
        if (random.nextBoolean()) {
            this.func_150516_a(world, i - 2, j + 7, k, LOTRMod.mugBlock, 1);
        }
        if (random.nextBoolean()) {
            this.func_150516_a(world, i - 2, j + 7, k + 1, LOTRMod.plateBlock, 0);
        }
        if (random.nextBoolean()) {
            this.func_150516_a(world, i - 2, j + 7, k + 2, LOTRMod.barrel, 5);
        }
        for (int i2 = i - 4; i2 <= i + 4; ++i2) {
            for (int k2 = k - 4; k2 <= k + 4; ++k2) {
                this.placeRandomBrick(world, random, i2, j + 9, k2);
                if (((Math.abs(i2 - i) == 4 && Math.abs(k2 - k) % 2 == 0) || (Math.abs(k2 - k) == 4 && Math.abs(i2 - i) % 2 == 0)) && LOTRMod.isOpaque(world, i2, j + 9, k2)) {
                    this.placeRandomBrick(world, random, i2, j + 10, k2);
                }
            }
        }
        for (int j3 = j + 1; j3 <= j + 9; ++j3) {
            if (rotation == 2) {
                if (LOTRMod.isOpaque(world, i + 3, j3, k)) {
                    this.func_150516_a(world, i + 2, j3, k, Blocks.ladder, 4);
                }
            }
            else if (LOTRMod.isOpaque(world, i, j3, k + 3)) {
                this.func_150516_a(world, i, j3, k + 2, Blocks.ladder, 2);
            }
        }
        if (rotation == 0) {
            for (int j3 = j + 1; j3 <= j + 2; ++j3) {
                this.func_150516_a(world, i - 1, j3, k - 3, LOTRMod.brick, 5);
                this.func_150516_a(world, i, j3, k - 3, Blocks.air, 0);
                this.func_150516_a(world, i + 1, j3, k - 3, LOTRMod.brick, 5);
            }
            for (int k3 = k - 4; k3 >= k - 7; --k3) {
                for (int i3 = i - 2; i3 <= i + 2; i3 += 4) {
                    final int j2 = world.getHeightValue(i3, k3);
                    final Block id = world.getBlock(i3, j2 - 1, k3);
                    if (id == Blocks.grass && random.nextInt(4) != 0) {
                        this.func_150516_a(world, i3, j2, k3, LOTRMod.wall, 3 + random.nextInt(3));
                    }
                }
            }
        }
        if (rotation == 1) {
            for (int j3 = j + 1; j3 <= j + 2; ++j3) {
                this.func_150516_a(world, i + 3, j3, k - 1, LOTRMod.brick, 5);
                this.func_150516_a(world, i + 3, j3, k, Blocks.air, 0);
                this.func_150516_a(world, i + 3, j3, k + 1, LOTRMod.brick, 5);
            }
            for (int i2 = i + 4; i2 <= i + 7; ++i2) {
                for (int k2 = k - 2; k2 <= k + 2; k2 += 4) {
                    final int j2 = world.getHeightValue(i2, k2);
                    final Block id = world.getBlock(i2, j2 - 1, k2);
                    if (id == Blocks.grass && random.nextInt(4) != 0) {
                        this.func_150516_a(world, i2, j2, k2, LOTRMod.wall, 3 + random.nextInt(3));
                    }
                }
            }
        }
        if (rotation == 2) {
            for (int j3 = j + 1; j3 <= j + 2; ++j3) {
                this.func_150516_a(world, i - 1, j3, k + 3, LOTRMod.brick, 5);
                this.func_150516_a(world, i, j3, k + 3, Blocks.air, 0);
                this.func_150516_a(world, i + 1, j3, k + 3, LOTRMod.brick, 5);
            }
            for (int k3 = k + 4; k3 <= k + 7; ++k3) {
                for (int i3 = i - 2; i3 <= i + 2; i3 += 4) {
                    final int j2 = world.getHeightValue(i3, k3);
                    final Block id = world.getBlock(i3, j2 - 1, k3);
                    if (id == Blocks.grass && random.nextInt(4) != 0) {
                        this.func_150516_a(world, i3, j2, k3, LOTRMod.wall, 3 + random.nextInt(3));
                    }
                }
            }
        }
        if (rotation == 3) {
            for (int j3 = j + 1; j3 <= j + 2; ++j3) {
                this.func_150516_a(world, i - 3, j3, k - 1, LOTRMod.brick, 5);
                this.func_150516_a(world, i - 3, j3, k, Blocks.air, 0);
                this.func_150516_a(world, i - 3, j3, k + 1, LOTRMod.brick, 5);
            }
            for (int i2 = i - 4; i2 >= i - 7; --i2) {
                for (int k2 = k - 2; k2 <= k + 2; k2 += 4) {
                    final int j2 = world.getHeightValue(i2, k2);
                    final Block id = world.getBlock(i2, j2 - 1, k2);
                    if (id == Blocks.grass && random.nextInt(4) != 0) {
                        this.func_150516_a(world, i2, j2, k2, LOTRMod.wall, 3 + random.nextInt(3));
                    }
                }
            }
        }
        final int radius = 8;
        for (int ruinParts = 2 + random.nextInt(6), l = 0; l < ruinParts; ++l) {
            final int i4 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            final int k4 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            final int j4 = world.getHeightValue(i4, k4);
            final Block id2 = world.getBlock(i4, j4 - 1, k4);
            if (id2 == Blocks.grass) {
                final int height = 1 + random.nextInt(3);
                boolean flag = true;
                for (int j5 = j4; j5 < j4 + height && flag; flag = !LOTRMod.isOpaque(world, i4, j5, k4), ++j5) {}
                if (flag) {
                    for (int j5 = j4; j5 < j4 + height; ++j5) {
                        this.func_150516_a(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
                    }
                    this.setGrassToDirt(world, i4, j4 - 1, k4);
                }
            }
        }
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(16) == 0) {
            return;
        }
        if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
        }
        else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 1);
        }
    }
}
