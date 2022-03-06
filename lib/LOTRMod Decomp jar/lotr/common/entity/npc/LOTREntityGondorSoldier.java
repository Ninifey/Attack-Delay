// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityGondorSoldier extends LOTREntityGondorLevyman
{
    public LOTREntityGondorSoldier(final World world) {
        super(world);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(6) == 0);
        super.npcShield = LOTRShields.ALIGNMENT_GONDOR;
    }
    
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.45, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.75);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(6);
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerGondor));
        }
        else if (i == 4) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
        }
        else if (i == 5) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeGondor));
        }
        if (((Entity)this).rand.nextInt(3) == 0) {
            super.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceGondor));
        }
        else {
            super.npcItemsInv.setMeleeWeaponMounted(super.npcItemsInv.getMeleeWeapon());
        }
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearGondor));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setIdleItemMounted(super.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGondor));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGondor));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGondor));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetGondor));
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            if (mounted) {
                this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItemMounted());
            }
            else {
                this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
            }
        }
        else if (mounted) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeaponMounted());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        if (((Entity)this).rand.nextInt(8) == 0) {
            return LOTRMiniQuestFactory.GONDOR_KILL_RENEGADE.createQuest(this);
        }
        return super.createMiniQuest();
    }
}
