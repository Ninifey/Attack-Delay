// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.inventory.Container;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import java.util.Collection;
import net.minecraft.inventory.Slot;
import java.util.ArrayList;
import net.minecraft.entity.passive.EntityHorse;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ContainerHorseInventory;

public class LOTRContainerMountInventory extends ContainerHorseInventory
{
    public LOTRContainerMountInventory(final IInventory playerInv, final IInventory horseInv, final LOTREntityHorse horse) {
        super(playerInv, horseInv, (EntityHorse)horse);
        final List<Slot> slots = new ArrayList<Slot>(((Container)this).inventorySlots);
        ((Container)this).inventorySlots.clear();
        ((Container)this).inventoryItemStacks.clear();
        this.addSlotToContainer((Slot)slots.get(0));
        final Slot armorSlot = slots.get(1);
        this.addSlotToContainer((Slot)new Slot(armorSlot.inventory, armorSlot.slotNumber, armorSlot.xDisplayPosition, armorSlot.yDisplayPosition) {
            public boolean isItemValid(final ItemStack itemstack) {
                return super.isItemValid(itemstack) && horse.func_110259_cr() && horse.isMountArmorValid(itemstack);
            }
            
            @SideOnly(Side.CLIENT)
            public boolean func_111238_b() {
                return horse.func_110259_cr();
            }
        });
        for (int i = 2; i < slots.size(); ++i) {
            this.addSlotToContainer((Slot)slots.get(i));
        }
    }
}
