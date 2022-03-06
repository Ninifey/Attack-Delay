// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class LOTRSlotAnvilOutput extends Slot
{
    private LOTRContainerAnvil theAnvil;
    
    public LOTRSlotAnvilOutput(final LOTRContainerAnvil container, final IInventory inv, final int id, final int i, final int j) {
        super(inv, id, i, j);
        this.theAnvil = container;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return false;
    }
    
    public boolean canTakeStack(final EntityPlayer entityplayer) {
        return this.getHasStack() && (this.theAnvil.materialCost <= 0 || this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.materialCost));
    }
    
    public void onPickupFromSlot(final EntityPlayer entityplayer, final ItemStack itemstack) {
        final int materials = this.theAnvil.materialCost;
        this.theAnvil.invInput.setInventorySlotContents(0, (ItemStack)null);
        final ItemStack combinerItem = this.theAnvil.invInput.getStackInSlot(1);
        if (combinerItem != null) {
            final ItemStack itemStack = combinerItem;
            --itemStack.stackSize;
            if (combinerItem.stackSize <= 0) {
                this.theAnvil.invInput.setInventorySlotContents(1, (ItemStack)null);
            }
            else {
                this.theAnvil.invInput.setInventorySlotContents(1, combinerItem);
            }
        }
        if (materials > 0) {
            this.theAnvil.takeMaterialOrCoinAmount(materials);
        }
        this.theAnvil.materialCost = 0;
        this.theAnvil.playAnvilSound();
    }
}
