// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketTraderInteract;
import lotr.common.entity.npc.LOTRTradeable;
import net.minecraft.util.StatCollector;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiTradeInteract extends LOTRGuiNPCInteract
{
    private GuiButton buttonTalk;
    private GuiButton buttonTrade;
    private GuiButton buttonExchange;
    private GuiButton buttonSmith;
    
    public LOTRGuiTradeInteract(final LOTREntityNPC entity) {
        super(entity);
    }
    
    public void initGui() {
        this.buttonTalk = new GuiButton(0, super.width / 2 - 65, super.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.talk"));
        this.buttonTrade = new GuiButton(1, super.width / 2 + 5, super.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.trade"));
        this.buttonExchange = new GuiButton(2, super.width / 2 - 65, super.height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("lotr.gui.npc.exchange"));
        super.buttonList.add(this.buttonTalk);
        super.buttonList.add(this.buttonTrade);
        super.buttonList.add(this.buttonExchange);
        if (super.theEntity instanceof LOTRTradeable.Smith) {
            final GuiButton buttonTalk = this.buttonTalk;
            buttonTalk.field_146128_h -= 35;
            final GuiButton buttonTrade = this.buttonTrade;
            buttonTrade.field_146128_h -= 35;
            this.buttonSmith = new GuiButton(3, super.width / 2 + 40, super.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.smith"));
            super.buttonList.add(this.buttonSmith);
        }
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            final LOTRPacketTraderInteract packet = new LOTRPacketTraderInteract(super.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
}
