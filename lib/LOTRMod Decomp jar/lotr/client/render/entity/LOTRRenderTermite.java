// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelTermite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderTermite extends RenderLiving
{
    private static ResourceLocation texture;
    
    public LOTRRenderTermite() {
        super((ModelBase)new LOTRModelTermite(), 0.2f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderTermite.texture;
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final float scale = 0.25f;
        GL11.glScalef(scale, scale, scale);
    }
    
    static {
        LOTRRenderTermite.texture = new ResourceLocation("lotr:mob/termite.png");
    }
}
