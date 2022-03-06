// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderDart extends Render
{
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationItemsTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityDart dart = (LOTREntityDart)entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(dart.prevRotationYaw + (dart.rotationYaw - dart.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(dart.prevRotationPitch + (dart.rotationPitch - dart.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        GL11.glEnable(32826);
        final float f2 = dart.shake - f1;
        if (f2 > 0.0f) {
            final float f3 = -MathHelper.sin(f2 * 3.0f) * f2;
            GL11.glRotatef(f3, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(-135.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(0.0f, -1.0f, 0.0f);
        final float scale = 0.6f;
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(0.0f, 0.8f, 0.0f);
        final ItemStack itemstack = dart.getProjectileItem();
        final IIcon icon = itemstack.getIconIndex();
        final Tessellator tessellator = Tessellator.instance;
        final float minU = icon.getMinU();
        final float maxU = icon.getMaxU();
        final float minV = icon.getMinV();
        final float maxV = icon.getMaxV();
        final int width = icon.getIconWidth();
        final int height = icon.getIconWidth();
        this.bindTexture(this.getEntityTexture(dart));
        ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, width, height, 0.0625f);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
