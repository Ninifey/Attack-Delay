// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityRangerNorth extends LOTREntityRanger
{
    public LOTREntityRangerNorth(final World world) {
        super(world);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(20) == 0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(5);
        if (i == 0 || i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBarrow));
        }
        final int r = ((Entity)this).rand.nextInt(2);
        if (r == 0) {
            super.npcItemsInv.setRangedWeapon(new ItemStack((Item)Items.bow));
        }
        else if (r == 1) {
            super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rangerBow));
        }
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.RANGER_NORTH;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killRangerNorth;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "rangerNorth/ranger/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "rangerNorth/ranger/hired";
        }
        return "rangerNorth/ranger/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        if (((Entity)this).rand.nextInt(8) == 0) {
            return LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.createQuest(this);
        }
        return LOTRMiniQuestFactory.RANGER_NORTH.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.RANGER_NORTH;
    }
}
