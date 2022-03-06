// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.ArrayList;
import java.util.Iterator;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.List;

public class LOTRValuableItems
{
    private static List<ItemStack> toolMaterials;
    private static boolean initTools;
    
    private static void registerToolMaterials() {
        if (!LOTRValuableItems.initTools) {
            LOTRValuableItems.toolMaterials.clear();
            final Item.ToolMaterial[] values;
            final Item.ToolMaterial[] allMaterials = values = Item.ToolMaterial.values();
            for (final Item.ToolMaterial material : values) {
                if (material.getHarvestLevel() >= 2) {
                    final ItemStack repair = material.getRepairItemStack();
                    if (repair != null) {
                        LOTRValuableItems.toolMaterials.add(repair.copy());
                    }
                }
            }
            LOTRValuableItems.initTools = true;
        }
    }
    
    public static List<ItemStack> getToolMaterials() {
        registerToolMaterials();
        return LOTRValuableItems.toolMaterials;
    }
    
    public static boolean canMagpieSteal(final ItemStack itemstack) {
        registerToolMaterials();
        final Item item = itemstack.getItem();
        if (item instanceof LOTRItemCoin || item instanceof LOTRItemRing || item instanceof LOTRItemGem) {
            return true;
        }
        for (final ItemStack listItem : LOTRValuableItems.toolMaterials) {
            if (LOTRRecipes.checkItemEquals(listItem, itemstack)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        LOTRValuableItems.toolMaterials = new ArrayList<ItemStack>();
        LOTRValuableItems.initTools = false;
    }
}
