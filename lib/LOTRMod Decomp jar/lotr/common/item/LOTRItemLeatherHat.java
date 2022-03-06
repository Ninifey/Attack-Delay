// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.Item;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRItemLeatherHat extends LOTRItemArmor
{
    public static final int HAT_LEATHER = 6834742;
    public static final int HAT_SHIRRIFF_CHIEF = 2301981;
    public static final int FEATHER_WHITE = 16777215;
    public static final int FEATHER_SHIRRIFF_CHIEF = 3381529;
    public static final int HAT_BLACK = 0;
    @SideOnly(Side.CLIENT)
    private IIcon featherIcon;
    
    public LOTRItemLeatherHat() {
        super(LOTRMaterial.COSMETIC, 0);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        super.registerIcons(iconregister);
        this.featherIcon = iconregister.registerIcon(this.getIconString() + "_feather");
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final ItemStack itemstack, final int pass) {
        if (pass == 1 && hasFeather(itemstack)) {
            return this.featherIcon;
        }
        return ((Item)this).itemIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        if (pass == 1 && hasFeather(itemstack)) {
            return getFeatherColor(itemstack);
        }
        return getHatColor(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        if (isHatDyed(itemstack)) {
            list.add(StatCollector.translateToLocal("item.lotr.hat.dyed"));
        }
        if (hasFeather(itemstack)) {
            list.add(StatCollector.translateToLocal("item.lotr.hat.feathered"));
        }
    }
    
    public static int getHatColor(final ItemStack itemstack) {
        final int dye = getSavedDyeColor(itemstack);
        if (dye != -1) {
            return dye;
        }
        return 6834742;
    }
    
    private static int getSavedDyeColor(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("HatColor")) {
            return itemstack.getTagCompound().getInteger("HatColor");
        }
        return -1;
    }
    
    public static boolean isHatDyed(final ItemStack itemstack) {
        return getSavedDyeColor(itemstack) != -1;
    }
    
    public static ItemStack setHatColor(final ItemStack itemstack, final int i) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setInteger("HatColor", i);
        return itemstack;
    }
    
    public static int getFeatherColor(final ItemStack itemstack) {
        int i = -1;
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("FeatherColor")) {
            i = itemstack.getTagCompound().getInteger("FeatherColor");
        }
        return i;
    }
    
    public static boolean hasFeather(final ItemStack itemstack) {
        return getFeatherColor(itemstack) != -1;
    }
    
    public static boolean isFeatherDyed(final ItemStack itemstack) {
        return hasFeather(itemstack) && getFeatherColor(itemstack) != 16777215;
    }
    
    public static ItemStack setFeatherColor(final ItemStack itemstack, final int i) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setInteger("FeatherColor", i);
        return itemstack;
    }
    
    public static void removeHatAndFeatherDye(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null) {
            itemstack.getTagCompound().removeTag("HatColor");
        }
        if (hasFeather(itemstack) && isFeatherDyed(itemstack)) {
            setFeatherColor(itemstack, 16777215);
        }
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        return "lotr:armor/hat.png";
    }
}
