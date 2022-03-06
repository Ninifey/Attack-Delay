// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.map;

import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;

public class LOTRWorldGenUtumnoEntrance extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenUtumnoEntrance() {
        super(false);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, int rotation) {
        rotation = 2;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        super.originY = 0;
        final int radius = 8;
        final int baseHeight = 40;
        final int portalHeight = 10;
        final int portalBase = portalHeight - 2 - 1;
        for (int i2 = -radius; i2 <= radius; ++i2) {
            for (int k2 = -radius; k2 <= radius; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int maxHeight = 100 + random.nextInt(10), j2 = baseHeight; j2 <= maxHeight; ++j2) {
                    if (i3 == radius || k3 == radius || j2 == baseHeight || j2 >= maxHeight - 10) {
                        this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.utumnoBrick, 2);
                    }
                    else {
                        this.setAir(world, i2, j2, k2);
                    }
                }
                if (i3 < radius && k3 < radius && random.nextInt(16) == 0) {
                    for (int height = 1 + random.nextInt(2), j3 = baseHeight; j3 <= baseHeight + height; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, LOTRMod.utumnoBrick, 2);
                    }
                }
            }
        }
        for (int l = 0; l < 40; ++l) {
            final int i4 = -random.nextInt(radius * 3) + random.nextInt(radius * 3);
            final int k4 = -random.nextInt(radius * 3) + random.nextInt(radius * 3);
            final int width = 1 + random.nextInt(3);
            final int height2 = width * 4 + random.nextInt(4);
            for (int i5 = i4 - width; i5 <= i4 + width; ++i5) {
                for (int k5 = k4 - width; k5 <= k4 + width; ++k5) {
                    final int base = this.getTopBlock(world, i5, k5);
                    for (int top = base + height2 - random.nextInt(3), j4 = base; j4 < top; ++j4) {
                        this.setBlockAndMetadata(world, i5, j4, k5, LOTRMod.utumnoBrick, 2);
                    }
                }
            }
        }
        final int entranceX = -radius;
        final int entranceZ = -radius;
        final int entranceY = 80;
        final int entranceSize = 6;
        final int entranceSizeExtra = entranceSize + 3;
        for (int i6 = entranceX - entranceSize; i6 <= entranceX + entranceSize; ++i6) {
            for (int j3 = entranceY - entranceSize; j3 <= entranceY + entranceSize; ++j3) {
                for (int k6 = entranceZ - entranceSize; k6 <= entranceZ + entranceSize; ++k6) {
                    final int i7 = i6 - entranceX;
                    final int j4 = j3 - entranceY;
                    final int k7 = k6 - entranceZ;
                    final float dist = (float)(i7 * i7 + j4 * j4 + k7 * k7);
                    if (dist < entranceSize * entranceSize || (dist < entranceSizeExtra * entranceSizeExtra && random.nextInt(6) == 0)) {
                        this.setAir(world, i6, j3, k6);
                    }
                }
            }
        }
        int stairX = entranceX + 1;
        int stairY = entranceY - entranceSize - 1;
        int stairZ = entranceZ + 1;
        int stairDirection = 2;
        while (true) {
            this.setBlockAndMetadata(world, stairX, stairY, stairZ, LOTRMod.utumnoBrick, 2);
            if (stairY <= baseHeight) {
                break;
            }
            --stairY;
            if (stairDirection == 0 && this.getBlock(world, stairX, stairY, stairZ + 1).isOpaqueCube()) {
                stairDirection = 1;
            }
            if (stairDirection == 1 && this.getBlock(world, stairX - 1, stairY, stairZ).isOpaqueCube()) {
                stairDirection = 2;
            }
            if (stairDirection == 2 && this.getBlock(world, stairX, stairY, stairZ - 1).isOpaqueCube()) {
                stairDirection = 3;
            }
            if (stairDirection == 3 && this.getBlock(world, stairX + 1, stairY, stairZ).isOpaqueCube()) {
                stairDirection = 0;
            }
            if (stairDirection == 0) {
                ++stairZ;
            }
            if (stairDirection == 1) {
                --stairX;
            }
            if (stairDirection == 2) {
                --stairZ;
            }
            if (stairDirection != 3) {
                continue;
            }
            ++stairX;
        }
        for (int i8 = -2; i8 <= 2; ++i8) {
            for (int k8 = -2; k8 <= 2; ++k8) {
                final int i9 = Math.abs(i8);
                final int k9 = Math.abs(k8);
                for (int j5 = portalBase; j5 <= baseHeight + 1; ++j5) {
                    if (i9 < 2 && k9 < 2) {
                        if (j5 == portalBase) {
                            this.setBlockAndMetadata(world, i8, j5, k8, LOTRMod.utumnoBrickEntrance, 0);
                        }
                        else if (j5 < portalHeight) {
                            this.setAir(world, i8, j5, k8);
                        }
                        else if (j5 == portalHeight && i9 <= 1 && k9 <= 1) {
                            this.setBlockAndMetadata(world, i8, j5, k8, LOTRMod.utumnoPortal, 0);
                        }
                        else {
                            this.setAir(world, i8, j5, k8);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i8, j5, k8, LOTRMod.utumnoBrickEntrance, 0);
                    }
                }
                if (i9 == 2 && k9 == 2) {
                    int min = baseHeight + 2;
                    int max = min + 2 + random.nextInt(2);
                    for (int j6 = min; j6 <= max; ++j6) {
                        this.setBlockAndMetadata(world, i8, j6, k8, LOTRMod.utumnoPillar, 1);
                    }
                    this.setBlockAndMetadata(world, i8, max + 1, k8, LOTRMod.utumnoBrick, 2);
                    min = max + 2;
                    max = min + 2;
                    for (int j6 = min; j6 <= max; ++j6) {
                        this.setBlockAndMetadata(world, i8, j6, k8, LOTRMod.utumnoPillar, 1);
                    }
                }
            }
        }
        return true;
    }
}
