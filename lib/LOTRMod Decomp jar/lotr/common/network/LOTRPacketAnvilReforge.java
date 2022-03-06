// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.inventory.LOTRContainerAnvil;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAnvilReforge implements IMessage
{
    public void toBytes(final ByteBuf data) {
    }
    
    public void fromBytes(final ByteBuf data) {
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAnvilReforge, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAnvilReforge packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final Container container = ((EntityPlayer)entityplayer).openContainer;
            if (container instanceof LOTRContainerAnvil) {
                final LOTRContainerAnvil anvil = (LOTRContainerAnvil)container;
                anvil.reforgeItem();
            }
            return null;
        }
    }
}
