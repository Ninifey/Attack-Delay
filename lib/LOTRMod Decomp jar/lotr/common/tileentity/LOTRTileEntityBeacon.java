// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.commons.lang3.StringUtils;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.fellowship.LOTRFellowship;
import java.util.Set;
import java.util.Iterator;
import net.minecraft.world.chunk.Chunk;
import java.util.Collection;
import java.util.HashSet;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.EnumSkyBlock;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import java.util.UUID;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityBeacon extends TileEntity
{
    private static final float beaconRange = 80.0f;
    private static final float beaconRangeSq = 6400.0f;
    private int ticksExisted;
    private boolean isLit;
    private int litCounter;
    private int unlitCounter;
    private static final int lightingTime = 100;
    private long stateChangeTime;
    private String beaconName;
    private UUID beaconFellowshipID;
    private List<EntityPlayer> editingPlayers;
    
    public LOTRTileEntityBeacon() {
        this.stateChangeTime = -1L;
        this.editingPlayers = new ArrayList<EntityPlayer>();
    }
    
    public void setLit(final boolean flag) {
        final boolean wasLit = this.isLit;
        if (!(this.isLit = flag)) {
            this.litCounter = 0;
        }
        else {
            this.unlitCounter = 0;
        }
        this.updateLight();
        this.stateChangeTime = super.worldObj.getTotalWorldTime();
        if (wasLit && !this.isLit) {
            this.sendFellowshipMessage(false);
        }
    }
    
    private void updateLight() {
        super.worldObj.updateLightByType(EnumSkyBlock.Block, super.xCoord, super.yCoord, super.zCoord);
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public boolean isLit() {
        return this.isLit;
    }
    
    public boolean isFullyLit() {
        return this.isLit() && this.litCounter == 100;
    }
    
    public void updateEntity() {
        ++this.ticksExisted;
        if (!super.worldObj.isClient) {
            if (this.isLit && this.litCounter < 100) {
                ++this.litCounter;
                if (this.litCounter == 100) {
                    this.updateLight();
                    this.sendFellowshipMessage(true);
                }
            }
            if (!this.isLit && this.unlitCounter < 100) {
                ++this.unlitCounter;
                if (this.unlitCounter == 100) {
                    this.updateLight();
                }
            }
            if (this.ticksExisted % 10 == 0) {
                final boolean spreadLit = this.isLit && this.litCounter >= 100;
                final boolean spreadUnlit = !this.isLit && this.unlitCounter >= 100;
                if (spreadLit || spreadUnlit) {
                    final List<LOTRTileEntityBeacon> nearbyTiles = new ArrayList<LOTRTileEntityBeacon>();
                    final int range = 88;
                    final int chunkRange = range >> 4;
                    final int chunkX = super.xCoord >> 4;
                    final int chunkZ = super.zCoord >> 4;
                    final ChunkCoordinates coordsThis = new ChunkCoordinates(super.xCoord, super.yCoord, super.zCoord);
                    for (int i1 = -chunkRange; i1 <= chunkRange; ++i1) {
                        for (int k1 = -chunkRange; k1 <= chunkRange; ++k1) {
                            final int i2 = chunkX + i1;
                            final int k2 = chunkZ + k1;
                            if (super.worldObj.getChunkProvider().chunkExists(i2, k2)) {
                                final Chunk chunk = super.worldObj.getChunkFromChunkCoords(i2, k2);
                                if (chunk != null) {
                                    for (final Object obj : chunk.chunkTileEntityMap.values()) {
                                        final TileEntity te = (TileEntity)obj;
                                        if (!te.isInvalid() && te instanceof LOTRTileEntityBeacon) {
                                            final LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)te;
                                            if (coordsThis.getDistanceSquared(beacon.xCoord, beacon.yCoord, beacon.zCoord) > 6400.0f) {
                                                continue;
                                            }
                                            nearbyTiles.add(beacon);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (spreadLit) {
                        for (final LOTRTileEntityBeacon other : nearbyTiles) {
                            if (!other.isLit && this.stateChangeTime > other.stateChangeTime) {
                                other.setLit(true);
                            }
                        }
                    }
                    if (spreadUnlit) {
                        for (final LOTRTileEntityBeacon other : nearbyTiles) {
                            if (other.isLit && this.stateChangeTime > other.stateChangeTime) {
                                other.setLit(false);
                            }
                        }
                    }
                }
            }
        }
        final Set<EntityPlayer> removePlayers = new HashSet<EntityPlayer>();
        for (final EntityPlayer entityplayer : this.editingPlayers) {
            if (((Entity)entityplayer).isDead) {
                removePlayers.add(entityplayer);
            }
        }
        this.editingPlayers.removeAll(removePlayers);
    }
    
    public boolean isPlayerEditing(final EntityPlayer entityplayer) {
        return this.editingPlayers.contains(entityplayer);
    }
    
    public void addEditingPlayer(final EntityPlayer entityplayer) {
        if (!this.editingPlayers.contains(entityplayer)) {
            this.editingPlayers.add(entityplayer);
        }
    }
    
    public void releaseEditingPlayer(final EntityPlayer entityplayer) {
        this.editingPlayers.remove(entityplayer);
    }
    
    public UUID getFellowshipID() {
        return this.beaconFellowshipID;
    }
    
    public String getBeaconName() {
        return this.beaconName;
    }
    
    public void setFellowship(final LOTRFellowship fs) {
        if (fs != null) {
            this.beaconFellowshipID = fs.getFellowshipID();
        }
        else {
            this.beaconFellowshipID = null;
        }
        this.beaconFellowshipID = ((fs == null) ? null : fs.getFellowshipID());
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public void setBeaconName(final String name) {
        this.beaconName = name;
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    private void sendFellowshipMessage(final boolean lit) {
        if (this.beaconFellowshipID != null) {
            final LOTRFellowship fs = LOTRFellowshipData.getFellowship(this.beaconFellowshipID);
            if (fs != null && !fs.isDisbanded()) {
                String beaconMessageName = this.beaconName;
                if (StringUtils.isBlank((CharSequence)beaconMessageName)) {
                    beaconMessageName = fs.getName();
                }
                final IChatComponent message = (IChatComponent)new ChatComponentTranslation(lit ? "container.lotr.beacon.lit" : "container.lotr.beacon.unlit", new Object[] { beaconMessageName });
                message.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                for (final UUID player : fs.getAllPlayerUUIDs()) {
                    final EntityPlayer entityplayer = super.worldObj.func_152378_a(player);
                    if (entityplayer != null) {
                        entityplayer.addChatMessage(message);
                    }
                }
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("IsLit", this.isLit);
        nbt.setByte("LitCounter", (byte)this.litCounter);
        nbt.setByte("UnlitCounter", (byte)this.unlitCounter);
        nbt.setLong("StateChangeTime", this.stateChangeTime);
        if (this.beaconName != null) {
            nbt.setString("BeaconName", this.beaconName);
        }
        if (this.beaconFellowshipID != null) {
            nbt.setString("BeaconFellowship", this.beaconFellowshipID.toString());
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isLit = nbt.getBoolean("IsLit");
        this.litCounter = nbt.getByte("LitCounter");
        this.unlitCounter = nbt.getByte("UnlitCounter");
        this.stateChangeTime = nbt.getLong("StateChangeTime");
        if (nbt.hasKey("BeaconName")) {
            this.beaconName = nbt.getString("BeaconName");
        }
        else {
            this.beaconName = null;
        }
        if (nbt.hasKey("BeaconFellowship")) {
            this.beaconFellowshipID = UUID.fromString(nbt.getString("BeaconFellowship"));
        }
        else {
            this.beaconFellowshipID = null;
        }
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.readFromNBT(data);
        this.updateLight();
    }
}
