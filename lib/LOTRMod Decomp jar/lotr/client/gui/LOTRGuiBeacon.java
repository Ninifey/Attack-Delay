// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketBeaconEdit;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.util.StatCollector;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import lotr.common.fellowship.LOTRFellowshipClient;
import java.util.UUID;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiBeacon extends LOTRGuiScreenBase
{
    private static ResourceLocation guiTexture;
    private int xSize;
    private int ySize;
    private int guiLeft;
    private int guiTop;
    private int beaconX;
    private int beaconY;
    private int beaconZ;
    private UUID initFellowshipID;
    private LOTRFellowshipClient initFellowship;
    private String initBeaconName;
    private String currentBeaconName;
    private GuiButton buttonDone;
    private GuiTextField fellowshipNameField;
    private GuiTextField beaconNameField;
    
    public LOTRGuiBeacon(final LOTRTileEntityBeacon beacon) {
        this.xSize = 200;
        this.ySize = 160;
        this.beaconX = beacon.xCoord;
        this.beaconY = beacon.yCoord;
        this.beaconZ = beacon.zCoord;
        this.initFellowshipID = beacon.getFellowshipID();
        this.initBeaconName = beacon.getBeaconName();
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
        this.initFellowship = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getClientFellowshipByID(this.initFellowshipID);
        super.buttonList.add(this.buttonDone = new GuiButton(0, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 130, 80, 20, StatCollector.translateToLocal("container.lotr.beacon.done")));
        (this.fellowshipNameField = new GuiTextField(super.fontRendererObj, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 45, 160, 20)).func_146203_f(40);
        if (this.initFellowship != null) {
            this.fellowshipNameField.setText(this.initFellowship.getName());
        }
        (this.beaconNameField = new GuiTextField(super.fontRendererObj, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 100, 160, 20)).func_146203_f(40);
        if (!StringUtils.isBlank((CharSequence)this.initBeaconName)) {
            this.beaconNameField.setText(this.initBeaconName);
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.mc.getTextureManager().bindTexture(LOTRGuiBeacon.guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        final TileEntity te = super.mc.theWorld.getTileEntity(this.beaconX, this.beaconY, this.beaconZ);
        String s = new ItemStack(te.getBlockType(), 1, te.getBlockMetadata()).getDisplayName();
        super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 4210752);
        this.fellowshipNameField.drawTextBox();
        s = StatCollector.translateToLocal("container.lotr.beacon.nameFellowship");
        super.fontRendererObj.drawString(s, this.fellowshipNameField.field_146209_f + 4, this.fellowshipNameField.field_146210_g - 4 - super.fontRendererObj.FONT_HEIGHT, 4210752);
        this.currentBeaconName = this.beaconNameField.getText();
        this.beaconNameField.func_146184_c(true);
        if (this.beaconNameField.isFocused()) {
            this.beaconNameField.drawTextBox();
        }
        else {
            String beaconNameEff = this.currentBeaconName;
            if (StringUtils.isBlank((CharSequence)beaconNameEff)) {
                beaconNameEff = this.fellowshipNameField.getText();
                this.beaconNameField.func_146184_c(false);
            }
            this.beaconNameField.setText(beaconNameEff);
            this.beaconNameField.drawTextBox();
            this.beaconNameField.setText(this.currentBeaconName);
        }
        s = StatCollector.translateToLocal("container.lotr.beacon.nameBeacon");
        super.fontRendererObj.drawString(s, this.beaconNameField.field_146209_f + 4, this.beaconNameField.field_146210_g - 4 - super.fontRendererObj.FONT_HEIGHT * 2, 4210752);
        s = StatCollector.translateToLocal("container.lotr.beacon.namePrefix");
        super.fontRendererObj.drawString(s, this.beaconNameField.field_146209_f + 4, this.beaconNameField.field_146210_g - 4 - super.fontRendererObj.FONT_HEIGHT, 4210752);
        super.drawScreen(i, j, f);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.fellowshipNameField.updateCursorCounter();
        this.beaconNameField.updateCursorCounter();
        final double dSq = super.mc.thePlayer.getDistanceSq(this.beaconX + 0.5, this.beaconY + 0.5, this.beaconZ + 0.5);
        if (dSq > 64.0) {
            super.mc.thePlayer.closeScreen();
        }
        else {
            final TileEntity tileentity = super.mc.theWorld.getTileEntity(this.beaconX, this.beaconY, this.beaconZ);
            if (!(tileentity instanceof LOTRTileEntityBeacon)) {
                super.mc.thePlayer.closeScreen();
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int i) {
        if (this.fellowshipNameField.func_146176_q() && this.fellowshipNameField.textboxKeyTyped(c, i)) {
            return;
        }
        if (this.beaconNameField.func_146176_q() && this.beaconNameField.textboxKeyTyped(c, i)) {
            return;
        }
        super.keyTyped(c, i);
    }
    
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        this.fellowshipNameField.mouseClicked(i, j, k);
        this.beaconNameField.mouseClicked(i, j, k);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled && button == this.buttonDone) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    private void sendBeaconEditPacket(final boolean closed) {
        UUID fsID = null;
        final String fsName = this.fellowshipNameField.getText();
        if (!StringUtils.isBlank((CharSequence)fsName)) {
            final LOTRFellowshipClient fs = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer).getClientFellowshipByName(fsName);
            if (fs != null) {
                fsID = fs.getFellowshipID();
            }
        }
        final String beaconName = this.currentBeaconName;
        final LOTRPacketBeaconEdit packet = new LOTRPacketBeaconEdit(this.beaconX, this.beaconY, this.beaconZ, fsID, beaconName, true);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    public void onGuiClosed() {
        this.sendBeaconEditPacket(true);
    }
    
    static {
        LOTRGuiBeacon.guiTexture = new ResourceLocation("lotr:gui/beacon.png");
    }
}
