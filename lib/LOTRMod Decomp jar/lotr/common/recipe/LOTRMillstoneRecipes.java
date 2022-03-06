// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import java.util.HashMap;
import java.util.Iterator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import java.util.Map;

public class LOTRMillstoneRecipes
{
    private static Map<ItemStack, MillstoneResult> recipeList;
    
    public static void createRecipes() {
        addRecipe(Blocks.stone, new ItemStack(Blocks.cobblestone));
        addRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel), 0.75f);
        addRecipe(new ItemStack(LOTRMod.rock, 1, 0), new ItemStack(LOTRMod.mordorGravel), 0.75f);
        addRecipe(Blocks.gravel, new ItemStack(Items.flint), 0.25f);
        addRecipe(LOTRMod.mordorGravel, new ItemStack(Items.flint), 0.25f);
        addRecipe(LOTRMod.obsidianGravel, new ItemStack(LOTRMod.obsidianShard), 1.0f);
        addRecipe(LOTRMod.oreSalt, new ItemStack(LOTRMod.salt));
        addRecipe(new ItemStack(Blocks.sandstone, 1, 0), new ItemStack((Block)Blocks.sand, 2, 0));
        addRecipe(new ItemStack(LOTRMod.redSandstone, 1, 0), new ItemStack((Block)Blocks.sand, 2, 1));
        addRecipe(new ItemStack(LOTRMod.whiteSandstone, 1, 0), new ItemStack(LOTRMod.whiteSand, 2, 0));
        addCrackedBricks(new ItemStack(Blocks.brick_block, 1, 0), new ItemStack(LOTRMod.redBrick, 1, 1));
        addCrackedBricks(new ItemStack(Blocks.stonebrick, 1, 0), new ItemStack(Blocks.stonebrick, 1, 2));
        addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 0), new ItemStack(LOTRMod.brick, 1, 7));
        addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 1), new ItemStack(LOTRMod.brick, 1, 3));
        addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 6), new ItemStack(LOTRMod.brick4, 1, 5));
        addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 11), new ItemStack(LOTRMod.brick, 1, 13));
        addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 15), new ItemStack(LOTRMod.brick3, 1, 11));
        addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 0), new ItemStack(LOTRMod.brick2, 1, 1));
        addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 3), new ItemStack(LOTRMod.brick2, 1, 5));
        addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 8), new ItemStack(LOTRMod.brick2, 1, 9));
        addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 2), new ItemStack(LOTRMod.brick3, 1, 4));
        addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 5), new ItemStack(LOTRMod.brick3, 1, 7));
        addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 13), new ItemStack(LOTRMod.brick3, 1, 14));
        addCrackedBricks(new ItemStack(LOTRMod.brick4, 1, 0), new ItemStack(LOTRMod.brick4, 1, 2));
        addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 1), new ItemStack(LOTRMod.brick6, 1, 4));
        addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 2), new ItemStack(LOTRMod.brick5, 1, 5));
        addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 8), new ItemStack(LOTRMod.brick5, 1, 10));
        addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 11), new ItemStack(LOTRMod.brick5, 1, 14));
        addCrackedBricks(new ItemStack(LOTRMod.brick6, 1, 6), new ItemStack(LOTRMod.brick6, 1, 7));
        addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 0), new ItemStack(LOTRMod.pillar2, 1, 0));
        addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 1), new ItemStack(LOTRMod.pillar, 1, 2));
        addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 10), new ItemStack(LOTRMod.pillar, 1, 11));
        addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 12), new ItemStack(LOTRMod.pillar, 1, 13));
        addCrackedBricks(new ItemStack(LOTRMod.pillar2, 1, 13), new ItemStack(LOTRMod.pillar2, 1, 14));
    }
    
    public static void addRecipe(final Block block, final ItemStack result) {
        addRecipe(block, result, 1.0f);
    }
    
    public static void addRecipe(final Block block, final ItemStack result, final float chance) {
        addRecipe(Item.getItemFromBlock(block), result, chance);
    }
    
    public static void addRecipe(final Item item, final ItemStack result) {
        addRecipe(new ItemStack(item, 1, 32767), result, 1.0f);
    }
    
    public static void addRecipe(final Item item, final ItemStack result, final float chance) {
        addRecipe(new ItemStack(item, 1, 32767), result, chance);
    }
    
    public static void addRecipe(final ItemStack itemstack, final ItemStack result) {
        addRecipe(itemstack, result, 1.0f);
    }
    
    public static void addRecipe(final ItemStack itemstack, final ItemStack result, final float chance) {
        LOTRMillstoneRecipes.recipeList.put(itemstack, new MillstoneResult(result, chance));
    }
    
    public static void addCrackedBricks(final ItemStack itemstack, final ItemStack result) {
        addRecipe(itemstack, result, 1.0f);
        GameRegistry.addSmelting(itemstack, result, 0.1f);
    }
    
    public static MillstoneResult getMillingResult(final ItemStack itemstack) {
        for (final Map.Entry<ItemStack, MillstoneResult> e : LOTRMillstoneRecipes.recipeList.entrySet()) {
            final ItemStack target = e.getKey();
            final MillstoneResult result = e.getValue();
            final ItemStack resultItem = result.resultItem;
            if (matches(itemstack, target)) {
                return result;
            }
        }
        return null;
    }
    
    private static boolean matches(final ItemStack itemstack, final ItemStack target) {
        return target.getItem() == itemstack.getItem() && (target.getItemDamage() == 32767 || target.getItemDamage() == itemstack.getItemDamage());
    }
    
    static {
        LOTRMillstoneRecipes.recipeList = new HashMap<ItemStack, MillstoneResult>();
    }
    
    public static class MillstoneResult
    {
        public final ItemStack resultItem;
        public final float chance;
        
        public MillstoneResult(final ItemStack itemstack, final float f) {
            this.resultItem = itemstack;
            this.chance = f;
        }
    }
}
