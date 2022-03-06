// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityAngmarHillman;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityAngmarHillmanChieftain;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public class LOTRWorldGenAngmarHillmanChieftainHouse extends LOTRWorldGenStructureBase2
{
    private Block woodBlock;
    private int woodMeta;
    private Block plankBlock;
    private int plankMeta;
    private Block slabBlock;
    private int slabMeta;
    private Block stairBlock;
    private Block fenceBlock;
    private int fenceMeta;
    private Block doorBlock;
    private Block floorBlock;
    private int floorMeta;
    
    public LOTRWorldGenAngmarHillmanChieftainHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -6; k2 <= 6; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass && block != Blocks.stone) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 4) {
                        return false;
                    }
                }
            }
        }
        this.woodBlock = Blocks.log;
        this.woodMeta = 1;
        this.plankBlock = Blocks.planks;
        this.plankMeta = 1;
        this.slabBlock = (Block)Blocks.wooden_slab;
        this.slabMeta = 1;
        this.stairBlock = Blocks.spruce_stairs;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 0;
        this.doorBlock = LOTRMod.doorSpruce;
        this.floorBlock = Blocks.stained_hardened_clay;
        this.floorMeta = 15;
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                for (int j3 = 1; j3 <= 10; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                for (int j3 = 0; (j3 == 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    if (this.getBlock(world, i3, j3 + 1, k3).isOpaqueCube()) {
                        this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)Blocks.grass, 0);
                    }
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -5; k3 <= 5; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, this.floorBlock, this.floorMeta);
                if (random.nextInt(2) == 0) {
                    this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                }
            }
        }
        for (final int i4 : new int[] { -4, 4 }) {
            for (int k4 = -4; k4 <= 4; ++k4) {
                this.setBlockAndMetadata(world, i4, 1, k4, this.woodBlock, this.woodMeta | 0x8);
                this.setBlockAndMetadata(world, i4, 4, k4, this.woodBlock, this.woodMeta | 0x8);
            }
            for (int j2 = 1; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i4, j2, -5, this.woodBlock, this.woodMeta);
                this.setBlockAndMetadata(world, i4, j2, 0, this.woodBlock, this.woodMeta);
                this.setBlockAndMetadata(world, i4, j2, 5, this.woodBlock, this.woodMeta);
            }
        }
        for (final int i4 : new int[] { -3, 3 }) {
            for (int k4 = -4; k4 <= 4; ++k4) {
                this.setBlockAndMetadata(world, i4, 1, k4, this.plankBlock, this.plankMeta);
            }
            for (int j2 = 2; j2 <= 3; ++j2) {
                this.setBlockAndMetadata(world, i4, j2, -4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i4, j2, -1, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i4, j2, 1, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i4, j2, 4, this.plankBlock, this.plankMeta);
            }
            this.setBlockAndMetadata(world, i4, 3, -3, this.stairBlock, 7);
            this.setBlockAndMetadata(world, i4, 3, -2, this.stairBlock, 6);
            this.setBlockAndMetadata(world, i4, 3, 2, this.stairBlock, 7);
            this.setBlockAndMetadata(world, i4, 3, 3, this.stairBlock, 6);
            for (int j2 = 1; j2 <= 5; ++j2) {
                this.setBlockAndMetadata(world, i4, j2, 0, this.woodBlock, this.woodMeta);
            }
            this.setBlockAndMetadata(world, i4, 1, -5, this.woodBlock, this.woodMeta | 0x4);
            this.setBlockAndMetadata(world, i4, 2, -5, this.stairBlock, 2);
            this.setBlockAndMetadata(world, i4, 3, -5, this.stairBlock, 6);
            this.setBlockAndMetadata(world, i4, 4, -5, this.slabBlock, this.slabMeta);
        }
        for (final int i4 : new int[] { -2, 2 }) {
            for (int j2 = 1; j2 <= 3; ++j2) {
                this.setBlockAndMetadata(world, i4, j2, -4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i4, j2, -5, this.woodBlock, this.woodMeta);
            }
            this.setBlockAndMetadata(world, i4, 4, -5, this.slabBlock, this.slabMeta);
            this.setBlockAndMetadata(world, i4, 2, -6, Blocks.torch, 4);
            this.setBlockAndMetadata(world, i4, 3, -6, Blocks.skull, 2);
        }
        for (int j4 = 1; j4 <= 3; ++j4) {
            this.setBlockAndMetadata(world, -1, j4, -4, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 1, j4, -4, this.woodBlock, this.woodMeta);
        }
        this.setBlockAndMetadata(world, -1, 2, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 2, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 3, -5, this.stairBlock, 4);
        this.setBlockAndMetadata(world, -1, 4, -5, this.stairBlock, 1);
        this.setBlockAndMetadata(world, 1, 3, -5, this.stairBlock, 5);
        this.setBlockAndMetadata(world, 1, 4, -5, this.stairBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 3, -5, this.slabBlock, this.slabMeta | 0x8);
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -4, this.woodBlock, this.woodMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 5, -5, this.stairBlock, 6);
        }
        this.setBlockAndMetadata(world, -2, 5, -4, Blocks.skull, 3);
        this.setBlockAndMetadata(world, 2, 5, -4, Blocks.skull, 3);
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -5, this.woodBlock, this.woodMeta | 0x4);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 7, -5, this.woodBlock, this.woodMeta | 0x4);
        }
        for (int j4 = 4; j4 <= 9; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, -5, this.woodBlock, this.woodMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, -4, this.stairBlock, 7);
        this.setBlockAndMetadata(world, 0, 6, -6, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, -4, Blocks.torch, 3);
        this.placeWallBanner(world, 0, 5, -5, LOTRItemBanner.BannerType.ANGMAR, 2);
        this.placeWallBanner(world, -2, 5, -5, LOTRItemBanner.BannerType.RHUDAUR, 2);
        this.placeWallBanner(world, 2, 5, -5, LOTRItemBanner.BannerType.RHUDAUR, 2);
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, 5, this.woodBlock, this.woodMeta | 0x4);
            this.setBlockAndMetadata(world, i3, 2, 5, this.stairBlock, 3);
            this.setBlockAndMetadata(world, i3, 3, 5, this.stairBlock, 7);
            this.setBlockAndMetadata(world, i3, 4, 5, this.woodBlock, this.woodMeta | 0x4);
        }
        this.setBlockAndMetadata(world, -3, 5, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -2, 5, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -1, 5, 5, this.slabBlock, this.slabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 5, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 5, 5, this.slabBlock, this.slabMeta | 0x8);
        this.setBlockAndMetadata(world, 2, 5, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 3, 5, 5, this.plankBlock, this.plankMeta);
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int j5 = 6; j5 <= 7; ++j5) {
                this.setBlockAndMetadata(world, i3, j5, 5, this.plankBlock, this.plankMeta);
            }
        }
        for (final int i4 : new int[] { -2, 2 }) {
            for (int j2 = 1; j2 <= 4; ++j2) {
                this.setBlockAndMetadata(world, i4, j2, 4, this.plankBlock, this.plankMeta);
            }
            this.setBlockAndMetadata(world, i4, 5, 4, this.fenceBlock, this.fenceMeta);
        }
        for (int j4 = 4; j4 <= 5; ++j4) {
            this.setBlockAndMetadata(world, -3, j4, 4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 3, j4, 4, this.plankBlock, this.plankMeta);
        }
        for (int j4 = 7; j4 <= 9; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, 5, this.woodBlock, this.woodMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, 4, this.stairBlock, 6);
        this.setBlockAndMetadata(world, 0, 5, 4, Blocks.torch, 4);
        this.placeWallBanner(world, 0, 4, 5, LOTRItemBanner.BannerType.ANGMAR, 2);
        this.setBlockAndMetadata(world, -1, 4, 4, Blocks.skull, 2);
        this.setBlockAndMetadata(world, 1, 4, 4, Blocks.skull, 2);
        this.setBlockAndMetadata(world, 0, 3, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 3, 6, this.stairBlock, 7);
        this.setBlockAndMetadata(world, 0, 4, 6, this.woodBlock, this.woodMeta);
        this.setBlockAndMetadata(world, 0, 5, 6, this.woodBlock, this.woodMeta);
        this.setBlockAndMetadata(world, 0, 6, 6, this.stairBlock, 3);
        this.setBlockAndMetadata(world, -2, 5, 0, Blocks.torch, 2);
        this.placeWallBanner(world, -3, 3, 0, LOTRItemBanner.BannerType.RHUDAUR, 1);
        this.setBlockAndMetadata(world, 2, 5, 0, Blocks.torch, 1);
        this.placeWallBanner(world, 3, 3, 0, LOTRItemBanner.BannerType.RHUDAUR, 3);
        for (int k5 = -3; k5 <= -1; ++k5) {
            this.setBlockAndMetadata(world, -3, 4, k5, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 3, 4, k5, this.stairBlock, 1);
        }
        for (int k5 = -4; k5 <= -1; ++k5) {
            this.setBlockAndMetadata(world, -3, 5, k5, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 3, 5, k5, this.stairBlock, 5);
        }
        for (int k5 = 1; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -3, 4, k5, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 3, 4, k5, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -3, 5, k5, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 3, 5, k5, this.stairBlock, 5);
        }
        for (int k5 = -6; k5 <= 6; ++k5) {
            this.setBlockAndMetadata(world, -5, 4, k5, this.slabBlock, this.slabMeta | 0x8);
            this.setBlockAndMetadata(world, -4, 5, k5, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -3, 6, k5, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -2, 7, k5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -2, 8, k5, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -1, 9, k5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -1, 10, k5, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 0, 10, k5, this.woodBlock, this.woodMeta | 0x8);
            this.setBlockAndMetadata(world, 1, 10, k5, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 1, 9, k5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 2, 8, k5, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 2, 7, k5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 3, 6, k5, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, k5, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 5, 4, k5, this.slabBlock, this.slabMeta | 0x8);
        }
        for (final int k2 : new int[] { -6, 6 }) {
            this.setBlockAndMetadata(world, -4, 4, k2, this.slabBlock, this.slabMeta | 0x8);
            this.setBlockAndMetadata(world, -3, 5, k2, this.stairBlock, 4);
            this.setBlockAndMetadata(world, -2, 6, k2, this.stairBlock, 4);
            this.setBlockAndMetadata(world, -1, 7, k2, this.stairBlock, 4);
            this.setBlockAndMetadata(world, -1, 8, k2, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1, 8, k2, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1, 7, k2, this.stairBlock, 5);
            this.setBlockAndMetadata(world, 2, 6, k2, this.stairBlock, 5);
            this.setBlockAndMetadata(world, 3, 5, k2, this.stairBlock, 5);
            this.setBlockAndMetadata(world, 4, 4, k2, this.slabBlock, this.slabMeta | 0x8);
        }
        this.setBlockAndMetadata(world, 0, 11, -6, this.stairBlock, 3);
        this.setBlockAndMetadata(world, 0, 11, -7, this.stairBlock, 6);
        this.setBlockAndMetadata(world, 0, 12, -7, this.stairBlock, 3);
        this.setBlockAndMetadata(world, 0, 11, 6, this.stairBlock, 2);
        this.setBlockAndMetadata(world, 0, 11, 7, this.stairBlock, 7);
        this.setBlockAndMetadata(world, 0, 12, 7, this.stairBlock, 2);
        for (int k5 = -1; k5 <= 1; ++k5) {
            this.setBlockAndMetadata(world, -1, 10, k5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1, 10, k5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -1, 11, k5, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 1, 11, k5, this.stairBlock, 0);
        }
        this.setBlockAndMetadata(world, 0, 11, -1, this.stairBlock, 2);
        this.setBlockAndMetadata(world, 0, 11, 1, this.stairBlock, 3);
        this.setAir(world, 0, 10, 0);
        for (int l = 0; l <= 2; ++l) {
            final int j5 = 4 + l * 2;
            this.setBlockAndMetadata(world, -4 + l, j5, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -4 + l, j5 + 1, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -4 + l, j5 + 2, 0, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 4 - l, j5, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 4 - l, j5 + 1, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 4 - l, j5 + 2, 0, this.stairBlock, 0);
        }
        for (int k5 = -4; k5 <= 4; ++k5) {
            this.setBlockAndMetadata(world, -2, 6, k5, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k5, this.stairBlock, 5);
        }
        for (int k5 = -3; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -1, 8, k5, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 1, 8, k5, this.stairBlock, 5);
        }
        for (final int i4 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, i4, 8, -5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i4, 8, -4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i4, 8, 4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i4, 8, 5, this.plankBlock, this.plankMeta);
        }
        this.setBlockAndMetadata(world, -1, 7, -4, this.stairBlock, 4);
        this.setBlockAndMetadata(world, 1, 7, -4, this.stairBlock, 5);
        this.setBlockAndMetadata(world, -1, 7, 4, this.stairBlock, 4);
        this.setBlockAndMetadata(world, 1, 7, 4, this.stairBlock, 5);
        for (int j4 = 0; j4 >= -5; --j4) {
            for (int i5 = -1; i5 <= 1; ++i5) {
                for (int k6 = -1; k6 <= 1; ++k6) {
                    if (i5 == 0 && k6 == 0) {
                        this.setAir(world, i5, j4, k6);
                    }
                    else {
                        this.setBlockAndMetadata(world, i5, j4, k6, Blocks.cobblestone, 0);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, -6, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, -5, 0, (Block)Blocks.fire, 0);
        this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.bronzeBars, 0);
        this.setAir(world, 0, 1, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.strawBed, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, LOTRMod.strawBed, 8);
        for (int j4 = 1; j4 <= 2; ++j4) {
            this.setBlockAndMetadata(world, -1, j4, 4, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 1, j4, 4, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -2, 1, 3, LOTRMod.angmarTable, 0);
        this.setBlockAndMetadata(world, -2, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, 1, 3, Blocks.furnace, 5);
        this.placeChest(world, random, 2, 1, 2, 5, LOTRChestContents.ANGMAR_HILLMAN_HOUSE);
        final LOTREntityAngmarHillman chieftain = new LOTREntityAngmarHillmanChieftain(world);
        this.spawnNPCAndSetHome(chieftain, world, 0, 1, 0, 8);
        return true;
    }
}
