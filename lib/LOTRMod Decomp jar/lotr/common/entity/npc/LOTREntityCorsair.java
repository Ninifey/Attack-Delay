// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRFoods;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.LOTRShields;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityCorsair extends LOTREntityUmbarian
{
    private EntityAIBase rangedAttackAI;
    private EntityAIBase meleeAttackAI;
    private static ItemStack[] weaponsCorsair;
    
    public LOTREntityCorsair(final World world) {
        super(world);
        this.rangedAttackAI = this.createHaradrimRangedAttackAI();
        this.addTargetTasks(true);
        super.spawnRidingHorse = false;
        super.npcShield = LOTRShields.ALIGNMENT_CORSAIR;
    }
    
    @Override
    protected EntityAIBase createHaradrimAttackAI() {
        return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, true);
    }
    
    protected EntityAIBase createHaradrimRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.5, 30, 40, 16.0f);
    }
    
    @Override
    protected LOTRFoods getHaradrimFoods() {
        return LOTRFoods.CORSAIR;
    }
    
    @Override
    protected LOTRFoods getHaradrimDrinks() {
        return LOTRFoods.CORSAIR_DRINK;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.5);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityCorsair.weaponsCorsair.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityCorsair.weaponsCorsair[i].copy());
        if (((Entity)this).rand.nextInt(5) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearCorsair));
        }
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.nearHaradBow));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsCorsair));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsCorsair));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyCorsair));
        if (((Entity)this).rand.nextInt(2) == 0) {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetCorsair));
        }
        return data;
    }
    
    @Override
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        if (mode == AttackMode.MELEE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(2, this.meleeAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
        if (mode == AttackMode.RANGED) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(2, this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getRangedWeapon());
        }
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase entity) {
        super.onKillEntity(entity);
        if (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).canDropRares() && ((Entity)this).rand.nextInt(2) == 0) {
            int coins = this.getRandomCoinDropAmount();
            coins *= (int)MathHelper.randomFloatClamp(((Entity)this).rand, 1.0f, 3.0f);
            if (coins > 0) {
                entity.func_145779_a(LOTRMod.silverCoin, coins);
            }
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        this.dropNPCArrows(i);
    }
    
    @Override
    protected void dropHaradrimItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(3) == 0) {
            this.dropChestContents(LOTRChestContents.CORSAIR, 1, 2 + i);
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "nearHarad/umbar/corsair/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "nearHarad/umbar/corsair/hired";
        }
        return "nearHarad/umbar/corsair/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.CORSAIR.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.CORSAIR;
    }
    
    static {
        LOTREntityCorsair.weaponsCorsair = new ItemStack[] { new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.daggerCorsair), new ItemStack(LOTRMod.daggerCorsairPoisoned), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.battleaxeCorsair), new ItemStack(LOTRMod.battleaxeCorsair) };
    }
}
