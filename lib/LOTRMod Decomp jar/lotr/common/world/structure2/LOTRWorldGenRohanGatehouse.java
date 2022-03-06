// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanGatehouse extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanGatehouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean oneWoodType() {
        return true;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -9; i2 <= 9; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -9; i2 <= 9; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 >= 3 || k3 <= 1) {
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                    for (int j3 = 1; j3 <= 12; ++j3) {
                        this.setAir(world, i2, j3, k2);
                    }
                }
                if (i3 == 2 && k3 == 0) {
                    for (int j3 = 1; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
                if (i3 == 2 && k3 == 1) {
                    for (int j3 = 1; j3 <= 3; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plankBlock, super.plankMeta);
                }
                if (i3 == 1 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                if (i3 >= 3 && i3 <= 9 && k3 <= 2) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    for (int j3 = 2; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                    }
                }
                if ((i3 == 4 || i3 == 8) && k3 == 2) {
                    for (int j3 = 2; j3 <= 8; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.woodBeamBlock, super.woodBeamMeta);
                    }
                    this.setBlockAndMetadata(world, i2, 9, k2, super.plankBlock, super.plankMeta);
                }
                if ((i3 >= 5 && i3 <= 7 && k3 == 2) || ((i3 == 4 | i3 == 8) && k3 <= 1)) {
                    this.setBlockAndMetadata(world, i2, 9, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                if (i3 == 9 && k3 <= 1) {
                    for (int j3 = 2; j3 <= 5; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.woodBeamBlock, super.woodBeamMeta);
                    }
                    this.setBlockAndMetadata(world, i2, 6, k2, super.fenceBlock, super.fenceMeta);
                }
                if (i3 >= 5 && i3 <= 7 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    this.setBlockAndMetadata(world, i2, 5, k2, super.woodBeamBlock, super.woodBeamMeta);
                    if (k2 < 0) {
                        this.setBlockAndMetadata(world, i2, 6, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (i3 == 3 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 2, k2, super.brickWallBlock, super.brickWallMeta);
                    this.setBlockAndMetadata(world, i2, 3, k2, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i2, 4, k2, super.brickWallBlock, super.brickWallMeta);
                }
                if (i3 == 9 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 2, k2, super.brickWallBlock, super.brickWallMeta);
                    for (int j3 = 3; j3 <= 6; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (i3 <= 8 && k3 <= 1) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.plankBlock, super.plankMeta);
                }
                if (i3 <= 3 && k3 == 2) {
                    if (i3 == 3) {
                        this.setBlockAndMetadata(world, i2, 5, k2, super.plankBlock, super.plankMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 5, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                    }
                    this.setBlockAndMetadata(world, i2, 6, k2, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int j4 = 1; j4 <= 5; ++j4) {
                this.setBlockAndMetadata(world, i2, j4, 0, super.gateBlock, 2);
            }
        }
        this.setBlockAndMetadata(world, 0, 6, 0, super.fenceBlock, super.fenceMeta);
        for (final int i4 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, i4, 6, 0, super.woodBeamBlock, super.woodBeamMeta);
            this.setBlockAndMetadata(world, i4, 7, 0, Blocks.lever, 13);
        }
        for (final int i4 : new int[] { -3, 3 }) {
            this.placeWallBanner(world, i4, 5, -2, super.bannerType, 2);
        }
        for (final int i4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i4, 4, -2, Blocks.torch, 4);
            this.setBlockAndMetadata(world, i4, 4, 2, Blocks.torch, 3);
        }
        for (final int k4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, -9, 8, k4, Blocks.torch, 1);
            this.setBlockAndMetadata(world, -3, 8, k4, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 3, 8, k4, Blocks.torch, 1);
            this.setBlockAndMetadata(world, 9, 8, k4, Blocks.torch, 2);
        }
        for (final int i4 : new int[] { -6, 6 }) {
            for (int k5 = -3; k5 <= 3; ++k5) {
                final int k6 = Math.abs(k5);
                if (k6 == 3) {
                    this.setBlockAndMetadata(world, i4 - 1, 10, k5, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i4, 10, k5, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i4 + 1, 10, k5, super.roofSlabBlock, super.roofSlabMeta);
                }
                if (k6 == 2) {
                    this.setBlockAndMetadata(world, i4 - 2, 10, k5, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i4 - 1, 10, k5, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i4, 10, k5, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i4 + 1, 10, k5, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i4 + 2, 10, k5, super.roofSlabBlock, super.roofSlabMeta);
                }
                if (k6 <= 1) {
                    this.setBlockAndMetadata(world, i4 - 3, 10, k5, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i4 - 2, 10, k5, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i4 - 1, 10, k5, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                    this.setBlockAndMetadata(world, i4 - 1, 11, k5, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i4, 10, k5, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                    this.setBlockAndMetadata(world, i4, 11, k5, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i4 + 1, 10, k5, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                    this.setBlockAndMetadata(world, i4 + 1, 11, k5, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i4 + 2, 10, k5, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i4 + 3, 10, k5, super.roofSlabBlock, super.roofSlabMeta);
                }
            }
        }
        for (final int i4 : new int[] { -3, 3 }) {
            final int j3 = 6;
            final int k7 = 0;
            final LOTREntityRohanMan guard = new LOTREntityRohirrimWarrior(world);
            this.spawnNPCAndSetHome(guard, world, i4, j3, k7, 8);
        }
        for (int k8 = 3; k8 <= 4; ++k8) {
            final int maxStep = 16;
            for (int step = 0; step < maxStep; ++step) {
                final int i4 = -6 - step;
                final int j3 = 5 - ((step <= 1) ? 0 : (step - 2));
                if (this.isOpaque(world, i4, j3, k8)) {
                    break;
                }
                if (step <= 1) {
                    this.setBlockAndMetadata(world, i4, j3, k8, super.plankBlock, super.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i4, j3, k8, super.plankStairBlock, 1);
                }
                this.setGrassToDirt(world, i4, j3 - 1, k8);
                for (int j5 = j3 - 1; !this.isOpaque(world, i4, j5, k8) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i4, j5, k8, super.plankBlock, super.plankMeta);
                    this.setGrassToDirt(world, i4, j5 - 1, k8);
                }
            }
            for (int step = 0; step < maxStep; ++step) {
                final int i4 = 6 + step;
                final int j3 = 5 - ((step <= 1) ? 0 : (step - 2));
                if (this.isOpaque(world, i4, j3, k8)) {
                    break;
                }
                if (step <= 1) {
                    this.setBlockAndMetadata(world, i4, j3, k8, super.plankBlock, super.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i4, j3, k8, super.plankStairBlock, 0);
                }
                this.setGrassToDirt(world, i4, j3 - 1, k8);
                for (int j5 = j3 - 1; !this.isOpaque(world, i4, j5, k8) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i4, j5, k8, super.plankBlock, super.plankMeta);
                    this.setGrassToDirt(world, i4, j5 - 1, k8);
                }
            }
        }
        return true;
    }
}
