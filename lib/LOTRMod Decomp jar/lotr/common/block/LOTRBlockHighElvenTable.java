// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockHighElvenTable extends LOTRBlockCraftingTable
{
    @SideOnly(Side.CLIENT)
    private IIcon[] tableIcons;
    
    public LOTRBlockHighElvenTable() {
        super(Material.wood, LOTRFaction.HIGH_ELF, 26);
        this.setStepSound(Block.soundTypeWood);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 1) {
            return this.tableIcons[1];
        }
        if (i == 0) {
            return Blocks.planks.getIcon(2, 0);
        }
        return this.tableIcons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.tableIcons = new IIcon[2])[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.tableIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
    }
}
