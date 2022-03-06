// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityDolGuldurOrc;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityWoodElfCaptain;
import lotr.common.LOTRFoods;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityWoodElfWarrior;
import lotr.common.entity.npc.LOTREntityWoodElfScout;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWoodElfTower extends LOTRWorldGenStructureBase
{
    private WorldGenerator treeGen;
    protected Block plateBlock;
    
    public LOTRWorldGenWoodElfTower(final boolean flag) {
        super(flag);
        this.treeGen = (WorldGenerator)new LOTRWorldGenMirkOak(true, 6, 6, 0, false).setGreenOak().disableRestrictions().disableRoots();
        this.plateBlock = LOTRMod.woodPlateBlock;
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        final int radius = 6;
        final int radiusPlusOne = radius + 1;
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                k += radiusPlusOne;
                break;
            }
            case 1: {
                i -= radiusPlusOne;
                break;
            }
            case 2: {
                k -= radiusPlusOne;
                break;
            }
            case 3: {
                i += radiusPlusOne;
                break;
            }
        }
        if (super.restrictions) {
            int minHeight = j;
            int maxHeight = j;
            for (int i2 = i - radiusPlusOne; i2 <= i + radiusPlusOne; ++i2) {
                for (int k2 = k - radiusPlusOne; k2 <= k + radiusPlusOne; ++k2) {
                    final int i3 = i2 - i;
                    final int k3 = k2 - k;
                    if (i3 * i3 + k3 * k3 <= radiusPlusOne * radiusPlusOne) {
                        final int j2 = world.getTopSolidOrLiquidBlock(i2, k2) - 1;
                        final Block block = world.getBlock(i2, j2, k2);
                        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone && !block.isWood((IBlockAccess)world, i2, j2, k2) && !block.isLeaves((IBlockAccess)world, i2, j2, k2)) {
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
        }
        final int sections = 3 + random.nextInt(3);
        final int sectionHeight = 8;
        final int topHeight = j + sections * sectionHeight;
        final int wallThresholdMin = radius * radius;
        final int wallThresholdMax = radiusPlusOne * radiusPlusOne;
        for (int i4 = i - radius; i4 <= i + radius; ++i4) {
            for (int k4 = k - radius; k4 <= k + radius; ++k4) {
                final int i5 = i4 - i;
                final int k5 = k4 - k;
                final int distSq = i5 * i5 + k5 * k5;
                if (distSq < wallThresholdMax) {
                    int j3;
                    for (int start = j3 = j - sectionHeight; (j3 == start || !LOTRMod.isOpaque(world, i4, j3, k4)) && j3 >= 0; --j3) {
                        if (j3 != start || distSq >= wallThresholdMin) {
                            this.func_150516_a(world, i4, j3, k4, LOTRMod.brick3, 5);
                        }
                        else {
                            this.func_150516_a(world, i4, j3, k4, LOTRMod.planks2, 13);
                        }
                        this.setGrassToDirt(world, i4, j3 - 1, k4);
                    }
                }
            }
        }
        for (int l = -1; l < sections; ++l) {
            final int sectionBase = j + l * sectionHeight;
            for (int j4 = sectionBase + 1; j4 <= sectionBase + sectionHeight; ++j4) {
                for (int i6 = i - radius; i6 <= i + radius; ++i6) {
                    for (int k6 = k - radius; k6 <= k + radius; ++k6) {
                        final int i7 = i6 - i;
                        final int k7 = k6 - k;
                        final int distSq2 = i7 * i7 + k7 * k7;
                        if (distSq2 < wallThresholdMax) {
                            if (distSq2 >= wallThresholdMin) {
                                this.func_150516_a(world, i6, j4, k6, LOTRMod.brick3, 5);
                                if (l == sections - 1 && j4 == sectionBase + sectionHeight) {
                                    this.func_150516_a(world, i6, j4 + 1, k6, LOTRMod.brick3, 5);
                                    this.func_150516_a(world, i6, j4 + 2, k6, LOTRMod.slabSingle6, 2);
                                }
                            }
                            else if (j4 == sectionBase + sectionHeight && (Math.abs(i7) > 2 || Math.abs(k7) > 2)) {
                                this.func_150516_a(world, i6, j4, k6, LOTRMod.planks2, 13);
                            }
                            else {
                                this.func_150516_a(world, i6, j4, k6, Blocks.air, 0);
                            }
                            this.setGrassToDirt(world, i6, j4 - 1, k6);
                        }
                    }
                }
                this.func_150516_a(world, i, j4, k, LOTRMod.wood7, 1);
            }
            for (int l2 = 0; l2 < 2; ++l2) {
                final int stairBase = sectionBase + l2 * 4;
                this.func_150516_a(world, i - 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
                this.func_150516_a(world, i, stairBase + 1, k + 1, LOTRMod.slabSingle6, 2);
                this.func_150516_a(world, i, stairBase + 1, k + 2, LOTRMod.slabSingle6, 2);
                this.func_150516_a(world, i + 1, stairBase + 2, k, LOTRMod.slabSingle6, 2);
                this.func_150516_a(world, i + 2, stairBase + 2, k, LOTRMod.slabSingle6, 2);
                this.func_150516_a(world, i, stairBase + 3, k - 1, LOTRMod.slabSingle6, 2);
                this.func_150516_a(world, i, stairBase + 3, k - 2, LOTRMod.slabSingle6, 2);
                this.func_150516_a(world, i - 1, stairBase + 4, k, LOTRMod.slabSingle6, 2);
                this.func_150516_a(world, i - 2, stairBase + 4, k, LOTRMod.slabSingle6, 2);
                for (int i8 = 0; i8 <= 1; ++i8) {
                    for (int k8 = 0; k8 <= 1; ++k8) {
                        this.func_150516_a(world, i + 1 + i8, stairBase + 1, k + 1 + k8, LOTRMod.slabSingle6, 10);
                        this.func_150516_a(world, i + 1 + i8, stairBase + 2, k - 2 + k8, LOTRMod.slabSingle6, 10);
                        this.func_150516_a(world, i - 2 + i8, stairBase + 3, k - 2 + k8, LOTRMod.slabSingle6, 10);
                        this.func_150516_a(world, i - 2 + i8, stairBase + 4, k + 1 + k8, LOTRMod.slabSingle6, 10);
                    }
                }
                this.func_150516_a(world, i - 1, stairBase + 2, k, LOTRMod.woodElvenTorch, 2);
                this.func_150516_a(world, i + 1, stairBase + 4, k, LOTRMod.woodElvenTorch, 1);
            }
            this.func_150516_a(world, i - 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i - 4, sectionBase + 3, k - 4, LOTRMod.woodElvenTorch, 5);
            this.func_150516_a(world, i - 4, sectionBase + 2, k + 4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i - 4, sectionBase + 3, k + 4, LOTRMod.woodElvenTorch, 5);
            this.func_150516_a(world, i + 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i + 4, sectionBase + 3, k - 4, LOTRMod.woodElvenTorch, 5);
            this.func_150516_a(world, i + 4, sectionBase + 2, k + 4, LOTRMod.fence2, 13);
            this.func_150516_a(world, i + 4, sectionBase + 3, k + 4, LOTRMod.woodElvenTorch, 5);
            if (l > 0) {
                for (int j4 = sectionBase + 1; j4 <= sectionBase + 4; ++j4) {
                    for (int i6 = i - 1; i6 <= i + 1; ++i6) {
                        this.func_150516_a(world, i6, j4, k - 6, Blocks.air, 0);
                        this.func_150516_a(world, i6, j4, k + 6, Blocks.air, 0);
                    }
                    for (int k9 = k - 1; k9 <= k + 1; ++k9) {
                        this.func_150516_a(world, i - 6, j4, k9, Blocks.air, 0);
                        this.func_150516_a(world, i + 6, j4, k9, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i - 1, sectionBase + 4, k - 6, LOTRMod.stairsWoodElvenBrick, 5);
                this.func_150516_a(world, i + 1, sectionBase + 4, k - 6, LOTRMod.stairsWoodElvenBrick, 4);
                this.func_150516_a(world, i - 1, sectionBase + 4, k + 6, LOTRMod.stairsWoodElvenBrick, 5);
                this.func_150516_a(world, i + 1, sectionBase + 4, k + 6, LOTRMod.stairsWoodElvenBrick, 4);
                this.func_150516_a(world, i - 6, sectionBase + 4, k - 1, LOTRMod.stairsWoodElvenBrick, 7);
                this.func_150516_a(world, i - 6, sectionBase + 4, k + 1, LOTRMod.stairsWoodElvenBrick, 6);
                this.func_150516_a(world, i + 6, sectionBase + 4, k - 1, LOTRMod.stairsWoodElvenBrick, 7);
                this.func_150516_a(world, i + 6, sectionBase + 4, k + 1, LOTRMod.stairsWoodElvenBrick, 6);
                for (int i9 = i - 2; i9 <= i + 2; ++i9) {
                    this.func_150516_a(world, i9, sectionBase, k - 8, LOTRMod.stairsGreenOak, 6);
                    this.func_150516_a(world, i9, sectionBase + 1, k - 8, LOTRMod.fence2, 13);
                    this.func_150516_a(world, i9, sectionBase, k + 8, LOTRMod.stairsGreenOak, 7);
                    this.func_150516_a(world, i9, sectionBase + 1, k + 8, LOTRMod.fence2, 13);
                }
                for (int k10 = k - 2; k10 <= k + 2; ++k10) {
                    this.func_150516_a(world, i - 8, sectionBase, k10, LOTRMod.stairsGreenOak, 4);
                    this.func_150516_a(world, i - 8, sectionBase + 1, k10, LOTRMod.fence2, 13);
                    this.func_150516_a(world, i + 8, sectionBase, k10, LOTRMod.stairsGreenOak, 5);
                    this.func_150516_a(world, i + 8, sectionBase + 1, k10, LOTRMod.fence2, 13);
                }
                for (int i9 = i - 1; i9 <= i + 1; ++i9) {
                    this.func_150516_a(world, i9, sectionBase, k - 7, LOTRMod.planks2, 13);
                    this.func_150516_a(world, i9, sectionBase, k + 7, LOTRMod.planks2, 13);
                }
                for (int k10 = k - 1; k10 <= k + 1; ++k10) {
                    this.func_150516_a(world, i - 7, sectionBase, k10, LOTRMod.planks2, 13);
                    this.func_150516_a(world, i + 7, sectionBase, k10, LOTRMod.planks2, 13);
                }
                this.func_150516_a(world, i - 7, sectionBase, k - 2, LOTRMod.stairsGreenOak, 6);
                this.func_150516_a(world, i - 7, sectionBase + 1, k - 2, LOTRMod.fence2, 13);
                this.func_150516_a(world, i - 8, sectionBase + 2, k - 2, LOTRMod.woodElvenTorch, 5);
                this.func_150516_a(world, i - 7, sectionBase, k + 2, LOTRMod.stairsGreenOak, 7);
                this.func_150516_a(world, i - 7, sectionBase + 1, k + 2, LOTRMod.fence2, 13);
                this.func_150516_a(world, i - 8, sectionBase + 2, k + 2, LOTRMod.woodElvenTorch, 5);
                this.func_150516_a(world, i + 7, sectionBase, k - 2, LOTRMod.stairsGreenOak, 6);
                this.func_150516_a(world, i + 7, sectionBase + 1, k - 2, LOTRMod.fence2, 13);
                this.func_150516_a(world, i + 8, sectionBase + 2, k - 2, LOTRMod.woodElvenTorch, 5);
                this.func_150516_a(world, i + 7, sectionBase, k + 2, LOTRMod.stairsGreenOak, 7);
                this.func_150516_a(world, i + 7, sectionBase + 1, k + 2, LOTRMod.fence2, 13);
                this.func_150516_a(world, i + 8, sectionBase + 2, k + 2, LOTRMod.woodElvenTorch, 5);
                this.func_150516_a(world, i - 2, sectionBase, k - 7, LOTRMod.stairsGreenOak, 4);
                this.func_150516_a(world, i - 2, sectionBase + 1, k - 7, LOTRMod.fence2, 13);
                this.func_150516_a(world, i - 2, sectionBase + 2, k - 8, LOTRMod.woodElvenTorch, 5);
                this.func_150516_a(world, i + 2, sectionBase, k - 7, LOTRMod.stairsGreenOak, 5);
                this.func_150516_a(world, i + 2, sectionBase + 1, k - 7, LOTRMod.fence2, 13);
                this.func_150516_a(world, i + 2, sectionBase + 2, k - 8, LOTRMod.woodElvenTorch, 5);
                this.func_150516_a(world, i - 2, sectionBase, k + 7, LOTRMod.stairsGreenOak, 4);
                this.func_150516_a(world, i - 2, sectionBase + 1, k + 7, LOTRMod.fence2, 13);
                this.func_150516_a(world, i - 2, sectionBase + 2, k + 8, LOTRMod.woodElvenTorch, 5);
                this.func_150516_a(world, i + 2, sectionBase, k + 7, LOTRMod.stairsGreenOak, 5);
                this.func_150516_a(world, i + 2, sectionBase + 1, k + 7, LOTRMod.fence2, 13);
                this.func_150516_a(world, i + 2, sectionBase + 2, k + 8, LOTRMod.woodElvenTorch, 5);
            }
            final LOTREntityWoodElf woodElf = (random.nextInt(3) == 0) ? new LOTREntityWoodElfScout(world) : new LOTREntityWoodElfWarrior(world);
            woodElf.setLocationAndAngles(i - 3 + 0.5, (double)(sectionBase + 1), k - 3 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
            woodElf.spawnRidingHorse = false;
            woodElf.onSpawnWithEgg(null);
            woodElf.setHomeArea(i, sectionBase + 1, k, 12);
            woodElf.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)woodElf);
        }
        this.treeGen.generate(world, random, i, topHeight, k);
        for (int j5 = topHeight + 2; j5 <= topHeight + 3; ++j5) {
            this.func_150516_a(world, i + 6, j5, k - 3, LOTRMod.brick3, 5);
            this.func_150516_a(world, i + 6, j5, k, LOTRMod.brick3, 5);
            this.func_150516_a(world, i + 6, j5, k + 3, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 3, j5, k + 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i, j5, k + 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i + 3, j5, k + 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 6, j5, k - 3, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 6, j5, k, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 6, j5, k + 3, LOTRMod.brick3, 5);
            this.func_150516_a(world, i - 3, j5, k - 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i, j5, k - 6, LOTRMod.brick3, 5);
            this.func_150516_a(world, i + 3, j5, k - 6, LOTRMod.brick3, 5);
        }
        this.func_150516_a(world, i + 6, topHeight + 2, k - 2, LOTRMod.brick3, 5);
        this.func_150516_a(world, i + 6, topHeight + 2, k + 2, LOTRMod.brick3, 5);
        this.func_150516_a(world, i - 2, topHeight + 2, k + 6, LOTRMod.brick3, 5);
        this.func_150516_a(world, i + 2, topHeight + 2, k + 6, LOTRMod.brick3, 5);
        this.func_150516_a(world, i - 6, topHeight + 2, k - 2, LOTRMod.brick3, 5);
        this.func_150516_a(world, i - 6, topHeight + 2, k + 2, LOTRMod.brick3, 5);
        this.func_150516_a(world, i - 2, topHeight + 2, k - 6, LOTRMod.brick3, 5);
        this.func_150516_a(world, i + 2, topHeight + 2, k - 6, LOTRMod.brick3, 5);
        final ItemStack bow1 = new ItemStack(LOTRMod.mirkwoodBow);
        final ItemStack bow2 = new ItemStack(LOTRMod.mirkwoodBow);
        final ItemStack[] armor = { new ItemStack(LOTRMod.helmetWoodElvenScout), new ItemStack(LOTRMod.bodyWoodElvenScout), new ItemStack(LOTRMod.legsWoodElvenScout), new ItemStack(LOTRMod.bootsWoodElvenScout) };
        switch (rotation) {
            case 0: {
                this.placeArmorStand(world, i, topHeight + 1, k + 5, 0, armor);
                this.spawnItemFrame(world, i + 6, topHeight + 2, k, 1, bow1);
                this.spawnItemFrame(world, i - 6, topHeight + 2, k, 3, bow2);
                this.func_150516_a(world, i, topHeight + 1, k - 4, LOTRMod.commandTable, 0);
                break;
            }
            case 1: {
                this.spawnItemFrame(world, i, topHeight + 2, k + 6, 2, bow1);
                this.spawnItemFrame(world, i, topHeight + 2, k - 6, 0, bow2);
                this.placeArmorStand(world, i - 5, topHeight + 1, k, 1, armor);
                this.func_150516_a(world, i + 4, topHeight + 1, k, LOTRMod.commandTable, 0);
                break;
            }
            case 2: {
                this.spawnItemFrame(world, i + 6, topHeight + 2, k, 1, bow1);
                this.placeArmorStand(world, i, topHeight + 1, k - 5, 2, armor);
                this.spawnItemFrame(world, i - 6, topHeight + 2, k, 3, bow2);
                this.func_150516_a(world, i, topHeight + 1, k + 4, LOTRMod.commandTable, 0);
                break;
            }
            case 3: {
                this.spawnItemFrame(world, i, topHeight + 2, k + 6, 2, bow1);
                this.placeArmorStand(world, i + 5, topHeight + 1, k, 3, armor);
                this.spawnItemFrame(world, i, topHeight + 2, k - 6, 0, bow2);
                this.func_150516_a(world, i - 4, topHeight + 1, k, LOTRMod.commandTable, 0);
                break;
            }
        }
        this.placeWallBanner(world, i, topHeight + 1, k + 6, 0, LOTRItemBanner.BannerType.WOOD_ELF);
        this.placeWallBanner(world, i - 6, topHeight + 1, k, 1, LOTRItemBanner.BannerType.WOOD_ELF);
        this.placeWallBanner(world, i, topHeight + 1, k - 6, 2, LOTRItemBanner.BannerType.WOOD_ELF);
        this.placeWallBanner(world, i + 6, topHeight + 1, k, 3, LOTRItemBanner.BannerType.WOOD_ELF);
        for (int i6 = i - 3; i6 <= i + 3; ++i6) {
            this.func_150516_a(world, i6, j - sectionHeight + 1, k - 5, (Block)Blocks.wooden_slab, 8);
            this.func_150516_a(world, i6, j - sectionHeight + 1, k + 5, (Block)Blocks.wooden_slab, 8);
            if (random.nextBoolean()) {
                this.placeMug(world, random, i6, j - sectionHeight + 2, k + 5, 0, LOTRFoods.WOOD_ELF_DRINK);
            }
            if (Math.abs(i6 - i) > 1) {
                this.placeBarrel(world, random, i6, j - sectionHeight + 2, k - 5, 3, LOTRFoods.WOOD_ELF_DRINK);
            }
        }
        this.func_150516_a(world, i - 1, j - sectionHeight + 1, k - 5, LOTRMod.woodElvenTable, 0);
        this.func_150516_a(world, i + 1, j - sectionHeight + 1, k - 5, LOTRMod.woodElvenTable, 0);
        this.func_150516_a(world, i, j - sectionHeight + 1, k - 5, (Block)Blocks.chest, 0);
        LOTRChestContents.fillChest(world, random, i, j - sectionHeight + 1, k - 5, LOTRChestContents.WOOD_ELF_HOUSE);
        for (int i6 = i + 4; i6 <= i + 5; ++i6) {
            this.func_150516_a(world, i6, j - sectionHeight + 1, k - 3, Blocks.oak_stairs, 3);
            this.func_150516_a(world, i6, j - sectionHeight + 1, k - 1, Blocks.planks, 0);
            this.placeMug(world, random, i6, j - sectionHeight + 2, k - 1, 0, LOTRFoods.WOOD_ELF_DRINK);
            this.func_150516_a(world, i6, j - sectionHeight + 1, k, (Block)Blocks.wooden_slab, 8);
            this.placePlateWithCertainty(world, random, i6, j - sectionHeight + 2, k, this.plateBlock, LOTRFoods.ELF);
            this.func_150516_a(world, i6, j - sectionHeight + 1, k + 1, Blocks.planks, 0);
            this.placeMug(world, random, i6, j - sectionHeight + 2, k + 1, 2, LOTRFoods.WOOD_ELF_DRINK);
            this.func_150516_a(world, i6, j - sectionHeight + 1, k + 3, Blocks.oak_stairs, 2);
        }
        this.func_150516_a(world, i + 4, j - sectionHeight + 1, k - 4, Blocks.planks, 0);
        this.func_150516_a(world, i + 4, j - sectionHeight + 1, k + 4, Blocks.planks, 0);
        for (int j6 = j - sectionHeight - 6; j6 <= j - sectionHeight - 1; ++j6) {
            this.placeDungeonBlock(world, random, i - 6, j6, k);
            this.placeDungeonBlock(world, random, i - 5, j6, k - 2);
            this.placeDungeonBlock(world, random, i - 5, j6, k - 1);
            this.placeDungeonBlock(world, random, i - 5, j6, k + 1);
            this.placeDungeonBlock(world, random, i - 5, j6, k + 2);
            this.placeDungeonBlock(world, random, i - 4, j6, k - 3);
            this.placeDungeonBlock(world, random, i - 4, j6, k + 3);
            this.placeDungeonBlock(world, random, i - 3, j6, k - 5);
            this.placeDungeonBlock(world, random, i - 3, j6, k - 4);
            this.placeDungeonBlock(world, random, i - 3, j6, k + 4);
            this.placeDungeonBlock(world, random, i - 3, j6, k + 5);
            this.placeDungeonBlock(world, random, i - 2, j6, k - 6);
            this.placeDungeonBlock(world, random, i - 2, j6, k + 6);
            this.placeDungeonBlock(world, random, i - 1, j6, k - 6);
            this.placeDungeonBlock(world, random, i - 1, j6, k + 6);
            this.placeDungeonBlock(world, random, i, j6, k - 6);
            this.placeDungeonBlock(world, random, i, j6, k + 6);
            this.placeDungeonBlock(world, random, i + 1, j6, k - 5);
            this.placeDungeonBlock(world, random, i + 1, j6, k - 4);
            this.placeDungeonBlock(world, random, i + 1, j6, k + 4);
            this.placeDungeonBlock(world, random, i + 1, j6, k + 5);
            this.placeDungeonBlock(world, random, i + 2, j6, k - 3);
            this.placeDungeonBlock(world, random, i + 2, j6, k + 3);
            this.placeDungeonBlock(world, random, i + 3, j6, k - 2);
            this.placeDungeonBlock(world, random, i + 3, j6, k + 2);
            this.placeDungeonBlock(world, random, i + 4, j6, k - 2);
            this.placeDungeonBlock(world, random, i + 4, j6, k + 2);
            this.placeDungeonBlock(world, random, i + 5, j6, k - 1);
            this.placeDungeonBlock(world, random, i + 5, j6, k);
            this.placeDungeonBlock(world, random, i + 5, j6, k + 1);
            if (j6 == j - sectionHeight - 6 || j6 == j - sectionHeight - 1) {
                this.placeDungeonBlock(world, random, i - 5, j6, k);
                for (int k6 = k - 2; k6 <= k + 2; ++k6) {
                    this.placeDungeonBlock(world, random, i - 4, j6, k6);
                }
                for (int k6 = k - 3; k6 <= k + 3; ++k6) {
                    this.placeDungeonBlock(world, random, i - 3, j6, k6);
                }
                for (int k6 = k - 5; k6 <= k + 5; ++k6) {
                    this.placeDungeonBlock(world, random, i - 2, j6, k6);
                    this.placeDungeonBlock(world, random, i - 1, j6, k6);
                    this.placeDungeonBlock(world, random, i, j6, k6);
                }
                for (int k6 = k - 3; k6 <= k + 3; ++k6) {
                    this.placeDungeonBlock(world, random, i + 1, j6, k6);
                }
                for (int k6 = k - 2; k6 <= k + 2; ++k6) {
                    this.placeDungeonBlock(world, random, i + 2, j6, k6);
                }
                for (int k6 = k - 1; k6 <= k + 1; ++k6) {
                    this.placeDungeonBlock(world, random, i + 3, j6, k6);
                    this.placeDungeonBlock(world, random, i + 4, j6, k6);
                }
            }
            else {
                this.func_150516_a(world, i - 5, j6, k, Blocks.air, 0);
                for (int k6 = k - 2; k6 <= k + 2; ++k6) {
                    this.func_150516_a(world, i - 4, j6, k6, Blocks.air, 0);
                }
                for (int k6 = k - 3; k6 <= k + 3; ++k6) {
                    this.func_150516_a(world, i - 3, j6, k6, Blocks.air, 0);
                }
                for (int k6 = k - 5; k6 <= k + 5; ++k6) {
                    this.func_150516_a(world, i - 2, j6, k6, Blocks.air, 0);
                    this.func_150516_a(world, i - 1, j6, k6, Blocks.air, 0);
                    this.func_150516_a(world, i, j6, k6, Blocks.air, 0);
                }
                for (int k6 = k - 3; k6 <= k + 3; ++k6) {
                    this.func_150516_a(world, i + 1, j6, k6, Blocks.air, 0);
                }
                for (int k6 = k - 2; k6 <= k + 2; ++k6) {
                    this.func_150516_a(world, i + 2, j6, k6, Blocks.air, 0);
                }
                for (int k6 = k - 1; k6 <= k + 1; ++k6) {
                    this.func_150516_a(world, i + 3, j6, k6, Blocks.air, 0);
                    this.func_150516_a(world, i + 4, j6, k6, Blocks.air, 0);
                }
            }
        }
        for (int i6 = i - 2; i6 <= i; ++i6) {
            this.placeDungeonBlock(world, random, i6, j - sectionHeight - 2, k - 5);
            this.placeDungeonBlock(world, random, i6, j - sectionHeight - 2, k - 4);
            this.placeDungeonBlock(world, random, i6, j - sectionHeight - 2, k + 4);
            this.placeDungeonBlock(world, random, i6, j - sectionHeight - 2, k + 5);
        }
        for (int k9 = k - 1; k9 <= k + 1; ++k9) {
            this.placeDungeonBlock(world, random, i + 3, j - sectionHeight - 2, k9);
            this.placeDungeonBlock(world, random, i + 4, j - sectionHeight - 2, k9);
        }
        for (int j6 = j - sectionHeight - 5; j6 <= j - sectionHeight - 3; ++j6) {
            for (int i8 = i - 2; i8 <= i; ++i8) {
                this.func_150516_a(world, i8, j6, k - 4, LOTRMod.woodElfBars, 0);
                this.func_150516_a(world, i8, j6, k + 4, LOTRMod.woodElfBars, 0);
            }
            for (int k6 = k - 1; k6 <= k + 1; ++k6) {
                this.func_150516_a(world, i + 3, j6, k6, LOTRMod.woodElfBars, 0);
            }
        }
        this.placePrisoner(world, random, i - 2, j - sectionHeight - 5, k - 5, 3, 1);
        this.placePrisoner(world, random, i - 2, j - sectionHeight - 5, k + 5, 3, 1);
        this.placePrisoner(world, random, i + 4, j - sectionHeight - 5, k - 1, 1, 3);
        this.func_150516_a(world, i - 4, j - sectionHeight - 3, k - 1, LOTRMod.woodElvenTorch, 1);
        this.func_150516_a(world, i - 4, j - sectionHeight - 3, k + 1, LOTRMod.woodElvenTorch, 1);
        for (int j6 = j - sectionHeight - 5; j6 <= j - sectionHeight; ++j6) {
            this.func_150516_a(world, i - 5, j6, k, Blocks.ladder, 5);
        }
        this.func_150516_a(world, i - 5, j - sectionHeight + 1, k, Blocks.trapdoor, 3);
        switch (rotation) {
            case 0: {
                final int k8 = k - radius;
                for (int i6 = i - 1; i6 <= i + 1; ++i6) {
                    for (int j7 = j + 1; j7 <= j + 3; ++j7) {
                        this.func_150516_a(world, i6, j7, k8, LOTRMod.gateWoodElven, 2);
                    }
                }
                this.placeWallBanner(world, i, j + 6, k8, 2, LOTRItemBanner.BannerType.WOOD_ELF);
                break;
            }
            case 1: {
                final int i6 = i + radius;
                for (int k8 = k - 1; k8 <= k + 1; ++k8) {
                    for (int j7 = j + 1; j7 <= j + 3; ++j7) {
                        this.func_150516_a(world, i6, j7, k8, LOTRMod.gateWoodElven, 5);
                    }
                }
                this.placeWallBanner(world, i6, j + 6, k, 3, LOTRItemBanner.BannerType.WOOD_ELF);
                break;
            }
            case 2: {
                final int k8 = k + radius;
                for (int i6 = i - 1; i6 <= i + 1; ++i6) {
                    for (int j7 = j + 1; j7 <= j + 3; ++j7) {
                        this.func_150516_a(world, i6, j7, k8, LOTRMod.gateWoodElven, 3);
                    }
                }
                this.placeWallBanner(world, i, j + 6, k8, 0, LOTRItemBanner.BannerType.WOOD_ELF);
                break;
            }
            case 3: {
                final int i6 = i - radius;
                for (int k8 = k - 1; k8 <= k + 1; ++k8) {
                    for (int j7 = j + 1; j7 <= j + 3; ++j7) {
                        this.func_150516_a(world, i6, j7, k8, LOTRMod.gateWoodElven, 4);
                    }
                }
                this.placeWallBanner(world, i6, j + 6, k, 1, LOTRItemBanner.BannerType.WOOD_ELF);
                break;
            }
        }
        final LOTREntityWoodElfCaptain woodElfCaptain = new LOTREntityWoodElfCaptain(world);
        woodElfCaptain.setLocationAndAngles(i - 3 + 0.5, (double)(topHeight + 1), k - 3 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
        woodElfCaptain.spawnRidingHorse = false;
        woodElfCaptain.onSpawnWithEgg(null);
        woodElfCaptain.setHomeArea(i, topHeight, k, 16);
        world.spawnEntityInWorld((Entity)woodElfCaptain);
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityWoodElfWarrior.class, LOTREntityWoodElfScout.class);
        respawner.setCheckRanges(12, -16, 40, 12);
        respawner.setSpawnRanges(5, 1, 40, 12);
        this.placeNPCRespawner(respawner, world, i, j, k);
        return true;
    }
    
    private void placeDungeonBlock(final World world, final Random random, final int i, final int j, final int k) {
        final int l = random.nextInt(3);
        switch (l) {
            case 0: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 5);
                break;
            }
            case 1: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 6);
                break;
            }
            case 2: {
                this.func_150516_a(world, i, j, k, LOTRMod.brick3, 7);
                break;
            }
        }
    }
    
    private void placePrisoner(final World world, final Random random, int i, final int j, int k, final int xRange, final int zRange) {
        i += random.nextInt(xRange);
        k += random.nextInt(zRange);
        if (random.nextInt(3) == 0) {
            LOTREntityNPC npc;
            if (random.nextInt(10) == 0) {
                npc = new LOTREntityDwarf(world);
            }
            else if (random.nextBoolean()) {
                npc = new LOTREntityGundabadOrc(world);
            }
            else {
                npc = new LOTREntityDolGuldurOrc(world);
            }
            npc.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, 0.0f, 0.0f);
            npc.spawnRidingHorse = false;
            npc.onSpawnWithEgg(null);
            for (int l = 0; l < 5; ++l) {
                npc.setCurrentItemOrArmor(l, (ItemStack)null);
            }
            npc.npcItemsInv.setMeleeWeapon(null);
            npc.npcItemsInv.setMeleeWeaponMounted(null);
            npc.npcItemsInv.setRangedWeapon(null);
            npc.npcItemsInv.setSpearBackup(null);
            npc.npcItemsInv.setIdleItem(null);
            npc.npcItemsInv.setIdleItemMounted(null);
            npc.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)npc);
        }
        else {
            this.placeSkull(world, random, i, j, k);
        }
    }
}
