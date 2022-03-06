// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.entity.EntityTracker;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.material.Material;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.entity.IProjectile;
import cpw.mods.fml.common.registry.IThrowableEntity;
import net.minecraft.entity.Entity;

public abstract class LOTREntityProjectileBase extends Entity implements IThrowableEntity, IProjectile
{
    private int xTile;
    private int yTile;
    private int zTile;
    private Block inTile;
    private int inData;
    public boolean inGround;
    public int shake;
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    public int canBePickedUp;
    public int knockbackStrength;
    
    public LOTREntityProjectileBase(final World world) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inData = 0;
        this.inGround = false;
        this.shake = 0;
        this.ticksInAir = 0;
        this.canBePickedUp = 0;
        this.knockbackStrength = 0;
        this.setSize(0.5f, 0.5f);
    }
    
    public LOTREntityProjectileBase(final World world, final ItemStack item, final double d, final double d1, final double d2) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inData = 0;
        this.inGround = false;
        this.shake = 0;
        this.ticksInAir = 0;
        this.canBePickedUp = 0;
        this.knockbackStrength = 0;
        this.setProjectileItem(item);
        this.setSize(0.5f, 0.5f);
        this.setPosition(d, d1, d2);
        super.yOffset = 0.0f;
    }
    
    public LOTREntityProjectileBase(final World world, final EntityLivingBase entityliving, final ItemStack item, final float charge) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inData = 0;
        this.inGround = false;
        this.shake = 0;
        this.ticksInAir = 0;
        this.canBePickedUp = 0;
        this.knockbackStrength = 0;
        this.setProjectileItem(item);
        this.shootingEntity = (Entity)entityliving;
        if (entityliving instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(((Entity)entityliving).posX, ((Entity)entityliving).posY + entityliving.getEyeHeight(), ((Entity)entityliving).posZ, ((Entity)entityliving).rotationYaw, ((Entity)entityliving).rotationPitch);
        super.posX -= MathHelper.cos(super.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        super.posY -= 0.1;
        super.posZ -= MathHelper.sin(super.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(super.posX, super.posY, super.posZ);
        super.yOffset = 0.0f;
        super.motionX = -MathHelper.sin(super.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(super.rotationPitch / 180.0f * 3.1415927f);
        super.motionZ = MathHelper.cos(super.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(super.rotationPitch / 180.0f * 3.1415927f);
        super.motionY = -MathHelper.sin(super.rotationPitch / 180.0f * 3.1415927f);
        this.setThrowableHeading(super.motionX, super.motionY, super.motionZ, charge * 1.5f, 1.0f);
    }
    
    public LOTREntityProjectileBase(final World world, final EntityLivingBase entityliving, final EntityLivingBase target, final ItemStack item, final float charge, final float inaccuracy) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inData = 0;
        this.inGround = false;
        this.shake = 0;
        this.ticksInAir = 0;
        this.canBePickedUp = 0;
        this.knockbackStrength = 0;
        this.setProjectileItem(item);
        this.shootingEntity = (Entity)entityliving;
        if (entityliving instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.setSize(0.5f, 0.5f);
        super.posY = ((Entity)entityliving).posY + entityliving.getEyeHeight() - 0.1;
        final double d = ((Entity)target).posX - ((Entity)entityliving).posX;
        final double d2 = ((Entity)target).posY + target.getEyeHeight() - 0.7 - super.posY;
        final double d3 = ((Entity)target).posZ - ((Entity)entityliving).posZ;
        final double d4 = MathHelper.sqrt_double(d * d + d3 * d3);
        if (d4 >= 1.0E-7) {
            final float f = (float)(Math.atan2(d3, d) * 180.0 / 3.141592653589793) - 90.0f;
            final float f2 = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
            final double d5 = d / d4;
            final double d6 = d3 / d4;
            this.setLocationAndAngles(((Entity)entityliving).posX + d5, super.posY, ((Entity)entityliving).posZ + d6, f, f2);
            super.yOffset = 0.0f;
            final float d7 = (float)d4 * 0.2f;
            this.setThrowableHeading(d, d2 + d7, d3, charge * 1.5f, inaccuracy);
        }
    }
    
    public Entity getThrower() {
        return this.shootingEntity;
    }
    
    public void setThrower(final Entity entity) {
        this.shootingEntity = entity;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(final double d) {
        double d2 = super.boundingBox.getAverageEdgeLength() * 4.0;
        d2 *= 64.0;
        return d < d2 * d2;
    }
    
    protected void entityInit() {
        super.dataWatcher.addObject(17, (Object)0);
        super.dataWatcher.addObjectByDataType(18, 5);
    }
    
    public ItemStack getProjectileItem() {
        return super.dataWatcher.getWatchableObjectItemStack(18);
    }
    
    public void setProjectileItem(final ItemStack item) {
        super.dataWatcher.updateObject(18, (Object)item);
    }
    
    public void setThrowableHeading(double d, double d1, double d2, final float f, final float f1) {
        final float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += super.rand.nextGaussian() * 0.0075 * f1;
        d1 += super.rand.nextGaussian() * 0.0075 * f1;
        d2 += super.rand.nextGaussian() * 0.0075 * f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        super.motionX = d;
        super.motionY = d1;
        super.motionZ = d2;
        final float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        final float n = (float)(Math.atan2(d, d2) * 180.0 / 3.141592653589793);
        super.rotationYaw = n;
        super.prevRotationYaw = n;
        final float n2 = (float)(Math.atan2(d1, f3) * 180.0 / 3.141592653589793);
        super.rotationPitch = n2;
        super.prevRotationPitch = n2;
        this.ticksInGround = 0;
    }
    
    public void setVelocity(final double d, final double d1, final double d2) {
        super.motionX = d;
        super.motionY = d1;
        super.motionZ = d2;
        if (super.prevRotationPitch == 0.0f && super.prevRotationYaw == 0.0f) {
            final float f = MathHelper.sqrt_double(d * d + d2 * d2);
            final float n = (float)(Math.atan2(d, d2) * 180.0 / 3.141592653589793);
            super.rotationYaw = n;
            super.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(d1, f) * 180.0 / 3.141592653589793);
            super.rotationPitch = n2;
            super.prevRotationPitch = n2;
            super.prevRotationPitch = super.rotationPitch;
            super.prevRotationYaw = super.rotationYaw;
            this.setLocationAndAngles(super.posX, super.posY, super.posZ, super.rotationYaw, super.rotationPitch);
            this.ticksInGround = 0;
        }
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (super.prevRotationPitch == 0.0f && super.prevRotationYaw == 0.0f) {
            final float f = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
            final float n = (float)(Math.atan2(super.motionX, super.motionZ) * 180.0 / 3.141592653589793);
            super.rotationYaw = n;
            super.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(super.motionY, f) * 180.0 / 3.141592653589793);
            super.rotationPitch = n2;
            super.prevRotationPitch = n2;
        }
        final Block block = super.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
        if (block != Blocks.air) {
            block.setBlockBoundsBasedOnState((IBlockAccess)super.worldObj, this.xTile, this.yTile, this.zTile);
            final AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(super.worldObj, this.xTile, this.yTile, this.zTile);
            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(super.posX, super.posY, super.posZ))) {
                this.inGround = true;
            }
        }
        if (this.shake > 0) {
            --this.shake;
        }
        if (this.inGround) {
            final Block j = super.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
            final int k = super.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (j == this.inTile && k == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround >= this.maxTicksInGround()) {
                    this.setDead();
                }
            }
            else {
                this.inGround = false;
                super.motionX *= super.rand.nextFloat() * 0.2f;
                super.motionY *= super.rand.nextFloat() * 0.2f;
                super.motionZ *= super.rand.nextFloat() * 0.2f;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else {
            ++this.ticksInAir;
            Vec3 vec3d = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
            Vec3 vec3d2 = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
            MovingObjectPosition movingobjectposition = super.worldObj.func_147447_a(vec3d, vec3d2, false, true, false);
            vec3d = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
            vec3d2 = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
            if (movingobjectposition != null) {
                vec3d2 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }
            Entity entity = null;
            final List list = super.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, super.boundingBox.addCoord(super.motionX, super.motionY, super.motionZ).expand(1.0, 1.0, 1.0));
            double d = 0.0;
            for (int l = 0; l < list.size(); ++l) {
                final Entity entity2 = list.get(l);
                if (entity2.canBeCollidedWith()) {
                    if (entity2 != this.shootingEntity || this.ticksInAir >= 5) {
                        final float f2 = 0.3f;
                        final AxisAlignedBB axisalignedbb2 = entity2.boundingBox.expand((double)f2, (double)f2, (double)f2);
                        final MovingObjectPosition movingobjectposition2 = axisalignedbb2.calculateIntercept(vec3d, vec3d2);
                        if (movingobjectposition2 != null) {
                            final double d2 = vec3d.distanceTo(movingobjectposition2.hitVec);
                            if (d2 < d || d == 0.0) {
                                entity = entity2;
                                d = d2;
                            }
                        }
                    }
                }
            }
            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }
            if (movingobjectposition != null && movingobjectposition.entityHit instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
                if (entityplayer.capabilities.disableDamage || (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))) {
                    movingobjectposition = null;
                }
            }
            if (movingobjectposition != null) {
                final Entity hitEntity = movingobjectposition.entityHit;
                if (hitEntity != null) {
                    final ItemStack itemstack = this.getProjectileItem();
                    int damageInt = MathHelper.ceiling_double_int((double)this.getBaseImpactDamage(hitEntity, itemstack));
                    final int fireAspect = 0;
                    if (itemstack != null) {
                        if (this.shootingEntity instanceof EntityLivingBase && hitEntity instanceof EntityLivingBase) {
                            this.knockbackStrength += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)this.shootingEntity, (EntityLivingBase)hitEntity);
                        }
                        else {
                            this.knockbackStrength += LOTRWeaponStats.getTotalKnockback(itemstack);
                        }
                    }
                    if (this.getIsCritical()) {
                        damageInt += super.rand.nextInt(damageInt / 2 + 2);
                    }
                    final double[] prevMotion = { hitEntity.motionX, hitEntity.motionY, hitEntity.motionZ };
                    final DamageSource damagesource = this.getDamageSource();
                    if (hitEntity.attackEntityFrom(damagesource, (float)damageInt)) {
                        final double[] newMotion = { hitEntity.motionX, hitEntity.motionY, hitEntity.motionZ };
                        final float kbf = this.getKnockbackFactor();
                        hitEntity.motionX = prevMotion[0] + (newMotion[0] - prevMotion[0]) * kbf;
                        hitEntity.motionY = prevMotion[1] + (newMotion[1] - prevMotion[1]) * kbf;
                        hitEntity.motionZ = prevMotion[2] + (newMotion[2] - prevMotion[2]) * kbf;
                        if (this.isBurning()) {
                            hitEntity.setFire(5);
                        }
                        if (hitEntity instanceof EntityLivingBase) {
                            final EntityLivingBase hitEntityLiving = (EntityLivingBase)hitEntity;
                            if (this.knockbackStrength > 0) {
                                final float knockback = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
                                if (knockback > 0.0f) {
                                    hitEntityLiving.addVelocity(super.motionX * this.knockbackStrength * 0.6 / knockback, 0.1, super.motionZ * this.knockbackStrength * 0.6 / knockback);
                                }
                            }
                            if (fireAspect > 0) {
                                hitEntityLiving.setFire(fireAspect * 4);
                            }
                            if (this.shootingEntity instanceof EntityLivingBase) {
                                EnchantmentHelper.func_151384_a(hitEntityLiving, this.shootingEntity);
                                EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, (Entity)hitEntityLiving);
                            }
                            if (this.shootingEntity instanceof EntityPlayerMP && hitEntityLiving instanceof EntityPlayer) {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(6, 0.0f));
                            }
                        }
                        super.worldObj.playSoundAtEntity((Entity)this, this.getImpactSound(), 1.0f, 1.2f / (super.rand.nextFloat() * 0.2f + 0.9f));
                        this.onCollideWithTarget(hitEntity);
                    }
                    else {
                        super.motionX *= -0.1;
                        super.motionY *= -0.1;
                        super.motionZ *= -0.1;
                        super.rotationYaw += 180.0f;
                        super.prevRotationYaw += 180.0f;
                        this.ticksInAir = 0;
                    }
                }
                else {
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    this.inTile = super.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
                    this.inData = super.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    super.motionX = (float)(movingobjectposition.hitVec.xCoord - super.posX);
                    super.motionY = (float)(movingobjectposition.hitVec.yCoord - super.posY);
                    super.motionZ = (float)(movingobjectposition.hitVec.zCoord - super.posZ);
                    final float f3 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
                    super.posX -= super.motionX / f3 * 0.05;
                    super.posY -= super.motionY / f3 * 0.05;
                    super.posZ -= super.motionZ / f3 * 0.05;
                    super.worldObj.playSoundAtEntity((Entity)this, this.getImpactSound(), 1.0f, 1.2f / (super.rand.nextFloat() * 0.2f + 0.9f));
                    this.inGround = true;
                    this.shake = 7;
                    this.setIsCritical(false);
                    if (this.inTile.getMaterial() != Material.air) {
                        this.inTile.onEntityCollidedWithBlock(super.worldObj, this.xTile, this.yTile, this.zTile, (Entity)this);
                    }
                }
            }
            if (this.getIsCritical()) {
                for (int l = 0; l < 4; ++l) {
                    super.worldObj.spawnParticle("crit", super.posX + super.motionX * l / 4.0, super.posY + super.motionY * l / 4.0, super.posZ + super.motionZ * l / 4.0, -super.motionX, -super.motionY + 0.2, -super.motionZ);
                }
            }
            super.posX += super.motionX;
            super.posY += super.motionY;
            super.posZ += super.motionZ;
            final float f4 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
            super.rotationYaw = (float)(Math.atan2(super.motionX, super.motionZ) * 180.0 / 3.141592653589793);
            super.rotationPitch = (float)(Math.atan2(super.motionY, f4) * 180.0 / 3.141592653589793);
            while (super.rotationPitch - super.prevRotationPitch < -180.0f) {
                super.prevRotationPitch -= 360.0f;
            }
            while (super.rotationPitch - super.prevRotationPitch >= 180.0f) {
                super.prevRotationPitch += 360.0f;
            }
            while (super.rotationYaw - super.prevRotationYaw < -180.0f) {
                super.prevRotationYaw -= 360.0f;
            }
            while (super.rotationYaw - super.prevRotationYaw >= 180.0f) {
                super.prevRotationYaw += 360.0f;
            }
            super.rotationPitch = super.prevRotationPitch + (super.rotationPitch - super.prevRotationPitch) * 0.2f;
            super.rotationYaw = super.prevRotationYaw + (super.rotationYaw - super.prevRotationYaw) * 0.2f;
            float f5 = this.getSpeedReduction();
            if (this.isInWater()) {
                for (int k2 = 0; k2 < 4; ++k2) {
                    final float f6 = 0.25f;
                    super.worldObj.spawnParticle("bubble", super.posX - super.motionX * f6, super.posY - super.motionY * f6, super.posZ - super.motionZ * f6, super.motionX, super.motionY, super.motionZ);
                }
                f5 = 0.8f;
            }
            super.motionX *= f5;
            super.motionY *= f5;
            super.motionZ *= f5;
            super.motionY -= 0.05000000074505806;
            this.setPosition(super.posX, super.posY, super.posZ);
            this.func_145775_I();
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setInteger("xTile", this.xTile);
        nbt.setInteger("yTile", this.yTile);
        nbt.setInteger("zTile", this.zTile);
        nbt.setInteger("inTile", Block.getIdFromBlock(this.inTile));
        nbt.setByte("inData", (byte)this.inData);
        nbt.setByte("shake", (byte)this.shake);
        nbt.setByte("inGround", (byte)(byte)(this.inGround ? 1 : 0));
        nbt.setByte("pickup", (byte)this.canBePickedUp);
        nbt.setByte("Knockback", (byte)this.knockbackStrength);
        if (this.getProjectileItem() != null) {
            nbt.setTag("ProjectileItem", (NBTBase)this.getProjectileItem().writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        this.xTile = nbt.getInteger("xTile");
        this.yTile = nbt.getInteger("yTile");
        this.zTile = nbt.getInteger("zTile");
        this.inTile = Block.getBlockById(nbt.getInteger("inTile"));
        this.inData = nbt.getByte("inData");
        this.shake = nbt.getByte("shake");
        this.inGround = (nbt.getByte("inGround") == 1);
        this.canBePickedUp = nbt.getByte("pickup");
        this.knockbackStrength = nbt.getByte("Knockback");
        if (nbt.hasKey("itemID")) {
            final ItemStack item = new ItemStack(Item.getItemById(nbt.getInteger("itemID")), 1, nbt.getInteger("itemDamage"));
            if (nbt.hasKey("ItemTagCompound")) {
                item.setTagCompound(nbt.getCompoundTag("ItemTagCompound"));
            }
            this.setProjectileItem(item);
        }
        else {
            this.setProjectileItem(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("ProjectileItem")));
        }
    }
    
    protected ItemStack createPickupDrop(final EntityPlayer entityplayer) {
        final ItemStack itemstack = this.getProjectileItem();
        if (itemstack != null) {
            final ItemStack itemPickup = itemstack.copy();
            if (itemPickup.isItemStackDamageable()) {
                itemPickup.damageItem(1, (EntityLivingBase)entityplayer);
                if (itemPickup.getItemDamage() >= itemPickup.getMaxDamage()) {
                    return null;
                }
            }
            return itemPickup;
        }
        return null;
    }
    
    public void onCollideWithPlayer(final EntityPlayer entityplayer) {
        if (!super.worldObj.isClient && this.inGround && this.shake <= 0) {
            final ItemStack itemstack = this.createPickupDrop(entityplayer);
            if (itemstack != null) {
                boolean pickup = this.canBePickedUp == 1 || (this.canBePickedUp == 2 && entityplayer.capabilities.isCreativeMode);
                if (this.canBePickedUp == 1 && !entityplayer.inventory.addItemStackToInventory(itemstack.copy())) {
                    pickup = false;
                    final EntityItem entityitem = new EntityItem(super.worldObj, super.posX, super.posY, super.posZ, itemstack);
                    entityitem.delayBeforeCanPickup = 0;
                    super.worldObj.spawnEntityInWorld((Entity)entityitem);
                    this.setDead();
                }
                if (pickup) {
                    if (!super.isDead) {
                        final EntityTracker entitytracker = ((WorldServer)super.worldObj).getEntityTracker();
                        entitytracker.func_151247_a((Entity)this, (Packet)new S0DPacketCollectItem(this.getEntityId(), entityplayer.getEntityId()));
                    }
                    this.playSound("random.pop", 0.2f, ((super.rand.nextFloat() - super.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    this.setDead();
                }
            }
            else {
                this.setDead();
            }
        }
    }
    
    protected void onCollideWithTarget(final Entity entity) {
        if (!super.worldObj.isClient) {
            final ItemStack itemstack = this.getProjectileItem();
            if (itemstack == null || !itemstack.isItemStackDamageable()) {
                this.setDead();
            }
        }
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public float getShadowSize() {
        return 0.0f;
    }
    
    public boolean canAttackWithItem() {
        return false;
    }
    
    public abstract float getBaseImpactDamage(final Entity p0, final ItemStack p1);
    
    protected float getKnockbackFactor() {
        return 1.0f;
    }
    
    public DamageSource getDamageSource() {
        if (this.shootingEntity == null) {
            return DamageSource.causeThrownDamage((Entity)this, (Entity)this);
        }
        return DamageSource.causeThrownDamage((Entity)this, this.shootingEntity);
    }
    
    public void setIsCritical(final boolean flag) {
        super.dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
    }
    
    public boolean getIsCritical() {
        return super.dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public String getImpactSound() {
        return "random.bowhit";
    }
    
    public float getSpeedReduction() {
        return 0.99f;
    }
    
    public int maxTicksInGround() {
        return (this.canBePickedUp == 1) ? 6000 : 1200;
    }
}
