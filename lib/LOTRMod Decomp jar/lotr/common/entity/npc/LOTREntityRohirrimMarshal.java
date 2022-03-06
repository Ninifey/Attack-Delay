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

public class LOTREntityRohirrimMarshal extends LOTREntityRohirrimWarrior implements LOTRUnitTradeable
{
    public LOTREntityRohirrimMarshal(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcCape = LOTRCapes.ROHAN;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
        super.npcItemsInv.setMeleeWeaponMounted(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setIdleItemMounted(super.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohanMarshal));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohanMarshal));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohanMarshal));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRohanMarshal));
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }
    
    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.ROHIRRIM_MARSHAL;
    }
    
    @Override
    public LOTRInvasions getConquestHorn() {
        return LOTRInvasions.ROHAN;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onUnitTrade(final EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRohirrimMarshal);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "rohan/warrior/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "rohan/marshal/friendly";
        }
        return "rohan/marshal/neutral";
    }
}
