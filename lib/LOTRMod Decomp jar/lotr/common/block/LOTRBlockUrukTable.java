// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockUrukTable extends LOTRBlockCraftingTable
{
    @SideOnly(Side.CLIENT)
    private IIcon[] tableIcons;
    
    public LOTRBlockUrukTable() {
        super(Material.rock, LOTRFaction.URUK_HAI, 8);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 1) {
            return this.tableIcons[1];
        }
        if (i == 0) {
            return LOTRMod.brick2.getIcon(0, 7);
        }
        return this.tableIcons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.tableIcons = new IIcon[2])[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.tableIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
    }
}
