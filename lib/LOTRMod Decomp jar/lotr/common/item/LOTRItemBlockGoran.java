// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.block.LOTRBlockGoran;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;

public class LOTRItemBlockGoran extends LOTRItemBlockMetadata
{
    public LOTRItemBlockGoran(final Block block) {
        super(block);
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        int i = itemstack.getItemDamage();
        if (i >= LOTRBlockGoran.displayNames.length) {
            i = 0;
        }
        return LOTRBlockGoran.displayNames[i];
    }
}
