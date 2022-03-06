// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import lotr.common.network.LOTRPacketConquestGridRequest;
import net.minecraft.server.MinecraftServer;
import lotr.common.network.LOTRPacketIsOpRequest;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.util.IChatComponent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import com.google.common.math.IntMath;
import lotr.common.network.LOTRPacketRenameCWP;
import lotr.common.network.LOTRPacketDeleteCWP;
import lotr.common.network.LOTRPacketCreateCWP;
import lotr.common.network.LOTRPacketCWPSharedHide;
import lotr.common.network.LOTRPacketShareCWP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import lotr.common.network.LOTRPacketMapTp;
import lotr.common.network.LOTRPacketFastTravel;
import org.apache.commons.lang3.StringUtils;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.fac.LOTRControlZone;
import net.minecraft.util.IIcon;
import net.minecraft.util.ChunkCoordinates;
import lotr.common.quest.LOTRMiniQuest;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.entity.AbstractClientPlayer;
import java.awt.Color;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.LOTRPlayerData;
import java.util.Iterator;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.client.settings.GameSettings;
import lotr.client.LOTRKeyHandler;
import lotr.common.world.map.LOTRCustomWaypoint;
import java.util.Collection;
import java.util.regex.Pattern;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.gui.ScaledResolution;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.world.map.LOTRConquestGrid;
import java.util.ArrayList;
import net.minecraft.world.World;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import lotr.client.LOTRTextures;
import lotr.common.LOTRConfig;
import org.lwjgl.input.Mouse;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketClientMQEvent;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import java.util.HashSet;
import java.util.HashMap;
import com.mojang.authlib.GameProfile;
import lotr.common.LOTRDimension;
import java.util.Set;
import lotr.common.world.map.LOTRConquestZone;
import lotr.common.fac.LOTRFaction;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import lotr.common.world.map.LOTRAbstractWaypoint;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import java.util.UUID;
import java.util.Map;

public class LOTRGuiMap extends LOTRGuiMenuBase
{
    private static Map<UUID, PlayerLocationInfo> playerLocations;
    public static final ResourceLocation mapIconsTexture;
    public static final ResourceLocation conquestTexture;
    private static final ItemStack questBookIcon;
    public static final int BLACK = -16777216;
    public static final int BORDER_COLOR = -6156032;
    private static final int MIN_ZOOM = -3;
    private static final int MAX_ZOOM = 4;
    private static final int mapBorder = 30;
    private static boolean fullscreen;
    private static int mapWidth;
    private static int mapHeight;
    private static int mapXMin;
    private static int mapXMax;
    private static int mapYMin;
    private static int mapYMax;
    private static int mapXMin_W;
    private static int mapXMax_W;
    private static int mapYMin_W;
    private static int mapYMax_W;
    private static List<LOTRGuiMapWidget> mapWidgets;
    private LOTRGuiMapWidget widgetAddCWP;
    private LOTRGuiMapWidget widgetDelCWP;
    private LOTRGuiMapWidget widgetRenameCWP;
    private LOTRGuiMapWidget widgetToggleWPs;
    private LOTRGuiMapWidget widgetToggleCWPs;
    private LOTRGuiMapWidget widgetToggleHiddenSWPs;
    private LOTRGuiMapWidget widgetZoomIn;
    private LOTRGuiMapWidget widgetZoomOut;
    private LOTRGuiMapWidget widgetFullScreen;
    private LOTRGuiMapWidget widgetSepia;
    private LOTRGuiMapWidget widgetLabels;
    private LOTRGuiMapWidget widgetShareCWP;
    private LOTRGuiMapWidget widgetHideSWP;
    private LOTRGuiMapWidget widgetUnhideSWP;
    private float posX;
    private float posY;
    private int isMouseButtonDown;
    private int prevMouseX;
    private int prevMouseY;
    private boolean isMouseWithinMap;
    private int mouseXCoord;
    private int mouseZCoord;
    private float posXMove;
    private float posYMove;
    private float prevPosX;
    private float prevPosY;
    private static int zoomPower;
    private int prevZoomPower;
    private float zoomScale;
    private float zoomScaleStable;
    private float zoomExp;
    private static int zoomTicksMax;
    private int zoomTicks;
    public boolean enableZoomOutWPFading;
    private LOTRAbstractWaypoint selectedWaypoint;
    private static final int waypointSelectRange = 5;
    public static boolean showWP;
    public static boolean showCWP;
    public static boolean showHiddenSWP;
    private boolean hasOverlay;
    private String[] overlayLines;
    private GuiButton buttonOverlayFunction;
    private GuiTextField nameWPTextField;
    private boolean creatingWaypoint;
    private boolean creatingWaypointNew;
    private boolean deletingWaypoint;
    private boolean renamingWaypoint;
    private boolean sharingWaypoint;
    private boolean sharingWaypointNew;
    private LOTRGuiFellowships fellowshipDrawGUI;
    private LOTRFellowshipClient mouseOverRemoveSharedFellowship;
    private LOTRGuiScrollPane scrollPaneWPShares;
    private List<UUID> displayedWPShareList;
    private static int maxDisplayedWPShares;
    private int displayedWPShares;
    public boolean isPlayerOp;
    private int tickCounter;
    private boolean hasControlZones;
    private LOTRFaction controlZoneFaction;
    private boolean mouseControlZone;
    private boolean mouseControlZoneReduced;
    private boolean isConquestGrid;
    private static final int conqBorderW = 8;
    private static final int conqBorderUp = 22;
    private static final int conqBorderDown = 54;
    private boolean loadingConquestGrid;
    private Map<LOTRFaction, List<LOTRConquestZone>> facConquestGrids;
    private Set<LOTRFaction> requestedFacGrids;
    private Set<LOTRFaction> receivedFacGrids;
    private int ticksUntilRequestFac;
    private static final int REQUEST_FAC_WAIT = 40;
    private float highestViewedConqStr;
    private static final int conqKeyGrades = 10;
    public static final int CONQUEST_COLOR = 12255232;
    private static final int CONQUEST_COLOR_OPQ = -4521984;
    private static final int CONQUEST_COLOR_NO_EFFECT = 1973790;
    private static LOTRDimension.DimensionRegion currentRegion;
    private static LOTRDimension.DimensionRegion prevRegion;
    private static List<LOTRFaction> currentFactionList;
    private int currentFactionIndex;
    private int prevFactionIndex;
    private LOTRFaction conquestViewingFaction;
    private static Map<LOTRDimension.DimensionRegion, LOTRFaction> lastViewedRegions;
    private float currentFacScroll;
    private boolean isFacScrolling;
    private boolean wasMouseDown;
    private boolean mouseInFacScroll;
    private int facScrollWidth;
    private int facScrollHeight;
    private int facScrollX;
    private int facScrollY;
    private int facScrollBorder;
    private int facScrollWidgetWidth;
    private int facScrollWidgetHeight;
    private GuiButton buttonConquestRegions;
    
    public static void addPlayerLocationInfo(final GameProfile player, final double x, final double z) {
        if (player.isComplete()) {
            LOTRGuiMap.playerLocations.put(player.getId(), new PlayerLocationInfo(player, x, z));
        }
    }
    
    public static void clearPlayerLocations() {
        LOTRGuiMap.playerLocations.clear();
    }
    
    public LOTRGuiMap() {
        this.prevZoomPower = LOTRGuiMap.zoomPower;
        this.enableZoomOutWPFading = true;
        this.scrollPaneWPShares = new LOTRGuiScrollPane(9, 8);
        this.isPlayerOp = false;
        this.hasControlZones = false;
        this.isConquestGrid = false;
        this.loadingConquestGrid = false;
        this.facConquestGrids = new HashMap<LOTRFaction, List<LOTRConquestZone>>();
        this.requestedFacGrids = new HashSet<LOTRFaction>();
        this.receivedFacGrids = new HashSet<LOTRFaction>();
        this.ticksUntilRequestFac = 40;
        this.currentFactionIndex = 0;
        this.prevFactionIndex = 0;
        this.currentFacScroll = 0.0f;
        this.isFacScrolling = false;
        this.facScrollWidth = 240;
        this.facScrollHeight = 14;
        this.facScrollBorder = 1;
        this.facScrollWidgetWidth = 17;
        this.facScrollWidgetHeight = 12;
        if (!LOTRGenLayerWorld.loadedBiomeImage()) {
            new LOTRGenLayerWorld();
        }
    }
    
    public LOTRGuiMap setConquestGrid() {
        this.isConquestGrid = true;
        return this;
    }
    
    public void setControlZone(final LOTRFaction f) {
        this.hasControlZones = true;
        this.controlZoneFaction = f;
    }
    
    @Override
    public void initGui() {
        super.xSize = 256;
        super.ySize = 256;
        super.initGui();
        if (LOTRGuiMap.fullscreen) {
            final int midX = super.width / 2;
            final int d = 100;
            super.buttonMenuReturn.field_146128_h = midX - d - super.buttonMenuReturn.field_146120_f;
            super.buttonMenuReturn.field_146129_i = 4;
        }
        if (this.isConquestGrid || this.hasControlZones) {
            super.buttonList.remove(super.buttonMenuReturn);
        }
        this.setupMapWidgets();
        if (this.isConquestGrid) {
            this.loadingConquestGrid = true;
            this.setupMapDimensions();
            this.conquestViewingFaction = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getPledgeFaction();
            if (this.conquestViewingFaction == null) {
                this.conquestViewingFaction = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getViewingFaction();
            }
            LOTRGuiMap.prevRegion = (LOTRGuiMap.currentRegion = this.conquestViewingFaction.factionRegion);
            LOTRGuiMap.currentFactionList = LOTRGuiMap.currentRegion.factionList;
            final int index = LOTRGuiMap.currentFactionList.indexOf(this.conquestViewingFaction);
            this.currentFactionIndex = index;
            this.prevFactionIndex = index;
            LOTRGuiMap.lastViewedRegions.put(LOTRGuiMap.currentRegion, this.conquestViewingFaction);
            this.facScrollX = LOTRGuiMap.mapXMin;
            this.facScrollY = LOTRGuiMap.mapYMax + 8;
            this.setCurrentScrollFromFaction();
            super.buttonList.add(this.buttonConquestRegions = new LOTRGuiButtonRedBook(200, LOTRGuiMap.mapXMax - 120, LOTRGuiMap.mapYMax + 5, 120, 20, ""));
        }
        if (this.hasControlZones) {
            this.setupMapDimensions();
            final int[] zoneBorders = this.controlZoneFaction.calculateFullControlZoneWorldBorders();
            final int xMin = zoneBorders[0];
            final int xMax = zoneBorders[1];
            final int zMin = zoneBorders[2];
            final int zMax = zoneBorders[3];
            final float x = (xMin + xMax) / 2.0f;
            final float z = (zMin + zMax) / 2.0f;
            this.posX = x / LOTRGenLayerWorld.scale + 810.0f;
            this.posY = z / LOTRGenLayerWorld.scale + 730.0f;
            final int zoneWidth = xMax - xMin;
            final int zoneHeight = zMax - zMin;
            final double mapZoneWidth = zoneWidth / (double)LOTRGenLayerWorld.scale;
            final double mapZoneHeight = zoneHeight / (double)LOTRGenLayerWorld.scale;
            final int zoomPowerWidth = MathHelper.floor_double(Math.log(LOTRGuiMap.mapWidth / mapZoneWidth) / Math.log(2.0));
            final int zoomPowerHeight = MathHelper.floor_double(Math.log(LOTRGuiMap.mapHeight / mapZoneHeight) / Math.log(2.0));
            LOTRGuiMap.zoomPower = Math.min(zoomPowerWidth, zoomPowerHeight);
            this.prevZoomPower = LOTRGuiMap.zoomPower;
        }
        else if (super.mc.thePlayer != null) {
            this.posX = (float)(((Entity)super.mc.thePlayer).posX / LOTRGenLayerWorld.scale) + 810.0f;
            this.posY = (float)(((Entity)super.mc.thePlayer).posZ / LOTRGenLayerWorld.scale) + 730.0f;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.buttonOverlayFunction = new GuiButton(0, 0, 0, 160, 20, "");
        final GuiButton buttonOverlayFunction = this.buttonOverlayFunction;
        final GuiButton buttonOverlayFunction2 = this.buttonOverlayFunction;
        final boolean b = false;
        buttonOverlayFunction2.field_146125_m = b;
        buttonOverlayFunction.enabled = b;
        super.buttonList.add(this.buttonOverlayFunction);
        this.nameWPTextField = new GuiTextField(super.fontRendererObj, LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2 - 80, LOTRGuiMap.mapYMin + 50, 160, 20);
        (this.fellowshipDrawGUI = new LOTRGuiFellowships()).setWorldAndResolution(super.mc, super.width, super.height);
        if (super.mc.currentScreen == this) {
            final LOTRPacketClientMQEvent packet = new LOTRPacketClientMQEvent(LOTRPacketClientMQEvent.ClientMQEvent.MAP);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    private void setupMapWidgets() {
        LOTRGuiMap.mapWidgets.clear();
        LOTRGuiMap.mapWidgets.add(this.widgetAddCWP = new LOTRGuiMapWidget(-16, 6, 10, "addCWP", 0, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetDelCWP = new LOTRGuiMapWidget(-16, 20, 10, "delCWP", 10, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetRenameCWP = new LOTRGuiMapWidget(-16, 34, 10, "renameCWP", 0, 10));
        LOTRGuiMap.mapWidgets.add(this.widgetToggleWPs = new LOTRGuiMapWidget(-58, 6, 10, "toggleWPs", 80, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetToggleCWPs = new LOTRGuiMapWidget(-44, 6, 10, "toggleCWPs", 90, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetToggleHiddenSWPs = new LOTRGuiMapWidget(-30, 6, 10, "toggleHiddenSWPs", 100, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetZoomIn = new LOTRGuiMapWidget(6, 6, 10, "zoomIn", 30, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetZoomOut = new LOTRGuiMapWidget(6, 20, 10, "zoomOut", 40, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetFullScreen = new LOTRGuiMapWidget(20, 6, 10, "fullScreen", 50, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetSepia = new LOTRGuiMapWidget(34, 6, 10, "sepia", 60, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetLabels = new LOTRGuiMapWidget(-72, 6, 10, "labels", 70, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetShareCWP = new LOTRGuiMapWidget(-16, 48, 10, "shareCWP", 10, 10));
        LOTRGuiMap.mapWidgets.add(this.widgetHideSWP = new LOTRGuiMapWidget(-16, 20, 10, "hideSWP", 20, 0));
        LOTRGuiMap.mapWidgets.add(this.widgetUnhideSWP = new LOTRGuiMapWidget(-16, 20, 10, "unhideSWP", 20, 10));
        if (this.isConquestGrid) {
            LOTRGuiMap.mapWidgets.clear();
            LOTRGuiMap.mapWidgets.add(this.widgetToggleWPs);
            LOTRGuiMap.mapWidgets.add(this.widgetToggleCWPs);
            LOTRGuiMap.mapWidgets.add(this.widgetToggleHiddenSWPs);
            LOTRGuiMap.mapWidgets.add(this.widgetZoomIn);
            LOTRGuiMap.mapWidgets.add(this.widgetZoomOut);
            LOTRGuiMap.mapWidgets.add(this.widgetLabels);
        }
    }
    
    private void setupMapDimensions() {
        if (this.isConquestGrid) {
            final int windowWidth = 400;
            final int windowHeight = 240;
            LOTRGuiMap.mapXMin = super.width / 2 - windowWidth / 2;
            LOTRGuiMap.mapXMax = super.width / 2 + windowWidth / 2;
            LOTRGuiMap.mapYMin = super.height / 2 - 16 - windowHeight / 2;
            LOTRGuiMap.mapYMax = LOTRGuiMap.mapYMin + windowHeight;
        }
        else if (LOTRGuiMap.fullscreen) {
            LOTRGuiMap.mapXMin = 30;
            LOTRGuiMap.mapXMax = super.width - 30;
            LOTRGuiMap.mapYMin = 30;
            LOTRGuiMap.mapYMax = super.height - 30;
        }
        else {
            final int windowWidth = 312;
            LOTRGuiMap.mapXMin = super.width / 2 - windowWidth / 2;
            LOTRGuiMap.mapXMax = super.width / 2 + windowWidth / 2;
            LOTRGuiMap.mapYMin = super.guiTop;
            LOTRGuiMap.mapYMax = super.guiTop + 200;
        }
        LOTRGuiMap.mapWidth = LOTRGuiMap.mapXMax - LOTRGuiMap.mapXMin;
        LOTRGuiMap.mapHeight = LOTRGuiMap.mapYMax - LOTRGuiMap.mapYMin;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.tickCounter;
        if (this.zoomTicks > 0) {
            --this.zoomTicks;
        }
        if (this.creatingWaypointNew || this.renamingWaypoint || this.sharingWaypointNew) {
            this.nameWPTextField.updateCursorCounter();
        }
        this.handleMapKeyboardMovement();
        if (this.isConquestGrid) {
            this.updateCurrentDimensionAndFaction();
            if (this.ticksUntilRequestFac > 0) {
                --this.ticksUntilRequestFac;
            }
        }
    }
    
    private void updateCurrentDimensionAndFaction() {
        if (this.currentFactionIndex != this.prevFactionIndex) {
            this.conquestViewingFaction = LOTRGuiMap.currentFactionList.get(this.currentFactionIndex);
            this.ticksUntilRequestFac = 40;
        }
        this.prevFactionIndex = this.currentFactionIndex;
        if (LOTRGuiMap.currentRegion != LOTRGuiMap.prevRegion) {
            LOTRGuiMap.lastViewedRegions.put(LOTRGuiMap.prevRegion, this.conquestViewingFaction);
            LOTRGuiMap.currentFactionList = LOTRGuiMap.currentRegion.factionList;
            this.conquestViewingFaction = (LOTRGuiMap.lastViewedRegions.containsKey(LOTRGuiMap.currentRegion) ? LOTRGuiMap.lastViewedRegions.get(LOTRGuiMap.currentRegion) : LOTRGuiMap.currentRegion.factionList.get(0));
            final int index = LOTRGuiMap.currentFactionList.indexOf(this.conquestViewingFaction);
            this.currentFactionIndex = index;
            this.prevFactionIndex = index;
            this.ticksUntilRequestFac = 40;
        }
        LOTRGuiMap.prevRegion = LOTRGuiMap.currentRegion;
    }
    
    public void setupZoomVariables(final float f) {
        this.zoomExp = (float)LOTRGuiMap.zoomPower;
        if (this.zoomTicks > 0) {
            final float progress = (LOTRGuiMap.zoomTicksMax - (this.zoomTicks - f)) / LOTRGuiMap.zoomTicksMax;
            this.zoomExp = this.prevZoomPower + (LOTRGuiMap.zoomPower - this.prevZoomPower) * progress;
        }
        this.zoomScale = (float)Math.pow(2.0, this.zoomExp);
        this.zoomScaleStable = (float)Math.pow(2.0, (this.zoomTicks == 0) ? LOTRGuiMap.zoomPower : Math.min(LOTRGuiMap.zoomPower, this.prevZoomPower));
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        final Tessellator tess = Tessellator.instance;
        ((Gui)this).zLevel = 0.0f;
        this.setupMapDimensions();
        this.setupZoomVariables(f);
        if (this.isConquestGrid) {
            this.loadingConquestGrid = !this.receivedFacGrids.contains(this.conquestViewingFaction);
            this.highestViewedConqStr = 0.0f;
            this.setupConquestScrollBar(i, j);
            this.buttonConquestRegions.displayString = LOTRGuiMap.currentRegion.getRegionName();
            final GuiButton buttonConquestRegions = this.buttonConquestRegions;
            final GuiButton buttonConquestRegions2 = this.buttonConquestRegions;
            final boolean b = true;
            buttonConquestRegions2.enabled = b;
            buttonConquestRegions.field_146125_m = b;
        }
        this.posX = this.prevPosX;
        this.posY = this.prevPosY;
        this.isMouseWithinMap = (i >= LOTRGuiMap.mapXMin && i < LOTRGuiMap.mapXMax && j >= LOTRGuiMap.mapYMin && j < LOTRGuiMap.mapYMax);
        if (!this.hasOverlay && !this.isFacScrolling && this.zoomTicks == 0 && Mouse.isButtonDown(0)) {
            if ((this.isMouseButtonDown == 0 || this.isMouseButtonDown == 1) && this.isMouseWithinMap) {
                if (this.isMouseButtonDown == 0) {
                    this.isMouseButtonDown = 1;
                }
                else {
                    final float x = (i - this.prevMouseX) / this.zoomScale;
                    final float y = (j - this.prevMouseY) / this.zoomScale;
                    this.posX -= x;
                    this.posY -= y;
                    if (x != 0.0f || y != 0.0f) {
                        this.selectedWaypoint = null;
                    }
                }
                this.prevMouseX = i;
                this.prevMouseY = j;
            }
        }
        else {
            this.isMouseButtonDown = 0;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.posX += this.posXMove * f;
        this.posY += this.posYMove * f;
        final boolean isSepia = this.isConquestGrid || LOTRConfig.enableSepiaMap;
        if (this.isConquestGrid) {
            this.drawDefaultBackground();
        }
        if (LOTRGuiMap.fullscreen || this.isConquestGrid) {
            super.mc.getTextureManager().bindTexture(LOTRTextures.overlayTexture);
            if (this.conquestViewingFaction != null) {
                final float[] cqColors = this.conquestViewingFaction.getFactionColorComponents();
                GL11.glColor4f(cqColors[0], cqColors[1], cqColors[2], 1.0f);
            }
            else {
                GL11.glColor4f(0.65f, 0.5f, 0.35f, 1.0f);
            }
            tess.startDrawingQuads();
            if (this.isConquestGrid) {
                final int w = 8;
                final int up = 22;
                final int down = 54;
                tess.addVertexWithUV((double)(LOTRGuiMap.mapXMin - w), (double)(LOTRGuiMap.mapYMax + down), (double)((Gui)this).zLevel, 0.0, 1.0);
                tess.addVertexWithUV((double)(LOTRGuiMap.mapXMax + w), (double)(LOTRGuiMap.mapYMax + down), (double)((Gui)this).zLevel, 1.0, 1.0);
                tess.addVertexWithUV((double)(LOTRGuiMap.mapXMax + w), (double)(LOTRGuiMap.mapYMin - up), (double)((Gui)this).zLevel, 1.0, 0.0);
                tess.addVertexWithUV((double)(LOTRGuiMap.mapXMin - w), (double)(LOTRGuiMap.mapYMin - up), (double)((Gui)this).zLevel, 0.0, 0.0);
            }
            else {
                tess.addVertexWithUV(0.0, (double)super.height, (double)((Gui)this).zLevel, 0.0, 1.0);
                tess.addVertexWithUV((double)super.width, (double)super.height, (double)((Gui)this).zLevel, 1.0, 1.0);
                tess.addVertexWithUV((double)super.width, 0.0, (double)((Gui)this).zLevel, 1.0, 0.0);
                tess.addVertexWithUV(0.0, 0.0, (double)((Gui)this).zLevel, 0.0, 0.0);
            }
            tess.draw();
            final int redW = this.isConquestGrid ? 2 : 4;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.renderGraduatedRects(LOTRGuiMap.mapXMin - 1, LOTRGuiMap.mapYMin - 1, LOTRGuiMap.mapXMax + 1, LOTRGuiMap.mapYMax + 1, -6156032, -16777216, redW);
        }
        else {
            this.drawDefaultBackground();
            this.renderGraduatedRects(LOTRGuiMap.mapXMin - 1, LOTRGuiMap.mapYMin - 1, LOTRGuiMap.mapXMax + 1, LOTRGuiMap.mapYMax + 1, -6156032, -16777216, 4);
        }
        this.setupScrollBars(i, j);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int oceanColor = LOTRTextures.getMapOceanColor(isSepia);
        drawRect(LOTRGuiMap.mapXMin, LOTRGuiMap.mapYMin, LOTRGuiMap.mapXMax, LOTRGuiMap.mapYMax, oceanColor);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (!this.isConquestGrid) {
            final String title = StatCollector.translateToLocal("lotr.gui.map.title");
            if (LOTRGuiMap.fullscreen) {
                this.drawCenteredString(title, super.width / 2, 10, 16777215);
            }
            else {
                this.drawCenteredString(title, super.width / 2, super.guiTop - 30, 16777215);
            }
        }
        if (this.hasControlZones && LOTRFaction.controlZonesEnabled((World)super.mc.theWorld)) {
            this.renderMapAndOverlay(isSepia, 1.0f, false);
            this.renderControlZone(i, j);
            GL11.glEnable(3042);
            this.renderMapAndOverlay(isSepia, 0.5f, true);
            GL11.glDisable(3042);
            String tooltip = null;
            if (this.mouseControlZone) {
                tooltip = StatCollector.translateToLocal("lotr.gui.map.controlZoneFull");
            }
            else if (this.mouseControlZoneReduced) {
                tooltip = StatCollector.translateToLocal("lotr.gui.map.controlZoneReduced");
            }
            if (tooltip != null) {
                final int strWidth = super.mc.fontRenderer.getStringWidth(tooltip);
                final int strHeight = super.mc.fontRenderer.FONT_HEIGHT;
                int rectX = i + 12;
                int rectY = j - 12;
                final int border = 3;
                final int rectWidth = strWidth + border * 2;
                final int rectHeight = strHeight + border * 2;
                final int mapBorder2 = 2;
                rectX = Math.max(rectX, LOTRGuiMap.mapXMin + mapBorder2);
                rectX = Math.min(rectX, LOTRGuiMap.mapXMax - mapBorder2 - rectWidth);
                rectY = Math.max(rectY, LOTRGuiMap.mapYMin + mapBorder2);
                rectY = Math.min(rectY, LOTRGuiMap.mapYMax - mapBorder2 - rectHeight);
                GL11.glTranslatef(0.0f, 0.0f, 300.0f);
                this.drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
                super.mc.fontRenderer.drawString(tooltip, rectX + border, rectY + border, 16777215);
                GL11.glTranslatef(0.0f, 0.0f, -300.0f);
            }
        }
        else {
            this.renderMapAndOverlay(isSepia, 1.0f, true);
            if (this.isConquestGrid && this.conquestViewingFaction != null) {
                this.requestConquestGridIfNeed(this.conquestViewingFaction);
                if (!this.loadingConquestGrid) {
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    this.setupMapClipping();
                    final float alphaFunc = GL11.glGetFloat(3010);
                    GL11.glAlphaFunc(516, 0.005f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    Collection<LOTRConquestZone> allZones = this.facConquestGrids.get(this.conquestViewingFaction);
                    if (allZones == null) {
                        allZones = new ArrayList<LOTRConquestZone>();
                    }
                    final Collection<LOTRConquestZone> zonesInView = new ArrayList<LOTRConquestZone>();
                    this.highestViewedConqStr = 0.0f;
                    float mouseOverStr = 0.0f;
                    LOTRConquestZone mouseOverZone = null;
                    LOTRConquestGrid.ConquestEffective mouseOverEffect = null;
                    for (int pass = 0; pass <= 1; ++pass) {
                        if (pass != 1 || this.highestViewedConqStr > 0.0f) {
                            final Collection<LOTRConquestZone> zoneList = (pass == 0) ? allZones : zonesInView;
                            for (final LOTRConquestZone zone : zoneList) {
                                final float strength = zone.getConquestStrength(this.conquestViewingFaction, (World)super.mc.theWorld);
                                final int[] min = LOTRConquestGrid.getMinCoordsOnMap(zone);
                                final int[] max = LOTRConquestGrid.getMaxCoordsOnMap(zone);
                                final float[] minF = this.transformMapCoords((float)min[0], (float)min[1]);
                                final float[] maxF = this.transformMapCoords((float)max[0], (float)max[1]);
                                final float minX = minF[0];
                                final float maxX = maxF[0];
                                final float minY = minF[1];
                                final float maxY = maxF[1];
                                if (maxX >= LOTRGuiMap.mapXMin && minX <= LOTRGuiMap.mapXMax && maxY >= LOTRGuiMap.mapYMin && minY <= LOTRGuiMap.mapYMax) {
                                    if (pass == 0) {
                                        if (strength > this.highestViewedConqStr) {
                                            this.highestViewedConqStr = strength;
                                        }
                                        zonesInView.add(zone);
                                    }
                                    else {
                                        if (pass != 1 || strength <= 0.0f) {
                                            continue;
                                        }
                                        float strFrac = strength / this.highestViewedConqStr;
                                        float strAlpha;
                                        strFrac = (strAlpha = MathHelper.clamp_float(strFrac, 0.0f, 1.0f));
                                        if (strength > 0.0f) {
                                            strAlpha = Math.max(strAlpha, 0.1f);
                                        }
                                        final LOTRConquestGrid.ConquestEffective effect = LOTRConquestGrid.getConquestEffectIn((World)super.mc.theWorld, zone, this.conquestViewingFaction);
                                        final int zoneColor = 0xBB0000 | Math.round(strAlpha * 255.0f) << 24;
                                        if (effect == LOTRConquestGrid.ConquestEffective.EFFECTIVE) {
                                            LOTRGuiScreenBase.drawFloatRect(minX, minY, maxX, maxY, zoneColor);
                                        }
                                        else {
                                            final int zoneColor2 = 0x1E1E1E | Math.round(strAlpha * 255.0f) << 24;
                                            if (effect == LOTRConquestGrid.ConquestEffective.ALLY_BOOST) {
                                                final float zoneYSize = maxY - minY;
                                                for (int strips = 8, l = 0; l < strips; ++l) {
                                                    final float stripYSize = zoneYSize / strips;
                                                    LOTRGuiScreenBase.drawFloatRect(minX, minY + stripYSize * l, maxX, minY + stripYSize * (l + 1), (l % 2 == 0) ? zoneColor : zoneColor2);
                                                }
                                            }
                                            else if (effect == LOTRConquestGrid.ConquestEffective.NO_EFFECT) {
                                                LOTRGuiScreenBase.drawFloatRect(minX, minY, maxX, maxY, zoneColor2);
                                            }
                                        }
                                        if (i < minX || i >= maxX || j < minY || j >= maxY) {
                                            continue;
                                        }
                                        mouseOverStr = strength;
                                        mouseOverZone = zone;
                                        mouseOverEffect = effect;
                                    }
                                }
                            }
                        }
                    }
                    GL11.glAlphaFunc(516, alphaFunc);
                    if (mouseOverZone != null && i >= LOTRGuiMap.mapXMin && i < LOTRGuiMap.mapXMax && j >= LOTRGuiMap.mapYMin && j < LOTRGuiMap.mapYMax) {
                        final int[] min2 = LOTRConquestGrid.getMinCoordsOnMap(mouseOverZone);
                        final int[] max2 = LOTRConquestGrid.getMaxCoordsOnMap(mouseOverZone);
                        final float[] minF2 = this.transformMapCoords((float)min2[0], (float)min2[1]);
                        final float[] maxF2 = this.transformMapCoords((float)max2[0], (float)max2[1]);
                        final float minX2 = minF2[0];
                        final float maxX2 = maxF2[0];
                        final float minY2 = minF2[1];
                        final float maxY2 = maxF2[1];
                        LOTRGuiScreenBase.drawFloatRect(minX2 - 1.0f, minY2 - 1.0f, maxX2 + 1.0f, minY2, -16777216);
                        LOTRGuiScreenBase.drawFloatRect(minX2 - 1.0f, maxY2, maxX2 + 1.0f, maxY2 + 1.0f, -16777216);
                        LOTRGuiScreenBase.drawFloatRect(minX2 - 1.0f, minY2, minX2, maxY2, -16777216);
                        LOTRGuiScreenBase.drawFloatRect(maxX2, minY2, maxX2 + 1.0f, maxY2, -16777216);
                        LOTRGuiScreenBase.drawFloatRect(minX2 - 2.0f, minY2 - 2.0f, maxX2 + 2.0f, minY2 - 1.0f, -4521984);
                        LOTRGuiScreenBase.drawFloatRect(minX2 - 2.0f, maxY2 + 1.0f, maxX2 + 2.0f, maxY2 + 2.0f, -4521984);
                        LOTRGuiScreenBase.drawFloatRect(minX2 - 2.0f, minY2 - 1.0f, minX2 - 1.0f, maxY2 + 1.0f, -4521984);
                        LOTRGuiScreenBase.drawFloatRect(maxX2 + 1.0f, minY2 - 1.0f, maxX2 + 2.0f, maxY2 + 1.0f, -4521984);
                        final String tooltip2 = LOTRAlignmentValues.formatConqForDisplay(mouseOverStr, false);
                        String subtip = null;
                        if (mouseOverEffect == LOTRConquestGrid.ConquestEffective.ALLY_BOOST) {
                            subtip = StatCollector.translateToLocalFormatted("lotr.gui.map.conquest.allyBoost", new Object[] { this.conquestViewingFaction.factionName() });
                        }
                        else if (mouseOverEffect == LOTRConquestGrid.ConquestEffective.NO_EFFECT) {
                            subtip = StatCollector.translateToLocal("lotr.gui.map.conquest.noEffect");
                        }
                        final int strWidth2 = super.mc.fontRenderer.getStringWidth(tooltip2);
                        final int subWidth = (subtip == null) ? 0 : super.mc.fontRenderer.getStringWidth(subtip);
                        final int strHeight2 = super.mc.fontRenderer.FONT_HEIGHT;
                        final float guiScale = (float)new ScaledResolution(super.mc, super.mc.displayWidth, super.mc.displayHeight).getScaleFactor();
                        final float subScale = (guiScale <= 2.0f) ? guiScale : (guiScale - 1.0f);
                        final float subScaleRel = subScale / guiScale;
                        int rectX2 = i + 12;
                        int rectY2 = j - 12;
                        final int border2 = 3;
                        final int iconSize = 16;
                        final int rectWidth2 = border2 * 2 + Math.max(strWidth2 + iconSize + border2, (int)(subWidth * subScaleRel));
                        int rectHeight2 = Math.max(strHeight2, iconSize) + border2 * 2;
                        if (subtip != null) {
                            rectHeight2 += border2 + (int)(strHeight2 * subScaleRel);
                        }
                        final int mapBorder3 = 2;
                        rectX2 = Math.max(rectX2, LOTRGuiMap.mapXMin + mapBorder3);
                        rectX2 = Math.min(rectX2, LOTRGuiMap.mapXMax - mapBorder3 - rectWidth2);
                        rectY2 = Math.max(rectY2, LOTRGuiMap.mapYMin + mapBorder3);
                        rectY2 = Math.min(rectY2, LOTRGuiMap.mapYMax - mapBorder3 - rectHeight2);
                        GL11.glTranslatef(0.0f, 0.0f, 300.0f);
                        this.drawFancyRect(rectX2, rectY2, rectX2 + rectWidth2, rectY2 + rectHeight2);
                        super.mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        this.drawTexturedModalRect(rectX2 + border2, rectY2 + border2, 0, 228, iconSize, iconSize);
                        super.mc.fontRenderer.drawString(tooltip2, rectX2 + iconSize + border2 * 2, rectY2 + border2 + (iconSize - strHeight2) / 2, 16777215);
                        if (subtip != null) {
                            GL11.glPushMatrix();
                            GL11.glScalef(subScaleRel, subScaleRel, 1.0f);
                            final int subX = rectX2 + border2;
                            final int subY = rectY2 + border2 * 2 + iconSize;
                            super.mc.fontRenderer.drawString(subtip, Math.round(subX / subScaleRel), Math.round(subY / subScaleRel), 16777215);
                            GL11.glPopMatrix();
                        }
                        GL11.glTranslatef(0.0f, 0.0f, -300.0f);
                    }
                    this.endMapClipping();
                    GL11.glDisable(3042);
                }
            }
        }
        ((Gui)this).zLevel += 50.0f;
        LOTRTextures.drawMapCompassBottomLeft(LOTRGuiMap.mapXMin + 12, LOTRGuiMap.mapYMax - 12, ((Gui)this).zLevel, 1.0);
        ((Gui)this).zLevel -= 50.0f;
        this.renderRoads();
        this.renderPlayers(i, j);
        if (!this.loadingConquestGrid) {
            this.renderMiniQuests((EntityPlayer)super.mc.thePlayer, i, j);
        }
        this.renderWaypoints(0, i, j);
        this.renderLabels();
        this.renderWaypoints(1, i, j);
        if (!this.loadingConquestGrid && this.selectedWaypoint != null && this.isWaypointVisible(this.selectedWaypoint)) {
            if (!this.hasOverlay) {
                this.renderWaypointTooltip(this.selectedWaypoint, true, i, j);
            }
        }
        else {
            this.selectedWaypoint = null;
        }
        if (this.isConquestGrid) {
            if (this.loadingConquestGrid) {
                drawRect(LOTRGuiMap.mapXMin, LOTRGuiMap.mapYMin, LOTRGuiMap.mapXMax, LOTRGuiMap.mapYMax, -1429949539);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                super.mc.getTextureManager().bindTexture(LOTRTextures.overlayTexture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
                tess.startDrawingQuads();
                tess.addVertexWithUV((double)LOTRGuiMap.mapXMin, (double)LOTRGuiMap.mapYMax, 0.0, 0.0, 1.0);
                tess.addVertexWithUV((double)LOTRGuiMap.mapXMax, (double)LOTRGuiMap.mapYMax, 0.0, 1.0, 1.0);
                tess.addVertexWithUV((double)LOTRGuiMap.mapXMax, (double)LOTRGuiMap.mapYMin, 0.0, 1.0, 0.0);
                tess.addVertexWithUV((double)LOTRGuiMap.mapXMin, (double)LOTRGuiMap.mapYMin, 0.0, 0.0, 0.0);
                tess.draw();
                String loadText = "";
                final LOTRConquestGrid.ConquestViewableQuery query = LOTRConquestGrid.canPlayerViewConquest((EntityPlayer)super.mc.thePlayer, this.conquestViewingFaction);
                if (query.result == LOTRConquestGrid.ConquestViewable.CAN_VIEW) {
                    loadText = StatCollector.translateToLocal("lotr.gui.map.conquest.wait");
                    for (int ellipsis = 1 + this.tickCounter / 10 % 3, k = 0; k < ellipsis; ++k) {
                        loadText += ".";
                    }
                }
                else if (query.result == LOTRConquestGrid.ConquestViewable.UNPLEDGED) {
                    loadText = StatCollector.translateToLocal("lotr.gui.map.conquest.noPledge");
                }
                else {
                    final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer);
                    final LOTRFaction pledgeFac = pd.getPledgeFaction();
                    final LOTRFactionRank needRank = query.needRank;
                    final String needAlign = LOTRAlignmentValues.formatAlignForDisplay(needRank.alignment);
                    String format = "";
                    if (query.result == LOTRConquestGrid.ConquestViewable.NEED_RANK) {
                        format = "lotr.gui.map.conquest.needRank";
                    }
                    loadText = StatCollector.translateToLocalFormatted(format, new Object[] { pledgeFac.factionName(), needRank.getFullNameWithGender(pd), needAlign });
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final int stringWidth = 250;
                final String[] splitNewline = loadText.split(Pattern.quote("\\n"));
                final List<String> loadLines = new ArrayList<String>();
                for (final String line : splitNewline) {
                    loadLines.addAll(super.fontRendererObj.listFormattedStringToWidth(line, stringWidth));
                }
                final int stringX = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2;
                int stringY = (LOTRGuiMap.mapYMin + LOTRGuiMap.mapYMax) / 2 - loadLines.size() * super.fontRendererObj.FONT_HEIGHT / 2;
                for (final String s : loadLines) {
                    this.drawCenteredString(s, stringX, stringY, 3748142);
                    stringY += super.fontRendererObj.FONT_HEIGHT;
                }
                GL11.glDisable(3042);
            }
            super.mc.getTextureManager().bindTexture(LOTRGuiMap.conquestTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(LOTRGuiMap.mapXMin - 8, LOTRGuiMap.mapYMin - 22, 0, 0, LOTRGuiMap.mapWidth + 16, LOTRGuiMap.mapHeight + 22 + 54, 512);
        }
        ((Gui)this).zLevel = 100.0f;
        if (!this.hasOverlay) {
            if (this.isMiddleEarth() && this.selectedWaypoint != null) {
                ((Gui)this).zLevel += 500.0f;
                final boolean hasUnlocked = this.selectedWaypoint.hasPlayerUnlocked((EntityPlayer)super.mc.thePlayer);
                final int fastTravel = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getFTTimer();
                final boolean canFastTravel = fastTravel <= 0 && hasUnlocked;
                String notUnlocked = "If you can read this, something has gone hideously wrong";
                if (this.selectedWaypoint instanceof LOTRCustomWaypoint) {
                    if (((LOTRCustomWaypoint)this.selectedWaypoint).isShared()) {
                        notUnlocked = StatCollector.translateToLocal("lotr.gui.map.waypointUnavailableShared");
                    }
                }
                else if (!this.selectedWaypoint.isUnlockable((EntityPlayer)super.mc.thePlayer)) {
                    notUnlocked = StatCollector.translateToLocal("lotr.gui.map.waypointUnavailableEnemy");
                }
                else {
                    notUnlocked = StatCollector.translateToLocal("lotr.gui.map.waypointUnavailableTravel");
                }
                final int waypointCooldown = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getFTCooldown(this.selectedWaypoint);
                final String ftLine1 = StatCollector.translateToLocalFormatted("lotr.gui.map.fastTravel.1", new Object[] { GameSettings.getKeyDisplayString(LOTRKeyHandler.keyBindingFastTravel.getKeyCode()) });
                final String ftLine2 = StatCollector.translateToLocalFormatted("lotr.gui.map.fastTravel.2", new Object[] { LOTRLevelData.getHMSTime(waypointCooldown) });
                final String cooldownTime = StatCollector.translateToLocalFormatted("lotr.gui.map.fastTravelTimeRemaining", new Object[] { LOTRLevelData.getHMSTime(fastTravel) });
                if (LOTRGuiMap.fullscreen || this.isConquestGrid) {
                    if (!hasUnlocked) {
                        this.renderFullscreenSubtitles(notUnlocked);
                    }
                    else if (canFastTravel) {
                        this.renderFullscreenSubtitles(ftLine1, ftLine2);
                    }
                    else {
                        this.renderFullscreenSubtitles(cooldownTime);
                    }
                }
                else {
                    final int stringHeight = super.fontRendererObj.FONT_HEIGHT;
                    final int rectWidth3 = 256;
                    final int border3 = 3;
                    int rectHeight3 = border3 * 2 + stringHeight;
                    if (canFastTravel) {
                        rectHeight3 += stringHeight + border3;
                    }
                    final int x2 = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2 - rectWidth3 / 2;
                    final int y2 = LOTRGuiMap.mapYMax + 10;
                    final int strX = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2;
                    final int strY = y2 + border3;
                    if (!hasUnlocked) {
                        this.drawFancyRect(x2, y2, x2 + rectWidth3, y2 + rectHeight3);
                        this.drawCenteredString(notUnlocked, strX, strY, 16777215);
                    }
                    else if (canFastTravel) {
                        this.drawFancyRect(x2, y2, x2 + rectWidth3, y2 + rectHeight3);
                        this.drawCenteredString(ftLine1, strX, strY, 16777215);
                        this.drawCenteredString(ftLine2, strX, strY + stringHeight + border3, 16777215);
                    }
                    else {
                        this.drawFancyRect(x2, y2, x2 + rectWidth3, y2 + rectHeight3);
                        this.drawCenteredString(cooldownTime, strX, strY, 16777215);
                    }
                }
                ((Gui)this).zLevel -= 500.0f;
            }
            else if (this.isMouseWithinMap) {
                ((Gui)this).zLevel += 500.0f;
                final float biomePosX = this.posX + (i - LOTRGuiMap.mapXMin - LOTRGuiMap.mapWidth / 2) / this.zoomScale;
                final float biomePosZ = this.posY + (j - LOTRGuiMap.mapYMin - LOTRGuiMap.mapHeight / 2) / this.zoomScale;
                final int biomePosX_int = MathHelper.floor_double((double)biomePosX);
                final int biomePosZ_int = MathHelper.floor_double((double)biomePosZ);
                LOTRBiome biome = LOTRGenLayerWorld.getBiomeOrOcean(biomePosX_int, biomePosZ_int);
                if (biome.isHiddenBiome() && !LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).hasAchievement(biome.getBiomeAchievement())) {
                    biome = LOTRBiome.ocean;
                }
                this.mouseXCoord = Math.round((biomePosX - 810.0f) * LOTRGenLayerWorld.scale);
                this.mouseZCoord = Math.round((biomePosZ - 730.0f) * LOTRGenLayerWorld.scale);
                final String biomeName = biome.getBiomeDisplayName();
                final String coords = StatCollector.translateToLocalFormatted("lotr.gui.map.coords", new Object[] { this.mouseXCoord, this.mouseZCoord });
                final String teleport = StatCollector.translateToLocalFormatted("lotr.gui.map.tp", new Object[] { GameSettings.getKeyDisplayString(LOTRKeyHandler.keyBindingMapTeleport.getKeyCode()) });
                final int stringHeight = super.fontRendererObj.FONT_HEIGHT;
                if (LOTRGuiMap.fullscreen || this.isConquestGrid) {
                    this.renderFullscreenSubtitles(biomeName, coords);
                    if (this.canTeleport()) {
                        GL11.glPushMatrix();
                        if (this.isConquestGrid) {
                            GL11.glTranslatef((float)((LOTRGuiMap.mapXMax - LOTRGuiMap.mapXMin) / 2 - 8 - super.fontRendererObj.getStringWidth(teleport) / 2), 0.0f, 0.0f);
                        }
                        else {
                            GL11.glTranslatef((float)(super.width / 2 - 30 - super.fontRendererObj.getStringWidth(teleport) / 2), 0.0f, 0.0f);
                        }
                        this.renderFullscreenSubtitles(teleport);
                        GL11.glPopMatrix();
                    }
                }
                else {
                    final int rectWidth3 = 256;
                    final int border3 = 3;
                    int rectHeight3 = border3 * 3 + stringHeight * 2;
                    if (this.canTeleport()) {
                        rectHeight3 += (stringHeight + border3) * 2;
                    }
                    final int x2 = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2 - rectWidth3 / 2;
                    final int y2 = LOTRGuiMap.mapYMax + 10;
                    this.drawFancyRect(x2, y2, x2 + rectWidth3, y2 + rectHeight3);
                    final int strX = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2;
                    int strY = y2 + border3;
                    this.drawCenteredString(biomeName, strX, strY, 16777215);
                    strY += stringHeight + border3;
                    this.drawCenteredString(coords, strX, strY, 16777215);
                    if (this.canTeleport()) {
                        this.drawCenteredString(teleport, strX, strY + (stringHeight + border3) * 2, 16777215);
                    }
                }
                ((Gui)this).zLevel -= 500.0f;
            }
        }
        if (this.isConquestGrid) {
            final String s2 = StatCollector.translateToLocalFormatted("lotr.gui.map.conquest.title", new Object[] { this.conquestViewingFaction.factionName() });
            final int x3 = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2;
            final int y3 = LOTRGuiMap.mapYMin - 14;
            LOTRTickHandlerClient.drawAlignmentText(super.fontRendererObj, x3 - super.fontRendererObj.getStringWidth(s2) / 2, y3, s2, 1.0f);
            if (!this.loadingConquestGrid) {
                final int keyBorder = 8;
                final int keyWidth = 24;
                final int keyHeight = 12;
                final int iconSize2 = 16;
                final int iconGap = keyBorder / 2;
                final float guiScale2 = (float)new ScaledResolution(super.mc, super.mc.displayWidth, super.mc.displayHeight).getScaleFactor();
                final float labelScale = (guiScale2 <= 2.0f) ? guiScale2 : (guiScale2 - 1.0f);
                final float labelScaleRel = labelScale / guiScale2;
                super.mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(LOTRGuiMap.mapXMax - keyBorder - iconSize2, LOTRGuiMap.mapYMax - keyBorder - iconSize2, 0, 228, iconSize2, iconSize2);
                for (int pass2 = 0; pass2 <= 1; ++pass2) {
                    for (int m = 0; m <= 10; ++m) {
                        final float frac = (10 - m) / 10.0f;
                        final float strFrac2 = frac * this.highestViewedConqStr;
                        final int x4 = LOTRGuiMap.mapXMax - keyBorder - iconSize2 - iconGap - m * keyWidth;
                        final int x5 = x4 - keyWidth;
                        final int y4 = LOTRGuiMap.mapYMax - keyBorder - (iconSize2 - keyHeight) / 2;
                        final int y5 = y4 - keyHeight;
                        if (pass2 == 0) {
                            final int color = 0xBB0000 | Math.round(frac * 255.0f) << 24;
                            drawRect(x5, y5, x4, y4, color);
                        }
                        else if (pass2 == 1 && m % 2 == 0) {
                            final String keyLabel = LOTRAlignmentValues.formatConqForDisplay(strFrac2, false);
                            final int strX2 = (int)((x5 + keyWidth / 2) / labelScaleRel);
                            final int strY2 = (int)((y5 + keyHeight / 2) / labelScaleRel) - super.fontRendererObj.FONT_HEIGHT / 2;
                            GL11.glPushMatrix();
                            GL11.glScalef(labelScaleRel, labelScaleRel, 1.0f);
                            this.drawCenteredString(keyLabel, strX2, strY2, 16777215);
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
            if (this.hasConquestScrollBar()) {
                super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.facScrollX, this.facScrollY, 0, 128, this.facScrollWidth, this.facScrollHeight);
                for (int factions = LOTRGuiMap.currentFactionList.size(), index = 0; index < factions; ++index) {
                    final LOTRFaction faction = LOTRGuiMap.currentFactionList.get(index);
                    final float[] factionColors = faction.getFactionColorComponents();
                    final float shade = 0.6f;
                    GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0f);
                    float xMin = index / (float)factions;
                    float xMax = (index + 1) / (float)factions;
                    xMin = this.facScrollX + this.facScrollBorder + xMin * (this.facScrollWidth - this.facScrollBorder * 2);
                    xMax = this.facScrollX + this.facScrollBorder + xMax * (this.facScrollWidth - this.facScrollBorder * 2);
                    final float yMin = (float)(this.facScrollY + this.facScrollBorder);
                    final float yMax = (float)(this.facScrollY + this.facScrollHeight - this.facScrollBorder);
                    final float minU = (0 + this.facScrollBorder) / 256.0f;
                    final float maxU = (0 + this.facScrollWidth - this.facScrollBorder) / 256.0f;
                    final float minV = (128 + this.facScrollBorder) / 256.0f;
                    final float maxV = (128 + this.facScrollHeight - this.facScrollBorder) / 256.0f;
                    tess.startDrawingQuads();
                    tess.addVertexWithUV((double)xMin, (double)yMax, (double)((Gui)this).zLevel, (double)minU, (double)maxV);
                    tess.addVertexWithUV((double)xMax, (double)yMax, (double)((Gui)this).zLevel, (double)maxU, (double)maxV);
                    tess.addVertexWithUV((double)xMax, (double)yMin, (double)((Gui)this).zLevel, (double)maxU, (double)minV);
                    tess.addVertexWithUV((double)xMin, (double)yMin, (double)((Gui)this).zLevel, (double)minU, (double)minV);
                    tess.draw();
                }
                super.mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final int scroll = (int)(this.currentFacScroll * (this.facScrollWidth - this.facScrollBorder * 2 - this.facScrollWidgetWidth));
                this.drawTexturedModalRect(this.facScrollX + this.facScrollBorder + scroll, this.facScrollY + this.facScrollBorder, 0, 142, this.facScrollWidgetWidth, this.facScrollWidgetHeight);
            }
        }
        if (!this.hasOverlay && this.hasControlZones) {
            String s2 = StatCollector.translateToLocalFormatted("lotr.gui.map.controlZones", new Object[] { this.controlZoneFaction.factionName() });
            final int x3 = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2;
            final int y3 = LOTRGuiMap.mapYMin + 20;
            LOTRTickHandlerClient.drawAlignmentText(super.fontRendererObj, x3 - super.fontRendererObj.getStringWidth(s2) / 2, y3, s2, 1.0f);
            if (!LOTRFaction.controlZonesEnabled((World)super.mc.theWorld)) {
                s2 = StatCollector.translateToLocal("lotr.gui.map.controlZonesDisabled");
                LOTRTickHandlerClient.drawAlignmentText(super.fontRendererObj, x3 - super.fontRendererObj.getStringWidth(s2) / 2, LOTRGuiMap.mapYMin + LOTRGuiMap.mapHeight / 2, s2, 1.0f);
            }
        }
        final boolean buttonVisible = this.buttonOverlayFunction.field_146125_m;
        this.buttonOverlayFunction.field_146125_m = false;
        super.drawScreen(i, j, f);
        this.buttonOverlayFunction.field_146125_m = buttonVisible;
        this.renderMapWidgets(i, j);
        if (this.hasOverlay) {
            GL11.glTranslatef(0.0f, 0.0f, 500.0f);
            int overlayBorder = 10;
            if (LOTRGuiMap.fullscreen) {
                overlayBorder = 40;
            }
            final int rectX3 = LOTRGuiMap.mapXMin + overlayBorder;
            final int rectY3 = LOTRGuiMap.mapYMin + overlayBorder;
            final int rectX4 = LOTRGuiMap.mapXMax - overlayBorder;
            final int rectY4 = LOTRGuiMap.mapYMax - overlayBorder;
            this.drawFancyRect(rectX3, rectY3, rectX4, rectY4);
            if (this.overlayLines != null) {
                final int stringX2 = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2;
                int stringY2 = rectY3 + 3 + super.mc.fontRenderer.FONT_HEIGHT;
                final int stringWidth2 = (int)((LOTRGuiMap.mapWidth - overlayBorder * 2) * 0.75f);
                final List<String> displayLines = new ArrayList<String>();
                for (final String s3 : this.overlayLines) {
                    displayLines.addAll(super.fontRendererObj.listFormattedStringToWidth(s3, stringWidth2));
                }
                for (final String s4 : displayLines) {
                    this.drawCenteredString(s4, stringX2, stringY2, 16777215);
                    stringY2 += super.mc.fontRenderer.FONT_HEIGHT;
                }
                stringY2 += super.mc.fontRenderer.FONT_HEIGHT;
                if (this.sharingWaypoint) {
                    this.fellowshipDrawGUI.clearMouseOverFellowship();
                    this.mouseOverRemoveSharedFellowship = null;
                    final int iconWidth = 8;
                    final int iconGap2 = 8;
                    final int fullWidth = this.fellowshipDrawGUI.xSize + iconGap2 + iconWidth;
                    final int fsX = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2 - fullWidth / 2;
                    int fsY = stringY2;
                    this.scrollPaneWPShares.paneX0 = fsX;
                    final LOTRGuiScrollPane scrollPaneWPShares = this.scrollPaneWPShares;
                    final int n3 = fsX + fullWidth;
                    final LOTRGuiFellowships fellowshipDrawGUI = this.fellowshipDrawGUI;
                    scrollPaneWPShares.scrollBarX0 = n3 + 2 + 2;
                    this.scrollPaneWPShares.paneY0 = fsY;
                    final LOTRGuiScrollPane scrollPaneWPShares2 = this.scrollPaneWPShares;
                    final int n4 = fsY;
                    final int font_HEIGHT = super.mc.fontRenderer.FONT_HEIGHT;
                    final LOTRGuiFellowships fellowshipDrawGUI2 = this.fellowshipDrawGUI;
                    scrollPaneWPShares2.paneY1 = n4 + (font_HEIGHT + 5) * this.displayedWPShares;
                    this.scrollPaneWPShares.mouseDragScroll(i, j);
                    final int[] sharesMinMax = this.scrollPaneWPShares.getMinMaxIndices(this.displayedWPShareList, this.displayedWPShares);
                    for (int index2 = sharesMinMax[0]; index2 <= sharesMinMax[1]; ++index2) {
                        final UUID fsID = this.displayedWPShareList.get(index2);
                        final LOTRFellowshipClient fs = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getClientFellowshipByID(fsID);
                        if (fs != null) {
                            this.fellowshipDrawGUI.drawFellowshipEntry(fs, fsX, fsY, i, j, false, fullWidth);
                            final int iconRemoveX = fsX + this.fellowshipDrawGUI.xSize + iconGap2;
                            final int iconRemoveY = fsY;
                            boolean mouseOverRemove = false;
                            if (fs == this.fellowshipDrawGUI.getMouseOverFellowship()) {
                                mouseOverRemove = (i >= iconRemoveX && i <= iconRemoveX + iconWidth && j >= iconRemoveY && j <= iconRemoveY + iconWidth);
                                if (mouseOverRemove) {
                                    this.mouseOverRemoveSharedFellowship = fs;
                                }
                            }
                            super.mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            this.drawTexturedModalRect(iconRemoveX, iconRemoveY, 8, 16 + (mouseOverRemove ? 0 : iconWidth), iconWidth, iconWidth);
                            final int n5 = stringY2;
                            final int font_HEIGHT2 = super.mc.fontRenderer.FONT_HEIGHT;
                            final LOTRGuiFellowships fellowshipDrawGUI3 = this.fellowshipDrawGUI;
                            stringY2 = (fsY = n5 + (font_HEIGHT2 + 5));
                        }
                    }
                    if (this.scrollPaneWPShares.hasScrollBar) {
                        this.scrollPaneWPShares.drawScrollBar();
                    }
                }
                if (this.creatingWaypointNew || this.renamingWaypoint || this.sharingWaypointNew) {
                    this.nameWPTextField.field_146209_f = (rectX3 + rectX4) / 2 - this.nameWPTextField.field_146218_h / 2;
                    this.nameWPTextField.field_146210_g = stringY2;
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0f, 0.0f, ((Gui)this).zLevel);
                    this.nameWPTextField.drawTextBox();
                    GL11.glPopMatrix();
                    if (this.sharingWaypointNew && this.isFellowshipAlreadyShared(this.nameWPTextField.getText(), (LOTRCustomWaypoint)this.selectedWaypoint)) {
                        final String alreadyShared = StatCollector.translateToLocal("lotr.gui.map.customWaypoint.shareNew.already");
                        final int sx = this.nameWPTextField.field_146209_f + this.nameWPTextField.field_146218_h + 6;
                        final int sy = this.nameWPTextField.field_146210_g + this.nameWPTextField.field_146219_i / 2 - super.fontRendererObj.FONT_HEIGHT / 2;
                        super.fontRendererObj.drawString(alreadyShared, sx, sy, 16711680);
                    }
                    stringY2 += this.nameWPTextField.field_146219_i + super.mc.fontRenderer.FONT_HEIGHT;
                }
                stringY2 += super.mc.fontRenderer.FONT_HEIGHT;
                if (this.buttonOverlayFunction.field_146125_m) {
                    this.buttonOverlayFunction.enabled = true;
                    if (this.creatingWaypointNew || this.renamingWaypoint) {
                        this.buttonOverlayFunction.enabled = this.isValidWaypointName(this.nameWPTextField.getText());
                    }
                    if (this.sharingWaypointNew) {
                        this.buttonOverlayFunction.enabled = this.isExistingUnsharedFellowshipName(this.nameWPTextField.getText(), (LOTRCustomWaypoint)this.selectedWaypoint);
                    }
                    this.buttonOverlayFunction.field_146128_h = stringX2 - this.buttonOverlayFunction.field_146120_f / 2;
                    this.buttonOverlayFunction.field_146129_i = stringY2;
                    if (this.sharingWaypoint) {
                        this.buttonOverlayFunction.field_146129_i = rectY4 - 3 - super.mc.fontRenderer.FONT_HEIGHT - this.buttonOverlayFunction.field_146121_g;
                    }
                    this.buttonOverlayFunction.drawButton(super.mc, i, j);
                }
            }
            GL11.glTranslatef(0.0f, 0.0f, -500.0f);
        }
    }
    
    private void setupScrollBars(final int i, final int j) {
        LOTRGuiMap.maxDisplayedWPShares = (LOTRGuiMap.fullscreen ? 8 : 5);
        if (this.selectedWaypoint != null && this.hasOverlay && this.sharingWaypoint) {
            this.displayedWPShareList = ((LOTRCustomWaypoint)this.selectedWaypoint).getSharedFellowshipIDs();
            this.displayedWPShares = this.displayedWPShareList.size();
            this.scrollPaneWPShares.hasScrollBar = false;
            if (this.displayedWPShares > LOTRGuiMap.maxDisplayedWPShares) {
                this.displayedWPShares = LOTRGuiMap.maxDisplayedWPShares;
                this.scrollPaneWPShares.hasScrollBar = true;
            }
        }
        else {
            this.displayedWPShareList = null;
            this.displayedWPShares = 0;
            this.scrollPaneWPShares.hasScrollBar = false;
            this.scrollPaneWPShares.mouseDragScroll(i, j);
        }
    }
    
    public void renderMapAndOverlay(final boolean sepia, final float alpha, final boolean overlay) {
        LOTRGuiMap.mapXMin_W = LOTRGuiMap.mapXMin;
        LOTRGuiMap.mapXMax_W = LOTRGuiMap.mapXMax;
        LOTRGuiMap.mapYMin_W = LOTRGuiMap.mapYMin;
        LOTRGuiMap.mapYMax_W = LOTRGuiMap.mapYMax;
        final float mapScaleX = LOTRGuiMap.mapWidth / this.zoomScale;
        final float mapScaleY = LOTRGuiMap.mapHeight / this.zoomScale;
        double minU = (this.posX - mapScaleX / 2.0f) / (double)LOTRGenLayerWorld.imageWidth;
        double maxU = (this.posX + mapScaleX / 2.0f) / (double)LOTRGenLayerWorld.imageWidth;
        double minV = (this.posY - mapScaleY / 2.0f) / (double)LOTRGenLayerWorld.imageHeight;
        double maxV = (this.posY + mapScaleY / 2.0f) / (double)LOTRGenLayerWorld.imageHeight;
        if (minU < 0.0) {
            LOTRGuiMap.mapXMin_W = LOTRGuiMap.mapXMin + (int)Math.round((0.0 - minU) * LOTRGenLayerWorld.imageWidth * this.zoomScale);
            minU = 0.0;
            if (maxU < 0.0) {
                maxU = 0.0;
                LOTRGuiMap.mapXMax_W = LOTRGuiMap.mapXMin_W;
            }
        }
        if (maxU > 1.0) {
            LOTRGuiMap.mapXMax_W = LOTRGuiMap.mapXMax - (int)Math.round((maxU - 1.0) * LOTRGenLayerWorld.imageWidth * this.zoomScale);
            maxU = 1.0;
            if (minU > 1.0) {
                minU = 1.0;
                LOTRGuiMap.mapXMin_W = LOTRGuiMap.mapXMax_W;
            }
        }
        if (minV < 0.0) {
            LOTRGuiMap.mapYMin_W = LOTRGuiMap.mapYMin + (int)Math.round((0.0 - minV) * LOTRGenLayerWorld.imageHeight * this.zoomScale);
            minV = 0.0;
            if (maxV < 0.0) {
                maxV = 0.0;
                LOTRGuiMap.mapYMax_W = LOTRGuiMap.mapYMin_W;
            }
        }
        if (maxV > 1.0) {
            LOTRGuiMap.mapYMax_W = LOTRGuiMap.mapYMax - (int)Math.round((maxV - 1.0) * LOTRGenLayerWorld.imageHeight * this.zoomScale);
            maxV = 1.0;
            if (minV > 1.0) {
                minV = 1.0;
                LOTRGuiMap.mapYMin_W = LOTRGuiMap.mapYMax_W;
            }
        }
        LOTRTextures.drawMap((EntityPlayer)super.mc.thePlayer, sepia, LOTRGuiMap.mapXMin_W, LOTRGuiMap.mapXMax_W, LOTRGuiMap.mapYMin_W, LOTRGuiMap.mapYMax_W, ((Gui)this).zLevel, minU, maxU, minV, maxV, alpha);
        if (overlay && !isOSRS()) {
            LOTRTextures.drawMapOverlay((EntityPlayer)super.mc.thePlayer, LOTRGuiMap.mapXMin, LOTRGuiMap.mapXMax, LOTRGuiMap.mapYMin, LOTRGuiMap.mapYMax, ((Gui)this).zLevel, minU, maxU, minV, maxV);
        }
    }
    
    private void renderGraduatedRects(final int x1, final int y1, final int x2, final int y2, final int c1, final int c2, final int w) {
        final float[] rgb1 = new Color(c1).getColorComponents(null);
        final float[] rgb2 = new Color(c2).getColorComponents(null);
        for (int l = w - 1; l >= 0; --l) {
            final float f = l / (float)(w - 1);
            final float r = rgb1[0] + (rgb2[0] - rgb1[0]) * f;
            final float g = rgb1[1] + (rgb2[1] - rgb1[1]) * f;
            final float b = rgb1[2] + (rgb2[2] - rgb1[2]) * f;
            final int color = new Color(r, g, b).getRGB() - 16777216;
            drawRect(x1 - l, y1 - l, x2 + l, y2 + l, color);
        }
    }
    
    private void renderMapWidgets(final int mouseX, final int mouseY) {
        this.widgetAddCWP.visible = (!this.hasOverlay && this.isMiddleEarth());
        this.widgetDelCWP.visible = (!this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && !((LOTRCustomWaypoint)this.selectedWaypoint).isShared());
        this.widgetRenameCWP.visible = (!this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && !((LOTRCustomWaypoint)this.selectedWaypoint).isShared());
        this.widgetToggleWPs.visible = !this.hasOverlay;
        this.widgetToggleWPs.setTexVIndex(LOTRGuiMap.showWP ? 0 : 1);
        this.widgetToggleCWPs.visible = !this.hasOverlay;
        this.widgetToggleCWPs.setTexVIndex(LOTRGuiMap.showCWP ? 0 : 1);
        this.widgetToggleHiddenSWPs.visible = !this.hasOverlay;
        this.widgetToggleHiddenSWPs.setTexVIndex(LOTRGuiMap.showHiddenSWP ? 1 : 0);
        this.widgetZoomIn.visible = !this.hasOverlay;
        this.widgetZoomIn.setTexVIndex((LOTRGuiMap.zoomPower >= 4) ? 1 : 0);
        this.widgetZoomOut.visible = !this.hasOverlay;
        this.widgetZoomOut.setTexVIndex((LOTRGuiMap.zoomPower <= -3) ? 1 : 0);
        this.widgetFullScreen.visible = !this.hasOverlay;
        this.widgetSepia.visible = !this.hasOverlay;
        this.widgetLabels.visible = !this.hasOverlay;
        this.widgetShareCWP.visible = (!this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && !((LOTRCustomWaypoint)this.selectedWaypoint).isShared());
        this.widgetHideSWP.visible = (!this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && ((LOTRCustomWaypoint)this.selectedWaypoint).isShared() && !((LOTRCustomWaypoint)this.selectedWaypoint).isSharedHidden());
        this.widgetUnhideSWP.visible = (!this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && ((LOTRCustomWaypoint)this.selectedWaypoint).isShared() && ((LOTRCustomWaypoint)this.selectedWaypoint).isSharedHidden());
        LOTRGuiMapWidget mouseOverWidget = null;
        for (final LOTRGuiMapWidget widget : LOTRGuiMap.mapWidgets) {
            if (widget.visible) {
                super.mc.getTextureManager().bindTexture(LOTRGuiMap.mapIconsTexture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(LOTRGuiMap.mapXMin + widget.getMapXPos(LOTRGuiMap.mapWidth), LOTRGuiMap.mapYMin + widget.getMapYPos(LOTRGuiMap.mapHeight), widget.getTexU(), widget.getTexV(), widget.width, widget.width);
                if (!widget.isMouseOver(mouseX - LOTRGuiMap.mapXMin, mouseY - LOTRGuiMap.mapYMin, LOTRGuiMap.mapWidth, LOTRGuiMap.mapHeight)) {
                    continue;
                }
                mouseOverWidget = widget;
            }
        }
        if (mouseOverWidget != null) {
            final float z = ((Gui)this).zLevel;
            final int stringWidth = 200;
            final List desc = super.fontRendererObj.listFormattedStringToWidth(mouseOverWidget.getTranslatedName(), stringWidth);
            this.func_146283_a(desc, mouseX, mouseY);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ((Gui)this).zLevel = z;
        }
    }
    
    private void renderFullscreenSubtitles(final String... lines) {
        final int strX = LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2;
        int strY = LOTRGuiMap.mapYMax + 7;
        if (this.isConquestGrid) {
            strY = LOTRGuiMap.mapYMax + 26;
        }
        final int border = super.fontRendererObj.FONT_HEIGHT + 3;
        if (lines.length == 1) {
            strY += border / 2;
        }
        for (final String s : lines) {
            this.drawCenteredString(s, strX, strY, 16777215);
            strY += border;
        }
    }
    
    private void renderPlayers(final int mouseX, final int mouseY) {
        String mouseOverPlayerName = null;
        double mouseOverPlayerX = 0.0;
        double mouseOverPlayerY = 0.0;
        double distanceMouseOverPlayer = Double.MAX_VALUE;
        final int iconWidthHalf = 4;
        final Map<UUID, PlayerLocationInfo> playersToRender = new HashMap<UUID, PlayerLocationInfo>();
        playersToRender.putAll(LOTRGuiMap.playerLocations);
        if (this.isMiddleEarth()) {
            playersToRender.put(super.mc.thePlayer.getUniqueID(), new PlayerLocationInfo(super.mc.thePlayer.getGameProfile(), ((Entity)super.mc.thePlayer).posX, ((Entity)super.mc.thePlayer).posZ));
        }
        for (final Map.Entry<UUID, PlayerLocationInfo> entry : playersToRender.entrySet()) {
            final UUID player = entry.getKey();
            final PlayerLocationInfo info = entry.getValue();
            final GameProfile profile = info.profile;
            final String playerName = profile.getName();
            final double playerPosX = info.posX;
            final double playerPosZ = info.posZ;
            final float[] pos = this.transformCoords(playerPosX, playerPosZ);
            final int playerX = Math.round(pos[0]);
            final int playerY = Math.round(pos[1]);
            final double distToPlayer = this.renderPlayerIcon(profile, playerName, playerX, playerY, mouseX, mouseY);
            if (distToPlayer <= iconWidthHalf + 3 && distToPlayer <= distanceMouseOverPlayer) {
                mouseOverPlayerName = playerName;
                mouseOverPlayerX = playerX;
                mouseOverPlayerY = playerY;
                distanceMouseOverPlayer = distToPlayer;
            }
        }
        if (mouseOverPlayerName != null && !this.hasOverlay && !this.loadingConquestGrid) {
            final int strWidth = super.mc.fontRenderer.getStringWidth(mouseOverPlayerName);
            final int strHeight = super.mc.fontRenderer.FONT_HEIGHT;
            int rectX = (int)Math.round(mouseOverPlayerX);
            int rectY = (int)Math.round(mouseOverPlayerY);
            rectY += iconWidthHalf + 3;
            final int border = 3;
            final int rectWidth = strWidth + border * 2;
            rectX -= rectWidth / 2;
            final int rectHeight = strHeight + border * 2;
            final int mapBorder2 = 2;
            rectX = Math.max(rectX, LOTRGuiMap.mapXMin + mapBorder2);
            rectX = Math.min(rectX, LOTRGuiMap.mapXMax - mapBorder2 - rectWidth);
            rectY = Math.max(rectY, LOTRGuiMap.mapYMin + mapBorder2);
            rectY = Math.min(rectY, LOTRGuiMap.mapYMax - mapBorder2 - rectHeight);
            GL11.glTranslatef(0.0f, 0.0f, 300.0f);
            this.drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
            super.mc.fontRenderer.drawString(mouseOverPlayerName, rectX + border, rectY + border, 16777215);
            GL11.glTranslatef(0.0f, 0.0f, -300.0f);
        }
    }
    
    private double renderPlayerIcon(final GameProfile profile, final String playerName, double playerX, double playerY, final int mouseX, final int mouseY) {
        final Tessellator tessellator = Tessellator.instance;
        final int iconWidthHalf = 4;
        final int iconBorder = iconWidthHalf + 1;
        playerX = Math.max(LOTRGuiMap.mapXMin + iconBorder, playerX);
        playerX = Math.min(LOTRGuiMap.mapXMax - iconBorder - 1, playerX);
        playerY = Math.max(LOTRGuiMap.mapYMin + iconBorder, playerY);
        playerY = Math.min(LOTRGuiMap.mapYMax - iconBorder - 1, playerY);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation skin = AbstractClientPlayer.locationStevePng;
        if (profile.getId().equals(super.mc.thePlayer.getUniqueID())) {
            skin = super.mc.thePlayer.getLocationSkin();
        }
        else {
            final Map map = super.mc.func_152342_ad().func_152788_a(profile);
            final MinecraftProfileTexture.Type type = MinecraftProfileTexture.Type.SKIN;
            if (map.containsKey(type)) {
                skin = super.mc.func_152342_ad().func_152792_a((MinecraftProfileTexture)map.get(type), type);
            }
        }
        super.mc.getTextureManager().bindTexture(skin);
        double iconMinU = 0.125;
        double iconMaxU = 0.25;
        double iconMinV = 0.25;
        double iconMaxV = 0.5;
        final double playerX_d = playerX + 0.5;
        final double playerY_d = playerY + 0.5;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(playerX_d - iconWidthHalf, playerY_d + iconWidthHalf, (double)((Gui)this).zLevel, iconMinU, iconMaxV);
        tessellator.addVertexWithUV(playerX_d + iconWidthHalf, playerY_d + iconWidthHalf, (double)((Gui)this).zLevel, iconMaxU, iconMaxV);
        tessellator.addVertexWithUV(playerX_d + iconWidthHalf, playerY_d - iconWidthHalf, (double)((Gui)this).zLevel, iconMaxU, iconMinV);
        tessellator.addVertexWithUV(playerX_d - iconWidthHalf, playerY_d - iconWidthHalf, (double)((Gui)this).zLevel, iconMinU, iconMinV);
        tessellator.draw();
        iconMinU = 0.625;
        iconMaxU = 0.75;
        iconMinV = 0.25;
        iconMaxV = 0.5;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(playerX_d - iconWidthHalf - 0.5, playerY_d + iconWidthHalf + 0.5, (double)((Gui)this).zLevel, iconMinU, iconMaxV);
        tessellator.addVertexWithUV(playerX_d + iconWidthHalf + 0.5, playerY_d + iconWidthHalf + 0.5, (double)((Gui)this).zLevel, iconMaxU, iconMaxV);
        tessellator.addVertexWithUV(playerX_d + iconWidthHalf + 0.5, playerY_d - iconWidthHalf - 0.5, (double)((Gui)this).zLevel, iconMaxU, iconMinV);
        tessellator.addVertexWithUV(playerX_d - iconWidthHalf - 0.5, playerY_d - iconWidthHalf - 0.5, (double)((Gui)this).zLevel, iconMinU, iconMinV);
        tessellator.draw();
        final double dx = playerX - mouseX;
        final double dy = playerY - mouseY;
        final double distToPlayer = Math.sqrt(dx * dx + dy * dy);
        return distToPlayer;
    }
    
    private void renderMiniQuests(final EntityPlayer entityplayer, final int mouseX, final int mouseY) {
        if (this.hasOverlay) {
            return;
        }
        LOTRMiniQuest mouseOverQuest = null;
        int mouseOverQuestX = 0;
        int mouseOverQuestY = 0;
        double distanceMouseOverQuest = Double.MAX_VALUE;
        final List<LOTRMiniQuest> quests = (List<LOTRMiniQuest>)LOTRLevelData.getData(entityplayer).getActiveMiniQuests();
        for (final LOTRMiniQuest quest : quests) {
            final ChunkCoordinates location = quest.getLastLocation();
            if (location != null) {
                final float[] pos = this.transformCoords((float)location.posX, (float)location.posZ);
                int questX = Math.round(pos[0]);
                int questY = Math.round(pos[1]);
                final float scale = 0.5f;
                final float invScale = 1.0f / scale;
                final IIcon icon = LOTRGuiMap.questBookIcon.getIconIndex();
                int iconWidthHalf = icon.getIconWidth() / 2;
                iconWidthHalf *= (int)scale;
                final int iconBorder = iconWidthHalf + 1;
                questX = Math.max(LOTRGuiMap.mapXMin + iconBorder, questX);
                questX = Math.min(LOTRGuiMap.mapXMax - iconBorder - 1, questX);
                questY = Math.max(LOTRGuiMap.mapYMin + iconBorder, questY);
                questY = Math.min(LOTRGuiMap.mapYMax - iconBorder - 1, questY);
                int iconX = Math.round(questX * invScale);
                int iconY = Math.round(questY * invScale);
                iconX -= iconWidthHalf;
                iconY -= iconWidthHalf;
                GL11.glScalef(scale, scale, scale);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(2896);
                GL11.glEnable(2884);
                LOTRGuiMenuBase.renderItem.renderItemAndEffectIntoGUI(super.mc.fontRenderer, super.mc.getTextureManager(), LOTRGuiMap.questBookIcon, iconX, iconY);
                GL11.glDisable(2896);
                GL11.glEnable(3008);
                GL11.glScalef(invScale, invScale, invScale);
                final double dx = questX - mouseX;
                final double dy = questY - mouseY;
                final double distToQuest = Math.sqrt(dx * dx + dy * dy);
                if (distToQuest > iconWidthHalf + 3 || distToQuest > distanceMouseOverQuest) {
                    continue;
                }
                mouseOverQuest = quest;
                mouseOverQuestX = questX;
                mouseOverQuestY = questY;
                distanceMouseOverQuest = distToQuest;
            }
        }
        if (mouseOverQuest != null && !this.hasOverlay) {
            final String name = mouseOverQuest.entityName;
            final int stringWidth = super.mc.fontRenderer.getStringWidth(name);
            final int stringHeight = super.mc.fontRenderer.FONT_HEIGHT;
            int x = mouseOverQuestX;
            int y = mouseOverQuestY;
            y += 7;
            final int border = 3;
            final int rectWidth = stringWidth + border * 2;
            x -= rectWidth / 2;
            final int rectHeight = stringHeight + border * 2;
            final int mapBorder2 = 2;
            x = Math.max(x, LOTRGuiMap.mapXMin + mapBorder2);
            x = Math.min(x, LOTRGuiMap.mapXMax - mapBorder2 - rectWidth);
            y = Math.max(y, LOTRGuiMap.mapYMin + mapBorder2);
            y = Math.min(y, LOTRGuiMap.mapYMax - mapBorder2 - rectHeight);
            GL11.glTranslatef(0.0f, 0.0f, 300.0f);
            this.drawFancyRect(x, y, x + rectWidth, y + rectHeight);
            super.mc.fontRenderer.drawString(name, x + border, y + border, 16777215);
            GL11.glTranslatef(0.0f, 0.0f, -300.0f);
        }
    }
    
    private void renderControlZone(final int mouseX, final int mouseY) {
        this.mouseControlZone = false;
        this.mouseControlZoneReduced = false;
        final LOTRFaction faction = this.controlZoneFaction;
        if (faction.factionDimension == LOTRDimension.MIDDLE_EARTH) {
            final List<LOTRControlZone> controlZones = faction.getControlZones();
            if (!controlZones.isEmpty()) {
                final Tessellator tessellator = Tessellator.instance;
                this.setupMapClipping();
                GL11.glDisable(3553);
                for (int pass = 0; pass <= 2; ++pass) {
                    int color = 16711680;
                    if (pass == 1) {
                        color = 5570560;
                    }
                    if (pass == 0) {
                        color = 16733525;
                    }
                    for (final LOTRControlZone zone : controlZones) {
                        float radius = (float)zone.radius;
                        if (pass == 2) {
                            --radius;
                        }
                        if (pass == 0) {
                            radius = (float)(zone.radius + this.controlZoneFaction.getControlZoneReducedRange());
                        }
                        final float radiusWorld = (float)LOTRWaypoint.mapToWorldR(radius);
                        tessellator.startDrawing(9);
                        tessellator.setColorOpaque_I(color);
                        final int sides = 100;
                        for (int l = sides - 1; l >= 0; --l) {
                            final float angle = l / (float)sides * 2.0f * 3.1415927f;
                            double x = zone.xCoord;
                            double z = zone.zCoord;
                            x += MathHelper.cos(angle) * radiusWorld;
                            z += MathHelper.sin(angle) * radiusWorld;
                            final float[] trans = this.transformCoords(x, z);
                            tessellator.addVertex((double)trans[0], (double)trans[1], (double)((Gui)this).zLevel);
                        }
                        tessellator.draw();
                        if (!this.mouseControlZone || !this.mouseControlZoneReduced) {
                            final float[] trans2 = this.transformCoords((float)zone.xCoord, (float)zone.zCoord);
                            final float dx = mouseX - trans2[0];
                            final float dy = mouseY - trans2[1];
                            final float rScaled = radius * this.zoomScale;
                            if (dx * dx + dy * dy > rScaled * rScaled) {
                                continue;
                            }
                            if (pass >= 1) {
                                this.mouseControlZone = true;
                            }
                            else {
                                if (pass != 0) {
                                    continue;
                                }
                                this.mouseControlZoneReduced = true;
                            }
                        }
                    }
                }
                GL11.glEnable(3553);
                this.endMapClipping();
            }
        }
    }
    
    private void renderRoads() {
        if ((!LOTRGuiMap.showWP && !LOTRGuiMap.showCWP) || !LOTRFixedStructures.hasMapFeatures((World)super.mc.theWorld)) {
            return;
        }
        this.renderRoads(this.hasMapLabels());
    }
    
    public void renderRoads(final boolean labels) {
        float roadZoomlerp = (this.zoomExp + 3.3f) / 2.2f;
        roadZoomlerp = Math.min(roadZoomlerp, 1.0f);
        if (!this.enableZoomOutWPFading) {
            roadZoomlerp = 1.0f;
        }
        if (roadZoomlerp > 0.0f) {
            for (final LOTRRoads road : LOTRRoads.allRoads) {
                int interval = Math.round(400.0f / this.zoomScaleStable);
                interval = Math.max(interval, 1);
                for (int i = 0; i < road.roadPoints.length; i += interval) {
                    final LOTRRoads.RoadPoint point = road.roadPoints[i];
                    final float[] pos = this.transformCoords(point.x, point.z);
                    final float x = pos[0];
                    final float y = pos[1];
                    if (x >= LOTRGuiMap.mapXMin && x < LOTRGuiMap.mapXMax && y >= LOTRGuiMap.mapYMin && y < LOTRGuiMap.mapYMax) {
                        double roadWidth = 1.0;
                        int roadColor = 0;
                        float roadAlpha = roadZoomlerp;
                        if (isOSRS()) {
                            roadWidth = 3.0 * this.zoomScale;
                            roadColor = 6575407;
                            roadAlpha = 1.0f;
                        }
                        final double roadWidthLess1 = roadWidth - 1.0;
                        GL11.glDisable(3553);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        final Tessellator tessellator = Tessellator.instance;
                        tessellator.startDrawingQuads();
                        tessellator.setColorRGBA_I(roadColor, (int)(roadAlpha * 255.0f));
                        tessellator.addVertex(x - roadWidthLess1, y + roadWidth, (double)((Gui)this).zLevel);
                        tessellator.addVertex(x + roadWidth, y + roadWidth, (double)((Gui)this).zLevel);
                        tessellator.addVertex(x + roadWidth, y - roadWidthLess1, (double)((Gui)this).zLevel);
                        tessellator.addVertex(x - roadWidthLess1, y - roadWidthLess1, (double)((Gui)this).zLevel);
                        tessellator.draw();
                        GL11.glDisable(3042);
                        GL11.glEnable(3553);
                    }
                    if (labels) {
                        final int clip = 200;
                        if (x >= LOTRGuiMap.mapXMin - clip && x <= LOTRGuiMap.mapXMax + clip && y >= LOTRGuiMap.mapYMin - clip && y <= LOTRGuiMap.mapYMax + clip) {
                            float zoomlerp = (this.zoomExp + 1.0f) / 4.0f;
                            final float scale;
                            zoomlerp = (scale = Math.min(zoomlerp, 1.0f));
                            final String name = road.getDisplayName();
                            final int nameWidth = super.fontRendererObj.getStringWidth(name);
                            final int nameInterval = (int)((nameWidth * 2 + 100) * 200.0f / this.zoomScaleStable);
                            if (i % nameInterval < interval) {
                                boolean endNear = false;
                                final double dMax = (nameWidth / 2.0 + 25.0) * scale;
                                final double dMaxSq = dMax * dMax;
                                for (final LOTRRoads.RoadPoint end : road.endpoints) {
                                    final float[] endPos = this.transformCoords(end.x, end.z);
                                    final float endX = endPos[0];
                                    final float endY = endPos[1];
                                    final float dx = x - endX;
                                    final float dy = y - endY;
                                    final double dSq = dx * dx + dy * dy;
                                    if (dSq < dMaxSq) {
                                        endNear = true;
                                    }
                                }
                                if (!endNear && zoomlerp > 0.0f) {
                                    this.setupMapClipping();
                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(x, y, 0.0f);
                                    GL11.glScalef(scale, scale, scale);
                                    final LOTRRoads.RoadPoint nextPoint = road.roadPoints[Math.min(i + 1, road.roadPoints.length - 1)];
                                    final LOTRRoads.RoadPoint prevPoint = road.roadPoints[Math.max(i - 1, 0)];
                                    final double grad = (nextPoint.z - prevPoint.z) / (nextPoint.x - prevPoint.x);
                                    float angle = (float)Math.atan(grad);
                                    angle = (float)Math.toDegrees(angle);
                                    if (Math.abs(angle) > 90.0f) {
                                        angle += 180.0f;
                                    }
                                    GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
                                    float alpha = zoomlerp;
                                    alpha *= 0.8f;
                                    final int alphaI = LOTRClientProxy.getAlphaInt(alpha);
                                    GL11.glEnable(3042);
                                    GL11.glBlendFunc(770, 771);
                                    final int strX = -nameWidth / 2;
                                    final int strY = -15;
                                    super.fontRendererObj.drawString(name, strX + 1, strY + 1, 0 + (alphaI << 24));
                                    super.fontRendererObj.drawString(name, strX, strY, 16777215 + (alphaI << 24));
                                    GL11.glDisable(3042);
                                    GL11.glPopMatrix();
                                    this.endMapClipping();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean isWaypointVisible(final LOTRAbstractWaypoint wp) {
        if (wp instanceof LOTRCustomWaypoint) {
            final LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)wp;
            return (!cwp.isShared() || !cwp.isSharedHidden() || LOTRGuiMap.showHiddenSWP) && LOTRGuiMap.showCWP;
        }
        return LOTRGuiMap.showWP;
    }
    
    private void renderWaypoints(final int pass, final int mouseX, final int mouseY) {
        if ((!LOTRGuiMap.showWP && !LOTRGuiMap.showCWP && !LOTRGuiMap.showHiddenSWP) || !LOTRFixedStructures.hasMapFeatures((World)super.mc.theWorld)) {
            return;
        }
        this.renderWaypoints(LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getAllAvailableWaypoints(), pass, mouseX, mouseY, this.hasMapLabels(), false);
    }
    
    public void renderWaypoints(final List<LOTRAbstractWaypoint> waypoints, final int pass, final int mouseX, final int mouseY, final boolean labels, final boolean overrideToggles) {
        this.setupMapClipping();
        LOTRAbstractWaypoint mouseOverWP = null;
        double distanceMouseOverWP = Double.MAX_VALUE;
        float wpZoomlerp = (this.zoomExp + 3.3f) / 2.2f;
        wpZoomlerp = Math.min(wpZoomlerp, 1.0f);
        if (!this.enableZoomOutWPFading) {
            wpZoomlerp = 1.0f;
        }
        if (wpZoomlerp > 0.0f) {
            for (final LOTRAbstractWaypoint waypoint : waypoints) {
                final boolean unlocked = super.mc.thePlayer != null && waypoint.hasPlayerUnlocked((EntityPlayer)super.mc.thePlayer);
                final boolean hidden = waypoint.isHidden();
                final boolean custom = waypoint instanceof LOTRCustomWaypoint;
                final boolean shared = waypoint instanceof LOTRCustomWaypoint && ((LOTRCustomWaypoint)waypoint).isShared();
                if ((this.isWaypointVisible(waypoint) || overrideToggles) && (!hidden || unlocked)) {
                    final float[] pos = this.transformCoords((float)waypoint.getXCoord(), (float)waypoint.getZCoord());
                    final float x = pos[0];
                    final float y = pos[1];
                    final int clip = 200;
                    if (x < LOTRGuiMap.mapXMin - clip || x > LOTRGuiMap.mapXMax + clip || y < LOTRGuiMap.mapYMin - clip || y > LOTRGuiMap.mapYMax + clip) {
                        continue;
                    }
                    if (pass == 0) {
                        final float wpAlpha = wpZoomlerp;
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        if (isOSRS()) {
                            final float osScale = 0.33f;
                            GL11.glPushMatrix();
                            GL11.glScalef(0.33f, 0.33f, 1.0f);
                            super.mc.getTextureManager().bindTexture(LOTRTextures.osrsTexture);
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            this.drawTexturedModalRectFloat(x / 0.33f - 8.0f, y / 0.33f - 8.0f, 0, 0, 15.0f, 15.0f);
                            GL11.glPopMatrix();
                        }
                        else {
                            super.mc.getTextureManager().bindTexture(LOTRGuiMap.mapIconsTexture);
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, wpAlpha);
                            this.drawTexturedModalRectFloat(x - 2.0f, y - 2.0f, 0 + (unlocked ? 4 : 0), 200 + (shared ? 8 : (custom ? 4 : 0)), 4.0f, 4.0f);
                        }
                        GL11.glDisable(3042);
                        if (labels) {
                            float zoomlerp = (this.zoomExp + 1.0f) / 4.0f;
                            zoomlerp = Math.min(zoomlerp, 1.0f);
                            if (zoomlerp > 0.0f) {
                                GL11.glPushMatrix();
                                GL11.glTranslatef(x, y, 0.0f);
                                final float scale = zoomlerp;
                                GL11.glScalef(scale, scale, scale);
                                float alpha = zoomlerp;
                                alpha *= 0.8f;
                                int alphaI = (int)(alpha * 255.0f);
                                alphaI = MathHelper.clamp_int(alphaI, 4, 255);
                                GL11.glEnable(3042);
                                GL11.glBlendFunc(770, 771);
                                final String s = waypoint.getDisplayName();
                                final int strX = -super.fontRendererObj.getStringWidth(s) / 2;
                                final int strY = -15;
                                super.fontRendererObj.drawString(s, strX + 1, strY + 1, 0 + (alphaI << 24));
                                super.fontRendererObj.drawString(s, strX, strY, 16777215 + (alphaI << 24));
                                GL11.glDisable(3042);
                                GL11.glPopMatrix();
                            }
                        }
                    }
                    if (pass != 1 || waypoint == this.selectedWaypoint || x < LOTRGuiMap.mapXMin - 2 || x > LOTRGuiMap.mapXMax + 2 || y < LOTRGuiMap.mapYMin - 2 || y > LOTRGuiMap.mapYMax + 2) {
                        continue;
                    }
                    final double dx = x - mouseX;
                    final double dy = y - mouseY;
                    final double distToWP = Math.sqrt(dx * dx + dy * dy);
                    if (distToWP > 5.0 || distToWP > distanceMouseOverWP) {
                        continue;
                    }
                    mouseOverWP = waypoint;
                    distanceMouseOverWP = distToWP;
                }
            }
        }
        if (pass == 1 && mouseOverWP != null && !this.hasOverlay && !this.loadingConquestGrid) {
            this.renderWaypointTooltip(mouseOverWP, false, mouseX, mouseY);
        }
        this.endMapClipping();
    }
    
    private void renderWaypointTooltip(final LOTRAbstractWaypoint waypoint, final boolean selected, final int mouseX, final int mouseY) {
        final String name = waypoint.getDisplayName();
        final int wpX = waypoint.getXCoord();
        final int wpZ = waypoint.getZCoord();
        final int wpY = waypoint.getYCoordDisplay();
        String coords;
        if (wpY >= 0) {
            coords = StatCollector.translateToLocalFormatted("lotr.gui.map.coordsY", new Object[] { wpX, wpY, wpZ });
        }
        else {
            coords = StatCollector.translateToLocalFormatted("lotr.gui.map.coords", new Object[] { wpX, wpZ });
        }
        final String loreText = waypoint.getLoreText((EntityPlayer)super.mc.thePlayer);
        final float guiScale = (float)new ScaledResolution(super.mc, super.mc.displayWidth, super.mc.displayHeight).getScaleFactor();
        float loreScale = guiScale - 1.0f;
        if (guiScale <= 2.0f) {
            loreScale = guiScale;
        }
        final float loreScaleRel = loreScale / guiScale;
        final float loreScaleRelInv = 1.0f / loreScaleRel;
        final int loreFontHeight = MathHelper.ceiling_double_int((double)(super.fontRendererObj.FONT_HEIGHT * loreScaleRel));
        final float[] pos = this.transformCoords((float)wpX, (float)wpZ);
        int rectX = Math.round(pos[0]);
        int rectY = Math.round(pos[1]);
        rectY += 5;
        final int border = 3;
        final int stringHeight = super.fontRendererObj.FONT_HEIGHT;
        int innerRectWidth = super.fontRendererObj.getStringWidth(name);
        if (selected) {
            innerRectWidth = Math.max(innerRectWidth, super.fontRendererObj.getStringWidth(coords));
            if (loreText != null) {
                innerRectWidth += 50;
                innerRectWidth = Math.round(innerRectWidth * (loreScaleRel / 0.66667f));
            }
        }
        final int rectWidth = innerRectWidth + border * 2;
        rectX -= rectWidth / 2;
        int rectHeight = border * 2 + stringHeight;
        if (selected) {
            rectHeight += border + stringHeight;
            if (loreText != null) {
                rectHeight += border;
                rectHeight += super.fontRendererObj.listFormattedStringToWidth(loreText, (int)(innerRectWidth * loreScaleRelInv)).size() * loreFontHeight;
                rectHeight += border;
            }
        }
        final int mapBorder2 = 2;
        rectX = Math.max(rectX, LOTRGuiMap.mapXMin + mapBorder2);
        rectX = Math.min(rectX, LOTRGuiMap.mapXMax - mapBorder2 - rectWidth);
        rectY = Math.max(rectY, LOTRGuiMap.mapYMin + mapBorder2);
        rectY = Math.min(rectY, LOTRGuiMap.mapYMax - mapBorder2 - rectHeight);
        final int stringX = rectX + rectWidth / 2;
        int stringY = rectY + border;
        GL11.glTranslatef(0.0f, 0.0f, 300.0f);
        this.drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
        this.drawCenteredString(name, stringX, stringY, 16777215);
        stringY += super.fontRendererObj.FONT_HEIGHT + border;
        if (selected) {
            this.drawCenteredString(coords, stringX, stringY, 16777215);
            stringY += super.fontRendererObj.FONT_HEIGHT + border * 2;
            if (loreText != null) {
                GL11.glPushMatrix();
                GL11.glScalef(loreScaleRel, loreScaleRel, 1.0f);
                final List loreLines = super.fontRendererObj.listFormattedStringToWidth(loreText, (int)(innerRectWidth * loreScaleRelInv));
                for (int l = 0; l < loreLines.size(); ++l) {
                    final String line = loreLines.get(l);
                    this.drawCenteredString(line, (int)(stringX * loreScaleRelInv), (int)(stringY * loreScaleRelInv), 16777215);
                    stringY += loreFontHeight;
                }
                GL11.glPopMatrix();
            }
        }
        GL11.glTranslatef(0.0f, 0.0f, -300.0f);
    }
    
    private void renderLabels() {
        if (!this.hasMapLabels()) {
            return;
        }
        this.setupMapClipping();
        final LOTRMapLabels[] allMapLabels;
        final LOTRMapLabels[] allLabels = allMapLabels = LOTRMapLabels.allMapLabels();
        for (final LOTRMapLabels label : allMapLabels) {
            final float[] pos = this.transformMapCoords((float)label.posX, (float)label.posY);
            final float x = pos[0];
            final float y = pos[1];
            final float zoomlerp = (this.zoomExp - label.minZoom) / (label.maxZoom - label.minZoom);
            Label_0447: {
                if (zoomlerp > 0.0f && zoomlerp < 1.0f) {
                    float alpha = (0.5f - Math.abs(zoomlerp - 0.5f)) / 0.5f;
                    alpha *= 0.7f;
                    if (isOSRS()) {
                        if (alpha < 0.3f) {
                            break Label_0447;
                        }
                        alpha = 1.0f;
                    }
                    GL11.glPushMatrix();
                    GL11.glTranslatef(x, y, 0.0f);
                    final float scale = this.zoomScale * label.scale;
                    GL11.glScalef(scale, scale, scale);
                    if (!isOSRS()) {
                        GL11.glRotatef((float)label.angle, 0.0f, 0.0f, 1.0f);
                    }
                    int alphaI = (int)(alpha * 255.0f);
                    alphaI = MathHelper.clamp_int(alphaI, 4, 255);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    final float alphaFunc = GL11.glGetFloat(3010);
                    GL11.glAlphaFunc(516, 0.01f);
                    final String s = label.getDisplayName();
                    final int strX = -super.fontRendererObj.getStringWidth(s) / 2;
                    final int strY = -super.fontRendererObj.FONT_HEIGHT / 2;
                    if (isOSRS()) {
                        if (label.scale > 2.5f) {
                            super.fontRendererObj.drawString(s, strX + 1, strY + 1, 0 + (alphaI << 24));
                            super.fontRendererObj.drawString(s, strX, strY, 16755200 + (alphaI << 24));
                        }
                        else {
                            super.fontRendererObj.drawString(s, strX + 1, strY + 1, 0 + (alphaI << 24));
                            super.fontRendererObj.drawString(s, strX, strY, 16777215 + (alphaI << 24));
                        }
                    }
                    else {
                        super.fontRendererObj.drawString(s, strX, strY, 16777215 + (alphaI << 24));
                    }
                    GL11.glAlphaFunc(516, alphaFunc);
                    GL11.glDisable(3042);
                    GL11.glPopMatrix();
                }
            }
        }
        this.endMapClipping();
    }
    
    private void setupMapClipping() {
        final ScaledResolution sr = new ScaledResolution(super.mc, super.mc.displayWidth, super.mc.displayHeight);
        final int scale = sr.getScaleFactor();
        GL11.glEnable(3089);
        GL11.glScissor(LOTRGuiMap.mapXMin * scale, (super.height - LOTRGuiMap.mapYMax) * scale, LOTRGuiMap.mapWidth * scale, LOTRGuiMap.mapHeight * scale);
    }
    
    private void endMapClipping() {
        GL11.glDisable(3089);
    }
    
    private float[] transformCoords(float x, float z) {
        x = x / LOTRGenLayerWorld.scale + 810.0f;
        z = z / LOTRGenLayerWorld.scale + 730.0f;
        return this.transformMapCoords(x, z);
    }
    
    private float[] transformCoords(final double x, final double z) {
        return this.transformCoords((float)x, (float)z);
    }
    
    private float[] transformMapCoords(float x, float z) {
        x -= this.posX;
        z -= this.posY;
        x *= this.zoomScale;
        z *= this.zoomScale;
        x += LOTRGuiMap.mapXMin + LOTRGuiMap.mapWidth / 2;
        z += LOTRGuiMap.mapYMin + LOTRGuiMap.mapHeight / 2;
        return new float[] { x, z };
    }
    
    private void drawFancyRect(final int x1, final int y1, final int x2, final int y2) {
        drawRect(x1, y1, x2, y2, -1073741824);
        this.drawHorizontalLine(x1 - 1, x2, y1 - 1, -6156032);
        this.drawHorizontalLine(x1 - 1, x2, y2, -6156032);
        this.drawVerticalLine(x1 - 1, y1 - 1, y2, -6156032);
        this.drawVerticalLine(x2, y1 - 1, y2, -6156032);
    }
    
    private boolean isValidWaypointName(final String name) {
        return !StringUtils.isBlank((CharSequence)name);
    }
    
    private LOTRFellowshipClient getFellowshipByName(final String name) {
        final String fsName = StringUtils.strip(name);
        return LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getClientFellowshipByName(fsName);
    }
    
    private boolean isExistingFellowshipName(final String name) {
        final LOTRFellowshipClient fs = this.getFellowshipByName(name);
        return fs != null;
    }
    
    private boolean isExistingUnsharedFellowshipName(final String name, final LOTRCustomWaypoint waypoint) {
        final LOTRFellowshipClient fs = this.getFellowshipByName(name);
        return fs != null && !waypoint.hasSharedFellowship(fs.getFellowshipID());
    }
    
    private boolean isFellowshipAlreadyShared(final String name, final LOTRCustomWaypoint waypoint) {
        return this.isExistingFellowshipName(name) && !this.isExistingUnsharedFellowshipName(name, waypoint);
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (this.hasOverlay) {
            if (this.creatingWaypointNew && this.nameWPTextField.textboxKeyTyped(c, i)) {
                return;
            }
            if (this.renamingWaypoint && this.nameWPTextField.textboxKeyTyped(c, i)) {
                return;
            }
            if (this.sharingWaypointNew && this.nameWPTextField.textboxKeyTyped(c, i)) {
                return;
            }
            if (i == 1 || i == super.mc.gameSettings.keyBindInventory.getKeyCode()) {
                if (this.creatingWaypointNew) {
                    this.openOverlayCreate();
                }
                else if (this.sharingWaypointNew) {
                    this.openOverlayShare();
                }
                else {
                    this.closeOverlay();
                }
            }
        }
        else {
            if (!this.loadingConquestGrid) {
                if (i == LOTRKeyHandler.keyBindingFastTravel.getKeyCode() && this.isMiddleEarth() && this.selectedWaypoint != null && this.selectedWaypoint.hasPlayerUnlocked((EntityPlayer)super.mc.thePlayer) && LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getFTTimer() <= 0) {
                    final LOTRPacketFastTravel packet = new LOTRPacketFastTravel(this.selectedWaypoint);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
                    super.mc.thePlayer.closeScreen();
                    return;
                }
                if (this.selectedWaypoint == null && i == LOTRKeyHandler.keyBindingMapTeleport.getKeyCode() && this.isMouseWithinMap && this.canTeleport()) {
                    final LOTRPacketMapTp packet2 = new LOTRPacketMapTp(this.mouseXCoord, this.mouseZCoord);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet2);
                    super.mc.thePlayer.closeScreen();
                    return;
                }
            }
            if (this.hasControlZones && (i == 1 || i == super.mc.gameSettings.keyBindInventory.getKeyCode())) {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new LOTRGuiFactions());
                return;
            }
            super.keyTyped(c, i);
        }
    }
    
    private void closeOverlay() {
        this.hasOverlay = false;
        this.overlayLines = null;
        this.creatingWaypoint = false;
        this.creatingWaypointNew = false;
        this.deletingWaypoint = false;
        this.renamingWaypoint = false;
        this.sharingWaypoint = false;
        this.sharingWaypointNew = false;
        final GuiButton buttonOverlayFunction = this.buttonOverlayFunction;
        final GuiButton buttonOverlayFunction2 = this.buttonOverlayFunction;
        final boolean b = false;
        buttonOverlayFunction2.field_146125_m = b;
        buttonOverlayFunction.enabled = b;
        this.nameWPTextField.setText("");
    }
    
    private void openOverlayCreate() {
        this.closeOverlay();
        this.hasOverlay = true;
        this.creatingWaypoint = true;
        final int numWaypoints = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getCustomWaypoints().size();
        final int maxWaypoints = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getMaxCustomWaypoints();
        final int remaining = maxWaypoints - numWaypoints;
        if (remaining <= 0) {
            this.overlayLines = new String[] { StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.allUsed.1", new Object[] { maxWaypoints }), "", StatCollector.translateToLocal("lotr.gui.map.customWaypoint.allUsed.2") };
        }
        else {
            this.overlayLines = new String[] { StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.create.1", new Object[] { numWaypoints, maxWaypoints }), "", StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.create.2", new Object[0]) };
            this.buttonOverlayFunction.field_146125_m = true;
            this.buttonOverlayFunction.displayString = StatCollector.translateToLocal("lotr.gui.map.customWaypoint.create.button");
        }
    }
    
    private void openOverlayCreateNew() {
        this.closeOverlay();
        this.hasOverlay = true;
        this.creatingWaypointNew = true;
        this.overlayLines = new String[] { StatCollector.translateToLocal("lotr.gui.map.customWaypoint.createNew.1") };
        this.buttonOverlayFunction.field_146125_m = true;
        this.buttonOverlayFunction.displayString = StatCollector.translateToLocal("lotr.gui.map.customWaypoint.createNew.button");
    }
    
    private void openOverlayDelete() {
        this.closeOverlay();
        this.hasOverlay = true;
        this.deletingWaypoint = true;
        this.overlayLines = new String[] { StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.delete.1", new Object[] { this.selectedWaypoint.getDisplayName() }) };
        this.buttonOverlayFunction.field_146125_m = true;
        this.buttonOverlayFunction.displayString = StatCollector.translateToLocal("lotr.gui.map.customWaypoint.delete.button");
    }
    
    private void openOverlayRename() {
        this.closeOverlay();
        this.hasOverlay = true;
        this.renamingWaypoint = true;
        this.overlayLines = new String[] { StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.rename.1", new Object[] { this.selectedWaypoint.getDisplayName() }) };
        this.buttonOverlayFunction.field_146125_m = true;
        this.buttonOverlayFunction.displayString = StatCollector.translateToLocal("lotr.gui.map.customWaypoint.rename.button");
    }
    
    private void openOverlayShare() {
        this.closeOverlay();
        this.hasOverlay = true;
        this.sharingWaypoint = true;
        this.overlayLines = new String[] { StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.share.1", new Object[] { this.selectedWaypoint.getDisplayName() }) };
        this.buttonOverlayFunction.field_146125_m = true;
        this.buttonOverlayFunction.displayString = StatCollector.translateToLocal("lotr.gui.map.customWaypoint.share.button");
    }
    
    private void openOverlayShareNew() {
        this.closeOverlay();
        this.hasOverlay = true;
        this.sharingWaypointNew = true;
        this.overlayLines = new String[] { StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.shareNew.1", new Object[] { this.selectedWaypoint.getDisplayName() }) };
        this.buttonOverlayFunction.field_146125_m = true;
        this.buttonOverlayFunction.displayString = StatCollector.translateToLocal("lotr.gui.map.customWaypoint.shareNew.button");
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        LOTRGuiMapWidget mouseWidget = null;
        for (final LOTRGuiMapWidget widget : LOTRGuiMap.mapWidgets) {
            if (widget.isMouseOver(i - LOTRGuiMap.mapXMin, j - LOTRGuiMap.mapYMin, LOTRGuiMap.mapWidth, LOTRGuiMap.mapHeight)) {
                mouseWidget = widget;
                break;
            }
        }
        if (this.hasOverlay && (this.creatingWaypointNew || this.renamingWaypoint || this.sharingWaypointNew)) {
            this.nameWPTextField.mouseClicked(i, j, k);
        }
        if (this.hasOverlay && k == 0 && this.sharingWaypoint && this.mouseOverRemoveSharedFellowship != null) {
            final String fsName = this.mouseOverRemoveSharedFellowship.getName();
            final LOTRPacketShareCWP packet = new LOTRPacketShareCWP((LOTRCustomWaypoint)this.selectedWaypoint, fsName, false);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            return;
        }
        if (!this.hasOverlay && k == 0 && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint) {
            final LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)this.selectedWaypoint;
            if (!cwp.isShared()) {
                if (mouseWidget == this.widgetDelCWP) {
                    this.openOverlayDelete();
                    return;
                }
                if (mouseWidget == this.widgetRenameCWP) {
                    this.openOverlayRename();
                    return;
                }
                if (mouseWidget == this.widgetShareCWP) {
                    this.openOverlayShare();
                    return;
                }
            }
            else {
                if (mouseWidget == this.widgetHideSWP) {
                    final LOTRPacketCWPSharedHide packet2 = new LOTRPacketCWPSharedHide(cwp, true);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet2);
                    this.selectedWaypoint = null;
                    return;
                }
                if (mouseWidget == this.widgetUnhideSWP) {
                    final LOTRPacketCWPSharedHide packet2 = new LOTRPacketCWPSharedHide(cwp, false);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet2);
                    return;
                }
            }
        }
        if (!this.hasOverlay && k == 0 && this.isMiddleEarth() && mouseWidget == this.widgetAddCWP) {
            this.openOverlayCreate();
            return;
        }
        if (!this.hasOverlay && k == 0) {
            if (mouseWidget == this.widgetToggleWPs) {
                LOTRGuiMap.showWP = !LOTRGuiMap.showWP;
                LOTRClientProxy.sendClientInfoPacket(null, null);
                return;
            }
            if (mouseWidget == this.widgetToggleCWPs) {
                LOTRGuiMap.showCWP = !LOTRGuiMap.showCWP;
                LOTRClientProxy.sendClientInfoPacket(null, null);
                return;
            }
            if (mouseWidget == this.widgetToggleHiddenSWPs) {
                LOTRGuiMap.showHiddenSWP = !LOTRGuiMap.showHiddenSWP;
                LOTRClientProxy.sendClientInfoPacket(null, null);
                return;
            }
            if (this.zoomTicks == 0) {
                if (mouseWidget == this.widgetZoomIn) {
                    this.zoomIn();
                    return;
                }
                if (mouseWidget == this.widgetZoomOut) {
                    this.zoomOut();
                    return;
                }
            }
            if (mouseWidget == this.widgetFullScreen) {
                LOTRGuiMap.fullscreen = !LOTRGuiMap.fullscreen;
                final ScaledResolution sr = new ScaledResolution(super.mc, super.mc.displayWidth, super.mc.displayHeight);
                this.setWorldAndResolution(super.mc, sr.getScaledWidth(), sr.getScaledHeight());
                return;
            }
            if (mouseWidget == this.widgetSepia) {
                LOTRConfig.toggleSepia();
                return;
            }
            if (mouseWidget == this.widgetLabels) {
                this.toggleMapLabels();
                return;
            }
        }
        if (!this.hasOverlay && !this.loadingConquestGrid && k == 0 && this.isMouseWithinMap) {
            this.selectedWaypoint = null;
            double distanceSelectedWP = Double.MAX_VALUE;
            final List<LOTRAbstractWaypoint> waypoints = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getAllAvailableWaypoints();
            for (final LOTRAbstractWaypoint waypoint : waypoints) {
                final boolean unlocked = waypoint.hasPlayerUnlocked((EntityPlayer)super.mc.thePlayer);
                final boolean hidden = waypoint.isHidden();
                if (this.isWaypointVisible(waypoint) && (!hidden || unlocked)) {
                    final float[] pos = this.transformCoords((float)waypoint.getXCoord(), (float)waypoint.getZCoord());
                    final float x = pos[0];
                    final float y = pos[1];
                    final float dx = x - i;
                    final float dy = y - j;
                    final double distToWP = Math.sqrt(dx * dx + dy * dy);
                    if (distToWP > 5.0 || distToWP > distanceSelectedWP) {
                        continue;
                    }
                    this.selectedWaypoint = waypoint;
                    distanceSelectedWP = distToWP;
                }
            }
        }
        super.mouseClicked(i, j, k);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonOverlayFunction) {
                if (this.creatingWaypoint) {
                    this.openOverlayCreateNew();
                }
                else if (this.creatingWaypointNew && this.isValidWaypointName(this.nameWPTextField.getText())) {
                    final String waypointName = this.nameWPTextField.getText();
                    final LOTRPacketCreateCWP packet = new LOTRPacketCreateCWP(waypointName);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
                    this.closeOverlay();
                }
                else if (this.deletingWaypoint) {
                    final LOTRPacketDeleteCWP packet2 = new LOTRPacketDeleteCWP((LOTRCustomWaypoint)this.selectedWaypoint);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet2);
                    this.closeOverlay();
                    this.selectedWaypoint = null;
                }
                else if (this.renamingWaypoint && this.isValidWaypointName(this.nameWPTextField.getText())) {
                    final String newName = this.nameWPTextField.getText();
                    final LOTRPacketRenameCWP packet3 = new LOTRPacketRenameCWP((LOTRCustomWaypoint)this.selectedWaypoint, newName);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet3);
                    this.closeOverlay();
                }
                else if (this.sharingWaypoint) {
                    this.openOverlayShareNew();
                }
                else if (this.sharingWaypointNew && this.isExistingUnsharedFellowshipName(this.nameWPTextField.getText(), (LOTRCustomWaypoint)this.selectedWaypoint)) {
                    final String fsName = this.nameWPTextField.getText();
                    final LOTRPacketShareCWP packet4 = new LOTRPacketShareCWP((LOTRCustomWaypoint)this.selectedWaypoint, fsName, true);
                    LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet4);
                    this.openOverlayShare();
                }
            }
            else if (button == this.buttonConquestRegions) {
                final List<LOTRDimension.DimensionRegion> regionList = LOTRDimension.MIDDLE_EARTH.dimensionRegions;
                if (!regionList.isEmpty()) {
                    int i = regionList.indexOf(LOTRGuiMap.currentRegion);
                    i = IntMath.mod(++i, regionList.size());
                    LOTRGuiMap.currentRegion = regionList.get(i);
                    this.updateCurrentDimensionAndFaction();
                    this.setCurrentScrollFromFaction();
                }
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
    
    private void handleMapKeyboardMovement() {
        this.prevPosX += this.posXMove;
        this.prevPosY += this.posYMove;
        this.posXMove = 0.0f;
        this.posYMove = 0.0f;
        if (!this.hasOverlay) {
            final float move = 12.0f / (float)Math.pow(this.zoomScale, 0.800000011920929);
            if (this.isKeyDownAndNotMouse(super.mc.gameSettings.keyBindLeft) || Keyboard.isKeyDown(203)) {
                this.posXMove -= move;
            }
            if (this.isKeyDownAndNotMouse(super.mc.gameSettings.keyBindRight) || Keyboard.isKeyDown(205)) {
                this.posXMove += move;
            }
            if (this.isKeyDownAndNotMouse(super.mc.gameSettings.keyBindForward) || Keyboard.isKeyDown(200)) {
                this.posYMove -= move;
            }
            if (this.isKeyDownAndNotMouse(super.mc.gameSettings.keyBindBack) || Keyboard.isKeyDown(208)) {
                this.posYMove += move;
            }
            if (this.posXMove != 0.0f || this.posYMove != 0.0f) {
                this.selectedWaypoint = null;
            }
        }
    }
    
    private boolean isKeyDownAndNotMouse(final KeyBinding keybinding) {
        return keybinding.getKeyCode() >= 0 && GameSettings.isKeyDown(keybinding);
    }
    
    public void handleMouseInput() {
        super.handleMouseInput();
        int k = Mouse.getEventDWheel();
        if (this.isConquestGrid && this.hasConquestScrollBar() && this.mouseInFacScroll && k != 0) {
            if (k < 0) {
                this.currentFactionIndex = Math.min(this.currentFactionIndex + 1, Math.max(0, LOTRGuiMap.currentFactionList.size() - 1));
            }
            else if (k > 0) {
                this.currentFactionIndex = Math.max(this.currentFactionIndex - 1, 0);
            }
            this.setCurrentScrollFromFaction();
            return;
        }
        if (!this.hasOverlay && this.zoomTicks == 0) {
            if (k < 0 && LOTRGuiMap.zoomPower > -3) {
                this.zoomOut();
                return;
            }
            if (k > 0 && LOTRGuiMap.zoomPower < 4) {
                this.zoomIn();
                return;
            }
        }
        if (this.hasOverlay && k != 0) {
            k = Integer.signum(k);
            if (this.scrollPaneWPShares.hasScrollBar && this.scrollPaneWPShares.mouseOver) {
                final int l = this.displayedWPShareList.size() - this.displayedWPShares;
                this.scrollPaneWPShares.mouseWheelScroll(k, l);
            }
        }
    }
    
    private void zoomOut() {
        this.zoom(-1);
    }
    
    private void zoomIn() {
        this.zoom(1);
    }
    
    private void zoom(final int boost) {
        this.prevZoomPower = LOTRGuiMap.zoomPower;
        LOTRGuiMap.zoomPower += boost;
        this.zoomTicks = LOTRGuiMap.zoomTicksMax;
        this.selectedWaypoint = null;
    }
    
    public void setCWPProtectionMessage(final IChatComponent message) {
        this.closeOverlay();
        this.hasOverlay = true;
        this.creatingWaypoint = false;
        this.creatingWaypointNew = false;
        final String protection = StatCollector.translateToLocalFormatted("lotr.gui.map.customWaypoint.protected.1", new Object[] { message.getFormattedText() });
        final String warning = StatCollector.translateToLocal("lotr.gui.map.customWaypoint.protected.2");
        this.overlayLines = new String[] { protection, "", warning };
    }
    
    private boolean canTeleport() {
        if (!this.isMiddleEarth()) {
            return false;
        }
        if (this.loadingConquestGrid) {
            return false;
        }
        final int chunkX = MathHelper.floor_double(((Entity)super.mc.thePlayer).posX) >> 4;
        final int chunkZ = MathHelper.floor_double(((Entity)super.mc.thePlayer).posZ) >> 4;
        if (super.mc.theWorld.getChunkProvider().provideChunk(chunkX, chunkZ) instanceof EmptyChunk) {
            return false;
        }
        this.requestIsOp();
        return this.isPlayerOp;
    }
    
    private void requestIsOp() {
        if (super.mc.isSingleplayer()) {
            final MinecraftServer server = (MinecraftServer)super.mc.getIntegratedServer();
            this.isPlayerOp = (server.worldServers[0].getWorldInfo().areCommandsAllowed() && server.getServerOwner().equalsIgnoreCase(super.mc.thePlayer.getGameProfile().getName()));
        }
        else {
            final LOTRPacketIsOpRequest packet = new LOTRPacketIsOpRequest();
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    private boolean isMiddleEarth() {
        return ((Entity)super.mc.thePlayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID;
    }
    
    private void requestConquestGridIfNeed(final LOTRFaction conqFac) {
        if (!this.requestedFacGrids.contains(conqFac) && this.ticksUntilRequestFac <= 0) {
            this.requestedFacGrids.add(conqFac);
            final LOTRPacketConquestGridRequest packet = new LOTRPacketConquestGridRequest(conqFac);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    public void receiveConquestGrid(final LOTRFaction conqFac, final List<LOTRConquestZone> allZones) {
        if (this.isConquestGrid) {
            this.receivedFacGrids.add(conqFac);
            this.facConquestGrids.put(conqFac, allZones);
        }
    }
    
    private boolean hasConquestScrollBar() {
        return this.isConquestGrid && LOTRGuiMap.currentFactionList.size() > 1;
    }
    
    private void setupConquestScrollBar(final int i, final int j) {
        final boolean isMouseDown = Mouse.isButtonDown(0);
        final int i2 = this.facScrollX;
        final int j2 = this.facScrollY;
        final int i3 = i2 + this.facScrollWidth;
        final int j3 = j2 + this.facScrollHeight;
        this.mouseInFacScroll = (i >= i2 && j >= j2 && i < i3 && j < j3);
        if (!this.wasMouseDown && isMouseDown && this.mouseInFacScroll) {
            this.isFacScrolling = true;
        }
        if (!isMouseDown) {
            this.isFacScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isFacScrolling) {
            this.currentFacScroll = (i - i2 - this.facScrollWidgetWidth / 2.0f) / (i3 - i2 - (float)this.facScrollWidgetWidth);
            this.currentFacScroll = MathHelper.clamp_float(this.currentFacScroll, 0.0f, 1.0f);
            this.currentFactionIndex = Math.round(this.currentFacScroll * (LOTRGuiMap.currentFactionList.size() - 1));
        }
    }
    
    private void setCurrentScrollFromFaction() {
        this.currentFacScroll = this.currentFactionIndex / (float)(LOTRGuiMap.currentFactionList.size() - 1);
    }
    
    private boolean hasMapLabels() {
        if (this.isConquestGrid) {
            return LOTRConfig.mapLabelsConquest;
        }
        return LOTRConfig.mapLabels;
    }
    
    private void toggleMapLabels() {
        if (this.isConquestGrid) {
            LOTRConfig.toggleMapLabelsConquest();
        }
        else {
            LOTRConfig.toggleMapLabels();
        }
    }
    
    public void setFakeMapProperties(final float x, final float y, final float scale, final float scaleExp, final float scaleStable) {
        this.posX = x;
        this.posY = y;
        this.zoomScale = scale;
        this.zoomExp = scaleExp;
        this.zoomScaleStable = scaleStable;
    }
    
    public static int[] setFakeStaticProperties(final int w, final int h, final int xmin, final int xmax, final int ymin, final int ymax) {
        final int[] ret = { LOTRGuiMap.mapWidth, LOTRGuiMap.mapHeight, LOTRGuiMap.mapXMin, LOTRGuiMap.mapXMax, LOTRGuiMap.mapYMin, LOTRGuiMap.mapYMax };
        LOTRGuiMap.mapWidth = w;
        LOTRGuiMap.mapHeight = h;
        LOTRGuiMap.mapXMin = xmin;
        LOTRGuiMap.mapXMax = xmax;
        LOTRGuiMap.mapYMin = ymin;
        LOTRGuiMap.mapYMax = ymax;
        return ret;
    }
    
    private static boolean isOSRS() {
        return LOTRConfig.osrsMap;
    }
    
    static {
        LOTRGuiMap.playerLocations = new HashMap<UUID, PlayerLocationInfo>();
        mapIconsTexture = new ResourceLocation("lotr:map/mapScreen.png");
        conquestTexture = new ResourceLocation("lotr:map/conquest.png");
        questBookIcon = new ItemStack(LOTRMod.redBook);
        LOTRGuiMap.fullscreen = true;
        LOTRGuiMap.mapWidgets = new ArrayList<LOTRGuiMapWidget>();
        LOTRGuiMap.zoomPower = 0;
        LOTRGuiMap.zoomTicksMax = 6;
        LOTRGuiMap.showWP = true;
        LOTRGuiMap.showCWP = true;
        LOTRGuiMap.showHiddenSWP = false;
        LOTRGuiMap.lastViewedRegions = new HashMap<LOTRDimension.DimensionRegion, LOTRFaction>();
    }
    
    private static class PlayerLocationInfo
    {
        public GameProfile profile;
        public double posX;
        public double posZ;
        
        public PlayerLocationInfo(final GameProfile p, final double x, final double z) {
            this.profile = p;
            this.posX = x;
            this.posZ = z;
        }
    }
}
