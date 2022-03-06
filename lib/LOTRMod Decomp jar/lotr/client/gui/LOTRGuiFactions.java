// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import lotr.common.network.LOTRPacketPledgeSet;
import net.minecraft.client.gui.GuiScreen;
import com.google.common.math.IntMath;
import org.lwjgl.input.Mouse;
import java.util.Iterator;
import lotr.common.fac.LOTRFactionData;
import lotr.common.fac.LOTRMapRegion;
import net.minecraft.client.renderer.Tessellator;
import java.util.Collections;
import java.util.Comparator;
import net.minecraft.util.MathHelper;
import java.util.Collection;
import java.util.ArrayList;
import lotr.common.fac.LOTRFactionRelations;
import lotr.client.LOTRTextures;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.LOTRConfig;
import lotr.client.LOTRTickHandlerClient;
import org.lwjgl.opengl.GL11;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.util.StatCollector;
import lotr.client.LOTRClientProxy;
import java.util.HashMap;
import lotr.common.LOTRPlayerData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketClientMQEvent;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import lotr.common.fac.LOTRFaction;
import java.util.List;
import lotr.common.LOTRDimension;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiFactions extends LOTRGuiMenuBase
{
    public static final ResourceLocation factionsTexture;
    public static final ResourceLocation factionsTextureFull;
    private static LOTRDimension currentDimension;
    private static LOTRDimension prevDimension;
    private static LOTRDimension.DimensionRegion currentRegion;
    private static LOTRDimension.DimensionRegion prevRegion;
    private static List<LOTRFaction> currentFactionList;
    private int currentFactionIndex;
    private int prevFactionIndex;
    private LOTRFaction currentFaction;
    private static final int maxAlignmentsDisplayed = 1;
    private static Page currentPage;
    private int pageY;
    private int pageWidth;
    private int pageHeight;
    private int pageBorderLeft;
    private int pageBorderTop;
    private int pageMapX;
    private int pageMapY;
    private int pageMapSize;
    private LOTRGuiMap mapDrawGui;
    private GuiButton buttonRegions;
    private GuiButton buttonPagePrev;
    private GuiButton buttonPageNext;
    private GuiButton buttonFactionMap;
    private LOTRGuiButtonPledge buttonPledge;
    private LOTRGuiButtonPledge buttonPledgeConfirm;
    private LOTRGuiButtonPledge buttonPledgeRevoke;
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasMouseDown;
    private int scrollBarWidth;
    private int scrollBarHeight;
    private int scrollBarX;
    private int scrollBarY;
    private int scrollBarBorder;
    private int scrollWidgetWidth;
    private int scrollWidgetHeight;
    private LOTRGuiScrollPane scrollPaneAlliesEnemies;
    private int scrollAlliesEnemiesX;
    private static final int maxDisplayedAlliesEnemies = 10;
    private int numDisplayedAlliesEnemies;
    private List currentAlliesEnemies;
    private boolean isOtherPlayer;
    private String otherPlayerName;
    private Map<LOTRFaction, Float> playerAlignmentMap;
    private boolean isPledging;
    private boolean isUnpledging;
    
    public LOTRGuiFactions() {
        this.currentFactionIndex = 0;
        this.prevFactionIndex = 0;
        this.pageY = 46;
        this.pageWidth = 256;
        this.pageHeight = 128;
        this.pageBorderLeft = 16;
        this.pageBorderTop = 12;
        this.pageMapX = 159;
        this.pageMapY = 22;
        this.pageMapSize = 80;
        super.xSize = this.pageWidth;
        this.currentScroll = 0.0f;
        this.isScrolling = false;
        this.scrollBarWidth = 240;
        this.scrollBarHeight = 14;
        this.scrollBarX = super.xSize / 2 - this.scrollBarWidth / 2;
        this.scrollBarY = 180;
        this.scrollBarBorder = 1;
        this.scrollWidgetWidth = 17;
        this.scrollWidgetHeight = 12;
        this.scrollPaneAlliesEnemies = new LOTRGuiScrollPane(7, 7).setColors(5521198, 8019267);
        this.scrollAlliesEnemiesX = 138;
        this.isOtherPlayer = false;
        this.isPledging = false;
        this.isUnpledging = false;
        this.mapDrawGui = new LOTRGuiMap();
    }
    
    public void setOtherPlayer(final String name, final Map<LOTRFaction, Float> alignments) {
        this.isOtherPlayer = true;
        this.otherPlayerName = name;
        this.playerAlignmentMap = alignments;
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int i, final int j) {
        super.setWorldAndResolution(mc, i, j);
        this.mapDrawGui.setWorldAndResolution(mc, i, j);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (this.isOtherPlayer) {
            super.buttonList.remove(super.buttonMenuReturn);
        }
        super.buttonList.add(this.buttonRegions = new LOTRGuiButtonRedBook(0, super.guiLeft + super.xSize / 2 - 60, super.guiTop + 200, 120, 20, ""));
        super.buttonList.add(this.buttonPagePrev = new LOTRGuiButtonFactionsPage(1, super.guiLeft + 8, super.guiTop + this.pageY + 104, false));
        super.buttonList.add(this.buttonPageNext = new LOTRGuiButtonFactionsPage(2, super.guiLeft + 232, super.guiTop + this.pageY + 104, true));
        super.buttonList.add(this.buttonFactionMap = new LOTRGuiButtonFactionsMap(3, super.guiLeft + this.pageMapX + this.pageMapSize - 3 - 8, super.guiTop + this.pageY + this.pageMapY + 3));
        super.buttonList.add(this.buttonPledge = new LOTRGuiButtonPledge(this, 4, super.guiLeft + 14, super.guiTop + this.pageY + this.pageHeight - 42, ""));
        super.buttonList.add(this.buttonPledgeConfirm = new LOTRGuiButtonPledge(this, 5, super.guiLeft + this.pageWidth / 2 - 16, super.guiTop + this.pageY + this.pageHeight - 50, ""));
        super.buttonList.add(this.buttonPledgeRevoke = new LOTRGuiButtonPledge(this, 6, super.guiLeft + this.pageWidth / 2 - 16, super.guiTop + this.pageY + this.pageHeight - 50, ""));
        this.buttonPledgeRevoke.isBroken = true;
        LOTRGuiFactions.prevDimension = (LOTRGuiFactions.currentDimension = LOTRDimension.getCurrentDimension((World)super.mc.theWorld));
        this.currentFaction = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getViewingFaction();
        LOTRGuiFactions.prevRegion = (LOTRGuiFactions.currentRegion = this.currentFaction.factionRegion);
        LOTRGuiFactions.currentFactionList = LOTRGuiFactions.currentRegion.factionList;
        final int index = LOTRGuiFactions.currentFactionList.indexOf(this.currentFaction);
        this.currentFactionIndex = index;
        this.prevFactionIndex = index;
        this.setCurrentScrollFromFaction();
        if (super.mc.currentScreen == this) {
            final LOTRPacketClientMQEvent packet = new LOTRPacketClientMQEvent(LOTRPacketClientMQEvent.ClientMQEvent.FACTIONS);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.updateCurrentDimensionAndFaction();
        final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer);
        if (this.isPledging && !playerData.hasPledgeAlignment(this.currentFaction)) {
            this.isPledging = false;
        }
        if (this.isUnpledging && !playerData.isPledgedTo(this.currentFaction)) {
            this.isUnpledging = false;
        }
    }
    
    private void updateCurrentDimensionAndFaction() {
        final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer);
        final Map<LOTRDimension.DimensionRegion, LOTRFaction> lastViewedRegions = new HashMap<LOTRDimension.DimensionRegion, LOTRFaction>();
        if (this.currentFactionIndex != this.prevFactionIndex) {
            this.currentFaction = LOTRGuiFactions.currentFactionList.get(this.currentFactionIndex);
        }
        this.prevFactionIndex = this.currentFactionIndex;
        LOTRGuiFactions.currentDimension = LOTRDimension.getCurrentDimension((World)super.mc.theWorld);
        if (LOTRGuiFactions.currentDimension != LOTRGuiFactions.prevDimension) {
            LOTRGuiFactions.currentRegion = LOTRGuiFactions.currentDimension.dimensionRegions.get(0);
        }
        if (LOTRGuiFactions.currentRegion != LOTRGuiFactions.prevRegion) {
            pd.setRegionLastViewedFaction(LOTRGuiFactions.prevRegion, this.currentFaction);
            lastViewedRegions.put(LOTRGuiFactions.prevRegion, this.currentFaction);
            LOTRGuiFactions.currentFactionList = LOTRGuiFactions.currentRegion.factionList;
            this.currentFaction = pd.getRegionLastViewedFaction(LOTRGuiFactions.currentRegion);
            final int index = LOTRGuiFactions.currentFactionList.indexOf(this.currentFaction);
            this.currentFactionIndex = index;
            this.prevFactionIndex = index;
        }
        LOTRGuiFactions.prevDimension = LOTRGuiFactions.currentDimension;
        LOTRGuiFactions.prevRegion = LOTRGuiFactions.currentRegion;
        final LOTRFaction prevFaction = pd.getViewingFaction();
        final boolean changes = this.currentFaction != prevFaction;
        if (changes) {
            pd.setViewingFaction(this.currentFaction);
            LOTRClientProxy.sendClientInfoPacket(this.currentFaction, lastViewedRegions);
            this.isPledging = false;
            this.isUnpledging = false;
        }
    }
    
    private boolean useFullPageTexture() {
        return this.isPledging || this.isUnpledging || LOTRGuiFactions.currentPage == Page.RANKS;
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        final LOTRPlayerData clientPD = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer);
        boolean mouseOverAlignLock = false;
        boolean mouseOverWarCrimes = false;
        if (!this.isPledging && !this.isUnpledging) {
            this.buttonPagePrev.enabled = (LOTRGuiFactions.currentPage.prev() != null);
            this.buttonPageNext.enabled = (LOTRGuiFactions.currentPage.next() != null);
            final GuiButton buttonFactionMap = this.buttonFactionMap;
            final GuiButton buttonFactionMap2 = this.buttonFactionMap;
            final boolean b = LOTRGuiFactions.currentPage != Page.RANKS && this.currentFaction.isPlayableAlignmentFaction() && LOTRDimension.getCurrentDimension((World)super.mc.theWorld) == this.currentFaction.factionDimension;
            buttonFactionMap2.enabled = b;
            buttonFactionMap.field_146125_m = b;
            if (!LOTRFaction.controlZonesEnabled((World)super.mc.theWorld)) {
                final GuiButton buttonFactionMap3 = this.buttonFactionMap;
                final GuiButton buttonFactionMap4 = this.buttonFactionMap;
                final boolean b2 = false;
                buttonFactionMap4.enabled = b2;
                buttonFactionMap3.field_146125_m = b2;
            }
            if (!this.isOtherPlayer && LOTRGuiFactions.currentPage == Page.FRONT) {
                if (clientPD.isPledgedTo(this.currentFaction)) {
                    this.buttonPledge.isBroken = this.buttonPledge.func_146115_a();
                    final LOTRGuiButtonPledge buttonPledge = this.buttonPledge;
                    final LOTRGuiButtonPledge buttonPledge2 = this.buttonPledge;
                    final boolean b3 = true;
                    buttonPledge2.enabled = b3;
                    buttonPledge.field_146125_m = b3;
                    this.buttonPledge.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.unpledge"));
                }
                else {
                    this.buttonPledge.isBroken = false;
                    this.buttonPledge.field_146125_m = (clientPD.getPledgeFaction() == null && this.currentFaction.isPlayableAlignmentFaction() && clientPD.getAlignment(this.currentFaction) >= 0.0f);
                    this.buttonPledge.enabled = (this.buttonPledge.field_146125_m && clientPD.hasPledgeAlignment(this.currentFaction));
                    final String desc1 = StatCollector.translateToLocal("lotr.gui.factions.pledge");
                    final String desc2 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeReq", new Object[] { LOTRAlignmentValues.formatAlignForDisplay(this.currentFaction.getPledgeAlignment()) });
                    this.buttonPledge.setDisplayLines(desc1, desc2);
                }
            }
            else {
                final LOTRGuiButtonPledge buttonPledge3 = this.buttonPledge;
                final LOTRGuiButtonPledge buttonPledge4 = this.buttonPledge;
                final boolean b4 = false;
                buttonPledge4.enabled = b4;
                buttonPledge3.field_146125_m = b4;
            }
            final LOTRGuiButtonPledge buttonPledgeConfirm = this.buttonPledgeConfirm;
            final LOTRGuiButtonPledge buttonPledgeConfirm2 = this.buttonPledgeConfirm;
            final boolean b5 = false;
            buttonPledgeConfirm2.enabled = b5;
            buttonPledgeConfirm.field_146125_m = b5;
            final LOTRGuiButtonPledge buttonPledgeRevoke = this.buttonPledgeRevoke;
            final LOTRGuiButtonPledge buttonPledgeRevoke2 = this.buttonPledgeRevoke;
            final boolean b6 = false;
            buttonPledgeRevoke2.enabled = b6;
            buttonPledgeRevoke.field_146125_m = b6;
        }
        else {
            this.buttonPagePrev.enabled = false;
            this.buttonPageNext.enabled = false;
            final GuiButton buttonFactionMap5 = this.buttonFactionMap;
            final GuiButton buttonFactionMap6 = this.buttonFactionMap;
            final boolean b7 = false;
            buttonFactionMap6.enabled = b7;
            buttonFactionMap5.field_146125_m = b7;
            final LOTRGuiButtonPledge buttonPledge5 = this.buttonPledge;
            final LOTRGuiButtonPledge buttonPledge6 = this.buttonPledge;
            final boolean b8 = false;
            buttonPledge6.enabled = b8;
            buttonPledge5.field_146125_m = b8;
            if (this.isPledging) {
                this.buttonPledgeConfirm.field_146125_m = true;
                this.buttonPledgeConfirm.enabled = (clientPD.canMakeNewPledge() && clientPD.canPledgeTo(this.currentFaction));
                this.buttonPledgeConfirm.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.pledge"));
                final LOTRGuiButtonPledge buttonPledgeRevoke3 = this.buttonPledgeRevoke;
                final LOTRGuiButtonPledge buttonPledgeRevoke4 = this.buttonPledgeRevoke;
                final boolean b9 = false;
                buttonPledgeRevoke4.enabled = b9;
                buttonPledgeRevoke3.field_146125_m = b9;
            }
            else if (this.isUnpledging) {
                final LOTRGuiButtonPledge buttonPledgeConfirm3 = this.buttonPledgeConfirm;
                final LOTRGuiButtonPledge buttonPledgeConfirm4 = this.buttonPledgeConfirm;
                final boolean b10 = false;
                buttonPledgeConfirm4.enabled = b10;
                buttonPledgeConfirm3.field_146125_m = b10;
                final LOTRGuiButtonPledge buttonPledgeRevoke5 = this.buttonPledgeRevoke;
                final LOTRGuiButtonPledge buttonPledgeRevoke6 = this.buttonPledgeRevoke;
                final boolean b11 = true;
                buttonPledgeRevoke6.enabled = b11;
                buttonPledgeRevoke5.field_146125_m = b11;
                this.buttonPledgeRevoke.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.unpledge"));
            }
        }
        this.setupScrollBar(i, j);
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.useFullPageTexture()) {
            super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTextureFull);
        }
        else {
            super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(super.guiLeft, super.guiTop + this.pageY, 0, 0, this.pageWidth, this.pageHeight);
        String title = StatCollector.translateToLocalFormatted("lotr.gui.factions.title", new Object[] { LOTRGuiFactions.currentDimension.getDimensionName() });
        if (this.isOtherPlayer) {
            title = StatCollector.translateToLocalFormatted("lotr.gui.factions.titleOther", new Object[] { this.otherPlayerName });
        }
        super.fontRendererObj.drawString(title, super.guiLeft + super.xSize / 2 - super.fontRendererObj.getStringWidth(title) / 2, super.guiTop - 30, 16777215);
        if (LOTRGuiFactions.currentRegion != null && LOTRGuiFactions.currentDimension.dimensionRegions.size() > 1) {
            this.buttonRegions.displayString = LOTRGuiFactions.currentRegion.getRegionName();
            final GuiButton buttonRegions = this.buttonRegions;
            final GuiButton buttonRegions2 = this.buttonRegions;
            final boolean b12 = true;
            buttonRegions2.enabled = b12;
            buttonRegions.field_146125_m = b12;
        }
        else {
            this.buttonRegions.displayString = "";
            final GuiButton buttonRegions3 = this.buttonRegions;
            final GuiButton buttonRegions4 = this.buttonRegions;
            final boolean b13 = false;
            buttonRegions4.enabled = b13;
            buttonRegions3.field_146125_m = b13;
        }
        if (this.currentFaction != null) {
            float alignment;
            if (this.isOtherPlayer && this.playerAlignmentMap != null) {
                alignment = this.playerAlignmentMap.get(this.currentFaction);
            }
            else {
                alignment = clientPD.getAlignment(this.currentFaction);
            }
            int x = super.guiLeft + super.xSize / 2;
            int y = super.guiTop;
            LOTRTickHandlerClient.renderAlignmentBar(alignment, this.isOtherPlayer, this.currentFaction, (float)x, (float)y, true, false, true, true);
            y += super.fontRendererObj.FONT_HEIGHT + 22;
            String s = this.currentFaction.factionSubtitle();
            this.drawCenteredString(s, x, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT * 3;
            if (!this.useFullPageTexture()) {
                if (this.currentFaction.factionMapInfo != null) {
                    final LOTRMapRegion mapInfo = this.currentFaction.factionMapInfo;
                    final int mapX = mapInfo.mapX;
                    final int mapY = mapInfo.mapY;
                    final int mapR = mapInfo.radius;
                    final int xMin = super.guiLeft + this.pageMapX;
                    final int xMax = xMin + this.pageMapSize;
                    final int yMin = super.guiTop + this.pageY + this.pageMapY;
                    final int yMax = yMin + this.pageMapSize;
                    final int mapBorder = 1;
                    drawRect(xMin - mapBorder, yMin - mapBorder, xMax + mapBorder, yMax + mapBorder, -16777216);
                    final float zoom = this.pageMapSize / (float)(mapR * 2);
                    final float zoomExp = (float)Math.log(zoom) / (float)Math.log(2.0);
                    this.mapDrawGui.setFakeMapProperties((float)mapX, (float)mapY, zoom, zoomExp, zoom);
                    final LOTRGuiMap mapDrawGui = this.mapDrawGui;
                    final int[] statics = LOTRGuiMap.setFakeStaticProperties(this.pageMapSize, this.pageMapSize, xMin, xMax, yMin, yMax);
                    this.mapDrawGui.enableZoomOutWPFading = false;
                    final boolean sepia = LOTRConfig.enableSepiaMap;
                    this.mapDrawGui.renderMapAndOverlay(sepia, 1.0f, true);
                    final LOTRGuiMap mapDrawGui2 = this.mapDrawGui;
                    LOTRGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
                }
                final int wcX = super.guiLeft + this.pageMapX + 3;
                final int wcY = super.guiTop + this.pageY + this.pageMapY + this.pageMapSize + 5;
                final int wcWidth = 8;
                super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (this.currentFaction.approvesWarCrimes) {
                    this.drawTexturedModalRect(wcX, wcY, 33, 142, wcWidth, wcWidth);
                }
                else {
                    this.drawTexturedModalRect(wcX, wcY, 41, 142, wcWidth, wcWidth);
                }
                if (i >= wcX && i < wcX + wcWidth && j >= wcY && j < wcY + wcWidth) {
                    mouseOverWarCrimes = true;
                }
            }
            x = super.guiLeft + this.pageBorderLeft;
            y = super.guiTop + this.pageY + this.pageBorderTop;
            if (!this.isPledging && !this.isUnpledging) {
                if (LOTRGuiFactions.currentPage == Page.FRONT) {
                    if (this.isOtherPlayer) {
                        s = StatCollector.translateToLocalFormatted("lotr.gui.factions.pageOther", new Object[] { this.otherPlayerName });
                        super.fontRendererObj.drawString(s, x, y, 8019267);
                        y += super.fontRendererObj.FONT_HEIGHT * 2;
                    }
                    final String alignmentInfo = StatCollector.translateToLocal("lotr.gui.factions.alignment");
                    super.fontRendererObj.drawString(alignmentInfo, x, y, 8019267);
                    x += super.fontRendererObj.getStringWidth(alignmentInfo) + 5;
                    final String alignmentString = LOTRAlignmentValues.formatAlignForDisplay(alignment);
                    LOTRTickHandlerClient.drawAlignmentText(super.fontRendererObj, x, y, alignmentString, 1.0f);
                    if (clientPD.isPledgeEnemyAlignmentLimited(this.currentFaction)) {
                        super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        final int lockX = x + super.fontRendererObj.getStringWidth(alignmentString) + 5;
                        final int lockY = y;
                        final int lockWidth = 16;
                        this.drawTexturedModalRect(lockX, lockY, 0, 200, lockWidth, lockWidth);
                        if (i >= lockX && i < lockX + lockWidth && j >= lockY && j < lockY + lockWidth) {
                            mouseOverAlignLock = true;
                        }
                    }
                    y += super.fontRendererObj.FONT_HEIGHT;
                    x = super.guiLeft + this.pageBorderLeft;
                    final LOTRFactionRank curRank = this.currentFaction.getRank(alignment);
                    String rankName = curRank.getFullNameWithGender(clientPD);
                    rankName = StatCollector.translateToLocalFormatted("lotr.gui.factions.alignment.state", new Object[] { rankName });
                    super.fontRendererObj.drawString(rankName, x, y, 8019267);
                    y += super.fontRendererObj.FONT_HEIGHT * 2;
                    if (!this.isOtherPlayer) {
                        final LOTRFactionData factionData = clientPD.getFactionData(this.currentFaction);
                        if (alignment >= 0.0f) {
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.enemiesKilled", new Object[] { factionData.getEnemiesKilled() });
                            super.fontRendererObj.drawString(s, x, y, 8019267);
                            y += super.fontRendererObj.FONT_HEIGHT;
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.trades", new Object[] { factionData.getTradeCount() });
                            super.fontRendererObj.drawString(s, x, y, 8019267);
                            y += super.fontRendererObj.FONT_HEIGHT;
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.hires", new Object[] { factionData.getHireCount() });
                            super.fontRendererObj.drawString(s, x, y, 8019267);
                            y += super.fontRendererObj.FONT_HEIGHT;
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.miniquests", new Object[] { factionData.getMiniQuestsCompleted() });
                            super.fontRendererObj.drawString(s, x, y, 8019267);
                            y += super.fontRendererObj.FONT_HEIGHT;
                            if (clientPD.isPledgedTo(this.currentFaction)) {
                                final float conq = factionData.getConquestEarned();
                                if (conq != 0.0f) {
                                    final int conqInt = Math.round(conq);
                                    s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.conquest", new Object[] { conqInt });
                                    super.fontRendererObj.drawString(s, x, y, 8019267);
                                    y += super.fontRendererObj.FONT_HEIGHT;
                                }
                            }
                        }
                        if (alignment <= 0.0f) {
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.npcsKilled", new Object[] { factionData.getNPCsKilled() });
                            super.fontRendererObj.drawString(s, x, y, 8019267);
                            y += super.fontRendererObj.FONT_HEIGHT;
                        }
                        if (this.buttonPledge.field_146125_m && clientPD.isPledgedTo(this.currentFaction)) {
                            s = StatCollector.translateToLocal("lotr.gui.factions.pledged");
                            final int px = this.buttonPledge.field_146128_h + this.buttonPledge.field_146120_f + 8;
                            final int py = this.buttonPledge.field_146129_i + this.buttonPledge.field_146121_g / 2 - super.fontRendererObj.FONT_HEIGHT / 2;
                            super.fontRendererObj.drawString(s, px, py, 16711680);
                        }
                    }
                }
                else if (LOTRGuiFactions.currentPage == Page.RANKS) {
                    final LOTRFactionRank curRank2 = this.currentFaction.getRank(clientPD);
                    final int[] minMax = this.scrollPaneAlliesEnemies.getMinMaxIndices(this.currentAlliesEnemies, this.numDisplayedAlliesEnemies);
                    for (int index = minMax[0]; index <= minMax[1]; ++index) {
                        final Object listObj = this.currentAlliesEnemies.get(index);
                        if (listObj instanceof String) {
                            s = (String)listObj;
                            super.fontRendererObj.drawString(s, x, y, 8019267);
                        }
                        else if (listObj instanceof LOTRFactionRank) {
                            final LOTRFactionRank rank = (LOTRFactionRank)listObj;
                            String rankName2 = rank.getShortNameWithGender(clientPD);
                            String rankAlign = LOTRAlignmentValues.formatAlignForDisplay(rank.alignment);
                            if (rank == LOTRFactionRank.RANK_ENEMY) {
                                rankAlign = "-";
                            }
                            boolean hiddenRankName = false;
                            if (!clientPD.isPledgedTo(this.currentFaction) && rank.alignment > this.currentFaction.getPledgeAlignment() && rank.alignment > this.currentFaction.getRankAbove(curRank2).alignment) {
                                hiddenRankName = true;
                            }
                            if (hiddenRankName) {
                                rankName2 = StatCollector.translateToLocal("lotr.gui.factions.rank?");
                            }
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.listRank", new Object[] { rankName2, rankAlign });
                            if (rank == curRank2) {
                                LOTRTickHandlerClient.drawAlignmentText(super.fontRendererObj, x, y, s, 1.0f);
                            }
                            else {
                                super.fontRendererObj.drawString(s, x, y, 8019267);
                            }
                        }
                        y += super.fontRendererObj.FONT_HEIGHT;
                    }
                }
                else if (LOTRGuiFactions.currentPage == Page.ALLIES || LOTRGuiFactions.currentPage == Page.ENEMIES) {
                    final int avgBgColor = LOTRTextures.computeAverageFactionPageColor(LOTRGuiFactions.factionsTexture, 20, 20, 120, 80);
                    final int[] minMax = this.scrollPaneAlliesEnemies.getMinMaxIndices(this.currentAlliesEnemies, this.numDisplayedAlliesEnemies);
                    for (int index = minMax[0]; index <= minMax[1]; ++index) {
                        final Object listObj = this.currentAlliesEnemies.get(index);
                        if (listObj instanceof LOTRFactionRelations.Relation) {
                            final LOTRFactionRelations.Relation rel = (LOTRFactionRelations.Relation)listObj;
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.relationHeader", new Object[] { rel.getDisplayName() });
                            super.fontRendererObj.drawString(s, x, y, 8019267);
                        }
                        else if (listObj instanceof LOTRFaction) {
                            final LOTRFaction fac = (LOTRFaction)listObj;
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.list", new Object[] { fac.factionName() });
                            super.fontRendererObj.drawString(s, x, y, LOTRTextures.findContrastingColor(fac.getFactionColor(), avgBgColor));
                        }
                        y += super.fontRendererObj.FONT_HEIGHT;
                    }
                }
                if (this.scrollPaneAlliesEnemies.hasScrollBar) {
                    this.scrollPaneAlliesEnemies.drawScrollBar();
                }
            }
            else {
                final int stringWidth = this.pageWidth - this.pageBorderLeft * 2;
                final List<String> displayLines = new ArrayList<String>();
                if (this.isPledging) {
                    final List<LOTRFaction> facsPreventingPledge = clientPD.getFactionsPreventingPledgeTo(this.currentFaction);
                    if (facsPreventingPledge.isEmpty()) {
                        if (clientPD.canMakeNewPledge()) {
                            if (clientPD.canPledgeTo(this.currentFaction)) {
                                String desc3 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeDesc1", new Object[] { this.currentFaction.factionName() });
                                displayLines.addAll(super.fontRendererObj.listFormattedStringToWidth(desc3, stringWidth));
                                displayLines.add("");
                                desc3 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeDesc2", new Object[0]);
                                displayLines.addAll(super.fontRendererObj.listFormattedStringToWidth(desc3, stringWidth));
                            }
                        }
                        else {
                            final LOTRFaction brokenPledge = clientPD.getBrokenPledgeFaction();
                            final String brokenPledgeName = (brokenPledge == null) ? StatCollector.translateToLocal("lotr.gui.factions.pledgeUnknown") : brokenPledge.factionName();
                            final String desc4 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeBreakCooldown", new Object[] { this.currentFaction.factionName(), brokenPledgeName });
                            displayLines.addAll(super.fontRendererObj.listFormattedStringToWidth(desc4, stringWidth));
                            displayLines.add("");
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
                            this.drawTexturedModalRect(super.guiLeft + this.pageWidth / 2 - 97, super.guiTop + this.pageY + 56, 0, 240, 194, 16);
                            final float cdFrac = clientPD.getPledgeBreakCooldown() / (float)clientPD.getPledgeBreakCooldownStart();
                            this.drawTexturedModalRect(super.guiLeft + this.pageWidth / 2 - 75, super.guiTop + this.pageY + 60, 22, 232, MathHelper.ceiling_float_int(cdFrac * 150.0f), 8);
                        }
                    }
                    else {
                        Collections.sort(facsPreventingPledge, new Comparator<LOTRFaction>() {
                            @Override
                            public int compare(final LOTRFaction o1, final LOTRFaction o2) {
                                final float align1 = clientPD.getAlignment(o1);
                                final float align2 = clientPD.getAlignment(o2);
                                return -Float.valueOf(align1).compareTo(align2);
                            }
                        });
                        String facNames = "If you are reading this, something has gone hideously wrong.";
                        if (facsPreventingPledge.size() == 1) {
                            facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies1", new Object[] { facsPreventingPledge.get(0).factionName() });
                        }
                        else if (facsPreventingPledge.size() == 2) {
                            facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies2", new Object[] { facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName() });
                        }
                        else if (facsPreventingPledge.size() == 3) {
                            facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies3", new Object[] { facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName(), facsPreventingPledge.get(2).factionName() });
                        }
                        else if (facsPreventingPledge.size() > 3) {
                            facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies3+", new Object[] { facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName(), facsPreventingPledge.get(2).factionName(), facsPreventingPledge.size() - 3 });
                        }
                        final String desc5 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeEnemies", new Object[] { this.currentFaction.factionName(), facNames });
                        displayLines.addAll(super.fontRendererObj.listFormattedStringToWidth(desc5, stringWidth));
                        displayLines.add("");
                    }
                }
                else if (this.isUnpledging) {
                    String desc6 = StatCollector.translateToLocalFormatted("lotr.gui.factions.unpledgeDesc1", new Object[] { this.currentFaction.factionName() });
                    displayLines.addAll(super.fontRendererObj.listFormattedStringToWidth(desc6, stringWidth));
                    displayLines.add("");
                    desc6 = StatCollector.translateToLocalFormatted("lotr.gui.factions.unpledgeDesc2", new Object[0]);
                    displayLines.addAll(super.fontRendererObj.listFormattedStringToWidth(desc6, stringWidth));
                }
                for (final String line : displayLines) {
                    super.fontRendererObj.drawString(line, x, y, 8019267);
                    y += super.mc.fontRenderer.FONT_HEIGHT;
                }
            }
        }
        if (this.hasScrollBar()) {
            super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(super.guiLeft + this.scrollBarX, super.guiTop + this.scrollBarY, 0, 128, this.scrollBarWidth, this.scrollBarHeight);
            for (int factions = LOTRGuiFactions.currentFactionList.size(), index2 = 0; index2 < factions; ++index2) {
                final LOTRFaction faction = LOTRGuiFactions.currentFactionList.get(index2);
                final float[] factionColors = faction.getFactionColorComponents();
                final float shade = 0.6f;
                GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0f);
                float xMin2 = index2 / (float)factions;
                float xMax2 = (index2 + 1) / (float)factions;
                xMin2 = super.guiLeft + this.scrollBarX + this.scrollBarBorder + xMin2 * (this.scrollBarWidth - this.scrollBarBorder * 2);
                xMax2 = super.guiLeft + this.scrollBarX + this.scrollBarBorder + xMax2 * (this.scrollBarWidth - this.scrollBarBorder * 2);
                final float yMin2 = (float)(super.guiTop + this.scrollBarY + this.scrollBarBorder);
                final float yMax2 = (float)(super.guiTop + this.scrollBarY + this.scrollBarHeight - this.scrollBarBorder);
                final float minU = (0 + this.scrollBarBorder) / 256.0f;
                final float maxU = (0 + this.scrollBarWidth - this.scrollBarBorder) / 256.0f;
                final float minV = (128 + this.scrollBarBorder) / 256.0f;
                final float maxV = (128 + this.scrollBarHeight - this.scrollBarBorder) / 256.0f;
                final Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV((double)xMin2, (double)yMax2, (double)((Gui)this).zLevel, (double)minU, (double)maxV);
                tessellator.addVertexWithUV((double)xMax2, (double)yMax2, (double)((Gui)this).zLevel, (double)maxU, (double)maxV);
                tessellator.addVertexWithUV((double)xMax2, (double)yMin2, (double)((Gui)this).zLevel, (double)maxU, (double)minV);
                tessellator.addVertexWithUV((double)xMin2, (double)yMin2, (double)((Gui)this).zLevel, (double)minU, (double)minV);
                tessellator.draw();
            }
            super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.canScroll()) {
                final int scroll = (int)(this.currentScroll * (this.scrollBarWidth - this.scrollBarBorder * 2 - this.scrollWidgetWidth));
                this.drawTexturedModalRect(super.guiLeft + this.scrollBarX + this.scrollBarBorder + scroll, super.guiTop + this.scrollBarY + this.scrollBarBorder, 0, 142, this.scrollWidgetWidth, this.scrollWidgetHeight);
            }
        }
        super.drawScreen(i, j, f);
        if (this.buttonFactionMap.enabled && this.buttonFactionMap.func_146115_a()) {
            final float z = ((Gui)this).zLevel;
            final String s2 = StatCollector.translateToLocal("lotr.gui.factions.viewMap");
            final int stringWidth2 = 200;
            final List desc7 = super.fontRendererObj.listFormattedStringToWidth(s2, stringWidth2);
            this.func_146283_a(desc7, i, j);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ((Gui)this).zLevel = z;
        }
        if (mouseOverAlignLock) {
            final float z = ((Gui)this).zLevel;
            final String alignLimit = LOTRAlignmentValues.formatAlignForDisplay(clientPD.getPledgeEnemyAlignmentLimit(this.currentFaction));
            final String lockDesc = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeLocked", new Object[] { alignLimit, clientPD.getPledgeFaction().factionName() });
            final int stringWidth3 = 200;
            final List desc8 = super.fontRendererObj.listFormattedStringToWidth(lockDesc, stringWidth3);
            this.func_146283_a(desc8, i, j);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ((Gui)this).zLevel = z;
        }
        if (mouseOverWarCrimes) {
            final float z = ((Gui)this).zLevel;
            String warCrimes = this.currentFaction.approvesWarCrimes ? "lotr.gui.factions.warCrimesYes" : "lotr.gui.factions.warCrimesNo";
            warCrimes = StatCollector.translateToLocal(warCrimes);
            final int stringWidth2 = 200;
            final List desc7 = super.fontRendererObj.listFormattedStringToWidth(warCrimes, stringWidth2);
            this.func_146283_a(desc7, i, j);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ((Gui)this).zLevel = z;
        }
    }
    
    private boolean hasScrollBar() {
        return LOTRGuiFactions.currentFactionList.size() > 1;
    }
    
    private boolean canScroll() {
        return true;
    }
    
    private void setupScrollBar(final int i, final int j) {
        final boolean isMouseDown = Mouse.isButtonDown(0);
        final int i2 = super.guiLeft + this.scrollBarX;
        final int j2 = super.guiTop + this.scrollBarY;
        final int i3 = i2 + this.scrollBarWidth;
        final int j3 = j2 + this.scrollBarHeight;
        if (!this.wasMouseDown && isMouseDown && i >= i2 && j >= j2 && i < i3 && j < j3) {
            this.isScrolling = this.canScroll();
        }
        if (!isMouseDown) {
            this.isScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (i - i2 - this.scrollWidgetWidth / 2.0f) / (i3 - i2 - (float)this.scrollWidgetWidth);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
            this.currentFactionIndex = Math.round(this.currentScroll * (LOTRGuiFactions.currentFactionList.size() - 1));
            this.scrollPaneAlliesEnemies.resetScroll();
        }
        if (LOTRGuiFactions.currentPage == Page.ALLIES || LOTRGuiFactions.currentPage == Page.ENEMIES || LOTRGuiFactions.currentPage == Page.RANKS) {
            if (LOTRGuiFactions.currentPage == Page.ALLIES) {
                this.currentAlliesEnemies = new ArrayList();
                final List<LOTRFaction> allies = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.ALLY);
                if (!allies.isEmpty()) {
                    this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.ALLY);
                    this.currentAlliesEnemies.addAll(allies);
                }
                final List<LOTRFaction> friends = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.FRIEND);
                if (!friends.isEmpty()) {
                    if (!this.currentAlliesEnemies.isEmpty()) {
                        this.currentAlliesEnemies.add(null);
                    }
                    this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.FRIEND);
                    this.currentAlliesEnemies.addAll(friends);
                }
            }
            else if (LOTRGuiFactions.currentPage == Page.ENEMIES) {
                this.currentAlliesEnemies = new ArrayList();
                final List<LOTRFaction> mortals = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.MORTAL_ENEMY);
                if (!mortals.isEmpty()) {
                    this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.MORTAL_ENEMY);
                    this.currentAlliesEnemies.addAll(mortals);
                }
                final List<LOTRFaction> enemies = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.ENEMY);
                if (!enemies.isEmpty()) {
                    if (!this.currentAlliesEnemies.isEmpty()) {
                        this.currentAlliesEnemies.add(null);
                    }
                    this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.ENEMY);
                    this.currentAlliesEnemies.addAll(enemies);
                }
            }
            else if (LOTRGuiFactions.currentPage == Page.RANKS) {
                (this.currentAlliesEnemies = new ArrayList()).add(StatCollector.translateToLocal("lotr.gui.factions.rankHeader"));
                if (LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getAlignment(this.currentFaction) <= 0.0f) {
                    this.currentAlliesEnemies.add(LOTRFactionRank.RANK_ENEMY);
                }
                LOTRFactionRank rank = LOTRFactionRank.RANK_NEUTRAL;
                while (true) {
                    this.currentAlliesEnemies.add(rank);
                    final LOTRFactionRank nextRank = this.currentFaction.getRankAbove(rank);
                    if (nextRank == null || nextRank.isDummyRank()) {
                        break;
                    }
                    if (this.currentAlliesEnemies.contains(nextRank)) {
                        break;
                    }
                    rank = nextRank;
                }
            }
            this.scrollPaneAlliesEnemies.hasScrollBar = false;
            this.numDisplayedAlliesEnemies = this.currentAlliesEnemies.size();
            if (this.numDisplayedAlliesEnemies > 10) {
                this.numDisplayedAlliesEnemies = 10;
                this.scrollPaneAlliesEnemies.hasScrollBar = true;
            }
            this.scrollPaneAlliesEnemies.paneX0 = super.guiLeft;
            this.scrollPaneAlliesEnemies.scrollBarX0 = super.guiLeft + this.scrollAlliesEnemiesX;
            if (LOTRGuiFactions.currentPage == Page.RANKS) {
                final LOTRGuiScrollPane scrollPaneAlliesEnemies = this.scrollPaneAlliesEnemies;
                scrollPaneAlliesEnemies.scrollBarX0 += 50;
            }
            this.scrollPaneAlliesEnemies.paneY0 = super.guiTop + this.pageY + this.pageBorderTop;
            this.scrollPaneAlliesEnemies.paneY1 = this.scrollPaneAlliesEnemies.paneY0 + super.fontRendererObj.FONT_HEIGHT * this.numDisplayedAlliesEnemies;
            this.scrollPaneAlliesEnemies.mouseDragScroll(i, j);
        }
        else {
            this.scrollPaneAlliesEnemies.hasScrollBar = false;
            this.scrollPaneAlliesEnemies.mouseDragScroll(i, j);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (i == 1 || i == super.mc.gameSettings.keyBindInventory.getKeyCode()) {
            if (this.isPledging) {
                this.isPledging = false;
                return;
            }
            if (this.isUnpledging) {
                this.isUnpledging = false;
                return;
            }
            if (this.isOtherPlayer) {
                super.mc.thePlayer.closeScreen();
                return;
            }
        }
        super.keyTyped(c, i);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonRegions) {
                final List<LOTRDimension.DimensionRegion> regionList = LOTRGuiFactions.currentDimension.dimensionRegions;
                if (!regionList.isEmpty()) {
                    int i = regionList.indexOf(LOTRGuiFactions.currentRegion);
                    i = IntMath.mod(++i, regionList.size());
                    LOTRGuiFactions.currentRegion = regionList.get(i);
                    this.updateCurrentDimensionAndFaction();
                    this.setCurrentScrollFromFaction();
                    this.scrollPaneAlliesEnemies.resetScroll();
                    this.isPledging = false;
                    this.isUnpledging = false;
                }
            }
            else if (button == this.buttonPagePrev) {
                final Page newPage = LOTRGuiFactions.currentPage.prev();
                if (newPage != null) {
                    LOTRGuiFactions.currentPage = newPage;
                    this.scrollPaneAlliesEnemies.resetScroll();
                    this.isPledging = false;
                    this.isUnpledging = false;
                }
            }
            else if (button == this.buttonPageNext) {
                final Page newPage = LOTRGuiFactions.currentPage.next();
                if (newPage != null) {
                    LOTRGuiFactions.currentPage = newPage;
                    this.scrollPaneAlliesEnemies.resetScroll();
                    this.isPledging = false;
                    this.isUnpledging = false;
                }
            }
            else if (button == this.buttonFactionMap) {
                final LOTRGuiMap factionGuiMap = new LOTRGuiMap();
                factionGuiMap.setControlZone(this.currentFaction);
                super.mc.displayGuiScreen((GuiScreen)factionGuiMap);
            }
            else if (button == this.buttonPledge) {
                if (LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).isPledgedTo(this.currentFaction)) {
                    this.isUnpledging = true;
                }
                else {
                    this.isPledging = true;
                }
            }
            else if (button == this.buttonPledgeConfirm) {
                final LOTRPacketPledgeSet packet = new LOTRPacketPledgeSet(this.currentFaction);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
                this.isPledging = false;
            }
            else if (button == this.buttonPledgeRevoke) {
                final LOTRPacketPledgeSet packet = new LOTRPacketPledgeSet(null);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
                this.isUnpledging = false;
                super.mc.displayGuiScreen((GuiScreen)null);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        int k = Mouse.getEventDWheel();
        if (k != 0) {
            k = Integer.signum(k);
            if (this.scrollPaneAlliesEnemies.hasScrollBar && this.scrollPaneAlliesEnemies.mouseOver) {
                final int l = this.currentAlliesEnemies.size() - this.numDisplayedAlliesEnemies;
                this.scrollPaneAlliesEnemies.mouseWheelScroll(k, l);
            }
            else {
                if (k < 0) {
                    this.currentFactionIndex = Math.min(this.currentFactionIndex + 1, Math.max(0, LOTRGuiFactions.currentFactionList.size() - 1));
                }
                if (k > 0) {
                    this.currentFactionIndex = Math.max(this.currentFactionIndex - 1, 0);
                }
                this.setCurrentScrollFromFaction();
                this.scrollPaneAlliesEnemies.resetScroll();
                this.isPledging = false;
                this.isUnpledging = false;
            }
        }
    }
    
    private void setCurrentScrollFromFaction() {
        this.currentScroll = this.currentFactionIndex / (float)(LOTRGuiFactions.currentFactionList.size() - 1);
    }
    
    public void drawButtonHoveringText(final List list, final int i, final int j) {
        this.func_146283_a(list, i, j);
    }
    
    static {
        factionsTexture = new ResourceLocation("lotr:gui/factions.png");
        factionsTextureFull = new ResourceLocation("lotr:gui/factions_full.png");
        LOTRGuiFactions.currentPage = Page.FRONT;
    }
    
    public enum Page
    {
        FRONT, 
        RANKS, 
        ALLIES, 
        ENEMIES;
        
        public Page prev() {
            int i = this.ordinal();
            if (i == 0) {
                return null;
            }
            --i;
            return values()[i];
        }
        
        public Page next() {
            int i = this.ordinal();
            if (i == values().length - 1) {
                return null;
            }
            ++i;
            return values()[i];
        }
    }
}
