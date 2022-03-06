// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSetOption;
import lotr.common.LOTRPlayerData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiOptions extends LOTRGuiMenuBase
{
    private LOTRGuiButtonOptions buttonFriendlyFire;
    private LOTRGuiButtonOptions buttonHiredDeathMessages;
    private LOTRGuiButtonOptions buttonAlignment;
    private LOTRGuiButtonOptions buttonMapLocation;
    private LOTRGuiButtonOptions buttonConquest;
    private LOTRGuiButtonOptions buttonFeminineRank;
    
    @Override
    public void initGui() {
        super.initGui();
        final int buttonX = super.guiLeft + super.xSize / 2 - 100;
        final int buttonY = super.guiTop + 40;
        super.buttonList.add(this.buttonFriendlyFire = new LOTRGuiButtonOptions(0, buttonX, buttonY, 200, 20, "lotr.gui.options.friendlyFire"));
        super.buttonList.add(this.buttonHiredDeathMessages = new LOTRGuiButtonOptions(1, buttonX, buttonY + 24, 200, 20, "lotr.gui.options.hiredDeathMessages"));
        super.buttonList.add(this.buttonAlignment = new LOTRGuiButtonOptions(2, buttonX, buttonY + 48, 200, 20, "lotr.gui.options.showAlignment"));
        super.buttonList.add(this.buttonMapLocation = new LOTRGuiButtonOptions(3, buttonX, buttonY + 72, 200, 20, "lotr.gui.options.showMapLocation"));
        super.buttonList.add(this.buttonConquest = new LOTRGuiButtonOptions(5, buttonX, buttonY + 96, 200, 20, "lotr.gui.options.conquest"));
        super.buttonList.add(this.buttonFeminineRank = new LOTRGuiButtonOptions(4, buttonX, buttonY + 120, 200, 20, "lotr.gui.options.femRank"));
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        String s = StatCollector.translateToLocal("lotr.gui.options.title");
        super.fontRendererObj.drawString(s, super.guiLeft + 100 - super.fontRendererObj.getStringWidth(s) / 2, super.guiTop - 30, 16777215);
        s = StatCollector.translateToLocal("lotr.gui.options.worldSettings");
        super.fontRendererObj.drawString(s, super.guiLeft + 100 - super.fontRendererObj.getStringWidth(s) / 2, super.guiTop + 10, 16777215);
        final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)super.mc.thePlayer);
        this.buttonFriendlyFire.setState(pd.getFriendlyFire());
        this.buttonHiredDeathMessages.setState(pd.getEnableHiredDeathMessages());
        this.buttonAlignment.setState(!pd.getHideAlignment());
        this.buttonMapLocation.setState(!pd.getHideMapLocation());
        this.buttonConquest.setState(pd.getEnableConquestKills());
        this.buttonFeminineRank.setState(pd.getFemRankOverride());
        super.drawScreen(i, j, f);
        for (int k = 0; k < super.buttonList.size(); ++k) {
            final GuiButton button = super.buttonList.get(k);
            if (button instanceof LOTRGuiButtonOptions) {
                ((LOTRGuiButtonOptions)button).drawTooltip(super.mc, i, j);
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button instanceof LOTRGuiButtonOptions) {
                final LOTRPacketSetOption packet = new LOTRPacketSetOption(button.id);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
}
