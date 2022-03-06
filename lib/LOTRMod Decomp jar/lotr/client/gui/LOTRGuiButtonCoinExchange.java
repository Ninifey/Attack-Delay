// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonCoinExchange extends GuiButton
{
    public LOTRGuiButtonCoinExchange(final int i, final int j, final int k) {
        super(i, j, k, 32, 17, "");
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            final FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRGuiCoinExchange.guiTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            super.field_146123_n = (i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g);
            final int k = this.getHoverState(super.field_146123_n);
            final int u = 176 + super.id * super.field_146120_f;
            final int v = 0 + k * super.field_146121_g;
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, u, v, super.field_146120_f, super.field_146121_g);
            this.mouseDragged(mc, i, j);
        }
    }
}
