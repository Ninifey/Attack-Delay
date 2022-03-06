// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.item.LOTRItemCoin;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHireableBase;

public class LOTRSlotAlignmentReward extends LOTRSlotProtected
{
    public static int REWARD_COST;
    private LOTRContainerUnitTrade theContainer;
    private LOTRHireableBase theTrader;
    private LOTREntityNPC theLivingTrader;
    private ItemStack alignmentReward;
    
    public LOTRSlotAlignmentReward(final LOTRContainerUnitTrade container, final IInventory inv, final int i, final int j, final int k, final LOTRHireableBase entity, final ItemStack item) {
        super(inv, i, j, k);
        this.theContainer = container;
        this.theTrader = entity;
        this.theLivingTrader = (LOTREntityNPC)this.theTrader;
        this.alignmentReward = item.copy();
    }
    
    public boolean canTakeStack(final EntityPlayer entityplayer) {
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.theTrader.getFaction()) < 1500.0f) {
            return false;
        }
        final int coins = LOTRItemCoin.getInventoryValue(entityplayer);
        return coins >= LOTRSlotAlignmentReward.REWARD_COST && super.canTakeStack(entityplayer);
    }
    
    public void onPickupFromSlot(final EntityPlayer entityplayer, final ItemStack itemstack) {
        final LOTRFaction faction = this.theLivingTrader.getFaction();
        if (!((Entity)entityplayer).worldObj.isClient) {
            LOTRItemCoin.takeCoins(LOTRSlotAlignmentReward.REWARD_COST, entityplayer);
            LOTRLevelData.getData(entityplayer).getFactionData(faction).takeConquestHorn();
            this.theLivingTrader.playTradeSound();
        }
        super.onPickupFromSlot(entityplayer, itemstack);
        if (!((Entity)entityplayer).worldObj.isClient) {
            final ItemStack reward = this.alignmentReward.copy();
            this.putStack(reward);
            ((EntityPlayerMP)entityplayer).sendContainerToPlayer((Container)this.theContainer);
        }
    }
    
    static {
        LOTRSlotAlignmentReward.REWARD_COST = 2000;
    }
}
