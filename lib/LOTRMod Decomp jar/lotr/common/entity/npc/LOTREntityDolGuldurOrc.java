// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityDolGuldurOrc extends LOTREntityOrc
{
    public LOTREntityDolGuldurOrc(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(8);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDolGuldur));
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDolGuldur));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDolGuldurPoisoned));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDolGuldur));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerDolGuldur));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeDolGuldur));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeDolGuldur));
        }
        else if (i == 7) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDolGuldur));
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDolGuldur));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDolGuldur));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDolGuldur));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDolGuldur));
        if (((Entity)this).rand.nextInt(5) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDolGuldur));
        }
        if (!((Entity)this).worldObj.isClient && super.spawnRidingHorse && !(this instanceof LOTRBannerBearer)) {
            final LOTREntityMirkwoodSpider spider = new LOTREntityMirkwoodSpider(((Entity)this).worldObj);
            spider.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, ((Entity)this).rotationYaw, 0.0f);
            if (((Entity)this).worldObj.func_147461_a(((Entity)spider).boundingBox).isEmpty()) {
                spider.onSpawnWithEgg(null);
                spider.isNPCPersistent = super.isNPCPersistent;
                ((Entity)this).worldObj.spawnEntityInWorld((Entity)spider);
                this.mountEntity((Entity)spider);
            }
        }
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DOL_GULDUR;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDolGuldurOrc;
    }
    
    @Override
    protected void dropOrcItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.DOL_GULDUR_TENT, 1, 2 + i);
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "dolGuldur/orc/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "dolGuldur/orc/hired";
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
            return "dolGuldur/orc/friendly";
        }
        return "dolGuldur/orc/neutral";
    }
    
    @Override
    protected String getOrcSkirmishSpeech() {
        return "dolGuldur/orc/skirmish";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.DOL_GULDUR.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.DOL_GULDUR;
    }
}
