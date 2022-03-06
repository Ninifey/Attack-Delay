// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketPledge implements IMessage
{
    private LOTRFaction pledgeFac;
    
    public LOTRPacketPledge() {
    }
    
    public LOTRPacketPledge(final LOTRFaction f) {
        this.pledgeFac = f;
    }
    
    public void toBytes(final ByteBuf data) {
        int facID;
        if (this.pledgeFac == null) {
            facID = -1;
        }
        else {
            facID = this.pledgeFac.ordinal();
        }
        data.writeByte(facID);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int facID = data.readByte();
        if (facID == -1) {
            this.pledgeFac = null;
        }
        else {
            this.pledgeFac = LOTRFaction.forID(facID);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketPledge, IMessage>
    {
        public IMessage onMessage(final LOTRPacketPledge packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                pd.setPledgeFaction(packet.pledgeFac);
            }
            return null;
        }
    }
}
