// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.BlockLeavesBase;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenBushes extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        Block leafBlock = null;
        int leafMeta = 0;
        for (int l = 0; l < 40; ++l) {
            final int i2 = i - random.nextInt(6) + random.nextInt(6);
            final int j2 = j + random.nextInt(12);
            final int k2 = k - random.nextInt(6) + random.nextInt(6);
            final Block block = world.getBlock(i2, j2, k2);
            if (block instanceof BlockLeavesBase) {
                final int meta = world.getBlockMetadata(i2, j2, k2);
                leafBlock = block;
                leafMeta = meta;
                break;
            }
        }
        if (leafBlock == null) {
            return false;
        }
        final Block below = world.getBlock(i, j - 1, k);
        if (below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            int size = 0;
            if (random.nextInt(3) == 0) {
                ++size;
            }
            for (int i3 = -size; i3 <= size; ++i3) {
                for (int k2 = -size; k2 <= size; ++k2) {
                    final int i4 = i + i3;
                    final int k3 = k + k2;
                    if (size == 0 || Math.abs(i3) != size || Math.abs(k2) != size || random.nextInt(3) == 0) {
                        final Block block2 = world.getBlock(i4, j, k3);
                        if (!block2.getMaterial().isLiquid() && block2.isReplaceable((IBlockAccess)world, i4, j, k3)) {
                            world.setBlock(i4, j, k3, leafBlock, leafMeta | 0x4, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
