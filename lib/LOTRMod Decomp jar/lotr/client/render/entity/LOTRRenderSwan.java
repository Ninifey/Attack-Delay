// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelSwan;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderSwan extends RenderLiving
{
    public static ResourceLocation swanSkin;
    
    public LOTRRenderSwan() {
        super((ModelBase)new LOTRModelSwan(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderSwan.swanSkin;
    }
    
    static {
        LOTRRenderSwan.swanSkin = new ResourceLocation("lotr:mob/swan.png");
    }
}
