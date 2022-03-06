// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonFactionsPage extends GuiButton
{
    private boolean leftOrRight;
    
    public LOTRGuiButtonFactionsPage(final int i, final int x, final int y, final boolean flag) {
        super(i, x, y, 16, 16, "");
        this.leftOrRight = flag;
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            final FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            super.field_146123_n = (i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g);
            final int k = this.getHoverState(super.field_146123_n);
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, 0 + k * 16, this.leftOrRight ? 176 : 160, super.field_146120_f, super.field_146121_g);
            this.mouseDragged(mc, i, j);
            if (super.enabled) {
                final FontRenderer fr = mc.fontRenderer;
                final int stringBorder = 0;
                final int stringY = super.field_146129_i + super.field_146121_g / 2 - fr.FONT_HEIGHT / 2;
                if (this.leftOrRight) {
                    final String s = "->";
                    fr.drawString(s, super.field_146128_h - stringBorder - fr.getStringWidth(s), stringY, 0);
                }
                else {
                    final String s = "<-";
                    fr.drawString(s, super.field_146128_h + super.field_146120_f + stringBorder, stringY, 0);
                }
            }
        }
    }
}
