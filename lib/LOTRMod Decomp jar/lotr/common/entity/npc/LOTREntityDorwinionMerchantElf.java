// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityDorwinionMerchantElf extends LOTREntityDorwinionElf implements LOTRTravellingTrader
{
    public LOTREntityDorwinionMerchantElf(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.DORWINION_MERCHANT_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.DORWINION_MERCHANT_SELL;
    }
    
    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityDorwinionGuard(((Entity)this).worldObj);
    }
    
    @Override
    public String getDepartureSpeech() {
        return "dorwinion/merchantElf/departure";
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        final int colorHat = LOTREntityDorwinionMerchantMan.hatColors[((Entity)this).rand.nextInt(LOTREntityDorwinionMerchantMan.hatColors.length)];
        final int colorFeather = LOTREntityDorwinionMerchantMan.featherColors[((Entity)this).rand.nextInt(LOTREntityDorwinionMerchantMan.featherColors.length)];
        LOTRItemLeatherHat.setHatColor(hat, colorHat);
        LOTRItemLeatherHat.setFeatherColor(hat, colorFeather);
        this.setCurrentItemOrArmor(4, hat);
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
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDorwinionMerchant);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "dorwinion/merchantElf/friendly";
        }
        return "dorwinion/merchantElf/hostile";
    }
}
