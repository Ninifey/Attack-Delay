// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import lotr.common.LOTRFoods;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenNearHaradTent extends LOTRWorldGenHarnedorStructure
{
    public LOTRWorldGenNearHaradTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
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
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                for (int j2 = 0; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                for (int j2 = 1; j2 <= 6; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 3 && k3 == 3) {
                    for (int j3 = 1; j3 <= 5; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i2, 6, k2, Blocks.torch, 5);
                }
                else if (i3 == 3 || k3 == 3) {
                    for (int j3 = 1; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.roofBlock, super.roofMeta);
                    }
                }
                if ((i3 == 2 && k3 <= 2) || (k3 == 2 && i3 <= 2)) {
                    for (int j3 = 4; j3 <= 5; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, Blocks.wool, 15);
                    }
                    this.setBlockAndMetadata(world, i2, 1, k2, Blocks.carpet, 15);
                }
                if (i3 == 2 && k3 == 2) {
                    for (int j3 = 1; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
                if ((i3 == 1 && k3 <= 1) || (k3 == 1 && i3 <= 1)) {
                    this.setBlockAndMetadata(world, i2, 6, k2, super.roofBlock, super.roofMeta);
                    this.setBlockAndMetadata(world, i2, 1, k2, Blocks.carpet, 14);
                }
            }
        }
        for (int j4 = 1; j4 <= 5; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, 0, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, 0, 6, 0, super.plankBlock, super.plankMeta);
        this.placeBanner(world, 0, 7, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        for (int j4 = 2; j4 <= 3; ++j4) {
            for (int i4 = -1; i4 <= 1; ++i4) {
                this.setBlockAndMetadata(world, i4, j4, -3, Blocks.wool, 15);
                this.setBlockAndMetadata(world, i4, j4, 3, Blocks.wool, 15);
            }
            for (int k2 = -1; k2 <= 1; ++k2) {
                this.setBlockAndMetadata(world, -3, j4, k2, Blocks.wool, 15);
                this.setBlockAndMetadata(world, 3, j4, k2, Blocks.wool, 15);
            }
        }
        this.setAir(world, 0, 2, -3);
        this.setAir(world, 0, 2, 3);
        this.setAir(world, -3, 2, 0);
        this.setAir(world, 3, 2, 0);
        this.setBlockAndMetadata(world, -1, 1, -3, Blocks.wool, 15);
        this.setBlockAndMetadata(world, 0, 1, -3, Blocks.carpet, 14);
        this.setBlockAndMetadata(world, 0, 1, -2, Blocks.carpet, 14);
        this.setBlockAndMetadata(world, 1, 1, -3, Blocks.wool, 15);
        this.setBlockAndMetadata(world, -2, 1, 0, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -2, 1, 1, super.bedBlock, 8);
        this.placeBarrel(world, random, -1, 1, 2, 2, LOTRFoods.HARNEDOR_DRINK);
        this.setBlockAndMetadata(world, 0, 1, 2, LOTRMod.nearHaradTable, 0);
        this.placeChest(world, random, 1, 1, 2, LOTRMod.chestBasket, 2, LOTRChestContents.HARNEDOR_HOUSE);
        this.placeChest(world, random, 2, 1, 1, LOTRMod.chestBasket, 5, LOTRChestContents.HARNEDOR_HOUSE);
        this.setBlockAndMetadata(world, 2, 1, 0, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 0, 3, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 3, 2, Blocks.torch, 4);
        this.placeWallBanner(world, -1, 3, 3, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, 1, 3, 3, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, 3, 3, 1, LOTRItemBanner.BannerType.NEAR_HARAD, 3);
        this.placeWallBanner(world, 3, 3, -1, LOTRItemBanner.BannerType.NEAR_HARAD, 3);
        this.placeWallBanner(world, -1, 3, -3, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        this.placeWallBanner(world, 1, 3, -3, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        this.placeWallBanner(world, -3, 3, 1, LOTRItemBanner.BannerType.NEAR_HARAD, 1);
        this.placeWallBanner(world, -3, 3, -1, LOTRItemBanner.BannerType.NEAR_HARAD, 1);
        final LOTREntityNearHaradrimBase haradrim = new LOTREntityHarnedorWarrior(world);
        haradrim.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(haradrim, world, 0, 1, -1, 16);
        return true;
    }
}
