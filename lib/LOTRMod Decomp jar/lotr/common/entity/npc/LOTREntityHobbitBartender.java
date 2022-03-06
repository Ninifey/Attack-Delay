// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;

public class LOTREntityHobbitBartender extends LOTREntityHobbit implements LOTRTradeable.Bartender
{
    public LOTREntityHobbitBartender(final World world) {
        super(world);
        super.npcLocationName = "entity.lotr.HobbitBartender.locationName";
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HOBBIT_BARTENDER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HOBBIT_BARTENDER_SELL;
    }
    
    @Override
    protected void dropHobbitItems(final boolean flag, final int i) {
        for (int count = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), k = 0; k < count; ++k) {
            final int j = ((Entity)this).rand.nextInt(10);
            switch (j) {
                case 0:
                case 1: {
                    this.entityDropItem(LOTRFoods.HOBBIT.getRandomFood(((Entity)this).rand), 0.0f);
                    break;
                }
                case 2: {
                    this.entityDropItem(new ItemStack(Items.gold_nugget, 2 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
                case 3: {
                    this.entityDropItem(new ItemStack(Items.bowl, 1 + ((Entity)this).rand.nextInt(4)), 0.0f);
                    break;
                }
                case 4: {
                    this.entityDropItem(new ItemStack(LOTRMod.hobbitPipe, 1, ((Entity)this).rand.nextInt(100)), 0.0f);
                    break;
                }
                case 5: {
                    this.entityDropItem(new ItemStack(LOTRMod.pipeweed, 1 + ((Entity)this).rand.nextInt(2)), 0.0f);
                    break;
                }
                case 6:
                case 7:
                case 8: {
                    this.entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
                    break;
                }
                case 9: {
                    final Item drink = LOTRFoods.HOBBIT_DRINK.getRandomFood(((Entity)this).rand).getItem();
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
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBartender);
        if (type == LOTRTradeEntries.TradeType.SELL && itemstack.getItem() == LOTRMod.pipeweedLeaf) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.sellPipeweedLeaf);
        }
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "hobbit/bartender/friendly";
        }
        return "hobbit/bartender/hostile";
    }
}
