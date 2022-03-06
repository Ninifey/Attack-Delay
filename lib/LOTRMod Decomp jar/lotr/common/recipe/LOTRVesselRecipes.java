// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import net.minecraftforge.oredict.ShapelessOreRecipe;
import java.util.Collection;
import java.util.Arrays;
import lotr.common.item.LOTRItemMug;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LOTRVesselRecipes
{
    public static void addRecipes(final ItemStack result, final Object[] ingredients) {
        addRecipes(result, null, ingredients);
    }
    
    public static void addRecipes(final ItemStack result, final Item drinkBase, final Object[] ingredients) {
        final List<IRecipe> recipes = generateRecipes(result, drinkBase, ingredients);
        for (final IRecipe r : recipes) {
            GameRegistry.addRecipe(r);
        }
    }
    
    public static List<IRecipe> generateRecipes(final ItemStack result, final Object[] ingredients) {
        return generateRecipes(result, null, ingredients);
    }
    
    public static List<IRecipe> generateRecipes(final ItemStack result, final Item drinkBase, final Object[] ingredients) {
        final List recipes = new ArrayList();
        for (final LOTRItemMug.Vessel v : LOTRItemMug.Vessel.values()) {
            final List vIngredients = new ArrayList();
            ItemStack vBase = v.getEmptyVessel();
            if (drinkBase != null) {
                vBase = new ItemStack(drinkBase);
                LOTRItemMug.setVessel(vBase, v, true);
            }
            vIngredients.add(vBase);
            vIngredients.addAll(Arrays.asList(ingredients));
            final ItemStack vResult = result.copy();
            LOTRItemMug.setVessel(vResult, v, true);
            final IRecipe recipe = (IRecipe)new ShapelessOreRecipe(vResult, vIngredients.toArray());
            recipes.add(recipe);
        }
        return (List<IRecipe>)recipes;
    }
}
