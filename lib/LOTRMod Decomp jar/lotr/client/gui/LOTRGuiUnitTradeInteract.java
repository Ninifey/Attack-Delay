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

public class LOTRGuiUnitTradeInteract extends LOTRGuiNPCInteract
{
    private GuiButton buttonTalk;
    private GuiButton buttonHire;
    
    public LOTRGuiUnitTradeInteract(final LOTREntityNPC entity) {
        super(entity);
    }
    
    public void initGui() {
        this.buttonTalk = new GuiButton(0, super.width / 2 - 65, super.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.talk"));
        this.buttonHire = new GuiButton(1, super.width / 2 + 5, super.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.hire"));
        super.buttonList.add(this.buttonTalk);
        super.buttonList.add(this.buttonHire);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            final LOTRPacketUnitTraderInteract packet = new LOTRPacketUnitTraderInteract(super.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
}
