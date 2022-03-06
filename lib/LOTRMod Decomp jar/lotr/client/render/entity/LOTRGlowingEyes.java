// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.Minecraft;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

public class LOTRGlowingEyes
{
    public static void renderGlowingEyes(final Entity entity, final ResourceLocation eyesTexture, final Model model, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(1, 1);
        final float lastX = OpenGlHelper.lastBrightnessX;
        final float lastY = OpenGlHelper.lastBrightnessY;
        final int light = LOTRClientProxy.TESSELLATOR_MAX_BRIGHTNESS;
        final int lx = light % 65536;
        final int ly = light / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx / 1.0f, ly / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(eyesTexture);
        model.renderGlowingEyes(entity, f, f1, f2, f3, f4, f5);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public interface Model
    {
        void renderGlowingEyes(final Entity p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6);
    }
}
