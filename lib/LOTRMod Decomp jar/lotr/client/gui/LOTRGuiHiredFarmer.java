// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.gui.GuiButton;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import net.minecraft.util.StatCollector;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRGuiHiredFarmer extends LOTRGuiHiredNPC
{
    private LOTRGuiButtonOptions buttonGuardMode;
    private LOTRGuiSlider sliderGuardRange;
    
    public LOTRGuiHiredFarmer(final LOTREntityNPC npc) {
        super(npc);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        super.buttonList.add(this.buttonGuardMode = new LOTRGuiButtonOptions(0, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 70, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.mode")));
        this.buttonGuardMode.setState(super.theNPC.hiredNPCInfo.isGuardMode());
        super.buttonList.add(this.sliderGuardRange = new LOTRGuiSlider(1, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 94, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.range")));
        this.sliderGuardRange.setMinMaxValues(LOTRHiredNPCInfo.GUARD_RANGE_MIN, LOTRHiredNPCInfo.GUARD_RANGE_MAX);
        this.sliderGuardRange.setSliderValue(super.theNPC.hiredNPCInfo.getGuardRange());
        this.sliderGuardRange.field_146125_m = super.theNPC.hiredNPCInfo.isGuardMode();
        super.buttonList.add(new LOTRGuiButtonOptions(2, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 142, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.openInv")));
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button instanceof LOTRGuiSlider) {
            return;
        }
        if (button.enabled) {
            this.sendActionPacket(button.id);
        }
    }
    
    @Override
    public void drawScreen(final int i, final int j, final float f) {
        super.drawScreen(i, j, f);
        final String s = super.theNPC.hiredNPCInfo.getStatusString();
        super.fontRendererObj.drawString(s, super.guiLeft + super.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, super.guiTop + 50, 4210752);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.buttonGuardMode.setState(super.theNPC.hiredNPCInfo.isGuardMode());
        this.sliderGuardRange.field_146125_m = super.theNPC.hiredNPCInfo.isGuardMode();
        if (this.sliderGuardRange.dragging) {
            final int i = this.sliderGuardRange.getSliderValue();
            super.theNPC.hiredNPCInfo.setGuardRange(i);
            this.sendActionPacket(this.sliderGuardRange.id, i);
        }
    }
}
