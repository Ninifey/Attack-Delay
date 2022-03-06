// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityEasterlingWarlord extends LOTREntityEasterlingGoldWarrior implements LOTRUnitTradeable
{
    public LOTREntityEasterlingWarlord(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    protected EntityAIBase createEasterlingAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRhun));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRhunGold));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRhunGold));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRhunGold));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRhunWarlord));
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.EASTERLING_WARLORD;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.RHUN;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRhunCaptain);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "rhun/warrior/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "rhun/warlord/friendly";
        }
        return "rhun/warlord/neutral";
    }
}
