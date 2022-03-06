// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanWell extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanWell(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, super.rockBlock, super.rockMeta);
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                for (int j3 = 1; j3 <= 5; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                if (i3 == 1 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    for (int j3 = 2; j3 <= 3; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plank2SlabBlock, super.plank2SlabMeta);
                }
                if ((i3 == 0 && k3 == 1) || (k3 == 0 && i3 == 1)) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
                if (i3 == 0 && k3 == 0) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.plank2SlabBlock, super.plank2SlabMeta);
                    for (int j3 = 3; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.gateWoodenCross, 0);
        final int depth = 0 + random.nextInt(2);
        final int waterDepth = 2 + random.nextInt(4);
        int j3;
        int wellTop;
        int wellBottom;
        for (wellTop = -1, wellBottom = (j3 = wellTop - depth - waterDepth - 1); j3 <= wellTop; ++j3) {
            for (int i4 = -1; i4 <= 1; ++i4) {
                for (int k4 = -1; k4 <= 1; ++k4) {
                    final int i5 = Math.abs(i4);
                    final int k5 = Math.abs(k4);
                    if (j3 == wellBottom) {
                        this.setBlockAndMetadata(world, i4, j3, k4, super.rockBlock, super.rockMeta);
                    }
                    else if (i5 == 0 && k5 == 0) {
                        if (j3 <= wellBottom + waterDepth) {
                            this.setBlockAndMetadata(world, i4, j3, k4, Blocks.water, 0);
                        }
                        else {
                            this.setAir(world, i4, j3, k4);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i4, j3, k4, super.rockBlock, super.rockMeta);
                    }
                }
            }
        }
        for (j3 = wellBottom + waterDepth + 1; j3 <= wellTop; ++j3) {
            this.setBlockAndMetadata(world, 0, j3, 0, Blocks.ladder, 2);
        }
        return true;
    }
}
