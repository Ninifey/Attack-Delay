// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import net.minecraft.block.Block;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingSmithy extends LOTRWorldGenEasterlingStructureTown
{
    public LOTRWorldGenEasterlingSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -7; k2 <= 7; ++k2) {
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
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 == 6 && k4 % 4 == 2) || (k4 == 6 && i4 % 4 == 2)) {
                    for (int j2 = 4; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if (i4 == 6 || k4 == 6) {
                    for (int j2 = 3; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (k4 == 6) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                    }
                    else if (i4 == 6) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.woodBeamBlock, super.woodBeamMeta | 0x8);
                    }
                }
                else {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickRedBlock, super.brickRedMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 9; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                    if (IntMath.mod(i3, 2) == 1 && IntMath.mod(k3, 2) == 1) {
                        this.setBlockAndMetadata(world, i3, 0, k3, super.pillarRedBlock, super.pillarRedMeta);
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 0, 2, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        }
        for (int k5 = -5; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, -2, 0, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
            this.setBlockAndMetadata(world, 2, 0, k5, super.woodBeamBlock, super.woodBeamMeta | 0x8);
        }
        this.setBlockAndMetadata(world, -4, 2, -6, LOTRMod.reedBars, 0);
        this.setBlockAndMetadata(world, 4, 2, -6, LOTRMod.reedBars, 0);
        for (final int k2 : new int[] { -4, 0 }) {
            this.setBlockAndMetadata(world, -6, 2, k2 - 1, super.brickStairBlock, 7);
            this.setAir(world, -6, 2, k2);
            this.setBlockAndMetadata(world, -6, 2, k2 + 1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -6, 3, k2, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 2, k2 - 1, super.brickStairBlock, 7);
            this.setAir(world, 6, 2, k2);
            this.setBlockAndMetadata(world, 6, 2, k2 + 1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, 6, 3, k2, super.brickStairBlock, 4);
        }
        for (final int k2 : new int[] { -7, 7 }) {
            this.setBlockAndMetadata(world, -6, 3, k2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 6, 3, k2, super.fenceBlock, super.fenceMeta);
        }
        for (final int i5 : new int[] { -7, 7 }) {
            this.setBlockAndMetadata(world, i5, 3, -6, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i5, 3, 6, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -2, 3, -7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, -7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 3, 7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, 7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -7, 3, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -7, 3, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 7, 3, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 7, 3, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 0, -6, super.woodBeamBlock, super.woodBeamMeta | 0x4);
        this.setBlockAndMetadata(world, 0, 1, -6, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -6, super.doorBlock, 8);
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 == 7 && k4 == 7) || (i4 == 7 && (k4 == 0 || k4 == 4)) || (k4 == 7 && (i4 == 0 || i4 == 4))) {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.roofSlabBlock, super.roofSlabMeta);
                }
                if (k4 == 7) {
                    if (i3 == -6 || i3 == -3 || i3 == 1 || i3 == 5) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.roofStairBlock, 5);
                    }
                    if (i3 == -5 || i3 == -1 || i3 == 3 || i3 == 6) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.roofStairBlock, 4);
                    }
                    if (i4 == 2) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.roofStairBlock, (k3 < 0) ? 2 : 3);
                    }
                }
                if (i4 == 7) {
                    if (k3 == -6 || k3 == -3 || k3 == 1 || k3 == 5) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.roofStairBlock, 6);
                    }
                    if (k3 == -5 || k3 == -1 || k3 == 3 || k3 == 6) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.roofStairBlock, 7);
                    }
                    if (k4 == 2) {
                        this.setBlockAndMetadata(world, i3, 4, k3, super.roofStairBlock, (i3 < 0) ? 1 : 0);
                    }
                }
            }
        }
        for (int step = 0; step <= 1; ++step) {
            final int j3 = 5 + step;
            for (int i2 = -6 + step; i2 <= 6 - step; ++i2) {
                this.setBlockAndMetadata(world, i2, j3, -6 + step, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i2, j3, 6 - step, super.roofStairBlock, 3);
            }
            for (int k6 = -6 + step; k6 <= 6 - step; ++k6) {
                this.setBlockAndMetadata(world, -6 + step, j3, k6, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 6 - step, j3, k6, super.roofStairBlock, 0);
            }
            for (int i2 = -5 + step; i2 <= 5 - step; ++i2) {
                this.setBlockAndMetadata(world, i2, j3, -5 + step, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i2, j3, 5 - step, super.roofStairBlock, 6);
            }
            for (int k6 = -5 + step; k6 <= 5 - step; ++k6) {
                this.setBlockAndMetadata(world, -5 + step, j3, k6, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 5 - step, j3, k6, super.roofStairBlock, 5);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 <= 1 && k4 >= 3) || (k4 <= 1 && i4 >= 3) || (i4 >= 2 && k4 >= 2)) {
                    this.setBlockAndMetadata(world, i3, 6, k3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                }
                if (i4 == 2 && k4 == 2) {
                    this.setBlockAndMetadata(world, i3, 6, k3, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i3, 5, k3, LOTRMod.chandelier, 0);
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            final int i6 = Math.abs(i3);
            if (i6 >= 2) {
                this.setBlockAndMetadata(world, i3, 7, -2, super.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i3, 7, 2, super.roofStairBlock, 3);
            }
            if (i6 >= 1) {
                this.setBlockAndMetadata(world, i3, 8, -1, super.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i3, 8, 1, super.roofStairBlock, 6);
            }
            if (i6 >= 0) {
                this.setBlockAndMetadata(world, i3, 9, 0, super.roofSlabBlock, super.roofSlabMeta);
            }
        }
        for (int k5 = -4; k5 <= 4; ++k5) {
            final int k7 = Math.abs(k5);
            if (k7 >= 2) {
                this.setBlockAndMetadata(world, -2, 7, k5, super.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 2, 7, k5, super.roofStairBlock, 0);
            }
            if (k7 >= 1) {
                this.setBlockAndMetadata(world, -1, 8, k5, super.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 1, 8, k5, super.roofStairBlock, 5);
            }
            if (k7 >= 0) {
                this.setBlockAndMetadata(world, 0, 9, k5, super.roofSlabBlock, super.roofSlabMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 9, -4, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 9, 4, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 9, 0, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 9, 0, super.roofStairBlock, 1);
        for (final int k2 : new int[] { -3, 3 }) {
            this.setBlockAndMetadata(world, -1, 7, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 0, 7, k2, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 1, 7, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, -1, 8, k2, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, 0, 8, k2, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 1, 8, k2, super.roofBlock, super.roofMeta);
        }
        for (final int i5 : new int[] { -3, 3 }) {
            this.setBlockAndMetadata(world, i5, 7, -1, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, i5, 7, 0, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i5, 7, 1, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, i5, 8, -1, super.roofBlock, super.roofMeta);
            this.setBlockAndMetadata(world, i5, 8, 0, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, i5, 8, 1, super.roofBlock, super.roofMeta);
        }
        this.setBlockAndMetadata(world, 0, 4, -7, super.plankBlock, super.plankMeta);
        this.spawnItemFrame(world, 0, 4, -7, 2, new ItemStack(LOTRMod.blacksmithHammer));
        this.setBlockAndMetadata(world, -2, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 5, 3, -2, Blocks.torch, 1);
        for (int i3 = -5; i3 <= 5; ++i3) {
            final int i6 = Math.abs(i3);
            if (i6 == 2) {
                this.setBlockAndMetadata(world, i3, 1, -2, super.woodBeamBlock, super.woodBeamMeta);
                this.setBlockAndMetadata(world, i3, 2, -2, super.plankSlabBlock, super.plankSlabMeta);
            }
            else if (i3 == 4) {
                this.setBlockAndMetadata(world, i3, 1, -2, super.fenceGateBlock, 0);
            }
            else {
                this.setBlockAndMetadata(world, i3, 1, -2, super.fenceBlock, super.fenceMeta);
            }
        }
        this.setBlockAndMetadata(world, 3, 1, 5, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 4, 1, 5, super.tableBlock, 0);
        this.setBlockAndMetadata(world, 5, 1, 5, super.woodBeamBlock, super.woodBeamMeta);
        for (int k5 = 3; k5 <= 4; ++k5) {
            this.placeChest(world, random, 5, 1, k5, 5, LOTRChestContents.EASTERLING_SMITHY);
        }
        this.placeWeaponRack(world, 4, 3, 5, 6, random.nextBoolean() ? null : this.getEasterlingWeaponItem(random));
        this.placeWeaponRack(world, 5, 3, 4, 7, random.nextBoolean() ? null : this.getEasterlingWeaponItem(random));
        this.placeArmorStand(world, 5, 1, 0, 1, null);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = 4; k3 <= 6; ++k3) {
                for (int j4 = 1; j4 <= 6; ++j4) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (int j5 = 1; j5 <= 5; ++j5) {
            this.setAir(world, 0, j5, 5);
        }
        this.setBlockAndMetadata(world, 0, 0, 5, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 1, 5, (Block)Blocks.fire, 0);
        this.setBlockAndMetadata(world, 0, 0, 4, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 1, 4, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 3, 4, super.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 5, 4, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -1, 6, 6, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 6, 6, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 7, 6, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 8, 6, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 9, 6, Blocks.flower_pot, 0);
        for (int i3 = -3; i3 <= -2; ++i3) {
            for (int j3 = 1; j3 <= 2; ++j3) {
                this.setBlockAndMetadata(world, i3, j3, 5, super.brickBlock, super.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, -3, 1, 4, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 1, 3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -3, 2, 3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -5, 1, 3, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -4, 1, 3, LOTRMod.alloyForge, 2);
        this.setBlockAndMetadata(world, -5, 2, 3, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -4, 2, 3, super.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 2, 4, super.barsBlock, 0);
        for (int i3 = -5; i3 <= -4; ++i3) {
            for (int k3 = 4; k3 <= 5; ++k3) {
                this.setBlockAndMetadata(world, i3, 3, k3, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i3, 1, k3, Blocks.lava, 0);
            }
        }
        this.setBlockAndMetadata(world, -5, 3, 3, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 3, 3, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 3, 3, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 3, 4, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -3, 3, 5, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 0, Blocks.anvil, 1);
        this.setBlockAndMetadata(world, -5, 1, 0, (Block)Blocks.cauldron, 3);
        final LOTREntityEasterling blacksmith = new LOTREntityEasterlingBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 0, 16);
        return true;
    }
}
