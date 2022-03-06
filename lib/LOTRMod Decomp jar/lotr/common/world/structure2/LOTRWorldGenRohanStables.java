// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTREntityRohanStablemaster;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanStables extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanStables(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -8; i2 <= 8; ++i2) {
                for (int k2 = -1; k2 <= 26; ++k2) {
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
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k3 = 0; k3 <= 26; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = IntMath.mod(k3, 4);
                if (k3 <= 12) {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 7; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.grass, 0);
                    for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        final int randomGround = random.nextInt(4);
                        if (randomGround == 0) {
                            this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 0);
                        }
                        else if (randomGround == 1) {
                            this.setBlockAndMetadata(world, i3, j2, k3, Blocks.gravel, 0);
                        }
                        else if (randomGround == 2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.cobbleBlock, super.cobbleMeta);
                        }
                        else if (randomGround == 3) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.rockBlock, super.rockMeta);
                        }
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 6; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                if (k3 >= 0 && k3 <= 12) {
                    if (k3 >= 1 && k3 <= 11) {
                        if (i4 >= 1 && i4 <= 2) {
                            this.setBlockAndMetadata(world, i3, 0, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                        }
                        if (i4 <= 2 && random.nextBoolean()) {
                            this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                        }
                    }
                    if (i4 == 7 && k4 != 0) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.plankBlock, super.plankMeta);
                        this.setBlockAndMetadata(world, i3, 2, k3, super.fenceBlock, super.fenceMeta);
                        if (k4 == 2) {
                            this.setBlockAndMetadata(world, i3, 3, k3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, 3, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    else if ((k3 == 0 || k3 == 12) && i4 >= 4 && i4 <= 6) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.plankBlock, super.plankMeta);
                        this.setBlockAndMetadata(world, i3, 2, k3, super.fenceBlock, super.fenceMeta);
                        if (i4 == 5) {
                            this.setBlockAndMetadata(world, i3, 3, k3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, 3, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    if (i4 >= 3 && i4 <= 6 && k3 >= 1 && k3 <= 11) {
                        if (k4 == 0) {
                            if (i4 >= 4) {
                                this.setBlockAndMetadata(world, i3, 1, k3, super.plankBlock, super.plankMeta);
                                this.setBlockAndMetadata(world, i3, 2, k3, super.fenceBlock, super.fenceMeta);
                            }
                        }
                        else {
                            if (i4 >= 4) {
                                this.setBlockAndMetadata(world, i3, 0, k3, Blocks.dirt, 1);
                                if (i4 == 6) {
                                    if (k4 == 3) {
                                        this.setBlockAndMetadata(world, i3, 1, k3, (Block)Blocks.cauldron, 3);
                                    }
                                    else {
                                        this.setBlockAndMetadata(world, i3, 1, k3, Blocks.hay_block, 0);
                                    }
                                }
                                else {
                                    this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                                }
                            }
                            if (i4 == 3) {
                                if (k4 == 1) {
                                    this.setBlockAndMetadata(world, i3, 1, k3, super.fenceBlock, super.fenceMeta);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i3, 1, k3, super.fenceGateBlock, (i3 > 0) ? 3 : 1);
                                }
                                if (k4 == 2) {
                                    this.setBlockAndMetadata(world, i3, 4, k3, super.brickSlabBlock, super.brickSlabMeta);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                                }
                            }
                        }
                        if (k4 == 2 && i4 == 5) {
                            final LOTREntityHorse horse = new LOTREntityHorse(world);
                            this.spawnNPCAndSetHome((EntityCreature)horse, world, i3, 1, k3, 0);
                            horse.setHorseType(0);
                            horse.saddleMountForWorldGen();
                            horse.detachHome();
                        }
                    }
                    if (k3 == 0 && i4 >= 1 && i4 <= 2) {
                        for (int j2 = 1; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.gateBlock, 2);
                        }
                        this.setBlockAndMetadata(world, i3, 4, k3, super.plank2SlabBlock, super.plank2SlabMeta);
                    }
                    if (k3 == 12 && i4 >= 1 && i4 <= 2) {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.fenceGateBlock, 0);
                        this.setBlockAndMetadata(world, i3, 3, k3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                        this.setBlockAndMetadata(world, i3, 4, k3, super.plank2SlabBlock, super.plank2SlabMeta);
                    }
                }
                else if (i4 == 7 || k3 == 26) {
                    boolean beam = false;
                    if (k3 == 19 && i4 == 7) {
                        beam = true;
                    }
                    if (k3 == 26 && i4 % 7 == 0) {
                        beam = true;
                    }
                    if (beam) {
                        for (int j3 = 1; j3 <= 2; ++j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                        this.setGrassToDirt(world, i3, 0, k3);
                        this.setBlockAndMetadata(world, i3, 3, k3, super.fenceBlock, super.fenceMeta);
                        this.setBlockAndMetadata(world, i3, 4, k3, Blocks.torch, 5);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 1, k3, super.fenceBlock, super.fenceMeta);
                    }
                }
                else {
                    if (random.nextInt(3) == 0) {
                        this.plantTallGrass(world, random, i3, 1, k3);
                    }
                    final int hayX = 0;
                    final int hayZ = 20;
                    final int hayDist = 1 + random.nextInt(3);
                    final int dx = i3 - hayX;
                    final int dz = k3 - hayZ;
                    final int distSq = dx * dx + dz * dz;
                    if (distSq < hayDist * hayDist && random.nextInt(3) != 0) {
                        for (int hayHeight = 1 + random.nextInt(3), j4 = 1; j4 <= hayHeight; ++j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, Blocks.hay_block, 0);
                        }
                        this.setGrassToDirt(world, i3, 0, k3);
                    }
                    if (i4 == 4 && k3 == 23) {
                        for (int horses = 2 + random.nextInt(3), l = 0; l < horses; ++l) {
                            final LOTREntityHorse horse2 = new LOTREntityHorse(world);
                            this.spawnNPCAndSetHome((EntityCreature)horse2, world, i3, 1, k3, 0);
                            horse2.setHorseType(0);
                            horse2.setGrowingAge(0);
                            horse2.detachHome();
                        }
                    }
                }
            }
        }
        for (int k5 = 0; k5 <= 12; ++k5) {
            for (int step = 0; step <= 7; ++step) {
                final int i2 = 8 - step;
                final int j5 = 4 + step / 2;
                Block block = super.roofSlabBlock;
                int meta = super.roofSlabMeta;
                if (k5 == 6) {
                    block = super.plank2SlabBlock;
                    meta = super.plank2SlabMeta;
                }
                if (step % 2 == 1) {
                    meta |= 0x8;
                }
                this.setBlockAndMetadata(world, -i2, j5, k5, block, meta);
                this.setBlockAndMetadata(world, i2, j5, k5, block, meta);
                if (step >= 2) {
                    block = super.plankSlabBlock;
                    meta = super.plankSlabMeta;
                    if (step % 2 == 1) {
                        meta |= 0x8;
                    }
                    this.setBlockAndMetadata(world, -i2, j5 - 1, k5, block, meta);
                    this.setBlockAndMetadata(world, i2, j5 - 1, k5, block, meta);
                }
            }
        }
        for (final int k2 : new int[] { -1, 13 }) {
            for (int step2 = 0; step2 <= 7; ++step2) {
                final int i5 = 8 - step2;
                final int j6 = 4 + step2 / 2;
                if (step2 % 2 == 0) {
                    this.setBlockAndMetadata(world, -i5, j6, k2, super.plank2SlabBlock, super.plank2SlabMeta);
                    this.setBlockAndMetadata(world, -i5, j6 - 1, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                    this.setBlockAndMetadata(world, i5, j6, k2, super.plank2SlabBlock, super.plank2SlabMeta);
                    this.setBlockAndMetadata(world, i5, j6 - 1, k2, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
                else {
                    this.setBlockAndMetadata(world, -i5, j6, k2, super.plank2Block, super.plank2Meta);
                    this.setBlockAndMetadata(world, i5, j6, k2, super.plank2Block, super.plank2Meta);
                }
            }
        }
        for (int k5 = -2; k5 <= 14; ++k5) {
            this.setBlockAndMetadata(world, 0, 7, k5, super.logBlock, super.logMeta | 0x8);
            this.setBlockAndMetadata(world, 0, 8, k5, super.plank2SlabBlock, super.plank2SlabMeta);
        }
        for (final int k2 : new int[] { -1, 6, 13 }) {
            this.setBlockAndMetadata(world, -1, 8, k2, super.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 1, 8, k2, super.plank2StairBlock, 4);
        }
        for (final int k2 : new int[] { 0, 12 }) {
            this.setBlockAndMetadata(world, -5, 4, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -4, 4, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 4, 4, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 5, 4, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -2, 5, k2, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 5, k2, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, -2, 6, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -1, 6, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 1, 5, k2, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 5, k2, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 1, 6, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 2, 6, k2, super.plankBlock, super.plankMeta);
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k3 = 0; k3 <= 12; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = IntMath.mod(k3, 4);
                if ((i4 == 0 || i4 == 3 || i4 == 7) && k4 == 0) {
                    if (i4 == 0 && (k3 == 4 || k3 == 8)) {
                        for (int j2 = 1; j2 <= 2; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.rockWallBlock, super.rockWallMeta);
                        }
                    }
                    else {
                        for (int j2 = 1; j2 <= 2; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickCarvedBlock, super.brickCarvedMeta);
                    if (i4 == 3) {
                        for (int j2 = 4; j2 <= 5; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                    if (i4 == 0) {
                        for (int j2 = 4; j2 <= 6; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                }
                if (k3 >= 1 && k3 <= 11 && ((i4 == 0 && k4 != 0) || (i4 >= 1 && i4 <= 2 && k4 == 0))) {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            if (IntMath.mod(i3, 3) == 0) {
                this.setBlockAndMetadata(world, i3, 3, -1, Blocks.torch, 4);
                this.setBlockAndMetadata(world, i3, 3, 13, Blocks.torch, 3);
            }
        }
        for (final int k2 : new int[] { 4, 8 }) {
            this.setBlockAndMetadata(world, -1, 3, k2, Blocks.torch, 1);
            this.setBlockAndMetadata(world, 1, 3, k2, Blocks.torch, 2);
        }
        final LOTREntityRohanStablemaster stablemaster = new LOTREntityRohanStablemaster(world);
        this.spawnNPCAndSetHome(stablemaster, world, 0, 1, 6, 8);
        for (int men = 1 + random.nextInt(3), m = 0; m < men; ++m) {
            final LOTREntityRohanMan stabler = random.nextBoolean() ? new LOTREntityRohirrimWarrior(world) : new LOTREntityRohanMan(world);
            this.spawnNPCAndSetHome(stabler, world, 0, 1, 6, 16);
        }
        return true;
    }
}
