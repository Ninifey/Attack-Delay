// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import lotr.client.model.LOTRModelTroll;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

public class LOTRRenderUtumnoTroll extends LOTRRenderTroll
{
    private static LOTRRandomSkins utumnoTrollSkins;
    
    public LOTRRenderUtumnoTroll() {
        LOTRRenderUtumnoTroll.utumnoTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/utumno");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderUtumnoTroll.utumnoTrollSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    @Override
    protected void renderTrollWeapon(final EntityLivingBase entity, final float f) {
        ((LOTRModelTroll)((RendererLivingEntity)this).mainModel).renderWoodenClubWithSpikes(0.0625f);
    }
}
