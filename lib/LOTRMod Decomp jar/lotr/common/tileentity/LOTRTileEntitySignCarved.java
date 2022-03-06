// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import lotr.common.block.LOTRBlockSignCarved;
import net.minecraft.util.IIcon;

public class LOTRTileEntitySignCarved extends LOTRTileEntitySign
{
    @Override
    public int getNumLines() {
        return 8;
    }
    
    public IIcon getOnBlockIcon() {
        final World world = this.getWorldObj();
        final Block block = this.getBlockType();
        if (block instanceof LOTRBlockSignCarved) {
            final LOTRBlockSignCarved signBlock = (LOTRBlockSignCarved)block;
            final int meta = this.getBlockMetadata();
            final int i = super.xCoord;
            final int j = super.yCoord;
            final int k = super.zCoord;
            final int onSide = meta;
            return signBlock.getOnBlockIcon((IBlockAccess)world, i, j, k, onSide);
        }
        return Blocks.stone.getIcon(0, 0);
    }
}
