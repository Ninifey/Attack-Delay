// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import java.util.HashMap;
import lotr.common.LOTRMod;
import lotr.client.LOTRTextures;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.Minecraft;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.npc.LOTREntityWargBombardier;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.entity.npc.LOTREntityWarg;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelWarg;
import net.minecraft.util.ResourceLocation;
import java.util.Map;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderWarg extends RenderLiving
{
    private static Map wargSkins;
    private static ResourceLocation wargSaddle;
    private LOTRModelWarg saddleModel;
    private LOTRModelWarg eyesModel;
    
    public LOTRRenderWarg() {
        super((ModelBase)new LOTRModelWarg(), 0.5f);
        this.saddleModel = new LOTRModelWarg(0.5f);
        this.eyesModel = new LOTRModelWarg(0.05f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityWarg warg = (LOTREntityWarg)entity;
        final ResourceLocation skin = getWargSkin(warg.getWargType());
        return LOTRRenderHorse.getLayeredMountTexture(warg, skin);
    }
    
    public static ResourceLocation getWargSkin(final LOTREntityWarg.WargType type) {
        final String s = type.textureName();
        ResourceLocation wargSkin = LOTRRenderWarg.wargSkins.get(s);
        if (wargSkin == null) {
            wargSkin = new ResourceLocation("lotr:mob/warg/" + s + ".png");
            LOTRRenderWarg.wargSkins.put(s, wargSkin);
        }
        return wargSkin;
    }
    
    public void doRender(final EntityLiving entity, final double d, final double d1, final double d2, final float f, final float f1) {
        if (entity instanceof LOTREntityWargBombardier) {
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1 + 1.7f, (float)d2);
            GL11.glRotatef(-f, 0.0f, 1.0f, 0.0f);
            final int i = entity.getBrightnessForRender(f1);
            final int j = i % 65536;
            final int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0f, k / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final LOTRRenderOrcBomb bombRenderer = (LOTRRenderOrcBomb)RenderManager.instance.getEntityClassRenderObject((Class)LOTREntityOrcBomb.class);
            bombRenderer.renderBomb((Entity)entity, 0.0, 0.0, 0.0, f1, ((LOTREntityWargBombardier)entity).getBombFuse(), ((LOTREntityWargBombardier)entity).getBombStrengthLevel(), 0.75f, 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable(32826);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        super.doRender(entity, d, d1, d2, f, f1);
        if (Minecraft.isGuiEnabled() && ((LOTREntityWarg)entity).hiredNPCInfo.getHiringPlayer() == ((Render)this).renderManager.livingPlayer) {
            LOTRNPCRendering.renderHiredIcon((EntityLivingBase)entity, d, d1 + 0.5, d2);
            LOTRNPCRendering.renderNPCHealthBar((EntityLivingBase)entity, d, d1 + 0.5, d2);
        }
    }
    
    protected void renderModel(final EntityLivingBase entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.renderModel(entity, f, f1, f2, f3, f4, f5);
        final LOTREntityWarg warg = (LOTREntityWarg)entity;
        final ResourceLocation eyes = LOTRTextures.getEyesTexture(getWargSkin(warg.getWargType()), new int[][] { { 100, 12 }, { 108, 12 } }, 2, 1);
        LOTRGlowingEyes.renderGlowingEyes((Entity)entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        final LOTREntityWarg warg = (LOTREntityWarg)entity;
        if (pass == 0 && warg.isMountSaddled()) {
            this.bindTexture(LOTRRenderWarg.wargSaddle);
            this.setRenderPassModel((ModelBase)this.saddleModel);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        if (LOTRMod.isAprilFools()) {
            GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
        }
    }
    
    static {
        LOTRRenderWarg.wargSkins = new HashMap();
        LOTRRenderWarg.wargSaddle = new ResourceLocation("lotr:mob/warg/saddle.png");
    }
}
