// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.inventory.Container;

public class LOTRContainerGollum extends Container
{
    public LOTREntityGollum theGollum;
    
    public LOTRContainerGollum(final InventoryPlayer inv, final LOTREntityGollum gollum) {
        this.theGollum = gollum;
        for (int i = 0; i < LOTREntityGollum.INV_ROWS; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)gollum.inventory, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 144));
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theGollum != null && this.theGollum.getGollumOwner() == entityplayer && entityplayer.getDistanceSqToEntity((Entity)this.theGollum) <= 144.0 && this.theGollum.isEntityAlive();
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i < LOTREntityGollum.INV_ROWS * 9) {
                if (!this.mergeItemStack(itemstack2, LOTREntityGollum.INV_ROWS * 9, super.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack2, 0, LOTREntityGollum.INV_ROWS * 9, false)) {
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
