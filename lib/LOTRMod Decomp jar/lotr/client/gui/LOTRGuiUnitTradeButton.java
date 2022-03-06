// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiUnitTradeButton extends GuiButton
{
    private static ResourceLocation guiTexture;
    
    public LOTRGuiUnitTradeButton(final int i, final int j, final int k, final int width, final int height) {
        super(i, j, k, width, height, "");
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            mc.getTextureManager().bindTexture(LOTRGuiUnitTradeButton.guiTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean flag = i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g;
            final int k = super.id * 19;
            int l = 0;
            if (!super.enabled) {
                l += super.field_146120_f * 2;
            }
            else if (flag) {
                l += super.field_146120_f;
            }
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, l, k, super.field_146120_f, super.field_146121_g);
        }
    }
    
    static {
        LOTRGuiUnitTradeButton.guiTexture = new ResourceLocation("lotr:gui/npc/unit_trade_buttons.png");
    }
}
