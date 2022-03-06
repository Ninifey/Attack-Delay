// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityFlowerPot extends TileEntity
{
    public Item item;
    public int meta;
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("PlantID", Item.getIdFromItem(this.item));
        nbt.setInteger("PlantMeta", this.meta);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.item = Item.getItemById(nbt.getInteger("PlantID"));
        this.meta = nbt.getInteger("PlantMeta");
        if (Block.getBlockFromItem(this.item) == null) {
            this.item = null;
            this.meta = 0;
        }
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity packet) {
        this.readFromNBT(packet.func_148857_g());
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
    }
}
