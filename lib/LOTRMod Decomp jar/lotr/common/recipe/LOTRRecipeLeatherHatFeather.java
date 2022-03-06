// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import lotr.common.item.LOTRItemFeatherDyed;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;

public class LOTRRecipeLeatherHatFeather implements IRecipe
{
    public boolean matches(final InventoryCrafting inv, final World world) {
        ItemStack hat = null;
        ItemStack feather = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() == LOTRMod.leatherHat && !LOTRItemLeatherHat.hasFeather(itemstack)) {
                    if (hat != null) {
                        return false;
                    }
                    hat = itemstack;
                }
                else {
                    if (!LOTRMod.isOreNameEqual(itemstack, "feather") && itemstack.getItem() != LOTRMod.featherDyed) {
                        return false;
                    }
                    if (feather != null) {
                        return false;
                    }
                    feather = itemstack;
                }
            }
        }
        return hat != null && feather != null;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        ItemStack hat = null;
        ItemStack feather = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() == LOTRMod.leatherHat && !LOTRItemLeatherHat.hasFeather(itemstack)) {
                    if (hat != null) {
                        return null;
                    }
                    hat = itemstack.copy();
                }
                else {
                    if (!LOTRMod.isOreNameEqual(itemstack, "feather") && itemstack.getItem() != LOTRMod.featherDyed) {
                        return null;
                    }
                    if (feather != null) {
                        return null;
                    }
                    feather = itemstack.copy();
                }
            }
        }
        if (hat == null || feather == null) {
            return null;
        }
        int featherColor = 16777215;
        if (feather.getItem() == LOTRMod.featherDyed) {
            featherColor = LOTRItemFeatherDyed.getFeatherColor(feather);
        }
        LOTRItemLeatherHat.setFeatherColor(hat, featherColor);
        return hat;
    }
    
    public int getRecipeSize() {
        return 2;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
