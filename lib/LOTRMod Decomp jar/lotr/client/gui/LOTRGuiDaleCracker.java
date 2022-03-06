// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiButton;
import lotr.common.inventory.LOTRContainerDaleCracker;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LOTRGuiDaleCracker extends GuiContainer
{
    private static ResourceLocation texture;
    private LOTRContainerDaleCracker theCracker;
    private GuiButton buttonSeal;
    
    public LOTRGuiDaleCracker(final EntityPlayer entityplayer) {
        super((Container)new LOTRContainerDaleCracker(entityplayer));
        this.theCracker = (LOTRContainerDaleCracker)super.field_147002_h;
    }
    
    public void initGui() {
        super.initGui();
        ((GuiScreen)this).buttonList.add(this.buttonSeal = new GuiButton(0, super.field_147003_i + super.field_146999_f / 2 - 40, super.field_147009_r + 48, 80, 20, StatCollector.translateToLocal("lotr.gui.daleCracker.seal")));
        this.buttonSeal.enabled = false;
    }
    
    protected void func_146979_b(final int i, final int j) {
        final String s = StatCollector.translateToLocal("lotr.gui.daleCracker");
        ((GuiScreen)this).fontRendererObj.drawString(s, super.field_146999_f / 2 - ((GuiScreen)this).fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        ((GuiScreen)this).fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, super.field_147000_g - 96 + 2, 4210752);
    }
    
    protected void func_146976_a(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((GuiScreen)this).mc.getTextureManager().bindTexture(LOTRGuiDaleCracker.texture);
        this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.field_146999_f, super.field_147000_g);
    }
    
    public void updateScreen() {
        super.updateScreen();
        this.buttonSeal.enabled = !this.theCracker.isCrackerInvEmpty();
    }
    
    protected boolean func_146983_a(final int i) {
        return false;
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled && button == this.buttonSeal && !this.theCracker.isCrackerInvEmpty()) {
            this.theCracker.sendSealingPacket((EntityPlayer)((GuiScreen)this).mc.thePlayer);
            ((GuiScreen)this).mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    static {
        LOTRGuiDaleCracker.texture = new ResourceLocation("lotr:gui/daleCracker.png");
    }
}
