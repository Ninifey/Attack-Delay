// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LOTRItemOrcSkullStaff extends LOTRItemSword
{
    public LOTRItemOrcSkullStaff() {
        super(LOTRMaterial.MORDOR);
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return repairItem.getItem() == Items.skull;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return null;
    }
}
