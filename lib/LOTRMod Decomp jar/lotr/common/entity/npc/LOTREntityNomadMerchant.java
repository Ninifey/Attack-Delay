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

public class LOTREntityNomadMerchant extends LOTREntityNomad implements LOTRTravellingTrader
{
    private static int[] robeColors;
    
    public LOTREntityNomadMerchant(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.NOMAD_MERCHANT_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.NOMAD_MERCHANT_SELL;
    }
    
    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityNomad(((Entity)this).worldObj);
    }
    
    @Override
    public String getDepartureSpeech() {
        return "nearHarad/nomad/merchant/departure";
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pouch, 1, 3));
        final int robeColor = LOTREntityNomadMerchant.robeColors[((Entity)this).rand.nextInt(LOTREntityNomadMerchant.robeColors.length)];
        final ItemStack[] array;
        final ItemStack[] robe = array = new ItemStack[] { new ItemStack(LOTRMod.bootsHaradRobes), new ItemStack(LOTRMod.legsHaradRobes), new ItemStack(LOTRMod.bodyHaradRobes), new ItemStack(LOTRMod.helmetHaradRobes) };
        for (final ItemStack item : array) {
            LOTRItemHaradRobes.setRobesColor(item, robeColor);
        }
        if (((Entity)this).rand.nextBoolean()) {
            LOTRItemHaradTurban.setHasOrnament(robe[3], true);
        }
        this.setCurrentItemOrArmor(1, robe[0]);
        this.setCurrentItemOrArmor(2, robe[1]);
        this.setCurrentItemOrArmor(3, robe[2]);
        this.setCurrentItemOrArmor(4, robe[3]);
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
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNomadMerchant);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "nearHarad/nomad/merchant/friendly";
        }
        return "nearHarad/nomad/merchant/hostile";
    }
    
    static {
        LOTREntityNomadMerchant.robeColors = new int[] { 15723226, 13551017, 6512465, 2499615, 11376219, 7825215 };
    }
}
