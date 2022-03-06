// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDaleBlacksmith;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDaleSmithy extends LOTRWorldGenDaleStructure
{
    public LOTRWorldGenDaleSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -2; k2 <= 12; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (block != Blocks.grass) {
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
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = 0; k3 <= 10; ++k3) {
                for (int j3 = 0; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 10; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -1; k3 <= 11; ++k3) {
                final int i4 = Math.abs(i3);
                if ((i4 == 4 && IntMath.mod(k3, 4) == 3) || (i3 == 0 && k3 == 11)) {
                    for (int j4 = 3; (j4 >= 1 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j4 - 1, k3);
                    }
                }
                if (i4 == 4 && IntMath.mod(k3, 4) == 0) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickStairBlock, 7);
                }
                if (i4 == 4 && IntMath.mod(k3, 4) == 2) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickStairBlock, 6);
                }
                if (k3 == 11 && IntMath.mod(i3, 4) == 1) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickStairBlock, 4);
                }
                if (k3 == 11 && IntMath.mod(i3, 4) == 3) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickStairBlock, 5);
                }
                if (k3 == -1 && i3 == -3) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickStairBlock, 4);
                }
                if (k3 == -1 && i3 == 3) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.brickStairBlock, 5);
                }
                if (i4 == 1 && k3 == -1) {
                    for (int j4 = 3; (j4 >= 1 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i3, j4, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j4 - 1, k3);
                    }
                }
                if (i4 == 0 && k3 == -1) {
                    for (int j4 = 0; (j4 == 0 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i3, j4, k3, super.floorBlock, super.floorMeta);
                        this.setGrassToDirt(world, i3, j4 - 1, k3);
                    }
                    for (int j4 = 1; j4 <= 3; ++j4) {
                        this.setAir(world, i3, j4, k3);
                    }
                }
                if (i4 <= 2 && k3 >= 1 && k3 <= 9) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                }
                if (i4 <= 3 && k3 >= 0 && k3 <= 10) {
                    if (i4 == 3 || k3 == 0 || k3 == 10) {
                        for (int j4 = 1; j4 <= 4; ++j4) {
                            this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    this.setBlockAndMetadata(world, i3, 4, k3, super.brickBlock, super.brickMeta);
                }
                if (i4 == 4 || k3 == -1 || k3 == 11) {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.brickBlock, super.brickMeta);
                    for (int j4 = 5; j4 <= 6; ++j4) {
                        this.setBlockAndMetadata(world, i3, j4, k3, super.plankBlock, super.plankMeta);
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -2, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i3, 4, 12, super.brickStairBlock, 7);
            if (IntMath.mod(i3, 2) == 1) {
                this.setBlockAndMetadata(world, i3, 5, -2, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, i3, 5, 12, super.brickWallBlock, super.brickWallMeta);
            }
        }
        for (int k4 = -1; k4 <= 11; ++k4) {
            this.setBlockAndMetadata(world, -5, 4, k4, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 5, 4, k4, super.brickStairBlock, 4);
            if (IntMath.mod(k4, 2) == 0) {
                this.setBlockAndMetadata(world, -5, 5, k4, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, 5, 5, k4, super.brickWallBlock, super.brickWallMeta);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, -1, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 7, 11, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (int k4 = 0; k4 <= 10; ++k4) {
            this.setBlockAndMetadata(world, -4, 7, k4, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            this.setBlockAndMetadata(world, 4, 7, k4, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            if (IntMath.mod(i3, 4) == 0) {
                for (int j5 = 5; j5 <= 7; ++j5) {
                    this.setBlockAndMetadata(world, i3, j5, -1, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i3, j5, 11, super.brickBlock, super.brickMeta);
                }
                this.setBlockAndMetadata(world, i3, 7, -2, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i3, 7, 12, super.brickStairBlock, 7);
            }
            else if (IntMath.mod(i3, 4) == 2) {
                this.setBlockAndMetadata(world, i3, 6, -1, super.barsBlock, 0);
                this.setBlockAndMetadata(world, i3, 6, 11, super.barsBlock, 0);
            }
        }
        for (int k4 = -1; k4 <= 11; ++k4) {
            if (IntMath.mod(k4, 4) == 3) {
                for (int j5 = 5; j5 <= 7; ++j5) {
                    this.setBlockAndMetadata(world, -4, j5, k4, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, 4, j5, k4, super.brickBlock, super.brickMeta);
                }
                this.setBlockAndMetadata(world, -5, 7, k4, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, 5, 7, k4, super.brickStairBlock, 4);
            }
            else if (IntMath.mod(k4, 4) == 1) {
                this.setBlockAndMetadata(world, -4, 6, k4, super.barsBlock, 0);
                this.setBlockAndMetadata(world, 4, 6, k4, super.barsBlock, 0);
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -2; k3 <= 12; ++k3) {
                if (i3 <= -4 || i3 >= 4 || k3 <= -1 || k3 >= 11) {
                    this.setBlockAndMetadata(world, i3, 8, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -1; k3 <= 11; ++k3) {
                if (i3 == -4 || i3 == 4 || k3 == -1 || k3 == 11) {
                    this.setBlockAndMetadata(world, i3, 9, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = 0; k3 <= 10; ++k3) {
                if (i3 == -3 || i3 == 3 || k3 == 0 || k3 == 10) {
                    this.setBlockAndMetadata(world, i3, 8, k3, super.plankBlock, super.plankMeta);
                }
                if (k3 == 3 || k3 == 7) {
                    this.setBlockAndMetadata(world, i3, 8, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                }
                else if (i3 == 0) {
                    this.setBlockAndMetadata(world, i3, 8, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                }
                this.setBlockAndMetadata(world, i3, 10, k3, super.brickBlock, super.brickMeta);
            }
            this.setBlockAndMetadata(world, i3, 10, -1, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, i3, 10, 11, super.plankBlock, super.plankMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, -2, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 10, -2, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 9, 12, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 10, 12, super.brickStairBlock, 7);
        for (int k4 = -2; k4 <= 12; ++k4) {
            this.setBlockAndMetadata(world, -5, 9, k4, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 10, k4, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 11, k4, super.roofStairBlock, 1);
            for (int i5 = -2; i5 <= 2; ++i5) {
                this.setBlockAndMetadata(world, i5, 11, k4, super.roofBlock, super.roofMeta);
            }
            this.setBlockAndMetadata(world, 3, 11, k4, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 10, k4, super.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 5, 9, k4, super.roofStairBlock, 0);
            if (k4 == -2 || k4 == 12) {
                this.setBlockAndMetadata(world, -4, 9, k4, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, -3, 10, k4, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 3, 10, k4, super.roofStairBlock, 5);
                this.setBlockAndMetadata(world, 4, 9, k4, super.roofStairBlock, 5);
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 7; k3 <= 9; ++k3) {
                for (int j3 = 1; j3 <= 12; ++j3) {
                    if (i3 == 0 && k3 == 8) {
                        this.setAir(world, i3, j3, k3);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                    }
                }
                if (Math.abs(i3) == 1 && (k3 == 7 || k3 == 9)) {
                    this.setBlockAndMetadata(world, i3, 13, k3, super.brickSlabBlock, super.brickSlabMeta);
                }
                else if (Math.abs(i3) == 1 || k3 == 7 || k3 == 9) {
                    this.setBlockAndMetadata(world, i3, 13, k3, super.brickBlock, super.brickMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 13, k3, super.barsBlock, 0);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, super.floorBlock, super.floorMeta);
        this.setBlockAndMetadata(world, 0, 1, 0, super.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, 0, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, 0, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 3);
        for (final int i6 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i6, 1, 1, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, i6, 2, 1, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, i6, 3, 1, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i6, 3, 2, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i6, 3, 3, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i6, 1, 4, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, i6, 2, 4, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, i6, 3, 4, super.pillarBlock, super.pillarMeta);
        }
        this.setBlockAndMetadata(world, -1, 3, 1, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 3, 1, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -2, 1, 2, LOTRMod.unsmeltery, 4);
        this.setBlockAndMetadata(world, -2, 1, 3, LOTRMod.alloyForge, 4);
        this.setBlockAndMetadata(world, -2, 2, 3, Blocks.furnace, 4);
        this.setBlockAndMetadata(world, 2, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.daleTable, 0);
        for (int l = 0; l <= 3; ++l) {
            final int k3 = 6 + l;
            final int j3 = 1 + l;
            for (final int i7 : new int[] { -2, 2 }) {
                this.setAir(world, i7, 4, k3);
                this.setBlockAndMetadata(world, i7, j3, k3, super.brickStairBlock, 2);
                for (int j6 = j3 - 1; j6 >= 1; --j6) {
                    this.setBlockAndMetadata(world, i7, j6, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 1, 6, Blocks.anvil, 3);
        this.setBlockAndMetadata(world, -1, 1, 7, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 1, 7, Blocks.stone_brick_stairs, 2);
        this.setBlockAndMetadata(world, 1, 1, 7, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -1, 2, 7, LOTRMod.wallStoneV, 1);
        this.setAir(world, 0, 2, 7);
        this.setBlockAndMetadata(world, 1, 2, 7, LOTRMod.wallStoneV, 1);
        this.setBlockAndMetadata(world, 0, 3, 7, super.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -1, 1, 8, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 1, 8, Blocks.lava, 0);
        this.setBlockAndMetadata(world, 1, 1, 8, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -2, 2, 8, Blocks.stonebrick, 0);
        this.setAir(world, -1, 2, 8);
        this.setAir(world, 1, 2, 8);
        this.setBlockAndMetadata(world, 2, 2, 8, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 1, 9, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -1, 2, 9, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 2, 9, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 1, 2, 9, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 7, 0, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 7, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 7, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -3, 7, 7, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 7, 7, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 7, 10, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, 6, Blocks.crafting_table, 0);
        this.placeChest(world, random, -1, 5, 6, 2, LOTRChestContents.DALE_HOUSE);
        this.placeChest(world, random, 1, 5, 6, 2, LOTRChestContents.DALE_HOUSE);
        for (int i3 = -2; i3 <= 0; ++i3) {
            for (int k3 = 1; k3 <= 3; ++k3) {
                if ((i3 == -2 || i3 == 0) && (k3 == 1 || k3 == 3)) {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.plankBlock, super.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                if (random.nextBoolean()) {
                    this.placePlate(world, random, i3, 6, k3, super.plateBlock, LOTRFoods.DALE);
                }
                else {
                    final int mugMeta = random.nextInt(4);
                    this.placeMug(world, random, i3, 6, k3, mugMeta, LOTRFoods.DALE_DRINK);
                }
            }
        }
        for (final int i6 : new int[] { 2, 3 }) {
            this.setBlockAndMetadata(world, i6, 5, 1, LOTRMod.strawBed, 2);
            this.setBlockAndMetadata(world, i6, 5, 0, LOTRMod.strawBed, 10);
        }
        final LOTREntityDaleBlacksmith blacksmith = new LOTREntityDaleBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 5, 16);
        return true;
    }
}
