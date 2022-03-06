// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.item.LOTREntityWargskinRug;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelWargskinRug;

public class LOTRRenderWargskinRug extends LOTRRenderRugBase
{
    public LOTRRenderWargskinRug() {
        super(new LOTRModelWargskinRug());
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityWargskinRug rug = (LOTREntityWargskinRug)entity;
        return LOTRRenderWarg.getWargSkin(rug.getRugType());
    }
}
