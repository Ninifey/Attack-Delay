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
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import lotr.common.world.map.LOTRCustomWaypoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketRenameCWP implements IMessage
{
    private int wpID;
    private String name;
    
    public LOTRPacketRenameCWP() {
    }
    
    public LOTRPacketRenameCWP(final LOTRCustomWaypoint wp, final String s) {
        this.wpID = wp.getID();
        this.name = s;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.wpID);
        final byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.wpID = data.readInt();
        final int length = data.readShort();
        this.name = data.readBytes(length).toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketRenameCWP, IMessage>
    {
        public IMessage onMessage(final LOTRPacketRenameCWP packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            final LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
            if (cwp != null) {
                final String wpName = LOTRCustomWaypoint.validateCustomName(packet.name);
                if (wpName != null) {
                    pd.renameCustomWaypoint(cwp, wpName);
                }
            }
            return null;
        }
    }
}
