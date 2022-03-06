// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityUtumnoOrc extends LOTREntityOrc
{
    public LOTREntityUtumnoOrc(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        super.isWeakOrc = false;
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, true);
    }
    
    @Override
    public void setupNPCName() {
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(((Entity)this).rand, ((Entity)this).rand.nextBoolean()));
        }
        else {
            super.familyInfo.setName(LOTRNames.getOrcName(((Entity)this).rand));
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(6);
        if (i == 0 || i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUtumno));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumno));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumnoPoisoned));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUtumno));
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearUtumno));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUtumno));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUtumno));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUtumno));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUtumno));
        }
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killUtumnoOrc;
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.65f;
    }
    
    @Override
    public boolean canOrcSkirmish() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return "utumno/orc/hostile";
    }
}
