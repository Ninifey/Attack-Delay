// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketItemSquadron;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StringUtils;
import lotr.common.LOTRSquadrons;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiSquadronItem extends LOTRGuiScreenBase
{
    private static final ResourceLocation guiTexture;
    private static final RenderItem itemRenderer;
    private int xSize;
    private int ySize;
    private int guiLeft;
    private int guiTop;
    private GuiButton buttonDone;
    private GuiTextField squadronNameField;
    private ItemStack theItem;
    private boolean sendSquadronUpdate;
    
    public LOTRGuiSquadronItem() {
        this.xSize = 200;
        this.ySize = 120;
        this.sendSquadronUpdate = false;
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        super.buttonList.add(this.buttonDone = new GuiButton(1, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 85, 80, 20, StatCollector.translateToLocal("lotr.gui.squadronItem.done")));
        final ItemStack itemstack = ((EntityPlayer)super.mc.thePlayer).inventory.getCurrentItem();
        if (itemstack != null && itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
            this.theItem = itemstack;
            (this.squadronNameField = new GuiTextField(super.fontRendererObj, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 50, 160, 20)).func_146203_f(LOTRSquadrons.SQUADRON_LENGTH_MAX);
            final String squadron = LOTRSquadrons.getSquadron(this.theItem);
            if (!StringUtils.isNullOrEmpty(squadron)) {
                this.squadronNameField.setText(squadron);
            }
        }
        if (this.theItem == null) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.mc.getTextureManager().bindTexture(LOTRGuiSquadronItem.guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String s = this.theItem.getDisplayName();
        super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 4210752);
        s = StatCollector.translateToLocal("lotr.gui.squadronItem.squadron");
        super.fontRendererObj.drawString(s, this.squadronNameField.field_146209_f, this.squadronNameField.field_146210_g - super.fontRendererObj.FONT_HEIGHT - 3, 4210752);
        final boolean noSquadron = StringUtils.isNullOrEmpty(this.squadronNameField.getText()) && !this.squadronNameField.isFocused();
        if (noSquadron) {
            final String squadronMessage = StatCollector.translateToLocal("lotr.gui.squadronItem.none");
            this.squadronNameField.setText(EnumChatFormatting.DARK_GRAY + squadronMessage);
        }
        this.squadronNameField.drawTextBox();
        if (noSquadron) {
            this.squadronNameField.setText("");
        }
        super.drawScreen(i, j, f);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.squadronNameField.updateCursorCounter();
        final ItemStack itemstack = super.mc.thePlayer.getCurrentEquippedItem();
        if (itemstack == null || !(itemstack.getItem() instanceof LOTRSquadrons.SquadronItem)) {
            super.mc.thePlayer.closeScreen();
        }
        else {
            this.theItem = itemstack;
        }
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button == this.buttonDone) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    @Override
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
    
    public void onGuiClosed() {
        super.onGuiClosed();
        final String squadron = this.squadronNameField.getText();
        final LOTRPacketItemSquadron packet = new LOTRPacketItemSquadron(squadron);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    static {
        guiTexture = new ResourceLocation("lotr:gui/squadronItem.png");
        itemRenderer = new RenderItem();
    }
}
