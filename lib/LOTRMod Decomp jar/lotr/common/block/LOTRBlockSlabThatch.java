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
import net.minecraft.block.material.Material;

public class LOTRBlockSlabThatch extends LOTRBlockSlabBase
{
    public LOTRBlockSlabThatch(final boolean flag) {
        super(flag, Material.grass, 2);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGrass);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        return LOTRMod.thatch.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
}
