// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public abstract class LOTREntitySkeletalWraith extends LOTREntityNPC
{
    public LOTREntitySkeletalWraith(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        ((Entity)this).isImmuneToFire = true;
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIRestrictSun((EntityCreature)this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.2, false));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HOSTILE;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
    
    @Override
    public void onLivingUpdate() {
        if (((Entity)this).worldObj.isDaytime() && !((Entity)this).worldObj.isClient) {
            final float f = this.getBrightness(1.0f);
            if (f > 0.5f && ((Entity)this).rand.nextFloat() * 30.0f < (f - 0.4f) * 2.0f && ((Entity)this).worldObj.canBlockSeeTheSky(MathHelper.floor_double(((Entity)this).posX), MathHelper.floor_double(((Entity)this).posY), MathHelper.floor_double(((Entity)this).posZ))) {
                boolean flag = true;
                final ItemStack itemstack = this.getEquipmentInSlot(4);
                if (itemstack != null) {
                    if (itemstack.isItemStackDamageable()) {
                        itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + ((Entity)this).rand.nextInt(2));
                        if (itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage()) {
                            this.renderBrokenItemStack(itemstack);
                            this.setCurrentItemOrArmor(4, (ItemStack)null);
                        }
                    }
                    flag = false;
                }
                if (flag) {
                    this.setFire(8);
                }
            }
        }
        super.onLivingUpdate();
        if (((Entity)this).rand.nextBoolean()) {
            ((Entity)this).worldObj.spawnParticle("smoke", ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(Items.bone, 1);
        }
    }
    
    @Override
    public boolean canDropRares() {
        return false;
    }
    
    protected String getLivingSound() {
        return "mob.skeleton.say";
    }
    
    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }
    
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
}
