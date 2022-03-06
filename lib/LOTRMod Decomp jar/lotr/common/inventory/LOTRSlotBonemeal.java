// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraft.inventory.Slot;

public class LOTRSlotBonemeal extends Slot
{
    private World worldObj;
    
    public LOTRSlotBonemeal(final IInventory inv, final int i, final int j, final int k, final World world) {
        super(inv, i, j, k);
        this.worldObj = world;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15;
    }
}
