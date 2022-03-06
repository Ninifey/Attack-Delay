// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketPortalPos implements IMessage
{
    private int portalX;
    private int portalY;
    private int portalZ;
    
    public LOTRPacketPortalPos() {
    }
    
    public LOTRPacketPortalPos(final int i, final int j, final int k) {
        this.portalX = i;
        this.portalY = j;
        this.portalZ = k;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.portalX);
        data.writeInt(this.portalY);
        data.writeInt(this.portalZ);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.portalX = data.readInt();
        this.portalY = data.readInt();
        this.portalZ = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketPortalPos, IMessage>
    {
        public IMessage onMessage(final LOTRPacketPortalPos packet, final MessageContext context) {
            LOTRLevelData.middleEarthPortalX = packet.portalX;
            LOTRLevelData.middleEarthPortalY = packet.portalY;
            LOTRLevelData.middleEarthPortalZ = packet.portalZ;
            return null;
        }
    }
}
