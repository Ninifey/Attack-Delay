// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import lotr.common.LOTRLevelData;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import lotr.client.LOTRTickHandlerClient;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import lotr.common.LOTRConfig;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.Gui;

public class LOTRGuiMiniquestTracker extends Gui
{
    private static ResourceLocation guiTexture;
    private static RenderItem renderItem;
    private int width;
    private int height;
    private int barX;
    private int barY;
    private int barWidth;
    private int barHeight;
    private int barEdge;
    private int iconWidth;
    private int iconHeight;
    private int gap;
    private LOTRMiniQuest trackedQuest;
    private boolean holdingComplete;
    private int completeTime;
    private static final int completeTimeMax = 200;
    
    public LOTRGuiMiniquestTracker() {
        this.barX = 16;
        this.barY = 10;
        this.barWidth = 90;
        this.barHeight = 15;
        this.barEdge = 2;
        this.iconWidth = 20;
        this.iconHeight = 20;
        this.gap = 4;
    }
    
    public void drawTracker(final Minecraft mc, final EntityPlayer entityplayer) {
        final ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        this.width = resolution.getScaledWidth();
        this.height = resolution.getScaledHeight();
        final FontRenderer fr = mc.fontRenderer;
        final boolean flip = LOTRConfig.trackingQuestRight;
        if (entityplayer != null && this.trackedQuest != null) {
            final float[] questRGB = this.trackedQuest.getQuestColorComponents();
            final ItemStack itemstack = this.trackedQuest.getQuestIcon();
            String objective = this.trackedQuest.getQuestObjective();
            final String progress = this.trackedQuest.getQuestProgressShorthand();
            final float completion = this.trackedQuest.getCompletionFactor();
            final boolean failed = this.trackedQuest.isFailed();
            final boolean complete = this.trackedQuest.isCompleted();
            if (failed) {
                objective = this.trackedQuest.getQuestFailureShorthand();
            }
            else if (complete) {
                objective = StatCollector.translateToLocal("lotr.gui.redBook.mq.diary.complete");
            }
            int x = this.barX;
            int y = this.barY;
            if (flip) {
                x = this.width - this.barX - this.iconWidth;
            }
            GL11.glEnable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            mc.getTextureManager().bindTexture(LOTRGuiMiniquestTracker.guiTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x, y, 0, 0, this.iconWidth, this.iconHeight);
            final int iconX = x + (this.iconWidth - 16) / 2;
            final int iconY = y + (this.iconHeight - 16) / 2;
            if (flip) {
                x -= this.barWidth + this.gap;
            }
            else {
                x += this.iconWidth + this.gap;
            }
            int meterWidth = this.barWidth - this.barEdge * 2;
            meterWidth = Math.round(meterWidth * completion);
            mc.getTextureManager().bindTexture(LOTRGuiMiniquestTracker.guiTexture);
            GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
            this.drawTexturedModalRect(x + this.barEdge, y, this.iconWidth + this.barEdge, this.barHeight, meterWidth, this.barHeight);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x, y, this.iconWidth, 0, this.barWidth, this.barHeight);
            LOTRTickHandlerClient.drawAlignmentText(fr, x + this.barWidth / 2 - fr.getStringWidth(progress) / 2, y + this.barHeight - this.barHeight / 2 - fr.FONT_HEIGHT / 2, progress, 1.0f);
            y += this.barHeight + this.gap;
            fr.drawSplitString(objective, x, y, this.barWidth, 16777215);
            GL11.glDisable(3042);
            GL11.glDisable(3008);
            if (itemstack != null) {
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glDisable(2896);
                GL11.glEnable(32826);
                GL11.glEnable(2896);
                GL11.glEnable(2884);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                LOTRGuiMiniquestTracker.renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), itemstack, iconX, iconY);
                GL11.glDisable(2896);
            }
        }
    }
    
    public void update(final Minecraft mc, final EntityPlayer entityplayer) {
        if (entityplayer == null) {
            this.trackedQuest = null;
        }
        else {
            if (this.trackedQuest != null && this.trackedQuest.isCompleted() && !this.holdingComplete) {
                this.completeTime = 200;
                this.holdingComplete = true;
            }
            final LOTRMiniQuest currentTrackedQuest = LOTRLevelData.getData(entityplayer).getTrackingMiniQuest();
            if (this.completeTime > 0 && currentTrackedQuest == null) {
                --this.completeTime;
            }
            else {
                this.trackedQuest = currentTrackedQuest;
                this.holdingComplete = false;
            }
        }
    }
    
    public void setTrackedQuest(final LOTRMiniQuest quest) {
        this.trackedQuest = quest;
    }
    
    static {
        LOTRGuiMiniquestTracker.guiTexture = new ResourceLocation("lotr:gui/quest/tracker.png");
        LOTRGuiMiniquestTracker.renderItem = new RenderItem();
    }
}
