// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRLevelData;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.InventoryBasic;
import lotr.common.entity.npc.LOTRUnitTradeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import lotr.common.fac.LOTRFaction;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHireableBase;
import net.minecraft.inventory.Container;

public class LOTRContainerUnitTrade extends Container
{
    public LOTRHireableBase theUnitTrader;
    public LOTREntityNPC theLivingTrader;
    private LOTRFaction traderFaction;
    private World theWorld;
    private IInventory alignmentRewardInv;
    public int alignmentRewardSlots;
    
    public LOTRContainerUnitTrade(final EntityPlayer entityplayer, final LOTRHireableBase trader, final World world) {
        this.theUnitTrader = trader;
        this.theLivingTrader = (LOTREntityNPC)this.theUnitTrader;
        this.traderFaction = this.theLivingTrader.getFaction();
        this.theWorld = world;
        ItemStack reward = null;
        if (this.theUnitTrader instanceof LOTRUnitTradeable) {
            final LOTRInvasions conquestType = ((LOTRUnitTradeable)this.theUnitTrader).getConquestHorn();
            reward = ((conquestType == null) ? null : conquestType.createConquestHorn());
        }
        final boolean hasReward = reward != null;
        this.alignmentRewardSlots = (hasReward ? 1 : 0);
        this.alignmentRewardInv = (IInventory)new InventoryBasic("specialItem", false, this.alignmentRewardSlots);
        if (hasReward) {
            this.addSlotToContainer((Slot)new LOTRSlotAlignmentReward(this, this.alignmentRewardInv, 0, 174, 78, this.theUnitTrader, reward.copy()));
            if (!world.isClient && LOTRLevelData.getData(entityplayer).getAlignment(this.traderFaction) >= 1500.0f) {
                this.alignmentRewardInv.setInventorySlotContents(0, reward.copy());
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, j + i * 9 + 9, 30 + j * 18, 174 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, i, 30 + i * 18, 232));
        }
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theLivingTrader != null && entityplayer.getDistanceToEntity((Entity)this.theLivingTrader) <= 12.0 && this.theLivingTrader.isEntityAlive() && this.theLivingTrader.getAttackTarget() == null && this.theUnitTrader.canTradeWith(entityplayer);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i >= 0 && i < this.alignmentRewardSlots) {
                if (!this.mergeItemStack(itemstack2, this.alignmentRewardSlots, 36 + this.alignmentRewardSlots, true)) {
                    return null;
                }
            }
            else if (i >= this.alignmentRewardSlots && i < 27 + this.alignmentRewardSlots) {
                if (!this.mergeItemStack(itemstack2, 27 + this.alignmentRewardSlots, 36 + this.alignmentRewardSlots, false)) {
                    return null;
                }
            }
            else if (i >= 27 + this.alignmentRewardSlots && i < 36 + this.alignmentRewardSlots) {
                if (!this.mergeItemStack(itemstack2, this.alignmentRewardSlots, 27 + this.alignmentRewardSlots, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack2, this.alignmentRewardSlots, 27 + this.alignmentRewardSlots, false)) {
                return null;
            }
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (itemstack2.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityplayer, itemstack2);
        }
        return itemstack;
    }
}
