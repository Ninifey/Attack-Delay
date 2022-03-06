// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGaladhrimElf;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import lotr.common.world.feature.LOTRWorldGenMallornExtreme;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenElfHouse extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenElfHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, (super.usingPlayer != null) ? 2 : 0);
        if (super.usingPlayer != null) {
            final LOTRWorldGenMallornExtreme treeGen = new LOTRWorldGenMallornExtreme(true);
            final int i2 = 0;
            final int j2 = 0;
            final int k2 = 0;
            final int height = treeGen.generateAndReturnHeight(world, random, this.getX(i2, k2), this.getY(j2), this.getZ(i2, k2), true);
            super.originY += MathHelper.floor_double((double)(height * MathHelper.randomFloatClamp(random, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MIN, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MAX)));
        }
        if (super.restrictions) {
            for (int i3 = -8; i3 <= 8; ++i3) {
                for (int j3 = -3; j3 <= 6; ++j3) {
                    for (int k3 = -8; k3 <= 8; ++k3) {
                        if (Math.abs(i3) > 2 || Math.abs(k3) > 2) {
                            if (!this.isAir(world, i3, j3, k3)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        else if (super.usingPlayer != null) {
            for (int i3 = -2; i3 <= 2; ++i3) {
                for (int k4 = -2; k4 <= 2; ++k4) {
                    for (int j2 = 0; !this.isOpaque(world, i3, j2, k4) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k4, LOTRMod.wood, 1);
                    }
                }
            }
        }
        for (int i3 = -7; i3 <= 7; ++i3) {
            for (int j3 = 1; j3 <= 4; ++j3) {
                for (int k3 = -7; k3 <= 7; ++k3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, Blocks.air, 0);
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int j3 = -1; j3 <= 5; ++j3) {
                for (int k3 = -2; k3 <= 2; ++k3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, LOTRMod.wood, 1);
                    if (j3 >= 1 && j3 <= 2 && Math.abs(i3) == 2 && Math.abs(k3) == 2) {
                        this.setBlockAndMetadata(world, i3, j3, k3, LOTRMod.fence, 1);
                    }
                }
            }
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k4 = -6; k4 <= 6; ++k4) {
                if (Math.abs(i3) > 2 || Math.abs(k4) > 2) {
                    if (Math.abs(i3) != 6) {
                        if (Math.abs(k4) != 6) {
                            this.setBlockAndMetadata(world, i3, 0, k4, LOTRMod.planks, 1);
                        }
                    }
                }
            }
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -6, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, i3, 0, 6, LOTRMod.planks, 1);
        }
        for (int k5 = -5; k5 <= 5; ++k5) {
            this.setBlockAndMetadata(world, -6, 0, k5, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, 6, 0, k5, LOTRMod.planks, 1);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -7, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, i3, 0, 7, LOTRMod.planks, 1);
        }
        for (int k5 = -3; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -7, 0, k5, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, 7, 0, k5, LOTRMod.planks, 1);
        }
        for (int j4 = 1; j4 <= 4; ++j4) {
            this.setBlockAndMetadata(world, -5, j4, -5, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, 5, j4, -5, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, -5, j4, 5, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, 5, j4, 5, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, -6, j4, -3, LOTRMod.wood, 1);
            this.setBlockAndMetadata(world, -6, j4, 3, LOTRMod.wood, 1);
            this.setBlockAndMetadata(world, 6, j4, -3, LOTRMod.wood, 1);
            this.setBlockAndMetadata(world, 6, j4, 3, LOTRMod.wood, 1);
            this.setBlockAndMetadata(world, -3, j4, -6, LOTRMod.wood, 1);
            this.setBlockAndMetadata(world, -3, j4, 6, LOTRMod.wood, 1);
            this.setBlockAndMetadata(world, 3, j4, -6, LOTRMod.wood, 1);
            this.setBlockAndMetadata(world, 3, j4, 6, LOTRMod.wood, 1);
        }
        this.setBlockAndMetadata(world, -4, 2, -5, getRandomTorch(random), 2);
        this.setBlockAndMetadata(world, -5, 2, -4, getRandomTorch(random), 3);
        this.setBlockAndMetadata(world, 4, 2, -5, getRandomTorch(random), 1);
        this.setBlockAndMetadata(world, 5, 2, -4, getRandomTorch(random), 3);
        this.setBlockAndMetadata(world, -4, 2, 5, getRandomTorch(random), 2);
        this.setBlockAndMetadata(world, -5, 2, 4, getRandomTorch(random), 4);
        this.setBlockAndMetadata(world, 4, 2, 5, getRandomTorch(random), 1);
        this.setBlockAndMetadata(world, 5, 2, 4, getRandomTorch(random), 4);
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 1, -7, LOTRMod.fence, 1);
            this.setBlockAndMetadata(world, i3, 1, 7, LOTRMod.fence, 1);
        }
        for (int k5 = -3; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -7, 1, k5, LOTRMod.fence, 1);
            this.setBlockAndMetadata(world, 7, 1, k5, LOTRMod.fence, 1);
        }
        this.setBlockAndMetadata(world, -4, 1, -6, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, -5, 1, -6, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, -4, 1, 6, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, -5, 1, 6, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, 4, 1, -6, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, 5, 1, -6, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, 4, 1, 6, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, 5, 1, 6, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, -6, 1, -4, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, -6, 1, -5, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, 6, 1, -4, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, 6, 1, -5, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, -6, 1, 4, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, -6, 1, 5, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, 6, 1, 4, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, 6, 1, 5, LOTRMod.fence, 1);
        this.setBlockAndMetadata(world, -6, 4, -2, LOTRMod.stairsMallorn, 7);
        this.setBlockAndMetadata(world, -6, 4, -1, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, -6, 4, 0, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, -6, 4, 1, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, -6, 4, 2, LOTRMod.stairsMallorn, 6);
        this.setBlockAndMetadata(world, 6, 4, -2, LOTRMod.stairsMallorn, 7);
        this.setBlockAndMetadata(world, 6, 4, -1, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 6, 4, 0, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 6, 4, 1, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 6, 4, 2, LOTRMod.stairsMallorn, 6);
        this.setBlockAndMetadata(world, -2, 4, -6, LOTRMod.stairsMallorn, 4);
        this.setBlockAndMetadata(world, -1, 4, -6, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 0, 4, -6, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 1, 4, -6, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 2, 4, -6, LOTRMod.stairsMallorn, 5);
        this.setBlockAndMetadata(world, -2, 4, 6, LOTRMod.stairsMallorn, 4);
        this.setBlockAndMetadata(world, -1, 4, 6, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 0, 4, 6, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 1, 4, 6, LOTRMod.woodSlabSingle, 9);
        this.setBlockAndMetadata(world, 2, 4, 6, LOTRMod.stairsMallorn, 5);
        for (int i3 = -6; i3 <= -4; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -6, LOTRMod.stairsMallorn, 6);
            this.setBlockAndMetadata(world, i3, 4, 6, LOTRMod.stairsMallorn, 7);
        }
        for (int i3 = 4; i3 <= 6; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -6, LOTRMod.stairsMallorn, 6);
            this.setBlockAndMetadata(world, i3, 4, 6, LOTRMod.stairsMallorn, 7);
        }
        for (int k5 = -6; k5 <= -4; ++k5) {
            this.setBlockAndMetadata(world, -6, 4, k5, LOTRMod.stairsMallorn, 5);
            this.setBlockAndMetadata(world, 6, 4, k5, LOTRMod.stairsMallorn, 4);
        }
        for (int k5 = 4; k5 <= 6; ++k5) {
            this.setBlockAndMetadata(world, -6, 4, k5, LOTRMod.stairsMallorn, 5);
            this.setBlockAndMetadata(world, 6, 4, k5, LOTRMod.stairsMallorn, 4);
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            if (Math.abs(i3) > 1) {
                this.setBlockAndMetadata(world, i3, 4, -5, LOTRMod.stairsMallorn, 7);
                this.setBlockAndMetadata(world, i3, 4, 5, LOTRMod.stairsMallorn, 6);
            }
        }
        for (int k5 = -4; k5 <= 4; ++k5) {
            if (Math.abs(k5) > 1) {
                this.setBlockAndMetadata(world, -5, 4, k5, LOTRMod.stairsMallorn, 4);
                this.setBlockAndMetadata(world, 5, 4, k5, LOTRMod.stairsMallorn, 5);
            }
        }
        for (int i3 = -6; i3 <= 6; ++i3) {
            for (int k4 = -6; k4 <= 6; ++k4) {
                if (!super.restrictions || i3 < -2 || i3 > 2 || k4 < -2 || k4 > 2) {
                    if (i3 == -6 || i3 == 6) {
                        if (k4 == -6) {
                            continue;
                        }
                        if (k4 == 6) {
                            continue;
                        }
                    }
                    this.setBlockAndMetadata(world, i3, 5, k4, LOTRMod.planks, 1);
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -7, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, i3, 5, 7, LOTRMod.planks, 1);
        }
        for (int k5 = -3; k5 <= 3; ++k5) {
            this.setBlockAndMetadata(world, -7, 5, k5, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, 7, 5, k5, LOTRMod.planks, 1);
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            for (int k4 = -5; k4 <= 5; ++k4) {
                if (!super.restrictions || i3 < -2 || i3 > 2 || k4 < -2 || k4 > 2) {
                    if (i3 == -5 || i3 == 5) {
                        if (k4 == -5) {
                            continue;
                        }
                        if (k4 == 5) {
                            continue;
                        }
                    }
                    this.setBlockAndMetadata(world, i3, 6, k4, LOTRMod.planks, 1);
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -6, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, i3, 6, 6, LOTRMod.planks, 1);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -6, 6, k5, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, 6, 6, k5, LOTRMod.planks, 1);
        }
        for (int i3 = -8; i3 <= 8; ++i3) {
            int stairZ = 0;
            int stairX = i3;
            final int i4 = Math.abs(i3);
            if (i4 <= 3) {
                stairZ = 8;
            }
            else if (i4 <= 5) {
                stairZ = 7;
            }
            else if (i4 <= 7) {
                stairZ = 6;
            }
            else {
                stairZ = 4;
            }
            this.setBlockAndMetadata(world, stairX, 5, -stairZ, LOTRMod.stairsMallorn, 2);
            this.setBlockAndMetadata(world, stairX, 5, stairZ, LOTRMod.stairsMallorn, 3);
            --stairZ;
            stairX = Integer.signum(stairX) * (Math.abs(stairX) - 1);
            this.setBlockAndMetadata(world, stairX, 6, -stairZ, LOTRMod.stairsMallorn, 2);
            this.setBlockAndMetadata(world, stairX, 6, stairZ, LOTRMod.stairsMallorn, 3);
        }
        for (int k5 = -8; k5 <= 8; ++k5) {
            int stairX2 = 0;
            int stairZ2 = k5;
            final int k6 = Math.abs(k5);
            if (k6 <= 3) {
                stairX2 = 8;
            }
            else if (k6 <= 5) {
                stairX2 = 7;
            }
            else if (k6 <= 7) {
                stairX2 = 6;
            }
            else {
                stairX2 = 4;
            }
            this.setBlockAndMetadata(world, -stairX2, 5, stairZ2, LOTRMod.stairsMallorn, 1);
            this.setBlockAndMetadata(world, stairX2, 5, stairZ2, LOTRMod.stairsMallorn, 0);
            --stairX2;
            stairZ2 = Integer.signum(stairZ2) * (Math.abs(stairZ2) - 1);
            this.setBlockAndMetadata(world, -stairX2, 6, stairZ2, LOTRMod.stairsMallorn, 1);
            this.setBlockAndMetadata(world, stairX2, 6, stairZ2, LOTRMod.stairsMallorn, 0);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -3, LOTRMod.stairsMallorn, 6);
            this.setBlockAndMetadata(world, i3, 4, 3, LOTRMod.stairsMallorn, 7);
        }
        for (int k5 = -2; k5 <= 2; ++k5) {
            this.setBlockAndMetadata(world, -3, 4, k5, LOTRMod.stairsMallorn, 5);
            this.setBlockAndMetadata(world, 3, 4, k5, LOTRMod.stairsMallorn, 4);
        }
        for (int bough = 0; bough <= 2; ++bough) {
            final int j3 = -3 + bough;
            final int i5 = 0 + bough;
            final int k2 = 3 + bough;
            for (int i6 = -i5; i6 <= i5; ++i6) {
                for (int k7 = -k2; k7 <= k2; ++k7) {
                    this.setBlockAndMetadata(world, i6, j3, k7, LOTRMod.wood, 13);
                    this.setBlockAndMetadata(world, k7, j3, i6, LOTRMod.wood, 13);
                }
            }
        }
        final Block ladder = random.nextBoolean() ? LOTRMod.hithlainLadder : LOTRMod.mallornLadder;
        for (int j3 = 3; j3 >= -3 || (!this.isOpaque(world, 0, j3, -3) && this.getY(j3) >= 0); --j3) {
            this.setBlockAndMetadata(world, 0, j3, -3, ladder, 2);
        }
        this.setBlockAndMetadata(world, -2, 1, 0, LOTRMod.elvenTable, 0);
        this.setBlockAndMetadata(world, -2, 2, 0, Blocks.air, 0);
        this.setBlockAndMetadata(world, -2, 3, 0, Blocks.air, 0);
        this.setBlockAndMetadata(world, -2, 4, 0, LOTRMod.wood, 5);
        this.setBlockAndMetadata(world, 2, 1, 0, LOTRMod.elvenTable, 0);
        this.setBlockAndMetadata(world, 2, 2, 0, Blocks.air, 0);
        this.setBlockAndMetadata(world, 2, 3, 0, Blocks.air, 0);
        this.setBlockAndMetadata(world, 2, 4, 0, LOTRMod.wood, 5);
        this.placeChest(world, random, 0, 1, 2, LOTRMod.chestMallorn, 0, LOTRChestContents.ELF_HOUSE);
        this.setBlockAndMetadata(world, 0, 2, 2, Blocks.air, 0);
        this.setBlockAndMetadata(world, 0, 3, 2, Blocks.air, 0);
        this.setBlockAndMetadata(world, 0, 4, 2, LOTRMod.wood, 9);
        this.tryPlaceLight(world, -7, -1, -3, random);
        this.tryPlaceLight(world, -7, -1, 3, random);
        this.tryPlaceLight(world, 7, -1, -3, random);
        this.tryPlaceLight(world, 7, -1, 3, random);
        this.tryPlaceLight(world, -3, -1, -7, random);
        this.tryPlaceLight(world, 3, -1, -7, random);
        this.tryPlaceLight(world, -3, -1, 7, random);
        this.tryPlaceLight(world, 3, -1, 7, random);
        this.placeFlowerPot(world, -4, 1, -5, getRandomPlant(random));
        this.placeFlowerPot(world, -5, 1, -4, getRandomPlant(random));
        this.placeFlowerPot(world, -5, 1, 4, getRandomPlant(random));
        this.placeFlowerPot(world, -4, 1, 5, getRandomPlant(random));
        this.placeFlowerPot(world, 4, 1, -5, getRandomPlant(random));
        this.placeFlowerPot(world, 5, 1, -4, getRandomPlant(random));
        this.placeFlowerPot(world, 5, 1, 4, getRandomPlant(random));
        this.placeFlowerPot(world, 4, 1, 5, getRandomPlant(random));
        this.setBlockAndMetadata(world, -2, 1, 5, LOTRMod.elvenBed, 3);
        this.setBlockAndMetadata(world, -3, 1, 5, LOTRMod.elvenBed, 11);
        final LOTREntityElf elf = new LOTREntityGaladhrimElf(world);
        elf.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(elf, world, 0, 1, 4, 8);
        return true;
    }
    
    private void tryPlaceLight(final World world, final int i, final int j, final int k, final Random random) {
        final int height = 2 + random.nextInt(6);
        for (int j2 = j; j2 >= -height; --j2) {
            if (super.restrictions) {
                if (!this.isAir(world, i, j2, k)) {
                    return;
                }
                if (j2 == -height && (!this.isAir(world, i, j2, k - 1) || !this.isAir(world, i, j2, k + 1) || !this.isAir(world, i - 1, j2, k) || !this.isAir(world, i + 1, j2, k))) {
                    return;
                }
            }
        }
        for (int j2 = j; j2 >= j - height; --j2) {
            if (j2 == j - height) {
                this.setBlockAndMetadata(world, i, j2, k, LOTRMod.planks, 1);
                this.setBlockAndMetadata(world, i, j2, k - 1, getRandomTorch(random), 4);
                this.setBlockAndMetadata(world, i, j2, k + 1, getRandomTorch(random), 3);
                this.setBlockAndMetadata(world, i - 1, j2, k, getRandomTorch(random), 1);
                this.setBlockAndMetadata(world, i + 1, j2, k, getRandomTorch(random), 2);
            }
            else {
                this.setBlockAndMetadata(world, i, j2, k, LOTRMod.fence, 1);
            }
        }
    }
    
    public static ItemStack getRandomPlant(final Random random) {
        return random.nextBoolean() ? new ItemStack(LOTRMod.elanor) : new ItemStack(LOTRMod.niphredil);
    }
    
    public static Block getRandomTorch(final Random random) {
        if (random.nextBoolean()) {
            final int i = random.nextInt(3);
            if (i == 0) {
                return LOTRMod.mallornTorchBlue;
            }
            if (i == 1) {
                return LOTRMod.mallornTorchGold;
            }
            if (i == 2) {
                return LOTRMod.mallornTorchGreen;
            }
        }
        return LOTRMod.mallornTorchSilver;
    }
    
    public static ItemStack getRandomChandelier(final Random random) {
        if (random.nextBoolean()) {
            final int i = random.nextInt(3);
            if (i == 0) {
                return new ItemStack(LOTRMod.chandelier, 1, 13);
            }
            if (i == 1) {
                return new ItemStack(LOTRMod.chandelier, 1, 14);
            }
            if (i == 2) {
                return new ItemStack(LOTRMod.chandelier, 1, 15);
            }
        }
        return new ItemStack(LOTRMod.chandelier, 1, 5);
    }
}
