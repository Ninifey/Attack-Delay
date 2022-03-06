// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderArrow;

public class LOTRRenderArrowPoisoned extends RenderArrow
{
    private static final ResourceLocation arrowPoisonTexture;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderArrowPoisoned.arrowPoisonTexture;
    }
    
    static {
        arrowPoisonTexture = new ResourceLocation("lotr:item/arrowPoisoned.png");
    }
}
