// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBarrel;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;

public class LOTRTradeEntryBarrel extends LOTRTradeEntry
{
    public LOTRTradeEntryBarrel(final ItemStack itemstack, final int cost) {
        super(itemstack, cost);
    }
    
    @Override
    public ItemStack createTradeItem() {
        final ItemStack drinkItem = super.createTradeItem();
        final ItemStack barrelItem = new ItemStack(LOTRMod.barrel);
        final LOTRTileEntityBarrel barrel = new LOTRTileEntityBarrel();
        barrel.setInventorySlotContents(9, new ItemStack(drinkItem.getItem(), LOTRBrewingRecipes.BARREL_CAPACITY, drinkItem.getItemDamage()));
        barrel.barrelMode = 2;
        LOTRItemBarrel.setBarrelDataFromTE(barrelItem, barrel);
        return barrelItem;
    }
}
