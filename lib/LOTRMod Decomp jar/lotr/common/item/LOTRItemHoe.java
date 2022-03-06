// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

public class LOTRItemHoe extends ItemHoe
{
    public LOTRItemHoe(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemHoe(final Item.ToolMaterial material) {
        super(material);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabTools);
    }
}
