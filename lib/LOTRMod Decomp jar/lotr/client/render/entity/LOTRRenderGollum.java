// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.client.LOTRSpeechClient;
import net.minecraft.client.Minecraft;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelGollum;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderGollum extends RenderLiving
{
    private static ResourceLocation skin;
    
    public LOTRRenderGollum() {
        super((ModelBase)new LOTRModelGollum(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderGollum.skin;
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final float scale = 0.85f;
        GL11.glScalef(scale, scale, scale);
    }
    
    public void doRender(final EntityLiving entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityGollum gollum = (LOTREntityGollum)entity;
        super.doRender((EntityLiving)gollum, d, d1, d2, f, f1);
        if (Minecraft.isGuiEnabled()) {
            if (!LOTRSpeechClient.hasSpeech(gollum)) {
                this.func_147906_a((Entity)gollum, gollum.getCommandSenderName(), d, d1 + 0.5, d2, 64);
            }
            if (gollum.getGollumOwner() == Minecraft.getMinecraft().thePlayer) {
                LOTRNPCRendering.renderNPCHealthBar((EntityLivingBase)entity, d, d1 + 0.5, d2);
            }
        }
    }
    
    protected void renderEquippedItems(final EntityLivingBase entity, final float f) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        final ItemStack heldItem = entity.getHeldItem();
        if (heldItem != null && heldItem.getItem() == Items.fish) {
            GL11.glPushMatrix();
            ((LOTRModelGollum)((RendererLivingEntity)this).mainModel).head.postRender(0.0625f);
            GL11.glTranslatef(0.21875f, 0.03125f, -0.375f);
            final float f2 = 0.375f;
            GL11.glScalef(f2, f2, f2);
            GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-50.0f, 1.0f, 0.0f, 0.0f);
            ((Render)this).renderManager.itemRenderer.renderItem(entity, heldItem, 0);
            GL11.glPopMatrix();
        }
    }
    
    static {
        LOTRRenderGollum.skin = new ResourceLocation("lotr:mob/char/gollum.png");
    }
}
