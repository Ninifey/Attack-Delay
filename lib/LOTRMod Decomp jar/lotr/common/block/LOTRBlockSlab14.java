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

public class LOTRBlockSlab14 extends LOTRBlockSlabBase
{
    public LOTRBlockSlab14(final boolean flag) {
        super(flag, Material.rock, 1);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        if (j == 0) {
            return LOTRMod.pillar2.getIcon(i, 14);
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
