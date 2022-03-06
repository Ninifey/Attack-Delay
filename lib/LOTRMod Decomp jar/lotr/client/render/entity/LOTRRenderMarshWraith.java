// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityMarshWraith;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelMarshWraith;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderMarshWraith extends RenderLiving
{
    private static ResourceLocation skin;
    
    public LOTRRenderMarshWraith() {
        super((ModelBase)new LOTRModelMarshWraith(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderMarshWraith.skin;
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        final float f2 = 0.9375f;
        GL11.glScalef(f2, f2, f2);
        final LOTREntityMarshWraith wraith = (LOTREntityMarshWraith)entity;
        if (wraith.getSpawnFadeTime() < 30) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, wraith.getSpawnFadeTime() / 30.0f);
        }
        else if (wraith.getDeathFadeTime() > 0) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, wraith.getDeathFadeTime() / 30.0f);
        }
    }
    
    protected float getDeathMaxRotation(final EntityLivingBase entity) {
        return 0.0f;
    }
    
    static {
        LOTRRenderMarshWraith.skin = new ResourceLocation("lotr:mob/wraith/marshWraith.png");
    }
}
