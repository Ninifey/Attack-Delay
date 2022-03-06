// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.item.LOTRItemLionRug;
import lotr.common.entity.item.LOTREntityLionRug;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelLionRug;

public class LOTRRenderLionRug extends LOTRRenderRugBase
{
    public LOTRRenderLionRug() {
        super(new LOTRModelLionRug());
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityLionRug rug = (LOTREntityLionRug)entity;
        final LOTRItemLionRug.LionRugType type = rug.getRugType();
        if (type == LOTRItemLionRug.LionRugType.LION) {
            return LOTRRenderLion.textureLion;
        }
        if (type == LOTRItemLionRug.LionRugType.LIONESS) {
            return LOTRRenderLion.textureLioness;
        }
        return new ResourceLocation("");
    }
}
