// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiLabel;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.client.ForgeHooksClient;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.util.StatCollector;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import lotr.common.world.map.LOTRWaypoint;
import java.util.List;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiMainMenu;

public class LOTRGuiMainMenu extends GuiMainMenu
{
    private static final ResourceLocation titleTexture;
    private static final ResourceLocation vignetteTexture;
    private LOTRGuiMap mapGui;
    private static LOTRGuiRendererMap mapRenderer;
    private static int tickCounter;
    private static Random rand;
    private static List<LOTRWaypoint> waypointRoute;
    private static int currentWPIndex;
    private static boolean randomWPStart;
    private static float mapSpeed;
    private static float mapVelX;
    private static float mapVelY;
    private static final float wpChangeDistance = 12.0f;
    private static final float mapSpeedMax = 0.8f;
    private static final float mapSpeedIncr = 0.01f;
    private static final float mapAccel = 0.02f;
    private static final float zoomBase = -0.1f;
    private static final float zoomOscilSpeed = 0.003f;
    private static final float zoomOscilMax = 0.8f;
    
    public LOTRGuiMainMenu() {
        this.mapGui = new LOTRGuiMap();
        (LOTRGuiMainMenu.mapRenderer = new LOTRGuiRendererMap()).setSepia(false);
        if (LOTRGuiMainMenu.waypointRoute.isEmpty()) {
            setupWaypoints();
            if (LOTRGuiMainMenu.randomWPStart) {
                LOTRGuiMainMenu.currentWPIndex = LOTRGuiMainMenu.rand.nextInt(LOTRGuiMainMenu.waypointRoute.size());
            }
            else {
                LOTRGuiMainMenu.currentWPIndex = 0;
            }
        }
        final LOTRWaypoint wp = LOTRGuiMainMenu.waypointRoute.get(LOTRGuiMainMenu.currentWPIndex);
        final LOTRGuiRendererMap mapRenderer = LOTRGuiMainMenu.mapRenderer;
        final LOTRGuiRendererMap mapRenderer2 = LOTRGuiMainMenu.mapRenderer;
        final float n = (float)wp.getX();
        mapRenderer2.mapX = n;
        mapRenderer.prevMapX = n;
        final LOTRGuiRendererMap mapRenderer3 = LOTRGuiMainMenu.mapRenderer;
        final LOTRGuiRendererMap mapRenderer4 = LOTRGuiMainMenu.mapRenderer;
        final float n2 = (float)wp.getY();
        mapRenderer4.mapY = n2;
        mapRenderer3.prevMapY = n2;
    }
    
    private static void setupWaypoints() {
        LOTRGuiMainMenu.waypointRoute.clear();
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.HOBBITON);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.BRANDYWINE_BRIDGE);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.BUCKLEBURY);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.WITHYWINDLE_VALLEY);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.BREE);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.WEATHERTOP);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.RIVENDELL);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.WEST_GATE);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.DIMRILL_DALE);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.CERIN_AMROTH);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.CARAS_GALADHON);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.NORTH_UNDEEP);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.SOUTH_UNDEEP);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.ARGONATH);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.RAUROS);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.EDORAS);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.HELMS_DEEP);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.ISENGARD);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.DUNHARROW);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.ERECH);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.MINAS_TIRITH);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.MINAS_MORGUL);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.MOUNT_DOOM);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.MORANNON);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.EAST_RHOVANION_ROAD);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.OLD_RHOVANION);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.RUNNING_FORD);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.DALE_CITY);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.THRANDUIL_HALLS);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.ENCHANTED_RIVER);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.FOREST_GATE);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.BEORN);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.EAGLES_EYRIE);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.GOBLIN_TOWN);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.MOUNT_GRAM);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.FORNOST);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.ANNUMINAS);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.MITHLOND_NORTH);
        LOTRGuiMainMenu.waypointRoute.add(LOTRWaypoint.TOWER_HILLS);
    }
    
    public void initGui() {
        super.initGui();
        int lowerButtonMaxY = 0;
        for (final Object obj : ((GuiScreen)this).buttonList) {
            final GuiButton button = (GuiButton)obj;
            final int buttonMaxY = button.field_146129_i + button.field_146121_g;
            if (buttonMaxY > lowerButtonMaxY) {
                lowerButtonMaxY = buttonMaxY;
            }
        }
        final int idealMoveDown = 50;
        final int lowestSuitableHeight = ((GuiScreen)this).height - 25;
        int moveDown = Math.min(idealMoveDown, lowestSuitableHeight - lowerButtonMaxY);
        moveDown = Math.max(moveDown, 0);
        for (int i = 0; i < ((GuiScreen)this).buttonList.size(); ++i) {
            final GuiButton guiButton;
            final GuiButton button2 = guiButton = ((GuiScreen)this).buttonList.get(i);
            guiButton.field_146129_i += moveDown;
            if (button2.getClass() == GuiButton.class) {
                final GuiButton newButton = new LOTRGuiButtonRedBook(button2.id, button2.field_146128_h, button2.field_146129_i, button2.field_146120_f, button2.field_146121_g, button2.displayString);
                ((GuiScreen)this).buttonList.set(i, newButton);
            }
        }
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int i, final int j) {
        super.setWorldAndResolution(mc, i, j);
        this.mapGui.setWorldAndResolution(mc, i, j);
    }
    
    public void updateScreen() {
        super.updateScreen();
        ++LOTRGuiMainMenu.tickCounter;
        LOTRGuiMainMenu.mapRenderer.updateTick();
        final LOTRWaypoint wp = LOTRGuiMainMenu.waypointRoute.get(LOTRGuiMainMenu.currentWPIndex);
        final float dx = wp.getX() - LOTRGuiMainMenu.mapRenderer.mapX;
        final float dy = wp.getY() - LOTRGuiMainMenu.mapRenderer.mapY;
        final float distSq = dx * dx + dy * dy;
        final float dist = (float)Math.sqrt(distSq);
        if (dist <= 12.0f) {
            ++LOTRGuiMainMenu.currentWPIndex;
            if (LOTRGuiMainMenu.currentWPIndex >= LOTRGuiMainMenu.waypointRoute.size()) {
                LOTRGuiMainMenu.currentWPIndex = 0;
            }
        }
        else {
            LOTRGuiMainMenu.mapSpeed += 0.01f;
            LOTRGuiMainMenu.mapSpeed = Math.min(LOTRGuiMainMenu.mapSpeed, 0.8f);
            final float vXNew = dx / dist * LOTRGuiMainMenu.mapSpeed;
            final float vYNew = dy / dist * LOTRGuiMainMenu.mapSpeed;
            final float a = 0.02f;
            LOTRGuiMainMenu.mapVelX += (vXNew - LOTRGuiMainMenu.mapVelX) * a;
            LOTRGuiMainMenu.mapVelY += (vYNew - LOTRGuiMainMenu.mapVelY) * a;
        }
        final LOTRGuiRendererMap mapRenderer = LOTRGuiMainMenu.mapRenderer;
        mapRenderer.mapX += LOTRGuiMainMenu.mapVelX;
        final LOTRGuiRendererMap mapRenderer2 = LOTRGuiMainMenu.mapRenderer;
        mapRenderer2.mapY += LOTRGuiMainMenu.mapVelY;
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        GL11.glEnable(3008);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        LOTRGuiMainMenu.mapRenderer.zoomExp = -0.1f + MathHelper.cos((LOTRGuiMainMenu.tickCounter + f) * 0.003f) * 0.8f;
        LOTRGuiMainMenu.mapRenderer.zoomStable = (float)Math.pow(2.0, -0.10000000149011612);
        LOTRGuiMainMenu.mapRenderer.renderMap((GuiScreen)this, this.mapGui, f);
        LOTRGuiMainMenu.mapRenderer.renderVignettes((GuiScreen)this, ((Gui)this).zLevel, 2);
        GL11.glDisable(3042);
        final Tessellator tessellator = Tessellator.instance;
        final short short1 = 274;
        final int k = ((GuiScreen)this).width / 2 - short1 / 2;
        final byte b0 = 30;
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiMainMenu.titleTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 155, 44);
        this.drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
        final String modSubtitle = StatCollector.translateToLocal("lotr.menu.title");
        this.drawString(((GuiScreen)this).fontRendererObj, modSubtitle, ((GuiScreen)this).width / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(modSubtitle) / 2, 80, -1);
        final List<String> brandings = (List<String>)Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
        for (int l = 0; l < brandings.size(); ++l) {
            final String brd = brandings.get(l);
            if (!Strings.isNullOrEmpty(brd)) {
                this.drawString(((GuiScreen)this).fontRendererObj, brd, 2, ((GuiScreen)this).height - (10 + l * (((GuiScreen)this).fontRendererObj.FONT_HEIGHT + 1)), -1);
            }
        }
        ForgeHooksClient.renderMainMenu((GuiMainMenu)this, ((GuiScreen)this).fontRendererObj, ((GuiScreen)this).width, ((GuiScreen)this).height);
        final String copyright = "Copyright Mojang AB. Do not distribute!";
        this.drawString(((GuiScreen)this).fontRendererObj, copyright, ((GuiScreen)this).width - ((GuiScreen)this).fontRendererObj.getStringWidth(copyright) - 2, ((GuiScreen)this).height - 10, -1);
        final String field_92025_p = (String)ObfuscationReflectionHelper.getPrivateValue((Class)GuiMainMenu.class, (Object)this, new String[] { "field_92025_p" });
        final String field_146972_A = (String)ObfuscationReflectionHelper.getPrivateValue((Class)GuiMainMenu.class, (Object)this, new String[] { "field_146972_A" });
        final int field_92024_r = (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiMainMenu.class, (Object)this, new String[] { "field_92024_r" });
        final int field_92022_t = (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiMainMenu.class, (Object)this, new String[] { "field_92022_t" });
        final int field_92021_u = (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiMainMenu.class, (Object)this, new String[] { "field_92021_u" });
        final int field_92020_v = (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiMainMenu.class, (Object)this, new String[] { "field_92020_v" });
        final int field_92019_w = (int)ObfuscationReflectionHelper.getPrivateValue((Class)GuiMainMenu.class, (Object)this, new String[] { "field_92019_w" });
        if (field_92025_p != null && field_92025_p.length() > 0) {
            drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
            this.drawString(((GuiScreen)this).fontRendererObj, field_92025_p, field_92022_t, field_92021_u, -1);
            this.drawString(((GuiScreen)this).fontRendererObj, field_146972_A, (((GuiScreen)this).width - field_92024_r) / 2, ((GuiScreen)this).buttonList.get(0).field_146129_i - 12, -1);
        }
        for (final Object button : ((GuiScreen)this).buttonList) {
            ((GuiButton)button).drawButton(((GuiScreen)this).mc, i, j);
        }
        for (final Object label : ((GuiScreen)this).labelList) {
            ((GuiLabel)label).func_146159_a(((GuiScreen)this).mc, i, j);
        }
    }
    
    static {
        titleTexture = new ResourceLocation("textures/gui/title/minecraft.png");
        vignetteTexture = new ResourceLocation("textures/misc/vignette.png");
        LOTRGuiMainMenu.rand = new Random();
        LOTRGuiMainMenu.waypointRoute = new ArrayList<LOTRWaypoint>();
        LOTRGuiMainMenu.randomWPStart = false;
    }
}
