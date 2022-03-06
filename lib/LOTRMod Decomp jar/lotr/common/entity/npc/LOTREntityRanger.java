// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.LOTRCapes;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class LOTREntityRanger extends LOTREntityDunedain
{
    public EntityAIBase rangedAttackAI;
    public EntityAIBase meleeAttackAI;
    private int sneakCooldown;
    private EntityLivingBase prevRangerTarget;
    
    public LOTREntityRanger(final World world) {
        super(world);
        this.rangedAttackAI = this.createDunedainRangedAI();
        this.sneakCooldown = 0;
        this.addTargetTasks(true);
        super.npcCape = LOTRCapes.RANGER;
    }
    
    @Override
    protected EntityAIBase createDunedainAttackAI() {
        return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.5, true);
    }
    
    protected EntityAIBase createDunedainRangedAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 20, 40, 20.0f);
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    public boolean isRangerSneaking() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setRangerSneaking(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
        if (flag) {
            this.sneakCooldown = 20;
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.5);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
        super.npcItemsInv.setRangedWeapon(new ItemStack((Item)Items.bow));
        super.npcItemsInv.setIdleItem(null);
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRanger));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRanger));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRanger));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRanger));
        return data;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient) {
            if (((Entity)this).ridingEntity == null) {
                if (this.isRangerSneaking()) {
                    if (this.getAttackTarget() == null) {
                        if (this.sneakCooldown > 0) {
                            --this.sneakCooldown;
                        }
                        else {
                            this.setRangerSneaking(false);
                        }
                    }
                    else {
                        this.sneakCooldown = 20;
                    }
                }
                else {
                    this.sneakCooldown = 0;
                }
            }
            else if (this.isRangerSneaking()) {
                this.setRangerSneaking(false);
            }
        }
    }
    
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
    public void setAttackTarget(final EntityLivingBase target) {
        super.setAttackTarget(target);
        if (target != null && target != this.prevRangerTarget) {
            this.prevRangerTarget = target;
            if (!((Entity)this).worldObj.isClient && !this.isRangerSneaking() && ((Entity)this).ridingEntity == null) {
                this.setRangerSneaking(true);
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && !((Entity)this).worldObj.isClient && this.isRangerSneaking()) {
            this.setRangerSneaking(false);
        }
        return flag;
    }
    
    public void swingItem() {
        super.swingItem();
        if (!((Entity)this).worldObj.isClient && this.isRangerSneaking()) {
            this.setRangerSneaking(false);
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        this.dropNPCArrows(i);
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
        if (!this.isRangerSneaking()) {
            super.func_145780_a(i, j, k, block);
        }
    }
}
