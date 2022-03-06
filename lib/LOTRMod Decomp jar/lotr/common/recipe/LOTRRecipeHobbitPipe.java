// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import lotr.common.item.LOTRItemHobbitPipe;
import net.minecraft.block.BlockColored;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemDye;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;

public class LOTRRecipeHobbitPipe implements IRecipe
{
    public boolean matches(final InventoryCrafting inv, final World world) {
        ItemStack hobbitPipe = null;
        ItemStack dye = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() == LOTRMod.hobbitPipe) {
                    if (hobbitPipe != null) {
                        return false;
                    }
                    hobbitPipe = itemstack;
                }
                else if (itemstack.getItem() == LOTRMod.mithrilNugget) {
                    dye = itemstack;
                }
                else {
                    if (LOTRItemDye.isItemDye(itemstack) == -1) {
                        return false;
                    }
                    dye = itemstack;
                }
            }
        }
        return hobbitPipe != null && dye != null;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        ItemStack hobbitPipe = null;
        ItemStack dye = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() == LOTRMod.hobbitPipe) {
                    if (hobbitPipe != null) {
                        return null;
                    }
                    hobbitPipe = itemstack;
                }
                else if (itemstack.getItem() == LOTRMod.mithrilNugget) {
                    dye = itemstack;
                }
                else {
                    if (LOTRItemDye.isItemDye(itemstack) == -1) {
                        return null;
                    }
                    dye = itemstack;
                }
            }
        }
        if (hobbitPipe != null && dye != null) {
            final int itemDamage = hobbitPipe.getItemDamage();
            final int smokeType = (dye.getItem() == LOTRMod.mithrilNugget) ? 16 : BlockColored.func_150031_c(LOTRItemDye.isItemDye(dye));
            final ItemStack result = new ItemStack(LOTRMod.hobbitPipe);
            result.setItemDamage(itemDamage);
            LOTRItemHobbitPipe.setSmokeColor(result, smokeType);
            return result;
        }
        return null;
    }
    
    public int getRecipeSize() {
        return 2;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
