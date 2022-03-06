// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.LOTRFellowshipClient;

public class LOTRPacketFellowshipRename extends LOTRPacketFellowshipDo
{
    private String newName;
    
    public LOTRPacketFellowshipRename() {
    }
    
    public LOTRPacketFellowshipRename(final LOTRFellowshipClient fs, final String name) {
        super(fs);
        this.newName = name;
    }
    
    @Override
    public void toBytes(final ByteBuf data) {
        super.toBytes(data);
        final byte[] nameBytes = this.newName.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
    }
    
    @Override
    public void fromBytes(final ByteBuf data) {
        super.fromBytes(data);
        final int nameLength = data.readByte();
        final ByteBuf nameBytes = data.readBytes(nameLength);
        this.newName = nameBytes.toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipRename, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipRename packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRFellowship fellowship = packet.getFellowship();
            if (fellowship != null) {
                final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                playerData.renameFellowship(fellowship, packet.newName);
            }
            return null;
        }
    }
}
