// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import lotr.common.LOTRAchievement;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonAchievements extends GuiButton
{
    private boolean leftOrRight;
    public LOTRAchievement.Category buttonCategory;
    
    public LOTRGuiButtonAchievements(final int i, final boolean flag, final int j, final int k) {
        super(i, j, k, 15, 21, "");
        this.leftOrRight = flag;
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
            int texU = this.leftOrRight ? 0 : (super.field_146120_f * 3);
            final int texV = 124;
            final boolean highlighted = i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g;
            if (!super.enabled) {
                texU += super.field_146120_f * 2;
            }
            else if (highlighted) {
                texU += super.field_146120_f;
            }
            final float[] catColors = this.buttonCategory.getCategoryRGB();
            GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0f);
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, texU, texV, super.field_146120_f, super.field_146121_g);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, texU, texV + super.field_146121_g, super.field_146120_f, super.field_146121_g);
        }
    }
}
