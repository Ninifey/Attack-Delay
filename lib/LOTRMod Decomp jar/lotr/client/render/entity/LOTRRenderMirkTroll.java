// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import lotr.client.model.LOTRModelTroll;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityMirkTroll;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

public class LOTRRenderMirkTroll extends LOTRRenderTroll
{
    private static LOTRRandomSkins mirkSkins;
    private static LOTRRandomSkins mirkArmorSkins;
    
    public LOTRRenderMirkTroll() {
        LOTRRenderMirkTroll.mirkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll");
        LOTRRenderMirkTroll.mirkArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll_armor");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderMirkTroll.mirkSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    @Override
    protected void renderTrollWeapon(final EntityLivingBase entity, final float f) {
        ((LOTRModelTroll)((RendererLivingEntity)this).mainModel).renderBattleaxe(0.0625f);
    }
    
    @Override
    protected void bindTrollOutfitTexture(final EntityLivingBase entity) {
        this.bindTexture(LOTRRenderMirkTroll.mirkArmorSkins.getRandomSkin((LOTRRandomSkinEntity)entity));
    }
}
