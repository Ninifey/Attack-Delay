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

public class LOTREntityMordorOrcSlaver extends LOTREntityMordorOrc implements LOTRUnitTradeable
{
    public LOTREntityMordorOrcSlaver(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.addTargetTasks(false);
        super.isWeakOrc = false;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.brandingIron));
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsOrc));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsOrc));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyOrc));
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.MORDOR_ORC_SLAVER;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return null;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 200.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireNurnSlave);
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
            return "mordor/slaver/friendly";
        }
        return "mordor/slaver/neutral";
    }
}
