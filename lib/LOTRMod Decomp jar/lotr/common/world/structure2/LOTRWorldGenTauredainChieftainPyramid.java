// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityTauredainChieftain;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainChieftainPyramid extends LOTRWorldGenTauredainHouse
{
    public LOTRWorldGenTauredainChieftainPyramid(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 11;
    }
    
    @Override
    protected boolean useStoneBrick() {
        return true;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for (int i2 = -10; i2 <= 10; ++i2) {
            for (int k2 = -10; k2 <= 10; ++k2) {
                this.layFoundation(world, i2, k2);
                for (int j2 = 1; j2 <= 14; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        for (int i2 = -10; i2 <= 10; ++i2) {
            for (int k2 = -10; k2 <= 10; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 >= 8 || k3 >= 8) {
                    this.setBlockAndMetadata(world, i2, 0, k2, super.brickBlock, super.brickMeta);
                    if (k2 < 0 && i2 == 0) {
                        this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.brick4, 4);
                    }
                    if (i3 <= 9 && k3 <= 9 && (i3 == 9 || k3 == 9)) {
                        this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.brick4, 4);
                    }
                }
                else {
                    for (int j3 = 1; j3 <= 4; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                    }
                    if (i3 <= 1 && k2 <= 0) {
                        final int step = 4 - (-3 - k2);
                        if (step >= 0 && step <= 4) {
                            for (int j4 = step + 1; j4 <= 4; ++j4) {
                                this.setAir(world, i2, j4, k2);
                            }
                            if (step == 0) {
                                this.setBlockAndMetadata(world, -1, step, k2, super.brickBlock, super.brickMeta);
                                this.setBlockAndMetadata(world, 0, step, k2, LOTRMod.brick4, 4);
                                this.setBlockAndMetadata(world, 1, step, k2, super.brickBlock, super.brickMeta);
                            }
                            else if (step <= 4) {
                                this.setBlockAndMetadata(world, -1, step, k2, super.brickStairBlock, 2);
                                this.setBlockAndMetadata(world, 0, step, k2, LOTRMod.stairsTauredainBrickObsidian, 2);
                                this.setBlockAndMetadata(world, 1, step, k2, super.brickStairBlock, 2);
                            }
                        }
                    }
                    else if ((i3 == 7 && k2 % 2 == 0) || (k3 == 7 && i2 % 2 == 0)) {
                        this.setBlockAndMetadata(world, i2, 1, k2, super.brickWallBlock, super.brickWallMeta);
                        this.placeTauredainTorch(world, i2, 2, k2);
                    }
                }
            }
        }
        for (int k4 = -2; k4 <= 4; ++k4) {
            this.setBlockAndMetadata(world, 0, 4, k4, LOTRMod.brick4, 4);
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            this.setBlockAndMetadata(world, i2, 4, 0, LOTRMod.brick4, 4);
        }
        for (final int i4 : new int[] { -5, 5 }) {
            for (final int k5 : new int[] { -5, 5 }) {
                for (int i5 = i4 - 1; i5 <= i4 + 1; ++i5) {
                    for (int k6 = k5 - 1; k6 <= k5 + 1; ++k6) {
                        final int i6 = Math.abs(i5 - i4);
                        final int k7 = Math.abs(k6 - k5);
                        if (i6 == 1 && k7 == 1) {
                            this.setBlockAndMetadata(world, i5, 5, k6, super.brickSlabBlock, super.brickSlabMeta);
                        }
                        else if (i6 == 1 || k7 == 1) {
                            this.setBlockAndMetadata(world, i5, 5, k6, super.brickBlock, super.brickMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i5, 5, k6, LOTRMod.hearth, 0);
                            this.setBlockAndMetadata(world, i5, 6, k6, (Block)Blocks.fire, 0);
                        }
                    }
                }
            }
        }
        for (final int i4 : new int[] { -3, 3 }) {
            this.setBlockAndMetadata(world, i4, 5, -6, super.brickWallBlock, super.brickWallMeta);
            for (int j3 = 5; j3 <= 7; ++j3) {
                for (int k8 = -5; k8 <= -3; ++k8) {
                    this.setBlockAndMetadata(world, i4, j3, k8, super.brickBlock, super.brickMeta);
                }
                this.setBlockAndMetadata(world, i4, j3, -1, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i4, j3, 1, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i4, j3, 3, super.brickBlock, super.brickMeta);
            }
            this.setBlockAndMetadata(world, i4, 5, 4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i4, 7, 0, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 5, -2, super.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i4, 7, -2, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i4, 5, 2, super.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i4, 7, 2, super.brickStairBlock, 6);
            for (int k9 = -4; k9 <= 4; ++k9) {
                if (k9 == 0 || Math.abs(k9) == 3) {
                    this.setBlockAndMetadata(world, i4, 8, k9, super.brickBlock, super.brickMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i4, 8, k9, super.brickSlabBlock, super.brickSlabMeta);
                }
            }
        }
        for (final int i4 : new int[] { -4, 4 }) {
            for (int j3 = 5; j3 <= 7; ++j3) {
                this.setBlockAndMetadata(world, i4, j3, -4, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i4, j3, -2, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i4, j3, 2, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i4, j3, 4, super.brickBlock, super.brickMeta);
            }
            this.setBlockAndMetadata(world, i4, 5, -3, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i4, 5, 3, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i4, 7, -1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            this.setBlockAndMetadata(world, i4, 7, 1, super.brickSlabBlock, super.brickSlabMeta | 0x8);
            for (int k9 = -4; k9 <= 4; ++k9) {
                if (Math.abs(k9) == 4) {
                    this.setBlockAndMetadata(world, i4, 8, k9, super.brickBlock, super.brickMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i4, 8, k9, super.brickSlabBlock, super.brickSlabMeta);
                }
            }
        }
        for (final int i4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i4, 5, -6, super.brickWallBlock, super.brickWallMeta);
            this.placeTauredainTorch(world, i4, 6, -6);
            this.setBlockAndMetadata(world, i4, 5, -5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, i4, 6, -5, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, i4, 8, -4, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, i4, 8, -3, super.brickSlabBlock, super.brickSlabMeta);
            this.placeArmorStand(world, i4, 5, 2, 0, new ItemStack[] { new ItemStack(LOTRMod.helmetTauredain), new ItemStack(LOTRMod.bodyTauredain), new ItemStack(LOTRMod.legsTauredain), new ItemStack(LOTRMod.bootsTauredain) });
        }
        for (int j5 = 5; j5 <= 7; ++j5) {
            this.setBlockAndMetadata(world, -2, j5, 4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 2, j5, 4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -1, j5, 3, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 1, j5, 3, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 7, 3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -2, 5, 3, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -2, 7, 3, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 5, 3, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 7, 3, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 7, 4, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 7, 4, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 8, 3, super.brickSlabBlock, super.brickSlabMeta);
            this.setBlockAndMetadata(world, i2, 8, 4, super.brickSlabBlock, super.brickSlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 8, 3, super.brickBlock, super.brickMeta);
        for (int k4 = -3; k4 <= 3; ++k4) {
            this.setBlockAndMetadata(world, -7, 5, k4, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, 7, 5, k4, super.brickWallBlock, super.brickWallMeta);
        }
        for (int i2 = -6; i2 <= -5; ++i2) {
            this.setBlockAndMetadata(world, i2, 5, -3, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, i2, 5, 3, super.brickWallBlock, super.brickWallMeta);
        }
        for (int i2 = 5; i2 <= 6; ++i2) {
            this.setBlockAndMetadata(world, i2, 5, -3, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, i2, 5, 3, super.brickWallBlock, super.brickWallMeta);
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            this.setBlockAndMetadata(world, i2, 5, 7, super.brickWallBlock, super.brickWallMeta);
        }
        for (int k4 = 5; k4 <= 6; ++k4) {
            this.setBlockAndMetadata(world, -3, 5, k4, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, 3, 5, k4, super.brickWallBlock, super.brickWallMeta);
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 8; j3 <= 9; ++j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                }
                if (i3 == 2 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 10, k2, super.brickSlabBlock, super.brickSlabMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i2, 10, k2, super.brickBlock, super.brickMeta);
                }
                if (i3 == 1 && k3 == 1) {
                    for (int j3 = 11; j3 <= 12; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickWallBlock, super.brickWallMeta);
                    }
                }
                if (i3 == 0 && k3 == 0) {
                    this.setBlockAndMetadata(world, i2, 10, k2, LOTRMod.hearth, 0);
                    this.setBlockAndMetadata(world, i2, 11, k2, (Block)Blocks.fire, 0);
                }
                if (i3 <= 1 && k3 <= 1) {
                    this.setBlockAndMetadata(world, i2, 13, k2, LOTRMod.brick4, 3);
                    if (k2 == -1) {
                        this.setBlockAndMetadata(world, i2, 14, k2, LOTRMod.stairsTauredainBrickGold, 2);
                    }
                    else if (k2 == 1) {
                        this.setBlockAndMetadata(world, i2, 14, k2, LOTRMod.stairsTauredainBrickGold, 3);
                    }
                    else if (i2 == -1) {
                        this.setBlockAndMetadata(world, i2, 14, k2, LOTRMod.stairsTauredainBrickGold, 1);
                    }
                    else if (i2 == 1) {
                        this.setBlockAndMetadata(world, i2, 14, k2, LOTRMod.stairsTauredainBrickGold, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 14, k2, LOTRMod.brick4, 3);
                        this.setBlockAndMetadata(world, i2, 15, k2, LOTRMod.wall4, 3);
                        this.placeTauredainTorch(world, i2, 16, k2);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -1, 8, -3, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 8, -3, super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 9, -3, super.brickSlabBlock, super.brickSlabMeta);
        this.setBlockAndMetadata(world, 1, 8, -3, super.brickBlock, super.brickMeta);
        this.placeWallBanner(world, -1, 8, -3, LOTRItemBanner.BannerType.TAUREDAIN, 2);
        this.placeWallBanner(world, 1, 8, -3, LOTRItemBanner.BannerType.TAUREDAIN, 2);
        this.placeWallBanner(world, -3, 7, -3, LOTRItemBanner.BannerType.TAUREDAIN, 1);
        this.placeWallBanner(world, 3, 7, -3, LOTRItemBanner.BannerType.TAUREDAIN, 3);
        this.setBlockAndMetadata(world, -2, 6, -1, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -2, 6, 1, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 6, -1, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 2, 6, 1, Blocks.torch, 1);
        this.spawnItemFrame(world, -1, 6, 3, 2, new ItemStack(LOTRMod.tauredainDart));
        this.spawnItemFrame(world, 1, 6, 3, 2, new ItemStack(LOTRMod.daggerTauredain));
        final LOTREntityTauredainChieftain chieftain = new LOTREntityTauredainChieftain(world);
        this.spawnNPCAndSetHome(chieftain, world, 0, 5, 0, 8);
        return true;
    }
}
