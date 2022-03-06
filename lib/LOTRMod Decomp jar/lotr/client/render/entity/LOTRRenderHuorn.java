// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelHuorn;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderHuorn extends RenderLiving
{
    private static LOTRRandomSkins faceSkins;
    
    public LOTRRenderHuorn() {
        super((ModelBase)new LOTRModelHuorn(), 0.0f);
        LOTRRenderHuorn.faceSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/huorn/face");
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
        return LOTRRenderHuorn.faceSkins.getRandomSkin(huorn);
    }
    
    public void doRender(final EntityLiving entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
        if (huorn.ignoringFrustumForRender) {
            huorn.ignoringFrustumForRender = false;
            ((Entity)huorn).ignoreFrustumCheck = false;
        }
        super.doRender(entity, d, d1, d2, f, f1);
        if (Minecraft.isGuiEnabled() && huorn.hiredNPCInfo.getHiringPlayer() == ((Render)this).renderManager.livingPlayer) {
            LOTRNPCRendering.renderHiredIcon((EntityLivingBase)entity, d, d1 + 3.5, d2);
            LOTRNPCRendering.renderNPCHealthBar((EntityLivingBase)entity, d, d1 + 3.5, d2);
        }
    }
    
    protected void renderLivingAt(final EntityLivingBase entity, double d, double d1, double d2) {
        final LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
        if (!huorn.isHuornActive()) {
            final int i = MathHelper.floor_double(((Entity)huorn).posX);
            final int j = MathHelper.floor_double(((Entity)huorn).posY);
            final int k = MathHelper.floor_double(((Entity)huorn).posZ);
            final double n = i + 0.5;
            final RenderManager renderManager = ((Render)this).renderManager;
            d = n - RenderManager.renderPosX;
            final double n2 = j;
            final RenderManager renderManager2 = ((Render)this).renderManager;
            d1 = n2 - RenderManager.renderPosY;
            final double n3 = k + 0.5;
            final RenderManager renderManager3 = ((Render)this).renderManager;
            d2 = n3 - RenderManager.renderPosZ;
        }
        d1 -= 0.0078125;
        super.renderLivingAt(entity, d, d1, d2);
        ((EntityLivingBase)huorn).hurtTime = 0;
    }
    
    protected void rotateCorpse(final EntityLivingBase entity, final float f, float f1, final float f2) {
        final LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
        if (!huorn.isHuornActive()) {
            f1 = 0.0f;
        }
        super.rotateCorpse(entity, f, f1, f2);
    }
    
    protected float handleRotationFloat(final EntityLivingBase entity, final float f) {
        return f;
    }
}
