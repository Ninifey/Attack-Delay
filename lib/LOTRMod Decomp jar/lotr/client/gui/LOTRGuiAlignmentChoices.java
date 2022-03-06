// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketAlignmentChoices;
import lotr.common.LOTRPlayerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.renderer.Tessellator;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.client.LOTRClientProxy;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import lotr.common.fac.LOTRFaction;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiAlignmentChoices extends LOTRGuiScreenBase
{
    private int xSize;
    private int ySize;
    private int guiLeft;
    private int guiTop;
    private int page;
    private GuiButton buttonConfirm;
    private Map<LOTRFaction, LOTRGuiButtonRedBook> facButtons;
    private Map<LOTRGuiButtonRedBook, LOTRFaction> buttonFacs;
    private Set<LOTRFaction> setZeroFacs;
    private static final int colorConflict = -62464;
    private static final int colorSelected = -1;
    
    public LOTRGuiAlignmentChoices() {
        this.xSize = 430;
        this.ySize = 250;
        this.page = 0;
        this.facButtons = new HashMap<LOTRFaction, LOTRGuiButtonRedBook>();
        this.buttonFacs = new HashMap<LOTRGuiButtonRedBook, LOTRFaction>();
        this.setZeroFacs = new HashSet<LOTRFaction>();
    }
    
    public void initGui() {
        super.initGui();
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        super.buttonList.add(this.buttonConfirm = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 100, this.guiTop + this.ySize - 30, 200, 20, "BUTTON"));
        for (final LOTRFaction fac : LOTRFaction.getPlayableAlignmentFactions()) {
            final LOTRGuiButtonRedBook button = new LOTRGuiButtonRedBook(0, 0, 0, 80, 20, "");
            this.facButtons.put(fac, button);
            this.buttonFacs.put(button, fac);
            super.buttonList.add(button);
            final LOTRGuiButtonRedBook lotrGuiButtonRedBook = button;
            final LOTRGuiButtonRedBook lotrGuiButtonRedBook2 = button;
            final boolean b = false;
            lotrGuiButtonRedBook2.enabled = b;
            lotrGuiButtonRedBook.field_146125_m = b;
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        drawRect(this.guiLeft, this.guiTop, this.guiLeft + this.xSize, this.guiTop + this.ySize, -5756117);
        drawRect(this.guiLeft + 2, this.guiTop + 2, this.guiLeft + this.xSize - 2, this.guiTop + this.ySize - 2, -1847889);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
        final int warnIconSize = 32;
        this.drawTexturedModalRect(this.guiLeft - warnIconSize, this.guiTop, 16, 128, warnIconSize, warnIconSize);
        this.drawTexturedModalRect(this.guiLeft + this.xSize, this.guiTop, 16, 128, warnIconSize, warnIconSize);
        final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer);
        final int textColor = 8019267;
        final int border = 7;
        final int lineWidth = this.xSize - border * 2;
        final int x = this.guiLeft + border;
        int y = this.guiTop + border;
        if (this.page == 0) {
            String s = "Hello! You are reading this because you earned alignment before Update 35.";
            super.fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
            y += super.fontRendererObj.FONT_HEIGHT * super.fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
            y += super.fontRendererObj.FONT_HEIGHT;
            s = "This update introduces 'Enemy Alignment Draining'. If you have + alignment with two Mortal Enemy factions (more severe than Enemy), both alignments will slowly drain over time until one reaches 0.";
            super.fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
            y += super.fontRendererObj.FONT_HEIGHT * super.fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
            y += super.fontRendererObj.FONT_HEIGHT;
            s = "You can still hold + alignment with Mortal Enemies in the short term if you work quickly. But long-term public friendship with Gondor and Mordor together is not in the spirit of Tolkien's Middle-earth.";
            super.fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
            y += super.fontRendererObj.FONT_HEIGHT * super.fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
            y += super.fontRendererObj.FONT_HEIGHT;
            s = "Because you have played before, you have the option to set any unwanted alignments to zero immediately, to prevent draining high alignment from factions you care about. This will also help if you want to Pledge to a faction.";
            super.fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
            y += super.fontRendererObj.FONT_HEIGHT * super.fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
            y += super.fontRendererObj.FONT_HEIGHT;
            s = "Note that if you are a server admin or playing in singleplayer you can toggle this feature in the LOTR mod config. However, players who wish to Pledge will still need to reduce Mortal Enemy alignments to zero.";
            super.fontRendererObj.drawSplitString(EnumChatFormatting.ITALIC + s, x, y, lineWidth, textColor);
            y += super.fontRendererObj.FONT_HEIGHT * super.fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
            this.buttonConfirm.displayString = "View your alignments";
        }
        else if (this.page == 1) {
            String s = "Choose which alignments to set to zero. You can choose as many or as few as you like, but you can only choose once. Alignments which will drain due to a conflict are in RED - this will update as you select unwanted factions.";
            super.fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
            y += super.fontRendererObj.FONT_HEIGHT * super.fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
            y += super.fontRendererObj.FONT_HEIGHT;
            s = "If you are hoping to Pledge to a faction, you will need to have 0 or - alignment with all of its Mortal Enemies.";
            super.fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
            y += super.fontRendererObj.FONT_HEIGHT * super.fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
            y += super.fontRendererObj.FONT_HEIGHT;
            int buttonX = this.guiLeft + border;
            int buttonY = y;
            for (final LOTRFaction fac : LOTRFaction.getPlayableAlignmentFactions()) {
                final LOTRGuiButtonRedBook button = this.facButtons.get(fac);
                button.field_146125_m = true;
                button.enabled = false;
                button.displayString = "";
                button.field_146128_h = buttonX;
                button.field_146129_i = buttonY;
                buttonX += button.field_146120_f + 4;
                if (buttonX >= this.guiLeft + this.xSize - border) {
                    buttonX = this.guiLeft + border;
                    buttonY += 24;
                }
                final float align = pd.getAlignment(fac);
                final String facName = fac.factionName();
                final String alignS = LOTRAlignmentValues.formatAlignForDisplay(align);
                String status = "Not draining";
                button.enabled = false;
                if (align > 0.0f) {
                    final boolean isDraining = this.isFactionConflicting(pd, fac, false);
                    final boolean willDrain = this.isFactionConflicting(pd, fac, true);
                    if (isDraining) {
                        if (this.setZeroFacs.contains(fac)) {
                            status = "Setting to zero";
                            button.enabled = true;
                            drawRect(button.field_146128_h - 1, button.field_146129_i - 1, button.field_146128_h + button.field_146120_f + 1, button.field_146129_i + button.field_146121_g + 1, -1);
                        }
                        else if (willDrain) {
                            status = "Draining";
                            button.enabled = true;
                            drawRect(button.field_146128_h - 1, button.field_146129_i - 1, button.field_146128_h + button.field_146120_f + 1, button.field_146129_i + button.field_146121_g + 1, -62464);
                        }
                        else {
                            status = "Will not drain after CONFIRM";
                            button.enabled = false;
                        }
                    }
                }
                final float buttonTextScale = 0.5f;
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 0.0f, 100.0f);
                GL11.glScalef(buttonTextScale, buttonTextScale, 1.0f);
                final int buttonTextX = (int)((button.field_146128_h + button.field_146120_f / 2) / buttonTextScale);
                int buttonTextY = (int)(button.field_146129_i / buttonTextScale) + 4;
                this.drawCenteredString(facName, buttonTextX, buttonTextY, textColor);
                buttonTextY += super.fontRendererObj.FONT_HEIGHT;
                this.drawCenteredString(alignS, buttonTextX, buttonTextY, textColor);
                buttonTextY += super.fontRendererObj.FONT_HEIGHT;
                this.drawCenteredString(status, buttonTextX, buttonTextY, textColor);
                GL11.glPopMatrix();
                if (button.func_146115_a() && align > 0.0f && !this.setZeroFacs.contains(fac) && this.isFactionConflicting(pd, fac, true)) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0f, 0.0f, 100.0f);
                    for (final LOTRFaction otherFac : LOTRFaction.getPlayableAlignmentFactions()) {
                        if (fac != otherFac && !this.setZeroFacs.contains(otherFac) && pd.doFactionsDrain(fac, otherFac) && pd.getAlignment(otherFac) > 0.0f) {
                            final LOTRGuiButtonRedBook otherButton = this.facButtons.get(otherFac);
                            final int x2 = button.field_146128_h + button.field_146120_f / 2;
                            final int x3 = otherButton.field_146128_h + otherButton.field_146120_f / 2;
                            final int y2 = button.field_146129_i + button.field_146121_g / 2;
                            final int y3 = otherButton.field_146129_i + otherButton.field_146121_g / 2;
                            GL11.glDisable(3553);
                            final Tessellator tess = Tessellator.instance;
                            tess.startDrawing(1);
                            GL11.glPushAttrib(2849);
                            GL11.glLineWidth(4.0f);
                            tess.setColorOpaque_I(-62464);
                            tess.addVertex((double)x2, (double)y2, 0.0);
                            tess.addVertex((double)x3, (double)y3, 0.0);
                            tess.draw();
                            GL11.glPopAttrib();
                            GL11.glEnable(3553);
                        }
                    }
                    GL11.glPopMatrix();
                }
            }
            s = "If you do not want to choose now you can close this screen with '" + GameSettings.getKeyDisplayString(super.mc.gameSettings.keyBindInventory.getKeyCode()) + "' and it will appear again when you log in. Remember - you can only choose once.";
            y = this.buttonConfirm.field_146129_i - super.fontRendererObj.FONT_HEIGHT * (super.fontRendererObj.listFormattedStringToWidth(s, lineWidth).size() + 1);
            super.fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
            this.buttonConfirm.displayString = "CONFIRM - set " + this.setZeroFacs.size() + " alignments to zero";
        }
        super.drawScreen(i, j, f);
    }
    
    private boolean isFactionConflicting(final LOTRPlayerData pd, final LOTRFaction fac, final boolean accountForSelection) {
        for (final LOTRFaction otherFac : LOTRFaction.getPlayableAlignmentFactions()) {
            if (fac != otherFac && (!accountForSelection || !this.setZeroFacs.contains(otherFac)) && pd.doFactionsDrain(fac, otherFac) && pd.getAlignment(otherFac) > 0.0f) {
                return true;
            }
        }
        return false;
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonConfirm) {
                if (this.page == 0) {
                    this.page = 1;
                }
                else if (this.page == 1) {
                    final LOTRPacketAlignmentChoices packet = new LOTRPacketAlignmentChoices(this.setZeroFacs);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
                    super.mc.thePlayer.closeScreen();
                }
            }
            else if (this.buttonFacs.containsKey(button)) {
                final LOTRFaction fac = this.buttonFacs.get(button);
                if (this.isFactionConflicting(LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer), fac, false)) {
                    if (this.setZeroFacs.contains(fac)) {
                        this.setZeroFacs.remove(fac);
                    }
                    else {
                        this.setZeroFacs.add(fac);
                    }
                }
            }
        }
        super.actionPerformed(button);
    }
}
