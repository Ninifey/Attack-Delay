// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;

public class LOTRBlockWallClayTile extends LOTRBlockWallBase
{
    public LOTRBlockWallClayTile() {
        super(LOTRMod.clayTile, 1);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return LOTRMod.clayTile.getIcon(i, j);
    }
}
