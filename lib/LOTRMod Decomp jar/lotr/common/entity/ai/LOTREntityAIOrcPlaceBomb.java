// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIOrcPlaceBomb extends EntityAIBase
{
    private World worldObj;
    private LOTREntityOrc attacker;
    private EntityLivingBase entityTarget;
    private double moveSpeed;
    private PathEntity entityPathEntity;
    private int rePathDelay;
    
    public LOTREntityAIOrcPlaceBomb(final LOTREntityOrc entity, final double speed) {
        this.attacker = entity;
        this.worldObj = ((Entity)entity).worldObj;
        this.moveSpeed = speed;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        final EntityLivingBase entity = this.attacker.getAttackTarget();
        if (entity == null) {
            return false;
        }
        if (this.attacker.npcItemsInv.getBomb() == null) {
            return false;
        }
        this.entityTarget = entity;
        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving((Entity)this.entityTarget);
        return this.entityPathEntity != null;
    }
    
    public boolean continueExecuting() {
        if (this.attacker.npcItemsInv.getBomb() == null) {
            return false;
        }
        final EntityLivingBase entity = this.attacker.getAttackTarget();
        return entity != null && this.entityTarget.isEntityAlive() && !this.attacker.getNavigator().noPath();
    }
    
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.moveSpeed);
        this.rePathDelay = 0;
    }
    
    public void resetTask() {
        this.entityTarget = null;
        this.attacker.getNavigator().clearPathEntity();
    }
    
    public void updateTask() {
        this.attacker.getLookHelper().setLookPositionWithEntity((Entity)this.entityTarget, 30.0f, 30.0f);
        if (this.attacker.getEntitySenses().canSee((Entity)this.entityTarget) && --this.rePathDelay <= 0) {
            this.rePathDelay = 4 + this.attacker.getRNG().nextInt(7);
            this.attacker.getNavigator().tryMoveToEntityLiving((Entity)this.entityTarget, this.moveSpeed);
        }
        if (this.attacker.getDistanceToEntity((Entity)this.entityTarget) < 3.0) {
            final LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(this.worldObj, ((Entity)this.attacker).posX, ((Entity)this.attacker).posY + 1.0, ((Entity)this.attacker).posZ, (EntityLivingBase)this.attacker);
            int meta = 0;
            final ItemStack bombItem = this.attacker.npcItemsInv.getBomb();
            if (bombItem != null && Block.getBlockFromItem(bombItem.getItem()) instanceof LOTRBlockOrcBomb) {
                meta = bombItem.getItemDamage();
            }
            bomb.setBombStrengthLevel(meta);
            bomb.setFuseFromHiredUnit();
            bomb.droppedByHiredUnit = this.attacker.hiredNPCInfo.isActive;
            bomb.droppedTargetingPlayer = (this.entityTarget instanceof EntityPlayer);
            this.worldObj.spawnEntityInWorld((Entity)bomb);
            this.worldObj.playSoundAtEntity((Entity)this.attacker, "game.tnt.primed", 1.0f, 1.0f);
            this.worldObj.playSoundAtEntity((Entity)this.attacker, "lotr:orc.fire", 1.0f, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f + 1.0f);
            this.attacker.npcItemsInv.setBomb(null);
            final int bombSlot = 5;
            if (this.attacker.hiredReplacedInv.hasReplacedEquipment(bombSlot)) {
                this.attacker.hiredReplacedInv.onEquipmentChanged(bombSlot, null);
            }
            this.attacker.refreshCurrentAttackMode();
        }
    }
}
