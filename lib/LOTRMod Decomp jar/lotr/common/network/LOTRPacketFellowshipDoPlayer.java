// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import java.util.UUID;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.LOTRFellowshipClient;

public class LOTRPacketFellowshipDoPlayer extends LOTRPacketFellowshipDo
{
    private String subjectUsername;
    private PlayerFunction function;
    
    public LOTRPacketFellowshipDoPlayer() {
    }
    
    public LOTRPacketFellowshipDoPlayer(final LOTRFellowshipClient fs, final String name, final PlayerFunction f) {
        super(fs);
        this.subjectUsername = name;
        this.function = f;
    }
    
    @Override
    public void toBytes(final ByteBuf data) {
        super.toBytes(data);
        final byte[] nameBytes = this.subjectUsername.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
        data.writeByte(this.function.ordinal());
    }
    
    @Override
    public void fromBytes(final ByteBuf data) {
        super.fromBytes(data);
        final int nameLength = data.readByte();
        final ByteBuf nameBytes = data.readBytes(nameLength);
        this.subjectUsername = nameBytes.toString(Charsets.UTF_8);
        this.function = PlayerFunction.values()[data.readByte()];
    }
    
    public UUID getSubjectPlayerUUID() {
        final GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(this.subjectUsername);
        if (profile != null && profile.getId() != null) {
            return profile.getId();
        }
        return null;
    }
    
    public enum PlayerFunction
    {
        INVITE, 
        REMOVE, 
        TRANSFER, 
        OP, 
        DEOP;
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipDoPlayer, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipDoPlayer packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRFellowship fellowship = packet.getFellowship();
            final UUID subjectPlayer = packet.getSubjectPlayerUUID();
            if (fellowship != null && subjectPlayer != null) {
                final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                if (packet.function == PlayerFunction.INVITE) {
                    playerData.invitePlayerToFellowship(fellowship, subjectPlayer);
                }
                else if (packet.function == PlayerFunction.REMOVE) {
                    playerData.removePlayerFromFellowship(fellowship, subjectPlayer);
                }
                else if (packet.function == PlayerFunction.TRANSFER) {
                    playerData.transferFellowship(fellowship, subjectPlayer);
                }
                else if (packet.function == PlayerFunction.OP) {
                    playerData.setFellowshipAdmin(fellowship, subjectPlayer, true);
                }
                else if (packet.function == PlayerFunction.DEOP) {
                    playerData.setFellowshipAdmin(fellowship, subjectPlayer, false);
                }
            }
            return null;
        }
    }
}
