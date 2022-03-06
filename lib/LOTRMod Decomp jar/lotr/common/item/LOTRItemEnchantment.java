// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import lotr.common.enchant.LOTREnchantment;
import net.minecraft.item.Item;

public class LOTRItemEnchantment extends Item
{
    public final LOTREnchantment theEnchant;
    
    public LOTRItemEnchantment(final LOTREnchantment ench) {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMaterials);
        this.theEnchant = ench;
    }
}
