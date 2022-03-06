// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainWatchtower extends LOTRWorldGenTauredainHouse
{
    public LOTRWorldGenTauredainWatchtower(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 2;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                for (int j2 = 7; j2 <= 13; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.placeWoodBase(world, -1, -1, false);
        this.placeWoodBase(world, 1, -1, true);
        this.placeWoodBase(world, -1, 1, false);
        this.placeWoodBase(world, 1, 1, false);
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 1 && k3 == 1) {
                    this.setBlockAndMetadata(world, i2, 8, k2, super.woodBlock, super.woodMeta);
                    this.setBlockAndMetadata(world, i2, 9, k2, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i2, 10, k2, super.fenceBlock, super.fenceMeta);
                    this.setBlockAndMetadata(world, i2, 11, k2, super.woodBlock, super.woodMeta);
                    this.setBlockAndMetadata(world, i2, 12, k2, super.woodBlock, super.woodMeta);
                }
                if (i3 == 1 && k3 % 2 == 0) {
                    if (i2 != 1 || k2 != -2) {
                        this.setBlockAndMetadata(world, i2, 7, k2, super.woodBlock, super.woodMeta | 0x8);
                    }
                    this.setBlockAndMetadata(world, i2, 11, k2, super.woodBlock, super.woodMeta | 0x8);
                }
                if (k3 == 1 && i3 % 2 == 0) {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.woodBlock, super.woodMeta | 0x4);
                    this.setBlockAndMetadata(world, i2, 11, k2, super.woodBlock, super.woodMeta | 0x4);
                }
                if ((i3 == 1 && k3 == 2) || (k3 == 1 && i3 == 2)) {
                    this.setBlockAndMetadata(world, i2, 12, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if ((i3 == 0 && k3 == 2) || (k3 == 0 && i3 == 2)) {
                    this.setBlockAndMetadata(world, i2, 12, k2, super.thatchBlock, super.thatchMeta);
                }
                if ((i3 == 0 && k3 == 1) || (k3 == 0 && i3 == 1)) {
                    this.setBlockAndMetadata(world, i2, 12, k2, super.thatchBlock, super.thatchMeta);
                    this.setBlockAndMetadata(world, i2, 13, k2, super.thatchSlabBlock, super.thatchSlabMeta);
                }
                if (i3 == 0 && k3 == 0) {
                    this.setBlockAndMetadata(world, i2, 12, k2, super.thatchBlock, super.thatchMeta);
                    this.setBlockAndMetadata(world, i2, 13, k2, super.thatchBlock, super.thatchMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 7, 0, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 7, -2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 11, 0, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 8, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 1, 8, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 0, 8, 1, super.fenceBlock, super.fenceMeta);
        return true;
    }
    
    private void placeWoodBase(final World world, final int i, final int k, final boolean ladder) {
        for (int j = 7; (j == 7 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
            if (ladder) {
                this.setBlockAndMetadata(world, i, j, k, super.woodBlock, super.woodMeta);
                if (!this.isOpaque(world, i, j - 1, k)) {
                    this.setBlockAndMetadata(world, i, j, k - 1, Blocks.ladder, 2);
                }
            }
            else if (j >= 6) {
                this.setBlockAndMetadata(world, i, j, k, super.woodBlock, super.woodMeta);
            }
            else if (this.isOpaque(world, i, j - 1, k)) {
                this.setBlockAndMetadata(world, i, j, k, super.woodBlock, super.woodMeta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, super.fenceBlock, super.fenceMeta);
            }
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
}
