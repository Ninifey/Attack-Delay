// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import net.minecraft.inventory.IInventory;
import java.util.Iterator;
import java.lang.reflect.Field;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;

public class LOTRRecipesPoisonDrinks implements IRecipe
{
    public boolean matches(final InventoryCrafting inv, final World world) {
        ItemStack drink = null;
        ItemStack poison = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (LOTRPoisonedDrinks.canPoison(itemstack)) {
                    if (drink != null) {
                        return false;
                    }
                    drink = itemstack;
                }
                else {
                    if (!LOTRMod.isOreNameEqual(itemstack, "poison")) {
                        return false;
                    }
                    if (poison != null) {
                        return false;
                    }
                    poison = itemstack;
                }
            }
        }
        return drink != null && poison != null;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        ItemStack drink = null;
        ItemStack poison = null;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (LOTRPoisonedDrinks.canPoison(itemstack)) {
                    if (drink != null) {
                        return null;
                    }
                    drink = itemstack.copy();
                }
                else {
                    if (!LOTRMod.isOreNameEqual(itemstack, "poison")) {
                        return null;
                    }
                    if (poison != null) {
                        return null;
                    }
                    poison = itemstack.copy();
                }
            }
        }
        if (drink == null || poison == null) {
            return null;
        }
        final ItemStack result = drink.copy();
        LOTRPoisonedDrinks.setDrinkPoisoned(result, true);
        EntityPlayer craftingPlayer = null;
        try {
            if (inv instanceof InventoryCrafting) {
                Container cwb = null;
                for (final Field f : inv.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    final Object obj = f.get(inv);
                    if (obj instanceof Container) {
                        cwb = (Container)obj;
                        break;
                    }
                }
                if (cwb != null) {
                    for (final Object obj2 : cwb.inventorySlots) {
                        final Slot slot = (Slot)obj2;
                        final IInventory slotInv = slot.inventory;
                        if (slotInv instanceof InventoryPlayer) {
                            final InventoryPlayer playerInv = (InventoryPlayer)slotInv;
                            craftingPlayer = playerInv.player;
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (craftingPlayer != null) {
            LOTRPoisonedDrinks.setPoisonerPlayer(result, craftingPlayer);
        }
        else {
            FMLLog.bigWarning("LOTR Warning! Poisoned drink was crafted, player could not be found!", new Object[0]);
        }
        return result;
    }
    
    public int getRecipeSize() {
        return 2;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
