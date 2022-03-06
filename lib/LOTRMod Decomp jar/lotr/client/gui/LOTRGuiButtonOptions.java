// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonOptions extends GuiButton
{
    public String baseDisplayString;
    
    public LOTRGuiButtonOptions(final int i, final int j, final int k, final int l, final int i1, final String s) {
        super(i, j, k, l, i1, s);
        this.baseDisplayString = s;
    }
    
    private String getDescription() {
        return StatCollector.translateToLocal(this.baseDisplayString + ".desc.on") + "\n\n" + StatCollector.translateToLocal(this.baseDisplayString + ".desc.off");
    }
    
    public void setState(final String s) {
        super.displayString = StatCollector.translateToLocal(this.baseDisplayString) + ": " + s;
    }
    
    public void setState(final boolean flag) {
        this.setState(flag ? StatCollector.translateToLocal("lotr.gui.button.on") : StatCollector.translateToLocal("lotr.gui.button.off"));
    }
    
    public void drawTooltip(final Minecraft mc, int i, int j) {
        if (super.enabled && this.func_146115_a()) {
            final String s = this.getDescription();
            final int border = 3;
            final int stringWidth = 200;
            final int stringHeight = mc.fontRenderer.listFormattedStringToWidth(s, stringWidth).size() * mc.fontRenderer.FONT_HEIGHT;
            final int offset = 10;
            i += offset;
            j += offset;
            drawRect(i, j, i + stringWidth + border * 2, j + stringHeight + border * 2, -1073741824);
            mc.fontRenderer.drawSplitString(s, i + border, j + border, stringWidth, 16777215);
        }
    }
}
