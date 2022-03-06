// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public class LOTRWorldGenRangerWatchtower extends LOTRWorldGenStructureBase2
{
    private Block woodBlock;
    private int woodMeta;
    private Block plankBlock;
    private int plankMeta;
    private Block fenceBlock;
    private int fenceMeta;
    private Block stairBlock;
    
    public LOTRWorldGenRangerWatchtower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        if (super.restrictions) {
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        final int randomWood = random.nextInt(4);
        if (randomWood == 0) {
            this.woodBlock = Blocks.log;
            this.woodMeta = 0;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.stairBlock = Blocks.oak_stairs;
        }
        else if (randomWood == 1) {
            this.woodBlock = Blocks.log;
            this.woodMeta = 1;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.stairBlock = Blocks.spruce_stairs;
        }
        else if (randomWood == 2) {
            this.woodBlock = LOTRMod.wood2;
            this.woodMeta = 1;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 9;
            this.stairBlock = LOTRMod.stairsBeech;
        }
        else if (randomWood == 3) {
            this.woodBlock = LOTRMod.wood3;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 12;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 12;
            this.stairBlock = LOTRMod.stairsMaple;
        }
        this.generateSupportPillar(world, -3, 4, -3);
        this.generateSupportPillar(world, -3, 4, 3);
        this.generateSupportPillar(world, 3, 4, -3);
        this.generateSupportPillar(world, 3, 4, 3);
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = -2; k3 <= 2; ++k3) {
                for (int j3 = 5; j3 <= 19; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        for (int j4 = 6; j4 <= 19; ++j4) {
            this.setBlockAndMetadata(world, -2, j4, -2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -2, j4, 2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 2, j4, -2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 2, j4, 2, this.woodBlock, this.woodMeta);
        }
        for (int j4 = 5; j4 <= 10; j4 += 5) {
            for (int i4 = -3; i4 <= 3; ++i4) {
                for (int k4 = -3; k4 <= 3; ++k4) {
                    this.setBlockAndMetadata(world, i4, j4, k4, this.plankBlock, this.plankMeta);
                }
            }
            for (int i4 = -4; i4 <= 4; ++i4) {
                this.setBlockAndMetadata(world, i4, j4, -4, this.stairBlock, 2);
                this.setBlockAndMetadata(world, i4, j4, 4, this.stairBlock, 3);
            }
            for (int k3 = -3; k3 <= 3; ++k3) {
                this.setBlockAndMetadata(world, -4, j4, k3, this.stairBlock, 1);
                this.setBlockAndMetadata(world, 4, j4, k3, this.stairBlock, 0);
            }
            for (int i4 = -2; i4 <= 2; ++i4) {
                this.setBlockAndMetadata(world, i4, j4 + 1, -3, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, i4, j4 + 1, 3, this.fenceBlock, this.fenceMeta);
            }
            for (int k3 = -2; k3 <= 2; ++k3) {
                this.setBlockAndMetadata(world, -3, j4 + 1, k3, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, 3, j4 + 1, k3, this.fenceBlock, this.fenceMeta);
            }
            this.setBlockAndMetadata(world, 0, j4 + 2, -3, Blocks.torch, 5);
            this.setBlockAndMetadata(world, 0, j4 + 2, 3, Blocks.torch, 5);
            this.setBlockAndMetadata(world, -3, j4 + 2, 0, Blocks.torch, 5);
            this.setBlockAndMetadata(world, 3, j4 + 2, 0, Blocks.torch, 5);
            this.spawnNPCAndSetHome(new LOTREntityRangerNorth(world), world, -1, j4 + 1, 0, 8);
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = -2; k3 <= 2; ++k3) {
                final int i5 = Math.abs(i3);
                final int k5 = Math.abs(k3);
                if (i5 < 2 || k5 < 2) {
                    this.setBlockAndMetadata(world, i3, 15, k3, this.plankBlock, this.plankMeta);
                    if ((i5 < 2 && k5 == 2) || (i5 == 2 && k5 < 2)) {
                        this.setBlockAndMetadata(world, i3, 16, k3, this.fenceBlock, this.fenceMeta);
                    }
                }
            }
        }
        this.setGrassToDirt(world, 0, 0, 0);
        for (int j4 = 1; j4 <= 25; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, 0, this.woodBlock, this.woodMeta);
            if (j4 <= 15) {
                this.setBlockAndMetadata(world, 0, j4, -1, Blocks.ladder, 2);
            }
        }
        this.setBlockAndMetadata(world, 0, 6, -1, Blocks.trapdoor, 0);
        this.setBlockAndMetadata(world, 0, 11, -1, Blocks.trapdoor, 0);
        this.setBlockAndMetadata(world, 0, 17, -2, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 0, 17, 2, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -2, 17, 0, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 2, 17, 0, Blocks.torch, 5);
        this.placeChest(world, random, 0, 16, 1, 0, LOTRChestContents.RANGER_TENT);
        this.setBlockAndMetadata(world, 0, 11, 1, LOTRMod.rangerTable, 0);
        for (int j4 = 17; j4 <= 18; ++j4) {
            this.setBlockAndMetadata(world, -2, j4, -2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -2, j4, 2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 2, j4, -2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 2, j4, 2, this.fenceBlock, this.fenceMeta);
        }
        for (int step = 0; step <= 1; ++step) {
            for (int i4 = -2 + step; i4 <= 2 - step; ++i4) {
                this.setBlockAndMetadata(world, i4, 20 + step, -2 + step, this.stairBlock, 2);
                this.setBlockAndMetadata(world, i4, 20 + step, 2 - step, this.stairBlock, 3);
            }
            for (int k3 = -1 + step; k3 <= 1 - step; ++k3) {
                this.setBlockAndMetadata(world, -2 + step, 20 + step, k3, this.stairBlock, 1);
                this.setBlockAndMetadata(world, 2 - step, 20 + step, k3, this.stairBlock, 0);
            }
        }
        this.placeWallBanner(world, -2, 15, 0, LOTRItemBanner.BannerType.RANGER_NORTH, 3);
        this.placeWallBanner(world, 2, 15, 0, LOTRItemBanner.BannerType.RANGER_NORTH, 1);
        this.placeWallBanner(world, 0, 15, -2, LOTRItemBanner.BannerType.RANGER_NORTH, 2);
        this.placeWallBanner(world, 0, 15, 2, LOTRItemBanner.BannerType.RANGER_NORTH, 0);
        for (int j4 = 24; j4 <= 25; ++j4) {
            this.setBlockAndMetadata(world, 1, j4, 0, Blocks.wool, 13);
            this.setBlockAndMetadata(world, 2, j4, 1, Blocks.wool, 13);
            this.setBlockAndMetadata(world, 2, j4, 2, Blocks.wool, 13);
            this.setBlockAndMetadata(world, 3, j4, 3, Blocks.wool, 13);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityRangerNorth.class);
        respawner.setCheckRanges(24, -12, 20, 8);
        respawner.setSpawnRanges(4, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    private void generateSupportPillar(final World world, final int i, final int j, final int k) {
        for (int j2 = j; !this.isOpaque(world, i, j2, k) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i, j2, k, this.woodBlock, this.woodMeta);
            this.setGrassToDirt(world, i, j2 - 1, i);
        }
    }
}
