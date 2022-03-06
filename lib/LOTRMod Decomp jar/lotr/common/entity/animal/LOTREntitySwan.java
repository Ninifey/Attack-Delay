// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.entity.LOTREntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIPanic;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.world.World;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.ai.EntityAIBase;
import java.util.Random;
import net.minecraft.entity.EntityCreature;

public class LOTREntitySwan extends EntityCreature implements LOTRAmbientCreature
{
    public float flapPhase;
    public float flapPower;
    public float prevFlapPower;
    public float prevFlapPhase;
    public float flapAccel;
    private int peckTime;
    private int peckLength;
    private int timeUntilHiss;
    private static Random violenceRand;
    private boolean assignedAttackOrFlee;
    private EntityAIBase attackAI;
    private EntityAIBase fleeAI;
    private IEntitySelector swanAttackRange;
    private static boolean wreckBalrogs;
    
    public LOTREntitySwan(final World world) {
        super(world);
        this.flapAccel = 1.0f;
        this.assignedAttackOrFlee = false;
        this.attackAI = new LOTREntityAIAttackOnCollide(this, 1.4, true);
        this.fleeAI = (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.8);
        this.swanAttackRange = (IEntitySelector)new IEntitySelector() {
            public boolean isEntityApplicable(final Entity entity) {
                return entity instanceof EntityLivingBase && entity.isEntityAlive() && LOTREntitySwan.this.getDistanceSqToEntity(entity) < 16.0;
            }
        };
        this.setSize(0.5f, 0.7f);
        this.getNavigator().setAvoidsWater(false);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, this.attackAI);
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLivingBase.class, 10.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        ((EntityLiving)this).targetTasks.addTask(0, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        if (LOTREntitySwan.wreckBalrogs) {
            ((EntityLiving)this).targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)LOTREntityBalrog.class, 0, true));
        }
        ((EntityLiving)this).targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true, false, this.swanAttackRange));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }
    
    protected boolean isAIEnabled() {
        return true;
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.prevFlapPhase = this.flapPhase;
        this.prevFlapPower = this.flapPower;
        this.flapPower += (((Entity)this).onGround ? -0.02f : 0.05f);
        this.flapPower = Math.max(0.0f, Math.min(this.flapPower, 1.0f));
        if (!((Entity)this).onGround) {
            this.flapAccel = 0.6f;
        }
        this.flapPhase += this.flapAccel;
        this.flapAccel *= 0.95f;
        if (!((Entity)this).onGround && ((Entity)this).motionY < 0.0) {
            ((Entity)this).motionY *= 0.6;
        }
        if (((Entity)this).inWater && ((Entity)this).motionY < 0.0) {
            ((Entity)this).motionY *= 0.01;
        }
        if (!((Entity)this).worldObj.isClient && this.getAttackTarget() != null) {
            final EntityLivingBase target = this.getAttackTarget();
            if (!target.isEntityAlive() || (target instanceof EntityPlayer && ((EntityPlayer)target).capabilities.isCreativeMode)) {
                this.setAttackTarget((EntityLivingBase)null);
            }
        }
        if (this.peckLength > 0) {
            ++this.peckTime;
            if (this.peckTime >= this.peckLength) {
                this.peckTime = 0;
                this.peckLength = 0;
            }
        }
        else {
            this.peckTime = 0;
        }
    }
    
    private boolean isViolentSwan() {
        final long seed = this.getUniqueID().getLeastSignificantBits();
        LOTREntitySwan.violenceRand.setSeed(seed);
        return LOTREntitySwan.violenceRand.nextBoolean();
    }
    
    public void updateAITasks() {
        if (!this.assignedAttackOrFlee) {
            ((EntityLiving)this).tasks.removeTask(this.attackAI);
            ((EntityLiving)this).tasks.removeTask(this.fleeAI);
            final boolean violent = this.isViolentSwan();
            if (violent) {
                ((EntityLiving)this).tasks.addTask(1, this.attackAI);
            }
            else {
                ((EntityLiving)this).tasks.addTask(1, this.fleeAI);
            }
            this.assignedAttackOrFlee = true;
        }
        super.updateAITasks();
        if (this.timeUntilHiss <= 0) {
            if (this.getAttackTarget() == null && ((Entity)this).rand.nextInt(3) == 0) {
                final double range = 8.0;
                final List nearbyPlayers = ((Entity)this).worldObj.selectEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this).boundingBox.expand(range, range, range), LOTRMod.selectNonCreativePlayers());
                if (!nearbyPlayers.isEmpty()) {
                    final EntityPlayer entityplayer = nearbyPlayers.get(((Entity)this).rand.nextInt(nearbyPlayers.size()));
                    this.getNavigator().clearPathEntity();
                    float hissLook = (float)Math.toDegrees(Math.atan2(((Entity)entityplayer).posZ - ((Entity)this).posZ, ((Entity)entityplayer).posX - ((Entity)this).posX));
                    final float n;
                    hissLook = (n = hissLook - 90.0f);
                    ((EntityLivingBase)this).rotationYawHead = n;
                    ((Entity)this).rotationYaw = n;
                    ((Entity)this).worldObj.setEntityState((Entity)this, (byte)21);
                    this.playSound("lotr:swan.hiss", this.getSoundVolume(), this.getSoundPitch());
                    this.timeUntilHiss = 80 + ((Entity)this).rand.nextInt(80);
                }
            }
        }
        else {
            --this.timeUntilHiss;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 20) {
            this.peckLength = 10;
        }
        else if (b == 21) {
            this.peckLength = 40;
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public float getPeckAngle(final float tick) {
        if (this.peckLength == 0) {
            return 0.0f;
        }
        final float peck = (this.peckTime + tick) / this.peckLength;
        final float cutoff = 0.2f;
        if (peck < cutoff) {
            return peck / cutoff;
        }
        if (peck < 1.0f - cutoff) {
            return 1.0f;
        }
        return (1.0f - peck) / cutoff;
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        if (LOTREntitySwan.wreckBalrogs && entity instanceof LOTREntityBalrog) {
            f *= 50.0f;
        }
        final boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
        if (flag) {
            ((Entity)this).worldObj.setEntityState((Entity)this, (byte)20);
            if (LOTREntitySwan.wreckBalrogs && entity instanceof LOTREntityBalrog) {
                entity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * 2.0f), 0.2, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * 2.0f));
                this.setFire(0);
            }
            return true;
        }
        return false;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, float f) {
        final Entity entity = damagesource.getEntity();
        if (LOTREntitySwan.wreckBalrogs && entity instanceof LOTREntityBalrog) {
            f /= 20.0f;
        }
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag) {
            if (LOTREntitySwan.wreckBalrogs && entity instanceof LOTREntityBalrog) {
                this.setFire(0);
            }
            return true;
        }
        return false;
    }
    
    protected void fall(final float f) {
    }
    
    public void dropFewItems(final boolean flag, final int i) {
        for (int feathers = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), l = 0; l < feathers; ++l) {
            this.func_145779_a(LOTRMod.swanFeather, 1);
        }
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && LOTRAmbientSpawnChecks.canSpawn((EntityLiving)this, 16, 8, 40, 2, Material.water);
    }
    
    public float getBlockPathWeight(final int i, final int j, final int k) {
        return (((Entity)this).worldObj.getBlock(i, j - 1, k) == ((Entity)this).worldObj.getBiomeGenForCoords(i, k).topBlock) ? 10.0f : (((Entity)this).worldObj.getLightBrightness(i, j, k) - 0.5f);
    }
    
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 1 + ((Entity)this).worldObj.rand.nextInt(2);
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
    
    static {
        LOTREntitySwan.violenceRand = new Random();
        LOTREntitySwan.wreckBalrogs = false;
    }
}
