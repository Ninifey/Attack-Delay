// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.OpenGlHelper;
import lotr.client.LOTRClientProxy;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRBannerProtection;
import net.minecraft.client.Minecraft;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.culling.Frustrum;
import lotr.client.model.LOTRModelBanner;
import net.minecraft.util.ResourceLocation;
import lotr.common.item.LOTRItemBanner;
import java.util.Map;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderBanner extends Render
{
    private static Map<LOTRItemBanner.BannerType, ResourceLocation> bannerTextures;
    private static ResourceLocation standTexture;
    private static LOTRModelBanner model;
    private static Frustrum bannerFrustum;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getStandTexture(entity);
    }
    
    public static ResourceLocation getStandTexture(final LOTRItemBanner.BannerType type) {
        return LOTRRenderBanner.standTexture;
    }
    
    private ResourceLocation getStandTexture(final Entity entity) {
        final LOTREntityBanner banner = (LOTREntityBanner)entity;
        return getStandTexture(banner.getBannerType());
    }
    
    public static ResourceLocation getBannerTexture(final LOTRItemBanner.BannerType type) {
        ResourceLocation r = LOTRRenderBanner.bannerTextures.get(type);
        if (r == null) {
            r = new ResourceLocation("lotr:item/banner/banner_" + type.bannerName + ".png");
            LOTRRenderBanner.bannerTextures.put(type, r);
        }
        return r;
    }
    
    private ResourceLocation getBannerTexture(final Entity entity) {
        final LOTREntityBanner banner = (LOTREntityBanner)entity;
        return getBannerTexture(banner.getBannerType());
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityBanner banner = (LOTREntityBanner)entity;
        final Minecraft mc = Minecraft.getMinecraft();
        final boolean debug = mc.gameSettings.showDebugInfo;
        final boolean protecting = banner.isProtectingTerritory();
        final boolean accessible = protecting && LOTRBannerProtection.forPlayer((EntityPlayer)mc.thePlayer).protects(banner) == LOTRBannerProtection.ProtectType.NONE;
        final boolean renderBox = debug && protecting;
        final boolean seeThroughWalls = renderBox && ((EntityPlayer)mc.thePlayer).capabilities.isCreativeMode;
        final int protectColor = 65280;
        LOTRRenderBanner.bannerFrustum.setPosition(d + RenderManager.renderPosX, d1 + RenderManager.renderPosY, d2 + RenderManager.renderPosZ);
        if (LOTRRenderBanner.bannerFrustum.isBoundingBoxInFrustum(banner.boundingBox)) {
            GL11.glPushMatrix();
            GL11.glDisable(2884);
            GL11.glTranslatef((float)d, (float)d1 + 1.5f, (float)d2);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            GL11.glRotatef(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, 0.01f, 0.0f);
            if (seeThroughWalls) {
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                final int light = LOTRClientProxy.TESSELLATOR_MAX_BRIGHTNESS;
                final int lx = light % 65536;
                final int ly = light / 65536;
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx / 1.0f, ly / 1.0f);
                GL11.glColor4f((protectColor >> 16 & 0xFF) / 255.0f, (protectColor >> 8 & 0xFF) / 255.0f, (protectColor >> 0 & 0xFF) / 255.0f, 1.0f);
            }
            this.bindTexture(this.getStandTexture(entity));
            LOTRRenderBanner.model.renderStand(0.0625f);
            LOTRRenderBanner.model.renderPost(0.0625f);
            this.bindTexture(this.getBannerTexture(entity));
            LOTRRenderBanner.model.renderBanner(0.0625f);
            if (seeThroughWalls) {
                GL11.glEnable(2929);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
            }
            GL11.glEnable(2884);
            GL11.glPopMatrix();
        }
        if (renderBox) {
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GL11.glDisable(3553);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            final int light = LOTRClientProxy.TESSELLATOR_MAX_BRIGHTNESS;
            final int lx = light % 65536;
            final int ly = light / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx / 1.0f, ly / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(2896);
            final float width = entity.width / 2.0f;
            final AxisAlignedBB aabb = banner.createProtectionCube().offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderGlobal.drawOutlinedBoundingBox(aabb, protectColor);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }
    
    static {
        LOTRRenderBanner.bannerTextures = new HashMap<LOTRItemBanner.BannerType, ResourceLocation>();
        LOTRRenderBanner.standTexture = new ResourceLocation("lotr:item/banner/stand.png");
        LOTRRenderBanner.model = new LOTRModelBanner();
        LOTRRenderBanner.bannerFrustum = new Frustrum();
    }
}
