// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockLilyPad;

public class LOTRBlockFangornRiverweed extends BlockLilyPad
{
    public LOTRBlockFangornRiverweed() {
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int meta) {
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        return 16777215;
    }
}
