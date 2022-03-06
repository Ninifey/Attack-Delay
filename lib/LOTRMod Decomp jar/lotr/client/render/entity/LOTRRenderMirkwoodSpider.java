// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMirkwoodSpider extends LOTRRenderSpiderBase
{
    private static ResourceLocation[] spiderSkins;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityMirkwoodSpider spider = (LOTREntityMirkwoodSpider)entity;
        return LOTRRenderMirkwoodSpider.spiderSkins[spider.getSpiderType()];
    }
    
    static {
        LOTRRenderMirkwoodSpider.spiderSkins = new ResourceLocation[] { new ResourceLocation("lotr:mob/spider/spider_mirkwood.png"), new ResourceLocation("lotr:mob/spider/spider_mirkwoodSlowness.png"), new ResourceLocation("lotr:mob/spider/spider_mirkwoodPoison.png") };
    }
}
