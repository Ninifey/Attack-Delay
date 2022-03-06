// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockButton;

public class LOTRBlockButton extends BlockButton
{
    private String iconPath;
    
    public LOTRBlockButton(final boolean flag, final String s) {
        super(flag);
        this.iconPath = s;
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        ((Block)this).blockIcon = iconregister.registerIcon(this.iconPath);
    }
}
