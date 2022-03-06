// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketBrandingIron;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;
import lotr.common.item.LOTRItemBrandingIron;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiBrandingIron extends LOTRGuiScreenBase
{
    private static final ResourceLocation guiTexture;
    private static final RenderItem itemRenderer;
    private int xSize;
    private int ySize;
    private int guiLeft;
    private int guiTop;
    private GuiButton buttonDone;
    private GuiTextField brandNameField;
    private ItemStack theItem;
    
    public LOTRGuiBrandingIron() {
        this.xSize = 200;
        this.ySize = 132;
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        super.buttonList.add(this.buttonDone = new GuiButton(1, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 97, 80, 20, StatCollector.translateToLocal("lotr.gui.brandingIron.done")));
        final ItemStack itemstack = ((EntityPlayer)super.mc.thePlayer).inventory.getCurrentItem();
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemBrandingIron) {
            this.theItem = itemstack;
            this.brandNameField = new GuiTextField(super.fontRendererObj, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 50, 160, 20);
        }
        if (this.theItem == null) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.mc.getTextureManager().bindTexture(LOTRGuiBrandingIron.guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String s = StatCollector.translateToLocal("lotr.gui.brandingIron.title");
        super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 4210752);
        s = StatCollector.translateToLocal("lotr.gui.brandingIron.naming");
        super.fontRendererObj.drawString(s, this.brandNameField.field_146209_f, this.brandNameField.field_146210_g - super.fontRendererObj.FONT_HEIGHT - 3, 4210752);
        s = StatCollector.translateToLocal("lotr.gui.brandingIron.unnameHint");
        super.fontRendererObj.drawString(s, this.brandNameField.field_146209_f, this.brandNameField.field_146210_g + this.brandNameField.field_146219_i + 3, 4210752);
        this.brandNameField.drawTextBox();
        this.buttonDone.enabled = !StringUtils.isBlank((CharSequence)this.brandNameField.getText());
        super.drawScreen(i, j, f);
        if (this.theItem != null) {
            LOTRGuiBrandingIron.itemRenderer.renderItemIntoGUI(super.fontRendererObj, super.mc.getTextureManager(), this.theItem, this.guiLeft + 8, this.guiTop + 8);
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.brandNameField.updateCursorCounter();
        final ItemStack itemstack = super.mc.thePlayer.getCurrentEquippedItem();
        if (itemstack == null || !(itemstack.getItem() instanceof LOTRItemBrandingIron)) {
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
        if (this.brandNameField.func_146176_q() && this.brandNameField.textboxKeyTyped(c, i)) {
            return;
        }
        super.keyTyped(c, i);
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        this.brandNameField.mouseClicked(i, j, k);
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        final String brandName = this.brandNameField.getText();
        if (!StringUtils.isBlank((CharSequence)brandName)) {
            final LOTRPacketBrandingIron packet = new LOTRPacketBrandingIron(brandName);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    static {
        guiTexture = new ResourceLocation("lotr:gui/brandingIron.png");
        itemRenderer = new RenderItem();
    }
}
