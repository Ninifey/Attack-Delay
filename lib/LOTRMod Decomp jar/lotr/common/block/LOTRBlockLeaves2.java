// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;

public class LOTRBlockLeaves2 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeaves2() {
        this.setLeafNames("lebethron", "beech", "holly", "banana");
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.sapling2);
    }
    
    @Override
    protected int getSaplingChance(final int meta) {
        if (meta == 3) {
            return 9;
        }
        return super.getSaplingChance(meta);
    }
}
