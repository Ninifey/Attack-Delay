// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;

public class LOTRInventoryGollum implements IInventory
{
    private ItemStack[] inventory;
    private LOTREntityGollum theGollum;
    
    public LOTRInventoryGollum(final LOTREntityGollum gollum) {
        this.inventory = new ItemStack[9];
        this.theGollum = gollum;
    }
    
    public ItemStack getStackInSlot(final int i) {
        return this.inventory[i];
    }
    
    public ItemStack decrStackSize(final int i, final int j) {
        if (this.inventory[i] == null) {
            return null;
        }
        if (this.inventory[i].stackSize <= j) {
            final ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            this.onInventoryChanged();
            return itemstack;
        }
        final ItemStack itemstack = this.inventory[i].splitStack(j);
        if (this.inventory[i].stackSize == 0) {
            this.inventory[i] = null;
        }
        this.onInventoryChanged();
        return itemstack;
    }
    
    public ItemStack getStackInSlotOnClosing(final int i) {
        if (this.inventory[i] != null) {
            final ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
        this.inventory[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    public int getSizeInventory() {
        return this.inventory.length;
    }
    
    public String getInventoryName() {
        return "Gollum";
    }
    
    public boolean isInventoryNameLocalized() {
        return false;
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return true;
    }
    
    public void onInventoryChanged() {
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
    
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        final NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < items.tagCount(); ++i) {
            final NBTTagCompound itemData = items.getCompoundTagAt(i);
            final byte byte0 = itemData.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.inventory.length) {
                this.inventory[byte0] = ItemStack.loadItemStackFromNBT(itemData);
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        final NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                final NBTTagCompound itemData = new NBTTagCompound();
                itemData.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(itemData);
                items.appendTag((NBTBase)itemData);
            }
        }
        nbt.setTag("Items", (NBTBase)items);
    }
    
    public void dropAllItems() {
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                this.theGollum.entityDropItem(this.inventory[i], 0.0f);
                this.inventory[i] = null;
            }
        }
    }
}
