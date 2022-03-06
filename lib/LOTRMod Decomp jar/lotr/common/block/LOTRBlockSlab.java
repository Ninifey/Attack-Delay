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

public class LOTRBlockSlab extends LOTRBlockSlabBase
{
    public LOTRBlockSlab(final boolean flag) {
        super(flag, Material.rock, 8);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        if (j == 0) {
            return LOTRMod.smoothStone.getIcon(i, 0);
        }
        if (j == 1) {
            return LOTRMod.brick.getIcon(i, 0);
        }
        if (j == 2) {
            return LOTRMod.smoothStone.getIcon(i, 1);
        }
        if (j == 3) {
            return LOTRMod.brick.getIcon(i, 1);
        }
        if (j == 4) {
            return LOTRMod.brick.getIcon(i, 2);
        }
        if (j == 5) {
            return LOTRMod.brick.getIcon(i, 3);
        }
        if (j == 6) {
            return LOTRMod.brick.getIcon(i, 4);
        }
        if (j == 7) {
            return LOTRMod.brick.getIcon(i, 6);
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
