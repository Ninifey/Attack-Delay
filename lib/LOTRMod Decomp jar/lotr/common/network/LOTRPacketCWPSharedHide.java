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
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketCWPSharedHide implements IMessage
{
    private int cwpID;
    private UUID sharingPlayer;
    private boolean hideCWP;
    
    public LOTRPacketCWPSharedHide() {
    }
    
    public LOTRPacketCWPSharedHide(final LOTRCustomWaypoint cwp, final boolean hide) {
        this.cwpID = cwp.getID();
        this.sharingPlayer = cwp.getSharingPlayerID();
        this.hideCWP = hide;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.cwpID);
        data.writeLong(this.sharingPlayer.getMostSignificantBits());
        data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        data.writeBoolean(this.hideCWP);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.cwpID = data.readInt();
        this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        this.hideCWP = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketCWPSharedHide, IMessage>
    {
        public IMessage onMessage(final LOTRPacketCWPSharedHide packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            final LOTRCustomWaypoint cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
            if (cwp != null) {
                pd.hideOrUnhideSharedCustomWaypoint(cwp, packet.hideCWP);
            }
            return null;
        }
    }
}
