// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.SharedMonsterAttributes;
import java.util.UUID;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.init.Items;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import lotr.common.entity.LOTRRandomSkinEntity;

public class LOTREntityDeer extends LOTREntityAnimalMF implements LOTRRandomSkinEntity
{
    public LOTREntityDeer(final World world) {
        super(world);
        this.setSize(0.8f, 1.0f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.8));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2, Items.wheat, false));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent((EntityAnimal)this, 1.4));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.4));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    @Override
    public Class getAnimalMFBaseClass() {
        return this.getClass();
    }
    
    @Override
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
        this.setMale(((Entity)this).rand.nextBoolean());
    }
    
    @Override
    public boolean isMale() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(20) == 1;
    }
    
    public void setMale(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(20, (Object)(byte)(flag ? 1 : 0));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("DeerMale", this.isMale());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setMale(nbt.getBoolean("DeerMale"));
    }
    
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack.getItem() == Items.wheat;
    }
    
    public EntityAgeable createChild(final EntityAgeable entity) {
        final LOTREntityDeer deer = new LOTREntityDeer(((Entity)this).worldObj);
        deer.setMale(((Entity)this).rand.nextBoolean());
        return (EntityAgeable)deer;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int hide = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), l = 0; l < hide; ++l) {
            this.func_145779_a(Items.leather, 1);
        }
        for (int meat = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), j = 0; j < meat; ++j) {
            if (this.isBurning()) {
                this.func_145779_a(LOTRMod.deerCooked, 1);
            }
            else {
                this.func_145779_a(LOTRMod.deerRaw, 1);
            }
        }
    }
    
    public int getTalkInterval() {
        return 300;
    }
    
    protected float getSoundVolume() {
        return 0.5f;
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
}
