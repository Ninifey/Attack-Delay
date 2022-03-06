// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketCoinExchange;
import net.minecraft.inventory.Slot;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiButton;
import lotr.common.inventory.LOTRContainerCoinExchange;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiCoinExchange extends GuiContainer
{
    public static ResourceLocation guiTexture;
    private LOTRContainerCoinExchange theContainer;
    private GuiButton buttonLeft;
    private GuiButton buttonRight;
    
    public LOTRGuiCoinExchange(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        super((Container)new LOTRContainerCoinExchange(entityplayer, npc));
        this.theContainer = (LOTRContainerCoinExchange)super.field_147002_h;
        super.field_147000_g = 188;
    }
    
    public void initGui() {
        super.initGui();
        final int i = super.field_147003_i + super.field_146999_f / 2;
        final int j = 28;
        final int k = 16;
        ((GuiScreen)this).buttonList.add(this.buttonLeft = new LOTRGuiButtonCoinExchange(0, i - j - k, super.field_147009_r + 45));
        ((GuiScreen)this).buttonList.add(this.buttonRight = new LOTRGuiButtonCoinExchange(1, i + j - k, super.field_147009_r + 45));
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.buttonLeft.enabled = (!this.theContainer.exchanged && this.theContainer.exchangeInv.getStackInSlot(0) != null);
        this.buttonRight.enabled = (!this.theContainer.exchanged && this.theContainer.exchangeInv.getStackInSlot(1) != null);
        super.drawScreen(i, j, f);
    }
    
    protected void func_146979_b(final int i, final int j) {
        this.drawCenteredString(StatCollector.translateToLocal("container.lotr.coinExchange"), 89, 11, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 94, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiCoinExchange.guiTexture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        if (this.theContainer.exchanged) {
            for (int l = 0; l < this.theContainer.exchangeInv.getSizeInventory(); ++l) {
                final Slot slot = this.theContainer.getSlotFromInventory(this.theContainer.exchangeInv, l);
                if (slot.getHasStack()) {
                    this.drawTexturedModalRect(super.field_147003_i + slot.xDisplayPosition - 5, super.field_147009_r + slot.yDisplayPosition - 5, 176, 51, 26, 26);
                }
            }
        }
    }
    
    private void drawCenteredString(final String s, final int i, final int j, final int k) {
        ((GuiScreen)this).fontRendererObj.drawString(s, i - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, j, k);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonLeft || button == this.buttonRight) {
                final LOTRPacketCoinExchange packet = new LOTRPacketCoinExchange(button.id);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
    
    static {
        LOTRGuiCoinExchange.guiTexture = new ResourceLocation("lotr:gui/coin_exchange.png");
    }
}
