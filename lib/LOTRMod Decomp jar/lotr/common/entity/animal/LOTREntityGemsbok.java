// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntities;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityAgeable;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.init.Items;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityAnimal;

public class LOTREntityGemsbok extends EntityAnimal
{
    public LOTREntityGemsbok(final World world) {
        super(world);
        this.setSize(0.9f, 1.4f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.3));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2, Items.wheat, false));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent((EntityAnimal)this, 1.1));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 6.0f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(22.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack.getItem() == Items.wheat;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int j = 1 + ((Entity)this).rand.nextInt(4) + ((Entity)this).rand.nextInt(1 + i), k = 0; k < j; ++k) {
            this.func_145779_a(LOTRMod.gemsbokHide, 1);
        }
        if (((Entity)this).rand.nextBoolean()) {
            this.func_145779_a(LOTRMod.gemsbokHorn, 1);
        }
    }
    
    public EntityAgeable createChild(final EntityAgeable entity) {
        return (EntityAgeable)new LOTREntityGemsbok(((Entity)this).worldObj);
    }
    
    protected String getLivingSound() {
        return "lotr:deer.say";
    }
    
    protected String getHurtSound() {
        return "lotr:deer.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:deer.death";
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * this.getGemsbokSoundPitch();
    }
    
    protected float getGemsbokSoundPitch() {
        return 0.8f;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
}
