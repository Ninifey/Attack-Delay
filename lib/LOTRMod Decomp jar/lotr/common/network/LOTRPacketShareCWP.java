// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.fellowship.LOTRFellowship;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import lotr.common.world.map.LOTRCustomWaypoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketShareCWP implements IMessage
{
    private int wpID;
    private String fsName;
    private boolean adding;
    
    public LOTRPacketShareCWP() {
    }
    
    public LOTRPacketShareCWP(final LOTRCustomWaypoint wp, final String s, final boolean add) {
        this.wpID = wp.getID();
        this.fsName = s;
        this.adding = add;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.wpID);
        final byte[] nameBytes = this.fsName.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
        data.writeBoolean(this.adding);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.wpID = data.readInt();
        final int length = data.readShort();
        this.fsName = data.readBytes(length).toString(Charsets.UTF_8);
        this.adding = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketShareCWP, IMessage>
    {
        public IMessage onMessage(final LOTRPacketShareCWP packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            final LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
            if (cwp != null) {
                final LOTRFellowship fellowship = pd.getFellowshipByName(packet.fsName);
                if (fellowship != null) {
                    if (packet.adding) {
                        pd.customWaypointAddSharedFellowship(cwp, fellowship);
                    }
                    else {
                        pd.customWaypointRemoveSharedFellowship(cwp, fellowship);
                    }
                }
            }
            return null;
        }
    }
}
