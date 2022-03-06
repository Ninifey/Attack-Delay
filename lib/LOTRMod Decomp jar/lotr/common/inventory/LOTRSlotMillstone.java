// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class LOTRSlotMillstone extends Slot
{
    private EntityPlayer thePlayer;
    private int itemsTaken;
    
    public LOTRSlotMillstone(final EntityPlayer entityplayer, final IInventory inv, final int i, final int j, final int k) {
        super(inv, i, j, k);
        this.thePlayer = entityplayer;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return false;
    }
    
    public ItemStack decrStackSize(final int i) {
        if (this.getHasStack()) {
            this.itemsTaken += Math.min(i, this.getStack().stackSize);
        }
        return super.decrStackSize(i);
    }
    
    public void onPickupFromSlot(final EntityPlayer entityplayer, final ItemStack itemstack) {
        this.onCrafting(itemstack);
        super.onPickupFromSlot(entityplayer, itemstack);
    }
    
    protected void onCrafting(final ItemStack itemstack, final int i) {
        this.itemsTaken += i;
        this.onCrafting(itemstack);
    }
    
    protected void onCrafting(final ItemStack itemstack) {
        itemstack.onCrafting(((Entity)this.thePlayer).worldObj, this.thePlayer, this.itemsTaken);
        this.itemsTaken = 0;
        if (!((Entity)this.thePlayer).worldObj.isClient && itemstack.getItem() == LOTRMod.obsidianShard) {
            LOTRLevelData.getData(this.thePlayer).addAchievement(LOTRAchievement.smeltObsidianShard);
        }
    }
}
