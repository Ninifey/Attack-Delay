// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRPoisonedDrinks;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityMug extends TileEntity
{
    private ItemStack mugItem;
    private LOTRItemMug.Vessel mugVessel;
    
    public ItemStack getMugItem() {
        if (this.mugItem == null) {
            return this.getVessel().getEmptyVessel();
        }
        final ItemStack copy = this.mugItem.copy();
        if (LOTRItemMug.isItemFullDrink(copy)) {
            LOTRItemMug.setVessel(copy, this.getVessel(), true);
        }
        return copy;
    }
    
    public void setMugItem(ItemStack itemstack) {
        if (itemstack != null && itemstack.stackSize <= 0) {
            itemstack = null;
        }
        this.mugItem = itemstack;
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public ItemStack getMugItemForRender() {
        return LOTRItemMug.getEquivalentDrink(this.getMugItem());
    }
    
    public void setEmpty() {
        this.setMugItem(null);
    }
    
    public boolean isEmpty() {
        return !LOTRItemMug.isItemFullDrink(this.getMugItem());
    }
    
    public LOTRItemMug.Vessel getVessel() {
        if (this.mugVessel == null) {
            for (final LOTRItemMug.Vessel v : LOTRItemMug.Vessel.values()) {
                if (v.canPlace && v.getBlock() == this.getBlockType()) {
                    return v;
                }
            }
            return LOTRItemMug.Vessel.MUG;
        }
        return this.mugVessel;
    }
    
    public void setVessel(final LOTRItemMug.Vessel v) {
        this.mugVessel = v;
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
    }
    
    public boolean canPoisonMug() {
        final ItemStack itemstack = this.getMugItem();
        return itemstack != null && LOTRPoisonedDrinks.canPoison(itemstack) && !LOTRPoisonedDrinks.isDrinkPoisoned(itemstack);
    }
    
    public void poisonMug(final EntityPlayer entityplayer) {
        final ItemStack itemstack = this.getMugItem();
        LOTRPoisonedDrinks.setDrinkPoisoned(itemstack, true);
        LOTRPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
        this.setMugItem(itemstack);
    }
    
    public void updateEntity() {
        if (!super.worldObj.isClient && this.isEmpty() && super.worldObj.canLightningStrikeAt(super.xCoord, super.yCoord, super.zCoord) && super.worldObj.rand.nextInt(6000) == 0) {
            final ItemStack waterItem = new ItemStack(LOTRMod.mugWater);
            LOTRItemMug.setVessel(waterItem, this.getVessel(), false);
            this.setMugItem(waterItem);
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.onInventoryChanged();
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("HasMugItem", this.mugItem != null);
        if (this.mugItem != null) {
            nbt.setTag("MugItem", (NBTBase)this.mugItem.writeToNBT(new NBTTagCompound()));
        }
        if (this.mugVessel != null) {
            nbt.setByte("Vessel", (byte)this.mugVessel.id);
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("ItemID")) {
            final Item item = Item.getItemById(nbt.getInteger("ItemID"));
            if (item != null) {
                final int damage = nbt.getInteger("ItemDamage");
                this.mugItem = new ItemStack(item, 1, damage);
            }
        }
        else {
            final boolean hasItem = nbt.getBoolean("HasMugItem");
            if (!hasItem) {
                this.mugItem = null;
            }
            else {
                this.mugItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("MugItem"));
            }
        }
        if (nbt.hasKey("Vessel")) {
            this.mugVessel = LOTRItemMug.Vessel.forMeta(nbt.getByte("Vessel"));
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
}
