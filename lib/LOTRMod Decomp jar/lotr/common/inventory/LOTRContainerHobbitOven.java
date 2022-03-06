// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import net.minecraft.inventory.Container;

public class LOTRContainerHobbitOven extends Container
{
    private LOTRTileEntityHobbitOven theOven;
    private int currentCookTime;
    private int ovenCookTime;
    private int currentItemFuelValue;
    
    public LOTRContainerHobbitOven(final InventoryPlayer inv, final LOTRTileEntityHobbitOven oven) {
        this.currentCookTime = 0;
        this.ovenCookTime = 0;
        this.currentItemFuelValue = 0;
        this.theOven = oven;
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)oven, i, 8 + i * 18, 21));
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer((Slot)new SlotFurnace(inv.player, (IInventory)oven, i + 9, 8 + i * 18, 67));
        }
        this.addSlotToContainer(new Slot((IInventory)oven, 18, 80, 111));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 8 + j * 18, 133 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 191));
        }
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate((Container)this, 0, this.theOven.currentCookTime);
        crafting.sendProgressBarUpdate((Container)this, 1, this.theOven.ovenCookTime);
        crafting.sendProgressBarUpdate((Container)this, 2, this.theOven.currentItemFuelValue);
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < super.crafters.size(); ++i) {
            final ICrafting crafting = super.crafters.get(i);
            if (this.currentCookTime != this.theOven.currentCookTime) {
                crafting.sendProgressBarUpdate((Container)this, 0, this.theOven.currentCookTime);
            }
            if (this.ovenCookTime != this.theOven.ovenCookTime) {
                crafting.sendProgressBarUpdate((Container)this, 1, this.theOven.ovenCookTime);
            }
            if (this.currentItemFuelValue != this.theOven.currentItemFuelValue) {
                crafting.sendProgressBarUpdate((Container)this, 2, this.theOven.currentItemFuelValue);
            }
        }
        this.currentCookTime = this.theOven.currentCookTime;
        this.ovenCookTime = this.theOven.ovenCookTime;
        this.currentItemFuelValue = this.theOven.currentItemFuelValue;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int i, final int j) {
        if (i == 0) {
            this.theOven.currentCookTime = j;
        }
        if (i == 1) {
            this.theOven.ovenCookTime = j;
        }
        if (i == 2) {
            this.theOven.currentItemFuelValue = j;
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theOven.isUseableByPlayer(entityplayer);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i >= 9 && i < 18) {
                if (!this.mergeItemStack(itemstack2, 19, 55, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack2, itemstack);
            }
            else if (i >= 9 && i != 18) {
                if (LOTRTileEntityHobbitOven.isCookResultAcceptable(FurnaceRecipes.smelting().func_151395_a(itemstack2))) {
                    if (!this.mergeItemStack(itemstack2, 0, 9, false)) {
                        return null;
                    }
                }
                else if (TileEntityFurnace.func_145954_b(itemstack2)) {
                    if (!this.mergeItemStack(itemstack2, 18, 19, false)) {
                        return null;
                    }
                }
                else if (i >= 19 && i < 46) {
                    if (!this.mergeItemStack(itemstack2, 46, 55, false)) {
                        return null;
                    }
                }
                else if (i >= 46 && i < 55 && !this.mergeItemStack(itemstack2, 19, 46, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack2, 19, 55, false)) {
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
