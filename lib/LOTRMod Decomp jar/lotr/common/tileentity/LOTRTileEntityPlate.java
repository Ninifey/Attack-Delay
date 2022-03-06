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
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemFood;
import lotr.common.entity.LOTRPlateFallingInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityPlate extends TileEntity
{
    private ItemStack foodItem;
    public LOTRPlateFallingInfo plateFallInfo;
    
    public static boolean isValidFoodItem(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof ItemFood) {
                return !(item instanceof ItemSoup) && !item.hasContainerItem(itemstack);
            }
        }
        return false;
    }
    
    public ItemStack getFoodItem() {
        return this.foodItem;
    }
    
    public void setFoodItem(ItemStack item) {
        if (item != null && item.stackSize <= 0) {
            item = null;
        }
        this.foodItem = item;
        if (super.worldObj != null) {
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        }
        this.onInventoryChanged();
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("PlateEmpty", this.foodItem == null);
        if (this.foodItem != null) {
            nbt.setTag("FoodItem", (NBTBase)this.foodItem.writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("FoodID")) {
            final Item item = Item.getItemById(nbt.getInteger("FoodID"));
            if (item != null) {
                final int damage = nbt.getInteger("FoodDamage");
                this.foodItem = new ItemStack(item, 1, damage);
            }
        }
        else {
            final boolean empty = nbt.getBoolean("PlateEmpty");
            if (empty) {
                this.foodItem = null;
            }
            else {
                this.foodItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("FoodItem"));
            }
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
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        AxisAlignedBB bb = super.getRenderBoundingBox();
        if (this.foodItem != null) {
            bb = bb.addCoord(0.0, (double)(this.foodItem.stackSize * 0.03125f), 0.0);
        }
        return bb;
    }
}
