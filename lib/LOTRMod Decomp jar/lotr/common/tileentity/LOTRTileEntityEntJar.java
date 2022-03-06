// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityEntJar extends TileEntity
{
    public int drinkMeta;
    public int drinkAmount;
    public static int MAX_CAPACITY;
    
    public LOTRTileEntityEntJar() {
        this.drinkMeta = -1;
    }
    
    public void updateEntity() {
        if (!super.worldObj.isClient && (super.worldObj.canLightningStrikeAt(super.xCoord, super.yCoord, super.zCoord) || super.worldObj.canLightningStrikeAt(super.xCoord, super.yCoord + 1, super.zCoord)) && super.worldObj.rand.nextInt(2500) == 0) {
            this.fillWithWater();
        }
    }
    
    public boolean fillFromBowl(final ItemStack itemstack) {
        if (this.drinkMeta == -1 && this.drinkAmount == 0) {
            this.drinkMeta = itemstack.getItemDamage();
            ++this.drinkAmount;
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.onInventoryChanged();
            return true;
        }
        if (this.drinkMeta == itemstack.getItemDamage() && this.drinkAmount < LOTRTileEntityEntJar.MAX_CAPACITY) {
            ++this.drinkAmount;
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.onInventoryChanged();
            return true;
        }
        return false;
    }
    
    public void fillWithWater() {
        if (this.drinkMeta == -1 && this.drinkAmount < LOTRTileEntityEntJar.MAX_CAPACITY) {
            ++this.drinkAmount;
        }
        this.drinkAmount = Math.min(this.drinkAmount, LOTRTileEntityEntJar.MAX_CAPACITY);
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public void consume() {
        --this.drinkAmount;
        if (this.drinkAmount <= 0) {
            this.drinkMeta = -1;
        }
        this.drinkAmount = Math.max(this.drinkAmount, 0);
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("DrinkMeta", this.drinkMeta);
        nbt.setInteger("DrinkAmount", this.drinkAmount);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.drinkMeta = nbt.getInteger("DrinkMeta");
        this.drinkAmount = nbt.getInteger("DrinkAmount");
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.readFromNBT(data);
    }
    
    static {
        LOTRTileEntityEntJar.MAX_CAPACITY = 6;
    }
}
