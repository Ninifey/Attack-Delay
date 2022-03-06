// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityAurochs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelAurochs;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderAurochs extends RenderLiving
{
    private static LOTRRandomSkins aurochsSkins;
    
    public LOTRRenderAurochs() {
        super((ModelBase)new LOTRModelAurochs(), 0.5f);
        LOTRRenderAurochs.aurochsSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/aurochs");
    }
    
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityAurochs aurochs = (LOTREntityAurochs)entity;
        final ResourceLocation skin = LOTRRenderAurochs.aurochsSkins.getRandomSkin(aurochs);
        return skin;
    }
}
