// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.client.resources.IReloadableResourceManager;
import java.awt.Graphics2D;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.DynamicTexture;
import java.awt.image.BufferedImageOp;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.MathHelper;
import net.minecraft.init.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import lotr.common.entity.npc.LOTREntityTree;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.client.resources.IResourceManager;
import java.util.HashMap;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class LOTRHuornTextures implements IResourceManagerReloadListener
{
    public static LOTRHuornTextures INSTANCE;
    private RenderManager renderManager;
    private HashMap woodTextures;
    private HashMap leafTexturesFast;
    private HashMap leafTexturesFancy;
    
    public LOTRHuornTextures() {
        this.renderManager = RenderManager.instance;
        this.woodTextures = new HashMap();
        this.leafTexturesFast = new HashMap();
        this.leafTexturesFancy = new HashMap();
    }
    
    public void onResourceManagerReload(final IResourceManager resourcemanager) {
        this.woodTextures.clear();
        this.leafTexturesFast.clear();
        this.leafTexturesFancy.clear();
    }
    
    public void bindWoodTexture(final LOTREntityHuornBase entity) {
        final int treeType = entity.getTreeType();
        final Block block = LOTREntityTree.WOOD_BLOCKS[treeType];
        final int meta = LOTREntityTree.WOOD_META[treeType];
        ResourceLocation texture = this.woodTextures.get(treeType);
        if (texture == null) {
            texture = this.getDynamicHuornTexture(block, meta);
            this.woodTextures.put(treeType, texture);
        }
        this.renderManager.renderEngine.bindTexture(texture);
    }
    
    public void bindLeafTexture(final LOTREntityHuornBase entity) {
        final int treeType = entity.getTreeType();
        final Block block = LOTREntityTree.LEAF_BLOCKS[treeType];
        final int meta = LOTREntityTree.LEAF_META[treeType];
        ResourceLocation texture = this.leafTexturesFast.get(treeType);
        if (Minecraft.isFancyGraphicsEnabled()) {
            texture = this.leafTexturesFancy.get(treeType);
        }
        if (texture == null) {
            texture = this.getDynamicHuornTexture(block, meta);
            if (Minecraft.isFancyGraphicsEnabled()) {
                this.leafTexturesFancy.put(treeType, texture);
            }
            else {
                this.leafTexturesFast.put(treeType, texture);
            }
        }
        this.renderManager.renderEngine.bindTexture(texture);
        int color = block.getRenderColor(meta);
        if (block == Blocks.leaves && meta == 0) {
            final int i = MathHelper.floor_double(((Entity)entity).posX);
            final int j = MathHelper.floor_double(((Entity)entity).boundingBox.minY);
            final int k = MathHelper.floor_double(((Entity)entity).posZ);
            color = ((Entity)entity).worldObj.getBiomeGenForCoords(i, k).getBiomeFoliageColor(i, j, k);
        }
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, 1.0f);
    }
    
    private ResourceLocation getDynamicHuornTexture(final Block block, final int meta) {
        try {
            final boolean aF = Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1;
            final TextureMap terrainAtlas = (TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture);
            final BufferedImage[] icons = new BufferedImage[6];
            int width = block.getIcon(0, meta).getIconWidth();
            if (aF) {
                width -= 16;
            }
            for (int i = 0; i < 6; ++i) {
                final IIcon icon = block.getIcon(i, meta);
                int iconWidth = icon.getIconWidth();
                int iconHeight = icon.getIconHeight();
                if (aF) {
                    iconWidth -= 16;
                    iconHeight -= 16;
                }
                if (iconWidth != width || iconHeight != width) {
                    throw new RuntimeException("Error registering Huorn textures: all icons for block " + block.getUnlocalizedName() + " must have the same texture dimensions");
                }
                final ResourceLocation iconPath = new ResourceLocation(icon.getIconName());
                final ResourceLocation r = new ResourceLocation(iconPath.getResourceDomain(), "textures/blocks/" + iconPath.getResourcePath() + ".png");
                final BufferedImage iconImage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(r).getInputStream());
                icons[i] = iconImage.getSubimage(0, 0, width, width);
            }
            final BufferedImage image = new BufferedImage(width * 4, width * 2, 2);
            final Graphics2D g2d = image.createGraphics();
            g2d.drawImage(icons[1], null, width, 0);
            g2d.drawImage(icons[0], null, width * 2, 0);
            g2d.drawImage(icons[4], null, 0, width);
            g2d.drawImage(icons[2], null, width, width);
            g2d.drawImage(icons[5], null, width * 2, width);
            g2d.drawImage(icons[3], null, width * 3, width);
            g2d.dispose();
            return this.renderManager.renderEngine.getDynamicTextureLocation("lotr:huorn", new DynamicTexture(image));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    static {
        LOTRHuornTextures.INSTANCE = new LOTRHuornTextures();
        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener((IResourceManagerReloadListener)LOTRHuornTextures.INSTANCE);
    }
}
