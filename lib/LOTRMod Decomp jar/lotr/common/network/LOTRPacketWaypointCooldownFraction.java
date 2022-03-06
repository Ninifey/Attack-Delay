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

public class LOTRPacketWaypointCooldownFraction implements IMessage
{
    private boolean isCustom;
    private int wpID;
    private float fraction;
    private UUID sharingPlayer;
    
    public LOTRPacketWaypointCooldownFraction() {
    }
    
    public LOTRPacketWaypointCooldownFraction(final LOTRAbstractWaypoint wp, final float f) {
        this.isCustom = (wp instanceof LOTRCustomWaypoint);
        this.wpID = wp.getID();
        this.fraction = f;
        if (wp instanceof LOTRCustomWaypoint) {
            this.sharingPlayer = ((LOTRCustomWaypoint)wp).getSharingPlayerID();
        }
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeBoolean(this.isCustom);
        data.writeInt(this.wpID);
        data.writeFloat(this.fraction);
        final boolean shared = this.sharingPlayer != null;
        data.writeBoolean(shared);
        if (shared) {
            data.writeLong(this.sharingPlayer.getMostSignificantBits());
            data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.isCustom = data.readBoolean();
        this.wpID = data.readInt();
        this.fraction = data.readFloat();
        final boolean shared = data.readBoolean();
        if (shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketWaypointCooldownFraction, IMessage>
    {
        public IMessage onMessage(final LOTRPacketWaypointCooldownFraction packet, final MessageContext context) {
            final boolean custom = packet.isCustom;
            final int wpID = packet.wpID;
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRAbstractWaypoint waypoint = null;
            if (!custom) {
                if (wpID >= 0 && wpID < LOTRWaypoint.values().length) {
                    waypoint = LOTRWaypoint.values()[wpID];
                }
            }
            else {
                final UUID sharingPlayerID = packet.sharingPlayer;
                if (sharingPlayerID != null) {
                    waypoint = pd.getSharedCustomWaypointByID(sharingPlayerID, wpID);
                }
                else {
                    waypoint = pd.getCustomWaypointByID(wpID);
                }
            }
            if (waypoint != null) {
                pd.setFTCooldownFraction(waypoint, packet.fraction);
            }
            return null;
        }
    }
}
