// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import java.util.HashMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import lotr.client.render.LOTRBufferedImageIcon;
import net.minecraft.util.IIcon;
import lotr.common.util.LOTRLog;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.texture.AbstractTexture;
import java.awt.Color;
import net.minecraft.client.renderer.texture.DynamicTexture;
import lotr.common.util.LOTRColorUtil;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import lotr.common.LOTRDimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import javax.imageio.ImageIO;
import lotr.common.world.biome.LOTRBiome;
import lotr.client.gui.LOTRGuiMap;
import net.minecraft.client.renderer.OpenGlHelper;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import lotr.common.LOTRConfig;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.common.util.LOTRCommonIcons;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.resources.IReloadableResourceManager;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class LOTRTextures implements IResourceManagerReloadListener
{
    private static Minecraft mc;
    public static ResourceLocation missingTexture;
    private static ResourceLocation mapTexture;
    private static ResourceLocation sepiaMapTexture;
    public static ResourceLocation overlayTexture;
    public static ResourceLocation mapTerrain;
    public static final ResourceLocation osrsTexture;
    public static final int OSRS_WATER = 6453158;
    public static final int OSRS_GRASS = 5468426;
    public static final int OSRS_BEACH = 9279778;
    public static final int OSRS_HILL = 6575407;
    public static final int OSRS_MOUNTAIN = 14736861;
    public static final int OSRS_MOUNTAIN_EDGE = 9005125;
    public static final int OSRS_SNOW = 14215139;
    public static final int OSRS_TUNDRA = 9470587;
    public static final int OSRS_SAND = 13548147;
    public static final int OSRS_TREE = 2775058;
    public static final int OSRS_WILD = 3290677;
    public static final int OSRS_PATH = 6575407;
    public static final int OSRS_KINGDOM_COLOR = 16755200;
    private static ResourceLocation particleTextures;
    private static ResourceLocation newWaterParticles;
    private static int newWaterU;
    private static int newWaterV;
    private static int newWaterWidth;
    private static int newWaterHeight;
    private static Map<ResourceLocation, ResourceLocation> eyesTextures;
    private static Map<ResourceLocation, Integer> averagedPageColors;
    
    public static void load() {
        final IResourceManager resMgr = LOTRTextures.mc.getResourceManager();
        final TextureManager texMgr = LOTRTextures.mc.getTextureManager();
        final LOTRTextures textures = new LOTRTextures();
        textures.onResourceManagerReload(resMgr);
        ((IReloadableResourceManager)resMgr).registerReloadListener((IResourceManagerReloadListener)textures);
        MinecraftForge.EVENT_BUS.register((Object)textures);
        final TextureMap texMapBlocks = LOTRTextures.mc.getTextureMapBlocks();
        final TextureMap texMapItems = (TextureMap)texMgr.getTexture(texMgr.getResourceLocation(1));
        textures.preTextureStitch(new TextureStitchEvent.Pre(texMapBlocks));
        textures.preTextureStitch(new TextureStitchEvent.Pre(texMapItems));
    }
    
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        loadMapTextures();
        replaceWaterParticles();
        LOTRTextures.eyesTextures.clear();
        LOTRTextures.averagedPageColors.clear();
    }
    
    @SubscribeEvent
    public void preTextureStitch(final TextureStitchEvent.Pre event) {
        final TextureMap map = event.map;
        if (map.getTextureType() == 0) {
            LOTRCommonIcons.iconEmptyBlock = generateIconEmpty(map);
        }
        if (map.getTextureType() == 1) {
            LOTRCommonIcons.iconEmptyItem = generateIconEmpty(map);
            LOTRCommonIcons.iconMeleeWeapon = map.registerIcon("lotr:slotMelee");
            LOTRCommonIcons.iconBomb = map.registerIcon("lotr:slotBomb");
        }
    }
    
    public static void drawMap(final EntityPlayer entityplayer, final double x0, final double x1, final double y0, final double y1, final double z, final double minU, final double maxU, final double minV, final double maxV) {
        drawMap(entityplayer, LOTRConfig.enableSepiaMap, x0, x1, y0, y1, z, minU, maxU, minV, maxV, 1.0f);
    }
    
    public static void drawMap(final EntityPlayer entityplayer, final boolean sepia, final double x0, final double x1, final double y0, final double y1, final double z, final double minU, final double maxU, final double minV, final double maxV, final float alpha) {
        final Tessellator tessellator = Tessellator.instance;
        LOTRTextures.mc.getTextureManager().bindTexture(getMapTexture(entityplayer, sepia));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x0, y1, z, minU, maxV);
        tessellator.addVertexWithUV(x1, y1, z, maxU, maxV);
        tessellator.addVertexWithUV(x1, y0, z, maxU, minV);
        tessellator.addVertexWithUV(x0, y0, z, minU, minV);
        tessellator.draw();
        final boolean meneltarma = entityplayer != null && LOTRLevelData.getData(entityplayer).hasAchievement(LOTRAchievement.enterMeneltarma);
        if (!meneltarma) {
            final int mtX = LOTRWaypoint.MENELTARMA_SUMMIT.getX();
            final int mtY = LOTRWaypoint.MENELTARMA_SUMMIT.getY();
            final int mtW = 20;
            double mtMinU = (mtX - mtW) / (double)LOTRGenLayerWorld.imageWidth;
            double mtMaxU = (mtX + mtW) / (double)LOTRGenLayerWorld.imageWidth;
            double mtMinV = (mtY - mtW) / (double)LOTRGenLayerWorld.imageHeight;
            double mtMaxV = (mtY + mtW) / (double)LOTRGenLayerWorld.imageHeight;
            if (minU <= mtMaxU && maxU >= mtMinU && minV <= mtMaxV && maxV >= mtMinV) {
                GL11.glDisable(3553);
                final int oceanColor = getMapOceanColor(sepia);
                mtMinU = Math.max(mtMinU, minU);
                mtMaxU = Math.min(mtMaxU, maxU);
                mtMinV = Math.max(mtMinV, minV);
                mtMaxV = Math.min(mtMaxV, maxV);
                final double ratioX = (x1 - x0) / (maxU - minU);
                final double ratioY = (y1 - y0) / (maxV - minV);
                final double mtX2 = x0 + (mtMinU - minU) * ratioX;
                final double mtX3 = x0 + (mtMaxU - minU) * ratioX;
                final double mtY2 = y0 + (mtMinV - minV) * ratioY;
                final double mtY3 = y0 + (mtMaxV - minV) * ratioY;
                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(oceanColor);
                tessellator.addVertexWithUV(mtX2, mtY3, z, mtMinU, mtMaxV);
                tessellator.addVertexWithUV(mtX3, mtY3, z, mtMaxU, mtMaxV);
                tessellator.addVertexWithUV(mtX3, mtY2, z, mtMaxU, mtMinV);
                tessellator.addVertexWithUV(mtX2, mtY2, z, mtMinU, mtMinV);
                tessellator.draw();
                GL11.glEnable(3553);
            }
        }
    }
    
    public static void drawMapOverlay(final EntityPlayer entityplayer, final double x0, final double x1, final double y0, final double y1, final double z, final double minU, final double maxU, final double minV, final double maxV) {
        final Tessellator tessellator = Tessellator.instance;
        LOTRTextures.mc.getTextureManager().bindTexture(LOTRTextures.overlayTexture);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x0, y1, z, 0.0, 1.0);
        tessellator.addVertexWithUV(x1, y1, z, 1.0, 1.0);
        tessellator.addVertexWithUV(x1, y0, z, 1.0, 0.0);
        tessellator.addVertexWithUV(x0, y0, z, 0.0, 0.0);
        tessellator.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }
    
    public static void drawMapCompassBottomLeft(final double x, final double y, final double z, final double scale) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        LOTRTextures.mc.getTextureManager().bindTexture(LOTRGuiMap.mapIconsTexture);
        final int width = 32;
        final int height = 32;
        final double x2 = x;
        final double x3 = x + width * scale;
        final double y2 = y - height * scale;
        final double y3 = y;
        final int texU = 224;
        final int texV = 200;
        final float u0 = texU / 256.0f;
        final float u2 = (texU + width) / 256.0f;
        final float v0 = texV / 256.0f;
        final float v2 = (texV + height) / 256.0f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x2, y3, z, (double)u0, (double)v2);
        tessellator.addVertexWithUV(x3, y3, z, (double)u2, (double)v2);
        tessellator.addVertexWithUV(x3, y2, z, (double)u2, (double)v0);
        tessellator.addVertexWithUV(x2, y2, z, (double)u0, (double)v0);
        tessellator.draw();
    }
    
    private static ResourceLocation getMapTexture(final EntityPlayer entityplayer, final boolean sepia) {
        return (LOTRConfig.osrsMap || sepia) ? LOTRTextures.sepiaMapTexture : LOTRTextures.mapTexture;
    }
    
    public static int getMapOceanColor(final boolean sepia) {
        if (LOTRConfig.osrsMap) {
            return -10324058;
        }
        int ocean = LOTRBiome.ocean.color;
        if (sepia) {
            ocean = getSepia(ocean);
        }
        return ocean;
    }
    
    public static void loadMapTextures() {
        LOTRTextures.mapTexture = new ResourceLocation("lotr:map/map.png");
        try {
            final BufferedImage mapImage = ImageIO.read(LOTRTextures.mc.getResourceManager().getResource(LOTRTextures.mapTexture).getInputStream());
            LOTRTextures.sepiaMapTexture = convertToSepia(mapImage, "lotr:map_sepia");
        }
        catch (IOException e) {
            FMLLog.severe("Failed to generate LOTR sepia map", new Object[0]);
            e.printStackTrace();
            LOTRTextures.sepiaMapTexture = LOTRTextures.mapTexture;
        }
    }
    
    private static ResourceLocation convertToSepia(final BufferedImage srcImage, final String name) {
        final int mapWidth = srcImage.getWidth();
        final int mapHeight = srcImage.getHeight();
        final int[] colors = srcImage.getRGB(0, 0, mapWidth, mapHeight, null, 0, mapWidth);
        for (int i = 0; i < colors.length; ++i) {
            int color = colors[i];
            if (LOTRConfig.osrsMap) {
                final Integer biomeID = LOTRDimension.MIDDLE_EARTH.colorsToBiomeIDs.get(color);
                if (biomeID == null) {
                    color = getMapOceanColor(true);
                }
                else {
                    final LOTRBiome biome = LOTRDimension.MIDDLE_EARTH.biomeList[biomeID];
                    if (biome.heightBaseParameter < 0.0f) {
                        color = 6453158;
                    }
                    else if (biome.heightBaseParameter > 0.8f) {
                        color = 14736861;
                    }
                    else if (biome.heightBaseParameter > 0.4f) {
                        color = 6575407;
                    }
                    else if (biome instanceof LOTRBiomeGenMordor) {
                        color = 3290677;
                    }
                    else if (biome.decorator.treesPerChunk > 1) {
                        color = 2775058;
                    }
                    else if (biome.temperature < 0.3f) {
                        if (biome.temperature < 0.2f) {
                            color = 14215139;
                        }
                        else {
                            color = 9470587;
                        }
                    }
                    else if (biome.rainfall < 0.2f) {
                        color = 13548147;
                    }
                    else {
                        color = 5468426;
                    }
                }
            }
            else {
                color = getSepia(color);
            }
            colors[i] = color;
        }
        BufferedImage newMapImage = new BufferedImage(mapWidth, mapHeight, 2);
        newMapImage.setRGB(0, 0, mapWidth, mapHeight, colors, 0, mapWidth);
        if (LOTRConfig.osrsMap) {
            final BufferedImage temp = newMapImage;
            newMapImage = new BufferedImage(mapWidth, mapHeight, 2);
            for (int j = 0; j < mapWidth; ++j) {
                for (int k = 0; k < mapHeight; ++k) {
                    int rgb = temp.getRGB(j, k);
                    if (rgb == 5468426) {
                        final int range = 8;
                        int water = 0;
                        int total = 0;
                        for (int x = -range; x < range; ++x) {
                            for (int y = -range; y < range; ++y) {
                                final int x2 = j + x;
                                final int y2 = y + k;
                                if (x2 >= 0 && x2 < mapWidth && y2 >= 0 && y2 < mapHeight) {
                                    final int rgb2 = temp.getRGB(x2, y2);
                                    if (rgb2 == 6453158) {
                                        ++water;
                                    }
                                    ++total;
                                }
                            }
                        }
                        if (water > 0) {
                            final float ratio = water / (float)total;
                            rgb = LOTRColorUtil.lerpColors_I(5468426, 9279778, ratio * 2.0f);
                        }
                    }
                    else if (rgb == 14736861) {
                        final int range = 8;
                        int edge = 0;
                        int total = 0;
                        for (int x = -range; x < range; ++x) {
                            for (int y = -range; y < range; ++y) {
                                final int x2 = j + x;
                                final int y2 = y + k;
                                if (x2 >= 0 && x2 < mapWidth && y2 >= 0 && y2 < mapHeight) {
                                    final int rgb2 = temp.getRGB(x2, y2);
                                    if (rgb2 != 14736861) {
                                        ++edge;
                                    }
                                    ++total;
                                }
                            }
                        }
                        if (edge > 0) {
                            final float ratio = edge / (float)total;
                            rgb = LOTRColorUtil.lerpColors_I(14736861, 9005125, ratio * 1.5f);
                        }
                    }
                    newMapImage.setRGB(j, k, rgb | 0xFF000000);
                }
            }
        }
        final ResourceLocation sepiaTexture = LOTRTextures.mc.renderEngine.getDynamicTextureLocation(name, new DynamicTexture(newMapImage));
        return sepiaTexture;
    }
    
    private static int getSepia(final int rgb) {
        final Color color = new Color(rgb);
        final int alpha = rgb >> 24 & 0xFF;
        final float[] colors = color.getColorComponents(null);
        final float r = colors[0];
        final float g = colors[1];
        final float b = colors[2];
        float newR = r * 0.79f + g * 0.39f + b * 0.26f;
        float newG = r * 0.52f + g * 0.35f + b * 0.19f;
        float newB = r * 0.35f + g * 0.26f + b * 0.15f;
        newR = Math.min(Math.max(0.0f, newR), 1.0f);
        newG = Math.min(Math.max(0.0f, newG), 1.0f);
        newB = Math.min(Math.max(0.0f, newB), 1.0f);
        int sepia = new Color(newR, newG, newB).getRGB();
        sepia |= alpha << 24;
        return sepia;
    }
    
    public static void replaceWaterParticles() {
        try {
            final BufferedImage particles = ImageIO.read(LOTRTextures.mc.getResourcePackRepository().rprDefaultResourcePack.getInputStream(LOTRTextures.particleTextures));
            final BufferedImage waterParticles = ImageIO.read(LOTRTextures.mc.getResourceManager().getResource(LOTRTextures.newWaterParticles).getInputStream());
            final int[] rgb = waterParticles.getRGB(0, 0, waterParticles.getWidth(), waterParticles.getHeight(), null, 0, waterParticles.getWidth());
            particles.setRGB(LOTRTextures.newWaterU, LOTRTextures.newWaterV, LOTRTextures.newWaterWidth, LOTRTextures.newWaterHeight, rgb, 0, LOTRTextures.newWaterWidth);
            final TextureManager textureManager = LOTRTextures.mc.getTextureManager();
            textureManager.bindTexture(LOTRTextures.particleTextures);
            final AbstractTexture texture = (AbstractTexture)textureManager.getTexture(LOTRTextures.particleTextures);
            TextureUtil.uploadTextureImageAllocate(texture.getGlTextureId(), particles, false, false);
        }
        catch (IOException e) {
            FMLLog.severe("Failed to replace rain particles", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static ResourceLocation getEyesTexture(final ResourceLocation skin, final int[][] eyesCoords, final int eyeWidth, final int eyeHeight) {
        ResourceLocation eyes = LOTRTextures.eyesTextures.get(skin);
        if (eyes == null) {
            try {
                final BufferedImage skinImage = ImageIO.read(LOTRTextures.mc.getResourceManager().getResource(skin).getInputStream());
                final int skinWidth = skinImage.getWidth();
                final int skinHeight = skinImage.getHeight();
                final BufferedImage eyesImage = new BufferedImage(skinWidth, skinHeight, 2);
                for (final int[] eyePos : eyesCoords) {
                    final int eyeX = eyePos[0];
                    final int eyeY = eyePos[1];
                    for (int i = eyeX; i < eyeX + eyeWidth; ++i) {
                        for (int j = eyeY; j < eyeY + eyeHeight; ++j) {
                            final int rgb = skinImage.getRGB(i, j);
                            eyesImage.setRGB(i, j, rgb);
                        }
                    }
                }
                eyes = LOTRTextures.mc.renderEngine.getDynamicTextureLocation(skin.toString() + "_eyes_" + eyeWidth + "_" + eyeHeight, new DynamicTexture(eyesImage));
            }
            catch (IOException e) {
                LOTRLog.logger.error("Failed to generate eyes skin");
                e.printStackTrace();
                eyes = LOTRTextures.missingTexture;
            }
            LOTRTextures.eyesTextures.put(skin, eyes);
        }
        return eyes;
    }
    
    private static IIcon generateIconEmpty(final TextureMap textureMap) {
        final String iconName = "textures/blocks/LOTR_EMPTY_ICON";
        final int size = 16;
        final BufferedImage iconImage = new BufferedImage(size, size, 2);
        for (int i = 0; i < iconImage.getWidth(); ++i) {
            for (int j = 0; j < iconImage.getHeight(); ++j) {
                int rgb = 0;
                final int alpha = 0;
                rgb |= alpha;
                iconImage.setRGB(i, j, rgb);
            }
        }
        final LOTRBufferedImageIcon icon = new LOTRBufferedImageIcon(iconName, iconImage);
        icon.setIconWidth(iconImage.getWidth());
        icon.setIconHeight(iconImage.getHeight());
        textureMap.setTextureEntry(iconName, (TextureAtlasSprite)icon);
        return (IIcon)icon;
    }
    
    public static int computeAverageFactionPageColor(final ResourceLocation texture, final int u0, final int v0, final int u1, final int v1) {
        if (!LOTRTextures.averagedPageColors.containsKey(texture)) {
            int avgColor = 0;
            try {
                final BufferedImage pageImage = ImageIO.read(LOTRTextures.mc.getResourceManager().getResource(texture).getInputStream());
                long totalR = 0L;
                long totalG = 0L;
                long totalB = 0L;
                long totalA = 0L;
                int count = 0;
                for (int u2 = u0; u2 < u1; ++u2) {
                    for (int v2 = v0; v2 < v1; ++v2) {
                        final int rgb = pageImage.getRGB(u2, v2);
                        final Color color = new Color(rgb);
                        totalR += color.getRed();
                        totalG += color.getGreen();
                        totalB += color.getBlue();
                        totalA += color.getAlpha();
                        ++count;
                    }
                }
                final int avgR = (int)(totalR / count);
                final int avgG = (int)(totalG / count);
                final int avgB = (int)(totalB / count);
                final int avgA = (int)(totalA / count);
                avgColor = new Color(avgR, avgG, avgB, avgA).getRGB();
            }
            catch (IOException e) {
                FMLLog.severe("LOTR: Failed to generate average page colour", new Object[0]);
                e.printStackTrace();
                avgColor = 0;
            }
            LOTRTextures.averagedPageColors.put(texture, avgColor);
            return avgColor;
        }
        return LOTRTextures.averagedPageColors.get(texture);
    }
    
    public static int findContrastingColor(final int text, final int bg) {
        final Color cText = new Color(text);
        final Color cBg = new Color(bg);
        final float[] hsbText = Color.RGBtoHSB(cText.getRed(), cText.getGreen(), cText.getBlue(), null);
        final float[] hsbBg = Color.RGBtoHSB(cBg.getRed(), cBg.getGreen(), cBg.getBlue(), null);
        float bText = hsbText[2];
        final float bBg = hsbBg[2];
        final float limit = 0.4f;
        if (Math.abs(bText - bBg) < limit) {
            if (bBg > 0.66f) {
                bText = bBg - limit;
            }
            else {
                bText = bBg + limit;
            }
        }
        return Color.HSBtoRGB(hsbText[0], hsbText[1], bText);
    }
    
    static {
        LOTRTextures.mc = Minecraft.getMinecraft();
        LOTRTextures.missingTexture = LOTRTextures.mc.getTextureManager().getDynamicTextureLocation("lotr.missingSkin", TextureUtil.missingTexture);
        LOTRTextures.overlayTexture = new ResourceLocation("lotr:map/mapOverlay.png");
        LOTRTextures.mapTerrain = new ResourceLocation("lotr:map/terrain.png");
        osrsTexture = new ResourceLocation("lotr:map/osrs.png");
        LOTRTextures.particleTextures = new ResourceLocation("textures/particle/particles.png");
        LOTRTextures.newWaterParticles = new ResourceLocation("lotr:misc/waterParticles.png");
        LOTRTextures.newWaterU = 0;
        LOTRTextures.newWaterV = 8;
        LOTRTextures.newWaterWidth = 64;
        LOTRTextures.newWaterHeight = 8;
        LOTRTextures.eyesTextures = new HashMap<ResourceLocation, ResourceLocation>();
        LOTRTextures.averagedPageColors = new HashMap<ResourceLocation, Integer>();
    }
}
