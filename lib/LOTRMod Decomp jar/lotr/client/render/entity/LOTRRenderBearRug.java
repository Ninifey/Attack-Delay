// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.item.LOTREntityBearRug;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelBearRug;

public class LOTRRenderBearRug extends LOTRRenderRugBase
{
    public LOTRRenderBearRug() {
        super(new LOTRModelBearRug());
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityBearRug rug = (LOTREntityBearRug)entity;
        return LOTRRenderBear.getBearSkin(rug.getRugType());
    }
    
    @Override
    protected void preRenderCallback() {
        LOTRRenderBear.scaleBearModel();
    }
}
