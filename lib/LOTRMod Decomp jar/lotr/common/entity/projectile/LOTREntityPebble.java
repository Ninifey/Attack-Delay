// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityPebble extends EntityThrowable
{
    private boolean isSling;
    
    public LOTREntityPebble(final World world) {
        super(world);
        this.isSling = false;
    }
    
    public LOTREntityPebble(final World world, final EntityLivingBase entityliving) {
        super(world, entityliving);
        this.isSling = false;
    }
    
    public LOTREntityPebble(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
        this.isSling = false;
    }
    
    public LOTREntityPebble setSling() {
        this.isSling = true;
        return this;
    }
    
    public boolean isSling() {
        return this.isSling;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Sling", this.isSling);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.isSling = nbt.getBoolean("Sling");
    }
    
    public void onUpdate() {
        super.onUpdate();
        final float speed = MathHelper.sqrt_double(((Entity)this).motionX * ((Entity)this).motionX + ((Entity)this).motionZ * ((Entity)this).motionZ);
        if (speed > 0.1f && ((Entity)this).motionY < 0.0 && this.isInWater()) {
            final float factor = MathHelper.randomFloatClamp(((Entity)this).rand, 0.4f, 0.8f);
            ((Entity)this).motionX *= factor;
            ((Entity)this).motionZ *= factor;
            ((Entity)this).motionY += factor;
        }
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (m.entityHit != null) {
            final float damage = this.isSling ? 2.0f : 1.0f;
            m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), damage);
        }
        if (!((Entity)this).worldObj.isClient) {
            this.entityDropItem(new ItemStack(LOTRMod.pebble), 0.0f);
            this.setDead();
        }
    }
    
    protected float func_70182_d() {
        return 1.0f;
    }
    
    protected float getGravityVelocity() {
        return 0.04f;
    }
}
