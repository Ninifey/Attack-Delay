// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import lotr.common.fellowship.LOTRFellowshipProfile;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import net.minecraft.util.StringUtils;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.item.LOTREntityBanner;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketEditBanner implements IMessage
{
    private int bannerID;
    public boolean playerSpecificProtection;
    public boolean selfProtection;
    public float alignmentProtection;
    public int whitelistLength;
    public String[] whitelistSlots;
    
    public LOTRPacketEditBanner() {
    }
    
    public LOTRPacketEditBanner(final LOTREntityBanner banner) {
        this.bannerID = banner.getEntityId();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.bannerID);
        data.writeBoolean(this.playerSpecificProtection);
        data.writeBoolean(this.selfProtection);
        data.writeFloat(this.alignmentProtection);
        data.writeShort(this.whitelistLength);
        final boolean sendUsernames = this.whitelistSlots != null;
        data.writeBoolean(sendUsernames);
        if (sendUsernames) {
            data.writeShort(this.whitelistSlots.length);
            for (int index = 0; index < this.whitelistSlots.length; ++index) {
                data.writeShort(index);
                final String username = this.whitelistSlots[index];
                if (StringUtils.isNullOrEmpty(username)) {
                    data.writeByte(-1);
                }
                else {
                    final byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
                    data.writeByte(usernameBytes.length);
                    data.writeBytes(usernameBytes);
                }
            }
            data.writeShort(-1);
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.bannerID = data.readInt();
        this.playerSpecificProtection = data.readBoolean();
        this.selfProtection = data.readBoolean();
        this.alignmentProtection = data.readFloat();
        this.whitelistLength = data.readShort();
        final boolean sendUsernames = data.readBoolean();
        if (sendUsernames) {
            this.whitelistSlots = new String[data.readShort()];
            int index = 0;
            while ((index = data.readShort()) >= 0) {
                final int length = data.readByte();
                if (length == -1) {
                    this.whitelistSlots[index] = null;
                }
                else {
                    final ByteBuf usernameBytes = data.readBytes(length);
                    final String name = usernameBytes.toString(Charsets.UTF_8);
                    this.whitelistSlots[index] = name;
                }
            }
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketEditBanner, IMessage>
    {
        public IMessage onMessage(final LOTRPacketEditBanner packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity bEntity = world.getEntityByID(packet.bannerID);
            if (bEntity instanceof LOTREntityBanner) {
                final LOTREntityBanner banner = (LOTREntityBanner)bEntity;
                if (banner.canPlayerEditBanner((EntityPlayer)entityplayer)) {
                    banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
                    banner.setSelfProtection(packet.selfProtection);
                    banner.setAlignmentProtection(packet.alignmentProtection);
                    banner.resizeWhitelist(packet.whitelistLength);
                    if (packet.whitelistSlots != null) {
                        for (int index = 0; index < packet.whitelistSlots.length; ++index) {
                            if (index != 0) {
                                final String username = packet.whitelistSlots[index];
                                if (StringUtils.isNullOrEmpty(username)) {
                                    banner.whitelistPlayer(index, null);
                                }
                                else if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
                                    final String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
                                    final LOTRFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
                                    if (fellowship != null) {
                                        banner.whitelistFellowship(index, fellowship);
                                    }
                                }
                                else {
                                    final GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
                                    if (profile != null) {
                                        banner.whitelistPlayer(index, profile);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
    }
}
