// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelGemsbok;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderGemsbok extends RenderLiving
{
    private static ResourceLocation texture;
    
    public LOTRRenderGemsbok() {
        super((ModelBase)new LOTRModelGemsbok(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderGemsbok.texture;
    }
    
    static {
        LOTRRenderGemsbok.texture = new ResourceLocation("lotr:mob/gemsbok.png");
    }
}
