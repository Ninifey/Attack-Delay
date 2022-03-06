// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAlignDrain implements IMessage
{
    private int numFactions;
    
    public LOTRPacketAlignDrain() {
    }
    
    public LOTRPacketAlignDrain(final int num) {
        this.numFactions = num;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.numFactions);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.numFactions = data.readByte();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAlignDrain, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAlignDrain packet, final MessageContext context) {
            LOTRMod.proxy.displayAlignDrain(packet.numFactions);
            return null;
        }
    }
}
