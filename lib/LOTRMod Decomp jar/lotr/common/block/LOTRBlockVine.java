// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockVine;

public class LOTRBlockVine extends BlockVine
{
    public LOTRBlockVine() {
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setHardness(0.2f);
        this.setStepSound(Block.soundTypeGrass);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int i) {
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        return 16777215;
    }
}
