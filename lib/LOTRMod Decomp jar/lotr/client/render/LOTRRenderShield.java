// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemArmor;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRShields;

public class LOTRRenderShield
{
    private static int SHIELD_WIDTH;
    private static int SHIELD_HEIGHT;
    private static float MODELSCALE;
    
    public static void renderShield(final LOTRShields shield, final EntityLivingBase entity, final ModelBiped model) {
        final Minecraft mc = Minecraft.getMinecraft();
        final ResourceLocation shieldTexture = shield.shieldTexture;
        final ItemStack held = (entity == null) ? null : entity.getHeldItem();
        final ItemStack heldLeft = (entity instanceof LOTREntityNPC) ? ((LOTREntityNPC)entity).getHeldItemLeft() : null;
        final ItemStack inUse = (entity instanceof EntityPlayer) ? ((EntityPlayer)entity).getItemInUse() : null;
        final boolean holdingSword = entity == null || (held != null && (held.getItem() instanceof ItemSword || held.getItem() instanceof ItemTool) && (inUse == null || inUse.getItemUseAction() != EnumAction.bow));
        final boolean blocking = holdingSword && inUse != null && inUse.getItemUseAction() == EnumAction.block;
        if (heldLeft != null && entity instanceof LOTREntityNPC) {
            final LOTREntityNPC npc = (LOTREntityNPC)entity;
            if (npc.npcCape != null) {
                return;
            }
        }
        final ItemStack chestplate = (entity == null) ? null : entity.getEquipmentInSlot(3);
        final boolean wearingChestplate = chestplate != null && chestplate.getItem().isValidArmor(chestplate, ((LOTRItemArmor)LOTRMod.bodyMithril).armorType, (Entity)entity);
        final boolean renderOnBack = !holdingSword || (holdingSword && heldLeft != null);
        GL11.glPushMatrix();
        if (renderOnBack) {
            model.bipedBody.postRender(LOTRRenderShield.MODELSCALE);
        }
        else {
            model.bipedLeftArm.postRender(LOTRRenderShield.MODELSCALE);
        }
        GL11.glScalef(-1.5f, -1.5f, 1.5f);
        if (renderOnBack) {
            GL11.glTranslatef(0.5f, -0.8f, 0.0f);
            if (wearingChestplate) {
                GL11.glTranslatef(0.0f, 0.0f, 0.24f);
            }
            else {
                GL11.glTranslatef(0.0f, 0.0f, 0.16f);
            }
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        }
        else if (blocking) {
            GL11.glRotatef(10.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(-0.4f, -0.9f, -0.15f);
        }
        else {
            GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(-0.5f, -0.75f, 0.0f);
            if (wearingChestplate) {
                GL11.glTranslatef(0.0f, 0.0f, -0.24f);
            }
            else {
                GL11.glTranslatef(0.0f, 0.0f, -0.16f);
            }
            GL11.glRotatef(-15.0f, 0.0f, 0.0f, 1.0f);
        }
        mc.getTextureManager().bindTexture(shieldTexture);
        GL11.glEnable(3008);
        doRenderShield(0.0f);
        GL11.glTranslatef(1.0f, 0.0f, 0.0f);
        GL11.glScalef(-1.0f, 1.0f, 1.0f);
        doRenderShield(0.5f);
        GL11.glPopMatrix();
    }
    
    private static void doRenderShield(final float f) {
        final float minU = 0.0f + f;
        final float maxU = 0.5f + f;
        final float minV = 0.0f;
        final float maxV = 1.0f;
        final int width = LOTRRenderShield.SHIELD_WIDTH;
        final int height = LOTRRenderShield.SHIELD_HEIGHT;
        final double depth1 = LOTRRenderShield.MODELSCALE * 0.5f * f;
        final double depth2 = LOTRRenderShield.MODELSCALE * 0.5f * (0.5f + f);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(0.0, 0.0, depth1, (double)maxU, (double)maxV);
        tessellator.addVertexWithUV(1.0, 0.0, depth1, (double)minU, (double)maxV);
        tessellator.addVertexWithUV(1.0, 1.0, depth1, (double)minU, (double)minV);
        tessellator.addVertexWithUV(0.0, 1.0, depth1, (double)maxU, (double)minV);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(0.0, 1.0, depth2, (double)maxU, (double)minV);
        tessellator.addVertexWithUV(1.0, 1.0, depth2, (double)minU, (double)minV);
        tessellator.addVertexWithUV(1.0, 0.0, depth2, (double)minU, (double)maxV);
        tessellator.addVertexWithUV(0.0, 0.0, depth2, (double)maxU, (double)maxV);
        tessellator.draw();
        final float f2 = 0.5f * (maxU - minU) / width;
        final float f3 = 0.5f * (maxV - minV) / height;
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        for (int k = 0; k < width; ++k) {
            final float f4 = k / (float)width;
            final float f5 = maxU + (minU - maxU) * f4 - f2;
            tessellator.addVertexWithUV((double)f4, 0.0, depth2, (double)f5, (double)maxV);
            tessellator.addVertexWithUV((double)f4, 0.0, depth1, (double)f5, (double)maxV);
            tessellator.addVertexWithUV((double)f4, 1.0, depth1, (double)f5, (double)minV);
            tessellator.addVertexWithUV((double)f4, 1.0, depth2, (double)f5, (double)minV);
        }
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        for (int k = 0; k < width; ++k) {
            final float f4 = k / (float)width;
            final float f5 = maxU + (minU - maxU) * f4 - f2;
            final float f6 = f4 + 1.0f / width;
            tessellator.addVertexWithUV((double)f6, 1.0, depth2, (double)f5, (double)minV);
            tessellator.addVertexWithUV((double)f6, 1.0, depth1, (double)f5, (double)minV);
            tessellator.addVertexWithUV((double)f6, 0.0, depth1, (double)f5, (double)maxV);
            tessellator.addVertexWithUV((double)f6, 0.0, depth2, (double)f5, (double)maxV);
        }
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        for (int k = 0; k < height; ++k) {
            final float f4 = k / (float)height;
            final float f5 = maxV + (minV - maxV) * f4 - f3;
            final float f6 = f4 + 1.0f / height;
            tessellator.addVertexWithUV(0.0, (double)f6, depth1, (double)maxU, (double)f5);
            tessellator.addVertexWithUV(1.0, (double)f6, depth1, (double)minU, (double)f5);
            tessellator.addVertexWithUV(1.0, (double)f6, depth2, (double)minU, (double)f5);
            tessellator.addVertexWithUV(0.0, (double)f6, depth2, (double)maxU, (double)f5);
        }
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        for (int k = 0; k < height; ++k) {
            final float f4 = k / (float)height;
            final float f5 = maxV + (minV - maxV) * f4 - f3;
            tessellator.addVertexWithUV(1.0, (double)f4, depth1, (double)minU, (double)f5);
            tessellator.addVertexWithUV(0.0, (double)f4, depth1, (double)maxU, (double)f5);
            tessellator.addVertexWithUV(0.0, (double)f4, depth2, (double)maxU, (double)f5);
            tessellator.addVertexWithUV(1.0, (double)f4, depth2, (double)minU, (double)f5);
        }
        tessellator.draw();
    }
    
    static {
        LOTRRenderShield.SHIELD_WIDTH = 32;
        LOTRRenderShield.SHIELD_HEIGHT = 32;
        LOTRRenderShield.MODELSCALE = 0.0625f;
    }
}
