// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTREntityIronHillsMerchant extends LOTREntityDwarf implements LOTRTravellingTrader
{
    public LOTREntityIronHillsMerchant(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.IRON_HILLS_MERCHANT_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.IRON_HILLS_MERCHANT_SELL;
    }
    
    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityDwarf(((Entity)this).worldObj);
    }
    
    @Override
    public String getDepartureSpeech() {
        return "dwarf/merchant/departure";
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeIronHillsMerchant);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "dwarf/merchant/friendly";
        }
        return "dwarf/dwarf/hostile";
    }
}
