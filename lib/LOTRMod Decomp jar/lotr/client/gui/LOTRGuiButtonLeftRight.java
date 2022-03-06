// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonLeftRight extends GuiButton
{
    private static ResourceLocation texture;
    private boolean leftOrRight;
    
    public LOTRGuiButtonLeftRight(final int i, final boolean flag, final int j, final int k, final String s) {
        super(i, j, k, 120, 20, s);
        this.leftOrRight = flag;
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            final FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRGuiButtonLeftRight.texture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            super.field_146123_n = (i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g);
            final int k = this.getHoverState(super.field_146123_n);
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, this.leftOrRight ? 0 : 136, k * 20, super.field_146120_f, super.field_146121_g);
            this.mouseDragged(mc, i, j);
            int l = 14737632;
            if (!super.enabled) {
                l = -6250336;
            }
            else if (super.field_146123_n) {
                l = 16777120;
            }
            if (this.leftOrRight) {
                this.drawCenteredString(fontrenderer, super.displayString, super.field_146128_h + 67, super.field_146129_i + (super.field_146121_g - 8) / 2, l);
            }
            else {
                this.drawCenteredString(fontrenderer, super.displayString, super.field_146128_h + super.field_146120_f - 67, super.field_146129_i + (super.field_146121_g - 8) / 2, l);
            }
        }
    }
    
    static {
        LOTRGuiButtonLeftRight.texture = new ResourceLocation("lotr:gui/widgets.png");
    }
}
