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
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.tileentity.LOTRTileEntityAlloyForgeBase;
import net.minecraft.inventory.Container;

public class LOTRContainerAlloyForge extends Container
{
    private LOTRTileEntityAlloyForgeBase theForge;
    private int currentSmeltTime;
    private int forgeSmeltTime;
    private int currentItemFuelValue;
    
    public LOTRContainerAlloyForge(final InventoryPlayer inv, final LOTRTileEntityAlloyForgeBase forge) {
        this.currentSmeltTime = 0;
        this.forgeSmeltTime = 0;
        this.currentItemFuelValue = 0;
        this.theForge = forge;
        for (int i = 0; i < 4; ++i) {
            this.addSlotToContainer(new Slot((IInventory)forge, i, 53 + i * 18, 21));
        }
        for (int i = 0; i < 4; ++i) {
            this.addSlotToContainer(new Slot((IInventory)forge, i + 4, 53 + i * 18, 39));
        }
        for (int i = 0; i < 4; ++i) {
            this.addSlotToContainer((Slot)new SlotFurnace(inv.player, (IInventory)forge, i + 8, 53 + i * 18, 85));
        }
        this.addSlotToContainer(new Slot((IInventory)forge, 12, 80, 129));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 8 + j * 18, 151 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 209));
        }
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate((Container)this, 0, this.theForge.currentSmeltTime);
        crafting.sendProgressBarUpdate((Container)this, 1, this.theForge.forgeSmeltTime);
        crafting.sendProgressBarUpdate((Container)this, 2, this.theForge.currentItemFuelValue);
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < super.crafters.size(); ++i) {
            final ICrafting crafting = super.crafters.get(i);
            if (this.currentSmeltTime != this.theForge.currentSmeltTime) {
                crafting.sendProgressBarUpdate((Container)this, 0, this.theForge.currentSmeltTime);
            }
            if (this.forgeSmeltTime != this.theForge.forgeSmeltTime) {
                crafting.sendProgressBarUpdate((Container)this, 1, this.theForge.forgeSmeltTime);
            }
            if (this.currentItemFuelValue != this.theForge.currentItemFuelValue) {
                crafting.sendProgressBarUpdate((Container)this, 2, this.theForge.currentItemFuelValue);
            }
        }
        this.currentSmeltTime = this.theForge.currentSmeltTime;
        this.forgeSmeltTime = this.theForge.forgeSmeltTime;
        this.currentItemFuelValue = this.theForge.currentItemFuelValue;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int i, final int j) {
        if (i == 0) {
            this.theForge.currentSmeltTime = j;
        }
        if (i == 1) {
            this.theForge.forgeSmeltTime = j;
        }
        if (i == 2) {
            this.theForge.currentItemFuelValue = j;
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theForge.isUseableByPlayer(entityplayer);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i >= 8 && i < 12) {
                if (!this.mergeItemStack(itemstack2, 13, 49, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack2, itemstack);
            }
            else if (i >= 8 && i != 12) {
                if (this.theForge.getSmeltingResult(itemstack2) != null) {
                    if (!this.mergeItemStack(itemstack2, 4, 8, false)) {
                        return null;
                    }
                }
                else if (TileEntityFurnace.func_145954_b(itemstack2)) {
                    if (!this.mergeItemStack(itemstack2, 12, 13, false)) {
                        return null;
                    }
                }
                else if (i >= 13 && i < 40) {
                    if (!this.mergeItemStack(itemstack2, 40, 49, false)) {
                        return null;
                    }
                }
                else if (i >= 40 && i < 49 && !this.mergeItemStack(itemstack2, 13, 40, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack2, 13, 49, false)) {
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
