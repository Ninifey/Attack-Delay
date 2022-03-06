// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityBlackUruk;
import lotr.common.entity.npc.LOTREntityBlackUrukArcher;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityBlackUrukCaptain;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenBlackUrukFort extends LOTRWorldGenMordorStructure
{
    public LOTRWorldGenBlackUrukFort(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 19);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -18; i2 <= 26; ++i2) {
                for (int k2 = -18; k2 <= 20; ++k2) {
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
                    if (maxHeight - minHeight > 16) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -17; i3 <= 25; ++i3) {
            for (int k3 = -18; k3 <= 19; ++k3) {
                for (int j3 = 1; j3 <= 16; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("black_uruk_fort");
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("BRICK_CARVED", super.brickCarvedBlock, super.brickCarvedMeta);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
        this.associateBlockMetaAlias("SMOOTH", super.smoothBlock, super.smoothMeta);
        this.associateBlockMetaAlias("SMOOTH_SLAB", super.smoothSlabBlock, super.smoothSlabMeta);
        this.associateBlockMetaAlias("TILE", super.tileBlock, super.tileMeta);
        this.associateBlockMetaAlias("TILE_SLAB", super.tileSlabBlock, super.tileSlabMeta);
        this.associateBlockMetaAlias("TILE_SLAB_INV", super.tileSlabBlock, super.tileSlabMeta | 0x8);
        this.associateBlockAlias("TILE_STAIR", super.tileStairBlock);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("BEAM", super.beamBlock, super.beamMeta);
        this.associateBlockMetaAlias("BEAM|4", super.beamBlock, super.beamMeta | 0x4);
        this.associateBlockMetaAlias("BEAM|8", super.beamBlock, super.beamMeta | 0x8);
        this.addBlockMetaAliasOption("GROUND", 6, LOTRMod.rock, 0);
        this.addBlockMetaAliasOption("GROUND", 2, LOTRMod.mordorDirt, 0);
        this.addBlockMetaAliasOption("GROUND", 2, LOTRMod.mordorGravel, 0);
        this.associateBlockAlias("GATE_IRON", super.gateIronBlock);
        this.associateBlockAlias("GATE_ORC", super.gateOrcBlock);
        this.associateBlockMetaAlias("BARS", super.barsBlock, 0);
        this.associateBlockMetaAlias("CHANDELIER", super.chandelierBlock, super.chandelierMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -2, 5, -17, LOTRItemBanner.BannerType.BLACK_URUK, 2);
        this.placeWallBanner(world, 2, 5, -17, LOTRItemBanner.BannerType.BLACK_URUK, 2);
        this.placeWallBanner(world, 0, 14, -20, LOTRItemBanner.BannerType.MORDOR, 2);
        this.setBlockAndMetadata(world, -2, 11, -15, LOTRMod.commandTable, 0);
        this.placeOrcTorch(world, -3, 2, -18);
        this.placeOrcTorch(world, 3, 2, -18);
        this.placeOrcTorch(world, 19, 2, -11);
        this.placeOrcTorch(world, -3, 2, -10);
        this.placeOrcTorch(world, 3, 2, -10);
        this.placeOrcTorch(world, 2, 2, 4);
        this.placeOrcTorch(world, 5, 2, 4);
        this.placeOrcTorch(world, -11, 2, 12);
        this.placeOrcTorch(world, 18, 4, -13);
        this.placeOrcTorch(world, 10, 4, -13);
        this.placeOrcTorch(world, 21, 4, -10);
        this.placeOrcTorch(world, 21, 4, -2);
        this.placeOrcTorch(world, 24, 4, 1);
        this.placeOrcTorch(world, 20, 4, 1);
        this.placeOrcTorch(world, -13, 4, 3);
        this.placeOrcTorch(world, 5, 4, 9);
        this.placeOrcTorch(world, 2, 4, 9);
        this.placeOrcTorch(world, -13, 4, 11);
        this.placeOrcTorch(world, 11, 4, 14);
        this.placeOrcTorch(world, -4, 4, 14);
        this.placeOrcTorch(world, -10, 4, 14);
        this.placeOrcTorch(world, 20, 4, 17);
        this.placeOrcTorch(world, 22, 10, -14);
        this.placeOrcTorch(world, 22, 15, -14);
        this.placeWeaponRack(world, 9, 2, 2, 6, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 10, 2, 3, 5, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 8, 2, 3, 7, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 9, 2, 4, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 15, 2, 7, 6, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 16, 2, 8, 5, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 14, 2, 8, 7, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 15, 2, 9, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 6, 5, 10, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 1, 5, 10, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 10, 5, 12, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, -3, 5, 12, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 6, 6, 10, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 1, 6, 10, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 10, 6, 12, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, -3, 6, 12, 4, this.getRandomUrukWeapon(random));
        this.placeArmorStand(world, 15, 1, -9, 2, new ItemStack[] { new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondorGambeson), null, null });
        this.placeUrukArmor(world, random, 8, 4, 10, 2);
        this.placeUrukArmor(world, random, -1, 4, 10, 2);
        this.placeUrukArmor(world, random, 7, 4, 12, 3);
        this.placeUrukArmor(world, random, 0, 4, 12, 1);
        this.placeUrukArmor(world, random, 6, 4, 13, 2);
        this.placeUrukArmor(world, random, 1, 4, 13, 2);
        this.placeChest(world, random, 15, 1, 7, 2, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, 9, 4, 11, 3, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, -2, 4, 11, 3, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, 12, 4, 13, 5, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, -5, 4, 13, 4, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, 12, 4, 17, 5, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, -5, 4, 17, 4, LOTRChestContents.BLACK_URUK_FORT);
        for (int j4 = 4; j4 <= 5; ++j4) {
            for (final int i4 : new int[] { -3, -1, 1 }) {
                this.setBlockAndMetadata(world, i4, j4, 17, super.bedBlock, 0);
                this.setBlockAndMetadata(world, i4, j4, 18, super.bedBlock, 8);
            }
            for (final int i4 : new int[] { 6, 8, 10 }) {
                this.setBlockAndMetadata(world, i4, j4, 17, super.bedBlock, 0);
                this.setBlockAndMetadata(world, i4, j4, 18, super.bedBlock, 8);
            }
        }
        this.placeBarrel(world, random, -11, 5, -3, 3, LOTRFoods.ORC_DRINK);
        this.placeBarrel(world, random, -13, 5, -3, 3, LOTRFoods.ORC_DRINK);
        for (int k4 = -7; k4 <= -4; ++k4) {
            for (final int i4 : new int[] { -13, -11 }) {
                if (random.nextBoolean()) {
                    this.placePlate(world, random, i4, 5, k4, LOTRMod.woodPlateBlock, LOTRFoods.ORC);
                }
                else {
                    this.placeMug(world, random, i4, 5, k4, random.nextInt(4), LOTRFoods.ORC_DRINK);
                }
            }
        }
        final LOTREntityBlackUrukCaptain captain = new LOTREntityBlackUrukCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 8);
        for (int uruks = 12, l = 0; l < uruks; ++l) {
            final LOTREntityBlackUruk uruk = (random.nextInt(3) == 0) ? new LOTREntityBlackUrukArcher(world) : new LOTREntityBlackUruk(world);
            uruk.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(uruk, world, 0, 1, 0, 32);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityBlackUruk.class, LOTREntityBlackUrukArcher.class);
        respawner.setCheckRanges(32, -16, 20, 24);
        respawner.setSpawnRanges(24, -4, 8, 24);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    protected ItemStack getRandomUrukWeapon(final Random random) {
        final ItemStack[] items = { new ItemStack(LOTRMod.scimitarBlackUruk), new ItemStack(LOTRMod.daggerBlackUruk), new ItemStack(LOTRMod.daggerBlackUrukPoisoned), new ItemStack(LOTRMod.spearBlackUruk), new ItemStack(LOTRMod.battleaxeBlackUruk), new ItemStack(LOTRMod.hammerBlackUruk), new ItemStack(LOTRMod.blackUrukBow) };
        return items[random.nextInt(items.length)].copy();
    }
    
    private void placeUrukArmor(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        ItemStack[] armor;
        if (random.nextInt(4) != 0) {
            armor = new ItemStack[] { null, null, null, null };
        }
        else {
            armor = new ItemStack[] { new ItemStack(LOTRMod.helmetBlackUruk), new ItemStack(LOTRMod.bodyBlackUruk), new ItemStack(LOTRMod.legsBlackUruk), new ItemStack(LOTRMod.bootsBlackUruk) };
        }
        this.placeArmorStand(world, i, j, k, meta, armor);
    }
}
