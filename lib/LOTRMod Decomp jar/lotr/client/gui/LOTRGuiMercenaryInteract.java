// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMercenaryInteract;
import net.minecraft.client.gui.GuiButton;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRGuiMercenaryInteract extends LOTRGuiUnitTradeInteract
{
    public LOTRGuiMercenaryInteract(final LOTREntityNPC entity) {
        super(entity);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            final LOTRPacketMercenaryInteract packet = new LOTRPacketMercenaryInteract(super.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
}
