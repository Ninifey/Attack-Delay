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

public class LOTREntityDwarfAxeThrower extends LOTREntityDwarfWarrior
{
    public LOTREntityDwarfAxeThrower(final World world) {
        super(world);
    }
    
    public EntityAIBase getDwarfAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 40, 12.0f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.75);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeDwarven));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getRangedWeapon());
        return data;
    }
    
    @Override
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
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
            axeItem = new ItemStack(LOTRMod.throwingAxeDwarven);
        }
        final LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(((Entity)this).worldObj, (EntityLivingBase)this, target, axeItem, 1.0f, (float)this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).getAttributeValue());
        this.playSound("random.bow", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)axe);
        this.swingItem();
    }
}
