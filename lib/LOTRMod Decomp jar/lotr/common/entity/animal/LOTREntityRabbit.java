// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.entity.npc.LOTRFarmhand;
import net.minecraft.util.MathHelper;
import net.minecraft.block.material.Material;
import lotr.common.LOTRMod;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.util.DamageSource;
import java.util.UUID;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIRabbitEatCrops;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.ai.LOTREntityAIAvoidWithChance;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIFlee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.EntityCreature;

public class LOTREntityRabbit extends EntityCreature implements LOTRAmbientCreature, LOTRRandomSkinEntity
{
    private static final String fleeSound = "lotr:rabbit.flee";
    
    public LOTREntityRabbit(final World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIFlee(this, 2.0));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAvoidWithChance(this, EntityPlayer.class, 4.0f, 1.3, 1.5, 0.05f, "lotr:rabbit.flee"));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAvoidWithChance(this, LOTREntityNPC.class, 4.0f, 1.3, 1.5, 0.05f, "lotr:rabbit.flee"));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIRabbitEatCrops(this, 1.2));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLivingBase.class, 8.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    public boolean isRabbitEating() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setRabbitEating(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    protected boolean isAIEnabled() {
        return true;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && !((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer && this.isRabbitEating()) {
            final EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.attackRabbit);
        }
        return flag;
    }
    
    public void dropFewItems(final boolean flag, final int i) {
        for (int meat = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), l = 0; l < meat; ++l) {
            if (this.isBurning()) {
                this.func_145779_a(LOTRMod.rabbitCooked, 1);
            }
            else {
                this.func_145779_a(LOTRMod.rabbitRaw, 1);
            }
        }
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere()) {
            final boolean flag = LOTRAmbientSpawnChecks.canSpawn((EntityLiving)this, 8, 4, 32, 4, Material.plants, Material.vine);
            if (flag) {
                final int i = MathHelper.floor_double(((Entity)this).posX);
                final int j = MathHelper.floor_double(((Entity)this).posY);
                final int k = MathHelper.floor_double(((Entity)this).posZ);
                return !this.anyFarmhandsNearby(i, j, k);
            }
        }
        return false;
    }
    
    public boolean anyFarmhandsNearby(final int i, final int j, final int k) {
        final int range = 16;
        final List farmhands = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTRFarmhand.class, AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).expand((double)range, (double)range, (double)range));
        return !farmhands.isEmpty();
    }
    
    public float getBlockPathWeight(final int i, final int j, final int k) {
        final Block block = ((Entity)this).worldObj.getBlock(i, j - 1, k);
        if (block == Blocks.grass) {
            return 10.0f;
        }
        return ((Entity)this).worldObj.getLightBrightness(i, j, k) - 0.5f;
    }
    
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 1 + ((Entity)this).rand.nextInt(2);
    }
    
    protected String getHurtSound() {
        return "lotr:rabbit.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:rabbit.death";
    }
    
    public int getTalkInterval() {
        return 200;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
}
