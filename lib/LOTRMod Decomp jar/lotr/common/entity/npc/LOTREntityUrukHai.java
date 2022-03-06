// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.fac.LOTRFaction;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityUrukHai extends LOTREntityOrc
{
    public LOTREntityUrukHai(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        super.isWeakOrc = false;
        super.npcShield = LOTRShields.ALIGNMENT_URUK_HAI;
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(8);
        if (i == 0 || i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarUruk));
        }
        else if (i == 2 || i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeUruk));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUruk));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUruk));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUrukPoisoned));
        }
        else if (i == 7) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUruk));
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearUruk));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUruk));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUruk));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUruk));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUruk));
        }
        return data;
    }
    
    @Override
    protected void dropOrcItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.URUK_TENT, 1, 2 + i);
        }
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
            final double range = 12.0;
            final List nearbySpawners = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTREntityNPCRespawner.class, ((Entity)this).boundingBox.expand(range, range, range));
            for (final Object obj : nearbySpawners) {
                final LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)obj;
                if (spawner.spawnClass1 != null && LOTREntityUrukHai.class.isAssignableFrom(spawner.spawnClass1)) {
                    LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.raidUrukCamp);
                    break;
                }
            }
        }
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.URUK_HAI;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killUrukHai;
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }
    
    @Override
    public boolean canOrcSkirmish() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "isengard/orc/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "isengard/orc/hired";
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
            return "isengard/orc/friendly";
        }
        return "isengard/orc/neutral";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.ISENGARD.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.ISENGARD;
    }
}
