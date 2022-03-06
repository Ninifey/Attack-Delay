// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.passive.EntitySheep;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import lotr.client.LOTRClientProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelSmokeShip;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderSmokeRing extends Render
{
    private ModelBase magicShipModel;
    
    public LOTRRenderSmokeRing() {
        this.magicShipModel = new LOTRModelSmokeShip();
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRClientProxy.particlesTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(32826);
        GL11.glEnable(2977);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final LOTREntitySmokeRing smokeRing = (LOTREntitySmokeRing)entity;
        final float age = smokeRing.getRenderSmokeAge(f1);
        final float opacity = 1.0f - age;
        final int colour = smokeRing.getSmokeColour();
        if (colour == 16) {
            GL11.glDisable(3553);
            GL11.glDisable(2884);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, opacity * 0.75f);
            GL11.glScalef(0.3f, -0.3f, 0.3f);
            GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1, 0.0f, 0.0f, -1.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            this.magicShipModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
            GL11.glEnable(2884);
            GL11.glEnable(3553);
        }
        else {
            final float[] colours = EntitySheep.fleeceColorTable[colour];
            GL11.glColor4f(colours[0], colours[1], colours[2], opacity);
            this.bindEntityTexture(entity);
            final float smokeSize = 0.1f + 0.9f * age;
            GL11.glScalef(smokeSize, smokeSize, smokeSize);
            this.drawSprite(tessellator, 0);
        }
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    private void drawSprite(final Tessellator tessellator, final int index) {
        final float var3 = (index % 16 * 16 + 0) / 128.0f;
        final float var4 = (index % 16 * 16 + 16) / 128.0f;
        final float var5 = (index / 16 * 16 + 0) / 128.0f;
        final float var6 = (index / 16 * 16 + 16) / 128.0f;
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
}
