// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;

public class LOTRItemHaradRobes extends LOTRItemArmor
{
    public static int ROBES_WHITE;
    
    public LOTRItemHaradRobes(final int slot) {
        this(LOTRMaterial.HARAD_ROBES, slot);
    }
    
    public LOTRItemHaradRobes(final LOTRMaterial material, final int slot) {
        super(material, slot);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        return getRobesColor(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        if (areRobesDyed(itemstack)) {
            list.add(StatCollector.translateToLocal("item.lotr.haradRobes.dyed"));
        }
    }
    
    public static int getRobesColor(final ItemStack itemstack) {
        final int dye = getSavedDyeColor(itemstack);
        if (dye != -1) {
            return dye;
        }
        return LOTRItemHaradRobes.ROBES_WHITE;
    }
    
    private static int getSavedDyeColor(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("RobesColor")) {
            return itemstack.getTagCompound().getInteger("RobesColor");
        }
        return -1;
    }
    
    public static boolean areRobesDyed(final ItemStack itemstack) {
        return getSavedDyeColor(itemstack) != -1;
    }
    
    public static void setRobesColor(final ItemStack itemstack, final int i) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setInteger("RobesColor", i);
    }
    
    public static void removeRobeDye(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null) {
            itemstack.getTagCompound().removeTag("RobesColor");
        }
    }
    
    static {
        LOTRItemHaradRobes.ROBES_WHITE = 16777215;
    }
}
