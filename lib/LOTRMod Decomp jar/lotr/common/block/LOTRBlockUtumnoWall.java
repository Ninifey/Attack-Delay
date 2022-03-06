// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRWorldProviderUtumno;

public class LOTRBlockUtumnoWall extends LOTRBlockWallBase implements LOTRWorldProviderUtumno.UtumnoBlock
{
    public LOTRBlockUtumnoWall() {
        super(LOTRMod.utumnoBrick, 6);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (j == 0) {
            return LOTRMod.utumnoBrick.getIcon(i, 0);
        }
        if (j == 1) {
            return LOTRMod.utumnoBrick.getIcon(i, 2);
        }
        if (j == 2) {
            return LOTRMod.utumnoBrick.getIcon(i, 4);
        }
        if (j == 3) {
            return LOTRMod.utumnoBrick.getIcon(i, 6);
        }
        if (j == 4) {
            return LOTRMod.utumnoBrick.getIcon(i, 7);
        }
        if (j == 5) {
            return LOTRMod.utumnoBrick.getIcon(i, 8);
        }
        return super.getIcon(i, j);
    }
}
