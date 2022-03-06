// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderBiped;

public class LOTRRenderSkeleton extends RenderBiped
{
    private static ResourceLocation skin;
    
    public LOTRRenderSkeleton() {
        super((ModelBiped)new LOTRModelSkeleton(), 0.5f);
    }
    
    protected void func_82421_b() {
        super.field_82423_g = (ModelBiped)new LOTRModelSkeleton(1.0f);
        super.field_82425_h = (ModelBiped)new LOTRModelSkeleton(0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderSkeleton.skin;
    }
    
    static {
        LOTRRenderSkeleton.skin = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    }
}
