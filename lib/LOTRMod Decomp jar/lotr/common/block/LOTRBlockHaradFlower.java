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

public class LOTRBlockHaradFlower extends LOTRBlockFlower
{
    @SideOnly(Side.CLIENT)
    private IIcon[] flowerIcons;
    private static String[] flowerNames;
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= LOTRBlockHaradFlower.flowerNames.length) {
            j = 0;
        }
        return this.flowerIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.flowerIcons = new IIcon[LOTRBlockHaradFlower.flowerNames.length];
        for (int i = 0; i < LOTRBlockHaradFlower.flowerNames.length; ++i) {
            this.flowerIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + LOTRBlockHaradFlower.flowerNames[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < LOTRBlockHaradFlower.flowerNames.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    static {
        LOTRBlockHaradFlower.flowerNames = new String[] { "red", "yellow", "daisy", "pink" };
    }
}
