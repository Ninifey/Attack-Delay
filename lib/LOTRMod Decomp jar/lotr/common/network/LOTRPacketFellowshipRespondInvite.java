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

public class LOTRPacketFellowshipRespondInvite extends LOTRPacketFellowshipDo
{
    private boolean acceptOrReject;
    
    public LOTRPacketFellowshipRespondInvite() {
    }
    
    public LOTRPacketFellowshipRespondInvite(final LOTRFellowshipClient fs, final boolean accept) {
        super(fs);
        this.acceptOrReject = accept;
    }
    
    @Override
    public void toBytes(final ByteBuf data) {
        super.toBytes(data);
        data.writeBoolean(this.acceptOrReject);
    }
    
    @Override
    public void fromBytes(final ByteBuf data) {
        super.fromBytes(data);
        this.acceptOrReject = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipRespondInvite, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipRespondInvite packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRFellowship fellowship = packet.getFellowship();
            if (fellowship != null) {
                final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                if (packet.acceptOrReject) {
                    playerData.acceptFellowshipInvite(fellowship);
                }
                else {
                    playerData.rejectFellowshipInvite(fellowship);
                }
            }
            return null;
        }
    }
}
