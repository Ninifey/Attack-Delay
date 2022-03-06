// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityHobbitShirriffChief extends LOTREntityHobbitShirriff implements LOTRUnitTradeable
{
    public LOTREntityHobbitShirriffChief(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        LOTRItemLeatherHat.setHatColor(hat, 2301981);
        LOTRItemLeatherHat.setFeatherColor(hat, 3381529);
        this.setCurrentItemOrArmor(4, hat);
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.HOBBIT_SHIRRIFF_CHIEF;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.HOBBIT;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeHobbitShirriffChief);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "hobbit/shirriff/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "hobbit/shirriffChief/friendly";
        }
        return "hobbit/shirriffChief/neutral";
    }
}
