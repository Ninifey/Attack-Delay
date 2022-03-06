// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenBDBarrow extends LOTRWorldGenStructureBase2
{
    private LOTRWorldGenStructureBase2 ruinsGen;
    
    public LOTRWorldGenBDBarrow(final boolean flag) {
        super(flag);
        this.ruinsGen = new LOTRWorldGenStoneRuin.STONE(3, 3);
        this.ruinsGen.restrictions = false;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        final int radius = 12;
        final int height = 7;
        final int base = -4;
        this.setOriginAndRotation(world, i, j, k, rotation, (super.usingPlayer != null) ? radius : 0);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -radius; i2 <= radius; ++i2) {
                for (int k2 = -radius; k2 <= radius; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (this.getBlock(world, i2, j2, k2) != Blocks.grass) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                }
            }
            if (maxHeight - minHeight > 5) {
                return false;
            }
        }
        for (int i3 = -radius; i3 <= radius; ++i3) {
            for (int j3 = height; j3 >= base; --j3) {
                for (int k3 = -radius; k3 <= radius; ++k3) {
                    if (i3 * i3 + (j3 - base) * (j3 - base) + k3 * k3 <= radius * radius) {
                        final boolean grass = !this.isOpaque(world, i3, j3 + 1, k3);
                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)(grass ? Blocks.grass : Blocks.dirt), 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
            }
        }
        for (int i3 = -radius; i3 <= radius; ++i3) {
            for (int k4 = -radius; k4 <= radius; ++k4) {
                for (int j4 = base - 1; !this.isOpaque(world, i3, j4, k4) && this.getY(j4) >= 0; --j4) {
                    if (i3 * i3 + k4 * k4 <= radius * radius) {
                        this.setBlockAndMetadata(world, i3, j4, k4, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i3, j4 - 1, k4);
                    }
                }
            }
        }
        final int innerR = 5;
        final int innerH = 5;
        final int innerB = -2;
        for (int i4 = -innerR - 1; i4 <= innerR + 1; ++i4) {
            for (int k5 = -innerR - 1; k5 <= innerR + 1; ++k5) {
                for (int j5 = innerB + 1; j5 <= innerB + innerH + 1; ++j5) {
                    final int d = i4 * i4 + (j5 - innerB - 1) * (j5 - innerB - 1) + k5 * k5;
                    if (d < innerR * innerR) {
                        this.setAir(world, i4, j5, k5);
                        if (d > (innerR - 1) * (innerR - 1) && random.nextInt(3) == 0) {
                            this.placeRandomBrick(world, random, i4, j5, k5);
                        }
                    }
                    else if (d < (innerR + 1) * (innerR + 1)) {
                        this.placeRandomBrick(world, random, i4, j5, k5);
                    }
                }
                this.placeRandomBrick(world, random, i4, innerB, k5);
            }
        }
        this.placeSpawnerChest(world, random, 0, innerB + 1, 0, LOTRMod.spawnerChestStone, 0, LOTREntityBarrowWight.class, LOTRChestContents.BARROW_DOWNS);
        this.setBlockAndMetadata(world, 1, innerB + 1, 0, Blocks.stone_stairs, 0);
        this.setBlockAndMetadata(world, -1, innerB + 1, 0, Blocks.stone_stairs, 1);
        this.setBlockAndMetadata(world, 0, innerB + 1, -1, Blocks.stone_stairs, 2);
        this.setBlockAndMetadata(world, 0, innerB + 1, 1, Blocks.stone_stairs, 3);
        for (int k2 = -radius + 2; k2 <= -innerR + 1; ++k2) {
            for (int i5 = -1; i5 <= 1; ++i5) {
                this.placeRandomBrick(world, random, i5, 0, k2);
                for (int j5 = 1; j5 <= 3; ++j5) {
                    this.setAir(world, i5, j5, k2);
                }
                this.placeRandomBrick(world, random, i5, 4, k2);
            }
            for (int j2 = 0; j2 <= 4; ++j2) {
                this.placeRandomBrick(world, random, -2, j2, k2);
                this.placeRandomBrick(world, random, 2, j2, k2);
            }
        }
        for (int i4 = -1; i4 <= 1; ++i4) {
            this.setBlockAndMetadata(world, i4, innerB + 1, -innerR + 1, Blocks.stone_stairs, 3);
            for (int j2 = innerB + 2; j2 <= 3; ++j2) {
                this.setAir(world, i4, j2, -innerR + 1);
                this.setAir(world, i4, j2, -innerR + 2);
            }
            this.setBlockAndMetadata(world, i4, innerB + 2, -innerR + 0, Blocks.stone_stairs, 3);
        }
        this.placeRandomBrick(world, random, -2, innerB + 1, -innerR + 1);
        this.placeRandomBrick(world, random, 2, innerB + 1, -innerR + 1);
        for (final int i6 : new int[] { -3, 3 }) {
            this.placeRandomBrick(world, random, i6, 1, -radius + 1);
            this.placeRandomBrick(world, random, i6, 0, -radius + 1);
            this.placeRandomBrick(world, random, i6, -1, -radius + 1);
            this.placeRandomBrick(world, random, i6, 2, -radius + 2);
            this.placeRandomBrick(world, random, i6, 1, -radius + 2);
        }
        for (int i4 = -2; i4 <= 2; ++i4) {
            this.placeRandomBrick(world, random, i4, 5, -radius + 4);
            if (Math.abs(i4) <= 1) {
                this.placeRandomBrick(world, random, i4, 5, -radius + 3);
            }
        }
        for (int j6 = 1; j6 <= 3; ++j6) {
            this.placeRandomBrick(world, random, -1, j6, -radius + 4);
            this.placeRandomBrick(world, random, 0, j6, -radius + 6);
            this.placeRandomBrick(world, random, 1, j6, -radius + 4);
        }
        final int rX = 0;
        final int rY = height + 1;
        final int rZ = 0;
        this.ruinsGen.generateWithSetRotation(world, random, this.getX(rX, rZ), this.getY(rY), this.getZ(rX, rZ), this.getRotationMode());
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.stone, 0);
        }
        else if (random.nextInt(3) > 0) {
            final int l = random.nextInt(2);
            if (l == 0) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.cobblestone, 0);
            }
            if (l == 1) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.mossy_cobblestone, 0);
            }
        }
        else {
            final int l = random.nextInt(3);
            if (l == 0) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
            }
            if (l == 1) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
            }
            if (l == 2) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
            }
        }
    }
}
