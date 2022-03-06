// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemAncientItemParts extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    private String[] partNames;
    
    public LOTRItemAncientItemParts() {
        this.partNames = new String[] { "swordTip", "swordBlade", "swordHilt", "armorPlate" };
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.icons.length) {
            i = 0;
        }
        return this.icons[i];
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.icons = new IIcon[this.partNames.length];
        for (int i = 0; i < this.partNames.length; ++i) {
            this.icons[i] = iconregister.registerIcon(this.getIconString() + "_" + this.partNames[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i <= 3; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
