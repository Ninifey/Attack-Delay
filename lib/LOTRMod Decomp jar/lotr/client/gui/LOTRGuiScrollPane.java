// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import java.util.List;

public class LOTRGuiScrollPane
{
    private final int scrollWidgetWidth;
    private final int scrollWidgetHeight;
    private int barColor;
    private int widgetColor;
    public int scrollBarX0;
    public int paneX0;
    public int paneY0;
    public int paneY1;
    public boolean hasScrollBar;
    public float currentScroll;
    public boolean isScrolling;
    public boolean mouseOver;
    private boolean wasMouseDown;
    
    public LOTRGuiScrollPane(final int ww, final int wh) {
        this.scrollWidgetWidth = ww;
        this.scrollWidgetHeight = wh;
        this.barColor = -1711276033;
        this.widgetColor = -1426063361;
    }
    
    public LOTRGuiScrollPane setColors(int c1, int c2) {
        final int alphaMask = -16777216;
        if ((c1 & alphaMask) == 0x0) {
            c1 |= alphaMask;
        }
        if ((c2 & alphaMask) == 0x0) {
            c2 |= alphaMask;
        }
        this.barColor = c1;
        this.widgetColor = c2;
        return this;
    }
    
    public int[] getMinMaxIndices(final List list, final int displayed) {
        final int size = list.size();
        int min = 0 + Math.round(this.currentScroll * (size - displayed));
        int max = displayed - 1 + Math.round(this.currentScroll * (size - displayed));
        min = Math.max(min, 0);
        max = Math.min(max, size - 1);
        return new int[] { min, max };
    }
    
    public void drawScrollBar() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int x0 = this.scrollBarX0 + this.scrollWidgetWidth / 2;
        int y0 = this.paneY0;
        int y2 = this.paneY1;
        Gui.drawRect(x0, y0, x0 + 1, y2, this.barColor);
        final int scroll = (int)(this.currentScroll * (y2 - y0 - this.scrollWidgetHeight));
        x0 = this.scrollBarX0;
        final int x2 = x0 + this.scrollWidgetWidth;
        y0 += scroll;
        y2 = y0 + this.scrollWidgetHeight;
        Gui.drawRect(x0, y0, x2, y2, this.widgetColor);
    }
    
    public void mouseDragScroll(final int i, final int j) {
        final boolean isMouseDown = Mouse.isButtonDown(0);
        if (!this.hasScrollBar) {
            this.resetScroll();
        }
        else {
            int x0 = this.paneX0;
            final int x2 = this.scrollBarX0 + this.scrollWidgetWidth;
            final int y0 = this.paneY0;
            final int y2 = this.paneY1;
            this.mouseOver = (i >= x0 && j >= y0 && i < x2 && j < y2);
            x0 = this.scrollBarX0;
            final boolean mouseOverScroll = i >= x0 && j >= y0 && i < x2 && j < y2;
            if (!this.wasMouseDown && isMouseDown && mouseOverScroll) {
                this.isScrolling = true;
            }
            if (!isMouseDown) {
                this.isScrolling = false;
            }
            if (this.isScrolling) {
                this.currentScroll = (j - y0 - this.scrollWidgetHeight / 2.0f) / (y2 - y0 - (float)this.scrollWidgetHeight);
                this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
            }
        }
        this.wasMouseDown = isMouseDown;
    }
    
    public void resetScroll() {
        this.currentScroll = 0.0f;
        this.isScrolling = false;
    }
    
    public void mouseWheelScroll(final int delta, final int size) {
        this.currentScroll -= delta / (float)size;
        this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
    }
}
