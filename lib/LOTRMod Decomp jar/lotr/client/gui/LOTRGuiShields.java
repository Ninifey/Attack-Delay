// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import net.minecraft.client.model.ModelBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSelectShield;
import java.util.List;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import lotr.client.render.LOTRRenderShield;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiButton;
import lotr.common.LOTRShields;
import net.minecraft.client.model.ModelBiped;

public class LOTRGuiShields extends LOTRGuiMenuBase
{
    private static ModelBiped playerModel;
    private int modelX;
    private int modelY;
    private float modelRotation;
    private float modelRotationPrev;
    private int isMouseDown;
    private int mouseX;
    private int mouseY;
    private int prevMouseX;
    private LOTRShields.ShieldType currentShieldType;
    private static int currentShieldTypeID;
    private LOTRShields currentShield;
    private static int currentShieldID;
    private GuiButton shieldLeft;
    private GuiButton shieldRight;
    private GuiButton shieldSelect;
    private GuiButton shieldRemove;
    private GuiButton changeCategory;
    
    public LOTRGuiShields() {
        this.modelRotation = -140.0f;
        this.modelRotationPrev = this.modelRotation;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.modelX = super.guiLeft + super.xSize / 2;
        this.modelY = super.guiTop + 40;
        super.buttonList.add(this.shieldLeft = new LOTRGuiButtonShieldsArrows(0, true, super.guiLeft + super.xSize / 2 - 64, super.guiTop + 207));
        super.buttonList.add(this.shieldSelect = new GuiButton(1, super.guiLeft + super.xSize / 2 - 40, super.guiTop + 195, 80, 20, StatCollector.translateToLocal("lotr.gui.shields.select")));
        super.buttonList.add(this.shieldRight = new LOTRGuiButtonShieldsArrows(2, false, super.guiLeft + super.xSize / 2 + 44, super.guiTop + 207));
        super.buttonList.add(this.shieldRemove = new GuiButton(3, super.guiLeft + super.xSize / 2 - 40, super.guiTop + 219, 80, 20, StatCollector.translateToLocal("lotr.gui.shields.remove")));
        super.buttonList.add(this.changeCategory = new GuiButton(4, super.guiLeft + super.xSize / 2 - 80, super.guiTop + 250, 160, 20, ""));
        final LOTRShields equippedShield = this.getPlayerEquippedShield();
        if (equippedShield != null) {
            LOTRGuiShields.currentShieldTypeID = equippedShield.shieldType.ordinal();
            LOTRGuiShields.currentShieldID = equippedShield.shieldID;
        }
        this.updateCurrentShield(0, 0);
    }
    
    private void updateCurrentShield(final int shield, final int type) {
        if (shield != 0) {
            LOTRGuiShields.currentShieldID += shield;
            LOTRGuiShields.currentShieldID = Math.max(LOTRGuiShields.currentShieldID, 0);
            LOTRGuiShields.currentShieldID = Math.min(LOTRGuiShields.currentShieldID, this.currentShieldType.list.size() - 1);
        }
        if (type != 0) {
            LOTRGuiShields.currentShieldTypeID += type;
            if (LOTRGuiShields.currentShieldTypeID > LOTRShields.ShieldType.values().length - 1) {
                LOTRGuiShields.currentShieldTypeID = 0;
            }
            if (LOTRGuiShields.currentShieldTypeID < 0) {
                LOTRGuiShields.currentShieldTypeID = LOTRShields.ShieldType.values().length - 1;
            }
            LOTRGuiShields.currentShieldID = 0;
        }
        this.currentShieldType = LOTRShields.ShieldType.values()[LOTRGuiShields.currentShieldTypeID];
        this.currentShield = this.currentShieldType.list.get(LOTRGuiShields.currentShieldID);
        while (!this.currentShield.canDisplay((EntityPlayer)super.mc.thePlayer)) {
            if ((shield < 0 || type != 0) && this.canGoLeft()) {
                this.updateCurrentShield(-1, 0);
            }
            else if ((shield > 0 || type != 0) && this.canGoRight()) {
                this.updateCurrentShield(1, 0);
            }
            else {
                this.updateCurrentShield(0, 1);
            }
        }
    }
    
    private boolean canGoLeft() {
        for (int i = 0; i <= LOTRGuiShields.currentShieldID - 1; ++i) {
            final LOTRShields shield = this.currentShieldType.list.get(i);
            if (shield.canDisplay((EntityPlayer)super.mc.thePlayer)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean canGoRight() {
        for (int i = LOTRGuiShields.currentShieldID + 1; i <= this.currentShieldType.list.size() - 1; ++i) {
            final LOTRShields shield = this.currentShieldType.list.get(i);
            if (shield.canDisplay((EntityPlayer)super.mc.thePlayer)) {
                return true;
            }
        }
        return false;
    }
    
    private LOTRShields getPlayerEquippedShield() {
        return LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getShield();
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.modelRotationPrev = this.modelRotation;
        this.modelRotationPrev = MathHelper.wrapAngleTo180_float(this.modelRotationPrev);
        this.modelRotation = MathHelper.wrapAngleTo180_float(this.modelRotation);
        final boolean mouseWithinModel = Math.abs(this.mouseX - this.modelX) <= 60 && Math.abs(this.mouseY - this.modelY) <= 80;
        if (Mouse.isButtonDown(0)) {
            if (this.isMouseDown == 0 || this.isMouseDown == 1) {
                if (this.isMouseDown == 0) {
                    if (mouseWithinModel) {
                        this.isMouseDown = 1;
                    }
                }
                else if (this.mouseX != this.prevMouseX) {
                    final float move = -(this.mouseX - this.prevMouseX) * 1.0f;
                    this.modelRotation += move;
                }
                this.prevMouseX = this.mouseX;
            }
        }
        else {
            this.isMouseDown = 0;
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.mouseX = i;
        this.mouseY = j;
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        String s = StatCollector.translateToLocal("lotr.gui.shields.title");
        this.drawCenteredString(s, super.guiLeft + super.xSize / 2, super.guiTop - 30, 16777215);
        GL11.glEnable(2903);
        RenderHelper.enableStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glEnable(32826);
        GL11.glEnable(3008);
        GL11.glTranslatef((float)this.modelX, (float)this.modelY, 50.0f);
        final float scale = 55.0f;
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(this.modelRotationPrev + (this.modelRotation - this.modelRotationPrev) * f, 0.0f, 1.0f, 0.0f);
        super.mc.getTextureManager().bindTexture(super.mc.thePlayer.getLocationSkin());
        LOTRGuiShields.playerModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        LOTRRenderShield.renderShield(this.currentShield, null, LOTRGuiShields.playerModel);
        GL11.glDisable(32826);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int x = super.guiLeft + super.xSize / 2;
        int y = super.guiTop + 145;
        s = this.currentShield.getShieldName();
        this.drawCenteredString(s, x, y, 16777215);
        y += super.fontRendererObj.FONT_HEIGHT * 2;
        final List desc = super.fontRendererObj.listFormattedStringToWidth(this.currentShield.getShieldDesc(), 220);
        for (int l = 0; l < desc.size(); ++l) {
            s = desc.get(l);
            this.drawCenteredString(s, x, y, 16777215);
            y += super.fontRendererObj.FONT_HEIGHT;
        }
        this.shieldLeft.enabled = this.canGoLeft();
        this.shieldSelect.enabled = this.currentShield.canPlayerWear((EntityPlayer)super.mc.thePlayer);
        this.shieldSelect.displayString = ((this.getPlayerEquippedShield() == this.currentShield) ? StatCollector.translateToLocal("lotr.gui.shields.selected") : StatCollector.translateToLocal("lotr.gui.shields.select"));
        this.shieldRight.enabled = this.canGoRight();
        this.shieldRemove.enabled = (this.getPlayerEquippedShield() != null && this.getPlayerEquippedShield() == this.currentShield);
        this.changeCategory.displayString = this.currentShieldType.getDisplayName();
        super.drawScreen(i, j, f);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.shieldLeft) {
                this.updateCurrentShield(-1, 0);
            }
            else if (button == this.shieldSelect) {
                this.updateCurrentShield(0, 0);
                final LOTRPacketSelectShield packet = new LOTRPacketSelectShield(this.currentShield);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
            else if (button == this.shieldRight) {
                this.updateCurrentShield(1, 0);
            }
            else if (button == this.shieldRemove) {
                this.updateCurrentShield(0, 0);
                final LOTRPacketSelectShield packet = new LOTRPacketSelectShield(null);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
            else if (button == this.changeCategory) {
                this.updateCurrentShield(0, 1);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
    
    static {
        LOTRGuiShields.playerModel = new ModelBiped();
        ((ModelBase)LOTRGuiShields.playerModel).isChild = false;
    }
}
