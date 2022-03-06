// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import lotr.common.item.LOTRItemCrossbowBolt;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderCrossbowBolt extends Render
{
    private static ResourceLocation boltTexture;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderCrossbowBolt.boltTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityCrossbowBolt bolt = (LOTREntityCrossbowBolt)entity;
        final ItemStack boltItem = bolt.getProjectileItem();
        this.bindEntityTexture((Entity)bolt);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(bolt.prevRotationYaw + (bolt.rotationYaw - bolt.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(bolt.prevRotationPitch + (bolt.rotationPitch - bolt.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        final Tessellator tessellator = Tessellator.instance;
        int yOffset = 0;
        if (boltItem != null && boltItem.getItem() instanceof LOTRItemCrossbowBolt && ((LOTRItemCrossbowBolt)boltItem.getItem()).isPoisoned) {
            yOffset = 1;
        }
        final float f2 = 0.0f;
        final float f3 = 0.5f;
        final float f4 = (0 + yOffset * 10) / 32.0f;
        final float f5 = (5 + yOffset * 10) / 32.0f;
        final float f6 = 0.0f;
        final float f7 = 0.15625f;
        final float f8 = (5 + yOffset * 10) / 32.0f;
        final float f9 = (10 + yOffset * 10) / 32.0f;
        final float f10 = 0.05625f;
        GL11.glEnable(32826);
        final float f11 = bolt.shake - f1;
        if (f11 > 0.0f) {
            final float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(f10, 0.0f, 0.0f);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7.0, -2.0, -2.0, (double)f6, (double)f8);
        tessellator.addVertexWithUV(-7.0, -2.0, 2.0, (double)f7, (double)f8);
        tessellator.addVertexWithUV(-7.0, 2.0, 2.0, (double)f7, (double)f9);
        tessellator.addVertexWithUV(-7.0, 2.0, -2.0, (double)f6, (double)f9);
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0f, 0.0f);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7.0, 2.0, -2.0, (double)f6, (double)f8);
        tessellator.addVertexWithUV(-7.0, 2.0, 2.0, (double)f7, (double)f8);
        tessellator.addVertexWithUV(-7.0, -2.0, 2.0, (double)f7, (double)f9);
        tessellator.addVertexWithUV(-7.0, -2.0, -2.0, (double)f6, (double)f9);
        tessellator.draw();
        for (int i = 0; i < 4; ++i) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, f10);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8.0, -2.0, 0.0, (double)f2, (double)f4);
            tessellator.addVertexWithUV(8.0, -2.0, 0.0, (double)f3, (double)f4);
            tessellator.addVertexWithUV(8.0, 2.0, 0.0, (double)f3, (double)f5);
            tessellator.addVertexWithUV(-8.0, 2.0, 0.0, (double)f2, (double)f5);
            tessellator.draw();
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderCrossbowBolt.boltTexture = new ResourceLocation("lotr:item/crossbowBolt.png");
    }
}
