// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketUpdateViewingFaction implements IMessage
{
    private LOTRFaction viewingFaction;
    
    public LOTRPacketUpdateViewingFaction() {
    }
    
    public LOTRPacketUpdateViewingFaction(final LOTRFaction f) {
        this.viewingFaction = f;
    }
    
    public void toBytes(final ByteBuf data) {
        final int facID = this.viewingFaction.ordinal();
        data.writeByte(facID);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int facID = data.readByte();
        this.viewingFaction = LOTRFaction.forID(facID);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketUpdateViewingFaction, IMessage>
    {
        public IMessage onMessage(final LOTRPacketUpdateViewingFaction packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRLevelData.getData(entityplayer).setViewingFaction(packet.viewingFaction);
            return null;
        }
    }
}
