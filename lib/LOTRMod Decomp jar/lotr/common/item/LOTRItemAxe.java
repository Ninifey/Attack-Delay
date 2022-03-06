// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class LOTRItemAxe extends ItemAxe
{
    public LOTRItemAxe(final LOTRMaterial material) {
        this(material.toToolMaterial());
    }
    
    public LOTRItemAxe(final Item.ToolMaterial material) {
        super(material);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabTools);
        this.setHarvestLevel("axe", material.getHarvestLevel());
    }
}
