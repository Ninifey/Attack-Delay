// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDunlendingWarrior;
import lotr.common.entity.npc.LOTREntityDunlendingArcher;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDunlendingWarlord;
import lotr.common.entity.item.LOTREntityRugBase;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.item.LOTREntityBearRug;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.animal.LOTREntityCrebain;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDunlandHillFort extends LOTRWorldGenDunlandStructure
{
    public LOTRWorldGenDunlandHillFort(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 10);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -12; i2 <= 12; ++i2) {
                for (int k2 = -12; k2 <= 12; ++k2) {
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
        for (int i3 = -11; i3 <= 11; ++i3) {
            for (int k3 = -11; k3 <= 11; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 1; j2 <= 8; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if ((i4 <= 8 && k4 <= 8) || (i4 <= 1 && k3 < 0)) {
                    final int randomGround = random.nextInt(3);
                    if (randomGround == 0) {
                        this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.grass, 0);
                    }
                    else if (randomGround == 1) {
                        this.setBlockAndMetadata(world, i3, 0, k3, Blocks.dirt, 1);
                    }
                    else if (randomGround == 2) {
                        this.setBlockAndMetadata(world, i3, 0, k3, LOTRMod.dirtPath, 0);
                    }
                    if ((i4 > 3 || k3 < -3 || k3 > 2) && random.nextInt(5) == 0) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i3, 0, k3, Blocks.cobblestone, 0);
                }
                this.setGrassToDirt(world, i3, -1, k3);
                for (int j2 = -1; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, Blocks.cobblestone, 0);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
            }
        }
        this.loadStrScan("dunland_fort");
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
        this.setBlockAndMetadata(world, 8, 1, 5, super.bedBlock, 9);
        this.setBlockAndMetadata(world, 7, 1, 5, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 7, 1, 7, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 7, 1, 8, super.bedBlock, 8);
        this.setBlockAndMetadata(world, 5, 1, 7, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 5, 1, 8, super.bedBlock, 8);
        this.placeChest(world, random, 5, 1, 5, LOTRMod.chestBasket, 3, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeChest(world, random, -4, 1, 8, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeChest(world, random, 6, 1, -8, (Block)Blocks.chest, 3, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeChest(world, random, 5, 1, -8, (Block)Blocks.chest, 3, LOTRChestContents.DUNLENDING_HOUSE);
        for (int i3 = -6; i3 <= -5; ++i3) {
            final int j3 = 1;
            final int k5 = 8;
            if (random.nextBoolean()) {
                this.placeArmorStand(world, i3, j3, k5, 0, new ItemStack[] { new ItemStack(LOTRMod.helmetDunlending), new ItemStack(LOTRMod.bodyDunlending), new ItemStack(LOTRMod.legsDunlending), new ItemStack(LOTRMod.bootsDunlending) });
            }
            else {
                this.placeArmorStand(world, i3, j3, k5, 0, new ItemStack[] { new ItemStack(LOTRMod.helmetFur), new ItemStack(LOTRMod.bodyFur), new ItemStack(LOTRMod.legsFur), new ItemStack(LOTRMod.bootsFur) });
            }
        }
        this.placeWeaponRack(world, -7, 2, -3, 5, this.getRandomDunlandWeapon(random));
        this.placeBarrel(world, random, 8, 2, 7, 2, LOTRFoods.DUNLENDING_DRINK);
        this.placeSkull(world, random, -2, 7, -11);
        this.placeSkull(world, random, 2, 7, -11);
        this.placeSkull(world, random, -11, 7, 2);
        this.placeSkull(world, random, 3, 7, 8);
        this.placeSkull(world, random, 11, 8, -3);
        this.placeAnimalJar(world, 8, 2, -6, LOTRMod.birdCageWood, 0, new LOTREntityCrebain(world));
        this.setBlockAndMetadata(world, 6, 1, -3, LOTRMod.commandTable, 0);
        this.placeWallBanner(world, -2, 5, -11, LOTRItemBanner.BannerType.DUNLAND, 2);
        this.placeWallBanner(world, 2, 5, -11, LOTRItemBanner.BannerType.DUNLAND, 2);
        this.placeWallBanner(world, -8, 4, 0, LOTRItemBanner.BannerType.DUNLAND, 1);
        this.placeWallBanner(world, 8, 4, 0, LOTRItemBanner.BannerType.DUNLAND, 3);
        final LOTREntityBearRug rug = new LOTREntityBearRug(world);
        final LOTREntityBear.BearType[] bearTypes = { LOTREntityBear.BearType.LIGHT, LOTREntityBear.BearType.DARK, LOTREntityBear.BearType.BLACK };
        rug.setRugType(bearTypes[random.nextInt(bearTypes.length)]);
        this.placeRug(rug, world, -5, 1, -4, -45.0f);
        final LOTREntityDunlendingWarlord warlord = new LOTREntityDunlendingWarlord(world);
        this.spawnNPCAndSetHome(warlord, world, 0, 1, 2, 8);
        for (int warriors = 6, l = 0; l < warriors; ++l) {
            final LOTREntityDunlendingWarrior warrior = (random.nextInt(3) == 0) ? new LOTREntityDunlendingArcher(world) : new LOTREntityDunlendingWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 1, 2, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityDunlendingWarrior.class, LOTREntityDunlendingArcher.class);
        respawner.setCheckRanges(20, -8, 12, 12);
        respawner.setSpawnRanges(6, -1, 4, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
}
