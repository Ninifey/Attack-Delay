// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRFoods;
import net.minecraft.init.Blocks;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;

public class LOTRWorldGenHighElvenHall extends LOTRWorldGenStructureBase
{
    private Block plankBlock;
    private int plankMeta;
    private Block slabBlock;
    private int slabMeta;
    private Block stairBlock;
    private Block roofBlock;
    private int roofMeta;
    private Block roofStairBlock;
    protected Block tableBlock;
    protected Block plateBlock;
    protected LOTRItemBanner.BannerType bannerType;
    protected LOTRChestContents chestContents;
    
    public LOTRWorldGenHighElvenHall(final boolean flag) {
        super(flag);
        this.tableBlock = LOTRMod.highElvenTable;
        this.plateBlock = LOTRMod.plateBlock;
        this.bannerType = LOTRItemBanner.BannerType.HIGH_ELF;
        this.chestContents = LOTRChestContents.HIGH_ELVEN_HALL;
    }
    
    protected LOTREntityElf createElf(final World world) {
        return new LOTREntityHighElf(world);
    }
    
    public boolean generate(final World world, final Random random, int i, int j, int k) {
        if (super.restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
            return false;
        }
        --j;
        int rotation = random.nextInt(4);
        if (!super.restrictions && super.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch (rotation) {
            case 0: {
                i -= 8;
                ++k;
                break;
            }
            case 1: {
                i -= 16;
                k -= 8;
                break;
            }
            case 2: {
                i -= 7;
                k -= 16;
                break;
            }
            case 3: {
                ++i;
                k -= 7;
                break;
            }
        }
        if (super.restrictions) {
            int minHeight = j + 1;
            int maxHeight = j + 1;
            for (int i2 = i - 1; i2 <= i + 16; ++i2) {
                for (int k2 = k - 1; k2 <= k + 16; ++k2) {
                    final int j2 = world.getTopSolidOrLiquidBlock(i2, k2);
                    final Block block = world.getBlock(i2, j2 - 1, k2);
                    if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
                        return false;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                }
            }
            if (Math.abs(maxHeight - minHeight) > 5) {
                return false;
            }
            int height = j + 1;
            for (int i3 = i - 1; i3 <= i + 16; ++i3) {
                for (int k3 = k - 1; k3 <= k + 16; ++k3) {
                    if ((i3 == i - 1 || i3 == i + 16) && (k3 == k - 1 || k3 == k + 16)) {
                        final int j3 = world.getTopSolidOrLiquidBlock(i3, k3);
                        if (j3 > height) {
                            height = j3;
                        }
                    }
                }
            }
            j = height - 1;
        }
        final int randomWood = random.nextInt(4);
        switch (randomWood) {
            case 0: {
                this.plankBlock = Blocks.planks;
                this.plankMeta = 0;
                this.slabBlock = (Block)Blocks.wooden_slab;
                this.slabMeta = 0;
                this.stairBlock = Blocks.oak_stairs;
                break;
            }
            case 1: {
                this.plankBlock = Blocks.planks;
                this.plankMeta = 2;
                this.slabBlock = (Block)Blocks.wooden_slab;
                this.slabMeta = 2;
                this.stairBlock = Blocks.birch_stairs;
                break;
            }
            case 2: {
                this.plankBlock = LOTRMod.planks;
                this.plankMeta = 9;
                this.slabBlock = LOTRMod.woodSlabSingle2;
                this.slabMeta = 1;
                this.stairBlock = LOTRMod.stairsBeech;
                break;
            }
            case 3: {
                this.plankBlock = LOTRMod.planks;
                this.plankMeta = 4;
                this.slabBlock = LOTRMod.woodSlabSingle;
                this.slabMeta = 4;
                this.stairBlock = LOTRMod.stairsApple;
                break;
            }
        }
        final int randomRoof = random.nextInt(5);
        if (randomRoof == 0) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 11;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
        }
        else if (randomRoof == 1) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 3;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
        }
        else if (randomRoof == 2) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 9;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedCyan;
        }
        else if (randomRoof == 3) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 8;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
        }
        else if (randomRoof == 4) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 7;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedGray;
        }
        for (int i2 = i; i2 <= i + 15; ++i2) {
            for (int k2 = k; k2 <= k + 15; ++k2) {
                for (int j2 = j; (j2 == j || !LOTRMod.isOpaque(world, i2, j2, k2)) && j2 >= 0; --j2) {
                    this.func_150516_a(world, i2, j2, k2, LOTRMod.brick3, 2);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                for (int j2 = j + 1; j2 <= j + 4; ++j2) {
                    this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                }
                if (i2 < i + 2 || i2 > i + 13 || k2 < k + 2 || k2 > k + 13) {
                    this.func_150516_a(world, i2, j + 5, k2, LOTRMod.brick3, 2);
                }
                else {
                    this.func_150516_a(world, i2, j + 5, k2, this.plankBlock, this.plankMeta);
                }
                for (int j2 = j + 6; j2 <= j + 9; ++j2) {
                    this.func_150516_a(world, i2, j2, k2, Blocks.air, 0);
                }
            }
        }
        for (int i2 = i + 1; i2 <= i + 14; ++i2) {
            this.func_150516_a(world, i2, j + 6, k, LOTRMod.wall2, 11);
            this.func_150516_a(world, i2, j + 6, k + 15, LOTRMod.wall2, 11);
        }
        for (int k4 = k + 1; k4 <= k + 14; ++k4) {
            this.func_150516_a(world, i, j + 6, k4, LOTRMod.brick3, 2);
            this.func_150516_a(world, i, j + 7, k4, LOTRMod.wall2, 11);
            this.func_150516_a(world, i + 15, j + 6, k4, LOTRMod.wall2, 11);
        }
        for (int j4 = j; j4 <= j + 5; j4 += 5) {
            for (int k2 = k; k2 <= k + 15; k2 += 15) {
                for (int i4 = i; i4 <= i + 15; i4 += 3) {
                    for (int j5 = j4 + 1; j5 <= j4 + 4; ++j5) {
                        this.func_150516_a(world, i4, j5, k2, LOTRMod.pillar, 10);
                    }
                    this.func_150516_a(world, i4, j4 + 5, k2, LOTRMod.brick3, 2);
                }
                for (int i4 = i + 1; i4 <= i + 13; i4 += 3) {
                    this.func_150516_a(world, i4, j4 + 5, k2, LOTRMod.stairsHighElvenBrick, 5);
                    this.func_150516_a(world, i4 + 1, j4 + 5, k2, LOTRMod.stairsHighElvenBrick, 4);
                }
            }
            for (int i3 = i; i3 <= i + 15; i3 += 15) {
                for (int k3 = k + 3; k3 <= k + 12; k3 += 3) {
                    for (int j5 = j4 + 1; j5 <= j4 + 4; ++j5) {
                        this.func_150516_a(world, i3, j5, k3, LOTRMod.pillar, 10);
                    }
                    this.func_150516_a(world, i3, j4 + 5, k3, LOTRMod.brick3, 2);
                }
                for (int k3 = k + 1; k3 <= k + 13; k3 += 3) {
                    this.func_150516_a(world, i3, j4 + 5, k3, LOTRMod.stairsHighElvenBrick, 7);
                    this.func_150516_a(world, i3, j4 + 5, k3 + 1, LOTRMod.stairsHighElvenBrick, 6);
                }
            }
            for (int i3 = i; i3 <= i + 15; i3 += 3) {
                this.func_150516_a(world, i3, j4 + 4, k + 1, LOTRMod.highElvenTorch, 3);
                this.func_150516_a(world, i3, j4 + 4, k + 14, LOTRMod.highElvenTorch, 4);
            }
            for (int k2 = k; k2 <= k + 15; k2 += 3) {
                this.func_150516_a(world, i + 1, j4 + 4, k2, LOTRMod.highElvenTorch, 1);
                this.func_150516_a(world, i + 14, j4 + 4, k2, LOTRMod.highElvenTorch, 2);
            }
        }
        int roofWidth;
        int roofX;
        int roofY;
        int roofZ;
        for (roofWidth = 18, roofX = i - 1, roofY = j + 11, roofZ = k - 1; roofWidth > 2; roofWidth -= 2, ++roofX, ++roofY, ++roofZ) {
            for (int i5 = roofX; i5 < roofX + roofWidth; ++i5) {
                this.func_150516_a(world, i5, roofY, roofZ, this.roofStairBlock, 2);
                this.func_150516_a(world, i5, roofY, roofZ + roofWidth - 1, this.roofStairBlock, 3);
            }
            for (int k5 = roofZ; k5 < roofZ + roofWidth; ++k5) {
                this.func_150516_a(world, roofX, roofY, k5, this.roofStairBlock, 0);
                this.func_150516_a(world, roofX + roofWidth - 1, roofY, k5, this.roofStairBlock, 1);
            }
            for (int i5 = roofX + 1; i5 < roofX + roofWidth - 2; ++i5) {
                for (int k6 = roofZ + 1; k6 < roofZ + roofWidth - 2; ++k6) {
                    this.func_150516_a(world, i5, roofY, k6, Blocks.air, 0);
                }
            }
            for (int i5 = roofX + 1; i5 < roofX + roofWidth - 1; ++i5) {
                if (roofWidth > 16) {
                    this.func_150516_a(world, i5, roofY, roofZ + 1, this.roofBlock, this.roofMeta);
                    this.func_150516_a(world, i5, roofY, roofZ + roofWidth - 2, this.roofBlock, this.roofMeta);
                }
                else {
                    this.func_150516_a(world, i5, roofY, roofZ + 1, this.roofStairBlock, 7);
                    this.func_150516_a(world, i5, roofY, roofZ + roofWidth - 2, this.roofStairBlock, 6);
                }
            }
            for (int k5 = roofZ + 1; k5 < roofZ + roofWidth - 1; ++k5) {
                if (roofWidth > 16) {
                    this.func_150516_a(world, roofX + 1, roofY, k5, this.roofBlock, this.roofMeta);
                    this.func_150516_a(world, roofX + roofWidth - 2, roofY, k5, this.roofBlock, this.roofMeta);
                }
                else {
                    this.func_150516_a(world, roofX + 1, roofY, k5, this.roofStairBlock, 5);
                    this.func_150516_a(world, roofX + roofWidth - 2, roofY, k5, this.roofStairBlock, 4);
                }
            }
        }
        for (int i5 = roofX; i5 < roofX + roofWidth; ++i5) {
            for (int k6 = roofZ; k6 < roofZ + roofWidth; ++k6) {
                this.func_150516_a(world, i5, roofY - 1, k6, LOTRMod.glass, 0);
            }
        }
        this.func_150516_a(world, i + 2, j + 6, k + 9, LOTRMod.highElvenBed, 1);
        this.func_150516_a(world, i + 1, j + 6, k + 9, LOTRMod.highElvenBed, 9);
        this.func_150516_a(world, i + 1, j + 6, k + 10, this.plankBlock, this.plankMeta);
        this.placeFlowerPot(world, i + 1, j + 7, k + 10, this.getRandomPlant(random));
        this.func_150516_a(world, i + 1, j + 6, k + 8, this.plankBlock, this.plankMeta);
        this.placeFlowerPot(world, i + 1, j + 7, k + 8, this.getRandomPlant(random));
        this.func_150516_a(world, i + 1, j + 6, k + 6, Blocks.bookshelf, 0);
        this.func_150516_a(world, i + 1, j + 6, k + 5, this.plankBlock, this.plankMeta);
        this.func_150516_a(world, i + 1, j + 6, k + 4, this.plankBlock, this.plankMeta);
        this.func_150516_a(world, i + 1, j + 6, k + 3, Blocks.bookshelf, 0);
        this.func_150516_a(world, i + 3, j + 6, k + 4, this.stairBlock, 0);
        this.placeMug(world, random, i + 1, j + 7, k + 4, 1, LOTRFoods.ELF_DRINK);
        this.func_150516_a(world, i + 11, j + 6, k + 10, this.plankBlock, this.plankMeta);
        this.func_150516_a(world, i + 11, j + 6, k + 11, this.plankBlock, this.plankMeta);
        for (int k5 = k + 10; k5 <= k + 12; ++k5) {
            this.func_150516_a(world, i + 13, j + 6, k5, this.stairBlock, 0);
        }
        for (int i5 = i + 11; i5 <= i + 13; ++i5) {
            this.func_150516_a(world, i5, j + 6, k + 13, this.stairBlock, 2);
        }
        for (int k5 = k + 5; k5 <= k + 9; ++k5) {
            for (int i6 = i + 7; i6 <= i + 10; ++i6) {
                this.func_150516_a(world, i6, j + 5, k5, Blocks.air, 0);
            }
        }
        for (int k5 = k + 5; k5 <= k + 6; ++k5) {
            for (int j6 = j + 1; j6 <= j + 4; ++j6) {
                this.func_150516_a(world, i + 7, j6, k5, LOTRMod.brick3, 2);
            }
            this.func_150516_a(world, i + 7, j + 5, k5, LOTRMod.stairsHighElvenBrick, 1);
            for (int i6 = i + 8; i6 <= i + 10; ++i6) {
                for (int j7 = j + 1; j7 <= j + 3; ++j7) {
                    this.func_150516_a(world, i6, j7, k5, LOTRMod.brick3, 2);
                }
            }
            this.func_150516_a(world, i + 8, j + 4, k5, LOTRMod.stairsHighElvenBrick, 1);
        }
        for (int i5 = i + 9; i5 <= i + 10; ++i5) {
            for (int j6 = j + 1; j6 <= j + 2; ++j6) {
                this.func_150516_a(world, i5, j6, k + 7, LOTRMod.brick3, 2);
            }
            this.func_150516_a(world, i5, j + 3, k + 7, LOTRMod.stairsHighElvenBrick, 3);
            this.func_150516_a(world, i5, j + 1, k + 8, LOTRMod.brick3, 2);
            this.func_150516_a(world, i5, j + 2, k + 8, LOTRMod.stairsHighElvenBrick, 3);
            this.func_150516_a(world, i5, j + 1, k + 9, LOTRMod.stairsHighElvenBrick, 3);
        }
        this.func_150516_a(world, i + 11, j + 1, k + 5, LOTRMod.pillar, 10);
        this.func_150516_a(world, i + 11, j + 1, k + 6, this.tableBlock, 0);
        this.func_150516_a(world, i + 11, j + 1, k + 7, LOTRMod.pillar, 10);
        this.placeFlowerPot(world, i + 11, j + 2, k + 5, this.getRandomPlant(random));
        this.placeFlowerPot(world, i + 11, j + 2, k + 7, this.getRandomPlant(random));
        this.func_150516_a(world, i + 11, j + 3, k + 6, LOTRMod.highElvenTorch, 1);
        this.func_150516_a(world, i + 6, j + 3, k + 6, LOTRMod.highElvenTorch, 2);
        this.func_150516_a(world, i + 8, j + 3, k + 7, LOTRMod.highElvenTorch, 3);
        this.func_150516_a(world, i + 10, j + 1, k + 4, LOTRMod.pillar, 10);
        this.placeBarrel(world, random, i + 10, j + 2, k + 4, 2, LOTRFoods.ELF_DRINK);
        this.func_150516_a(world, i + 7, j + 1, k + 4, LOTRMod.pillar, 10);
        this.placeBarrel(world, random, i + 7, j + 2, k + 4, 2, LOTRFoods.ELF_DRINK);
        this.func_150516_a(world, i + 8, j + 1, k + 4, (Block)Blocks.chest, 0);
        this.func_150516_a(world, i + 9, j + 1, k + 4, (Block)Blocks.chest, 0);
        LOTRChestContents.fillChest(world, random, i + 8, j + 1, k + 4, this.chestContents);
        LOTRChestContents.fillChest(world, random, i + 9, j + 1, k + 4, this.chestContents);
        this.func_150516_a(world, i + 8, j + 2, k + 5, Blocks.furnace, 2);
        this.func_150516_a(world, i + 9, j + 2, k + 5, Blocks.furnace, 2);
        this.setBlockMetadata(world, i + 8, j + 2, k + 5, 2);
        this.setBlockMetadata(world, i + 9, j + 2, k + 5, 2);
        this.func_150516_a(world, i + 7, j + 1, k + 7, this.plankBlock, this.plankMeta);
        this.func_150516_a(world, i + 8, j + 1, k + 7, this.plankBlock, this.plankMeta);
        this.func_150516_a(world, i + 8, j + 1, k + 8, this.plankBlock, this.plankMeta);
        this.placePlateWithCertainty(world, random, i + 7, j + 2, k + 7, this.plateBlock, LOTRFoods.ELF);
        this.placePlateWithCertainty(world, random, i + 8, j + 2, k + 7, this.plateBlock, LOTRFoods.ELF);
        this.placePlateWithCertainty(world, random, i + 8, j + 2, k + 8, this.plateBlock, LOTRFoods.ELF);
        for (int k5 = k + 6; k5 <= k + 12; ++k5) {
            for (int i6 = i + 2; i6 <= i + 4; ++i6) {
                this.func_150516_a(world, i6, j + 1, k5, this.slabBlock, this.slabMeta | 0x8);
            }
        }
        for (int k5 = k + 6; k5 <= k + 12; k5 += 3) {
            this.func_150516_a(world, i + 2, j + 1, k5, this.plankBlock, this.plankMeta);
            this.func_150516_a(world, i + 4, j + 1, k5, this.plankBlock, this.plankMeta);
            this.func_150516_a(world, i + 1, j + 1, k5, this.stairBlock, 1);
            this.func_150516_a(world, i + 5, j + 1, k5, this.stairBlock, 0);
        }
        this.func_150516_a(world, i + 3, j + 1, k + 13, this.stairBlock, 2);
        this.func_150516_a(world, i + 3, j + 1, k + 5, this.stairBlock, 3);
        for (int k5 = k + 6; k5 <= k + 12; k5 += 2) {
            this.placePlateWithCertainty(world, random, i + 2, j + 2, k5, this.plateBlock, LOTRFoods.ELF);
            this.placePlateWithCertainty(world, random, i + 4, j + 2, k5, this.plateBlock, LOTRFoods.ELF);
        }
        for (int k5 = k + 7; k5 <= k + 11; k5 += 2) {
            final int l = random.nextInt(3);
            if (l == 0) {
                this.func_150516_a(world, i + 3, j + 2, k5, LOTRMod.appleCrumble, 0);
            }
            else if (l == 1) {
                this.func_150516_a(world, i + 3, j + 2, k5, LOTRMod.cherryPie, 0);
            }
            else if (l == 2) {
                this.func_150516_a(world, i + 3, j + 2, k5, LOTRMod.berryPie, 0);
            }
            this.placeMug(world, random, i + 2, j + 2, k5, 3, LOTRFoods.ELF_DRINK);
            this.placeMug(world, random, i + 4, j + 2, k5, 1, LOTRFoods.ELF_DRINK);
        }
        this.placeMug(world, random, i + 3, j + 2, k + 6, 0, LOTRFoods.ELF_DRINK);
        this.placeMug(world, random, i + 3, j + 2, k + 12, 2, LOTRFoods.ELF_DRINK);
        this.placeFlowerPot(world, i + 3, j + 2, k + 8, this.getRandomPlant(random));
        this.placeFlowerPot(world, i + 3, j + 2, k + 10, this.getRandomPlant(random));
        for (int j8 = j + 3; j8 <= j + 8; j8 += 5) {
            for (int i6 = i + 3; i6 <= i + 12; i6 += 3) {
                this.placeWallBanner(world, i6, j8, k, 0, this.bannerType);
                this.placeWallBanner(world, i6, j8, k + 15, 2, this.bannerType);
            }
            for (int k6 = k + 3; k6 <= k + 12; k6 += 3) {
                this.placeWallBanner(world, i, j8, k6, 3, this.bannerType);
                this.placeWallBanner(world, i + 15, j8, k6, 1, this.bannerType);
            }
        }
        int elves;
        for (elves = 2 + random.nextInt(4), int l = 0; l < elves; ++l) {
            final LOTREntityElf elf = this.createElf(world);
            elf.setLocationAndAngles((double)(i + 6), (double)(j + 6), (double)(k + 6), 0.0f, 0.0f);
            elf.spawnRidingHorse = false;
            elf.onSpawnWithEgg(null);
            elf.isNPCPersistent = true;
            world.spawnEntityInWorld((Entity)elf);
            elf.setHomeArea(i + 7, j + 3, k + 7, 24);
        }
        return true;
    }
    
    private ItemStack getRandomPlant(final Random random) {
        final int l = random.nextInt(5);
        switch (l) {
            case 0: {
                return new ItemStack(Blocks.sapling, 1, 0);
            }
            case 1: {
                return new ItemStack(Blocks.sapling, 1, 2);
            }
            case 2: {
                return new ItemStack(Blocks.sapling, 1, 2);
            }
            case 3: {
                return new ItemStack((Block)Blocks.red_flower);
            }
            case 4: {
                return new ItemStack((Block)Blocks.yellow_flower);
            }
            default: {
                return new ItemStack(Blocks.sapling, 1, 0);
            }
        }
    }
}
