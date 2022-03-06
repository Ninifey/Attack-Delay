// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.inventory.Container;

public class LOTRContainerUnsmeltery extends Container
{
    private LOTRTileEntityUnsmeltery theUnsmeltery;
    private int currentSmeltTime;
    private int forgeSmeltTime;
    private int currentItemFuelValue;
    
    public LOTRContainerUnsmeltery(final InventoryPlayer inv, final LOTRTileEntityUnsmeltery unsmeltery) {
        this.currentSmeltTime = 0;
        this.forgeSmeltTime = 0;
        this.currentItemFuelValue = 0;
        this.theUnsmeltery = unsmeltery;
        this.addSlotToContainer(new Slot((IInventory)unsmeltery, 0, 56, 17));
        this.addSlotToContainer(new Slot((IInventory)unsmeltery, 1, 56, 53));
        this.addSlotToContainer((Slot)new LOTRSlotUnsmeltResult((IInventory)unsmeltery, 2, 116, 35));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 142));
        }
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate((Container)this, 0, this.theUnsmeltery.currentSmeltTime);
        crafting.sendProgressBarUpdate((Container)this, 1, this.theUnsmeltery.forgeSmeltTime);
        crafting.sendProgressBarUpdate((Container)this, 2, this.theUnsmeltery.currentItemFuelValue);
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < super.crafters.size(); ++i) {
            final ICrafting crafting = super.crafters.get(i);
            if (this.currentSmeltTime != this.theUnsmeltery.currentSmeltTime) {
                crafting.sendProgressBarUpdate((Container)this, 0, this.theUnsmeltery.currentSmeltTime);
            }
            if (this.forgeSmeltTime != this.theUnsmeltery.forgeSmeltTime) {
                crafting.sendProgressBarUpdate((Container)this, 1, this.theUnsmeltery.forgeSmeltTime);
            }
            if (this.currentItemFuelValue != this.theUnsmeltery.currentItemFuelValue) {
                crafting.sendProgressBarUpdate((Container)this, 2, this.theUnsmeltery.currentItemFuelValue);
            }
        }
        this.currentSmeltTime = this.theUnsmeltery.currentSmeltTime;
        this.forgeSmeltTime = this.theUnsmeltery.forgeSmeltTime;
        this.currentItemFuelValue = this.theUnsmeltery.currentItemFuelValue;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int i, final int j) {
        if (i == 0) {
            this.theUnsmeltery.currentSmeltTime = j;
        }
        if (i == 1) {
            this.theUnsmeltery.forgeSmeltTime = j;
        }
        if (i == 2) {
            this.theUnsmeltery.currentItemFuelValue = j;
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theUnsmeltery.isUseableByPlayer(entityplayer);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i == 2) {
                if (!this.mergeItemStack(itemstack2, 3, 39, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack2, itemstack);
            }
            else if (i != 1 && i != 0) {
                if (this.theUnsmeltery.canBeUnsmelted(itemstack2)) {
                    if (!this.mergeItemStack(itemstack2, 0, 1, false)) {
                        return null;
                    }
                }
                else if (TileEntityFurnace.func_145954_b(itemstack2)) {
                    if (!this.mergeItemStack(itemstack2, 1, 2, false)) {
                        return null;
                    }
                }
                else if (i >= 3 && i < 30) {
                    if (!this.mergeItemStack(itemstack2, 30, 39, false)) {
                        return null;
                    }
                }
                else if (i >= 30 && i < 39 && !this.mergeItemStack(itemstack2, 3, 30, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack2, 3, 39, false)) {
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
