// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import java.util.UUID;
import lotr.common.fellowship.LOTRFellowshipProfile;
import com.mojang.authlib.GameProfile;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import net.minecraft.util.StringUtils;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBannerData implements IMessage
{
    private int entityID;
    private boolean openGui;
    public boolean playerSpecificProtection;
    public boolean selfProtection;
    public boolean structureProtection;
    public int customRange;
    public float alignmentProtection;
    public int whitelistLength;
    public String[] whitelistSlots;
    
    public LOTRPacketBannerData() {
    }
    
    public LOTRPacketBannerData(final int id, final boolean flag) {
        this.entityID = id;
        this.openGui = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.openGui);
        data.writeBoolean(this.playerSpecificProtection);
        data.writeBoolean(this.selfProtection);
        data.writeBoolean(this.structureProtection);
        data.writeShort(this.customRange);
        data.writeFloat(this.alignmentProtection);
        data.writeShort(this.whitelistLength);
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
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.openGui = data.readBoolean();
        this.playerSpecificProtection = data.readBoolean();
        this.selfProtection = data.readBoolean();
        this.structureProtection = data.readBoolean();
        this.customRange = data.readShort();
        this.alignmentProtection = data.readFloat();
        this.whitelistLength = data.readShort();
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
    
    public static class Handler implements IMessageHandler<LOTRPacketBannerData, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBannerData packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityBanner) {
                final LOTREntityBanner banner = (LOTREntityBanner)entity;
                banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
                banner.setSelfProtection(packet.selfProtection);
                banner.setStructureProtection(packet.structureProtection);
                banner.setCustomRange(packet.customRange);
                banner.setAlignmentProtection(packet.alignmentProtection);
                banner.resizeWhitelist(packet.whitelistLength);
                for (int index = 0; index < packet.whitelistSlots.length; ++index) {
                    final String username = packet.whitelistSlots[index];
                    if (StringUtils.isNullOrEmpty(username)) {
                        banner.whitelistPlayer(index, null);
                    }
                    else if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
                        final String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
                        final LOTRFellowshipProfile profile = new LOTRFellowshipProfile(banner, null, fsName);
                        banner.whitelistPlayer(index, profile);
                    }
                    else {
                        final GameProfile profile2 = new GameProfile((UUID)null, username);
                        banner.whitelistPlayer(index, profile2);
                    }
                }
                if (packet.openGui) {
                    LOTRMod.proxy.displayBannerGui(banner);
                }
            }
            return null;
        }
    }
}
