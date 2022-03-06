// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfArcher extends LOTREntityDorwinionElfWarrior
{
    public LOTREntityDorwinionElfArcher(final World world) {
        super(world);
    }
    
    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 30, 50, 24.0f);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.dorwinionElfBow));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getRangedWeapon());
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            ((EntityLiving)this).tasks.removeTask(super.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(super.rangedAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            ((EntityLiving)this).tasks.removeTask(super.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(super.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(2, super.rangedAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getRangedWeapon());
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        this.npcArrowAttack(target, f);
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
    }
}
