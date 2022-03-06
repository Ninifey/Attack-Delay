// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;

public class LOTRBlockLeaves8 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeaves8() {
        this.setLeafNames("plum", "redwood", "pomegranate", "palm");
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.sapling8);
    }
    
    @Override
    protected void addSpecialLeafDrops(final ArrayList drops, final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        if ((meta & 0x3) == 0x0) {
            final int fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.plum));
            }
        }
        if ((meta & 0x3) == 0x2) {
            final int fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.pomegranate));
            }
        }
    }
}
