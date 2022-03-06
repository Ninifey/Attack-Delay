// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.LOTRConfig;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.world.map.LOTRAbstractWaypoint;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFastTravel implements IMessage
{
    private boolean isCustom;
    private int wpID;
    private UUID sharingPlayer;
    
    public LOTRPacketFastTravel() {
    }
    
    public LOTRPacketFastTravel(final LOTRAbstractWaypoint wp) {
        this.isCustom = (wp instanceof LOTRCustomWaypoint);
        this.wpID = wp.getID();
        if (wp instanceof LOTRCustomWaypoint) {
            this.sharingPlayer = ((LOTRCustomWaypoint)wp).getSharingPlayerID();
        }
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeBoolean(this.isCustom);
        data.writeInt(this.wpID);
        final boolean shared = this.sharingPlayer != null;
        data.writeBoolean(shared);
        if (shared) {
            data.writeLong(this.sharingPlayer.getMostSignificantBits());
            data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.isCustom = data.readBoolean();
        this.wpID = data.readInt();
        final boolean shared = data.readBoolean();
        if (shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFastTravel, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFastTravel packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            if (!LOTRConfig.enableFastTravel) {
                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.ftDisabled", new Object[0]));
            }
            else {
                final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                if (playerData.getFTTimer() <= 0) {
                    final boolean isCustom = packet.isCustom;
                    final int waypointID = packet.wpID;
                    LOTRAbstractWaypoint waypoint = null;
                    if (!isCustom) {
                        if (waypointID >= 0 && waypointID < LOTRWaypoint.values().length) {
                            waypoint = LOTRWaypoint.values()[waypointID];
                        }
                    }
                    else {
                        final UUID sharingPlayer = packet.sharingPlayer;
                        if (sharingPlayer != null) {
                            waypoint = playerData.getSharedCustomWaypointByID(sharingPlayer, waypointID);
                        }
                        else {
                            waypoint = playerData.getCustomWaypointByID(waypointID);
                        }
                    }
                    if (waypoint != null && waypoint.hasPlayerUnlocked((EntityPlayer)entityplayer)) {
                        final boolean canTravel = playerData.canFastTravel();
                        if (!canTravel) {
                            entityplayer.closeScreen();
                            entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("lotr.fastTravel.underAttack", new Object[0]));
                        }
                        else {
                            playerData.setTargetFTWaypoint(waypoint);
                        }
                    }
                }
            }
            return null;
        }
    }
}
