// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.item.LOTREntityBannerWall;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelBannerWall;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderBannerWall extends Render
{
    private static LOTRModelBannerWall model;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityBannerWall banner = (LOTREntityBannerWall)entity;
        return LOTRRenderBanner.getStandTexture(banner.getBannerType());
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityBannerWall banner = (LOTREntityBannerWall)entity;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glRotatef(f, 0.0f, 1.0f, 0.0f);
        this.bindTexture(LOTRRenderBanner.getStandTexture(banner.getBannerType()));
        LOTRRenderBannerWall.model.renderPost(0.0625f);
        this.bindTexture(LOTRRenderBanner.getBannerTexture(banner.getBannerType()));
        LOTRRenderBannerWall.model.renderBanner(0.0625f);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        if (RenderManager.field_85095_o) {
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            final float width = entity.width / 2.0f;
            final AxisAlignedBB aabb = ((Entity)banner).boundingBox.copy().offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderGlobal.drawOutlinedBoundingBox(aabb, 16777215);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }
    
    static {
        LOTRRenderBannerWall.model = new LOTRModelBannerWall();
    }
}
