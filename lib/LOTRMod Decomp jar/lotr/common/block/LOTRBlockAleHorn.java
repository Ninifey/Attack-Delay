// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockAleHorn extends LOTRBlockMug
{
    public LOTRBlockAleHorn() {
        super(5.0f, 12.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, final int j) {
        return Blocks.stained_hardened_clay.getIcon(i, 0);
    }
}
