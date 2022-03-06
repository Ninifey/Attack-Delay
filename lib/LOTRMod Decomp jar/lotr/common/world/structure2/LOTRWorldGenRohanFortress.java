// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTREntityRohirrimArcher;
import lotr.common.entity.npc.LOTREntityRohirrimMarshal;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityHorse;
import com.google.common.math.IntMath;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenRohanFortress extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanFortress(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.gateBlock = LOTRMod.gateRohan;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 13);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -12; i2 <= 12; ++i2) {
                for (int k2 = -12; k2 <= 12; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -12; i2 <= 12; ++i2) {
            for (int k2 = -12; k2 <= 12; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 1; j3 <= 10; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    if ((i3 == 12 && (k3 == 12 || k3 == 9 || k3 == 2)) || (k3 == 12 && (i3 == 12 || i3 == 9 || i3 == 2))) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.woodBeam2Block, super.woodBeam2Meta);
                    }
                    else if (i3 > 9 || k3 > 9) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.plankBlock, super.plankMeta);
                    }
                    else if (j3 == 0) {
                        final int randomGround = random.nextInt(3);
                        if (randomGround == 0) {
                            this.setBlockAndMetadata(world, i2, j3, k2, (Block)Blocks.grass, 0);
                        }
                        else if (randomGround == 1) {
                            this.setBlockAndMetadata(world, i2, j3, k2, Blocks.dirt, 1);
                        }
                        else if (randomGround == 2) {
                            this.setBlockAndMetadata(world, i2, j3, k2, LOTRMod.dirtPath, 0);
                        }
                        if (random.nextInt(3) == 0) {
                            this.setBlockAndMetadata(world, i2, j3 + 1, k2, LOTRMod.thatchFloor, 0);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, j3, k2, Blocks.dirt, 0);
                    }
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
            }
        }
        for (int i2 = -12; i2 <= 12; ++i2) {
            for (int k2 = -12; k2 <= 12; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                int yBoost = 0;
                if (k2 < 8 && i3 < 7) {
                    yBoost = 1;
                }
                if ((i3 == 9 || i3 == 12) && (k3 == 9 || k3 == 12)) {
                    for (int j4 = 1; j4 <= 8; ++j4) {
                        this.setBlockAndMetadata(world, i2, j4, k2, super.woodBeam2Block, super.woodBeam2Meta);
                    }
                }
                else if ((i3 == 12 || k3 == 12) && (k3 == 2 || i3 == 2)) {
                    for (int j4 = 1; j4 <= 6 + yBoost; ++j4) {
                        this.setBlockAndMetadata(world, i2, j4, k2, super.woodBeam2Block, super.woodBeam2Meta);
                    }
                }
                else if (i3 == 12 || k3 == 12) {
                    for (int j4 = 1; j4 <= 5 + yBoost; ++j4) {
                        this.setBlockAndMetadata(world, i2, j4, k2, super.plankBlock, super.plankMeta);
                    }
                    if ((i3 == 12 && k3 >= 10 && k3 <= 11) || (k3 == 12 && i3 >= 10 && i3 <= 11)) {
                        this.setBlockAndMetadata(world, i2, 5 + yBoost, k2, super.fenceBlock, super.fenceMeta);
                    }
                    else if (IntMath.mod(i3 + k3, 2) == 0) {
                        this.setBlockAndMetadata(world, i2, 6 + yBoost, k2, super.plankBlock, super.plankMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 6 + yBoost, k2, super.plankSlabBlock, super.plankSlabMeta);
                    }
                }
                else if (i3 > 9 || k3 > 9) {
                    for (int j4 = 1; j4 <= 4 + yBoost; ++j4) {
                        this.setBlockAndMetadata(world, i2, j4, k2, super.plankBlock, super.plankMeta);
                    }
                }
                else if (i3 == 9 || k3 == 9) {
                    this.setBlockAndMetadata(world, i2, 5 + yBoost, k2, super.fenceBlock, super.fenceMeta);
                    if ((i3 == 9 && IntMath.mod(k2, 3) == 0) || (k3 == 9 && IntMath.mod(i2, 3) == 0)) {
                        this.setBlockAndMetadata(world, i2, 6 + yBoost, k2, Blocks.torch, 5);
                    }
                    if (k2 == -9) {
                        this.setBlockAndMetadata(world, i2, 4 + yBoost, k2, super.plank2StairBlock, 7);
                    }
                    else if (k2 == 9) {
                        this.setBlockAndMetadata(world, i2, 4 + yBoost, k2, super.plank2StairBlock, 6);
                    }
                    else if (i2 == -9) {
                        this.setBlockAndMetadata(world, i2, 4 + yBoost, k2, super.plank2StairBlock, 4);
                    }
                    else if (i2 == 9) {
                        this.setBlockAndMetadata(world, i2, 4 + yBoost, k2, super.plank2StairBlock, 5);
                    }
                }
            }
        }
        for (final int i4 : new int[] { -12, 9 }) {
            for (final int k4 : new int[] { -12, 9 }) {
                this.setBlockAndMetadata(world, i4 + 1, 8, k4, super.plank2StairBlock, 4);
                this.setBlockAndMetadata(world, i4 + 2, 8, k4, super.plank2StairBlock, 5);
                this.setBlockAndMetadata(world, i4 + 1, 8, k4 + 3, super.plank2StairBlock, 4);
                this.setBlockAndMetadata(world, i4 + 2, 8, k4 + 3, super.plank2StairBlock, 5);
                this.setBlockAndMetadata(world, i4, 8, k4 + 1, super.plank2StairBlock, 7);
                this.setBlockAndMetadata(world, i4, 8, k4 + 2, super.plank2StairBlock, 6);
                this.setBlockAndMetadata(world, i4 + 3, 8, k4 + 1, super.plank2StairBlock, 7);
                this.setBlockAndMetadata(world, i4 + 3, 8, k4 + 2, super.plank2StairBlock, 6);
                for (int i5 = i4; i5 <= i4 + 3; ++i5) {
                    this.setBlockAndMetadata(world, i5, 9, k4 - 1, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i5, 9, k4 + 4, super.roofSlabBlock, super.roofSlabMeta);
                }
                for (int k5 = k4; k5 <= k4 + 3; ++k5) {
                    this.setBlockAndMetadata(world, i4 - 1, 9, k5, super.roofSlabBlock, super.roofSlabMeta);
                    this.setBlockAndMetadata(world, i4 + 4, 9, k5, super.roofSlabBlock, super.roofSlabMeta);
                }
                for (int i5 = i4; i5 <= i4 + 3; ++i5) {
                    for (int k6 = k4; k6 <= k4 + 3; ++k6) {
                        if (i5 >= i4 + 1 && i5 <= i4 + 2 && k6 >= k4 + 1 && k6 <= k4 + 2) {
                            this.setBlockAndMetadata(world, i5, 9, k6, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                            this.setBlockAndMetadata(world, i5, 10, k6, super.roofSlabBlock, super.roofSlabMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i5, 9, k6, super.roofBlock, super.roofMeta);
                        }
                    }
                }
            }
        }
        for (int k7 = -12; k7 <= 12; ++k7) {
            final int k8 = Math.abs(k7);
            if ((k8 >= 10 && k8 <= 11) || (k8 >= 3 && k8 <= 8)) {
                this.setBlockAndMetadata(world, -12, 1, k7, super.plank2StairBlock, 1);
                for (int j2 = 2; j2 <= 3; ++j2) {
                    this.setAir(world, -12, j2, k7);
                }
                this.setBlockAndMetadata(world, -12, 4, k7, super.plank2StairBlock, 5);
                this.setBlockAndMetadata(world, 12, 1, k7, super.plank2StairBlock, 0);
                for (int j2 = 2; j2 <= 3; ++j2) {
                    this.setAir(world, 12, j2, k7);
                }
                this.setBlockAndMetadata(world, 12, 4, k7, super.plank2StairBlock, 4);
            }
            if (k8 == 12 && k7 > 0) {
                for (int i6 = -1; i6 <= 1; ++i6) {
                    this.setBlockAndMetadata(world, i6, 1, k7, super.plank2Block, super.plank2Meta);
                    this.setBlockAndMetadata(world, i6, 4, k7, super.plank2Block, super.plank2Meta);
                    this.setBlockAndMetadata(world, i6, 5, k7, super.woodBeam2Block, super.woodBeam2Meta | 0x4);
                    this.setBlockAndMetadata(world, i6, 6, k7, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i6, 5, k7 - 1 * Integer.signum(k7), super.plankSlabBlock, super.plankSlabMeta);
                }
                this.setBlockAndMetadata(world, -2, 7, k7, super.plankSlabBlock, super.plankSlabMeta);
                this.setBlockAndMetadata(world, 2, 7, k7, super.plankSlabBlock, super.plankSlabMeta);
                this.setBlockAndMetadata(world, -1, 2, k7, super.plank2StairBlock, 0);
                this.setAir(world, 0, 2, k7);
                this.setBlockAndMetadata(world, 1, 2, k7, super.plank2StairBlock, 1);
                this.setBlockAndMetadata(world, -1, 3, k7, super.plank2StairBlock, 4);
                this.setAir(world, 0, 3, k7);
                this.setBlockAndMetadata(world, 1, 3, k7, super.plank2StairBlock, 5);
            }
        }
        for (int i2 = -12; i2 <= 12; ++i2) {
            final int i7 = Math.abs(i2);
            if ((i7 >= 10 && i7 <= 11) || (i7 >= 3 && i7 <= 8)) {
                this.setBlockAndMetadata(world, i2, 1, -12, super.plank2StairBlock, 2);
                for (int j2 = 2; j2 <= 3; ++j2) {
                    this.setAir(world, i2, j2, -12);
                }
                this.setBlockAndMetadata(world, i2, 4, -12, super.plank2StairBlock, 6);
                this.setBlockAndMetadata(world, i2, 1, 12, super.plank2StairBlock, 3);
                for (int j2 = 2; j2 <= 3; ++j2) {
                    this.setAir(world, i2, j2, 12);
                }
                this.setBlockAndMetadata(world, i2, 4, 12, super.plank2StairBlock, 7);
            }
            if (i7 == 12) {
                for (int k9 = -1; k9 <= 1; ++k9) {
                    this.setBlockAndMetadata(world, i2, 1, k9, super.plank2Block, super.plank2Meta);
                    this.setBlockAndMetadata(world, i2, 4, k9, super.plank2Block, super.plank2Meta);
                    this.setBlockAndMetadata(world, i2, 5, k9, super.woodBeam2Block, super.woodBeam2Meta | 0x8);
                    this.setBlockAndMetadata(world, i2, 6, k9, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i2 - 1 * Integer.signum(i2), 5, k9, super.plankSlabBlock, super.plankSlabMeta);
                }
                this.setBlockAndMetadata(world, i2, 7, -2, super.plankSlabBlock, super.plankSlabMeta);
                this.setBlockAndMetadata(world, i2, 7, 2, super.plankSlabBlock, super.plankSlabMeta);
                this.setBlockAndMetadata(world, i2, 2, -1, super.plank2StairBlock, 3);
                this.setAir(world, i2, 2, 0);
                this.setBlockAndMetadata(world, i2, 2, 1, super.plank2StairBlock, 2);
                this.setBlockAndMetadata(world, i2, 3, -1, super.plank2StairBlock, 7);
                this.setAir(world, i2, 3, 0);
                this.setBlockAndMetadata(world, i2, 3, 1, super.plank2StairBlock, 6);
            }
        }
        for (int k7 = -11; k7 <= -10; ++k7) {
            this.setAir(world, -6, 5, k7);
            this.setBlockAndMetadata(world, -5, 5, k7, super.plankStairBlock, 1);
            this.setBlockAndMetadata(world, 5, 5, k7, super.plankStairBlock, 0);
            this.setAir(world, 6, 5, k7);
        }
        for (int j5 = 4; j5 <= 7; ++j5) {
            this.setBlockAndMetadata(world, -6, j5, -9, super.woodBeam2Block, super.woodBeam2Meta);
            this.setBlockAndMetadata(world, 6, j5, -9, super.woodBeam2Block, super.woodBeam2Meta);
        }
        this.setBlockAndMetadata(world, -6, 8, -9, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 6, 8, -9, Blocks.torch, 5);
        for (int k7 = -12; k7 <= -10; ++k7) {
            for (int j6 = 1; j6 <= 4; ++j6) {
                for (int i6 = -1; i6 <= 1; ++i6) {
                    this.setAir(world, i6, j6, k7);
                }
                this.setBlockAndMetadata(world, -2, j6, k7, super.woodBeam2Block, super.woodBeam2Meta);
                this.setBlockAndMetadata(world, 2, j6, k7, super.woodBeam2Block, super.woodBeam2Meta);
            }
            for (int i8 = -1; i8 <= 1; ++i8) {
                this.setBlockAndMetadata(world, i8, 4, k7, super.woodBeam2Block, super.woodBeam2Meta | 0x4);
            }
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            for (int i8 = -1; i8 <= 1; ++i8) {
                this.setBlockAndMetadata(world, i8, j5, -11, super.gateBlock, 3);
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 4, -12, super.plank2StairBlock, 6);
            this.setBlockAndMetadata(world, i2, 5, -12, super.woodBeam2Block, super.woodBeam2Meta | 0x4);
            this.setBlockAndMetadata(world, i2, 6, -12, super.woodBeam2Block, super.woodBeam2Meta | 0x4);
            this.setBlockAndMetadata(world, i2, 6, -11, super.plankSlabBlock, super.plankSlabMeta);
        }
        for (int j5 = 6; j5 <= 8; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, -12, super.woodBeam2Block, super.woodBeam2Meta);
        }
        this.setBlockAndMetadata(world, -2, 8, -12, super.plankStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 9, -12, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, 2, 8, -12, super.plankStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 7, -12, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 1, 7, -12, super.fenceBlock, super.fenceMeta);
        this.placeWallBanner(world, -2, 6, -12, super.bannerType, 2);
        this.placeWallBanner(world, 0, 7, -12, super.bannerType, 2);
        this.placeWallBanner(world, 2, 6, -12, super.bannerType, 2);
        this.setBlockAndMetadata(world, -2, 3, -13, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, -13, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 3, -9, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, -9, Blocks.torch, 3);
        for (int k7 = -13; k7 <= 9; ++k7) {
            for (int i8 = -1; i8 <= 1; ++i8) {
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i8, j2, k7)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i8, j2, k7, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i8, j2 - 1, k7);
                }
            }
            if (k7 > -10) {
                this.setBlockAndMetadata(world, -2, 0, k7, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                this.setBlockAndMetadata(world, 2, 0, k7, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                if (IntMath.mod(k7, 4) == 2) {
                    this.setBlockAndMetadata(world, -2, 1, k7, super.brickWallBlock, super.brickWallMeta);
                    this.setBlockAndMetadata(world, -2, 2, k7, Blocks.torch, 5);
                    this.setBlockAndMetadata(world, 2, 1, k7, super.brickWallBlock, super.brickWallMeta);
                    this.setBlockAndMetadata(world, 2, 2, k7, Blocks.torch, 5);
                }
            }
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, -2, j5, 10, super.woodBeam2Block, super.woodBeam2Meta);
            this.setBlockAndMetadata(world, 2, j5, 10, super.woodBeam2Block, super.woodBeam2Meta);
        }
        this.setBlockAndMetadata(world, -2, 3, 9, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, 9, Blocks.torch, 4);
        for (final int i4 : new int[] { -7, 7 }) {
            this.setBlockAndMetadata(world, i4, 1, 0, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4, 2, 0, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i4, 3, 0, Blocks.torch, 5);
            for (int l = 0; l < 2; ++l) {
                final LOTREntityHorse horse = new LOTREntityHorse(world);
                this.spawnNPCAndSetHome((EntityCreature)horse, world, i4 - Integer.signum(i4) * 3, 1, 0, 0);
                horse.setHorseType(0);
                horse.saddleMountForWorldGen();
                horse.detachHome();
                this.leashEntityTo((EntityCreature)horse, world, i4, 2, 0);
            }
        }
        for (int k7 = -9; k7 <= -5; ++k7) {
            for (int i8 = -9; i8 <= -5; ++i8) {
                this.setBlockAndMetadata(world, i8, 3, k7, super.plank2SlabBlock, super.plank2SlabMeta);
            }
        }
        this.setBlockAndMetadata(world, -9, 3, -9, super.plank2Block, super.plank2Meta);
        this.setBlockAndMetadata(world, -6, 3, -9, super.plank2Block, super.plank2Meta);
        for (int j5 = 1; j5 <= 2; ++j5) {
            if (j5 == 1) {
                this.setBlockAndMetadata(world, -7, j5, -9, Blocks.furnace, 3);
                this.setBlockAndMetadata(world, -9, j5, -7, Blocks.furnace, 4);
            }
            else {
                this.setBlockAndMetadata(world, -7, j5, -9, LOTRMod.alloyForge, 3);
                this.setBlockAndMetadata(world, -9, j5, -7, LOTRMod.alloyForge, 4);
            }
            this.setBlockAndMetadata(world, -8, j5, -9, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -9, j5, -9, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -9, j5, -8, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -5, j5, -5, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -5, 1, -9, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -5, 2, -9, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -6, 1, -9, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -6, 2, -9, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -9, 1, -6, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -9, 2, -6, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -9, 1, -5, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -9, 2, -5, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -6, 1, -5, Blocks.anvil, 1);
        this.setBlockAndMetadata(world, -5, 1, -6, (Block)Blocks.cauldron, 3);
        final LOTREntityRohanBlacksmith blacksmith = new LOTREntityRohanBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, -4, 1, -4, 8);
        for (int k2 = 5; k2 <= 9; ++k2) {
            for (int i6 = -9; i6 <= -5; ++i6) {
                this.setBlockAndMetadata(world, i6, 3, k2, super.plank2SlabBlock, super.plank2SlabMeta);
            }
        }
        this.setBlockAndMetadata(world, -9, 3, 9, super.plank2Block, super.plank2Meta);
        for (int j6 = 1; j6 <= 2; ++j6) {
            this.setBlockAndMetadata(world, -9, j6, 9, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -5, j6, 5, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -5, 1, 9, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -5, 2, 9, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -6, 1, 9, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -6, 2, 9, Blocks.torch, 4);
        this.placeChest(world, random, -7, 1, 9, 2, LOTRChestContents.ROHAN_WATCHTOWER);
        this.placeChest(world, random, -8, 1, 9, 2, LOTRChestContents.ROHAN_WATCHTOWER);
        this.setBlockAndMetadata(world, -9, 1, 8, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -9, 1, 7, super.tableBlock, 0);
        this.setBlockAndMetadata(world, -9, 1, 6, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -9, 2, 6, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -9, 1, 5, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -9, 2, 5, super.fenceBlock, super.fenceMeta);
        for (int k2 = 5; k2 <= 10; ++k2) {
            for (int i6 = 5; i6 <= 10; ++i6) {
                this.setBlockAndMetadata(world, i6, 0, k2, super.plank2Block, super.plank2Meta);
                this.setAir(world, i6, 1, k2);
                this.setAir(world, i6, 2, k2);
                this.setBlockAndMetadata(world, i6, 3, k2, super.plank2Block, super.plank2Meta);
            }
        }
        for (int k2 = 4; k2 <= 9; ++k2) {
            this.setBlockAndMetadata(world, 4, 3, k2, super.plank2StairBlock, 1);
        }
        for (int i8 = 5; i8 <= 9; ++i8) {
            this.setBlockAndMetadata(world, i8, 3, 4, super.plank2StairBlock, 2);
        }
        for (int k2 = 5; k2 <= 10; ++k2) {
            this.setBlockAndMetadata(world, 5, 1, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 5, 2, k2, super.plankBlock, super.plankMeta);
        }
        for (int i8 = 6; i8 <= 10; ++i8) {
            this.setBlockAndMetadata(world, i8, 1, 5, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, i8, 2, 5, super.plankBlock, super.plankMeta);
        }
        this.setBlockAndMetadata(world, 5, 0, 8, super.plank2Block, super.plank2Meta);
        this.setBlockAndMetadata(world, 5, 1, 8, super.doorBlock, 2);
        this.setBlockAndMetadata(world, 5, 2, 8, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 5, 1, 5, super.woodBeam2Block, super.woodBeam2Meta);
        this.setBlockAndMetadata(world, 5, 2, 5, super.woodBeam2Block, super.woodBeam2Meta);
        for (int i8 = 6; i8 <= 10; ++i8) {
            if (IntMath.mod(i8, 2) == 0) {
                this.setBlockAndMetadata(world, i8, 2, 6, Blocks.torch, 3);
                this.setBlockAndMetadata(world, i8, 2, 10, Blocks.torch, 4);
            }
            for (int k9 = 6; k9 <= 10; ++k9) {
                if (random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i8, 1, k9, LOTRMod.thatchFloor, 0);
                }
            }
        }
        for (final int k10 : new int[] { 6, 10 }) {
            this.setBlockAndMetadata(world, 7, 1, k10, super.bedBlock, 3);
            this.setBlockAndMetadata(world, 6, 1, k10, super.bedBlock, 11);
            this.setBlockAndMetadata(world, 9, 1, k10, super.bedBlock, 1);
            this.setBlockAndMetadata(world, 10, 1, k10, super.bedBlock, 9);
        }
        this.placeChest(world, random, 8, 1, 6, 3, super.chestContents);
        this.placeChest(world, random, 8, 1, 10, 2, super.chestContents);
        this.setBlockAndMetadata(world, 7, 1, 8, super.carpetBlock, super.carpetMeta);
        this.setBlockAndMetadata(world, 8, 1, 8, super.carpetBlock, super.carpetMeta);
        this.setBlockAndMetadata(world, 10, 1, 8, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 10, 2, 8, 5, LOTRFoods.ROHAN_DRINK);
        for (int j6 = 1; j6 <= 4; ++j6) {
            this.setBlockAndMetadata(world, 6, j6, -9, super.woodBeam2Block, super.woodBeam2Meta);
            this.setBlockAndMetadata(world, 7, j6, -9, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 8, j6, -9, super.plank2Block, super.plank2Meta);
            this.setBlockAndMetadata(world, 9, j6, -9, super.woodBeam2Block, super.woodBeam2Meta);
        }
        for (int k2 = -8; k2 <= -7; ++k2) {
            for (int i6 = 6; i6 <= 9; ++i6) {
                final int stairHeight = i6 - 5;
                for (int j3 = 0; j3 < stairHeight; ++j3) {
                    this.setBlockAndMetadata(world, i6, j3, k2, super.plankBlock, super.plankMeta);
                }
                this.setBlockAndMetadata(world, i6, stairHeight, k2, super.plankStairBlock, 1);
            }
            this.setAir(world, 9, 5, k2);
        }
        this.placeWallBanner(world, -10, 3, 0, super.bannerType, 1);
        this.placeWallBanner(world, 10, 3, 0, super.bannerType, 3);
        for (int i8 = -1; i8 <= 1; ++i8) {
            this.setBlockAndMetadata(world, i8, 0, 10, super.brickBlock, super.brickMeta);
            for (int j2 = 1; j2 <= 3; ++j2) {
                this.setAir(world, i8, j2, 10);
            }
        }
        this.setBlockAndMetadata(world, 0, 1, 9, LOTRMod.commandTable, 0);
        this.placeWallBanner(world, 0, 3, 11, super.bannerType, 2);
        final LOTREntityRohirrimMarshal marshal = new LOTREntityRohirrimMarshal(world);
        marshal.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(marshal, world, 0, 1, 0, 8);
        for (int m = 0; m < 8; ++m) {
            final LOTREntityRohirrimWarrior rohirrim = (world.rand.nextInt(3) == 0) ? new LOTREntityRohirrimArcher(world) : new LOTREntityRohirrimWarrior(world);
            rohirrim.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(rohirrim, world, 0, 1, 0, 20);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityRohirrimWarrior.class, LOTREntityRohirrimArcher.class);
        respawner.setCheckRanges(16, -8, 10, 12);
        respawner.setSpawnRanges(11, 1, 6, 20);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
}
