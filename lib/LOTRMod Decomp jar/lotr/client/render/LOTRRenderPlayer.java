// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render;

import net.minecraft.world.World;
import java.util.Iterator;
import java.util.List;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.client.gui.FontRenderer;
import lotr.common.LOTRPlayerData;
import lotr.client.render.entity.LOTRNPCRendering;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.util.MathHelper;
import lotr.client.LOTRClientProxy;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import lotr.common.LOTRConfig;
import lotr.common.world.LOTRWorldProvider;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.common.LOTRShields;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRLevelData;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.Minecraft;

public class LOTRRenderPlayer
{
    private Minecraft mc;
    private RenderManager renderManager;
    
    public LOTRRenderPlayer() {
        this.mc = Minecraft.getMinecraft();
        this.renderManager = RenderManager.instance;
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void preRenderSpecials(final RenderPlayerEvent.Specials.Pre event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final float tick = event.partialRenderTick;
        final LOTRShields shield = LOTRLevelData.getData(entityplayer).getShield();
        if (shield != null) {
            if (!entityplayer.isInvisible()) {
                LOTRRenderShield.renderShield(shield, (EntityLivingBase)entityplayer, event.renderer.modelBipedMain);
            }
            else if (!entityplayer.isInvisibleToPlayer((EntityPlayer)this.mc.thePlayer)) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
                GL11.glDepthMask(false);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glAlphaFunc(516, 0.003921569f);
                LOTRRenderShield.renderShield(shield, (EntityLivingBase)entityplayer, event.renderer.modelBipedMain);
                GL11.glDisable(3042);
                GL11.glAlphaFunc(516, 0.1f);
                GL11.glPopMatrix();
                GL11.glDepthMask(true);
            }
        }
    }
    
    @SubscribeEvent
    public void postRender(final RenderPlayerEvent.Post event) {
        final EntityPlayer entityplayer = event.entityPlayer;
        final float tick = event.partialRenderTick;
        final double d0 = RenderManager.renderPosX;
        final double d2 = RenderManager.renderPosY;
        final double d3 = RenderManager.renderPosZ;
        final float f0 = (float)((Entity)entityplayer).lastTickPosX + (float)(((Entity)entityplayer).posX - ((Entity)entityplayer).lastTickPosX) * tick;
        final float f2 = (float)((Entity)entityplayer).lastTickPosY + (float)(((Entity)entityplayer).posY - ((Entity)entityplayer).lastTickPosY) * tick;
        final float f3 = (float)((Entity)entityplayer).lastTickPosZ + (float)(((Entity)entityplayer).posZ - ((Entity)entityplayer).lastTickPosZ) * tick;
        final float fr0 = f0 - (float)d0;
        final float fr2 = f2 - (float)d2;
        final float fr3 = f3 - (float)d3;
        final float yOffset = entityplayer.isPlayerSleeping() ? -1.5f : 0.0f;
        if (this.shouldRenderAlignment(entityplayer) && (((World)this.mc.theWorld).provider instanceof LOTRWorldProvider || LOTRConfig.alwaysShowAlignment)) {
            final LOTRPlayerData clientPD = LOTRLevelData.getData((EntityPlayer)this.mc.thePlayer);
            final LOTRPlayerData otherPD = LOTRLevelData.getData(entityplayer);
            final float alignment = otherPD.getAlignment(clientPD.getViewingFaction());
            final double dist = entityplayer.getDistanceSqToEntity((Entity)this.renderManager.livingPlayer);
            final float range = RendererLivingEntity.NAME_TAG_RANGE;
            if (dist < range * range) {
                final FontRenderer fr4 = Minecraft.getMinecraft().fontRenderer;
                GL11.glPushMatrix();
                GL11.glTranslatef(fr0, fr2, fr3);
                GL11.glTranslatef(0.0f, ((Entity)entityplayer).height + 0.6f + yOffset, 0.0f);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                GL11.glScalef(-1.0f, -1.0f, 1.0f);
                final float scale = 0.025f;
                GL11.glScalef(scale, scale, scale);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final String sAlign = LOTRAlignmentValues.formatAlignForDisplay(alignment);
                this.mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
                LOTRTickHandlerClient.drawTexturedModalRect(-MathHelper.floor_double((fr4.getStringWidth(sAlign) + 18) / 2.0), -19.0, 0, 36, 16, 16);
                LOTRTickHandlerClient.drawAlignmentText(fr4, 18 - MathHelper.floor_double((fr4.getStringWidth(sAlign) + 18) / 2.0), -12, sAlign, 1.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glEnable(2896);
                GL11.glDisable(32826);
                GL11.glPopMatrix();
            }
        }
        if (this.shouldRenderFellowPlayerHealth(entityplayer)) {
            LOTRNPCRendering.renderHealthBar((EntityLivingBase)entityplayer, fr0, fr2, fr3, new int[] { 16375808, 12006707 }, null);
        }
    }
    
    private boolean shouldRenderPlayerHUD(final EntityPlayer entityplayer) {
        return Minecraft.isGuiEnabled() && entityplayer != this.renderManager.livingPlayer && !entityplayer.isSneaking() && !entityplayer.isInvisibleToPlayer((EntityPlayer)this.mc.thePlayer);
    }
    
    private boolean shouldRenderAlignment(final EntityPlayer entityplayer) {
        if (!LOTRConfig.displayAlignmentAboveHead || !this.shouldRenderPlayerHUD(entityplayer)) {
            return false;
        }
        if (LOTRLevelData.getData(entityplayer).getHideAlignment()) {
            final String playerName = entityplayer.getCommandSenderName();
            final List<LOTRFellowshipClient> fellowships = LOTRLevelData.getData((EntityPlayer)this.mc.thePlayer).getClientFellowships();
            for (final LOTRFellowshipClient fs : fellowships) {
                if (fs.isPlayerIn(playerName)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    private boolean shouldRenderFellowPlayerHealth(final EntityPlayer entityplayer) {
        if (LOTRConfig.fellowPlayerHealthBars && this.shouldRenderPlayerHUD(entityplayer)) {
            final List<LOTRFellowshipClient> fellowships = LOTRLevelData.getData((EntityPlayer)this.mc.thePlayer).getClientFellowships();
            for (final LOTRFellowshipClient fs : fellowships) {
                if (fs.isPlayerIn(entityplayer.getCommandSenderName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
