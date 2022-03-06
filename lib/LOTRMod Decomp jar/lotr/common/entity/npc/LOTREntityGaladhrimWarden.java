// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;

public class LOTREntityGaladhrimWarden extends LOTREntityGaladhrimElf
{
    private int sneakCooldown;
    private EntityLivingBase prevElfTarget;
    
    public LOTREntityGaladhrimWarden(final World world) {
        super(world);
        this.sneakCooldown = 0;
        ((EntityLiving)this).tasks.addTask(2, super.rangedAttackAI);
    }
    
    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 25, 35, 24.0f);
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    public boolean isElfSneaking() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setElfSneaking(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
        if (flag) {
            this.sneakCooldown = 20;
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerElven));
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mallornBow));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getRangedWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHithlain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHithlain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHithlain));
        if (((Entity)this).rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetHithlain));
        }
        return data;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient) {
            if (this.isElfSneaking()) {
                if (this.getAttackTarget() == null) {
                    if (this.sneakCooldown > 0) {
                        --this.sneakCooldown;
                    }
                    else {
                        this.setElfSneaking(false);
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
    }
    
    @Override
    public void setAttackTarget(final EntityLivingBase target) {
        super.setAttackTarget(target);
        if (target != null && target != this.prevElfTarget) {
            this.prevElfTarget = target;
            if (!((Entity)this).worldObj.isClient && !this.isElfSneaking()) {
                this.setElfSneaking(true);
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && !((Entity)this).worldObj.isClient && this.isElfSneaking()) {
            this.setElfSneaking(false);
        }
        return flag;
    }
    
    public void swingItem() {
        super.swingItem();
        if (!((Entity)this).worldObj.isClient && this.isElfSneaking()) {
            this.setElfSneaking(false);
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "galadhrim/warrior/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "galadhrim/elf/hired";
        }
        return "galadhrim/warrior/friendly";
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
        if (!this.isElfSneaking()) {
            super.func_145780_a(i, j, k, block);
        }
    }
}
