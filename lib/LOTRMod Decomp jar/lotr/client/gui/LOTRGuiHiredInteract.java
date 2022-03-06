// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredUnitInteract;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRGuiHiredInteract extends LOTRGuiNPCInteract
{
    public LOTRGuiHiredInteract(final LOTREntityNPC entity) {
        super(entity);
    }
    
    public void initGui() {
        super.buttonList.add(new GuiButton(0, super.width / 2 - 65, super.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.talk")));
        super.buttonList.add(new GuiButton(1, super.width / 2 + 5, super.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.command")));
        super.buttonList.add(new GuiButton(2, super.width / 2 - 65, super.height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("lotr.gui.npc.dismiss")));
        super.buttonList.get(0).enabled = (super.theEntity.getSpeechBank((EntityPlayer)super.mc.thePlayer) != null);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id == 2) {
                super.mc.displayGuiScreen((GuiScreen)new LOTRGuiHiredDismiss(super.theEntity));
                return;
            }
            final LOTRPacketHiredUnitInteract packet = new LOTRPacketHiredUnitInteract(super.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
        }
    }
}
