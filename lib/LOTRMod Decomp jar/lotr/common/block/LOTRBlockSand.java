// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockFalling;

public class LOTRBlockSand extends BlockFalling
{
    public LOTRBlockSand() {
        super(Material.sand);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeSand);
    }
}
