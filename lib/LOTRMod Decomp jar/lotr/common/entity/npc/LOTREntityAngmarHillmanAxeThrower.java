// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityAngmarHillmanAxeThrower extends LOTREntityAngmarHillmanWarrior
{
    public LOTREntityAngmarHillmanAxeThrower(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase getHillmanAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.4, 40, 60, 12.0f);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(3) == 0) {
            super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeIron));
        }
        else {
            super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeBronze));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getRangedWeapon());
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getRangedWeapon());
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        ItemStack axeItem = super.npcItemsInv.getRangedWeapon();
        if (axeItem == null) {
            axeItem = new ItemStack(LOTRMod.throwingAxeIron);
        }
        final LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(((Entity)this).worldObj, (EntityLivingBase)this, target, axeItem, 1.0f, (float)this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).getAttributeValue());
        this.playSound("random.bow", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)axe);
        this.swingItem();
    }
}
