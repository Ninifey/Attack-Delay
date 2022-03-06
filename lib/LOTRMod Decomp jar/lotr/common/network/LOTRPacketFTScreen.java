// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.world.map.LOTRAbstractWaypoint;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFTScreen implements IMessage
{
    private boolean isCustom;
    private int wpID;
    private UUID sharingPlayer;
    private int startX;
    private int startZ;
    
    public LOTRPacketFTScreen() {
    }
    
    public LOTRPacketFTScreen(final LOTRAbstractWaypoint wp, final int x, final int z) {
        this.isCustom = (wp instanceof LOTRCustomWaypoint);
        this.wpID = wp.getID();
        if (wp instanceof LOTRCustomWaypoint) {
            this.sharingPlayer = ((LOTRCustomWaypoint)wp).getSharingPlayerID();
        }
        this.startX = x;
        this.startZ = z;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeBoolean(this.isCustom);
        data.writeInt(this.wpID);
        final boolean shared = this.sharingPlayer != null;
        data.writeBoolean(shared);
        if (shared) {
            data.writeLong(this.sharingPlayer.getMostSignificantBits());
            data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        }
        data.writeInt(this.startX);
        data.writeInt(this.startZ);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.isCustom = data.readBoolean();
        this.wpID = data.readInt();
        final boolean shared = data.readBoolean();
        if (shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        }
        this.startX = data.readInt();
        this.startZ = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFTScreen, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFTScreen packet, final MessageContext context) {
            final boolean custom = packet.isCustom;
            final int wpID = packet.wpID;
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
            LOTRAbstractWaypoint waypoint = null;
            if (!custom) {
                if (wpID >= 0 && wpID < LOTRWaypoint.values().length) {
                    waypoint = LOTRWaypoint.values()[wpID];
                }
            }
            else {
                final UUID sharingPlayerID = packet.sharingPlayer;
                if (sharingPlayerID != null) {
                    waypoint = playerData.getSharedCustomWaypointByID(sharingPlayerID, wpID);
                }
                else {
                    waypoint = playerData.getCustomWaypointByID(wpID);
                }
            }
            if (waypoint != null) {
                LOTRMod.proxy.displayFTScreen(waypoint, packet.startX, packet.startZ);
            }
            return null;
        }
    }
}
