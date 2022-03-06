// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.OpenGlHelper;
import lotr.common.world.map.LOTRWaypoint;
import lotr.client.LOTRTextures;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiRendererMap
{
    private static final ResourceLocation vignetteTexture;
    public float prevMapX;
    public float mapX;
    public float prevMapY;
    public float mapY;
    public float zoomExp;
    public float zoomStable;
    private boolean sepia;
    
    public LOTRGuiRendererMap() {
        this.sepia = false;
    }
    
    public void setSepia(final boolean flag) {
        this.sepia = flag;
    }
    
    public void updateTick() {
        this.prevMapX = this.mapX;
        this.prevMapY = this.mapY;
    }
    
    public void renderMap(final GuiScreen gui, final LOTRGuiMap mapGui, final float f) {
        this.renderMap(gui, mapGui, f, 0, 0, gui.width, gui.height);
    }
    
    public void renderMap(final GuiScreen gui, final LOTRGuiMap mapGui, final float f, final int x0, final int y0, final int x1, final int y1) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int oceanColor = LOTRTextures.getMapOceanColor(this.sepia);
        GuiScreen.drawRect(x0, y0, x1, y1, oceanColor);
        final float zoom = (float)Math.pow(2.0, this.zoomExp);
        final float mapPosX = this.prevMapX + (this.mapX - this.prevMapX) * f;
        final float mapPosY = this.prevMapY + (this.mapY - this.prevMapY) * f;
        mapGui.setFakeMapProperties(mapPosX, mapPosY, zoom, this.zoomExp, this.zoomStable);
        final int[] statics = LOTRGuiMap.setFakeStaticProperties(x1 - x0, y1 - y0, x0, x1, y0, y1);
        mapGui.enableZoomOutWPFading = false;
        mapGui.renderMapAndOverlay(this.sepia, 1.0f, true);
        mapGui.renderRoads(false);
        mapGui.renderWaypoints(LOTRWaypoint.listAllWaypoints(), 0, 0, 0, false, true);
        LOTRGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
    }
    
    public void renderVignette(final GuiScreen gui, final double zLevel) {
        this.renderVignette(gui, zLevel, 0, 0, gui.width, gui.height);
    }
    
    public void renderVignette(final GuiScreen gui, final double zLevel, final int x0, final int y0, final int x1, final int y1) {
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(771, 769, 1, 0);
        final float alpha = 1.0f;
        GL11.glColor4f(alpha, alpha, alpha, 1.0f);
        gui.mc.getTextureManager().bindTexture(LOTRGuiRendererMap.vignetteTexture);
        final double u0 = x0 / (double)gui.width;
        final double u2 = x1 / (double)gui.width;
        final double v0 = y0 / (double)gui.height;
        final double v2 = y1 / (double)gui.height;
        final double z = zLevel;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x0, (double)y1, z, u0, v2);
        tessellator.addVertexWithUV((double)x1, (double)y1, z, u2, v2);
        tessellator.addVertexWithUV((double)x1, (double)y0, z, u2, v0);
        tessellator.addVertexWithUV((double)x0, (double)y0, z, u0, v0);
        tessellator.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    }
    
    public void renderVignettes(final GuiScreen gui, final double zLevel, final int count) {
        for (int l = 0; l < count; ++l) {
            this.renderVignette(gui, zLevel);
        }
    }
    
    public void renderVignettes(final GuiScreen gui, final double zLevel, final int count, final int x0, final int y0, final int x1, final int y1) {
        for (int l = 0; l < count; ++l) {
            this.renderVignette(gui, zLevel, x0, y0, x1, y1);
        }
    }
    
    static {
        vignetteTexture = new ResourceLocation("textures/misc/vignette.png");
    }
}
