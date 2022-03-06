// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.material.Material;

public class LOTRBlockSlabV extends LOTRBlockSlabBase
{
    public LOTRBlockSlabV(final boolean flag) {
        super(flag, Material.rock, 6);
        this.setcreativeTab(CreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        if (j == 0) {
            return Blocks.stonebrick.getIcon(i, 1);
        }
        if (j == 1) {
            return Blocks.stonebrick.getIcon(i, 2);
        }
        if (j == 2) {
            return LOTRMod.redBrick.getIcon(i, 0);
        }
        if (j == 3) {
            return LOTRMod.redBrick.getIcon(i, 1);
        }
        if (j == 4) {
            return Blocks.mossy_cobblestone.getIcon(i, 0);
        }
        if (j == 5) {
            return Blocks.stone.getIcon(i, 0);
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
