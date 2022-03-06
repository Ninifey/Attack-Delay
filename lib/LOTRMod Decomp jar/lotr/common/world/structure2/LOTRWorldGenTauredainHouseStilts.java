// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityTauredainShaman;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseStilts extends LOTRWorldGenTauredainHouse
{
    public LOTRWorldGenTauredainHouseStilts(final boolean flag) {
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
        super.bedBlock = LOTRMod.strawBed;
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                for (int j2 = 3; j2 <= 7; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.placeStilt(world, -3, -3, false);
        this.placeStilt(world, 3, -3, false);
        this.placeStilt(world, -3, 3, false);
        this.placeStilt(world, 3, 3, false);
        this.placeStilt(world, 0, -4, true);
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 3 && k3 == 3) {
                    for (int j3 = 3; j3 <= 7; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.woodBlock, super.woodMeta);
                    }
                }
                if (i3 == 3 && k3 <= 2) {
                    this.setBlockAndMetadata(world, i2, 3, k2, super.woodBlock, super.woodMeta | 0x8);
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i2, 5, k2, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i2, 6, k2, super.woodBlock, super.woodMeta | 0x8);
                }
                if (k3 == 3 && i3 <= 2) {
                    this.setBlockAndMetadata(world, i2, 3, k2, super.woodBlock, super.woodMeta | 0x4);
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i2, 5, k2, super.plankBlock, super.plankMeta);
                    this.setBlockAndMetadata(world, i2, 6, k2, super.woodBlock, super.woodMeta | 0x4);
                }
                if (i3 <= 2 && k3 <= 2) {
                    this.setBlockAndMetadata(world, i2, 3, k2, super.plankBlock, super.plankMeta);
                    if (random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i2, 4, k2, LOTRMod.thatchFloor, 0);
                    }
                }
                if (i2 == -3 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plankStairBlock, 1);
                    this.setBlockAndMetadata(world, i2, 5, k2, super.fenceBlock, super.fenceMeta);
                }
                if (i2 == 3 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plankStairBlock, 0);
                    this.setBlockAndMetadata(world, i2, 5, k2, super.fenceBlock, super.fenceMeta);
                }
                if (k2 == 3 && i3 == 1) {
                    this.setBlockAndMetadata(world, i2, 4, k2, super.plankStairBlock, 3);
                    this.setBlockAndMetadata(world, i2, 5, k2, super.fenceBlock, super.fenceMeta);
                }
            }
        }
        for (final int j4 : new int[] { 3, 6 }) {
            this.setBlockAndMetadata(world, -4, j4, -3, super.woodBlock, super.woodMeta | 0x4);
            this.setBlockAndMetadata(world, 4, j4, -3, super.woodBlock, super.woodMeta | 0x4);
            this.setBlockAndMetadata(world, -4, j4, 3, super.woodBlock, super.woodMeta | 0x4);
            this.setBlockAndMetadata(world, 4, j4, 3, super.woodBlock, super.woodMeta | 0x4);
            this.setBlockAndMetadata(world, -3, j4, -4, super.woodBlock, super.woodMeta | 0x8);
            this.setBlockAndMetadata(world, -3, j4, 4, super.woodBlock, super.woodMeta | 0x8);
            this.setBlockAndMetadata(world, 3, j4, -4, super.woodBlock, super.woodMeta | 0x8);
            this.setBlockAndMetadata(world, 3, j4, 4, super.woodBlock, super.woodMeta | 0x8);
        }
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if ((i3 == 4 && k3 == 2) || (k3 == 4 && i3 == 2)) {
                    this.setBlockAndMetadata(world, i2, 6, k2, super.fenceBlock, super.fenceMeta);
                }
                if ((i3 == 4 && k3 <= 3) || (k3 == 4 && i3 <= 3)) {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if ((i3 == 3 && k3 <= 2) || (k3 == 3 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.thatchBlock, super.thatchMeta);
                }
                if ((i3 == 2 && k3 <= 2) || (k3 == 2 && i3 <= 2)) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                    if (i3 == 2 && k3 == 2) {
                        this.setBlockAndMetadata(world, i2, 7, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 7, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
                if ((i3 == 1 && k3 <= 1) || (k3 == 1 && i3 <= 1)) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
                }
                if (i3 == 0 && k3 == 0) {
                    this.setBlockAndMetadata(world, i2, 9, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 3, -4, super.plankBlock, super.plankMeta);
            if (i2 != 0) {
                this.setBlockAndMetadata(world, i2, 3, -5, super.plankSlabBlock, super.plankSlabMeta | 0x8);
                this.setBlockAndMetadata(world, i2, 4, -5, super.fenceBlock, super.fenceMeta);
            }
        }
        this.setBlockAndMetadata(world, -2, 4, -4, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 2, 4, -4, super.fenceBlock, super.fenceMeta);
        this.setAir(world, 0, 4, -3);
        this.setAir(world, 0, 5, -3);
        this.setBlockAndMetadata(world, 0, 6, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 6, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 6, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 6, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 4, -2, super.woodBlock, super.woodMeta);
        this.placeChest(world, random, -2, 5, -2, 3, LOTRChestContents.TAUREDAIN_HOUSE);
        this.setBlockAndMetadata(world, 2, 4, -2, super.woodBlock, super.woodMeta);
        this.placeBarrel(world, random, 2, 5, -2, 3, LOTRFoods.TAUREDAIN_DRINK);
        for (final int i4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i4, 4, 1, super.bedBlock, 0);
            this.setBlockAndMetadata(world, i4, 4, 2, super.bedBlock, 8);
        }
        this.setBlockAndMetadata(world, -1, 4, 2, LOTRMod.tauredainTable, 0);
        this.setBlockAndMetadata(world, 0, 4, 2, super.woodBlock, super.woodMeta);
        this.placeMug(world, random, 0, 5, 2, 0, LOTRFoods.TAUREDAIN_DRINK);
        this.setBlockAndMetadata(world, 1, 4, 2, Blocks.crafting_table, 0);
        this.placeTauredainTorch(world, -1, 5, -5);
        this.placeTauredainTorch(world, 1, 5, -5);
        final LOTREntityTauredainShaman shaman = new LOTREntityTauredainShaman(world);
        this.spawnNPCAndSetHome(shaman, world, 0, 4, 0, 2);
        return true;
    }
    
    private void placeStilt(final World world, final int i, final int k, final boolean ladder) {
        for (int j = 3; (j == 3 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
            this.setBlockAndMetadata(world, i, j, k, super.woodBlock, super.woodMeta);
            this.setGrassToDirt(world, i, j - 1, k);
            if (ladder) {
                this.setBlockAndMetadata(world, i, j, k - 1, Blocks.ladder, 2);
            }
        }
    }
}
