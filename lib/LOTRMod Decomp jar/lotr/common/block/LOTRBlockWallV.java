// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class LOTRBlockWallV extends LOTRBlockWallBase
{
    public LOTRBlockWallV() {
        super(Blocks.stonebrick, 9);
        this.setcreativeTab(CreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (j == 0) {
            return Blocks.stone.getIcon(i, 0);
        }
        if (j == 1) {
            return Blocks.stonebrick.getIcon(i, 0);
        }
        if (j == 2) {
            return Blocks.stonebrick.getIcon(i, 1);
        }
        if (j == 3) {
            return Blocks.stonebrick.getIcon(i, 2);
        }
        if (j == 4) {
            return Blocks.sandstone.getIcon(i, 0);
        }
        if (j == 5) {
            return LOTRMod.redSandstone.getIcon(i, 0);
        }
        if (j == 6) {
            return Blocks.brick_block.getIcon(i, 0);
        }
        if (j == 7) {
            return LOTRMod.redBrick.getIcon(i, 0);
        }
        if (j == 8) {
            return LOTRMod.redBrick.getIcon(i, 1);
        }
        return super.getIcon(i, j);
    }
}
