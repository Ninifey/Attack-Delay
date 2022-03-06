// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.block.Block;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRangerHouse extends LOTRWorldGenRangerStructure
{
    public LOTRWorldGenRangerHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 7; ++i2) {
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
                    if (maxHeight - minHeight > 5) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 7; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                final int k4 = Math.abs(k3);
                for (int j3 = 1; j3 <= 8; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                if (i3 >= 5 && k4 <= 1) {
                    for (int j3 = 5; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
                else if (i3 == 6 && k4 == 2) {
                    for (int j3 = 4; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
                else if (i3 <= 5) {
                    boolean beam = false;
                    boolean wall = false;
                    if (i3 == -2 && IntMath.mod(k3, 3) == 0) {
                        beam = true;
                    }
                    if (k4 == 3 && (i3 == 2 || i3 == 5)) {
                        beam = true;
                    }
                    if (i3 == -2 || k4 == 3) {
                        wall = true;
                    }
                    if (beam) {
                        for (int j4 = 4; (j4 >= 0 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.woodBeamBlock, super.woodBeamMeta);
                            this.setGrassToDirt(world, i3, j4 - 1, k3);
                        }
                    }
                    else if (wall) {
                        for (int j4 = 1; j4 <= 4; ++j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.wallBlock, super.wallMeta);
                        }
                        for (int j4 = 0; (j4 >= 0 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.plankBlock, super.plankMeta);
                            this.setGrassToDirt(world, i3, j4 - 1, k3);
                        }
                    }
                    else {
                        for (int j4 = 0; (j4 >= 0 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.plankBlock, super.plankMeta);
                            this.setGrassToDirt(world, i3, j4 - 1, k3);
                        }
                        if (random.nextInt(3) == 0) {
                            this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                        }
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 0, -3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 1, -3, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -3, super.doorBlock, 8);
        this.setBlockAndMetadata(world, -2, 2, -1, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -2, 2, 1, super.fenceBlock, super.fenceMeta);
        for (final int k2 : new int[] { -3, 3 }) {
            if (k2 >= 0) {
                this.setBlockAndMetadata(world, 0, 2, k2, super.fenceBlock, super.fenceMeta);
            }
            this.setBlockAndMetadata(world, 3, 2, k2, super.fenceBlock, super.fenceMeta);
        }
        for (int i3 = -2; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 4, 3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            if (i3 <= 4) {
                this.setBlockAndMetadata(world, i3, 4, 0, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            }
        }
        for (int i3 = -2; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -4, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 4, 4, super.roofStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 5, -3, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 5, 3, super.roofStairBlock, 3);
        }
        for (int k5 = -3; k5 <= 3; ++k5) {
            final int k6 = Math.abs(k5);
            this.setBlockAndMetadata(world, -3, 4, k5, super.roofStairBlock, 1);
            if (k6 <= 2) {
                this.setBlockAndMetadata(world, -2, 5, k5, super.roofStairBlock, 1);
            }
            if (k6 >= 2) {
                this.setBlockAndMetadata(world, 6, 4, k5, super.roofStairBlock, 0);
            }
            if (k6 == 2) {
                this.setBlockAndMetadata(world, 5, 5, k5, super.roofStairBlock, 0);
            }
        }
        for (int i3 = -1; i3 <= 4; ++i3) {
            for (int k3 = -2; k3 <= 2; ++k3) {
                final int k4 = Math.abs(k3);
                if (k4 <= 1 && i3 >= 0 && i3 <= 3) {
                    this.setBlockAndMetadata(world, i3, 6, k3, super.roofBlock, super.roofMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 6, k3, super.roofSlabBlock, super.roofSlabMeta);
                }
            }
        }
        for (final int k2 : new int[] { -2, 2 }) {
            for (int i4 = -1; i4 <= 4; ++i4) {
                this.setBlockAndMetadata(world, i4, 5, k2, super.roofBlock, super.roofMeta);
            }
        }
        for (final int i5 : new int[] { -1, 4 }) {
            for (int k7 = -1; k7 <= 1; ++k7) {
                this.setBlockAndMetadata(world, i5, 5, k7, super.fenceBlock, super.fenceMeta);
            }
        }
        this.setBlockAndMetadata(world, 6, 5, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 7, 5, -1, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 7, 5, 0, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 7, 5, 1, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 6, 5, 1, super.brickStairBlock, 3);
        for (int j5 = 6; j5 <= 7; ++j5) {
            this.setBlockAndMetadata(world, 6, j5, 0, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 6, 8, 0, super.brickWallBlock, super.brickWallMeta);
        this.setBlockAndMetadata(world, 2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 3, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 1, -1, super.tableBlock, 0);
        this.setBlockAndMetadata(world, -1, 1, 0, super.plankBlock, super.plankMeta);
        this.placePlateWithCertainty(world, random, -1, 2, 0, super.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, -1, 1, 1, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -1, 1, 2, super.plankBlock, super.plankMeta);
        this.placeMug(world, random, -1, 2, 2, random.nextInt(3), LOTRFoods.RANGER_DRINK);
        this.placeChest(world, random, 0, 1, 2, 2, super.chestContentsHouse);
        this.setBlockAndMetadata(world, 1, 1, 2, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 1, 2, 2, 2, LOTRFoods.RANGER_DRINK);
        for (final int k2 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, 3, 1, k2, super.bedBlock, 1);
            this.setBlockAndMetadata(world, 4, 1, k2, super.bedBlock, 9);
            this.setBlockAndMetadata(world, 5, 1, k2, super.plankBlock, super.plankMeta);
            for (int j2 = 2; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, 5, j2, k2, super.fenceBlock, super.fenceMeta);
            }
        }
        this.setBlockAndMetadata(world, 6, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 6, 1, 0, (Block)Blocks.fire, 0);
        for (int j5 = 2; j5 <= 3; ++j5) {
            this.setAir(world, 6, j5, 0);
        }
        this.setBlockAndMetadata(world, 5, 1, 0, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 5, 2, 0, Blocks.furnace, 5);
        this.spawnItemFrame(world, 5, 3, 0, 3, this.getRangerFramedItem(random));
        int gateX = 0;
        int gateZ = 0;
        int gateMeta = -1;
        int i5 = -5;
        while (true) {
            while (i5 <= -5) {
                int k7 = -4;
                if (this.isValidGatePos(world, i5, 1, k7)) {
                    gateX = i5;
                    gateZ = k7 + 1;
                    gateMeta = 0;
                }
                else {
                    k7 = 4;
                    if (!this.isValidGatePos(world, i5, 1, k7)) {
                        ++i5;
                        continue;
                    }
                    gateX = i5;
                    gateZ = k7 - 1;
                    gateMeta = 2;
                }
                if (gateMeta != -1) {
                    for (i5 = -6; i5 <= -3; ++i5) {
                        for (k7 = -3; k7 <= 3; ++k7) {
                            final int k8 = Math.abs(k7);
                            for (int j6 = 1; j6 <= 3; ++j6) {
                                this.setAir(world, i5, j6, k7);
                            }
                            for (int j6 = 0; (j6 >= 0 || !this.isOpaque(world, i5, j6, k7)) && this.getY(j6) >= 0; --j6) {
                                if (j6 == 0) {
                                    this.setBlockAndMetadata(world, i5, j6, k7, (Block)Blocks.grass, 0);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i5, j6, k7, Blocks.dirt, 0);
                                }
                                this.setGrassToDirt(world, i5, j6 - 1, k7);
                            }
                            if (i5 == -6 || k8 == 3) {
                                this.setBlockAndMetadata(world, i5, 1, k7, super.fenceBlock, super.fenceMeta);
                            }
                        }
                    }
                    this.setBlockAndMetadata(world, gateX, 1, gateZ, super.fenceGateBlock, gateMeta);
                    this.setBlockAndMetadata(world, gateX, 0, gateZ, LOTRMod.dirtPath, 0);
                    for (int k2 = -2; k2 <= 2; ++k2) {
                        this.setBlockAndMetadata(world, -5, 0, k2, LOTRMod.dirtPath, 0);
                        for (int i4 = -4; i4 <= -3; ++i4) {
                            if (k2 != 0 || i4 != -3) {
                                this.setBlockAndMetadata(world, i4, 0, k2, Blocks.farmland, 7);
                                this.setBlockAndMetadata(world, i4, 1, k2, super.cropBlock, super.cropMeta);
                            }
                        }
                    }
                    this.setBlockAndMetadata(world, -3, -1, 0, Blocks.dirt, 0);
                    this.setGrassToDirt(world, -3, -2, 0);
                    this.setBlockAndMetadata(world, -3, 0, 0, Blocks.water, 0);
                    this.setBlockAndMetadata(world, -3, 1, 0, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, -3, 2, 0, Blocks.torch, 1);
                    this.setBlockAndMetadata(world, -6, 2, 0, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, -6, 3, 0, Blocks.hay_block, 0);
                    this.setBlockAndMetadata(world, -6, 4, 0, Blocks.pumpkin, 3);
                }
                for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
                    final LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
                    this.spawnNPCAndSetHome(dunedain, world, 2, 1, 0, 16);
                }
                return true;
            }
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i4 = -7;
                if (this.isValidGatePos(world, i4, 1, k2)) {
                    gateX = i4 + 1;
                    gateZ = k2;
                    gateMeta = 3;
                    break;
                }
            }
            continue;
        }
    }
    
    private boolean isValidGatePos(final World world, final int i, final int j, final int k) {
        return this.isOpaque(world, i, j - 1, k) && this.isAir(world, i, j, k) && this.isAir(world, i, j + 1, k);
    }
}
