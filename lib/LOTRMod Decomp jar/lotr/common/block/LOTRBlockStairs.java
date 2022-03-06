// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class LOTRBlockStairs extends BlockStairs
{
    public LOTRBlockStairs(final Block block, final int metadata) {
        super(block, metadata);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        ((Block)this).field_149783_u = true;
    }
}
