// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import lotr.client.model.LOTRModelTroll;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityOlogHai;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

public class LOTRRenderOlogHai extends LOTRRenderTroll
{
    private static LOTRRandomSkins ologSkins;
    private static LOTRRandomSkins ologArmorSkins;
    
    public LOTRRenderOlogHai() {
        LOTRRenderOlogHai.ologSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/ologHai");
        LOTRRenderOlogHai.ologArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/ologHai_armor");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderOlogHai.ologSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    @Override
    protected void renderTrollWeapon(final EntityLivingBase entity, final float f) {
        ((LOTRModelTroll)((RendererLivingEntity)this).mainModel).renderWarhammer(0.0625f);
    }
    
    @Override
    protected void bindTrollOutfitTexture(final EntityLivingBase entity) {
        this.bindTexture(LOTRRenderOlogHai.ologArmorSkins.getRandomSkin((LOTRRandomSkinEntity)entity));
    }
}
