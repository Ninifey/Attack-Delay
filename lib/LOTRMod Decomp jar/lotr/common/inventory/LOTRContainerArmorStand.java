// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.inventory.Container;

public class LOTRContainerArmorStand extends Container
{
    private LOTRTileEntityArmorStand theArmorStand;
    
    public LOTRContainerArmorStand(final InventoryPlayer inv, final LOTRTileEntityArmorStand armorStand) {
        this.theArmorStand = armorStand;
        for (int i = 0; i < 4; ++i) {
            this.addSlotToContainer((Slot)new LOTRSlotArmorStand((IInventory)armorStand, i, 80, 21 + i * 18, i, (Entity)inv.player));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 165));
        }
        for (int i = 0; i < 4; ++i) {
            this.addSlotToContainer((Slot)new LOTRSlotArmorStand((IInventory)inv, 36 + (3 - i), 8, 21 + i * 18, i, (Entity)inv.player));
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theArmorStand.isUseableByPlayer(entityplayer);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i < 4) {
                if (!this.mergeItemStack(itemstack2, 4, 40, true)) {
                    return null;
                }
            }
            else {
                if (!(itemstack.getItem() instanceof ItemArmor) || this.getSlotFromInventory((IInventory)this.theArmorStand, ((ItemArmor)itemstack.getItem()).armorType).getHasStack()) {
                    return null;
                }
                final int j = ((ItemArmor)itemstack.getItem()).armorType;
                if (!this.mergeItemStack(itemstack2, j, j + 1, false)) {
                    return null;
                }
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
