// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import java.util.Random;

public class LOTRBlockObsidianGravel extends LOTRBlockGravel
{
    public Item getItemDropped(final int i, final Random rand, final int j) {
        return Item.getItemFromBlock((Block)this);
    }
}
