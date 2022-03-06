// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityDunlending;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDunlendingBartender;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDunlendingTavern extends LOTRWorldGenDunlandStructure
{
    public LOTRWorldGenDunlendingTavern(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -11; i2 <= 11; ++i2) {
                for (int k2 = -8; k2 <= 8; ++k2) {
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
                    if (maxHeight - minHeight > 12) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -9; i3 <= 9; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 1; j2 <= 7; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, super.floorBlock, super.floorMeta);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                if (random.nextInt(4) == 0 && (i4 > 3 || k4 > 2)) {
                    this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                }
            }
        }
        this.loadStrScan("dunland_tavern");
        this.associateBlockMetaAlias("FLOOR", super.floorBlock, super.floorMeta);
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", super.woodBlock, super.woodMeta | 0x4);
        this.associateBlockMetaAlias("WOOD|8", super.woodBlock, super.woodMeta | 0x8);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.associateBlockMetaAlias("BARS", super.barsBlock, super.barsMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeFlowerPot(world, 8, 2, -5, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, 8, 2, 5, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -8, 2, -4, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -8, 2, 4, this.getRandomFlower(world, random));
        this.placeChest(world, random, 7, 1, -5, LOTRMod.chestBasket, 5, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeBarrel(world, random, 7, 2, 6, 2, LOTRFoods.DUNLENDING_DRINK);
        this.placeBarrel(world, random, 4, 2, 6, 2, LOTRFoods.DUNLENDING_DRINK);
        this.placeFoodOrDrink(world, random, -6, 2, -6);
        this.placeFoodOrDrink(world, random, -5, 2, -6);
        this.placeFoodOrDrink(world, random, -6, 2, -1);
        this.placeFoodOrDrink(world, random, -5, 2, -1);
        this.placeFoodOrDrink(world, random, -6, 2, 0);
        this.placeFoodOrDrink(world, random, -5, 2, 0);
        this.placeFoodOrDrink(world, random, -6, 2, 1);
        this.placeFoodOrDrink(world, random, -5, 2, 1);
        this.placeFoodOrDrink(world, random, -6, 2, 6);
        this.placeFoodOrDrink(world, random, -5, 2, 6);
        this.placeFoodOrDrink(world, random, -1, 2, 6);
        this.placeFoodOrDrink(world, random, 0, 2, 6);
        this.placeFoodOrDrink(world, random, 1, 2, 6);
        this.placeFoodOrDrink(world, random, 5, 2, 3);
        this.placeFoodOrDrink(world, random, 6, 2, 3);
        this.placeFoodOrDrink(world, random, 8, 2, 4);
        this.placeFoodOrDrink(world, random, 5, 2, -3);
        this.placeFoodOrDrink(world, random, 6, 2, -3);
        this.placeFoodOrDrink(world, random, 8, 2, -4);
        this.placeFoodOrDrink(world, random, 4, 2, -6);
        this.placeFoodOrDrink(world, random, 7, 2, -6);
        final String[] tavernName = LOTRNames.getDunlendingTavernName(random);
        final String tavernNameNPC = tavernName[0] + " " + tavernName[1];
        this.placeSign(world, 0, 3, -8, Blocks.wall_sign, 2, new String[] { "", tavernName[0], tavernName[1], "" });
        this.placeWallBanner(world, -8, 6, 0, LOTRItemBanner.BannerType.DUNLAND, 1);
        this.placeWallBanner(world, 8, 6, 0, LOTRItemBanner.BannerType.DUNLAND, 3);
        for (final int k5 : new int[] { -3, 3 }) {
            this.placeDunlandItemFrame(world, random, -3, 2, k5, 1);
            this.placeDunlandItemFrame(world, random, 3, 2, k5, 3);
        }
        final LOTREntityDunlendingBartender bartender = new LOTREntityDunlendingBartender(world);
        bartender.setSpecificLocationName(tavernNameNPC);
        if (random.nextBoolean()) {
            this.spawnNPCAndSetHome(bartender, world, 5, 1, -4, 2);
        }
        else {
            this.spawnNPCAndSetHome(bartender, world, 5, 1, 4, 2);
        }
        for (int dunlendings = MathHelper.getRandomIntegerInRange(random, 3, 8), l = 0; l < dunlendings; ++l) {
            final LOTREntityDunlending dunlending = new LOTREntityDunlending(world);
            this.spawnNPCAndSetHome(dunlending, world, 0, 1, 0, 16);
        }
        return true;
    }
    
    private void placeFoodOrDrink(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.DUNLENDING_DRINK);
            }
            else {
                final Block plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
                if (random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
                }
                else {
                    this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.DUNLENDING);
                }
            }
        }
    }
}
