// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityDikDik;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelDikDik;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderDikDik extends RenderLiving
{
    private static LOTRRandomSkins skins;
    
    public LOTRRenderDikDik() {
        super((ModelBase)new LOTRModelDikDik(), 0.8f);
        LOTRRenderDikDik.skins = LOTRRandomSkins.loadSkinsList("lotr:mob/dikdik");
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityDikDik dikdik = (LOTREntityDikDik)entity;
        return LOTRRenderDikDik.skins.getRandomSkin(dikdik);
    }
}
