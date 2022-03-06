// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public interface LOTRConnectedBlock
{
    String getConnectedName(final int p0);
    
    boolean areBlocksConnected(final IBlockAccess p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    public static class Checks
    {
        public static boolean matchBlockAndMeta(final Block block, final IBlockAccess world, final int i, final int j, final int k, final int i1, final int j1, final int k1) {
            final int meta = world.getBlockMetadata(i, j, k);
            final Block otherBlock = world.getBlock(i1, j1, k1);
            final int otherMeta = world.getBlockMetadata(i1, j1, k1);
            return otherBlock == block && meta == otherMeta;
        }
    }
}
