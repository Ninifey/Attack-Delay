// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.inventory.Container;

public class LOTRContainerBookshelf extends Container
{
    public LOTRTileEntityBookshelf shelfInv;
    private int numRows;
    
    public LOTRContainerBookshelf(final IInventory player, final LOTRTileEntityBookshelf shelf) {
        this.shelfInv = shelf;
        this.numRows = this.shelfInv.getSizeInventory() / 9;
        this.shelfInv.openChest();
        final int playerSlotY = (this.numRows - 4) * 18;
        for (int j = 0; j < this.numRows; ++j) {
            for (int i = 0; i < 9; ++i) {
                this.addSlotToContainer((Slot)new Slot(this.shelfInv, i + j * 9, 8 + i * 18, 18 + j * 18) {
                    public boolean isItemValid(final ItemStack itemstack) {
                        return LOTRTileEntityBookshelf.isBookItem(itemstack);
                    }
                });
            }
        }
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 9; ++i) {
                this.addSlotToContainer(new Slot(player, i + j * 9 + 9, 8 + i * 18, 103 + j * 18 + playerSlotY));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(player, k, 8 + k * 18, 161 + playerSlotY));
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.shelfInv.isUseableByPlayer(entityplayer);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i < this.numRows * 9) {
                if (!this.mergeItemStack(itemstack2, this.numRows * 9, super.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else {
                if (!LOTRTileEntityBookshelf.isBookItem(itemstack)) {
                    return null;
                }
                if (!this.mergeItemStack(itemstack2, 0, this.numRows * 9, false)) {
                    return null;
                }
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
        boolean anyContents = false;
        for (int i = 0; i < this.shelfInv.getSizeInventory(); ++i) {
            if (this.shelfInv.getStackInSlot(i) != null) {
                anyContents = true;
                break;
            }
        }
        super.onContainerClosed(entityplayer);
        this.shelfInv.closeChest();
        if (!anyContents && this.shelfInv.numPlayersUsing <= 0) {
            final World world = this.shelfInv.getWorldObj();
            if (!world.isClient) {
                world.setBlock(this.shelfInv.xCoord, this.shelfInv.yCoord, this.shelfInv.zCoord, Blocks.bookshelf, 0, 3);
            }
        }
    }
}
