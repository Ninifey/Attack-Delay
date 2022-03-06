// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntityWoodElfWarrior extends LOTREntityWoodElf
{
    public LOTREntityWoodElfWarrior(final World world) {
        super(world);
        ((EntityLiving)this).tasks.removeTask(super.rangedAttackAI);
        ((EntityLiving)this).tasks.addTask(2, super.meleeAttackAI);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(4) == 0);
        super.npcShield = LOTRShields.ALIGNMENT_WOOD_ELF;
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityElk elk = new LOTREntityElk(((Entity)this).worldObj);
        elk.setMountArmor(new ItemStack(LOTRMod.elkArmorWoodElven));
        return elk;
    }
    
    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 25, 35, 24.0f);
    }
    
    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
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
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmWoodElven));
        }
        else if (i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearWoodElven));
        }
        else {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordWoodElven));
        }
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mirkwoodBow));
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearWoodElven));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsWoodElven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsWoodElven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyWoodElven));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetWoodElven));
        }
        else {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
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
            return "woodElf/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "woodElf/elf/hired";
        }
        if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= getWoodlandTrustLevel()) {
            return "woodElf/warrior/friendly";
        }
        return "woodElf/elf/neutral";
    }
}
