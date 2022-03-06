// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.item.Item;
import net.minecraftforge.common.EnumPlantType;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraft.inventory.Slot;

public class LOTRSlotSeeds extends Slot
{
    private World worldObj;
    
    public LOTRSlotSeeds(final IInventory inv, final int i, final int j, final int k, final World world) {
        super(inv, i, j, k);
        this.worldObj = world;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        return item instanceof IPlantable && ((IPlantable)item).getPlantType((IBlockAccess)this.worldObj, -1, -1, -1) == EnumPlantType.Crop;
    }
}
