// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanSmithy extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -10; i2 <= 5; ++i2) {
                for (int k2 = -3; k2 <= 4; ++k2) {
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
        for (int i3 = -10; i3 <= 5; ++i3) {
            for (int k3 = -3; k3 <= 4; ++k3) {
                for (int j3 = 2; j3 <= 8; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                final boolean corner = (i3 == -10 || i3 == 5) && (k3 == -3 || k3 == 4);
                if (corner) {
                    for (int j4 = 1; (j4 >= 1 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i3, j4, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                        this.setGrassToDirt(world, i3, j4 - 1, k3);
                    }
                    this.setBlockAndMetadata(world, i3, 2, k3, super.rockSlabBlock, super.rockSlabMeta);
                }
                else {
                    for (int j4 = 1; (j4 >= 1 || !this.isOpaque(world, i3, j4, k3)) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j4 - 1, k3);
                    }
                }
            }
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -3, super.brickStairBlock, 2);
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            final int i4 = Math.abs(i3);
            if (i4 == 2) {
                for (int j3 = 1; j3 <= 2; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, -3, super.logBlock, super.logMeta);
                }
                for (int j3 = 3; j3 <= 4; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, -3, super.fenceBlock, super.fenceMeta);
                }
            }
            if (i4 == 3) {
                for (int j3 = 2; j3 <= 4; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, -2, super.plankBlock, super.plankMeta);
                }
            }
            if (i4 == 4) {
                for (final int k4 : new int[] { -2, 3 }) {
                    this.setBlockAndMetadata(world, i3, 2, k4, super.logBlock, super.logMeta);
                    for (int j5 = 3; j5 <= 4; ++j5) {
                        this.setBlockAndMetadata(world, i3, j5, k4, super.fenceBlock, super.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 5, k4, super.plank2Block, super.plank2Meta);
                }
                for (int k5 = -1; k5 <= 2; ++k5) {
                    this.setBlockAndMetadata(world, i3, 5, k5, super.roofSlabBlock, super.roofSlabMeta);
                }
            }
            if (i3 == 4) {
                for (int k5 = -1; k5 <= 2; ++k5) {
                    for (int j4 = 2; j4 <= 4; ++j4) {
                        if (k5 >= 0 && k5 <= 1 && j4 >= 3) {
                            this.setBlockAndMetadata(world, i3, j4, k5, super.barsBlock, 0);
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, j4, k5, super.plankBlock, super.plankMeta);
                        }
                    }
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            final int i4 = Math.abs(i3);
            this.setBlockAndMetadata(world, i3, 5, 3, super.roofSlabBlock, super.roofSlabMeta);
            for (int k5 = -2; k5 <= 2; ++k5) {
                if (i4 == 3 && k5 == -2) {
                    this.setBlockAndMetadata(world, i3, 5, k5, super.roofSlabBlock, super.roofSlabMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 5, k5, super.roofBlock, super.roofMeta);
                }
            }
            if (i4 <= 2) {
                for (int k5 = -2; k5 <= 2; ++k5) {
                    boolean slab = false;
                    if (i4 == 0 && k5 == -2) {
                        slab = true;
                    }
                    if (i4 == 1 && (k5 == -1 || k5 == 2)) {
                        slab = true;
                    }
                    if (i4 == 2 && k5 >= 0 && k5 <= 1) {
                        slab = true;
                    }
                    if (slab) {
                        this.setBlockAndMetadata(world, i3, 6, k5, super.roofSlabBlock, super.roofSlabMeta);
                    }
                    boolean full = false;
                    if (i4 == 0 && k5 >= -1 && k5 <= 2) {
                        full = true;
                    }
                    if (i4 == 1 && k5 >= 0 && k5 <= 1) {
                        full = true;
                    }
                    if (full) {
                        this.setBlockAndMetadata(world, i3, 6, k5, super.roofBlock, super.roofMeta);
                    }
                    slab = false;
                    if (i4 == 0 && k5 >= 0 && k5 <= 1) {
                        slab = true;
                    }
                    if (slab) {
                        this.setBlockAndMetadata(world, i3, 7, k5, super.roofSlabBlock, super.roofSlabMeta);
                    }
                }
            }
        }
        for (int k6 = -3; k6 <= 2; ++k6) {
            this.setBlockAndMetadata(world, 0, 5, k6, super.logBlock, super.logMeta | 0x8);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            final int i4 = Math.abs(i3);
            if (i4 == 0) {
                this.setBlockAndMetadata(world, i3, 6, -3, super.plank2SlabBlock, super.plank2SlabMeta);
            }
            if (i4 == 1) {
                this.setBlockAndMetadata(world, i3, 5, -3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            }
            if (i4 == 2) {
                this.setBlockAndMetadata(world, i3, 5, -3, super.plank2SlabBlock, super.plank2SlabMeta);
            }
            if (i4 == 3) {
                this.setBlockAndMetadata(world, i3, 4, -3, super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            final int i4 = Math.abs(i3);
            if (i4 <= 1) {
                for (int j3 = 2; j3 <= 6; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, 3, super.brickBlock, super.brickMeta);
                }
            }
            if (i4 == 2) {
                this.setBlockAndMetadata(world, i3, 2, 3, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i3, 4, 3, super.fenceBlock, super.fenceMeta);
            }
            if (i4 == 3) {
                for (int j3 = 2; j3 <= 4; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, 3, super.brickBlock, super.brickMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -1, 7, 3, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 7, 3, super.brickStairBlock, 0);
        for (int j6 = 7; j6 <= 9; ++j6) {
            this.setBlockAndMetadata(world, 0, j6, 3, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 10, 3, Blocks.flower_pot, 0);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int j7 = 2; j7 <= 6; ++j7) {
                this.setBlockAndMetadata(world, i3, j7, 4, super.brickBlock, super.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 3, 4, super.brickCarvedBlock, super.brickCarvedMeta);
        this.setBlockAndMetadata(world, -1, 6, 4, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 6, 4, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 7, 4, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 2, 3, (Block)Blocks.fire, 0);
        this.setAir(world, 0, 3, 3);
        this.setBlockAndMetadata(world, 0, 4, 2, Blocks.torch, 4);
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, 2, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i3, 6, 2, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 7, 2, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 2, 2, LOTRMod.alloyForge, 2);
        this.setBlockAndMetadata(world, 0, 2, 2, LOTRMod.unsmeltery, 2);
        this.setBlockAndMetadata(world, 1, 2, 2, Blocks.furnace, 2);
        for (int j6 = 3; j6 <= 4; ++j6) {
            this.setBlockAndMetadata(world, -1, j6, 2, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, 1, j6, 2, super.brickWallBlock, super.brickWallMeta);
        }
        this.setBlockAndMetadata(world, 2, 2, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 2, 2, super.logBlock, super.logMeta);
        this.setBlockAndMetadata(world, 3, 2, 1, super.tableBlock, 0);
        for (int j6 = 3; j6 <= 4; ++j6) {
            final ItemStack weapon = random.nextBoolean() ? this.getRandomRohanWeapon(random) : null;
            this.placeWeaponRack(world, 3, j6, 2, 6, weapon);
        }
        this.placeArmorStand(world, 3, 2, -1, 1, this.getRohanArmourItems());
        this.setBlockAndMetadata(world, -7, 2, 0, Blocks.anvil, 1);
        this.setBlockAndMetadata(world, -8, 2, 3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.setBlockAndMetadata(world, -7, 2, 3, (Block)Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -6, 2, 3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        final LOTREntityRohanMan blacksmith = new LOTREntityRohanBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 2, 0, 8);
        return true;
    }
}
