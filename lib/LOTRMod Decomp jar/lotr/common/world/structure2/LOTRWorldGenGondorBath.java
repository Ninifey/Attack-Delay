// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;

public class LOTRWorldGenGondorBath extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorBath(final boolean flag) {
        super(flag);
    }
    
    protected LOTREntityNPC createBather(final World world) {
        return new LOTREntityGondorMan(world);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 10);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -11; i2 <= 11; ++i2) {
                for (int k2 = -9; k2 <= 9; ++k2) {
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
                    if (maxHeight - minHeight > 6) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -11; i3 <= 11; ++i3) {
            for (int k3 = -9; k3 <= 9; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 >= -1 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 8; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 <= 6 && k4 <= 4 && i4 + k4 <= 8) {
                    this.setBlockAndMetadata(world, i3, 0, k3, Blocks.water, 0);
                }
            }
        }
        for (int i3 = -10; i3 <= 10; ++i3) {
            for (int k3 = -8; k3 <= 8; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 == 10 && k4 % 4 == 0) || (k4 == 8 && i4 % 4 == 2)) {
                    for (int j2 = 1; j2 <= 4; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                    }
                    this.setBlockAndMetadata(world, i3 - 1, 1, k3, super.brickStairBlock, 1);
                    this.setBlockAndMetadata(world, i3 + 1, 1, k3, super.brickStairBlock, 0);
                    this.setBlockAndMetadata(world, i3, 1, k3 - 1, super.brickStairBlock, 2);
                    this.setBlockAndMetadata(world, i3, 1, k3 + 1, super.brickStairBlock, 3);
                    this.setBlockAndMetadata(world, i3 - 1, 4, k3, super.brickStairBlock, 5);
                    this.setBlockAndMetadata(world, i3 + 1, 4, k3, super.brickStairBlock, 4);
                    this.setBlockAndMetadata(world, i3, 4, k3 - 1, super.brickStairBlock, 6);
                    this.setBlockAndMetadata(world, i3, 4, k3 + 1, super.brickStairBlock, 7);
                }
                if (i4 == 10 || k4 == 8) {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (final int i5 : new int[] { -6, 6 }) {
            for (final int k5 : new int[] { -4, 4 }) {
                for (int j3 = 1; j3 <= 7; ++j3) {
                    this.setBlockAndMetadata(world, i5, j3, k5, super.pillarBlock, super.pillarMeta);
                }
                this.setBlockAndMetadata(world, i5 - 1, 1, k5, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i5 + 1, 1, k5, super.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i5, 1, k5 - 1, super.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i5, 1, k5 + 1, super.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i5 - 1, 7, k5, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i5 + 1, 7, k5, super.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i5, 7, k5 - 1, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i5, 7, k5 + 1, super.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i5 - 1, 4, k5, Blocks.torch, 1);
                this.setBlockAndMetadata(world, i5 + 1, 4, k5, Blocks.torch, 2);
                this.setBlockAndMetadata(world, i5, 4, k5 - 1, Blocks.torch, 4);
                this.setBlockAndMetadata(world, i5, 4, k5 + 1, Blocks.torch, 3);
            }
        }
        for (int step = 0; step <= 3; ++step) {
            final int j4 = 5 + step;
            final int i2 = 11 - step;
            final int k2 = 9 - step;
            for (int i6 = -i2; i6 <= i2; ++i6) {
                this.setBlockAndMetadata(world, i6, j4, -k2, super.brick2StairBlock, 2);
                this.setBlockAndMetadata(world, i6, j4, k2, super.brick2StairBlock, 3);
            }
            for (int k6 = -k2 + 1; k6 <= k2 - 1; ++k6) {
                this.setBlockAndMetadata(world, -i2, j4, k6, super.brick2StairBlock, 1);
                this.setBlockAndMetadata(world, i2, j4, k6, super.brick2StairBlock, 0);
            }
            if (step >= 2) {
                for (int i6 = -i2 + 1; i6 <= i2 - 1; ++i6) {
                    this.setBlockAndMetadata(world, i6, j4 - 1, -k2, super.brick2StairBlock, 7);
                    this.setBlockAndMetadata(world, i6, j4 - 1, k2, super.brick2StairBlock, 6);
                }
                for (int k6 = -k2; k6 <= k2; ++k6) {
                    this.setBlockAndMetadata(world, -i2, j4 - 1, k6, super.brick2StairBlock, 4);
                    this.setBlockAndMetadata(world, i2, j4 - 1, k6, super.brick2StairBlock, 5);
                }
            }
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k3 = -5; k3 <= 5; ++k3) {
                this.setBlockAndMetadata(world, i3, 8, k3, super.brick2Block, super.brick2Meta);
            }
        }
        for (int i3 = -9; i3 <= 9; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 == 9 && k4 % 4 == 0) || (k4 == 7 && i4 % 4 == 2)) {
                    for (int j2 = 5; j2 <= 6; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                    }
                }
            }
        }
        for (int bathers = 2 + random.nextInt(4), l = 0; l < bathers; ++l) {
            final LOTREntityNPC man = this.createBather(world);
            this.spawnNPCAndSetHome(man, world, 0, 0, 0, 16);
        }
        return true;
    }
}
