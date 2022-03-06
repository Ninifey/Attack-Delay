// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import java.util.List;
import lotr.common.util.LOTRFunctions;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StatCollector;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.world.World;
import lotr.client.LOTRClientProxy;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.entity.npc.LOTRSpeech;
import net.minecraft.util.ResourceLocation;
import lotr.common.world.map.LOTRAbstractWaypoint;

public class LOTRGuiFastTravel extends LOTRGuiScreenBase
{
    private LOTRGuiMap mapGui;
    private LOTRGuiRendererMap mapRenderer;
    private int tickCounter;
    private LOTRAbstractWaypoint theWaypoint;
    private int startX;
    private int startZ;
    private String message;
    private boolean chunkLoaded;
    private boolean playedSound;
    private static final ResourceLocation ftSound;
    private final float zoomBase;
    private final float mapScaleFactor;
    private float currentZoom;
    private float prevZoom;
    private static final float zoomInAmount = 0.5f;
    private static final float zoomInIncr = 0.008333334f;
    private boolean finishedZoomIn;
    private float mapSpeed;
    private float mapVelX;
    private float mapVelY;
    private static final float mapSpeedMax = 2.0f;
    private static final float mapSpeedIncr = 0.01f;
    private static final float mapAccel = 0.2f;
    private boolean reachedWP;
    private static final float wpReachedDistance = 1.0f;
    
    public LOTRGuiFastTravel(final LOTRAbstractWaypoint waypoint, final int x, final int z) {
        this.chunkLoaded = false;
        this.playedSound = false;
        this.finishedZoomIn = false;
        this.reachedWP = false;
        this.theWaypoint = waypoint;
        this.startX = x;
        this.startZ = z;
        this.message = LOTRSpeech.getRandomSpeech("fastTravel");
        this.mapGui = new LOTRGuiMap();
        (this.mapRenderer = new LOTRGuiRendererMap()).setSepia(true);
        this.mapRenderer.mapX = (float)LOTRWaypoint.worldToMapX(this.startX);
        this.mapRenderer.mapY = (float)LOTRWaypoint.worldToMapZ(this.startZ);
        final float dx = this.theWaypoint.getX() - this.mapRenderer.mapX;
        final float dy = this.theWaypoint.getY() - this.mapRenderer.mapY;
        final float distSq = dx * dx + dy * dy;
        final float dist = (float)Math.sqrt(distSq);
        this.mapScaleFactor = dist / 100.0f;
        this.zoomBase = -(float)(Math.log(this.mapScaleFactor * 0.3f) / Math.log(2.0));
        final float n = this.zoomBase + 0.5f;
        this.prevZoom = n;
        this.currentZoom = n;
    }
    
    @Override
    public void updateScreen() {
        if (!this.chunkLoaded && LOTRClientProxy.doesClientChunkExist((World)super.mc.theWorld, this.theWaypoint.getXCoord(), this.theWaypoint.getZCoord())) {
            this.chunkLoaded = true;
        }
        if (!this.playedSound) {
            super.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.func_147673_a(LOTRGuiFastTravel.ftSound));
            this.playedSound = true;
        }
        this.mapRenderer.updateTick();
        ++this.tickCounter;
        this.prevZoom = this.currentZoom;
        if (!this.reachedWP) {
            final float dx = this.theWaypoint.getX() - this.mapRenderer.mapX;
            final float dy = this.theWaypoint.getY() - this.mapRenderer.mapY;
            final float distSq = dx * dx + dy * dy;
            final float dist = (float)Math.sqrt(distSq);
            if (dist <= 1.0f * this.mapScaleFactor) {
                this.reachedWP = true;
                this.mapSpeed = 0.0f;
                this.mapVelX = 0.0f;
                this.mapVelY = 0.0f;
            }
            else {
                this.mapSpeed += 0.01f;
                this.mapSpeed = Math.min(this.mapSpeed, 2.0f);
                final float vXNew = dx / dist * this.mapSpeed;
                final float vYNew = dy / dist * this.mapSpeed;
                final float a = 0.2f;
                this.mapVelX += (vXNew - this.mapVelX) * a;
                this.mapVelY += (vYNew - this.mapVelY) * a;
            }
            final LOTRGuiRendererMap mapRenderer = this.mapRenderer;
            mapRenderer.mapX += this.mapVelX * this.mapScaleFactor;
            final LOTRGuiRendererMap mapRenderer2 = this.mapRenderer;
            mapRenderer2.mapY += this.mapVelY * this.mapScaleFactor;
            this.currentZoom -= 0.008333334f;
            this.currentZoom = Math.max(this.currentZoom, this.zoomBase);
        }
        else {
            this.currentZoom += 0.008333334f;
            this.currentZoom = Math.min(this.currentZoom, this.zoomBase + 0.5f);
            if (this.currentZoom >= this.zoomBase + 0.5f) {
                this.finishedZoomIn = true;
            }
        }
        if (this.chunkLoaded && this.reachedWP && this.finishedZoomIn) {
            super.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int i, final int j) {
        super.setWorldAndResolution(mc, i, j);
        this.mapGui.setWorldAndResolution(mc, i, j);
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (this.chunkLoaded && (i == 1 || i == super.mc.gameSettings.keyBindInventory.getKeyCode())) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        GL11.glEnable(3008);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        final float zoom = this.prevZoom + (this.currentZoom - this.prevZoom) * f;
        this.mapRenderer.zoomExp = zoom;
        this.mapRenderer.zoomStable = (float)Math.pow(2.0, this.zoomBase);
        this.mapRenderer.renderMap(this, this.mapGui, f);
        this.mapRenderer.renderVignettes(this, ((Gui)this).zLevel, 4);
        GL11.glEnable(3042);
        final String title = StatCollector.translateToLocalFormatted("lotr.fastTravel.travel", new Object[] { this.theWaypoint.getDisplayName() });
        final String titleExtra = (new String[] { "", ".", "..", "..." })[this.tickCounter / 10 % 4];
        final List messageLines = super.fontRendererObj.listFormattedStringToWidth(this.message, super.width - 100);
        final String skipText = StatCollector.translateToLocalFormatted("lotr.fastTravel.skip", new Object[] { GameSettings.getKeyDisplayString(super.mc.gameSettings.keyBindInventory.getKeyCode()) });
        final float boxAlpha = 0.5f;
        final int boxColor = (int)(boxAlpha * 255.0f) << 24 | 0x0;
        final int fh = super.fontRendererObj.FONT_HEIGHT;
        final int border = fh * 2;
        if (this.chunkLoaded) {
            drawRect(0, 0, super.width, 0 + border + fh * 3 + border, boxColor);
        }
        else {
            drawRect(0, 0, super.width, 0 + border + fh + border, boxColor);
        }
        int messageY = super.height - border - messageLines.size() * fh;
        drawRect(0, messageY - border, super.width, super.height, boxColor);
        GL11.glDisable(3042);
        super.fontRendererObj.drawStringWithShadow(title + titleExtra, super.width / 2 - super.fontRendererObj.getStringWidth(title) / 2, 0 + border, 16777215);
        for (final Object obj : messageLines) {
            final String s1 = (String)obj;
            this.drawCenteredString(super.fontRendererObj, s1, super.width / 2, messageY, 16777215);
            messageY += fh;
        }
        if (this.chunkLoaded) {
            final float skipAlpha = LOTRFunctions.triangleWave(this.tickCounter + f, 0.3f, 1.0f, 80.0f);
            final int skipColor = 0xFFFFFF | LOTRClientProxy.getAlphaInt(skipAlpha) << 24;
            GL11.glEnable(3042);
            super.fontRendererObj.drawString(skipText, super.width / 2 - super.fontRendererObj.getStringWidth(skipText) / 2, 0 + border + fh * 2, skipColor);
        }
        GL11.glDisable(3042);
        super.drawScreen(i, j, f);
    }
    
    static {
        ftSound = new ResourceLocation("lotr:event.fastTravel");
    }
}
