// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.nbt.NBTBase;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.world.biome.LOTRBiomeGenMirkwood;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.item.LOTRItemBarrel;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.Entity;

public class LOTREntityBarrel extends Entity
{
    private boolean field_70279_a;
    private double speedMultiplier;
    private static double minSpeedMultiplier;
    private static double maxSpeedMultiplier;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    public NBTTagCompound barrelItemData;
    
    public LOTREntityBarrel(final World world) {
        super(world);
        this.field_70279_a = true;
        this.speedMultiplier = LOTREntityBarrel.minSpeedMultiplier;
        super.preventEntitySpawning = true;
        this.setSize(1.0f, 1.0f);
        super.yOffset = 0.0f;
    }
    
    public LOTREntityBarrel(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1 + super.yOffset, d2);
        super.motionX = 0.0;
        super.motionY = 0.0;
        super.motionZ = 0.0;
        super.prevPosX = d;
        super.prevPosY = d1;
        super.prevPosZ = d2;
    }
    
    protected void entityInit() {
        super.dataWatcher.addObject(17, (Object)0);
        super.dataWatcher.addObject(18, (Object)1);
        super.dataWatcher.addObject(19, (Object)0.0f);
        super.dataWatcher.addObject(20, (Object)new ItemStack(LOTRMod.barrel));
    }
    
    public void setTimeSinceHit(final int i) {
        super.dataWatcher.updateObject(17, (Object)i);
    }
    
    public int getTimeSinceHit() {
        return super.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setForwardDirection(final int i) {
        super.dataWatcher.updateObject(18, (Object)i);
    }
    
    public int getForwardDirection() {
        return super.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void setDamageTaken(final float f) {
        super.dataWatcher.updateObject(19, (Object)f);
    }
    
    public float getDamageTaken() {
        return super.dataWatcher.getWatchableObjectFloat(19);
    }
    
    private void setBarrelItem(final ItemStack itemstack) {
        super.dataWatcher.updateObject(20, (Object)itemstack);
    }
    
    private ItemStack getBarrelItem() {
        return super.dataWatcher.getWatchableObjectItemStack(20);
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity par1Entity) {
        return par1Entity.boundingBox;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return super.boundingBox;
    }
    
    public boolean canBePushed() {
        return true;
    }
    
    public double getMountedYOffset() {
        return 0.5;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!super.worldObj.isClient && !super.isDead) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + f * 10.0f);
            final Block.SoundType stepSound = LOTRMod.barrel.stepSound;
            this.playSound(stepSound.getDigResourcePath(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getFrequency() * 0.8f);
            this.setBeenAttacked();
            final boolean isCreative = damagesource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damagesource.getEntity()).capabilities.isCreativeMode;
            if (isCreative || this.getDamageTaken() > 40.0f) {
                if (super.riddenByEntity != null) {
                    super.riddenByEntity.mountEntity((Entity)this);
                }
                if (!isCreative) {
                    this.entityDropItem(this.getBarrelDrop(), 0.0f);
                }
                this.setDead();
            }
            return true;
        }
        return true;
    }
    
    private ItemStack getBarrelDrop() {
        final ItemStack itemstack = new ItemStack(LOTRMod.barrel);
        if (this.barrelItemData != null) {
            LOTRItemBarrel.setBarrelData(itemstack, this.barrelItemData);
        }
        return itemstack;
    }
    
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }
    
    public boolean canBeCollidedWith() {
        return !super.isDead;
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(final double d, final double d1, final double d2, final float f, final float f1, final int i) {
        if (this.field_70279_a) {
            this.boatPosRotationIncrements = i + 5;
        }
        else {
            final double d3 = d - super.posX;
            final double d4 = d1 - super.posY;
            final double d5 = d2 - super.posZ;
            final double d6 = d3 * d3 + d4 * d4 + d5 * d5;
            if (d6 <= 1.0) {
                return;
            }
            this.boatPosRotationIncrements = 3;
        }
        this.boatX = d;
        this.boatY = d1;
        this.boatZ = d2;
        this.boatYaw = f;
        this.boatPitch = f1;
        super.motionX = this.velocityX;
        super.motionY = this.velocityY;
        super.motionZ = this.velocityZ;
    }
    
    @SideOnly(Side.CLIENT)
    public void setVelocity(final double par1, final double par3, final double par5) {
        super.motionX = par1;
        this.velocityX = par1;
        super.motionY = par3;
        this.velocityY = par3;
        super.motionZ = par5;
        this.velocityZ = par5;
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!super.worldObj.isClient) {
            this.setBarrelItem(this.getBarrelDrop());
        }
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        super.prevPosX = super.posX;
        super.prevPosY = super.posY;
        super.prevPosZ = super.posZ;
        final byte b0 = 5;
        double d0 = 0.0;
        for (int i = 0; i < b0; ++i) {
            final double d2 = super.boundingBox.minY + (super.boundingBox.maxY - super.boundingBox.minY) * (i + 0) / b0 - 0.125;
            final double d3 = super.boundingBox.minY + (super.boundingBox.maxY - super.boundingBox.minY) * (i + 1) / b0 - 0.125;
            final AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(super.boundingBox.minX, d2, super.boundingBox.minZ, super.boundingBox.maxX, d3, super.boundingBox.maxZ);
            if (super.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
                d0 += 1.0 / b0;
            }
        }
        final double d4 = Math.sqrt(super.motionX * super.motionX + super.motionZ * super.motionZ);
        if (d4 > 0.2625) {
            final double d5 = Math.cos(super.rotationYaw * 3.141592653589793 / 180.0);
            final double d6 = Math.sin(super.rotationYaw * 3.141592653589793 / 180.0);
            for (int j = 0; j < 1.0 + d4 * 60.0; ++j) {
                final double d7 = super.rand.nextFloat() * 2.0f - 1.0f;
                final double d8 = (super.rand.nextInt(2) * 2 - 1) * 0.7;
                if (super.rand.nextBoolean()) {
                    final double d9 = super.posX - d5 * d7 * 0.8 + d6 * d8;
                    final double d10 = super.posZ - d6 * d7 * 0.8 - d5 * d8;
                    super.worldObj.spawnParticle("splash", d9, super.posY - 0.125, d10, super.motionX, super.motionY, super.motionZ);
                }
                else {
                    final double d9 = super.posX + d5 + d6 * d7 * 0.7;
                    final double d10 = super.posZ + d6 - d5 * d7 * 0.7;
                    super.worldObj.spawnParticle("splash", d9, super.posY - 0.125, d10, super.motionX, super.motionY, super.motionZ);
                }
            }
        }
        if (super.worldObj.isClient && this.field_70279_a) {
            if (this.boatPosRotationIncrements > 0) {
                final double d5 = super.posX + (this.boatX - super.posX) / this.boatPosRotationIncrements;
                final double d6 = super.posY + (this.boatY - super.posY) / this.boatPosRotationIncrements;
                final double d11 = super.posZ + (this.boatZ - super.posZ) / this.boatPosRotationIncrements;
                final double d12 = MathHelper.wrapAngleTo180_double(this.boatYaw - super.rotationYaw);
                super.rotationYaw += (float)(d12 / this.boatPosRotationIncrements);
                super.rotationPitch += (float)((this.boatPitch - super.rotationPitch) / this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(d5, d6, d11);
                this.setRotation(super.rotationYaw, super.rotationPitch);
            }
            else {
                final double d5 = super.posX + super.motionX;
                final double d6 = super.posY + super.motionY;
                final double d11 = super.posZ + super.motionZ;
                this.setPosition(d5, d6, d11);
                if (super.onGround) {
                    super.motionX *= 0.5;
                    super.motionY *= 0.5;
                    super.motionZ *= 0.5;
                }
                super.motionX *= 0.99;
                super.motionY *= 0.95;
                super.motionZ *= 0.99;
            }
        }
        else {
            if (d0 < 1.0) {
                final double d5 = d0 * 2.0 - 1.0;
                super.motionY += 0.04 * d5;
            }
            else {
                if (super.motionY < 0.0) {
                    super.motionY /= 2.0;
                }
                super.motionY += 0.007;
            }
            if (super.riddenByEntity != null && super.riddenByEntity instanceof EntityLivingBase) {
                final double d5 = ((EntityLivingBase)super.riddenByEntity).moveForward;
                if (d5 > 0.0) {
                    final double d6 = -Math.sin(super.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
                    final double d11 = Math.cos(super.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
                    super.motionX += d6 * this.speedMultiplier * 0.05;
                    super.motionZ += d11 * this.speedMultiplier * 0.05;
                }
            }
            double d5 = Math.sqrt(super.motionX * super.motionX + super.motionZ * super.motionZ);
            if (d5 > LOTREntityBarrel.maxSpeedMultiplier) {
                final double d6 = LOTREntityBarrel.maxSpeedMultiplier / d5;
                super.motionX *= d6;
                super.motionZ *= d6;
                d5 = LOTREntityBarrel.maxSpeedMultiplier;
            }
            if (d5 > d4 && this.speedMultiplier < LOTREntityBarrel.maxSpeedMultiplier) {
                this.speedMultiplier += (LOTREntityBarrel.maxSpeedMultiplier - this.speedMultiplier) / (LOTREntityBarrel.maxSpeedMultiplier / 150.0);
                if (this.speedMultiplier > LOTREntityBarrel.maxSpeedMultiplier) {
                    this.speedMultiplier = LOTREntityBarrel.maxSpeedMultiplier;
                }
            }
            else {
                this.speedMultiplier -= (this.speedMultiplier - LOTREntityBarrel.minSpeedMultiplier) / (LOTREntityBarrel.maxSpeedMultiplier / 150.0);
                if (this.speedMultiplier < LOTREntityBarrel.minSpeedMultiplier) {
                    this.speedMultiplier = LOTREntityBarrel.minSpeedMultiplier;
                }
            }
            if (super.onGround) {
                super.motionX *= 0.5;
                super.motionY *= 0.5;
                super.motionZ *= 0.5;
            }
            this.moveEntity(super.motionX, super.motionY, super.motionZ);
            super.motionX *= 0.99;
            super.motionY *= 0.95;
            super.motionZ *= 0.99;
            super.rotationPitch = 0.0f;
            double d6 = super.rotationYaw;
            final double d11 = super.prevPosX - super.posX;
            final double d12 = super.prevPosZ - super.posZ;
            if (d11 * d11 + d12 * d12 > 0.001) {
                d6 = (float)(Math.atan2(d12, d11) * 180.0 / 3.141592653589793);
            }
            double d13 = MathHelper.wrapAngleTo180_double(d6 - super.rotationYaw);
            if (d13 > 20.0) {
                d13 = 20.0;
            }
            if (d13 < -20.0) {
                d13 = -20.0;
            }
            this.setRotation(super.rotationYaw += (float)d13, super.rotationPitch);
            if (!super.worldObj.isClient) {
                final List nearbyEntities = super.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, super.boundingBox.expand(0.2, 0.0, 0.2));
                if (nearbyEntities != null && !nearbyEntities.isEmpty()) {
                    for (int l = 0; l < nearbyEntities.size(); ++l) {
                        final Entity entity = nearbyEntities.get(l);
                        if (entity != super.riddenByEntity && entity.canBePushed() && entity instanceof LOTREntityBarrel) {
                            entity.applyEntityCollision((Entity)this);
                        }
                    }
                }
                if (super.riddenByEntity != null && super.riddenByEntity.isDead) {
                    super.riddenByEntity = null;
                }
            }
        }
        if (!super.worldObj.isClient && super.riddenByEntity instanceof EntityPlayer && super.worldObj.isAABBInMaterial(super.boundingBox, Material.water)) {
            final int k = MathHelper.floor_double(super.posX);
            final int m = MathHelper.floor_double(super.posZ);
            if (super.worldObj.getBiomeGenForCoords(k, m) instanceof LOTRBiomeGenMirkwood) {
                LOTRLevelData.getData((EntityPlayer)super.riddenByEntity).addAchievement(LOTRAchievement.rideBarrelMirkwood);
            }
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        if (this.barrelItemData != null) {
            nbt.setTag("BarrelItemData", (NBTBase)this.barrelItemData);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        if (nbt.hasKey("BarrelItemData")) {
            this.barrelItemData = nbt.getCompoundTag("BarrelItemData");
        }
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }
    
    public boolean interactFirst(final EntityPlayer entityplayer) {
        if (super.riddenByEntity != null && super.riddenByEntity instanceof EntityPlayer && super.riddenByEntity != entityplayer) {
            return true;
        }
        if (!super.worldObj.isClient) {
            entityplayer.mountEntity((Entity)this);
        }
        return true;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return this.getBarrelItem();
    }
    
    static {
        LOTREntityBarrel.minSpeedMultiplier = 0.04;
        LOTREntityBarrel.maxSpeedMultiplier = 0.25;
    }
}
