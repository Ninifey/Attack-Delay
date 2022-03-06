// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import lotr.common.entity.npc.LOTRTradeSellResult;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import lotr.common.entity.npc.LOTRTradeEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import lotr.common.entity.npc.LOTRTradeEntries;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Container;

public class LOTRContainerTrade extends Container
{
    public IInventory tradeInvBuy;
    public IInventory tradeInvSell;
    public IInventory tradeInvSellOffer;
    public LOTRTradeable theTrader;
    public LOTREntityNPC theTraderNPC;
    private World theWorld;
    
    public LOTRContainerTrade(final InventoryPlayer inv, final LOTRTradeable trader, final World world) {
        this.tradeInvBuy = (IInventory)new InventoryBasic("trade", false, 9);
        this.tradeInvSell = (IInventory)new InventoryBasic("trade", false, 9);
        this.tradeInvSellOffer = (IInventory)new InventoryBasic("trade", false, 9);
        this.theTrader = trader;
        this.theTraderNPC = (LOTREntityNPC)trader;
        this.theWorld = world;
        if (!world.isClient) {
            this.updateAllTradeSlots();
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer((Slot)new LOTRSlotTrade(this, this.tradeInvBuy, i, 8 + i * 18, 40, this.theTraderNPC, LOTRTradeEntries.TradeType.BUY));
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer((Slot)new LOTRSlotTrade(this, this.tradeInvSell, i, 8 + i * 18, 92, this.theTraderNPC, LOTRTradeEntries.TradeType.SELL));
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(this.tradeInvSellOffer, i, 8 + i * 18, 141));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)inv, j + i * 9 + 9, 8 + j * 18, 188 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)inv, i, 8 + i * 18, 246));
        }
    }
    
    public void updateAllTradeSlots() {
        final LOTRTradeEntry[] buyTrades = this.theTraderNPC.traderNPCInfo.getBuyTrades();
        final LOTRTradeEntry[] sellTrades = this.theTraderNPC.traderNPCInfo.getSellTrades();
        if (buyTrades != null) {
            for (int i = 0; i < this.tradeInvBuy.getSizeInventory(); ++i) {
                LOTRTradeEntry trade = null;
                if (i < buyTrades.length) {
                    trade = buyTrades[i];
                }
                if (trade != null) {
                    this.tradeInvBuy.setInventorySlotContents(i, trade.createTradeItem());
                }
                else {
                    this.tradeInvBuy.setInventorySlotContents(i, (ItemStack)null);
                }
            }
        }
        if (sellTrades != null) {
            for (int i = 0; i < this.tradeInvSell.getSizeInventory(); ++i) {
                LOTRTradeEntry trade = null;
                if (i < sellTrades.length) {
                    trade = sellTrades[i];
                }
                if (trade != null) {
                    this.tradeInvSell.setInventorySlotContents(i, trade.createTradeItem());
                }
                else {
                    this.tradeInvSell.setInventorySlotContents(i, (ItemStack)null);
                }
            }
        }
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        this.theTraderNPC.traderNPCInfo.sendClientPacket((EntityPlayer)crafting);
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return this.theTraderNPC != null && entityplayer.getDistanceToEntity((Entity)this.theTraderNPC) <= 12.0 && this.theTraderNPC.isEntityAlive() && this.theTraderNPC.getAttackTarget() == null && this.theTrader.canTradeWith(entityplayer);
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if (!this.theWorld.isClient) {
            for (int i = 0; i < this.tradeInvSellOffer.getSizeInventory(); ++i) {
                final ItemStack itemstack = this.tradeInvSellOffer.getStackInSlotOnClosing(i);
                if (itemstack != null) {
                    entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            final LOTRTradeSellResult sellResult = LOTRTradeEntries.getItemSellResult(itemstack2, this.theTraderNPC);
            final boolean sellable = sellResult != null && sellResult.tradesMade > 0;
            if (i < 9) {
                if (!this.mergeItemStack(itemstack2, 27, 63, true)) {
                    return null;
                }
            }
            else {
                if (i >= 9 && i < 18) {
                    return null;
                }
                if (i >= 18 && i < 27) {
                    if (!this.mergeItemStack(itemstack2, 27, 63, true)) {
                        return null;
                    }
                }
                else if (sellable) {
                    if (!this.mergeItemStack(itemstack2, 18, 27, false)) {
                        return null;
                    }
                }
                else if (i >= 27 && i < 54) {
                    if (!this.mergeItemStack(itemstack2, 54, 63, false)) {
                        return null;
                    }
                }
                else if (i >= 54 && i < 63 && !this.mergeItemStack(itemstack2, 27, 54, false)) {
                    return null;
                }
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
