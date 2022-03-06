// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;

public class LOTRBlockWall5 extends LOTRBlockWallBase
{
    public LOTRBlockWall5() {
        super(LOTRMod.brick, 3);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (j == 0) {
            return LOTRMod.brick6.getIcon(i, 6);
        }
        if (j == 1) {
            return LOTRMod.brick6.getIcon(i, 7);
        }
        if (j == 2) {
            return LOTRMod.brick6.getIcon(i, 10);
        }
        return super.getIcon(i, j);
    }
}
