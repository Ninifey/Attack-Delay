// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTREntityHarnedorArcher;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedorWarlord;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorFort extends LOTRWorldGenHarnedorStructure
{
    public LOTRWorldGenHarnedorFort(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 12);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -15; i2 <= 15; ++i2) {
                for (int k2 = -15; k2 <= 15; ++k2) {
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
                final boolean bedRegion = (i4 <= 3 && k3 >= 5 && k3 <= 9) || (i4 <= 2 && k3 == 4) || (i4 <= 1 && k3 == 3);
                for (int airHeight = 7, j3 = 0; j3 <= airHeight; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
                for (int j3 = 0; (j3 >= -1 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                    if (!bedRegion || j3 != 0) {
                        if (j3 == 0) {
                            if (i4 <= 11 && k4 <= 11) {
                                if (random.nextBoolean()) {
                                    this.setBlockAndMetadata(world, i3, j3, k3, LOTRMod.dirtPath, 0);
                                }
                                else {
                                    final int randomGround = random.nextInt(3);
                                    if (randomGround == 0) {
                                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)Blocks.grass, 0);
                                    }
                                    else if (randomGround == 1) {
                                        this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 1);
                                    }
                                    else if (randomGround == 2) {
                                        this.setBlockAndMetadata(world, i3, j3, k3, (Block)Blocks.sand, 0);
                                    }
                                }
                            }
                            else {
                                final int randomGround = random.nextInt(3);
                                if (randomGround == 0) {
                                    this.setBlockAndMetadata(world, i3, j3, k3, (Block)Blocks.grass, 0);
                                }
                                else if (randomGround == 1) {
                                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 1);
                                }
                                else if (randomGround == 2) {
                                    this.setBlockAndMetadata(world, i3, j3, k3, (Block)Blocks.sand, 0);
                                }
                            }
                        }
                        else {
                            this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                        }
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                }
                if (!bedRegion && i4 <= 10 && k4 <= 10 && random.nextInt(5) == 0) {
                    this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                }
            }
        }
        this.loadStrScan("harnedor_fort");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", super.woodBlock, super.woodMeta | 0x4);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        this.associateBlockMetaAlias("PLANK2_SLAB", super.plank2SlabBlock, super.plank2SlabMeta);
        this.associateBlockMetaAlias("PLANK2_SLAB_INV", super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        this.associateBlockMetaAlias("BONE", super.boneBlock, super.boneMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, 2, 0, 8, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 2, 0, 9, super.bedBlock, 8);
        this.setBlockAndMetadata(world, 10, 1, 7, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 10, 1, 8, super.bedBlock, 8);
        this.setBlockAndMetadata(world, 7, 1, 10, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 8, 1, 10, super.bedBlock, 9);
        this.setBlockAndMetadata(world, -10, 1, 7, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -10, 1, 8, super.bedBlock, 8);
        this.setBlockAndMetadata(world, -7, 1, 10, super.bedBlock, 3);
        this.setBlockAndMetadata(world, -8, 1, 10, super.bedBlock, 11);
        this.placeChest(world, random, 0, 0, 7, LOTRMod.chestBasket, 3, LOTRChestContents.HARNEDOR_HOUSE);
        this.placeChest(world, random, -9, 1, 9, LOTRMod.chestBasket, 2, LOTRChestContents.HARNEDOR_HOUSE);
        this.placeChest(world, random, 9, 1, 9, LOTRMod.chestBasket, 2, LOTRChestContents.HARNEDOR_HOUSE);
        for (int i3 = -2; i3 <= 0; ++i3) {
            final int j4 = 1;
            final int k5 = 9;
            if (random.nextBoolean()) {
                this.placePlate(world, random, i3, j4, k5, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
            }
            else {
                this.placeMug(world, random, i3, j4, k5, 0, LOTRFoods.HARNEDOR_DRINK);
            }
        }
        this.placeWeaponRack(world, 4, 2, -1, 6, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, 5, 2, 0, 5, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, 4, 2, 1, 4, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, 3, 2, 0, 7, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, -4, 2, -1, 6, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, -3, 2, 0, 5, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, -4, 2, 1, 4, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, -5, 2, 0, 7, this.getRandomHarnedorWeapon(random));
        this.placeHarnedorArmor(world, random, 9, 1, -3, 1);
        this.placeHarnedorArmor(world, random, 9, 1, 0, 1);
        this.placeHarnedorArmor(world, random, 9, 1, 3, 1);
        this.placeSkull(world, random, -8, 3, -4);
        this.placeSkull(world, random, -8, 3, 2);
        this.placeSkull(world, random, -10, 6, 0);
        this.placeSkull(world, random, 10, 6, 0);
        this.placeSkull(world, random, -13, 8, -15);
        this.placeSkull(world, random, 13, 8, -15);
        this.placeSkull(world, random, -13, 8, 15);
        this.placeSkull(world, random, 13, 8, 15);
        this.placeWallBanner(world, 0, 5, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, -3, 4, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, 3, 4, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, 0, 6, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.setBlockAndMetadata(world, 7, 1, 5, LOTRMod.commandTable, 0);
        final LOTREntityHarnedorWarlord warlord = new LOTREntityHarnedorWarlord(world);
        warlord.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(warlord, world, 0, 3, 7, 4);
        for (int warriors = 4 + random.nextInt(4), l = 0; l < warriors; ++l) {
            final LOTREntityHarnedhrim warrior = (random.nextInt(3) == 0) ? new LOTREntityHarnedorArcher(world) : new LOTREntityHarnedorWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 1, 0, 16);
        }
        for (final int i5 : new int[] { -4, 4 }) {
            final int j3 = 1;
            final int k6 = -6;
            final LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome((EntityCreature)horse, world, i5, j3, k6, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
            this.leashEntityTo((EntityCreature)horse, world, i5, j3, k6);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
        respawner.setCheckRanges(16, -8, 12, 12);
        respawner.setSpawnRanges(8, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    private void placeHarnedorArmor(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        ItemStack[] armor;
        if (random.nextInt(3) != 0) {
            armor = new ItemStack[] { null, null, null, null };
        }
        else {
            armor = new ItemStack[] { new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsHarnedor), new ItemStack(LOTRMod.bootsHarnedor) };
        }
        this.placeArmorStand(world, i, j, k, meta, armor);
    }
}
