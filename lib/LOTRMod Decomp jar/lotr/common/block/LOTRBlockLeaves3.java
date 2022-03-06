// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;

public class LOTRBlockLeaves3 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeaves3() {
        this.setLeafNames("maple", "larch", "datePalm", "mangrove");
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.sapling3);
    }
    
    @Override
    protected int getSaplingChance(final int meta) {
        if (meta == 2) {
            return 15;
        }
        return super.getSaplingChance(meta);
    }
}
