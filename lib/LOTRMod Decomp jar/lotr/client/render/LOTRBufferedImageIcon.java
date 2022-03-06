// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render;

import java.util.HashSet;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import java.util.Set;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class LOTRBufferedImageIcon extends TextureAtlasSprite
{
    private final String iconName;
    private final BufferedImage imageRGB;
    private static Set<String> loadedResources;
    
    public LOTRBufferedImageIcon(final String s, final BufferedImage rgb) {
        super(s);
        this.iconName = s;
        this.imageRGB = rgb;
        if (!LOTRBufferedImageIcon.loadedResources.contains(s)) {
            final TextureManager texManager = Minecraft.getMinecraft().getTextureManager();
            final ResourceLocation r = new ResourceLocation(this.iconName);
            final ResourceLocation r2 = new ResourceLocation(r.getResourceDomain(), String.format("%s%s", r.getResourcePath(), ".png"));
            texManager.func_147645_c(r2);
            texManager.loadTexture(r2, (ITextureObject)new DynamicTexture(this.imageRGB));
            LOTRBufferedImageIcon.loadedResources.add(s);
        }
    }
    
    public boolean load(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        final BufferedImage[] imageArray = new BufferedImage[1 + Minecraft.getMinecraft().gameSettings.mipmapLevels];
        imageArray[0] = this.imageRGB;
        this.func_147964_a(imageArray, (AnimationMetadataSection)null, Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0f);
        return false;
    }
    
    public boolean hasCustomLoader(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return true;
    }
    
    static {
        LOTRBufferedImageIcon.loadedResources = new HashSet<String>();
    }
}
