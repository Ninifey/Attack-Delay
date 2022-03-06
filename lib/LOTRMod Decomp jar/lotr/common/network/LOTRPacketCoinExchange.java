// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.inventory.LOTRContainerCoinExchange;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketCoinExchange implements IMessage
{
    private int button;
    
    public LOTRPacketCoinExchange() {
    }
    
    public LOTRPacketCoinExchange(final int i) {
        this.button = i;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.button);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.button = data.readByte();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketCoinExchange, IMessage>
    {
        public IMessage onMessage(final LOTRPacketCoinExchange packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final Container container = ((EntityPlayer)entityplayer).openContainer;
            if (container != null && container instanceof LOTRContainerCoinExchange) {
                final LOTRContainerCoinExchange coinExchange = (LOTRContainerCoinExchange)container;
                coinExchange.handleExchangePacket(packet.button);
            }
            return null;
        }
    }
}
