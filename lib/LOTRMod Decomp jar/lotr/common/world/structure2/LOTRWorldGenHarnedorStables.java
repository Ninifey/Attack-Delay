// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorStables extends LOTRWorldGenHarnedorStructure
{
    public LOTRWorldGenHarnedorStables(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -7; i2 <= 7; ++i2) {
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
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int k3 = -10; k3 <= 10; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 <= 5 && k4 <= 4) || (i4 <= 4 && k4 <= 6) || (i4 <= 3 && k4 <= 7) || (i4 <= 2 && k4 <= 8) || (i4 <= 1 && k4 <= 9)) {
                    for (int j2 = 1; j2 <= 6; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("harnedor_stables");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.addBlockMetaAliasOption("GROUND", 5, (Block)Blocks.grass, 0);
        this.addBlockMetaAliasOption("GROUND", 4, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 1, (Block)Blocks.sand, 0);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -2, 4, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, 2, 4, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, -2, 4, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        this.placeWallBanner(world, 2, 4, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        this.spawnItemFrame(world, -2, 2, 0, 1, new ItemStack(Items.saddle));
        this.spawnItemFrame(world, 2, 2, 0, 3, new ItemStack(Items.lead));
        this.setBlockAndMetadata(world, -3, 1, 6, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 7, super.bedBlock, 8);
        this.placeChest(world, random, -4, 1, 6, LOTRMod.chestBasket, 4, LOTRChestContents.HARNEDOR_HOUSE);
        this.placePlateWithCertainty(world, random, 4, 2, 6, LOTRMod.woodPlateBlock, LOTRFoods.HARNEDOR);
        this.placeMug(world, random, 4, 2, 5, 1, LOTRFoods.HARNEDOR_DRINK);
        final LOTREntityHarnedhrim harnedhrim = new LOTREntityHarnedhrim(world);
        this.spawnNPCAndSetHome(harnedhrim, world, 0, 1, 0, 12);
        for (final int k5 : new int[] { -2, 2 }) {
            for (final int i5 : new int[] { -4, 4 }) {
                final int j3 = 1;
                final LOTREntityHorse horse = new LOTREntityHorse(world);
                this.spawnNPCAndSetHome((EntityCreature)horse, world, i5, j3, k5, 0);
                horse.setHorseType(0);
                horse.saddleMountForWorldGen();
                horse.detachHome();
            }
        }
        return true;
    }
}
