// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import java.util.Iterator;
import lotr.common.entity.npc.LOTRTradeSellResult;
import net.minecraft.inventory.IInventory;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.item.LOTRItemCoin;
import java.util.Map;
import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTRTradeEntries;
import lotr.common.entity.npc.LOTRTradeEntry;
import java.util.HashMap;
import lotr.common.inventory.LOTRContainerTrade;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketSell implements IMessage
{
    public void toBytes(final ByteBuf data) {
    }
    
    public void fromBytes(final ByteBuf data) {
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketSell, IMessage>
    {
        public IMessage onMessage(final LOTRPacketSell packet, final MessageContext context) {
            final EntityPlayer entityplayer = (EntityPlayer)context.getServerHandler().playerEntity;
            final Container container = entityplayer.openContainer;
            if (container != null && container instanceof LOTRContainerTrade) {
                final LOTRContainerTrade tradeContainer = (LOTRContainerTrade)container;
                final LOTREntityNPC trader = tradeContainer.theTraderNPC;
                final IInventory invSellOffer = tradeContainer.tradeInvSellOffer;
                final Map<LOTRTradeEntry, Integer> tradesUsed = new HashMap<LOTRTradeEntry, Integer>();
                int totalCoins = 0;
                for (int i = 0; i < invSellOffer.getSizeInventory(); ++i) {
                    final ItemStack itemstack = invSellOffer.getStackInSlot(i);
                    if (itemstack != null) {
                        final LOTRTradeSellResult sellResult = LOTRTradeEntries.getItemSellResult(itemstack, trader);
                        if (sellResult != null) {
                            final int tradeIndex = sellResult.tradeIndex;
                            final int value = sellResult.totalSellValue;
                            final int itemsSold = sellResult.itemsSold;
                            final LOTRTradeEntry[] sellTrades = trader.traderNPCInfo.getSellTrades();
                            LOTRTradeEntry trade = null;
                            if (sellTrades != null) {
                                trade = sellTrades[tradeIndex];
                            }
                            totalCoins += value;
                            if (trade != null) {
                                final Integer prevValue = tradesUsed.get(trade);
                                if (prevValue == null) {
                                    tradesUsed.put(trade, value);
                                }
                                else {
                                    tradesUsed.put(trade, prevValue + value);
                                }
                            }
                            final ItemStack itemStack = itemstack;
                            itemStack.stackSize -= itemsSold;
                            if (itemstack.stackSize <= 0) {
                                invSellOffer.setInventorySlotContents(i, (ItemStack)null);
                            }
                        }
                    }
                }
                if (totalCoins > 0) {
                    for (final Map.Entry<LOTRTradeEntry, Integer> e : tradesUsed.entrySet()) {
                        final LOTRTradeEntry trade2 = e.getKey();
                        final int value2 = e.getValue();
                        trader.traderNPCInfo.onTrade(entityplayer, trade2, LOTRTradeEntries.TradeType.SELL, value2);
                    }
                    LOTRItemCoin.giveCoins(totalCoins, entityplayer);
                    if (totalCoins >= 1000) {
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.earnManyCoins);
                    }
                    trader.playTradeSound();
                }
            }
            return null;
        }
    }
}
