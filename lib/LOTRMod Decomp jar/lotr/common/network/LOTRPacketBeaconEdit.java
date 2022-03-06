// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBeaconEdit implements IMessage
{
    private int beaconX;
    private int beaconY;
    private int beaconZ;
    private UUID fellowshipID;
    private String beaconName;
    private boolean releasePlayer;
    
    public LOTRPacketBeaconEdit() {
    }
    
    public LOTRPacketBeaconEdit(final int i, final int j, final int k, final UUID fsID, final String name, final boolean release) {
        this.beaconX = i;
        this.beaconY = j;
        this.beaconZ = k;
        this.fellowshipID = fsID;
        this.beaconName = name;
        this.releasePlayer = release;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.beaconX);
        data.writeInt(this.beaconY);
        data.writeInt(this.beaconZ);
        final boolean hasFs = this.fellowshipID != null;
        data.writeBoolean(hasFs);
        if (hasFs) {
            data.writeLong(this.fellowshipID.getMostSignificantBits());
            data.writeLong(this.fellowshipID.getLeastSignificantBits());
        }
        final boolean hasName = this.beaconName != null;
        data.writeBoolean(hasName);
        if (hasName) {
            final byte[] nameBytes = this.beaconName.getBytes(Charsets.UTF_8);
            data.writeShort(nameBytes.length);
            data.writeBytes(nameBytes);
        }
        data.writeBoolean(this.releasePlayer);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.beaconX = data.readInt();
        this.beaconY = data.readInt();
        this.beaconZ = data.readInt();
        if (data.readBoolean()) {
            this.fellowshipID = new UUID(data.readLong(), data.readLong());
        }
        if (data.readBoolean()) {
            final int length = data.readShort();
            final ByteBuf nameBytes = data.readBytes(length);
            this.beaconName = nameBytes.toString(Charsets.UTF_8);
        }
        this.releasePlayer = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBeaconEdit, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBeaconEdit packet, final MessageContext context) {
            final EntityPlayer entityplayer = (EntityPlayer)context.getServerHandler().playerEntity;
            final TileEntity te = ((Entity)entityplayer).worldObj.getTileEntity(packet.beaconX, packet.beaconY, packet.beaconZ);
            if (te instanceof LOTRTileEntityBeacon) {
                final LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)te;
                if (beacon.isPlayerEditing(entityplayer)) {
                    LOTRFellowship fellowship = null;
                    if (packet.fellowshipID != null) {
                        fellowship = LOTRFellowshipData.getFellowship(packet.fellowshipID);
                    }
                    if (fellowship != null && !fellowship.isDisbanded() && fellowship.containsPlayer(entityplayer.getUniqueID())) {
                        beacon.setFellowship(fellowship);
                    }
                    else {
                        beacon.setFellowship(null);
                    }
                    if (packet.beaconName != null) {
                        beacon.setBeaconName(packet.beaconName);
                    }
                    else {
                        beacon.setBeaconName(null);
                    }
                    if (packet.releasePlayer) {
                        beacon.releaseEditingPlayer(entityplayer);
                    }
                }
            }
            return null;
        }
    }
}
