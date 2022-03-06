// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.awt.Color;
import lotr.common.item.LOTRItemRedBook;
import net.minecraft.util.IIcon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRMod;
import lotr.common.LOTRLevelData;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.StringUtils;
import lotr.common.LOTRConfig;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import lotr.client.LOTRSpeechClient;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;

public class LOTRNPCRendering
{
    private static RenderItem itemRenderer;
    
    public static void renderAllNPCSpeeches(final Minecraft mc, final World world, final float f) {
        GL11.glPushMatrix();
        RenderHelper.enableStandardItemLighting();
        GL11.glAlphaFunc(516, 0.01f);
        final double d0 = RenderManager.renderPosX;
        final double d2 = RenderManager.renderPosY;
        final double d3 = RenderManager.renderPosZ;
        for (final Object obj : world.loadedEntityList) {
            final Entity entity = (Entity)obj;
            final boolean inRange = entity.isInRangeToRender3d(d0, d2, d3);
            if (entity instanceof LOTREntityNPC && inRange) {
                final LOTREntityNPC npc = (LOTREntityNPC)entity;
                if (!npc.isEntityAlive()) {
                    LOTRSpeechClient.removeSpeech(npc);
                }
                else {
                    final LOTRSpeechClient.TimedSpeech timedSpeech = LOTRSpeechClient.getSpeechFor(npc);
                    if (timedSpeech == null) {
                        continue;
                    }
                    final double d4 = ((Entity)npc).lastTickPosX + (((Entity)npc).posX - ((Entity)npc).lastTickPosX) * f;
                    final double d5 = ((Entity)npc).lastTickPosY + (((Entity)npc).posY - ((Entity)npc).lastTickPosY) * f;
                    final double d6 = ((Entity)npc).lastTickPosZ + (((Entity)npc).posZ - ((Entity)npc).lastTickPosZ) * f;
                    renderSpeech((EntityLivingBase)npc, timedSpeech.getSpeech(), timedSpeech.getAge(), d4 - d0, d5 - d2, d6 - d3);
                }
            }
        }
        GL11.glAlphaFunc(516, 0.1f);
        RenderHelper.disableStandardItemLighting();
        mc.entityRenderer.disableLightmap((double)f);
        GL11.glPopMatrix();
    }
    
    public static void renderSpeech(final EntityLivingBase entity, final String speech, final float speechAge, final double d, final double d1, final double d2) {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        world.theProfiler.startSection("renderNPCSpeech");
        final TextureManager textureManager = mc.getTextureManager();
        final RenderManager renderManager = RenderManager.instance;
        final FontRenderer fr = mc.fontRenderer;
        final double distance = RendererLivingEntity.NAME_TAG_RANGE;
        final double distanceSq = entity.getDistanceSqToEntity((Entity)renderManager.livingPlayer);
        if (distanceSq <= distance * distance) {
            final String name = EnumChatFormatting.YELLOW + entity.getCommandSenderName();
            final int fontHeight = fr.FONT_HEIGHT;
            final int speechWidth = 150;
            final List<String> speechLines = (List<String>)fr.listFormattedStringToWidth(speech, speechWidth);
            float alpha = 0.8f;
            if (speechAge < 0.1f) {
                alpha *= speechAge / 0.1f;
            }
            final int intAlpha = (int)(alpha * 255.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1 + ((Entity)entity).height + 0.3f, (float)d2);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final Tessellator tessellator = Tessellator.instance;
            final float scale = 0.015f;
            GL11.glScalef(-scale, -scale, scale);
            GL11.glTranslatef(0.0f, (float)(-fontHeight * (3 + speechLines.size())), 0.0f);
            GL11.glDisable(3553);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f * alpha);
            final int halfNameW = fr.getStringWidth(name) / 2;
            tessellator.addVertex((double)(-halfNameW - 1), 0.0, 0.0);
            tessellator.addVertex((double)(-halfNameW - 1), (double)fontHeight, 0.0);
            tessellator.addVertex((double)(halfNameW + 1), (double)fontHeight, 0.0);
            tessellator.addVertex((double)(halfNameW + 1), 0.0, 0.0);
            tessellator.draw();
            GL11.glEnable(3553);
            fr.drawString(name, -halfNameW, 0, intAlpha << 24 | 0xFFFFFF);
            GL11.glTranslatef(0.0f, (float)fontHeight, 0.0f);
            for (final String line : speechLines) {
                GL11.glTranslatef(0.0f, (float)fontHeight, 0.0f);
                GL11.glDisable(3553);
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f * alpha);
                final int halfLineW = fr.getStringWidth(line) / 2;
                tessellator.addVertex((double)(-halfLineW - 1), 0.0, 0.0);
                tessellator.addVertex((double)(-halfLineW - 1), (double)fontHeight, 0.0);
                tessellator.addVertex((double)(halfLineW + 1), (double)fontHeight, 0.0);
                tessellator.addVertex((double)(halfLineW + 1), 0.0, 0.0);
                tessellator.draw();
                GL11.glEnable(3553);
                fr.drawString(line, -halfLineW, 0, intAlpha << 24 | 0xFFFFFF);
            }
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
        world.theProfiler.endSection();
    }
    
    public static void renderHiredIcon(final EntityLivingBase entity, final double d, final double d1, final double d2) {
        if (!LOTRConfig.hiredUnitIcons) {
            return;
        }
        if (((Entity)entity).riddenByEntity instanceof LOTREntityNPC) {
            return;
        }
        if (entity instanceof LOTREntityNPC && LOTRSpeechClient.hasSpeech((LOTREntityNPC)entity)) {
            return;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        world.theProfiler.startSection("renderHiredIcon");
        final TextureManager textureManager = mc.getTextureManager();
        final RenderManager renderManager = RenderManager.instance;
        final double distance = RendererLivingEntity.NAME_TAG_RANGE;
        final double distanceSq = entity.getDistanceSqToEntity((Entity)renderManager.livingPlayer);
        if (distanceSq <= distance * distance) {
            final ItemStack hiredIcon = entity.getHeldItem();
            String squadron = null;
            if (entity instanceof LOTREntityNPC) {
                final LOTREntityNPC npc = (LOTREntityNPC)entity;
                final String s = npc.hiredNPCInfo.getSquadron();
                if (!StringUtils.isNullOrEmpty(s)) {
                    squadron = s;
                }
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1 + ((Entity)entity).height, (float)d2);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (squadron != null) {
                GL11.glTranslatef(0.0f, 0.3f, 0.0f);
                GL11.glPushMatrix();
                final FontRenderer fr = mc.fontRenderer;
                final Tessellator tessellator = Tessellator.instance;
                final int halfWidth = fr.getStringWidth(squadron) / 2;
                final float boxScale = 0.015f;
                GL11.glScalef(-boxScale, -boxScale, boxScale);
                GL11.glDisable(3553);
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                tessellator.addVertex((double)(-halfWidth - 1), -9.0, 0.0);
                tessellator.addVertex((double)(-halfWidth - 1), 0.0, 0.0);
                tessellator.addVertex((double)(halfWidth + 1), 0.0, 0.0);
                tessellator.addVertex((double)(halfWidth + 1), -9.0, 0.0);
                tessellator.draw();
                GL11.glEnable(3553);
                fr.drawString(squadron, -halfWidth, -8, 553648127);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                fr.drawString(squadron, -halfWidth, -8, -1);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glPopMatrix();
            }
            if (hiredIcon != null) {
                GL11.glTranslatef(0.0f, 0.5f, 0.0f);
                GL11.glScalef(-1.0f, -1.0f, 1.0f);
                final float itemScale = 0.03f;
                GL11.glScalef(itemScale, itemScale, itemScale);
                textureManager.bindTexture(TextureMap.locationItemsTexture);
                LOTRNPCRendering.itemRenderer.renderIcon(-8, -8, hiredIcon.getIconIndex(), 16, 16);
            }
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
        world.theProfiler.endSection();
    }
    
    public static void renderNPCHealthBar(final EntityLivingBase entity, final double d, final double d1, final double d2) {
        if (!LOTRConfig.hiredUnitHealthBars) {
            return;
        }
        if (((Entity)entity).riddenByEntity instanceof LOTREntityNPC) {
            return;
        }
        if (entity instanceof LOTREntityNPC && LOTRSpeechClient.hasSpeech((LOTREntityNPC)entity)) {
            return;
        }
        renderHealthBar(entity, d, d1, d2, new int[] { 5888860, 12006707 }, new int[] { 6079225, 12006707 });
    }
    
    public static void renderHealthBar(final EntityLivingBase entity, final double d, final double d1, final double d2, final int[] colors, final int[] mountColors) {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        world.theProfiler.startSection("renderHealthBar");
        final RenderManager renderManager = RenderManager.instance;
        final double distance = RendererLivingEntity.NAME_TAG_RANGE;
        final double distanceSq = entity.getDistanceSqToEntity((Entity)renderManager.livingPlayer);
        if (distanceSq <= distance * distance) {
            final float f1 = 1.6f;
            final float f2 = 0.016666666f * f1;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1 + ((Entity)entity).height + 0.7f, (float)d2);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-f2, -f2, f2);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            final Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(3553);
            final int colorHealth = colors[0];
            final int colorBase = colors[1];
            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_I(0);
            tessellator.addVertex(-19.5, 18.5, 0.0);
            tessellator.addVertex(-19.5, 21.0, 0.0);
            tessellator.addVertex(19.5, 21.0, 0.0);
            tessellator.addVertex(19.5, 18.5, 0.0);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_I(colorBase);
            tessellator.addVertex(-19.0, 19.0, 0.0);
            tessellator.addVertex(-19.0, 20.5, 0.0);
            tessellator.addVertex(19.0, 20.5, 0.0);
            tessellator.addVertex(19.0, 19.0, 0.0);
            tessellator.draw();
            double healthRemaining = entity.getHealth() / entity.getMaxHealth();
            if (healthRemaining < 0.0) {
                healthRemaining = 0.0;
            }
            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_I(colorHealth);
            tessellator.addVertex(-19.0, 19.0, 0.0);
            tessellator.addVertex(-19.0, 20.5, 0.0);
            tessellator.addVertex(-19.0 + 38.0 * healthRemaining, 20.5, 0.0);
            tessellator.addVertex(-19.0 + 38.0 * healthRemaining, 19.0, 0.0);
            tessellator.draw();
            if (mountColors != null && ((Entity)entity).ridingEntity instanceof EntityLivingBase) {
                final EntityLivingBase mount = (EntityLivingBase)((Entity)entity).ridingEntity;
                final int mountColorHealth = mountColors[0];
                final int mountColorBase = mountColors[1];
                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(0);
                tessellator.addVertex(-19.5, 23.5, 0.0);
                tessellator.addVertex(-19.5, 26.0, 0.0);
                tessellator.addVertex(19.5, 26.0, 0.0);
                tessellator.addVertex(19.5, 23.5, 0.0);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(mountColorBase);
                tessellator.addVertex(-19.0, 24.0, 0.0);
                tessellator.addVertex(-19.0, 25.5, 0.0);
                tessellator.addVertex(19.0, 25.5, 0.0);
                tessellator.addVertex(19.0, 24.0, 0.0);
                tessellator.draw();
                double mountHealthRemaining = mount.getHealth() / mount.getMaxHealth();
                if (mountHealthRemaining < 0.0) {
                    mountHealthRemaining = 0.0;
                }
                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(mountColorHealth);
                tessellator.addVertex(-19.0, 24.0, 0.0);
                tessellator.addVertex(-19.0, 25.5, 0.0);
                tessellator.addVertex(-19.0 + 38.0 * mountHealthRemaining, 25.5, 0.0);
                tessellator.addVertex(-19.0 + 38.0 * mountHealthRemaining, 24.0, 0.0);
                tessellator.draw();
            }
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
        world.theProfiler.endSection();
    }
    
    public static void renderQuestBook(final LOTREntityNPC npc, final double d, final double d1, final double d2) {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        world.theProfiler.startSection("renderMiniquestBook");
        final float distance = mc.renderViewEntity.getDistanceToEntity((Entity)npc);
        final boolean aboveHead = distance <= LOTRMiniQuest.RENDER_HEAD_DISTANCE;
        final TextureManager textureManager = mc.getTextureManager();
        final RenderManager renderManager = RenderManager.instance;
        final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
        if (!LOTRLevelData.getData(entityplayer).getMiniQuestsForEntity(npc, true).isEmpty() && !LOTRSpeechClient.hasSpeech(npc)) {
            final ItemStack questBook = new ItemStack(LOTRMod.redBook);
            IIcon icon = questBook.getIconIndex();
            if (icon == null) {
                icon = (IIcon)((TextureMap)textureManager.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite("missingno");
            }
            final Tessellator tessellator = Tessellator.instance;
            final float minU = icon.getMinU();
            final float maxU = icon.getMaxU();
            final float minV = icon.getMinV();
            final float maxV = icon.getMaxV();
            if (aboveHead) {
                final float age = ((Entity)npc).ticksExisted + LOTRTickHandlerClient.renderTick;
                float rotation = age % 360.0f;
                rotation *= 6.0f;
                GL11.glPushMatrix();
                GL11.glEnable(32826);
                GL11.glDisable(2896);
                GL11.glTranslatef((float)d, (float)d1 + ((Entity)npc).height + 1.3f, (float)d2);
                final float scale = 1.0f;
                GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(-0.5f * scale, -0.5f * scale, 0.03125f * scale);
                GL11.glScalef(scale, scale, scale);
                textureManager.bindTexture(TextureMap.locationItemsTexture);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
                GL11.glEnable(2896);
                GL11.glDisable(32826);
                GL11.glPopMatrix();
            }
            else {
                float scale2 = distance / (float)LOTRMiniQuest.RENDER_HEAD_DISTANCE;
                scale2 = (float)Math.pow(scale2, 1.1);
                final float alpha = (float)Math.pow(scale2, -0.4);
                GL11.glPushMatrix();
                GL11.glTranslatef((float)d, (float)d1 + ((Entity)npc).height + 1.3f, (float)d2);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                GL11.glScalef(scale2, scale2, scale2);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                textureManager.bindTexture(TextureMap.locationItemsTexture);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                GL11.glScalef(-1.0f, -1.0f, 1.0f);
                final float itemScale = 0.0625f;
                GL11.glScalef(itemScale, itemScale, itemScale);
                textureManager.bindTexture(TextureMap.locationItemsTexture);
                LOTRNPCRendering.itemRenderer.renderIcon(-8, -8, icon, 16, 16);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glEnable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
        world.theProfiler.endSection();
    }
    
    public static void renderQuestOffer(final LOTREntityNPC npc, final double d, final double d1, final double d2) {
        final Minecraft mc = Minecraft.getMinecraft();
        final World world = (World)mc.theWorld;
        world.theProfiler.startSection("renderMiniquestoffer");
        if (npc.isEntityAlive() && npc.questInfo.clientIsOffering && !LOTRSpeechClient.hasSpeech(npc)) {
            final EntityPlayer entityplayer = (EntityPlayer)mc.thePlayer;
            final float distance = mc.renderViewEntity.getDistanceToEntity((Entity)npc);
            if (distance <= 16.0 && LOTRLevelData.getData(entityplayer).getMiniQuestsForEntity(npc, true).isEmpty()) {
                final TextureManager textureManager = mc.getTextureManager();
                final RenderManager renderManager = RenderManager.instance;
                final IIcon icon = LOTRItemRedBook.questOfferIcon;
                final Tessellator tessellator = Tessellator.instance;
                final float minU = icon.getMinU();
                final float maxU = icon.getMaxU();
                final float minV = icon.getMinV();
                final float maxV = icon.getMaxV();
                final float scale = 0.75f;
                final float alpha = 1.0f;
                final int questColor = npc.questInfo.clientOfferColor;
                final float[] questRGB = new Color(questColor).getColorComponents(null);
                GL11.glPushMatrix();
                GL11.glTranslatef((float)d, (float)d1 + ((Entity)npc).height + 1.0f, (float)d2);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                GL11.glScalef(scale, scale, scale);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                textureManager.bindTexture(TextureMap.locationItemsTexture);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                GL11.glScalef(-1.0f, -1.0f, 1.0f);
                final float itemScale = 0.0625f;
                GL11.glScalef(itemScale, itemScale, itemScale);
                textureManager.bindTexture(TextureMap.locationItemsTexture);
                GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], alpha);
                LOTRNPCRendering.itemRenderer.renderIcon(-8, -8, icon, 16, 16);
                GL11.glDisable(3042);
                GL11.glEnable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
        world.theProfiler.endSection();
    }
    
    static {
        LOTRNPCRendering.itemRenderer = new RenderItem();
    }
}
