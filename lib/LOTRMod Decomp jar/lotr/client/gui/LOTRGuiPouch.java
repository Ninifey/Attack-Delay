// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketRenamePouch;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiTextField;
import lotr.common.inventory.LOTRContainerPouch;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiPouch extends GuiContainer
{
    private static ResourceLocation texture;
    private LOTRContainerPouch thePouch;
    private GuiTextField theGuiTextField;
    
    public LOTRGuiPouch(final EntityPlayer entityplayer) {
        super((Container)new LOTRContainerPouch(entityplayer));
        this.thePouch = (LOTRContainerPouch)super.field_147002_h;
        super.field_147000_g = 180;
    }
    
    public void initGui() {
        super.initGui();
        (this.theGuiTextField = new GuiTextField(((GuiScreen)this).fontRendererObj, super.field_147003_i + super.field_146999_f / 2 - 80, super.field_147009_r + 7, 160, 20)).setText(this.thePouch.getDisplayName());
    }
    
    protected void func_146979_b(final int i, final int j) {
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, super.field_147000_g - 96 + 2, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiPouch.texture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
        for (int rows = this.thePouch.capacity / 9, l = 0; l < rows; ++l) {
            this.drawTexturedModalRect(super.field_147003_i + 7, super.field_147009_r + 29 + l * 18, 0, 180, 162, 18);
        }
        GL11.glDisable(2896);
        this.theGuiTextField.drawTextBox();
        GL11.glEnable(2896);
    }
    
    public void updateScreen() {
        super.updateScreen();
        this.theGuiTextField.updateCursorCounter();
    }
    
    protected void keyTyped(final char c, final int i) {
        if (this.theGuiTextField.textboxKeyTyped(c, i)) {
            this.renamePouch();
        }
        else {
            super.keyTyped(c, i);
        }
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        this.theGuiTextField.mouseClicked(i, j, k);
    }
    
    protected boolean func_146983_a(final int i) {
        return false;
    }
    
    private void renamePouch() {
        final String name = this.theGuiTextField.getText();
        this.thePouch.renamePouch(name);
        final LOTRPacketRenamePouch packet = new LOTRPacketRenamePouch(name);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    static {
        LOTRGuiPouch.texture = new ResourceLocation("lotr:gui/pouch.png");
    }
}
