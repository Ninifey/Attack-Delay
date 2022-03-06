// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSell;
import lotr.common.entity.npc.LOTRTradeSellResult;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import lotr.common.entity.npc.LOTRTradeEntry;
import lotr.common.entity.npc.LOTRTradeEntries;
import org.lwjgl.opengl.GL11;
import lotr.common.inventory.LOTRSlotTrade;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRTradeable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.gui.GuiButton;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiTrade extends GuiContainer
{
    public static final ResourceLocation guiTexture;
    private static int lockedTradeColor;
    public LOTREntityNPC theEntity;
    private LOTRContainerTrade containerTrade;
    private GuiButton buttonSell;
    private static int sellQueryX;
    private static int sellQueryY;
    private static int sellQueryWidth;
    
    public LOTRGuiTrade(final InventoryPlayer inv, final LOTRTradeable trader, final World world) {
        super((Container)new LOTRContainerTrade(inv, trader, world));
        this.containerTrade = (LOTRContainerTrade)super.field_147002_h;
        this.theEntity = (LOTREntityNPC)trader;
        super.field_147000_g = 270;
    }
    
    public void initGui() {
        super.initGui();
        this.buttonSell = new LOTRGuiTradeButton(0, super.field_147003_i + 79, super.field_147009_r + 164);
        this.buttonSell.enabled = false;
        ((GuiScreen)this).buttonList.add(this.buttonSell);
    }
    
    protected void func_146979_b(final int i, final int j) {
        this.drawCenteredString(this.theEntity.getNPCName(), 89, 11, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.buy"), 8, 28, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.sell"), 8, 79, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.sellOffer"), 8, 129, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 176, 4210752);
        for (int l = 0; l < this.containerTrade.tradeInvBuy.getSizeInventory(); ++l) {
            final LOTRSlotTrade slotBuy = (LOTRSlotTrade)this.containerTrade.getSlotFromInventory(this.containerTrade.tradeInvBuy, l);
            final LOTRTradeEntry trade = slotBuy.getTrade();
            if (trade != null) {
                if (trade.isAvailable()) {
                    final int cost = slotBuy.cost();
                    if (cost > 0) {
                        this.renderCost(Integer.toString(cost), slotBuy.xDisplayPosition + 8, slotBuy.yDisplayPosition + 22);
                    }
                }
                else {
                    GL11.glTranslatef(0.0f, 0.0f, 200.0f);
                    final int x = slotBuy.xDisplayPosition;
                    final int y = slotBuy.yDisplayPosition;
                    drawRect(x, y, x + 16, y + 16, LOTRGuiTrade.lockedTradeColor);
                    GL11.glTranslatef(0.0f, 0.0f, -200.0f);
                    this.drawCenteredString(StatCollector.translateToLocal("container.lotr.trade.locked"), slotBuy.xDisplayPosition + 8, slotBuy.yDisplayPosition + 22, 4210752);
                }
            }
        }
        for (int l = 0; l < this.containerTrade.tradeInvSell.getSizeInventory(); ++l) {
            final LOTRSlotTrade slotSell = (LOTRSlotTrade)this.containerTrade.getSlotFromInventory(this.containerTrade.tradeInvSell, l);
            final LOTRTradeEntry trade = slotSell.getTrade();
            if (trade != null) {
                if (trade.isAvailable()) {
                    final int cost = slotSell.cost();
                    if (cost > 0) {
                        this.renderCost(Integer.toString(cost), slotSell.xDisplayPosition + 8, slotSell.yDisplayPosition + 22);
                    }
                }
                else {
                    GL11.glTranslatef(0.0f, 0.0f, 200.0f);
                    final int x = slotSell.xDisplayPosition;
                    final int y = slotSell.yDisplayPosition;
                    drawRect(x, y, x + 16, y + 16, LOTRGuiTrade.lockedTradeColor);
                    GL11.glTranslatef(0.0f, 0.0f, -200.0f);
                    this.drawCenteredString(StatCollector.translateToLocal("container.lotr.trade.locked"), slotSell.xDisplayPosition + 8, slotSell.yDisplayPosition + 22, 4210752);
                }
            }
        }
        int totalSellPrice = 0;
        for (int k = 0; k < this.containerTrade.tradeInvSellOffer.getSizeInventory(); ++k) {
            final Slot slotSell2 = this.containerTrade.getSlotFromInventory(this.containerTrade.tradeInvSellOffer, k);
            final ItemStack item = slotSell2.getStack();
            if (item != null) {
                final LOTRTradeSellResult sellResult = LOTRTradeEntries.getItemSellResult(item, this.theEntity);
                if (sellResult != null) {
                    totalSellPrice += sellResult.totalSellValue;
                }
            }
        }
        if (totalSellPrice > 0) {
            ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocalFormatted("container.lotr.trade.sellPrice", new Object[] { totalSellPrice }), 100, 169, 4210752);
        }
        this.buttonSell.enabled = (totalSellPrice > 0);
    }
    
    private void renderCost(final String s, int x, int y) {
        final int l = ((GuiScreen)this).fontRendererObj.getStringWidth(s);
        final boolean halfSize = l > 15;
        if (halfSize) {
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 1.0f);
            x *= 2;
            y *= 2;
            y += ((GuiScreen)this).fontRendererObj.FONT_HEIGHT / 2;
        }
        this.drawCenteredString(s, x, y, 4210752);
        if (halfSize) {
            GL11.glPopMatrix();
        }
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiTrade.guiTexture);
        func_146110_a(super.field_147003_i, super.field_147009_r, 0.0f, 0.0f, super.field_146999_f, super.field_147000_g, 512.0f, 512.0f);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled && button == this.buttonSell) {
            final LOTRPacketSell packet = new LOTRPacketSell();
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    private void drawCenteredString(final String s, final int i, final int j, final int k) {
        ((GuiScreen)this).fontRendererObj.drawString(s, i - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, j, k);
    }
    
    static {
        guiTexture = new ResourceLocation("lotr:gui/npc/trade.png");
        LOTRGuiTrade.lockedTradeColor = -1610612736;
        LOTRGuiTrade.sellQueryWidth = 12;
    }
}
