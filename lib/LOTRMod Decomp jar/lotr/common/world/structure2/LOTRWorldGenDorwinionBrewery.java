// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDorwinionElfVintner;
import lotr.common.item.LOTRItemMug;
import net.minecraft.util.MathHelper;
import net.minecraft.tileentity.TileEntityChest;
import lotr.common.LOTRFoods;
import com.google.common.math.IntMath;
import lotr.common.item.LOTRItemBanner;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionBrewery extends LOTRWorldGenDorwinionHouse
{
    public LOTRWorldGenDorwinionBrewery(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -6; i2 <= 6; ++i2) {
                for (int k2 = 0; k2 <= 19; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (block != Blocks.grass) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -6; i2 <= 6; ++i2) {
            for (int k2 = 0; k2 <= 19; ++k2) {
                this.setBlockAndMetadata(world, i2, 0, k2, (Block)Blocks.grass, 0);
                for (int j2 = -1; !this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                for (int j2 = 1; j2 <= 10; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
                if (i2 >= -5 && i2 <= 5 && k2 >= 1 && k2 <= 18) {
                    if ((i2 == -5 || i2 == 5) && (k2 == 1 || k2 == 18)) {
                        for (int j2 = 0; j2 <= 5; ++j2) {
                            this.setBlockAndMetadata(world, i2, j2, k2, super.woodBeamBlock, super.woodBeamMeta);
                        }
                    }
                    else if (i2 == -5 || i2 == 5 || k2 == 1 || k2 == 18) {
                        for (int j2 = 0; j2 <= 5; ++j2) {
                            this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                        }
                    }
                    else if (i2 >= -2 && i2 <= 2) {
                        this.setBlockAndMetadata(world, i2, 0, k2, super.floorBlock, super.floorMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 0, k2, super.plankBlock, super.plankMeta);
                    }
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 0, 1, super.floorBlock, super.floorMeta);
            for (int j3 = 1; j3 <= 3; ++j3) {
                this.setBlockAndMetadata(world, i2, j3, 1, LOTRMod.gateWooden, 2);
            }
        }
        for (int k3 = 2; k3 <= 17; ++k3) {
            if (k3 % 3 == 2) {
                this.setBlockAndMetadata(world, -6, 1, k3, super.brickStairBlock, 1);
                this.setGrassToDirt(world, -6, 0, k3);
                this.setBlockAndMetadata(world, 6, 1, k3, super.brickStairBlock, 0);
                this.setGrassToDirt(world, 6, 0, k3);
            }
            else {
                this.setBlockAndMetadata(world, -6, 1, k3, super.leafBlock, super.leafMeta);
                this.setBlockAndMetadata(world, 6, 1, k3, super.leafBlock, super.leafMeta);
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            if (Math.abs(i2) == 4) {
                this.setBlockAndMetadata(world, i2, 1, 19, super.brickStairBlock, 3);
                this.setGrassToDirt(world, i2, 0, 19);
            }
            else {
                this.setBlockAndMetadata(world, i2, 1, 19, super.leafBlock, super.leafMeta);
            }
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            if (Math.abs(i2) == 4 || Math.abs(i2) == 2) {
                this.setBlockAndMetadata(world, i2, 1, 0, super.brickStairBlock, 2);
                this.setGrassToDirt(world, i2, 0, 0);
            }
            else if (Math.abs(i2) == 3) {
                this.setBlockAndMetadata(world, i2, 1, 0, super.leafBlock, super.leafMeta);
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            this.setBlockAndMetadata(world, i2, 5, 0, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i2, 5, 19, super.brickStairBlock, 7);
        }
        for (int k3 = 0; k3 <= 19; ++k3) {
            if (k3 >= 3 && k3 <= 16) {
                if (k3 % 3 == 0) {
                    this.setAir(world, -5, 3, k3);
                    this.setBlockAndMetadata(world, -5, 4, k3, super.brickStairBlock, 7);
                    this.setAir(world, 5, 3, k3);
                    this.setBlockAndMetadata(world, 5, 4, k3, super.brickStairBlock, 7);
                }
                else if (k3 % 3 == 1) {
                    this.setAir(world, -5, 3, k3);
                    this.setBlockAndMetadata(world, -5, 4, k3, super.brickStairBlock, 6);
                    this.setAir(world, 5, 3, k3);
                    this.setBlockAndMetadata(world, 5, 4, k3, super.brickStairBlock, 6);
                }
            }
            this.setBlockAndMetadata(world, -6, 5, k3, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 5, k3, super.brickStairBlock, 4);
            if ((k3 <= 7 && k3 % 2 == 0) || (k3 >= 12 && k3 % 2 == 1)) {
                this.setBlockAndMetadata(world, -6, 6, k3, super.brickSlabBlock, super.brickSlabMeta);
                this.setBlockAndMetadata(world, 6, 6, k3, super.brickSlabBlock, super.brickSlabMeta);
            }
            if (k3 == 8 || k3 == 11) {
                this.setBlockAndMetadata(world, -6, 4, k3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                this.setBlockAndMetadata(world, -6, 5, k3, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, -6, 6, k3, super.brickSlabBlock, super.brickSlabMeta);
                this.setBlockAndMetadata(world, 6, 4, k3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                this.setBlockAndMetadata(world, 6, 5, k3, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, 6, 6, k3, super.brickSlabBlock, super.brickSlabMeta);
                this.placeWallBanner(world, -5, 3, k3, LOTRItemBanner.BannerType.DORWINION, 3);
                this.placeWallBanner(world, 5, 3, k3, LOTRItemBanner.BannerType.DORWINION, 1);
            }
            if (k3 == 9 || k3 == 10) {
                this.setBlockAndMetadata(world, -6, 6, k3, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, 6, 6, k3, super.brickBlock, super.brickMeta);
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            if (Math.abs(i2) == 3) {
                this.setBlockAndMetadata(world, i2, 2, 1, super.brickSlabBlock, super.brickSlabMeta);
                this.setAir(world, i2, 3, 1);
            }
            if (Math.abs(i2) == 2) {
                this.placeWallBanner(world, i2, 4, 1, LOTRItemBanner.BannerType.DORWINION, 2);
            }
            if (IntMath.mod(i2, 2) == 1) {
                this.setBlockAndMetadata(world, i2, 2, 18, super.brickSlabBlock, super.brickSlabMeta);
                this.setAir(world, i2, 3, 18);
            }
        }
        for (final int k4 : new int[] { 1, 18 }) {
            this.setBlockAndMetadata(world, -4, 6, k4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -3, 6, k4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -2, 6, k4, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, -1, 6, k4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 0, 6, k4, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, 1, 6, k4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 2, 6, k4, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, 3, 6, k4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 4, 6, k4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -3, 7, k4, super.brickBlock, super.brickMeta);
            this.setAir(world, -2, 7, k4);
            this.setBlockAndMetadata(world, -1, 7, k4, super.brickBlock, super.brickMeta);
            this.setAir(world, 0, 7, k4);
            this.setBlockAndMetadata(world, 1, 7, k4, super.brickBlock, super.brickMeta);
            this.setAir(world, 2, 7, k4);
            this.setBlockAndMetadata(world, 3, 7, k4, super.brickBlock, super.brickMeta);
            for (int i3 = -2; i3 <= 2; ++i3) {
                this.setBlockAndMetadata(world, i3, 8, k4, super.brickBlock, super.brickMeta);
            }
            for (int i3 = -1; i3 <= 1; ++i3) {
                this.setBlockAndMetadata(world, i3, 9, k4, super.brickBlock, super.brickMeta);
            }
            this.setBlockAndMetadata(world, 0, 10, k4, super.brickBlock, super.brickMeta);
        }
        for (int k3 = 2; k3 <= 17; ++k3) {
            this.setBlockAndMetadata(world, -4, 6, k3, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 7, k3, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -2, 8, k3, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 9, k3, super.plankStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 10, k3, super.plankBlock, super.plankMeta);
            this.setBlockAndMetadata(world, 1, 9, k3, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 2, 8, k3, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 7, k3, super.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 6, k3, super.plankStairBlock, 5);
        }
        for (int k3 = 0; k3 <= 19; ++k3) {
            this.setBlockAndMetadata(world, -5, 6, k3, super.clayStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 7, k3, super.clayStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 8, k3, super.clayStairBlock, 1);
            this.setBlockAndMetadata(world, -2, 9, k3, super.clayStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 10, k3, super.clayStairBlock, 1);
            this.setBlockAndMetadata(world, 0, 11, k3, super.claySlabBlock, super.claySlabMeta);
            this.setBlockAndMetadata(world, 1, 10, k3, super.clayStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 9, k3, super.clayStairBlock, 0);
            this.setBlockAndMetadata(world, 3, 8, k3, super.clayStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 7, k3, super.clayStairBlock, 0);
            this.setBlockAndMetadata(world, 5, 6, k3, super.clayStairBlock, 0);
        }
        for (final int k4 : new int[] { 0, 19 }) {
            this.setBlockAndMetadata(world, -4, 6, k4, super.clayStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 7, k4, super.clayStairBlock, 4);
            this.setBlockAndMetadata(world, -2, 8, k4, super.clayStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 9, k4, super.clayStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 10, k4, super.clayBlock, super.clayMeta);
            this.setBlockAndMetadata(world, 1, 9, k4, super.clayStairBlock, 5);
            this.setBlockAndMetadata(world, 2, 8, k4, super.clayStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 7, k4, super.clayStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 6, k4, super.clayStairBlock, 5);
        }
        for (int k3 = 2; k3 <= 17; ++k3) {
            if (k3 % 3 == 2) {
                for (int i4 = -4; i4 <= 4; ++i4) {
                    this.setBlockAndMetadata(world, i4, 6, k3, super.woodBeamBlock, super.woodBeamMeta | 0x4);
                }
                this.setBlockAndMetadata(world, -4, 5, k3, Blocks.torch, 2);
                this.setBlockAndMetadata(world, 4, 5, k3, Blocks.torch, 1);
            }
        }
        this.setBlockAndMetadata(world, -2, 5, 2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 5, 2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 5, 17, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 5, 17, Blocks.torch, 4);
        this.placeWallBanner(world, 0, 5, 1, LOTRItemBanner.BannerType.DORWINION, 0);
        this.placeWallBanner(world, 0, 5, 18, LOTRItemBanner.BannerType.DORWINION, 2);
        final ItemStack drink = LOTRFoods.DORWINION_DRINK.getRandomBrewableDrink(random);
        for (int k2 = 2; k2 <= 17; ++k2) {
            for (int i5 = -4; i5 <= 4; ++i5) {
                if (Math.abs(i5) >= 3) {
                    if (k2 == 2 || k2 == 17) {
                        this.setBlockAndMetadata(world, i5, 1, k2, super.plankBlock, super.plankMeta);
                    }
                    else if (k2 % 3 == 0) {
                        this.setBlockAndMetadata(world, i5, 1, k2, Blocks.spruce_stairs, 6);
                        this.setBlockAndMetadata(world, i5, 2, k2, Blocks.spruce_stairs, 2);
                    }
                    else if (k2 % 3 == 1) {
                        this.setBlockAndMetadata(world, i5, 1, k2, Blocks.spruce_stairs, 7);
                        this.setBlockAndMetadata(world, i5, 2, k2, Blocks.spruce_stairs, 3);
                    }
                }
            }
            if (k2 >= 5 && k2 <= 15 && k2 % 3 == 2) {
                this.setBlockAndMetadata(world, -4, 1, k2, super.plankBlock, super.plankMeta);
                this.placeBarrel(world, random, -4, 2, k2, 4, drink);
                this.setBlockAndMetadata(world, 4, 1, k2, super.plankBlock, super.plankMeta);
                this.placeBarrel(world, random, 4, 2, k2, 5, drink);
            }
        }
        for (int k2 = 8; k2 <= 11; ++k2) {
            for (int i5 = -1; i5 <= 1; ++i5) {
                if (Math.abs(i5) == 1 && (k2 == 8 || k2 == 11)) {
                    this.setBlockAndMetadata(world, i5, 1, k2, super.plankBlock, super.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i5, 1, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                }
            }
            this.placeMug(world, random, -1, 2, k2, 1, drink, LOTRFoods.DORWINION_DRINK);
            this.placeMug(world, random, 1, 2, k2, 3, drink, LOTRFoods.DORWINION_DRINK);
        }
        this.setBlockAndMetadata(world, 0, 1, 17, Blocks.crafting_table, 0);
        for (int i4 = -2; i4 <= 2; ++i4) {
            if (Math.abs(i4) >= 1) {
                this.setBlockAndMetadata(world, i4, 1, 17, (Block)Blocks.chest, 2);
                final TileEntity tileentity = this.getTileEntity(world, i4, 1, 17);
                if (tileentity instanceof TileEntityChest) {
                    final TileEntityChest chest = (TileEntityChest)tileentity;
                    for (int wines = MathHelper.getRandomIntegerInRange(random, 3, 6), l = 0; l < wines; ++l) {
                        final ItemStack chestDrinkItem = drink.copy();
                        chestDrinkItem.stackSize = 1;
                        LOTRItemMug.setStrengthMeta(chestDrinkItem, MathHelper.getRandomIntegerInRange(random, 1, 4));
                        final LOTRItemMug.Vessel[] chestVessels = LOTRFoods.DORWINION_DRINK.getDrinkVessels();
                        final LOTRItemMug.Vessel v = chestVessels[random.nextInt(chestVessels.length)];
                        LOTRItemMug.setVessel(chestDrinkItem, v, true);
                        chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), chestDrinkItem);
                    }
                }
            }
        }
        final LOTREntityDorwinionElfVintner vintner = new LOTREntityDorwinionElfVintner(world);
        this.spawnNPCAndSetHome(vintner, world, 0, 1, 13, 16);
        return true;
    }
}
