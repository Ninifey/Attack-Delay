// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;

public class LOTRBlockWallBone extends LOTRBlockWallBase
{
    public LOTRBlockWallBone() {
        super(LOTRMod.boneBlock, 1);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (j == 0) {
            return LOTRMod.boneBlock.getIcon(i, 0);
        }
        return super.getIcon(i, j);
    }
}
