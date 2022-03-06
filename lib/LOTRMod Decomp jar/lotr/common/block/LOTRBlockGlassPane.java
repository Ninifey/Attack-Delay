// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockGlassPane extends LOTRBlockPane
{
    public LOTRBlockGlassPane() {
        super("lotr:glass", "lotr:glass_pane_top", Material.glass, false);
        this.setHardness(0.3f);
        this.setStepSound(Block.soundTypeGlass);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public boolean canPaneConnectTo(final IBlockAccess world, final int i, final int j, final int k, final ForgeDirection dir) {
        return super.canPaneConnectTo(world, i, j, k, dir) || world.getBlock(i, j, k) instanceof LOTRBlockGlass;
    }
}
