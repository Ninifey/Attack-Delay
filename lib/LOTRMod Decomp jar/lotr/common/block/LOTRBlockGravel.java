// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockGravel;

public class LOTRBlockGravel extends BlockGravel
{
    public LOTRBlockGravel() {
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(0.6f);
        this.setStepSound(Block.soundTypeGravel);
    }
}
