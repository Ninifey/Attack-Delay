// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.item.ItemFood;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.LOTREntityUtils;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.world.World;

public class LOTREntityHobbitOrcharder extends LOTREntityHobbit implements LOTRTradeable
{
    public LOTREntityHobbitOrcharder(final World world) {
        super(world);
        LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.2, false));
        this.addTargetTasks(false);
        super.isNPCPersistent = false;
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HOBBIT_ORCHARDER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HOBBIT_ORCHARDER_SELL;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        LOTRItemLeatherHat.setHatColor(hat, 4818735);
        this.setCurrentItemOrArmor(4, hat);
        final int i = ((Entity)this).rand.nextInt(3);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_axe));
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_axe));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeBronze));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        return data;
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
        if (type == LOTRTradeEntries.TradeType.BUY && itemstack.getItem() instanceof ItemFood) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyOrcharderFood);
        }
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "hobbit/orcharder/friendly";
        }
        return "hobbit/hobbit/hostile";
    }
}
