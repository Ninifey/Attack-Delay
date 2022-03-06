// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemRing extends Item
{
    @SideOnly(Side.CLIENT)
    public static IIcon saxIcon;
    
    public LOTRItemRing() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        super.registerIcons(iconregister);
        LOTRItemRing.saxIcon = iconregister.registerIcon("lotr:sax");
    }
}
