// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelDeer;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderDeer extends RenderLiving
{
    private static LOTRRandomSkins deerSkins;
    
    public LOTRRenderDeer() {
        super((ModelBase)new LOTRModelDeer(), 0.5f);
        LOTRRenderDeer.deerSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/deer");
    }
    
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityDeer deer = (LOTREntityDeer)entity;
        final ResourceLocation deerSkin = LOTRRenderDeer.deerSkins.getRandomSkin(deer);
        return deerSkin;
    }
}
