// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderUtumnoIceSpider extends LOTRRenderSpiderBase
{
    private static ResourceLocation spiderSkin;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderUtumnoIceSpider.spiderSkin;
    }
    
    static {
        LOTRRenderUtumnoIceSpider.spiderSkin = new ResourceLocation("lotr:mob/spider/spider_utumnoIce.png");
    }
}
