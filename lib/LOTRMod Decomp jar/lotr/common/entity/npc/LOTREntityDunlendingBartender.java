// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityDunlendingBartender extends LOTREntityDunlending implements LOTRTradeable.Bartender
{
    public LOTREntityDunlendingBartender(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcLocationName = "entity.lotr.DunlendingBartender.locationName";
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.DUNLENDING_BARTENDER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.DUNLENDING_BARTENDER_SELL;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.mug));
        return data;
    }
    
    @Override
    public void dropDunlendingItems(final boolean flag, final int i) {
        for (int j = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), k = 0; k < j; ++k) {
            final int l = ((Entity)this).rand.nextInt(7);
            switch (l) {
                case 0:
                case 1:
                case 2: {
                    final Item food = LOTRFoods.DUNLENDING.getRandomFood(((Entity)this).rand).getItem();
                    this.entityDropItem(new ItemStack(food), 0.0f);
                    break;
                }
                case 3: {
                    this.entityDropItem(new ItemStack(Items.gold_nugget, 2 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
                case 4:
                case 5: {
                    this.entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
                    break;
                }
                case 6: {
                    final Item drink = LOTRFoods.DUNLENDING_DRINK.getRandomFood(((Entity)this).rand).getItem();
                    this.entityDropItem(new ItemStack(drink, 1, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
            }
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
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDunlendingBartender);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "dunlending/bartender/friendly";
        }
        return "dunlending/dunlending/hostile";
    }
}
