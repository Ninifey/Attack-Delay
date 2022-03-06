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

public class LOTRPacketCWPSharedUnlockClient implements IMessage
{
    private int cwpID;
    private UUID sharingPlayer;
    
    public LOTRPacketCWPSharedUnlockClient() {
    }
    
    public LOTRPacketCWPSharedUnlockClient(final int id, final UUID player) {
        this.cwpID = id;
        this.sharingPlayer = player;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.cwpID);
        data.writeLong(this.sharingPlayer.getMostSignificantBits());
        data.writeLong(this.sharingPlayer.getLeastSignificantBits());
    }
    
    public void fromBytes(final ByteBuf data) {
        this.cwpID = data.readInt();
        this.sharingPlayer = new UUID(data.readLong(), data.readLong());
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketCWPSharedUnlockClient, IMessage>
    {
        public IMessage onMessage(final LOTRPacketCWPSharedUnlockClient packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                final LOTRCustomWaypoint cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
                if (cwp != null) {
                    pd.unlockSharedCustomWaypoint(cwp);
                }
            }
            return null;
        }
    }
}
