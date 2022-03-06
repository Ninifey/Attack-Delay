// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTownFlowers extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronTownFlowers(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        final ItemStack flower = this.getRandomFlower(world, random);
        final Block flowerBlock = Block.getBlockFromItem(flower.getItem());
        final int flowerMeta = flower.getItemDamage();
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
                for (int j3 = 1; j3 <= 4; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, super.stoneBlock, super.stoneMeta);
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                if ((k2 == 0 || k2 == 3) && i3 % 2 == 1) {
                    this.setBlockAndMetadata(world, i2, 1, k2, super.brickSlabBlock, super.brickSlabMeta);
                }
                if (k2 >= 1 && k2 <= 2 && i3 <= 2) {
                    this.setBlockAndMetadata(world, i2, 0, k2, (Block)Blocks.grass, 0);
                    this.setBlockAndMetadata(world, i2, 1, k2, flowerBlock, flowerMeta);
                }
            }
        }
        return true;
    }
}
