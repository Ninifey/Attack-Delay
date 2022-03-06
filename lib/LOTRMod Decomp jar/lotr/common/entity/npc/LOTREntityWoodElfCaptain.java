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

public class LOTREntityWoodElfCaptain extends LOTREntityWoodElfWarrior implements LOTRUnitTradeable
{
    public LOTREntityWoodElfCaptain(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcCape = LOTRCapes.WOOD_ELF;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordWoodElven));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsWoodElven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsWoodElven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyWoodElven));
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.WOOD_ELF_CAPTAIN;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.WOOD_ELF;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 250.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeWoodElfCaptain);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "woodElf/warrior/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "woodElf/captain/friendly";
        }
        return "woodElf/captain/neutral";
    }
}
