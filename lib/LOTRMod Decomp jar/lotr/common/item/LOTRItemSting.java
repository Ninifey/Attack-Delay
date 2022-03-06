// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemSting extends LOTRItemDagger implements LOTRStoryItem
{
    public LOTRItemSting() {
        super(Item.ToolMaterial.IRON);
        this.setMaxDamage(700);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabStory);
        super.lotrWeaponDamage += 2.0f;
    }
    
    public float func_150893_a(final ItemStack itemstack, final Block block) {
        if (block == LOTRMod.webUngoliant) {
            return 15.0f;
        }
        return super.func_150893_a(itemstack, block);
    }
}
