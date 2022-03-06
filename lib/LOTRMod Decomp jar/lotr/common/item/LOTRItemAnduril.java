// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemAnduril extends LOTRItemSword implements LOTRStoryItem
{
    public LOTRItemAnduril() {
        super(Item.ToolMaterial.IRON);
        this.setMaxDamage(1500);
        super.lotrWeaponDamage = 9.0f;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabStory);
    }
}
