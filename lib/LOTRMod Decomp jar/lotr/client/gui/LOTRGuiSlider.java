// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiSlider extends GuiButton
{
    private String baseDisplayString;
    private String overrideStateString;
    private boolean isTime;
    private boolean isFloat;
    private boolean valueOnly;
    private int numberDigits;
    private int minValue;
    private int maxValue;
    private float minValueF;
    private float maxValueF;
    private float sliderValue;
    public boolean dragging;
    
    public LOTRGuiSlider(final int id, final int x, final int y, final int width, final int height, final String s) {
        super(id, x, y, width, height, s);
        this.isTime = false;
        this.isFloat = false;
        this.valueOnly = false;
        this.numberDigits = 0;
        this.sliderValue = 1.0f;
        this.dragging = false;
        this.baseDisplayString = s;
    }
    
    public void setFloat() {
        this.isFloat = true;
    }
    
    public void setMinutesSecondsTime() {
        this.isTime = true;
    }
    
    public void setValueOnly() {
        this.valueOnly = true;
    }
    
    public void setNumberDigits(final int i) {
        this.numberDigits = i;
    }
    
    public int getHoverState(final boolean flag) {
        return 0;
    }
    
    public void setMinMaxValues(final int min, final int max) {
        this.minValue = min;
        this.maxValue = max;
    }
    
    public int getSliderValue() {
        return this.minValue + Math.round(this.sliderValue * (this.maxValue - this.minValue));
    }
    
    public void setSliderValue(int value) {
        value = MathHelper.clamp_int(value, this.minValue, this.maxValue);
        this.sliderValue = (value - this.minValue) / (float)(this.maxValue - this.minValue);
    }
    
    public void setMinMaxValues_F(final float min, final float max) {
        this.minValueF = min;
        this.maxValueF = max;
    }
    
    public float getSliderValue_F() {
        return this.minValueF + this.sliderValue * (this.maxValueF - this.minValueF);
    }
    
    public void setSliderValue_F(float value) {
        value = MathHelper.clamp_float(value, this.minValueF, this.maxValueF);
        this.sliderValue = (value - this.minValueF) / (this.maxValueF - this.minValueF);
    }
    
    public void setOverrideStateString(final String s) {
        this.overrideStateString = s;
    }
    
    public void drawButton(final Minecraft mc, final int i, final int j) {
        if (this.overrideStateString != null) {
            super.displayString = this.overrideStateString;
        }
        else if (this.isTime) {
            final int value = this.getSliderValue();
            final int seconds = value % 60;
            final int minutes = value / 60;
            final String s = String.format("%d:%02d", minutes, seconds);
            super.displayString = s;
        }
        else if (this.isFloat) {
            final String s2 = String.format("%.2f", this.getSliderValue_F());
            super.displayString = s2;
        }
        else {
            final int value = this.getSliderValue();
            super.displayString = String.valueOf(value);
            if (this.numberDigits > 0) {
                super.displayString = String.format("%0" + this.numberDigits + "d", value);
            }
        }
        if (!this.valueOnly) {
            super.displayString = this.baseDisplayString + ": " + super.displayString;
        }
        super.drawButton(mc, i, j);
    }
    
    protected void mouseDragged(final Minecraft mc, final int i, final int j) {
        if (super.field_146125_m && super.enabled) {
            if (this.dragging) {
                this.sliderValue = (i - (super.field_146128_h + 4)) / (float)(super.field_146120_f - 8);
                if (this.sliderValue < 0.0f) {
                    this.sliderValue = 0.0f;
                }
                if (this.sliderValue > 1.0f) {
                    this.sliderValue = 1.0f;
                }
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(super.field_146128_h + (int)(this.sliderValue * (super.field_146120_f - 8)), super.field_146129_i, 0, 66, 4, 20);
            this.drawTexturedModalRect(super.field_146128_h + (int)(this.sliderValue * (super.field_146120_f - 8)) + 4, super.field_146129_i, 196, 66, 4, 20);
        }
    }
    
    public boolean mousePressed(final Minecraft mc, final int i, final int j) {
        if (super.mousePressed(mc, i, j)) {
            this.sliderValue = (i - (super.field_146128_h + 4)) / (float)(super.field_146120_f - 8);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            return this.dragging = true;
        }
        return false;
    }
    
    public void mouseReleased(final int i, final int j) {
        this.dragging = false;
    }
}
