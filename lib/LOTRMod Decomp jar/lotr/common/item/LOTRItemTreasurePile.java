// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemTreasurePile extends ItemBlock
{
    public LOTRItemTreasurePile(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    public int getMetadata(final int i) {
        return i;
    }
}
