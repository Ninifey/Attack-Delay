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
import lotr.common.fellowship.LOTRFellowship;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFellowshipRemove implements IMessage
{
    private UUID fellowshipID;
    private boolean isInvite;
    
    public LOTRPacketFellowshipRemove() {
    }
    
    public LOTRPacketFellowshipRemove(final LOTRFellowship fs, final boolean invite) {
        this.fellowshipID = fs.getFellowshipID();
        this.isInvite = invite;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeLong(this.fellowshipID.getMostSignificantBits());
        data.writeLong(this.fellowshipID.getLeastSignificantBits());
        data.writeBoolean(this.isInvite);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.fellowshipID = new UUID(data.readLong(), data.readLong());
        this.isInvite = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipRemove, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipRemove packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            if (packet.isInvite) {
                LOTRLevelData.getData(entityplayer).removeClientFellowshipInvite(packet.fellowshipID);
            }
            else {
                LOTRLevelData.getData(entityplayer).removeClientFellowship(packet.fellowshipID);
            }
            return null;
        }
    }
}
