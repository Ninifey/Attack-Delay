// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import lotr.client.LOTRClientProxy;
import net.minecraft.util.EnumChatFormatting;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import lotr.common.fac.LOTRFaction;
import net.minecraft.util.IChatComponent;
import lotr.common.LOTRAchievement;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.gui.Gui;

public class LOTRGuiNotificationDisplay extends Gui
{
    private static int guiXSize;
    private static int guiYSize;
    private static RenderItem itemRenderer;
    private Minecraft mc;
    private int windowWidth;
    private int windowHeight;
    private List<Notification> notifications;
    private Set<Notification> notificationsToRemove;
    
    public LOTRGuiNotificationDisplay() {
        this.notifications = new ArrayList<Notification>();
        this.notificationsToRemove = new HashSet<Notification>();
        this.mc = Minecraft.getMinecraft();
    }
    
    public void queueAchievement(final LOTRAchievement achievement) {
        this.notifications.add(new NotificationAchievement(achievement));
    }
    
    public void queueFellowshipNotification(final IChatComponent message) {
        this.notifications.add(new NotificationFellowship(message));
    }
    
    public void queueConquest(final LOTRFaction fac, final float conq, final boolean cleansing) {
        this.notifications.add(new NotificationConquest(fac, conq, cleansing));
    }
    
    private void updateWindowScale() {
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        this.windowWidth = this.mc.displayWidth;
        this.windowHeight = this.mc.displayHeight;
        final ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.windowWidth = scaledresolution.getScaledWidth();
        this.windowHeight = scaledresolution.getScaledHeight();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)this.windowWidth, (double)this.windowHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }
    
    public void updateWindow() {
        if (!this.notifications.isEmpty()) {
            int index = 0;
            for (final Notification notif : this.notifications) {
                final long notifTime = notif.getNotificationTime();
                final double d0 = (Minecraft.getSystemTime() - notifTime) / (double)notif.getDurationMs();
                if (d0 < 0.0 || d0 > 1.0) {
                    this.notificationsToRemove.add(notif);
                }
                else {
                    this.updateWindowScale();
                    if (Minecraft.isGuiEnabled()) {
                        GL11.glEnable(3008);
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                        double d2 = d0 * 2.0;
                        if (d2 > 1.0) {
                            d2 = 2.0 - d2;
                        }
                        d2 *= 4.0;
                        d2 = 1.0 - d2;
                        if (d2 < 0.0) {
                            d2 = 0.0;
                        }
                        d2 *= d2;
                        d2 *= d2;
                        final int i = this.windowWidth - LOTRGuiNotificationDisplay.guiXSize;
                        int j = 0 - (int)(d2 * 36.0);
                        j += index * (LOTRGuiNotificationDisplay.guiYSize + 8);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glEnable(3553);
                        this.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
                        GL11.glDisable(2896);
                        this.drawTexturedModalRect(i, j, 0, 200, LOTRGuiNotificationDisplay.guiXSize, LOTRGuiNotificationDisplay.guiYSize);
                        notif.renderText(i + 30, j + 7);
                        GL11.glEnable(3008);
                        notif.renderIcon(i + 8, j + 8);
                    }
                }
                ++index;
            }
        }
        if (!this.notificationsToRemove.isEmpty()) {
            this.notifications.removeAll(this.notificationsToRemove);
        }
    }
    
    static {
        LOTRGuiNotificationDisplay.guiXSize = 190;
        LOTRGuiNotificationDisplay.guiYSize = 32;
        LOTRGuiNotificationDisplay.itemRenderer = new RenderItem();
    }
    
    private abstract class Notification
    {
        private Long notificationTime;
        
        private Notification() {
            this.notificationTime = Minecraft.getSystemTime();
        }
        
        public Long getNotificationTime() {
            return this.notificationTime;
        }
        
        public abstract int getDurationMs();
        
        public abstract void renderText(final int p0, final int p1);
        
        public abstract void renderIcon(final int p0, final int p1);
    }
    
    private class NotificationAchievement extends Notification
    {
        private LOTRAchievement achievement;
        
        public NotificationAchievement(final LOTRAchievement ach) {
            this.achievement = ach;
        }
        
        @Override
        public int getDurationMs() {
            return 3000;
        }
        
        @Override
        public void renderText(final int x, final int y) {
            LOTRGuiNotificationDisplay.this.mc.fontRenderer.drawString(StatCollector.translateToLocal("achievement.get"), x, y, 8019267);
            LOTRGuiNotificationDisplay.this.mc.fontRenderer.drawString(this.achievement.getTitle((EntityPlayer)LOTRGuiNotificationDisplay.this.mc.thePlayer), x, y + 11, 8019267);
        }
        
        @Override
        public void renderIcon(final int x, final int y) {
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2903);
            GL11.glEnable(2896);
            LOTRGuiNotificationDisplay.itemRenderer.renderItemAndEffectIntoGUI(LOTRGuiNotificationDisplay.this.mc.fontRenderer, LOTRGuiNotificationDisplay.this.mc.getTextureManager(), this.achievement.icon, x, y);
            GL11.glDisable(2896);
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3008);
            LOTRGuiNotificationDisplay.this.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            LOTRGuiNotificationDisplay.this.drawTexturedModalRect(x + 162, y + 1, 190, 17, 16, 16);
        }
    }
    
    private class NotificationFellowship extends Notification
    {
        private IChatComponent message;
        
        public NotificationFellowship(final IChatComponent msg) {
            this.message = msg;
        }
        
        @Override
        public int getDurationMs() {
            return 6000;
        }
        
        @Override
        public void renderText(final int x, final int y) {
            LOTRGuiNotificationDisplay.this.mc.fontRenderer.drawSplitString(this.message.getFormattedText(), x, y, 152, 8019267);
        }
        
        @Override
        public void renderIcon(final int x, final int y) {
            LOTRGuiNotificationDisplay.this.mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            LOTRGuiNotificationDisplay.this.drawTexturedModalRect(x, y, 80, 0, 16, 16);
        }
    }
    
    private class NotificationConquest extends Notification
    {
        private LOTRFaction conqFac;
        private float conqValue;
        private boolean isCleansing;
        
        public NotificationConquest(final LOTRFaction fac, final float conq, final boolean clean) {
            this.conqFac = fac;
            this.conqValue = conq;
            this.isCleansing = clean;
        }
        
        @Override
        public int getDurationMs() {
            return 6000;
        }
        
        @Override
        public void renderText(final int x, final int y) {
            final String conqS = LOTRAlignmentValues.formatConqForDisplay(this.conqValue, false);
            LOTRTickHandlerClient.drawConquestText(LOTRGuiNotificationDisplay.this.mc.fontRenderer, x + 1, y, conqS, this.isCleansing, 1.0f);
            LOTRGuiNotificationDisplay.this.mc.fontRenderer.drawString(StatCollector.translateToLocal("lotr.gui.map.conquest.notif"), x + LOTRGuiNotificationDisplay.this.mc.fontRenderer.getStringWidth(conqS + " ") + 2, y, 8019267);
            LOTRGuiNotificationDisplay.this.mc.fontRenderer.drawString(EnumChatFormatting.ITALIC + this.conqFac.factionName(), x, y + 11, 9666921);
        }
        
        @Override
        public void renderIcon(final int x, final int y) {
            LOTRGuiNotificationDisplay.this.mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            LOTRGuiNotificationDisplay.this.drawTexturedModalRect(x, y, this.isCleansing ? 16 : 0, 228, 16, 16);
        }
    }
}
