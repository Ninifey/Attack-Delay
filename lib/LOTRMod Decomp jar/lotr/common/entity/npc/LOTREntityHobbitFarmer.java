// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.LOTREntityUtils;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.world.World;

public class LOTREntityHobbitFarmer extends LOTREntityHobbit implements LOTRTradeable, LOTRUnitTradeable
{
    public LOTREntityHobbitFarmer(final World world) {
        super(world);
        LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.2, false));
        this.addTargetTasks(false);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HOBBIT_FARMER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HOBBIT_FARMER_SELL;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        LOTRItemLeatherHat.setHatColor(hat, 10390131);
        this.setCurrentItemOrArmor(4, hat);
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return null;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        if (type == LOTRTradeEntries.TradeType.BUY && itemstack.getItem() == Items.potato) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyPotatoHobbitFarmer);
        }
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.HOBBIT_FARMER;
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireHobbitFarmer);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "hobbit/farmer/friendly";
        }
        return "hobbit/farmer/hostile";
    }
}
