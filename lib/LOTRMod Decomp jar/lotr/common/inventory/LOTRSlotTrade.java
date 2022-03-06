// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTRTradeEntry;
import net.minecraft.inventory.IInventory;
import lotr.common.entity.npc.LOTRTradeEntries;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRSlotTrade extends LOTRSlotProtected
{
    private LOTRContainerTrade theContainer;
    private LOTREntityNPC theEntity;
    private LOTRTradeEntries.TradeType tradeType;
    
    public LOTRSlotTrade(final LOTRContainerTrade container, final IInventory inv, final int i, final int j, final int k, final LOTREntityNPC entity, final LOTRTradeEntries.TradeType type) {
        super(inv, i, j, k);
        this.theContainer = container;
        this.theEntity = entity;
        this.tradeType = type;
    }
    
    public int cost() {
        final LOTRTradeEntry trade = this.getTrade();
        return (trade == null) ? 0 : trade.getCost();
    }
    
    public LOTRTradeEntry getTrade() {
        LOTRTradeEntry[] trades = null;
        if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
            trades = this.theEntity.traderNPCInfo.getBuyTrades();
        }
        else if (this.tradeType == LOTRTradeEntries.TradeType.SELL) {
            trades = this.theEntity.traderNPCInfo.getSellTrades();
        }
        if (trades == null) {
            return null;
        }
        final int i = this.getSlotIndex();
        if (i >= 0 && i < trades.length) {
            return trades[i];
        }
        return null;
    }
    
    public boolean canTakeStack(final EntityPlayer entityplayer) {
        if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
            if (this.getTrade() != null && !this.getTrade().isAvailable()) {
                return false;
            }
            final int coins = LOTRItemCoin.getInventoryValue(entityplayer);
            if (coins < this.cost()) {
                return false;
            }
        }
        return this.tradeType != LOTRTradeEntries.TradeType.SELL && super.canTakeStack(entityplayer);
    }
    
    public void onPickupFromSlot(final EntityPlayer entityplayer, final ItemStack itemstack) {
        if (this.tradeType == LOTRTradeEntries.TradeType.BUY && !((Entity)entityplayer).worldObj.isClient) {
            LOTRItemCoin.takeCoins(this.cost(), entityplayer);
        }
        super.onPickupFromSlot(entityplayer, itemstack);
        if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
            final LOTRTradeEntry trade = this.getTrade();
            if (!((Entity)entityplayer).worldObj.isClient && trade != null) {
                this.putStack(trade.createTradeItem());
                ((EntityPlayerMP)entityplayer).sendContainerToPlayer((Container)this.theContainer);
                this.theEntity.traderNPCInfo.onTrade(entityplayer, trade, LOTRTradeEntries.TradeType.BUY, this.cost());
                this.theEntity.playTradeSound();
            }
        }
    }
}
