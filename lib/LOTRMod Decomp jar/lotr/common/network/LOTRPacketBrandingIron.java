// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.item.LOTRItemBrandingIron;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBrandingIron implements IMessage
{
    private String brandName;
    
    public LOTRPacketBrandingIron() {
    }
    
    public LOTRPacketBrandingIron(final String s) {
        this.brandName = s;
    }
    
    public void toBytes(final ByteBuf data) {
        if (StringUtils.isBlank((CharSequence)this.brandName)) {
            data.writeInt(-1);
        }
        else {
            final byte[] brandBytes = this.brandName.getBytes(Charsets.UTF_8);
            data.writeInt(brandBytes.length);
            data.writeBytes(brandBytes);
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        final int length = data.readInt();
        if (length > -1) {
            this.brandName = data.readBytes(length).toString(Charsets.UTF_8);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBrandingIron, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBrandingIron packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
            if (itemstack != null && itemstack.getItem() instanceof LOTRItemBrandingIron) {
                String brandName = packet.brandName;
                brandName = LOTRItemBrandingIron.trimAcceptableBrandName(brandName);
                if (!StringUtils.isBlank((CharSequence)brandName) && !LOTRItemBrandingIron.hasBrandName(itemstack)) {
                    LOTRItemBrandingIron.setBrandName(itemstack, brandName);
                }
            }
            return null;
        }
    }
}
