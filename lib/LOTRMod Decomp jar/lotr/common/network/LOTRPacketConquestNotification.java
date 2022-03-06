// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketConquestNotification implements IMessage
{
    private LOTRFaction conqFac;
    private float conqVal;
    private boolean isCleansing;
    
    public LOTRPacketConquestNotification() {
    }
    
    public LOTRPacketConquestNotification(final LOTRFaction fac, final float f, final boolean clean) {
        this.conqFac = fac;
        this.conqVal = f;
        this.isCleansing = clean;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.conqFac.ordinal());
        data.writeFloat(this.conqVal);
        data.writeBoolean(this.isCleansing);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int facID = data.readByte();
        this.conqFac = LOTRFaction.forID(facID);
        this.conqVal = data.readFloat();
        this.isCleansing = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketConquestNotification, IMessage>
    {
        public IMessage onMessage(final LOTRPacketConquestNotification packet, final MessageContext context) {
            if (packet.conqFac != null) {
                LOTRMod.proxy.queueConquestNotification(packet.conqFac, packet.conqVal, packet.isCleansing);
            }
            return null;
        }
    }
}
