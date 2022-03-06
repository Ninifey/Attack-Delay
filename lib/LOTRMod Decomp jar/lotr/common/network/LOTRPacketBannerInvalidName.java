// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBannerInvalidName implements IMessage
{
    private int entityID;
    private int slot;
    private String prevText;
    
    public LOTRPacketBannerInvalidName() {
    }
    
    public LOTRPacketBannerInvalidName(final int id, final int s, final String pre) {
        this.entityID = id;
        this.slot = s;
        this.prevText = pre;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeInt(this.slot);
        final byte[] textBytes = this.prevText.getBytes(Charsets.UTF_8);
        data.writeByte(textBytes.length);
        data.writeBytes(textBytes);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.slot = data.readInt();
        final int length = data.readByte();
        final ByteBuf textBytes = data.readBytes(length);
        this.prevText = textBytes.toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBannerInvalidName, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBannerInvalidName packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityBanner) {
                final LOTREntityBanner banner = (LOTREntityBanner)entity;
                LOTRMod.proxy.invalidateBannerUsername(banner, packet.slot, packet.prevText);
            }
            return null;
        }
    }
}
