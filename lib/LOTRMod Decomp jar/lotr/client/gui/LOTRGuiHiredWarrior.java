// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCSquadron;
import net.minecraft.util.StringUtils;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import net.minecraft.util.StatCollector;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiHiredWarrior extends LOTRGuiHiredNPC
{
    private static String[] pageTitles;
    private GuiButton buttonLeft;
    private GuiButton buttonRight;
    private LOTRGuiButtonOptions buttonOpenInv;
    private LOTRGuiButtonOptions buttonTeleport;
    private LOTRGuiButtonOptions buttonGuardMode;
    private LOTRGuiSlider sliderGuardRange;
    private GuiTextField squadronNameField;
    private boolean updatePage;
    private boolean sendSquadronUpdate;
    
    public LOTRGuiHiredWarrior(final LOTREntityNPC npc) {
        super(npc);
        this.sendSquadronUpdate = false;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (super.page == 0) {
            super.buttonList.add(this.buttonOpenInv = new LOTRGuiButtonOptions(0, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 142, 160, 20, StatCollector.translateToLocal("lotr.gui.warrior.openInv")));
        }
        if (super.page == 1) {
            super.buttonList.add(this.buttonTeleport = new LOTRGuiButtonOptions(0, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 180, 160, 20, StatCollector.translateToLocal("lotr.gui.warrior.teleport")));
            super.buttonList.add(this.buttonGuardMode = new LOTRGuiButtonOptions(1, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 50, 160, 20, StatCollector.translateToLocal("lotr.gui.warrior.guardMode")));
            super.buttonList.add(this.sliderGuardRange = new LOTRGuiSlider(2, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 74, 160, 20, StatCollector.translateToLocal("lotr.gui.warrior.guardRange")));
            this.sliderGuardRange.setMinMaxValues(LOTRHiredNPCInfo.GUARD_RANGE_MIN, LOTRHiredNPCInfo.GUARD_RANGE_MAX);
            this.sliderGuardRange.setSliderValue(super.theNPC.hiredNPCInfo.getGuardRange());
            (this.squadronNameField = new GuiTextField(super.fontRendererObj, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 130, 160, 20)).func_146203_f(LOTRSquadrons.SQUADRON_LENGTH_MAX);
            final String squadron = super.theNPC.hiredNPCInfo.getSquadron();
            if (!StringUtils.isNullOrEmpty(squadron)) {
                this.squadronNameField.setText(squadron);
            }
        }
        this.buttonLeft = new LOTRGuiButtonLeftRight(1000, true, super.guiLeft - 160, super.guiTop + 50, "");
        this.buttonRight = new LOTRGuiButtonLeftRight(1001, false, super.guiLeft + super.xSize + 40, super.guiTop + 50, "");
        super.buttonList.add(this.buttonLeft);
        super.buttonList.add(this.buttonRight);
        if (super.page == 0) {
            this.buttonLeft.displayString = LOTRGuiHiredWarrior.pageTitles[LOTRGuiHiredWarrior.pageTitles.length - 1];
        }
        else {
            this.buttonLeft.displayString = LOTRGuiHiredWarrior.pageTitles[super.page - 1];
        }
        if (super.page == LOTRGuiHiredWarrior.pageTitles.length - 1) {
            this.buttonRight.displayString = LOTRGuiHiredWarrior.pageTitles[0];
        }
        else {
            this.buttonRight.displayString = LOTRGuiHiredWarrior.pageTitles[super.page + 1];
        }
        this.buttonLeft.displayString = StatCollector.translateToLocal("lotr.gui.warrior." + this.buttonLeft.displayString);
        this.buttonRight.displayString = StatCollector.translateToLocal("lotr.gui.warrior." + this.buttonRight.displayString);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button instanceof LOTRGuiSlider) {
            return;
        }
        if (button.enabled) {
            if (button instanceof LOTRGuiButtonLeftRight) {
                if (button == this.buttonLeft) {
                    --super.page;
                    if (super.page < 0) {
                        super.page = LOTRGuiHiredWarrior.pageTitles.length - 1;
                    }
                }
                else if (button == this.buttonRight) {
                    ++super.page;
                    if (super.page >= LOTRGuiHiredWarrior.pageTitles.length) {
                        super.page = 0;
                    }
                }
                super.buttonList.clear();
                this.updatePage = true;
            }
            else {
                this.sendActionPacket(button.id);
            }
        }
    }
    
    @Override
    public void drawScreen(final int i, final int j, final float f) {
        super.drawScreen(i, j, f);
        if (super.page == 0) {
            String s = StatCollector.translateToLocalFormatted("lotr.gui.warrior.health", new Object[] { Math.round(super.theNPC.getHealth()), Math.round(super.theNPC.getMaxHealth()) });
            super.fontRendererObj.drawString(s, super.guiLeft + super.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, super.guiTop + 50, 4210752);
            s = super.theNPC.hiredNPCInfo.getStatusString();
            super.fontRendererObj.drawString(s, super.guiLeft + super.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, super.guiTop + 62, 4210752);
            s = StatCollector.translateToLocalFormatted("lotr.gui.warrior.kills", new Object[] { super.theNPC.hiredNPCInfo.mobKills });
            super.fontRendererObj.drawString(s, super.guiLeft + super.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, super.guiTop + 74, 4210752);
        }
        if (super.page == 1) {
            final String s = StatCollector.translateToLocal("lotr.gui.warrior.squadron");
            super.fontRendererObj.drawString(s, this.squadronNameField.field_146209_f, this.squadronNameField.field_146210_g - super.fontRendererObj.FONT_HEIGHT - 3, 4210752);
            this.squadronNameField.drawTextBox();
        }
    }
    
    @Override
    public void updateScreen() {
        if (this.updatePage) {
            this.initGui();
            this.updatePage = false;
        }
        super.updateScreen();
        if (super.page == 1) {
            this.buttonTeleport.setState(super.theNPC.hiredNPCInfo.teleportAutomatically);
            this.buttonTeleport.enabled = !super.theNPC.hiredNPCInfo.isGuardMode();
            this.buttonGuardMode.setState(super.theNPC.hiredNPCInfo.isGuardMode());
            this.sliderGuardRange.field_146125_m = super.theNPC.hiredNPCInfo.isGuardMode();
            if (this.sliderGuardRange.dragging) {
                final int i = this.sliderGuardRange.getSliderValue();
                super.theNPC.hiredNPCInfo.setGuardRange(i);
                this.sendActionPacket(this.sliderGuardRange.id, i);
            }
            this.squadronNameField.updateCursorCounter();
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (super.page == 1 && this.squadronNameField != null && this.squadronNameField.func_146176_q() && this.squadronNameField.textboxKeyTyped(c, i)) {
            super.theNPC.hiredNPCInfo.setSquadron(this.squadronNameField.getText());
            this.sendSquadronUpdate = true;
            return;
        }
        super.keyTyped(c, i);
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        if (super.page == 1 && this.squadronNameField != null) {
            this.squadronNameField.mouseClicked(i, j, k);
        }
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.sendSquadronUpdate) {
            final String squadron = super.theNPC.hiredNPCInfo.getSquadron();
            final LOTRPacketNPCSquadron packet = new LOTRPacketNPCSquadron(super.theNPC, squadron);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
    
    static {
        LOTRGuiHiredWarrior.pageTitles = new String[] { "overview", "options" };
    }
}
