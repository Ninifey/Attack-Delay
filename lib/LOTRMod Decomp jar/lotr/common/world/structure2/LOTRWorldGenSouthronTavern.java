// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.block.Block;
import lotr.common.entity.npc.LOTREntitySouthronBartender;
import lotr.common.entity.npc.LOTREntityUmbarBartender;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.init.Blocks;
import net.minecraft.entity.EntityCreature;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTavern extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronTavern(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 16);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 6; ++i2) {
                for (int k2 = -16; k2 <= 16; ++k2) {
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
                    if (maxHeight - minHeight > 10) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k3 = -15; k3 <= 15; ++k3) {
                for (int j3 = 0; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.stoneBlock, super.stoneMeta);
                    this.setGrassToDirt(world, i3, j3 - 1, k3);
                }
                for (int j3 = 1; j3 <= 9; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("southron_tavern");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", super.woodBeamBlock, super.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", super.woodBeamBlock, super.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", super.woodBeamBlock, super.woodBeamMeta8);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        final String[] tavernName = LOTRNames.getHaradTavernName(random);
        final String tavernNameNPC = tavernName[0] + " " + tavernName[1];
        this.placeWeaponRack(world, 4, 3, -4, 7, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -4, 3, 4, 5, this.getRandomHaradWeapon(random));
        this.spawnItemFrame(world, 5, 3, -8, 3, this.getRandomHaradItem(random));
        this.spawnItemFrame(world, -5, 3, -4, 1, this.getRandomHaradItem(random));
        this.spawnItemFrame(world, 5, 3, 4, 3, this.getRandomHaradItem(random));
        this.placeFoodOrDrink(world, random, -2, 2, -12);
        this.placeFoodOrDrink(world, random, -2, 2, -11);
        for (int i2 = 0; i2 <= 2; ++i2) {
            for (int k2 = -9; k2 <= -7; ++k2) {
                this.placeFoodOrDrink(world, random, i2, 2, k2);
            }
        }
        this.placeFoodOrDrink(world, random, -2, 2, -5);
        this.placeFoodOrDrink(world, random, -2, 2, -4);
        this.placeFoodOrDrink(world, random, 1, 2, -4);
        this.placeFoodOrDrink(world, random, 2, 2, -4);
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = 1; k2 <= 3; ++k2) {
                if (i2 == 0 && k2 == 2) {
                    this.setBlockAndMetadata(world, i2, 2, k2, LOTRMod.lemonCake, 0);
                }
                else {
                    this.placeFoodOrDrink(world, random, i2, 2, k2);
                }
            }
        }
        this.placeFoodOrDrink(world, random, -3, 2, 7);
        this.placeFoodOrDrink(world, random, -2, 2, 7);
        this.placeFoodOrDrink(world, random, -1, 2, 7);
        this.placeKebabStand(world, random, -4, 2, 9, LOTRMod.kebabStand, 4);
        this.placeChest(world, random, 3, 1, 14, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
        this.placeBarrel(world, random, 4, 2, 11, 5, LOTRFoods.SOUTHRON_DRINK);
        this.placeBarrel(world, random, 4, 2, 12, 5, LOTRFoods.SOUTHRON_DRINK);
        this.setBlockAndMetadata(world, -3, 8, -13, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -3, 8, -14, super.bedBlock, 10);
        this.setBlockAndMetadata(world, -4, 8, -13, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -4, 8, -14, super.bedBlock, 10);
        this.placeFlowerPot(world, -1, 9, -14, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -3, 8, -5, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 8, -4, super.bedBlock, 8);
        this.setBlockAndMetadata(world, -4, 8, -5, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -4, 8, -4, super.bedBlock, 8);
        this.placeFlowerPot(world, -1, 9, -4, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -3, 8, -1, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -3, 8, -2, super.bedBlock, 10);
        this.setBlockAndMetadata(world, -4, 8, -1, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -4, 8, -2, super.bedBlock, 10);
        this.placeFlowerPot(world, -1, 9, -2, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -3, 8, 7, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 8, 8, super.bedBlock, 8);
        this.setBlockAndMetadata(world, -4, 8, 7, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -4, 8, 8, super.bedBlock, 8);
        this.placeFlowerPot(world, -1, 9, 8, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, 1, 9, -3, this.getRandomFlower(world, random));
        this.placeWallBanner(world, -2, 5, -15, super.bannerType, 0);
        this.placeWallBanner(world, 2, 5, -15, super.bannerType, 0);
        final LOTREntityNearHaradrimBase bartender = this.createBartender(world);
        bartender.setSpecificLocationName(tavernNameNPC);
        this.spawnNPCAndSetHome(bartender, world, -2, 1, 8, 4);
        for (int haradrim = 4 + random.nextInt(10), l = 0; l < haradrim; ++l) {
            final LOTREntityNearHaradrimBase southron = this.createHaradrim(world);
            this.spawnNPCAndSetHome(southron, world, 0, 1, 0, 16);
        }
        for (int i4 = -1; i4 <= 1; ++i4) {
            int j4 = 0;
            for (int step = 0; step < 12; ++step) {
                final int k4 = -17 - step;
                if (this.isOpaque(world, i4, j4 + 1, k4)) {
                    ++j4;
                    this.setAir(world, i4, j4 + 1, k4);
                    this.setAir(world, i4, j4 + 2, k4);
                    this.setAir(world, i4, j4 + 3, k4);
                    this.setBlockAndMetadata(world, i4, j4, k4, super.stoneStairBlock, 3);
                    this.setGrassToDirt(world, i4, j4 - 1, k4);
                    for (int j5 = j4 - 1; !this.isOpaque(world, i4, j5, k4) && this.getY(j5) >= 0; --j5) {
                        this.setBlockAndMetadata(world, i4, j5, k4, super.stoneBlock, super.stoneMeta);
                        this.setGrassToDirt(world, i4, j5 - 1, k4);
                    }
                }
                else {
                    if (this.isOpaque(world, i4, j4, k4)) {
                        break;
                    }
                    this.setAir(world, i4, j4 + 1, k4);
                    this.setAir(world, i4, j4 + 2, k4);
                    this.setAir(world, i4, j4 + 3, k4);
                    this.setBlockAndMetadata(world, i4, j4, k4, super.stoneStairBlock, 2);
                    this.setGrassToDirt(world, i4, j4 - 1, k4);
                    for (int j5 = j4 - 1; !this.isOpaque(world, i4, j5, k4) && this.getY(j5) >= 0; --j5) {
                        this.setBlockAndMetadata(world, i4, j5, k4, super.stoneBlock, super.stoneMeta);
                        this.setGrassToDirt(world, i4, j5 - 1, k4);
                    }
                    --j4;
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 5, -16, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 5, -17, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 4, -17, super.plankBlock, super.plankMeta);
        this.placeSign(world, -1, 4, -17, Blocks.wall_sign, 5, new String[] { "", tavernName[0], tavernName[1], "" });
        this.placeSign(world, 0, 4, -18, Blocks.wall_sign, 2, new String[] { "", tavernName[0], tavernName[1], "" });
        this.placeSign(world, 1, 4, -17, Blocks.wall_sign, 4, new String[] { "", tavernName[0], tavernName[1], "" });
        return true;
    }
    
    protected LOTREntityNearHaradrimBase createBartender(final World world) {
        if (this.isUmbar()) {
            return new LOTREntityUmbarBartender(world);
        }
        return new LOTREntitySouthronBartender(world);
    }
    
    private void placeFoodOrDrink(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.SOUTHRON_DRINK);
            }
            else {
                final Block plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
                if (random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
                }
                else {
                    this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.SOUTHRON);
                }
            }
        }
    }
}
