// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenCorsairTent extends LOTRWorldGenCorsairStructure
{
    public LOTRWorldGenCorsairTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (!this.isSurface(world, i2, j2, k2) && block != Blocks.stone && block != Blocks.sandstone) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 4) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    final int randomGround = random.nextInt(3);
                    if (randomGround == 0) {
                        if (j3 == 0) {
                            this.setBiomeTop(world, i3, j3, k3);
                        }
                        else {
                            this.setBiomeFiller(world, i3, j3, k3);
                        }
                    }
                    else if (randomGround == 1) {
                        this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 1);
                    }
                    else if (randomGround == 2) {
                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)Blocks.sand, 0);
                    }
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 3; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        for (int k4 = -3; k4 <= 3; ++k4) {
            for (final int i4 : new int[] { -2, 2 }) {
                for (int j4 = 1; j4 <= 2; ++j4) {
                    this.setBlockAndMetadata(world, i4, j4, k4, Blocks.wool, random.nextBoolean() ? 15 : 7);
                }
                this.setGrassToDirt(world, i4, 0, k4);
            }
            this.setBlockAndMetadata(world, -1, 3, k4, Blocks.wool, random.nextBoolean() ? 15 : 7);
            this.setBlockAndMetadata(world, 1, 3, k4, Blocks.wool, random.nextBoolean() ? 15 : 7);
            this.setBlockAndMetadata(world, 0, 4, k4, Blocks.wool, random.nextBoolean() ? 15 : 7);
            if (Math.abs(k4) == 3) {
                this.setBlockAndMetadata(world, 0, 5, k4, Blocks.wool, random.nextBoolean() ? 15 : 7);
            }
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, -3, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 0, j5, 3, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
        if (random.nextBoolean()) {
            this.placeChest(world, random, -1, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.CORSAIR, 1 + random.nextInt(2));
        }
        else {
            this.placeChest(world, random, 1, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.CORSAIR, 1 + random.nextInt(2));
        }
        return true;
    }
}
