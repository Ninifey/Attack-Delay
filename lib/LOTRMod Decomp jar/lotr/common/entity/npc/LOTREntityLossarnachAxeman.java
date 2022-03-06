// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.LOTRShields;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityLossarnachAxeman extends LOTREntityGondorSoldier
{
    private EntityAIBase rangedAttackAI;
    private EntityAIBase meleeAttackAI;
    
    public LOTREntityLossarnachAxeman(final World world) {
        super(world);
        this.rangedAttackAI = this.createGondorRangedAI();
        super.spawnRidingHorse = false;
        super.npcShield = LOTRShields.ALIGNMENT_LOSSARNACH;
    }
    
    @Override
    public EntityAIBase createGondorAttackAI() {
        return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }
    
    protected EntityAIBase createGondorRangedAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.3, 30, 50, 16.0f);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeLossarnach));
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeLossarnach));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsLossarnach));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsLossarnach));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLossarnach));
        if (((Entity)this).rand.nextInt(3) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetLossarnach));
        }
        else {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        return data;
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        ItemStack axeItem = super.npcItemsInv.getRangedWeapon();
        if (axeItem == null) {
            axeItem = new ItemStack(LOTRMod.throwingAxeLossarnach);
        }
        final LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(((Entity)this).worldObj, (EntityLivingBase)this, target, axeItem, 1.0f, (float)this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).getAttributeValue());
        this.playSound("random.bow", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)axe);
        this.swingItem();
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
