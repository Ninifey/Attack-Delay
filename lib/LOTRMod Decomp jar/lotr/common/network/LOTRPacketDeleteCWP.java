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
import lotr.common.world.map.LOTRCustomWaypoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketDeleteCWP implements IMessage
{
    private int wpID;
    
    public LOTRPacketDeleteCWP() {
    }
    
    public LOTRPacketDeleteCWP(final LOTRCustomWaypoint wp) {
        this.wpID = wp.getID();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.wpID);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.wpID = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketDeleteCWP, IMessage>
    {
        public IMessage onMessage(final LOTRPacketDeleteCWP packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            final LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
            if (cwp != null) {
                pd.removeCustomWaypoint(cwp);
            }
            return null;
        }
    }
}
