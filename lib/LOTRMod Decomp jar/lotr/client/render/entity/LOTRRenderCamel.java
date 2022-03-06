// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import java.io.InputStream;
import java.io.IOException;
import net.minecraft.client.renderer.texture.DynamicTexture;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import java.util.Map;
import lotr.client.model.LOTRModelCamel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderCamel extends RenderLiving
{
    private static ResourceLocation camelSkin;
    private static ResourceLocation saddleTexture;
    private static ResourceLocation carpetBase;
    private static ResourceLocation carpetOverlay;
    private LOTRModelCamel modelSaddle;
    private LOTRModelCamel modelCarpet;
    private static Map<String, ResourceLocation> coloredCarpetTextures;
    
    public LOTRRenderCamel() {
        super((ModelBase)new LOTRModelCamel(), 0.5f);
        this.modelSaddle = new LOTRModelCamel(0.5f);
        this.modelCarpet = new LOTRModelCamel(0.55f);
        this.setRenderPassModel((ModelBase)this.modelSaddle);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityCamel camel = (LOTREntityCamel)entity;
        return LOTRRenderHorse.getLayeredMountTexture(camel, LOTRRenderCamel.camelSkin);
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        final LOTREntityCamel camel = (LOTREntityCamel)entity;
        if (pass == 0 && camel.isMountSaddled()) {
            this.setRenderPassModel((ModelBase)this.modelSaddle);
            this.bindTexture(LOTRRenderCamel.saddleTexture);
            return 1;
        }
        if (pass == 1 && camel.isCamelWearingCarpet()) {
            this.setRenderPassModel((ModelBase)this.modelCarpet);
            final int color = camel.getCamelCarpetColor();
            final ResourceLocation carpet = getColoredCarpetTexture(color);
            this.bindTexture(carpet);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        GL11.glScalef(1.25f, 1.25f, 1.25f);
    }
    
    public static ResourceLocation getColoredCarpetTexture(final int carpetRGB) {
        final String path = "lotr:camel_carpet_0x" + Integer.toHexString(carpetRGB);
        ResourceLocation res = LOTRRenderCamel.coloredCarpetTextures.get(path);
        if (res == null) {
            try {
                final Minecraft mc = Minecraft.getMinecraft();
                final InputStream isBase = mc.getResourceManager().getResource(LOTRRenderCamel.carpetBase).getInputStream();
                final BufferedImage imgBase = ImageIO.read(isBase);
                final InputStream isOverlay = mc.getResourceManager().getResource(LOTRRenderCamel.carpetOverlay).getInputStream();
                final BufferedImage imgOverlay = ImageIO.read(isOverlay);
                final BufferedImage imgDyed = new BufferedImage(imgBase.getWidth(), imgBase.getHeight(), 2);
                final int carpetR = carpetRGB >> 16 & 0xFF;
                final int carpetG = carpetRGB >> 8 & 0xFF;
                final int carpetB = carpetRGB & 0xFF;
                for (int i = 0; i < imgDyed.getWidth(); ++i) {
                    for (int j = 0; j < imgDyed.getHeight(); ++j) {
                        final int argbOverlay = imgOverlay.getRGB(i, j);
                        final int aOverlay = argbOverlay >> 24 & 0xFF;
                        if (aOverlay > 0) {
                            imgDyed.setRGB(i, j, argbOverlay);
                        }
                        else {
                            final int argb = imgBase.getRGB(i, j);
                            final int a = argb >> 24 & 0xFF;
                            int r = argb >> 16 & 0xFF;
                            int g = argb >> 8 & 0xFF;
                            int b = argb & 0xFF;
                            r = r * carpetR / 255;
                            g = g * carpetG / 255;
                            b = b * carpetB / 255;
                            final int dyed = a << 24 | r << 16 | g << 8 | b;
                            imgDyed.setRGB(i, j, dyed);
                        }
                    }
                }
                res = mc.renderEngine.getDynamicTextureLocation(path, new DynamicTexture(imgDyed));
            }
            catch (IOException e) {
                System.out.println("LOTR: Error generating coloured camel carpet texture");
                e.printStackTrace();
                res = LOTRRenderCamel.carpetBase;
            }
            LOTRRenderCamel.coloredCarpetTextures.put(path, res);
        }
        return res;
    }
    
    static {
        LOTRRenderCamel.camelSkin = new ResourceLocation("lotr:mob/camel/camel.png");
        LOTRRenderCamel.saddleTexture = new ResourceLocation("lotr:mob/camel/saddle.png");
        LOTRRenderCamel.carpetBase = new ResourceLocation("lotr:mob/camel/carpet_base.png");
        LOTRRenderCamel.carpetOverlay = new ResourceLocation("lotr:mob/camel/carpet_overlay.png");
        LOTRRenderCamel.coloredCarpetTextures = new HashMap<String, ResourceLocation>();
    }
}
