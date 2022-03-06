// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;

public class LOTRBlockScorchedWall extends LOTRBlockWallBase
{
    public LOTRBlockScorchedWall() {
        super(LOTRMod.scorchedStone, 1);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return LOTRMod.scorchedStone.getIcon(i, j);
    }
}
