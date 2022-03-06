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

public class LOTRBlockRhunFlower extends LOTRBlockFlower
{
    @SideOnly(Side.CLIENT)
    private IIcon[] flowerIcons;
    private static String[] flowerNames;
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= LOTRBlockRhunFlower.flowerNames.length) {
            j = 0;
        }
        return this.flowerIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.flowerIcons = new IIcon[LOTRBlockRhunFlower.flowerNames.length];
        for (int i = 0; i < LOTRBlockRhunFlower.flowerNames.length; ++i) {
            this.flowerIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + LOTRBlockRhunFlower.flowerNames[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < LOTRBlockRhunFlower.flowerNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    static {
        LOTRBlockRhunFlower.flowerNames = new String[] { "chrysBlue", "chrysOrange", "chrysPink", "chrysYellow", "chrysWhite" };
    }
}
