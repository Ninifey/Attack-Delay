// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoSlab2 extends LOTRBlockUtumnoSlabBase
{
    public LOTRBlockUtumnoSlab2(final boolean flag) {
        super(flag, 3);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        if (j == 0) {
            return LOTRMod.utumnoBrick.getIcon(i, 6);
        }
        if (j == 1) {
            return LOTRMod.utumnoBrick.getIcon(i, 7);
        }
        if (j == 2) {
            return LOTRMod.utumnoBrick.getIcon(i, 8);
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
