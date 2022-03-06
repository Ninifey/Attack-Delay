// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.Minecraft;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonPledge extends GuiButton
{
    private LOTRGuiFactions parentGUI;
    public boolean isBroken;
    public List<String> displayLines;
    
    public LOTRGuiButtonPledge(final LOTRGuiFactions gui, final int i, final int x, final int y, final String s) {
        super(i, x, y, 32, 32, s);
        this.parentGUI = gui;
    }
    
    public void setDisplayLines(final String... s) {
        if (s == null) {
            this.displayLines = null;
        }
        else {
            this.displayLines = Arrays.asList(s);
        }
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m) {
            final FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            super.field_146123_n = (i >= super.field_146128_h && j >= super.field_146129_i && i < super.field_146128_h + super.field_146120_f && j < super.field_146129_i + super.field_146121_g);
            final int state = this.getHoverState(super.field_146123_n);
            this.drawTexturedModalRect(super.field_146128_h, super.field_146129_i, 0 + state * super.field_146120_f, 180, super.field_146120_f, super.field_146121_g);
            this.mouseDragged(mc, i, j);
            if (this.func_146115_a() && this.displayLines != null) {
                final float z = ((Gui)this).zLevel;
                final int stringWidth = 200;
                this.parentGUI.drawButtonHoveringText(this.displayLines, i, j);
                GL11.glDisable(2896);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                ((Gui)this).zLevel = z;
            }
        }
    }
    
    public int getHoverState(final boolean flag) {
        if (this.isBroken) {
            return flag ? 4 : 3;
        }
        if (!super.enabled) {
            return 0;
        }
        return flag ? 2 : 1;
    }
}
