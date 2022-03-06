// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockFallenLeaves;
import net.minecraft.block.BlockLeavesBase;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenFallenLeaves extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        Block fallenLeaf = null;
        int fallenMeta = 0;
        for (int l = 0; l < 40; ++l) {
            final int i2 = i - random.nextInt(6) + random.nextInt(6);
            final int j2 = j + random.nextInt(12);
            final int k2 = k - random.nextInt(6) + random.nextInt(6);
            final Block block = world.getBlock(i2, j2, k2);
            if (block instanceof BlockLeavesBase) {
                final int meta = world.getBlockMetadata(i2, j2, k2);
                final Object[] obj = LOTRBlockFallenLeaves.fallenBlockMetaFromLeafBlockMeta(block, meta);
                if (obj != null) {
                    fallenLeaf = (Block)obj[0];
                    fallenMeta = (int)obj[1];
                    break;
                }
            }
        }
        if (fallenLeaf == null) {
            return false;
        }
        for (int l = 0; l < 64; ++l) {
            final int i2 = i - random.nextInt(5) + random.nextInt(5);
            final int j2 = j - random.nextInt(3) + random.nextInt(3);
            final int k2 = k - random.nextInt(5) + random.nextInt(5);
            final Block below = world.getBlock(i2, j2 - 1, k2);
            final Block block2 = world.getBlock(i2, j2, k2);
            if (fallenLeaf.canBlockStay(world, i2, j2, k2) && !block2.getMaterial().isLiquid() && block2.isReplaceable((IBlockAccess)world, i2, j2, k2)) {
                world.setBlock(i2, j2, k2, fallenLeaf, fallenMeta, 2);
            }
        }
        return true;
    }
}
