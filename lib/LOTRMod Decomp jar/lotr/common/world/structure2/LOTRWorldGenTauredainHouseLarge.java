// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityTauredain;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import lotr.common.item.LOTRItemBanner;
import com.google.common.math.IntMath;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseLarge extends LOTRWorldGenTauredainHouse
{
    public LOTRWorldGenTauredainHouseLarge(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 5;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for (int i2 = -6; i2 <= 5; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                this.layFoundation(world, i2, k2);
                for (int j2 = 1; j2 <= 11; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        for (int i2 = -6; i2 <= 5; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                if (i2 >= -5 && i2 <= 4 && k2 >= -3 && k2 <= 3) {
                    this.setBlockAndMetadata(world, i2, 0, k2, super.floorBlock, super.floorMeta);
                }
                if (((i2 == -5 || i2 == 4) && k2 >= -3 && k2 <= 3) || ((k2 == -3 || k2 == 3) && i2 >= -5 && i2 <= 4)) {
                    this.setBlockAndMetadata(world, i2, 3, k2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                    this.setBlockAndMetadata(world, i2, 4, k2, LOTRMod.mudGrass, 0);
                    this.setBlockAndMetadata(world, i2, 5, k2, (Block)Blocks.tallgrass, 1);
                }
                if (((i2 == -4 || i2 == 3) && k2 >= -2 && k2 <= 2) || ((k2 == -2 || k2 == 2) && i2 >= -4 && i2 <= 3)) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i2, 5, k2, super.brickBlock, super.brickMeta);
                    this.setBlockAndMetadata(world, i2, 8, k2, super.plankBlock, super.plankMeta);
                }
            }
        }
        for (final int i3 : new int[] { -6, 5 }) {
            for (final int k3 : new int[] { -4, 4 }) {
                for (int j3 = 1; j3 <= 5; ++j3) {
                    this.setBlockAndMetadata(world, i3, j3, k3, super.woodBlock, super.woodMeta);
                }
            }
        }
        for (final int k4 : new int[] { -4, 4 }) {
            for (int i4 = -5; i4 <= 4; ++i4) {
                this.setBlockAndMetadata(world, i4, 5, k4, super.brickSlabBlock, super.brickSlabMeta);
                if (IntMath.mod(i4, 3) == 1) {
                    this.setBlockAndMetadata(world, i4, 4, k4, super.woodBlock, super.woodMeta | 0x8);
                }
                else {
                    this.setBlockAndMetadata(world, i4, 4, k4, super.plankBlock, super.plankMeta);
                }
            }
            for (int j4 = 1; j4 <= 3; ++j4) {
                this.setBlockAndMetadata(world, -5, j4, k4, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, 4, j4, k4, super.brickBlock, super.brickMeta);
            }
            for (final int i5 : new int[] { -4, 2 }) {
                this.setBlockAndMetadata(world, i5, 1, k4, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i5 + 1, 1, k4, super.brickBlock, super.brickMeta);
                if (random.nextInt(3) == 0) {
                    this.placeTauredainFlowerPot(world, i5, 2, k4, random);
                }
                if (random.nextInt(3) == 0) {
                    this.placeTauredainFlowerPot(world, i5 + 1, 2, k4, random);
                }
                this.setBlockAndMetadata(world, i5, 3, k4, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i5 + 1, 3, k4, super.brickStairBlock, 4);
            }
        }
        this.setBlockAndMetadata(world, -1, 3, -4, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 3, -4, super.brickBlock, super.brickMeta);
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, -1, j5, 4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 0, j5, 4, super.brickBlock, super.brickMeta);
        }
        for (final int i3 : new int[] { -6, 5 }) {
            for (int k5 = -3; k5 <= 3; ++k5) {
                this.setBlockAndMetadata(world, i3, 5, k5, super.brickSlabBlock, super.brickSlabMeta);
                if (k5 % 2 == 0) {
                    this.setBlockAndMetadata(world, i3, 4, k5, super.woodBlock, super.woodMeta | 0x4);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 4, k5, super.plankBlock, super.plankMeta);
                }
            }
            for (int j4 = 1; j4 <= 3; ++j4) {
                this.setBlockAndMetadata(world, i3, j4, -3, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i3, j4, 3, super.brickBlock, super.brickMeta);
            }
            for (final int k3 : new int[] { -2, 1 }) {
                this.setBlockAndMetadata(world, i3, 1, k3, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i3, 1, k3 + 1, super.brickBlock, super.brickMeta);
                if (random.nextInt(3) == 0) {
                    this.placeTauredainFlowerPot(world, i3, 2, k3, random);
                }
                if (random.nextInt(3) == 0) {
                    this.placeTauredainFlowerPot(world, i3, 2, k3 + 1, random);
                }
                this.setBlockAndMetadata(world, i3, 3, k3, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i3, 3, k3 + 1, super.brickStairBlock, 7);
            }
        }
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, -2, j5, -4, super.woodBlock, super.woodMeta);
            this.setBlockAndMetadata(world, 1, j5, -4, super.woodBlock, super.woodMeta);
            this.setBlockAndMetadata(world, -2, j5, 4, super.woodBlock, super.woodMeta);
            this.setBlockAndMetadata(world, 1, j5, 4, super.woodBlock, super.woodMeta);
            this.setBlockAndMetadata(world, -6, j5, 0, super.woodBlock, super.woodMeta);
            this.setBlockAndMetadata(world, 5, j5, 0, super.woodBlock, super.woodMeta);
        }
        for (int i2 = -3; i2 <= 2; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                this.setBlockAndMetadata(world, i2, 4, k2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
            }
        }
        for (int j5 = 5; j5 <= 9; ++j5) {
            this.setBlockAndMetadata(world, -4, j5, -2, super.woodBlock, super.woodMeta);
            this.setBlockAndMetadata(world, 3, j5, -2, super.woodBlock, super.woodMeta);
            this.setBlockAndMetadata(world, -4, j5, 2, super.woodBlock, super.woodMeta);
            this.setBlockAndMetadata(world, 3, j5, 2, super.woodBlock, super.woodMeta);
        }
        for (int k6 = -3; k6 <= 3; ++k6) {
            this.setBlockAndMetadata(world, -2, 8, k6, super.woodBlock, super.woodMeta | 0x8);
            this.setBlockAndMetadata(world, 1, 8, k6, super.woodBlock, super.woodMeta | 0x8);
        }
        this.setBlockAndMetadata(world, -5, 8, 0, super.woodBlock, super.woodMeta | 0x4);
        this.setBlockAndMetadata(world, -4, 8, 0, super.woodBlock, super.woodMeta | 0x4);
        this.setBlockAndMetadata(world, 3, 8, 0, super.woodBlock, super.woodMeta | 0x4);
        this.setBlockAndMetadata(world, 4, 8, 0, super.woodBlock, super.woodMeta | 0x4);
        for (final int k4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, -3, 6, k4, super.brickStairBlock, 0);
            this.setBlockAndMetadata(world, -3, 7, k4, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k4, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 7, k4, super.brickStairBlock, 5);
            for (int i4 = -2; i4 <= 1; ++i4) {
                if (random.nextInt(3) == 0) {
                    this.placeTauredainFlowerPot(world, i4, 6, k4, random);
                }
            }
        }
        for (final int i3 : new int[] { -4, 3 }) {
            this.setBlockAndMetadata(world, i3, 6, -1, super.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 7, -1, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 6, 1, super.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 7, 1, super.brickStairBlock, 6);
            if (random.nextInt(3) == 0) {
                this.placeTauredainFlowerPot(world, i3, 6, 0, random);
            }
        }
        for (int i2 = -3; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 9, -2, LOTRMod.stairsTauredainBrickGold, 2);
            this.setBlockAndMetadata(world, i2, 10, -1, LOTRMod.stairsTauredainBrickGold, 2);
            this.setBlockAndMetadata(world, i2, 9, 2, LOTRMod.stairsTauredainBrickGold, 3);
            this.setBlockAndMetadata(world, i2, 10, 1, LOTRMod.stairsTauredainBrickGold, 3);
        }
        for (int k6 = -1; k6 <= 1; ++k6) {
            this.setBlockAndMetadata(world, -4, 9, k6, LOTRMod.stairsTauredainBrickGold, 1);
            this.setBlockAndMetadata(world, -3, 10, k6, LOTRMod.stairsTauredainBrickGold, 1);
            this.setBlockAndMetadata(world, 3, 9, k6, LOTRMod.stairsTauredainBrickGold, 0);
            this.setBlockAndMetadata(world, 2, 10, k6, LOTRMod.stairsTauredainBrickGold, 0);
        }
        for (int i2 = -2; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 10, 0, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i2, 11, 0, super.brickSlabBlock, super.brickSlabMeta);
        }
        for (int k6 = 0; k6 <= 1; ++k6) {
            this.setBlockAndMetadata(world, -2, 5, k6, super.bedBlock, 3);
            this.setBlockAndMetadata(world, -3, 5, k6, super.bedBlock, 11);
        }
        this.setBlockAndMetadata(world, -3, 5, -1, super.woodBlock, super.woodMeta);
        this.placeTauredainFlowerPot(world, -3, 6, -1, random);
        this.placeSkull(world, random, -2, 9, 0);
        this.placeSkull(world, random, 1, 9, 0);
        for (final int k4 : new int[] { -1, 1 }) {
            this.setBlockAndMetadata(world, -3, 8, k4, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 2, 8, k4, Blocks.torch, 1);
        }
        for (int j5 = 1; j5 <= 5; ++j5) {
            if (j5 <= 3) {
                this.setBlockAndMetadata(world, 3, j5, 1, super.woodBlock, super.woodMeta);
            }
            this.setBlockAndMetadata(world, 2, j5, 1, Blocks.ladder, 5);
        }
        this.placeWallBanner(world, 3, 3, 1, LOTRItemBanner.BannerType.TAUREDAIN, 2);
        for (int i2 = -1; i2 <= 0; ++i2) {
            for (int k2 = 0; k2 <= 2; ++k2) {
                this.setBlockAndMetadata(world, i2, 3, k2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                this.setBlockAndMetadata(world, i2, 4, k2, super.plankBlock, super.plankMeta);
            }
            for (int k2 = 0; k2 <= 3; ++k2) {
                this.setBlockAndMetadata(world, i2, 1, k2, super.plankBlock, super.plankMeta);
                if ((i2 + k2) % 2 == 0) {
                    this.placePlateWithCertainty(world, random, i2, 2, k2, super.plateBlock, LOTRFoods.TAUREDAIN);
                }
                else {
                    this.placeMug(world, random, i2, 2, k2, random.nextInt(4), LOTRFoods.TAUREDAIN_DRINK);
                }
            }
        }
        for (int i2 = -5; i2 <= -4; ++i2) {
            this.setBlockAndMetadata(world, i2, 1, 3, Blocks.furnace, 2);
        }
        this.setBlockAndMetadata(world, -3, 1, 3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -2, 1, 3, LOTRMod.tauredainTable, 0);
        for (int i2 = 1; i2 <= 2; ++i2) {
            this.placeChest(world, random, i2, 1, 3, 2, LOTRChestContents.TAUREDAIN_HOUSE);
        }
        for (int i2 = 3; i2 <= 4; ++i2) {
            this.setBlockAndMetadata(world, i2, 1, 3, super.woodBlock, super.woodMeta);
        }
        this.setBlockAndMetadata(world, -5, 1, -3, super.woodBlock, super.woodMeta);
        this.setBlockAndMetadata(world, -5, 1, -2, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -5, 1, -1, super.woodBlock, super.woodMeta);
        this.placeBarrel(world, random, -5, 2, -3, 4, LOTRFoods.TAUREDAIN_DRINK);
        this.placeMug(world, random, -5, 2, -2, 3, LOTRFoods.TAUREDAIN_DRINK);
        this.placeMug(world, random, -5, 2, -1, 3, LOTRFoods.TAUREDAIN_DRINK);
        this.setBlockAndMetadata(world, 4, 1, -3, super.woodBlock, super.woodMeta);
        this.setBlockAndMetadata(world, 4, 1, -2, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 4, 1, -1, super.woodBlock, super.woodMeta);
        for (int k6 = -3; k6 <= -1; ++k6) {
            this.placePlate(world, random, 4, 2, k6, super.plateBlock, LOTRFoods.TAUREDAIN);
        }
        this.setBlockAndMetadata(world, -1, 0, -4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 0, -4, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -5, 2, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 2, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -5, 2, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 2, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 4, 2, 3, Blocks.torch, 4);
        this.placeTauredainTorch(world, -6, 6, -4);
        this.placeTauredainTorch(world, 5, 6, -4);
        this.placeTauredainTorch(world, -6, 6, 4);
        this.placeTauredainTorch(world, 5, 6, 4);
        this.setBlockAndMetadata(world, -1, 1, -4, super.doorBlock, 1);
        this.setBlockAndMetadata(world, -1, 2, -4, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 1, -4, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, super.doorBlock, 9);
        final LOTREntityTauredain male = new LOTREntityTauredain(world);
        male.familyInfo.setMale(true);
        male.setupNPCName();
        final LOTREntityTauredain female = new LOTREntityTauredain(world);
        female.familyInfo.setMale(false);
        female.setupNPCName();
        this.spawnNPCAndSetHome(male, world, 0, 1, -1, 16);
        this.spawnNPCAndSetHome(female, world, 0, 1, -1, 16);
        return true;
    }
}
