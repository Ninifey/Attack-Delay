package ru.indieplay.ariamis.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.lwjgl.opengl.GL11;
import ru.indieplay.ariamis.common.WeaponStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TickHandler {
    private static ResourceLocation portalOverlay;
    private static ResourceLocation elvenPortalOverlay;
    private static ResourceLocation morgulPortalOverlay;
    private static ResourceLocation mistOverlay;
    private static ResourceLocation frostOverlay;
    private static ResourceLocation burnOverlay;
    private static ResourceLocation wightOverlay;
    public static HashMap playersInPortals;
    public static HashMap playersInElvenPortals;
    public static HashMap playersInMorgulPortals;
    public static int clientTick;
    public static float renderTick;
    private GuiScreen lastGuiOpen;
    private int mistTick;
    private int prevMistTick;
    private static final int mistTickMax = 80;
    private float mistFactor;
    private float sunGlare;
    private float prevSunGlare;
    private float rainFactor;
    private float prevRainFactor;
    private int alignmentXBase;
    private int alignmentYBase;
    private int alignmentXCurrent;
    private int alignmentYCurrent;
    private int alignmentXPrev;
    private int alignmentYPrev;
    private static final int alignmentYOffscreen = -20;
    private boolean firstAlignmentRender;
    public static int alignDrainTick;
    public static final int alignDrainTickMax = 200;
    public static int alignDrainNum;
    private int frostTick;
    private static final int frostTickMax = 80;
    private int burnTick;
    private static final int burnTickMax = 40;
    private int drunkennessDirection;
    private int newDate;
    private static final int newDateMax = 200;
    private float utumnoCamRoll;
    public boolean inUtumnoReturnPortal;
    public int utumnoReturnX;
    public int utumnoReturnZ;
    private double lastUtumnoReturnY;
    private int prevWightLookTick;
    private int wightLookTick;
    private static final int wightLookTickMax = 100;
    public static boolean anyWightsViewed;
    private int prevWightNearTick;
    private int wightNearTick;
    private static final int wightNearTickMax = 100;
    private int prevBalrogNearTick;
    private int balrogNearTick;
    private static final int balrogNearTickMax = 100;
    private float balrogFactor;
    public static int scrapTraderMisbehaveTick;
    private float[] storedLightTable;
    private int storedScrapID;
    private boolean addedClientPoisonEffect;
    private int musicTrackTick;
    private static final int musicTrackTickMax = 200;
    private static final int musicTrackTickFadeTime = 60;
    public boolean cancelItemHighlight;
    private ItemStack lastHighlightedItemstack;
    private String highlightedItemstackName;

    public TickHandler() {
        this.firstAlignmentRender = true;
        this.drunkennessDirection = 1;
        this.newDate = 0;
        this.utumnoCamRoll = 0.0f;
        this.inUtumnoReturnPortal = false;
        this.lastUtumnoReturnY = -1.0;
        this.prevWightLookTick = 0;
        this.wightLookTick = 0;
        this.prevWightNearTick = 0;
        this.wightNearTick = 0;
        this.prevBalrogNearTick = 0;
        this.balrogNearTick = 0;
        this.addedClientPoisonEffect = false;
        this.musicTrackTick = 0;
        this.cancelItemHighlight = false;
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void AttackEntityEvent(AttackEntityEvent ae){
//        System.out.println(ae.target);
    //    System.out.println(ae.entityPlayer.eyeHeight);

    }

    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final EntityClientPlayerMP entityplayer = minecraft.thePlayer;
        final WorldClient world = minecraft.theWorld;
        if (event.phase == TickEvent.Phase.START) {
            ++TickHandler.clientTick;
        }
        if (event.phase == TickEvent.Phase.END) {
            if (minecraft.currentScreen == null) {
                this.lastGuiOpen = null;
            }
            if (FMLClientHandler.instance().hasOptifine()) {
                int optifineSetting = 0;
                try {
                    Object field = GameSettings.class.getField("ofTrees").get(minecraft.gameSettings);
                    if (field instanceof Integer) {
                        optifineSetting = (Integer)field;
                    }
                }
                catch (Exception ex) {}
                final boolean fancyGraphics = (optifineSetting == 0) ? minecraft.gameSettings.fancyGraphics : (optifineSetting != 1 && optifineSetting == 2);
            }
            if (entityplayer != null && world != null) {
                if (!this.isGamePaused(minecraft)) {
                    if (TickHandler.alignDrainTick > 0) {
                        --TickHandler.alignDrainTick;
                        if (TickHandler.alignDrainTick <= 0) {
                            TickHandler.alignDrainNum = 0;
                        }
                    }
                    final EntityLivingBase viewer = minecraft.renderViewEntity;
                    final int i = MathHelper.floor_double(((Entity)viewer).posX);
                    final int j = MathHelper.floor_double(((Entity)viewer).boundingBox.minY);
                    final int k = MathHelper.floor_double(((Entity)viewer).posZ);
                    final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                    AttackTiming.update();
                    this.prevMistTick = this.mistTick;
                    if (this.mistTick > 0) {
                        --this.mistTick;
                    }
                    if (this.frostTick > 0) {
                        --this.frostTick;
                    }
                    if (this.burnTick > 0) {
                        --this.burnTick;
                    }
                    this.prevWightLookTick = this.wightLookTick;
                    if (TickHandler.anyWightsViewed) {
                        if (this.wightLookTick < 100) {
                            ++this.wightLookTick;
                        }
                    }
                    else if (this.wightLookTick > 0) {
                        --this.wightLookTick;
                    }
                    this.prevWightNearTick = this.wightNearTick;
                    final double wightRange = 32.0;
                    this.prevBalrogNearTick = this.balrogNearTick;
                    final double balrogRange = 24.0;

                    {
                        final float n = 0.0f;
                        this.sunGlare = n;
                        this.prevSunGlare = n;
                    }
                     {
                        final float n2 = 0.0f;
                        this.rainFactor = n2;
                        this.prevRainFactor = n2;
                    }


                    if (minecraft.renderViewEntity.isPotionActive(Potion.confusion.id)) {
                        float drunkenness = (float)minecraft.renderViewEntity.getActivePotionEffect(Potion.confusion).getDuration();
                        drunkenness /= 20.0f;
                        if (drunkenness > 100.0f) {
                            drunkenness = 100.0f;
                        }
                        final EntityLivingBase renderViewEntity = minecraft.renderViewEntity;
                        ((Entity)renderViewEntity).rotationYaw += this.drunkennessDirection * drunkenness / 20.0f;
                        final EntityLivingBase renderViewEntity2 = minecraft.renderViewEntity;
                        ((Entity)renderViewEntity2).rotationPitch += MathHelper.cos(((Entity)minecraft.renderViewEntity).ticksExisted / 10.0f) * drunkenness / 20.0f;
                        if (((World)world).rand.nextInt(100) == 0) {
                            this.drunkennessDirection *= -1;
                        }
                    }
                    if (TickHandler.scrapTraderMisbehaveTick > 0) {
                        --TickHandler.scrapTraderMisbehaveTick;
                        if (TickHandler.scrapTraderMisbehaveTick <= 0) {
                            ((World)world).provider.lightBrightnessTable = Arrays.copyOf(this.storedLightTable, this.storedLightTable.length);
                            final Entity scrap = world.getEntityByID(this.storedScrapID);
                            if (scrap != null) {
                                scrap.ignoreFrustumCheck = false;
                            }
                        }
                    }
                    else {
                        final MovingObjectPosition target = minecraft.objectMouseOver;
                    }
                }

            }
            final GuiScreen guiscreen = minecraft.currentScreen;
            if (guiscreen != null) {
                if (guiscreen instanceof GuiMainMenu && !(this.lastGuiOpen instanceof GuiMainMenu)) {
                    AttackTiming.reset();
                    this.firstAlignmentRender = true;
                }
                this.lastGuiOpen = guiscreen;
            }
            TickHandler.anyWightsViewed = false;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        if (event.phase == TickEvent.Phase.END && player instanceof EntityClientPlayerMP) {
            final EntityClientPlayerMP clientPlayer = (EntityClientPlayerMP)player;
        }
    }

    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final EntityClientPlayerMP entityplayer = minecraft.thePlayer;
        final World world = (World)minecraft.theWorld;
        if (event.phase == TickEvent.Phase.START) {
            TickHandler.renderTick = event.renderTickTime;
            if (this.cancelItemHighlight) {
                final GuiIngame guiIngame = minecraft.ingameGUI;
                final int highlightTicks = ReflectionClient.getHighlightedItemTicks(guiIngame);
                if (highlightTicks > 0) {
                    ReflectionClient.setHighlightedItemTicks(guiIngame, 0);
                    this.cancelItemHighlight = false;
                }
            }
        }
        if (event.phase == TickEvent.Phase.END) {
            if (entityplayer != null && world != null) {
            }
        }
    }


    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        if (event.world instanceof WorldClient) {
           // Proxy.customEffectRenderer.clearEffectsAndSetWorld(event.world);
        }
    }

    @SubscribeEvent
    public void onPreRenderGameOverlay(final RenderGameOverlayEvent.Pre event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        final float partialTicks = event.partialTicks;
        final GuiIngame guiIngame = mc.ingameGUI;
        if (world != null && entityplayer != null) {
            if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
                ((World)mc.theWorld).theProfiler.startSection("lotr_fixHighlightedItemName");
                final ItemStack itemstack = ReflectionClient.getHighlightedItemStack(guiIngame);

                ((World)mc.theWorld).theProfiler.endSection();
            }
            if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
                if (this.sunGlare > 0.0f && mc.gameSettings.thirdPersonView == 0) {
                    float brightness = this.prevSunGlare + (this.sunGlare - this.prevSunGlare) * partialTicks;
                    brightness *= 1.0f;
                    this.renderOverlay(null, brightness, mc, null);
                }
                else {
                    this.mistFactor = 0.0f;
                }
                if (this.frostTick > 0) {
                    this.renderOverlay(null, this.frostTick / 80.0f * 0.9f, mc, TickHandler.frostOverlay);
                }
                if (this.burnTick > 0) {
                    this.renderOverlay(null, this.burnTick / 40.0f * 0.6f, mc, TickHandler.burnOverlay);
                }
                if (this.wightLookTick > 0) {
                    this.renderOverlay(null, this.wightLookTick / 100.0f * 0.95f, mc, TickHandler.wightOverlay);
                }
            }
            if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
                AttackTiming.renderAttackMeter(event.resolution, partialTicks);
            }
            
            if (event.type == RenderGameOverlayEvent.ElementType.ARMOR) {
                event.setCanceled(true);
                final ScaledResolution resolution = event.resolution;
                final int width = resolution.getScaledWidth();
                final int height = resolution.getScaledHeight();
                mc.mcProfiler.startSection("armor");
                GL11.glEnable(3042);
                int left = width / 2 - 91;
                final int top2 = height - GuiIngameForge.left_height;
                final int level = WeaponStats.getTotalArmorValue((EntityPlayer)mc.thePlayer);
                if (level > 0) {
                    for (int j = 1; j < 20; j += 2) {
                        if (j < level) {
                            guiIngame.drawTexturedModalRect(left, top2, 34, 9, 9, 9);
                        }
                        else if (j == level) {
                            guiIngame.drawTexturedModalRect(left, top2, 25, 9, 9, 9);
                        }
                        else if (j > level) {
                            guiIngame.drawTexturedModalRect(left, top2, 16, 9, 9, 9);
                        }
                        left += 8;
                    }
                }
                GuiIngameForge.left_height += 10;
                GL11.glDisable(3042);
                mc.mcProfiler.endSection();
            }
        }
    }

    @SubscribeEvent
    public void onPostRenderGameOverlay(final RenderGameOverlayEvent.Post event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        if (world != null && entityplayer != null) {
            if (event.type == RenderGameOverlayEvent.ElementType.ALL && this.lastHighlightedItemstack != null) {
                if (this.highlightedItemstackName != null) {
                    this.lastHighlightedItemstack.setStackDisplayName(this.highlightedItemstackName);
                }
                else {
                    this.lastHighlightedItemstack.func_135074_t();
                }
                this.lastHighlightedItemstack = null;
                this.highlightedItemstackName = null;
            }
            if (event.type == RenderGameOverlayEvent.ElementType.HEALTH && this.addedClientPoisonEffect) {
                entityplayer.removePotionEffectClient(Potion.poison.id);
                this.addedClientPoisonEffect = false;
            }
        }
    }
    
    
    @SubscribeEvent
    public void getItemTooltip(final ItemTooltipEvent event) {
        final ItemStack itemstack = event.itemStack;
        final List<String> tooltip = (List<String>)event.toolTip;
        if (WeaponStats.getMeleeSpeed(itemstack) != 1) {
            int dmgIndex = -1;
            for (int j = 0; j < tooltip.size(); ++j) {
                final String s = tooltip.get(j);
                if (s.startsWith(EnumChatFormatting.BLUE.toString())) {
                    dmgIndex = j;
                    break;
                }
            }
            if (dmgIndex >= 0) {
                final List<String> newTooltip = new ArrayList<String>();
                for (int i = 0; i <= dmgIndex - 1; ++i) {
                    newTooltip.add(tooltip.get(i));
                }
                final float meleeSpeed = WeaponStats.getMeleeSpeed(itemstack);

                newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("item.stat.speed", new Object[] { Math.round(meleeSpeed * 100.0f) }));
                final float meleRange = WeaponStats.getMeleeReachFactor(itemstack);
                final int pcReach = Math.round(meleRange * 100.0f);
                newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("item.stat.range", new Object[] { pcReach } ));

                for (int k = dmgIndex + 1; k < tooltip.size(); ++k) {
                    newTooltip.add(tooltip.get(k));
                }
                tooltip.clear();
                tooltip.addAll(newTooltip);
            }
        }
        final int armorProtect = WeaponStats.getArmorProtection(itemstack);
        if (itemstack.getItem() == Item.getItemFromBlock(Blocks.monster_egg)) {
            tooltip.set(0, EnumChatFormatting.RED + tooltip.get(0));
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(final FOVUpdateEvent event) {
        final EntityPlayerSP entityplayer = event.entity;
        float fov = event.newfov;
        final ItemStack itemstack = entityplayer.getHeldItem();
        final Item item = (itemstack == null) ? null : itemstack.getItem();
        float usage = -1.0f;
        if (entityplayer.isUsingItem()) {
            float maxDrawTime = 0.0f;
            if (maxDrawTime > 0.0f) {
                final int i = entityplayer.getItemInUseDuration();
                usage = i / maxDrawTime;
                if (usage > 1.0f) {
                    usage = 1.0f;
                }
                else {
                    usage *= usage;
                }
            }
        }
        if (usage >= 0.0f) {
            fov *= 1.0f - usage * 0.15f;
        }
        event.newfov = fov;
    }

    @SubscribeEvent
    public void onRenderFog(final EntityViewRenderEvent.RenderFogEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final EntityLivingBase viewer = event.entity;
        final World world = (World)mc.theWorld;
        final WorldProvider provider = world.provider;
        final int i = MathHelper.floor_double(((Entity)viewer).posX);
        final int j = MathHelper.floor_double(((Entity)viewer).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)viewer).posZ);
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        final float farPlane = event.farPlaneDistance;
        final int fogMode = event.fogMode;

    }

    @SubscribeEvent
    public void onFogColors(final EntityViewRenderEvent.FogColors event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        final WorldProvider provider = world.provider;
    }

    private boolean isGamePaused(final Minecraft mc) {
        return mc.isSingleplayer() && mc.currentScreen != null && mc.currentScreen.doesGuiPauseGame() && !mc.getIntegratedServer().getPublic();
    }

    private void renderOverlay(final float[] rgb, final float alpha, final Minecraft mc, final ResourceLocation texture) {
        final ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        final int width = resolution.getScaledWidth();
        final int height = resolution.getScaledHeight();
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        if (rgb != null) {
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], alpha);
        }
        else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        }
        GL11.glDisable(3008);
        if (texture != null) {
            mc.getTextureManager().bindTexture(texture);
        }
        else {
            GL11.glDisable(3553);
        }
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0, (double)height, -90.0, 0.0, 1.0);
        tessellator.addVertexWithUV((double)width, (double)height, -90.0, 1.0, 1.0);
        tessellator.addVertexWithUV((double)width, 0.0, -90.0, 1.0, 0.0);
        tessellator.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        tessellator.draw();
        if (texture == null) {
            GL11.glEnable(3553);
        }
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderAlignment(final Minecraft mc, final float f) {
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        final ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        final int width = resolution.getScaledWidth();
        final int height = resolution.getScaledHeight();
        final boolean boss = BossStatus.bossName != null && BossStatus.statusBarTime > 0;
        if (boss) {
            this.alignmentYBase += 20;
        }
        if (this.firstAlignmentRender) {
            final int alignmentXBase = this.alignmentXBase;
            this.alignmentXCurrent = alignmentXBase;
            this.alignmentXPrev = alignmentXBase;
            final int n = -20;
            this.alignmentYCurrent = n;
            this.alignmentYPrev = n;
            this.firstAlignmentRender = false;
        }
        final float alignmentXF = this.alignmentXPrev + (this.alignmentXCurrent - this.alignmentXPrev) * f;
        final float alignmentYF = this.alignmentYPrev + (this.alignmentYCurrent - this.alignmentYPrev) * f;
        final boolean text = this.alignmentYCurrent == this.alignmentYBase;
        if (TickHandler.alignDrainTick > 0 && text) {
            float alpha = 1.0f;
            final int fadeTick = 20;
            if (TickHandler.alignDrainTick < fadeTick) {
                alpha = TickHandler.alignDrainTick / (float)fadeTick;
            }
        }
    }


    public static void drawTexturedModalRect(final double x, final double y, final int u, final int v, final int width, final int height) {
        final float f = 0.00390625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0.0, y + height, 0.0, (double)((u + 0) * f), (double)((v + height) * f));
        tessellator.addVertexWithUV(x + width, y + height, 0.0, (double)((u + width) * f), (double)((v + height) * f));
        tessellator.addVertexWithUV(x + width, y + 0.0, 0.0, (double)((u + width) * f), (double)((v + 0) * f));
        tessellator.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (double)((u + 0) * f), (double)((v + 0) * f));
        tessellator.draw();
    }

    public static void drawAlignmentText(final FontRenderer f, final int x, final int y, final String s, final float alphaF) {
        drawBorderedText(f, x, y, s, 16772620, alphaF);
    }

    public static void drawConquestText(final FontRenderer f, final int x, final int y, final String s, final boolean cleanse, final float alphaF) {
        drawBorderedText(f, x, y, s, cleanse ? 16773846 : 14833677, alphaF);
    }

    public static void drawBorderedText(final FontRenderer f, final int x, final int y, final String s, final int color, final float alphaF) {
        int alpha = (int)(alphaF * 255.0f);
        alpha = MathHelper.clamp_int(alpha, 4, 255);
        alpha <<= 24;
        f.drawString(s, x - 1, y - 1, 0x0 | alpha);
        f.drawString(s, x, y - 1, 0x0 | alpha);
        f.drawString(s, x + 1, y - 1, 0x0 | alpha);
        f.drawString(s, x + 1, y, 0x0 | alpha);
        f.drawString(s, x + 1, y + 1, 0x0 | alpha);
        f.drawString(s, x, y + 1, 0x0 | alpha);
        f.drawString(s, x - 1, y + 1, 0x0 | alpha);
        f.drawString(s, x - 1, y, 0x0 | alpha);
        f.drawString(s, x, y, color | alpha);
    }

    public void onFrostDamage() {
        this.frostTick = 80;
    }

    public void onBurnDamage() {
        this.burnTick = 40;
    }

    public void updateDate() {
        this.newDate = 200;
    }

    public float getWightLookFactor() {
        float f = this.prevWightLookTick + (this.wightLookTick - this.prevWightLookTick) * TickHandler.renderTick;
        f /= 100.0f;
        return f;
    }

    static {
        TickHandler.portalOverlay = new ResourceLocation("lotr:misc/portal_overlay.png");
        TickHandler.elvenPortalOverlay = new ResourceLocation("lotr:misc/elvenportal_overlay.png");
        TickHandler.morgulPortalOverlay = new ResourceLocation("lotr:misc/morgulportal_overlay.png");
        TickHandler.mistOverlay = new ResourceLocation("lotr:misc/mist_overlay.png");
        TickHandler.frostOverlay = new ResourceLocation("lotr:misc/frost_overlay.png");
        TickHandler.burnOverlay = new ResourceLocation("lotr:misc/burn_overlay.png");
        TickHandler.wightOverlay = new ResourceLocation("lotr:misc/wight.png");
        TickHandler.playersInPortals = new HashMap();
        TickHandler.playersInElvenPortals = new HashMap();
        TickHandler.playersInMorgulPortals = new HashMap();
        TickHandler.scrapTraderMisbehaveTick = 0;
    }
}
