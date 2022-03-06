// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityRohirrimWarrior extends LOTREntityRohanMan
{
    public LOTREntityRohirrimWarrior(final World world) {
        super(world);
        this.addTargetTasks(true);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(3) == 0);
        super.npcShield = LOTRShields.ALIGNMENT_ROHAN;
    }
    
    public EntityAIBase createRohanAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.45, false);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.75);
        this.getEntityAttribute(LOTREntityNPC.horseAttackSpeed).setBaseValue(2.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(3) == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRohan));
        }
        else {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
        }
        if (((Entity)this).rand.nextInt(4) == 0) {
            super.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceRohan));
        }
        else {
            super.npcItemsInv.setMeleeWeaponMounted(super.npcItemsInv.getMeleeWeapon());
        }
        if (((Entity)this).rand.nextInt(4) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRohan));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setIdleItemMounted(super.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohan));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohan));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohan));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRohan));
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
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "rohan/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "rohan/warrior/hired";
        }
        return "rohan/warrior/friendly";
    }
}
