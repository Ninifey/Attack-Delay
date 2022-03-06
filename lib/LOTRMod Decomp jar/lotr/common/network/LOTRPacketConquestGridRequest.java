// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.map.LOTRConquestGrid;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketConquestGridRequest implements IMessage
{
    private LOTRFaction conqFac;
    
    public LOTRPacketConquestGridRequest() {
    }
    
    public LOTRPacketConquestGridRequest(final LOTRFaction fac) {
        this.conqFac = fac;
    }
    
    public void toBytes(final ByteBuf data) {
        final int facID = this.conqFac.ordinal();
        data.writeByte(facID);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int facID = data.readByte();
        this.conqFac = LOTRFaction.forID(facID);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketConquestGridRequest, IMessage>
    {
        public IMessage onMessage(final LOTRPacketConquestGridRequest packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRFaction fac = packet.conqFac;
            if (fac != null) {
                final LOTRConquestGrid.ConquestViewableQuery query = LOTRConquestGrid.canPlayerViewConquest((EntityPlayer)entityplayer, fac);
                if (query.result == LOTRConquestGrid.ConquestViewable.CAN_VIEW) {
                    LOTRConquestGrid.sendConquestGridTo(entityplayer, fac);
                }
            }
            return null;
        }
    }
}
