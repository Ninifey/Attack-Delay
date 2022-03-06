// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.util.ArrayList;
import java.util.Iterator;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import java.util.List;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketCreateCWPClient implements IMessage
{
    private int mapX;
    private int mapY;
    private int xCoord;
    private int yCoord;
    private int zCoord;
    private int cwpID;
    private String name;
    private List<UUID> sharedFellowshipIDs;
    private UUID sharingPlayer;
    private String sharingPlayerName;
    private boolean sharedUnlocked;
    private boolean sharedHidden;
    
    public LOTRPacketCreateCWPClient() {
    }
    
    public LOTRPacketCreateCWPClient(final int xm, final int ym, final int xc, final int yc, final int zc, final int id, final String s, final List<UUID> fsIDs) {
        this.mapX = xm;
        this.mapY = ym;
        this.xCoord = xc;
        this.yCoord = yc;
        this.zCoord = zc;
        this.cwpID = id;
        this.name = s;
        this.sharedFellowshipIDs = fsIDs;
    }
    
    public LOTRPacketCreateCWPClient setSharingPlayer(final UUID player, final String name, final boolean unlocked, final boolean hidden) {
        this.sharingPlayer = player;
        this.sharingPlayerName = name;
        this.sharedUnlocked = unlocked;
        this.sharedHidden = hidden;
        return this;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.mapX);
        data.writeInt(this.mapY);
        data.writeInt(this.xCoord);
        data.writeInt(this.yCoord);
        data.writeInt(this.zCoord);
        data.writeInt(this.cwpID);
        final byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
        final boolean sharedFellowships = this.sharedFellowshipIDs != null;
        data.writeBoolean(sharedFellowships);
        if (sharedFellowships) {
            data.writeShort(this.sharedFellowshipIDs.size());
            for (final UUID fsID : this.sharedFellowshipIDs) {
                data.writeLong(fsID.getMostSignificantBits());
                data.writeLong(fsID.getLeastSignificantBits());
            }
        }
        final boolean shared = this.sharingPlayer != null;
        data.writeBoolean(shared);
        if (shared) {
            data.writeLong(this.sharingPlayer.getMostSignificantBits());
            data.writeLong(this.sharingPlayer.getLeastSignificantBits());
            final byte[] usernameBytes = this.sharingPlayerName.getBytes(Charsets.UTF_8);
            data.writeByte(usernameBytes.length);
            data.writeBytes(usernameBytes);
            data.writeBoolean(this.sharedUnlocked);
            data.writeBoolean(this.sharedHidden);
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.mapX = data.readInt();
        this.mapY = data.readInt();
        this.xCoord = data.readInt();
        this.yCoord = data.readInt();
        this.zCoord = data.readInt();
        this.cwpID = data.readInt();
        final int nameLength = data.readShort();
        this.name = data.readBytes(nameLength).toString(Charsets.UTF_8);
        this.sharedFellowshipIDs = new ArrayList<UUID>();
        final boolean sharedFellowships = data.readBoolean();
        if (sharedFellowships) {
            for (int sharedLength = data.readShort(), l = 0; l < sharedLength; ++l) {
                final UUID fsID = new UUID(data.readLong(), data.readLong());
                this.sharedFellowshipIDs.add(fsID);
            }
        }
        final boolean shared = data.readBoolean();
        if (shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
            final int usernameLength = data.readByte();
            this.sharingPlayerName = data.readBytes(usernameLength).toString(Charsets.UTF_8);
            this.sharedUnlocked = data.readBoolean();
            this.sharedHidden = data.readBoolean();
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketCreateCWPClient, IMessage>
    {
        public IMessage onMessage(final LOTRPacketCreateCWPClient packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            final LOTRCustomWaypoint cwp = new LOTRCustomWaypoint(packet.name, packet.mapX, packet.mapY, packet.xCoord, packet.yCoord, packet.zCoord, packet.cwpID);
            if (packet.sharedFellowshipIDs != null) {
                cwp.setSharedFellowshipIDs(packet.sharedFellowshipIDs);
            }
            if (packet.sharingPlayer != null) {
                if (!LOTRMod.proxy.isSingleplayer()) {
                    cwp.setSharingPlayerID(packet.sharingPlayer);
                    cwp.setSharingPlayerName(packet.sharingPlayerName);
                    if (packet.sharedUnlocked) {
                        cwp.setSharedUnlocked();
                    }
                    cwp.setSharedHidden(packet.sharedHidden);
                    pd.addOrUpdateSharedCustomWaypoint(cwp);
                }
            }
            else if (!LOTRMod.proxy.isSingleplayer()) {
                pd.addCustomWaypoint(cwp);
            }
            return null;
        }
    }
}
