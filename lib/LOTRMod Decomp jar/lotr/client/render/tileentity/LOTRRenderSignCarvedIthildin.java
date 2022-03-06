// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import lotr.client.LOTRClientProxy;
import lotr.common.tileentity.LOTRTileEntitySignCarved;
import lotr.common.tileentity.LOTRTileEntitySignCarvedIthildin;
import net.minecraft.tileentity.TileEntity;

public class LOTRRenderSignCarvedIthildin extends LOTRRenderSignCarved
{
    @Override
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntitySignCarvedIthildin sign = (LOTRTileEntitySignCarvedIthildin)tileentity;
        final float alphaFunc = LOTRRenderDwarvenGlow.setupGlow(sign.getGlowBrightness(f));
        super.renderTileEntityAt(tileentity, d, d1, d2, f);
        LOTRRenderDwarvenGlow.endGlow(alphaFunc);
    }
    
    @Override
    protected int getTextColor(final LOTRTileEntitySignCarved sign, final float f) {
        final float glow = ((LOTRTileEntitySignCarvedIthildin)sign).getGlowBrightness(f);
        final int alpha = LOTRClientProxy.getAlphaInt(glow);
        return 0xFFFFFF | alpha << 24;
    }
}
