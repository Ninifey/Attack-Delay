// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;

public abstract class LOTRBlockWallBase extends BlockWall
{
    private int subtypes;
    
    public LOTRBlockWallBase(final Block block, final int i) {
        super(block);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.subtypes = i;
    }
    
    public boolean canPlaceTorchOnTop(final World world, final int i, final int j, final int k) {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int j = 0; j < this.subtypes; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
}
