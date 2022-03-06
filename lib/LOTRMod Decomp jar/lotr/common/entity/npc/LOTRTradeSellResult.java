// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;

public class LOTRTradeSellResult
{
    public final int tradeIndex;
    public final int tradeValue;
    public final int tradeStackSize;
    public final int tradesMade;
    public final int itemsSold;
    public final int totalSellValue;
    
    public LOTRTradeSellResult(final int index, final LOTRTradeEntry trade, final ItemStack sellItem) {
        final ItemStack tradeItem = trade.createTradeItem();
        this.tradeIndex = index;
        this.tradeValue = trade.getCost();
        this.tradeStackSize = tradeItem.stackSize;
        this.tradesMade = (trade.isAvailable() ? (sellItem.stackSize / this.tradeStackSize) : 0);
        this.itemsSold = this.tradesMade * tradeItem.stackSize;
        this.totalSellValue = this.tradesMade * this.tradeValue;
    }
}
