// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import lotr.common.entity.npc.LOTRInventoryHiredReplacedItems;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.inventory.Container;

public class LOTRContainerHiredWarriorInventory extends Container
{
    private LOTREntityNPC theNPC;
    public LOTRInventoryHiredReplacedItems npcInv;
    public IInventory proxyInv;
    private final int npcFullInvSize;
    private int npcActiveSlotCount;
    
    public LOTRContainerHiredWarriorInventory(final InventoryPlayer inv, final LOTREntityNPC entity) {
        this.theNPC = entity;
        this.npcInv = this.theNPC.hiredReplacedInv;
        this.npcFullInvSize = this.npcInv.getSizeInventory();
        this.proxyInv = (IInventory)new InventoryBasic("npcTemp", false, this.npcFullInvSize);
        for (int i = 0; i < 4; ++i) {
            final Slot slot = new LOTRSlotHiredReplaceItem(new LOTRSlotArmorStand(this.proxyInv, i, 80, 21 + i * 18, i, (Entity)inv.player), this.theNPC);
            this.addSlotToContainer(slot);
        }
        final int[] array = { 0 };
        final int n = 0;
        final LOTRInventoryHiredReplacedItems npcInv = this.npcInv;
        array[n] = 4;
        for (final int j : array) {
            final Slot slot2 = new LOTRSlotHiredReplaceItem(new LOTRSlotMeleeWeapon(this.proxyInv, j, 50, 48), this.theNPC);
            this.addSlotToContainer(slot2);
        }
        if (this.theNPC instanceof LOTREntityOrc && ((LOTREntityOrc)this.theNPC).isOrcBombardier()) {
            final LOTRInventoryHiredReplacedItems npcInv2 = this.npcInv;
            final int i = 5;
            final Slot slot = new LOTRSlotHiredReplaceItem(new LOTRSlotBomb(this.proxyInv, i, 110, 48), this.theNPC);
            this.addSlotToContainer(slot);
        }
        for (int i = 0; i < this.npcFullInvSize; ++i) {
            if (this.getSlotFromInventory(this.proxyInv, i) != null) {
                ++this.npcActiveSlotCount;
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot((IInventory)inv, k + i * 9 + 9, 8 + k * 18, 107 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 165));
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theNPC != null && this.theNPC.isEntityAlive() && this.theNPC.hiredNPCInfo.isActive && this.theNPC.hiredNPCInfo.getHiringPlayer() == entityplayer && this.theNPC.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR && entityplayer.getDistanceSqToEntity((Entity)this.theNPC) <= 144.0;
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
            if (slot.inventory == this.proxyInv) {
                if (!this.mergeItemStack(itemstack2, this.npcActiveSlotCount, super.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else {
                for (int j = 0; j < this.npcFullInvSize; ++j) {
                    final Slot npcSlot = this.getSlotFromInventory(this.proxyInv, j);
                    if (npcSlot != null) {
                        final int npcSlotNo = npcSlot.slotNumber;
                        if (npcSlot.isItemValid(itemstack2) && !this.mergeItemStack(itemstack2, npcSlotNo, npcSlotNo + 1, false)) {
                            return null;
                        }
                    }
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
