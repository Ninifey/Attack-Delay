// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Slot;

public class LOTRSlotArmorStand extends Slot
{
    private int armorSlot;
    private Entity armorTestEntity;
    
    public LOTRSlotArmorStand(final IInventory inv, final int i, final int j, final int k, final int a, final Entity entity) {
        super(inv, i, j, k);
        this.armorSlot = a;
        this.armorTestEntity = entity;
    }
    
    public int getSlotStackLimit() {
        return 1;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        return item.isValidArmor(itemstack, this.armorSlot, this.armorTestEntity);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getBackgroundIconIndex() {
        return ItemArmor.func_94602_b(this.armorSlot);
    }
}
