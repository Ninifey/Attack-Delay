// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDunedain;
import lotr.common.LOTRFoods;
import com.google.common.math.IntMath;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRangerStables extends LOTRWorldGenRangerStructure
{
    public LOTRWorldGenRangerStables(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1, -2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -1; k2 <= 10; ++k2) {
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
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = 0; k3 <= 9; ++k3) {
                final int i4 = Math.abs(i3);
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 8; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                if (k3 <= 4 && (k3 == 0 || k3 == 4 || i4 == 4)) {
                    boolean beam = false;
                    if ((k3 == 0 || k3 == 4) && (i4 == 0 || i4 == 4)) {
                        beam = true;
                    }
                    if (beam) {
                        for (int j2 = 1; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                    else if (k3 == 4) {
                        for (int j2 = 1; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.plankBlock, super.plankMeta);
                        }
                    }
                    else {
                        for (int j2 = 1; j2 <= 3; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.wallBlock, super.wallMeta);
                        }
                    }
                }
            }
        }
        for (final int k2 : new int[] { 0, 4 }) {
            for (int i5 = -3; i5 <= 3; ++i5) {
                if (i5 != 0) {
                    this.setBlockAndMetadata(world, i5, 3, k2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                }
            }
        }
        for (final int i6 : new int[] { -4, 0, 4 }) {
            for (int k4 = 0; k4 <= 3; ++k4) {
                this.setBlockAndMetadata(world, i6, 4, k4, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
            for (int j2 = 4; j2 <= 6; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, 4, super.woodBeamBlock, super.woodBeamMeta);
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, 4, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (final int i6 : new int[] { -4, 4 }) {
            this.setBlockAndMetadata(world, i6, 5, 2, super.wallBlock, super.wallMeta);
            this.setBlockAndMetadata(world, i6, 5, 3, super.wallBlock, super.wallMeta);
            this.setBlockAndMetadata(world, i6, 6, 3, super.wallBlock, super.wallMeta);
        }
        this.setBlockAndMetadata(world, -2, 1, 0, super.doorBlock, 1);
        this.setBlockAndMetadata(world, -2, 2, 0, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 2, 2, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -4, 2, 2, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 4, 2, 2, super.fenceBlock, super.fenceMeta);
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = 1; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.plankBlock, super.plankMeta);
            }
            if (i3 != 0) {
                for (int k3 = 1; k3 <= 3; ++k3) {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                this.setBlockAndMetadata(world, i3, 4, 4, super.plankBlock, super.plankMeta);
            }
        }
        for (final int i6 : new int[] { -4, 0, 4 }) {
            for (int j2 = 1; j2 <= 3; ++j2) {
                this.setBlockAndMetadata(world, i6, j2, 9, super.woodBeamBlock, super.woodBeamMeta);
            }
            for (int k4 = 5; k4 <= 9; ++k4) {
                this.setBlockAndMetadata(world, i6, 4, k4, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            }
            for (int k4 = 5; k4 <= 8; ++k4) {
                this.setBlockAndMetadata(world, i6, 1, k4, super.fenceBlock, super.fenceMeta);
                this.setBlockAndMetadata(world, i6, 3, k4, super.fenceBlock, super.fenceMeta);
            }
            this.setBlockAndMetadata(world, i6, 2, 5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i6, 2, 8, super.fenceBlock, super.fenceMeta);
        }
        for (final int i6 : new int[] { -4, 4 }) {
            this.setBlockAndMetadata(world, i6, 5, 5, super.wallBlock, super.wallMeta);
            this.setBlockAndMetadata(world, i6, 5, 6, super.wallBlock, super.wallMeta);
            this.setBlockAndMetadata(world, i6, 5, 7, super.wallBlock, super.wallMeta);
            this.setBlockAndMetadata(world, i6, 6, 5, super.wallBlock, super.wallMeta);
            this.setBlockAndMetadata(world, i6, 6, 6, super.wallBlock, super.wallMeta);
            this.setBlockAndMetadata(world, i6, 7, 5, super.wallBlock, super.wallMeta);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            if (i3 != 0) {
                this.setBlockAndMetadata(world, i3, 1, 9, super.fenceGateBlock, 2);
                for (int k3 = 5; k3 <= 8; ++k3) {
                    final int randomFloor = random.nextInt(3);
                    if (randomFloor == 0) {
                        this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.grass, 0);
                    }
                    else if (randomFloor == 1) {
                        this.setBlockAndMetadata(world, i3, 0, k3, Blocks.dirt, 1);
                    }
                    else if (randomFloor == 2) {
                        this.setBlockAndMetadata(world, i3, 0, k3, LOTRMod.dirtPath, 0);
                    }
                    if (random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                }
                this.setBlockAndMetadata(world, i3, 4, 5, super.plankStairBlock, 7);
                this.setBlockAndMetadata(world, i3, 4, 6, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            }
        }
        this.setBlockAndMetadata(world, -3, 3, 9, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 3, 9, super.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 3, 9, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 3, 9, super.plankStairBlock, 5);
        for (int i3 = -5; i3 <= 5; ++i3) {
            final boolean avoidBeam = IntMath.mod(i3, 4) == 0;
            if (!avoidBeam) {
                this.setBlockAndMetadata(world, i3, 4, 0, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i3, 4, 9, super.roofStairBlock, 3);
            }
            for (int l = 0; l <= 2; ++l) {
                this.setBlockAndMetadata(world, i3, 5 + l, 1 + l, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i3, 5 + l, 8 - l, super.roofStairBlock, 3);
            }
            for (int k5 = 4; k5 <= 5; ++k5) {
                this.setBlockAndMetadata(world, i3, 8, k5, super.roofSlabBlock, super.roofSlabMeta);
            }
            if (Math.abs(i3) == 5) {
                for (int l = 0; l <= 3; ++l) {
                    this.setBlockAndMetadata(world, i3, 4 + l, 1 + l, super.roofStairBlock, 7);
                    this.setBlockAndMetadata(world, i3, 4 + l, 8 - l, super.roofStairBlock, 6);
                }
            }
        }
        for (final int i6 : new int[] { -4, 0, 4 }) {
            this.setBlockAndMetadata(world, i6, 3, -1, Blocks.torch, 4);
            this.setBlockAndMetadata(world, i6, 3, 10, Blocks.torch, 3);
        }
        this.setBlockAndMetadata(world, -5, 3, 4, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 5, 3, 4, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 1, 3, Blocks.crafting_table, 0);
        this.placeChest(world, random, -2, 1, 3, 2, super.chestContentsHouse);
        this.setBlockAndMetadata(world, -1, 1, 3, super.plankBlock, super.plankMeta);
        this.placePlateWithCertainty(world, random, -1, 2, 3, super.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, 0, 1, 3, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 0, 2, 3, 2, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 1, 1, 3, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 1, 2, 3, 0, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 3, 1, 3, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, 3, 2, 3, 1, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 2, 1, 1, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 3, 1, 1, super.bedBlock, 9);
        this.setBlockAndMetadata(world, -3, 3, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 3, 2, Blocks.torch, 1);
        for (int j4 = 1; j4 <= 4; ++j4) {
            this.setBlockAndMetadata(world, 2, j4, 3, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 0, 7, 5, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 7, 5, super.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 7, 5, super.plankStairBlock, 5);
        for (int i3 = -3; i3 <= 3; ++i3) {
            if (random.nextInt(3) != 0) {
                this.setBlockAndMetadata(world, i3, 5, 2, Blocks.hay_block, 0);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            if (random.nextInt(3) != 0) {
                final int h = 5;
                for (int h2 = h + random.nextInt(2), j3 = h; j3 <= h2; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, 6, Blocks.hay_block, 0);
                }
            }
        }
        for (final int i6 : new int[] { -3, 3 }) {
            for (int k4 = 3; k4 <= 5; ++k4) {
                if (random.nextInt(3) != 0) {
                    final int h3 = 5;
                    for (int h4 = h3 + random.nextInt(2), j5 = h3; j5 <= h4; ++j5) {
                        this.setBlockAndMetadata(world, i6, j5, k4, Blocks.hay_block, 0);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -2, 5, 3, super.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 5, 3, super.bedBlock, 11);
        this.placeChest(world, random, -3, 5, 5, 4, super.chestContentsHouse);
        this.setBlockAndMetadata(world, -3, 6, 4, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 6, 4, Blocks.torch, 1);
        for (int men = 1, m = 0; m < men; ++m) {
            final LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
            this.spawnNPCAndSetHome(dunedain, world, 0, 1, 2, 8);
        }
        for (final int i5 : new int[] { -2, 2 }) {
            final LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome((EntityCreature)horse, world, i5, 1, 7, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
        }
        return true;
    }
}
