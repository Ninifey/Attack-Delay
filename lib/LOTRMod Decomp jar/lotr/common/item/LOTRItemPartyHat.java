// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;

public class LOTRItemPartyHat extends LOTRItemArmor
{
    public static final int HAT_WHITE = 16777215;
    
    public LOTRItemPartyHat() {
        super(LOTRMaterial.COSMETIC, 0);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        return getHatColor(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        if (isHatDyed(itemstack) && getHatColor(itemstack) != 16777215) {
            list.add(StatCollector.translateToLocal("item.lotr.hat.dyed"));
        }
    }
    
    public static int getHatColor(final ItemStack itemstack) {
        final int dye = getSavedDyeColor(itemstack);
        if (dye != -1) {
            return dye;
        }
        return 16777215;
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
    
    public static ItemStack createDyedHat(final int i) {
        return setHatColor(new ItemStack(LOTRMod.partyHat), i);
    }
    
    public static void removeHatDye(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null) {
            itemstack.getTagCompound().removeTag("HatColor");
        }
    }
    
    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        return "lotr:armor/partyhat.png";
    }
}
