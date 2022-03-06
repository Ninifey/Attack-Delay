// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;

public class LOTRBlockWallClayTileDyed extends LOTRBlockWallBase
{
    public LOTRBlockWallClayTileDyed() {
        super(LOTRMod.clayTileDyed, 16);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return LOTRMod.clayTileDyed.getIcon(i, j);
    }
}
