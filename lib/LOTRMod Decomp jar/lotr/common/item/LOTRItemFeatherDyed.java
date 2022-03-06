// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class LOTRItemFeatherDyed extends Item
{
    public LOTRItemFeatherDyed() {
        this.setMaxStackSize(1);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int i) {
        return Items.feather.getIconFromDamage(i);
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        return getFeatherColor(itemstack);
    }
    
    public static int getFeatherColor(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("FeatherColor")) {
            return itemstack.getTagCompound().getInteger("FeatherColor");
        }
        return 16777215;
    }
    
    public static boolean isFeatherDyed(final ItemStack itemstack) {
        return getFeatherColor(itemstack) != 16777215;
    }
    
    public static void setFeatherColor(final ItemStack itemstack, final int i) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setInteger("FeatherColor", i);
    }
    
    public static void removeFeatherDye(final ItemStack itemstack) {
        setFeatherColor(itemstack, 16777215);
    }
}
