// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.LOTRSquadrons;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import net.minecraft.util.StringUtils;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketItemSquadron implements IMessage
{
    private String squadron;
    
    public LOTRPacketItemSquadron() {
    }
    
    public LOTRPacketItemSquadron(final String s) {
        this.squadron = s;
    }
    
    public void toBytes(final ByteBuf data) {
        if (StringUtils.isNullOrEmpty(this.squadron)) {
            data.writeInt(-1);
        }
        else {
            final byte[] sqBytes = this.squadron.getBytes(Charsets.UTF_8);
            data.writeInt(sqBytes.length);
            data.writeBytes(sqBytes);
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        final int length = data.readInt();
        if (length > -1) {
            this.squadron = data.readBytes(length).toString(Charsets.UTF_8);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketItemSquadron, IMessage>
    {
        public IMessage onMessage(final LOTRPacketItemSquadron packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
            if (itemstack != null && itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
                String squadron = packet.squadron;
                if (!StringUtils.isNullOrEmpty(squadron)) {
                    squadron = LOTRSquadrons.checkAcceptableLength(squadron);
                    LOTRSquadrons.setSquadron(itemstack, squadron);
                }
                else {
                    LOTRSquadrons.setSquadron(itemstack, "");
                }
            }
            return null;
        }
    }
}
