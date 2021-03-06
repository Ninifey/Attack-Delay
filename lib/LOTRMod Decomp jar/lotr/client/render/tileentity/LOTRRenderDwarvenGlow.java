// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;

public class LOTRRenderDwarvenGlow
{
    public static float setupGlow(final float brightness) {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        final float alphaFunc = GL11.glGetFloat(3010);
        GL11.glAlphaFunc(516, 0.02f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, brightness);
        return alphaFunc;
    }
    
    public static void endGlow(final float alphaFunc) {
        GL11.glAlphaFunc(516, alphaFunc);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
