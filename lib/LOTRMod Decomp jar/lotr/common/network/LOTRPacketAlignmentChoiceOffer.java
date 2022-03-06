// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAlignmentChoiceOffer implements IMessage
{
    public void toBytes(final ByteBuf data) {
    }
    
    public void fromBytes(final ByteBuf data) {
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAlignmentChoiceOffer, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAlignmentChoiceOffer packet, final MessageContext context) {
            LOTRMod.proxy.displayAlignmentChoice();
            return null;
        }
    }
}
