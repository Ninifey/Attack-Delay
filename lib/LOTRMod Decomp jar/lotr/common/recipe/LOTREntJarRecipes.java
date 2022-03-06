// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import java.util.HashMap;
import java.util.Iterator;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import java.util.Map;

public class LOTREntJarRecipes
{
    private static Map<ItemStack, ItemStack> recipes;
    
    public static void createDraughtRecipes() {
        addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 0), new ItemStack(LOTRMod.entDraught, 1, 0));
        addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 1), new ItemStack(LOTRMod.entDraught, 1, 1));
        addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 2), new ItemStack(LOTRMod.entDraught, 1, 2));
        addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 3), new ItemStack(LOTRMod.entDraught, 1, 3));
        addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 4), new ItemStack(LOTRMod.entDraught, 1, 4));
        addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 5), new ItemStack(LOTRMod.entDraught, 1, 5));
        addDraughtRecipe(new ItemStack(LOTRMod.fangornRiverweed), new ItemStack(LOTRMod.entDraught, 1, 6));
    }
    
    private static void addDraughtRecipe(final ItemStack ingredient, final ItemStack result) {
        LOTREntJarRecipes.recipes.put(ingredient, result);
    }
    
    public static ItemStack findMatchingRecipe(final ItemStack input) {
        if (input == null) {
            return null;
        }
        for (final ItemStack recipeInput : LOTREntJarRecipes.recipes.keySet()) {
            if (LOTRRecipes.checkItemEquals(recipeInput, input)) {
                return LOTREntJarRecipes.recipes.get(recipeInput).copy();
            }
        }
        return null;
    }
    
    static {
        LOTREntJarRecipes.recipes = new HashMap<ItemStack, ItemStack>();
    }
}
