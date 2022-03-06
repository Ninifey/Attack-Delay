// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import com.mojang.authlib.GameProfile;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import lotr.common.fellowship.LOTRFellowshipProfile;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.item.LOTREntityBanner;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBannerRequestInvalidName implements IMessage
{
    private int bannerID;
    private int slot;
    private String username;
    
    public LOTRPacketBannerRequestInvalidName() {
    }
    
    public LOTRPacketBannerRequestInvalidName(final LOTREntityBanner banner, final int i, final String s) {
        this.bannerID = banner.getEntityId();
        this.slot = i;
        this.username = s;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.bannerID);
        data.writeShort(this.slot);
        final byte[] nameBytes = this.username.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.bannerID = data.readInt();
        this.slot = data.readShort();
        final int length = data.readByte();
        this.username = data.readBytes(length).toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBannerRequestInvalidName, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBannerRequestInvalidName packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity bEntity = world.getEntityByID(packet.bannerID);
            if (bEntity instanceof LOTREntityBanner) {
                final LOTREntityBanner banner = (LOTREntityBanner)bEntity;
                final String username = packet.username;
                boolean invalid = false;
                if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
                    final String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
                    final LOTRFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
                    if (fellowship == null) {
                        invalid = true;
                    }
                }
                else {
                    final GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(packet.username);
                    if (profile == null) {
                        invalid = true;
                    }
                }
                if (invalid) {
                    final LOTRPacketBannerInvalidName packetResponse = new LOTRPacketBannerInvalidName(banner.getEntityId(), packet.slot, packet.username);
                    LOTRPacketHandler.networkWrapper.sendTo((IMessage)packetResponse, entityplayer);
                }
            }
            return null;
        }
    }
}
