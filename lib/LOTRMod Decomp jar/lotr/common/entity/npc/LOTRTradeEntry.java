// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.ItemStack;

public class LOTRTradeEntry
{
    private final ItemStack tradeItem;
    private int tradeCost;
    private int recentTradeValue;
    private static final int valueThreshold = 200;
    private static final int valueDecayTime = 60;
    private int lockedTicks;
    
    public LOTRTradeEntry(final ItemStack itemstack, final int cost) {
        this.tradeItem = itemstack;
        this.tradeCost = cost;
    }
    
    public ItemStack createTradeItem() {
        return this.tradeItem.copy();
    }
    
    public int getCost() {
        return this.tradeCost;
    }
    
    public void setCost(final int cost) {
        this.tradeCost = cost;
    }
    
    public boolean isAvailable() {
        return this.recentTradeValue < 200 && this.lockedTicks <= 0;
    }
    
    public boolean updateAvailable(final LOTREntityNPC entity) {
        final boolean prevAvailable = this.isAvailable();
        if (((Entity)entity).ticksExisted % 60 == 0 && this.recentTradeValue > 0) {
            --this.recentTradeValue;
        }
        if (this.lockedTicks > 0) {
            --this.lockedTicks;
        }
        return this.isAvailable() != prevAvailable;
    }
    
    public boolean matches(final ItemStack itemstack) {
        final ItemStack tradeCreated = this.createTradeItem();
        if (LOTRItemMug.isItemFullDrink(tradeCreated)) {
            final ItemStack tradeDrink = LOTRItemMug.getEquivalentDrink(tradeCreated);
            final ItemStack offerDrink = LOTRItemMug.getEquivalentDrink(itemstack);
            return tradeDrink.getItem() == offerDrink.getItem();
        }
        return OreDictionary.itemMatches(tradeCreated, itemstack, false);
    }
    
    public void doTransaction(final int value) {
        this.recentTradeValue += value;
    }
    
    public void setLockedForTicks(final int ticks) {
        this.lockedTicks = ticks;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        this.tradeItem.writeToNBT(nbt);
        nbt.setInteger("Cost", this.tradeCost);
        nbt.setInteger("RecentTradeValue", this.recentTradeValue);
        nbt.setInteger("LockedTicks", this.lockedTicks);
    }
    
    public static LOTRTradeEntry readFromNBT(final NBTTagCompound nbt) {
        final ItemStack savedItem = ItemStack.loadItemStackFromNBT(nbt);
        if (savedItem != null) {
            final int cost = nbt.getInteger("Cost");
            final LOTRTradeEntry trade = new LOTRTradeEntry(savedItem, cost);
            if (nbt.hasKey("RecentTradeValue")) {
                trade.recentTradeValue = nbt.getInteger("RecentTradeValue");
            }
            trade.lockedTicks = nbt.getInteger("LockedTicks");
            return trade;
        }
        return null;
    }
}
