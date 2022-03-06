// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import net.minecraft.nbt.NBTTagCompound;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;

public class LOTRRecipesBanners implements IRecipe
{
    public boolean matches(final InventoryCrafting inv, final World world) {
        return this.getCraftingResult(inv) != null;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        ItemStack baseBanner = null;
        int emptyBanners = 0;
        for (int pass = 0; pass < 2; ++pass) {
            for (int i = 0; i < inv.getSizeInventory(); ++i) {
                final ItemStack itemstack = inv.getStackInSlot(i);
                if (itemstack != null) {
                    if (!(itemstack.getItem() instanceof LOTRItemBanner)) {
                        return null;
                    }
                    final NBTTagCompound data = LOTRItemBanner.getProtectionData(itemstack);
                    if (pass == 0 && data != null) {
                        if (baseBanner != null) {
                            return null;
                        }
                        baseBanner = itemstack;
                    }
                    if (pass == 1) {
                        if (baseBanner == null || itemstack.getItemDamage() != baseBanner.getItemDamage()) {
                            return null;
                        }
                        if (data == null) {
                            ++emptyBanners;
                        }
                    }
                }
            }
        }
        if (baseBanner == null) {
            return null;
        }
        if (emptyBanners > 0) {
            final ItemStack result = baseBanner.copy();
            result.stackSize = emptyBanners + 1;
            return result;
        }
        final ItemStack result = baseBanner.copy();
        result.stackSize = 1;
        result.setTagCompound((NBTTagCompound)null);
        return result;
    }
    
    public int getRecipeSize() {
        return 1;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
