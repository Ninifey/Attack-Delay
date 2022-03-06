// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockScorchedStone extends Block
{
    public LOTRBlockScorchedStone() {
        super(Material.rock);
        this.setHardness(2.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
}
