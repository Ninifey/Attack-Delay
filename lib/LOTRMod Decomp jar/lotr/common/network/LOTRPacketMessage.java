// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.FMLLog;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRGuiMessageTypes;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMessage implements IMessage
{
    private LOTRGuiMessageTypes message;
    
    public LOTRPacketMessage() {
    }
    
    public LOTRPacketMessage(final LOTRGuiMessageTypes m) {
        this.message = m;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.message.ordinal());
    }
    
    public void fromBytes(final ByteBuf data) {
        final int messageID = data.readByte();
        if (messageID < 0 || messageID >= LOTRGuiMessageTypes.values().length) {
            FMLLog.severe("Failed to display LOTR message: There is no message with ID " + messageID, new Object[0]);
            this.message = null;
        }
        else {
            this.message = LOTRGuiMessageTypes.values()[messageID];
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMessage, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMessage packet, final MessageContext context) {
            if (packet.message != null) {
                LOTRMod.proxy.displayMessage(packet.message);
            }
            return null;
        }
    }
}
