// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.entity.LOTREntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import lotr.common.world.biome.LOTRBiomeGenFarHaradSwamp;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import java.util.List;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;

public class LOTREntityCrocodile extends EntityMob
{
    private EntityAIBase targetAI;
    private boolean prevCanTarget;
    
    public LOTREntityCrocodile(final World world) {
        super(world);
        this.prevCanTarget = true;
        this.setSize(2.1f, 0.7f);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.5, false));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 12.0f));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        ((EntityLiving)this).targetTasks.addTask(0, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        ((EntityLiving)this).targetTasks.addTask(1, this.targetAI = (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true));
        ((EntityLiving)this).targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)LOTREntityNPC.class, 400, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
    }
    
    public int getSnapTime() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(20);
    }
    
    public void setSnapTime(final int i) {
        ((Entity)this).dataWatcher.updateObject(20, (Object)i);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public boolean canBreatheUnderwater() {
        return true;
    }
    
    protected String getLivingSound() {
        return "lotr:crocodile.say";
    }
    
    protected String getDeathSound() {
        return "lotr:crocodile.death";
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && this.isInWater()) {
            ((Entity)this).motionY += 0.02;
        }
        if (!((Entity)this).worldObj.isClient && this.getAttackTarget() != null) {
            final EntityLivingBase entity = this.getAttackTarget();
            if (!entity.isEntityAlive() || (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode)) {
                this.setAttackTarget((EntityLivingBase)null);
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            final boolean canTarget = this.getBrightness(1.0f) < 0.5f;
            if (canTarget != this.prevCanTarget) {
                if (canTarget) {
                    ((EntityLiving)this).targetTasks.addTask(1, this.targetAI);
                }
                else {
                    ((EntityLiving)this).targetTasks.removeTask(this.targetAI);
                }
            }
            this.prevCanTarget = canTarget;
        }
        if (!((Entity)this).worldObj.isClient) {
            final int i = this.getSnapTime();
            if (i > 0) {
                this.setSnapTime(i - 1);
            }
        }
        if (this.getAttackTarget() == null && ((Entity)this).worldObj.rand.nextInt(1000) == 0) {
            final List list = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)EntityAnimal.class, ((Entity)this).boundingBox.expand(12.0, 6.0, 12.0));
            if (!list.isEmpty()) {
                final EntityAnimal entityanimal = list.get(((Entity)this).rand.nextInt(list.size()));
                if (entityanimal.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) == null) {
                    this.setAttackTarget((EntityLivingBase)entityanimal);
                }
            }
        }
    }
    
    protected Item func_146068_u() {
        return Items.rotten_flesh;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int count = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), j = 0; j < count; ++j) {
            final int drop = ((Entity)this).rand.nextInt(5);
            switch (drop) {
                case 0: {
                    this.func_145779_a(Items.bone, 1);
                    break;
                }
                case 1: {
                    this.func_145779_a(Items.fish, 1);
                    break;
                }
                case 2: {
                    this.func_145779_a(Items.leather, 1);
                    break;
                }
                case 3: {
                    this.func_145779_a(LOTRMod.zebraRaw, 1);
                    break;
                }
                case 4: {
                    this.func_145779_a(LOTRMod.gemsbokHide, 1);
                    break;
                }
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean flag = super.attackEntityAsMob(entity);
        if (flag) {
            if (!((Entity)this).worldObj.isClient) {
                this.setSnapTime(20);
            }
            ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:crocodile.snap", this.getSoundVolume(), this.getSoundPitch());
        }
        return flag;
    }
    
    public boolean getCanSpawnHere() {
        final List nearbyCrocodiles = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)this.getClass(), ((Entity)this).boundingBox.expand(24.0, 12.0, 24.0));
        if (nearbyCrocodiles.size() > 3) {
            return false;
        }
        if (((Entity)this).worldObj.checkNoEntityCollision(((Entity)this).boundingBox) && this.isValidLightLevel() && ((Entity)this).worldObj.getCollidingBoundingBoxes((Entity)this, ((Entity)this).boundingBox).size() == 0) {
            for (int i = -8; i <= 8; ++i) {
                for (int j = -8; j <= 8; ++j) {
                    for (int k = -8; k <= 8; ++k) {
                        final int i2 = MathHelper.floor_double(((Entity)this).posX) + i;
                        final int j2 = MathHelper.floor_double(((Entity)this).posY) + j;
                        final int k2 = MathHelper.floor_double(((Entity)this).posZ) + k;
                        if (((Entity)this).worldObj.blockExists(i2, j2, k2)) {
                            final Block block = ((Entity)this).worldObj.getBlock(i2, j2, k2);
                            if (block.getMaterial() == Material.water) {
                                if (((Entity)this).posY > 60.0) {
                                    return true;
                                }
                                if (((Entity)this).rand.nextInt(50) == 0) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    protected boolean isValidLightLevel() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        return ((Entity)this).worldObj.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenFarHaradSwamp || super.isValidLightLevel();
    }
    
    public void moveEntityWithHeading(final float f, final float f1) {
        if (!((Entity)this).worldObj.isClient && this.isInWater() && this.getAttackTarget() != null) {
            this.moveFlying(f, f1, 0.1f);
        }
        super.moveEntityWithHeading(f, f1);
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
}
