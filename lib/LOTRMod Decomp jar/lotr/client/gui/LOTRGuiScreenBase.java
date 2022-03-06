// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.GuiScreen;

public abstract class LOTRGuiScreenBase extends GuiScreen
{
    public void updateScreen() {
        super.updateScreen();
        if (!super.mc.thePlayer.isEntityAlive() || ((Entity)super.mc.thePlayer).isDead) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    protected void keyTyped(final char c, final int i) {
        if (i == 1 || i == super.mc.gameSettings.keyBindInventory.getKeyCode()) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    protected void drawCenteredString(final String s, final int x, final int y, final int c) {
        super.fontRendererObj.drawString(s, x - super.fontRendererObj.getStringWidth(s) / 2, y, c);
    }
    
    public void drawTexturedModalRect(final int x, final int y, final int u, final int v, final int width, final int height) {
        this.drawTexturedModalRect(x, y, u, v, width, height, 256);
    }
    
    public void drawTexturedModalRect(final int x, final int y, final int u, final int v, final int width, final int height, final int imageWidth) {
        drawTexturedModalRectFloat(x, y, u, v, width, height, imageWidth, ((Gui)this).zLevel);
    }
    
    public void drawTexturedModalRectFloat(final float x, final float y, final int u, final int v, final float width, final float height) {
        this.drawTexturedModalRectFloat(x, y, u, v, width, height, 256);
    }
    
    public void drawTexturedModalRectFloat(final float x, final float y, final int u, final int v, final float width, final float height, final int imageWidth) {
        drawTexturedModalRectFloat(x, y, u, v, width, height, imageWidth, ((Gui)this).zLevel);
    }
    
    public static void drawTexturedModalRectFloat(final double x, final double y, final double u, final double v, final double width, final double height, final int imageWidth, final float z) {
        final float f = 1.0f / imageWidth;
        final float f2 = 1.0f / imageWidth;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0.0, y + height, (double)z, (u + 0.0) * f, (v + height) * f2);
        tessellator.addVertexWithUV(x + width, y + height, (double)z, (u + width) * f, (v + height) * f2);
        tessellator.addVertexWithUV(x + width, y + 0.0, (double)z, (u + width) * f, (v + 0.0) * f2);
        tessellator.addVertexWithUV(x + 0.0, y + 0.0, (double)z, (u + 0.0) * f, (v + 0.0) * f2);
        tessellator.draw();
    }
    
    public static void drawFloatRect(float x0, float y0, float x1, float y1, final int color) {
        if (x0 < x1) {
            final float temp = x0;
            x0 = x1;
            x1 = temp;
        }
        if (y0 < y1) {
            final float temp = y0;
            y0 = y1;
            y1 = temp;
        }
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(r, g, b, alpha);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double)x0, (double)y1, 0.0);
        tessellator.addVertex((double)x1, (double)y1, 0.0);
        tessellator.addVertex((double)x1, (double)y0, 0.0);
        tessellator.addVertex((double)x0, (double)y0, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
}
