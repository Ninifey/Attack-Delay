// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonRedBook extends GuiButton
{
    public LOTRGuiButtonRedBook(final int i, final int x, final int y, final int w, final int h, final String s) {
        super(i, x, y, w, h, s);
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            final FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRGuiRedBook.guiTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            super.field_146123_n = (i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g);
            final int k = this.getHoverState(super.field_146123_n);
            func_146110_a(super.field_146128_h, super.field_146129_i, 170.0f, (float)(256 + k * 20), super.field_146120_f, super.field_146121_g, 512.0f, 512.0f);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            func_146110_a(super.field_146128_h, super.field_146129_i, 170.0f, 316.0f, super.field_146120_f / 2, super.field_146121_g, 512.0f, 512.0f);
            func_146110_a(super.field_146128_h + super.field_146120_f / 2, super.field_146129_i, (float)(370 - super.field_146120_f / 2), 316.0f, super.field_146120_f / 2, super.field_146121_g, 512.0f, 512.0f);
            this.mouseDragged(mc, i, j);
            int color = 8019267;
            if (!super.enabled) {
                color = 5521198;
            }
            else if (super.field_146123_n) {
                color = 8019267;
            }
            fontrenderer.drawString(super.displayString, super.field_146128_h + super.field_146120_f / 2 - fontrenderer.getStringWidth(super.displayString) / 2, super.field_146129_i + (super.field_146121_g - 8) / 2, color);
        }
    }
}
