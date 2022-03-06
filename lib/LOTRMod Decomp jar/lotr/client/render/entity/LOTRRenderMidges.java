// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.animal.LOTREntityMidges;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelMidge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderMidges extends RenderLiving
{
    private static ResourceLocation midgeTexture;
    private float renderTick;
    
    public LOTRRenderMidges() {
        super((ModelBase)new LOTRModelMidge(), 0.0f);
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        super.doRender(entity, d, d1, d2, f, this.renderTick = f1);
    }
    
    protected void renderModel(final EntityLivingBase entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.bindEntityTexture((Entity)entity);
        ((RendererLivingEntity)this).mainModel.setRotationAngles(f, f1, f2, f3, f4, f5, (Entity)entity);
        final LOTREntityMidges midges = (LOTREntityMidges)entity;
        for (int l = 0; l < midges.midges.length; ++l) {
            final LOTREntityMidges.Midge midge = midges.midges[l];
            GL11.glPushMatrix();
            GL11.glTranslatef(midge.midge_prevPosX + (midge.midge_posX - midge.midge_prevPosX) * this.renderTick, midge.midge_prevPosY + (midge.midge_posY - midge.midge_prevPosY) * this.renderTick, midge.midge_prevPosZ + (midge.midge_posZ - midge.midge_prevPosZ) * this.renderTick);
            GL11.glRotatef(midge.midge_rotation, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(0.2f, 0.2f, 0.2f);
            ((RendererLivingEntity)this).mainModel.render((Entity)entity, f, f1, f2, f3, f4, f5);
            GL11.glPopMatrix();
        }
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderMidges.midgeTexture;
    }
    
    static {
        LOTRRenderMidges.midgeTexture = new ResourceLocation("lotr:mob/midge.png");
    }
}
