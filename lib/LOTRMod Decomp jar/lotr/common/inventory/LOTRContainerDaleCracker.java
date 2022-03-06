// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemDaleCracker;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSealCracker;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Container;

public class LOTRContainerDaleCracker extends Container
{
    private ItemStack theCrackerItem;
    private IInventory crackerInventory;
    private int capacity;
    
    public LOTRContainerDaleCracker(final EntityPlayer entityplayer) {
        this.theCrackerItem = entityplayer.inventory.getCurrentItem();
        this.capacity = 3;
        this.crackerInventory = (IInventory)new InventoryBasic("cracker", false, this.capacity);
        for (int i = 0; i < this.capacity; ++i) {
            this.addSlotToContainer((Slot)new LOTRSlotDaleCracker(this.crackerInventory, i, 62 + i * 18, 24));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, i, 8 + i * 18, 142));
        }
    }
    
    public boolean isCrackerInvEmpty() {
        for (int i = 0; i < this.crackerInventory.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.crackerInventory.getStackInSlot(i);
            if (itemstack != null) {
                return false;
            }
        }
        return true;
    }
    
    public void sendSealingPacket(final EntityPlayer entityplayer) {
        final LOTRPacketSealCracker packet = new LOTRPacketSealCracker();
        LOTRPacketHandler.networkWrapper.sendToServer((IMessage)packet);
    }
    
    public void receiveSealingPacket(final EntityPlayer entityplayer) {
        if (!this.isCrackerInvEmpty()) {
            final IInventory tempContents = (IInventory)new InventoryBasic("crackerTemp", false, this.crackerInventory.getSizeInventory());
            for (int i = 0; i < tempContents.getSizeInventory(); ++i) {
                tempContents.setInventorySlotContents(i, this.crackerInventory.getStackInSlot(i));
                this.crackerInventory.setInventorySlotContents(i, (ItemStack)null);
            }
            LOTRItemDaleCracker.setEmpty(this.theCrackerItem, false);
            LOTRItemDaleCracker.setSealingPlayerName(this.theCrackerItem, entityplayer.getCommandSenderName());
            LOTRItemDaleCracker.setCustomCrackerContents(this.theCrackerItem, tempContents);
        }
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if (!((Entity)entityplayer).worldObj.isClient) {
            for (int i = 0; i < this.crackerInventory.getSizeInventory(); ++i) {
                final ItemStack itemstack = this.crackerInventory.getStackInSlotOnClosing(i);
                if (itemstack != null) {
                    entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return ItemStack.areItemStacksEqual(this.theCrackerItem, entityplayer.inventory.getCurrentItem());
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
        final Slot aCrackerSlot = this.getSlotFromInventory(this.crackerInventory, 0);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i < this.capacity) {
                if (!this.mergeItemStack(itemstack2, this.capacity, this.capacity + 36, true)) {
                    return null;
                }
            }
            else if (aCrackerSlot.isItemValid(itemstack2) && !this.mergeItemStack(itemstack2, 0, this.capacity, false)) {
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
