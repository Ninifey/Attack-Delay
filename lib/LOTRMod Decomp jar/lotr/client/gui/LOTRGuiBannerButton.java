// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiBannerButton extends GuiButton
{
    private static ResourceLocation guiTexture;
    
    public LOTRGuiBannerButton(final int i, final int j, final int k) {
        super(i, j, k, 7, 7, "");
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            mc.getTextureManager().bindTexture(LOTRGuiBannerButton.guiTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean over = i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g;
            final int k = 226 + super.id * super.field_146120_f;
            final int l = this.getHoverState(over) * super.field_146120_f;
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, k, l, super.field_146120_f, super.field_146121_g);
        }
    }
    
    static {
        LOTRGuiBannerButton.guiTexture = new ResourceLocation("lotr:gui/banner_edit.png");
    }
}
