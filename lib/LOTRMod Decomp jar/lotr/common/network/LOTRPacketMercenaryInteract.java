// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketMercenaryInteract extends LOTRPacketUnitTraderInteract
{
    public LOTRPacketMercenaryInteract() {
    }
    
    public LOTRPacketMercenaryInteract(final int idt, final int a) {
        super(idt, a);
    }
    
    @Override
    protected void openTradeGUI(final EntityPlayer entityplayer, final LOTREntityNPC trader) {
        entityplayer.openGui((Object)LOTRMod.instance, 59, ((Entity)entityplayer).worldObj, trader.getEntityId(), 0, 0);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMercenaryInteract, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMercenaryInteract message, final MessageContext ctx) {
            return new LOTRPacketUnitTraderInteract.Handler().onMessage(message, ctx);
        }
    }
}
