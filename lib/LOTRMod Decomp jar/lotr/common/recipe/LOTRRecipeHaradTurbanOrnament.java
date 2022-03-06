// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemHaradTurban;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;

public class LOTRRecipeHaradTurbanOrnament implements IRecipe
{
    public boolean matches(final InventoryCrafting inv, final World world) {
        ItemStack turban = null;
        ItemStack gold = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() == LOTRMod.helmetHaradRobes && !LOTRItemHaradTurban.hasOrnament(itemstack)) {
                    if (turban != null) {
                        return false;
                    }
                    turban = itemstack;
                }
                else {
                    if (!LOTRMod.isOreNameEqual(itemstack, "nuggetGold")) {
                        return false;
                    }
                    if (gold != null) {
                        return false;
                    }
                    gold = itemstack;
                }
            }
        }
        return turban != null && gold != null;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        ItemStack turban = null;
        ItemStack gold = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() == LOTRMod.helmetHaradRobes && !LOTRItemHaradTurban.hasOrnament(itemstack)) {
                    if (turban != null) {
                        return null;
                    }
                    turban = itemstack.copy();
                }
                else {
                    if (!LOTRMod.isOreNameEqual(itemstack, "nuggetGold")) {
                        return null;
                    }
                    if (gold != null) {
                        return null;
                    }
                    gold = itemstack.copy();
                }
            }
        }
        if (turban == null || gold == null) {
            return null;
        }
        LOTRItemHaradTurban.setHasOrnament(turban, true);
        return turban;
    }
    
    public int getRecipeSize() {
        return 2;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
