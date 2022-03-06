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
import lotr.common.LOTRShields;
import lotr.common.LOTRCapes;
import net.minecraft.world.World;

public class LOTREntityLebenninCaptain extends LOTREntityGondorSoldier implements LOTRUnitTradeable
{
    public LOTREntityLebenninCaptain(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcCape = LOTRCapes.GONDOR;
        super.npcShield = LOTRShields.ALIGNMENT_LEBENNIN;
    }
    
    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
        super.npcItemsInv.setMeleeWeaponMounted(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setIdleItemMounted(super.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGondor));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGondor));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGondor));
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.LEBENNIN_CAPTAIN;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.GONDOR_LEBENNIN;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeLebenninCaptain);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "gondor/soldier/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "gondor/lebenninCaptain/friendly";
        }
        return "gondor/lebenninCaptain/neutral";
    }
}
