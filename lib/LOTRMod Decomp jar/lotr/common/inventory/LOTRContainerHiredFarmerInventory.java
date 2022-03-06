// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.inventory.Container;

public class LOTRContainerHiredFarmerInventory extends Container
{
    private LOTREntityNPC theNPC;
    
    public LOTRContainerHiredFarmerInventory(final InventoryPlayer inv, final LOTREntityNPC entity) {
        this.theNPC = entity;
        this.addSlotToContainer((Slot)new LOTRSlotSeeds((IInventory)this.theNPC.hiredNPCInfo.getHiredInventory(), 0, 80, 21, ((Entity)this.theNPC).worldObj));
        for (int i = 0; i < 2; ++i) {
            this.addSlotToContainer((Slot)new LOTRSlotProtected((IInventory)this.theNPC.hiredNPCInfo.getHiredInventory(), i + 1, 71 + i * 18, 47));
        }
        this.addSlotToContainer((Slot)new LOTRSlotBonemeal((IInventory)this.theNPC.hiredNPCInfo.getHiredInventory(), 3, 123, 34, ((Entity)this.theNPC).worldObj));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 8 + j * 18, 79 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 137));
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theNPC != null && this.theNPC.isEntityAlive() && this.theNPC.hiredNPCInfo.isActive && this.theNPC.hiredNPCInfo.getHiringPlayer() == entityplayer && this.theNPC.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER && entityplayer.getDistanceSqToEntity((Entity)this.theNPC) <= 144.0;
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if (!((Entity)this.theNPC).worldObj.isClient) {
            this.theNPC.hiredNPCInfo.sendClientPacket(true);
        }
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
                if (super.inventorySlots.get(0).isItemValid(itemstack2) && !this.mergeItemStack(itemstack2, 0, 1, false)) {
                    return null;
                }
                if (super.inventorySlots.get(3).isItemValid(itemstack2) && !this.mergeItemStack(itemstack2, 3, 4, false)) {
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
