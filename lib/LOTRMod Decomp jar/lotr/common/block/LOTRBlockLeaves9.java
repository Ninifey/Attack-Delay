// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;

public class LOTRBlockLeaves9 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeaves9() {
        this.setLeafNames("dragon", "kanuka");
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.sapling9);
    }
}
