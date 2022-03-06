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
import lotr.common.LOTRCapes;
import net.minecraft.world.World;

public class LOTREntityLossarnachCaptain extends LOTREntityLossarnachAxeman implements LOTRUnitTradeable
{
    public LOTREntityLossarnachCaptain(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcCape = LOTRCapes.LOSSARNACH;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeLossarnach));
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeLossarnach));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsLossarnach));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsLossarnach));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLossarnach));
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.LOSSARNACH_CAPTAIN;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.GONDOR_LOSSARNACH;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeLossarnachCaptain);
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
            return "gondor/lossarnachCaptain/friendly";
        }
        return "gondor/lossarnachCaptain/neutral";
    }
}
