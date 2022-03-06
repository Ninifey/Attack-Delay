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
import net.minecraft.world.World;

public class LOTREntityMordorOrcMercenaryCaptain extends LOTREntityMordorOrc implements LOTRUnitTradeable
{
    public LOTREntityMordorOrcMercenaryCaptain(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.addTargetTasks(false);
        super.isWeakOrc = false;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.orcSkullStaff));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsOrc));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsOrc));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyOrc));
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.MORDOR_ORC_MERCENARY_CAPTAIN;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.MORDOR;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeOrcCaptain);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "mordor/orc/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "mordor/chieftain/friendly";
        }
        return "mordor/chieftain/neutral";
    }
}
