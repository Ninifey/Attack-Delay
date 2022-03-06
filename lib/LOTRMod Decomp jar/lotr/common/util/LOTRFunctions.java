// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.util;

import net.minecraft.util.MathHelper;

public class LOTRFunctions
{
    public static float triangleWave(final float t, final float min, final float max, final float period) {
        return min + (max - min) * (Math.abs(t % period / period - 0.5f) * 2.0f);
    }
    
    public static float normalisedSin(final float t) {
        return (MathHelper.sin(t) + 1.0f) / 2.0f;
    }
}
