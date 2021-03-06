// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketEditNPCRespawner;
import net.minecraft.util.StringUtils;
import net.minecraft.util.StatCollector;
import lotr.common.entity.LOTREntities;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import lotr.common.entity.LOTREntityNPCRespawner;

public class LOTRGuiNPCRespawner extends LOTRGuiScreenBase
{
    private int xSize;
    private int ySize;
    private int guiLeft;
    private int guiTop;
    private LOTREntityNPCRespawner theSpawner;
    private GuiTextField textSpawnClass1;
    private GuiTextField textSpawnClass2;
    private LOTRGuiSlider sliderCheckHorizontal;
    private LOTRGuiSlider sliderCheckVerticalMin;
    private LOTRGuiSlider sliderCheckVerticalMax;
    private LOTRGuiSlider sliderSpawnCap;
    private LOTRGuiSlider sliderBlockEnemy;
    private LOTRGuiSlider sliderSpawnHorizontal;
    private LOTRGuiSlider sliderSpawnVerticalMin;
    private LOTRGuiSlider sliderSpawnVerticalMax;
    private LOTRGuiSlider sliderHomeRange;
    private LOTRGuiButtonOptions buttonMounts;
    private LOTRGuiSlider sliderSpawnIntervalM;
    private LOTRGuiSlider sliderSpawnIntervalS;
    private LOTRGuiSlider sliderNoPlayerRange;
    private GuiButton buttonDestroy;
    private boolean destroySpawner;
    
    public LOTRGuiNPCRespawner(final LOTREntityNPCRespawner entity) {
        this.xSize = 256;
        this.ySize = 280;
        this.destroySpawner = false;
        this.theSpawner = entity;
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        this.textSpawnClass1 = new GuiTextField(super.fontRendererObj, this.guiLeft + this.xSize / 2 - 190, this.guiTop + 35, 180, 20);
        if (this.theSpawner.spawnClass1 != null) {
            this.textSpawnClass1.setText(LOTREntities.getStringFromClass(this.theSpawner.spawnClass1));
        }
        this.textSpawnClass2 = new GuiTextField(super.fontRendererObj, this.guiLeft + this.xSize / 2 + 10, this.guiTop + 35, 180, 20);
        if (this.theSpawner.spawnClass2 != null) {
            this.textSpawnClass2.setText(LOTREntities.getStringFromClass(this.theSpawner.spawnClass2));
        }
        super.buttonList.add(this.sliderCheckHorizontal = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 70, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.checkHorizontal")));
        this.sliderCheckHorizontal.setMinMaxValues(0, 64);
        this.sliderCheckHorizontal.setSliderValue(this.theSpawner.checkHorizontalRange);
        super.buttonList.add(this.sliderCheckVerticalMin = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 95, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.checkVerticalMin")));
        this.sliderCheckVerticalMin.setMinMaxValues(-64, 64);
        this.sliderCheckVerticalMin.setSliderValue(this.theSpawner.checkVerticalMin);
        super.buttonList.add(this.sliderCheckVerticalMax = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 120, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.checkVerticalMax")));
        this.sliderCheckVerticalMax.setMinMaxValues(-64, 64);
        this.sliderCheckVerticalMax.setSliderValue(this.theSpawner.checkVerticalMax);
        super.buttonList.add(this.sliderSpawnCap = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 145, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnCap")));
        this.sliderSpawnCap.setMinMaxValues(0, 64);
        this.sliderSpawnCap.setSliderValue(this.theSpawner.spawnCap);
        super.buttonList.add(this.sliderBlockEnemy = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 170, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.blockEnemy")));
        final LOTRGuiSlider sliderBlockEnemy = this.sliderBlockEnemy;
        final int min = 0;
        final LOTREntityNPCRespawner theSpawner = this.theSpawner;
        sliderBlockEnemy.setMinMaxValues(min, 64);
        this.sliderBlockEnemy.setSliderValue(this.theSpawner.blockEnemySpawns);
        super.buttonList.add(this.sliderSpawnHorizontal = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 70, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnHorizontal")));
        this.sliderSpawnHorizontal.setMinMaxValues(0, 64);
        this.sliderSpawnHorizontal.setSliderValue(this.theSpawner.spawnHorizontalRange);
        super.buttonList.add(this.sliderSpawnVerticalMin = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 95, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnVerticalMin")));
        this.sliderSpawnVerticalMin.setMinMaxValues(-64, 64);
        this.sliderSpawnVerticalMin.setSliderValue(this.theSpawner.spawnVerticalMin);
        super.buttonList.add(this.sliderSpawnVerticalMax = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 120, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnVerticalMax")));
        this.sliderSpawnVerticalMax.setMinMaxValues(-64, 64);
        this.sliderSpawnVerticalMax.setSliderValue(this.theSpawner.spawnVerticalMax);
        super.buttonList.add(this.sliderHomeRange = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 145, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.homeRange")));
        this.sliderHomeRange.setMinMaxValues(-1, 64);
        this.sliderHomeRange.setSliderValue(this.theSpawner.homeRange);
        super.buttonList.add(this.buttonMounts = new LOTRGuiButtonOptions(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 170, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.mounts")));
        super.buttonList.add(this.sliderSpawnIntervalM = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 100 - 5, this.guiTop + 195, 100, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnIntervalM")));
        this.sliderSpawnIntervalM.setMinMaxValues(0, 60);
        this.sliderSpawnIntervalM.setValueOnly();
        this.sliderSpawnIntervalM.setSliderValue(this.theSpawner.spawnInterval / 20 / 60);
        super.buttonList.add(this.sliderSpawnIntervalS = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 5, this.guiTop + 195, 100, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnIntervalS")));
        this.sliderSpawnIntervalS.setMinMaxValues(0, 59);
        this.sliderSpawnIntervalS.setValueOnly();
        this.sliderSpawnIntervalS.setNumberDigits(2);
        this.sliderSpawnIntervalS.setSliderValue(this.theSpawner.spawnInterval / 20 % 60);
        super.buttonList.add(this.sliderNoPlayerRange = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 220, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.noPlayerRange")));
        this.sliderNoPlayerRange.setMinMaxValues(0, 64);
        this.sliderNoPlayerRange.setSliderValue(this.theSpawner.noPlayerRange);
        super.buttonList.add(this.buttonDestroy = new GuiButton(0, this.guiLeft + this.xSize / 2 - 50, this.guiTop + 255, 100, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.destroy")));
    }
    
    public void drawScreen(int i, int j, final float f) {
        this.drawDefaultBackground();
        String s = StatCollector.translateToLocal("lotr.gui.npcRespawner.title");
        super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop, 16777215);
        this.textSpawnClass1.drawTextBox();
        this.textSpawnClass2.drawTextBox();
        s = StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnClass1");
        super.fontRendererObj.drawString(s, this.textSpawnClass1.field_146209_f + 3, this.textSpawnClass1.field_146210_g - super.fontRendererObj.FONT_HEIGHT - 3, 13421772);
        s = StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnClass2");
        super.fontRendererObj.drawString(s, this.textSpawnClass2.field_146209_f + 3, this.textSpawnClass2.field_146210_g - super.fontRendererObj.FONT_HEIGHT - 3, 13421772);
        if (this.theSpawner.mountSetting == 0) {
            this.buttonMounts.setState(StatCollector.translateToLocal("lotr.gui.npcRespawner.mounts.0"));
        }
        else if (this.theSpawner.mountSetting == 1) {
            this.buttonMounts.setState(StatCollector.translateToLocal("lotr.gui.npcRespawner.mounts.1"));
        }
        else {
            this.buttonMounts.setState(StatCollector.translateToLocal("lotr.gui.npcRespawner.mounts.2"));
        }
        if (!this.theSpawner.blockEnemySpawns()) {
            this.sliderBlockEnemy.setOverrideStateString(StatCollector.translateToLocal("lotr.gui.npcRespawner.blockEnemy.off"));
        }
        else {
            this.sliderBlockEnemy.setOverrideStateString(null);
        }
        if (!this.theSpawner.hasHomeRange()) {
            this.sliderHomeRange.setOverrideStateString(StatCollector.translateToLocal("lotr.gui.npcRespawner.homeRange.off"));
        }
        else {
            this.sliderHomeRange.setOverrideStateString(null);
        }
        final String timepre = StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnInterval");
        final int timepreX = this.sliderSpawnIntervalM.field_146128_h - 5 - super.fontRendererObj.getStringWidth(timepre);
        final int timepreY = this.sliderSpawnIntervalM.field_146129_i + this.sliderSpawnIntervalM.field_146121_g / 2 - super.fontRendererObj.FONT_HEIGHT / 2;
        super.fontRendererObj.drawString(timepre, timepreX, timepreY, 16777215);
        final String timesplit = ":";
        final int timesplitX = (this.sliderSpawnIntervalM.field_146128_h + this.sliderSpawnIntervalM.field_146120_f + this.sliderSpawnIntervalS.field_146128_h) / 2 - super.fontRendererObj.getStringWidth(timesplit) / 2;
        final int timesplitY = this.sliderSpawnIntervalM.field_146129_i + this.sliderSpawnIntervalM.field_146121_g / 2 - super.fontRendererObj.FONT_HEIGHT / 2;
        super.fontRendererObj.drawString(timesplit, timesplitX, timesplitY, 16777215);
        super.drawScreen(i, j, f);
        this.updateSliders();
        if (this.sliderBlockEnemy.enabled && this.sliderBlockEnemy.func_146115_a() && !this.sliderBlockEnemy.dragging) {
            final String tooltip = StatCollector.translateToLocal("lotr.gui.npcRespawner.blockEnemy.tooltip");
            final int border = 3;
            final int stringWidth = super.mc.fontRenderer.getStringWidth(tooltip);
            final int stringHeight = super.mc.fontRenderer.FONT_HEIGHT;
            final int offset = 10;
            i += offset;
            j += offset;
            drawRect(i, j, i + stringWidth + border * 2, j + stringHeight + border * 2, -1073741824);
            super.mc.fontRenderer.drawString(tooltip, i + border, j + border, 16777215);
        }
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button instanceof LOTRGuiSlider) {
            return;
        }
        if (button.enabled) {
            if (button == this.buttonMounts) {
                this.theSpawner.toggleMountSetting();
            }
            if (button == this.buttonDestroy) {
                this.destroySpawner = true;
                super.mc.thePlayer.closeScreen();
            }
        }
    }
    
    private void updateSliders() {
        if (this.sliderCheckHorizontal.dragging) {
            this.theSpawner.checkHorizontalRange = this.sliderCheckHorizontal.getSliderValue();
        }
        if (this.sliderCheckVerticalMin.dragging) {
            this.theSpawner.checkVerticalMin = this.sliderCheckVerticalMin.getSliderValue();
            if (this.theSpawner.checkVerticalMax < this.theSpawner.checkVerticalMin) {
                this.theSpawner.checkVerticalMax = this.theSpawner.checkVerticalMin;
                this.sliderCheckVerticalMax.setSliderValue(this.theSpawner.checkVerticalMax);
            }
        }
        if (this.sliderCheckVerticalMax.dragging) {
            this.theSpawner.checkVerticalMax = this.sliderCheckVerticalMax.getSliderValue();
            if (this.theSpawner.checkVerticalMin > this.theSpawner.checkVerticalMax) {
                this.theSpawner.checkVerticalMin = this.theSpawner.checkVerticalMax;
                this.sliderCheckVerticalMin.setSliderValue(this.theSpawner.checkVerticalMin);
            }
        }
        if (this.sliderSpawnCap.dragging) {
            this.theSpawner.spawnCap = this.sliderSpawnCap.getSliderValue();
        }
        if (this.sliderBlockEnemy.dragging) {
            this.theSpawner.blockEnemySpawns = this.sliderBlockEnemy.getSliderValue();
        }
        if (this.sliderSpawnHorizontal.dragging) {
            this.theSpawner.spawnHorizontalRange = this.sliderSpawnHorizontal.getSliderValue();
        }
        if (this.sliderSpawnVerticalMin.dragging) {
            this.theSpawner.spawnVerticalMin = this.sliderSpawnVerticalMin.getSliderValue();
            if (this.theSpawner.spawnVerticalMax < this.theSpawner.spawnVerticalMin) {
                this.theSpawner.spawnVerticalMax = this.theSpawner.spawnVerticalMin;
                this.sliderSpawnVerticalMax.setSliderValue(this.theSpawner.spawnVerticalMax);
            }
        }
        if (this.sliderSpawnVerticalMax.dragging) {
            this.theSpawner.spawnVerticalMax = this.sliderSpawnVerticalMax.getSliderValue();
            if (this.theSpawner.spawnVerticalMin > this.theSpawner.spawnVerticalMax) {
                this.theSpawner.spawnVerticalMin = this.theSpawner.spawnVerticalMax;
                this.sliderSpawnVerticalMin.setSliderValue(this.theSpawner.spawnVerticalMin);
            }
        }
        if (this.sliderHomeRange.dragging) {
            this.theSpawner.homeRange = this.sliderHomeRange.getSliderValue();
        }
        if (this.sliderSpawnIntervalM.dragging || this.sliderSpawnIntervalS.dragging) {
            if (this.sliderSpawnIntervalM.getSliderValue() == 0) {
                int s = this.sliderSpawnIntervalS.getSliderValue();
                s = Math.max(s, 1);
                this.sliderSpawnIntervalS.setSliderValue(s);
            }
            this.theSpawner.spawnInterval = (this.sliderSpawnIntervalM.getSliderValue() * 60 + this.sliderSpawnIntervalS.getSliderValue()) * 20;
        }
        if (this.sliderNoPlayerRange.dragging) {
            this.theSpawner.noPlayerRange = this.sliderNoPlayerRange.getSliderValue();
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.textSpawnClass1.updateCursorCounter();
        this.textSpawnClass2.updateCursorCounter();
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (this.textSpawnClass1.func_146176_q() && this.textSpawnClass1.textboxKeyTyped(c, i)) {
            return;
        }
        if (this.textSpawnClass2.func_146176_q() && this.textSpawnClass2.textboxKeyTyped(c, i)) {
            return;
        }
        super.keyTyped(c, i);
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        this.textSpawnClass1.mouseClicked(i, j, k);
        this.textSpawnClass2.mouseClicked(i, j, k);
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        this.sendSpawnerData();
    }
    
    private void sendSpawnerData() {
        final String s1 = this.textSpawnClass1.getText();
        final String s2 = this.textSpawnClass2.getText();
        if (!StringUtils.isNullOrEmpty(s1)) {
            final Class entityClass = LOTREntities.getClassFromString(s1);
            this.theSpawner.spawnClass1 = entityClass;
        }
        if (!StringUtils.isNullOrEmpty(s2)) {
            final Class entityClass = LOTREntities.getClassFromString(s2);
            this.theSpawner.spawnClass2 = entityClass;
        }
        final LOTRPacketEditNPCRespawner packet = new LOTRPacketEditNPCRespawner(this.theSpawner);
        packet.destroy = this.destroySpawner;
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
}
