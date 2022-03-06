// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketShareCWPClient implements IMessage
{
    private int cwpID;
    private UUID fellowshipID;
    private boolean adding;
    
    public LOTRPacketShareCWPClient() {
    }
    
    public LOTRPacketShareCWPClient(final int id, final UUID fsID, final boolean add) {
        this.cwpID = id;
        this.fellowshipID = fsID;
        this.adding = add;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.cwpID);
        data.writeLong(this.fellowshipID.getMostSignificantBits());
        data.writeLong(this.fellowshipID.getLeastSignificantBits());
        data.writeBoolean(this.adding);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.cwpID = data.readInt();
        this.fellowshipID = new UUID(data.readLong(), data.readLong());
        this.adding = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketShareCWPClient, IMessage>
    {
        public IMessage onMessage(final LOTRPacketShareCWPClient packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                final LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.cwpID);
                if (cwp != null) {
                    final LOTRFellowshipClient fsClient = pd.getClientFellowshipByID(packet.fellowshipID);
                    if (fsClient != null) {
                        if (packet.adding) {
                            pd.customWaypointAddSharedFellowshipClient(cwp, fsClient);
                        }
                        else {
                            pd.customWaypointRemoveSharedFellowshipClient(cwp, fsClient);
                        }
                    }
                }
            }
            return null;
        }
    }
}
