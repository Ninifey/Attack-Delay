// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class LOTRSlotSaddle extends Slot
{
    public LOTRSlotSaddle(final IInventory inv, final int i, final int j, final int k) {
        super(inv, i, j, k);
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return super.isItemValid(itemstack) && itemstack.getItem() == Items.saddle && !this.getHasStack();
    }
}
