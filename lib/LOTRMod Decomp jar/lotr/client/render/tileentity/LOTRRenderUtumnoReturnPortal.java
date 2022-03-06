// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.world.World;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import lotr.common.tileentity.LOTRTileEntityUtumnoReturnPortal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderUtumnoReturnPortal extends TileEntitySpecialRenderer
{
    private static ResourceLocation lightCircle;
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityUtumnoReturnPortal portal = (LOTRTileEntityUtumnoReturnPortal)tileentity;
        final World world = portal.getWorldObj();
        world.theProfiler.startSection("utumnoReturnPortal");
        final float renderTime = portal.ticksExisted + f;
        final Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glAlphaFunc(516, 0.01f);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslatef((float)d + 0.5f, (float)d1, (float)d2 + 0.5f);
        float alpha = 0.2f + 0.15f * MathHelper.sin(renderTime * 0.1f);
        for (int passes = 12, i = 0; i < passes; ++i) {
            GL11.glPushMatrix();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, alpha);
            double width = (i + 1) / (float)passes * 0.99f;
            width /= 2.0;
            final double bottom = 0.0;
            final double top = bottom + LOTRTileEntityUtumnoReturnPortal.PORTAL_TOP;
            tessellator.addVertexWithUV(width, bottom, width, 0.0, 0.0);
            tessellator.addVertexWithUV(width, top, width, 0.0, 0.0);
            tessellator.addVertexWithUV(-width, top, width, 0.0, 0.0);
            tessellator.addVertexWithUV(-width, bottom, width, 0.0, 0.0);
            tessellator.addVertexWithUV(width, bottom, -width, 0.0, 0.0);
            tessellator.addVertexWithUV(width, top, -width, 0.0, 0.0);
            tessellator.addVertexWithUV(-width, top, -width, 0.0, 0.0);
            tessellator.addVertexWithUV(-width, bottom, -width, 0.0, 0.0);
            tessellator.addVertexWithUV(width, bottom, width, 0.0, 0.0);
            tessellator.addVertexWithUV(width, top, width, 0.0, 0.0);
            tessellator.addVertexWithUV(width, top, -width, 0.0, 0.0);
            tessellator.addVertexWithUV(width, bottom, -width, 0.0, 0.0);
            tessellator.addVertexWithUV(-width, bottom, width, 0.0, 0.0);
            tessellator.addVertexWithUV(-width, top, width, 0.0, 0.0);
            tessellator.addVertexWithUV(-width, top, -width, 0.0, 0.0);
            tessellator.addVertexWithUV(-width, bottom, -width, 0.0, 0.0);
            tessellator.draw();
            GL11.glPopMatrix();
        }
        GL11.glEnable(3553);
        this.bindTexture(LOTRRenderUtumnoReturnPortal.lightCircle);
        for (int circles = 12, j = 0; j < circles; ++j) {
            GL11.glPushMatrix();
            final float rotation = renderTime * j * 0.2f;
            GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
            alpha = 0.15f + 0.1f * MathHelper.sin(renderTime * 0.1f + j);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
            final double width2 = 1.5f + 1.5f * MathHelper.sin(renderTime * 0.05f + j);
            final double height = 0.01 + j * 0.01;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(width2, height, width2, 1.0, 1.0);
            tessellator.addVertexWithUV(width2, height, -width2, 1.0, 0.0);
            tessellator.addVertexWithUV(-width2, height, -width2, 0.0, 0.0);
            tessellator.addVertexWithUV(-width2, height, width2, 0.0, 1.0);
            tessellator.draw();
            GL11.glPopMatrix();
        }
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        world.theProfiler.endSection();
    }
    
    static {
        LOTRRenderUtumnoReturnPortal.lightCircle = new ResourceLocation("lotr:misc/utumnoPortal_lightCircle.png");
    }
}
