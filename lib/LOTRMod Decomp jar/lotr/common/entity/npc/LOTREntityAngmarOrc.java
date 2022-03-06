// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityAngmarOrc extends LOTREntityOrc
{
    public LOTREntityAngmarOrc(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(10);
        if (i == 0 || i == 1 || i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordAngmar));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeAngmar));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerAngmar));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerAngmarPoisoned));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerAngmar));
        }
        else if (i == 7) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeAngmar));
        }
        else if (i == 8) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeAngmar));
        }
        else if (i == 9) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmAngmar));
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearAngmar));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsAngmar));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsAngmar));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyAngmar));
        if (((Entity)this).rand.nextInt(5) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetAngmar));
        }
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ANGMAR;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killAngmarOrc;
    }
    
    @Override
    protected void dropOrcItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.ANGMAR_TENT, 1, 2 + i);
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "angmar/orc/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "angmar/orc/hired";
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
            return "angmar/orc/friendly";
        }
        return "angmar/orc/neutral";
    }
    
    @Override
    protected String getOrcSkirmishSpeech() {
        return "angmar/orc/skirmish";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.ANGMAR.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.ANGMAR;
    }
}
