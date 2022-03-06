// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import lotr.common.recipe.LOTRMillstoneRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityMillstone;
import net.minecraft.inventory.Container;

public class LOTRContainerMillstone extends Container
{
    private LOTRTileEntityMillstone theMillstone;
    private int currentMillTime;
    private boolean isMilling;
    
    public LOTRContainerMillstone(final InventoryPlayer inv, final LOTRTileEntityMillstone millstone) {
        this.currentMillTime = 0;
        this.theMillstone = millstone;
        this.addSlotToContainer(new Slot((IInventory)millstone, 0, 84, 25));
        this.addSlotToContainer((Slot)new LOTRSlotMillstone(inv.player, (IInventory)millstone, 1, 84, 71));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 8 + j * 18, 100 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 158));
        }
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate((Container)this, 0, this.theMillstone.currentMillTime);
        crafting.sendProgressBarUpdate((Container)this, 1, (int)(this.theMillstone.isMilling ? 1 : 0));
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < super.crafters.size(); ++i) {
            final ICrafting crafting = super.crafters.get(i);
            if (this.currentMillTime != this.theMillstone.currentMillTime) {
                crafting.sendProgressBarUpdate((Container)this, 0, this.theMillstone.currentMillTime);
            }
            if (this.isMilling != this.theMillstone.isMilling) {
                crafting.sendProgressBarUpdate((Container)this, 1, (int)(this.theMillstone.isMilling ? 1 : 0));
            }
        }
        this.currentMillTime = this.theMillstone.currentMillTime;
        this.isMilling = this.theMillstone.isMilling;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int i, final int j) {
        if (i == 0) {
            this.theMillstone.currentMillTime = j;
        }
        if (i == 1) {
            this.theMillstone.isMilling = (j == 1);
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theMillstone.isUseableByPlayer(entityplayer);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i == 1) {
                if (!this.mergeItemStack(itemstack2, 2, 38, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack2, itemstack);
            }
            else if (i != 0) {
                if (LOTRMillstoneRecipes.getMillingResult(itemstack2) != null) {
                    if (!this.mergeItemStack(itemstack2, 0, 1, false)) {
                        return null;
                    }
                }
                else if (i >= 2 && i < 29) {
                    if (!this.mergeItemStack(itemstack2, 29, 38, false)) {
                        return null;
                    }
                }
                else if (i >= 29 && i < 38 && !this.mergeItemStack(itemstack2, 2, 29, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack2, 2, 38, false)) {
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
