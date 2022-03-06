// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

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

public class LOTRPacketDeleteCWPClient implements IMessage
{
    private int cwpID;
    private UUID sharingPlayer;
    
    public LOTRPacketDeleteCWPClient() {
    }
    
    public LOTRPacketDeleteCWPClient(final int id) {
        this.cwpID = id;
    }
    
    public LOTRPacketDeleteCWPClient setSharingPlayer(final UUID player) {
        this.sharingPlayer = player;
        return this;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.cwpID);
        final boolean shared = this.sharingPlayer != null;
        data.writeBoolean(shared);
        if (shared) {
            data.writeLong(this.sharingPlayer.getMostSignificantBits());
            data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.cwpID = data.readInt();
        final boolean shared = data.readBoolean();
        if (shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketDeleteCWPClient, IMessage>
    {
        public IMessage onMessage(final LOTRPacketDeleteCWPClient packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if (packet.sharingPlayer != null) {
                if (!LOTRMod.proxy.isSingleplayer()) {
                    final LOTRCustomWaypoint cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
                    if (cwp != null) {
                        pd.removeSharedCustomWaypoint(cwp);
                    }
                }
            }
            else if (!LOTRMod.proxy.isSingleplayer()) {
                final LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.cwpID);
                if (cwp != null) {
                    pd.removeCustomWaypoint(cwp);
                }
            }
            return null;
        }
    }
}
