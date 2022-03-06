// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import net.minecraft.util.IIcon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class LOTRSlotBarrelResult extends Slot
{
    public LOTRSlotBarrelResult(final IInventory inv, final int i, final int j, final int k) {
        super(inv, i, j, k);
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return false;
    }
    
    public boolean canTakeStack(final EntityPlayer entityplayer) {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getBackgroundIconIndex() {
        IIcon barrelGui_emptyMugSlotIcon;
        if (this.getSlotIndex() > 5) {
            final LOTRItemMug lotrItemMug = (LOTRItemMug)LOTRMod.mugAle;
            barrelGui_emptyMugSlotIcon = LOTRItemMug.barrelGui_emptyMugSlotIcon;
        }
        else {
            barrelGui_emptyMugSlotIcon = null;
        }
        return barrelGui_emptyMugSlotIcon;
    }
}
