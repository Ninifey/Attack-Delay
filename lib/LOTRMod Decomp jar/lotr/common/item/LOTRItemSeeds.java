// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;

public class LOTRItemSeeds extends ItemSeeds
{
    public LOTRItemSeeds(final Block crop, final Block soil) {
        super(crop, soil);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMaterials);
    }
}
