// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import java.util.ArrayList;
import net.minecraft.world.IBlockAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraftforge.common.IShearable;

public class LOTRBlockDeadPlant extends LOTRBlockFlower implements IShearable
{
    public LOTRBlockDeadPlant() {
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final int i, final int j, final int k) {
        return true;
    }
    
    public ArrayList onSheared(final ItemStack item, final IBlockAccess world, final int i, final int j, final int k, final int fortune) {
        final ArrayList drops = new ArrayList();
        drops.add(new ItemStack((Block)this));
        return drops;
    }
}
