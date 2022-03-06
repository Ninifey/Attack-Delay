// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import lotr.common.inventory.LOTRInventoryPouch;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import java.util.Iterator;
import java.util.List;
import lotr.common.item.LOTRItemDye;
import lotr.common.item.LOTRItemPouch;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.crafting.IRecipe;

public class LOTRRecipesPouch implements IRecipe
{
    private int overrideColor;
    private boolean hasOverrideColor;
    
    public LOTRRecipesPouch() {
        this(-1, false);
    }
    
    public LOTRRecipesPouch(final LOTRFaction f) {
        this(f.getFactionColor(), true);
    }
    
    public LOTRRecipesPouch(final int color, final boolean flag) {
        this.overrideColor = color;
        this.hasOverrideColor = flag;
    }
    
    public boolean matches(final InventoryCrafting inv, final World world) {
        final List<ItemStack> pouches = new ArrayList<ItemStack>();
        final List<ItemStack> dyes = new ArrayList<ItemStack>();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() instanceof LOTRItemPouch) {
                    pouches.add(itemstack);
                }
                else {
                    if (LOTRItemDye.isItemDye(itemstack) == -1) {
                        return false;
                    }
                    dyes.add(itemstack);
                }
            }
        }
        if (pouches.isEmpty()) {
            return false;
        }
        if (pouches.size() == 1) {
            return this.hasOverrideColor || !dyes.isEmpty();
        }
        final int meta = this.getCombinedMeta(pouches);
        return LOTRItemPouch.getCapacityForMeta(meta) <= LOTRItemPouch.getMaxPouchCapacity();
    }
    
    private int getCombinedMeta(final List<ItemStack> pouches) {
        int size = 0;
        for (final ItemStack pouch : pouches) {
            size += pouch.getItemDamage() + 1;
        }
        return size - 1;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        final List<ItemStack> pouches = new ArrayList<ItemStack>();
        final int[] rgb = new int[3];
        int totalColor = 0;
        int coloredItems = 0;
        boolean anyDye = false;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() instanceof LOTRItemPouch) {
                    pouches.add(itemstack);
                    final int pouchColor = LOTRItemPouch.getPouchColor(itemstack);
                    final float r = (pouchColor >> 16 & 0xFF) / 255.0f;
                    final float g = (pouchColor >> 8 & 0xFF) / 255.0f;
                    final float b = (pouchColor & 0xFF) / 255.0f;
                    totalColor += (int)(Math.max(r, Math.max(g, b)) * 255.0f);
                    rgb[0] += (int)(r * 255.0f);
                    rgb[1] += (int)(g * 255.0f);
                    rgb[2] += (int)(b * 255.0f);
                    ++coloredItems;
                    if (LOTRItemPouch.isPouchDyed(itemstack)) {
                        anyDye = true;
                    }
                }
                else {
                    final int dye = LOTRItemDye.isItemDye(itemstack);
                    if (dye == -1) {
                        return null;
                    }
                    final float[] dyeColors = EntitySheep.fleeceColorTable[BlockColored.func_150031_c(dye)];
                    final int r2 = (int)(dyeColors[0] * 255.0f);
                    final int g2 = (int)(dyeColors[1] * 255.0f);
                    final int b2 = (int)(dyeColors[2] * 255.0f);
                    totalColor += Math.max(r2, Math.max(g2, b2));
                    final int[] array = rgb;
                    final int n = 0;
                    array[n] += r2;
                    final int[] array2 = rgb;
                    final int n2 = 1;
                    array2[n2] += g2;
                    final int[] array3 = rgb;
                    final int n3 = 2;
                    array3[n3] += b2;
                    ++coloredItems;
                    anyDye = true;
                }
            }
        }
        if (pouches.isEmpty()) {
            return null;
        }
        ItemStack pouch;
        if (pouches.size() == 1) {
            pouch = pouches.get(0).copy();
        }
        else {
            final int meta = this.getCombinedMeta(pouches);
            pouch = new ItemStack(LOTRMod.pouch);
            pouch.stackSize = 1;
            pouch.setItemDamage(meta);
            final LOTRInventoryPouch pouchInv = new LOTRInventoryPouch(pouch);
            int slot = 0;
            for (final ItemStack craftingPouch : pouches) {
                final LOTRInventoryPouch craftingPouchInv = new LOTRInventoryPouch(craftingPouch);
                for (int j = 0; j < craftingPouchInv.getSizeInventory(); ++j) {
                    final ItemStack slotItem = craftingPouchInv.getStackInSlot(j);
                    if (slotItem != null) {
                        pouchInv.setInventorySlotContents(slot, slotItem.copy());
                        ++slot;
                    }
                }
            }
        }
        if (this.hasOverrideColor) {
            LOTRItemPouch.setPouchColor(pouch, this.overrideColor);
        }
        else if (anyDye && coloredItems > 0) {
            int r3 = rgb[0] / coloredItems;
            int g3 = rgb[1] / coloredItems;
            int b3 = rgb[2] / coloredItems;
            final float averageColor = totalColor / (float)coloredItems;
            final float maxColor = (float)Math.max(r3, Math.max(g3, b3));
            r3 = (int)(r3 * averageColor / maxColor);
            g3 = (int)(g3 * averageColor / maxColor);
            b3 = (int)(b3 * averageColor / maxColor);
            final int color = (r3 << 16) + (g3 << 8) + b3;
            LOTRItemPouch.setPouchColor(pouch, color);
        }
        return pouch;
    }
    
    public int getRecipeSize() {
        return 10;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
