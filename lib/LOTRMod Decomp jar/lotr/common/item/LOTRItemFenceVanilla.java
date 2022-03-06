// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;

public class LOTRItemFenceVanilla extends LOTRItemBlockMetadata
{
    public LOTRItemFenceVanilla(final Block block) {
        super(block);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemstack) {
        return "tile.lotr.fenceVanilla." + itemstack.getItemDamage();
    }
}
