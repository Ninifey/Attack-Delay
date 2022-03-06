// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;

public class LOTRBlockFenceGate extends BlockFenceGate
{
    private Block plankBlock;
    private int plankMeta;
    
    public LOTRBlockFenceGate(final Block block, final int meta) {
        this.plankBlock = block;
        this.plankMeta = meta;
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return this.plankBlock.getIcon(i, this.plankMeta);
    }
}
