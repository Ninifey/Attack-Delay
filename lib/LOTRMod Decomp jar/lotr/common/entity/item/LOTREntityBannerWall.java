// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.init.Blocks;
import lotr.common.LOTRBannerProtection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.nbt.NBTBase;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Direction;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityHanging;

public class LOTREntityBannerWall extends EntityHanging
{
    private NBTTagCompound protectData;
    private boolean updatedClientBB;
    
    public LOTREntityBannerWall(final World world) {
        super(world);
        this.updatedClientBB = false;
        this.setSize(0.0f, 0.0f);
    }
    
    public LOTREntityBannerWall(final World world, final int i, final int j, final int k, final int dir) {
        super(world, i, j, k, dir);
        this.updatedClientBB = false;
        this.setSize(0.0f, 0.0f);
        this.setDirection(dir);
    }
    
    protected void entityInit() {
        ((Entity)this).dataWatcher.addObject(10, (Object)0);
        ((Entity)this).dataWatcher.addObject(11, (Object)0);
        ((Entity)this).dataWatcher.addObject(12, (Object)0);
        ((Entity)this).dataWatcher.addObject(13, (Object)0);
        ((Entity)this).dataWatcher.addObject(18, (Object)0);
    }
    
    private void updateWatchedDirection() {
        ((Entity)this).dataWatcher.updateObject(10, (Object)super.field_146063_b);
        ((Entity)this).dataWatcher.updateObject(11, (Object)super.field_146064_c);
        ((Entity)this).dataWatcher.updateObject(12, (Object)super.field_146062_d);
        ((Entity)this).dataWatcher.updateObject(13, (Object)(byte)super.hangingDirection);
    }
    
    public void getWatchedDirection() {
        super.field_146063_b = ((Entity)this).dataWatcher.getWatchableObjectInt(10);
        super.field_146064_c = ((Entity)this).dataWatcher.getWatchableObjectInt(11);
        super.field_146062_d = ((Entity)this).dataWatcher.getWatchableObjectInt(12);
        super.hangingDirection = ((Entity)this).dataWatcher.getWatchableObjectByte(13);
    }
    
    private int getBannerTypeID() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(18);
    }
    
    private void setBannerTypeID(final int i) {
        ((Entity)this).dataWatcher.updateObject(18, (Object)(byte)i);
    }
    
    public void setBannerType(final LOTRItemBanner.BannerType type) {
        this.setBannerTypeID(type.bannerID);
    }
    
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.forID(this.getBannerTypeID());
    }
    
    public void setProtectData(final NBTTagCompound nbt) {
        this.protectData = nbt;
    }
    
    public void setDirection(int dir) {
        if (dir < 0 || dir >= Direction.directions.length) {
            dir = 0;
        }
        super.hangingDirection = dir;
        final float n = Direction.rotateOpposite[dir] * 90.0f;
        ((Entity)this).rotationYaw = n;
        ((Entity)this).prevRotationYaw = n;
        final float width = 1.0f;
        final float thickness = 0.0625f;
        final float yEdge;
        final float edge = yEdge = 0.01f;
        float xSize;
        float zSize;
        float xEdge;
        float zEdge;
        if (dir == 0 || dir == 2) {
            xSize = width;
            zSize = thickness;
            xEdge = thickness + edge;
            zEdge = edge;
        }
        else {
            xSize = thickness;
            zSize = width;
            xEdge = edge;
            zEdge = thickness + edge;
        }
        float f = super.field_146063_b + 0.5f;
        final float f2 = super.field_146064_c + 0.5f;
        float f3 = super.field_146062_d + 0.5f;
        final float f4 = 0.5f + thickness / 2.0f;
        f += Direction.offsetX[dir] * f4;
        f3 += Direction.offsetZ[dir] * f4;
        this.setPosition((double)f, (double)f2, (double)f3);
        ((Entity)this).prevPosX = ((Entity)this).posX;
        ((Entity)this).prevPosY = ((Entity)this).posY;
        ((Entity)this).prevPosZ = ((Entity)this).posZ;
        ((Entity)this).boundingBox.setBounds((double)(f - xSize / 2.0f), (double)(f2 - 1.5f), (double)(f3 - zSize / 2.0f), (double)(f + xSize / 2.0f), (double)(f2 + 0.5f), (double)(f3 + zSize / 2.0f));
        ((Entity)this).boundingBox.setBB(((Entity)this).boundingBox.contract((double)xEdge, (double)yEdge, (double)zEdge));
        if (!((Entity)this).worldObj.isClient) {
            this.updateWatchedDirection();
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float f) {
        if (!this.updatedClientBB) {
            this.getWatchedDirection();
            this.setDirection(super.hangingDirection);
            this.updatedClientBB = true;
        }
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        if (((Entity)this).worldObj.blockExists(i, 0, k)) {
            final int j = MathHelper.floor_double(((Entity)this).posY);
            return ((Entity)this).worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
        }
        return 0;
    }
    
    public void onUpdate() {
        if (((Entity)this).worldObj.isClient && !this.updatedClientBB) {
            this.getWatchedDirection();
            this.setDirection(super.hangingDirection);
            this.updatedClientBB = true;
        }
        super.onUpdate();
    }
    
    public boolean onValidSurface() {
        if (!((Entity)this).worldObj.getCollidingBoundingBoxes((Entity)this, ((Entity)this).boundingBox).isEmpty()) {
            return false;
        }
        final int i = super.field_146063_b;
        final int j = super.field_146064_c;
        final int k = super.field_146062_d;
        final Block block = ((Entity)this).worldObj.getBlock(i, j, k);
        if (!block.getMaterial().isSolid()) {
            return false;
        }
        final List list = ((Entity)this).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, ((Entity)this).boundingBox);
        for (final Object obj : list) {
            if (obj instanceof EntityHanging) {
                return false;
            }
        }
        return true;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("BannerType", (byte)this.getBannerTypeID());
        if (this.protectData != null) {
            nbt.setTag("ProtectData", (NBTBase)this.protectData);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setBannerTypeID(nbt.getByte("BannerType"));
        if (nbt.hasKey("ProtectData")) {
            this.protectData = nbt.getCompoundTag("ProtectData");
        }
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        if (!((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
            if (LOTRBannerProtection.isProtectedByBanner(((Entity)this).worldObj, (Entity)this, LOTRBannerProtection.forPlayer(entityplayer), true)) {
                return false;
            }
        }
        return super.attackEntityFrom(damagesource, f);
    }
    
    public void onBroken(final Entity entity) {
        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, Blocks.planks.stepSound.getDigResourcePath(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getFrequency() * 0.8f);
        boolean flag = true;
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
            flag = false;
        }
        if (flag) {
            this.entityDropItem(this.getBannerItem(), 0.0f);
        }
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return this.getBannerItem();
    }
    
    private ItemStack getBannerItem() {
        final ItemStack item = new ItemStack(LOTRMod.banner, 1, this.getBannerType().bannerID);
        if (this.protectData != null) {
            LOTRItemBanner.setProtectionData(item, this.protectData);
        }
        return item;
    }
    
    public int getWidthPixels() {
        return 16;
    }
    
    public int getHeightPixels() {
        return 32;
    }
}
