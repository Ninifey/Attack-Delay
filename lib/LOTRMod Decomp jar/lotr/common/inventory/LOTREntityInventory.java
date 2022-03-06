// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryBasic;

public class LOTREntityInventory extends InventoryBasic
{
    protected EntityLivingBase theEntity;
    private String nbtName;
    
    public LOTREntityInventory(final String s, final EntityLivingBase npc, final int i) {
        super(s, true, i);
        this.theEntity = npc;
        this.nbtName = s;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        final NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack != null) {
                final NBTTagCompound itemData = new NBTTagCompound();
                itemData.setByte("Slot", (byte)i);
                itemstack.writeToNBT(itemData);
                items.appendTag((NBTBase)itemData);
            }
        }
        nbt.setTag(this.nbtName, (NBTBase)items);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        final NBTTagList items = nbt.getTagList(this.nbtName, 10);
        for (int i = 0; i < items.tagCount(); ++i) {
            final NBTTagCompound itemData = items.getCompoundTagAt(i);
            final int slot = itemData.getByte("Slot");
            if (slot >= 0 && slot < this.getSizeInventory()) {
                this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
            }
        }
    }
    
    public void dropAllItems() {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack != null) {
                this.dropItem(itemstack);
                this.setInventorySlotContents(i, (ItemStack)null);
            }
        }
    }
    
    protected void dropItem(final ItemStack itemstack) {
        this.theEntity.entityDropItem(itemstack, 0.0f);
    }
    
    public boolean isEmpty() {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) != null) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isFull() {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) == null) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addItemToInventory(final ItemStack itemstack) {
        final int origStack = itemstack.stackSize;
        if (itemstack != null && itemstack.stackSize > 0) {
            for (int i = 0; i < this.getSizeInventory() && itemstack.stackSize > 0; ++i) {
                final ItemStack itemInSlot = this.getStackInSlot(i);
                if (itemInSlot != null) {
                    if (itemInSlot.stackSize >= itemInSlot.getMaxStackSize()) {
                        continue;
                    }
                    if (!itemstack.isItemEqual(itemInSlot)) {
                        continue;
                    }
                    if (!ItemStack.areItemStackTagsEqual(itemInSlot, itemstack)) {
                        continue;
                    }
                }
                if (itemInSlot == null) {
                    final ItemStack copy = itemstack.copy();
                    copy.stackSize = Math.min(copy.stackSize, this.getInventoryStackLimit());
                    this.setInventorySlotContents(i, copy);
                    itemstack.stackSize -= copy.stackSize;
                }
                else {
                    int maxStackSize = itemInSlot.getMaxStackSize();
                    maxStackSize = Math.min(maxStackSize, this.getInventoryStackLimit());
                    int difference = maxStackSize - itemInSlot.stackSize;
                    difference = Math.min(difference, itemstack.stackSize);
                    itemstack.stackSize -= difference;
                    final ItemStack itemStack = itemInSlot;
                    itemStack.stackSize += difference;
                    this.setInventorySlotContents(i, itemInSlot);
                }
            }
        }
        return itemstack != null && itemstack.stackSize < origStack;
    }
}
