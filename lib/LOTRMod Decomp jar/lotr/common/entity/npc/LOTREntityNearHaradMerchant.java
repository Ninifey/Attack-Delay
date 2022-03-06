// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRItemHaradTurban;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityNearHaradMerchant extends LOTREntityNearHaradrim implements LOTRTravellingTrader
{
    private static int[] robeColors;
    
    public LOTREntityNearHaradMerchant(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.NEAR_HARAD_MERCHANT_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.NEAR_HARAD_MERCHANT_SELL;
    }
    
    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityNearHaradrim(((Entity)this).worldObj);
    }
    
    @Override
    public String getDepartureSpeech() {
        return "nearHarad/merchant/departure";
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pouch, 1, 3));
        final int robeColor = LOTREntityNearHaradMerchant.robeColors[((Entity)this).rand.nextInt(LOTREntityNearHaradMerchant.robeColors.length)];
        final ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
        LOTRItemHaradRobes.setRobesColor(turban, robeColor);
        if (((Entity)this).rand.nextBoolean()) {
            LOTRItemHaradTurban.setHasOrnament(turban, true);
        }
        this.setCurrentItemOrArmor(1, (ItemStack)null);
        this.setCurrentItemOrArmor(2, (ItemStack)null);
        this.setCurrentItemOrArmor(3, (ItemStack)null);
        this.setCurrentItemOrArmor(4, turban);
        return data;
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
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNearHaradMerchant);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "nearHarad/merchant/friendly";
        }
        return "nearHarad/merchant/hostile";
    }
    
    static {
        LOTREntityNearHaradMerchant.robeColors = new int[] { 15723226, 14829087, 12653845, 8526876, 2625038 };
    }
}
