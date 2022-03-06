// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.Iterator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import java.util.Collection;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import lotr.client.LOTRTextures;
import lotr.common.entity.LOTRRandomSkinEntity;
import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import java.util.Random;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class LOTRRandomSkins implements IResourceManagerReloadListener
{
    private static Random rand;
    private static Minecraft mc;
    private static Map<String, LOTRRandomSkins> allRandomSkins;
    protected String skinPath;
    protected List<ResourceLocation> skins;
    
    public static LOTRRandomSkins loadSkinsList(final String path) {
        LOTRRandomSkins skins = LOTRRandomSkins.allRandomSkins.get(path);
        if (skins == null) {
            skins = new LOTRRandomSkins(path, true, new Object[0]);
            LOTRRandomSkins.allRandomSkins.put(path, skins);
        }
        return skins;
    }
    
    private LOTRRandomSkins(final String path, final boolean register, final Object... args) {
        this.skinPath = path;
        this.handleExtraArgs(args);
        if (register) {
            ((IReloadableResourceManager)LOTRRandomSkins.mc.getResourceManager()).registerReloadListener((IResourceManagerReloadListener)this);
        }
        else {
            this.loadAllRandomSkins();
        }
    }
    
    protected void handleExtraArgs(final Object... args) {
    }
    
    protected void loadAllRandomSkins() {
        this.skins = new ArrayList<ResourceLocation>();
        int skinCount = 0;
        int skips = 0;
        final int maxSkips = 10;
        boolean foundAfterSkip = false;
        while (true) {
            final ResourceLocation skin = new ResourceLocation(this.skinPath + "/" + skinCount + ".png");
            boolean noFile = false;
            try {
                if (LOTRRandomSkins.mc.getResourceManager().getResource(skin) == null) {
                    noFile = true;
                }
            }
            catch (Exception e) {
                noFile = true;
            }
            if (noFile) {
                if (++skips >= maxSkips) {
                    break;
                }
                ++skinCount;
            }
            else {
                this.skins.add(skin);
                ++skinCount;
                if (skips <= 0) {
                    continue;
                }
                foundAfterSkip = true;
            }
        }
        if (this.skins.isEmpty()) {
            FMLLog.warning("LOTR: No random skins for %s", new Object[] { this.skinPath });
        }
        if (foundAfterSkip) {
            FMLLog.warning("LOTR: Random skins %s skipped a number. This is not good - please number your skins from 0 and upwards, with no gaps!", new Object[] { this.skinPath });
        }
    }
    
    public ResourceLocation getRandomSkin(final LOTRRandomSkinEntity rsEntity) {
        if (this.skins == null || this.skins.isEmpty()) {
            return LOTRTextures.missingTexture;
        }
        final Entity entity = (Entity)rsEntity;
        long l = entity.getUniqueID().getLeastSignificantBits();
        final long hash = this.skinPath.hashCode();
        l = (l * 39603773L ^ l * 6583690632L ^ hash);
        l = l * hash * 2906920L + l * 65936063L;
        LOTRRandomSkins.rand.setSeed(l);
        final int i = LOTRRandomSkins.rand.nextInt(this.skins.size());
        return this.skins.get(i);
    }
    
    public ResourceLocation getRandomSkin() {
        final int i = LOTRRandomSkins.rand.nextInt(this.skins.size());
        return this.skins.get(i);
    }
    
    public static int nextInt(final LOTRRandomSkinEntity rsEntity, final int n) {
        final Entity entity = (Entity)rsEntity;
        long l = entity.getUniqueID().getLeastSignificantBits();
        l = l * 29506206L * (l ^ 0x6429C58L) + 25859L;
        l = l * l * 426430295004L + 25925025L * l;
        LOTRRandomSkins.rand.setSeed(l);
        return LOTRRandomSkins.rand.nextInt(n);
    }
    
    public List<ResourceLocation> getAllSkins() {
        return this.skins;
    }
    
    public void onResourceManagerReload(final IResourceManager resourcemanager) {
        this.loadAllRandomSkins();
    }
    
    public static LOTRRandomSkins getCombinatorialSkins(final String path, final String... layers) {
        String combinedPath = path;
        for (final String s : layers) {
            combinedPath = combinedPath + "_" + s;
        }
        LOTRRandomSkins skins = LOTRRandomSkins.allRandomSkins.get(combinedPath);
        if (skins == null) {
            skins = new LOTRRandomSkinsCombinatorial(path, layers);
            LOTRRandomSkins.allRandomSkins.put(combinedPath, skins);
        }
        return skins;
    }
    
    static {
        LOTRRandomSkins.rand = new Random();
        LOTRRandomSkins.mc = Minecraft.getMinecraft();
        LOTRRandomSkins.allRandomSkins = new HashMap<String, LOTRRandomSkins>();
    }
    
    private static class LOTRRandomSkinsCombinatorial extends LOTRRandomSkins
    {
        private String[] skinLayers;
        
        private LOTRRandomSkinsCombinatorial(final String path, final String... layers) {
            super(path, true, layers, null);
        }
        
        @Override
        protected void handleExtraArgs(final Object... args) {
            this.skinLayers = (String[])args;
        }
        
        @Override
        protected void loadAllRandomSkins() {
            super.skins = new ArrayList<ResourceLocation>();
            final List<BufferedImage> layeredImages = new ArrayList<BufferedImage>();
            final List<BufferedImage> tempLayered = new ArrayList<BufferedImage>();
        Label_0425:
            for (final String layer : this.skinLayers) {
                final String layerPath = super.skinPath + "/" + layer;
                final LOTRRandomSkins layerSkins = new LOTRRandomSkins(layerPath, false, new Object[0], null);
                tempLayered.clear();
                for (final ResourceLocation overlay : layerSkins.getAllSkins()) {
                    try {
                        final BufferedImage overlayImage = ImageIO.read(LOTRRandomSkins.mc.getResourceManager().getResource(overlay).getInputStream());
                        if (layeredImages.isEmpty()) {
                            tempLayered.add(overlayImage);
                        }
                        else {
                            for (final BufferedImage baseImage : layeredImages) {
                                final int skinWidth = baseImage.getWidth();
                                final int skinHeight = baseImage.getHeight();
                                final BufferedImage newImage = new BufferedImage(skinWidth, skinHeight, 2);
                                for (int i = 0; i < skinWidth; ++i) {
                                    for (int j = 0; j < skinHeight; ++j) {
                                        final int baseRGB = baseImage.getRGB(i, j);
                                        final int overlayRGB = overlayImage.getRGB(i, j);
                                        final int opaqueTest = -16777216;
                                        if ((overlayRGB & opaqueTest) == opaqueTest) {
                                            newImage.setRGB(i, j, overlayRGB);
                                        }
                                        else {
                                            newImage.setRGB(i, j, baseRGB);
                                        }
                                    }
                                }
                                tempLayered.add(newImage);
                            }
                        }
                    }
                    catch (IOException e) {
                        FMLLog.severe("LOTR: Error combining skins " + super.skinPath + " on layer " + layer + "!", new Object[0]);
                        e.printStackTrace();
                        break Label_0425;
                    }
                }
                layeredImages.clear();
                layeredImages.addAll(tempLayered);
            }
            int skinCount = 0;
            for (final BufferedImage image : layeredImages) {
                final ResourceLocation skin = LOTRRandomSkins.mc.renderEngine.getDynamicTextureLocation(super.skinPath + "_layered/" + skinCount + ".png", new DynamicTexture(image));
                super.skins.add(skin);
                ++skinCount;
            }
            FMLLog.info("LOTR: Assembled combinatorial skins for " + super.skinPath + ": " + super.skins.size() + " skins", new Object[0]);
        }
    }
}
