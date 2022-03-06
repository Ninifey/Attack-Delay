// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfAltar extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfAltar(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 13);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -12; i2 <= 12; ++i2) {
                for (int k2 = -12; k2 <= 8; ++k2) {
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
                    if (maxHeight - minHeight > 16) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                for (int j3 = 5; j3 <= 10; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("gulf_altar");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("FLAG", super.flagBlock, super.flagMeta);
        this.associateBlockMetaAlias("BONE", super.boneBlock, super.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeSkull(world, 0, 7, 0, 0);
        final int holeX = 0;
        final int holeZ = 6;
        final int holeR = 3;
        final int holeD = 3;
        if (this.getTopBlock(world, holeX, holeZ) >= -8) {
            for (int i4 = -holeR; i4 <= holeR; ++i4) {
                for (int k4 = -holeR; k4 <= holeR; ++k4) {
                    final int i5 = holeX + i4;
                    final int k5 = holeZ + k4;
                    final int dSq = i4 * i4 + k4 * k4;
                    if (dSq < holeR * holeR) {
                        final int holeY = this.getTopBlock(world, i5, k5) - 1;
                        if (this.isSurface(world, i5, holeY, k5)) {
                            for (int holeDHere = (int)Math.round(Math.sqrt(Math.max(0, holeR * holeR - dSq))), j4 = 3; j4 >= -holeDHere; --j4) {
                                final int j5 = holeY + j4;
                                if (j4 > 0) {
                                    this.setAir(world, i5, j5, k5);
                                }
                                else if (j4 > -holeDHere) {
                                    this.setAir(world, i5, j5, k5);
                                }
                                else if (j4 == -holeDHere) {
                                    if (random.nextBoolean()) {
                                        this.setBlockAndMetadata(world, i5, j5, k5, Blocks.dirt, 1);
                                    }
                                    else {
                                        this.setBlockAndMetadata(world, i5, j5, k5, LOTRMod.wasteBlock, 0);
                                    }
                                    if (random.nextInt(6) == 0) {
                                        this.placeSkull(world, random, i5, j5 + 1, k5);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        final int maxSteps = 12;
        for (int i6 = -1; i6 <= 1; ++i6) {
            for (int step = 0; step < maxSteps; ++step) {
                final int j6 = 0 - step / 2;
                final int k6 = -13 - step;
                if (this.isOpaque(world, i6, j6, k6)) {
                    break;
                }
                if (step % 2 == 0) {
                    this.setBlockAndMetadata(world, i6, j6, k6, super.plankBlock, super.plankMeta);
                    this.setGrassToDirt(world, i6, j6 - 1, k6);
                }
                else {
                    this.setBlockAndMetadata(world, i6, j6, k6, super.plankSlabBlock, super.plankSlabMeta);
                    this.setBlockAndMetadata(world, i6, j6 - 1, k6, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
            }
        }
        for (int k4 = -1; k4 <= 1; ++k4) {
            for (int step = 0; step < maxSteps; ++step) {
                final int j6 = 0 - step / 2;
                final int i7 = -13 - step;
                if (this.isOpaque(world, i7, j6, k4)) {
                    break;
                }
                if (step % 2 == 0) {
                    this.setBlockAndMetadata(world, i7, j6, k4, super.plankBlock, super.plankMeta);
                    this.setGrassToDirt(world, i7, j6 - 1, k4);
                }
                else {
                    this.setBlockAndMetadata(world, i7, j6, k4, super.plankSlabBlock, super.plankSlabMeta);
                    this.setBlockAndMetadata(world, i7, j6 - 1, k4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
            }
            for (int step = 0; step < maxSteps; ++step) {
                final int j6 = 0 - step / 2;
                final int i7 = 13 + step;
                if (this.isOpaque(world, i7, j6, k4)) {
                    break;
                }
                if (step % 2 == 0) {
                    this.setBlockAndMetadata(world, i7, j6, k4, super.plankBlock, super.plankMeta);
                    this.setGrassToDirt(world, i7, j6 - 1, k4);
                }
                else {
                    this.setBlockAndMetadata(world, i7, j6, k4, super.plankSlabBlock, super.plankSlabMeta);
                    this.setBlockAndMetadata(world, i7, j6 - 1, k4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
            }
        }
        final LOTREntityGulfHaradrim gulfman = new LOTREntityGulfHaradrim(world);
        this.spawnNPCAndSetHome(gulfman, world, 0, 7, -1, 4);
        return true;
    }
}
