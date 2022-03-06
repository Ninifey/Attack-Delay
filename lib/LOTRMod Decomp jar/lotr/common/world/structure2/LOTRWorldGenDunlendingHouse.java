// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDunlendingHouse extends LOTRWorldGenDunlandStructure
{
    public LOTRWorldGenDunlendingHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 6; ++i2) {
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
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -6; k3 <= 5; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (k3 >= -5) {
                    for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.floorBlock, super.floorMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                for (int j2 = 1; j2 <= 6; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
            }
        }
        this.loadStrScan("dunland_house");
        this.associateBlockMetaAlias("FLOOR", super.floorBlock, super.floorMeta);
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("WOOD|8", super.woodBlock, super.woodMeta | 0x8);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.associateBlockMetaAlias("BARS", super.barsBlock, super.barsMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, super.bedBlock, 8);
        this.placeChest(world, random, -2, 1, 4, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeChest(world, random, 2, 1, 4, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeBarrel(world, random, -3, 2, -3, 4, LOTRFoods.DUNLENDING_DRINK);
        this.placePlate(world, random, -3, 2, -2, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
        this.placePlate(world, random, -3, 2, -1, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
        this.placeMug(world, random, 3, 2, -3, 1, LOTRFoods.DUNLENDING_DRINK);
        this.placePlate(world, random, 3, 2, -2, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
        this.placePlate(world, random, 3, 2, -1, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
        this.placeFlowerPot(world, -3, 2, 1, this.getRandomFlower(world, random));
        this.placeWeaponRack(world, 0, 3, -4, 4, this.getRandomDunlandWeapon(random));
        this.placeDunlandItemFrame(world, random, -2, 2, -5, 0);
        this.placeDunlandItemFrame(world, random, 2, 2, -5, 0);
        this.placeDunlandItemFrame(world, random, -2, 2, 5, 2);
        this.placeDunlandItemFrame(world, random, 2, 2, 5, 2);
        this.placeWallBanner(world, -2, 4, -6, LOTRItemBanner.BannerType.DUNLAND, 2);
        this.placeWallBanner(world, 2, 4, -6, LOTRItemBanner.BannerType.DUNLAND, 2);
        final LOTREntityDunlending dunlending = new LOTREntityDunlending(world);
        this.spawnNPCAndSetHome(dunlending, world, 0, 1, 0, 16);
        return true;
    }
}
