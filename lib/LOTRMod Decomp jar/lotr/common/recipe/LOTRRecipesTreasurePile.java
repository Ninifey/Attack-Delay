// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;

public class LOTRRecipesTreasurePile implements IRecipe
{
    private Block treasureBlock;
    private Item ingotItem;
    
    public LOTRRecipesTreasurePile(final Block block, final Item item) {
        this.treasureBlock = block;
        this.ingotItem = item;
    }
    
    public boolean matches(final InventoryCrafting inv, final World world) {
        return this.getCraftingResult(inv) != null;
    }
    
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        int ingredientCount = 0;
        int ingredientTotalSize = 0;
        int resultCount = 0;
        int resultMeta = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null) {
                if (itemstack.getItem() != Item.getItemFromBlock(this.treasureBlock)) {
                    return null;
                }
                ++ingredientCount;
                final int meta = itemstack.getItemDamage();
                ingredientTotalSize += meta + 1;
            }
        }
        if (ingredientCount > 0) {
            if (ingredientCount == 1) {
                if (ingredientTotalSize > 1) {
                    resultCount = ingredientTotalSize;
                    resultMeta = 0;
                }
            }
            else {
                resultCount = 1;
                resultMeta = ingredientTotalSize - 1;
            }
        }
        if (resultCount <= 0 || resultMeta > 7) {
            return null;
        }
        if (ingredientCount == 1 && ingredientTotalSize == 8) {
            return new ItemStack(this.ingotItem, 4);
        }
        return new ItemStack(this.treasureBlock, resultCount, resultMeta);
    }
    
    public int getRecipeSize() {
        return 10;
    }
    
    public ItemStack getRecipeOutput() {
        return null;
    }
}
