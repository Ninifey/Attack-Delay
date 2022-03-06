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
import lotr.common.world.map.LOTRWaypoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketWaypointRegion implements IMessage
{
    private LOTRWaypoint.Region region;
    private boolean unlocking;
    
    public LOTRPacketWaypointRegion() {
    }
    
    public LOTRPacketWaypointRegion(final LOTRWaypoint.Region r, final boolean flag) {
        this.region = r;
        this.unlocking = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.region.ordinal());
        data.writeBoolean(this.unlocking);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.region = LOTRWaypoint.regionForID(data.readByte());
        this.unlocking = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketWaypointRegion, IMessage>
    {
        public IMessage onMessage(final LOTRPacketWaypointRegion packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            final LOTRWaypoint.Region region = packet.region;
            if (region != null) {
                if (packet.unlocking) {
                    pd.unlockFTRegion(region);
                }
                else {
                    pd.lockFTRegion(region);
                }
            }
            return null;
        }
    }
}
