// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.tileentity.LOTRTileEntityMorgulPortal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderMorgulPortal extends TileEntitySpecialRenderer
{
    private static ResourceLocation portalTexture;
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityMorgulPortal portal = (LOTRTileEntityMorgulPortal)tileentity;
        final float f2 = LOTRTickHandlerClient.clientTick + f;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        GL11.glPushMatrix();
        this.bindTexture(LOTRRenderMorgulPortal.portalTexture);
        GL11.glMatrixMode(5890);
        GL11.glLoadIdentity();
        final float f3 = f2 * 0.01f;
        GL11.glTranslatef(f3, 0.0f, 0.0f);
        GL11.glMatrixMode(5888);
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 0.5f + 0.25f * MathHelper.sin(f2 / 40.0f), (float)d2 + 0.5f);
        final float f4 = 0.9f;
        GL11.glColor4f(f4, f4, f4, 1.0f);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(768, 1);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.5, 0.0, 0.5, 0.0, 0.0);
        tessellator.addVertexWithUV(0.5, 0.0, -0.5, 0.0, 0.0);
        tessellator.addVertexWithUV(-0.5, 0.0, -0.5, 0.0, 0.0);
        tessellator.addVertexWithUV(-0.5, 0.0, 0.5, 0.0, 0.0);
        tessellator.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glMatrixMode(5890);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderMorgulPortal.portalTexture = new ResourceLocation("lotr:misc/gulduril_glow.png");
    }
}
