// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.projectile.LOTREntityGandalfFireball;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import lotr.client.LOTRClientProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderGandalfFireball extends Render
{
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRClientProxy.particlesTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(32826);
        this.bindEntityTexture(entity);
        final Tessellator tessellator = Tessellator.instance;
        this.drawSprite(tessellator, 24 + ((LOTREntityGandalfFireball)entity).animationTick);
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    private void drawSprite(final Tessellator tessellator, final int index) {
        final float f = (index % 8 * 16 + 0) / 128.0f;
        final float f2 = (index % 8 * 16 + 16) / 128.0f;
        final float f3 = (index / 8 * 16 + 0) / 128.0f;
        final float f4 = (index / 8 * 16 + 16) / 128.0f;
        final float f5 = 1.0f;
        final float f6 = 0.5f;
        final float f7 = 0.25f;
        GL11.glRotatef(180.0f - super.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-super.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.setBrightness(15728880);
        tessellator.addVertexWithUV((double)(0.0f - f6), (double)(0.0f - f7), 0.0, (double)f, (double)f4);
        tessellator.addVertexWithUV((double)(f5 - f6), (double)(0.0f - f7), 0.0, (double)f2, (double)f4);
        tessellator.addVertexWithUV((double)(f5 - f6), (double)(f5 - f7), 0.0, (double)f2, (double)f3);
        tessellator.addVertexWithUV((double)(0.0f - f6), (double)(f5 - f7), 0.0, (double)f, (double)f3);
        tessellator.draw();
    }
}
