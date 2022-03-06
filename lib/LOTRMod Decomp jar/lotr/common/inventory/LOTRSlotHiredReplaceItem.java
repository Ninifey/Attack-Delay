// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTRInventoryHiredReplacedItems;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.inventory.Slot;

public class LOTRSlotHiredReplaceItem extends Slot
{
    private LOTREntityNPC theNPC;
    private LOTRInventoryHiredReplacedItems npcInv;
    private Slot parentSlot;
    
    public LOTRSlotHiredReplaceItem(final Slot slot, final LOTREntityNPC npc) {
        super(slot.inventory, slot.getSlotIndex(), slot.xDisplayPosition, slot.yDisplayPosition);
        this.parentSlot = slot;
        this.theNPC = npc;
        this.npcInv = this.theNPC.hiredReplacedInv;
        if (!((Entity)this.theNPC).worldObj.isClient) {
            final int i = this.getSlotIndex();
            if (this.npcInv.hasReplacedEquipment(i)) {
                super.inventory.setInventorySlotContents(i, this.npcInv.getEquippedReplacement(i));
            }
        }
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return this.parentSlot.isItemValid(itemstack) && this.theNPC.canReEquipHired(this.getSlotIndex(), itemstack);
    }
    
    public int getSlotStackLimit() {
        return this.parentSlot.getSlotStackLimit();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getBackgroundIconIndex() {
        return this.parentSlot.getBackgroundIconIndex();
    }
    
    public void onSlotChanged() {
        super.onSlotChanged();
        if (!((Entity)this.theNPC).worldObj.isClient) {
            this.npcInv.onEquipmentChanged(this.getSlotIndex(), this.getStack());
        }
    }
}
