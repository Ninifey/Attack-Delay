// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import java.util.HashMap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.util.ChunkCoordinates;
import java.util.Map;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityDwarvenDoor extends TileEntity
{
    private static Map<ChunkCoordinates, Pair<Long, Integer>> replacementGlowTicks;
    private static int GLOW_RANGE;
    private LOTRDwarvenGlowLogic glowLogic;
    private LOTRBlockGateDwarvenIthildin.DoorSize doorSize;
    private int doorPosX;
    private int doorPosY;
    private int doorBaseX;
    private int doorBaseY;
    private int doorBaseZ;
    
    public LOTRTileEntityDwarvenDoor() {
        this.glowLogic = new LOTRDwarvenGlowLogic().setPlayerRange(LOTRTileEntityDwarvenDoor.GLOW_RANGE);
    }
    
    public void setDoorSizeAndPos(LOTRBlockGateDwarvenIthildin.DoorSize size, final int i, final int j) {
        if (size == null) {
            size = LOTRBlockGateDwarvenIthildin.DoorSize._1x1;
        }
        this.doorSize = size;
        this.doorPosX = i;
        this.doorPosY = j;
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public LOTRBlockGateDwarvenIthildin.DoorSize getDoorSize() {
        if (this.doorSize == null) {
            this.doorSize = LOTRBlockGateDwarvenIthildin.DoorSize._1x1;
        }
        return this.doorSize;
    }
    
    public int getDoorPosX() {
        if (this.doorPosX < 0 || (this.doorSize != null && this.doorPosX >= this.doorSize.width)) {
            this.doorPosX = 0;
        }
        return this.doorPosX;
    }
    
    public int getDoorPosY() {
        if (this.doorPosY < 0 || (this.doorSize != null && this.doorPosY >= this.doorSize.height)) {
            this.doorPosY = 0;
        }
        return this.doorPosY;
    }
    
    public void setDoorBasePos(final int i, final int j, final int k) {
        this.doorBaseX = i;
        this.doorBaseY = j;
        this.doorBaseZ = k;
        this.glowLogic.resetGlowTick();
        this.onInventoryChanged();
    }
    
    public boolean isSameDoor(final LOTRTileEntityDwarvenDoor other) {
        return this.doorBaseX == other.doorBaseX && this.doorBaseY == other.doorBaseY && this.doorBaseZ == other.doorBaseZ;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeDoorToNBT(nbt);
    }
    
    private void writeDoorToNBT(final NBTTagCompound nbt) {
        if (this.doorSize != null) {
            nbt.setString("DoorSize", this.doorSize.doorName);
            nbt.setByte("DoorX", (byte)this.doorPosX);
            nbt.setByte("DoorY", (byte)this.doorPosY);
            nbt.setInteger("DoorBaseX", this.doorBaseX);
            nbt.setInteger("DoorBaseY", this.doorBaseY);
            nbt.setInteger("DoorBaseZ", this.doorBaseZ);
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readDoorFromNBT(nbt);
    }
    
    private void readDoorFromNBT(final NBTTagCompound nbt) {
        if (nbt.hasKey("DoorSize")) {
            this.doorSize = LOTRBlockGateDwarvenIthildin.DoorSize.forName(nbt.getString("DoorSize"));
            this.doorPosX = nbt.getByte("DoorX");
            this.doorPosY = nbt.getByte("DoorY");
            this.doorBaseX = nbt.getInteger("DoorBaseX");
            this.doorBaseY = nbt.getInteger("DoorBaseY");
            this.doorBaseZ = nbt.getInteger("DoorBaseZ");
        }
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeDoorToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.readDoorFromNBT(data);
    }
    
    public void updateEntity() {
        super.updateEntity();
        if (this.doorSize != null) {
            this.glowLogic.update(super.worldObj, this.doorBaseX, this.doorBaseY, this.doorBaseZ);
        }
        else {
            this.glowLogic.update(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
        }
    }
    
    public void invalidate() {
        super.invalidate();
        if (super.worldObj.isClient) {
            final long time = super.worldObj.getTotalWorldTime();
            final int glow = this.glowLogic.getGlowTick();
            final ChunkCoordinates coords = new ChunkCoordinates(super.xCoord, super.yCoord, super.zCoord);
            LOTRTileEntityDwarvenDoor.replacementGlowTicks.put(coords, (Pair<Long, Integer>)Pair.of((Object)time, (Object)glow));
        }
    }
    
    public void validate() {
        super.validate();
        if (super.worldObj.isClient) {
            final ChunkCoordinates coords = new ChunkCoordinates(super.xCoord, super.yCoord, super.zCoord);
            final long time = super.worldObj.getTotalWorldTime();
            if (LOTRTileEntityDwarvenDoor.replacementGlowTicks.containsKey(coords)) {
                final Pair<Long, Integer> stored = LOTRTileEntityDwarvenDoor.replacementGlowTicks.get(coords);
                final long storedTime = (long)stored.getLeft();
                final int glow = (int)stored.getRight();
                if (time == storedTime) {
                    this.glowLogic.setGlowTick(glow);
                }
                LOTRTileEntityDwarvenDoor.replacementGlowTicks.remove(coords);
            }
        }
    }
    
    public float getGlowBrightness(final float f) {
        if (this.doorSize != null && super.worldObj.blockExists(this.doorBaseX, this.doorBaseY, this.doorBaseZ)) {
            final TileEntity te = super.worldObj.getTileEntity(this.doorPosX, this.doorBaseY, this.doorBaseZ);
            if (te instanceof LOTRTileEntityDwarvenDoor) {
                final LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor)te;
                return otherDoor.glowLogic.getGlowBrightness(super.worldObj, this.doorPosX, this.doorBaseY, this.doorBaseZ, f);
            }
        }
        return this.glowLogic.getGlowBrightness(super.worldObj, super.xCoord, super.yCoord, super.zCoord, f);
    }
    
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        final double range = LOTRTileEntityDwarvenDoor.GLOW_RANGE + 20;
        return range * range;
    }
    
    static {
        LOTRTileEntityDwarvenDoor.replacementGlowTicks = new HashMap<ChunkCoordinates, Pair<Long, Integer>>();
        LOTRTileEntityDwarvenDoor.GLOW_RANGE = 12;
    }
}
