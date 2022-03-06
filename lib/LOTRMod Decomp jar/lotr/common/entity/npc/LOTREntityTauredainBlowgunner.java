// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.item.LOTRItemDart;
import lotr.common.item.LOTRItemBlowgun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityTauredainBlowgunner extends LOTREntityTauredain
{
    public LOTREntityTauredainBlowgunner(final World world) {
        super(world);
        this.addTargetTasks(true);
    }
    
    public EntityAIBase createHaradrimAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.5, 10, 30, 16.0f);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.tauredainBlowgun));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsTauredain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsTauredain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyTauredain));
        return data;
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        final ItemStack heldItem = this.getHeldItem();
        float str = 1.0f + this.getDistanceToEntity((Entity)target) / 16.0f * 0.015f;
        str *= LOTRItemBlowgun.getBlowgunLaunchSpeedFactor(heldItem);
        final LOTREntityDart dart = ((LOTRItemDart)LOTRMod.tauredainDart).createDart(((Entity)this).worldObj, (EntityLivingBase)this, target, new ItemStack(LOTRMod.tauredainDart), str, 1.0f);
        if (heldItem != null) {
            LOTRItemBlowgun.applyBlowgunModifiers(dart, heldItem);
        }
        this.playSound("lotr:item.dart", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 1.2f) + 0.5f);
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)dart);
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        this.dropNPCAmmo(LOTRMod.tauredainDart, i);
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "tauredain/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "tauredain/warrior/hired";
        }
        return "tauredain/warrior/friendly";
    }
}
