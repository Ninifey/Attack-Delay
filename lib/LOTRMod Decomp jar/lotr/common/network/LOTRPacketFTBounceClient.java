// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFTBounceClient implements IMessage
{
    public void toBytes(final ByteBuf data) {
    }
    
    public void fromBytes(final ByteBuf data) {
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFTBounceClient, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFTBounceClient packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPacketFTBounceServer packetResponse = new LOTRPacketFTBounceServer();
            LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packetResponse);
            return null;
        }
    }
}
