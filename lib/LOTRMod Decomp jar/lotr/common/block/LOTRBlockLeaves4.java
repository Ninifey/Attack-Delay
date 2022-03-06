// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.item.Item;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBlockLeaves4 extends LOTRBlockLeavesBase
{
    public LOTRBlockLeaves4() {
        this.setLeafNames("chestnut", "baobab", "cedar", "fir");
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        super.updateTick(world, i, j, k, random);
        if (!world.isClient && world.getBlock(i, j, k) == this) {
            final int meta = world.getBlockMetadata(i, j, k);
            final int leafType = meta & 0x3;
            final boolean playerPlaced = (meta & 0x4) != 0x0;
            if (leafType == 0 && !playerPlaced && world.isAirBlock(i, j - 1, k) && random.nextInt(300) == 0) {
                final double d = i + random.nextDouble();
                final double d2 = j - 0.2;
                final double d3 = k + random.nextDouble();
                final EntityItem conker = new EntityItem(world, d, d2, d3, new ItemStack(LOTRMod.chestnut));
                conker.delayBeforeCanPickup = 10;
                final EntityItem entityItem = conker;
                final EntityItem entityItem2 = conker;
                final EntityItem entityItem3 = conker;
                final double motionX = 0.0;
                ((Entity)entityItem3).motionZ = motionX;
                ((Entity)entityItem2).motionY = motionX;
                ((Entity)entityItem).motionX = motionX;
                world.spawnEntityInWorld((Entity)conker);
            }
        }
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock(LOTRMod.sapling4);
    }
    
    @Override
    protected void addSpecialLeafDrops(final ArrayList drops, final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        if ((meta & 0x3) == 0x0) {
            final int fruitChance = this.calcFortuneModifiedDropChance(20, fortune);
            if (world.rand.nextInt(fruitChance) == 0) {
                drops.add(new ItemStack(LOTRMod.chestnut));
            }
        }
    }
    
    @Override
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = super.getDrops(world, i, j, k, meta, fortune);
        if ((meta & 0x3) == 0x3 && LOTRMod.isChristmas()) {
            for (final ItemStack itemstack : drops) {
                if (world.rand.nextInt(3) == 0 && itemstack.getItem() == Item.getItemFromBlock(LOTRMod.sapling4)) {
                    itemstack.setStackDisplayName("Christmas Tree");
                }
            }
        }
        return drops;
    }
}
