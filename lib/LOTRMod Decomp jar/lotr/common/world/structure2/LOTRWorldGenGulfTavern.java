// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGulfBartender;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRMod;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTavern extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfTavern(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 10);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -10; i2 <= 10; ++i2) {
                for (int k2 = -10; k2 <= 10; ++k2) {
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
        for (int i3 = -10; i3 <= 10; ++i3) {
            for (int k3 = -10; k3 <= 10; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 * i4 + k4 * k4 < 100) {
                    for (int j2 = 1; j2 <= 6; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("gulf_tavern");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", super.beamBlock, super.beamMeta);
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        this.associateBlockAlias("PLANK2_STAIR", super.plank2StairBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        final String[] tavernName = LOTRNames.getHaradTavernName(random);
        final String tavernNameNPC = tavernName[0] + " " + tavernName[1];
        this.placeSign(world, 0, 3, -10, Blocks.wall_sign, 2, new String[] { "", tavernName[0], tavernName[1], "" });
        this.placeSign(world, 0, 3, 10, Blocks.wall_sign, 3, new String[] { "", tavernName[0], tavernName[1], "" });
        this.placeBarrel(world, random, -3, 2, -2, 4, LOTRFoods.GULF_HARAD_DRINK);
        this.placeBarrel(world, random, 3, 2, 1, 5, LOTRFoods.GULF_HARAD_DRINK);
        this.placeFlowerPot(world, 3, 2, -2, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -3, 2, 1, this.getRandomFlower(world, random));
        this.placeKebabStand(world, random, -2, 2, 2, LOTRMod.kebabStand, 2);
        this.placeKebabStand(world, random, 2, 2, 2, LOTRMod.kebabStand, 2);
        this.placeWallBanner(world, -2, 4, -3, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 2, 4, -3, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -2, 4, 3, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, 2, 4, 3, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeFoodOrDrink(world, random, -5, 2, -7);
        this.placeFoodOrDrink(world, random, -5, 2, -6);
        this.placeFoodOrDrink(world, random, -6, 2, -6);
        this.placeFoodOrDrink(world, random, -6, 2, -5);
        this.placeFoodOrDrink(world, random, -7, 2, -5);
        this.placeFoodOrDrink(world, random, -6, 2, -1);
        this.placeFoodOrDrink(world, random, -6, 2, 0);
        this.placeFoodOrDrink(world, random, -6, 2, 1);
        this.placeFoodOrDrink(world, random, -5, 2, 7);
        this.placeFoodOrDrink(world, random, -5, 2, 6);
        this.placeFoodOrDrink(world, random, -6, 2, 6);
        this.placeFoodOrDrink(world, random, -6, 2, 5);
        this.placeFoodOrDrink(world, random, -7, 2, 5);
        this.placeFoodOrDrink(world, random, 5, 2, -7);
        this.placeFoodOrDrink(world, random, 5, 2, -6);
        this.placeFoodOrDrink(world, random, 6, 2, -6);
        this.placeFoodOrDrink(world, random, 6, 2, -5);
        this.placeFoodOrDrink(world, random, 7, 2, -5);
        this.placeFoodOrDrink(world, random, 6, 2, -1);
        this.placeFoodOrDrink(world, random, 6, 2, 0);
        this.placeFoodOrDrink(world, random, 6, 2, 1);
        this.placeFoodOrDrink(world, random, 5, 2, 7);
        this.placeFoodOrDrink(world, random, 5, 2, 6);
        this.placeFoodOrDrink(world, random, 6, 2, 6);
        this.placeFoodOrDrink(world, random, 6, 2, 5);
        this.placeFoodOrDrink(world, random, 7, 2, 5);
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.placeFoodOrDrink(world, random, i2, 2, -3);
            this.placeFoodOrDrink(world, random, i2, 2, 3);
        }
        final LOTREntityGulfBartender bartender = new LOTREntityGulfBartender(world);
        bartender.setSpecificLocationName(tavernNameNPC);
        this.spawnNPCAndSetHome(bartender, world, 0, 1, 0, 4);
        for (int numHaradrim = MathHelper.getRandomIntegerInRange(random, 3, 8), l = 0; l < numHaradrim; ++l) {
            final LOTREntityGulfHaradrim haradrim = new LOTREntityGulfHaradrim(world);
            this.spawnNPCAndSetHome(haradrim, world, random.nextBoolean() ? -5 : 5, 1, 0, 16);
        }
        final int maxSteps = 12;
        for (int i5 = -1; i5 <= 1; ++i5) {
            for (int step = 0; step < 12; ++step) {
                final int j3 = 0 - step;
                final int k5 = -10 - step;
                if (this.isOpaque(world, i5, j3, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j3, k5, LOTRMod.stairsRedSandstone, 2);
                this.setGrassToDirt(world, i5, j3 - 1, k5);
                for (int j4 = j3 - 1; !this.isOpaque(world, i5, j4, k5) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i5, j4, k5, LOTRMod.redSandstone, 0);
                    this.setGrassToDirt(world, i5, j4 - 1, k5);
                }
            }
        }
        for (int i5 = -1; i5 <= 1; ++i5) {
            for (int step = 0; step < 12; ++step) {
                final int j3 = 0 - step;
                final int k5 = 10 + step;
                if (this.isOpaque(world, i5, j3, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j3, k5, LOTRMod.stairsRedSandstone, 3);
                this.setGrassToDirt(world, i5, j3 - 1, k5);
                for (int j4 = j3 - 1; !this.isOpaque(world, i5, j4, k5) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i5, j4, k5, LOTRMod.redSandstone, 0);
                    this.setGrassToDirt(world, i5, j4 - 1, k5);
                }
            }
        }
        return true;
    }
    
    private void placeFoodOrDrink(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.GULF_HARAD_DRINK);
            }
            else {
                final Block plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
                if (random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
                }
                else {
                    this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.GULF_HARAD);
                }
            }
        }
    }
}
