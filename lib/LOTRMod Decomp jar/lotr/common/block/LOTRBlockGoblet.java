// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockGoblet extends LOTRBlockMug
{
    public LOTRBlockGoblet() {
        super(2.5f, 9.0f);
        this.setStepSound(Block.soundTypeMetal);
    }
    
    public static class Gold extends LOTRBlockGoblet
    {
        @SideOnly(Side.CLIENT)
        @Override
        public IIcon getIcon(final int i, final int j) {
            return Blocks.gold_block.getIcon(i, 0);
        }
    }
    
    public static class Silver extends LOTRBlockGoblet
    {
        @SideOnly(Side.CLIENT)
        @Override
        public IIcon getIcon(final int i, final int j) {
            return LOTRMod.blockOreStorage.getIcon(i, 3);
        }
    }
    
    public static class Copper extends LOTRBlockGoblet
    {
        @SideOnly(Side.CLIENT)
        @Override
        public IIcon getIcon(final int i, final int j) {
            return LOTRMod.blockOreStorage.getIcon(i, 0);
        }
    }
    
    public static class Wood extends LOTRBlockGoblet
    {
        public Wood() {
            this.setStepSound(Block.soundTypeWood);
        }
        
        @SideOnly(Side.CLIENT)
        @Override
        public IIcon getIcon(final int i, final int j) {
            return Blocks.planks.getIcon(i, 0);
        }
    }
}
