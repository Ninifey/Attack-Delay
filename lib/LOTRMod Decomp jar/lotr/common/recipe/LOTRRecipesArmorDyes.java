// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemDye;
import net.minecraft.item.ItemArmor;
import java.util.ArrayList;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipesArmorDyes;

public class LOTRRecipesArmorDyes extends RecipesArmorDyes implements IRecipe
{
    public boolean matches(final InventoryCrafting inv, final World world) {
        ItemStack armor = null;
        final ArrayList dyes = new ArrayList();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() instanceof ItemArmor) {
                    final ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
                    if (armor != null) {
                        return false;
                    }
                    if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.CLOTH) {
                        return false;
                    }
                    armor = itemstack;
                }
                else {
                    if (LOTRItemDye.isItemDye(itemstack) == -1) {
                        return false;
                    }
                    dyes.add(itemstack);
                }
            }
        }
        return armor != null && !dyes.isEmpty();
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        ItemStack armor = null;
        ItemArmor itemarmor = null;
        final int[] rgb = new int[3];
        int totalColor = 0;
        int coloredItems = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() instanceof ItemArmor) {
                    itemarmor = (ItemArmor)itemstack.getItem();
                    if (armor != null) {
                        return null;
                    }
                    if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.CLOTH) {
                        return null;
                    }
                    armor = itemstack.copy();
                    armor.stackSize = 1;
                    if (itemarmor.hasColor(armor)) {
                        final int armorColor = itemarmor.getColor(armor);
                        final float r = (armorColor >> 16 & 0xFF) / 255.0f;
                        final float g = (armorColor >> 8 & 0xFF) / 255.0f;
                        final float b = (armorColor & 0xFF) / 255.0f;
                        totalColor += (int)(Math.max(r, Math.max(g, b)) * 255.0f);
                        rgb[0] += (int)(r * 255.0f);
                        rgb[1] += (int)(g * 255.0f);
                        rgb[2] += (int)(b * 255.0f);
                        ++coloredItems;
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
                }
            }
        }
        if (armor == null) {
            return null;
        }
        int r3 = rgb[0] / coloredItems;
        int g3 = rgb[1] / coloredItems;
        int b3 = rgb[2] / coloredItems;
        final float averageColor = totalColor / (float)coloredItems;
        final float maxColor = (float)Math.max(r3, Math.max(g3, b3));
        r3 = (int)(r3 * averageColor / maxColor);
        g3 = (int)(g3 * averageColor / maxColor);
        b3 = (int)(b3 * averageColor / maxColor);
        final int color = (r3 << 16) + (g3 << 8) + b3;
        itemarmor.func_82813_b(armor, color);
        return armor;
    }
    
    public int getRecipeSize() {
        return 10;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
