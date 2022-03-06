// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiTradeButton extends GuiButton
{
    public LOTRGuiTradeButton(final int i, final int j, final int k) {
        super(i, j, k, 18, 18, "Trade");
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        GL11.glDisable(2896);
        mc.getTextureManager().bindTexture(LOTRGuiTrade.guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.field_146123_n = (i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g);
        final int hoverState = this.getHoverState(super.field_146123_n);
        func_146110_a(super.field_146128_h, super.field_146129_i, 176.0f, (float)(hoverState * 18), super.field_146120_f, super.field_146121_g, 512.0f, 512.0f);
        this.mouseDragged(mc, i, j);
        GL11.glEnable(2896);
    }
}
