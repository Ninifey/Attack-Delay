// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.boss.BossStatus;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelEnt;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMallornEnt extends LOTRRenderEnt
{
    private static ResourceLocation mallornEntSkin;
    private static ResourceLocation shieldSkin;
    private LOTRModelEnt shieldModel;
    
    public LOTRRenderMallornEnt() {
        this.shieldModel = new LOTRModelEnt();
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        final LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
        if (((Entity)ent).addedToChunk) {
            BossStatus.setBossStatus((IBossDisplayData)ent, false);
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderMallornEnt.mallornEntSkin;
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
        final float scale = LOTREntityMallornEnt.BOSS_SCALE;
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(0.0f, -ent.getSpawningOffset(f), 0.0f);
        if (ent.isWeaponShieldActive()) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        }
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        final LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
        if (ent.isWeaponShieldActive()) {
            if (pass == 1) {
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                final float f2 = ((Entity)ent).ticksExisted + f;
                final float f3 = f2 * 0.01f;
                final float f4 = f2 * 0.01f;
                GL11.glTranslatef(f3, f4, 0.0f);
                GL11.glMatrixMode(5888);
                GL11.glAlphaFunc(516, 0.01f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                final float alpha = 0.3f + MathHelper.sin(f2 * 0.05f) * 0.15f;
                GL11.glColor4f(alpha, alpha, alpha, 1.0f);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                this.setRenderPassModel((ModelBase)this.shieldModel);
                this.bindTexture(LOTRRenderMallornEnt.shieldSkin);
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
            }
        }
        return -1;
    }
    
    static {
        LOTRRenderMallornEnt.mallornEntSkin = new ResourceLocation("lotr:mob/ent/mallorn.png");
        LOTRRenderMallornEnt.shieldSkin = new ResourceLocation("lotr:mob/ent/mallornEnt_shield.png");
    }
}
