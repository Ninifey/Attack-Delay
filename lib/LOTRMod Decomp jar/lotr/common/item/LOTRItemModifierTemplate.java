// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.LOTRMod;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import lotr.common.enchant.LOTREnchantmentHelper;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.enchant.LOTREnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemModifierTemplate extends Item
{
    public LOTRItemModifierTemplate() {
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMaterials);
    }
    
    public static LOTREnchantment getModifier(final ItemStack itemstack) {
        final NBTTagCompound nbt = itemstack.getTagCompound();
        if (nbt != null) {
            final String s = nbt.getString("ScrollModifier");
            return LOTREnchantment.getEnchantmentByName(s);
        }
        return null;
    }
    
    public static void setModifier(final ItemStack itemstack, final LOTREnchantment ench) {
        final String s = ench.enchantName;
        itemstack.setTagInfo("ScrollModifier", (NBTBase)new NBTTagString(s));
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        String s = super.getItemStackDisplayName(itemstack);
        final LOTREnchantment mod = getModifier(itemstack);
        if (mod != null) {
            s = String.format(s, mod.getDisplayName());
        }
        return s;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        super.addInformation(itemstack, entityplayer, list, flag);
        final LOTREnchantment mod = getModifier(itemstack);
        if (mod != null) {
            final String desc = mod.getNamedFormattedDescription(itemstack);
            list.add(desc);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if (ench.hasTemplateItem()) {
                final ItemStack itemstack = new ItemStack((Item)this);
                setModifier(itemstack, ench);
                list.add(itemstack);
            }
        }
    }
    
    public static ItemStack getRandomCommonTemplate(final Random random) {
        final List<LOTREnchantmentHelper.WeightedRandomEnchant> applicable = new ArrayList<LOTREnchantmentHelper.WeightedRandomEnchant>();
        for (final LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if (ench.hasTemplateItem()) {
                final int weight = LOTREnchantmentHelper.getSkilfulWeight(ench);
                final LOTREnchantmentHelper.WeightedRandomEnchant wre = new LOTREnchantmentHelper.WeightedRandomEnchant(ench, weight);
                applicable.add(wre);
            }
        }
        final LOTREnchantmentHelper.WeightedRandomEnchant chosenWre = (LOTREnchantmentHelper.WeightedRandomEnchant)WeightedRandom.getRandomItem(random, (Collection)applicable);
        final LOTREnchantment chosenEnch = chosenWre.theEnchant;
        final ItemStack itemstack = new ItemStack(LOTRMod.modTemplate);
        setModifier(itemstack, chosenEnch);
        return itemstack;
    }
}
