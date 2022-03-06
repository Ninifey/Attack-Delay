// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatAllowedCharacters;
import org.apache.commons.lang3.StringUtils;
import lotr.common.inventory.LOTRContainerAnvil;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketAnvilRename implements IMessage
{
    private String rename;
    
    public LOTRPacketAnvilRename() {
    }
    
    public LOTRPacketAnvilRename(final String s) {
        this.rename = s;
    }
    
    public void toBytes(final ByteBuf data) {
        final byte[] nameBytes = this.rename.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int nameLength = data.readShort();
        final ByteBuf nameBytes = data.readBytes(nameLength);
        this.rename = nameBytes.toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketAnvilRename, IMessage>
    {
        public IMessage onMessage(final LOTRPacketAnvilRename packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final Container container = ((EntityPlayer)entityplayer).openContainer;
            if (container instanceof LOTRContainerAnvil) {
                final LOTRContainerAnvil anvil = (LOTRContainerAnvil)container;
                String rename = packet.rename;
                if (rename != null && !StringUtils.isBlank((CharSequence)rename)) {
                    rename = ChatAllowedCharacters.filerAllowedCharacters(rename);
                    if (rename.length() <= 30) {
                        anvil.updateItemName(rename);
                    }
                }
                else {
                    anvil.updateItemName("");
                }
            }
            return null;
        }
    }
}
