// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class LOTREntityTauredainSmith extends LOTREntityTauredain implements LOTRTradeable.Smith
{
    public LOTREntityTauredainSmith(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.TAUREDAIN_SMITH_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.TAUREDAIN_SMITH_SELL;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.blacksmithHammer));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int ingots = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < ingots; ++l) {
            this.func_145779_a(LOTRMod.obsidianShard, 1);
        }
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeTauredainSmith);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "tauredain/smith/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "tauredain/smith/friendly";
        }
        return "tauredain/smith/neutral";
    }
}
