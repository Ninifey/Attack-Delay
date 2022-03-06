// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSelectTitle;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import java.awt.Color;
import lotr.client.LOTRReflectionClient;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.util.StatCollector;
import java.util.HashMap;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Map;
import java.util.List;
import lotr.common.LOTRTitle;

public class LOTRGuiTitles extends LOTRGuiMenuBase
{
    private LOTRTitle.PlayerTitle currentTitle;
    private List<LOTRTitle> displayedTitles;
    private static final int maxDisplayedTitles = 12;
    private Map<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> displayedTitleInfo;
    private LOTRTitle selectedTitle;
    private EnumChatFormatting selectedColor;
    private int colorBoxWidth;
    private int colorBoxGap;
    private Map<EnumChatFormatting, Pair<Integer, Integer>> displayedColorBoxes;
    private GuiButton selectButton;
    private GuiButton removeButton;
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasMouseDown;
    private int scrollBarWidth;
    private int scrollBarHeight;
    private int scrollBarX;
    private int scrollBarY;
    private int scrollWidgetWidth;
    private int scrollWidgetHeight;
    
    public LOTRGuiTitles() {
        this.displayedTitles = new ArrayList<LOTRTitle>();
        this.displayedTitleInfo = new HashMap<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>>();
        this.selectedColor = EnumChatFormatting.WHITE;
        this.colorBoxWidth = 8;
        this.colorBoxGap = 4;
        this.displayedColorBoxes = new HashMap<EnumChatFormatting, Pair<Integer, Integer>>();
        this.currentScroll = 0.0f;
        this.isScrolling = false;
        this.scrollBarWidth = 11;
        this.scrollBarHeight = 144;
        this.scrollBarX = 197 - (this.scrollBarWidth - 1) / 2;
        this.scrollBarY = 30;
        this.scrollWidgetWidth = 11;
        this.scrollWidgetHeight = 8;
    }
    
    @Override
    public void initGui() {
        super.xSize = 256;
        super.initGui();
        super.buttonList.add(this.selectButton = new GuiButton(0, super.guiLeft + super.xSize / 2 - 10 - 80, super.guiTop + 220, 80, 20, StatCollector.translateToLocal("lotr.gui.titles.select")));
        super.buttonList.add(this.removeButton = new GuiButton(1, super.guiLeft + super.xSize / 2 + 10, super.guiTop + 220, 80, 20, StatCollector.translateToLocal("lotr.gui.titles.remove")));
        this.updateScreen();
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.currentTitle = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getPlayerTitle();
        this.displayedTitles.clear();
        final List availableTitles = new ArrayList();
        final List unavailableTitles = new ArrayList();
        for (final LOTRTitle title : LOTRTitle.allTitles) {
            if (title.canPlayerUse((EntityPlayer)super.mc.thePlayer)) {
                availableTitles.add(title);
            }
            else {
                if (!title.canDisplay((EntityPlayer)super.mc.thePlayer)) {
                    continue;
                }
                unavailableTitles.add(title);
            }
        }
        Collections.sort((List<Comparable>)availableTitles);
        Collections.sort((List<Comparable>)unavailableTitles);
        this.displayedTitles.addAll(availableTitles);
        this.displayedTitles.add(null);
        this.displayedTitles.addAll(unavailableTitles);
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.setupScrollBar(i, j);
        final String s = StatCollector.translateToLocal("lotr.gui.titles.title");
        this.drawCenteredString(s, super.guiLeft + super.xSize / 2, super.guiTop - 30, 16777215);
        String titleName = (this.currentTitle == null) ? StatCollector.translateToLocal("lotr.gui.titles.currentTitle.none") : this.currentTitle.getTitle().getDisplayName();
        final EnumChatFormatting currentColor = (this.currentTitle == null) ? EnumChatFormatting.WHITE : this.currentTitle.getColor();
        titleName = currentColor + titleName + EnumChatFormatting.RESET;
        this.drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.titles.currentTitle", new Object[] { titleName }), super.guiLeft + super.xSize / 2, super.guiTop, 16777215);
        this.displayedTitleInfo.clear();
        int titleX = super.guiLeft + super.xSize / 2;
        int titleY = super.guiTop + 30;
        final int yIncrement = 12;
        this.drawVerticalLine(titleX - 70, titleY - 1, titleY + yIncrement * 12, -1711276033);
        this.drawVerticalLine(titleX + 70 - 1, titleY - 1, titleY + yIncrement * 12, -1711276033);
        final int size = this.displayedTitles.size();
        int min = 0 + Math.round(this.currentScroll * (size - 12));
        int max = 11 + Math.round(this.currentScroll * (size - 12));
        min = Math.max(min, 0);
        max = Math.min(max, size - 1);
        for (int index = min; index <= max; ++index) {
            final LOTRTitle title = this.displayedTitles.get(index);
            final boolean isCurrentTitle = this.currentTitle != null && this.currentTitle.getTitle() == title;
            String name;
            if (title != null) {
                name = title.getDisplayName();
                if (isCurrentTitle) {
                    name = "[" + name + "]";
                    name = this.currentTitle.getColor() + name;
                }
            }
            else {
                name = "---";
            }
            final int nameWidth = super.fontRendererObj.getStringWidth(name);
            final int nameHeight = super.mc.fontRenderer.FONT_HEIGHT;
            final int nameXMin = titleX - nameWidth / 2;
            final int nameXMax = titleX + nameWidth / 2;
            final int nameYMin = titleY;
            final int nameYMax = titleY + nameHeight;
            final boolean mouseOver = i >= nameXMin && i < nameXMax && j >= nameYMin && j < nameYMax;
            if (title != null) {
                this.displayedTitleInfo.put(title, (Pair<Boolean, Pair<Integer, Integer>>)Pair.of((Object)mouseOver, (Object)Pair.of((Object)titleX, (Object)titleY)));
            }
            int textColor;
            if (title != null) {
                if (title.canPlayerUse((EntityPlayer)super.mc.thePlayer)) {
                    textColor = (mouseOver ? 16777120 : 16777215);
                }
                else {
                    textColor = (mouseOver ? 12303291 : 7829367);
                }
            }
            else {
                textColor = 7829367;
            }
            this.drawCenteredString(name, titleX, titleY, textColor);
            titleY += yIncrement;
        }
        this.displayedColorBoxes.clear();
        if (this.selectedTitle != null) {
            final String title2 = this.selectedColor + this.selectedTitle.getDisplayName();
            this.drawCenteredString(title2, super.guiLeft + super.xSize / 2, super.guiTop + 185, 16777215);
            final List<EnumChatFormatting> colorCodes = new ArrayList<EnumChatFormatting>();
            for (final EnumChatFormatting ecf : EnumChatFormatting.values()) {
                if (ecf.Checks()) {
                    colorCodes.add(ecf);
                }
            }
            int colorX = super.guiLeft + super.xSize / 2 - (this.colorBoxWidth * colorCodes.size() + this.colorBoxGap * (colorCodes.size() - 1)) / 2;
            final int colorY = super.guiTop + 200;
            for (final EnumChatFormatting code : colorCodes) {
                final int color = LOTRReflectionClient.getFormattingColor(code);
                final float[] rgb = new Color(color).getColorComponents(null);
                GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
                final boolean mouseOver2 = i >= colorX && i < colorX + this.colorBoxWidth && j >= colorY && j < colorY + this.colorBoxWidth;
                GL11.glDisable(3553);
                this.drawTexturedModalRect(colorX, colorY + (mouseOver2 ? -1 : 0), 0, 0, this.colorBoxWidth, this.colorBoxWidth);
                GL11.glEnable(3553);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.displayedColorBoxes.put(code, (Pair<Integer, Integer>)Pair.of((Object)colorX, (Object)colorY));
                colorX += this.colorBoxWidth + this.colorBoxGap;
            }
        }
        if (this.displayedTitles.size() > 12) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final int scroll = (int)(this.currentScroll * (this.scrollBarHeight - this.scrollWidgetHeight));
            final int x1 = super.guiLeft + this.scrollBarX;
            final int y1 = super.guiTop + this.scrollBarY + scroll;
            final int x2 = x1 + this.scrollWidgetWidth;
            final int y2 = y1 + this.scrollWidgetHeight;
            drawRect(x1, y1, x2, y2, -1426063361);
        }
        this.selectButton.enabled = (this.selectedTitle != null);
        this.removeButton.enabled = (this.currentTitle != null);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.drawScreen(i, j, f);
        for (final Map.Entry<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : this.displayedTitleInfo.entrySet()) {
            final LOTRTitle title3 = entry.getKey();
            final String desc = title3.getDescription((EntityPlayer)super.mc.thePlayer);
            titleX = (int)((Pair)entry.getValue().getRight()).getLeft();
            titleY = (int)((Pair)entry.getValue().getRight()).getRight();
            final boolean mouseOver3 = (boolean)entry.getValue().getLeft();
            if (mouseOver3) {
                final int stringWidth = 200;
                final List titleLines = super.fontRendererObj.listFormattedStringToWidth(desc, stringWidth);
                final int offset = 10;
                final int x3 = i + offset;
                final int y3 = j + offset;
                this.func_146283_a(titleLines, x3, y3);
                GL11.glDisable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    private void setupScrollBar(final int i, final int j) {
        final boolean isMouseDown = Mouse.isButtonDown(0);
        final int i2 = super.guiLeft + this.scrollBarX;
        final int j2 = super.guiTop + this.scrollBarY;
        final int i3 = i2 + this.scrollBarWidth;
        final int j3 = j2 + this.scrollBarHeight;
        if (!this.wasMouseDown && isMouseDown && i >= i2 && j >= j2 && i < i3 && j < j3) {
            this.isScrolling = true;
        }
        if (!isMouseDown) {
            this.isScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (j - j2 - this.scrollWidgetHeight / 2.0f) / (j3 - j2 - (float)this.scrollWidgetHeight);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.selectButton && (this.currentTitle == null || this.selectedTitle != this.currentTitle.getTitle() || this.selectedColor != this.currentTitle.getColor())) {
                final LOTRPacketSelectTitle packet = new LOTRPacketSelectTitle(new LOTRTitle.PlayerTitle(this.selectedTitle, this.selectedColor));
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
            else if (button == this.removeButton) {
                final LOTRPacketSelectTitle packet = new LOTRPacketSelectTitle(null);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
    
    protected void mouseClicked(final int i, final int j, final int mouse) {
        if (mouse == 0) {
            for (final Map.Entry<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : this.displayedTitleInfo.entrySet()) {
                final LOTRTitle title = entry.getKey();
                final boolean mouseOver = (boolean)entry.getValue().getLeft();
                if (mouseOver && title.canPlayerUse((EntityPlayer)super.mc.thePlayer)) {
                    this.selectedTitle = title;
                    this.selectedColor = EnumChatFormatting.WHITE;
                    return;
                }
            }
            if (!this.displayedColorBoxes.isEmpty()) {
                for (final Map.Entry<EnumChatFormatting, Pair<Integer, Integer>> entry2 : this.displayedColorBoxes.entrySet()) {
                    final EnumChatFormatting color = entry2.getKey();
                    final int colorX = (int)entry2.getValue().getLeft();
                    final int colorY = (int)entry2.getValue().getRight();
                    if (i >= colorX && i < colorX + this.colorBoxWidth && j >= colorY && j < colorY + this.colorBoxWidth) {
                        this.selectedColor = color;
                        break;
                    }
                }
            }
        }
        super.mouseClicked(i, j, mouse);
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0) {
            i = Integer.signum(i);
            final int j = this.displayedTitles.size() - 12;
            this.currentScroll -= i / (float)j;
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
        }
    }
}
