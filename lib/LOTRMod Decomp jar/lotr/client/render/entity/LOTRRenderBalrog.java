// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import lotr.client.LOTRClientProxy;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelBalrog;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderBalrog extends RenderLiving
{
    private static LOTRRandomSkins balrogSkins;
    private static LOTRRandomSkins balrogSkinsBright;
    private static final ResourceLocation fireTexture;
    private LOTRModelBalrog balrogModel;
    private LOTRModelBalrog balrogModelBright;
    private LOTRModelBalrog fireModel;
    
    public LOTRRenderBalrog() {
        super((ModelBase)new LOTRModelBalrog(), 0.5f);
        this.balrogModel = (LOTRModelBalrog)((RendererLivingEntity)this).mainModel;
        this.balrogModelBright = new LOTRModelBalrog(0.05f);
        (this.fireModel = new LOTRModelBalrog(0.0f)).setFireModel();
        LOTRRenderBalrog.balrogSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog");
        LOTRRenderBalrog.balrogSkinsBright = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog_bright");
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderBalrog.balrogSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
        final ItemStack heldItem = balrog.getHeldItem();
        final LOTRModelBalrog balrogModel = this.balrogModel;
        final LOTRModelBalrog fireModel = this.fireModel;
        final int n = (heldItem == null) ? 0 : 2;
        fireModel.heldItemRight = n;
        balrogModel.heldItemRight = n;
        super.doRender((EntityLiving)balrog, d, d1, d2, f, f1);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
        final float scale = 2.0f;
        GL11.glScalef(scale, scale, scale);
        if (balrog.isBalrogCharging()) {
            float lean = balrog.getInterpolatedChargeLean(f);
            lean *= 35.0f;
            GL11.glRotatef(lean, 1.0f, 0.0f, 0.0f);
        }
    }
    
    private void setupFullBright() {
        final int light = LOTRClientProxy.TESSELLATOR_MAX_BRIGHTNESS;
        final int lx = light % 65536;
        final int ly = light / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx / 1.0f, ly / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        final LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
        if (balrog.isWreathedInFlame()) {
            if (pass == 1) {
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                final float f2 = ((Entity)balrog).ticksExisted + f;
                final float f3 = f2 * 0.01f;
                final float f4 = f2 * 0.01f;
                GL11.glTranslatef(f3, f4, 0.0f);
                GL11.glMatrixMode(5888);
                GL11.glAlphaFunc(516, 0.01f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                float alpha;
                if (balrog.isBalrogCharging()) {
                    alpha = 0.6f + MathHelper.sin(f2 * 0.1f) * 0.15f;
                }
                else {
                    alpha = 0.3f + MathHelper.sin(f2 * 0.05f) * 0.15f;
                }
                GL11.glColor4f(alpha, alpha, alpha, 1.0f);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                this.setRenderPassModel((ModelBase)this.fireModel);
                this.bindTexture(LOTRRenderBalrog.fireTexture);
                return 1;
            }
            if (pass == 2) {
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glAlphaFunc(516, 0.1f);
                GL11.glDisable(3042);
                GL11.glEnable(2896);
                GL11.glDepthMask(true);
                GL11.glDisable(2896);
                this.setupFullBright();
                this.setRenderPassModel((ModelBase)this.balrogModelBright);
                this.bindTexture(LOTRRenderBalrog.balrogSkinsBright.getRandomSkin(balrog));
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                return 1;
            }
            if (pass == 3) {
                GL11.glEnable(2896);
                GL11.glDisable(3042);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        return -1;
    }
    
    protected void renderEquippedItems(final EntityLivingBase entity, final float f) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        final ItemStack heldItem = entity.getHeldItem();
        if (heldItem != null) {
            GL11.glPushMatrix();
            this.balrogModel.body.postRender(0.0625f);
            this.balrogModel.rightArm.postRender(0.0625f);
            GL11.glTranslatef(-0.25f, 1.5f, -0.125f);
            final float scale = 1.25f;
            GL11.glScalef(scale, -scale, scale);
            GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            ((Render)this).renderManager.itemRenderer.renderItem(entity, heldItem, 0);
            if (heldItem.getItem().requiresMultipleRenderPasses()) {
                for (int x = 1; x < heldItem.getItem().getRenderPasses(heldItem.getItemDamage()); ++x) {
                    ((Render)this).renderManager.itemRenderer.renderItem(entity, heldItem, x);
                }
            }
            GL11.glPopMatrix();
        }
    }
    
    static {
        fireTexture = new ResourceLocation("lotr:mob/balrog/fire.png");
    }
}
