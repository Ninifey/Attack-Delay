// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.entity.npc.LOTREntityGulfHaradArcher;
import lotr.common.entity.npc.LOTREntityGulfHaradWarlord;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfWarCamp extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfWarCamp(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 15);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -16; i2 <= 16; ++i2) {
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
                    if (maxHeight - minHeight > 12) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -15; i3 <= 15; ++i3) {
            for (int k3 = -15; k3 <= 15; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 >= -1 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    if (j2 == 0) {
                        if (i4 <= 14 && k4 <= 14) {
                            if (random.nextBoolean()) {
                                this.setBlockAndMetadata(world, i3, j2, k3, LOTRMod.dirtPath, 0);
                            }
                            else {
                                final int randomGround = random.nextInt(3);
                                if (randomGround == 0) {
                                    this.setBlockAndMetadata(world, i3, j2, k3, (Block)Blocks.grass, 0);
                                }
                                else if (randomGround == 1) {
                                    this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 1);
                                }
                                else if (randomGround == 2) {
                                    this.setBlockAndMetadata(world, i3, j2, k3, (Block)Blocks.sand, 1);
                                }
                            }
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, j2, k3, (Block)Blocks.grass, 0);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 0);
                    }
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                int airHeight = 6;
                if (i4 <= 4 && k4 <= 4) {
                    airHeight = 15;
                }
                for (int j3 = 1; j3 <= airHeight; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                if (i4 <= 12 && k4 <= 12 && random.nextInt(5) == 0) {
                    this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                }
            }
        }
        this.loadStrScan("gulf_war_camp");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", super.woodBlock, super.woodMeta | 0x4);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.associateBlockMetaAlias("FLAG", super.flagBlock, super.flagMeta);
        this.associateBlockMetaAlias("BONE", super.boneBlock, super.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        for (int i3 = -13; i3 <= -9; i3 += 2) {
            this.setBlockAndMetadata(world, i3, 1, 12, super.bedBlock, 0);
            this.setBlockAndMetadata(world, i3, 1, 13, super.bedBlock, 8);
        }
        for (int i3 = 9; i3 <= 13; i3 += 2) {
            this.setBlockAndMetadata(world, i3, 1, 12, super.bedBlock, 0);
            this.setBlockAndMetadata(world, i3, 1, 13, super.bedBlock, 8);
        }
        this.placeChest(world, random, -12, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeChest(world, random, -10, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeChest(world, random, 10, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeChest(world, random, 12, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeChest(world, random, -1, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeGulfArmor(world, random, -11, 1, -13, 2);
        this.placeGulfArmor(world, random, -9, 1, -13, 2);
        this.placeGulfArmor(world, random, -13, 1, -11, 3);
        this.placeGulfArmor(world, random, -13, 1, -9, 3);
        this.placeGulfArmor(world, random, 9, 1, -13, 2);
        this.placeGulfArmor(world, random, 11, 1, -13, 2);
        this.placeGulfArmor(world, random, 13, 1, -11, 1);
        this.placeGulfArmor(world, random, 13, 1, -9, 1);
        this.placeWeaponRack(world, -8, 2, -9, 6, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, -9, 2, -8, 7, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, -7, 2, -8, 5, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, -8, 2, -7, 4, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, 8, 2, -9, 6, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, 7, 2, -8, 7, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, 9, 2, -8, 5, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, 8, 2, -7, 4, this.getRandomGulfWeapon(random));
        this.placeSkull(world, random, -12, 3, -2);
        this.placeSkull(world, random, -12, 3, 2);
        this.placeWeaponRack(world, 11, 2, -4, 7, new ItemStack(LOTRMod.nearHaradBow));
        this.placeWeaponRack(world, 11, 2, 4, 7, new ItemStack(LOTRMod.nearHaradBow));
        this.placeBarrel(world, random, -13, 2, 9, 3, LOTRFoods.GULF_HARAD_DRINK);
        this.placeBarrel(world, random, 13, 2, 9, 3, LOTRFoods.GULF_HARAD_DRINK);
        this.placeWallBanner(world, 0, 6, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -2, 5, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 2, 5, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -4, 4, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 4, 4, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, 5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, -5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, -5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, 5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        this.placeWallBanner(world, 5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        for (final int i5 : new int[] { -2, 2 }) {
            final int j2 = 1;
            final int k5 = 12;
            final LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome((EntityCreature)horse, world, i5, j2, k5, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
            this.leashEntityTo((EntityCreature)horse, world, i5, j2, k5);
        }
        final LOTREntityGulfHaradWarlord warlord = new LOTREntityGulfHaradWarlord(world);
        warlord.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(warlord, world, 0, 9, -3, 6);
        this.setBlockAndMetadata(world, 0, 9, 3, LOTRMod.commandTable, 0);
        for (int warriors = 6, l = 0; l < warriors; ++l) {
            final LOTREntityGulfHaradWarrior warrior = (random.nextInt(3) == 0) ? new LOTREntityGulfHaradArcher(world) : new LOTREntityGulfHaradWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 1, -1, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
        respawner.setCheckRanges(32, -8, 12, 24);
        respawner.setSpawnRanges(24, -4, 6, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    private void placeGulfArmor(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        ItemStack[] armor;
        if (random.nextInt(3) != 0) {
            armor = new ItemStack[] { null, null, null, null };
        }
        else {
            armor = new ItemStack[] { new ItemStack(LOTRMod.helmetGulfHarad), new ItemStack(LOTRMod.bodyGulfHarad), new ItemStack(LOTRMod.legsGulfHarad), new ItemStack(LOTRMod.bootsGulfHarad) };
        }
        this.placeArmorStand(world, i, j, k, meta, armor);
    }
}
