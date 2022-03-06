// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.animal.LOTREntityCrocodile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelCrocodile;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderCrocodile extends RenderLiving
{
    private static ResourceLocation texture;
    
    public LOTRRenderCrocodile() {
        super((ModelBase)new LOTRModelCrocodile(), 0.75f);
    }
    
    public ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderCrocodile.texture;
    }
    
    public float handleRotationFloat(final EntityLivingBase entity, final float f) {
        float snapTime = (float)((LOTREntityCrocodile)entity).getSnapTime();
        if (snapTime > 0.0f) {
            snapTime -= f;
        }
        return snapTime / 20.0f;
    }
    
    static {
        LOTRRenderCrocodile.texture = new ResourceLocation("lotr:mob/crocodile.png");
    }
}
