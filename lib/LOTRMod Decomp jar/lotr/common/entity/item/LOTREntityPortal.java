// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Teleporter;
import lotr.common.world.LOTRTeleporter;
import net.minecraftforge.common.DimensionManager;
import java.util.List;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRDimension;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;

public class LOTREntityPortal extends Entity
{
    public static int MAX_SCALE;
    private float prevPortalRotation;
    private float portalRotation;
    
    public LOTREntityPortal(final World world) {
        super(world);
        this.setSize(3.0f, 1.5f);
    }
    
    protected void entityInit() {
        super.dataWatcher.addObject(10, (Object)0);
    }
    
    public int getScale() {
        return super.dataWatcher.getWatchableObjectShort(10);
    }
    
    public void setScale(final int i) {
        super.dataWatcher.updateObject(10, (Object)(short)i);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setInteger("Scale", this.getScale());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        this.setScale(nbt.getInteger("Scale"));
    }
    
    public float getPortalRotation(final float f) {
        return this.prevPortalRotation + (this.portalRotation - this.prevPortalRotation) * f;
    }
    
    public void onUpdate() {
        this.prevPortalRotation = this.portalRotation;
        this.portalRotation += 4.0f;
        while (this.portalRotation - this.prevPortalRotation < -180.0f) {
            this.prevPortalRotation -= 360.0f;
        }
        while (this.portalRotation - this.prevPortalRotation >= 180.0f) {
            this.prevPortalRotation += 360.0f;
        }
        if (!super.worldObj.isClient && super.dimension != 0 && super.dimension != LOTRDimension.MIDDLE_EARTH.dimensionID) {
            this.setDead();
        }
        if (!super.worldObj.isClient && this.getScale() < LOTREntityPortal.MAX_SCALE) {
            this.setScale(this.getScale() + 1);
        }
        if (this.getScale() >= LOTREntityPortal.MAX_SCALE) {
            final List players = super.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, super.boundingBox.expand(8.0, 8.0, 8.0));
            for (int i = 0; i < players.size(); ++i) {
                final EntityPlayer entityplayer = players.get(i);
                if (super.boundingBox.intersectsWith(((Entity)entityplayer).boundingBox) && ((Entity)entityplayer).ridingEntity == null && ((Entity)entityplayer).riddenByEntity == null) {
                    LOTRMod.proxy.setInPortal(entityplayer);
                }
            }
            final List entities = super.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, super.boundingBox.expand(8.0, 8.0, 8.0));
            for (int j = 0; j < entities.size(); ++j) {
                final Entity entity = entities.get(j);
                if (!(entity instanceof EntityPlayer) && super.boundingBox.intersectsWith(entity.boundingBox) && entity.ridingEntity == null && entity.riddenByEntity == null && entity.timeUntilPortal == 0) {
                    this.transferEntity(entity);
                }
            }
            if (super.rand.nextInt(50) == 0) {
                super.worldObj.playSoundAtEntity((Entity)this, "portal.portal", 0.5f, super.rand.nextFloat() * 0.4f + 0.8f);
            }
            for (int j = 0; j < 2; ++j) {
                final double d = super.posX - 3.0 + super.rand.nextFloat() * 6.0f;
                final double d2 = super.posY - 0.75 + super.rand.nextFloat() * 3.0f;
                final double d3 = super.posZ - 3.0 + super.rand.nextFloat() * 6.0f;
                final double d4 = (super.posX - d) / 8.0;
                final double d5 = (super.posY + 1.5 - d2) / 8.0;
                final double d6 = (super.posZ - d3) / 8.0;
                final double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
                double d8 = 1.0 - d7;
                double d9 = 0.0;
                double d10 = 0.0;
                double d11 = 0.0;
                if (d8 > 0.0) {
                    d8 *= d8;
                    d9 += d4 / d7 * d8 * 0.2;
                    d10 += d5 / d7 * d8 * 0.2;
                    d11 += d6 / d7 * d8 * 0.2;
                }
                super.worldObj.spawnParticle("flame", d, d2, d3, d9, d10, d11);
            }
        }
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public boolean isEntityInvulnerable() {
        return true;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }
    
    private void transferEntity(final Entity entity) {
        if (!super.worldObj.isClient) {
            int dimension = 0;
            if (entity.dimension == 0) {
                dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
            }
            else if (entity.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                dimension = 0;
            }
            LOTRMod.transferEntityToDimension(entity, dimension, new LOTRTeleporter(DimensionManager.getWorld(dimension), true));
        }
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public void applyEntityCollision(final Entity entity) {
    }
    
    public boolean hitByEntity(final Entity entity) {
        return entity instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), 0.0f);
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final Entity entity = damagesource.getEntity();
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
            if (!super.worldObj.isClient) {
                final Block.SoundType sound = Blocks.glass.stepSound;
                super.worldObj.playSoundAtEntity((Entity)this, sound.getDigResourcePath(), (sound.getVolume() + 1.0f) / 2.0f, sound.getFrequency() * 0.8f);
                super.worldObj.setEntityState((Entity)this, (byte)16);
                this.setDead();
            }
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 16) {
            for (int l = 0; l < 16; ++l) {
                super.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(LOTRMod.goldRing), super.posX + (super.rand.nextDouble() - 0.5) * super.width, super.posY + super.rand.nextDouble() * super.height, super.posZ + (super.rand.nextDouble() - 0.5) * super.width, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.goldRing);
    }
    
    static {
        LOTREntityPortal.MAX_SCALE = 120;
    }
}
