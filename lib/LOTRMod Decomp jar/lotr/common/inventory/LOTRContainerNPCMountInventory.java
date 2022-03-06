// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Container;

public class LOTRContainerNPCMountInventory extends Container
{
    private IInventory theMountInv;
    private LOTREntityNPCRideable theMount;
    
    public LOTRContainerNPCMountInventory(final IInventory playerInv, final IInventory mountInv, final LOTREntityNPCRideable mount) {
        this.theMountInv = mountInv;
        this.theMount = mount;
        mountInv.openChest();
        this.addSlotToContainer((Slot)new Slot(mountInv, 0, 8, 18) {
            public boolean isItemValid(final ItemStack itemstack) {
                return super.isItemValid(itemstack) && itemstack.getItem() == Items.saddle && !this.getHasStack();
            }
        });
        this.addSlotToContainer((Slot)new Slot(mountInv, 1, 8, 36) {
            public boolean isItemValid(final ItemStack itemstack) {
                return super.isItemValid(itemstack) && mount.isMountArmorValid(itemstack);
            }
        });
        final int chestRows = 3;
        final int yOffset = (chestRows - 4) * 18;
        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + yOffset));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 160 + yOffset));
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theMountInv.isUseableByPlayer(entityplayer) && this.theMount.isEntityAlive() && this.theMount.getDistanceToEntity((Entity)entityplayer) < 8.0f;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int slotIndex) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (slotIndex < this.theMountInv.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack2, this.theMountInv.getSizeInventory(), super.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (this.getSlot(1).isItemValid(itemstack2) && !this.getSlot(1).getHasStack()) {
                if (!this.mergeItemStack(itemstack2, 1, 2, false)) {
                    return null;
                }
            }
            else if (this.getSlot(0).isItemValid(itemstack2)) {
                if (!this.mergeItemStack(itemstack2, 0, 1, false)) {
                    return null;
                }
            }
            else if (this.theMountInv.getSizeInventory() <= 2 || !this.mergeItemStack(itemstack2, 2, this.theMountInv.getSizeInventory(), false)) {
                return null;
            }
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        this.theMountInv.closeChest();
    }
}
