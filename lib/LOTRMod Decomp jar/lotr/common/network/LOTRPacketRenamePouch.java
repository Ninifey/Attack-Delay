// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.inventory.LOTRContainerPouch;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketRenamePouch implements IMessage
{
    private String name;
    
    public LOTRPacketRenamePouch() {
    }
    
    public LOTRPacketRenamePouch(final String s) {
        this.name = s;
    }
    
    public void toBytes(final ByteBuf data) {
        final byte[] nameData = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameData.length);
        data.writeBytes(nameData);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int length = data.readShort();
        this.name = data.readBytes(length).toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketRenamePouch, IMessage>
    {
        public IMessage onMessage(final LOTRPacketRenamePouch packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final Container container = ((EntityPlayer)entityplayer).openContainer;
            if (container != null && container instanceof LOTRContainerPouch) {
                final LOTRContainerPouch pouch = (LOTRContainerPouch)container;
                pouch.renamePouch(packet.name);
            }
            return null;
        }
    }
}
