// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EnumCreatureAttribute;
import lotr.common.LOTRAchievement;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.enchant.LOTREnchantment;
import net.minecraft.util.DamageSource;
import lotr.common.entity.projectile.LOTREntityMarshWraithBall;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.world.World;
import java.util.UUID;

public class LOTREntityMarshWraith extends LOTREntityNPC
{
    public UUID attackTargetUUID;
    private boolean checkedForAttackTarget;
    private int timeUntilDespawn;
    private static final int maxTimeUntilDespawn = 100;
    
    public LOTREntityMarshWraith(final World world) {
        super(world);
        this.timeUntilDespawn = -1;
        this.setSize(0.6f, 1.8f);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.6, 40, 12.0f));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        ((Entity)this).ignoreFrustumCheck = true;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }
    
    public int getSpawnFadeTime() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(16);
    }
    
    public void setSpawnFadeTime(final int i) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)i);
    }
    
    public int getDeathFadeTime() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setDeathFadeTime(final int i) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)i);
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HOSTILE;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("SpawnFadeTime", this.getSpawnFadeTime());
        nbt.setInteger("DeathFadeTime", this.getDeathFadeTime());
        if (this.attackTargetUUID != null) {
            nbt.setLong("TargetUUIDMost", this.attackTargetUUID.getMostSignificantBits());
            nbt.setLong("TargetUUIDLeast", this.attackTargetUUID.getLeastSignificantBits());
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSpawnFadeTime(nbt.getInteger("SpawnFadeTime"));
        this.setDeathFadeTime(nbt.getInteger("DeathFadeTime"));
        if (nbt.hasKey("TargetUUIDMost") && nbt.hasKey("TargetUUIDLeast")) {
            this.attackTargetUUID = new UUID(nbt.getLong("TargetUUIDMost"), nbt.getLong("TargetUUIDLeast"));
        }
    }
    
    public void setInWeb() {
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && !((Entity)this).isDead) {
            final int hover = 2;
            final int i = MathHelper.floor_double(((Entity)this).posX);
            final int j = MathHelper.floor_double(((Entity)this).posY);
            final int k = MathHelper.floor_double(((Entity)this).posZ);
            double newY = ((Entity)this).posY;
            for (int j2 = 0; j2 <= hover; ++j2) {
                final int j3 = j - j2;
                final Block block = ((Entity)this).worldObj.getBlock(i, j3, k);
                final Material material = block.getMaterial();
                if (material.isSolid() || material.isLiquid()) {
                    newY = Math.max(newY, j + j2 + 1);
                }
            }
            ((Entity)this).motionY += (newY - ((Entity)this).posY) * 0.04;
        }
        if (((Entity)this).rand.nextBoolean()) {
            ((Entity)this).worldObj.spawnParticle("smoke", ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, 0.0, 0.0, 0.0);
        }
        if (!((Entity)this).worldObj.isClient) {
            if (this.getAttackTarget() == null && this.attackTargetUUID != null && !this.checkedForAttackTarget) {
                for (int l = 0; l < ((Entity)this).worldObj.loadedEntityList.size(); ++l) {
                    final Entity entity = ((Entity)this).worldObj.loadedEntityList.get(l);
                    if (entity instanceof EntityLiving && entity.getUniqueID().equals(this.attackTargetUUID)) {
                        this.setAttackTarget((EntityLivingBase)entity);
                        break;
                    }
                }
                this.checkedForAttackTarget = true;
            }
            if (this.getSpawnFadeTime() < 30) {
                this.setSpawnFadeTime(this.getSpawnFadeTime() + 1);
            }
            if (this.getDeathFadeTime() > 0) {
                this.setDeathFadeTime(this.getDeathFadeTime() - 1);
            }
            if (this.getSpawnFadeTime() == 30 && this.getDeathFadeTime() == 0) {
                if (this.getAttackTarget() == null || ((Entity)this.getAttackTarget()).isDead) {
                    this.setDeathFadeTime(30);
                }
                else {
                    if (this.timeUntilDespawn == -1) {
                        this.timeUntilDespawn = 100;
                    }
                    final int l = MathHelper.floor_double(((Entity)this.getAttackTarget()).posX);
                    final int m = MathHelper.floor_double(((Entity)this.getAttackTarget()).boundingBox.minY);
                    final int k2 = MathHelper.floor_double(((Entity)this.getAttackTarget()).posZ);
                    if (((Entity)this).worldObj.getBlock(l, m, k2).getMaterial() == Material.water || ((Entity)this).worldObj.getBlock(l, m - 1, k2).getMaterial() == Material.water) {
                        this.timeUntilDespawn = 100;
                    }
                    else if (this.timeUntilDespawn > 0) {
                        --this.timeUntilDespawn;
                    }
                    else {
                        this.setDeathFadeTime(30);
                        this.setAttackTarget(null);
                    }
                }
            }
            if (this.getDeathFadeTime() == 1) {
                this.setDead();
            }
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        if (this.getSpawnFadeTime() == 30 && this.getDeathFadeTime() == 0) {
            final LOTREntityMarshWraithBall ball = new LOTREntityMarshWraithBall(((Entity)this).worldObj, (EntityLivingBase)this, target);
            this.playSound("lotr:wraith.marshWraith_shoot", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
            ((Entity)this).worldObj.spawnEntityInWorld((Entity)ball);
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        boolean vulnerable = false;
        final Entity entity = damagesource.getEntity();
        if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage()) {
            final ItemStack itemstack = ((EntityLivingBase)entity).getHeldItem();
            if (itemstack != null && LOTREnchantmentHelper.hasEnchant(itemstack, LOTREnchantment.baneWraith)) {
                vulnerable = true;
            }
        }
        if (vulnerable && this.getDeathFadeTime() == 0) {
            final boolean flag = super.attackEntityFrom(damagesource, f);
            if (flag) {
                this.timeUntilDespawn = 100;
            }
            return flag;
        }
        return false;
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient) {
            this.setDeathFadeTime(30);
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int flesh = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < flesh; ++l) {
            this.func_145779_a(Items.rotten_flesh, 1);
        }
        this.dropChestContents(LOTRChestContents.MARSH_REMAINS, 1, 3 + i);
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killMarshWraith;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected String getHurtSound() {
        return "lotr:wight.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:wight.death";
    }
    
    public boolean handleWaterMovement() {
        return false;
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
    }
    
    @Override
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        return false;
    }
}
