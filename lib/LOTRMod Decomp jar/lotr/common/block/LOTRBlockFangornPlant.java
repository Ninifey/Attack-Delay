// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockFangornPlant extends LOTRBlockFlower
{
    @SideOnly(Side.CLIENT)
    private IIcon[] plantIcons;
    private String[] plantNames;
    
    public LOTRBlockFangornPlant() {
        this.plantNames = new String[] { "green", "brown", "gold", "yellow", "red", "silver" };
        this.setFlowerBounds(0.2f, 0.0f, 0.2f, 0.8f, 0.8f, 0.8f);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.plantNames.length) {
            j = 0;
        }
        return this.plantIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.plantIcons = new IIcon[this.plantNames.length];
        for (int i = 0; i < this.plantNames.length; ++i) {
            this.plantIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.plantNames[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < this.plantNames.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
}
