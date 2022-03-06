// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import lotr.common.entity.projectile.LOTREntityFirePot;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityEasterlingFireThrower extends LOTREntityEasterlingWarrior
{
    public EntityAIBase rangedAttackAI;
    public EntityAIBase meleeAttackAI;
    
    public LOTREntityEasterlingFireThrower(final World world) {
        super(world);
        this.rangedAttackAI = this.createEasterlingRangedAI();
        super.spawnRidingHorse = false;
    }
    
    @Override
    protected EntityAIBase createEasterlingAttackAI() {
        return this.meleeAttackAI = super.createEasterlingAttackAI();
    }
    
    protected EntityAIBase createEasterlingRangedAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.3, 20, 30, 16.0f);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhun));
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rhunFirePot));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getRangedWeapon());
        return data;
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        final EntityArrow template = new EntityArrow(((Entity)this).worldObj, (EntityLivingBase)this, target, 1.0f, 0.5f);
        final LOTREntityFirePot pot = new LOTREntityFirePot(((Entity)this).worldObj, (EntityLivingBase)this);
        pot.setLocationAndAngles(((Entity)template).posX, ((Entity)template).posY, ((Entity)template).posZ, ((Entity)template).rotationYaw, ((Entity)template).rotationPitch);
        ((Entity)pot).motionX = ((Entity)template).motionX;
        ((Entity)pot).motionY = ((Entity)template).motionY;
        ((Entity)pot).motionZ = ((Entity)template).motionZ;
        this.playSound("random.bow", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)pot);
    }
    
    @Override
    public double getMeleeRange() {
        final EntityLivingBase target = this.getAttackTarget();
        if (target != null && target.isBurning()) {
            return Double.MAX_VALUE;
        }
        return super.getMeleeRange();
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
}
