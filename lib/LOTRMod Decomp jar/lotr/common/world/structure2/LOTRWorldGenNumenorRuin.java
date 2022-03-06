// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenNumenorRuin extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenNumenorRuin(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        final int width = 3 + random.nextInt(3);
        this.setOriginAndRotation(world, i, j, k, rotation, width + 1);
        for (int i2 = -width; i2 <= width; ++i2) {
            for (int k2 = -width; k2 <= width; ++k2) {
                if (Math.abs(i2) == width || Math.abs(k2) == width) {
                    for (int j2 = 0; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                        this.placeRandomBrick(world, random, i2, j2, k2);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i2, 0, k2, (Block)Blocks.grass, 0);
                    for (int j2 = -1; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i2, j2 - 1, k2);
                    }
                }
            }
        }
        if (random.nextBoolean()) {
            LOTRTreeType.OAK_LARGE.create(super.notifyChanges, random).generate(world, random, super.originX, super.originY + 1, super.originZ);
        }
        else {
            LOTRTreeType.BEECH_LARGE.create(super.notifyChanges, random).generate(world, random, super.originX, super.originY + 1, super.originZ);
        }
        for (int i2 = -width; i2 <= width; ++i2) {
            for (int k2 = -width; k2 <= width; ++k2) {
                if (Math.abs(i2) == width || Math.abs(k2) == width) {
                    for (int height = width * 2 + random.nextInt(8), j3 = 1; j3 < height; ++j3) {
                        this.placeRandomBrick(world, random, i2, j3, k2);
                    }
                }
            }
        }
        this.setAir(world, 0, 1, -width);
        this.setAir(world, 0, 2, -width);
        for (int ruins = 10 + random.nextInt(20), l = 0; l < ruins; ++l) {
            final int i3 = -width * 2 + random.nextInt(width * 2 + 1);
            final int k3 = -width * 2 + random.nextInt(width * 2 + 1);
            final int j4 = this.getTopBlock(world, i3, k3);
            final Block block = this.getBlock(world, i3, j4 - 1, k3);
            if (block == Blocks.grass || block == Blocks.dirt || block == Blocks.stone) {
                final int l2 = random.nextInt(3);
                if (l2 == 0) {
                    this.setBlockAndMetadata(world, i3, j4 - 1, k3, Blocks.gravel, 0);
                }
                else if (l2 == 1) {
                    this.placeRandomBrick(world, random, i3, j4 - 1, k3);
                }
                else if (l2 == 2) {
                    for (int height2 = 1 + random.nextInt(3), j5 = j4; j5 < j4 + height2; ++j5) {
                        if (this.isOpaque(world, i3, j5, k3)) {
                            break;
                        }
                        this.placeRandomBrick(world, random, i3, j5, k3);
                    }
                }
            }
        }
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(5);
        if (l == 0) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
        }
        else if (l == 1) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 1);
        }
        else if (l == 2) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 2);
        }
        else if (l == 3) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.cobblestone, 0);
        }
        else if (l == 4) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.mossy_cobblestone, 0);
        }
    }
}
