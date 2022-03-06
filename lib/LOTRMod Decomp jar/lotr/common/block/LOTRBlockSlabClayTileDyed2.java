// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;

public class LOTRBlockSlabClayTileDyed2 extends LOTRBlockSlabBase
{
    public LOTRBlockSlabClayTileDyed2(final boolean flag) {
        super(flag, Material.rock, 8);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(1.25f);
        this.setResistance(7.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        j += 8;
        return LOTRMod.clayTileDyed.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
