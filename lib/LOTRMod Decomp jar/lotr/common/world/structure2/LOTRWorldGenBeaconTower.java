// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGondorTowerGuard;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenBeaconTower extends LOTRWorldGenGondorStructure
{
    public boolean generateRoom;
    
    public LOTRWorldGenBeaconTower(final boolean flag) {
        super(flag);
        this.generateRoom = true;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        return this.generateWithSetRotationAndHeight(world, random, i, j, k, rotation, random.nextInt(4));
    }
    
    public boolean generateWithSetRotationAndHeight(final World world, final Random random, final int i, int j, final int k, final int rotation, final int height) {
        int doorBase = j - 1;
        j += height;
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        doorBase -= this.getY(0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                for (int j2 = 9; j2 <= 13; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 8; j3 >= doorBase || (!this.isOpaque(world, i2, j3, k2) && this.getY(j3) >= 0); --j3) {
                    if (i3 == 2 && k3 == 2) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.pillarBlock, super.pillarMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                    }
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                if (i3 == 2 && k3 == 2) {
                    for (int j3 = 9; j3 <= 12; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.pillarBlock, super.pillarMeta);
                    }
                }
                else if (i3 == 2 || k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 9, k2, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if ((i3 == 3 && k3 == 1) || (k3 == 3 && i3 == 1)) {
                    for (int j3 = 4; j3 >= 1 || (!this.isOpaque(world, i2, j3, k2) && this.getY(j3) >= 0); --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                }
            }
        }
        for (final int i4 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, i4, 5, -3, super.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i4, 5, 3, super.brickStairBlock, 3);
        }
        for (final int k4 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, -3, 5, k4, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 3, 5, k4, super.brickStairBlock, 0);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                this.setBlockAndMetadata(world, i2, 8, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 9, 0, super.rockBlock, super.rockMeta);
        this.setBlockAndMetadata(world, 0, 10, 0, LOTRMod.beacon, 0);
        this.setBlockAndMetadata(world, -2, 9, 0, super.fenceGateBlock, 3);
        for (int j4 = 8; !this.isOpaque(world, -3, j4, 0) && this.getY(j4) >= 0; --j4) {
            this.setBlockAndMetadata(world, -3, j4, 0, Blocks.ladder, 5);
        }
        this.setBlockAndMetadata(world, -2, 12, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 12, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 12, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 12, 1, Blocks.torch, 4);
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 3 || k3 == 3) {
                    this.setBlockAndMetadata(world, i2, 13, k2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                }
                else if (i3 == 2 || k3 == 2) {
                    if (i3 == 2 && k3 == 2) {
                        this.setBlockAndMetadata(world, i2, 13, k2, super.brickBlock, super.brickMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 13, k2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                    }
                    this.setBlockAndMetadata(world, i2, 14, k2, super.brickSlabBlock, super.brickSlabMeta);
                }
                else if (i3 == 1 || k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 14, k2, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (final int i4 : new int[] { -2, 2 }) {
            for (final int k5 : new int[] { -2, 2 }) {
                this.setBlockAndMetadata(world, i4, 13, k5 - 1, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i4, 13, k5 + 1, super.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i4 - 1, 13, k5, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i4 + 1, 13, k5, super.brickStairBlock, 4);
            }
        }
        if (this.generateRoom) {
            this.setBlockAndMetadata(world, 0, doorBase, -2, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 0, doorBase + 1, -2, super.doorBlock, 1);
            this.setBlockAndMetadata(world, 0, doorBase + 2, -2, super.doorBlock, 9);
            for (int i2 = -1; i2 <= 1; ++i2) {
                for (int k2 = -1; k2 <= 1; ++k2) {
                    this.setBlockAndMetadata(world, i2, doorBase, k2, super.brickBlock, super.brickMeta);
                    for (int j2 = doorBase + 1; j2 <= doorBase + 4; ++j2) {
                        this.setAir(world, i2, j2, k2);
                    }
                }
            }
            this.setBlockAndMetadata(world, 0, doorBase + 3, -1, Blocks.torch, 3);
            this.setBlockAndMetadata(world, 1, doorBase + 1, -1, super.tableBlock, 0);
            this.placeWallBanner(world, 2, doorBase + 4, -1, super.bannerType, 3);
            this.placeChest(world, random, -1, doorBase + 1, -1, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
            for (int j4 = doorBase + 1; j4 <= doorBase + 4; ++j4) {
                this.setBlockAndMetadata(world, 1, j4, 1, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, 1, j4, 0, Blocks.ladder, 2);
            }
            this.setBlockAndMetadata(world, -1, doorBase + 2, 1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 0, doorBase + 2, 1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            for (final int j5 : new int[] { doorBase + 1, doorBase + 3 }) {
                this.setBlockAndMetadata(world, -1, j5, 1, super.bedBlock, 1);
                this.setBlockAndMetadata(world, 0, j5, 1, super.bedBlock, 9);
            }
        }
        for (int soldiers = 1 + random.nextInt(2), l = 0; l < soldiers; ++l) {
            final LOTREntityGondorSoldier soldier = new LOTREntityGondorTowerGuard(world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, -1, 9, 0, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityGondorTowerGuard.class);
        respawner.setCheckRanges(16, -12, 12, 4);
        respawner.setSpawnRanges(2, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, 0, 9, 0);
        return true;
    }
}
