// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityBlackUruk extends LOTREntityMordorOrc
{
    public LOTREntityBlackUruk(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        super.isWeakOrc = false;
        super.npcShield = LOTRShields.ALIGNMENT_BLACK_URUK;
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.5);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(7);
        if (i == 0 || i == 1 || i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarBlackUruk));
        }
        else if (i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBlackUruk));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlackUruk));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlackUrukPoisoned));
        }
        else if (i == 6) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerBlackUruk));
        }
        if (((Entity)this).rand.nextInt(6) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBlackUruk));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlackUruk));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlackUruk));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlackUruk));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetBlackUruk));
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killBlackUruk;
    }
    
    @Override
    protected void dropOrcItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.BLACK_URUK_FORT, 1, 2 + i);
        }
        if (flag) {
            int shinyShirtChance = 6000;
            shinyShirtChance -= i * 500;
            shinyShirtChance = Math.max(shinyShirtChance, 1);
            if (((Entity)this).rand.nextInt(shinyShirtChance) == 0) {
                this.func_145779_a(LOTRMod.bodyMithril, 1);
            }
        }
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }
    
    @Override
    public boolean canOrcSkirmish() {
        return false;
    }
}
