// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import lotr.common.util.LOTRFunctions;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.WorldProvider;
import lotr.common.util.LOTRColorUtil;
import lotr.common.world.biome.LOTRBiomeGenBarrowDowns;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import lotr.common.item.LOTRItemBlowgun;
import lotr.common.item.LOTRItemSpear;
import lotr.common.item.LOTRItemBow;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumChatFormatting;
import java.util.Collection;
import net.minecraft.util.StringUtils;
import lotr.common.LOTRSquadrons;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import lotr.client.render.entity.LOTRNPCRendering;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.item.LOTRWeaponStats;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraft.potion.PotionEffect;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraft.client.gui.Gui;
import lotr.common.entity.npc.LOTREntitySpiderBase;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import lotr.client.fx.LOTREntityDeadMarshFace;
import lotr.common.world.biome.LOTRBiomeGenDeadMarshes;
import lotr.common.world.biome.LOTRBiomeGenMorgulVale;
import lotr.common.world.biome.LOTRBiomeGenMirkwoodCorrupted;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.StatCollector;
import java.util.ArrayList;
import net.minecraft.client.renderer.OpenGlHelper;
import lotr.client.model.LOTRModelCompass;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import lotr.client.gui.LOTRGuiMessage;
import lotr.common.entity.LOTRMountFunctions;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityClientPlayerMP;
import lotr.client.gui.LOTRGuiMap;
import lotr.client.gui.LOTRGuiMenu;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.LOTRDate;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.fac.LOTRFactionBounties;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.LOTRTime;
import lotr.common.LOTRLevelData;
import net.minecraft.client.gui.GuiMainMenu;
import lotr.client.sound.LOTRMusicTicker;
import lotr.common.LOTRMod;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import lotr.common.entity.item.LOTREntityPortal;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import java.util.Arrays;
import lotr.common.LOTRDimension;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.entity.npc.LOTREntityBalrog;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import net.minecraft.world.EnumSkyBlock;
import lotr.common.world.biome.LOTRBiomeGenMistyMountains;
import lotr.client.render.LOTRCloudRenderer;
import lotr.common.world.biome.LOTRBiomeGenUtumno;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.util.LOTRVersionChecker;
import lotr.common.block.LOTRBlockLeavesBase;
import net.minecraft.client.settings.GameSettings;
import lotr.client.render.tileentity.LOTRTileEntityMobSpawnerRenderer;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IReloadableResourceManager;
import lotr.common.util.LOTRLog;
import cpw.mods.fml.client.FMLClientHandler;
import lotr.common.LOTRConfig;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.item.ItemStack;
import lotr.client.sound.LOTRMusicTrack;
import lotr.client.gui.LOTRGuiMiniquestTracker;
import lotr.client.gui.LOTRGuiNotificationDisplay;
import net.minecraft.client.gui.GuiScreen;
import lotr.client.sound.LOTRAmbience;
import java.util.HashMap;
import net.minecraft.util.ResourceLocation;

public class LOTRTickHandlerClient
{
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
    private LOTRAmbience ambienceTicker;
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
    public static LOTRGuiNotificationDisplay notificationDisplay;
    public static LOTRGuiMiniquestTracker miniquestTracker;
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
    private LOTRMusicTrack lastTrack;
    private int musicTrackTick;
    private static final int musicTrackTickMax = 200;
    private static final int musicTrackTickFadeTime = 60;
    public boolean cancelItemHighlight;
    private ItemStack lastHighlightedItemstack;
    private String highlightedItemstackName;

    public LOTRTickHandlerClient() {
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
        this.lastTrack = null;
        this.musicTrackTick = 0;
        this.cancelItemHighlight = false;
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.ambienceTicker = new LOTRAmbience();
        LOTRTickHandlerClient.notificationDisplay = new LOTRGuiNotificationDisplay();
        LOTRTickHandlerClient.miniquestTracker = new LOTRGuiMiniquestTracker();
    }

    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final EntityClientPlayerMP entityplayer = minecraft.thePlayer;
        final WorldClient world = minecraft.theWorld;
        if (event.phase == TickEvent.Phase.START) {
            ++LOTRTickHandlerClient.clientTick;
            if (LOTRConfig.fixRenderDistance && !FMLClientHandler.instance().hasOptifine()) {
                final GameSettings gs = Minecraft.getMinecraft().gameSettings;
                int renderDistance = gs.renderDistanceChunks;
                if (renderDistance > 16) {
                    renderDistance = 16;
                    gs.renderDistanceChunks = renderDistance;
                    gs.saveOptions();
                    LOTRLog.logger.info("LOTR: Render distance was above 16 - set to 16 to prevent a vanilla crash");
                }
            }
            if (minecraft.entityRenderer != null && !(minecraft.entityRenderer instanceof LOTREntityRenderer)) {
                minecraft.entityRenderer = new LOTREntityRenderer(minecraft, minecraft.getResourceManager());
                ((IReloadableResourceManager)minecraft.getResourceManager()).registerReloadListener((IResourceManagerReloadListener)minecraft.entityRenderer);
                FMLLog.info("LOTR: Successfully replaced entityrenderer", new Object[0]);
            }
        }
        if (event.phase == TickEvent.Phase.END) {
            LOTRTileEntityMobSpawnerRenderer.onClientTick();
            if (minecraft.currentScreen == null) {
                this.lastGuiOpen = null;
            }
            if (FMLClientHandler.instance().hasOptifine()) {
                int optifineSetting = 0;
                try {
                    final Object field = GameSettings.class.getField("ofTrees").get(minecraft.gameSettings);
                    if (field instanceof Integer) {
                        optifineSetting = (int)field;
                    }
                }
                catch (Exception ex) {}
                final boolean fancyGraphics = (optifineSetting == 0) ? minecraft.gameSettings.fancyGraphics : (optifineSetting != 1 && optifineSetting == 2);
                LOTRBlockLeavesBase.setAllGraphicsLevels(fancyGraphics);
            }
            else {
                LOTRBlockLeavesBase.setAllGraphicsLevels(minecraft.gameSettings.fancyGraphics);
            }
            if (entityplayer != null && world != null) {
                if (LOTRConfig.checkUpdates) {
                    LOTRVersionChecker.checkForUpdates();
                }
                if (!this.isGamePaused(minecraft)) {
                    LOTRTickHandlerClient.miniquestTracker.update(minecraft, (EntityPlayer)entityplayer);
                    LOTRAlignmentTicker.updateAll((EntityPlayer)entityplayer, false);
                    if (LOTRTickHandlerClient.alignDrainTick > 0) {
                        --LOTRTickHandlerClient.alignDrainTick;
                        if (LOTRTickHandlerClient.alignDrainTick <= 0) {
                            LOTRTickHandlerClient.alignDrainNum = 0;
                        }
                    }
                    final EntityLivingBase viewer = minecraft.renderViewEntity;
                    final int i = MathHelper.floor_double(((Entity)viewer).posX);
                    final int j = MathHelper.floor_double(((Entity)viewer).boundingBox.minY);
                    final int k = MathHelper.floor_double(((Entity)viewer).posZ);
                    final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
                    LOTRBiome.updateWaterColor(i, j, k);
                    LOTRBiomeGenUtumno.updateFogColor(i, j, k);
                    LOTRCloudRenderer.updateClouds(world);
                    LOTRSpeechClient.update();
                    LOTRKeyHandler.update();
                    LOTRAttackTiming.update();
                    this.prevMistTick = this.mistTick;
                    if (((Entity)viewer).posY >= 72.0 && biome instanceof LOTRBiomeGenMistyMountains && biome != LOTRBiome.mistyMountainsFoothills && world.canBlockSeeTheSky(i, j, k) && world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 7) {
                        if (this.mistTick < 80) {
                            ++this.mistTick;
                        }
                    }
                    else if (this.mistTick > 0) {
                        --this.mistTick;
                    }
                    if (this.frostTick > 0) {
                        --this.frostTick;
                    }
                    if (this.burnTick > 0) {
                        --this.burnTick;
                    }
                    this.prevWightLookTick = this.wightLookTick;
                    if (LOTRTickHandlerClient.anyWightsViewed) {
                        if (this.wightLookTick < 100) {
                            ++this.wightLookTick;
                        }
                    }
                    else if (this.wightLookTick > 0) {
                        --this.wightLookTick;
                    }
                    this.prevWightNearTick = this.wightNearTick;
                    final double wightRange = 32.0;
                    final List nearbyWights = world.getEntitiesWithinAABB((Class)LOTREntityBarrowWight.class, ((Entity)viewer).boundingBox.expand(wightRange, wightRange, wightRange));
                    if (!nearbyWights.isEmpty()) {
                        if (this.wightNearTick < 100) {
                            ++this.wightNearTick;
                        }
                    }
                    else if (this.wightNearTick > 0) {
                        --this.wightNearTick;
                    }
                    this.prevBalrogNearTick = this.balrogNearTick;
                    final double balrogRange = 24.0;
                    final List nearbyBalrogs = world.getEntitiesWithinAABB((Class)LOTREntityBalrog.class, ((Entity)viewer).boundingBox.expand(balrogRange, balrogRange, balrogRange));
                    if (!nearbyBalrogs.isEmpty()) {
                        if (this.balrogNearTick < 100) {
                            ++this.balrogNearTick;
                        }
                    }
                    else if (this.balrogNearTick > 0) {
                        --this.balrogNearTick;
                    }
                    if (LOTRConfig.enableSunFlare && ((World)world).provider instanceof LOTRWorldProvider && !((World)world).provider.hasNoSky) {
                        this.prevSunGlare = this.sunGlare;
                        final MovingObjectPosition look = viewer.rayTrace(10000.0, LOTRTickHandlerClient.renderTick);
                        final boolean lookingAtSky = look == null || look.typeOfHit == MovingObjectPosition.MovingObjectType.MISS;
                        boolean biomeHasSun = true;
                        if (biome instanceof LOTRBiome) {
                            biomeHasSun = ((LOTRBiome)biome).hasSky();
                        }
                        final float sunPitch;
                        final float celestialAngle = sunPitch = world.getCelestialAngle(LOTRTickHandlerClient.renderTick) * 360.0f - 90.0f;
                        final float sunYaw = 90.0f;
                        final float yc = MathHelper.cos((float)Math.toRadians(-sunYaw - 180.0f));
                        final float ys = MathHelper.sin((float)Math.toRadians(-sunYaw - 180.0f));
                        final float pc = -MathHelper.cos((float)Math.toRadians(-sunPitch));
                        final float ps = MathHelper.sin((float)Math.toRadians(-sunPitch));
                        final Vec3 sunVec = Vec3.createVectorHelper((double)(ys * pc), (double)ps, (double)(yc * pc));
                        final Vec3 lookVec = viewer.getLook(LOTRTickHandlerClient.renderTick);
                        final double cos = lookVec.dotProduct(sunVec) / (lookVec.lengthVector() * sunVec.lengthVector());
                        final float cosThreshold = 0.95f;
                        float cQ = ((float)cos - cosThreshold) / (1.0f - cosThreshold);
                        cQ = Math.max(cQ, 0.0f);
                        final float brightness = world.getSunBrightness(LOTRTickHandlerClient.renderTick);
                        final float brightnessThreshold = 0.7f;
                        float bQ = (brightness - brightnessThreshold) / (1.0f - brightnessThreshold);
                        bQ = Math.max(bQ, 0.0f);
                        final float maxGlare = cQ * bQ;
                        if (maxGlare > 0.0f && lookingAtSky && !world.isRaining() && biomeHasSun) {
                            if (this.sunGlare < maxGlare) {
                                this.sunGlare += 0.1f * maxGlare;
                                this.sunGlare = Math.min(this.sunGlare, maxGlare);
                            }
                            else if (this.sunGlare > maxGlare) {
                                this.sunGlare -= 0.02f;
                                this.sunGlare = Math.max(this.sunGlare, maxGlare);
                            }
                        }
                        else {
                            if (this.sunGlare > 0.0f) {
                                this.sunGlare -= 0.02f;
                            }
                            this.sunGlare = Math.max(this.sunGlare, 0.0f);
                        }
                    }
                    else {
                        final float n = 0.0f;
                        this.sunGlare = n;
                        this.prevSunGlare = n;
                    }
                    if (LOTRConfig.newRain) {
                        this.prevRainFactor = this.rainFactor;
                        if (world.isRaining()) {
                            if (this.rainFactor < 1.0f) {
                                this.rainFactor += 0.008333334f;
                                this.rainFactor = Math.min(this.rainFactor, 1.0f);
                            }
                        }
                        else if (this.rainFactor > 0.0f) {
                            this.rainFactor -= 0.0016666667f;
                            this.rainFactor = Math.max(this.rainFactor, 0.0f);
                        }
                    }
                    else {
                        final float n2 = 0.0f;
                        this.rainFactor = n2;
                        this.prevRainFactor = n2;
                    }
                    if (minecraft.gameSettings.particleSetting < 2) {
                        this.spawnEnvironmentFX((EntityPlayer)entityplayer, (World)world);
                    }
                    LOTRClientProxy.customEffectRenderer.updateEffects();
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
                    if (LOTRDimension.getCurrentDimension((World)world) == LOTRDimension.UTUMNO) {
                        if (this.inUtumnoReturnPortal) {
                            if (this.utumnoCamRoll < 180.0f) {
                                this.utumnoCamRoll += 5.0f;
                                this.utumnoCamRoll = Math.min(this.utumnoCamRoll, 180.0f);
                                LOTRReflectionClient.setCameraRoll(minecraft.entityRenderer, this.utumnoCamRoll);
                            }
                        }
                        else if (this.utumnoCamRoll > 0.0f) {
                            this.utumnoCamRoll -= 5.0f;
                            this.utumnoCamRoll = Math.max(this.utumnoCamRoll, 0.0f);
                            LOTRReflectionClient.setCameraRoll(minecraft.entityRenderer, this.utumnoCamRoll);
                        }
                    }
                    else if (this.utumnoCamRoll != 0.0f) {
                        this.utumnoCamRoll = 0.0f;
                        LOTRReflectionClient.setCameraRoll(minecraft.entityRenderer, this.utumnoCamRoll);
                    }
                    if (this.newDate > 0) {
                        --this.newDate;
                    }
                    if (LOTRConfig.enableAmbience) {
                        this.ambienceTicker.updateAmbience((World)world, (EntityPlayer)entityplayer);
                    }
                    if (LOTRTickHandlerClient.scrapTraderMisbehaveTick > 0) {
                        --LOTRTickHandlerClient.scrapTraderMisbehaveTick;
                        if (LOTRTickHandlerClient.scrapTraderMisbehaveTick <= 0) {
                            ((World)world).provider.lightBrightnessTable = Arrays.copyOf(this.storedLightTable, this.storedLightTable.length);
                            final Entity scrap = world.getEntityByID(this.storedScrapID);
                            if (scrap != null) {
                                scrap.ignoreFrustumCheck = false;
                            }
                        }
                    }
                    else {
                        final MovingObjectPosition target = minecraft.objectMouseOver;
                        if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && target.entityHit instanceof LOTREntityScrapTrader) {
                            final LOTREntityScrapTrader scrap2 = (LOTREntityScrapTrader)target.entityHit;
                            if (minecraft.currentScreen == null && ((World)world).rand.nextInt(50000) == 0) {
                                LOTRTickHandlerClient.scrapTraderMisbehaveTick = 400;
                                ((Entity)scrap2).ignoreFrustumCheck = true;
                                this.storedScrapID = scrap2.getEntityId();
                                final float[] lightTable = ((World)world).provider.lightBrightnessTable;
                                this.storedLightTable = Arrays.copyOf(lightTable, lightTable.length);
                                for (int l = 0; l < lightTable.length; ++l) {
                                    lightTable[l] = 1.0E-7f;
                                }
                            }
                        }
                    }
                }
                if ((((Entity)entityplayer).dimension == 0 || ((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) && LOTRTickHandlerClient.playersInPortals.containsKey(entityplayer)) {
                    final List portals = world.getEntitiesWithinAABB((Class)LOTREntityPortal.class, ((Entity)entityplayer).boundingBox.expand(8.0, 8.0, 8.0));
                    boolean inPortal = false;
                    for (int m = 0; m < portals.size(); ++m) {
                        final LOTREntityPortal portal = portals.get(m);
                        if (portal.boundingBox.intersectsWith(((Entity)entityplayer).boundingBox)) {
                            inPortal = true;
                            break;
                        }
                    }
                    if (inPortal) {
                        int m = LOTRTickHandlerClient.playersInPortals.get(entityplayer);
                        ++m;
                        LOTRTickHandlerClient.playersInPortals.put(entityplayer, m);
                        if (m >= 100) {
                            minecraft.getSoundHandler().playSound((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), ((World)world).rand.nextFloat() * 0.4f + 0.8f));
                            LOTRTickHandlerClient.playersInPortals.remove(entityplayer);
                        }
                    }
                    else {
                        LOTRTickHandlerClient.playersInPortals.remove(entityplayer);
                    }
                }
                this.updatePlayerInPortal((EntityPlayer)entityplayer, LOTRTickHandlerClient.playersInElvenPortals, LOTRMod.elvenPortal);
                this.updatePlayerInPortal((EntityPlayer)entityplayer, LOTRTickHandlerClient.playersInMorgulPortals, LOTRMod.morgulPortal);
                if (this.inUtumnoReturnPortal) {
                    entityplayer.setPosition(this.utumnoReturnX + 0.5, ((Entity)entityplayer).posY, this.utumnoReturnZ + 0.5);
                    if (this.lastUtumnoReturnY >= 0.0 && ((Entity)entityplayer).posY < this.lastUtumnoReturnY) {
                        entityplayer.setPosition(((Entity)entityplayer).posX, this.lastUtumnoReturnY, ((Entity)entityplayer).posZ);
                    }
                    this.lastUtumnoReturnY = ((Entity)entityplayer).posY;
                }
                else {
                    this.lastUtumnoReturnY = -1.0;
                }
                this.inUtumnoReturnPortal = false;
            }
            LOTRClientProxy.musicHandler.update();
            if (LOTRConfig.displayMusicTrack) {
                final LOTRMusicTrack nowPlaying = LOTRMusicTicker.currentTrack;
                if (nowPlaying != this.lastTrack) {
                    this.lastTrack = nowPlaying;
                    this.musicTrackTick = 200;
                }
                if (this.lastTrack != null && this.musicTrackTick > 0) {
                    --this.musicTrackTick;
                }
            }
            final GuiScreen guiscreen = minecraft.currentScreen;
            if (guiscreen != null) {
                if (guiscreen instanceof GuiMainMenu && !(this.lastGuiOpen instanceof GuiMainMenu)) {
                    LOTRLevelData.needsLoad = true;
                    LOTRTime.needsLoad = true;
                    LOTRFellowshipData.needsLoad = true;
                    LOTRFactionBounties.needsLoad = true;
                    LOTRFactionRelations.needsLoad = true;
                    LOTRDate.resetWorldTimeInMenu();
                    LOTRConquestGrid.needsLoad = true;
                    LOTRSpeechClient.clearAll();
                    LOTRAttackTiming.reset();
                    LOTRGuiMenu.resetLastMenuScreen();
                    LOTRGuiMap.clearPlayerLocations();
                    LOTRCloudRenderer.resetClouds();
                    this.firstAlignmentRender = true;
                }
                this.lastGuiOpen = guiscreen;
            }
            LOTRTickHandlerClient.anyWightsViewed = false;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        if (event.phase == TickEvent.Phase.END && player instanceof EntityClientPlayerMP) {
            final EntityClientPlayerMP clientPlayer = (EntityClientPlayerMP)player;
            if (clientPlayer.isRiding()) {
                LOTRMountFunctions.sendControlToServer((EntityPlayer)clientPlayer);
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final EntityClientPlayerMP entityplayer = minecraft.thePlayer;
        final World world = (World)minecraft.theWorld;
        if (event.phase == TickEvent.Phase.START) {
            LOTRTickHandlerClient.renderTick = event.renderTickTime;
            if (this.cancelItemHighlight) {
                final GuiIngame guiIngame = minecraft.ingameGUI;
                final int highlightTicks = LOTRReflectionClient.getHighlightedItemTicks(guiIngame);
                if (highlightTicks > 0) {
                    LOTRReflectionClient.setHighlightedItemTicks(guiIngame, 0);
                    this.cancelItemHighlight = false;
                }
            }
        }
        if (event.phase == TickEvent.Phase.END) {
            if (entityplayer != null && world != null) {
                if (world.provider instanceof LOTRWorldProvider || LOTRConfig.alwaysShowAlignment) {
                    this.alignmentXPrev = this.alignmentXCurrent;
                    this.alignmentYPrev = this.alignmentYCurrent;
                    this.alignmentXCurrent = this.alignmentXBase;
                    final int yMove = (int)((this.alignmentYBase + 20) / 10.0f);
                    final boolean alignmentOnscreen = (minecraft.currentScreen == null || minecraft.currentScreen instanceof LOTRGuiMessage) && !minecraft.gameSettings.keyBindPlayerList.getIsKeyPressed() && !minecraft.gameSettings.showDebugInfo;
                    if (alignmentOnscreen) {
                        this.alignmentYCurrent = Math.min(this.alignmentYCurrent + yMove, this.alignmentYBase);
                    }
                    else {
                        this.alignmentYCurrent = Math.max(this.alignmentYCurrent - yMove, -20);
                    }
                    this.renderAlignment(minecraft, LOTRTickHandlerClient.renderTick);
                    if (LOTRConfig.enableOnscreenCompass && minecraft.currentScreen == null && !minecraft.gameSettings.showDebugInfo) {
                        GL11.glPushMatrix();
                        final ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
                        final int i = resolution.getScaledWidth();
                        final int j = resolution.getScaledHeight();
                        int compassX = i - 60;
                        int compassY = 40;
                        GL11.glTranslatef((float)compassX, (float)compassY, 0.0f);
                        float rotation = ((Entity)entityplayer).prevRotationYaw + (((Entity)entityplayer).rotationYaw - ((Entity)entityplayer).prevRotationYaw) * event.renderTickTime;
                        rotation = 180.0f - rotation;
                        LOTRModelCompass.compassModel.render(1.0f, rotation);
                        GL11.glPopMatrix();
                        if (LOTRConfig.compassExtraInfo) {
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            final float scale = 0.5f;
                            final float invScale = 1.0f / scale;
                            compassX *= (int)invScale;
                            compassY *= (int)invScale;
                            GL11.glScalef(scale, scale, scale);
                            final String coords = MathHelper.floor_double(((Entity)entityplayer).posX) + ", " + MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY) + ", " + MathHelper.floor_double(((Entity)entityplayer).posZ);
                            final FontRenderer fontRenderer = minecraft.fontRenderer;
                            fontRenderer.drawString(coords, compassX - fontRenderer.getStringWidth(coords) / 2, compassY + 70, 16777215);
                            final int playerX = MathHelper.floor_double(((Entity)entityplayer).posX);
                            final int playerZ = MathHelper.floor_double(((Entity)entityplayer).posZ);
                            if (LOTRClientProxy.doesClientChunkExist(world, playerX, playerZ)) {
                                final BiomeGenBase biome = world.getBiomeGenForCoords(playerX, playerZ);
                                if (biome instanceof LOTRBiome) {
                                    final String biomeName = ((LOTRBiome)biome).getBiomeDisplayName();
                                    fontRenderer.drawString(biomeName, compassX - fontRenderer.getStringWidth(biomeName) / 2, compassY - 70, 16777215);
                                }
                            }
                            GL11.glScalef(invScale, invScale, invScale);
                        }
                    }
                }
                if (((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID && minecraft.currentScreen == null && this.newDate > 0) {
                    final int halfMaxDate = 100;
                    float alpha = 0.0f;
                    if (this.newDate > halfMaxDate) {
                        alpha = (200 - this.newDate) / (float)halfMaxDate;
                    }
                    else {
                        alpha = this.newDate / (float)halfMaxDate;
                    }
                    final String date = LOTRDate.ShireReckoning.getShireDate().getDateName(true);
                    final ScaledResolution resolution2 = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
                    int k = resolution2.getScaledWidth();
                    int l = resolution2.getScaledHeight();
                    final float scale2 = 1.5f;
                    final float invScale2 = 1.0f / scale2;
                    k *= (int)invScale2;
                    l *= (int)invScale2;
                    final int x = (k - minecraft.fontRenderer.getStringWidth(date)) / 2;
                    final int y = (l - minecraft.fontRenderer.FONT_HEIGHT) * 2 / 5;
                    GL11.glScalef(scale2, scale2, scale2);
                    GL11.glEnable(3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    minecraft.fontRenderer.drawString(date, x, y, 16777215 + (LOTRClientProxy.getAlphaInt(alpha) << 24));
                    GL11.glDisable(3042);
                    GL11.glScalef(invScale2, invScale2, invScale2);
                }
                if (LOTRConfig.displayMusicTrack && minecraft.currentScreen == null && this.lastTrack != null && this.musicTrackTick > 0) {
                    final List<String> lines = new ArrayList<String>();
                    lines.add(StatCollector.translateToLocal("lotr.music.nowPlaying"));
                    final String title = this.lastTrack.getTitle();
                    lines.add(title);
                    if (!this.lastTrack.getAuthors().isEmpty()) {
                        String authors = "(";
                        int a = 0;
                        for (final String auth : this.lastTrack.getAuthors()) {
                            authors += auth;
                            if (a < this.lastTrack.getAuthors().size() - 1) {
                                authors += ", ";
                            }
                            ++a;
                        }
                        authors += ")";
                        lines.add(authors);
                    }
                    final ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
                    final int w = resolution.getScaledWidth();
                    final int h = resolution.getScaledHeight();
                    final int border = 20;
                    int x2 = w - border;
                    int y2 = h - border - lines.size() * minecraft.fontRenderer.FONT_HEIGHT;
                    float alpha2 = 1.0f;
                    if (this.musicTrackTick >= 140) {
                        alpha2 = (200 - this.musicTrackTick) / 60.0f;
                    }
                    else if (this.musicTrackTick <= 60) {
                        alpha2 = this.musicTrackTick / 60.0f;
                    }
                    for (final String line : lines) {
                        x2 = w - border - minecraft.fontRenderer.getStringWidth(line);
                        minecraft.fontRenderer.drawString(line, x2, y2, 16777215 + (LOTRClientProxy.getAlphaInt(alpha2) << 24));
                        y2 += minecraft.fontRenderer.FONT_HEIGHT;
                    }
                }
            }
            LOTRTickHandlerClient.notificationDisplay.updateWindow();
            if (LOTRConfig.enableQuestTracker && minecraft.currentScreen == null && !minecraft.gameSettings.showDebugInfo) {
                LOTRTickHandlerClient.miniquestTracker.drawTracker(minecraft, (EntityPlayer)entityplayer);
            }
        }
    }

    private void updatePlayerInPortal(final EntityPlayer entityplayer, final HashMap players, final Block portalBlock) {
        if ((((Entity)entityplayer).dimension == 0 || ((Entity)entityplayer).dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) && players.containsKey(entityplayer)) {
            final boolean inPortal = ((Entity)entityplayer).worldObj.getBlock(MathHelper.floor_double(((Entity)entityplayer).posX), MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY), MathHelper.floor_double(((Entity)entityplayer).posZ)) == portalBlock;
            if (inPortal) {
                int i = players.get(entityplayer);
                ++i;
                players.put(entityplayer, i);
                if (i >= entityplayer.getMaxInPortalTime()) {
                    Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), ((Entity)entityplayer).worldObj.rand.nextFloat() * 0.4f + 0.8f));
                    players.remove(entityplayer);
                }
            }
            else {
                players.remove(entityplayer);
            }
        }
    }

    private void spawnEnvironmentFX(final EntityPlayer entityplayer, final World world) {
        world.theProfiler.startSection("lotrEnvironmentFX");
        final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
        final int j = MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
        final byte range = 16;
        for (int l = 0; l < 1000; ++l) {
            final int i2 = i + world.rand.nextInt(range) - world.rand.nextInt(range);
            final int j2 = j + world.rand.nextInt(range) - world.rand.nextInt(range);
            final int k2 = k + world.rand.nextInt(range) - world.rand.nextInt(range);
            final Block block = world.getBlock(i2, j2, k2);
            final int meta = world.getBlockMetadata(i2, j2, k2);
            if (block.getMaterial() == Material.water) {
                final BiomeGenBase biome = world.getBiomeGenForCoords(i2, k2);
                if (biome instanceof LOTRBiomeGenMirkwoodCorrupted && world.rand.nextInt(20) == 0) {
                    LOTRMod.proxy.spawnParticle("mirkwoodWater", i2 + world.rand.nextFloat(), j2 + 0.75, k2 + world.rand.nextFloat(), 0.0, 0.05, 0.0);
                }
                if (biome instanceof LOTRBiomeGenMorgulVale && world.rand.nextInt(40) == 0) {
                    LOTRMod.proxy.spawnParticle("morgulWater", i2 + world.rand.nextFloat(), j2 + 0.75, k2 + world.rand.nextFloat(), 0.0, 0.05, 0.0);
                }
                if (biome instanceof LOTRBiomeGenDeadMarshes && world.rand.nextInt(800) == 0) {
                    world.spawnEntityInWorld((Entity)new LOTREntityDeadMarshFace(world, i2 + world.rand.nextFloat(), j2 + 0.25 - world.rand.nextFloat(), k2 + world.rand.nextFloat()));
                }
            }
            if (block.getMaterial() == Material.water && meta != 0) {
                final Block below = world.getBlock(i2, j2 - 1, k2);
                if (below.getMaterial() == Material.water) {
                    for (int i3 = i2 - 1; i3 <= i2 + 1; ++i3) {
                        for (int k3 = k2 - 1; k3 <= k2 + 1; ++k3) {
                            final Block adjBlock = world.getBlock(i3, j2 - 1, k3);
                            final int adjMeta = world.getBlockMetadata(i3, j2 - 1, k3);
                            if (adjBlock.getMaterial() == Material.water && adjMeta == 0 && world.isAirBlock(i3, j2, k3)) {
                                for (int l2 = 0; l2 < 2; ++l2) {
                                    final double d = i2 + 0.5 + (i3 - i2) * world.rand.nextFloat();
                                    final double d2 = j2 + world.rand.nextFloat() * 0.2f;
                                    final double d3 = k2 + 0.5 + (k3 - k2) * world.rand.nextFloat();
                                    world.spawnParticle("explode", d, d2, d3, 0.0, 0.0, 0.0);
                                }
                            }
                        }
                    }
                }
            }
        }
        world.theProfiler.endSection();
    }

    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        if (event.world instanceof WorldClient) {
            LOTRClientProxy.customEffectRenderer.clearEffectsAndSetWorld(event.world);
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
                final ItemStack itemstack = LOTRReflectionClient.getHighlightedItemStack(guiIngame);
                if (itemstack != null && !itemstack.hasDisplayName()) {
                    final List<LOTREnchantment> enchants = LOTREnchantmentHelper.getEnchantList(itemstack);
                    if (!enchants.isEmpty()) {
                        this.lastHighlightedItemstack = itemstack;
                        this.highlightedItemstackName = (itemstack.hasDisplayName() ? itemstack.getDisplayName() : null);
                        itemstack.setStackDisplayName(LOTREnchantmentHelper.getFullEnchantedName(itemstack, itemstack.getDisplayName()));
                    }
                }
                ((World)mc.theWorld).theProfiler.endSection();
            }
            if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
                if (this.sunGlare > 0.0f && mc.gameSettings.thirdPersonView == 0) {
                    float brightness = this.prevSunGlare + (this.sunGlare - this.prevSunGlare) * partialTicks;
                    brightness *= 1.0f;
                    this.renderOverlay(null, brightness, mc, null);
                }
                if (LOTRTickHandlerClient.playersInPortals.containsKey(entityplayer)) {
                    final int i = LOTRTickHandlerClient.playersInPortals.get(entityplayer);
                    if (i > 0) {
                        this.renderOverlay(null, 0.1f + i / 100.0f * 0.6f, mc, LOTRTickHandlerClient.portalOverlay);
                    }
                }
                if (LOTRTickHandlerClient.playersInElvenPortals.containsKey(entityplayer)) {
                    final int i = LOTRTickHandlerClient.playersInElvenPortals.get(entityplayer);
                    if (i > 0) {
                        this.renderOverlay(null, 0.1f + i / (float)entityplayer.getMaxInPortalTime() * 0.6f, mc, LOTRTickHandlerClient.elvenPortalOverlay);
                    }
                }
                if (LOTRTickHandlerClient.playersInMorgulPortals.containsKey(entityplayer)) {
                    final int i = LOTRTickHandlerClient.playersInMorgulPortals.get(entityplayer);
                    if (i > 0) {
                        this.renderOverlay(null, 0.1f + i / (float)entityplayer.getMaxInPortalTime() * 0.6f, mc, LOTRTickHandlerClient.morgulPortalOverlay);
                    }
                }
                if (LOTRConfig.enableMistyMountainsMist) {
                    float mistTickF = this.prevMistTick + (this.mistTick - this.prevMistTick) * partialTicks;
                    mistTickF /= 80.0f;
                    final float mistFactorY = (float)((Entity)entityplayer).posY / 256.0f;
                    this.mistFactor = mistTickF * mistFactorY;
                    if (this.mistFactor > 0.0f) {
                        this.renderOverlay(null, this.mistFactor * 0.75f, mc, LOTRTickHandlerClient.mistOverlay);
                    }
                }
                else {
                    this.mistFactor = 0.0f;
                }
                if (this.frostTick > 0) {
                    this.renderOverlay(null, this.frostTick / 80.0f * 0.9f, mc, LOTRTickHandlerClient.frostOverlay);
                }
                if (this.burnTick > 0) {
                    this.renderOverlay(null, this.burnTick / 40.0f * 0.6f, mc, LOTRTickHandlerClient.burnOverlay);
                }
                if (this.wightLookTick > 0) {
                    this.renderOverlay(null, this.wightLookTick / 100.0f * 0.95f, mc, LOTRTickHandlerClient.wightOverlay);
                }
            }
            if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
                if (LOTRConfig.meleeAttackMeter) {
                    LOTRAttackTiming.renderAttackMeter(event.resolution, partialTicks);
                }
                if (((Entity)entityplayer).ridingEntity instanceof LOTREntitySpiderBase) {
                    final LOTREntitySpiderBase spider = (LOTREntitySpiderBase)((Entity)entityplayer).ridingEntity;
                    if (spider.shouldRenderClimbingMeter()) {
                        mc.getTextureManager().bindTexture(Gui.icons);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glDisable(3042);
                        mc.mcProfiler.startSection("spiderClimb");
                        final ScaledResolution resolution = event.resolution;
                        final int width = resolution.getScaledWidth();
                        final int height = resolution.getScaledHeight();
                        final float charge = spider.getClimbFractionRemaining();
                        final int barWidth = 182;
                        final int x = width / 2 - 91;
                        final int filled = (int)(charge * 183.0f);
                        final int top = height - 32 + 3;
                        guiIngame.drawTexturedModalRect(x, top, 0, 84, 182, 5);
                        if (filled > 0) {
                            guiIngame.drawTexturedModalRect(x, top, 0, 89, filled, 5);
                        }
                        GL11.glEnable(3042);
                        mc.mcProfiler.endSection();
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                }
            }
            if (event.type == RenderGameOverlayEvent.ElementType.HEALTH && entityplayer.isPotionActive(LOTRPoisonedDrinks.killingPoison) && !entityplayer.isPotionActive(Potion.poison)) {
                entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 20));
                this.addedClientPoisonEffect = true;
            }
            final boolean enchantingDisabled = !LOTRLevelData.clientside_thisServer_enchanting && world.provider instanceof LOTRWorldProvider;
            if (event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE && enchantingDisabled) {
                event.setCanceled(true);
                return;
            }
            if (event.type == RenderGameOverlayEvent.ElementType.ALL && enchantingDisabled && ((Entity)entityplayer).ridingEntity == null) {
                GuiIngameForge.left_height -= 6;
                GuiIngameForge.right_height -= 6;
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
                final int level = LOTRWeaponStats.getTotalArmorValue((EntityPlayer)mc.thePlayer);
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
    public void onRenderDebugText(final RenderGameOverlayEvent.Text event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings.showDebugInfo && mc.theWorld != null && mc.thePlayer != null && mc.theWorld.getWorldChunkManager() instanceof LOTRWorldChunkManager) {
            ((World)mc.theWorld).theProfiler.startSection("lotrBiomeDisplay");
            final LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)mc.theWorld.getWorldChunkManager();
            final int i = MathHelper.floor_double(((Entity)mc.thePlayer).posX);
            final int j = MathHelper.floor_double(((Entity)mc.thePlayer).boundingBox.minY);
            final int k = MathHelper.floor_double(((Entity)mc.thePlayer).posZ);
            final LOTRBiome biome = (LOTRBiome)mc.theWorld.getBiomeGenForCoords(i, k);
            final LOTRBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
            event.left.add(null);
            biome.addBiomeF3Info(event.left, (World)mc.theWorld, variant, i, j, k);
            ((World)mc.theWorld).theProfiler.endSection();
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final float f = event.partialTicks;
        mc.entityRenderer.enableLightmap((double)f);
        RenderHelper.disableStandardItemLighting();
        LOTRClientProxy.customEffectRenderer.renderParticles((Entity)mc.renderViewEntity, f);
        mc.entityRenderer.disableLightmap((double)f);
        if (Minecraft.isGuiEnabled() && mc.entityRenderer.debugViewDirection == 0) {
            mc.mcProfiler.startSection("lotrSpeech");
            LOTRNPCRendering.renderAllNPCSpeeches(mc, (World)mc.theWorld, f);
            mc.mcProfiler.endSection();
        }
    }

    @SubscribeEvent
    public void getItemTooltip(final ItemTooltipEvent event) {
        final ItemStack itemstack = event.itemStack;
        final List<String> tooltip = (List<String>)event.toolTip;
        final EntityPlayer entityplayer = event.entityPlayer;
        final List<LOTREnchantment> enchantments = LOTREnchantmentHelper.getEnchantList(itemstack);
        if (!itemstack.hasDisplayName() && !enchantments.isEmpty()) {
            String name = tooltip.get(0);
            name = LOTREnchantmentHelper.getFullEnchantedName(itemstack, name);
            tooltip.set(0, name);
        }
        if (itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
            final String squadron = LOTRSquadrons.getSquadron(itemstack);
            if (!StringUtils.isNullOrEmpty(squadron)) {
                final List<String> newTooltip = new ArrayList<String>();
                newTooltip.add(tooltip.get(0));
                newTooltip.add(StatCollector.translateToLocalFormatted("item.lotr.generic.squadron", new Object[] { squadron }));
                for (int i = 1; i < tooltip.size(); ++i) {
                    newTooltip.add(tooltip.get(i));
                }
                tooltip.clear();
                tooltip.addAll(newTooltip);
            }
        }
        if (LOTRWeaponStats.isMeleeWeapon(itemstack)) {
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
                final float meleeDamage = LOTRWeaponStats.getMeleeDamageBonus(itemstack);
                newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.meleeDamage", new Object[] { meleeDamage }));
                final float meleeSpeed = LOTRWeaponStats.getMeleeSpeed(itemstack);
                final int pcSpeed = Math.round(meleeSpeed * 100.0f);
                newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.meleeSpeed", new Object[] { pcSpeed }));
                final float reach = LOTRWeaponStats.getMeleeReachFactor(itemstack);
                final int pcReach = Math.round(reach * 100.0f);
                newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.reach", new Object[] { pcReach }));
                final int kb = LOTRWeaponStats.getTotalKnockback(itemstack);
                if (kb > 0) {
                    newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.kb", new Object[] { kb }));
                }
                for (int k = dmgIndex + 1; k < tooltip.size(); ++k) {
                    newTooltip.add(tooltip.get(k));
                }
                tooltip.clear();
                tooltip.addAll(newTooltip);
            }
        }
        if (LOTRWeaponStats.isRangedWeapon(itemstack)) {
            tooltip.add("");
            final float drawSpeed = LOTRWeaponStats.getRangedSpeed(itemstack);
            if (drawSpeed > 0.0f) {
                final int pcSpeed2 = Math.round(drawSpeed * 100.0f);
                tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.rangedSpeed", new Object[] { pcSpeed2 }));
            }
            final float damage = LOTRWeaponStats.getRangedDamageFactor(itemstack, false);
            if (damage > 0.0f) {
                final int pcDamage = Math.round(damage * 100.0f);
                tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.rangedDamage", new Object[] { pcDamage }));
                if (itemstack.getItem() instanceof ItemBow || itemstack.getItem() instanceof LOTRItemCrossbow) {
                    final float range = LOTRWeaponStats.getRangedDamageFactor(itemstack, true);
                    final int pcRange = Math.round(range * 100.0f);
                    tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.range", new Object[] { pcRange }));
                }
            }
            final int kb2 = LOTRWeaponStats.getRangedKnockback(itemstack);
            if (kb2 > 0) {
                tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.kb", new Object[] { kb2 }));
            }
        }
        if (LOTRWeaponStats.isPoisoned(itemstack)) {
            tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("lotr.weaponstat.poison", new Object[0]));
        }
        final int armorProtect = LOTRWeaponStats.getArmorProtection(itemstack);
        if (armorProtect > 0) {
            tooltip.add("");
            final int pcProtection = Math.round(armorProtect / 25.0f * 100.0f);
            tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("lotr.weaponstat.protection", new Object[] { armorProtect, pcProtection }));
        }
        if (!enchantments.isEmpty()) {
            tooltip.add("");
            final List<String> enchGood = new ArrayList<String>();
            final List<String> enchBad = new ArrayList<String>();
            for (final LOTREnchantment ench : enchantments) {
                final String enchDesc = ench.getNamedFormattedDescription(itemstack);
                if (ench.isBeneficial()) {
                    enchGood.add(enchDesc);
                }
                else {
                    enchBad.add(enchDesc);
                }
            }
            tooltip.addAll(enchGood);
            tooltip.addAll(enchBad);
        }
        if (LOTRPoisonedDrinks.isDrinkPoisoned(itemstack) && LOTRPoisonedDrinks.canPlayerSeePoisoned(itemstack, entityplayer)) {
            tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("item.lotr.drink.poison"));
        }
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTROwner")) {
            tooltip.add("");
            String owner = itemstack.getTagCompound().getString("LOTROwner");
            owner = StatCollector.translateToLocalFormatted("item.lotr.generic.owner", new Object[] { owner });
            final List<String> ownerLines = (List<String>)Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(owner, 200);
            for (int l = 0; l < ownerLines.size(); ++l) {
                String line = ownerLines.get(l);
                if (l > 0) {
                    line = "  " + line;
                }
                tooltip.add(line);
            }
        }
        if (itemstack.getItem() == Item.getItemFromBlock(Blocks.monster_egg)) {
            tooltip.set(0, EnumChatFormatting.RED + tooltip.get(0));
        }
        if (LOTRMod.isAprilFools()) {
            String name2 = tooltip.get(0);
            name2 = name2.replace("kebab", "gyros");
            name2 = name2.replace("Kebab", "Gyros");
            tooltip.set(0, name2);
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
            if (item instanceof LOTRItemBow) {
                maxDrawTime = (float)((LOTRItemBow)item).getMaxDrawTime();
            }
            else if (item instanceof LOTRItemCrossbow) {
                maxDrawTime = (float)((LOTRItemCrossbow)item).getMaxDrawTime();
            }
            else if (item instanceof LOTRItemSpear) {
                maxDrawTime = (float)((LOTRItemSpear)item).getMaxDrawTime();
            }
            else if (item instanceof LOTRItemBlowgun) {
                maxDrawTime = (float)((LOTRItemBlowgun)item).getMaxDrawTime();
            }
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
        if (LOTRItemCrossbow.isLoaded(itemstack)) {
            usage = 1.0f;
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
        if (provider instanceof LOTRWorldProvider) {
            final LOTRBiome lotrbiome = (LOTRBiome)biome;
            final float[] fogStartEnd = ((LOTRWorldProvider)provider).modifyFogIntensity(farPlane, fogMode);
            float fogStart = fogStartEnd[0];
            float fogEnd = fogStartEnd[1];
            if (LOTRConfig.newRain && (lotrbiome.getEnableRain() || lotrbiome.getEnableSnow())) {
                final float rain = this.prevRainFactor + (this.rainFactor - this.prevRainFactor) * LOTRTickHandlerClient.renderTick;
                if (rain > 0.0f) {
                    final float rainOpacityStart = 0.95f;
                    final float rainOpacityEnd = 0.2f;
                    fogStart -= fogStart * (rain * rainOpacityStart);
                    fogEnd -= fogEnd * (rain * rainOpacityEnd);
                }
            }
            if (this.mistFactor > 0.0f) {
                final float mistOpacityStart = 0.95f;
                final float mistOpacityEnd = 0.7f;
                fogStart -= fogStart * (this.mistFactor * mistOpacityStart);
                fogEnd -= fogEnd * (this.mistFactor * mistOpacityEnd);
            }
            float wightFactor = this.prevWightNearTick + (this.wightNearTick - this.prevWightNearTick) * LOTRTickHandlerClient.renderTick;
            wightFactor /= 100.0f;
            if (wightFactor > 0.0f) {
                final float wightOpacityStart = 0.97f;
                final float wightOpacityEnd = 0.75f;
                fogStart -= fogStart * (wightFactor * wightOpacityStart);
                fogEnd -= fogEnd * (wightFactor * wightOpacityEnd);
            }
            if (lotrbiome instanceof LOTRBiomeGenBarrowDowns) {
                if (wightFactor > 0.0f) {
                    final int sky0 = lotrbiome.getBaseSkyColorByTemp(i, j, k);
                    final int sky2 = 9674385;
                    final int clouds0 = 16777215;
                    final int clouds2 = 11842740;
                    final int fog0 = 16777215;
                    final int fog2 = 10197915;
                    lotrbiome.biomeColors.setSky(LOTRColorUtil.lerpColors_I(sky0, sky2, wightFactor));
                    lotrbiome.biomeColors.setClouds(LOTRColorUtil.lerpColors_I(clouds0, clouds2, wightFactor));
                    lotrbiome.biomeColors.setFog(LOTRColorUtil.lerpColors_I(fog0, fog2, wightFactor));
                }
                else {
                    lotrbiome.biomeColors.resetSky();
                    lotrbiome.biomeColors.resetClouds();
                    lotrbiome.biomeColors.resetFog();
                }
            }
            this.balrogFactor = this.prevBalrogNearTick + (this.balrogNearTick - this.prevBalrogNearTick) * LOTRTickHandlerClient.renderTick;
            this.balrogFactor /= 100.0f;
            if (this.balrogFactor > 0.0f) {
                final float balrogOpacityStart = 0.98f;
                final float balrogOpacityEnd = 0.75f;
                fogStart -= fogStart * (this.balrogFactor * balrogOpacityStart);
                fogEnd -= fogEnd * (this.balrogFactor * balrogOpacityEnd);
            }
            GL11.glFogf(2915, fogStart);
            GL11.glFogf(2916, fogEnd);
        }
    }

    @SubscribeEvent
    public void onFogColors(final EntityViewRenderEvent.FogColors event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        final WorldProvider provider = world.provider;
        if (provider instanceof LOTRWorldProvider) {
            float[] rgb = { event.red, event.green, event.blue };
            rgb = ((LOTRWorldProvider)provider).handleFinalFogColors(event.entity, event.renderPartialTicks, rgb);
            event.red = rgb[0];
            event.green = rgb[1];
            event.blue = rgb[2];
        }
        if (this.balrogFactor > 0.0f) {
            final int shadowColor = 1114112;
            float[] rgb2 = { event.red, event.green, event.blue };
            rgb2 = LOTRColorUtil.lerpColors(rgb2, shadowColor, this.balrogFactor);
            event.red = rgb2[0];
            event.green = rgb2[1];
            event.blue = rgb2[2];
        }
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
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        final LOTRFaction viewingFac = pd.getViewingFaction();
        final ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        final int width = resolution.getScaledWidth();
        final int height = resolution.getScaledHeight();
        final boolean boss = BossStatus.bossName != null && BossStatus.statusBarTime > 0;
        this.alignmentXBase = width / 2 + LOTRConfig.alignmentXOffset;
        this.alignmentYBase = 4 + LOTRConfig.alignmentYOffset;
        if (boss) {
            this.alignmentYBase += 20;
        }
        if (this.firstAlignmentRender) {
            LOTRAlignmentTicker.updateAll(entityplayer, true);
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
        final float alignment = LOTRAlignmentTicker.forFaction(viewingFac).getInterpolatedAlignment(f);
        renderAlignmentBar(alignment, false, viewingFac, alignmentXF, alignmentYF, text, text, text, false);
        if (LOTRTickHandlerClient.alignDrainTick > 0 && text) {
            float alpha = 1.0f;
            final int fadeTick = 20;
            if (LOTRTickHandlerClient.alignDrainTick < fadeTick) {
                alpha = LOTRTickHandlerClient.alignDrainTick / (float)fadeTick;
            }
            renderAlignmentDrain(mc, (int)alignmentXF - 155, (int)alignmentYF + 2, LOTRTickHandlerClient.alignDrainNum, alpha);
        }
    }

    public static void renderAlignmentBar(final float alignment, final boolean isOtherPlayer, final LOTRFaction faction, final float x, final float y, final boolean renderFacName, final boolean renderValue, final boolean renderLimits, final boolean renderLimitValues) {
        final Minecraft mc = Minecraft.getMinecraft();
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        final LOTRPlayerData clientPD = LOTRLevelData.getData(entityplayer);
        final LOTRFactionRank rank = faction.getRank(alignment);
        final boolean pledged = clientPD.isPledgedTo(faction);
        final LOTRAlignmentTicker ticker = LOTRAlignmentTicker.forFaction(faction);
        float alignMin = 0.0f;
        float alignMax = 0.0f;
        LOTRFactionRank rankMin = null;
        LOTRFactionRank rankMax = null;
        final float pastRankMultiplier = 10.0f;
        if (!rank.isDummyRank()) {
            alignMin = rank.alignment;
            rankMin = rank;
            final LOTRFactionRank nextRank = faction.getRankAbove(rank);
            if (nextRank != null && !nextRank.isDummyRank() && nextRank != rank) {
                alignMax = nextRank.alignment;
                rankMax = nextRank;
            }
            else {
                alignMax = rank.alignment * 10.0f;
                rankMax = rank;
                while (alignment >= alignMax) {
                    alignMin = alignMax;
                    alignMax = alignMin * 10.0f;
                }
            }
        }
        else {
            final LOTRFactionRank firstRank = faction.getFirstRank();
            float firstRankAlign;
            if (firstRank != null && !firstRank.isDummyRank()) {
                firstRankAlign = firstRank.alignment;
            }
            else {
                firstRankAlign = 10.0f;
            }
            if (Math.abs(alignment) < firstRankAlign) {
                alignMin = -firstRankAlign;
                alignMax = firstRankAlign;
                rankMin = LOTRFactionRank.RANK_ENEMY;
                rankMax = ((firstRank != null && !firstRank.isDummyRank()) ? firstRank : LOTRFactionRank.RANK_NEUTRAL);
            }
            else if (alignment < 0.0f) {
                alignMax = -firstRankAlign;
                alignMin = alignMax * 10.0f;
                rankMax = (rankMin = LOTRFactionRank.RANK_ENEMY);
                while (alignment <= alignMin) {
                    alignMax *= 10.0f;
                    alignMin = alignMax * 10.0f;
                }
            }
            else {
                alignMin = firstRankAlign;
                alignMax = alignMin * 10.0f;
                rankMax = (rankMin = LOTRFactionRank.RANK_NEUTRAL);
                while (alignment >= alignMax) {
                    alignMin = alignMax;
                    alignMax = alignMin * 10.0f;
                }
            }
        }
        final float ringProgress = (alignment - alignMin) / (alignMax - alignMin);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
        final int barWidth = 232;
        final int barHeight = 14;
        final int activeBarWidth = 220;
        final float[] factionColors = faction.getFactionColorComponents();
        GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], 1.0f);
        drawTexturedModalRect(x - barWidth / 2, y, 0, 14, barWidth, barHeight);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        drawTexturedModalRect(x - barWidth / 2, y, 0, 0, barWidth, barHeight);
        final float ringProgressAdj = (ringProgress - 0.5f) * 2.0f;
        final int ringSize = 16;
        final float ringX = x - ringSize / 2 + ringProgressAdj * activeBarWidth / 2.0f;
        final float ringY = y + barHeight / 2 - ringSize / 2;
        final int flashTick = ticker.flashTick;
        if (pledged) {
            drawTexturedModalRect(ringX, ringY, 16 * Math.round((float)(flashTick / 3)), 212, ringSize, ringSize);
        }
        else {
            drawTexturedModalRect(ringX, ringY, 16 * Math.round((float)(flashTick / 3)), 36, ringSize, ringSize);
        }
        if (faction.isPlayableAlignmentFaction()) {
            float alpha = 0.0f;
            boolean definedZone = false;
            if (faction.inControlZone(entityplayer)) {
                alpha = 1.0f;
                definedZone = faction.inDefinedControlZone(entityplayer);
            }
            else {
                alpha = faction.getControlZoneAlignmentMultiplier(entityplayer);
                definedZone = true;
            }
            if (alpha > 0.0f) {
                final int arrowSize = 14;
                final int y2 = definedZone ? 60 : 88;
                final int y3 = definedZone ? 74 : 102;
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], alpha);
                drawTexturedModalRect(x - barWidth / 2 - arrowSize, y, 0, y3, arrowSize, arrowSize);
                drawTexturedModalRect(x + barWidth / 2, y, arrowSize, y3, arrowSize, arrowSize);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                drawTexturedModalRect(x - barWidth / 2 - arrowSize, y, 0, y2, arrowSize, arrowSize);
                drawTexturedModalRect(x + barWidth / 2, y, arrowSize, y2, arrowSize, arrowSize);
                GL11.glDisable(3042);
            }
        }
        final FontRenderer fr = mc.fontRenderer;
        final int textX = Math.round(x);
        final int textY = Math.round(y + barHeight + 4.0f);
        if (renderLimits) {
            String sMin = rankMin.getShortNameWithGender(clientPD);
            String sMax = rankMax.getShortNameWithGender(clientPD);
            if (renderLimitValues) {
                sMin = StatCollector.translateToLocalFormatted("lotr.gui.factions.alignment.limits", new Object[] { sMin, LOTRAlignmentValues.formatAlignForDisplay(alignMin) });
                sMax = StatCollector.translateToLocalFormatted("lotr.gui.factions.alignment.limits", new Object[] { sMax, LOTRAlignmentValues.formatAlignForDisplay(alignMax) });
            }
            final int limitsX = barWidth / 2 - 6;
            final int xMin = Math.round(x - limitsX);
            final int xMax = Math.round(x + limitsX);
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            drawAlignmentText(fr, xMin * 2 - fr.getStringWidth(sMin) / 2, textY * 2, sMin, 1.0f);
            drawAlignmentText(fr, xMax * 2 - fr.getStringWidth(sMax) / 2, textY * 2, sMax, 1.0f);
            GL11.glPopMatrix();
        }
        if (renderFacName) {
            final String name = faction.factionName();
            drawAlignmentText(fr, textX - fr.getStringWidth(name) / 2, textY, name, 1.0f);
        }
        if (renderValue) {
            final int numericalTick = ticker.numericalTick;
            String alignS;
            float alignAlpha;
            if (numericalTick > 0) {
                alignS = LOTRAlignmentValues.formatAlignForDisplay(alignment);
                alignAlpha = LOTRFunctions.triangleWave((float)numericalTick, 0.7f, 1.0f, 30.0f);
                final int fadeTick = 15;
                if (numericalTick < fadeTick) {
                    alignAlpha *= numericalTick / (float)fadeTick;
                }
            }
            else {
                alignS = rank.getShortNameWithGender(clientPD);
                alignAlpha = 1.0f;
            }
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            drawAlignmentText(fr, textX - fr.getStringWidth(alignS) / 2, textY + fr.FONT_HEIGHT + 3, alignS, alignAlpha);
            GL11.glDisable(3042);
        }
    }

    public static void renderAlignmentDrain(final Minecraft mc, final int x, final int y, final int numFactions) {
        renderAlignmentDrain(mc, x, y, numFactions, 1.0f);
    }

    public static void renderAlignmentDrain(final Minecraft mc, final int x, final int y, final int numFactions, final float alpha) {
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
        drawTexturedModalRect(x, y, 0, 128, 16, 16);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final String s = "-" + numFactions;
        final FontRenderer fr = mc.fontRenderer;
        drawBorderedText(fr, x + 8 - fr.getStringWidth(s) / 2, y + 8 - fr.FONT_HEIGHT / 2, s, 16777215, alpha);
        GL11.glDisable(3042);
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
        float f = this.prevWightLookTick + (this.wightLookTick - this.prevWightLookTick) * LOTRTickHandlerClient.renderTick;
        f /= 100.0f;
        return f;
    }

    static {
        LOTRTickHandlerClient.portalOverlay = new ResourceLocation("lotr:misc/portal_overlay.png");
        LOTRTickHandlerClient.elvenPortalOverlay = new ResourceLocation("lotr:misc/elvenportal_overlay.png");
        LOTRTickHandlerClient.morgulPortalOverlay = new ResourceLocation("lotr:misc/morgulportal_overlay.png");
        LOTRTickHandlerClient.mistOverlay = new ResourceLocation("lotr:misc/mist_overlay.png");
        LOTRTickHandlerClient.frostOverlay = new ResourceLocation("lotr:misc/frost_overlay.png");
        LOTRTickHandlerClient.burnOverlay = new ResourceLocation("lotr:misc/burn_overlay.png");
        LOTRTickHandlerClient.wightOverlay = new ResourceLocation("lotr:misc/wight.png");
        LOTRTickHandlerClient.playersInPortals = new HashMap();
        LOTRTickHandlerClient.playersInElvenPortals = new HashMap();
        LOTRTickHandlerClient.playersInMorgulPortals = new HashMap();
        LOTRTickHandlerClient.scrapTraderMisbehaveTick = 0;
    }
}
