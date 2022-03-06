// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityDaleBaker extends LOTREntityDaleMan implements LOTRTradeable
{
    public LOTREntityDaleBaker(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcLocationName = "entity.lotr.DaleBaker.locationName";
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.DALE_BAKER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.DALE_BAKER_SELL;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.rollingPin));
        super.npcItemsInv.setIdleItem(new ItemStack(Items.bread));
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int ingredients = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < ingredients; ++l) {
            if (((Entity)this).rand.nextBoolean()) {
                this.func_145779_a(Items.wheat, 1);
            }
            else {
                this.func_145779_a(Items.sugar, 1);
            }
        }
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDaleBaker);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "dale/baker/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "dale/baker/friendly";
        }
        return "dale/baker/friendly";
    }
}
