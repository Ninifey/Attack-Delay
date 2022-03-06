// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.dispenser.LOTRDispenseArrowPoisoned;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemArrowPoisoned extends Item
{
    public LOTRItemArrowPoisoned() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispenseArrowPoisoned());
    }
}
