// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import java.util.Iterator;
import java.util.Collection;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import java.util.ArrayList;

public class LOTRBrewingRecipes
{
    private static ArrayList<ShapelessOreRecipe> recipes;
    public static int BARREL_CAPACITY;
    
    public static void createBrewingRecipes() {
        addBrewingRecipe(new ItemStack(LOTRMod.mugAle, LOTRBrewingRecipes.BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat);
        addBrewingRecipe(new ItemStack(LOTRMod.mugMiruvor, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.mallornNut, LOTRMod.mallornNut, LOTRMod.mallornNut, LOTRMod.elanor, LOTRMod.niphredil, Items.sugar);
        addBrewingRecipe(new ItemStack(LOTRMod.mugOrcDraught, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.morgulShroom, LOTRMod.morgulShroom, LOTRMod.morgulShroom, "bone", "bone", "bone");
        addBrewingRecipe(new ItemStack(LOTRMod.mugMead, LOTRBrewingRecipes.BARREL_CAPACITY), Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar);
        addBrewingRecipe(new ItemStack(LOTRMod.mugCider, LOTRBrewingRecipes.BARREL_CAPACITY), "apple", "apple", "apple", "apple", "apple", "apple");
        addBrewingRecipe(new ItemStack(LOTRMod.mugPerry, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear);
        addBrewingRecipe(new ItemStack(LOTRMod.mugCherryLiqueur, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry);
        addBrewingRecipe(new ItemStack(LOTRMod.mugRum, LOTRBrewingRecipes.BARREL_CAPACITY), Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds);
        addBrewingRecipe(new ItemStack(LOTRMod.mugAthelasBrew, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas);
        addBrewingRecipe(new ItemStack(LOTRMod.mugDwarvenTonic, LOTRBrewingRecipes.BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, LOTRMod.dwarfHerb, LOTRMod.dwarfHerb, LOTRMod.mithrilNugget);
        addBrewingRecipe(new ItemStack(LOTRMod.mugDwarvenAle, LOTRBrewingRecipes.BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, LOTRMod.dwarfHerb, LOTRMod.dwarfHerb);
        addBrewingRecipe(new ItemStack(LOTRMod.mugVodka, LOTRBrewingRecipes.BARREL_CAPACITY), Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato);
        addBrewingRecipe(new ItemStack(LOTRMod.mugMapleBeer, LOTRBrewingRecipes.BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, LOTRMod.mapleSyrup, LOTRMod.mapleSyrup);
        addBrewingRecipe(new ItemStack(LOTRMod.mugAraq, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date);
        addBrewingRecipe(new ItemStack(LOTRMod.mugCarrotWine, LOTRBrewingRecipes.BARREL_CAPACITY), Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot);
        addBrewingRecipe(new ItemStack(LOTRMod.mugBananaBeer, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana);
        addBrewingRecipe(new ItemStack(LOTRMod.mugMelonLiqueur, LOTRBrewingRecipes.BARREL_CAPACITY), Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon);
        addBrewingRecipe(new ItemStack(LOTRMod.mugCactusLiqueur, LOTRBrewingRecipes.BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus);
        addBrewingRecipe(new ItemStack(LOTRMod.mugTorogDraught, LOTRBrewingRecipes.BARREL_CAPACITY), Items.reeds, Items.reeds, Items.rotten_flesh, Items.rotten_flesh, Blocks.dirt, LOTRMod.rhinoHorn);
        addBrewingRecipe(new ItemStack(LOTRMod.mugLemonLiqueur, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon);
        addBrewingRecipe(new ItemStack(LOTRMod.mugLimeLiqueur, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime);
        addBrewingRecipe(new ItemStack(LOTRMod.mugCornLiquor, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn);
        addBrewingRecipe(new ItemStack(LOTRMod.mugRedWine, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed);
        addBrewingRecipe(new ItemStack(LOTRMod.mugWhiteWine, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite);
        addBrewingRecipe(new ItemStack(LOTRMod.mugMorgulDraught, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.morgulFlower, LOTRMod.morgulFlower, LOTRMod.morgulFlower, "bone", "bone", "bone");
        addBrewingRecipe(new ItemStack(LOTRMod.mugPlumKvass, LOTRBrewingRecipes.BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, LOTRMod.plum, LOTRMod.plum, LOTRMod.plum);
        addBrewingRecipe(new ItemStack(LOTRMod.mugTermiteTequila, LOTRBrewingRecipes.BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, LOTRMod.termite);
        addBrewingRecipe(new ItemStack(LOTRMod.mugSourMilk, LOTRBrewingRecipes.BARREL_CAPACITY), Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket);
        addBrewingRecipe(new ItemStack(LOTRMod.mugPomegranateWine, LOTRBrewingRecipes.BARREL_CAPACITY), LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate);
    }
    
    private static void addBrewingRecipe(final ItemStack result, final Object... ingredients) {
        if (ingredients.length != 6) {
            throw new IllegalArgumentException("Brewing recipes must contain exactly 6 items");
        }
        LOTRBrewingRecipes.recipes.add(new ShapelessOreRecipe(result, ingredients));
    }
    
    public static boolean isWaterSource(final ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.water_bucket;
    }
    
    public static ItemStack findMatchingRecipe(final LOTRTileEntityBarrel barrel) {
        for (int i = 6; i < 9; ++i) {
            final ItemStack itemstack = barrel.getStackInSlot(i);
            if (!isWaterSource(itemstack)) {
                return null;
            }
        }
    Label_0037:
        for (final ShapelessOreRecipe recipe : LOTRBrewingRecipes.recipes) {
            final ArrayList ingredients = new ArrayList(recipe.getInput());
            for (int j = 0; j < 6; ++j) {
                final ItemStack itemstack2 = barrel.getStackInSlot(j);
                if (itemstack2 != null) {
                    boolean inRecipe = false;
                    final Iterator it = ingredients.iterator();
                    while (it.hasNext()) {
                        boolean match = false;
                        final Object next = it.next();
                        if (next instanceof ItemStack) {
                            match = LOTRRecipes.checkItemEquals((ItemStack)next, itemstack2);
                        }
                        else if (next instanceof ArrayList) {
                            for (final ItemStack item : (ArrayList)next) {
                                match = (match || LOTRRecipes.checkItemEquals(item, itemstack2));
                            }
                        }
                        if (match) {
                            inRecipe = true;
                            ingredients.remove(next);
                            break;
                        }
                    }
                    if (!inRecipe) {
                        continue Label_0037;
                    }
                }
            }
            if (ingredients.isEmpty()) {
                return recipe.func_77571_b().copy();
            }
        }
        return null;
    }
    
    static {
        LOTRBrewingRecipes.recipes = new ArrayList<ShapelessOreRecipe>();
        LOTRBrewingRecipes.BARREL_CAPACITY = 16;
    }
}
