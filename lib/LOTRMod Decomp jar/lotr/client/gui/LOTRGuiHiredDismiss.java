// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredUnitDismiss;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRGuiHiredDismiss extends LOTRGuiNPCInteract
{
    public LOTRGuiHiredDismiss(final LOTREntityNPC entity) {
        super(entity);
    }
    
    public void initGui() {
        super.buttonList.add(new GuiButton(0, super.width / 2 - 65, super.height / 5 * 3 + 40, 60, 20, StatCollector.translateToLocal("lotr.gui.dismiss.dismiss")));
        super.buttonList.add(new GuiButton(1, super.width / 2 + 5, super.height / 5 * 3 + 40, 60, 20, StatCollector.translateToLocal("lotr.gui.dismiss.cancel")));
    }
    
    @Override
    public void drawScreen(final int i, final int j, final float f) {
        super.drawScreen(i, j, f);
        String s = StatCollector.translateToLocal("lotr.gui.dismiss.warning1");
        int y = super.height / 5 * 3;
        super.fontRendererObj.drawString(s, (super.width - super.fontRendererObj.getStringWidth(s)) / 2, y, 16777215);
        y += super.fontRendererObj.FONT_HEIGHT;
        s = StatCollector.translateToLocal("lotr.gui.dismiss.warning2");
        super.fontRendererObj.drawString(s, (super.width - super.fontRendererObj.getStringWidth(s)) / 2, y, 16777215);
        y += super.fontRendererObj.FONT_HEIGHT;
        final Entity mount = ((Entity)super.theEntity).ridingEntity;
        final Entity rider = ((Entity)super.theEntity).riddenByEntity;
        final boolean hasMount = mount instanceof LOTREntityNPC && ((LOTREntityNPC)mount).hiredNPCInfo.getHiringPlayer() == super.mc.thePlayer;
        final boolean hasRider = rider instanceof LOTREntityNPC && ((LOTREntityNPC)rider).hiredNPCInfo.getHiringPlayer() == super.mc.thePlayer;
        if (hasMount) {
            s = StatCollector.translateToLocal("lotr.gui.dismiss.mount");
            super.fontRendererObj.drawString(s, (super.width - super.fontRendererObj.getStringWidth(s)) / 2, y, 11184810);
            y += super.fontRendererObj.FONT_HEIGHT;
        }
        if (hasRider) {
            s = StatCollector.translateToLocal("lotr.gui.dismiss.rider");
            super.fontRendererObj.drawString(s, (super.width - super.fontRendererObj.getStringWidth(s)) / 2, y, 11184810);
            y += super.fontRendererObj.FONT_HEIGHT;
        }
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id == 1) {
                super.mc.displayGuiScreen((GuiScreen)new LOTRGuiHiredInteract(super.theEntity));
                return;
            }
            final LOTRPacketHiredUnitDismiss packet = new LOTRPacketHiredUnitDismiss(super.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
}
