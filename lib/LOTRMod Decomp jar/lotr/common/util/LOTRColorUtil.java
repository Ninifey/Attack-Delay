// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.util;

import net.minecraft.util.MathHelper;
import java.awt.Color;

public class LOTRColorUtil
{
    public static float[] lerpColors(final int color0, final int color1, final float f) {
        final float[] rgb0 = new Color(color0).getColorComponents(null);
        return lerpColors(rgb0, color1, f);
    }
    
    public static float[] lerpColors(final float[] rgb0, final int color1, final float f) {
        final float[] rgb = new Color(color1).getColorComponents(null);
        final float r0 = rgb0[0];
        final float g0 = rgb0[1];
        final float b0 = rgb0[2];
        final float r2 = rgb[0];
        final float g2 = rgb[1];
        final float b2 = rgb[2];
        float r3 = r0 + (r2 - r0) * f;
        float g3 = g0 + (g2 - g0) * f;
        float b3 = b0 + (b2 - b0) * f;
        r3 = MathHelper.clamp_float(r3, 0.0f, 1.0f);
        g3 = MathHelper.clamp_float(g3, 0.0f, 1.0f);
        b3 = MathHelper.clamp_float(b3, 0.0f, 1.0f);
        return new float[] { r3, g3, b3 };
    }
    
    public static int lerpColors_I(final int color0, final int color1, final float f) {
        final float[] rgb0 = new Color(color0).getColorComponents(null);
        return lerpColors_I(rgb0, color1, f);
    }
    
    public static int lerpColors_I(final float[] rgb0, final int color1, final float f) {
        final float[] rgb = lerpColors(rgb0, color1, f);
        return new Color(rgb[0], rgb[1], rgb[2]).getRGB();
    }
}
