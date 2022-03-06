// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.LOTRFoods;
import net.minecraft.world.World;

public class LOTREntityNearHaradrim extends LOTREntityNearHaradrimBase
{
    public LOTREntityNearHaradrim(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    @Override
    protected LOTRFoods getHaradrimFoods() {
        return LOTRFoods.SOUTHRON;
    }
    
    @Override
    protected LOTRFoods getHaradrimDrinks() {
        return LOTRFoods.SOUTHRON_DRINK;
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getSouthronCoastName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorNearHarad));
        return horse;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerNearHarad));
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    protected void dropHaradrimItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(5) == 0) {
            this.dropChestContents(LOTRChestContents.NEAR_HARAD_HOUSE, 1, 2 + i);
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "nearHarad/coast/haradrim/friendly";
        }
        return "nearHarad/coast/haradrim/hostile";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.NEAR_HARAD.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.NEAR_HARAD;
    }
}
