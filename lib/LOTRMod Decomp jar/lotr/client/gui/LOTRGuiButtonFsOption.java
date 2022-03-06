// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonFsOption extends GuiButton
{
    private int iconU;
    private int iconV;
    
    public LOTRGuiButtonFsOption(final int i, final int x, final int y, final int u, final int v, final String s) {
        super(i, x, y, 16, 16, s);
        this.iconU = u;
        this.iconV = v;
    }
    
    public void setIconUV(final int u, final int v) {
        this.iconU = u;
        this.iconV = v;
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            final FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            super.field_146123_n = (i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g);
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, this.iconU, this.iconV + (super.field_146123_n ? super.field_146121_g : 0), super.field_146120_f, super.field_146121_g);
            this.mouseDragged(mc, i, j);
        }
    }
}
