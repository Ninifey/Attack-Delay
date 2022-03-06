// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityEasterlingBartender extends LOTREntityEasterling implements LOTRTradeable.Bartender
{
    public LOTREntityEasterlingBartender(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcLocationName = "entity.lotr.EasterlingBartender.locationName";
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.RHUN_BARTENDER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.RHUN_BARTENDER_SELL;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.mug));
        return data;
    }
    
    public void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int drinks = 1 + ((Entity)this).rand.nextInt(4) + i, l = 0; l < drinks; ++l) {
            final ItemStack drink = LOTRFoods.RHUN_DRINK.getRandomFood(((Entity)this).rand);
            this.entityDropItem(drink, 0.0f);
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRhunBartender);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "rhun/bartender/friendly";
        }
        return "rhun/bartender/hostile";
    }
}
