// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityDaleMan;
import net.minecraft.block.Block;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDaleSoldier;
import lotr.common.entity.npc.LOTREntityDaleLevyman;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDaleWatchtower extends LOTRWorldGenDaleStructure
{
    public LOTRWorldGenDaleWatchtower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
                    if (block != Blocks.grass) {
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
                for (int j3 = 8; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    if (i3 == 2 && k3 == 2) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.pillarBlock, super.pillarMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                    }
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                if (i3 == 2 && k3 == 2) {
                    for (int j3 = 9; j3 <= 11; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.pillarBlock, super.pillarMeta);
                    }
                    this.setBlockAndMetadata(world, i2, 12, k2, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i2, 12, k2 - 1, super.roofStairBlock, 6);
                    this.setBlockAndMetadata(world, i2, 12, k2 + 1, super.roofStairBlock, 7);
                    this.setBlockAndMetadata(world, i2 - 1, 12, k2, super.roofStairBlock, 5);
                    this.setBlockAndMetadata(world, i2 + 1, 12, k2, super.roofStairBlock, 4);
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if ((i3 == 1 && k3 == 3) || (i3 == 3 && k3 == 1)) {
                    for (int j3 = 3; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -1, 4, -3, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 4, -3, super.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 4, 3, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 4, 3, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 4, -1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 4, 1, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 3, 4, -1, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 4, 1, super.brickStairBlock, 0);
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                for (int j2 = 1; j2 <= 3; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                for (int j2 = 5; j2 <= 7; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                this.setBlockAndMetadata(world, i2, 4, k2, super.plankBlock, super.plankMeta);
                this.setBlockAndMetadata(world, i2, 8, k2, super.plankBlock, super.plankMeta);
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 9, -2, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i2, 9, 2, super.fenceBlock, super.fenceMeta);
        }
        for (int k4 = -1; k4 <= 1; ++k4) {
            this.setBlockAndMetadata(world, -2, 9, k4, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 2, 9, k4, super.fenceBlock, super.fenceMeta);
        }
        for (int j4 = 1; j4 <= 7; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, 1, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 0, 8, 1, Blocks.trapdoor, 9);
        this.setBlockAndMetadata(world, 0, 1, -2, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -2, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 0, 0, super.pillarBlock, super.pillarMeta);
        this.setBlockAndMetadata(world, -2, 1, -1, Blocks.crafting_table, 0);
        this.setAir(world, -2, 2, -1);
        this.setBlockAndMetadata(world, -2, 1, 1, LOTRMod.daleTable, 0);
        this.setAir(world, -2, 2, 1);
        this.setBlockAndMetadata(world, 2, 1, -1, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 2, 2, -1, 5, LOTRFoods.DALE_DRINK);
        this.placeChest(world, random, 2, 1, 1, 5, LOTRChestContents.DALE_WATCHTOWER);
        this.setAir(world, 2, 2, 1);
        for (final int i4 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, i4, 5, 0, LOTRMod.strawBed, 2);
            this.setBlockAndMetadata(world, i4, 5, -1, LOTRMod.strawBed, 10);
        }
        this.setBlockAndMetadata(world, 0, 7, -1, Blocks.torch, 3);
        this.placeWallBanner(world, 0, 7, -2, LOTRItemBanner.BannerType.DALE, 2);
        this.placeWallBanner(world, 0, 7, 2, LOTRItemBanner.BannerType.DALE, 0);
        this.placeWallBanner(world, -2, 7, 0, LOTRItemBanner.BannerType.DALE, 3);
        this.placeWallBanner(world, 2, 7, 0, LOTRItemBanner.BannerType.DALE, 1);
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 3 || k3 == 3) {
                    this.setBlockAndMetadata(world, i2, 13, k2, super.roofSlabBlock, super.roofSlabMeta);
                }
                else if (i3 == 2 || k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 13, k2, super.roofBlock, super.roofMeta);
                }
                else if (i3 == 1 || k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 14, k2, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i2, 15, k2, super.roofSlabBlock, super.roofSlabMeta);
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 14, -2, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i2, 14, 2, super.roofStairBlock, 3);
        }
        for (int k4 = -2; k4 <= 2; ++k4) {
            this.setBlockAndMetadata(world, -2, 14, k4, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 14, k4, super.roofStairBlock, 0);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 14, -1, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i2, 14, 1, super.roofStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -1, 14, 0, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 14, 0, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 13, 1, Blocks.torch, 4);
        for (int soldiers = 1 + random.nextInt(2), l = 0; l < soldiers; ++l) {
            final LOTREntityDaleMan soldier = random.nextBoolean() ? new LOTREntityDaleLevyman(world) : new LOTREntityDaleSoldier(world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 9, 0, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityDaleLevyman.class, LOTREntityDaleSoldier.class);
        respawner.setCheckRanges(16, -12, 12, 4);
        respawner.setSpawnRanges(2, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, 0, 9, 0);
        return true;
    }
}
