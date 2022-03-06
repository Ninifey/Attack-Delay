// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedorBartender;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import lotr.common.LOTRFoods;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorTavern extends LOTRWorldGenHarnedorStructure
{
    public LOTRWorldGenHarnedorTavern(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 7, -3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -15; i2 <= 15; ++i2) {
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
        for (int i3 = -13; i3 <= 13; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 <= 8 && k4 == 6) || (i4 <= 11 && k4 <= 5) || (i4 <= 13 && k4 <= 4)) {
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                    for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.plank2Block, super.plank2Meta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
            }
        }
        if (this.isRuined()) {
            this.loadStrScan("harnedor_tavern_ruined");
        }
        else {
            this.loadStrScan("harnedor_tavern");
        }
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        if (this.isRuined()) {
            this.setBlockAliasChance("PLANK2", 0.8f);
        }
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        if (!this.isRuined()) {
            this.placeWeaponRack(world, -3, 3, -1, 6, this.getRandomHarnedorWeapon(random));
            this.spawnItemFrame(world, -3, 3, 0, 0, this.getHarnedorFramedItem(random));
            this.placeWeaponRack(world, 3, 3, 1, 4, this.getRandomHarnedorWeapon(random));
            this.spawnItemFrame(world, 3, 3, 0, 2, this.getHarnedorFramedItem(random));
            this.placeFoodOrDrink(world, random, -4, 2, -1);
            this.placeFoodOrDrink(world, random, -3, 2, -1);
            this.placeFoodOrDrink(world, random, -2, 2, -1);
            this.placeFoodOrDrink(world, random, -2, 2, 0);
            this.placeFoodOrDrink(world, random, -2, 2, 1);
            this.placeFoodOrDrink(world, random, -3, 2, 1);
            this.placeFoodOrDrink(world, random, -4, 2, 1);
            this.placeFoodOrDrink(world, random, -4, 2, 0);
            this.placeFoodOrDrink(world, random, 4, 2, -1);
            this.placeFoodOrDrink(world, random, 3, 2, -1);
            this.placeFoodOrDrink(world, random, 2, 2, -1);
            this.placeFoodOrDrink(world, random, 2, 2, 0);
            this.placeFoodOrDrink(world, random, 2, 2, 1);
            this.placeFoodOrDrink(world, random, 3, 2, 1);
            this.placeFoodOrDrink(world, random, 4, 2, 1);
            this.placeFoodOrDrink(world, random, 4, 2, 0);
            this.placeFoodOrDrink(world, random, -7, 2, -5);
            this.placeFoodOrDrink(world, random, -8, 2, 5);
            this.placeFoodOrDrink(world, random, -7, 2, 5);
            this.placeFoodOrDrink(world, random, -6, 2, 5);
            this.placeFoodOrDrink(world, random, 6, 2, -5);
            this.placeFoodOrDrink(world, random, 7, 2, -5);
            this.placeFoodOrDrink(world, random, 6, 2, 5);
            this.placeFoodOrDrink(world, random, 7, 2, 5);
            this.placeFoodOrDrink(world, random, -9, 2, -2);
            this.placeFoodOrDrink(world, random, -9, 2, -1);
            this.placeFoodOrDrink(world, random, -9, 2, 1);
            this.placeFoodOrDrink(world, random, -9, 2, 2);
            this.placeFlowerPot(world, -12, 2, -3, this.getRandomFlower(world, random));
            this.placeFoodOrDrink(world, random, -12, 2, -2);
            this.placeFoodOrDrink(world, random, -12, 2, 1);
            this.placeFoodOrDrink(world, random, -12, 2, 2);
            this.placeBarrel(world, random, -12, 2, 3, 4, LOTRFoods.HARNEDOR_DRINK);
            this.placeBarrel(world, random, -11, 2, 4, 2, LOTRFoods.HARNEDOR_DRINK);
            this.placeKebabStand(world, random, -10, 2, -4, LOTRMod.kebabStand, 3);
            this.setBlockAndMetadata(world, 11, 1, -3, super.bedBlock, 2);
            this.setBlockAndMetadata(world, 11, 1, -4, super.bedBlock, 10);
            this.setBlockAndMetadata(world, 11, 1, 3, super.bedBlock, 0);
            this.setBlockAndMetadata(world, 11, 1, 4, super.bedBlock, 8);
            this.placeChest(world, random, 12, 1, -3, LOTRMod.chestBasket, 3, LOTRChestContents.HARNEDOR_HOUSE);
            this.placeChest(world, random, 12, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.HARNEDOR_HOUSE);
            this.placeFlowerPot(world, 12, 2, -1, this.getRandomFlower(world, random));
            this.placeFoodOrDrink(world, random, 11, 2, -1);
            this.placeFlowerPot(world, 11, 2, 1, this.getRandomFlower(world, random));
            this.placeFoodOrDrink(world, random, 12, 2, 1);
            final String[] tavernName = LOTRNames.getHaradTavernName(random);
            final String tavernNameNPC = tavernName[0] + " " + tavernName[1];
            this.placeSign(world, -1, 2, -6, Blocks.wall_sign, 5, new String[] { "", tavernName[0], tavernName[1], "" });
            this.placeSign(world, 1, 2, -6, Blocks.wall_sign, 4, new String[] { "", tavernName[0], tavernName[1], "" });
            this.placeSign(world, -1, 2, 6, Blocks.wall_sign, 5, new String[] { "", tavernName[0], tavernName[1], "" });
            this.placeSign(world, 1, 2, 6, Blocks.wall_sign, 4, new String[] { "", tavernName[0], tavernName[1], "" });
            this.placeWallBanner(world, -6, 4, -8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
            this.placeWallBanner(world, 6, 4, -8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
            this.placeWallBanner(world, -6, 4, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
            this.placeWallBanner(world, 6, 4, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
            this.placeWallBanner(world, 0, 6, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
            this.placeWallBanner(world, 0, 6, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
            this.placeWallBanner(world, -9, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 1);
            this.placeWallBanner(world, 9, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 3);
            final LOTREntityHarnedorBartender bartender = new LOTREntityHarnedorBartender(world);
            bartender.setSpecificLocationName(tavernNameNPC);
            this.spawnNPCAndSetHome(bartender, world, -10, 1, 0, 4);
            for (int numHaradrim = MathHelper.getRandomIntegerInRange(random, 3, 8), l = 0; l < numHaradrim; ++l) {
                final LOTREntityHarnedhrim haradrim = new LOTREntityHarnedhrim(world);
                this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
            }
        }
        final int maxSteps = 12;
        for (int i5 = -5; i5 <= -1; ++i5) {
            for (int step = 0; step < 12; ++step) {
                final int j3 = 0 - step;
                final int k5 = -7 - step;
                if (this.isOpaque(world, i5, j3, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j3, k5, super.plank2StairBlock, 2);
                this.setGrassToDirt(world, i5, j3 - 1, k5);
                for (int j4 = j3 - 1; !this.isOpaque(world, i5, j4, k5) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i5, j4, k5, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i5, j4 - 1, k5);
                }
            }
        }
        for (int i5 = 1; i5 <= 5; ++i5) {
            for (int step = 0; step < 12; ++step) {
                final int j3 = 0 - step;
                final int k5 = -7 - step;
                if (this.isOpaque(world, i5, j3, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j3, k5, super.plank2StairBlock, 2);
                this.setGrassToDirt(world, i5, j3 - 1, k5);
                for (int j4 = j3 - 1; !this.isOpaque(world, i5, j4, k5) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i5, j4, k5, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i5, j4 - 1, k5);
                }
            }
        }
        for (int i5 = -5; i5 <= -1; ++i5) {
            for (int step = 0; step < 12; ++step) {
                final int j3 = 0 - step;
                final int k5 = 7 + step;
                if (this.isOpaque(world, i5, j3, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j3, k5, super.plank2StairBlock, 3);
                this.setGrassToDirt(world, i5, j3 - 1, k5);
                for (int j4 = j3 - 1; !this.isOpaque(world, i5, j4, k5) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i5, j4, k5, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i5, j4 - 1, k5);
                }
            }
        }
        for (int i5 = 1; i5 <= 5; ++i5) {
            for (int step = 0; step < 12; ++step) {
                final int j3 = 0 - step;
                final int k5 = 7 + step;
                if (this.isOpaque(world, i5, j3, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j3, k5, super.plank2StairBlock, 3);
                this.setGrassToDirt(world, i5, j3 - 1, k5);
                for (int j4 = j3 - 1; !this.isOpaque(world, i5, j4, k5) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i5, j4, k5, super.plank2Block, super.plank2Meta);
                    this.setGrassToDirt(world, i5, j4 - 1, k5);
                }
            }
        }
        return true;
    }
    
    private void placeFoodOrDrink(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.HARNEDOR_DRINK);
            }
            else {
                final Block plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
                if (random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
                }
                else {
                    this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.HARNEDOR);
                }
            }
        }
    }
}
