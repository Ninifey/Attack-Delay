// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.util.LOTRCommonIcons;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockOrcBomb;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class LOTRSlotBomb extends Slot
{
    public LOTRSlotBomb(final IInventory inv, final int i, final int j, final int k) {
        super(inv, i, j, k);
    }
    
    public int getSlotStackLimit() {
        return 1;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return itemstack != null && Block.getBlockFromItem(itemstack.getItem()) instanceof LOTRBlockOrcBomb;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getBackgroundIconIndex() {
        return LOTRCommonIcons.iconBomb;
    }
}
