// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityLiving;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import lotr.common.entity.projectile.LOTREntitySpear;
import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIAttackOnCollide extends EntityAIBase
{
    protected World worldObj;
    protected EntityCreature theOwner;
    protected EntityLivingBase attackTarget;
    protected int attackTick;
    protected double moveSpeed;
    protected boolean sightNotRequired;
    protected PathEntity entityPathEntity;
    protected int pathCheckTimer;
    protected boolean avoidsWater;
    
    public LOTREntityAIAttackOnCollide(final EntityCreature entity, final double speed, final boolean flag) {
        this.attackTick = 0;
        this.theOwner = entity;
        this.worldObj = ((Entity)entity).worldObj;
        this.moveSpeed = speed;
        this.sightNotRequired = flag;
        this.avoidsWater = entity.getNavigator().getAvoidsWater();
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (this.theOwner instanceof LOTREntityNPC && ((LOTREntityNPC)this.theOwner).isPassive) {
            return false;
        }
        final EntityLivingBase entity = this.theOwner.getAttackTarget();
        if (entity == null) {
            return false;
        }
        this.attackTarget = entity;
        this.theOwner.getNavigator().setAvoidsWater(false);
        this.entityPathEntity = this.getPathEntity();
        if (this.entityPathEntity != null) {
            return true;
        }
        this.theOwner.getNavigator().setAvoidsWater(this.avoidsWater);
        return false;
    }
    
    public boolean continueExecuting() {
        if (!this.theOwner.isEntityAlive()) {
            return false;
        }
        this.attackTarget = this.theOwner.getAttackTarget();
        if (this.attackTarget == null || !this.attackTarget.isEntityAlive()) {
            return false;
        }
        if (this.sightNotRequired) {
            return this.theOwner.isWithinHomeDistance(MathHelper.floor_double(((Entity)this.attackTarget).posX), MathHelper.floor_double(((Entity)this.attackTarget).posY), MathHelper.floor_double(((Entity)this.attackTarget).posZ));
        }
        return !this.theOwner.getNavigator().noPath();
    }
    
    public void startExecuting() {
        this.theOwner.getNavigator().setPath(this.entityPathEntity, this.moveSpeed);
        this.pathCheckTimer = 0;
    }
    
    public void resetTask() {
        this.attackTarget = null;
        this.theOwner.getNavigator().clearPathEntity();
        this.theOwner.getNavigator().setAvoidsWater(this.avoidsWater);
    }
    
    public void updateTask() {
        this.updateLookAndPathing();
        if (this.attackTick > 0) {
            --this.attackTick;
        }
        final ItemStack weapon = this.theOwner.getHeldItem();
        if (weapon != null && weapon.getItem() instanceof LOTRItemSpear && this.attackTick <= 0 && this.theOwner instanceof LOTREntityNPC) {
            final LOTREntityNPC theNPC = (LOTREntityNPC)this.theOwner;
            final ItemStack spearBackup = theNPC.npcItemsInv.getSpearBackup();
            if (spearBackup != null) {
                final LOTRItemSpear spearItem = (LOTRItemSpear)weapon.getItem();
                final double d = this.theOwner.getDistanceToEntity((Entity)this.attackTarget);
                final double range = this.theOwner.getNavigator().getPathSearchRange();
                if (d > 5.0 && d < range * 0.75) {
                    final LOTREntitySpear spear = new LOTREntitySpear(this.worldObj, (EntityLivingBase)this.theOwner, this.attackTarget, weapon.copy(), 0.75f + (float)d * 0.025f, 0.5f);
                    this.worldObj.playSoundAtEntity((Entity)this.theOwner, "random.bow", 1.0f, 1.0f / (this.worldObj.rand.nextFloat() * 0.4f + 1.2f) + 0.25f);
                    this.worldObj.spawnEntityInWorld((Entity)spear);
                    this.attackTick = 30 + this.theOwner.getRNG().nextInt(20);
                    if (ItemStack.areItemStacksEqual(theNPC.npcItemsInv.getIdleItem(), theNPC.npcItemsInv.getMeleeWeapon())) {
                        theNPC.npcItemsInv.setIdleItem(spearBackup);
                    }
                    theNPC.npcItemsInv.setMeleeWeapon(spearBackup);
                    theNPC.npcItemsInv.setSpearBackup(null);
                    return;
                }
            }
        }
        float weaponReach = 1.0f;
        if (((Entity)this.theOwner).ridingEntity != null) {
            weaponReach = LOTREntityNPC.MOUNT_RANGE_BONUS;
        }
        weaponReach *= LOTRWeaponStats.getMeleeReachFactor(this.theOwner.getHeldItem());
        final float meleeRange = (float)((Entity)this.theOwner).boundingBox.getAverageEdgeLength() + weaponReach;
        if (this.theOwner.getDistanceSqToEntity((Entity)this.attackTarget) <= meleeRange * meleeRange && this.attackTick <= 0) {
            this.attackTick = LOTRWeaponStats.getAttackTimeMob(weapon);
            this.theOwner.attackEntityAsMob((Entity)this.attackTarget);
            this.theOwner.swingItem();
        }
    }
    
    protected void updateLookAndPathing() {
        this.theOwner.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0f, 30.0f);
        if (((Entity)this.theOwner).riddenByEntity != null && ((Entity)this.theOwner).riddenByEntity instanceof EntityLiving) {
            ((Entity)((Entity)this.theOwner).riddenByEntity).rotationYaw = ((Entity)this.theOwner).rotationYaw;
            ((EntityLivingBase)((Entity)this.theOwner).riddenByEntity).rotationYawHead = ((EntityLivingBase)this.theOwner).rotationYawHead;
        }
        if ((this.sightNotRequired || this.theOwner.getEntitySenses().canSee((Entity)this.attackTarget)) && --this.pathCheckTimer <= 0) {
            this.pathCheckTimer = 10 + this.theOwner.getRNG().nextInt(10);
            final PathEntity path = this.getPathEntity();
            if (path != null) {
                this.theOwner.getNavigator().setPath(path, this.moveSpeed);
            }
        }
    }
    
    private PathEntity getPathEntity() {
        if (((Entity)this.theOwner).ridingEntity != null) {
            return this.worldObj.getPathEntityToEntity((Entity)this.theOwner, (Entity)this.attackTarget, this.theOwner.getNavigator().getPathSearchRange(), true, this.theOwner.getNavigator().getCanBreakDoors(), this.theOwner.getNavigator().getAvoidsWater(), false);
        }
        return this.theOwner.getNavigator().getPathToEntityLiving((Entity)this.attackTarget);
    }
}
