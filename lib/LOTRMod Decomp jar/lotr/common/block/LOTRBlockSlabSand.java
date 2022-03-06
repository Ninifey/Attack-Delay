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

public class LOTRBlockSlabSand extends LOTRBlockSlabFalling
{
    public LOTRBlockSlabSand(final boolean flag) {
        super(flag, Material.sand, 3);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeSand);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        if (j == 0) {
            return Blocks.sand.getIcon(i, 0);
        }
        if (j == 1) {
            return Blocks.sand.getIcon(i, 1);
        }
        if (j == 2) {
            return LOTRMod.whiteSand.getIcon(i, 0);
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
