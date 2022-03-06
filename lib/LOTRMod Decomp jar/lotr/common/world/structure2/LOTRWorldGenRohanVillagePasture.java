// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillagePasture extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanVillagePasture(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
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
                    if (maxHeight - minHeight > 4) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    if (j2 == 0) {
                        final int randomFloor = random.nextInt(3);
                        if (randomFloor == 0) {
                            this.setBlockAndMetadata(world, i3, j2, k3, (Block)Blocks.grass, 0);
                        }
                        else if (randomFloor == 1) {
                            this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 1);
                        }
                        else if (randomFloor == 2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, LOTRMod.dirtPath, 0);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 0);
                    }
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 5; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 == 4 && k4 == 4) {
                    this.setGrassToDirt(world, i3, -1, k3);
                    for (int j2 = 1; j2 <= 2; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.logBlock, super.logMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 3, k3, Blocks.torch, 5);
                }
                else if (i4 == 4 || k4 == 4) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 1, -4, super.fenceGateBlock, 0);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -1; k3 <= 1; ++k3) {
                if (random.nextInt(3) == 0) {
                    final int j3 = 1;
                    int j4 = 1;
                    if (i3 == 0 && k3 == 0 && random.nextBoolean()) {
                        ++j4;
                    }
                    for (int j5 = j3; j5 <= j4; ++j5) {
                        this.setBlockAndMetadata(world, i3, j5, k3, Blocks.hay_block, 0);
                    }
                }
            }
        }
        for (int animals = 4 + random.nextInt(5), l = 0; l < animals; ++l) {
            final EntityCreature animal = (EntityCreature)LOTRWorldGenGondorBarn.getRandomAnimal(world, random);
            final int i5 = 3 * (random.nextBoolean() ? 1 : -1);
            final int k5 = 3 * (random.nextBoolean() ? 1 : -1);
            if (random.nextBoolean()) {
                this.spawnNPCAndSetHome(animal, world, i5, 1, 0, 0);
            }
            else {
                this.spawnNPCAndSetHome(animal, world, 0, 1, k5, 0);
            }
            animal.detachHome();
        }
        return true;
    }
}
