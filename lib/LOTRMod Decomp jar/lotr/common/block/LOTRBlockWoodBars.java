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

public class LOTRBlockWoodBars extends LOTRBlockPane
{
    public LOTRBlockWoodBars() {
        super("", "", Material.wood, true);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
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
