// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockKebab extends Block
{
    public LOTRBlockKebab() {
        super(Material.sand);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeWood);
    }
}
