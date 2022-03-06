// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderThrowingAxe extends Render
{
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationItemsTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityThrowingAxe axe = (LOTREntityThrowingAxe)entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        if (!axe.inGround) {
            GL11.glTranslatef(0.0f, 0.5f, 0.0f);
        }
        GL11.glRotatef(axe.prevRotationYaw + (axe.rotationYaw - axe.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        if (!axe.inGround) {
            GL11.glRotatef(axe.rotationPitch + (axe.inGround ? 0.0f : (45.0f * f1)), 0.0f, 0.0f, -1.0f);
        }
        else {
            GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(0.0f, 0.75f, 0.0f);
        }
        GL11.glEnable(32826);
        final float f2 = axe.shake - f1;
        if (f2 > 0.0f) {
            final float f3 = -MathHelper.sin(f2 * 3.0f) * f2;
            GL11.glRotatef(f3, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(-135.0f, 0.0f, 0.0f, 1.0f);
        final ItemStack axeItem = axe.getProjectileItem();
        final IIcon icon = axeItem.getIconIndex();
        if (icon == null) {
            FMLLog.severe("Error rendering throwing axe: no icon for " + axeItem.toString(), new Object[0]);
            GL11.glPopMatrix();
            return;
        }
        this.bindEntityTexture(entity);
        final Tessellator tessellator = Tessellator.instance;
        ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
