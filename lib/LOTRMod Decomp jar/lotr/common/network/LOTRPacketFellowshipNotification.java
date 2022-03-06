// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IChatComponent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFellowshipNotification implements IMessage
{
    private IChatComponent message;
    
    public LOTRPacketFellowshipNotification() {
    }
    
    public LOTRPacketFellowshipNotification(final IChatComponent c) {
        this.message = c;
    }
    
    public void toBytes(final ByteBuf data) {
        final String serialised = IChatComponent.Serializer.func_150696_a(this.message);
        final byte[] srlBytes = serialised.getBytes(Charsets.UTF_8);
        data.writeInt(srlBytes.length);
        data.writeBytes(srlBytes);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int length = data.readInt();
        final ByteBuf srlBytes = data.readBytes(length);
        final String serialised = srlBytes.toString(Charsets.UTF_8);
        this.message = IChatComponent.Serializer.func_150699_a(serialised);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipNotification, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipNotification packet, final MessageContext context) {
            LOTRMod.proxy.queueFellowshipNotification(packet.message);
            return null;
        }
    }
}
