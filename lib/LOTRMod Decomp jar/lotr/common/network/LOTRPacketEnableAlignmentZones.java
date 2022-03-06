// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketEnableAlignmentZones implements IMessage
{
    private boolean enable;
    
    public LOTRPacketEnableAlignmentZones() {
    }
    
    public LOTRPacketEnableAlignmentZones(final boolean flag) {
        this.enable = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeBoolean(this.enable);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.enable = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketEnableAlignmentZones, IMessage>
    {
        public IMessage onMessage(final LOTRPacketEnableAlignmentZones packet, final MessageContext context) {
            LOTRLevelData.setEnableAlignmentZones(packet.enable);
            return null;
        }
    }
}
