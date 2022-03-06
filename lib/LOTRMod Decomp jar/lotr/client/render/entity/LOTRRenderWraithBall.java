// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.projectile.LOTREntityMarshWraithBall;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderWraithBall extends Render
{
    private static ResourceLocation texture;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderWraithBall.texture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(32826);
        this.bindEntityTexture(entity);
        final Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
        this.drawSprite(tessellator, ((LOTREntityMarshWraithBall)entity).animationTick);
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    private void drawSprite(final Tessellator tessellator, final int index) {
        final float var3 = (index % 4 * 16 + 0) / 64.0f;
        final float var4 = (index % 4 * 16 + 16) / 64.0f;
        final float var5 = (index / 4 * 16 + 0) / 64.0f;
        final float var6 = (index / 4 * 16 + 16) / 64.0f;
        final float var7 = 1.0f;
        final float var8 = 0.5f;
        final float var9 = 0.25f;
        GL11.glRotatef(180.0f - super.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-super.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.addVertexWithUV((double)(0.0f - var8), (double)(0.0f - var9), 0.0, (double)var3, (double)var6);
        tessellator.addVertexWithUV((double)(var7 - var8), (double)(0.0f - var9), 0.0, (double)var4, (double)var6);
        tessellator.addVertexWithUV((double)(var7 - var8), (double)(var7 - var9), 0.0, (double)var4, (double)var5);
        tessellator.addVertexWithUV((double)(0.0f - var8), (double)(var7 - var9), 0.0, (double)var3, (double)var5);
        tessellator.draw();
    }
    
    static {
        LOTRRenderWraithBall.texture = new ResourceLocation("lotr:mob/wraith/marshWraith_ball.png");
    }
}
