// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoSlab extends LOTRBlockUtumnoSlabBase
{
    public LOTRBlockUtumnoSlab(final boolean flag) {
        super(flag, 6);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        if (j == 0) {
            return LOTRMod.utumnoBrick.getIcon(i, 0);
        }
        if (j == 1) {
            return LOTRMod.utumnoBrick.getIcon(i, 2);
        }
        if (j == 2) {
            return LOTRMod.utumnoBrick.getIcon(i, 4);
        }
        if (j == 3) {
            return LOTRMod.utumnoPillar.getIcon(i, 0);
        }
        if (j == 4) {
            return LOTRMod.utumnoPillar.getIcon(i, 1);
        }
        if (j == 5) {
            return LOTRMod.utumnoPillar.getIcon(i, 2);
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
