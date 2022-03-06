// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiScreen;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import lotr.common.LOTRDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.gui.GuiDownloadTerrain;

public class LOTRGuiDownloadTerrain extends GuiDownloadTerrain
{
    private LOTRGuiMap mapGui;
    private LOTRGuiRendererMap mapRenderer;
    private static final int mapBorder = 40;
    private static final float MAP_ZOOM = -0.3f;
    private int tickCounter;
    
    public LOTRGuiDownloadTerrain(final NetHandlerPlayClient handler) {
        super(handler);
        this.mapGui = new LOTRGuiMap();
        (this.mapRenderer = new LOTRGuiRendererMap()).setSepia(true);
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int i, final int j) {
        super.setWorldAndResolution(mc, i, j);
        this.mapGui.setWorldAndResolution(mc, i, j);
    }
    
    public void updateScreen() {
        super.updateScreen();
        ++this.tickCounter;
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        final int dimension = ((Entity)((GuiScreen)this).mc.thePlayer).dimension;
        if (dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            this.func_146278_c(0);
            GL11.glEnable(3008);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            final LOTRGuiRendererMap mapRenderer = this.mapRenderer;
            final LOTRGuiRendererMap mapRenderer2 = this.mapRenderer;
            final float n = (float)LOTRWaypoint.worldToMapX(((Entity)((GuiScreen)this).mc.thePlayer).posX);
            mapRenderer2.mapX = n;
            mapRenderer.prevMapX = n;
            final LOTRGuiRendererMap mapRenderer3 = this.mapRenderer;
            final LOTRGuiRendererMap mapRenderer4 = this.mapRenderer;
            final float n2 = (float)LOTRWaypoint.worldToMapZ(((Entity)((GuiScreen)this).mc.thePlayer).posZ);
            mapRenderer4.mapY = n2;
            mapRenderer3.prevMapY = n2;
            this.mapRenderer.zoomExp = -0.3f;
            this.mapRenderer.zoomStable = (float)Math.pow(2.0, -0.30000001192092896);
            final int x0 = 0;
            final int x2 = ((GuiScreen)this).width;
            final int y0 = 40;
            final int y2 = ((GuiScreen)this).height - 40;
            this.mapRenderer.renderMap((GuiScreen)this, this.mapGui, f, x0, y0, x2, y2);
            this.mapRenderer.renderVignettes((GuiScreen)this, ((Gui)this).zLevel, 1, x0, y0, x2, y2);
            GL11.glDisable(3042);
            final String s = StatCollector.translateToLocal("lotr.loading.enterME");
            this.drawCenteredString(((GuiScreen)this).fontRendererObj, s, ((GuiScreen)this).width / 2, ((GuiScreen)this).height / 2 - 50, 16777215);
        }
        else if (dimension == LOTRDimension.UTUMNO.dimensionID) {
            drawRect(0, 0, ((GuiScreen)this).width, ((GuiScreen)this).height, -16777216);
            GL11.glEnable(3042);
            final float alpha = 1.0f - this.tickCounter / 120.0f;
            final int alphaI = (int)(alpha * 255.0f);
            if (alphaI > 4) {
                final String s2 = StatCollector.translateToLocal("lotr.loading.enterUtumno");
                this.drawCenteredString(((GuiScreen)this).fontRendererObj, s2, ((GuiScreen)this).width / 2, ((GuiScreen)this).height / 2 - 50, alphaI << 24 | 0xFFFFFF);
            }
            GL11.glDisable(3042);
        }
        else {
            super.drawScreen(i, j, f);
        }
    }
}
