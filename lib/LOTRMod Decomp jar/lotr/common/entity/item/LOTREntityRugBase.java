// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.entity.Entity;

public abstract class LOTREntityRugBase extends Entity implements LOTRBannerProtectable
{
    private int timeSinceLastGrowl;
    
    public LOTREntityRugBase(final World world) {
        super(world);
        this.timeSinceLastGrowl = this.getTimeUntilGrowl();
    }
    
    protected void entityInit() {
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return super.boundingBox;
    }
    
    public void onUpdate() {
        super.onUpdate();
        super.prevPosX = super.posX;
        super.prevPosY = super.posY;
        super.prevPosZ = super.posZ;
        super.motionY -= 0.04;
        this.func_145771_j(super.posX, (super.boundingBox.minY + super.boundingBox.maxY) / 2.0, super.posZ);
        this.moveEntity(super.motionX, super.motionY, super.motionZ);
        float f = 0.98f;
        if (super.onGround) {
            f = 0.588f;
            final Block i = super.worldObj.getBlock(MathHelper.floor_double(super.posX), MathHelper.floor_double(super.boundingBox.minY) - 1, MathHelper.floor_double(super.posZ));
            if (i.getMaterial() != Material.air) {
                f = i.slipperiness * 0.98f;
            }
        }
        super.motionX *= f;
        super.motionY *= 0.98;
        super.motionZ *= f;
        if (super.onGround) {
            super.motionY *= -0.5;
        }
        if (!super.worldObj.isClient) {
            if (this.timeSinceLastGrowl > 0) {
                --this.timeSinceLastGrowl;
            }
            else if (super.rand.nextInt(5000) == 0) {
                super.worldObj.playSoundAtEntity((Entity)this, this.getRugNoise(), 1.0f, (super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 0.2f + 1.0f);
                this.timeSinceLastGrowl = this.getTimeUntilGrowl();
            }
        }
    }
    
    private int getTimeUntilGrowl() {
        return (60 + super.rand.nextInt(150)) * 20;
    }
    
    protected abstract String getRugNoise();
    
    protected void readEntityFromNBT(final NBTTagCompound nbt) {
    }
    
    protected void writeEntityToNBT(final NBTTagCompound nbt) {
    }
    
    protected abstract ItemStack getRugItem();
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        if (!super.worldObj.isClient && !super.isDead) {
            final Block.SoundType blockSound = Blocks.wool.stepSound;
            super.worldObj.playSoundAtEntity((Entity)this, blockSound.getDigResourcePath(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getFrequency() * 0.8f);
            final Entity attacker = damagesource.getEntity();
            final boolean creative = attacker instanceof EntityPlayer && ((EntityPlayer)attacker).capabilities.isCreativeMode;
            if (!creative) {
                this.entityDropItem(this.getRugItem(), 0.0f);
            }
            this.setDead();
        }
        return true;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return this.getRugItem();
    }
}
