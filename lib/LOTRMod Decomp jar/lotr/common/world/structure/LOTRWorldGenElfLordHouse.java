// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityGaladhrimLord;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import net.minecraft.util.MathHelper;
import lotr.common.world.feature.LOTRWorldGenMallornExtreme;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenElfLordHouse extends LOTRWorldGenStructureBase
{
    public LOTRWorldGenElfLordHouse(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, final int i, int j, final int k) {
        if (super.restrictions) {
            for (int i2 = i - 14; i2 <= i + 14; ++i2) {
                for (int j2 = j; j2 <= j + 7; ++j2) {
                    for (int k2 = k - 14; k2 <= k + 14; ++k2) {
                        if (Math.abs(i2 - i) > 2 || Math.abs(k2 - k) > 2) {
                            if (!world.isAirBlock(i2, j2, k2)) {
                                return false;
                            }
                        }
                    }
                }
            }
            int totalGrass = 0;
            int numGrass = 0;
            for (int i3 = i - 5; i3 <= i + 5; ++i3) {
                for (int k3 = k - 5; k3 <= k + 5; ++k3) {
                    if (Math.abs(i3 - i) > 2 || Math.abs(k3 - k) > 2) {
                        for (int j3 = j; j3 >= 0; --j3) {
                            if (world.getBlock(i3, j3, k3) == Blocks.grass) {
                                totalGrass += j3;
                                ++numGrass;
                                break;
                            }
                        }
                    }
                }
            }
            final int lowestGrass = totalGrass / numGrass;
            for (int i4 = i - 5; i4 <= i + 5; ++i4) {
                for (int k4 = k - 5; k4 <= k + 5; ++k4) {
                    if (Math.abs(i4 - i) > 2 || Math.abs(k4 - k) > 2) {
                        for (int j4 = j; j4 > lowestGrass; --j4) {
                            this.func_150516_a(world, i4, j4, k4, Blocks.air, 0);
                        }
                        this.func_150516_a(world, i4, lowestGrass, k4, (Block)Blocks.grass, 0);
                    }
                }
            }
        }
        else if (super.usingPlayer != null) {
            for (int i2 = i - 2; i2 <= i + 2; ++i2) {
                for (int k5 = k - 2; k5 <= k + 2; ++k5) {
                    for (int j5 = j; !LOTRMod.isOpaque(world, i2, j5, k5) && j5 >= 0; --j5) {
                        this.func_150516_a(world, i2, j5, k5, LOTRMod.wood, 1);
                    }
                }
            }
            --j;
            final LOTRWorldGenMallornExtreme treeGen = new LOTRWorldGenMallornExtreme(true);
            final int j2 = treeGen.generateAndReturnHeight(world, random, i, j, k, true);
            j += MathHelper.floor_double((double)(j2 * MathHelper.randomFloatClamp(random, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MIN, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MAX)));
        }
        this.buildStaircase(world, random, i, j, k);
        for (int i2 = i - 14; i2 <= i + 14; ++i2) {
            for (int j2 = j; j2 <= j + 6; ++j2) {
                for (int k2 = k - 14; k2 <= k + 14; ++k2) {
                    this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                }
            }
        }
        for (int i2 = i - 2; i2 <= i + 2; ++i2) {
            for (int j2 = j; j2 <= j + 7; ++j2) {
                for (int k2 = k - 2; k2 <= k + 2; ++k2) {
                    this.func_150516_a(world, i2, j2, k2, LOTRMod.wood, 1);
                }
            }
        }
        for (int i2 = i - 12; i2 <= i + 12; ++i2) {
            for (int k5 = k - 12; k5 <= k + 12; ++k5) {
                final int i5 = i2 - i;
                final int k6 = k5 - k;
                if (Math.abs(i5) > 2 || Math.abs(k6) > 2) {
                    final int distSq = i5 * i5 + k6 * k6;
                    if (distSq < 100) {
                        this.func_150516_a(world, i2, j, k5, LOTRMod.planks, 1);
                    }
                    else if (distSq < 169) {
                        this.func_150516_a(world, i2, j + 1, k5, LOTRMod.planks, 1);
                        if (distSq > 132) {
                            this.func_150516_a(world, i2, j + 2, k5, LOTRMod.fence, 1);
                        }
                    }
                }
            }
        }
        for (int i2 = i - 12; i2 <= i + 12; ++i2) {
            for (int k5 = k - 12; k5 <= k + 12; ++k5) {
                final int i5 = i2 - i;
                final int k6 = k5 - k;
                final int distSq = i5 * i5 + k6 * k6;
                if ((Math.abs(i5) > 2 || Math.abs(k6) > 2) && distSq < 169) {
                    this.func_150516_a(world, i2, j + 6, k5, LOTRMod.planks, 1);
                    int i6 = i2;
                    int k7 = k5;
                    if (i6 > i) {
                        --i6;
                    }
                    if (i6 < i) {
                        ++i6;
                    }
                    if (k7 > k) {
                        --k7;
                    }
                    if (k7 < k) {
                        ++k7;
                    }
                    this.func_150516_a(world, i6, j + 7, k7, LOTRMod.planks, 1);
                }
            }
        }
        this.buildStairCircle(world, i, j, k, 10, true, false);
        this.buildStairCircle(world, i, j + 1, k, 9, false, true);
        this.buildStairCircle(world, i, j + 6, k, 13, false, false);
        this.buildStairCircle(world, i, j + 7, k, 12, false, false);
        this.func_150516_a(world, i + 3, j + 3, k, LOTRWorldGenElfHouse.getRandomTorch(random), 1);
        this.func_150516_a(world, i - 3, j + 3, k, LOTRWorldGenElfHouse.getRandomTorch(random), 2);
        this.func_150516_a(world, i, j + 3, k + 3, LOTRWorldGenElfHouse.getRandomTorch(random), 3);
        this.func_150516_a(world, i, j + 3, k - 3, LOTRWorldGenElfHouse.getRandomTorch(random), 4);
        for (int i2 = i - 3; i2 <= i + 3; ++i2) {
            this.func_150516_a(world, i2, j + 5, k - 3, LOTRMod.stairsMallorn, 6);
            this.func_150516_a(world, i2, j + 5, k + 3, LOTRMod.stairsMallorn, 7);
        }
        for (int k8 = k - 2; k8 <= k + 2; ++k8) {
            this.func_150516_a(world, i - 3, j + 5, k8, LOTRMod.stairsMallorn, 4);
            this.func_150516_a(world, i + 3, j + 5, k8, LOTRMod.stairsMallorn, 5);
        }
        for (int i2 = i - 4; i2 <= i + 4; i2 += 8) {
            for (int k5 = k - 4; k5 <= k + 4; k5 += 8) {
                for (int j5 = j + 1; j5 <= j + 5; ++j5) {
                    this.func_150516_a(world, i2, j5, k5, LOTRMod.wood, 1);
                }
                this.func_150516_a(world, i2 + 1, j + 3, k5, LOTRWorldGenElfHouse.getRandomTorch(random), 1);
                this.func_150516_a(world, i2 - 1, j + 3, k5, LOTRWorldGenElfHouse.getRandomTorch(random), 2);
                this.func_150516_a(world, i2, j + 3, k5 + 1, LOTRWorldGenElfHouse.getRandomTorch(random), 3);
                this.func_150516_a(world, i2, j + 3, k5 - 1, LOTRWorldGenElfHouse.getRandomTorch(random), 4);
            }
        }
        this.func_150516_a(world, i - 5, j + 1, k - 5, LOTRMod.elvenTable, 0);
        this.func_150516_a(world, i - 5, j + 1, k - 4, LOTRMod.stairsMallorn, 7);
        this.placeFlowerPot(world, i - 5, j + 2, k - 4, LOTRWorldGenElfHouse.getRandomPlant(random));
        this.func_150516_a(world, i - 4, j + 1, k - 5, LOTRMod.stairsMallorn, 5);
        this.placeFlowerPot(world, i - 4, j + 2, k - 5, LOTRWorldGenElfHouse.getRandomPlant(random));
        this.func_150516_a(world, i + 5, j + 1, k - 5, LOTRMod.elvenTable, 0);
        this.func_150516_a(world, i + 5, j + 1, k - 4, LOTRMod.stairsMallorn, 7);
        this.placeFlowerPot(world, i + 5, j + 2, k - 4, LOTRWorldGenElfHouse.getRandomPlant(random));
        this.func_150516_a(world, i + 4, j + 1, k - 5, LOTRMod.stairsMallorn, 4);
        this.placeFlowerPot(world, i + 4, j + 2, k - 5, LOTRWorldGenElfHouse.getRandomPlant(random));
        this.func_150516_a(world, i - 5, j + 1, k + 5, LOTRMod.elvenTable, 0);
        this.func_150516_a(world, i - 5, j + 1, k + 4, LOTRMod.stairsMallorn, 6);
        this.placeFlowerPot(world, i - 5, j + 2, k + 4, LOTRWorldGenElfHouse.getRandomPlant(random));
        this.func_150516_a(world, i - 4, j + 1, k + 5, LOTRMod.stairsMallorn, 5);
        this.placeFlowerPot(world, i - 4, j + 2, k + 5, LOTRWorldGenElfHouse.getRandomPlant(random));
        this.func_150516_a(world, i + 5, j + 1, k + 5, LOTRMod.elvenTable, 0);
        this.func_150516_a(world, i + 5, j + 1, k + 4, LOTRMod.stairsMallorn, 6);
        this.placeFlowerPot(world, i + 5, j + 2, k + 4, LOTRWorldGenElfHouse.getRandomPlant(random));
        this.func_150516_a(world, i + 4, j + 1, k + 5, LOTRMod.stairsMallorn, 4);
        this.placeFlowerPot(world, i + 4, j + 2, k + 5, LOTRWorldGenElfHouse.getRandomPlant(random));
        this.placeRandomChandelier(world, random, i - 8, j + 5, k);
        this.placeRandomChandelier(world, random, i + 8, j + 5, k);
        this.placeRandomChandelier(world, random, i, j + 5, k - 8);
        this.placeRandomChandelier(world, random, i, j + 5, k + 8);
        for (int i2 = i - 8; i2 <= i + 8; i2 += 16) {
            for (int k5 = k - 8; k5 <= k + 8; k5 += 16) {
                for (int j5 = j + 2; j5 <= j + 5; ++j5) {
                    this.func_150516_a(world, i2, j5, k5, LOTRMod.planks, 1);
                }
                for (int i5 = i2 - 1; i5 <= i2 + 1; ++i5) {
                    this.func_150516_a(world, i5, j + 5, k5 - 1, LOTRMod.stairsMallorn, 6);
                    this.func_150516_a(world, i5, j + 5, k5 + 1, LOTRMod.stairsMallorn, 7);
                }
                this.func_150516_a(world, i2 - 1, j + 5, k5, LOTRMod.stairsMallorn, 4);
                this.func_150516_a(world, i2 + 1, j + 5, k5, LOTRMod.stairsMallorn, 5);
            }
        }
        for (int j6 = j + 2; j6 <= j + 5; ++j6) {
            this.func_150516_a(world, i - 12, j6, k - 4, LOTRMod.wood, 1);
            this.func_150516_a(world, i - 12, j6, k + 4, LOTRMod.wood, 1);
            this.func_150516_a(world, i + 12, j6, k - 4, LOTRMod.wood, 1);
            this.func_150516_a(world, i + 12, j6, k + 4, LOTRMod.wood, 1);
            this.func_150516_a(world, i - 4, j6, k - 12, LOTRMod.wood, 1);
            this.func_150516_a(world, i + 4, j6, k - 12, LOTRMod.wood, 1);
            this.func_150516_a(world, i - 4, j6, k + 12, LOTRMod.wood, 1);
            this.func_150516_a(world, i + 4, j6, k + 12, LOTRMod.wood, 1);
        }
        for (int k8 = k - 5; k8 <= k + 5; ++k8) {
            if (Math.abs(k8 - k) <= 2) {
                this.func_150516_a(world, i - 12, j + 5, k8, LOTRMod.woodSlabSingle, 9);
                this.func_150516_a(world, i + 12, j + 5, k8, LOTRMod.woodSlabSingle, 9);
            }
            else {
                this.func_150516_a(world, i - 11, j + 5, k8, LOTRMod.stairsMallorn, 5);
                this.func_150516_a(world, i + 11, j + 5, k8, LOTRMod.stairsMallorn, 4);
            }
            if (k8 - k == -5 || k8 - k == 3) {
                this.func_150516_a(world, i - 12, j + 5, k8, LOTRMod.stairsMallorn, 6);
                this.func_150516_a(world, i + 12, j + 5, k8, LOTRMod.stairsMallorn, 6);
            }
            else if (k8 - k == -3 || k8 - k == 5) {
                this.func_150516_a(world, i - 12, j + 5, k8, LOTRMod.stairsMallorn, 7);
                this.func_150516_a(world, i + 12, j + 5, k8, LOTRMod.stairsMallorn, 7);
            }
        }
        for (int i2 = i - 5; i2 <= i + 5; ++i2) {
            if (Math.abs(i2 - i) <= 2) {
                this.func_150516_a(world, i2, j + 5, k - 12, LOTRMod.woodSlabSingle, 9);
                this.func_150516_a(world, i2, j + 5, k + 12, LOTRMod.woodSlabSingle, 9);
            }
            else {
                this.func_150516_a(world, i2, j + 5, k - 11, LOTRMod.stairsMallorn, 7);
                this.func_150516_a(world, i2, j + 5, k + 11, LOTRMod.stairsMallorn, 6);
            }
            if (i2 - i == -5 || i2 - i == 3) {
                this.func_150516_a(world, i2, j + 5, k - 12, LOTRMod.stairsMallorn, 4);
                this.func_150516_a(world, i2, j + 5, k + 12, LOTRMod.stairsMallorn, 4);
            }
            else if (i2 - i == -3 || i2 - i == 5) {
                this.func_150516_a(world, i2, j + 5, k - 12, LOTRMod.stairsMallorn, 5);
                this.func_150516_a(world, i2, j + 5, k + 12, LOTRMod.stairsMallorn, 5);
            }
        }
        this.func_150516_a(world, i + 6, j + 1, k, LOTRMod.elvenBed, 3);
        this.func_150516_a(world, i + 7, j + 1, k, LOTRMod.elvenBed, 11);
        this.func_150516_a(world, i, j + 1, k + 7, LOTRMod.commandTable, 0);
        this.placeBanner(world, i, j + 2, k - 11, 0, LOTRItemBanner.BannerType.GALADHRIM);
        this.placeBanner(world, i + 11, j + 2, k, 1, LOTRItemBanner.BannerType.GALADHRIM);
        this.placeBanner(world, i, j + 2, k + 11, 2, LOTRItemBanner.BannerType.GALADHRIM);
        this.placeBanner(world, i - 11, j + 2, k, 3, LOTRItemBanner.BannerType.GALADHRIM);
        this.tryPlaceLight(world, i - 12, j, k - 2, random);
        this.tryPlaceLight(world, i - 12, j, k + 2, random);
        this.tryPlaceLight(world, i - 9, j, k + 9, random);
        this.tryPlaceLight(world, i - 2, j, k + 12, random);
        this.tryPlaceLight(world, i + 2, j, k + 12, random);
        this.tryPlaceLight(world, i + 9, j, k + 9, random);
        this.tryPlaceLight(world, i + 12, j, k + 2, random);
        this.tryPlaceLight(world, i + 12, j, k - 2, random);
        this.tryPlaceLight(world, i + 9, j, k - 9, random);
        this.tryPlaceLight(world, i + 2, j, k - 12, random);
        this.tryPlaceLight(world, i - 2, j, k - 12, random);
        this.tryPlaceLight(world, i - 9, j, k - 9, random);
        for (int i2 = i - 4; i2 <= i - 3; ++i2) {
            for (int k5 = k - 3; k5 <= k; ++k5) {
                this.func_150516_a(world, i2, j, k5, Blocks.air, 0);
            }
            this.func_150516_a(world, i2, j, k - 3, LOTRMod.stairsMallorn, 3);
        }
        final LOTREntityElf elfLord = new LOTREntityGaladhrimLord(world);
        elfLord.setLocationAndAngles(i + 0.5, (double)(j + 1), k + 3.5, 0.0f, 0.0f);
        elfLord.spawnRidingHorse = false;
        elfLord.onSpawnWithEgg(null);
        elfLord.setHomeArea(i, j, k, 8);
        world.spawnEntityInWorld((Entity)elfLord);
        return true;
    }
    
    private void buildStaircase(final World world, final Random random, final int i, final int j, final int k) {
        int i2 = i - 3;
        int j2 = j - 1;
        int k2 = k - 2;
        int l = 0;
        while (j2 >= 0) {
            final Block block = world.getBlock(i2, j2, k2);
            if (block.isOpaqueCube() && !block.isWood((IBlockAccess)world, i2, j2, k2)) {
                break;
            }
            final int l2 = l % 24;
            if (l2 < 5) {
                for (int i3 = i2; i3 >= i2 - 2; --i3) {
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        this.func_150516_a(world, i3, j3, k2, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i2, j2, k2, LOTRMod.stairsMallorn, 3);
                this.func_150516_a(world, i2 - 1, j2, k2, LOTRMod.stairsMallorn, 3);
                this.func_150516_a(world, i2 - 2, j2, k2, LOTRMod.stairsMallorn, 4);
                this.func_150516_a(world, i2 - 2, j2 + 1, k2, LOTRMod.fence, 1);
                if (l2 > 0) {
                    this.func_150516_a(world, i2 - 2, j2 + 2, k2, LOTRMod.fence, 1);
                }
                --j2;
                ++k2;
            }
            else if (l2 == 5) {
                for (int i3 = i2; i3 >= i2 - 2; --i3) {
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        for (int k3 = k2; k3 <= k2 + 2; ++k3) {
                            this.func_150516_a(world, i3, j3, k3, Blocks.air, 0);
                        }
                    }
                }
                for (int i3 = i2; i3 >= i2 - 1; --i3) {
                    for (int k4 = k2; k4 <= k2 + 1; ++k4) {
                        this.func_150516_a(world, i3, j2, k4, LOTRMod.planks, 1);
                    }
                }
                for (int k5 = k2; k5 <= k2 + 2; ++k5) {
                    this.func_150516_a(world, i2 - 2, j2, k5, LOTRMod.stairsMallorn, 4);
                    this.func_150516_a(world, i2 - 2, j2 + 1, k5, LOTRMod.fence, 1);
                }
                for (int i3 = i2; i3 >= i2 - 1; --i3) {
                    this.func_150516_a(world, i3, j2, k2 + 2, LOTRMod.stairsMallorn, 7);
                    this.func_150516_a(world, i3, j2 + 1, k2 + 2, LOTRMod.fence, 1);
                }
                this.func_150516_a(world, i2 - 2, j2 + 2, k2, LOTRMod.fence, 1);
                this.func_150516_a(world, i2 - 2, j2 + 2, k2 + 2, LOTRWorldGenElfHouse.getRandomTorch(random), 5);
                ++i2;
            }
            else if (l2 < 11) {
                for (int k5 = k2; k5 <= k2 + 2; ++k5) {
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        this.func_150516_a(world, i2, j3, k5, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i2, j2, k2, LOTRMod.stairsMallorn, 1);
                this.func_150516_a(world, i2, j2, k2 + 1, LOTRMod.stairsMallorn, 1);
                this.func_150516_a(world, i2, j2, k2 + 2, LOTRMod.stairsMallorn, 7);
                this.func_150516_a(world, i2, j2 + 1, k2 + 2, LOTRMod.fence, 1);
                if (l2 > 6) {
                    this.func_150516_a(world, i2, j2 + 2, k2 + 2, LOTRMod.fence, 1);
                }
                --j2;
                ++i2;
            }
            else if (l2 == 11) {
                for (int i3 = i2; i3 <= i2 + 2; ++i3) {
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        for (int k3 = k2; k3 <= k2 + 2; ++k3) {
                            this.func_150516_a(world, i3, j3, k3, Blocks.air, 0);
                        }
                    }
                }
                for (int i3 = i2; i3 <= i2 + 1; ++i3) {
                    for (int k4 = k2; k4 <= k2 + 1; ++k4) {
                        this.func_150516_a(world, i3, j2, k4, LOTRMod.planks, 1);
                    }
                }
                for (int i3 = i2; i3 <= i2 + 2; ++i3) {
                    this.func_150516_a(world, i3, j2, k2 + 2, LOTRMod.stairsMallorn, 7);
                    this.func_150516_a(world, i3, j2 + 1, k2 + 2, LOTRMod.fence, 1);
                }
                for (int k5 = k2; k5 <= k2 + 1; ++k5) {
                    this.func_150516_a(world, i2 + 2, j2, k5, LOTRMod.stairsMallorn, 5);
                    this.func_150516_a(world, i2 + 2, j2 + 1, k5, LOTRMod.fence, 1);
                }
                this.func_150516_a(world, i2, j2 + 2, k2 + 2, LOTRMod.fence, 1);
                this.func_150516_a(world, i2 + 2, j2 + 2, k2 + 2, LOTRWorldGenElfHouse.getRandomTorch(random), 5);
                --k2;
            }
            else if (l2 < 17) {
                for (int i3 = i2; i3 <= i2 + 2; ++i3) {
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        this.func_150516_a(world, i3, j3, k2, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i2, j2, k2, LOTRMod.stairsMallorn, 2);
                this.func_150516_a(world, i2 + 1, j2, k2, LOTRMod.stairsMallorn, 2);
                this.func_150516_a(world, i2 + 2, j2, k2, LOTRMod.stairsMallorn, 5);
                this.func_150516_a(world, i2 + 2, j2 + 1, k2, LOTRMod.fence, 1);
                if (l2 > 12) {
                    this.func_150516_a(world, i2 + 2, j2 + 2, k2, LOTRMod.fence, 1);
                }
                --j2;
                --k2;
            }
            else if (l2 == 17) {
                for (int i3 = i2; i3 <= i2 + 2; ++i3) {
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        for (int k3 = k2; k3 >= k2 - 2; --k3) {
                            this.func_150516_a(world, i3, j3, k3, Blocks.air, 0);
                        }
                    }
                }
                for (int i3 = i2; i3 <= i2 + 1; ++i3) {
                    for (int k4 = k2; k4 >= k2 - 1; --k4) {
                        this.func_150516_a(world, i3, j2, k4, LOTRMod.planks, 1);
                    }
                }
                for (int k5 = k2; k5 >= k2 - 2; --k5) {
                    this.func_150516_a(world, i2 + 2, j2, k5, LOTRMod.stairsMallorn, 5);
                    this.func_150516_a(world, i2 + 2, j2 + 1, k5, LOTRMod.fence, 1);
                }
                for (int i3 = i2; i3 <= i2 + 1; ++i3) {
                    this.func_150516_a(world, i3, j2, k2 - 2, LOTRMod.stairsMallorn, 6);
                    this.func_150516_a(world, i3, j2 + 1, k2 - 2, LOTRMod.fence, 1);
                }
                this.func_150516_a(world, i2 + 2, j2 + 2, k2, LOTRMod.fence, 1);
                this.func_150516_a(world, i2 + 2, j2 + 2, k2 - 2, LOTRWorldGenElfHouse.getRandomTorch(random), 5);
                --i2;
            }
            else if (l2 < 23) {
                for (int k5 = k2; k5 >= k2 - 2; --k5) {
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        this.func_150516_a(world, i2, j3, k5, Blocks.air, 0);
                    }
                }
                this.func_150516_a(world, i2, j2, k2, LOTRMod.stairsMallorn, 0);
                this.func_150516_a(world, i2, j2, k2 - 1, LOTRMod.stairsMallorn, 0);
                this.func_150516_a(world, i2, j2, k2 - 2, LOTRMod.stairsMallorn, 6);
                this.func_150516_a(world, i2, j2 + 1, k2 - 2, LOTRMod.fence, 1);
                if (l2 > 18) {
                    this.func_150516_a(world, i2, j2 + 2, k2 - 2, LOTRMod.fence, 1);
                }
                --j2;
                --i2;
            }
            else if (l2 == 23) {
                for (int i3 = i2; i3 >= i2 - 2; --i3) {
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        for (int k3 = k2; k3 >= k2 - 2; --k3) {
                            this.func_150516_a(world, i3, j3, k3, Blocks.air, 0);
                        }
                    }
                }
                for (int i3 = i2; i3 >= i2 - 1; --i3) {
                    for (int k4 = k2; k4 >= k2 - 1; --k4) {
                        this.func_150516_a(world, i3, j2, k4, LOTRMod.planks, 1);
                    }
                }
                for (int i3 = i2; i3 >= i2 - 2; --i3) {
                    this.func_150516_a(world, i3, j2, k2 - 2, LOTRMod.stairsMallorn, 6);
                    this.func_150516_a(world, i3, j2 + 1, k2 - 2, LOTRMod.fence, 1);
                }
                for (int k5 = k2; k5 >= k2 - 1; --k5) {
                    this.func_150516_a(world, i2 - 2, j2, k5, LOTRMod.stairsMallorn, 4);
                    this.func_150516_a(world, i2 - 2, j2 + 1, k5, LOTRMod.fence, 1);
                }
                this.func_150516_a(world, i2, j2 + 2, k2 - 2, LOTRMod.fence, 1);
                this.func_150516_a(world, i2 - 2, j2 + 2, k2 - 2, LOTRWorldGenElfHouse.getRandomTorch(random), 5);
                ++k2;
            }
            ++l;
        }
    }
    
    private void buildStairCircle(final World world, final int i, final int j, final int k, final int range, final boolean upsideDown, final boolean insideOut) {
        for (int i2 = i - range; i2 <= i + range; ++i2) {
            for (int k2 = k - range; k2 <= k + range; ++k2) {
                if (world.isAirBlock(i2, j, k2)) {
                    int direction = -1;
                    if (this.isMallornPlanks(world, i2 - 1, j, k2)) {
                        direction = 1;
                    }
                    else if (this.isMallornPlanks(world, i2 + 1, j, k2)) {
                        direction = 3;
                    }
                    else if (this.isMallornPlanks(world, i2, j, k2 - 1)) {
                        direction = 2;
                    }
                    else if (this.isMallornPlanks(world, i2, j, k2 + 1)) {
                        direction = 0;
                    }
                    else if (this.isMallornPlanks(world, i2 - 1, j, k2 - 1) || this.isMallornPlanks(world, i2 + 1, j, k2 - 1)) {
                        direction = 2;
                    }
                    else if (this.isMallornPlanks(world, i2 - 1, j, k2 + 1) || this.isMallornPlanks(world, i2 + 1, j, k2 + 1)) {
                        direction = 0;
                    }
                    if (direction != -1) {
                        if (insideOut) {
                            direction += 4;
                            direction &= 0x3;
                        }
                        int meta = 0;
                        switch (direction) {
                            case 0: {
                                meta = 2;
                                break;
                            }
                            case 1: {
                                meta = 1;
                                break;
                            }
                            case 2: {
                                meta = 3;
                                break;
                            }
                            case 3: {
                                meta = 0;
                                break;
                            }
                        }
                        if (upsideDown) {
                            meta |= 0x4;
                        }
                        this.func_150516_a(world, i2, j, k2, LOTRMod.stairsMallorn, meta);
                    }
                }
            }
        }
    }
    
    private boolean isMallornPlanks(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j, k) == LOTRMod.planks && world.getBlockMetadata(i, j, k) == 1;
    }
    
    private void tryPlaceLight(final World world, final int i, final int j, final int k, final Random random) {
        final int height = 3 + random.nextInt(7);
        for (int j2 = j; j2 >= j - height; --j2) {
            if (super.restrictions) {
                if (!world.isAirBlock(i, j2, k)) {
                    return;
                }
                if (j2 == j - height && (!world.isAirBlock(i, j2, k - 1) || !world.isAirBlock(i, j2, k + 1) || !world.isAirBlock(i - 1, j2, k) || !world.isAirBlock(i + 1, j2, k))) {
                    return;
                }
            }
        }
        for (int j2 = j; j2 >= j - height; --j2) {
            if (j2 == j - height) {
                this.func_150516_a(world, i, j2, k, LOTRMod.planks, 1);
                this.func_150516_a(world, i, j2, k - 1, LOTRWorldGenElfHouse.getRandomTorch(random), 4);
                this.func_150516_a(world, i, j2, k + 1, LOTRWorldGenElfHouse.getRandomTorch(random), 3);
                this.func_150516_a(world, i - 1, j2, k, LOTRWorldGenElfHouse.getRandomTorch(random), 2);
                this.func_150516_a(world, i + 1, j2, k, LOTRWorldGenElfHouse.getRandomTorch(random), 1);
            }
            else {
                this.func_150516_a(world, i, j2, k, LOTRMod.fence, 1);
            }
        }
    }
    
    private void placeRandomChandelier(final World world, final Random random, final int i, final int j, final int k) {
        final ItemStack itemstack = LOTRWorldGenElfHouse.getRandomChandelier(random);
        this.func_150516_a(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
    }
}
