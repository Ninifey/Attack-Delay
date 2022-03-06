// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;

public class LOTRBlockFruitLeaves extends LOTRBlockLeavesBase
{
    public LOTRBlockFruitLeaves() {
        this.setLeafNames("apple", "pear", "cherry", "mango");
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.fruitSapling);
    }
    
    @Override
    protected void addSpecialLeafDrops(final ArrayList drops, final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        if ((meta & 0x3) == 0x0) {
            final int fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                if (world.rand.nextBoolean()) {
                    drops.add(new ItemStack(Items.apple));
                }
                else {
                    drops.add(new ItemStack(LOTRMod.appleGreen));
                }
            }
        }
        if ((meta & 0x3) == 0x1) {
            final int fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.pear));
            }
        }
        if ((meta & 0x3) == 0x2) {
            final int fruitChance = this.calcFortuneModifiedDropChance(8, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.cherry));
            }
        }
        if ((meta & 0x3) == 0x3) {
            final int fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.mango));
            }
        }
    }
}
