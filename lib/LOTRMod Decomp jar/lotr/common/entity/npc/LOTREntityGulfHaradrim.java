// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;

public class LOTREntityGulfHaradrim extends LOTREntityNearHaradrimBase
{
    public LOTREntityGulfHaradrim(final World world) {
        super(world);
        this.addTargetTasks(true);
    }
    
    @Override
    protected LOTRFoods getHaradrimFoods() {
        return LOTRFoods.GULF_HARAD;
    }
    
    @Override
    protected LOTRFoods getHaradrimDrinks() {
        return LOTRFoods.GULF_HARAD_DRINK;
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getGulfHaradName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    protected void dropHaradrimItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(5) == 0) {
            this.dropChestContents(LOTRChestContents.GULF_HOUSE, 1, 2 + i);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killNearHaradrim;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "nearHarad/gulf/haradrim/friendly";
        }
        return "nearHarad/gulf/haradrim/hostile";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GULF_HARAD.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.GULF_HARAD;
    }
}
