// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import lotr.common.recipe.LOTRBrewingRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ICrafting;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.inventory.Container;

public class LOTRContainerBarrel extends Container
{
    public LOTRTileEntityBarrel theBarrel;
    private int barrelMode;
    private int brewingTime;
    
    public LOTRContainerBarrel(final InventoryPlayer inv, final LOTRTileEntityBarrel barrel) {
        this.barrelMode = 0;
        this.brewingTime = 0;
        this.theBarrel = barrel;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                final LOTRSlotBarrel slot = new LOTRSlotBarrel(barrel, j + i * 3, 14 + j * 18, 34 + i * 18);
                if (i == 2) {
                    slot.setWaterSource();
                }
                this.addSlotToContainer((Slot)slot);
            }
        }
        this.addSlotToContainer((Slot)new LOTRSlotBarrelResult((IInventory)barrel, 9, 108, 52));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 25 + j * 18, 139 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 25 + i * 18, 197));
        }
        if (!barrel.getWorldObj().isClient && inv.player instanceof EntityPlayerMP) {
            barrel.players.add(inv.player);
        }
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate((Container)this, 0, this.theBarrel.barrelMode);
        crafting.sendProgressBarUpdate((Container)this, 1, this.theBarrel.brewingTime);
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < super.crafters.size(); ++i) {
            final ICrafting crafting = super.crafters.get(i);
            if (this.barrelMode != this.theBarrel.barrelMode) {
                crafting.sendProgressBarUpdate((Container)this, 0, this.theBarrel.barrelMode);
            }
            if (this.brewingTime != this.theBarrel.brewingTime) {
                crafting.sendProgressBarUpdate((Container)this, 1, this.theBarrel.brewingTime);
            }
        }
        this.barrelMode = this.theBarrel.barrelMode;
        this.brewingTime = this.theBarrel.brewingTime;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int i, final int j) {
        if (i == 0) {
            this.theBarrel.barrelMode = j;
        }
        if (i == 1) {
            this.theBarrel.brewingTime = j;
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theBarrel.isUseableByPlayer(entityplayer);
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if (!this.theBarrel.getWorldObj().isClient && entityplayer instanceof EntityPlayerMP) {
            this.theBarrel.players.remove(entityplayer);
        }
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i < 9) {
                if (!this.mergeItemStack(itemstack2, 10, 46, true)) {
                    return null;
                }
            }
            else if (i != 9) {
                boolean flag = false;
                final Slot aBarrelSlot = super.inventorySlots.get(0);
                if (aBarrelSlot.isItemValid(itemstack2)) {
                    if (LOTRBrewingRecipes.isWaterSource(itemstack2)) {
                        flag = this.mergeItemStack(itemstack2, 6, 9, false);
                    }
                    else {
                        flag = this.mergeItemStack(itemstack2, 0, 6, false);
                    }
                }
                if (!flag) {
                    if (i >= 10 && i < 37) {
                        if (!this.mergeItemStack(itemstack2, 37, 46, false)) {
                            return null;
                        }
                    }
                    else if (!this.mergeItemStack(itemstack2, 10, 37, false)) {
                        return null;
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
