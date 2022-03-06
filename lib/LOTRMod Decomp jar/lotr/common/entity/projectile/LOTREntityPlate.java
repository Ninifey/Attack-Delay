// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import lotr.common.LOTRBannerProtection;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityPlate extends EntityThrowable implements IEntityAdditionalSpawnData
{
    private int plateSpin;
    private Block plateBlock;
    
    public LOTREntityPlate(final World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
    }
    
    public LOTREntityPlate(final World world, final Block block, final EntityLivingBase entityliving) {
        super(world, entityliving);
        this.setSize(0.5f, 0.5f);
        this.plateBlock = block;
    }
    
    public LOTREntityPlate(final World world, final Block block, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
        this.setSize(0.5f, 0.5f);
        this.plateBlock = block;
    }
    
    public void writeSpawnData(final ByteBuf data) {
        data.writeShort(Block.getIdFromBlock(this.plateBlock));
    }
    
    public void readSpawnData(final ByteBuf data) {
        Block block = Block.getBlockById((int)data.readShort());
        if (block == null) {
            block = LOTRMod.plateBlock;
        }
        this.plateBlock = block;
    }
    
    public Block getPlateBlock() {
        return this.plateBlock;
    }
    
    public void onUpdate() {
        super.onUpdate();
        ++this.plateSpin;
        ((Entity)this).rotationYaw = this.plateSpin % 12 / 12.0f * 360.0f;
        final float speed = MathHelper.sqrt_double(((Entity)this).motionX * ((Entity)this).motionX + ((Entity)this).motionZ * ((Entity)this).motionZ);
        if (speed > 0.1f && ((Entity)this).motionY < 0.0 && this.isInWater()) {
            final float factor = MathHelper.randomFloatClamp(((Entity)this).rand, 0.4f, 0.8f);
            ((Entity)this).motionX *= factor;
            ((Entity)this).motionZ *= factor;
            ((Entity)this).motionY += factor;
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if (this.plateBlock != null) {
            nbt.setShort("PlateBlockID", (short)Block.getIdFromBlock(this.plateBlock));
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("PlateBlockID")) {
            this.plateBlock = Block.getBlockById((int)nbt.getShort("PlateBlockID"));
        }
        if (this.plateBlock == null) {
            this.plateBlock = LOTRMod.plateBlock;
        }
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            if (m.entityHit == this.getThrower()) {
                return;
            }
            m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), 1.0f);
        }
        else if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !((Entity)this).worldObj.isClient) {
            final int i = m.blockX;
            final int j = m.blockY;
            final int k = m.blockZ;
            if (this.breakGlass(i, j, k)) {
                for (int range = 2, i2 = i - range; i2 <= i + range; ++i2) {
                    for (int j2 = j - range; j2 <= j + range; ++j2) {
                        for (int k2 = k - range; k2 <= k + range; ++k2) {
                            if (((Entity)this).rand.nextInt(4) != 0) {
                                this.breakGlass(i2, j2, k2);
                            }
                        }
                    }
                }
                return;
            }
        }
        for (int i = 0; i < 8; ++i) {
            final double d = ((Entity)this).posX + MathHelper.randomFloatClamp(((Entity)this).rand, -0.25f, 0.25f);
            final double d2 = ((Entity)this).posY + MathHelper.randomFloatClamp(((Entity)this).rand, -0.25f, 0.25f);
            final double d3 = ((Entity)this).posZ + MathHelper.randomFloatClamp(((Entity)this).rand, -0.25f, 0.25f);
            ((Entity)this).worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(this.plateBlock) + "_0", d, d2, d3, 0.0, 0.0, 0.0);
        }
        if (!((Entity)this).worldObj.isClient) {
            ((Entity)this).worldObj.playSoundAtEntity((Entity)this, this.plateBlock.stepSound.getDigResourcePath(), 1.0f, (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.2f + 1.0f);
            this.setDead();
        }
    }
    
    private boolean breakGlass(final int i, final int j, final int k) {
        final Block block = ((Entity)this).worldObj.getBlock(i, j, k);
        if (block.getMaterial() == Material.glass) {
            final boolean bannerProtection = LOTRBannerProtection.isProtectedByBanner(((Entity)this).worldObj, i, j, k, LOTRBannerProtection.forThrown(this), true);
            if (!bannerProtection) {
                ((Entity)this).worldObj.playAuxSFX(2001, i, j, k, Block.getIdFromBlock(block) + (((Entity)this).worldObj.getBlockMetadata(i, j, k) << 12));
                ((Entity)this).worldObj.setBlockToAir(i, j, k);
                return true;
            }
        }
        return false;
    }
    
    protected float func_70182_d() {
        return 1.5f;
    }
    
    protected float getGravityVelocity() {
        return 0.02f;
    }
}
