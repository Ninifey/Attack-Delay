// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockRedClay extends Block
{
    public LOTRBlockRedClay() {
        super(Material.field_151571_B);
        this.setHardness(0.6f);
        this.setStepSound(Block.soundTypeGravel);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return LOTRMod.redClayBall;
    }
    
    public int quantityDropped(final Random random) {
        return 4;
    }
}
