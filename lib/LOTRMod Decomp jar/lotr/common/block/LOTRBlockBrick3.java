// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockBrick3 extends LOTRBlockBrickBase
{
    public LOTRBlockBrick3() {
        this.setBrickNames("blueCarved", "redCarved", "highElven", "highElvenMossy", "highElvenCracked", "woodElven", "woodElvenMossy", "woodElvenCracked", "nearHaradCarved", "dolAmroth", "moredain", "nearHaradCracked", "dwarvenGlowing", "nearHaradRed", "nearHaradRedCracked", "nearHaradRedCarved");
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        if (world.getBlockMetadata(i, j, k) == 12) {
            return Blocks.glowstone.getLightValue();
        }
        return 0;
    }
}
