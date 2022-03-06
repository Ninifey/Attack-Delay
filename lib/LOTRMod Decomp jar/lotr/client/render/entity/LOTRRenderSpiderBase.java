// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.ResourceLocation;
import lotr.client.LOTRTextures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntitySpiderBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelSpider;
import net.minecraft.client.renderer.entity.RenderLiving;

public abstract class LOTRRenderSpiderBase extends RenderLiving
{
    private LOTRModelSpider eyesModel;
    
    public LOTRRenderSpiderBase() {
        super((ModelBase)new LOTRModelSpider(0.5f), 1.0f);
        this.eyesModel = new LOTRModelSpider(0.55f);
        this.setRenderPassModel((ModelBase)new LOTRModelSpider(0.5f));
    }
    
    public void doRender(final EntityLiving entity, final double d, final double d1, final double d2, final float f, final float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        if (Minecraft.isGuiEnabled() && ((LOTREntitySpiderBase)entity).hiredNPCInfo.getHiringPlayer() == ((Render)this).renderManager.livingPlayer) {
            LOTRNPCRendering.renderHiredIcon((EntityLivingBase)entity, d, d1 + 0.5, d2);
            LOTRNPCRendering.renderNPCHealthBar((EntityLivingBase)entity, d, d1 + 0.5, d2);
        }
    }
    
    protected void renderModel(final EntityLivingBase entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.renderModel(entity, f, f1, f2, f3, f4, f5);
        final ResourceLocation eyes1 = LOTRTextures.getEyesTexture(this.getEntityTexture((Entity)entity), new int[][] { { 39, 10 }, { 42, 11 }, { 44, 11 }, { 47, 10 } }, 2, 2);
        final ResourceLocation eyes2 = LOTRTextures.getEyesTexture(this.getEntityTexture((Entity)entity), new int[][] { { 41, 8 }, { 42, 9 }, { 45, 9 }, { 46, 8 } }, 1, 1);
        LOTRGlowingEyes.renderGlowingEyes((Entity)entity, eyes1, this.eyesModel, f, f1, f2, f3, f4, f5);
        LOTRGlowingEyes.renderGlowingEyes((Entity)entity, eyes2, this.eyesModel, f, f1, f2, f3, f4, f5);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final float scale = ((LOTREntitySpiderBase)entity).getSpiderScaleAmount();
        GL11.glScalef(scale, scale, scale);
    }
    
    protected float getDeathMaxRotation(final EntityLivingBase entity) {
        return 180.0f;
    }
}
