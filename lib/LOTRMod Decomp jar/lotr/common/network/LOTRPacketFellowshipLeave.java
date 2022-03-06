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
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.LOTRFellowshipClient;

public class LOTRPacketFellowshipLeave extends LOTRPacketFellowshipDo
{
    public LOTRPacketFellowshipLeave() {
    }
    
    public LOTRPacketFellowshipLeave(final LOTRFellowshipClient fs) {
        super(fs);
    }
    
    @Override
    public void toBytes(final ByteBuf data) {
        super.toBytes(data);
    }
    
    @Override
    public void fromBytes(final ByteBuf data) {
        super.fromBytes(data);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipLeave, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipLeave packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRFellowship fellowship = packet.getFellowship();
            if (fellowship != null) {
                final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                playerData.leaveFellowship(fellowship);
            }
            return null;
        }
    }
}
