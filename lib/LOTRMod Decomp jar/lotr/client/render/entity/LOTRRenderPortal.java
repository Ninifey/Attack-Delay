// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.client.model.LOTRModelPortal;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.item.LOTREntityPortal;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderPortal extends Render
{
    public static ResourceLocation ringTexture;
    public static ResourceLocation writingTexture;
    public static ModelBase ringModel;
    public static ModelBase writingModelOuter;
    public static ModelBase writingModelInner;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderPortal.ringTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityPortal portal = (LOTREntityPortal)entity;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)d, (float)d1 + 0.75f, (float)d2);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GL11.glEnable(32826);
        GL11.glScalef(1.0f, -1.0f, 1.0f);
        float portalScale = (float)portal.getScale();
        if (portalScale < LOTREntityPortal.MAX_SCALE) {
            portalScale += f1;
            portalScale /= LOTREntityPortal.MAX_SCALE;
            GL11.glScalef(portalScale, portalScale, portalScale);
        }
        GL11.glRotatef(portal.getPortalRotation(f1), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
        this.bindTexture(this.getEntityTexture(portal));
        final float scale = 0.0625f;
        LOTRRenderPortal.ringModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
        GL11.glDisable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Tessellator.instance.setBrightness(LOTRClientProxy.TESSELLATOR_MAX_BRIGHTNESS);
        this.bindTexture(LOTRRenderPortal.writingTexture);
        LOTRRenderPortal.writingModelOuter.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale * 1.05f);
        this.bindTexture(LOTRRenderPortal.writingTexture);
        LOTRRenderPortal.writingModelInner.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale * 0.85f);
        GL11.glEnable(2896);
        GL11.glDisable(32826);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderPortal.ringTexture = new ResourceLocation("lotr:misc/portal.png");
        LOTRRenderPortal.writingTexture = new ResourceLocation("lotr:misc/portal_writing.png");
        LOTRRenderPortal.ringModel = new LOTRModelPortal(0);
        LOTRRenderPortal.writingModelOuter = new LOTRModelPortal(1);
        LOTRRenderPortal.writingModelInner = new LOTRModelPortal(1);
    }
}
