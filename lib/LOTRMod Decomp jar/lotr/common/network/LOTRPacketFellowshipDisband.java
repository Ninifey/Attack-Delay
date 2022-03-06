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
import lotr.common.fellowship.LOTRFellowshipClient;

public class LOTRPacketFellowshipDisband extends LOTRPacketFellowshipDo
{
    public LOTRPacketFellowshipDisband() {
    }
    
    public LOTRPacketFellowshipDisband(final LOTRFellowshipClient fs) {
        super(fs);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipDisband, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipDisband packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRFellowship fellowship = packet.getFellowship();
            if (fellowship != null) {
                final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                playerData.disbandFellowship(fellowship);
            }
            return null;
        }
    }
}
