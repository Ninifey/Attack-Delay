// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityRivendellWarrior extends LOTREntityRivendellElf
{
    public LOTREntityRivendellWarrior(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(2, super.meleeAttackAI);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(4) == 0);
        super.npcShield = LOTRShields.ALIGNMENT_RIVENDELL;
    }
    
    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 25, 40, 24.0f);
    }
    
    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(6);
        if (i == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmRivendell));
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearRivendell));
        }
        else {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRivendell));
        }
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rivendellBow));
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRivendell));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRivendell));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRivendell));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRivendell));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRivendell));
        }
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "rivendell/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "rivendell/elf/hired";
        }
        return "rivendell/warrior/friendly";
    }
}
