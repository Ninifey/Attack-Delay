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
import lotr.common.LOTRCapes;
import net.minecraft.world.World;

public class LOTREntityHighElfLord extends LOTREntityHighElfWarrior implements LOTRUnitTradeable
{
    public LOTREntityHighElfLord(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcCape = LOTRCapes.HIGH_ELF;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordHighElven));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHighElven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHighElven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHighElven));
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.HIGH_ELF_LORD;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.HIGH_ELF_LINDON;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 300.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeHighElfLord);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "highElf/warrior/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "highElf/lord/friendly";
        }
        return "highElf/lord/neutral";
    }
}
