// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketIsOpResponse implements IMessage
{
    private boolean isOp;
    
    public LOTRPacketIsOpResponse() {
    }
    
    public LOTRPacketIsOpResponse(final boolean flag) {
        this.isOp = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeBoolean(this.isOp);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.isOp = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketIsOpResponse, IMessage>
    {
        public IMessage onMessage(final LOTRPacketIsOpResponse packet, final MessageContext context) {
            LOTRMod.proxy.setMapIsOp(packet.isOp);
            return null;
        }
    }
}
