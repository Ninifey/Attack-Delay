// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityCommandTable extends TileEntity
{
    private int zoomExp;
    private static final int MIN_ZOOM = -2;
    private static final int MAX_ZOOM = 2;
    
    public int getZoomExp() {
        return this.zoomExp;
    }
    
    public void setZoomExp(final int i) {
        this.zoomExp = MathHelper.clamp_int(i, -2, 2);
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public void toggleZoomExp() {
        int z = this.zoomExp;
        if (z <= -2) {
            z = 2;
        }
        else {
            --z;
        }
        this.setZoomExp(z);
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeTableToNBT(nbt);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readTableFromNBT(nbt);
    }
    
    private void writeTableToNBT(final NBTTagCompound nbt) {
        nbt.setByte("Zoom", (byte)this.zoomExp);
    }
    
    private void readTableFromNBT(final NBTTagCompound nbt) {
        this.zoomExp = nbt.getByte("Zoom");
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeTableToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.readTableFromNBT(data);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)(super.xCoord - 1), (double)super.yCoord, (double)(super.zCoord - 1), (double)(super.xCoord + 2), (double)(super.yCoord + 2), (double)(super.zCoord + 2));
    }
}
