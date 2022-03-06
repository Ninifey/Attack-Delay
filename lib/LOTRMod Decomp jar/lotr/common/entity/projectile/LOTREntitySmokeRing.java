// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntitySmokeRing extends EntityThrowable
{
    public static int MAX_AGE;
    public int renderSmokeAge;
    public int prevRenderSmokeAge;
    
    public LOTREntitySmokeRing(final World world) {
        super(world);
        this.renderSmokeAge = -1;
        this.prevRenderSmokeAge = -1;
    }
    
    public LOTREntitySmokeRing(final World world, final EntityLivingBase entityliving) {
        super(world, entityliving);
        this.renderSmokeAge = -1;
        this.prevRenderSmokeAge = -1;
    }
    
    public LOTREntitySmokeRing(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
        this.renderSmokeAge = -1;
        this.prevRenderSmokeAge = -1;
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    public int getSmokeAge() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(16);
    }
    
    public void setSmokeAge(final int age) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)age);
    }
    
    public int getSmokeColour() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17);
    }
    
    public void setSmokeColour(final int colour) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)colour);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("SmokeAge", this.getSmokeAge());
        nbt.setInteger("SmokeColour", this.getSmokeColour());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSmokeAge(nbt.getInteger("SmokeAge"));
        this.setSmokeColour(nbt.getInteger("SmokeColour"));
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.isInWater() && !((Entity)this).worldObj.isClient) {
            this.setDead();
        }
        if (this.renderSmokeAge == -1) {
            final int smokeAge = this.getSmokeAge();
            this.renderSmokeAge = smokeAge;
            this.prevRenderSmokeAge = smokeAge;
        }
        else {
            this.prevRenderSmokeAge = this.renderSmokeAge;
        }
        if (!((Entity)this).worldObj.isClient) {
            this.setSmokeAge(this.getSmokeAge() + 1);
            if (this.getSmokeAge() >= LOTREntitySmokeRing.MAX_AGE) {
                this.setDead();
            }
        }
        this.renderSmokeAge = this.getSmokeAge();
    }
    
    public float getRenderSmokeAge(final float f) {
        final float smokeAge = this.prevRenderSmokeAge + (this.renderSmokeAge - this.prevRenderSmokeAge) * f;
        return smokeAge / LOTREntitySmokeRing.MAX_AGE;
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && m.entityHit == this.getThrower()) {
            return;
        }
        if (!((Entity)this).worldObj.isClient) {
            this.setDead();
        }
    }
    
    protected float func_70182_d() {
        return 0.1f;
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
    
    static {
        LOTREntitySmokeRing.MAX_AGE = 300;
    }
}
