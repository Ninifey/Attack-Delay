// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTownGarden extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorTownGarden(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = 0; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = 0; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                for (int j3 = 1; j3 <= 3; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                if (i3 <= 2 && k2 >= 1 && k2 <= 2) {
                    this.setBlockAndMetadata(world, i2, 0, k2, (Block)Blocks.grass, 0);
                }
                if (i3 == 3 && (k2 == 0 || k2 == 3)) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.rockWallBlock, super.rockWallMeta);
                    this.setBlockAndMetadata(world, i2, 2, k2, Blocks.torch, 5);
                }
            }
        }
        for (int k3 = 1; k3 <= 2; ++k3) {
            final ItemStack flower = this.getRandomFlower(world, random);
            for (int i4 = -2; i4 <= 2; ++i4) {
                this.setBlockAndMetadata(world, i4, 1, k3, Block.getBlockFromItem(flower.getItem()), flower.getItemDamage());
            }
        }
        return true;
    }
}
