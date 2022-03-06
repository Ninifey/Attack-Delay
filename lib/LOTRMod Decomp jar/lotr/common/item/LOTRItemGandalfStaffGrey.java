// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemGandalfStaffGrey extends LOTRItemSword implements LOTRStoryItem
{
    public LOTRItemGandalfStaffGrey() {
        super(Item.ToolMaterial.WOOD);
        this.setMaxDamage(1000);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabStory);
        super.lotrWeaponDamage = 4.0f;
    }
}
