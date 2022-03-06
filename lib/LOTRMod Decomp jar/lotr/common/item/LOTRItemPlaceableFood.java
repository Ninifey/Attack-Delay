// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.Item;
import lotr.common.block.LOTRBlockPlaceableFood;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemReed;

public class LOTRItemPlaceableFood extends ItemReed
{
    public LOTRItemPlaceableFood(final Block block) {
        super(block);
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
        ((LOTRBlockPlaceableFood)block).foodItem = (Item)this;
    }
}
