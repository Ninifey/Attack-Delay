// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import lotr.common.item.LOTRItemBossTrophy;
import net.minecraft.world.World;
import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.entity.Entity;

public class LOTREntityBossTrophy extends Entity implements LOTRBannerProtectable
{
    public LOTREntityBossTrophy(final World world) {
        super(world);
        this.setSize(1.0f, 1.0f);
    }
    
    protected void entityInit() {
        super.dataWatcher.addObject(18, (Object)0);
        super.dataWatcher.addObject(19, (Object)0);
        super.dataWatcher.addObject(20, (Object)0);
    }
    
    private int getTrophyTypeID() {
        return super.dataWatcher.getWatchableObjectByte(18);
    }
    
    private void setTrophyTypeID(final int i) {
        super.dataWatcher.updateObject(18, (Object)(byte)i);
    }
    
    public void setTrophyType(final LOTRItemBossTrophy.TrophyType type) {
        this.setTrophyTypeID(type.trophyID);
    }
    
    public LOTRItemBossTrophy.TrophyType getTrophyType() {
        return LOTRItemBossTrophy.TrophyType.forID(this.getTrophyTypeID());
    }
    
    public boolean isTrophyHanging() {
        return super.dataWatcher.getWatchableObjectByte(19) == 1;
    }
    
    public void setTrophyHanging(final boolean flag) {
        super.dataWatcher.updateObject(19, (Object)(byte)(flag ? 1 : 0));
    }
    
    public int getTrophyFacing() {
        int i = super.dataWatcher.getWatchableObjectByte(20);
        if (i < 0 || i >= Direction.directions.length) {
            i = 0;
        }
        return i;
    }
    
    public void setTrophyFacing(final int i) {
        super.dataWatcher.updateObject(20, (Object)(byte)i);
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
        if (this.isTrophyHanging()) {
            if (!this.hangingOnValidSurface() && !super.worldObj.isClient && !super.isDead) {
                this.dropAsItem(true);
            }
        }
        else {
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
        }
    }
    
    public boolean hangingOnValidSurface() {
        if (this.isTrophyHanging()) {
            final int direction = this.getTrophyFacing();
            final int opposite = Direction.rotateOpposite[direction];
            final int dx = Direction.offsetX[opposite];
            final int dz = Direction.offsetZ[opposite];
            int blockX = MathHelper.floor_double(super.posX);
            final int blockY = MathHelper.floor_double(super.boundingBox.minY);
            int blockZ = MathHelper.floor_double(super.posZ);
            blockX += dx;
            blockZ += dz;
            final Block block = super.worldObj.getBlock(blockX, blockY, blockZ);
            final int blockSide = Direction.directionToFacing[direction];
            return block.isSideSolid((IBlockAccess)super.worldObj, blockX, blockY, blockZ, ForgeDirection.getOrientation(blockSide));
        }
        return false;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setByte("TrophyType", (byte)this.getTrophyTypeID());
        nbt.setBoolean("TrophyHanging", this.isTrophyHanging());
        nbt.setByte("TrophyFacing", (byte)this.getTrophyFacing());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        this.setTrophyTypeID(nbt.getByte("TrophyType"));
        this.setTrophyHanging(nbt.getBoolean("TrophyHanging"));
        this.setTrophyFacing(nbt.getByte("TrophyFacing"));
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        if (!super.worldObj.isClient && !super.isDead && damagesource.getSourceOfDamage() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)damagesource.getSourceOfDamage();
            this.dropAsItem(!entityplayer.capabilities.isCreativeMode);
            return true;
        }
        return false;
    }
    
    private void dropAsItem(final boolean dropItem) {
        super.worldObj.playSoundAtEntity((Entity)this, Blocks.stone.stepSound.getDigResourcePath(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getFrequency() * 0.8f);
        if (dropItem) {
            this.entityDropItem(new ItemStack(LOTRMod.bossTrophy, 1, this.getTrophyType().trophyID), 0.0f);
        }
        this.setDead();
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.bossTrophy, 1, this.getTrophyType().trophyID);
    }
}
