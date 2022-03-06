// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.Gui;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketBuyUnit;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import java.util.List;
import net.minecraft.inventory.Slot;
import lotr.common.LOTRLevelData;
import lotr.common.inventory.LOTRSlotAlignmentReward;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.client.LOTRClientProxy;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.LOTRSquadrons;
import net.minecraft.inventory.Container;
import lotr.common.inventory.LOTRContainerUnitTrade;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRUnitTradeEntries;
import lotr.common.fac.LOTRFaction;
import lotr.common.entity.npc.LOTRHireableBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public abstract class LOTRGuiHireBase extends GuiContainer
{
    private static ResourceLocation guiTexture;
    private LOTRHireableBase theUnitTrader;
    private LOTRFaction traderFaction;
    private LOTRUnitTradeEntries trades;
    private int currentTradeEntryIndex;
    private LOTREntityNPC currentDisplayedMob;
    private EntityLiving currentDisplayedMount;
    private float screenXSize;
    private float screenYSize;
    private LOTRGuiUnitTradeButton buttonHire;
    private LOTRGuiUnitTradeButton buttonLeftUnit;
    private LOTRGuiUnitTradeButton buttonRightUnit;
    private GuiTextField squadronNameField;
    private static final int extraInfoX = 49;
    private static final int extraInfoY = 106;
    private static final int extraInfoWidth = 9;
    private static final int extraInfoHeight = 7;
    
    public LOTRGuiHireBase(final EntityPlayer entityplayer, final LOTRHireableBase trader, final World world) {
        super((Container)new LOTRContainerUnitTrade(entityplayer, trader, world));
        super.field_146999_f = 220;
        super.field_147000_g = 256;
        this.theUnitTrader = trader;
        this.traderFaction = trader.getFaction();
    }
    
    protected void setTrades(final LOTRUnitTradeEntries t) {
        this.trades = t;
    }
    
    public void initGui() {
        super.initGui();
        ((GuiScreen)this).buttonList.add(this.buttonLeftUnit = new LOTRGuiUnitTradeButton(0, super.field_147003_i + 90, super.field_147009_r + 144, 12, 19));
        this.buttonLeftUnit.enabled = false;
        ((GuiScreen)this).buttonList.add(this.buttonHire = new LOTRGuiUnitTradeButton(1, super.field_147003_i + 102, super.field_147009_r + 144, 16, 19));
        ((GuiScreen)this).buttonList.add(this.buttonRightUnit = new LOTRGuiUnitTradeButton(2, super.field_147003_i + 118, super.field_147009_r + 144, 12, 19));
        (this.squadronNameField = new GuiTextField(((GuiScreen)this).fontRendererObj, super.field_147003_i + super.field_146999_f / 2 - 80, super.field_147009_r + 120, 160, 20)).func_146203_f(LOTRSquadrons.SQUADRON_LENGTH_MAX);
    }
    
    private LOTRUnitTradeEntry currentTrade() {
        return this.trades.tradeEntries[this.currentTradeEntryIndex];
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.buttonLeftUnit.enabled = (this.currentTradeEntryIndex > 0);
        this.buttonHire.enabled = this.currentTrade().hasRequiredCostAndAlignment((EntityPlayer)((GuiScreen)this).mc.thePlayer, this.theUnitTrader);
        this.buttonRightUnit.enabled = (this.currentTradeEntryIndex < this.trades.tradeEntries.length - 1);
        super.drawScreen(i, j, f);
        this.screenXSize = (float)i;
        this.screenYSize = (float)j;
    }
    
    public void updateScreen() {
        super.updateScreen();
        this.squadronNameField.updateCursorCounter();
    }
    
    protected void func_146979_b(final int i, final int j) {
        final LOTRUnitTradeEntry curTrade = this.currentTrade();
        this.drawCenteredString(this.theUnitTrader.getNPCName(), 110, 11, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 30, 162, 4210752);
        this.drawCenteredString(curTrade.getUnitTradeName(), 138, 50, 4210752);
        final int reqX = 64;
        final int reqXText = reqX + 19;
        int reqY = 65;
        final int reqYTextBelow = 4;
        final int reqGap = 18;
        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GuiScreen.itemRender.renderItemAndEffectIntoGUI(((GuiScreen)this).fontRendererObj, ((GuiScreen)this).mc.getTextureManager(), new ItemStack(LOTRMod.silverCoin), reqX, reqY);
        GL11.glDisable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int cost = curTrade.getCost((EntityPlayer)((GuiScreen)this).mc.thePlayer, this.theUnitTrader);
        ((GuiScreen)this).fontRendererObj.drawString(String.valueOf(cost), reqXText, reqY + reqYTextBelow, 4210752);
        reqY += reqGap;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
        this.drawTexturedModalRect(reqX, reqY, 0, 36, 16, 16);
        final float alignment = curTrade.alignmentRequired;
        final String alignS = LOTRAlignmentValues.formatAlignForDisplay(alignment);
        ((GuiScreen)this).fontRendererObj.drawString(alignS, reqXText, reqY + reqYTextBelow, 4210752);
        if (curTrade.getPledgeType() != LOTRUnitTradeEntry.PledgeType.NONE) {
            reqY += reqGap;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
            this.drawTexturedModalRect(reqX, reqY, 0, 212, 16, 16);
            final String pledge = StatCollector.translateToLocal("container.lotr.unitTrade.pledge");
            ((GuiScreen)this).fontRendererObj.drawString(pledge, reqXText, reqY + reqYTextBelow, 4210752);
            final int i2 = i - super.field_147003_i - reqX;
            final int j2 = j - super.field_147009_r - reqY;
            if (i2 >= 0 && i2 < 16 && j2 >= 0 && j2 < 16) {
                final String pledgeDesc = curTrade.getPledgeType().getCommandReqText(this.traderFaction);
                this.func_146279_a(pledgeDesc, i - super.field_147003_i, j - super.field_147009_r);
                GL11.glDisable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        if (((LOTRContainerUnitTrade)super.field_147002_h).alignmentRewardSlots > 0) {
            final Slot slot = super.field_147002_h.getSlot(0);
            final boolean hasRewardCost = slot.getHasStack();
            if (hasRewardCost) {
                GL11.glEnable(2896);
                GL11.glEnable(2884);
                GuiScreen.itemRender.renderItemAndEffectIntoGUI(((GuiScreen)this).fontRendererObj, ((GuiScreen)this).mc.getTextureManager(), new ItemStack(LOTRMod.silverCoin), 160, 100);
                GL11.glDisable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                cost = LOTRSlotAlignmentReward.REWARD_COST;
                ((GuiScreen)this).fontRendererObj.drawString(String.valueOf(cost), 179, 104, 4210752);
            }
            else if (!slot.getHasStack() && LOTRLevelData.getData((EntityPlayer)((GuiScreen)this).mc.thePlayer).getAlignment(this.traderFaction) < 1500.0f && this.func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, i, j)) {
                this.func_146279_a(StatCollector.translateToLocalFormatted("container.lotr.unitTrade.requiresAlignment", new Object[] { 1500.0f }), i - super.field_147003_i, j - super.field_147009_r);
                GL11.glDisable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        if (curTrade.hasExtraInfo()) {
            final String extraInfo = curTrade.getFormattedExtraInfo();
            final boolean mouseover = i >= super.field_147003_i + 49 && i < super.field_147003_i + 49 + 9 && j >= super.field_147009_r + 106 && j < super.field_147009_r + 106 + 7;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiHireBase.guiTexture);
            this.drawTexturedModalRect(49, 106, 220, 38 + (mouseover ? 1 : 0) * 7, 9, 7);
            if (mouseover) {
                final float z = ((Gui)this).zLevel;
                final int stringWidth = 200;
                final List desc = ((GuiScreen)this).fontRendererObj.listFormattedStringToWidth(extraInfo, stringWidth);
                this.func_146283_a(desc, i - super.field_147003_i, j - super.field_147009_r);
                GL11.glDisable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                ((Gui)this).zLevel = z;
            }
        }
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiHireBase.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        if (((LOTRContainerUnitTrade)super.field_147002_h).alignmentRewardSlots > 0) {
            final Slot slot = super.field_147002_h.getSlot(0);
            this.drawTexturedModalRect(super.field_147003_i + slot.xDisplayPosition - 3, super.field_147009_r + slot.yDisplayPosition - 3, super.field_146999_f, 16, 22, 22);
            if (!slot.getHasStack() && LOTRLevelData.getData((EntityPlayer)((GuiScreen)this).mc.thePlayer).getAlignment(this.traderFaction) < 1500.0f) {
                this.drawTexturedModalRect(super.field_147003_i + slot.xDisplayPosition, super.field_147009_r + slot.yDisplayPosition, super.field_146999_f, 0, 16, 16);
            }
        }
        this.drawMobOnGui(super.field_147003_i + 32, super.field_147009_r + 109, super.field_147003_i + 32 - this.screenXSize, super.field_147009_r + 109 - 50 - this.screenYSize);
        final boolean squadronPrompt = StringUtils.isNullOrEmpty(this.squadronNameField.getText()) && !this.squadronNameField.isFocused();
        if (squadronPrompt) {
            final String squadronMessage = StatCollector.translateToLocal("container.lotr.unitTrade.squadronBox");
            this.squadronNameField.setText(EnumChatFormatting.DARK_GRAY + squadronMessage);
        }
        this.squadronNameField.drawTextBox();
        if (squadronPrompt) {
            this.squadronNameField.setText("");
        }
    }
    
    private void drawMobOnGui(final int i, final int j, final float f, final float f1) {
        final Class entityClass = this.currentTrade().entityClass;
        final Class mountClass = this.currentTrade().mountClass;
        if (this.currentDisplayedMob == null || this.currentDisplayedMob.getClass() != entityClass || (mountClass == null && this.currentDisplayedMount != null) || (mountClass != null && (this.currentDisplayedMount == null || this.currentDisplayedMount.getClass() != mountClass))) {
            this.currentDisplayedMob = this.currentTrade().getOrCreateHiredNPC((World)((GuiScreen)this).mc.theWorld);
            if (mountClass != null) {
                final EntityLiving mount = this.currentTrade().createHiredMount((World)((GuiScreen)this).mc.theWorld);
                this.currentDisplayedMount = mount;
                this.currentDisplayedMob.mountEntity((Entity)this.currentDisplayedMount);
            }
            else {
                this.currentDisplayedMount = null;
            }
        }
        float size = ((Entity)this.currentDisplayedMob).width * ((Entity)this.currentDisplayedMob).height * ((Entity)this.currentDisplayedMob).width;
        if (this.currentDisplayedMount != null) {
            size += ((Entity)this.currentDisplayedMount).width * ((Entity)this.currentDisplayedMount).height * ((Entity)this.currentDisplayedMount).width * 0.5f;
        }
        final float scale = MathHelper.sqrt_float(MathHelper.sqrt_float(1.0f / size)) * 30.0f;
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)i, (float)j, 50.0f);
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-(float)Math.atan(f1 / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        ((EntityLivingBase)this.currentDisplayedMob).renderYawOffset = (float)Math.atan(f / 40.0f) * 20.0f;
        ((Entity)this.currentDisplayedMob).rotationYaw = (float)Math.atan(f / 40.0f) * 40.0f;
        ((Entity)this.currentDisplayedMob).rotationPitch = -(float)Math.atan(f1 / 40.0f) * 20.0f;
        ((EntityLivingBase)this.currentDisplayedMob).rotationYawHead = ((Entity)this.currentDisplayedMob).rotationYaw;
        GL11.glTranslatef(0.0f, ((Entity)this.currentDisplayedMob).yOffset, 0.0f);
        if (this.currentDisplayedMount != null) {
            GL11.glTranslatef(0.0f, (float)this.currentDisplayedMount.getMountedYOffset(), 0.0f);
        }
        RenderManager.instance.playerViewY = 180.0f;
        RenderManager.instance.func_147940_a((Entity)this.currentDisplayedMob, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (this.currentDisplayedMount != null) {
            GL11.glEnable(2903);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)i, (float)j, 50.0f);
            GL11.glScalef(-scale, scale, scale);
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
            RenderHelper.enableStandardItemLighting();
            GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-(float)Math.atan(f1 / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
            ((EntityLivingBase)this.currentDisplayedMount).renderYawOffset = (float)Math.atan(f / 40.0f) * 20.0f;
            ((Entity)this.currentDisplayedMount).rotationYaw = (float)Math.atan(f / 40.0f) * 40.0f;
            ((Entity)this.currentDisplayedMount).rotationPitch = -(float)Math.atan(f1 / 40.0f) * 20.0f;
            ((EntityLivingBase)this.currentDisplayedMount).rotationYawHead = ((Entity)this.currentDisplayedMount).rotationYaw;
            GL11.glTranslatef(0.0f, ((Entity)this.currentDisplayedMount).yOffset, 0.0f);
            RenderManager.instance.playerViewY = 180.0f;
            RenderManager.instance.func_147940_a((Entity)this.currentDisplayedMount, 0.0, 0.0, 0.0, 0.0f, 1.0f);
            GL11.glPopMatrix();
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(32826);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(3553);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        }
    }
    
    protected void keyTyped(final char c, final int i) {
        if (this.squadronNameField.func_146176_q() && this.squadronNameField.textboxKeyTyped(c, i)) {
            return;
        }
        super.keyTyped(c, i);
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        this.squadronNameField.mouseClicked(i, j, k);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonLeftUnit) {
                if (this.currentTradeEntryIndex > 0) {
                    --this.currentTradeEntryIndex;
                }
            }
            else if (button == this.buttonHire) {
                final String squadron = this.squadronNameField.getText();
                final LOTRPacketBuyUnit packet = new LOTRPacketBuyUnit(this.currentTradeEntryIndex, squadron);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
            else if (button == this.buttonRightUnit && this.currentTradeEntryIndex < this.trades.tradeEntries.length - 1) {
                ++this.currentTradeEntryIndex;
            }
        }
    }
    
    private void drawCenteredString(final String s, final int i, final int j, final int k) {
        ((GuiScreen)this).fontRendererObj.drawString(s, i - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, j, k);
    }
    
    static {
        LOTRGuiHireBase.guiTexture = new ResourceLocation("lotr:gui/npc/unit_trade.png");
    }
}
