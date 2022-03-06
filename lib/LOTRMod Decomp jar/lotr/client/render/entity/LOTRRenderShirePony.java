// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import lotr.common.entity.animal.LOTREntityShirePony;
import net.minecraft.entity.EntityLivingBase;

public class LOTRRenderShirePony extends LOTRRenderHorse
{
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final float scale = LOTREntityShirePony.PONY_SCALE;
        GL11.glScalef(scale, scale, scale);
    }
}
