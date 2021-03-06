// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTREntityWoodElfSmith extends LOTREntityWoodElf implements LOTRTradeable.Smith
{
    public LOTREntityWoodElfSmith(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.WOOD_ELF_SMITH_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.WOOD_ELF_SMITH_SELL;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        if (type == LOTRTradeEntries.TradeType.BUY) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeWoodElfSmith);
        }
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public boolean shouldRenderNPCHair() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "woodElf/smith/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "woodElf/smith/friendly";
        }
        return "woodElf/smith/neutral";
    }
}
