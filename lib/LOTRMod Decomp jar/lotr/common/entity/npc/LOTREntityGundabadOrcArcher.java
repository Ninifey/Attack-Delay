// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityGundabadOrcArcher extends LOTREntityGundabadOrc
{
    public LOTREntityGundabadOrcArcher(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 30, 60, 16.0f);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextInt(4) == 0) {
            if (((Entity)this).rand.nextBoolean()) {
                super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.ironCrossbow));
            }
            else {
                super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.bronzeCrossbow));
            }
        }
        else if (((Entity)this).rand.nextBoolean()) {
            super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.orcBow));
        }
        else {
            super.npcItemsInv.setRangedWeapon(new ItemStack((Item)Items.bow));
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
    
    private boolean isCrossbowOrc() {
        final ItemStack itemstack = super.npcItemsInv.getRangedWeapon();
        return itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow;
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        if (this.isCrossbowOrc()) {
            this.npcCrossbowAttack(target, f);
        }
        else {
            this.npcArrowAttack(target, f);
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        if (this.isCrossbowOrc()) {
            this.dropNPCCrossbowBolts(i);
        }
        else {
            this.dropNPCArrows(i);
        }
    }
}
