// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityMoredainChieftain;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainHutChieftain extends LOTRWorldGenMoredainHut
{
    public LOTRWorldGenMoredainHutChieftain(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 5;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            for (int k2 = -5; k2 <= 5; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 <= 4 && k3 <= 4) {
                    this.layFoundation(world, i2, k2);
                    for (int j2 = 1; j2 <= 9; ++j2) {
                        this.setAir(world, i2, j2, k2);
                    }
                }
                if ((i3 == 4 && k3 <= 4) || (k3 == 4 && i3 <= 4)) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.clayBlock, super.clayMeta);
                }
                if ((i3 == 4 && k3 <= 3) || (k3 == 4 && i3 <= 3)) {
                    for (int j2 = 2; j2 <= 4; ++j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                    }
                }
                if (i3 == 4 && k3 == 4) {
                    for (int j2 = 2; j2 <= 4; ++j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
                if (k2 == 5 && i3 <= 1) {
                    this.layFoundation(world, i2, k2);
                    this.setBlockAndMetadata(world, i2, 1, k2, super.clayBlock, super.clayMeta);
                    for (int j2 = 2; j2 <= 4; ++j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                    }
                    if (i3 == 0) {
                        this.setBlockAndMetadata(world, i2, 5, k2, super.plankBlock, super.plankMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 5, k2, super.plankSlabBlock, super.plankSlabMeta);
                    }
                }
                if ((i3 == 4 && k3 <= 2) || (k3 == 4 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.stainedClayBlock, super.stainedClayMeta);
                    if (i3 == 0 || k3 == 0) {
                        this.setBlockAndMetadata(world, i2, 6, k2, super.plankSlabBlock, super.plankSlabMeta);
                    }
                }
                if ((i3 == 4 && k3 == 3) || (k3 == 4 && i3 == 3)) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.plankSlabBlock, super.plankSlabMeta);
                }
                if (i3 == 3 && k3 == 3) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.plankBlock, super.plankMeta);
                    for (int j2 = 2; j2 <= 4; ++j2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, super.fenceBlock, super.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i2, 5, k2, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i2, 6, k2, super.fenceBlock, super.fenceMeta);
                }
                if ((i3 == 3 && k3 <= 2) || (k3 == 3 && i3 <= 2)) {
                    if (i3 == 0 || k3 == 0) {
                        this.setBlockAndMetadata(world, i2, 6, k2, super.plankSlabBlock, super.plankSlabMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 6, k2, super.plankBlock, super.plankMeta);
                    }
                    this.setBlockAndMetadata(world, i2, 7, k2, super.plankBlock, super.plankMeta);
                }
                if ((i3 == 3 && k3 == 2) || (k3 == 3 && i3 == 2)) {
                    this.setBlockAndMetadata(world, i2, 5, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                if (i3 == 2 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 6, k2, super.fenceBlock, super.fenceMeta);
                }
                if (k3 == 0 && i3 == 4) {
                    this.setBlockAndMetadata(world, i2, 2, k2, super.plankSlabBlock, super.plankSlabMeta);
                    this.setBlockAndMetadata(world, i2, 3, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
                if (k3 <= 1 && i3 == 5) {
                    if (k3 == 0) {
                        this.setBlockAndMetadata(world, i2, 4, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                        this.setBlockAndMetadata(world, i2, 5, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 3, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                        this.setBlockAndMetadata(world, i2, 5, k2, super.plankSlabBlock, super.plankSlabMeta);
                        this.dropFence(world, i2, 2, k2);
                    }
                }
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if ((i3 == 4 && k3 <= 2) || (k3 == 4 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if (i3 == 3 && k3 == 3) {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if ((i3 == 4 && k3 == 0) || (k3 == 4 && i3 == 0)) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i2, 9, k2, super.fenceBlock, super.fenceMeta);
                }
                if (i3 == 2 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if ((i3 == 3 && k3 <= 2) || (k3 == 3 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if ((i3 == 3 && k3 == 0) || (k3 == 3 && i3 == 0)) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.thatchBlock, super.thatchMeta);
                }
                if ((i3 == 2 && k3 <= 2) || (k3 == 2 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if (i3 + k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 9, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if (i3 + k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 9, k2, super.thatchBlock, super.thatchMeta);
                }
                if (i3 <= 2 && k3 <= 2 && i3 + k3 >= 3) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.thatchBlock, super.thatchMeta);
                }
                if (i3 == 1 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
            }
        }
        for (final int f : new int[] { -1, 1 }) {
            this.layFoundation(world, 2 * f, -5);
            this.setBlockAndMetadata(world, 2 * f, 1, -5, super.clayBlock, super.clayMeta);
            this.setBlockAndMetadata(world, 2 * f, 2, -5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 2 * f, 3, -5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 2 * f, 2, -4, super.stainedClayBlock, super.stainedClayMeta);
            this.setBlockAndMetadata(world, 2 * f, 3, -4, super.stainedClayBlock, super.stainedClayMeta);
            this.setAir(world, 1 * f, 1, -4);
            this.setAir(world, 1 * f, 2, -4);
            this.setBlockAndMetadata(world, 1 * f, 3, -4, super.stainedClayBlock, super.stainedClayMeta);
            this.setBlockAndMetadata(world, 1 * f, 4, -4, super.stainedClayBlock, super.stainedClayMeta);
            this.setAir(world, 0 * f, 1, -4);
            this.setAir(world, 0 * f, 2, -4);
            this.setAir(world, 0 * f, 3, -4);
            this.setBlockAndMetadata(world, 0 * f, 4, -4, super.stainedClayBlock, super.stainedClayMeta);
            this.setBlockAndMetadata(world, 2 * f, 4, -5, super.thatchSlabBlock, super.thatchSlabMeta);
            this.setBlockAndMetadata(world, 1 * f, 4, -5, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 0 * f, 5, -5, super.thatchSlabBlock, super.thatchSlabMeta);
            this.setBlockAndMetadata(world, 2 * f, 3, -3, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
            this.setBlockAndMetadata(world, 1 * f, 4, -3, super.thatchSlabBlock, super.thatchSlabMeta);
            this.setBlockAndMetadata(world, 0 * f, 4, -3, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
        }
        this.placeWallBanner(world, 0, 4, -4, LOTRItemBanner.BannerType.MOREDAIN, 2);
        this.placeWallBanner(world, -4, 5, 0, LOTRItemBanner.BannerType.MOREDAIN, 1);
        this.placeWallBanner(world, 4, 5, 0, LOTRItemBanner.BannerType.MOREDAIN, 3);
        this.placeWallBanner(world, -2, 8, 0, LOTRItemBanner.BannerType.MOREDAIN, 1);
        this.placeWallBanner(world, 0, 8, -2, LOTRItemBanner.BannerType.MOREDAIN, 0);
        this.placeWallBanner(world, 2, 8, 0, LOTRItemBanner.BannerType.MOREDAIN, 3);
        this.placeWallBanner(world, 0, 8, 2, LOTRItemBanner.BannerType.MOREDAIN, 2);
        this.setBlockAndMetadata(world, -2, 1, -2, LOTRMod.lionBed, 3);
        this.setBlockAndMetadata(world, -3, 1, -2, LOTRMod.lionBed, 11);
        this.setBlockAndMetadata(world, -2, 1, 0, LOTRMod.lionBed, 3);
        this.setBlockAndMetadata(world, -3, 1, 0, LOTRMod.lionBed, 11);
        this.setBlockAndMetadata(world, -3, 1, 1, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 3, 1, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 3, 1, 1, Blocks.crafting_table, 0);
        this.placeChest(world, random, 3, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.MOREDAIN_HUT);
        this.setBlockAndMetadata(world, 3, 1, -1, LOTRMod.moredainTable, 0);
        this.setBlockAndMetadata(world, 3, 1, -2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int j3 = 1; j3 <= 4; ++j3) {
                this.setBlockAndMetadata(world, i2, j3, 4, super.stainedClayBlock, super.stainedClayMeta);
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int j3 = 1; j3 <= 2; ++j3) {
                this.setBlockAndMetadata(world, i2, j3, 3, super.plankBlock, super.plankMeta);
            }
        }
        this.setBlockAndMetadata(world, -1, 3, 3, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, 0, 3, 3, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 1, 3, 3, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, -1, 1, 2, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 1, 2, super.thatchSlabBlock, super.thatchSlabMeta);
        this.setBlockAndMetadata(world, 1, 1, 2, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 2, 3, LOTRMod.goldBars, 0);
        for (final int f : new int[] { -1, 1 }) {
            for (int k4 = 2; k4 <= 3; ++k4) {
                this.setBlockAndMetadata(world, 2 * f, 3, k4, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                this.setBlockAndMetadata(world, 1 * f, 4, k4, super.thatchSlabBlock, super.thatchSlabMeta);
                this.setBlockAndMetadata(world, 0 * f, 4, k4, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
            }
        }
        this.setBlockAndMetadata(world, -3, 3, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 3, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 3, -1, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 3, 1, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 3, -1, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 3, 1, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 0, -1, LOTRMod.commandTable, 0);
        final LOTREntityMoredainChieftain chieftain = new LOTREntityMoredainChieftain(world);
        chieftain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(chieftain, world, 0, 1, 0, 8);
        return true;
    }
}
