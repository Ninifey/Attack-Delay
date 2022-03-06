// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import lotr.client.LOTRKeyHandler;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;

public abstract class LOTRGuiMenuBase extends LOTRGuiScreenBase
{
    public static RenderItem renderItem;
    public int xSize;
    public int ySize;
    public int guiLeft;
    public int guiTop;
    protected GuiButton buttonMenuReturn;
    
    public LOTRGuiMenuBase() {
        this.xSize = 200;
        this.ySize = 256;
    }
    
    public void initGui() {
        super.initGui();
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        final int buttonW = 120;
        final int buttonH = 20;
        final int buttonGap = 35;
        final int minGap = 10;
        super.buttonList.add(this.buttonMenuReturn = new LOTRGuiButtonLeftRight(1000, true, 0, this.guiTop + (this.ySize + buttonH) / 4, StatCollector.translateToLocal("lotr.gui.menuButton")));
        this.buttonMenuReturn.field_146128_h = Math.min(0 + buttonGap, this.guiLeft - minGap - this.buttonMenuReturn.field_146120_f);
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (i == LOTRKeyHandler.keyBindingMenu.getKeyCode()) {
            super.mc.displayGuiScreen((GuiScreen)new LOTRGuiMenu());
            return;
        }
        super.keyTyped(c, i);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled && button == this.buttonMenuReturn) {
            super.mc.displayGuiScreen((GuiScreen)new LOTRGuiMenu());
        }
        super.actionPerformed(button);
    }
    
    static {
        LOTRGuiMenuBase.renderItem = new RenderItem();
    }
}
