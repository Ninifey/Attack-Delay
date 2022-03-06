// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntitySnowTroll;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

public class LOTRRenderSnowTroll extends LOTRRenderTroll
{
    private static LOTRRandomSkins snowTrollSkins;
    
    public LOTRRenderSnowTroll() {
        LOTRRenderSnowTroll.snowTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/snowTroll");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderSnowTroll.snowTrollSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    @Override
    protected void bindTrollOutfitTexture(final EntityLivingBase entity) {
    }
    
    @Override
    protected void renderTrollWeapon(final EntityLivingBase entity, final float f) {
        final LOTREntitySnowTroll troll = (LOTREntitySnowTroll)entity;
        if (!troll.isThrowingSnow()) {
            ((LOTRModelTroll)((RendererLivingEntity)this).mainModel).renderWoodenClubWithSpikes(0.0625f);
        }
    }
}
