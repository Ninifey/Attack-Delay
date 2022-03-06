// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketUnitTraderInteract;
import net.minecraft.util.StatCollector;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiTradeUnitTradeInteract extends LOTRGuiTradeInteract
{
    private GuiButton buttonHire;
    
    public LOTRGuiTradeUnitTradeInteract(final LOTREntityNPC entity) {
        super(entity);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonHire = new GuiButton(-1, super.width / 2 - 65, super.height / 5 * 3 + 50, 130, 20, StatCollector.translateToLocal("lotr.gui.npc.hire"));
        super.buttonList.add(this.buttonHire);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonHire) {
                final LOTRPacketUnitTraderInteract packet = new LOTRPacketUnitTraderInteract(super.theEntity.getEntityId(), 1);
                LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
}
