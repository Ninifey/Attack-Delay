// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.dispenser.LOTRDispenseCrossbowBolt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemCrossbowBolt extends Item
{
    public boolean isPoisoned;
    
    public LOTRItemCrossbowBolt() {
        this.isPoisoned = false;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispenseCrossbowBolt(this));
    }
    
    public LOTRItemCrossbowBolt setPoisoned() {
        this.isPoisoned = true;
        return this;
    }
}
