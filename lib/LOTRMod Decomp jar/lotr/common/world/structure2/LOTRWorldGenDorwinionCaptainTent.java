// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityDorwinionCrossbower;
import lotr.common.entity.npc.LOTREntityDorwinionGuard;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDorwinionElfCaptain;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDorwinionCaptain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.util.Direction;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionCaptainTent extends LOTRWorldGenDorwinionTent
{
    public LOTRWorldGenDorwinionCaptainTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            int maxEdgeHeight = 0;
            for (int i2 = -15; i2 <= 15; ++i2) {
                for (int k2 = -15; k2 <= 15; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2);
                    final Block block = this.getBlock(world, i2, j2 - 1, k2);
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
                    if ((Math.abs(i2) == 8 || Math.abs(k2) == 8) && j2 > maxEdgeHeight) {
                        maxEdgeHeight = j2;
                    }
                }
            }
            super.originY = this.getY(maxEdgeHeight);
        }
        final int r = 35;
        final int h = 7;
        for (int i3 = -r; i3 <= r; ++i3) {
            for (int k3 = -r; k3 <= r; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                boolean within = false;
                for (int j3 = 0; j3 >= -h; --j3) {
                    final int j4 = j3 + r - 3;
                    final int d = i4 * i4 + j4 * j4 + k4 * k4;
                    if (d < r * r) {
                        final boolean grass = !this.isOpaque(world, i3, j3 + 1, k3);
                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)(grass ? Blocks.grass : Blocks.dirt), 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                        within = true;
                    }
                }
                if (within) {
                    for (int j3 = -h - 1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
            }
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                boolean inside = false;
                if (i4 <= 3 && k4 <= 3) {
                    inside = true;
                }
                if ((i4 == 4 && k4 <= 3) || (k4 == 4 && i4 <= 3)) {
                    inside = true;
                }
                if ((i4 == 5 && k4 <= 2) || (k4 == 5 && i4 <= 2)) {
                    inside = true;
                }
                if (inside) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                    for (int j3 = 1; j3 <= 4; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                }
                if ((i4 == 6 && k4 == 2) || (k4 == 6 && i4 == 2) || (i4 == 4 && k4 == 4)) {
                    this.setGrassToDirt(world, i3, 0, k3);
                    for (int j3 = 1; j3 <= 3; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 4, k3, super.clay2SlabBlock, super.clay2SlabMeta);
                }
                if ((i4 == 5 && k4 == 3) || (k4 == 5 && i4 == 3)) {
                    this.setGrassToDirt(world, i3, 0, k3);
                    this.setBlockAndMetadata(world, i3, 1, k3, super.wool1Block, super.wool1Meta);
                    this.setBlockAndMetadata(world, i3, 2, k3, super.wool2Block, super.wool2Meta);
                    this.setBlockAndMetadata(world, i3, 3, k3, super.wool1Block, super.wool1Meta);
                    this.setBlockAndMetadata(world, i3, 4, k3, super.clay2SlabBlock, super.clay2SlabMeta);
                }
                if ((i4 == 5 && k4 == 4) || (k4 == 5 && i4 == 4)) {
                    for (int j3 = 1; j3 <= 2; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.fenceBlock, super.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 3, k3, super.clay1Block, super.clay1Meta);
                }
                if ((i4 == 6 && k4 == 3) || (k4 == 6 && i4 == 3)) {
                    this.setBlockAndMetadata(world, i3, 3, k3, super.clay1SlabBlock, super.clay1SlabMeta | 0x8);
                }
                if ((i4 == 6 && k4 <= 1) || (k4 == 6 && i4 <= 1)) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.floorBlock, super.floorMeta);
                    final int gateMeta = Direction.directionToFacing[Direction.getMovementDirection((double)i3, (double)k3)];
                    for (int j5 = 1; j5 <= 3; ++j5) {
                        this.setBlockAndMetadata(world, i3, j5, k3, LOTRMod.gateWooden, gateMeta);
                    }
                    this.setBlockAndMetadata(world, i3, 4, k3, super.clay2SlabBlock, super.clay2SlabMeta);
                }
                if ((i4 == 5 && k4 == 2) || (k4 == 5 && i4 == 2)) {
                    this.setBlockAndMetadata(world, i3, 1, k3, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i3, 2, k3, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i3, 3, k3, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i3, 4, k3, super.clay1Block, super.clay1Meta);
                }
                if ((i4 == 5 && k4 <= 1) || (k4 == 5 && i4 <= 1)) {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.clay1SlabBlock, super.clay1SlabMeta | 0x8);
                }
                if ((i4 == 4 && k4 == 3) || (k4 == 4 && i4 == 3)) {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.clay1SlabBlock, super.clay1SlabMeta | 0x8);
                }
                if ((i4 == 4 && k4 <= 2) || (k4 == 4 && i4 <= 2) || (i4 == 3 && k4 == 3)) {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.clay2SlabBlock, super.clay2SlabMeta);
                }
                if ((i4 == 3 && k4 <= 2) || (k4 == 3 && i4 <= 2) || (i4 == 2 && k4 == 2)) {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.clay1SlabBlock, super.clay1SlabMeta);
                }
                if ((i4 == 2 && k4 == 1) || (k4 == 2 && i4 == 1)) {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.clay2SlabBlock, super.clay2SlabMeta);
                }
                if ((i4 == 0 && k4 == 2) || (k4 == 0 && i4 == 2) || (i4 == 1 && k4 == 1)) {
                    this.setBlockAndMetadata(world, i3, 5, k3, super.clay2Block, super.clay2Meta);
                }
                if ((i4 == 0 && k4 == 1) || (k4 == 0 && i4 == 1) || (i4 == 0 && k4 == 0)) {
                    this.setBlockAndMetadata(world, i3, 5, k3, LOTRMod.silverBars, 0);
                }
                if ((i4 == 2 && k4 <= 1) || (k4 == 2 && i4 <= 1) || (i4 <= 1 && k4 <= 1)) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.plankBlock, super.plankMeta);
                }
                if ((i4 == 2 && k4 == 0) || (k4 == 2 && i4 == 0)) {
                    for (int j3 = 1; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.woodBeamBlock, super.woodBeamMeta);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 1, 0, LOTRMod.commandTable, 0);
        this.setBlockAndMetadata(world, 0, 3, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 3, 3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 3, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 3, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 1, -4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, -3, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -4, 1, -3, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, -2, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, -4, 1, -2, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, -3, 2, -3, Blocks.bed, 2);
        this.setBlockAndMetadata(world, -3, 2, -4, Blocks.bed, 10);
        this.setBlockAndMetadata(world, 3, 1, -4, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 4, 1, -3, LOTRMod.dorwinionTable, 0);
        this.placeChest(world, random, -4, 1, 3, 2, LOTRChestContents.DORWINION_CAMP);
        this.placeChest(world, random, -3, 1, 4, 4, LOTRChestContents.DORWINION_CAMP);
        this.setBlockAndMetadata(world, 2, 1, 4, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.placeMug(world, random, 2, 2, 4, 1, LOTRFoods.DORWINION_DRINK);
        this.setBlockAndMetadata(world, 3, 1, 3, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.placeMug(world, random, 3, 2, 3, 0, LOTRFoods.DORWINION_DRINK);
        this.setBlockAndMetadata(world, 4, 1, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.placeMug(world, random, 4, 2, 2, 1, LOTRFoods.DORWINION_DRINK);
        this.setBlockAndMetadata(world, 3, 1, 4, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 3, 2, 4, 5, LOTRFoods.DORWINION_DRINK);
        this.setBlockAndMetadata(world, 4, 1, 3, super.plankBlock, super.plankMeta);
        this.placeBarrel(world, random, 4, 2, 3, 2, LOTRFoods.DORWINION_DRINK);
        for (final int i5 : new int[] { -2, 2 }) {
            this.placeWallBanner(world, i5, 3, -6, LOTRItemBanner.BannerType.DORWINION, 2);
            this.placeWallBanner(world, i5, 3, 6, LOTRItemBanner.BannerType.DORWINION, 0);
        }
        for (final int k5 : new int[] { -2, 2 }) {
            this.placeWallBanner(world, -6, 3, k5, LOTRItemBanner.BannerType.DORWINION, 3);
            this.placeWallBanner(world, 6, 3, k5, LOTRItemBanner.BannerType.DORWINION, 1);
        }
        final LOTREntityDorwinionCaptain captain = new LOTREntityDorwinionCaptain(world);
        this.spawnNPCAndSetHome(captain, world, 0, 1, -1, 16);
        if (random.nextInt(3) == 0) {
            final LOTREntityDorwinionElfCaptain elfCaptain = new LOTREntityDorwinionElfCaptain(world);
            this.spawnNPCAndSetHome(elfCaptain, world, 0, 1, -1, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityDorwinionGuard.class, LOTREntityDorwinionCrossbower.class);
        respawner.setCheckRanges(24, -12, 12, 12);
        respawner.setSpawnRanges(12, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
}
