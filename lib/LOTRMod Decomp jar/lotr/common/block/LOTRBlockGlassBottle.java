// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockGlassBottle extends LOTRBlockMug
{
    public LOTRBlockGlassBottle() {
        super(3.0f, 10.0f);
        this.setStepSound(Block.soundTypeGlass);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, final int j) {
        return Blocks.glass.getIcon(i, 0);
    }
}
