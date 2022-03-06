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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockSlabDirt extends LOTRBlockSlabBase
{
    public LOTRBlockSlabDirt(final boolean flag) {
        super(flag, Material.ground, 5);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGravel);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        if (j == 0) {
            return Blocks.dirt.getIcon(i, 0);
        }
        if (j == 1) {
            return LOTRMod.dirtPath.getIcon(i, 0);
        }
        if (j == 2) {
            return LOTRMod.mud.getIcon(i, 0);
        }
        if (j == 3) {
            return LOTRMod.mordorDirt.getIcon(i, 0);
        }
        if (j == 4) {
            return LOTRMod.dirtPath.getIcon(i, 1);
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
