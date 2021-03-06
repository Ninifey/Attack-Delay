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

public class LOTREntityMoredainChieftain extends LOTREntityMoredainWarrior implements LOTRUnitTradeable
{
    public LOTREntityMoredainChieftain(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeMoredain));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsMoredainLion));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsMoredainLion));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyMoredainLion));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetMoredainLion));
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.MOREDAIN_CHIEFTAIN;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.MOREDAIN;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeMoredainChieftain);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "moredain/moredain/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "moredain/chieftain/friendly";
        }
        return "moredain/chieftain/neutral";
    }
}
