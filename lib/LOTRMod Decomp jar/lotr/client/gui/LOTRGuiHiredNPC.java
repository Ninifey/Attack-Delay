// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredUnitCommand;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.ResourceLocation;

public abstract class LOTRGuiHiredNPC extends LOTRGuiScreenBase
{
    private static ResourceLocation guiTexture;
    public int xSize;
    public int ySize;
    public int guiLeft;
    public int guiTop;
    public LOTREntityNPC theNPC;
    public int page;
    
    public LOTRGuiHiredNPC(final LOTREntityNPC npc) {
        this.xSize = 200;
        this.ySize = 220;
        this.theNPC = npc;
    }
    
    public void initGui() {
        this.guiLeft = (super.width - this.xSize) / 2;
        this.guiTop = (super.height - this.ySize) / 2;
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.mc.getTextureManager().bindTexture(LOTRGuiHiredNPC.guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String s = this.theNPC.getNPCName();
        super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 3618615);
        s = this.theNPC.getEntityClassName();
        super.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - super.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 26, 3618615);
        if (this.page == 0) {
            int x = this.guiLeft + 6;
            int y = this.guiTop + 186;
            s = StatCollector.translateToLocal("lotr.hiredNPC.commandReq");
            super.fontRendererObj.drawString(s, x, y, 3618615);
            y += super.fontRendererObj.FONT_HEIGHT;
            x += 4;
            final LOTRFaction fac = this.theNPC.getHiringFaction();
            s = LOTRAlignmentValues.formatAlignForDisplay(this.theNPC.hiredNPCInfo.alignmentRequiredToCommand);
            s = StatCollector.translateToLocalFormatted("lotr.hiredNPC.commandReq.align", new Object[] { s, fac.factionName() });
            super.fontRendererObj.drawString(s, x, y, 3618615);
            y += super.fontRendererObj.FONT_HEIGHT;
            final LOTRUnitTradeEntry.PledgeType pledge = this.theNPC.hiredNPCInfo.pledgeType;
            final String pledgeReq = pledge.getCommandReqText(fac);
            if (pledgeReq != null) {
                super.fontRendererObj.drawString(pledgeReq, x, y, 3618615);
                y += super.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(i, j, f);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.theNPC.isEntityAlive() || this.theNPC.hiredNPCInfo.getHiringPlayer() != super.mc.thePlayer || this.theNPC.getDistanceSqToEntity((Entity)super.mc.thePlayer) > 64.0) {
            super.mc.thePlayer.closeScreen();
        }
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        this.sendActionPacket(-1);
    }
    
    public void sendActionPacket(final int action) {
        this.sendActionPacket(action, 0);
    }
    
    public void sendActionPacket(final int action, final int value) {
        final LOTRPacketHiredUnitCommand packet = new LOTRPacketHiredUnitCommand(this.theNPC.getEntityId(), this.page, action, value);
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    static {
        LOTRGuiHiredNPC.guiTexture = new ResourceLocation("lotr:gui/npc/hired.png");
    }
}
