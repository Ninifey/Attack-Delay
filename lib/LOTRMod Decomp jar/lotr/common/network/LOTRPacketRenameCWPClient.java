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
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketRenameCWPClient implements IMessage
{
    private int cwpID;
    private String name;
    private UUID sharingPlayer;
    
    public LOTRPacketRenameCWPClient() {
    }
    
    public LOTRPacketRenameCWPClient(final int id, final String s) {
        this.cwpID = id;
        this.name = s;
    }
    
    public LOTRPacketRenameCWPClient setSharingPlayer(final UUID player) {
        this.sharingPlayer = player;
        return this;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.cwpID);
        final byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
        final boolean shared = this.sharingPlayer != null;
        data.writeBoolean(shared);
        if (shared) {
            data.writeLong(this.sharingPlayer.getMostSignificantBits());
            data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.cwpID = data.readInt();
        final int length = data.readShort();
        this.name = data.readBytes(length).toString(Charsets.UTF_8);
        final boolean shared = data.readBoolean();
        if (shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketRenameCWPClient, IMessage>
    {
        public IMessage onMessage(final LOTRPacketRenameCWPClient packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if (packet.sharingPlayer != null) {
                if (!LOTRMod.proxy.isSingleplayer()) {
                    final LOTRCustomWaypoint cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
                    if (cwp != null) {
                        pd.renameSharedCustomWaypoint(cwp, packet.name);
                    }
                }
            }
            else if (!LOTRMod.proxy.isSingleplayer()) {
                final LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.cwpID);
                if (cwp != null) {
                    pd.renameCustomWaypoint(cwp, packet.name);
                }
            }
            return null;
        }
    }
}
