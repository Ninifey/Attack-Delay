// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;

public class LOTREntitySwanKnight extends LOTREntityDolAmrothSoldier
{
    public LOTREntitySwanKnight(final World world) {
        super(world);
        this.addTargetTasks(true);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(4) == 0);
        super.npcShield = LOTRShields.ALIGNMENT_DOL_AMROTH;
    }
    
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDolAmroth));
        return horse;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(LOTREntityNPC.horseAttackSpeed).setBaseValue(2.0);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.75);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(4) == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearDolAmroth));
        }
        else {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDolAmroth));
        }
        if (((Entity)this).rand.nextInt(3) == 0) {
            super.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceDolAmroth));
        }
        else {
            super.npcItemsInv.setMeleeWeaponMounted(super.npcItemsInv.getMeleeWeapon());
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        super.npcItemsInv.setIdleItemMounted(super.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDolAmroth));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDolAmroth));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDolAmroth));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDolAmroth));
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
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killSwanKnight;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "gondor/swanKnight/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "gondor/swanKnight/hired";
        }
        return "gondor/swanKnight/friendly";
    }
}
