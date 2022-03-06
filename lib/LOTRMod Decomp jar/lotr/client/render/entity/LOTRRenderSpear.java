// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.client.model.LOTRModelBoar;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.projectile.LOTREntitySpear;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderSpear extends Render
{
    private static ModelBase boarModel;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationItemsTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntitySpear spear = (LOTREntitySpear)entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(spear.prevRotationYaw + (spear.rotationYaw - spear.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(spear.prevRotationPitch + (spear.rotationPitch - spear.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        GL11.glEnable(32826);
        final float f2 = spear.shake - f1;
        if (f2 > 0.0f) {
            final float f3 = -MathHelper.sin(f2 * 3.0f) * f2;
            GL11.glRotatef(f3, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(-135.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(0.0f, -1.0f, 0.0f);
        if (LOTRMod.isAprilFools()) {
            this.bindTexture(LOTRRenderWildBoar.boarSkin);
            GL11.glTranslatef(0.0f, -2.5f, 0.0f);
            GL11.glScalef(0.25f, 0.25f, 0.25f);
            LOTRRenderSpear.boarModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.625f);
        }
        else {
            final ItemStack itemstack = spear.getProjectileItem();
            final IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
            if (customRenderer != null) {
                customRenderer.renderItem(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, new Object[2]);
            }
            else {
                FMLLog.severe("Error rendering spear: no custom renderer for " + itemstack.toString(), new Object[0]);
            }
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderSpear.boarModel = (ModelBase)new LOTRModelBoar();
        LOTRRenderSpear.boarModel.isChild = false;
    }
}
