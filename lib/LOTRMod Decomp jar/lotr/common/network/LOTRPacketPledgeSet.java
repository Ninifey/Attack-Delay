// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketPledgeSet implements IMessage
{
    private LOTRFaction pledgeFac;
    
    public LOTRPacketPledgeSet() {
    }
    
    public LOTRPacketPledgeSet(final LOTRFaction f) {
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
    
    public static class Handler implements IMessageHandler<LOTRPacketPledgeSet, IMessage>
    {
        public IMessage onMessage(final LOTRPacketPledgeSet packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            final LOTRFaction fac = packet.pledgeFac;
            if (fac == null) {
                pd.revokePledgeFaction((EntityPlayer)entityplayer, true);
            }
            else if (pd.canPledgeTo(fac) && pd.canMakeNewPledge()) {
                pd.setPledgeFaction(fac);
            }
            return null;
        }
    }
}
