// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.entity.EntityLivingBase;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import net.minecraft.client.model.PositionTextureVertex;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderGuldurilGlow extends TileEntitySpecialRenderer
{
    private static ResourceLocation texture;
    private static final float texSize = 64.0f;
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityGulduril glow = (LOTRTileEntityGulduril)tileentity;
        this.renderGlowAt(d, d1, d2, glow.ticksExisted, f, glow);
    }
    
    private void renderGlowAt(final double d, final double d1, final double d2, final int tick, final float f, final TileEntity te) {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        GL11.glPushMatrix();
        final float glowTick = tick + f;
        this.bindTexture(LOTRRenderGuldurilGlow.texture);
        GL11.glMatrixMode(5890);
        GL11.glLoadIdentity();
        final float glowX = glowTick * 0.008f;
        final float glowY = glowTick * 0.008f;
        GL11.glTranslatef(glowX, glowY, 0.0f);
        GL11.glMatrixMode(5888);
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 0.5f, (float)d2 + 0.5f);
        final float alpha = 0.6f;
        GL11.glColor4f(alpha, alpha, alpha, 1.0f);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(768, 1);
        final boolean glowWest = this.guldurilBlockAt(te, -1, 0, 0);
        final boolean glowEast = this.guldurilBlockAt(te, 1, 0, 0);
        final boolean glowBelow = this.guldurilBlockAt(te, 0, -1, 0);
        final boolean glowAbove = this.guldurilBlockAt(te, 0, 1, 0);
        final boolean glowNorth = this.guldurilBlockAt(te, 0, 0, -1);
        final boolean glowSouth = this.guldurilBlockAt(te, 0, 0, 1);
        final float edge = 8.0f;
        final float edgeNoGlow = 8.5f;
        final float xMin = -(glowWest ? 8.0f : 8.5f) / 16.0f;
        final float xMax = (glowEast ? 8.0f : 8.5f) / 16.0f;
        final float yMin = -(glowBelow ? 8.0f : 8.5f) / 16.0f;
        final float yMax = (glowAbove ? 8.0f : 8.5f) / 16.0f;
        final float zMin = -(glowNorth ? 8.0f : 8.5f) / 16.0f;
        final float zMax = (glowSouth ? 8.0f : 8.5f) / 16.0f;
        final PositionTextureVertex xyz = new PositionTextureVertex(xMin, yMin, zMin, 0.0f, 0.0f);
        final PositionTextureVertex Xyz = new PositionTextureVertex(xMax, yMin, zMin, 0.0f, 8.0f);
        final PositionTextureVertex XYz = new PositionTextureVertex(xMax, yMax, zMin, 8.0f, 8.0f);
        final PositionTextureVertex xYz = new PositionTextureVertex(xMin, yMax, zMin, 8.0f, 0.0f);
        final PositionTextureVertex xyZ = new PositionTextureVertex(xMin, yMin, zMax, 0.0f, 0.0f);
        final PositionTextureVertex XyZ = new PositionTextureVertex(xMax, yMin, zMax, 0.0f, 8.0f);
        final PositionTextureVertex XYZ = new PositionTextureVertex(xMax, yMax, zMax, 8.0f, 8.0f);
        final PositionTextureVertex xYZ = new PositionTextureVertex(xMin, yMax, zMax, 8.0f, 0.0f);
        if (!glowEast) {
            this.renderFace(XyZ, Xyz, XYz, XYZ);
        }
        if (!glowWest) {
            this.renderFace(xyz, xyZ, xYZ, xYz);
        }
        if (!glowBelow) {
            this.renderFace(XyZ, xyZ, xyz, Xyz);
        }
        if (!glowAbove) {
            this.renderFace(XYz, xYz, xYZ, XYZ);
        }
        if (!glowNorth) {
            this.renderFace(Xyz, xyz, xYz, XYz);
        }
        if (!glowSouth) {
            this.renderFace(xyZ, XyZ, XYZ, xYZ);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glMatrixMode(5890);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
    
    private boolean guldurilBlockAt(final TileEntity te, final int dx, final int dy, final int dz) {
        if (te == null) {
            return false;
        }
        final World world = te.getWorldObj();
        final TileEntity otherTE = world.getTileEntity(te.xCoord + dx, te.yCoord + dy, te.zCoord + dz);
        return otherTE instanceof LOTRTileEntityGulduril;
    }
    
    private void renderFace(final PositionTextureVertex v0, final PositionTextureVertex v1, final PositionTextureVertex v2, final PositionTextureVertex v3) {
        final float uMin = 0.0f;
        final float uMax = 0.25f;
        final float vMin = 0.0f;
        final float vMax = 0.25f;
        v0.texturePositionX = uMin;
        v0.texturePositionY = vMax;
        v1.texturePositionX = uMax;
        v1.texturePositionY = vMax;
        v2.texturePositionX = uMax;
        v2.texturePositionY = vMin;
        v3.texturePositionX = uMin;
        v3.texturePositionY = vMin;
        final Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        final PositionTextureVertex[] array;
        final PositionTextureVertex[] vertices = array = new PositionTextureVertex[] { v0, v1, v2, v3 };
        for (final PositionTextureVertex v4 : array) {
            tess.addVertexWithUV(v4.vector3D.xCoord, v4.vector3D.yCoord, v4.vector3D.zCoord, (double)v4.texturePositionX, (double)v4.texturePositionY);
        }
        tess.draw();
    }
    
    public void renderInvGlow() {
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        final EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
        this.renderGlowAt(0.0, 0.0, 0.0, LOTRTickHandlerClient.clientTick, LOTRTickHandlerClient.renderTick, null);
        GL11.glEnable(32826);
    }
    
    static {
        LOTRRenderGuldurilGlow.texture = new ResourceLocation("lotr:misc/gulduril_glow.png");
    }
}
