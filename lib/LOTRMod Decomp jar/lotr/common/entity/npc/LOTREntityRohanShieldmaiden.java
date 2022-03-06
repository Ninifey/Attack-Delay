// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityRohanShieldmaiden extends LOTREntityRohirrimWarrior
{
    public LOTREntityRohanShieldmaiden(final World world) {
        super(world);
        super.spawnRidingHorse = false;
        super.questInfo.setOfferChance(4000);
        super.questInfo.setMinAlignment(150.0f);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(false);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextBoolean()) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRohan));
        }
        else {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        return data;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "rohan/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "rohan/warrior/hired";
        }
        return "rohan/shieldmaiden/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.ROHAN_SHIELDMAIDEN.createQuest(this);
    }
}
