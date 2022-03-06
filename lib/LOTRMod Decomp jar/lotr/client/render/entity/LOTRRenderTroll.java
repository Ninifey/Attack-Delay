// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.Render;
import org.lwjgl.opengl.GL11;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelTroll;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderTroll extends RenderLiving
{
    private static LOTRRandomSkins trollSkins;
    public static ResourceLocation[] trollOutfits;
    private static ResourceLocation weaponsTexture;
    private LOTRModelTroll shirtModel;
    private LOTRModelTroll trousersModel;
    
    public LOTRRenderTroll() {
        super((ModelBase)new LOTRModelTroll(), 0.5f);
        this.shirtModel = new LOTRModelTroll(1.0f, 0);
        this.trousersModel = new LOTRModelTroll(0.75f, 1);
        LOTRRenderTroll.trollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/troll");
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderTroll.trollSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    public void doRender(final EntityLiving entity, final double d, final double d1, final double d2, final float f, final float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        if (Minecraft.isGuiEnabled() && ((LOTREntityNPC)entity).hiredNPCInfo.getHiringPlayer() == ((Render)this).renderManager.livingPlayer) {
            LOTRNPCRendering.renderHiredIcon((EntityLivingBase)entity, d, d1 + 1.0, d2);
            LOTRNPCRendering.renderNPCHealthBar((EntityLivingBase)entity, d, d1 + 1.0, d2);
        }
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final LOTREntityTroll troll = (LOTREntityTroll)entity;
        this.scaleTroll(troll, false);
        if (LOTRMod.isAprilFools() || troll.familyInfo.getName().toLowerCase().equals("shrek")) {
            GL11.glColor3f(0.0f, 1.0f, 0.0f);
        }
        else if (troll.familyInfo.getName().toLowerCase().equals("drek")) {
            GL11.glColor3f(0.2f, 0.4f, 1.0f);
        }
    }
    
    protected void scaleTroll(final LOTREntityTroll troll, final boolean inverse) {
        float scale = troll.getTrollScale();
        if (inverse) {
            scale = 1.0f / scale;
        }
        GL11.glScalef(scale, scale, scale);
    }
    
    protected void renderEquippedItems(final EntityLivingBase entity, final float f) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        super.renderEquippedItems(entity, f);
        GL11.glPushMatrix();
        this.bindTexture(LOTRRenderTroll.weaponsTexture);
        this.renderTrollWeapon(entity, f);
        GL11.glPopMatrix();
    }
    
    protected void renderTrollWeapon(final EntityLivingBase entity, final float f) {
        ((LOTRModelTroll)((RendererLivingEntity)this).mainModel).renderWoodenClub(0.0625f);
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        this.bindTrollOutfitTexture(entity);
        if (pass == 0) {
            this.shirtModel.onGround = ((RendererLivingEntity)this).mainModel.onGround;
            this.setRenderPassModel((ModelBase)this.shirtModel);
            return 1;
        }
        if (pass == 1) {
            this.trousersModel.onGround = this.trousersModel.onGround;
            this.setRenderPassModel((ModelBase)this.trousersModel);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    protected void bindTrollOutfitTexture(final EntityLivingBase entity) {
        int j = ((LOTREntityTroll)entity).getTrollOutfit();
        if (j < 0 || j >= LOTRRenderTroll.trollOutfits.length) {
            j = 0;
        }
        this.bindTexture(LOTRRenderTroll.trollOutfits[j]);
    }
    
    protected void rotateCorpse(final EntityLivingBase entity, final float f, float f1, final float f2) {
        if (((LOTREntityTroll)entity).getSneezingTime() > 0) {
            f1 += (float)(Math.cos(((Entity)entity).ticksExisted * 3.25) * 3.141592653589793);
        }
        super.rotateCorpse(entity, f, f1, f2);
    }
    
    static {
        LOTRRenderTroll.trollOutfits = new ResourceLocation[] { new ResourceLocation("lotr:mob/troll/outfit_0.png"), new ResourceLocation("lotr:mob/troll/outfit_1.png"), new ResourceLocation("lotr:mob/troll/outfit_2.png") };
        LOTRRenderTroll.weaponsTexture = new ResourceLocation("lotr:mob/troll/weapons.png");
    }
}
