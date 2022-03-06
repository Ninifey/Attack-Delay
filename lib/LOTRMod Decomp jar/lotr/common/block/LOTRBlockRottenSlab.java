// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;
import net.minecraft.block.material.Material;

public class LOTRBlockRottenSlab extends LOTRBlockSlabBase
{
    public LOTRBlockRottenSlab(final boolean flag) {
        super(flag, Material.wood, 1);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        j += 8;
        return LOTRMod.planksRotten.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}