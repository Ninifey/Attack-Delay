// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import java.util.Iterator;
import java.util.List;
import lotr.client.LOTRTickHandlerClient;
import java.util.Collection;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiButton;
import lotr.common.LOTRGuiMessageTypes;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiMessage extends LOTRGuiScreenBase
{
    private static ResourceLocation guiTexture;
    private LOTRGuiMessageTypes type;
    public int xSize;
    public int ySize;
    private int border;
    private int guiLeft;
    private int guiTop;
    private GuiButton buttonDismiss;
    private int buttonTimer;
    
    public LOTRGuiMessage(final LOTRGuiMessageTypes t) {
        this.xSize = 240;
        this.ySize = 160;
        this.border = 10;
        this.buttonTimer = 60;
        this.type = t;
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        super.buttonList.add(this.buttonDismiss = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 40, this.guiTop + this.ySize + 20, 80, 20, StatCollector.translateToLocal("lotr.gui.message.dismiss")));
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        super.mc.getTextureManager().bindTexture(LOTRGuiMessage.guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        final String msg = this.type.getMessage();
        final int pageWidth = this.xSize - this.border * 2;
        final String[] splitNewline = msg.split(Pattern.quote("\\n"));
        final List<String> msgLines = new ArrayList<String>();
        for (final String line : splitNewline) {
            msgLines.addAll(super.fontRendererObj.listFormattedStringToWidth(line, pageWidth));
        }
        final int x = this.guiLeft + this.border;
        int y = this.guiTop + this.border;
        for (final String line : msgLines) {
            super.fontRendererObj.drawString(line, x, y, 8019267);
            y += super.fontRendererObj.FONT_HEIGHT;
        }
        final String s = StatCollector.translateToLocal("lotr.gui.message.notDisplayedAgain");
        this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop + this.ySize - this.border / 2 - super.fontRendererObj.FONT_HEIGHT, 9666921);
        if (this.type == LOTRGuiMessageTypes.ALIGN_DRAIN) {
            final int numIcons = 3;
            final int iconGap = 40;
            for (int l = 0; l < numIcons; ++l) {
                int iconX = this.guiLeft + this.xSize / 2;
                iconX -= (numIcons - 1) * iconGap / 2;
                iconX += l * iconGap - 8;
                final int iconY = this.guiTop + this.border + 14;
                final int num = l + 1;
                LOTRTickHandlerClient.renderAlignmentDrain(super.mc, iconX, iconY, num);
            }
        }
        if (this.buttonTimer > 0) {
            --this.buttonTimer;
        }
        this.buttonDismiss.enabled = (this.buttonTimer == 0);
        super.drawScreen(i, j, f);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled && button == this.buttonDismiss) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
    }
    
    static {
        LOTRGuiMessage.guiTexture = new ResourceLocation("lotr:gui/message.png");
    }
}
