// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFTCooldown implements IMessage
{
    private int cooldownMax;
    private int cooldownMin;
    
    public LOTRPacketFTCooldown() {
    }
    
    public LOTRPacketFTCooldown(final int max, final int min) {
        this.cooldownMax = max;
        this.cooldownMin = min;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.cooldownMax);
        data.writeInt(this.cooldownMin);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.cooldownMax = data.readInt();
        this.cooldownMin = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFTCooldown, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFTCooldown packet, final MessageContext context) {
            LOTRLevelData.setFTCooldown(packet.cooldownMax, packet.cooldownMin);
            return null;
        }
    }
}
