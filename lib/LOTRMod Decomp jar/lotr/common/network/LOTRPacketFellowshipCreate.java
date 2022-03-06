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
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFellowshipCreate implements IMessage
{
    private String fellowshipName;
    
    public LOTRPacketFellowshipCreate() {
    }
    
    public LOTRPacketFellowshipCreate(final String name) {
        this.fellowshipName = name;
    }
    
    public void toBytes(final ByteBuf data) {
        final byte[] nameBytes = this.fellowshipName.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int nameLength = data.readByte();
        final ByteBuf nameBytes = data.readBytes(nameLength);
        this.fellowshipName = nameBytes.toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipCreate, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipCreate packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            playerData.createFellowship(packet.fellowshipName, true);
            return null;
        }
    }
}
