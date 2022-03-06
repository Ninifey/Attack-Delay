// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockBars extends LOTRBlockPane
{
    public LOTRBlockBars() {
        super("", "", Material.iron, true);
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeMetal);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        ((Block)this).blockIcon = iconregister.registerIcon(this.getTextureName());
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon func_150097_e() {
        return ((Block)this).blockIcon;
    }
}
