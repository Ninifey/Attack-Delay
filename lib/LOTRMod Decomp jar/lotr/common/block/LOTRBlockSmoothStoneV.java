// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.creativetab.CreativeTabs;

public class LOTRBlockSmoothStoneV extends LOTRBlockSmoothStoneBase
{
    public LOTRBlockSmoothStoneV() {
        this.setBrickNames("stone");
        this.setcreativeTab(CreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, final int j) {
        if (j == 0) {
            return Blocks.stone_slab.getIcon(i, 0);
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
