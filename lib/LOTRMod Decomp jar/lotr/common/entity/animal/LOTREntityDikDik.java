// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntities;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import java.util.UUID;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIPanic;
import lotr.common.entity.ai.LOTREntityAIAvoidWithChance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.EntityCreature;

public class LOTREntityDikDik extends EntityCreature implements LOTRAmbientCreature, LOTRRandomSkinEntity
{
    public LOTREntityDikDik(final World world) {
        super(world);
        this.setSize(0.6f, 1.0f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, (Class)LOTREntityLionBase.class, 12.0f, 1.5, 2.0));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAvoidWithChance(this, EntityPlayer.class, 12.0f, 1.5, 2.0, 0.1f));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 2.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.2));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && LOTRAmbientSpawnChecks.canSpawn((EntityLiving)this, 8, 4, 32, 4, Material.plants, Material.vine);
    }
    
    public float getBlockPathWeight(final int i, final int j, final int k) {
        final Block block = ((Entity)this).worldObj.getBlock(i, j - 1, k);
        if (block == Blocks.grass) {
            return 10.0f;
        }
        return ((Entity)this).worldObj.getLightBrightness(i, j, k) - 0.5f;
    }
    
    public int getTalkInterval() {
        return 300;
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
        return super.getSoundPitch() * 1.3f;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
}
