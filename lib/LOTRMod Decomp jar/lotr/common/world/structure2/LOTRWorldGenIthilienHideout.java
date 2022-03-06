// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityRangerIthilienCaptain;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityRangerIthilien;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenIthilienHideout extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenIthilienHideout(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        final int width = 5;
        final int height = 4;
        final int baseY = -(height + 2 + random.nextInt(4));
        if (super.restrictions) {
            if (!this.isSurface(world, 0, -1, 0)) {
                return false;
            }
            for (int i2 = -width; i2 <= width; ++i2) {
                for (int k2 = -width; k2 <= width; ++k2) {
                    for (int j2 = baseY; j2 <= baseY + height + 2; ++j2) {
                        if (!this.isOpaque(world, i2, j2, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        for (int i2 = -width - 1; i2 <= width + 1; ++i2) {
            for (int k2 = -width - 1; k2 <= width + 1; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                final boolean withinWalls = i3 <= width && k3 <= width;
                this.setBlockAndMetadata(world, i2, baseY, k2, Blocks.stone, 0);
                this.setBlockAndMetadata(world, i2, baseY + height + 1, k2, Blocks.stone, 0);
                for (int j3 = baseY + 1; j3 <= baseY + height; ++j3) {
                    if (withinWalls) {
                        this.setAir(world, i2, j3, k2);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, j3, k2, Blocks.stone, 0);
                    }
                }
                if (withinWalls) {
                    if ((i3 <= 2 && k3 <= 2) || random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i2, baseY + 1, k2, LOTRMod.thatchFloor, 0);
                    }
                    if (i3 == width || k3 == width) {
                        this.setBlockAndMetadata(world, i2, baseY + 1, k2, LOTRMod.planks, 8);
                    }
                }
            }
        }
        int ladderY;
        for (ladderY = baseY + 1; ladderY <= baseY + height || this.isOpaque(world, 0, ladderY, 0) || (this.isOpaque(world, -1, ladderY, 0) && this.isOpaque(world, 1, ladderY, 0) && this.isOpaque(world, 0, ladderY, -1) && this.isOpaque(world, 0, ladderY, 1)); ++ladderY) {
            if (!this.isOpaque(world, 0, ladderY, -1)) {
                this.setBlockAndMetadata(world, 0, ladderY, -1, Blocks.stone, 0);
            }
            this.setBlockAndMetadata(world, 0, ladderY, 0, Blocks.ladder, 3);
        }
        for (int pass = 0; pass <= 1; ++pass) {
            for (int i4 = -1; i4 <= 1; ++i4) {
                for (int k4 = -1; k4 <= 1; ++k4) {
                    final int i5 = Math.abs(i4);
                    final int k5 = Math.abs(k4);
                    if (i4 != 0 || k4 != 0) {
                        if (pass == 0 && i4 == 0 && k4 == 1) {
                            for (int j4 = 0; j4 <= 3; ++j4) {
                                final int j5 = ladderY + j4;
                                if (LOTRTreeType.OAK_ITHILIEN_HIDEOUT.create(super.notifyChanges, random).generate(world, random, this.getX(i4, k4), this.getY(j5), this.getZ(i4, k4))) {
                                    break;
                                }
                            }
                        }
                        if (pass == 1) {
                            final boolean doublegrass = i5 != k5;
                            int j6 = -3;
                            while (j6 <= 3) {
                                final int j7 = ladderY + j6;
                                final Block below = this.getBlock(world, i4, j7 - 1, k4);
                                if ((below == Blocks.grass || below == Blocks.dirt) && !this.isOpaque(world, i4, j7, k4) && !this.isOpaque(world, i4, j7 + 1, k4)) {
                                    if (doublegrass) {
                                        this.setBlockAndMetadata(world, i4, j7, k4, (Block)Blocks.double_plant, 2);
                                        this.setBlockAndMetadata(world, i4, j7 + 1, k4, (Block)Blocks.double_plant, 8);
                                        break;
                                    }
                                    this.setBlockAndMetadata(world, i4, j7, k4, (Block)Blocks.tallgrass, 1);
                                    break;
                                }
                                else {
                                    ++j6;
                                }
                            }
                        }
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -width, baseY + 3, -width, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -width, baseY + 3, width, Blocks.torch, 2);
        this.setBlockAndMetadata(world, width, baseY + 3, -width, Blocks.torch, 1);
        this.setBlockAndMetadata(world, width, baseY + 3, width, Blocks.torch, 1);
        this.placeWallBanner(world, -width - 1, baseY + 4, 0, LOTRItemBanner.BannerType.ITHILIEN, 1);
        this.placeWallBanner(world, 0, baseY + 4, -width - 1, LOTRItemBanner.BannerType.ITHILIEN, 0);
        this.placeWallBanner(world, width + 1, baseY + 4, 0, LOTRItemBanner.BannerType.ITHILIEN, 3);
        this.placeWallBanner(world, -2, baseY + 4, width + 1, LOTRItemBanner.BannerType.ITHILIEN, 2);
        this.placeWallBanner(world, 0, baseY + 4, width + 1, LOTRItemBanner.BannerType.GONDOR, 2);
        this.placeWallBanner(world, 2, baseY + 4, width + 1, LOTRItemBanner.BannerType.ITHILIEN, 2);
        this.setBlockAndMetadata(world, -2, baseY + 1, width, LOTRMod.gondorianTable, 0);
        this.setBlockAndMetadata(world, 0, baseY + 1, width, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, baseY + 1, width, Blocks.furnace, 2);
        this.placeChest(world, random, width, baseY + 1, 0, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
        final ItemStack drink = LOTRFoods.GONDOR_DRINK.getRandomBrewableDrink(random);
        this.placeBarrel(world, random, width, baseY + 2, -3, 5, drink);
        this.placeBarrel(world, random, width, baseY + 2, -2, 5, drink);
        this.placeBarrel(world, random, width, baseY + 2, 2, 5, drink);
        this.placeBarrel(world, random, width, baseY + 2, 3, 5, drink);
        for (int i4 = -3; i4 <= 3; i4 += 2) {
            this.setBlockAndMetadata(world, i4, baseY + 1, -width + 1, LOTRMod.strawBed, 2);
            this.setBlockAndMetadata(world, i4, baseY + 1, -width, LOTRMod.strawBed, 10);
        }
        this.placeChest(world, random, -width, baseY + 1, 0, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        final ItemStack[] rangerArmor = { new ItemStack(LOTRMod.helmetRangerIthilien), new ItemStack(LOTRMod.bodyRangerIthilien), new ItemStack(LOTRMod.legsRangerIthilien), new ItemStack(LOTRMod.bootsRangerIthilien) };
        this.placeArmorStand(world, -width, baseY + 2, -2, 3, rangerArmor);
        this.placeArmorStand(world, -width, baseY + 2, 2, 3, rangerArmor);
        for (int rangers = 2 + random.nextInt(3), l = 0; l < rangers; ++l) {
            final LOTREntityRangerIthilien ranger = new LOTREntityRangerIthilien(world);
            this.spawnNPCAndSetHome(ranger, world, -2, baseY + 1, -2, 16);
        }
        this.spawnNPCAndSetHome(new LOTREntityRangerIthilienCaptain(world), world, -2, baseY + 1, -2, 16);
        return true;
    }
}
