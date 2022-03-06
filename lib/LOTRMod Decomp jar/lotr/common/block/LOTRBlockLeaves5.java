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

public class LOTRBlockLeaves5 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeaves5() {
        this.setLeafNames("pine", "lemon", "orange", "lime");
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.sapling5);
    }
    
    @Override
    protected void addSpecialLeafDrops(final ArrayList drops, final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        if ((meta & 0x3) == 0x1) {
            final int fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.lemon));
            }
        }
        if ((meta & 0x3) == 0x2) {
            final int fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.orange));
            }
        }
        if ((meta & 0x3) == 0x3) {
            final int fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.lime));
            }
        }
    }
}
