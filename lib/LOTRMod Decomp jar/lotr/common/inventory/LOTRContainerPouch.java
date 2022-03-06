// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import org.apache.commons.lang3.StringUtils;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Container;

public class LOTRContainerPouch extends Container
{
    private ItemStack thePouchItem;
    private LOTRInventoryPouch pouchInventory;
    public int capacity;
    
    public LOTRContainerPouch(final EntityPlayer entityplayer) {
        this.thePouchItem = entityplayer.inventory.getCurrentItem();
        this.pouchInventory = new LOTRInventoryPouch(entityplayer, this);
        this.capacity = this.pouchInventory.getSizeInventory();
        for (int rows = this.capacity / 9, i = 0; i < rows; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer((Slot)new LOTRSlotPouch((IInventory)this.pouchInventory, j + i * 9, 8 + j * 18, 30 + i * 18));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, 98 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, i, 8 + i * 18, 156));
        }
    }
    
    public String getDisplayName() {
        return this.pouchInventory.getInventoryName();
    }
    
    public void renamePouch(final String name) {
        if (StringUtils.isBlank((CharSequence)name)) {
            this.pouchInventory.getPouchItem().func_135074_t();
        }
        else {
            this.pouchInventory.getPouchItem().setStackDisplayName(name);
        }
        this.syncPouchItem(this.pouchInventory.getPouchItem());
    }
    
    public void syncPouchItem(final ItemStack itemstack) {
        this.thePouchItem = itemstack;
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return ItemStack.areItemStacksEqual(this.thePouchItem, this.pouchInventory.getPouchItem());
    }
    
    public ItemStack slotClick(final int slotNo, final int j, final int k, final EntityPlayer entityplayer) {
        if (slotNo >= 0 && slotNo < super.inventorySlots.size()) {
            final Slot slot = super.inventorySlots.get(slotNo);
            if (slot.inventory == entityplayer.inventory && slot.getSlotIndex() == entityplayer.inventory.currentItem) {
                return null;
            }
        }
        return super.slotClick(slotNo, j, k, entityplayer);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        final Slot aPouchSlot = this.getSlotFromInventory((IInventory)this.pouchInventory, 0);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i < this.capacity) {
                if (!this.mergeItemStack(itemstack2, this.capacity, this.capacity + 36, true)) {
                    return null;
                }
            }
            else if (aPouchSlot.isItemValid(itemstack2) && !this.mergeItemStack(itemstack2, 0, this.capacity, false)) {
                return null;
            }
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (itemstack2.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityplayer, itemstack2);
        }
        return itemstack;
    }
}
